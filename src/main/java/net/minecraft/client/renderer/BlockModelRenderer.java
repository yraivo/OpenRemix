package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEnumOffsetType;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3i;
import net.minecraft.world.IBlockAccess;

import optifine.BetterGrass;
import optifine.BetterSnow;
import optifine.ConnectedTextures;
import optifine.CustomColors;
import optifine.NaturalTextures;
import optifine.OptiFineConfig;
import optifine.Reflector;
import optifine.RenderEnv;
import optifine.SmartLeaves;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.render.XrayModule;

import java.util.BitSet;
import java.util.List;

public class BlockModelRenderer {

    private static float aoLightValueOpaque;

    public boolean renderAmbientOccludedModelFromWorldState(
            IBlockAccess iBlockAccess,
            IBakedModel iBakedModel,
            Block block,
            BlockPos blockPos,
            WorldRenderer worldRenderer,
            boolean flag) {
        return this.renderAmbientOccludedModel(
                iBlockAccess,
                iBakedModel,
                block,
                iBlockAccess.getBlockState(blockPos),
                blockPos,
                worldRenderer,
                flag);
    }

    static {
        net.minecraft.client.renderer.BlockModelRenderer.aoLightValueOpaque = 0.2F;
    }

    public boolean renderBlockModel(
            IBlockAccess world,
            IBakedModel model,
            BlockState state,
            BlockPos position,
            WorldRenderer vertices,
            boolean checkSides) {
        boolean ambientOcclusion =
                MinecraftClient.isAmbientOcclusionEnabled()
                        && state.getBlock().getLightValue() == 0
                        && model.isAmbientOcclusion();

        try {
            Block block = state.getBlock();
            RemixClient.INSTANCE.getClientServices();
            // Xray's custom alpha is applied in the ambient quad path.
            if (ClientServices.moduleManager.xray.isEnabled()) {
                ambientOcclusion = true;
            }

            if (OptiFineConfig.isTreesSmart() && block instanceof BlockLeavesBase) {
                model = SmartLeaves.getLeavesModel(model);
            }

            return ambientOcclusion
                    ? this.renderAmbientOccludedModel(
                            world, model, block, state, position, vertices, checkSides)
                    : this.renderFlatLitModel(
                            world, model, block, state, position, vertices, checkSides);
        } catch (Throwable throwable) {
            CrashReport crash = CrashReport.makeCrashReport(throwable, "Tesselating block model");
            CrashReportCategory category = crash.makeCategory("Block model being tesselated");
            CrashReportCategory.addBlockInfo(category, position, state);
            category.addCrashSection("Using AO", ambientOcclusion);
            throw new ReportedException(crash);
        }
    }

    public boolean renderFlatLitModelFromWorldState(
            IBlockAccess iBlockAccess,
            IBakedModel iBakedModel,
            Block block,
            BlockPos blockPos,
            WorldRenderer worldRenderer,
            boolean flag) {
        return this.renderFlatLitModel(
                iBlockAccess,
                iBakedModel,
                block,
                iBlockAccess.getBlockState(blockPos),
                blockPos,
                worldRenderer,
                flag);
    }

    private void fillQuadBounds(
            Block block, int[] vertexData, Facing face, float[] bounds, BitSet flags) {
        float minX = 32.0F;
        float minY = 32.0F;
        float minZ = 32.0F;
        float maxX = -32.0F;
        float maxY = -32.0F;
        float maxZ = -32.0F;
        boolean legacyFaceFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        int stride = vertexData.length / 4;
        int corner = 0;

        while (corner < 4) {
            float x = Float.intBitsToFloat(vertexData[corner * stride]);
            float y = Float.intBitsToFloat(vertexData[corner * stride + 1]);
            float z = Float.intBitsToFloat(vertexData[corner * stride + 2]);
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            minZ = Math.min(minZ, z);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
            maxZ = Math.max(maxZ, z);
            corner++;
            if (legacyFaceFlow) {
                break;
            }
        }

        if (bounds != null) {
            bounds[Facing.WEST.getIndex()] = minX;
            bounds[Facing.EAST.getIndex()] = maxX;
            bounds[Facing.DOWN.getIndex()] = minY;
            bounds[Facing.UP.getIndex()] = maxY;
            bounds[Facing.NORTH.getIndex()] = minZ;
            bounds[Facing.SOUTH.getIndex()] = maxZ;
            bounds[Facing.WEST.getIndex() + Facing.VALUES.length] = 1.0F - minX;
            bounds[Facing.EAST.getIndex() + Facing.VALUES.length] = 1.0F - maxX;
            bounds[Facing.DOWN.getIndex() + Facing.VALUES.length] = 1.0F - minY;
            bounds[Facing.UP.getIndex() + Facing.VALUES.length] = 1.0F - maxY;
            bounds[Facing.NORTH.getIndex() + Facing.VALUES.length] = 1.0F - minZ;
            bounds[Facing.SOUTH.getIndex() + Facing.VALUES.length] = 1.0F - maxZ;
        }

        boolean partialX = minX >= 1.0E-4F || maxX <= 0.9999F;
        boolean partialY = minY >= 1.0E-4F || maxY <= 0.9999F;
        boolean partialZ = minZ >= 1.0E-4F || maxZ <= 0.9999F;
        switch (face) {
            case DOWN: // Down
                flags.set(1, partialX || partialZ);
                flags.set(0, (minY < 1.0E-4F || block.isFullCube()) && minY == maxY);
                if (!legacyFaceFlow) return;
            case UP: // Up
                flags.set(1, partialX || partialZ);
                flags.set(0, (maxY > 0.9999F || block.isFullCube()) && minY == maxY);
                if (!legacyFaceFlow) return;
            case NORTH: // North
                flags.set(1, partialX || partialY);
                flags.set(0, (minZ < 1.0E-4F || block.isFullCube()) && minZ == maxZ);
                if (!legacyFaceFlow) return;
            case SOUTH: // South
                flags.set(1, partialX || partialY);
                flags.set(0, (maxZ > 0.9999F || block.isFullCube()) && minZ == maxZ);
                if (!legacyFaceFlow) return;
            case WEST: // West
                flags.set(1, partialY || partialZ);
                flags.set(0, (minX < 1.0E-4F || block.isFullCube()) && minX == maxX);
                if (!legacyFaceFlow) return;
            case EAST: // East
                flags.set(1, partialY || partialZ);
                flags.set(0, (maxX > 0.9999F || block.isFullCube()) && minX == maxX);
                return;
            default:
                return;
        }
    }

    public static void updateAoLightValue() {
        net.minecraft.client.renderer.BlockModelRenderer.aoLightValueOpaque =
                1.0F - OptiFineConfig.getAmbientOcclusionLevel() * 0.8F;
    }

    public boolean renderFlatLitModel(
            IBlockAccess world,
            IBakedModel model,
            Block block,
            BlockState state,
            BlockPos position,
            WorldRenderer vertices,
            boolean checkSides) {
        boolean renderedQuads = false;
        boolean legacyFaceFlow = WorldRenderer.getLegacyRenderFlowMarker() == 0;
        RenderEnv renderEnv = null;
        for (Facing face : Facing.VALUES) {
            List faceQuads = model.getFaceQuads(face);
            if (!faceQuads.isEmpty()) {
                BlockPos neighbor = position.offset(face);
                boolean visibleFace = block.shouldSideBeRendered(world, neighbor, face);
                RemixClient.INSTANCE.getClientServices();
                XrayModule xray = ClientServices.moduleManager.xray;
                if (xray.isEnabled() && xray.isVisibleBlock(block)) {
                    visibleFace = true;
                }

                if (!checkSides || visibleFace) {
                    if (renderEnv == null) {
                        renderEnv = RenderEnv.getInstance(world, state, position);
                    }

                    if (!renderEnv.isBreakingAnimation(faceQuads)
                            && OptiFineConfig.isBetterGrass()) {
                        faceQuads =
                                BetterGrass.getFaceQuads(world, block, position, face, faceQuads);
                    }

                    int brightness = block.getMixedBrightnessForBlock(world, neighbor);
                    this.renderModelStandardQuads(
                            world,
                            block,
                            position,
                            face,
                            brightness,
                            false,
                            vertices,
                            faceQuads,
                            renderEnv);
                    renderedQuads = true;
                }
            }

            if (legacyFaceFlow) {
                break;
            }
        }

        List generalQuads = model.getGeneralQuads();
        if (!generalQuads.isEmpty()) {
            if (renderEnv == null) {
                renderEnv = RenderEnv.getInstance(world, state, position);
            }

            this.renderModelStandardQuads(
                    world, block, position, null, -1, true, vertices, generalQuads, renderEnv);
            renderedQuads = true;
        }

        if (renderEnv != null
                && OptiFineConfig.isBetterSnow()
                && !renderEnv.isBreakingAnimation()
                && BetterSnow.shouldRender(world, block, state, position)) {
            IBakedModel snowModel = BetterSnow.getModelSnowLayer();
            BlockState snowState = BetterSnow.getStateSnowLayer();
            this.renderFlatLitModel(
                    world, snowModel, snowState.getBlock(), snowState, position, vertices, true);
        }

        return renderedQuads;
    }

    public BlockModelRenderer() {
        if (Reflector.Is.exists()) {
            Reflector.setFieldValue(Reflector.Is, false);
        }
    }

    private void renderModelAmbientOcclusionQuads(
            IBlockAccess world,
            Block block,
            BlockPos position,
            WorldRenderer vertices,
            List quads,
            RenderEnv renderEnv) {
        boolean legacyQuadFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        RemixClient.INSTANCE.getClientServices();
        XrayModule xray = ClientServices.moduleManager.xray;
        boolean xrayHighlighted = xray.isEnabled() && xray.isVisibleBlock(block);
        float[] quadBounds = renderEnv.getQuadBounds();
        BitSet boundsFlags = renderEnv.getBoundsFlags();
        BlockModelRendererAmbientOcclusionFace ao = renderEnv.getAoFace();
        BlockState state = renderEnv.getBlockState();

        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();
        BlockEnumOffsetType offsetType = block.getOffsetType();
        if (offsetType != BlockEnumOffsetType.NONE) {
            long seed = MathHelper.positionRandom(position);
            x += ((float) (seed >> 16 & 15L) / 15.0F - 0.5) * 0.5;
            z += ((float) (seed >> 24 & 15L) / 15.0F - 0.5) * 0.5;
            if (offsetType == BlockEnumOffsetType.XYZ) {
                y += ((float) (seed >> 20 & 15L) / 15.0F - 1.0) * 0.2;
            }
        }

        for (Object entry : quads) {
            BakedQuad quad = (BakedQuad) entry;
            if (!renderEnv.isBreakingAnimation(quad)) {
                BakedQuad original = quad;
                if (OptiFineConfig.isConnectedTextures()) {
                    quad =
                            ConnectedTextures.getConnectedTexture(
                                    world, state, position, quad, renderEnv);
                }
                if (quad == original && OptiFineConfig.isNaturalTextures()) {
                    quad = NaturalTextures.getNaturalTexture(position, quad);
                }
            }

            this.fillQuadBounds(
                    block, quad.getVertexData(), quad.getFace(), quadBounds, boundsFlags);
            ao.updateVertexBrightness(
                    world, block, position, quad.getFace(), quadBounds, boundsFlags);
            boolean multiTexture = vertices.isMultiTexture();
            if (multiTexture) {
                vertices.addVertexData(quad.getVertexDataSingle());
                vertices.putSprite(quad.getSprite());
            }
            if (!multiTexture || legacyQuadFlow) {
                vertices.addVertexData(quad.getVertexData());
            }

            int[] brightness = BlockModelRendererAmbientOcclusionFace.getCornerBrightness(ao);
            vertices.putBrightness4(brightness[0], brightness[1], brightness[2], brightness[3]);
            this.applyAmbientQuadColors(
                    world,
                    block,
                    position,
                    vertices,
                    quad,
                    renderEnv,
                    BlockModelRendererAmbientOcclusionFace.getCornerLightMultipliers(ao),
                    xrayHighlighted,
                    legacyQuadFlow);
            vertices.putPosition(x, y, z);
            if (legacyQuadFlow) {
                break;
            }
        }
    }

    private void applyAmbientQuadColors(
            IBlockAccess world,
            Block block,
            BlockPos position,
            WorldRenderer vertices,
            BakedQuad quad,
            RenderEnv renderEnv,
            float[] shade,
            boolean xrayHighlighted,
            boolean legacyQuadFlow) {
        int customColor = CustomColors.getColorMultiplier(quad, block, world, position, renderEnv);
        if (!quad.hasTintIndex() && customColor < 0) {
            for (int corner = 0; corner < 4; corner++) {
                float light = shade[corner];
                if (xrayHighlighted) {
                    vertices.putColorMultiplier(light, light, light, 4 - corner);
                }
            }
            if (!xrayHighlighted || legacyQuadFlow) {
                for (int corner = 0; corner < 4; corner++) {
                    float light = shade[corner];
                    vertices.putColorRGB_F(light, light, light, 4 - corner);
                }
            }
            if (!legacyQuadFlow) {
                return;
            }
        }

        int tint =
                customColor != -1 && !legacyQuadFlow
                        ? customColor
                        : block.colorMultiplier(world, position, quad.getTintIndex());
        if (EntityRenderer.anaglyphEnable) {
            tint = TextureUtil.anaglyphColor(tint);
        }
        float red = (tint >> 16 & 0xFF) / 255.0F;
        float green = (tint >> 8 & 0xFF) / 255.0F;
        float blue = (tint & 0xFF) / 255.0F;
        if (xrayHighlighted) {
            for (int corner = 0; corner < 4; corner++) {
                float light = shade[corner];
                vertices.putColorMultiplier(light * red, light * green, light * blue, 4 - corner);
            }
        }
        if (!xrayHighlighted || legacyQuadFlow) {
            for (int corner = 0; corner < 4; corner++) {
                float light = shade[corner];
                vertices.putColorRGB_F(light * red, light * green, light * blue, 4 - corner);
            }
        }
    }

    private void renderModelBrightnessColorQuads(
            float brightness, float red, float green, float blue, List quads) {
        boolean legacySingleQuad = WorldRenderer.getLegacyRenderFlowMarker() == 0;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer vertices = tessellator.getWorldRenderer();
        for (Object entry : quads) {
            BakedQuad quad = (BakedQuad) entry;
            vertices.startDrawingQuads();
            vertices.setVertexFormat(DefaultVertexFormats.ITEM);
            vertices.addVertexData(quad.getVertexData());
            boolean tinted = quad.hasTintIndex();
            if (tinted) {
                vertices.putColorRGB_F4(red * brightness, green * brightness, blue * brightness);
            }
            if (!tinted || legacySingleQuad) {
                vertices.putColorRGB_F4(brightness, brightness, brightness);
            }

            Vec3i normal = quad.getFace().getDirectionVec();
            vertices.putNormal(normal.getX(), normal.getY(), normal.getZ());
            tessellator.draw();
            if (legacySingleQuad) {
                break;
            }
        }
    }

    public static float fixAoLightValue(float f) {
        return f == 0.2F ? net.minecraft.client.renderer.BlockModelRenderer.aoLightValueOpaque : f;
    }

    public boolean renderModel(
            IBlockAccess iBlockAccess,
            IBakedModel iBakedModel,
            BlockState blockState,
            BlockPos blockPos,
            WorldRenderer worldRenderer) {
        Block block = blockState.getBlock();
        block.setBlockBoundsBasedOnState(iBlockAccess, blockPos);
        return this.renderBlockModel(
                iBlockAccess, iBakedModel, blockState, blockPos, worldRenderer, true);
    }

    public void renderModelBrightness(
            IBakedModel iBakedModel, BlockState blockState, float f, boolean flag) {
        Block block = blockState.getBlock();
        block.setBlockBoundsForItemRender();
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        int renderColor = block.getRenderColor(block.getStateForEntityRender(blockState));
        if (EntityRenderer.anaglyphEnable) {
            renderColor = TextureUtil.anaglyphColor(renderColor);
        }

        float f1 = (renderColor >> 16 & 0xFF) / 255.0F;
        float f2 = (renderColor >> 8 & 0xFF) / 255.0F;
        float f3 = (renderColor & 0xFF) / 255.0F;
        if (!flag) {
            GlStateManager.color(f, f, f, 1.0F);
        }

        this.renderModelBrightnessColor(iBakedModel, f, f1, f2, f3);
    }

    public void renderModelBrightnessColor(
            IBakedModel iBakedModel, float f, float f1, float f2, float f3) {
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        Facing[] facingArray = Facing.VALUES;
        int i = j;

        for (Facing facing : facingArray) {
            this.renderModelBrightnessColorQuads(f, f1, f2, f3, iBakedModel.getFaceQuads(facing));
            if (i == 0) {
                break;
            }
        }

        this.renderModelBrightnessColorQuads(f, f1, f2, f3, iBakedModel.getGeneralQuads());
    }

    private void renderModelStandardQuads(
            IBlockAccess iBlockAccess,
            Block block,
            BlockPos blockPos,
            Facing facing,
            int i,
            boolean flag,
            WorldRenderer worldRenderer,
            List list,
            RenderEnv renderEnv) {
        BitSet bitset = renderEnv.getBoundsFlags();
        BlockState blockState = renderEnv.getBlockState();
        double x = blockPos.getX();
        int k2 = WorldRenderer.getLegacyRenderFlowToken();
        double y = blockPos.getY();
        double z = blockPos.getZ();
        int j = k2;
        BlockEnumOffsetType offsetType = block.getOffsetType();
        if (offsetType != BlockEnumOffsetType.NONE) {
            int k = blockPos.getX();
            int l = blockPos.getZ();
            long l1 = k * 3129871;
            long l2 = l;

            long i1 = l1 ^ l2 * 116129781L;
            long i2 = i1 * i1;

            long j2 = i2 * 42317861L;

            i1 = j2 + i1 * 11L;
            l2 = i1 >> 16;

            x += ((float) (l2 & 15L) / 15.0F - 0.5) * 0.5;
            l2 = i1 >> 24;

            z += ((float) (l2 & 15L) / 15.0F - 0.5) * 0.5;
            if (offsetType == BlockEnumOffsetType.XYZ) {
                l2 = i1 >> 20;

                y += ((float) (l2 & 15L) / 15.0F - 1.0) * 0.2;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_58577 = (list).iterator();
                (__recoveryIterator171_58577).hasNext(); ) {
            BakedQuad bakedQuad = (BakedQuad) (__recoveryIterator171_58577).next();
            if (!renderEnv.isBreakingAnimation(bakedQuad)) {
                BakedQuad bakedQuadValue = bakedQuad;
                if (OptiFineConfig.isConnectedTextures()) {
                    bakedQuad =
                            ConnectedTextures.getConnectedTexture(
                                    iBlockAccess, blockState, blockPos, bakedQuad, renderEnv);
                }

                if (bakedQuad == bakedQuadValue && OptiFineConfig.isNaturalTextures()) {
                    bakedQuad = NaturalTextures.getNaturalTexture(blockPos, bakedQuad);
                }
            }

            if (flag) {
                this.fillQuadBounds(
                        block, bakedQuad.getVertexData(), bakedQuad.getFace(), null, bitset);
                i =
                        bitset.get(0)
                                ? block.getMixedBrightnessForBlock(
                                        iBlockAccess, blockPos.offset(bakedQuad.getFace()))
                                : block.getMixedBrightnessForBlock(iBlockAccess, blockPos);
            }

            boolean multiTexture = worldRenderer.isMultiTexture();
            if (multiTexture) {
                worldRenderer.addVertexData(bakedQuad.getVertexDataSingle());
                worldRenderer.putSprite(bakedQuad.getSprite());
            }
            if (!multiTexture || j != 0) {
                worldRenderer.addVertexData(bakedQuad.getVertexData());
            }

            worldRenderer.putBrightness4(i, i, i, i);
            int k1 =
                    CustomColors.getColorMultiplier(
                            bakedQuad, block, iBlockAccess, blockPos, renderEnv);
            if (bakedQuad.hasTintIndex() || k1 != -1) {
                int j1 =
                        k1 != -1 && j == 0
                                ? k1
                                : block.colorMultiplier(
                                        iBlockAccess, blockPos, bakedQuad.getTintIndex());

                if (EntityRenderer.anaglyphEnable) {
                    j1 = TextureUtil.anaglyphColor(j1);
                }

                float f = (j1 >> 16 & 0xFF) / 255.0F;
                float f1 = (j1 >> 8 & 0xFF) / 255.0F;
                float f2 = (j1 & 0xFF) / 255.0F;
                worldRenderer.putColorMultiplier(f, f1, f2, 4);
                worldRenderer.putColorMultiplier(f, f1, f2, 3);
                worldRenderer.putColorMultiplier(f, f1, f2, 2);
                worldRenderer.putColorMultiplier(f, f1, f2, 1);
            }

            worldRenderer.putPosition(x, y, z);
            if (j != 0) {
                break;
            }
        }
    }

    public boolean renderAmbientOccludedModel(
            IBlockAccess world,
            IBakedModel model,
            Block block,
            BlockState state,
            BlockPos position,
            WorldRenderer vertices,
            boolean checkSides) {
        boolean legacyFaceFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        vertices.setPackedLightmap(983055);
        RenderEnv renderEnv = null;
        boolean renderedFace = false;

        for (int index = 0; index < Facing.VALUES.length; index++) {
            Facing face = Facing.VALUES[index];
            List quads = model.getFaceQuads(face);
            if (!quads.isEmpty()) {
                boolean visibleFace =
                        block.shouldSideBeRendered(world, position.offset(face), face);
                RemixClient.INSTANCE.getClientServices();
                XrayModule xray = ClientServices.moduleManager.xray;
                if (xray.isEnabled()
                        && xray.isVisibleBlock(block)
                        && (!xray.exposedSetting.asBoolean() || renderedFace)) {
                    visibleFace = true;
                }

                if (!checkSides || visibleFace) {
                    if (renderEnv == null) {
                        renderEnv = RenderEnv.getInstance(world, state, position);
                    }
                    if (!renderEnv.isBreakingAnimation(quads) && OptiFineConfig.isBetterGrass()) {
                        quads = BetterGrass.getFaceQuads(world, block, position, face, quads);
                    }

                    this.renderModelAmbientOcclusionQuads(
                            world, block, position, vertices, quads, renderEnv);
                    if (!renderedFace) {
                        // Once a face is exposed, revisit earlier faces for the Xray exposed
                        // setting.
                        renderedFace = true;
                        index = -1;
                    }
                }
            }
            if (legacyFaceFlow) {
                break;
            }
        }

        List generalQuads = model.getGeneralQuads();
        if (!generalQuads.isEmpty()) {
            if (renderEnv == null) {
                renderEnv = RenderEnv.getInstance(world, state, position);
            }
            this.renderModelAmbientOcclusionQuads(
                    world, block, position, vertices, generalQuads, renderEnv);
            renderedFace = true;
        }

        if (renderEnv != null
                && OptiFineConfig.isBetterSnow()
                && !renderEnv.isBreakingAnimation()
                && BetterSnow.shouldRender(world, block, state, position)) {
            IBakedModel snowModel = BetterSnow.getModelSnowLayer();
            BlockState snowState = BetterSnow.getStateSnowLayer();
            this.renderAmbientOccludedModel(
                    world, snowModel, snowState.getBlock(), snowState, position, vertices, true);
        }
        return renderedFace;
    }
}
