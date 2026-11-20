package optifine;

import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import javax.imageio.ImageIO;

public class TextureAnimations {

    private static String[] packedTextFragments;
    private static TextureAnimation[] textureAnimations;

    public static TextureAnimation makeTextureAnimation(
            Properties properties, ResourceLocation resourceLocation) {
        String property = properties.getProperty("from");
        String propertyValue = properties.getProperty("to");
        int i = OptiFineConfig.parseInt(properties.getProperty(packedTextFragments[0]), -1);
        int j = OptiFineConfig.parseInt(properties.getProperty(packedTextFragments[1]), -1);
        int k = OptiFineConfig.parseInt(properties.getProperty(packedTextFragments[2]), -1);
        int l = OptiFineConfig.parseInt(properties.getProperty(packedTextFragments[3]), -1);
        if (property == null || propertyValue == null) {
            OptiFineConfig.warn("TextureAnimation: Source or target texture not specified");
            return null;
        }

        if (i >= 0 && j >= 0 && k >= 0 && l >= 0) {
            property = property.trim();
            propertyValue = propertyValue.trim();
            String text = TextureUtils.getBasePath(resourceLocation.getResourcePath());
            property = TextureUtils.fixResourcePath(property, text);
            propertyValue = TextureUtils.fixResourcePath(propertyValue, text);
            byte[] abyte = getCustomTextureData(property, k);
            if (abyte == null) {
                OptiFineConfig.warn("TextureAnimation: Source texture not found: " + propertyValue);
                return null;
            } else {
                ResourceLocation resourceLocationValue = new ResourceLocation(propertyValue);
                if (!OptiFineConfig.hasResource(resourceLocationValue)) {
                    OptiFineConfig.warn(
                            "TextureAnimation: Target texture not found: " + propertyValue);
                    return null;
                } else {
                    return new TextureAnimation(
                            property,
                            abyte,
                            propertyValue,
                            resourceLocationValue,
                            i,
                            j,
                            k,
                            l,
                            properties,
                            1);
                }
            }
        } else {
            OptiFineConfig.warn("TextureAnimation: Invalid coordinates");
            return null;
        }
    }

    public static void updateCustomAnimations() {
        if (optifine.TextureAnimations.textureAnimations != null
                && OptiFineConfig.isAnimatedTextures()) {
            updateAnimations();
        }
    }

    public static void updateAnimations() {
        int i = ReflectorMethod.f();
        if (optifine.TextureAnimations.textureAnimations != null) {
            int j = 0;

            while (j < optifine.TextureAnimations.textureAnimations.length) {
                TextureAnimation textureAnimation = optifine.TextureAnimations.textureAnimations[j];
                textureAnimation.updateTexture();
                j++;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    private static byte[] loadImage(String text, int i) {
        int j3 = ReflectorMethod.f();
        GameSettings gameSettings = OptiFineConfig.getGameSettings();
        int j = j3;

        try {
            ResourceLocation resourceLocation = new ResourceLocation(text);
            InputStream inputstream = OptiFineConfig.getResourceStream(resourceLocation);
            if (inputstream == null) {
                return null;
            }

            BufferedImage bufferedimage = readTextureImage(inputstream);
            inputstream.close();
            if (bufferedimage == null) {
                return null;
            }

            if (i > 0 && bufferedimage.getWidth() != i) {
                double d0 = bufferedimage.getHeight() / bufferedimage.getWidth();
                int l = (int) (i * d0);
                bufferedimage = scaleBufferedImage(bufferedimage, i, l);
            }

            int width = bufferedimage.getWidth();
            int k = bufferedimage.getHeight();
            int[] aint = new int[width * k];
            byte[] abyte = new byte[width * k * 4];
            bufferedimage.getRGB(0, 0, width, k, aint, 0, width);
            int i1 = 0;

            while (i1 < aint.length) {
                int j1 = aint[i1] >> 24 & 0xFF;
                int k1 = aint[i1] >> 16 & 0xFF;
                int l1 = aint[i1] >> 8 & 0xFF;
                int i2 = aint[i1] & 0xFF;
                if (gameSettings != null && gameSettings.anaglyph) {
                    int j2 = (k1 * 30 + l1 * 59 + i2 * 11) / 100;
                    int k2 = (k1 * 30 + l1 * 70) / 100;
                    int l2 = (k1 * 30 + i2 * 70) / 100;
                    k1 = j2;
                    l1 = k2;
                    i2 = l2;
                }

                abyte[i1 * 4 + 0] = (byte) k1;
                abyte[i1 * 4 + 1] = (byte) l1;
                abyte[i1 * 4 + 2] = (byte) i2;
                abyte[i1 * 4 + 3] = (byte) j1;
                i1++;
                if (j != 0) {
                    break;
                }
            }

            return abyte;
        } catch (FileNotFoundException filenotfoundexception) {
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    static {
        optifine.TextureAnimations.packedTextFragments = new String[6];
        optifine.TextureAnimations.packedTextFragments[0] = new String("x");
        optifine.TextureAnimations.packedTextFragments[1] = new String("y");
        optifine.TextureAnimations.packedTextFragments[2] = new String("w");
        optifine.TextureAnimations.packedTextFragments[3] = new String("h");
        optifine.TextureAnimations.textureAnimations = null;
    }

    private static FileNotFoundException method_fd3f0eb1dd2c178d63ce(
            FileNotFoundException filenotfoundexception) {
        return filenotfoundexception;
    }

    public static BufferedImage scaleBufferedImage(BufferedImage bufferedimage, int i, int j) {
        BufferedImage bufferedimage1 = new BufferedImage(i, j, 2);
        Graphics2D graphics2d = bufferedimage1.createGraphics();
        graphics2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.drawImage(bufferedimage, 0, 0, i, j, (ImageObserver) null);
        return bufferedimage1;
    }

    public static TextureAnimation[] getTextureAnimations(IResourcePack[] iResourcePackArray) {
        int k = ReflectorMethod.f();
        ArrayList arrayList = new ArrayList();
        int j = 0;
        int i = k;

        while (j < iResourcePackArray.length) {
            IResourcePack iResourcePack = iResourcePackArray[j];
            TextureAnimation[] textureAnimationArray = getTextureAnimations(iResourcePack);
            if (textureAnimationArray != null) {
                arrayList.addAll(Arrays.asList(textureAnimationArray));
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return ((optifine.TextureAnimation[])
                arrayList.toArray(new TextureAnimation[arrayList.size()]));
    }

    private static BufferedImage readTextureImage(InputStream inputstream)
            throws IOException {
        BufferedImage bufferedimage = ImageIO.read(inputstream);
        inputstream.close();
        return bufferedimage;
    }

    public static void update() {
        optifine.TextureAnimations.textureAnimations = null;
        IResourcePack[] iResourcePackArray = OptiFineConfig.getResourcePacks();
        optifine.TextureAnimations.textureAnimations = getTextureAnimations(iResourcePackArray);
        if (OptiFineConfig.isAnimatedTextures()) {
            updateAnimations();
        }
    }

    public static byte[] getCustomTextureData(String text, int i) {
        byte[] abyte = loadImage(text, i);
        if (abyte == null) {
            abyte = loadImage("/anim" + text, i);
        }

        return abyte;
    }

    public static TextureAnimation[] getTextureAnimations(IResourcePack iResourcePack) {
        int k = ReflectorMethod.cR();
        String[] astring =
                ResUtils.collectFiles(
                        iResourcePack, "mcpatcher/anim", ".properties", (String[]) null);
        int i = k;
        if (astring.length <= 0) {
            return null;
        }

        ArrayList arrayList = new ArrayList();
        int j = 0;

        while (j < astring.length) {
            String text = astring[j];
            OptiFineConfig.dbg("Texture animation: " + text);

            try {
                ResourceLocation resourceLocation = new ResourceLocation(text);
                InputStream inputstream = iResourcePack.getInputStream(resourceLocation);
                Properties properties = new Properties();
                properties.load(inputstream);
                TextureAnimation textureAnimation =
                        makeTextureAnimation(properties, resourceLocation);
                if (textureAnimation != null) {
                    ResourceLocation resourceLocationValue =
                            new ResourceLocation(textureAnimation.fg());
                    if (OptiFineConfig.getDefiningResourcePack(resourceLocationValue)
                            != iResourcePack) {
                        OptiFineConfig.dbg(
                                "Skipped: "
                                        + text
                                        + ", target texture not loaded from same resource pack");
                        if (!(i != 0)) {

                            arrayList.add(textureAnimation);
                        }
                    } else {

                        arrayList.add(textureAnimation);
                    }
                }
            } catch (FileNotFoundException filenotfoundexception) {
                OptiFineConfig.warn("File not found: " + filenotfoundexception.getMessage());
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return ((optifine.TextureAnimation[])
                arrayList.toArray(new TextureAnimation[arrayList.size()]));
    }

    public static void reset() {
        optifine.TextureAnimations.textureAnimations = null;
    }
}
