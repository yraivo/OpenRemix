package shadersmod.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import shadersmod.common.ShaderLog;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class ShadersTex {
    public static IntBuffer intBuffer;
    public static Map<Integer, MultiTexID> multiTexMap;
    private static String[] packedTextFragments;
    public static IResourceManager resManager;
    public static String iconName;
    public static int updatingPage;

    public static TextureMap updatingTextureMap;
    public static int[] intArray;
    public static final int ef = 0;
    static ResourceLocation resLocation;
    public static final int a = 1048576;
    public static TextureAtlasSprite updatingSprite;
    public static ByteBuffer byteBuffer;

    public static final int f = -8421377;
    public static MultiTexID updatingTex;
    static int imageSize;
    public static final int Ao = 0;
    public static MultiTexID boundTex;

    public static void bindNSTextures(int p171_0, int p171_1) {
        if (shadersmod.client.Shaders.isRenderingWorld
                && net.minecraft.client.renderer.GlStateManager.getActiveTextureUnit() == 33984) {
            net.minecraft.client.renderer.GlStateManager.setActiveTexture(33986);
            net.minecraft.client.renderer.GlStateManager.bindTexture(p171_0);
            net.minecraft.client.renderer.GlStateManager.setActiveTexture(33987);
            net.minecraft.client.renderer.GlStateManager.bindTexture(p171_1);
            net.minecraft.client.renderer.GlStateManager.setActiveTexture(33984);
        }
    }

    public static void deleteTextures(AbstractTexture abstractTexture, int i) {
        MultiTexID aGt = abstractTexture.multiTex;
        if (aGt != null) {
            abstractTexture.multiTex = null;
            shadersmod.client.ShadersTex.multiTexMap.remove(aGt.base);
            GlStateManager.deleteTexture(aGt.norm);
            GlStateManager.deleteTexture(aGt.spec);
            if (aGt.base != i) {
                ShaderLog.warning(
                        "Error : MultiTexID.base mismatch: " + aGt.base + ", texid: " + i);
                GlStateManager.deleteTexture(aGt.base);
            }
        }
    }

    public static int[] loadAtlasSprite(
            BufferedImage bufferedimage, int i, int j, int k, int l, int[] aint, int i1, int j1) {
        shadersmod.client.ShadersTex.imageSize = k * l;
        bufferedimage.getRGB(i, j, k, l, aint, i1, j1);
        loadNSMap(
                shadersmod.client.ShadersTex.resManager,
                shadersmod.client.ShadersTex.resLocation,
                k,
                l,
                aint);
        return aint;
    }

    public static int roundUpPOT(int p171_0) {
        int j = p171_0 - 1;
        j |= j >> 1;
        j |= j >> 2;
        j |= j >> 4;
        j |= j >> 8;
        j |= j >> 16;
        return j + 1;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public static int[] createAIntImage(int p171_0, int p171_1) {
        int[] aint = new int[p171_0 * 3];
        java.util.Arrays.fill(aint, 0, p171_0, p171_1);
        java.util.Arrays.fill(aint, p171_0, p171_0 * 2, -8421377);
        java.util.Arrays.fill(aint, p171_0 * 2, p171_0 * 3, 0);
        return aint;
    }

    public static IntBuffer fillIntBuffer(int p171_0, int p171_1) {
        int[] aint = shadersmod.client.ShadersTex.getIntArray(p171_0);
        java.nio.IntBuffer intbuffer = shadersmod.client.ShadersTex.getIntBuffer(p171_0);
        java.util.Arrays.fill(shadersmod.client.ShadersTex.intArray, 0, p171_0, p171_1);
        shadersmod.client.ShadersTex.intBuffer.put(
                shadersmod.client.ShadersTex.intArray, 0, p171_0);
        return shadersmod.client.ShadersTex.intBuffer;
    }

    static {
        shadersmod.client.ShadersTex.packedTextFragments = new String[8];
        shadersmod.client.ShadersTex.packedTextFragments[0] = new String(" ");
        shadersmod.client.ShadersTex.packedTextFragments[1] = new String(" ");
        shadersmod.client.ShadersTex.packedTextFragments[2] = new String(" ");
        shadersmod.client.ShadersTex.packedTextFragments[3] = new String("_");
        shadersmod.client.ShadersTex.packedTextFragments[4] = new String("n");
        shadersmod.client.ShadersTex.packedTextFragments[5] = new String("s");
        shadersmod.client.ShadersTex.byteBuffer = BufferUtils.createByteBuffer(4194304);
        shadersmod.client.ShadersTex.intBuffer =
                shadersmod.client.ShadersTex.byteBuffer.asIntBuffer();
        shadersmod.client.ShadersTex.intArray = new int[1048576];
        shadersmod.client.ShadersTex.multiTexMap = new HashMap<>();
        shadersmod.client.ShadersTex.updatingTextureMap = null;
        shadersmod.client.ShadersTex.updatingSprite = null;
        shadersmod.client.ShadersTex.updatingTex = null;
        shadersmod.client.ShadersTex.boundTex = null;
        shadersmod.client.ShadersTex.updatingPage = 0;
        shadersmod.client.ShadersTex.iconName = null;
        shadersmod.client.ShadersTex.resManager = null;
        shadersmod.client.ShadersTex.resLocation = null;
        shadersmod.client.ShadersTex.imageSize = 0;
    }

    // region String decoding support

    // endregion

    public static int[][] readImageAndMipmaps(
            String text, int i, int j, int k, boolean flag, int l) {
        int[][] aint = new int[k][];
        int[] aint1;
        aint[0] = aint1 = new int[i * j];
        byte b0 = 0;
        BufferedImage bufferedimage =
                readImage(
                        shadersmod.client.ShadersTex.updatingTextureMap.completeResourceLocation(
                                new ResourceLocation(text), 0));
        if (bufferedimage != null) {
            int width = bufferedimage.getWidth();
            int j1 = bufferedimage.getHeight();
            if (width + (flag ? 16 : 0) == i) {
                b0 = 1;
                bufferedimage.getRGB(0, 0, width, width, aint1, 0, width);
            }
        }

        if (b0 == 0) {
            Arrays.fill(aint1, l);
        }

        GlStateManager.bindTexture(shadersmod.client.ShadersTex.updatingTex.spec);
        return genMipmapsSimple(aint.length - 1, i, aint);
    }

    public static void bindTextures(int p171_0, int p171_1, int p171_2) {
        if (shadersmod.client.Shaders.isRenderingWorld
                && net.minecraft.client.renderer.GlStateManager.getActiveTextureUnit() == 33984) {
            net.minecraft.client.renderer.GlStateManager.setActiveTexture(33986);
            net.minecraft.client.renderer.GlStateManager.bindTexture(p171_1);
            net.minecraft.client.renderer.GlStateManager.setActiveTexture(33987);
            net.minecraft.client.renderer.GlStateManager.bindTexture(p171_2);
            net.minecraft.client.renderer.GlStateManager.setActiveTexture(33984);
        }

        net.minecraft.client.renderer.GlStateManager.bindTexture(p171_0);
    }

    public static void bindNSTextures(MultiTexID multiTexID) {
        shadersmod.client.ShadersTex.bindNSTextures(multiTexID.norm, multiTexID.spec);
    }

    public static void bindTexture(ITextureObject iTextureObject) {
        int[] aint1 = ShaderOption.lz();
        int glTextureId = iTextureObject.getGlTextureId();
        int[] aint = aint1;
        if (iTextureObject instanceof TextureMap) {
            Shaders.atlasSizeX = ((TextureMap) iTextureObject).atlasWidth;
            Shaders.atlasSizeY = ((TextureMap) iTextureObject).atlasHeight;
            bindTextures(iTextureObject.getMultiTexID());
            if (aint == null) {
                return;
            }
        }

        Shaders.atlasSizeX = 0;
        Shaders.atlasSizeY = 0;
        bindTextures(iTextureObject.getMultiTexID());
    }

    public static void uploadTexSub1(int[][] aint, int i, int j, int k, int l, int i1) {
        int j1 = i * j;
        IntBuffer intbuffer = getIntBuffer(j1);
        int k1 = aint.length;
        int l1 = 0;
        int[] aint3 = ShaderOption.lz();
        int i2 = i;
        int[] aint1 = aint3;
        int j2 = j;
        int k2 = k;
        int l2 = l;

        while (i2 > 0 && j2 > 0 && l1 < k1) {
            int i3 = i2 * j2;
            int[] aint2 = aint[l1];
            ((Buffer) intbuffer).clear();
            if (aint2.length >= i3 * (i1 + 1)) {
                ((Buffer) intbuffer.put(aint2, i3 * i1, i3)).position(0).limit(i3);
                GL11.glTexSubImage2D(3553, l1, k2, l2, i2, j2, 32993, 33639, intbuffer);
            }

            i2 >>= 1;
            j2 >>= 1;
            k2 >>= 1;
            l2 >>= 1;
            l1++;
            if (aint1 != null) {
                break;
            }
        }

        ((Buffer) intbuffer).clear();
    }

    public static void allocateTextureMap(
            int i, int j, int k, int l, Stitcher stitcher, TextureMap textureMap) {
        ShaderLog.info(
                "allocateTextureMap "
                        + j
                        + packedTextFragments[0]
                        + k
                        + packedTextFragments[1]
                        + l
                        + packedTextFragments[2]);
        shadersmod.client.ShadersTex.updatingTextureMap = textureMap;
        textureMap.atlasWidth = k;
        textureMap.atlasHeight = l;
        MultiTexID updatingTexValue = getMultiTexID(textureMap);
        shadersmod.client.ShadersTex.updatingTex = updatingTexValue;
        TextureUtil.allocateTextureImpl(updatingTexValue.base, j, k, l);
        if (Shaders.configNormalMap) {
            TextureUtil.allocateTextureImpl(updatingTexValue.norm, j, k, l);
        }

        if (Shaders.configSpecularMap) {
            TextureUtil.allocateTextureImpl(updatingTexValue.spec, j, k, l);
        }

        GlStateManager.bindTexture(i);
    }

    public static void uploadTexSubForLoadAtlas(
            int[][] aint, int i, int j, int k, int l, boolean flag, boolean flag1) {
        TextureUtil.uploadTextureMipmap(aint, i, j, k, l, flag, flag1);
        byte b0 = 0;
        if (Shaders.configNormalMap) {
            int[][] aint1 =
                    readImageAndMipmaps(
                            shadersmod.client.ShadersTex.iconName + "_n",
                            i,
                            j,
                            aint.length,
                            remix.compat.compiler.JvmBooleanSlots.fromInt(b0),
                            -8421377);
            GlStateManager.bindTexture(shadersmod.client.ShadersTex.updatingTex.norm);
            TextureUtil.uploadTextureMipmap(aint1, i, j, k, l, flag, flag1);
        }

        if (Shaders.configSpecularMap) {
            int[][] aint2 =
                    readImageAndMipmaps(
                            shadersmod.client.ShadersTex.iconName + "_s",
                            i,
                            j,
                            aint.length,
                            remix.compat.compiler.JvmBooleanSlots.fromInt(b0),
                            0);
            GlStateManager.bindTexture(shadersmod.client.ShadersTex.updatingTex.spec);
            TextureUtil.uploadTextureMipmap(aint2, i, j, k, l, flag, flag1);
        }

        GlStateManager.bindTexture(shadersmod.client.ShadersTex.updatingTex.base);
    }

    public static void method_dfe0a02049d299e0493c(int[] aint, int i, int j, int k) {
        Math.min(j, k);
        int l = i;
        int i1 = j;
        int j1 = k;
        int k1 = 0;
        int[] aint2 = ShaderOption.lz();
        int l1 = 0;
        int[] aint1 = aint2;

        int i2 = 0;

        label65:
        while (i1 > 1 && j1 > 1) {
            k1 = l + i1 * j1;
            l1 = i1 / 2;
            int i3 = j1 / 2;
            int j2 = 0;

            while (true) {
                if (j2 < i3) {
                    int k2 = k1 + j2 * l1;
                    int l2 = l + j2 * 2 * i1;
                    int j3 = 0;

                    while (j3 < l1) {
                        aint[k2 + j3] =
                                blend4Alpha(
                                        aint[l2 + j3 * 2],
                                        aint[l2 + j3 * 2 + 1],
                                        aint[l2 + i1 + j3 * 2],
                                        aint[l2 + i1 + j3 * 2 + 1]);
                        j3++;
                        if (aint1 != null) {
                            break;
                        }
                    }

                    j2++;
                    if (aint1 == null) {
                        continue;
                    }
                }

                i2++;
                i1 = l1;
                j1 = i3;
                l = k1;
                if (aint1 != null) {
                    break label65;
                }
                break;
            }
        }

        while (i2 > 0) {
            i1 = j >> --i2;
            j1 = k >> i2;
            l = k1 - i1 * j1;
            int k3 = l;
            int l3 = 0;

            while (true) {
                if (l3 < j1) {
                    int i4 = 0;

                    while (i4 < i1) {
                        if (aint[k3] == 0) {
                            aint[k3] = aint[k1 + l3 / 2 * l1 + i4 / 2] & 16777215;
                        }

                        k3++;
                        i4++;
                        if (aint1 != null) {
                            break;
                        }
                    }

                    l3++;
                    if (aint1 == null) {
                        continue;
                    }
                }

                k1 = l;
                l1 = i1;
                if (aint1 != null) {
                    return;
                }
                break;
            }
        }
    }

    public static IntBuffer getIntBuffer(int p171_0) {
        if (shadersmod.client.ShadersTex.intBuffer.capacity() < p171_0) {
            int j = shadersmod.client.ShadersTex.roundUpPOT(p171_0);
            shadersmod.client.ShadersTex.byteBuffer = org.lwjgl.BufferUtils.createByteBuffer(j * 4);
            shadersmod.client.ShadersTex.intBuffer =
                    shadersmod.client.ShadersTex.byteBuffer.asIntBuffer();
        }

        return shadersmod.client.ShadersTex.intBuffer;
    }

    public static void fixTransparentColor(TextureAtlasSprite textureAtlasSprite, int[] aint) {}

    public static void method_990ffcb3236db51971d4(int[] aint, int i, int j, int k) {
        int[] aint2 = ShaderOption.lz();
        Math.min(j, k);
        int l = i;
        int i1 = j;
        int[] aint1 = aint2;
        int j1 = k;
        int k1 = 0;
        int l1 = 0;

        int i2 = 0;

        label65:
        while (i1 > 1 && j1 > 1) {
            k1 = l + i1 * j1;
            l1 = i1 / 2;
            int i3 = j1 / 2;
            int j2 = 0;

            while (true) {
                if (j2 < i3) {
                    int k2 = k1 + j2 * l1;
                    int l2 = l + j2 * 2 * i1;
                    int j3 = 0;

                    while (j3 < l1) {
                        aint[k2 + j3] =
                                blend4Simple(
                                        aint[l2 + j3 * 2],
                                        aint[l2 + j3 * 2 + 1],
                                        aint[l2 + i1 + j3 * 2],
                                        aint[l2 + i1 + j3 * 2 + 1]);
                        j3++;
                        if (aint1 != null) {
                            break;
                        }
                    }

                    j2++;
                    if (aint1 == null) {
                        continue;
                    }
                }

                i2++;
                i1 = l1;
                j1 = i3;
                l = k1;
                if (aint1 != null) {
                    break label65;
                }
                break;
            }
        }

        while (i2 > 0) {
            i1 = j >> --i2;
            j1 = k >> i2;
            l = k1 - i1 * j1;
            int k3 = l;
            int l3 = 0;

            while (true) {
                if (l3 < j1) {
                    int i4 = 0;

                    while (i4 < i1) {
                        if (aint[k3] == 0) {
                            aint[k3] = aint[k1 + l3 / 2 * l1 + i4 / 2] & 16777215;
                        }

                        k3++;
                        i4++;
                        if (aint1 != null) {
                            break;
                        }
                    }

                    l3++;
                    if (aint1 == null) {
                        continue;
                    }
                }

                k1 = l;
                l1 = i1;
                if (aint1 != null) {
                    return;
                }
                break;
            }
        }
    }

    public static int[] getIntArray(int p171_0) {
        if (shadersmod.client.ShadersTex.intArray == null) {
            shadersmod.client.ShadersTex.intArray = new int[1048576];
        }

        if (shadersmod.client.ShadersTex.intArray.length < p171_0) {
            shadersmod.client.ShadersTex.intArray =
                    new int[shadersmod.client.ShadersTex.roundUpPOT(p171_0)];
        }

        return shadersmod.client.ShadersTex.intArray;
    }

    public static void loadNSMap1(
            IResourceManager iResourceManager,
            ResourceLocation resourceLocation,
            int i,
            int j,
            int[] aint,
            int k,
            int l) {
        byte b0 = 0;

        try {
            IResource resource = iResourceManager.getResource(resourceLocation);
            BufferedImage bufferedimage = ImageIO.read(resource.getInputStream());
            if (bufferedimage != null
                    && bufferedimage.getWidth() == i
                    && bufferedimage.getHeight() == j) {
                bufferedimage.getRGB(0, 0, i, j, aint, k, i);
                b0 = 1;
            }
        } catch (IOException ioexception) {
        }

        if (b0 == 0) {
            Arrays.fill(aint, k, k + i * j, l);
        }
    }

    public static void bindTextureMapForUpdateAndRender(
            TextureManager textureManager, ResourceLocation resourceLocation) {
        TextureMap texture = (TextureMap) textureManager.getTexture(resourceLocation);
        Shaders.atlasSizeX = texture.atlasWidth;
        Shaders.atlasSizeY = texture.atlasHeight;
        bindTextures(shadersmod.client.ShadersTex.updatingTex = texture.getMultiTexID());
    }

    public static int blend4Alpha(int i, int j, int k, int l) {
        int i1;
        int j1;
        int k1;
        int l1;
        int j2;
        int k2;
        label11:
        {
            int[] aint1 = ShaderOption.lz();
            i1 = i >>> 24 & 0xFF;
            j1 = j >>> 24 & 0xFF;
            int[] aint = aint1;
            k1 = k >>> 24 & 0xFF;
            l1 = l >>> 24 & 0xFF;
            int i2 = i1 + j1 + k1 + l1;
            j2 = (i2 + 2) / 4;
            if (i2 != 0) {
                k2 = i2;
                if (aint == null) {
                    break label11;
                }
            }

            k2 = 4;
            i1 = 1;
            j1 = 1;
            k1 = 1;
            l1 = 1;
        }

        int l2 = (k2 + 1) / 2;
        return j2 << 24
                | ((i >>> 16 & 0xFF) * i1
                                        + (j >>> 16 & 0xFF) * j1
                                        + (k >>> 16 & 0xFF) * k1
                                        + (l >>> 16 & 0xFF) * l1
                                        + l2)
                                / k2
                        << 16
                | ((i >>> 8 & 0xFF) * i1
                                        + (j >>> 8 & 0xFF) * j1
                                        + (k >>> 8 & 0xFF) * k1
                                        + (l >>> 8 & 0xFF) * l1
                                        + l2)
                                / k2
                        << 8
                | ((i >>> 0 & 0xFF) * i1
                                        + (j >>> 0 & 0xFF) * j1
                                        + (k >>> 0 & 0xFF) * k1
                                        + (l >>> 0 & 0xFF) * l1
                                        + l2)
                                / k2
                        << 0;
    }

    public static int blend4Simple(int p171_0, int p171_1, int p171_2, int p171_3) {
        return ((p171_0 >>> 24 & 0xFF)
                                        + (p171_1 >>> 24 & 0xFF)
                                        + (p171_2 >>> 24 & 0xFF)
                                        + (p171_3 >>> 24 & 0xFF)
                                        + 2)
                                / 4
                        << 24
                | ((p171_0 >>> 16 & 0xFF)
                                        + (p171_1 >>> 16 & 0xFF)
                                        + (p171_2 >>> 16 & 0xFF)
                                        + (p171_3 >>> 16 & 0xFF)
                                        + 2)
                                / 4
                        << 16
                | ((p171_0 >>> 8 & 0xFF)
                                        + (p171_1 >>> 8 & 0xFF)
                                        + (p171_2 >>> 8 & 0xFF)
                                        + (p171_3 >>> 8 & 0xFF)
                                        + 2)
                                / 4
                        << 8
                | ((p171_0 >>> 0 & 0xFF)
                                        + (p171_1 >>> 0 & 0xFF)
                                        + (p171_2 >>> 0 & 0xFF)
                                        + (p171_3 >>> 0 & 0xFF)
                                        + 2)
                                / 4
                        << 0;
    }

    public static int[][] prepareAF(
            TextureAtlasSprite textureAtlasSprite, int[][] aint, int i, int j) {

        return aint;
    }

    public static int log2(int p171_0) {
        int j = 0;
        if ((p171_0 & -65536) != 0) {
            j += 16;
            p171_0 >>= 16;
        }

        if ((p171_0 & 0xFF00) != 0) {
            j += 8;
            p171_0 >>= 8;
        }

        if ((p171_0 & 240) != 0) {
            j += 4;
            p171_0 >>= 4;
        }

        if ((p171_0 & 6) != 0) {
            j += 2;
            p171_0 >>= 2;
        }

        if ((p171_0 & 2) != 0) {
            j++;
        }

        return j;
    }

    public static MultiTexID getMultiTexID(AbstractTexture abstractTexture) {
        shadersmod.client.MultiTexID aGt = abstractTexture.multiTex;
        if (aGt == null) {
            int glTextureId = abstractTexture.getGlTextureId();
            aGt =
                    (shadersmod.client.MultiTexID)
                            shadersmod.client.ShadersTex.multiTexMap.get(glTextureId);
            if (aGt == null) {
                aGt =
                        new shadersmod.client.MultiTexID(
                                glTextureId,
                                org.lwjgl.opengl.GL11.glGenTextures(),
                                org.lwjgl.opengl.GL11.glGenTextures());
                shadersmod.client.ShadersTex.multiTexMap.put(glTextureId, aGt);
            }

            abstractTexture.multiTex = aGt;
        }

        return aGt;
    }

    public static void updateSubTex1(int[] aint2, int i, int j, int k, int l) {
        int i1 = 0;
        int[] aint1 = ShaderOption.lz();
        int j1 = i;
        int k1 = j;
        int l1 = k;
        int i2 = l;
        int[] aint = aint1;

        while (j1 > 0 && k1 > 0) {
            GL11.glCopyTexSubImage2D(3553, i1, l1, i2, 0, 0, j1, k1);
            i1++;
            j1 /= 2;
            k1 /= 2;
            l1 /= 2;
            i2 /= 2;
            if (aint != null) {
                break;
            }
        }
    }

    public static IResource loadResource(
            IResourceManager iResourceManager, ResourceLocation resourceLocation)
            throws IOException {
        shadersmod.client.ShadersTex.resManager = iResourceManager;
        shadersmod.client.ShadersTex.resLocation = resourceLocation;
        return iResourceManager.getResource(resourceLocation);
    }

    public static void uploadTexSub(
            int[][] p171_0,
            int p171_1,
            int p171_2,
            int p171_3,
            int p171_4,
            boolean p171_5,
            boolean p171_6) {
        net.minecraft.client.renderer.texture.TextureUtil.uploadTextureMipmap(
                p171_0, p171_1, p171_2, p171_3, p171_4, p171_5, p171_6);
        if (shadersmod.client.Shaders.configNormalMap
                || shadersmod.client.Shaders.configSpecularMap) {
            if (shadersmod.client.Shaders.configNormalMap) {
                net.minecraft.client.renderer.GlStateManager.bindTexture(
                        shadersmod.client.ShadersTex.updatingTex.norm);
                shadersmod.client.ShadersTex.uploadTexSub1(
                        p171_0, p171_1, p171_2, p171_3, p171_4, 1);
            }

            if (shadersmod.client.Shaders.configSpecularMap) {
                net.minecraft.client.renderer.GlStateManager.bindTexture(
                        shadersmod.client.ShadersTex.updatingTex.spec);
                shadersmod.client.ShadersTex.uploadTexSub1(
                        p171_0, p171_1, p171_2, p171_3, p171_4, 2);
            }

            net.minecraft.client.renderer.GlStateManager.bindTexture(
                    shadersmod.client.ShadersTex.updatingTex.base);
        }
    }

    public static int blendColor(int p171_0, int p171_1, int p171_2) {
        int l = 255 - p171_2;
        return ((p171_0 >>> 24 & 0xFF) * p171_2 + (p171_1 >>> 24 & 0xFF) * l) / 255 << 24
                | ((p171_0 >>> 16 & 0xFF) * p171_2 + (p171_1 >>> 16 & 0xFF) * l) / 255 << 16
                | ((p171_0 >>> 8 & 0xFF) * p171_2 + (p171_1 >>> 8 & 0xFF) * l) / 255 << 8
                | ((p171_0 >>> 0 & 0xFF) * p171_2 + (p171_1 >>> 0 & 0xFF) * l) / 255 << 0;
    }

    public static void bindTextures(MultiTexID boundTexValue) {
        shadersmod.client.ShadersTex.boundTex = boundTexValue;
        if (shadersmod.client.Shaders.isRenderingWorld
                && net.minecraft.client.renderer.GlStateManager.getActiveTextureUnit() == 33984) {
            if (shadersmod.client.Shaders.configNormalMap) {
                net.minecraft.client.renderer.GlStateManager.setActiveTexture(33986);
                net.minecraft.client.renderer.GlStateManager.bindTexture(boundTexValue.norm);
            }

            if (shadersmod.client.Shaders.configSpecularMap) {
                net.minecraft.client.renderer.GlStateManager.setActiveTexture(33987);
                net.minecraft.client.renderer.GlStateManager.bindTexture(boundTexValue.spec);
            }

            net.minecraft.client.renderer.GlStateManager.setActiveTexture(33984);
        }

        net.minecraft.client.renderer.GlStateManager.bindTexture(boundTexValue.base);
    }

    public static int[][] getFrameTexData(int[][] aint, int i, int j, int k) {
        int[] aint5 = ShaderOption.lz();
        int l = aint.length;
        int[] aint1 = aint5;
        int[][] aint2 = new int[l][];
        int i1 = 0;

        while (i1 < l) {
            int[] aint3 = aint[i1];
            if (aint3 != null) {
                int j1 = (i >> i1) * (j >> i1);
                int[] aint4 = new int[j1 * 3];
                aint2[i1] = aint4;
                int k1 = aint3.length / 3;
                int l1 = j1 * k;
                byte b0 = 0;
                System.arraycopy(aint3, l1, aint4, b0, j1);
                l1 += k1;
                int i2 = b0 + j1;
                System.arraycopy(aint3, l1, aint4, i2, j1);
                l1 += k1;
                i2 += j1;
                System.arraycopy(aint3, l1, aint4, i2, j1);
            }

            i1++;
            if (aint1 != null) {
                break;
            }
        }

        return aint2;
    }

    public static void updateSubImage(
            MultiTexID multiTexID,
            int[] p171_1,
            int p171_2,
            int p171_3,
            int p171_4,
            int p171_5,
            boolean p171_6,
            boolean p171_7) {
        int i1 = p171_2 * p171_3;
        java.nio.IntBuffer intbuffer = shadersmod.client.ShadersTex.getIntBuffer(i1);
        ((java.nio.Buffer) intbuffer).clear();
        intbuffer.put(p171_1, 0, i1);
        ((java.nio.Buffer) intbuffer).position(0).limit(i1);
        net.minecraft.client.renderer.GlStateManager.bindTexture(multiTexID.base);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10241, 9728);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10240, 9728);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10242, 10497);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10243, 10497);
        org.lwjgl.opengl.GL11.glTexSubImage2D(
                3553, 0, p171_4, p171_5, p171_2, p171_3, 32993, 33639, intbuffer);
        if (p171_1.length == i1 * 3) {
            ((java.nio.Buffer) intbuffer).clear();
            ((java.nio.Buffer) intbuffer.put(p171_1, i1, i1)).position(0);
            ((java.nio.Buffer) intbuffer).position(0).limit(i1);
        }

        net.minecraft.client.renderer.GlStateManager.bindTexture(multiTexID.norm);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10241, 9728);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10240, 9728);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10242, 10497);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10243, 10497);
        org.lwjgl.opengl.GL11.glTexSubImage2D(
                3553, 0, p171_4, p171_5, p171_2, p171_3, 32993, 33639, intbuffer);
        if (p171_1.length == i1 * 3) {
            ((java.nio.Buffer) intbuffer).clear();
            intbuffer.put(p171_1, i1 * 2, i1);
            ((java.nio.Buffer) intbuffer).position(0).limit(i1);
        }

        net.minecraft.client.renderer.GlStateManager.bindTexture(multiTexID.spec);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10241, 9728);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10240, 9728);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10242, 10497);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10243, 10497);
        org.lwjgl.opengl.GL11.glTexSubImage2D(
                3553, 0, p171_4, p171_5, p171_2, p171_3, 32993, 33639, intbuffer);
        net.minecraft.client.renderer.GlStateManager.setActiveTexture(33984);
    }

    public static void bindTextures(int p171_0) {
        shadersmod.client.MultiTexID multiTexID =
                (shadersmod.client.MultiTexID) shadersmod.client.ShadersTex.multiTexMap.get(p171_0);
        shadersmod.client.ShadersTex.bindTextures(multiTexID);
    }

    public static ResourceLocation getNSMapLocation(
            ResourceLocation resourceLocation, String text) {
        String textValue = resourceLocation.getResourcePath();
        String[] astring = textValue.split(".png");
        String textValue2 = astring[0];
        return new ResourceLocation(
                resourceLocation.getResourceDomain(),
                textValue2 + packedTextFragments[3] + text + ".png");
    }

    public static void updateDynTexSubImage1(
            int[] p171_0, int p171_1, int p171_2, int p171_3, int p171_4, int p171_5) {
        int j1 = p171_1 * p171_2;
        java.nio.IntBuffer intbuffer = shadersmod.client.ShadersTex.getIntBuffer(j1);
        ((java.nio.Buffer) intbuffer).clear();
        int k1 = p171_5 * j1;
        if (p171_0.length >= k1 + j1) {
            ((java.nio.Buffer) intbuffer.put(p171_0, k1, j1)).position(0).limit(j1);
            org.lwjgl.opengl.GL11.glTexSubImage2D(
                    3553, 0, p171_3, p171_4, p171_1, p171_2, 32993, 33639, intbuffer);
            ((java.nio.Buffer) intbuffer).clear();
        }
    }

    public static boolean isSemiTransparent(int[] aint, int i, int j) {
        int[] aint2 = ShaderOption.lz();
        int k = i * j;
        int[] aint1 = aint2;
        if (aint[0] >>> 24 == 255 && aint[k - 1] == 0) {
            return true;
        }

        int l = 0;

        while (l < k) {
            int i1 = aint[l] >>> 24;
            if (i1 != 0 && i1 != 255) {
                return true;
            }

            l++;
            if (aint1 != null) {
                break;
            }
        }

        return false;
    }

    public static void loadNSMap(
            IResourceManager iResourceManager,
            ResourceLocation resourceLocation,
            int i,
            int j,
            int[] aint) {
        if (Shaders.configNormalMap) {
            loadNSMap1(
                    iResourceManager,
                    getNSMapLocation(resourceLocation, packedTextFragments[4]),
                    i,
                    j,
                    aint,
                    i * j,
                    -8421377);
        }

        if (Shaders.configSpecularMap) {
            loadNSMap1(
                    iResourceManager,
                    getNSMapLocation(resourceLocation, packedTextFragments[5]),
                    i,
                    j,
                    aint,
                    i * j * 2,
                    0);
        }
    }

    public static int[][] genMipmapsSimple(int i, int j, int[][] aint) {
        int[] aint4 = ShaderOption.lz();
        int k = 1;
        int[] aint1 = aint4;

        while (k <= i) {
            if (aint[k] == null) {
                int l = j >> k;
                int i1 = l * 2;
                int[] aint2 = aint[k - 1];
                int[] aint3 = aint[k] = new int[l * l];
                int j1 = 0;

                while (j1 < l) {
                    int k1 = 0;

                    while (k1 < l) {
                        int l1 = j1 * 2 * i1 + k1 * 2;
                        aint3[j1 * l + k1] =
                                blend4Simple(
                                        aint2[l1],
                                        aint2[l1 + 1],
                                        aint2[l1 + i1],
                                        aint2[l1 + i1 + 1]);
                        k1++;
                        if (aint1 != null) {
                            break;
                        }
                    }

                    j1++;
                    if (aint1 != null) {
                        break;
                    }
                }
            }

            k++;
            if (aint1 != null) {
                break;
            }
        }

        return aint;
    }

    public static void loadLayeredTexture(
            LayeredTexture layeredTexture, IResourceManager iResourceManager, List list) {
        int i = 0;
        int j = 0;
        int[] aint3 = ShaderOption.lz();
        int k = 0;
        int[] aint1 = null;
        int[] aint = aint3;

        for (java.util.Iterator<?> __recoveryIterator171_58844 = (list).iterator();
                (__recoveryIterator171_58844).hasNext(); ) {
            String text = (String) (__recoveryIterator171_58844).next();
            if (text != null) {
                try {
                    ResourceLocation resourceLocation = new ResourceLocation(text);
                    InputStream inputstream =
                            iResourceManager.getResource(resourceLocation).getInputStream();
                    BufferedImage bufferedimage = ImageIO.read(inputstream);
                    if (k == 0) {
                        i = bufferedimage.getWidth();
                        j = bufferedimage.getHeight();
                        k = i * j;
                        aint1 = createAIntImage(k, 0);
                    }

                    int[] aint2 = getIntArray(k * 3);
                    bufferedimage.getRGB(0, 0, i, j, aint2, 0, i);
                    loadNSMap(iResourceManager, resourceLocation, i, j, aint2);
                    int l = 0;

                    while (l < k) {
                        int i1 = aint2[l] >>> 24 & 0xFF;
                        aint1[k * 0 + l] = blendColor(aint2[k * 0 + l], aint1[k * 0 + l], i1);
                        aint1[k * 1 + l] = blendColor(aint2[k * 1 + l], aint1[k * 1 + l], i1);
                        aint1[k * 2 + l] = blendColor(aint2[k * 2 + l], aint1[k * 2 + l], i1);
                        l++;
                        if (aint != null) {
                            break;
                        }
                    }
                } catch (IOException ioexception) {
                    ioexception.printStackTrace();
                }
            }

            if (aint != null) {
                break;
            }
        }

        setupTexture(layeredTexture.getMultiTexID(), aint1, i, j, false, false);
    }

    public static void updateDynamicTexture(
            int p171_0, int[] p171_1, int p171_2, int p171_3, DynamicTexture dynamicTexture) {
        shadersmod.client.MultiTexID multiTexID = dynamicTexture.getMultiTexID();
        net.minecraft.client.renderer.GlStateManager.bindTexture(multiTexID.base);
        shadersmod.client.ShadersTex.updateDynTexSubImage1(p171_1, p171_2, p171_3, 0, 0, 0);
        net.minecraft.client.renderer.GlStateManager.bindTexture(multiTexID.norm);
        shadersmod.client.ShadersTex.updateDynTexSubImage1(p171_1, p171_2, p171_3, 0, 0, 1);
        net.minecraft.client.renderer.GlStateManager.bindTexture(multiTexID.spec);
        shadersmod.client.ShadersTex.updateDynTexSubImage1(p171_1, p171_2, p171_3, 0, 0, 2);
        net.minecraft.client.renderer.GlStateManager.bindTexture(multiTexID.base);
    }

    public static void setupTexture(
            MultiTexID multiTexID,
            int[] p171_1,
            int p171_2,
            int p171_3,
            boolean p171_4,
            boolean p171_5) {
        int k = p171_4 ? 9729 : 9728;
        int l = p171_5 ? 10496 : 10497;
        int i1 = p171_2 * p171_3;
        java.nio.IntBuffer intbuffer = shadersmod.client.ShadersTex.getIntBuffer(i1);
        ((java.nio.Buffer) intbuffer).clear();
        ((java.nio.Buffer) intbuffer.put(p171_1, 0, i1)).position(0).limit(i1);
        net.minecraft.client.renderer.GlStateManager.bindTexture(multiTexID.base);
        org.lwjgl.opengl.GL11.glTexImage2D(
                3553, 0, 6408, p171_2, p171_3, 0, 32993, 33639, intbuffer);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10241, k);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10240, k);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10242, l);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10243, l);
        ((java.nio.Buffer) intbuffer.put(p171_1, i1, i1)).position(0).limit(i1);
        net.minecraft.client.renderer.GlStateManager.bindTexture(multiTexID.norm);
        org.lwjgl.opengl.GL11.glTexImage2D(
                3553, 0, 6408, p171_2, p171_3, 0, 32993, 33639, intbuffer);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10241, k);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10240, k);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10242, l);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10243, l);
        ((java.nio.Buffer) intbuffer.put(p171_1, i1 * 2, i1)).position(0).limit(i1);
        net.minecraft.client.renderer.GlStateManager.bindTexture(multiTexID.spec);
        org.lwjgl.opengl.GL11.glTexImage2D(
                3553, 0, 6408, p171_2, p171_3, 0, 32993, 33639, intbuffer);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10241, k);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10240, k);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10242, l);
        org.lwjgl.opengl.GL11.glTexParameteri(3553, 10243, l);
        net.minecraft.client.renderer.GlStateManager.bindTexture(multiTexID.base);
    }

    public static ITextureObject createDefaultTexture() {
        net.minecraft.client.renderer.texture.DynamicTexture dynamicTexture =
                new net.minecraft.client.renderer.texture.DynamicTexture(1, 1);
        dynamicTexture.getTextureData()[0] = -1;
        dynamicTexture.updateDynamicTexture();
        return dynamicTexture;
    }

    static void updateTextureMinMagFilter() {
        TextureManager textureManager = MinecraftClient.getMinecraft().getTextureManager();
        ITextureObject texture = textureManager.getTexture(TextureMap.locationBlocksTexture);
        if (texture != null) {
            MultiTexID multiTexID = texture.getMultiTexID();
            GlStateManager.bindTexture(multiTexID.base);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
            GlStateManager.bindTexture(multiTexID.norm);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
            GlStateManager.bindTexture(multiTexID.spec);
            GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
            GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
            GlStateManager.bindTexture(0);
        }
    }

    public static int loadSimpleTexture(
            int i,
            BufferedImage bufferedimage,
            boolean flag,
            boolean flag1,
            IResourceManager iResourceManager,
            ResourceLocation resourceLocation,
            MultiTexID multiTexID) {
        int j = bufferedimage.getWidth();
        int k = bufferedimage.getHeight();
        int l = j * k;
        int[] aint = getIntArray(l * 3);
        bufferedimage.getRGB(0, 0, j, k, aint, 0, j);
        loadNSMap(iResourceManager, resourceLocation, j, k, aint);
        setupTexture(multiTexID, aint, j, k, flag, flag1);
        return i;
    }

    public static TextureAtlasSprite setSprite(TextureAtlasSprite textureAtlasSprite) {
        shadersmod.client.ShadersTex.updatingSprite = textureAtlasSprite;
        return textureAtlasSprite;
    }

    public static String setIconName(String text) {
        shadersmod.client.ShadersTex.iconName = text;
        return text;
    }

    public static void mergeImage(int[] aint, int i, int j, int k) {}

    public static BufferedImage readImage(ResourceLocation resourceLocation) {
        try {
            IResource resource =
                    shadersmod.client.ShadersTex.resManager.getResource(resourceLocation);
            if (resource == null) {
                return null;
            }

            InputStream inputstream = resource.getInputStream();
            if (inputstream == null) {
                return null;
            }

            BufferedImage bufferedimage = ImageIO.read(inputstream);
            inputstream.close();
            return bufferedimage;
        } catch (IOException ioexception) {
            return null;
        }
    }

    public static void initDynamicTexture(
            int p171_0, int p171_1, int p171_2, DynamicTexture dynamicTexture) {
        shadersmod.client.MultiTexID multiTexID = dynamicTexture.getMultiTexID();
        int[] aint = dynamicTexture.getTextureData();
        int l = p171_1 * p171_2;
        java.util.Arrays.fill(aint, l, l * 2, -8421377);
        java.util.Arrays.fill(aint, l * 2, l * 3, 0);
        net.minecraft.client.renderer.texture.TextureUtil.allocateTexture(
                multiTexID.base, p171_1, p171_2);
        net.minecraft.client.renderer.texture.TextureUtil.setTextureBlurMipmap(false, false);
        net.minecraft.client.renderer.texture.TextureUtil.setTextureClamped(false);
        net.minecraft.client.renderer.texture.TextureUtil.allocateTexture(
                multiTexID.norm, p171_1, p171_2);
        net.minecraft.client.renderer.texture.TextureUtil.setTextureBlurMipmap(false, false);
        net.minecraft.client.renderer.texture.TextureUtil.setTextureClamped(false);
        net.minecraft.client.renderer.texture.TextureUtil.allocateTexture(
                multiTexID.spec, p171_1, p171_2);
        net.minecraft.client.renderer.texture.TextureUtil.setTextureBlurMipmap(false, false);
        net.minecraft.client.renderer.texture.TextureUtil.setTextureClamped(false);
        net.minecraft.client.renderer.GlStateManager.bindTexture(multiTexID.base);
    }

    public static int[] createAIntImage(int p171_0) {
        int[] aint = new int[p171_0 * 3];
        java.util.Arrays.fill(aint, 0, p171_0, 0);
        java.util.Arrays.fill(aint, p171_0, p171_0 * 2, -8421377);
        java.util.Arrays.fill(aint, p171_0 * 2, p171_0 * 3, 0);
        return aint;
    }

    // region String decoding support

    // endregion
}
