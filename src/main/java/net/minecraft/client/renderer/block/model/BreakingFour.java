package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.util.Arrays;

public class BreakingFour extends BakedQuad {
    private TextureAtlasSprite texture;

    private void method_8d9b536bcdc9991c48fb() {
        int k = BakedQuad.cM();
        int j = 0;
        int i = k;

        while (j < 4) {
            this.a(j);
            j++;
            if (i == 0) {
                break;
            }
        }
    }

    public BreakingFour(BakedQuad bakedQuad, TextureAtlasSprite aACValue) {
        super(
                Arrays.copyOf(bakedQuad.getVertexData(), bakedQuad.getVertexData().length),
                bakedQuad.tintIndex,
                FaceBakery.getFacingFromVertexData(bakedQuad.getVertexData()));
        this.texture = aACValue;
        this.method_8d9b536bcdc9991c48fb();
    }

    private void a(int i) {
        int k = this.vertexData.length / 4;
        int l = k * i;
        float f = Float.intBitsToFloat(this.vertexData[l]);
        float f1 = Float.intBitsToFloat(this.vertexData[l + 1]);
        float f2 = Float.intBitsToFloat(this.vertexData[l + 2]);
        int i1 = BakedQuad.cM();
        float f3 = 0.0F;
        int j = i1;
        float f4 = 0.0F;
        switch (this.face) {
            case DOWN:
                f3 = f * 16.0F;
                f4 = (1.0F - f2) * 16.0F;
                if (j != 0) {
                    break;
                }
            case UP:
                f3 = f * 16.0F;
                f4 = f2 * 16.0F;
                if (j != 0) {
                    break;
                }
            case NORTH:
                f3 = (1.0F - f) * 16.0F;
                f4 = (1.0F - f1) * 16.0F;
                if (j != 0) {
                    break;
                }
            case SOUTH:
                f3 = f * 16.0F;
                f4 = (1.0F - f1) * 16.0F;
                if (j != 0) {
                    break;
                }
            case WEST:
                f3 = f2 * 16.0F;
                f4 = (1.0F - f1) * 16.0F;
                if (j != 0) {
                    break;
                }
            case EAST:
                f3 = (1.0F - f2) * 16.0F;
                f4 = (1.0F - f1) * 16.0F;
        }

        this.vertexData[l + 4] = Float.floatToRawIntBits(this.texture.getInterpolatedU(f3));
        this.vertexData[l + 4 + 1] = Float.floatToRawIntBits(this.texture.getInterpolatedV(f4));
    }
}
