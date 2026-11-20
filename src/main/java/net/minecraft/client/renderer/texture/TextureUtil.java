package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import optifine.Reflector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

public class TextureUtil {
    private static Logger logger;
    public static DynamicTexture missingTexture;
    private static String[] packedTextFragments;

    private static int[] mipmapBuffer;
    public static int[] missingTextureData;

    private static IntBuffer dataBuffer;

    public static void setTextureClamped(boolean p171_0) {
        if (p171_0) {
            org.lwjgl.opengl.GL11.glTexParameteri(3553, 10242, 33071);
            org.lwjgl.opengl.GL11.glTexParameteri(3553, 10243, 33071);
        } else {
            org.lwjgl.opengl.GL11.glTexParameteri(3553, 10242, 10497);
            org.lwjgl.opengl.GL11.glTexParameteri(3553, 10243, 10497);
        }
    }

    public static int[] readImageData(
            IResourceManager iResourceManager, ResourceLocation resourceLocation)
            throws IOException {
        BufferedImage bufferedimage =
                readBufferedImage(iResourceManager.getResource(resourceLocation).getInputStream());

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (bufferedimage == null) {
                return null;
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }

        int width = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        int[] aint = new int[width * j];
        bufferedimage.getRGB(0, 0, width, j, aint, 0, width);
        return aint;
    }

    public static void deleteTexture(int i) {
        GlStateManager.deleteTexture(i);
    }

    private static void uploadTextureImageSubImpl(
            BufferedImage bufferedimage, int i, int j, boolean flag, boolean flag1) {
        int k2 = TextureManager.method_beea3e16fe3ec0995eb9();
        int l = bufferedimage.getWidth();
        int height = bufferedimage.getHeight();
        int j1 = 4194304 / l;
        int k = k2;
        int[] aint = new int[j1 * l];
        setTextureBlurred(flag);
        setTextureClamped(flag1);
        int k1 = 0;

        while (k1 < l * height) {
            int l1 = k1 / l;
            int i2 = Math.min(j1, height - l1);
            int j2 = l * i2;
            bufferedimage.getRGB(0, l1, l, i2, aint, 0, l);
            copyToBuffer(aint, j2);
            GL11.glTexSubImage2D(
                    3553,
                    0,
                    i,
                    j + l1,
                    l,
                    i2,
                    32993,
                    33639,
                    net.minecraft.client.renderer.texture.TextureUtil.dataBuffer);
            k1 += l * j1;
            if (k != 0) {
                break;
            }
        }
    }

    public static void saveGlTexture(String text, int i, int j, int k, int l) {
        int j2 = TextureManager.method_7eec03da329a017ec143();
        bindTexture(i);
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        int i1 = j2;
        int j1 = 0;

        while (j1 <= j) {
            File file1 = new File(text + packedTextFragments[0] + j1 + ".png");
            int k1 = k >> j1;
            int l1 = l >> j1;
            int i2 = k1 * l1;
            IntBuffer intbuffer = BufferUtils.createIntBuffer(i2);
            int[] aint = new int[i2];
            GL11.glGetTexImage(3553, j1, 32993, 33639, intbuffer);
            intbuffer.get(aint);
            BufferedImage bufferedimage = new BufferedImage(k1, l1, 2);
            bufferedimage.setRGB(0, 0, k1, l1, aint, 0, k1);

            try {
                ImageIO.write(bufferedimage, "png", file1);
                net.minecraft.client.renderer.texture.TextureUtil.logger.debug(
                        "Exported png to: {}", new Object[] {file1.getAbsolutePath()});
            } catch (Exception exception) {
                net.minecraft.client.renderer.texture.TextureUtil.logger.debug(
                        "Unable to write: ", exception);
            }

            j1++;
            if (i1 == 0) {
                break;
            }
        }
    }

    public static BufferedImage readBufferedImage(InputStream inputStream)
            throws IOException, IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Checked exception carried by the original JVM handler.
            if (inputStream == null) {
                return null;
            }
        } catch (java.io.IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.client.renderer.texture.TextureUtil.a(ioexception));
        }

        java.awt.image.BufferedImage bufferedimage;
        try {
            bufferedimage = javax.imageio.ImageIO.read(inputStream);
        } finally {
            org.apache.commons.io.IOUtils.closeQuietly(inputStream);
        }

        return bufferedimage;
    }

    public static int uploadTextureImageAllocate(
            int p171_0, BufferedImage bufferedImage, boolean p171_2, boolean p171_3) {
        net.minecraft.client.renderer.texture.TextureUtil.allocateTexture(
                p171_0, bufferedImage.getWidth(), bufferedImage.getHeight());
        return net.minecraft.client.renderer.texture.TextureUtil.uploadTextureImageSub(
                p171_0, bufferedImage, 0, 0, p171_2, p171_3);
    }

    public static void uploadTextureMipmap(
            int[][] aint, int i, int j, int k, int l, boolean flag, boolean flag1) {
        int k1 = TextureManager.method_beea3e16fe3ec0995eb9();
        int j1 = 0;
        int i1 = k1;

        while (j1 < aint.length) {
            int[] aint1 = aint[j1];
            uploadTextureSub(
                    j1, aint1, i >> j1, j >> j1, k >> j1, l >> j1, flag, flag1, aint.length > 1);
            j1++;
            if (i1 != 0) {
                break;
            }
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public static int uploadTextureImageSub(
            int p171_0,
            BufferedImage bufferedImage,
            int p171_2,
            int p171_3,
            boolean p171_4,
            boolean p171_5) {
        net.minecraft.client.renderer.texture.TextureUtil.bindTexture(p171_0);
        net.minecraft.client.renderer.texture.TextureUtil.uploadTextureImageSubImpl(
                bufferedImage, p171_2, p171_3, p171_4, p171_5);
        return p171_0;
    }

    public static void processPixelValues(int[] aint, int i, int j) {
        int[] aint1 = new int[i];
        int j1 = TextureManager.method_7eec03da329a017ec143();
        int l = j / 2;
        int k = j1;
        int i1 = 0;

        while (i1 < l) {
            System.arraycopy(aint, i1 * i, aint1, 0, i);
            System.arraycopy(aint, (j - 1 - i1) * i, aint, i1 * i, i);
            System.arraycopy(aint1, 0, aint, (j - 1 - i1) * i, i);
            i1++;
            if (k == 0) {
                break;
            }
        }
    }

    public static int anaglyphColor(int p171_0) {
        int j = p171_0 >> 24 & 0xFF;
        int k = p171_0 >> 16 & 0xFF;
        int l = p171_0 >> 8 & 0xFF;
        int i1 = p171_0 & 0xFF;
        int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
        int k1 = (k * 30 + l * 70) / 100;
        int l1 = (k * 30 + i1 * 70) / 100;
        return j << 24 | j1 << 16 | k1 << 8 | l1;
    }

    public static void setTextureBlurMipmap(boolean p171_0, boolean p171_1) {
        if (p171_0) {
            org.lwjgl.opengl.GL11.glTexParameteri(3553, 10241, p171_1 ? 9987 : 9729);
            org.lwjgl.opengl.GL11.glTexParameteri(3553, 10240, 9729);
        } else {
            int i = optifine.OptiFineConfig.jG();
            org.lwjgl.opengl.GL11.glTexParameteri(3553, 10241, p171_1 ? i : 9728);
            org.lwjgl.opengl.GL11.glTexParameteri(3553, 10240, 9728);
        }
    }

    public static int[] updateAnaglyph(int[] aint) {
        int[] aint1 = new int[aint.length];

        for (int i = 0; i < aint.length; i++) {
            aint1[i] = anaglyphColor(aint[i]);
        }

        return aint1;
    }

    public static void allocateTexture(int i, int j, int k) {
        allocateTextureImpl(i, 0, j, k);
    }

    private static void uploadTextureSub(
            int p171_0,
            int[] p171_1,
            int p171_2,
            int p171_3,
            int p171_4,
            int p171_5,
            boolean p171_6,
            boolean p171_7,
            boolean p171_8) {
        int j1 = 4194304 / p171_2;
        net.minecraft.client.renderer.texture.TextureUtil.setTextureBlurMipmap(p171_6, p171_8);
        net.minecraft.client.renderer.texture.TextureUtil.setTextureClamped(p171_7);
        int l1 = 0;

        while (l1 < p171_2 * p171_3) {
            int i2 = l1 / p171_2;
            int k1 = java.lang.Math.min(j1, p171_3 - i2);
            int j2 = p171_2 * k1;
            net.minecraft.client.renderer.texture.TextureUtil.copyToBufferPos(p171_1, l1, j2);
            org.lwjgl.opengl.GL11.glTexSubImage2D(
                    3553,
                    p171_0,
                    p171_4,
                    p171_5 + i2,
                    p171_2,
                    k1,
                    32993,
                    33639,
                    net.minecraft.client.renderer.texture.TextureUtil.dataBuffer);
            l1 += p171_2 * k1;
        }
    }

    private static int blendColorComponent(
            int p171_0, int p171_1, int p171_2, int p171_3, int p171_4) {
        float fx = (float) java.lang.Math.pow((p171_0 >> p171_4 & 0xFF) / 255.0F, 2.2);
        float f1 = (float) java.lang.Math.pow((p171_1 >> p171_4 & 0xFF) / 255.0F, 2.2);
        float f2 = (float) java.lang.Math.pow((p171_2 >> p171_4 & 0xFF) / 255.0F, 2.2);
        float f3 = (float) java.lang.Math.pow((p171_3 >> p171_4 & 0xFF) / 255.0F, 2.2);
        float f4 = (float) java.lang.Math.pow((fx + f1 + f2 + f3) * 0.25, 0.45454545454545453);
        return (int) (f4 * 255.0);
    }

    public static void uploadTexture(int p171_0, int[] p171_1, int p171_2, int p171_3) {
        net.minecraft.client.renderer.texture.TextureUtil.bindTexture(p171_0);
        net.minecraft.client.renderer.texture.TextureUtil.uploadTextureSub(
                0, p171_1, p171_2, p171_3, 0, 0, false, false, false);
    }

    private static void copyToBuffer(int[] aint, int i) {
        copyToBufferPos(aint, 0, i);
    }

    public static int glGenTextures() {
        return GlStateManager.generateTexture();
    }

    static {
        net.minecraft.client.renderer.texture.TextureUtil.packedTextFragments = new String[3];
        net.minecraft.client.renderer.texture.TextureUtil.packedTextFragments[0] = new String("_");
        net.minecraft.client.renderer.texture.TextureUtil.logger = LogManager.getLogger();
        net.minecraft.client.renderer.texture.TextureUtil.dataBuffer =
                GLAllocation.createDirectIntBuffer(4194304);
        net.minecraft.client.renderer.texture.TextureUtil.missingTexture =
                new DynamicTexture(16, 16);
        net.minecraft.client.renderer.texture.TextureUtil.missingTextureData =
                net.minecraft.client.renderer.texture.TextureUtil.missingTexture.getTextureData();
        int[] aint =
                new int[] {-524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040};
        int[] aint1 =
                new int[] {
                    -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216,
                    -16777216
                };
        int j1 = aint.length;
        for (int k1 = 0; k1 < 16; k1++) {
            System.arraycopy(
                    k1 < j1 ? aint : aint1,
                    0,
                    net.minecraft.client.renderer.texture.TextureUtil.missingTextureData,
                    16 * k1,
                    j1);
            System.arraycopy(
                    k1 < j1 ? aint1 : aint,
                    0,
                    net.minecraft.client.renderer.texture.TextureUtil.missingTextureData,
                    16 * k1 + j1,
                    j1);
        }
        net.minecraft.client.renderer.texture.TextureUtil.missingTexture.updateDynamicTexture();
        net.minecraft.client.renderer.texture.TextureUtil.mipmapBuffer = new int[4];
    }

    public static void allocateTextureImpl(int i, int j, int k, int l) {
        Class<TextureUtil> oclass = TextureUtil.class;
        if (Reflector.IK.exists()) {
            oclass = Reflector.IK.getTargetClass();
        }

        synchronized (oclass) {
            deleteTexture(i);
            bindTexture(i);
        }

        if (j >= 0) {
            GL11.glTexParameteri(3553, 33085, j);
            GL11.glTexParameterf(3553, 33082, 0.0F);
            GL11.glTexParameterf(3553, 33083, j);
            GL11.glTexParameterf(3553, 34049, 0.0F);
        }

        for (int i1 = 0; i1 <= j; i1++) {
            GL11.glTexImage2D(3553, i1, 6408, k >> i1, l >> i1, 0, 32993, 33639, (IntBuffer) null);
        }
    }

    private static void copyToBufferPos(int[] p171_0, int p171_1, int p171_2) {
        int[] aint1 = p171_0;
        if (net.minecraft.client.MinecraftClient.getMinecraft().gameSettings.anaglyph) {
            aint1 = net.minecraft.client.renderer.texture.TextureUtil.updateAnaglyph(p171_0);
        }

        ((java.nio.Buffer) net.minecraft.client.renderer.texture.TextureUtil.dataBuffer).clear();
        net.minecraft.client.renderer.texture.TextureUtil.dataBuffer.put(aint1, p171_1, p171_2);
        ((java.nio.Buffer) net.minecraft.client.renderer.texture.TextureUtil.dataBuffer)
                .position(0)
                .limit(p171_2);
    }

    private static void setTextureBlurred(boolean flag) {
        setTextureBlurMipmap(flag, false);
    }

    static void bindTexture(int i) {
        GlStateManager.bindTexture(i);
    }

    private static int blendColors(int p171_0, int p171_1, int p171_2, int p171_3, boolean p171_4) {
        return optifine.Mipmaps.alphaBlend(p171_0, p171_1, p171_2, p171_3);
    }

    public static int[][] generateMipmapData(int i, int j, int[][] aint) {
        int k2 = TextureManager.method_beea3e16fe3ec0995eb9();
        int[][] aint1 = new int[i + 1][];
        int k = k2;
        aint1[0] = aint[0];
        if (i > 0) {
            byte b0 = 0;
            int l = 0;

            while (l < aint.length) {
                if (aint[0][l] >> 24 == 0) {
                    b0 = 1;
                    if (k == 0) {
                        break;
                    }
                }

                l++;
                if (k != 0) {
                    break;
                }
            }

            l = 1;

            while (l <= i) {
                label61:
                {
                    if (aint[l] != null) {
                        aint1[l] = aint[l];
                        if (k == 0) {
                            break label61;
                        }
                    }

                    int[] aint2 = aint1[l - 1];
                    int[] aint3 = new int[aint2.length >> 2];
                    int i1 = j >> l;
                    int j1 = aint3.length / i1;
                    int k1 = i1 << 1;
                    int l1 = 0;

                    label41:
                    while (l1 < i1) {
                        int i2 = 0;

                        while (true) {
                            if (i2 < j1) {
                                int j2 = 2 * (l1 + i2 * k1);
                                aint3[l1 + i2 * i1] =
                                        blendColors(
                                                aint2[j2 + 0],
                                                aint2[j2 + 1],
                                                aint2[j2 + 0 + k1],
                                                aint2[j2 + 1 + k1],
                                                remix.compat.compiler.JvmBooleanSlots.fromInt(b0));
                                i2++;
                                if (k == 0) {
                                    continue;
                                }
                            }

                            l1++;
                            if (k != 0) {
                                break label41;
                            }
                            break;
                        }
                    }

                    aint1[l] = aint3;
                }

                l++;
                if (k != 0) {
                    break;
                }
            }
        }

        return aint1;
    }

    public static int uploadTextureImage(int p171_0, BufferedImage bufferedImage) {
        return net.minecraft.client.renderer.texture.TextureUtil.uploadTextureImageAllocate(
                p171_0, bufferedImage, false, false);
    }
}
