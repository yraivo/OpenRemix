package optifine;

import net.minecraft.client.renderer.GLAllocation;

import org.lwjgl.opengl.GL11;

import java.awt.Dimension;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

public class Mipmaps {
    private int[][] mipmapDatas;
    private int[] data;
    private int width;
    private boolean direct;
    private IntBuffer[] mipmapBuffers;
    private String iconName;

    private int height;
    private Dimension[] mipmapDimensions;

    private static String[] packedTextFragments;

    private static int alphaBlend(int i, int j) {
        int l;
        int i1;
        int j1;
        {
            int l3 = ReflectorMethod.cR();
            l = (i & 0xFF000000) >> 24 & 0xFF;
            i1 = (j & 0xFF000000) >> 24 & 0xFF;
            int k = l3;
            j1 = (l + i1) / 2;
            if (l == 0 && i1 == 0) {
                l = 1;
                i1 = 1;
                if (!(k != 0)) {

                    if (l == 0) {
                        i = j;
                        j1 /= 2;
                    }

                    if (i1 == 0) {
                        j = i;
                        j1 /= 2;
                    }
                }
            } else {

                if (l == 0) {
                    i = j;
                    j1 /= 2;
                }

                if (i1 == 0) {
                    j = i;
                    j1 /= 2;
                }
            }
        }

        int k1 = (i >> 16 & 0xFF) * l;
        int l1 = (i >> 8 & 0xFF) * l;
        int i2 = (i & 0xFF) * l;
        int j2 = (j >> 16 & 0xFF) * i1;
        int k2 = (j >> 8 & 0xFF) * i1;
        int l2 = (j & 0xFF) * i1;
        int i3 = (k1 + j2) / (l + i1);
        int j3 = (l1 + k2) / (l + i1);
        int k3 = (i2 + l2) / (l + i1);
        return j1 << 24 | i3 << 16 | j3 << 8 | k3;
    }

    public static Dimension[] makeMipmapDimensions(int i, int j, String text) {
        int l = TextureUtils.method_c640c0ab3979b883b99e(i);
        int i2 = ReflectorMethod.f();
        int i1 = TextureUtils.method_c640c0ab3979b883b99e(j);
        int k = i2;
        if (l == i && i1 == j) {
            ArrayList arrayList = new ArrayList();
            int j1 = l;
            int k1 = i1;

            do {
                j1 /= 2;
                k1 /= 2;
                if (j1 <= 0 && k1 <= 0) {
                    return ((java.awt.Dimension[])
                            arrayList.toArray(new Dimension[arrayList.size()]));
                }

                if (j1 <= 0) {
                    j1 = 1;
                }

                if (k1 <= 0) {
                    k1 = 1;
                }

                int l1 = j1 * k1 * 4;
                Dimension dimension = new Dimension(j1, k1);
                arrayList.add(dimension);
            } while (k == 0);
        }

        OptiFineConfig.warn(
                "Mipmaps not possible (power of 2 dimensions needed), texture: "
                        + text
                        + ", dim: "
                        + i
                        + packedTextFragments[0]
                        + j);
        return new Dimension[0];
    }

    private int method_e0b8a2f32478f200146b(int p171_0, int p171_1) {
        int k = (p171_0 & 0xFF000000) >> 24 & 0xFF;
        int l = (p171_1 & 0xFF000000) >> 24 & 0xFF;
        return (k + l >> 1 << 24) + ((p171_0 & 16711422) + (p171_1 & 16711422) >> 1);
    }

    public static IntBuffer[] makeMipmapBuffers(Dimension[] adimension, int[][] aint) {
        int i = ReflectorMethod.f();
        if (adimension == null) {
            return null;
        }

        IntBuffer[] aintbuffer = new IntBuffer[adimension.length];
        int j = 0;

        while (j < adimension.length) {
            Dimension dimension = adimension[j];
            int k = dimension.width * dimension.height;
            IntBuffer intbuffer = GLAllocation.createDirectIntBuffer(k);
            int[] aint1 = aint[j];
            ((Buffer) intbuffer).clear();
            intbuffer.put(aint1);
            ((Buffer) intbuffer).clear();
            aintbuffer[j] = intbuffer;
            j++;
            if (i != 0) {
                break;
            }
        }

        return aintbuffer;
    }

    static {
        optifine.Mipmaps.packedTextFragments = new String[2];
        optifine.Mipmaps.packedTextFragments[0] = new String("x");
    }

    public static int alphaBlend(int p171_0, int p171_1, int p171_2, int p171_3) {
        int i1 = optifine.Mipmaps.alphaBlend(p171_0, p171_1);
        int j1 = optifine.Mipmaps.alphaBlend(p171_2, p171_3);
        return optifine.Mipmaps.alphaBlend(i1, j1);
    }

    public static void allocateMipmapTextures(int i, int j, String text) {
        Dimension[] adimension = makeMipmapDimensions(i, j, text);
        int l1 = ReflectorMethod.f();
        int l = 0;
        int k = l1;

        while (l < adimension.length) {
            Dimension dimension = adimension[l];
            int width = dimension.width;
            int j1 = dimension.height;
            int k1 = l + 1;
            GL11.glTexImage2D(3553, k1, 6408, width, j1, 0, 32993, 33639, (IntBuffer) null);
            l++;
            if (k != 0) {
                break;
            }
        }
    }

    public Mipmaps(
            String text, int widthValue, int heightValue, int[] dataValue, boolean directValue) {
        this.iconName = text;
        this.width = widthValue;
        this.height = heightValue;
        this.data = dataValue;
        this.direct = directValue;
        this.mipmapDimensions = optifine.Mipmaps.makeMipmapDimensions(widthValue, heightValue, text);
        this.mipmapDatas =
                optifine.Mipmaps.generateMipMapData(
                        dataValue, widthValue, heightValue, this.mipmapDimensions);
        if (directValue) {
            this.mipmapBuffers =
                    optifine.Mipmaps.makeMipmapBuffers(this.mipmapDimensions, this.mipmapDatas);
        }
    }

    public static int[][] generateMipMapData(
            int[] p171_0, int p171_1, int p171_2, Dimension[] dimensionArray) {
        int l3 = optifine.ReflectorMethod.cR();
        int[] aint1 = p171_0;
        int k = l3;
        int l = p171_1;
        boolean flag = true;
        int[][] aint2 = new int[dimensionArray.length][];
        int i1 = 0;

        while (i1 < dimensionArray.length) {
            java.awt.Dimension dimension = dimensionArray[i1];
            int j1 = dimension.width;
            int k1 = dimension.height;
            int[] aint3 = new int[j1 * k1];
            aint2[i1] = aint3;
            int l1 = i1 + 1;
            if ((flag)) {
                int i2 = 0;

                while (i2 < j1) {
                    int j2 = 0;

                    while (j2 < k1) {
                        int k2 = aint1[i2 * 2 + 0 + (j2 * 2 + 0) * l];
                        int l2 = aint1[i2 * 2 + 1 + (j2 * 2 + 0) * l];
                        int i3 = aint1[i2 * 2 + 1 + (j2 * 2 + 1) * l];
                        int j3 = aint1[i2 * 2 + 0 + (j2 * 2 + 1) * l];
                        int k3 = optifine.Mipmaps.alphaBlend(k2, l2, i3, j3);
                        aint3[i2 + j2 * j1] = k3;
                        j2++;
                        if (k == 0) {
                            break;
                        }
                    }

                    i2++;
                    if (k == 0) {
                        break;
                    }
                }
            }

            aint1 = aint3;
            l = j1;
            if (j1 <= 1 || k1 <= 1) {
                flag = false;
            }

            i1++;
            if (k == 0) {
                break;
            }
        }

        return aint2;
    }
}
