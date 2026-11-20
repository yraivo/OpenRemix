package optifine;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.FilenameUtils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

public class CapeUtils {


    public static void downloadCape(
            AbstractClientPlayer abstractClientPlayer) {
        String text = abstractClientPlayer.getNameClear();
        if (text != null && !text.isEmpty()) {
            String textValue = "http://s.optifine.net/capes/" + text + ".png";
            String baseName = FilenameUtils.getBaseName(textValue);
            ResourceLocation resourceLocation = new ResourceLocation("capeof/" + baseName);
            TextureManager textureManager = MinecraftClient.getMinecraft().getTextureManager();
            ITextureObject texture = textureManager.getTexture(resourceLocation);
            if (texture != null && texture instanceof ThreadDownloadImageData) {
                ThreadDownloadImageData threadDownloadImageData = (ThreadDownloadImageData) texture;
                if (threadDownloadImageData.imageFound != null) {
                    if (threadDownloadImageData.imageFound) {
                        abstractClientPlayer.setLocationOfCape(resourceLocation);
                    }

                    return;
                }
            }

            CapeImageBuffer capeImageBuffer =
                    new CapeImageBuffer(abstractClientPlayer, resourceLocation);
            ThreadDownloadImageData threadDownloadImageDataValue =
                    new ThreadDownloadImageData(
                            (File) null,
                            textValue,
                            (ResourceLocation) null,
                            capeImageBuffer);
            threadDownloadImageDataValue.pipeline = true;
            textureManager.loadTexture(resourceLocation, threadDownloadImageDataValue);
        }
    }

    public static BufferedImage parseCape(BufferedImage bufferedimage) {
        byte b0 = 64;
        int l = ReflectorMethod.f();
        byte b1 = 32;
        int j = bufferedimage.getWidth();
        int k = bufferedimage.getHeight();
        int i = l;

        while (b0 < j || b1 < k) {
            b0 *= 2;
            b1 *= 2;
            if (i != 0) {
                break;
            }
        }

        BufferedImage bufferedimage1 = new BufferedImage(b0, b1, 2);
        Graphics graphics = bufferedimage1.getGraphics();
        graphics.drawImage(bufferedimage, 0, 0, null);
        graphics.dispose();
        return bufferedimage1;
    }
}
