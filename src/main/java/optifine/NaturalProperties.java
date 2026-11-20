package optifine;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;

import java.util.IdentityHashMap;
import java.util.Map;

public class NaturalProperties {
    public int rotation;

    private Map[] quadMaps;
    private static String[] packedTextFragments;
    public boolean flip;

    public boolean isValid() {
        return this.rotation != 2 && this.rotation != 4 ? this.flip : true;
    }

    public NaturalProperties(String text) {
        int j = ReflectorMethod.f();
        this.rotation = 1;
        int i = j;
        this.flip = false;
        this.quadMaps = new Map[8];
        if (text.equals(packedTextFragments[0])) {
            this.rotation = 4;
            if (i == 0) {
                return;
            }
        }

        if (text.equals(packedTextFragments[1])) {
            this.rotation = 2;
            if (i == 0) {
                return;
            }
        }

        if (text.equals(packedTextFragments[2])) {
            this.flip = true;
            if (i == 0) {
                return;
            }
        }

        if (text.equals("4F")) {
            this.rotation = 4;
            this.flip = true;
            if (i == 0) {
                return;
            }
        }

        if (text.equals("2F")) {
            this.rotation = 2;
            this.flip = true;
            if (i == 0) {
                return;
            }
        }

        OptiFineConfig.warn("NaturalTextures: Unknown type: " + text);
    }

    public synchronized BakedQuad getQuad(BakedQuad bakedQuad, int i, boolean flag) {
        int j = i;
        if (flag) {
            j = i | 4;
        }

        if (j > 0 && j < this.quadMaps.length) {
            Map map = this.quadMaps[j];
            if (map == null) {
                map = new IdentityHashMap(1);
                this.quadMaps[j] = map;
            }

            BakedQuad bakedQuadValue = (BakedQuad) map.get(bakedQuad);
            if (bakedQuadValue == null) {
                bakedQuadValue = this.makeQuad(bakedQuad, i, flag);
                map.put(bakedQuad, bakedQuadValue);
            }

            return bakedQuadValue;
        } else {
            return bakedQuad;
        }
    }

    private int[] transformVertexData(int[] aint, int i, boolean flag) {
        int l1 = ReflectorMethod.cR();
        int[] aint1 = (int[]) aint.clone();
        int j = l1;
        int k = 4 - i;
        if (flag) {
            k += 3;
        }

        k %= 4;
        int l = aint1.length / 4;
        int i1 = 0;

        while (i1 < 4) {
            label26:
            {
                int j1 = i1 * l;
                int k1 = k * l;
                aint1[k1 + 4] = aint[j1 + 4];
                aint1[k1 + 4 + 1] = aint[j1 + 4 + 1];
                if (flag) {
                    if (--k >= 0) {
                        break label26;
                    }

                    k = 3;
                    if (j != 0) {
                        break label26;
                    }
                }

                if (++k > 3) {
                    k = 0;
                }
            }

            i1++;
            if (j == 0) {
                break;
            }
        }

        return aint1;
    }

    private boolean isFullSprite(BakedQuad bakedQuad) {
        int i1 = ReflectorMethod.cR();
        TextureAtlasSprite textureAtlasSprite = bakedQuad.getSprite();
        float minU = textureAtlasSprite.getMinU();
        float maxU = textureAtlasSprite.getMaxU();
        int i = i1;
        float f2 = maxU - minU;
        float f3 = f2 / 256.0F;
        float minV = textureAtlasSprite.getMinV();
        float maxV = textureAtlasSprite.getMaxV();
        float f6 = maxV - minV;
        float f7 = f6 / 256.0F;
        int[] aint = bakedQuad.getVertexData();
        int j = aint.length / 4;
        int k = 0;

        while (k < 4) {
            int l = k * j;
            float f8 = Float.intBitsToFloat(aint[l + 4]);
            float f9 = Float.intBitsToFloat(aint[l + 4 + 1]);
            if (!this.equalsDelta(f8, minU, f3) && !this.equalsDelta(f8, maxU, f3)) {
                return false;
            }

            if (!this.equalsDelta(f9, minV, f7) && !this.equalsDelta(f9, maxV, f7)) {
                return false;
            }

            k++;
            if (i == 0) {
                break;
            }
        }

        return true;
    }

    static {
        optifine.NaturalProperties.packedTextFragments = new String[4];
        optifine.NaturalProperties.packedTextFragments[0] = new String("4");
        optifine.NaturalProperties.packedTextFragments[1] = new String("2");
        optifine.NaturalProperties.packedTextFragments[2] = new String("F");
    }

    private BakedQuad makeQuad(BakedQuad bakedQuad, int i, boolean flag) {
        int[] aint = bakedQuad.getVertexData();
        int j = bakedQuad.getTintIndex();
        Facing facing = bakedQuad.getFace();
        TextureAtlasSprite textureAtlasSprite = bakedQuad.getSprite();
        if (!this.isFullSprite(bakedQuad)) {
            return bakedQuad;
        }

        aint = this.transformVertexData(aint, i, flag);
        return new BakedQuad(aint, j, facing, textureAtlasSprite);
    }

    private boolean equalsDelta(float f, float f1, float f2) {
        float f3 = MathHelper.abs(f - f1);
        return f3 < f2;
    }
}
