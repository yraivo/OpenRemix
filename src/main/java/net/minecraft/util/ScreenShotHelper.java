package net.minecraft.util;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEventAction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class ScreenShotHelper {
    private static String[] packedTextFragments;
    private static Logger logger;

    private static int[] pixelValues;
    private static DateFormat dateFormat;
    private static IntBuffer pixelBuffer;

    private static File getTimestampedPNGFileForDirectory(File file1) {
        String text = net.minecraft.util.ScreenShotHelper.dateFormat.format(new Date()).toString();
        int i = 1;

        while (true) {
            File file2 =
                    new File(
                            file1,
                            text
                                    + (i == 1 ? packedTextFragments[0] : packedTextFragments[1] + i)
                                    + ".png");
            if (!file2.exists()) {
                return file2;
            }

            i++;
        }
    }

    public static ChatComponent saveScreenshot(
            File file1, String text, int i, int j, Framebuffer framebuffer) {
        String textValue = Vec3i.getLegacyCoordinateFlowMarker();

        try {
            File file2 = new File(file1, "screenshots");
            file2.mkdir();
            if (OpenGlHelper.isFramebufferEnabled()) {
                i = framebuffer.framebufferTextureWidth;
                j = framebuffer.framebufferTextureHeight;
            }

            int k = i * j;
            if (net.minecraft.util.ScreenShotHelper.pixelBuffer == null || net.minecraft.util.ScreenShotHelper.pixelBuffer.capacity() < k) {
                net.minecraft.util.ScreenShotHelper.pixelBuffer = BufferUtils.createIntBuffer(k);
                net.minecraft.util.ScreenShotHelper.pixelValues = new int[k];
            }

            {
                GL11.glPixelStorei(3333, 1);
                GL11.glPixelStorei(3317, 1);
                ((Buffer) net.minecraft.util.ScreenShotHelper.pixelBuffer).clear();
                if (OpenGlHelper.isFramebufferEnabled()) {
                    GlStateManager.bindTexture(framebuffer.framebufferTexture);
                    GL11.glGetTexImage(3553, 0, 32993, 33639, net.minecraft.util.ScreenShotHelper.pixelBuffer);
                    if (!(textValue != null)) {

                        GL11.glReadPixels(0, 0, i, j, 32993, 33639, net.minecraft.util.ScreenShotHelper.pixelBuffer);
                    }
                } else {

                    GL11.glReadPixels(0, 0, i, j, 32993, 33639, net.minecraft.util.ScreenShotHelper.pixelBuffer);
                }
            }

            net.minecraft.util.ScreenShotHelper.pixelBuffer.get(net.minecraft.util.ScreenShotHelper.pixelValues);
            TextureUtil.processPixelValues(net.minecraft.util.ScreenShotHelper.pixelValues, i, j);
            BufferedImage bufferedimage = null;
            if (OpenGlHelper.isFramebufferEnabled()) {
                bufferedimage =
                        new BufferedImage(
                                framebuffer.framebufferWidth, framebuffer.framebufferHeight, 1);
                int l = framebuffer.framebufferTextureHeight - framebuffer.framebufferHeight;
                int i1 = l;

                while (i1 < framebuffer.framebufferTextureHeight) {
                    int j1 = 0;

                    while (j1 < framebuffer.framebufferWidth) {
                        bufferedimage.setRGB(j1, i1 - l, net.minecraft.util.ScreenShotHelper.pixelValues[i1 * framebuffer.framebufferTextureWidth + j1]);
                        j1++;
                        if (textValue == null) {
                            break;
                        }
                    }

                    i1++;
                    if (textValue == null) {
                        break;
                    }
                }
            } else {
                bufferedimage = new BufferedImage(i, j, 1);
                bufferedimage.setRGB(0, 0, i, j, net.minecraft.util.ScreenShotHelper.pixelValues, 0, i);
            }

            File file3;
            {
                if (text == null) {
                    file3 = getTimestampedPNGFileForDirectory(file2);
                    if (!(textValue != null)) {

                        file3 = new File(file2, text);
                    }
                } else {

                    file3 = new File(file2, text);
                }
            }

            ImageIO.write(bufferedimage, "png", file3);
            ChatComponentText chatComponentText = new ChatComponentText(file3.getName());
            chatComponentText
                    .getChatStyle()
                    .setChatClickEvent(
                            new ClickEvent(ClickEventAction.OPEN_FILE, file3.getAbsolutePath()));
            chatComponentText.getChatStyle().setUnderlined(true);
            return new ChatComponentTranslation("screenshot.success", chatComponentText);
        } catch (Exception exception) {
            net.minecraft.util.ScreenShotHelper.logger.warn("Couldn't save screenshot", exception);
            return new ChatComponentTranslation("screenshot.failure", exception.getMessage());
        }
    }

    static {
        net.minecraft.util.ScreenShotHelper.packedTextFragments = new String[4];
        net.minecraft.util.ScreenShotHelper.packedTextFragments[0] = new String("");
        net.minecraft.util.ScreenShotHelper.packedTextFragments[1] = new String("_");
        net.minecraft.util.ScreenShotHelper.logger = LogManager.getLogger();
        net.minecraft.util.ScreenShotHelper.dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public static ChatComponent saveScreenshot(
            File file1, int i, int j, Framebuffer framebuffer) {
        return saveScreenshot(file1, (String) null, i, j, framebuffer);
    }
}
