package optifine;

import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.ResourcePackRepositoryEntry;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

import org.apache.commons.io.IOUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

import shadersmod.client.Shaders;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class OptiFineConfig {
    private static MinecraftClient minecraft;
    private static String newRelease;
    public static boolean zoomMode;

    public static Float DEF_ALPHA_FUNC_LEVEL;
    public static String openGlVersion;
    private static boolean desktopModeChecked;
    private static String[] packedTextFragments;
    private static boolean notify64BitJava;
    private static PrintStream BL;
    public static String d;
    private static boolean fullscreenModeChecked;
    private static int availableProcessors;
    public static boolean waterOpacityChanged;
    private static DefaultResourcePack defaultResourcePackLazy;
    public static String b;
    private static Thread minecraftThread;

    private static boolean initialized;
    public static String c;
    private static ModelManager BO;
    public static boolean occlusionAvailable;
    public static String e;
    private static DisplayMode desktopDisplayMode;
    public static String openGlVendor;
    public static String a;
    public static String openGlRenderer;
    private static int antialiasingLevel;
    private static int texturePackClouds;
    public static boolean fancyFogAvailable;
    private static GameSettings gameSettings;

    public static InputStream getResourceStream(ResourceLocation resourceLocation)
            throws IOException {
        return getResourceStream(
                optifine.OptiFineConfig.minecraft.getResourceManager(), resourceLocation);
    }

    public static boolean isFromDefaultResourcePack(ResourceLocation resourceLocation) {
        IResourcePack iResourcePack = getDefiningResourcePack(resourceLocation);
        return iResourcePack == getDefaultResourcePack();
    }

    public static double limit(double p171_0, double p171_1, double p171_2) {
        try {
            if (p171_0 < p171_1) {
                return p171_1;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception1));
        }

        try {
            if (p171_0 > p171_2) {
                return p171_2;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return p171_0;
    }

    public static void iG() {
        MinecraftClient minecraftClient = optifine.OptiFineConfig.minecraft;
        int debugFPS = MinecraftClient.getDebugFPS();
        String text = getUpdates(optifine.OptiFineConfig.minecraft.debug);
        int j = optifine.OptiFineConfig.minecraft.renderGlobal.getCountActiveRenderers();
        int k = optifine.OptiFineConfig.minecraft.renderGlobal.getRenderedEntityCount();
        int l = optifine.OptiFineConfig.minecraft.renderGlobal.getRenderedTileEntityCount();
        String textValue =
                packedTextFragments[23]
                        + debugFPS
                        + " fps, C: "
                        + j
                        + ", E: "
                        + k
                        + packedTextFragments[24]
                        + l
                        + ", U: "
                        + text;
        optifine.OptiFineConfig.minecraft.fontRendererObj.drawString(textValue, 2, 2, -2039584);
    }

    public static void log(String text) {
        dbg(text);
    }

    public static void checkInitialized() {
        if (!optifine.OptiFineConfig.initialized && org.lwjgl.opengl.Display.isCreated()) {
            optifine.OptiFineConfig.initialized = true;
            optifine.OptiFineConfig.checkOpenGlCaps();
            optifine.OptiFineConfig.iJ();
        }
    }

    public static boolean isVoidParticles() {
        return optifine.OptiFineConfig.gameSettings.ofVoidParticles;
    }

    public static boolean isAnimatedTerrain() {
        return optifine.OptiFineConfig.gameSettings.ofAnimatedTerrain;
    }

    public static String readInputStream(InputStream inputStream, String text) throws IOException {
        java.io.InputStreamReader inputstreamreader =
                new java.io.InputStreamReader(inputStream, text);
        java.io.BufferedReader bufferedreader = new java.io.BufferedReader(inputstreamreader);
        java.lang.StringBuffer stringbuffer = new java.lang.StringBuffer();

        while (true) {
            java.lang.String textValue = bufferedreader.readLine();

            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Checked exception carried by the original JVM handler.
                if (textValue == null) {
                    return stringbuffer.toString();
                }
            } catch (java.io.IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        optifine.OptiFineConfig.method_b7e52da193241b30710d(ioexception));
            }

            stringbuffer.append(textValue);
            stringbuffer.append(optifine.OptiFineConfig.packedTextFragments[14]);
        }
    }

    private static void iJ() {
        VersionCheckThread versionCheckThread = new VersionCheckThread();
        versionCheckThread.start();
    }

    public static IResourceManager getResourceManager() {
        return optifine.OptiFineConfig.minecraft.getResourceManager();
    }

    public static boolean isDynamicHandLight() {
        try {
            if (!optifine.OptiFineConfig.isDynamicLights()) {
                return false;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception1));
        }

        try {
            if (optifine.OptiFineConfig.isShaders()) {
                return shadersmod.client.Shaders.isDynamicHandLight();
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return true;
    }

    private static void setThreadPriority(String text, int i) {
        int j = ReflectorMethod.cR();

        try {
            ThreadGroup threadgroup = Thread.currentThread().getThreadGroup();
            if (threadgroup == null) {
                return;
            }

            int k = (threadgroup.activeCount() + 10) * 2;
            Thread[] athread = new Thread[k];
            threadgroup.enumerate(athread, false);
            int l = 0;

            while (l < athread.length) {
                Thread thread = athread[l];
                if (thread != null && thread.getName().startsWith(text)) {
                    thread.setPriority(i);
                }

                l++;
                if (j == 0) {
                    break;
                }
            }
        } catch (Throwable throwable) {
            warn(throwable.getClass().getName() + ": " + throwable.getMessage());
        }
    }

    public static boolean isSkyEnabled() {
        return optifine.OptiFineConfig.gameSettings.ofSky;
    }

    public static float getAlphaFuncLevel() {
        return optifine.OptiFineConfig.DEF_ALPHA_FUNC_LEVEL;
    }

    public static TextureManager getTextureManager() {
        return optifine.OptiFineConfig.minecraft.getTextureManager();
    }

    public static String arrayToString(int[] aint) {
        int i = ReflectorMethod.f();
        if (aint == null) {
            return packedTextFragments[9];
        }

        StringBuffer stringbuffer = new StringBuffer(aint.length * 5);
        int j = 0;

        while (j < aint.length) {
            int k = aint[j];
            if (j > 0) {
                stringbuffer.append(", ");
            }

            stringbuffer.append(String.valueOf(k));
            j++;
            if (i != 0) {
                break;
            }
        }

        return stringbuffer.toString();
    }

    public static String method_02221406e7cb9480ea0a(String text, int i, char c0) {
        int j = ReflectorMethod.cR();
        if (text == null) {
            text = packedTextFragments[27];
        }

        if (text.length() >= i) {
            return text;
        }

        StringBuffer stringbuffer = new StringBuffer(text);

        while (stringbuffer.length() < i - text.length()) {
            stringbuffer.append(c0);
            if (j == 0) {
                break;
            }
        }

        return stringbuffer.toString() + text;
    }

    public static boolean W_boolean() {
        try {
            if (optifine.OptiFineConfig.gameSettings.ofAnimatedWater != 2) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static int getChunkViewDistance() {
        try {
            if (optifine.OptiFineConfig.gameSettings == null) {
                return 10;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return optifine.OptiFineConfig.gameSettings.renderDistanceChunks;
    }

    public static void updateThreadPriorities() {
        int j = ReflectorMethod.f();
        updateAvailableProcessors();
        int i = j;

        if (isSingleProcessor()) {
            if (isSmoothWorld()) {
                optifine.OptiFineConfig.minecraftThread.setPriority(10);
                setThreadPriority("Server thread", 1);
                if (i == 0) {
                    return;
                }
            }

            optifine.OptiFineConfig.minecraftThread.setPriority(5);
            setThreadPriority("Server thread", 5);
            if (i == 0) {
                return;
            }
        }

        optifine.OptiFineConfig.minecraftThread.setPriority(10);
        setThreadPriority("Server thread", 5);
    }

    public static String[] tokenize(String text, String textValue) {
        int j = ReflectorMethod.f();
        StringTokenizer stringtokenizer = new StringTokenizer(text, textValue);
        int i = j;
        ArrayList arrayList = new ArrayList();

        while (stringtokenizer.hasMoreTokens()) {
            String textValue2 = stringtokenizer.nextToken();
            arrayList.add(textValue2);
            if (i != 0) {
                break;
            }
        }

        return ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    public static RenderGlobal getRenderGlobal() {
        try {
            if (optifine.OptiFineConfig.minecraft == null) {
                return null;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return optifine.OptiFineConfig.minecraft.renderGlobal;
    }

    public static boolean isDynamicLightsFast() {
        return optifine.OptiFineConfig.gameSettings.ofDynamicLights == 1;
    }

    public static DisplayMode getDesktopDisplayMode() {
        return optifine.OptiFineConfig.desktopDisplayMode;
    }

    public static boolean isTranslucentBlocksFancy() {
        return optifine.OptiFineConfig.gameSettings.ofTranslucentBlocks == 0
                ? optifine.OptiFineConfig.gameSettings.fancyGraphics
                : optifine.OptiFineConfig.gameSettings.ofTranslucentBlocks == 2;
    }

    public static int getAnisotropicFilterLevel() {
        return optifine.OptiFineConfig.gameSettings.ofAfLevel;
    }

    public static byte[] readAll(InputStream inputStream) throws IOException {
        java.io.ByteArrayOutputStream bytearrayoutputstream = new java.io.ByteArrayOutputStream();
        byte[] abyte = new byte[1024];

        while (true) {
            int i = inputStream.read(abyte);
            if (i < 0) {
                inputStream.close();
                return bytearrayoutputstream.toByteArray();
            }

            bytearrayoutputstream.write(abyte, 0, i);
        }
    }

    public static void initDisplay() {
        optifine.OptiFineConfig.checkInitialized();
        optifine.OptiFineConfig.antialiasingLevel = optifine.OptiFineConfig.gameSettings.ofAaLevel;
        optifine.OptiFineConfig.checkDisplaySettings();
        optifine.OptiFineConfig.checkDisplayMode();
        optifine.OptiFineConfig.minecraftThread = java.lang.Thread.currentThread();
        optifine.OptiFineConfig.updateThreadPriorities();
        shadersmod.client.Shaders.startup(net.minecraft.client.MinecraftClient.getMinecraft());
    }

    public static void checkGlError(String text) {
        int i = GL11.glGetError();
        if (i != 0) {
            String textValue = GLU.gluErrorString(i);
            error("OpenGlError: " + i + " (" + textValue + "), at: " + text);
        }
    }

    public static boolean isAnimatedRedstone() {
        return optifine.OptiFineConfig.gameSettings.ofAnimatedRedstone;
    }

    private static String getBuild() {
        try {
            InputStream inputstream = OptiFineConfig.class.getResourceAsStream("/buildof.txt");
            return inputstream == null ? null : readLines(inputstream)[0];
        } catch (Exception exception) {
            warn(
                    packedTextFragments[2]
                            + exception.getClass().getName()
                            + ": "
                            + exception.getMessage());
            return null;
        }
    }

    public static boolean iX() {
        try {
            if (optifine.OptiFineConfig.gameSettings.ofConnectedTextures == 2) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static int parseInt(String text, int p171_1) {
        try {
            if (text == null) {
                return p171_1;
            }

            text = ((java.lang.String) (((java.lang.String) (text)).trim()));
            return ((int) (java.lang.Integer.parseInt(((java.lang.String) (text)))));
        } catch (java.lang.NumberFormatException numberformatexception) {
            return p171_1;
        }
    }

    public static boolean isPortalParticles() {
        return optifine.OptiFineConfig.gameSettings.ofPortalParticles;
    }

    public static boolean isRainFancy() {
        return optifine.OptiFineConfig.gameSettings.ofRain == 0
                ? optifine.OptiFineConfig.gameSettings.fancyGraphics
                : optifine.OptiFineConfig.gameSettings.ofRain == 2;
    }

    public static boolean ja() {
        return false;
    }

    public static boolean jb() {
        try {
            if (optifine.OptiFineConfig.getGameSettings().ofAaLevel > 0) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static boolean isAnimatedFire() {
        return optifine.OptiFineConfig.gameSettings.ofAnimatedFire;
    }

    public static boolean isCullFacesLeaves() {
        return ((optifine.OptiFineConfig.gameSettings.ofTrees == 0
                ? !optifine.OptiFineConfig.gameSettings.fancyGraphics
                : optifine.OptiFineConfig.gameSettings.ofTrees == 4));
    }

    public static boolean isFancyFogEnabled() {
        try {
            if (!optifine.OptiFineConfig.isFancyFogAvailable()) {
                return false;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception1));
        }

        try {
            if (optifine.OptiFineConfig.gameSettings.ofFogType == 2) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static boolean isPotionParticles() {
        return optifine.OptiFineConfig.gameSettings.ofPotionParticles;
    }

    public static boolean isAnimatedPortal() {
        return optifine.OptiFineConfig.gameSettings.ofAnimatedPortal;
    }

    public static TextureMap getTextureMap() {
        return getMinecraft().getTextureMapBlocks();
    }

    public static float getAmbientOcclusionLevel() {
        return optifine.OptiFineConfig.gameSettings.ofAoLevel;
    }

    public static boolean isAnimatedFlame() {
        return optifine.OptiFineConfig.gameSettings.ofAnimatedFlame;
    }

    public static DisplayMode[] getDisplayModes() {
        int i = ReflectorMethod.cR();

        try {
            DisplayMode[] adisplaymode = Display.getAvailableDisplayModes();
            ArrayList arrayList = new ArrayList();
            int j = 0;

            while (j < adisplaymode.length) {
                DisplayMode displaymode = adisplaymode[j];
                if (optifine.OptiFineConfig.desktopDisplayMode == null
                        || displaymode.getBitsPerPixel()
                                        == optifine.OptiFineConfig.desktopDisplayMode
                                                .getBitsPerPixel()
                                && displaymode.getFrequency()
                                        == optifine.OptiFineConfig.desktopDisplayMode
                                                .getFrequency()) {
                    arrayList.add(displaymode);
                }

                j++;
                if (i == 0) {
                    break;
                }
            }

            DisplayMode[] adisplaymode1 =
                    ((org.lwjgl.opengl.DisplayMode[])
                            arrayList.toArray(new DisplayMode[arrayList.size()]));
            DisplayModeComparator displayModeComparator = new DisplayModeComparator();
            Arrays.sort(adisplaymode1, displayModeComparator);
            return adisplaymode1;
        } catch (Exception exception) {
            exception.printStackTrace();
            return new DisplayMode[] {optifine.OptiFineConfig.desktopDisplayMode};
        }
    }

    public static String getOpenGlVersionString() {
        int i = optifine.OptiFineConfig.cK();
        return optifine.OptiFineConfig.packedTextFragments[3]
                + i / 10
                + optifine.OptiFineConfig.packedTextFragments[4]
                + i % 10;
    }

    public static boolean hasResource(
            IResourceManager iResourceManager, ResourceLocation resourceLocation) {
        try {
            IResource resource = iResourceManager.getResource(resourceLocation);
            return resource != null;
        } catch (IOException ioexception) {
            return false;
        }
    }

    public static String[] readLines(InputStream inputstream) throws IOException {
        ArrayList arrayList = new ArrayList();
        InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "ASCII");
        BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

        while (true) {
            String text = bufferedreader.readLine();
            if (text == null) {
                return ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]));
            }

            arrayList.add(text);
        }
    }

    public static boolean between(int p171_0, int p171_1, int p171_2) {
        return (p171_0 >= p171_1 && p171_0 <= p171_2);
    }

    public static float getFogStart() {
        return optifine.OptiFineConfig.gameSettings.ofFogStart;
    }

    private static ByteBuffer readIconImage(InputStream inputstream) throws IOException {
        int i1 = ReflectorMethod.cR();
        BufferedImage bufferedimage = ImageIO.read(inputstream);
        int i = i1;
        int[] aint =
                bufferedimage.getRGB(
                        0,
                        0,
                        bufferedimage.getWidth(),
                        bufferedimage.getHeight(),
                        (int[]) null,
                        0,
                        bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        int[] aint1 = aint;
        int j = aint.length;
        int k = 0;

        while (k < j) {
            int l = aint1[k];
            bytebuffer.putInt(l << 8 | l >> 24 & 0xFF);
            k++;
            if (i == 0) {
                break;
            }
        }

        ((Buffer) bytebuffer).flip();
        return bytebuffer;
    }

    public static void initGameSettings(GameSettings gameSettings) {
        if (optifine.OptiFineConfig.gameSettings == null) {
            optifine.OptiFineConfig.gameSettings = gameSettings;
            optifine.OptiFineConfig.minecraft = MinecraftClient.getMinecraft();
            optifine.OptiFineConfig.desktopDisplayMode = Display.getDesktopDisplayMode();
            updateAvailableProcessors();
            ReflectorForge.putLaunchBlackboard("optifine.ForgeSplashCompatible", Boolean.TRUE);
        }
    }

    public static boolean isCustomColors() {
        return optifine.OptiFineConfig.gameSettings.ofCustomColors;
    }

    public static int intHash(int p171_0) {
        p171_0 = p171_0 ^ 61 ^ p171_0 >> 16;
        p171_0 += p171_0 << 3;
        p171_0 ^= p171_0 >> 4;
        p171_0 *= 668265261;
        return p171_0 ^ p171_0 >> 15;
    }

    public static boolean isTreesSmart() {
        try {
            if (optifine.OptiFineConfig.gameSettings.ofTrees == 4) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static boolean isCustomSky() {
        return optifine.OptiFineConfig.gameSettings.ofCustomSky;
    }

    public static boolean isAnisotropicFiltering() {
        try {
            if (optifine.OptiFineConfig.getAnisotropicFilterLevel() > 1) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static boolean hasResource(ResourceLocation resourceLocation) {
        try {
            IResource resource = getResource(resourceLocation);
            return resource != null;
        } catch (IOException ioexception) {
            return false;
        }
    }

    public static boolean isCustomItems() {
        return optifine.OptiFineConfig.gameSettings.ofCustomItems;
    }

    public static boolean isDrippingWaterLava() {
        return optifine.OptiFineConfig.gameSettings.ofDrippingWaterLava;
    }

    public static boolean isSmoothBiomes() {
        return optifine.OptiFineConfig.gameSettings.ofSmoothBiomes;
    }

    static {
        optifine.OptiFineConfig.packedTextFragments = new String[30];
        optifine.OptiFineConfig.packedTextFragments[0] = new String("");
        optifine.OptiFineConfig.packedTextFragments[1] = new String("x");
        optifine.OptiFineConfig.packedTextFragments[2] = new String("");
        optifine.OptiFineConfig.packedTextFragments[3] = new String("");
        optifine.OptiFineConfig.packedTextFragments[4] = new String(".");
        optifine.OptiFineConfig.packedTextFragments[5] = new String(".");
        optifine.OptiFineConfig.packedTextFragments[6] = new String("(");
        optifine.OptiFineConfig.packedTextFragments[7] = new String(")");
        optifine.OptiFineConfig.packedTextFragments[8] = new String("");
        optifine.OptiFineConfig.packedTextFragments[9] = new String("");
        optifine.OptiFineConfig.packedTextFragments[10] = new String("");
        optifine.OptiFineConfig.packedTextFragments[11] = new String("");
        optifine.OptiFineConfig.packedTextFragments[12] = new String("");
        optifine.OptiFineConfig.packedTextFragments[13] = new String("x");
        optifine.OptiFineConfig.packedTextFragments[14] = new String("\n");
        optifine.OptiFineConfig.packedTextFragments[15] = new String("");
        optifine.OptiFineConfig.packedTextFragments[16] = new String("");
        optifine.OptiFineConfig.packedTextFragments[17] = new String("");
        optifine.OptiFineConfig.packedTextFragments[18] = new String("");
        optifine.OptiFineConfig.packedTextFragments[19] = new String("");
        optifine.OptiFineConfig.packedTextFragments[20] = new String("");
        optifine.OptiFineConfig.packedTextFragments[21] = new String("");
        optifine.OptiFineConfig.packedTextFragments[22] = new String("x");
        optifine.OptiFineConfig.packedTextFragments[23] = new String("");
        optifine.OptiFineConfig.packedTextFragments[24] = new String("+");
        optifine.OptiFineConfig.packedTextFragments[25] = new String("");
        optifine.OptiFineConfig.packedTextFragments[26] = new String("");
        optifine.OptiFineConfig.packedTextFragments[27] = new String("");
        optifine.OptiFineConfig.packedTextFragments[28] = new String("");
        a = "OptiFine";
        c = "HD_U";
        e = "OptiFine_1.8_HD_U_H6";
        b = "1.8";
        d = "H6";
        optifine.OptiFineConfig.newRelease = null;
        optifine.OptiFineConfig.notify64BitJava = false;
        optifine.OptiFineConfig.openGlVersion = null;
        optifine.OptiFineConfig.openGlRenderer = null;
        optifine.OptiFineConfig.openGlVendor = null;
        optifine.OptiFineConfig.fancyFogAvailable = false;
        optifine.OptiFineConfig.occlusionAvailable = false;
        optifine.OptiFineConfig.gameSettings = null;
        optifine.OptiFineConfig.minecraft = null;
        optifine.OptiFineConfig.initialized = false;
        optifine.OptiFineConfig.minecraftThread = null;
        optifine.OptiFineConfig.desktopDisplayMode = null;
        optifine.OptiFineConfig.antialiasingLevel = 0;
        optifine.OptiFineConfig.availableProcessors = 0;
        optifine.OptiFineConfig.zoomMode = false;
        optifine.OptiFineConfig.texturePackClouds = 0;
        optifine.OptiFineConfig.waterOpacityChanged = false;
        optifine.OptiFineConfig.fullscreenModeChecked = false;
        optifine.OptiFineConfig.desktopModeChecked = false;
        optifine.OptiFineConfig.defaultResourcePackLazy = null;
        BO = null;
        BL = new PrintStream(new FileOutputStream(FileDescriptor.out));
        optifine.OptiFineConfig.DEF_ALPHA_FUNC_LEVEL = 0.1F;
    }

    public static IResourcePack[] getResourcePacks() {
        ResourcePackRepository resourcePackRepository =
                optifine.OptiFineConfig.minecraft.getResourcePackRepository();
        int j = ReflectorMethod.f();
        List repositoryEntries = resourcePackRepository.getRepositoryEntries();
        ArrayList arrayList = new ArrayList();
        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_30190 = (repositoryEntries).iterator();
                (__recoveryIterator171_30190).hasNext(); ) {
            ResourcePackRepositoryEntry resourcePackRepositoryEntry =
                    (ResourcePackRepositoryEntry) (__recoveryIterator171_30190).next();
            arrayList.add(resourcePackRepositoryEntry.getResourcePack());
            if (i != 0) {
                break;
            }
        }

        if (resourcePackRepository.getResourcePackInstance() != null) {
            arrayList.add(resourcePackRepository.getResourcePackInstance());
        }

        return ((net.minecraft.client.resources.IResourcePack[])
                arrayList.toArray(new IResourcePack[arrayList.size()]));
    }

    public static int[] addIntsToArray(int[] aint, int[] aint1) {
        int i = ReflectorMethod.cR();
        if (aint != null && aint1 != null) {
            int j = aint.length;
            int k = j + aint1.length;
            int[] aint2 = new int[k];
            System.arraycopy(aint, 0, aint2, 0, j);
            int l = 0;

            while (l < aint1.length) {
                aint2[l + j] = aint1[l];
                l++;
                if (i == 0) {
                    break;
                }
            }

            return aint2;
        } else {
            throw new NullPointerException("The given array is NULL");
        }
    }

    public static boolean isWeatherEnabled() {
        return optifine.OptiFineConfig.gameSettings.ofWeather;
    }

    public static void dbg(String text) {
        BL.print("[OptiFine] ");
        BL.println(text);
    }

    public static String getVersion() {
        return "OptiFine_1.8_HD_U_H6";
    }

    public static boolean isWaterParticles() {
        return optifine.OptiFineConfig.gameSettings.ofWaterParticles;
    }

    public static boolean isOcclusionAvailable() {
        return optifine.OptiFineConfig.occlusionAvailable;
    }

    public static void checkDisplayMode() {
        int i = ReflectorMethod.f();

        try {
            if (optifine.OptiFineConfig.minecraft.isFullScreen()) {
                if (optifine.OptiFineConfig.fullscreenModeChecked) {
                    return;
                }

                optifine.OptiFineConfig.fullscreenModeChecked = true;
                optifine.OptiFineConfig.desktopModeChecked = false;
                DisplayMode displaymode = Display.getDisplayMode();
                Dimension dimension = getFullscreenDimension();
                if (dimension == null) {
                    return;
                }

                if (displaymode.getWidth() == dimension.width
                        && displaymode.getHeight() == dimension.height) {
                    return;
                }

                DisplayMode displaymode1 = getDisplayMode(dimension);
                if (displaymode1 == null) {
                    return;
                }

                Display.setDisplayMode(displaymode1);
                optifine.OptiFineConfig.minecraft.displayWidth =
                        Display.getDisplayMode().getWidth();
                optifine.OptiFineConfig.minecraft.displayHeight =
                        Display.getDisplayMode().getHeight();
                if (optifine.OptiFineConfig.minecraft.displayWidth <= 0) {
                    optifine.OptiFineConfig.minecraft.displayWidth = 1;
                }

                if (optifine.OptiFineConfig.minecraft.displayHeight <= 0) {
                    optifine.OptiFineConfig.minecraft.displayHeight = 1;
                }

                if (optifine.OptiFineConfig.minecraft.currentScreen != null) {
                    ScaledResolution scaledResolution =
                            new ScaledResolution(
                                    optifine.OptiFineConfig.minecraft,
                                    optifine.OptiFineConfig.minecraft.displayWidth,
                                    optifine.OptiFineConfig.minecraft.displayHeight);
                    int j = scaledResolution.getWidth();
                    int k = scaledResolution.getHeight();
                    optifine.OptiFineConfig.minecraft.currentScreen.setWorldAndResolution(
                            optifine.OptiFineConfig.minecraft, j, k);
                }

                optifine.OptiFineConfig.minecraft.loadingScreen =
                        new LoadingScreenRenderer(optifine.OptiFineConfig.minecraft);
                updateFramebufferSize();
                Display.setFullscreen(true);
                optifine.OptiFineConfig.minecraft.gameSettings.updateVSync();
                GlStateManager.enableTexture2D();
                if (i == 0) {
                    return;
                }
            }

            if (optifine.OptiFineConfig.desktopModeChecked) {
                return;
            }

            optifine.OptiFineConfig.desktopModeChecked = true;
            optifine.OptiFineConfig.fullscreenModeChecked = false;
            optifine.OptiFineConfig.minecraft.gameSettings.updateVSync();
            Display.update();
            GlStateManager.enableTexture2D();
            Display.setResizable(false);
            Display.setResizable(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static boolean isRainOff() {
        return optifine.OptiFineConfig.gameSettings.ofRain == 3;
    }

    public static boolean isCloudsOff() {
        return optifine.OptiFineConfig.gameSettings.ofClouds != 0
                ? optifine.OptiFineConfig.gameSettings.ofClouds == 3
                : (isShaders() && !Shaders.shaderPackClouds.isDefault()
                        ? Shaders.shaderPackClouds.jvm171_d_c8dbaa2b061a2d()
                        : (optifine.OptiFineConfig.texturePackClouds != 0
                                ? optifine.OptiFineConfig.texturePackClouds == 3
                                : false));
    }

    public static boolean isShaders() {
        return Shaders.shaderPackLoaded;
    }

    public static boolean jA() {
        try {
            if (optifine.OptiFineConfig.gameSettings.ofBetterGrass == 2) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static boolean isAnimatedSmoke() {
        return optifine.OptiFineConfig.gameSettings.ofAnimatedSmoke;
    }

    public static void warn(String text) {
        BL.print("[OptiFine] [WARN] ");
        BL.println(text);
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    private static String[] splitRelease(String text) {
        if (text != null && text.length() > 0) {
            Pattern pattern = Pattern.compile("([A-Z])([0-9]+)(.*)");
            Matcher matcher = pattern.matcher(text);
            if (!matcher.matches()) {
                return new String[] {
                    packedTextFragments[15], packedTextFragments[16], packedTextFragments[17]
                };
            }

            String textValue = normalize(matcher.group(1));
            String textValue2 = normalize(matcher.group(2));
            String textValue3 = normalize(matcher.group(3));
            return new String[] {textValue, textValue2, textValue3};
        } else {
            return new String[] {
                packedTextFragments[18], packedTextFragments[19], packedTextFragments[20]
            };
        }
    }

    public static GameSettings getGameSettings() {
        return optifine.OptiFineConfig.gameSettings;
    }

    private static int cK() {
        try {
            if (!org.lwjgl.opengl.GLContext.getCapabilities().OpenGL11) {
                return 10;
            }
        } catch (java.lang.NumberFormatException numberformatexception11) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception11));
        }

        try {
            if (!org.lwjgl.opengl.GLContext.getCapabilities().OpenGL12) {
                return 11;
            }
        } catch (java.lang.NumberFormatException numberformatexception10) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception10));
        }

        try {
            if (!org.lwjgl.opengl.GLContext.getCapabilities().OpenGL13) {
                return 12;
            }
        } catch (java.lang.NumberFormatException numberformatexception9) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception9));
        }

        try {
            if (!org.lwjgl.opengl.GLContext.getCapabilities().OpenGL14) {
                return 13;
            }
        } catch (java.lang.NumberFormatException numberformatexception8) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception8));
        }

        try {
            if (!org.lwjgl.opengl.GLContext.getCapabilities().OpenGL15) {
                return 14;
            }
        } catch (java.lang.NumberFormatException numberformatexception7) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception7));
        }

        try {
            if (!org.lwjgl.opengl.GLContext.getCapabilities().OpenGL20) {
                return 15;
            }
        } catch (java.lang.NumberFormatException numberformatexception6) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception6));
        }

        try {
            if (!org.lwjgl.opengl.GLContext.getCapabilities().OpenGL21) {
                return 20;
            }
        } catch (java.lang.NumberFormatException numberformatexception5) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception5));
        }

        try {
            if (!org.lwjgl.opengl.GLContext.getCapabilities().OpenGL30) {
                return 21;
            }
        } catch (java.lang.NumberFormatException numberformatexception4) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception4));
        }

        try {
            if (!org.lwjgl.opengl.GLContext.getCapabilities().OpenGL31) {
                return 30;
            }
        } catch (java.lang.NumberFormatException numberformatexception3) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception3));
        }

        try {
            if (!org.lwjgl.opengl.GLContext.getCapabilities().OpenGL32) {
                return 31;
            }
        } catch (java.lang.NumberFormatException numberformatexception2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception2));
        }

        try {
            if (!org.lwjgl.opengl.GLContext.getCapabilities().OpenGL33) {
                return 32;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception1));
        }

        try {
            if (!org.lwjgl.opengl.GLContext.getCapabilities().OpenGL40) {
                return 33;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return 40;
    }

    public static int getBitsOs() {
        String text = System.getenv("ProgramFiles(X86)");
        return text != null ? 64 : 32;
    }

    public static boolean isAnimatedLava() {
        try {
            if (optifine.OptiFineConfig.gameSettings.ofAnimatedLava != 2) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static Dimension getFullscreenDimension() {
        if (optifine.OptiFineConfig.desktopDisplayMode == null) {
            return null;
        }

        if (optifine.OptiFineConfig.gameSettings == null) {
            return new Dimension(
                    optifine.OptiFineConfig.desktopDisplayMode.getWidth(),
                    optifine.OptiFineConfig.desktopDisplayMode.getHeight());
        }

        String aIO = optifine.OptiFineConfig.gameSettings.ofFullscreenMode;
        if (aIO.equals("Default")) {
            return new Dimension(
                    optifine.OptiFineConfig.desktopDisplayMode.getWidth(),
                    optifine.OptiFineConfig.desktopDisplayMode.getHeight());
        }

        String[] astring = tokenize(aIO, " x");
        return astring.length < 2
                ? new Dimension(
                        optifine.OptiFineConfig.desktopDisplayMode.getWidth(),
                        optifine.OptiFineConfig.desktopDisplayMode.getHeight())
                : new Dimension(parseInt(astring[0], -1), parseInt(astring[1], -1));
    }

    public static int jG() {
        label33:
        {
            try {
                switch (optifine.OptiFineConfig.gameSettings.ofMipmapType) {
                    case 0:
                        return 9986;
                    case 1:
                        return 9986;
                    case 2:
                        break;
                    case 3:
                        break label33;
                    default:
                        return 9986;
                }
            } catch (java.lang.NumberFormatException numberformatexception2) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        optifine.OptiFineConfig.method_b7e52da193241b30710d(
                                numberformatexception2));
            }

            try {
                if (optifine.OptiFineConfig.isMultiTexture()) {
                    return 9985;
                }

                return 9986;
            } catch (java.lang.NumberFormatException numberformatexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        optifine.OptiFineConfig.method_b7e52da193241b30710d(
                                numberformatexception1));
            }
        }

        try {
            return optifine.OptiFineConfig.isMultiTexture() ? 9987 : 9986;
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }
    }

    public static int[] addIntToArray(int[] p171_0, int p171_1) {
        return optifine.OptiFineConfig.addIntsToArray(p171_0, new int[] {p171_1});
    }

    public static int getMipmapLevels() {
        return optifine.OptiFineConfig.gameSettings.mipmapLevels;
    }

    public static boolean jI() {
        try {
            if (optifine.OptiFineConfig.gameSettings.ofTrees == 0) {
                return optifine.OptiFineConfig.gameSettings.fancyGraphics;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception1));
        }

        try {
            if (optifine.OptiFineConfig.gameSettings.ofTrees != 1) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    private static String getUpdates(String text) {
        int i = text.indexOf(40);

        try {
            if (i < 0) {
                return optifine.OptiFineConfig.packedTextFragments[25];
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception1));
        }

        int j = text.indexOf(32, i);

        try {
            if (j < 0) {
                return optifine.OptiFineConfig.packedTextFragments[26];
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return text.substring(i + 1, j);
    }

    public static int jJ() {
        int k = ReflectorMethod.cR();
        String[] astring = new String[] {"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
        int i = k;
        int j = 0;

        while (j < astring.length) {
            String text = astring[j];
            String property = System.getProperty(text);
            if (property != null && property.contains("64")) {
                return 64;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return 32;
    }

    public static boolean isNaturalTextures() {
        return optifine.OptiFineConfig.gameSettings.ofNaturalTextures;
    }

    private static Method method_665961d04b346a7b2b2b(Class oclass, String text, Object[] aobject) {
        int k = ReflectorMethod.f();
        Method[] amethod = oclass.getMethods();
        int i = k;
        int j = 0;

        while (j < amethod.length) {
            Method method = amethod[j];
            if (method.getName().equals(text)
                    && method.getParameterTypes().length == aobject.length) {
                return method;
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        warn(
                "No method found for: "
                        + oclass.getName()
                        + packedTextFragments[5]
                        + text
                        + packedTextFragments[6]
                        + arrayToString(aobject)
                        + packedTextFragments[7]);
        return null;
    }

    public static String[] getDisplayModeNames() {
        org.lwjgl.opengl.DisplayMode[] adisplaymode = optifine.OptiFineConfig.getDisplayModes();
        java.lang.String[] astring = new java.lang.String[adisplaymode.length];
        int k = optifine.ReflectorMethod.f();
        int j = 0;
        int i = k;

        while (j < adisplaymode.length) {
            org.lwjgl.opengl.DisplayMode displaymode = adisplaymode[j];
            java.lang.String text =
                    optifine.OptiFineConfig.packedTextFragments[12]
                            + displaymode.getWidth()
                            + optifine.OptiFineConfig.packedTextFragments[13]
                            + displaymode.getHeight();
            astring[j] = text;
            j++;
            if (i != 0) {
                break;
            }
        }

        return astring;
    }

    public static ModelManager getModelManager() {
        return BO;
    }

    public static void writeFile(File file1, String text) throws IOException {
        FileOutputStream fileoutputstream = new FileOutputStream(file1);
        byte[] abyte = text.getBytes("ASCII");
        fileoutputstream.write(abyte);
        fileoutputstream.close();
    }

    public static DynamicTexture getMojangLogoTexture(DynamicTexture dynamicTexture) {
        try {
            ResourceLocation resourceLocation =
                    new ResourceLocation("textures/gui/title/mojang.png");
            InputStream inputstream = getResourceStream(resourceLocation);
            if (inputstream == null) {
                return dynamicTexture;
            }

            BufferedImage bufferedimage = ImageIO.read(inputstream);
            return bufferedimage == null ? dynamicTexture : new DynamicTexture(bufferedimage);
        } catch (Exception exception) {
            warn(exception.getClass().getName() + ": " + exception.getMessage());
            return dynamicTexture;
        }
    }

    public static String getNewRelease() {
        return optifine.OptiFineConfig.newRelease;
    }

    public static int getAntialiasingLevel() {
        return optifine.OptiFineConfig.antialiasingLevel;
    }

    public static boolean isAnimatedTextures() {
        return optifine.OptiFineConfig.gameSettings.ofAnimatedTextures;
    }

    public static boolean isGeneratedWater() {
        return optifine.OptiFineConfig.gameSettings.ofAnimatedWater == 1;
    }

    public static boolean isCloudsFancy() {
        return optifine.OptiFineConfig.gameSettings.ofClouds != 0
                ? optifine.OptiFineConfig.gameSettings.ofClouds == 2
                : (optifine.OptiFineConfig.isShaders()
                                && !shadersmod.client.Shaders.shaderPackClouds.isDefault()
                        ? shadersmod.client.Shaders.shaderPackClouds.isFancy()
                        : (optifine.OptiFineConfig.texturePackClouds != 0
                                ? optifine.OptiFineConfig.texturePackClouds == 2
                                : optifine.OptiFineConfig.gameSettings.fancyGraphics));
    }

    public static float limitTo1(float f) {
        return f < 0.0F ? 0.0F : (f > 1.0F ? 1.0F : f);
    }

    public static boolean isFancyFogAvailable() {
        return optifine.OptiFineConfig.fancyFogAvailable;
    }

    public static String method_cbedbe650d26c550e460(String text, int i, char c0) {
        int j = ReflectorMethod.f();
        if (text == null) {
            text = packedTextFragments[28];
        }

        if (text.length() >= i) {
            return text;
        }

        StringBuffer stringbuffer = new StringBuffer(text);

        while (stringbuffer.length() < i) {
            stringbuffer.append(c0);
            if (j != 0) {
                break;
            }
        }

        return stringbuffer.toString();
    }

    public static void setNotify64BitJava(boolean flag) {
        optifine.OptiFineConfig.notify64BitJava = flag;
    }

    public static void method_256b565648be898fe3d1(ModelManager modelManager) {}

    public static boolean isBetterGrass() {
        return optifine.OptiFineConfig.gameSettings.ofBetterGrass != 3;
    }

    public static Object[] addObjectsToArray(Object[] aobject, Object[] aobject1) {
        if (aobject == null) {
            throw new NullPointerException("The given array is NULL");
        }

        if (aobject1.length == 0) {
            return aobject;
        }

        int length = aobject.length;
        int j = length + aobject1.length;
        Object[] aobject2 = (Object[]) Array.newInstance(aobject.getClass().getComponentType(), j);
        System.arraycopy(aobject, 0, aobject2, 0, length);
        System.arraycopy(aobject1, 0, aobject2, length, aobject1.length);
        return aobject2;
    }

    public static boolean isGeneratedLava() {
        return optifine.OptiFineConfig.gameSettings.ofAnimatedLava == 1;
    }

    public static boolean jU() {
        try {
            if (!optifine.OptiFineConfig.isSingleProcessor()) {
                return false;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return optifine.OptiFineConfig.gameSettings.ofLazyChunkLoading;
    }

    public static boolean isFireworkParticles() {
        return optifine.OptiFineConfig.gameSettings.ofFireworkParticles;
    }

    public static IResource getResource(ResourceLocation resourceLocation) throws IOException {
        return optifine.OptiFineConfig.minecraft.getResourceManager().getResource(resourceLocation);
    }

    public static WorldServer getWorldServer() {
        if (optifine.OptiFineConfig.minecraft == null) {
            return null;
        }

        ClientWorld world = optifine.OptiFineConfig.minecraft.world;
        if (world == null) {
            return null;
        }

        if (!optifine.OptiFineConfig.minecraft.isIntegratedServerRunning()) {
            return null;
        }

        IntegratedServer integratedServer = optifine.OptiFineConfig.minecraft.getIntegratedServer();
        if (integratedServer == null) {
            return null;
        }

        WorldProvider bFY = world.provider;
        if (bFY == null) {
            return null;
        }

        int dimensionId = bFY.getDimensionId();

        try {
            return integratedServer.worldServerForDimension(dimensionId);
        } catch (NullPointerException nullpointerexception) {
            return null;
        }
    }

    public static void showGuiMessage(String text, String textValue) {
        optifine.GuiMessage guiMessage =
                new optifine.GuiMessage(
                        optifine.OptiFineConfig.minecraft.currentScreen, text, textValue);
        optifine.OptiFineConfig.minecraft.displayGuiScreen(guiMessage);
    }

    public static boolean isTimeDayOnly() {
        return optifine.OptiFineConfig.gameSettings.ofTime == 1;
    }

    public static String readFile(File file1) throws IOException {
        FileInputStream fileinputstream = new FileInputStream(file1);
        return readInputStream(fileinputstream, "ASCII");
    }

    public static boolean isMultiTexture() {
        try {
            if (optifine.OptiFineConfig.getAnisotropicFilterLevel() > 1) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception1));
        }

        try {
            if (optifine.OptiFineConfig.getAntialiasingLevel() > 0) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static boolean isClearWater() {
        return optifine.OptiFineConfig.gameSettings.ofClearWater;
    }

    public static void checkDisplaySettings() {
        int antialiasingLevelValue = getAntialiasingLevel();
        if (antialiasingLevelValue > 0) {
            DisplayMode displaymode = Display.getDisplayMode();
            dbg("FSAA Samples: " + antialiasingLevelValue);

            try {
                Display.destroy();
                Display.setDisplayMode(displaymode);
                Display.create(
                        new PixelFormat().withDepthBits(24).withSamples(antialiasingLevelValue));
                Display.setResizable(false);
                Display.setResizable(true);
            } catch (LWJGLException lwjglexception2) {
                warn("Error setting FSAA: " + antialiasingLevelValue + packedTextFragments[22]);
                lwjglexception2.printStackTrace();

                try {
                    Display.setDisplayMode(displaymode);
                    Display.create(new PixelFormat().withDepthBits(24));
                    Display.setResizable(false);
                    Display.setResizable(true);
                } catch (LWJGLException lwjglexception1) {
                    lwjglexception1.printStackTrace();

                    try {
                        Display.setDisplayMode(displaymode);
                        Display.create();
                        Display.setResizable(false);
                        Display.setResizable(true);
                    } catch (LWJGLException lwjglexception) {
                        lwjglexception.printStackTrace();
                    }
                }
            }

            if (!MinecraftClient.isRunningOnMac && getDefaultResourcePack() != null) {
                InputStream inputstream = null;
                InputStream inputstream1 = null;

                try {
                    inputstream =
                            getDefaultResourcePack()
                                    .getInputStreamAssets(
                                            new ResourceLocation("icons/icon_16x16.png"));
                    inputstream1 =
                            getDefaultResourcePack()
                                    .getInputStreamAssets(
                                            new ResourceLocation("icons/icon_32x32.png"));
                    if (inputstream != null && inputstream1 != null) {
                        Display.setIcon(
                                new ByteBuffer[] {
                                    readIconImage(inputstream), readIconImage(inputstream1)
                                });
                    }
                } catch (IOException ioexception) {
                    warn(
                            "Error setting window icon: "
                                    + ioexception.getClass().getName()
                                    + ": "
                                    + ioexception.getMessage());
                } finally {
                    IOUtils.closeQuietly(inputstream);
                    IOUtils.closeQuietly(inputstream1);
                }
            }
        }
    }

    public static int limit(int p171_0, int p171_1, int p171_2) {
        try {
            if (p171_0 < p171_1) {
                return p171_1;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception1));
        }

        try {
            if (p171_0 > p171_2) {
                return p171_2;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return p171_0;
    }

    public static boolean isFastFogEnabled() {
        try {
            if (optifine.OptiFineConfig.gameSettings.ofFogType == 1) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static void sleep(long p171_0) {
        try {
            java.lang.Thread.currentThread();
            java.lang.Thread.sleep(p171_0);
        } catch (java.lang.InterruptedException interruptedexception) {
            interruptedexception.printStackTrace();
        }
    }

    public static void updateFramebufferSize() {
        try {
            optifine.OptiFineConfig.minecraft
                    .getFramebuffer()
                    .createBindFramebuffer(
                            optifine.OptiFineConfig.minecraft.displayWidth,
                            optifine.OptiFineConfig.minecraft.displayHeight);
            if (optifine.OptiFineConfig.minecraft.entityRenderer != null) {
                optifine.OptiFineConfig.minecraft.entityRenderer.updateShaderGroupSize(
                        optifine.OptiFineConfig.minecraft.displayWidth,
                        optifine.OptiFineConfig.minecraft.displayHeight);
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }
    }

    public static Object[] addObjectToArray(Object[] aobject, Object object) {
        if (aobject == null) {
            throw new NullPointerException("The given array is NULL");
        }

        int length = aobject.length;
        int j = length + 1;
        Object[] aobject1 = (Object[]) Array.newInstance(aobject.getClass().getComponentType(), j);
        System.arraycopy(aobject, 0, aobject1, 0, length);
        aobject1[length] = object;
        return aobject1;
    }

    public static boolean isRainSplash() {
        return optifine.OptiFineConfig.gameSettings.ofRainSplash;
    }

    public static IResourcePack getDefiningResourcePack(ResourceLocation resourceLocation) {
        int k = ReflectorMethod.f();
        IResourcePack[] iResourcePackArray = getResourcePacks();
        int j = iResourcePackArray.length - 1;
        int i = k;

        while (j >= 0) {
            IResourcePack iResourcePack = iResourcePackArray[j];
            if (iResourcePack.resourceExists(resourceLocation)) {
                return iResourcePack;
            }

            j--;
            if (i != 0) {
                break;
            }
        }

        return getDefaultResourcePack().resourceExists(resourceLocation)
                ? getDefaultResourcePack()
                : null;
    }

    // region String decoding support

    // endregion

    public static int getRandom(BlockPos blockPos, int i) {
        int j = intHash(i + 37);
        j = intHash(j + blockPos.getX());
        j = intHash(j + blockPos.getZ());
        return intHash(j + blockPos.getY());
    }

    public static String normalize(String text) {
        try {
            if (text == null) {
                return optifine.OptiFineConfig.packedTextFragments[21];
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return text;
    }

    public static boolean isAnimatedExplosion() {
        return optifine.OptiFineConfig.gameSettings.ofAnimatedExplosion;
    }

    public static String arrayToString(Object[] aobject) {
        if (aobject == null) {
            return packedTextFragments[8];
        }

        StringBuffer stringbuffer = new StringBuffer(aobject.length * 5);

        for (int i = 0; i < aobject.length; i++) {
            Object object = aobject[i];
            if (i > 0) {
                stringbuffer.append(", ");
            }

            stringbuffer.append(String.valueOf(object));
        }

        return stringbuffer.toString();
    }

    public static boolean isFastRender() {
        return optifine.OptiFineConfig.gameSettings.ofFastRender;
    }

    public static boolean isNotify64BitJava() {
        return optifine.OptiFineConfig.notify64BitJava;
    }

    public static boolean equals(Object p171_0, Object p171_1) {
        try {
            if (p171_0 == p171_1) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception1));
        }

        try {
            if (p171_0 == null) {
                return false;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return p171_0.equals(p171_1);
    }

    public static boolean isDroppedItemsFancy() {
        return optifine.OptiFineConfig.gameSettings.ofDroppedItems == 0
                ? optifine.OptiFineConfig.gameSettings.fancyGraphics
                : optifine.OptiFineConfig.gameSettings.ofDroppedItems == 2;
    }

    public static boolean isDynamicFov() {
        return optifine.OptiFineConfig.gameSettings.ofDynamicFov;
    }

    public static boolean isSunMoonEnabled() {
        return optifine.OptiFineConfig.gameSettings.ofSunMoon;
    }

    public static boolean isTimeDefault() {
        try {
            if (optifine.OptiFineConfig.gameSettings.ofTime == 0) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static void updateTexturePackClouds() {
        optifine.OptiFineConfig.texturePackClouds = 0;
        IResourceManager iResourceManager = getResourceManager();
        if (iResourceManager != null) {
            try {
                InputStream inputstream =
                        iResourceManager
                                .getResource(new ResourceLocation("mcpatcher/color.properties"))
                                .getInputStream();
                if (inputstream == null) {
                    return;
                }

                Properties properties = new Properties();
                properties.load(inputstream);
                inputstream.close();
                String property = properties.getProperty("clouds");
                if (property == null) {
                    return;
                }

                dbg("Texture pack clouds: " + property);
                property = property.toLowerCase();
                if (property.equals("fast")) {
                    optifine.OptiFineConfig.texturePackClouds = 1;
                }

                if (property.equals("fancy")) {
                    optifine.OptiFineConfig.texturePackClouds = 2;
                }

                if (property.equals("off")) {
                    optifine.OptiFineConfig.texturePackClouds = 3;
                }
            } catch (Exception exception) {
            }
        }
    }

    public static DisplayMode getDisplayMode(Dimension dimension) throws LWJGLException {
        DisplayMode[] adisplaymode = Display.getAvailableDisplayModes();
        int k = ReflectorMethod.f();
        int j = 0;
        int i = k;

        while (j < adisplaymode.length) {
            DisplayMode displaymode = adisplaymode[j];
            if (displaymode.getWidth() == dimension.width
                    && displaymode.getHeight() == dimension.height
                    && (optifine.OptiFineConfig.desktopDisplayMode == null
                            || displaymode.getBitsPerPixel()
                                            == optifine.OptiFineConfig.desktopDisplayMode
                                                    .getBitsPerPixel()
                                    && displaymode.getFrequency()
                                            == optifine.OptiFineConfig.desktopDisplayMode
                                                    .getFrequency())) {
                return displaymode;
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        return optifine.OptiFineConfig.desktopDisplayMode;
    }

    // region String decoding support

    // endregion

    public static boolean dL() {
        try {
            if (optifine.OptiFineConfig.gameSettings.mipmapLevels > 0) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static void updateAvailableProcessors() {
        optifine.OptiFineConfig.availableProcessors = Runtime.getRuntime().availableProcessors();
    }

    public static InputStream getResourceStream(
            IResourceManager iResourceManager, ResourceLocation resourceLocation)
            throws IOException {
        IResource resource = iResourceManager.getResource(resourceLocation);
        return resource == null ? null : resource.getInputStream();
    }

    public static boolean isCustomFonts() {
        return optifine.OptiFineConfig.gameSettings.ofCustomFonts;
    }

    public static boolean isSwampColors() {
        return optifine.OptiFineConfig.gameSettings.ofSwampColors;
    }

    public static float limit(float f, float f1, float f2) {
        return f < f1 ? f1 : (f > f2 ? f2 : f);
    }

    public static boolean isShowCapes() {
        return optifine.OptiFineConfig.gameSettings.ofShowCapes;
    }

    public static boolean isAntialiasing() {
        try {
            if (optifine.OptiFineConfig.getAntialiasingLevel() > 0) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static boolean isFogOff() {
        try {
            if (optifine.OptiFineConfig.gameSettings.ofFogType == 3) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static int getAvailableProcessors() {
        return optifine.OptiFineConfig.availableProcessors;
    }

    public static boolean isTimeNightOnly() {
        return optifine.OptiFineConfig.gameSettings.ofTime == 2;
    }

    private static void checkOpenGlCaps() {
        log(packedTextFragments[0]);
        log(getVersion());
        log("Build: " + getBuild());
        log(
                "OS: "
                        + System.getProperty("os.name")
                        + " ("
                        + System.getProperty("os.arch")
                        + ") version "
                        + System.getProperty("os.version"));
        log(
                "Java: "
                        + System.getProperty("java.version")
                        + ", "
                        + System.getProperty("java.vendor"));
        log(
                "VM: "
                        + System.getProperty("java.vm.name")
                        + " ("
                        + System.getProperty("java.vm.info")
                        + "), "
                        + System.getProperty("java.vm.vendor"));
        log("LWJGL: " + Sys.getVersion());
        optifine.OptiFineConfig.openGlVersion = GL11.glGetString(7938);
        optifine.OptiFineConfig.openGlRenderer = GL11.glGetString(7937);
        optifine.OptiFineConfig.openGlVendor = GL11.glGetString(7936);
        log(
                "OpenGL: "
                        + optifine.OptiFineConfig.openGlRenderer
                        + ", version "
                        + optifine.OptiFineConfig.openGlVersion
                        + ", "
                        + optifine.OptiFineConfig.openGlVendor);
        log("OpenGL Version: " + getOpenGlVersionString());
        if (!GLContext.getCapabilities().OpenGL12) {
            log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
        }

        optifine.OptiFineConfig.fancyFogAvailable = GLContext.getCapabilities().GL_NV_fog_distance;
        if (!optifine.OptiFineConfig.fancyFogAvailable) {
            log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
        }

        optifine.OptiFineConfig.occlusionAvailable =
                GLContext.getCapabilities().GL_ARB_occlusion_query;
        if (!optifine.OptiFineConfig.occlusionAvailable) {
            log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
        }

        int i = MinecraftClient.getGLMaximumTextureSize();
        dbg("Maximum texture size: " + i + packedTextFragments[1] + i);
    }

    public static float parseFloat(String text, float p171_1) {
        try {
            if (text == null) {
                return p171_1;
            }

            text = ((java.lang.String) (((java.lang.String) (text)).trim()));
            return ((float) (java.lang.Float.parseFloat(((java.lang.String) (text)))));
        } catch (java.lang.NumberFormatException numberformatexception) {
            return p171_1;
        }
    }

    public static boolean isDynamicUpdates() {
        return optifine.OptiFineConfig.gameSettings.ofChunkUpdatesDynamic;
    }

    public static String[] readLines(File file) throws IOException {
        java.io.FileInputStream fileinputstream = new java.io.FileInputStream(file);
        return optifine.OptiFineConfig.readLines(fileinputstream);
    }

    public static String getVersionDebug() {
        StringBuffer stringbuffer = new StringBuffer(32);
        if (isDynamicLights()) {
            stringbuffer.append("DL: ");
            stringbuffer.append(String.valueOf(DynamicLights.getCount()));
            stringbuffer.append(", ");
        }

        stringbuffer.append("OptiFine_1.8_HD_U_H6");
        String text = Shaders.getShaderPackName();
        if (text != null) {
            stringbuffer.append(", ");
            stringbuffer.append(text);
        }

        return stringbuffer.toString();
    }

    public static String kv() {
        int i = optifine.ReflectorMethod.f();

        try {
            if (optifine.OptiFineConfig.minecraft == null) {
                return optifine.OptiFineConfig.packedTextFragments[10];
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        try {
            if (optifine.OptiFineConfig.minecraft.getResourcePackRepository() == null) {
                return optifine.OptiFineConfig.packedTextFragments[11];
            }
        } catch (java.lang.NumberFormatException numberformatexception2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception2));
        }

        net.minecraft.client.resources.IResourcePack[] iResourcePackArray =
                optifine.OptiFineConfig.getResourcePacks();

        try {
            if (iResourcePackArray.length <= 0) {
                return optifine.OptiFineConfig.getDefaultResourcePack().getPackName();
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception1));
        }

        java.lang.String[] astring = new java.lang.String[iResourcePackArray.length];
        int j = 0;

        while (j < iResourcePackArray.length) {
            astring[j] = iResourcePackArray[j].getPackName();
            j++;
            if (i != 0) {
                break;
            }
        }

        return optifine.OptiFineConfig.arrayToString(astring);
    }

    public static boolean isSingleProcessor() {
        try {
            if (optifine.OptiFineConfig.getAvailableProcessors() <= 1) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static int getUpdatesPerFrame() {
        return optifine.OptiFineConfig.gameSettings.ofChunkUpdates;
    }

    public static boolean parseBoolean(String text, boolean p171_1) {
        try {
            if (text == null) {
                return p171_1;
            }

            text = ((java.lang.String) (((java.lang.String) (text)).trim()));
            return ((boolean) (java.lang.Boolean.parseBoolean(((java.lang.String) (text)))));
        } catch (java.lang.NumberFormatException numberformatexception) {
            return p171_1;
        }
    }

    public static boolean isBetterSnow() {
        return optifine.OptiFineConfig.gameSettings.ofBetterSnow;
    }

    public static String readInputStream(InputStream inputstream) throws IOException {
        return readInputStream(inputstream, "ASCII");
    }

    public static boolean isRandomMobs() {
        return optifine.OptiFineConfig.gameSettings.ofRandomMobs;
    }

    public static int compareRelease(String text, String textValue) {
        java.lang.String[] astring = optifine.OptiFineConfig.splitRelease(text);
        java.lang.String[] astring1 = optifine.OptiFineConfig.splitRelease(textValue);
        java.lang.String textValue2 = astring[0];
        java.lang.String textValue3 = astring1[0];
        if (!((boolean)
                (((java.lang.String) (textValue2)).equals(((java.lang.Object) (textValue3)))))) {
            return ((int)
                    (((java.lang.String) (textValue2))
                            .compareTo(((java.lang.String) (textValue3)))));
        }

        int i = optifine.OptiFineConfig.parseInt(astring[1], -1);
        int j = optifine.OptiFineConfig.parseInt(astring1[1], -1);
        if (i != j) {
            return i - j;
        }

        java.lang.String textValue4 = astring[2];
        java.lang.String textValue5 = astring1[2];
        if (!((boolean)
                (((java.lang.String) (textValue4)).equals(((java.lang.Object) (textValue5)))))) {
            if (((boolean) (((java.lang.String) (textValue4)).isEmpty()))) {
                return 1;
            }

            if (((boolean) (((java.lang.String) (textValue5)).isEmpty()))) {
                return -1;
            }
        }

        return ((int)
                (((java.lang.String) (textValue4)).compareTo(((java.lang.String) (textValue5)))));
    }

    public static boolean kA() {
        float fx = optifine.OptiFineConfig.getAlphaFuncLevel();

        try {
            if (fx > optifine.OptiFineConfig.DEF_ALPHA_FUNC_LEVEL + 1.0E-5F) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static DefaultResourcePack getDefaultResourcePack() {
        int i = ReflectorMethod.f();
        if (optifine.OptiFineConfig.defaultResourcePackLazy == null) {
            MinecraftClient minecraftClient = MinecraftClient.getMinecraft();

            try {
                Field[] afield = minecraftClient.getClass().getDeclaredFields();
                int j = 0;

                while (j < afield.length) {
                    Field field = afield[j];
                    if (field.getType() == DefaultResourcePack.class) {
                        field.setAccessible(true);
                        optifine.OptiFineConfig.defaultResourcePackLazy =
                                (DefaultResourcePack) field.get(minecraftClient);
                        if (i == 0) {
                            break;
                        }
                    }

                    j++;
                    if (i != 0) {
                        break;
                    }
                }
            } catch (Exception exception) {
                warn(
                        "Error getting default resource pack: "
                                + exception.getClass().getName()
                                + ": "
                                + exception.getMessage());
            }

            if (optifine.OptiFineConfig.defaultResourcePackLazy == null) {
                ResourcePackRepository resourcePackRepository =
                        minecraftClient.getResourcePackRepository();
                if (resourcePackRepository != null) {
                    optifine.OptiFineConfig.defaultResourcePackLazy =
                            (DefaultResourcePack) resourcePackRepository.rprDefaultResourcePack;
                }
            }
        }

        return optifine.OptiFineConfig.defaultResourcePackLazy;
    }

    public static boolean isSmoothWorld() {
        return optifine.OptiFineConfig.gameSettings.ofSmoothWorld;
    }

    public static boolean isDynamicLights() {
        try {
            if (optifine.OptiFineConfig.gameSettings.ofDynamicLights != 3) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static void error(String text) {
        BL.print("[OptiFine] [ERROR] ");
        BL.println(text);
    }

    public static boolean isConnectedTextures() {
        return optifine.OptiFineConfig.gameSettings.ofConnectedTextures != 3;
    }

    public static boolean isVignetteEnabled() {
        try {
            if (optifine.OptiFineConfig.gameSettings.ofVignette == 0) {
                return optifine.OptiFineConfig.gameSettings.fancyGraphics;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception1));
        }

        try {
            if (optifine.OptiFineConfig.gameSettings.ofVignette == 2) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static boolean isMinecraftThread() {
        try {
            if (java.lang.Thread.currentThread() == optifine.OptiFineConfig.minecraftThread) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.OptiFineConfig.method_b7e52da193241b30710d(numberformatexception));
        }

        return false;
    }

    public static MinecraftClient getMinecraft() {
        return optifine.OptiFineConfig.minecraft;
    }

    public static void setNewRelease(String text) {}

    public static boolean isStarsEnabled() {
        return optifine.OptiFineConfig.gameSettings.ofStars;
    }

    public static Object[] addObjectToArray(Object[] p171_0, Object p171_1, int p171_2) {
        java.util.ArrayList arrayList = new java.util.ArrayList<>(java.util.Arrays.asList(p171_0));
        arrayList.add(p171_2, p171_1);
        java.lang.Object[] aobject1 =
                (java.lang.Object[])
                        java.lang.reflect.Array.newInstance(
                                p171_0.getClass().getComponentType(), arrayList.size());
        return arrayList.toArray(aobject1);
    }
}
