package net.minecraft.client.renderer.texture;

import net.minecraft.block.material.MapColor;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LayeredColorMaskTexture extends AbstractTexture {
    private List iy;
    private static String vI;

    private List tX;
    private static Logger LOG;
    private ResourceLocation textureLocation;

    static {
        vI = "Couldn't load layered image";
        net.minecraft.client.renderer.texture.LayeredColorMaskTexture.LOG = LogManager.getLogger();
    }

    // region String decoding support

    // endregion

    public LayeredColorMaskTexture(
            ResourceLocation resourceLocation, List list, List listValue) {
        this.textureLocation = resourceLocation;
        this.iy = list;
        this.tX = listValue;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void loadTexture(IResourceManager iResourceManager) throws IOException {
        int j2 = TextureManager.method_beea3e16fe3ec0995eb9();
        this.cj();
        int i = j2;

        BufferedImage bufferedimage;
        try {
            BufferedImage bufferedimage1 =
                    TextureUtil.readBufferedImage(
                            iResourceManager.getResource(this.textureLocation).getInputStream());
            int j = bufferedimage1.getType();
            if (j == 0) {
                j = 6;
            }

            bufferedimage =
                    new BufferedImage(bufferedimage1.getWidth(), bufferedimage1.getHeight(), j);
            Graphics graphics = bufferedimage.getGraphics();
            graphics.drawImage(bufferedimage1, 0, 0, (ImageObserver) null);
            int k = 0;

            while (k < this.iy.size() && k < this.tX.size()) {
                String text = (String) this.iy.get(k);
                MapColor mapColor = ((EnumDyeColor) this.tX.get(k)).getMapColor();
                if (text != null) {
                    InputStream inputstream =
                            iResourceManager
                                    .getResource(new ResourceLocation(text))
                                    .getInputStream();
                    BufferedImage bufferedimage2 =
                            TextureUtil.readBufferedImage(inputstream);
                    if (bufferedimage2.getWidth() == bufferedimage.getWidth()
                            && bufferedimage2.getHeight() == bufferedimage.getHeight()
                            && bufferedimage2.getType() == 6) {
                        int l = 0;

                        label60:
                        while (l < bufferedimage2.getHeight()) {
                            int i1 = 0;

                            while (true) {
                                if (i1 < bufferedimage2.getWidth()) {
                                    int j1 = bufferedimage2.getRGB(i1, l);
                                    if ((j1 & 0xFF000000) != 0) {
                                        int k1 = (j1 & 0xFF0000) << 8 & 0xFF000000;
                                        int l1 = bufferedimage1.getRGB(i1, l);
                                        int i2 =
                                                MathHelper.multiplyColor(l1, mapColor.colorValue)
                                                        & 16777215;
                                        bufferedimage2.setRGB(i1, l, k1 | i2);
                                    }

                                    i1++;
                                    if (i == 0) {
                                        continue;
                                    }
                                }

                                l++;
                                if (i != 0) {
                                    break label60;
                                }
                                break;
                            }
                        }

                        bufferedimage
                                .getGraphics()
                                .drawImage(bufferedimage2, 0, 0, (ImageObserver) null);
                    }
                }

                k++;
                if (i != 0) {
                    break;
                }
            }
        } catch (IOException ioexception) {
            net.minecraft.client.renderer.texture.LayeredColorMaskTexture.LOG.error(vI, ioexception);
            return;
        }

        TextureUtil.uploadTextureImage(this.getGlTextureId(), bufferedimage);
    }

    // region String decoding support

    // endregion
}
