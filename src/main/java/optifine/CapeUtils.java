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

    public static BufferedImage parseCape(BufferedImage capeImage) {
        int width = 64;
        int height = 32;
        int capeWidth = capeImage.getWidth();
        int capeHeight = capeImage.getHeight();
        while (width < capeWidth || height < capeHeight) {
            width = Math.multiplyExact(width, 2);
            height = Math.multiplyExact(height, 2);
        }

        BufferedImage texture = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = texture.getGraphics();
        graphics.drawImage(capeImage, 0, 0, null);
        graphics.dispose();
        return texture;
    }
}
