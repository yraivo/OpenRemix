package optifine;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import shadersmod.client.Shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class TextureUtils {
    public static String kK;
    public static String zc;
    public static String DY;
    public static String kG;
    public static String mp;
    public static String ks;
    public static String DZ;
    public static String Ea;
    public static TextureAtlasSprite iconSnow;

    public static String yz;
    public static String Ba;
    public static String f;
    public static String jR;
    public static String sT;
    public static TextureAtlasSprite iconFireLayer1;
    public static TextureAtlasSprite iconGlass;
    public static String b;
    public static TextureAtlasSprite iconFireLayer0;
    public static TextureAtlasSprite iconCompass;
    public static String Eg;
    public static String Eh;
    public static String Ei;
    public static TextureAtlasSprite iconWaterFlow;
    public static TextureAtlasSprite iconGrassSide;
    public static TextureAtlasSprite iconGrassSideOverlay;
    public static String g;
    public static String jT;
    public static String ki;
    public static String Em;
    public static String En;
    public static String Eo;
    public static String vI;
    public static String Ep;
    public static String te;
    public static String Eq;
    public static String yq;
    public static String Er;
    public static String Es;
    public static String oF;
    public static String Et;
    public static TextureAtlasSprite iconPortal;
    public static String c;
    public static TextureAtlasSprite iconGlassPaneTop;
    public static String zd;
    public static String Z;
    public static String Ew;
    public static String Cs;
    public static String Ex;
    public static String Ey;
    public static String yn;
    public static String Ez;
    private static String[] packedTextFragments;
    public static TextureAtlasSprite iconGrassTop;
    public static String jU;
    public static TextureAtlasSprite iconLavaFlow;
    public static String ED;
    public static String Cx;
    public static String d;
    public static String kg;
    public static String e;
    public static TextureAtlasSprite iconGrassSideSnowed;
    public static String yx;
    public static String EF;
    public static String EG;
    public static String za;
    public static String kE;
    public static String EH;
    public static String a;
    public static String EI;
    public static TextureAtlasSprite iconMyceliumTop;
    public static String EK;
    public static String Cv;
    public static String s;
    public static String mC;
    public static TextureAtlasSprite iconLavaStill;
    private static IntBuffer staticBuffer;
    public static String EN;

    public static TextureAtlasSprite iconClock;
    public static TextureAtlasSprite iconMyceliumSide;
    public static TextureAtlasSprite iconWaterStill;
    public static String ER;
    public static String ES;
    public static String ET;
    public static String EU;
    public static String EV;
    public static String EW;
    public static String h;

    public static String fixResourcePath(String text, String textValue) {
        String textValue2 = "assets/minecraft/";
        if (text.startsWith(textValue2)) {
            return text.substring(textValue2.length());
        }

        if (text.startsWith("./")) {
            text = text.substring(2);
            if (!textValue.endsWith(packedTextFragments[0])) {
                textValue = textValue + packedTextFragments[1];
            }

            return textValue + text;
        } else {
            if (text.startsWith("/~")) {
                text = text.substring(1);
            }

            String textValue3 = "mcpatcher/";
            if (text.startsWith("~/")) {
                text = text.substring(2);
                return textValue3 + text;
            } else {
                return text.startsWith(packedTextFragments[2])
                        ? textValue3 + text.substring(1)
                        : text;
            }
        }
    }

    public static void bindTexture(int i) {
        GlStateManager.bindTexture(i);
    }

    public static boolean isPowerOfTwo(int i) {
        int j = MathHelper.roundUpToPowerOfTwo(i);
        return j == i;
    }

    public static int method_46b7d87f71f96e2a0dba(int i) {
        int l = ReflectorMethod.f();
        byte b0 = 1;
        int k = 0;
        int j = l;

        while (b0 < i) {
            b0 *= 2;
            k++;
            if (j != 0) {
                break;
            }
        }

        return k;
    }

    public static ITextureObject getTexture(ResourceLocation resourceLocation) {
        ITextureObject texture = OptiFineConfig.getTextureManager().getTexture(resourceLocation);
        if (texture != null) {
            return texture;
        }

        if (!OptiFineConfig.hasResource(resourceLocation)) {
            return null;
        }

        SimpleTexture simpleTexture = new SimpleTexture(resourceLocation);
        OptiFineConfig.getTextureManager().loadTexture(resourceLocation, simpleTexture);
        return simpleTexture;
    }

    public static void registerResourceListener() {
        IResourceManager iResourceManager = OptiFineConfig.getResourceManager();
        if (iResourceManager instanceof IReloadableResourceManager) {
            IReloadableResourceManager iReloadableResourceManager =
                    (IReloadableResourceManager) iResourceManager;
            TextureResourceReloadListener textureResourceReloadListener =
                    new TextureResourceReloadListener();
            iReloadableResourceManager.registerReloadListener(textureResourceReloadListener);
        }

        TickableTextureAnimations tickableTextureAnimations = new TickableTextureAnimations();
        ResourceLocation resourceLocation = new ResourceLocation("optifine/TickableTextures");
        OptiFineConfig.getTextureManager()
                .loadTickableTexture(resourceLocation, tickableTextureAnimations);
    }

    static {
        optifine.TextureUtils.packedTextFragments = new String[5];
        optifine.TextureUtils.packedTextFragments[0] = new String("/");
        optifine.TextureUtils.packedTextFragments[1] = new String("/");
        optifine.TextureUtils.packedTextFragments[2] = new String("/");
        optifine.TextureUtils.packedTextFragments[3] = new String("");
        EW = "grass_side_snowed";
        Z = "sandstone_bottom";
        ki = "leaves_jungle";
        DZ = "cactus_side";
        EG = "sandstone_top";
        Es = "water_still";
        Eg = "soul_sand";
        yz = "gold_ore";
        Ei = "clock";
        yn = "obsidian";
        mC = "log_big_oak";
        za = "grass_side_overlay";
        ET = "farmland_wet";
        Ew = "compass";
        EF = "glowstone";
        jT = "leaves_spruce";
        kg = "leaves_acacia";
        EV = "portal";
        vI = "log_oak";
        d = "coarse_dirt";
        Ez = "farmland_dry";
        kE = "log_spruce";
        b = "stone";
        Ba = "log_oak_top";
        s = "log_acacia_top";
        h = "bedrock";
        kG = "log_acacia";
        sT = "gravel";
        a = "grass_top";
        En = "redstone_ore";
        Eh = "lava_flow";
        DY = "minecraft:blocks/";
        EU = "diamond_ore";
        EK = "fire_layer_1";
        Et = "coal_ore";
        Eo = "lapis_ore";
        EN = "fire_layer_0";
        ks = "log_birch";
        yq = "mycelium_top";
        f = "stone_slab_side";
        ES = "clay";
        Ep = "leaves_spruce";
        Em = "minecraft:items/";
        jU = "leaves_birch";
        Ey = "glass_pane_top";
        mp = "redstone_lamp_on";
        c = "dirt";
        ER = "redstone_lamp_off";
        yx = "mycelium_side";
        Ex = "leaves_spruce_opaque";
        Er = "water_flow";
        EI = "end_stone";
        Eq = "lava_still";
        jR = "leaves_big_oak";
        oF = "log_big_oak_top";
        zd = "snow";
        Cv = "log_spruce_top";
        g = "stone_slab_top";
        zc = "iron_ore";
        kK = "log_jungle";
        Cs = "log_birch_top";
        EH = "leaves_oak";
        ED = "netherrack";
        Cx = "log_jungle_top";
        e = "grass_side";
        Ea = "glass";
        te = "sand";
        optifine.TextureUtils.staticBuffer = GLAllocation.createDirectIntBuffer(256);
    }

    public static int method_e52c8f2535cd88b2ee66(int i) {
        int l = ReflectorMethod.cR();
        byte b0 = 1;
        int k = 0;
        int j = l;

        while (k < i) {
            b0 *= 2;
            k++;
            if (j == 0) {
                break;
            }
        }

        return b0;
    }

    public static Dimension getImageSize(InputStream inputstream, String text) {
        Iterator imageReadersBySuffix = ImageIO.getImageReadersBySuffix(text);

        while (imageReadersBySuffix.hasNext()) {
            ImageReader imagereader = (ImageReader) imageReadersBySuffix.next();

            Dimension dimension;
            try {
                ImageInputStream imageinputstream = ImageIO.createImageInputStream(inputstream);
                imagereader.setInput(imageinputstream);
                int width = imagereader.getWidth(imagereader.getMinIndex());
                int j = imagereader.getHeight(imagereader.getMinIndex());
                dimension = new Dimension(width, j);
            } catch (IOException ioexception) {
                continue;
            } finally {
                imagereader.dispose();
            }

            return dimension;
        }

        return null;
    }

    public static String getBasePath(String text) {
        int i = text.lastIndexOf(47);
        return i < 0 ? packedTextFragments[3] : text.substring(0, i);
    }

    public static BufferedImage scaleToPowerOfTwo(BufferedImage bufferedimage, int i) {
        if (bufferedimage == null) {
            return bufferedimage;
        }

        int j = bufferedimage.getWidth();
        int k = bufferedimage.getHeight();
        int l = Math.max(j, i);
        l = MathHelper.roundUpToPowerOfTwo(l);
        if (l == j) {
            return bufferedimage;
        }

        int i1 = k * l / j;
        BufferedImage bufferedimage1 = new BufferedImage(l, i1, 2);
        Graphics2D graphics2d = bufferedimage1.createGraphics();
        Object VALUE_INTERPOLATION_NEAREST_NEIGHBOR =
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        if (l % j != 0) {
            VALUE_INTERPOLATION_NEAREST_NEIGHBOR = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
        }

        graphics2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION, VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        graphics2d.drawImage(bufferedimage, 0, 0, l, i1, (ImageObserver) null);
        return bufferedimage1;
    }

    public static BufferedImage fixTextureDimensions(String text, BufferedImage bufferedimage) {
        if (text.startsWith("/mob/zombie") || text.startsWith("/mob/pigzombie")) {
            int width = bufferedimage.getWidth();
            int j = bufferedimage.getHeight();
            if (width == j * 2) {
                BufferedImage bufferedimage1 = new BufferedImage(width, j * 2, 2);
                Graphics2D graphics2d = bufferedimage1.createGraphics();
                graphics2d.setRenderingHint(
                        RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                graphics2d.drawImage(bufferedimage, 0, 0, width, j, (ImageObserver) null);
                return bufferedimage1;
            }
        }

        return bufferedimage;
    }

    public static BufferedImage scaleMinTo(BufferedImage bufferedimage, int i) {
        int j = ReflectorMethod.f();
        if (bufferedimage == null) {
            return bufferedimage;
        }

        int k = bufferedimage.getWidth();
        int l = bufferedimage.getHeight();
        if (k >= i) {
            return bufferedimage;
        }

        int i1 = k;

        while (i1 < i) {
            i1 *= 2;
            if (j != 0) {
                break;
            }
        }

        int j1 = l * i1 / k;
        BufferedImage bufferedimage1 = new BufferedImage(i1, j1, 2);
        Graphics2D graphics2d = bufferedimage1.createGraphics();
        Object VALUE_INTERPOLATION_NEAREST_NEIGHBOR =
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
        graphics2d.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION, VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        graphics2d.drawImage(bufferedimage, 0, 0, i1, j1, (ImageObserver) null);
        return bufferedimage1;
    }

    public static void update() {
        TextureMap textureMap = getTextureMapBlocks();
        if (textureMap != null) {
            String sx = "minecraft:blocks/";
            optifine.TextureUtils.iconGrassTop = textureMap.getSpriteSafe(sx + "grass_top");
            optifine.TextureUtils.iconGrassSide = textureMap.getSpriteSafe(sx + "grass_side");
            optifine.TextureUtils.iconGrassSideOverlay =
                    textureMap.getSpriteSafe(sx + "grass_side_overlay");
            optifine.TextureUtils.iconSnow = textureMap.getSpriteSafe(sx + "snow");
            optifine.TextureUtils.iconGrassSideSnowed =
                    textureMap.getSpriteSafe(sx + "grass_side_snowed");
            optifine.TextureUtils.iconMyceliumSide = textureMap.getSpriteSafe(sx + "mycelium_side");
            optifine.TextureUtils.iconMyceliumTop = textureMap.getSpriteSafe(sx + "mycelium_top");
            optifine.TextureUtils.iconWaterStill = textureMap.getSpriteSafe(sx + "water_still");
            optifine.TextureUtils.iconWaterFlow = textureMap.getSpriteSafe(sx + "water_flow");
            optifine.TextureUtils.iconLavaStill = textureMap.getSpriteSafe(sx + "lava_still");
            optifine.TextureUtils.iconLavaFlow = textureMap.getSpriteSafe(sx + "lava_flow");
            optifine.TextureUtils.iconFireLayer0 = textureMap.getSpriteSafe(sx + "fire_layer_0");
            optifine.TextureUtils.iconFireLayer1 = textureMap.getSpriteSafe(sx + "fire_layer_1");
            optifine.TextureUtils.iconPortal = textureMap.getSpriteSafe(sx + "portal");
            optifine.TextureUtils.iconGlass = textureMap.getSpriteSafe(sx + "glass");
            optifine.TextureUtils.iconGlassPaneTop = textureMap.getSpriteSafe(sx + "glass_pane_top");
            String text = "minecraft:items/";
            optifine.TextureUtils.iconCompass = textureMap.getSpriteSafe(text + "compass");
            optifine.TextureUtils.iconClock = textureMap.getSpriteSafe(text + "clock");
        }
    }

    public static TextureMap getTextureMapBlocks() {
        return MinecraftClient.getMinecraft().getTextureMapBlocks();
    }

    public static void applyAnisotropicLevel() {
        if (GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
            float fx = GL11.glGetFloat(34047);
            float anisotropicFilterLevel = OptiFineConfig.getAnisotropicFilterLevel();
            anisotropicFilterLevel = Math.min(anisotropicFilterLevel, fx);
            GL11.glTexParameterf(3553, 34046, anisotropicFilterLevel);
        }
    }

    public static int method_c640c0ab3979b883b99e(int i) {
        int k = ReflectorMethod.f();
        byte b0 = 1;
        int j = k;

        while (b0 < i) {
            b0 *= 2;
            if (j != 0) {
                break;
            }
        }

        return b0;
    }

    public static void resourcesReloaded(IResourceManager iResourceManager) {
        if (getTextureMapBlocks() != null) {
            OptiFineConfig.dbg("*** Reloading custom textures ***");
            CustomSky.reset();
            TextureAnimations.reset();
            update();
            NaturalTextures.update();
            BetterGrass.update();
            BetterSnow.update();
            TextureAnimations.update();
            CustomColors.update();
            CustomSky.update();
            RandomMobs.resetTextures();
            CustomItems.updateModels();
            Shaders.resourcesReloaded();
            Lang.resourcesReloaded();
            OptiFineConfig.updateTexturePackClouds();
            SmartLeaves.updateLeavesModels();
            OptiFineConfig.getTextureManager().tick();
        }
    }
}
