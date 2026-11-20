package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LayeredTexture extends AbstractTexture {
    public List layeredTextureNames;

    private static Logger logger;
    private static String te;

    // region String decoding support

    // endregion

    public LayeredTexture(String... astring) {
        this.layeredTextureNames = Lists.newArrayList(astring);
    }

    // region String decoding support

    // endregion

    static {
        te = "Couldn't load layered image";
        net.minecraft.client.renderer.texture.LayeredTexture.logger = LogManager.getLogger();
    }

    public void loadTexture(IResourceManager iResourceManager) throws IOException {
        int j = TextureManager.method_beea3e16fe3ec0995eb9();
        this.cj();
        int i = j;
        BufferedImage bufferedimage = null;

        try {
            for (java.util.Iterator<?> __recoveryIterator171_3934 =
                            (this.layeredTextureNames).iterator();
                    (__recoveryIterator171_3934).hasNext(); ) {
                String text = (String) (__recoveryIterator171_3934).next();
                if (text != null) {
                    InputStream inputstream =
                            iResourceManager
                                    .getResource(new ResourceLocation(text))
                                    .getInputStream();
                    BufferedImage bufferedimage1 =
                            TextureUtil.readBufferedImage(inputstream);
                    if (bufferedimage == null) {
                        bufferedimage =
                                new BufferedImage(
                                        bufferedimage1.getWidth(), bufferedimage1.getHeight(), 2);
                    }

                    bufferedimage
                            .getGraphics()
                            .drawImage(bufferedimage1, 0, 0, (ImageObserver) null);
                }

                if (i != 0) {
                    break;
                }
            }
        } catch (IOException ioexception) {
            net.minecraft.client.renderer.texture.LayeredTexture.logger.error(te, ioexception);
            return;
        }

        TextureUtil.uploadTextureImage(this.getGlTextureId(), bufferedimage);
    }
}
