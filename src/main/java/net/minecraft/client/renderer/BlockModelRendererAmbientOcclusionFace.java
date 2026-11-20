package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

import java.util.BitSet;

public class BlockModelRendererAmbientOcclusionFace {
    private int[] vertexBrightness;
    private final float[] vertexColorMultiplier = new float[4];

    private int getVertexBrightness(
            int i, int j, int k, int l, float f, float f1, float f2, float f3) {
        int i1 =
                (int)
                                ((i >> 16 & 0xFF) * f
                                        + (j >> 16 & 0xFF) * f1
                                        + (k >> 16 & 0xFF) * f2
                                        + (l >> 16 & 0xFF) * f3)
                        & 0xFF;
        int j1 =
                (int) ((i & 0xFF) * f + (j & 0xFF) * f1 + (k & 0xFF) * f2 + (l & 0xFF) * f3) & 0xFF;
        return i1 << 16 | j1;
    }

    static int[] getCornerBrightness(
            BlockModelRendererAmbientOcclusionFace blockModelRendererAmbientOcclusionFace) {
        return blockModelRendererAmbientOcclusionFace.vertexBrightness;
    }

    public BlockModelRendererAmbientOcclusionFace(BlockModelRenderer blockModelRenderer) {
        this.vertexBrightness = new int[4];
    }

    static float[] getCornerLightMultipliers(
            BlockModelRendererAmbientOcclusionFace blockModelRendererAmbientOcclusionFace) {
        return blockModelRendererAmbientOcclusionFace.vertexColorMultiplier;
    }

    private int getAoBrightness(int i, int j, int k, int l) {
        if (i == 0) {
            i = l;
        }

        if (j == 0) {
            j = l;
        }

        if (k == 0) {
            k = l;
        }

        return i + j + k + l >> 2 & 16711935;
    }

    public void updateVertexBrightness(
            IBlockAccess iBlockAccess,
            Block block,
            BlockPos blockPos,
            Facing facing,
            float[] afloat,
            BitSet bitset) {
        int i;
        BlockPos blockPosValue;
        BlockModelRendererEnumNeighborInfo blockModelRendererEnumNeighborInfo;
        BlockPos blockPosValue2;
        BlockPos blockPosValue3;
        int j;
        int k;
        int l;
        int i1;
        float f;
        float f1;
        float f2;
        float f3;
        boolean flag;
        boolean flag1;
        boolean flag2;
        boolean flag3;
        float f4;
        int j1;
        label72:
        {
            i = WorldRenderer.getLegacyRenderFlowToken();
            blockPosValue = bitset.get(0) ? blockPos.offset(facing) : blockPos;
            blockModelRendererEnumNeighborInfo =
                    BlockModelRendererEnumNeighborInfo.getNeighbourInfo(facing);
            blockPosValue2 = blockPosValue.offset(blockModelRendererEnumNeighborInfo.aPp[0]);
            blockPosValue3 = blockPosValue.offset(blockModelRendererEnumNeighborInfo.aPp[1]);
            BlockPos blockPosValue4 = blockPosValue.offset(blockModelRendererEnumNeighborInfo.aPp[2]);
            BlockPos blockPosValue5 = blockPosValue.offset(blockModelRendererEnumNeighborInfo.aPp[3]);
            j = block.getMixedBrightnessForBlock(iBlockAccess, blockPosValue2);
            k = block.getMixedBrightnessForBlock(iBlockAccess, blockPosValue3);
            l = block.getMixedBrightnessForBlock(iBlockAccess, blockPosValue4);
            i1 = block.getMixedBrightnessForBlock(iBlockAccess, blockPosValue5);
            f =
                    BlockModelRenderer.fixAoLightValue(
                            iBlockAccess.getBlockState(blockPosValue2).getBlock().getAmbientOcclusionLightValue());
            f1 =
                    BlockModelRenderer.fixAoLightValue(
                            iBlockAccess.getBlockState(blockPosValue3).getBlock().getAmbientOcclusionLightValue());
            f2 =
                    BlockModelRenderer.fixAoLightValue(
                            iBlockAccess.getBlockState(blockPosValue4).getBlock().getAmbientOcclusionLightValue());
            f3 =
                    BlockModelRenderer.fixAoLightValue(
                            iBlockAccess.getBlockState(blockPosValue5).getBlock().getAmbientOcclusionLightValue());
            flag =
                    iBlockAccess
                            .getBlockState(blockPosValue2.offset(facing))
                            .getBlock()
                            .isTranslucent();
            flag1 =
                    iBlockAccess
                            .getBlockState(blockPosValue3.offset(facing))
                            .getBlock()
                            .isTranslucent();
            flag2 =
                    iBlockAccess
                            .getBlockState(blockPosValue4.offset(facing))
                            .getBlock()
                            .isTranslucent();
            flag3 =
                    iBlockAccess
                            .getBlockState(blockPosValue5.offset(facing))
                            .getBlock()
                            .isTranslucent();
            if (!flag2 && !flag) {
                f4 = f;
                j1 = j;
                if (i == 0) {
                    break label72;
                }

                Entity.setLegacyGlobalFlowMarker(new String[5]);
            }

            BlockPos blockPosValue6 = blockPosValue2.offset(blockModelRendererEnumNeighborInfo.aPp[2]);
            f4 =
                    BlockModelRenderer.fixAoLightValue(
                            iBlockAccess.getBlockState(blockPosValue6).getBlock().getAmbientOcclusionLightValue());
            j1 = block.getMixedBrightnessForBlock(iBlockAccess, blockPosValue6);
        }

        float f5;
        int k1;
        label65:
        {
            if (!flag3 && !flag) {
                f5 = f;
                k1 = j;
                if (i == 0) {
                    break label65;
                }
            }

            BlockPos blockPosValue7 = blockPosValue2.offset(blockModelRendererEnumNeighborInfo.aPp[3]);
            f5 =
                    BlockModelRenderer.fixAoLightValue(
                            iBlockAccess.getBlockState(blockPosValue7).getBlock().getAmbientOcclusionLightValue());
            k1 = block.getMixedBrightnessForBlock(iBlockAccess, blockPosValue7);
        }

        float f6;
        int l1;
        label59:
        {
            if (!flag2 && !flag1) {
                f6 = f1;
                l1 = k;
                if (i == 0) {
                    break label59;
                }
            }

            BlockPos blockPosValue8 = blockPosValue3.offset(blockModelRendererEnumNeighborInfo.aPp[2]);
            f6 =
                    BlockModelRenderer.fixAoLightValue(
                            iBlockAccess.getBlockState(blockPosValue8).getBlock().getAmbientOcclusionLightValue());
            l1 = block.getMixedBrightnessForBlock(iBlockAccess, blockPosValue8);
        }

        float f7;
        int i2;
        label53:
        {
            if (!flag3 && !flag1) {
                f7 = f1;
                i2 = k;
                if (i == 0) {
                    break label53;
                }
            }

            BlockPos blockPosValue9 = blockPosValue3.offset(blockModelRendererEnumNeighborInfo.aPp[3]);
            f7 =
                    BlockModelRenderer.fixAoLightValue(
                            iBlockAccess.getBlockState(blockPosValue9).getBlock().getAmbientOcclusionLightValue());
            i2 = block.getMixedBrightnessForBlock(iBlockAccess, blockPosValue9);
        }

        int j2 = block.getMixedBrightnessForBlock(iBlockAccess, blockPos);
        if (bitset.get(0)
                || !iBlockAccess.getBlockState(blockPos.offset(facing)).getBlock().isOpaqueCube()) {
            j2 = block.getMixedBrightnessForBlock(iBlockAccess, blockPos.offset(facing));
        }

        float f8 =
                bitset.get(0)
                        ? iBlockAccess.getBlockState(blockPosValue).getBlock().getAmbientOcclusionLightValue()
                        : iBlockAccess.getBlockState(blockPos).getBlock().getAmbientOcclusionLightValue();
        f8 = BlockModelRenderer.fixAoLightValue(f8);
        BlockModelRendererVertexTranslations vertexTranslations =
                BlockModelRendererVertexTranslations.getVertexTranslations(facing);
        if (bitset.get(1) && blockModelRendererEnumNeighborInfo.e) {
            float f9 = (f3 + f + f5 + f8) * 0.25F;
            float f10 = (f2 + f + f4 + f8) * 0.25F;
            float f11 = (f2 + f1 + f6 + f8) * 0.25F;
            float f12 = (f3 + f1 + f7 + f8) * 0.25F;
            float f13 =
                    afloat[blockModelRendererEnumNeighborInfo.aPo[0].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPo[1].cz];
            float f14 =
                    afloat[blockModelRendererEnumNeighborInfo.aPo[2].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPo[3].cz];
            float f15 =
                    afloat[blockModelRendererEnumNeighborInfo.aPo[4].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPo[5].cz];
            float f16 =
                    afloat[blockModelRendererEnumNeighborInfo.aPo[6].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPo[7].cz];
            float f17 =
                    afloat[blockModelRendererEnumNeighborInfo.aPn[0].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPn[1].cz];
            float f18 =
                    afloat[blockModelRendererEnumNeighborInfo.aPn[2].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPn[3].cz];
            float f19 =
                    afloat[blockModelRendererEnumNeighborInfo.aPn[4].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPn[5].cz];
            float f20 =
                    afloat[blockModelRendererEnumNeighborInfo.aPn[6].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPn[7].cz];
            float f21 =
                    afloat[blockModelRendererEnumNeighborInfo.aPk[0].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPk[1].cz];
            float f22 =
                    afloat[blockModelRendererEnumNeighborInfo.aPk[2].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPk[3].cz];
            float f23 =
                    afloat[blockModelRendererEnumNeighborInfo.aPk[4].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPk[5].cz];
            float f24 =
                    afloat[blockModelRendererEnumNeighborInfo.aPk[6].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPk[7].cz];
            float f25 =
                    afloat[blockModelRendererEnumNeighborInfo.aPg[0].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPg[1].cz];
            float f26 =
                    afloat[blockModelRendererEnumNeighborInfo.aPg[2].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPg[3].cz];
            float f27 =
                    afloat[blockModelRendererEnumNeighborInfo.aPg[4].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPg[5].cz];
            float f28 =
                    afloat[blockModelRendererEnumNeighborInfo.aPg[6].cz]
                            * afloat[blockModelRendererEnumNeighborInfo.aPg[7].cz];
            this.vertexColorMultiplier[
                            BlockModelRendererVertexTranslations.method_5e44e384bdf398a9ff0b(
                                    vertexTranslations)] =
                    f9 * f13 + f10 * f14 + f11 * f15 + f12 * f16;
            this.vertexColorMultiplier[
                            BlockModelRendererVertexTranslations.method_21001c61e52a69c76f37(
                                    vertexTranslations)] =
                    f9 * f17 + f10 * f18 + f11 * f19 + f12 * f20;
            this.vertexColorMultiplier[
                            BlockModelRendererVertexTranslations.method_8d996f8725e0b61d3bae(
                                    vertexTranslations)] =
                    f9 * f21 + f10 * f22 + f11 * f23 + f12 * f24;
            this.vertexColorMultiplier[
                            BlockModelRendererVertexTranslations.method_942b59fb10bacb28166e(
                                    vertexTranslations)] =
                    f9 * f25 + f10 * f26 + f11 * f27 + f12 * f28;
            int k2 = this.getAoBrightness(i1, j, k1, j2);
            int l2 = this.getAoBrightness(l, j, j1, j2);
            int i3 = this.getAoBrightness(l, k, l1, j2);
            int j3 = this.getAoBrightness(i1, k, i2, j2);
            this.vertexBrightness[
                            BlockModelRendererVertexTranslations.method_5e44e384bdf398a9ff0b(
                                    vertexTranslations)] =
                    this.getVertexBrightness(k2, l2, i3, j3, f13, f14, f15, f16);
            this.vertexBrightness[
                            BlockModelRendererVertexTranslations.method_21001c61e52a69c76f37(
                                    vertexTranslations)] =
                    this.getVertexBrightness(k2, l2, i3, j3, f17, f18, f19, f20);
            this.vertexBrightness[
                            BlockModelRendererVertexTranslations.method_8d996f8725e0b61d3bae(
                                    vertexTranslations)] =
                    this.getVertexBrightness(k2, l2, i3, j3, f21, f22, f23, f24);
            this.vertexBrightness[
                            BlockModelRendererVertexTranslations.method_942b59fb10bacb28166e(
                                    vertexTranslations)] =
                    this.getVertexBrightness(k2, l2, i3, j3, f25, f26, f27, f28);
            if (i == 0) {
                return;
            }
        }

        float f29 = (f3 + f + f5 + f8) * 0.25F;
        float f30 = (f2 + f + f4 + f8) * 0.25F;
        float f31 = (f2 + f1 + f6 + f8) * 0.25F;
        float f32 = (f3 + f1 + f7 + f8) * 0.25F;
        this.vertexBrightness[
                        BlockModelRendererVertexTranslations.method_5e44e384bdf398a9ff0b(
                                vertexTranslations)] =
                this.getAoBrightness(i1, j, k1, j2);
        this.vertexBrightness[
                        BlockModelRendererVertexTranslations.method_21001c61e52a69c76f37(
                                vertexTranslations)] =
                this.getAoBrightness(l, j, j1, j2);
        this.vertexBrightness[
                        BlockModelRendererVertexTranslations.method_8d996f8725e0b61d3bae(
                                vertexTranslations)] =
                this.getAoBrightness(l, k, l1, j2);
        this.vertexBrightness[
                        BlockModelRendererVertexTranslations.method_942b59fb10bacb28166e(
                                vertexTranslations)] =
                this.getAoBrightness(i1, k, i2, j2);
        this.vertexColorMultiplier[
                        BlockModelRendererVertexTranslations.method_5e44e384bdf398a9ff0b(
                                vertexTranslations)] =
                f29;
        this.vertexColorMultiplier[
                        BlockModelRendererVertexTranslations.method_21001c61e52a69c76f37(
                                vertexTranslations)] =
                f30;
        this.vertexColorMultiplier[
                        BlockModelRendererVertexTranslations.method_8d996f8725e0b61d3bae(
                                vertexTranslations)] =
                f31;
        this.vertexColorMultiplier[
                        BlockModelRendererVertexTranslations.method_942b59fb10bacb28166e(
                                vertexTranslations)] =
                f32;
    }

    public BlockModelRendererAmbientOcclusionFace() {
        this.vertexBrightness = new int[4];
    }
}
