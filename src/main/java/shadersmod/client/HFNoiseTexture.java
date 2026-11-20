package shadersmod.client;

import org.lwjgl.opengl.GL11;

public class HFNoiseTexture {
    public int textureUnit;
    public int texID = GL11.glGenTextures();

    private byte[] genHFNoiseImage(int i, int j) {
        int[] aint1 = ShaderOption.lz();
        byte[] abyte = new byte[i * j * 3];
        int k = 0;
        int[] aint = aint1;
        int l = 0;

        while (l < j) {
            int i1 = 0;

            while (true) {
                if (i1 < i) {
                    int j1 = 1;

                    while (j1 < 4) {
                        abyte[k++] = this.random(i1, l, j1);
                        j1++;
                        if (aint != null) {
                            break;
                        }
                    }

                    i1++;
                    if (aint == null) {
                        continue;
                    }
                }

                l++;
                if (aint != null) {
                    return abyte;
                }
                break;
            }
        }

        return abyte;
    }

    public HFNoiseTexture(int p171_0, int p171_1) {
        this.textureUnit = 15;
        byte[] abyte = this.genHFNoiseImage(p171_0, p171_1);
        java.nio.ByteBuffer bytebuffer = org.lwjgl.BufferUtils.createByteBuffer(abyte.length);
        bytebuffer.put(abyte);
        ((java.nio.Buffer) bytebuffer).flip();
        net.minecraft.client.renderer.GlStateManager.bindTexture(this.texID);
        org.lwjgl.opengl.GL11.glTexImage2D(
                3553, 0, 6407, p171_0, p171_1, 0, 6407, 5121, bytebuffer);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10242, 10497);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10243, 10497);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10240, 9729);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10241, 9729);
        net.minecraft.client.renderer.GlStateManager.bindTexture(0);
    }

    private byte random(int p171_0, int p171_1, int p171_2) {
        int l =
                (this.method_c640c0ab3979b883b99e(p171_0)
                                        + this.method_c640c0ab3979b883b99e(p171_1 * 19))
                                * this.method_c640c0ab3979b883b99e(p171_2 * 23)
                        - p171_2;
        return (byte) (this.method_c640c0ab3979b883b99e(l) % 128);
    }

    public void deleteTexture() {
        net.minecraft.client.renderer.GlStateManager.deleteTexture(this.texID);
        this.texID = 0;
    }

    private int method_c640c0ab3979b883b99e(int p171_0) {
        p171_0 ^= p171_0 << 13;
        p171_0 ^= p171_0 >> 17;
        return p171_0 ^ p171_0 << 5;
    }

    public int getTexID() {
        return this.texID;
    }
}
