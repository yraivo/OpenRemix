package net.minecraft.client.renderer.block.model;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Facing;
import optifine.OptiFineConfig;
import optifine.Reflector;
import remix.compat.render.IVertexConsumer;
import remix.compat.render.IVertexProducer;

public class BakedQuad implements IVertexProducer {

    private TextureAtlasSprite sprite = null;
    protected int tintIndex;
    private int[] vertexDataSingle = null;

    protected int[] vertexData;
    protected Facing face;

    private static int f;

    // region String decoding support

    // endregion

    public BakedQuad(int[] aint, int tintIndexValue, Facing aAAValue, TextureAtlasSprite aAyValue) {
        this.vertexData = aint;
        this.tintIndex = tintIndexValue;
        this.face = aAAValue;
        this.sprite = aAyValue;
        this.dn();
    }

    private static TextureAtlasSprite getSpriteByUv(int[] aint) {
        float fx = 1.0F;
        int i1 = cM();
        float f1 = 1.0F;
        float f2 = 0.0F;
        int i = i1;
        float f3 = 0.0F;
        int j = aint.length / 4;
        int k = 0;

        while (k < 4) {
            int l = k * j;
            float f4 = Float.intBitsToFloat(aint[l + 4]);
            float f5 = Float.intBitsToFloat(aint[l + 4 + 1]);
            fx = Math.min(fx, f4);
            f1 = Math.min(f1, f5);
            f2 = Math.max(f2, f4);
            f3 = Math.max(f3, f5);
            k++;
            if (i == 0) {
                break;
            }
        }

        float f6 = (fx + f2) / 2.0F;
        float f7 = (f1 + f3) / 2.0F;
        return MinecraftClient.getMinecraft().getTextureMapBlocks().getIconByUV(f6, f7);
    }

    public int getTintIndex() {
        return this.tintIndex;
    }

    public Facing getFace() {
        return this.face;
    }

    @Override
    public String toString() {
        return "vertex: "
                + this.vertexData.length / 7
                + ", tint: "
                + this.tintIndex
                + ", facing: "
                + this.face
                + ", sprite: "
                + this.sprite;
    }

    public BakedQuad(int[] aint, int tintIndexValue, Facing aAAValue) {
        this.vertexData = aint;
        this.tintIndex = tintIndexValue;
        this.face = aAAValue;
        this.dn();
    }

    private void dn() {
        int i = cM();
        if (OptiFineConfig.isShaders()) {
            if (this.vertexData.length != 28) {
                return;
            }

            this.vertexData = method_5b6dc7dd02bd78cbfbf1(this.vertexData);
            if (i != 0) {
                return;
            }
        }

        if (this.vertexData.length == 56) {
            this.vertexData = method_68fef941ef45ae62c64d(this.vertexData);
        }
    }

    public int[] getVertexDataSingle() {
        if (this.vertexDataSingle == null) {
            this.vertexDataSingle = makeVertexDataSingle(this.getVertexData(), this.getSprite());
        }

        return this.vertexDataSingle;
    }

    public boolean hasTintIndex() {
        return this.tintIndex != -1;
    }

    public static int eR() {
        return f;
    }

    private static int[] method_5b6dc7dd02bd78cbfbf1(int[] aint) {
        int j = aint.length / 4;
        int k = j * 2;
        int[] aint1 = new int[k * 4];
        int i1 = eR();
        int l = 0;
        int i = i1;

        while (l < 4) {
            System.arraycopy(aint, l * j, aint1, l * k, j);
            l++;
            if (i != 0) {
                break;
            }
        }

        return aint1;
    }

    private static int[] method_68fef941ef45ae62c64d(int[] aint) {
        int i1 = eR();
        int j = aint.length / 4;
        int k = j / 2;
        int[] aint1 = new int[k * 4];
        int l = 0;
        int i = i1;

        while (l < 4) {
            System.arraycopy(aint, l * j, aint1, l * k, k);
            l++;
            if (i != 0) {
                break;
            }
        }

        return aint1;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        b(0);
    }

    public TextureAtlasSprite getSprite() {
        if (this.sprite == null) {
            this.sprite = getSpriteByUv(this.getVertexData());
        }

        return this.sprite;
    }

    public void pipe(IVertexConsumer iVertexConsumer) {
        Reflector.callVoid(Reflector.Gf, new Object[] {iVertexConsumer, this});
    }

    private static int[] makeVertexDataSingle(int[] aint, TextureAtlasSprite textureAtlasSprite) {
        int[] aint1 = (int[]) aint.clone();
        int k1 = cM();
        int j = textureAtlasSprite.sheetWidth / textureAtlasSprite.getIconWidth();
        int k = textureAtlasSprite.sheetHeight / textureAtlasSprite.getIconHeight();
        int l = aint1.length / 4;
        int i = k1;
        int i1 = 0;

        while (i1 < 4) {
            int j1 = i1 * l;
            float fx = Float.intBitsToFloat(aint1[j1 + 4]);
            float f1 = Float.intBitsToFloat(aint1[j1 + 4 + 1]);
            float f2 = textureAtlasSprite.toSingleU(fx);
            float f3 = textureAtlasSprite.toSingleV(f1);
            aint1[j1 + 4] = Float.floatToRawIntBits(f2);
            aint1[j1 + 4 + 1] = Float.floatToRawIntBits(f3);
            i1++;
            if (i == 0) {
                break;
            }
        }

        return aint1;
    }

    public static int cM() {
        int i = eR();
        return i == 0 ? 44 : 0;
    }

    public int[] getVertexData() {
        this.dn();
        return this.vertexData;
    }

    public static void b(int i) {
        f = i;
    }
}
