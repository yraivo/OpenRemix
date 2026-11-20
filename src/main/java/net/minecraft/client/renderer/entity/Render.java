package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import optifine.OptiFineConfig;
import shadersmod.client.Shaders;

import org.lwjgl.opengl.GL11;

public abstract class Render {
    private static ResourceLocation shadowTextures;
    protected float shadowOpaque = 1.0F;

    protected float shadowSize;

    protected RenderManager renderManager;
    private static String[] legacyEntityRenderState;

    // region String decoding support

    // endregion

    public Render(RenderManager aBLValue) {
        this.renderManager = aBLValue;
    }

    protected void renderName(Entity entity, double p171_1, double p171_2, double p171_3) {
        if (this.canRenderName(entity)) {
            this.renderLivingLabel(
                    entity, entity.getDisplayName().getFormattedText(), p171_1, p171_2, p171_3, 64);
        }
    }

    static {
        String[] astring1 = new String[3];
        setLegacyEntityRenderState(astring1);
        net.minecraft.client.renderer.entity.Render.shadowTextures = new ResourceLocation("textures/misc/shadow.png");
    }

    public FontRenderer getFontRendererFromRenderManager() {
        return this.renderManager.getFontRenderer();
    }

    protected void renderOffsetLivingLabel(
            Entity entity,
            double p171_1,
            double p171_2,
            double p171_3,
            String text,
            float p171_5,
            double p171_6) {
        this.renderLivingLabel(entity, text, p171_1, p171_2, p171_3, 64);
    }

    public void bindTexture(ResourceLocation resourceLocation) {
        this.renderManager.renderEngine.bindTexture(resourceLocation);
    }

    public boolean shouldRender(
            Entity entity, ICamera iCamera, double p171_2, double p171_3, double p171_4) {
        return entity.isInRangeToRender3d(p171_2, p171_3, p171_4)
                && (entity.ignoreFrustumCheck
                        || iCamera.isBoundingBoxInFrustum(entity.getEntityBoundingBox()));
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    protected boolean canRenderName(Entity entity) {
        return entity.getAlwaysRenderNameTagForRender() && entity.jvm171_d_45ffeb83d14f3c();
    }

    public void doRenderShadowAndFire(
            Entity entity,
            double p171_1,
            double p171_2,
            double p171_3,
            float p171_4,
            float p171_5) {
        if (this.renderManager.options != null) {
            if (this.renderManager.options.fancyGraphics
                    && this.shadowSize > 0.0F
                    && !entity.isInvisible()
                    && this.renderManager.isRenderShadow()) {
                double distanceToCamera =
                        this.renderManager.getDistanceToCamera(
                                entity.posX, entity.posY, entity.posZ);
                float f2 = (float) ((1.0 - distanceToCamera / 256.0) * this.shadowOpaque);
                if (f2 > 0.0F) {
                    this.renderShadow(entity, p171_1, p171_2, p171_3, f2, p171_5);
                }
            }

            if (entity.canRenderOnFire()
                    && (!(entity instanceof net.minecraft.entity.player.PlayerEntity)
                            || !((net.minecraft.entity.player.PlayerEntity) entity).isSpectator())) {
                this.renderEntityOnFire(entity, p171_1, p171_2, p171_3, p171_5);
            }
        }
    }

    public static void renderOffsetAABB(
            BoundingBox boundingBox, double p171_1, double p171_2, double p171_3) {
        net.minecraft.client.renderer.GlStateManager.disableTexture2D();
        net.minecraft.client.renderer.Tessellator instance = net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer worldRenderer = instance.getWorldRenderer();
        net.minecraft.client.renderer.GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        worldRenderer.startDrawingQuads();
        worldRenderer.setTranslation(p171_1, p171_2, p171_3);
        worldRenderer.setVertexNormal(0.0F, 0.0F, -1.0F);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.setVertexNormal(0.0F, 0.0F, 1.0F);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.setVertexNormal(0.0F, -1.0F, 0.0F);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.setVertexNormal(0.0F, 1.0F, 0.0F);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.setVertexNormal(-1.0F, 0.0F, 0.0F);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.setVertexNormal(1.0F, 0.0F, 0.0F);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.setTranslation(0.0, 0.0, 0.0);
        instance.draw();
        net.minecraft.client.renderer.GlStateManager.enableTexture2D();
    }

    public RenderManager getRenderManager() {
        return this.renderManager;
    }

    private void renderShadowBlock(
            Block block,
            double d0,
            double d1,
            double d2,
            BlockPos blockPos,
            float f,
            float f1,
            double d3,
            double d4,
            double d5) {
        if (block.isFullCube()) {
            Tessellator instance = Tessellator.getInstance();
            WorldRenderer worldRenderer = instance.getWorldRenderer();
            double d6 =
                    (f - (d1 - (blockPos.getY() + d4)) / 2.0)
                            * 0.5
                            * this.getWorldFromRenderManager().getLightBrightness(blockPos);
            if (d6 >= 0.0) {
                if (d6 > 1.0) {
                    d6 = 1.0;
                }

                worldRenderer.setVertexColorFromFloats(1.0F, 1.0F, 1.0F, (float) d6);
                double d7 = blockPos.getX() + block.getBlockBoundsMinX() + d3;
                double d8 = blockPos.getX() + block.getBlockBoundsMaxX() + d3;
                double d9 = blockPos.getY() + block.getBlockBoundsMinY() + d4 + 0.015625;
                double d10 = blockPos.getZ() + block.getBlockBoundsMinZ() + d5;
                double d11 = blockPos.getZ() + block.getBlockBoundsMaxZ() + d5;
                float f2 = (float) ((d0 - d7) / 2.0 / f1 + 0.5);
                float f3 = (float) ((d0 - d8) / 2.0 / f1 + 0.5);
                float f4 = (float) ((d2 - d10) / 2.0 / f1 + 0.5);
                float f5 = (float) ((d2 - d11) / 2.0 / f1 + 0.5);
                worldRenderer.addVertexWithUV(d7, d9, d10, f2, f4);
                worldRenderer.addVertexWithUV(d7, d9, d11, f2, f5);
                worldRenderer.addVertexWithUV(d8, d9, d11, f3, f5);
                worldRenderer.addVertexWithUV(d8, d9, d10, f3, f4);
            }
        }
    }

    protected boolean bindEntityTexture(Entity entity) {
        ResourceLocation entityTexture = this.getEntityTexture(entity);
        if (entityTexture == null) {
            return false;
        }

        this.bindTexture(entityTexture);
        return true;
    }

    protected abstract ResourceLocation getEntityTexture(Entity entity);

    private void renderEntityOnFire(Entity entity, double d0, double d1, double d2, float f11) {
        GlStateManager.disableLighting();
        TextureMap textureMapBlocks = MinecraftClient.getMinecraft().getTextureMapBlocks();
        TextureAtlasSprite atlasSprite =
                textureMapBlocks.getAtlasSprite("minecraft:blocks/fire_layer_0");
        TextureAtlasSprite atlasSpriteValue =
                textureMapBlocks.getAtlasSprite("minecraft:blocks/fire_layer_1");
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) d0, (float) d1, (float) d2);
        float f = entity.width * 1.4F;
        GlStateManager.scale(f, f, f);
        getLegacyEntityRenderState();

        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        float f1 = 0.5F;

        float f2 = 0.0F;
        float f3 = entity.height / f;
        float f4 = (float) (entity.posY - entity.getEntityBoundingBox().minY);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.0F, -0.3F + (int) f3 * 0.02F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f5 = 0.0F;
        int i = 0;
        worldRenderer.startDrawingQuads();

        while (f3 > 0.0F) {
            TextureAtlasSprite textureAtlasSprite = i % 2 == 0 ? atlasSprite : atlasSpriteValue;
            this.bindTexture(TextureMap.locationBlocksTexture);
            float minU = textureAtlasSprite.getMinU();
            float minV = textureAtlasSprite.getMinV();
            float maxU = textureAtlasSprite.getMaxU();
            float maxV = textureAtlasSprite.getMaxV();
            if (i / 2 % 2 == 0) {
                float f10 = maxU;
                maxU = minU;
                minU = f10;
            }

            worldRenderer.addVertexWithUV(f1 - f2, 0.0F - f4, f5, maxU, maxV);
            worldRenderer.addVertexWithUV(-f1 - f2, 0.0F - f4, f5, minU, maxV);
            worldRenderer.addVertexWithUV(-f1 - f2, 1.4F - f4, f5, minU, minV);
            worldRenderer.addVertexWithUV(f1 - f2, 1.4F - f4, f5, maxU, minV);
            f3 -= 0.45F;
            f4 -= 0.45F;
            f1 *= 0.9F;
            f5 += 0.03F;
            i++;
        }

        instance.draw();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
    }

    private World getWorldFromRenderManager() {
        return this.renderManager.worldObj;
    }

    public static String[] getLegacyEntityRenderState() {
        return net.minecraft.client.renderer.entity.Render.legacyEntityRenderState;
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.renderName(entity, d0, d1, d2);
    }

    protected void renderLivingLabel(
            Entity entity, String text, double d0, double d1, double d2, int i) {
        double distanceSqToEntity = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
        if (distanceSqToEntity <= i * i) {
            FontRenderer fontRendererFromRenderManager = this.getFontRendererFromRenderManager();
            float f = 1.6F;
            float f1 = 0.016666668F * f;
            GlStateManager.pushMatrix();
            GlStateManager.translate(
                    (float) d0 + 0.0F, (float) d1 + entity.height + 0.5F, (float) d2);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-f1, -f1, f1);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            Tessellator instance = Tessellator.getInstance();
            WorldRenderer worldRenderer = instance.getWorldRenderer();
            byte b0 = 0;
            if (text.equals("deadmau5")) {
                b0 = -10;
            }

            GlStateManager.disableTexture2D();
            worldRenderer.startDrawingQuads();
            int j = fontRendererFromRenderManager.getStringWidth(text) / 2;
            worldRenderer.setVertexColorFromFloats(0.0F, 0.0F, 0.0F, 0.25F);
            worldRenderer.addVertex(-j - 1, -1 + b0, 0.0);
            worldRenderer.addVertex(-j - 1, 8 + b0, 0.0);
            worldRenderer.addVertex(j + 1, 8 + b0, 0.0);
            worldRenderer.addVertex(j + 1, -1 + b0, 0.0);
            instance.draw();
            GlStateManager.enableTexture2D();
            fontRendererFromRenderManager.drawString(
                    text, -fontRendererFromRenderManager.getStringWidth(text) / 2, b0, 553648127);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            fontRendererFromRenderManager.drawString(
                    text, -fontRendererFromRenderManager.getStringWidth(text) / 2, b0, -1);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }

    private void renderShadow(
            Entity entity, double d0, double d1, double d2, float f, float f1) {
        getLegacyEntityRenderState();

        if (!OptiFineConfig.isShaders() || !Shaders.shouldSkipDefaultShadow) {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            this.renderManager.renderEngine.bindTexture(net.minecraft.client.renderer.entity.Render.shadowTextures);
            World world = this.getWorldFromRenderManager();
            GlStateManager.depthMask(false);
            float shadowSizeValue = this.shadowSize;
            if (entity instanceof EntityLiving) {
                EntityLiving entityLiving = (EntityLiving) entity;
                shadowSizeValue *= entityLiving.getRenderSizeModifier();
                if (entityLiving.isChild()) {
                    shadowSizeValue *= 0.5F;
                }
            }

            double d8 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * f1;
            double d3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * f1;
            double d4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * f1;
            int i = MathHelper.floor(d8 - shadowSizeValue);
            int j = MathHelper.floor(d8 + shadowSizeValue);
            int k = MathHelper.floor(d3 - shadowSizeValue);
            int l = MathHelper.floor(d3);
            int i1 = MathHelper.floor(d4 - shadowSizeValue);
            int j1 = MathHelper.floor(d4 + shadowSizeValue);
            double d5 = d0 - d8;
            double d6 = d1 - d3;
            double d7 = d2 - d4;
            Tessellator instance = Tessellator.getInstance();
            WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.startDrawingQuads();

            for (java.util.Iterator<?> __recoveryIterator171_45038 =
                            (BlockPos.getAllInBox(new BlockPos(i, k, i1), new BlockPos(j, l, j1)))
                                    .iterator();
                    (__recoveryIterator171_45038).hasNext(); ) {
                BlockPos blockPos = (BlockPos) (__recoveryIterator171_45038).next();
                Block block = world.getBlockState(blockPos.offsetDown()).getBlock();
                if (block.getRenderType() != -1 && world.getLightFromNeighbors(blockPos) > 3) {
                    this.renderShadowBlock(
                            block, d0, d1, d2, blockPos, f, shadowSizeValue, d5, d6, d7);
                }
            }

            instance.draw();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
        }
    }

    public static void setLegacyEntityRenderState(String[] astring) {
        net.minecraft.client.renderer.entity.Render.legacyEntityRenderState = astring;
    }
}
