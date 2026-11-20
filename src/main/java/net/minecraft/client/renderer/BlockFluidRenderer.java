package net.minecraft.client.renderer;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

import optifine.CustomColors;
import optifine.RenderEnv;

public class BlockFluidRenderer {

    private TextureAtlasSprite[] atlasSpritesWater;
    private TextureAtlasSprite[] atlasSpritesLava = new TextureAtlasSprite[2];


    private float getFluidHeight(IBlockAccess iBlockAccess, BlockPos blockPos, Material material) {
        int i1 = WorldRenderer.getLegacyRenderFlowToken();
        int j = 0;
        float f = 0.0F;
        int k = 0;
        int i = i1;

        while (k < 4) {
            BlockPos blockPosValue = blockPos.add(-(k & 1), 0, -(k >> 1 & 1));
            if (iBlockAccess.getBlockState(blockPosValue.offsetUp()).getBlock().getMaterial()
                    == material) {
                return 1.0F;
            }

            {
                BlockState blockState = iBlockAccess.getBlockState(blockPosValue);
                Material materialValue = blockState.getBlock().getMaterial();
                if (materialValue == material) {
                    int l = (Integer) blockState.getValue(BlockLiquid.LEVEL);
                    if (l >= 8 || l == 0) {
                        f += BlockLiquid.getLiquidHeightPercent(l) * 10.0F;
                        j += 10;
                    }

                    f += BlockLiquid.getLiquidHeightPercent(l);
                    j++;
                    if (!(i == 0)) {

                        if (!materialValue.isSolid()) {
                            f++;
                            j++;
                        }
                    }
                } else {

                    if (!materialValue.isSolid()) {
                        f++;
                        j++;
                    }
                }
            }

            k++;
            if (i != 0) {
                break;
            }
        }

        return 1.0F - f / j;
    }

    protected void initAtlasSprites() {
        TextureMap textureMapBlocks = MinecraftClient.getMinecraft().getTextureMapBlocks();
        this.atlasSpritesLava[0] = textureMapBlocks.getAtlasSprite("minecraft:blocks/lava_still");
        this.atlasSpritesLava[1] = textureMapBlocks.getAtlasSprite("minecraft:blocks/lava_flow");
        this.atlasSpritesWater[0] = textureMapBlocks.getAtlasSprite("minecraft:blocks/water_still");
        this.atlasSpritesWater[1] = textureMapBlocks.getAtlasSprite("minecraft:blocks/water_flow");
    }

    public BlockFluidRenderer() {
        this.atlasSpritesWater = new TextureAtlasSprite[2];
        this.initAtlasSprites();
    }

    public boolean renderFluid(
            IBlockAccess iBlockAccess,
            BlockState blockState,
            BlockPos blockPos,
            WorldRenderer worldRenderer) {
        BlockLiquid block = (BlockLiquid) blockState.getBlock();
        int j1 = WorldRenderer.getLegacyRenderFlowToken();
        block.setBlockBoundsBasedOnState(iBlockAccess, blockPos);
        int i = j1;
        TextureAtlasSprite[] textureAtlasSpriteArray =
                block.getMaterial() == Material.lava
                        ? this.atlasSpritesLava
                        : this.atlasSpritesWater;
        RenderEnv instance = RenderEnv.getInstance(iBlockAccess, blockState, blockPos);
        int j = CustomColors.getFluidColor(iBlockAccess, blockState, blockPos, instance);
        float f = (j >> 16 & 0xFF) / 255.0F;
        float f1 = (j >> 8 & 0xFF) / 255.0F;
        float f2 = (j & 0xFF) / 255.0F;
        boolean flag = block.shouldSideBeRendered(iBlockAccess, blockPos.offsetUp(), Facing.UP);
        boolean flag1 =
                block.shouldSideBeRendered(iBlockAccess, blockPos.offsetDown(), Facing.DOWN);
        boolean[] aboolean = instance.getBorderFlags();
        aboolean[0] =
                block.shouldSideBeRendered(iBlockAccess, blockPos.offsetNorth(), Facing.NORTH);
        aboolean[1] =
                block.shouldSideBeRendered(iBlockAccess, blockPos.offsetSouth(), Facing.SOUTH);
        aboolean[2] = block.shouldSideBeRendered(iBlockAccess, blockPos.offsetWest(), Facing.WEST);
        aboolean[3] = block.shouldSideBeRendered(iBlockAccess, blockPos.offsetEast(), Facing.EAST);
        if (!flag && !flag1 && !aboolean[0] && !aboolean[1] && !aboolean[2] && !aboolean[3]) {
            return false;
        }

        byte b0 = 0;
        float f3 = 0.5F;
        float f4 = 1.0F;
        float f5 = 0.8F;
        float f6 = 0.6F;
        Material material = block.getMaterial();
        float f7 = this.getFluidHeight(iBlockAccess, blockPos, material);
        float f8 = this.getFluidHeight(iBlockAccess, blockPos.offsetSouth(), material);
        float f9 = this.getFluidHeight(iBlockAccess, blockPos.offsetEast().offsetSouth(), material);
        float f10 = this.getFluidHeight(iBlockAccess, blockPos.offsetEast(), material);
        double x = blockPos.getX();
        double y = blockPos.getY();
        double z = blockPos.getZ();
        float f11 = 0.001F;
        if (flag) {
            b0 = 1;
            TextureAtlasSprite textureAtlasSprite = textureAtlasSpriteArray[0];
            float f12 = (float) BlockLiquid.getFlowDirection(iBlockAccess, blockPos, material);
            if (f12 > -999.0F) {
                textureAtlasSprite = textureAtlasSpriteArray[1];
            }

            float f13;
            float f14;
            float f15;
            float f16;
            float f17;
            float f18;
            float f19;
            float f20;
            label93:
            {
                worldRenderer.setSprite(textureAtlasSprite);
                f7 -= f11;
                f8 -= f11;
                f9 -= f11;
                f10 -= f11;
                if (f12 < -999.0F) {
                    f13 = textureAtlasSprite.getInterpolatedU(0.0);
                    f17 = textureAtlasSprite.getInterpolatedV(0.0);
                    f14 = f13;
                    f18 = textureAtlasSprite.getInterpolatedV(16.0);
                    f15 = textureAtlasSprite.getInterpolatedU(16.0);
                    f19 = f18;
                    f16 = f15;
                    f20 = f17;
                    if (i == 0) {
                        break label93;
                    }
                }

                float f21 = MathHelper.sin(f12) * 0.25F;
                float f22 = MathHelper.cos(f12) * 0.25F;

                f13 = textureAtlasSprite.getInterpolatedU(8.0F + (-f22 - f21) * 16.0F);
                f17 = textureAtlasSprite.getInterpolatedV(8.0F + (-f22 + f21) * 16.0F);
                f14 = textureAtlasSprite.getInterpolatedU(8.0F + (-f22 + f21) * 16.0F);
                f18 = textureAtlasSprite.getInterpolatedV(8.0F + (f22 + f21) * 16.0F);
                f15 = textureAtlasSprite.getInterpolatedU(8.0F + (f22 + f21) * 16.0F);
                f19 = textureAtlasSprite.getInterpolatedV(8.0F + (f22 - f21) * 16.0F);
                f16 = textureAtlasSprite.getInterpolatedU(8.0F + (f22 - f21) * 16.0F);
                f20 = textureAtlasSprite.getInterpolatedV(8.0F + (-f22 - f21) * 16.0F);
            }

            worldRenderer.setPackedLightmap(
                    block.getMixedBrightnessForBlock(iBlockAccess, blockPos));
            worldRenderer.setOpaqueVertexColorFromFloats(f4 * f, f4 * f1, f4 * f2);
            worldRenderer.addVertexWithUV(x + 0.0, y + f7, z + 0.0, f13, f17);
            worldRenderer.addVertexWithUV(x + 0.0, y + f8, z + 1.0, f14, f18);
            worldRenderer.addVertexWithUV(x + 1.0, y + f9, z + 1.0, f15, f19);
            worldRenderer.addVertexWithUV(x + 1.0, y + f10, z + 0.0, f16, f20);
            if (block.shouldRenderSides(iBlockAccess, blockPos.offsetUp())) {
                worldRenderer.addVertexWithUV(x + 0.0, y + f7, z + 0.0, f13, f17);
                worldRenderer.addVertexWithUV(x + 1.0, y + f10, z + 0.0, f16, f20);
                worldRenderer.addVertexWithUV(x + 1.0, y + f9, z + 1.0, f15, f19);
                worldRenderer.addVertexWithUV(x + 0.0, y + f8, z + 1.0, f14, f18);
            }
        }

        if (flag1) {
            worldRenderer.setPackedLightmap(
                    block.getMixedBrightnessForBlock(iBlockAccess, blockPos.offsetDown()));
            worldRenderer.setOpaqueVertexColorFromFloats(f3, f3, f3);
            float minU = textureAtlasSpriteArray[0].getMinU();
            float maxU = textureAtlasSpriteArray[0].getMaxU();
            float minV = textureAtlasSpriteArray[0].getMinV();
            float maxV = textureAtlasSpriteArray[0].getMaxV();
            worldRenderer.addVertexWithUV(x, y, z + 1.0, minU, maxV);
            worldRenderer.addVertexWithUV(x, y, z, minU, minV);
            worldRenderer.addVertexWithUV(x + 1.0, y, z, maxU, minV);
            worldRenderer.addVertexWithUV(x + 1.0, y, z + 1.0, maxU, maxV);
            b0 = 1;
        }

        int k = 0;

        while (k < 4) {
            int l = 0;
            int i1 = 0;
            if (k == 0) {
                i1--;
            }

            if (k == 1) {
                i1++;
            }

            if (k == 2) {
                l--;
            }

            if (k == 3) {
                l++;
            }

            BlockPos blockPosValue = blockPos.add(l, 0, i1);
            TextureAtlasSprite textureAtlasSpriteValue = textureAtlasSpriteArray[1];
            worldRenderer.setSprite(textureAtlasSpriteValue);
            if (aboolean[k]) {
                double d3;
                double d4;
                double d5;
                float f34;
                float f35;
                double d6;
                label103:
                {
                    if (k == 0) {
                        f34 = f7;
                        f35 = f10;
                        d6 = x;
                        d4 = x + 1.0;
                        d3 = z + f11;
                        d5 = z + f11;
                        if (i == 0) {
                            break label103;
                        }
                    }

                    if (k == 1) {
                        f34 = f9;
                        f35 = f8;
                        d6 = x + 1.0;
                        d4 = x;
                        d3 = z + 1.0 - f11;
                        d5 = z + 1.0 - f11;
                        if (i == 0) {
                            break label103;
                        }
                    }

                    if (k == 2) {
                        f34 = f8;
                        f35 = f7;
                        d6 = x + f11;
                        d4 = x + f11;
                        d3 = z + 1.0;
                        d5 = z;
                        if (i == 0) {
                            break label103;
                        }
                    }

                    f34 = f10;
                    f35 = f9;
                    d6 = x + 1.0 - f11;
                    d4 = x + 1.0 - f11;
                    d3 = z;
                    d5 = z + 1.0;
                }

                b0 = 1;
                float interpolatedU = textureAtlasSpriteValue.getInterpolatedU(0.0);
                float interpolatedUValue = textureAtlasSpriteValue.getInterpolatedU(8.0);
                float interpolatedV =
                        textureAtlasSpriteValue.getInterpolatedV((1.0F - f34) * 16.0F * 0.5F);
                float interpolatedVValue =
                        textureAtlasSpriteValue.getInterpolatedV((1.0F - f35) * 16.0F * 0.5F);
                float interpolatedVValue2 = textureAtlasSpriteValue.getInterpolatedV(8.0);
                worldRenderer.setPackedLightmap(
                        block.getMixedBrightnessForBlock(iBlockAccess, blockPosValue));
                float f29 = 1.0F;
                f29 *= k < 2 ? f5 : f6;
                worldRenderer.setOpaqueVertexColorFromFloats(
                        f4 * f29 * f, f4 * f29 * f1, f4 * f29 * f2);
                worldRenderer.addVertexWithUV(d6, y + f34, d3, interpolatedU, interpolatedV);
                worldRenderer.addVertexWithUV(
                        d4, y + f35, d5, interpolatedUValue, interpolatedVValue);
                worldRenderer.addVertexWithUV(
                        d4, y + 0.0, d5, interpolatedUValue, interpolatedVValue2);
                worldRenderer.addVertexWithUV(d6, y + 0.0, d3, interpolatedU, interpolatedVValue2);
                worldRenderer.addVertexWithUV(d6, y + 0.0, d3, interpolatedU, interpolatedVValue2);
                worldRenderer.addVertexWithUV(
                        d4, y + 0.0, d5, interpolatedUValue, interpolatedVValue2);
                worldRenderer.addVertexWithUV(
                        d4, y + f35, d5, interpolatedUValue, interpolatedVValue);
                worldRenderer.addVertexWithUV(d6, y + f34, d3, interpolatedU, interpolatedV);
            }

            k++;
            if (i != 0) {
                break;
            }
        }

        worldRenderer.setSprite((TextureAtlasSprite) null);
        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }
}
