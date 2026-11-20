package net.minecraft.client.settings;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerEnumChatVisibility;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;

import optifine.CustomColors;
import optifine.CustomSky;
import optifine.DynamicLights;
import optifine.Lang;
import optifine.NaturalTextures;
import optifine.OptiFineConfig;
import optifine.RandomMobs;
import optifine.Reflector;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import shadersmod.client.Shaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameSettings {
    public int streamChatUserFilter;
    private static int[] OF_TREES_VALUES;
    public KeyBinding keyBindUseItem;
    public KeyBinding keyBindStreamToggleMic;
    public boolean ofChunkUpdatesDynamic;
    public boolean chatLinks;
    public KeyBinding keyBindStreamPauseUnpause;
    public int ofAnimatedWater;
    public int streamCompression;
    public boolean ofShowFps;
    public float chatHeightUnfocused;
    public boolean fboEnable;
    private Map mapSoundLevels;
    public boolean ofDrippingWaterLava;
    public KeyBinding keyBindDrop;
    public KeyBinding keyBindSneak;
    public KeyBinding keyBindInventory;
    public static final int aIj = 2;
    public boolean ofClearWater;
    public boolean fancyGraphics;
    public KeyBinding keyBindSmoothCamera;
    public int ofRain;
    public KeyBinding keyBindPlayerList;
    public boolean smoothCamera;
    public boolean ofStars;
    public KeyBinding[] keyBindsHotbar;
    public static final int aIp = 0;
    public boolean ofAnimatedFire;
    public int thirdPersonView;
    public boolean ofLagometer;
    public int ofConnectedTextures;
    public KeyBinding keyBindSprint;
    public KeyBinding keyBindCommand;
    public static final int aIv = 4;
    public int guiScale;
    public boolean allowBlockAlternatives;
    public boolean ofVoidParticles;
    public static String DEFAULT_STR;
    public int ofClouds;
    public static final int NW = 1;
    private static String[] PARTICLES;
    public int ofMipmapType;
    private static String[] STREAM_MIC_MODES;
    private static String[] AMBIENT_OCCLUSIONS;
    public int ofBetterGrass;
    public boolean fullScreen;
    public boolean ofAnimatedTerrain;
    public boolean enableVsync;
    public boolean ofWaterParticles;
    public boolean ofFireworkParticles;
    public int ofAfLevel;

    public float ofFogStart;
    public int ofDroppedItems;
    public boolean chatLinksPrompt;
    public float ofAoLevel;
    public static final int aID = 3;
    public static final int aIE = 1;
    private static String[] KEYS_DYNAMIC_LIGHTS;
    private static String[] GUISCALES;
    public int particleSetting;
    public KeyBinding keyBindTogglePerspective;
    public KeyBinding keyBindPickBlock;
    public KeyBinding ofKeyBindZoom;
    public EntityPlayerEnumChatVisibility chatVisibility;
    public boolean ofSmoothFps;
    public int ofAaLevel;
    public boolean ofSunMoon;
    public boolean ofSmoothWorld;
    public boolean ofPotionParticles;
    public KeyBinding[] keyBindings;
    private static String[] STREAM_COMPRESSIONS;
    public boolean reducedDebugInfo;
    public String ofFullscreenMode;
    public boolean ofRandomMobs;
    public boolean ofCustomColors;
    public boolean ofOcclusionFancy;
    public boolean ofBetterSnow;
    public boolean ofCustomItems;
    public boolean ofLazyChunkLoading;
    public List resourcePacks;
    public float ofCloudsHeight;
    public float fovSetting;
    public float saturation;
    public KeyBinding keyBindAttack;
    public float streamMicVolume;
    private static Gson gson;
    public boolean forceUnicodeFont;
    public boolean invertMouse;
    public boolean ofAnimatedTextures;
    public int renderDistanceChunks;
    private Set setModelParts;
    public KeyBinding keyBindJump;
    public boolean showInventoryAchievementHint;
    public boolean ofShowCapes;
    private static Logger logger;
    public static final int aJe = 2;
    public boolean ofSwampColors;
    public KeyBinding keyBindLeft;
    public static final int KO = 0;
    public int mipmapLevels;
    public boolean ofPortalParticles;
    public int ofTranslucentBlocks;
    public int ambientOcclusion;
    public boolean ofRainSplash;
    private static ParameterizedType typeListString;
    public boolean ofAnimatedFlame;
    public boolean ofWeather;
    private static String[] STREAM_CHAT_MODES;
    public KeyBinding keyBindScreenshot;
    public boolean ofSmoothBiomes;
    public boolean ofFastMath;
    public float streamKbps;
    public boolean showDebugProfilerChart;
    public boolean snooperEnabled;
    public boolean ofAnimatedExplosion;
    public int ofTrees;
    public float chatWidth;
    private File optionsFile;
    public boolean ofFastRender;
    public float streamGameVolume;
    public boolean ofCustomFonts;
    public boolean advancedItemTooltips;
    public boolean debugCameraEnabled;
    public int ofDynamicLights;
    public boolean clouds;
    public int limitFramerate;
    public KeyBinding keyBindSpectatorOutlines;
    public int L;
    private File optionsFileOF;
    private static String[] STREAM_CHAT_FILTER_MODES;
    public boolean ofNaturalTextures;
    public float chatScale;
    public int ofVignette;
    public KeyBinding keyBindStreamStartStop;
    public boolean anaglyph;
    public String language;
    public KeyBinding keyBindChat;
    public float streamFps;
    public boolean ofAnimatedPortal;
    public boolean hideServerAddress;
    public int ofAnimatedLava;
    public boolean ofProfiler;
    protected MinecraftClient mc;
    public boolean ofAnimatedSmoke;
    public boolean streamSendMetadata;
    public KeyBinding keyBindFullscreen;
    public int streamChatEnabled;
    public int ofAutoSaveTicks;
    public int ofTime;
    public String lastServer;
    public boolean chatColours;
    public boolean heldItemTooltips;
    public boolean ofSky;
    public KeyBinding keyBindBack;
    public int streamMicToggleBehavior;
    public float chatHeightFocused;
    public float streamBytesPerPixel;
    public KeyBinding keyBindStreamCommercials;
    public boolean hideGUI;
    public boolean pauseOnLostFocus;
    public boolean viewBobbing;
    public boolean touchscreen;
    public boolean useVbo;
    public EnumDifficulty difficulty;
    public boolean ofCustomSky;
    public String streamPreferredServer;
    public KeyBinding keyBindForward;
    public float mouseSensitivity = 0.5F;
    public int ofFogType;
    private static String[] aJQ;
    public boolean ofAnimatedRedstone;
    public int ofChunkUpdates;
    public boolean ofDynamicFov;
    public float gammaSetting;

    public boolean showDebugInfo;
    public KeyBinding keyBindRight;
    private static String[] packedTextFragments;
    public int ni;
    private static int[] OF_DYNAMIC_LIGHTS;
    public float chatOpacity;

    public boolean getOptionOrdinalValue(GameSettingsOptions gameSettingsOptions) {
        switch (gameSettingsOptions) {
            case INVERT_MOUSE:
                return this.invertMouse;
            case VIEW_BOBBING:
                return this.viewBobbing;
            case ANAGLYPH:
                return this.anaglyph;
            case FBO_ENABLE:
                return this.fboEnable;
            case RENDER_CLOUDS:
                return this.clouds;
            case CHAT_COLOR:
                return this.chatColours;
            case CHAT_LINKS:
                return this.chatLinks;
            case CHAT_LINKS_PROMPT:
                return this.chatLinksPrompt;
            case SNOOPER_ENABLED:
                return this.snooperEnabled;
            case USE_FULLSCREEN:
                return this.fullScreen;
            case ENABLE_VSYNC:
                return this.enableVsync;
            case USE_VBO:
                return this.useVbo;
            case TOUCHSCREEN:
                return this.touchscreen;
            case STREAM_SEND_METADATA:
                return this.streamSendMetadata;
            case FORCE_UNICODE_FONT:
                return this.forceUnicodeFont;
            case BLOCK_ALTERNATIVES:
                return this.allowBlockAlternatives;
            case REDUCED_DEBUG_INFO:
                return this.reducedDebugInfo;
            default:
                return false;
        }
    }

    public static boolean isKeyDown(KeyBinding keyBinding) {
        int keyCode = keyBinding.getKeyCode();
        return keyCode < -100 || keyCode > 255
                ? false
                : (keyBinding.getKeyCode() == 0
                        ? false
                        : (keyBinding.getKeyCode() < 0
                                ? Mouse.isButtonDown(keyBinding.getKeyCode() + 100)
                                : Keyboard.isKeyDown(keyBinding.getKeyCode())));
    }

    public Set getModelParts() {
        return ImmutableSet.copyOf(this.setModelParts);
    }

    public static String[] BK() {
        return aJQ;
    }

    private void updateRenderClouds() {
        BK();

        switch (this.ofClouds) {
            case 1:
            case 2:
            default:
                this.clouds = true;

                break;

            case 3:
                this.clouds = false;
        }
    }

    public void cS() {
        BK();

        try {
            File file1 = this.optionsFileOF;
            if (!file1.exists()) {
                file1 = this.optionsFile;
            }

            if (!file1.exists()) {
                return;
            }

            BufferedReader bufferedreader = new BufferedReader(new FileReader(file1));
            String text = packedTextFragments[20];

            while ((text = bufferedreader.readLine()) != null) {
                try {
                    String[] astring1 = text.split(packedTextFragments[21]);
                    if (astring1[0].equals("ofRenderDistanceChunks") && astring1.length >= 2) {
                        this.renderDistanceChunks = Integer.valueOf(astring1[1]);
                        this.renderDistanceChunks =
                                OptiFineConfig.limit(this.renderDistanceChunks, 2, 32);
                    }

                    if (astring1[0].equals("ofFogType") && astring1.length >= 2) {
                        this.ofFogType = Integer.valueOf(astring1[1]);
                        this.ofFogType = OptiFineConfig.limit(this.ofFogType, 1, 3);
                    }

                    if (astring1[0].equals("ofFogStart") && astring1.length >= 2) {
                        this.ofFogStart = Float.valueOf(astring1[1]);
                        if (this.ofFogStart < 0.2F) {
                            this.ofFogStart = 0.2F;
                        }

                        if (this.ofFogStart > 0.81F) {
                            this.ofFogStart = 0.8F;
                        }
                    }

                    if (astring1[0].equals("ofMipmapType") && astring1.length >= 2) {
                        this.ofMipmapType = Integer.valueOf(astring1[1]);
                        this.ofMipmapType = OptiFineConfig.limit(this.ofMipmapType, 0, 3);
                    }

                    if (astring1[0].equals("ofOcclusionFancy") && astring1.length >= 2) {
                        this.ofOcclusionFancy = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofSmoothFps") && astring1.length >= 2) {
                        this.ofSmoothFps = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofSmoothWorld") && astring1.length >= 2) {
                        this.ofSmoothWorld = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofAoLevel") && astring1.length >= 2) {
                        this.ofAoLevel = Float.valueOf(astring1[1]);
                        this.ofAoLevel = OptiFineConfig.limit(this.ofAoLevel, 0.0F, 1.0F);
                    }

                    if (astring1[0].equals("ofClouds") && astring1.length >= 2) {
                        this.ofClouds = Integer.valueOf(astring1[1]);
                        this.ofClouds = OptiFineConfig.limit(this.ofClouds, 0, 3);
                        this.updateRenderClouds();
                    }

                    if (astring1[0].equals("ofCloudsHeight") && astring1.length >= 2) {
                        this.ofCloudsHeight = Float.valueOf(astring1[1]);
                        this.ofCloudsHeight = OptiFineConfig.limit(this.ofCloudsHeight, 0.0F, 1.0F);
                    }

                    if (astring1[0].equals("ofTrees") && astring1.length >= 2) {
                        this.ofTrees = Integer.valueOf(astring1[1]);
                        this.ofTrees =
                                limit(
                                        this.ofTrees,
                                        net.minecraft.client.settings.GameSettings.OF_TREES_VALUES);
                    }

                    if (astring1[0].equals("ofDroppedItems") && astring1.length >= 2) {
                        this.ofDroppedItems = Integer.valueOf(astring1[1]);
                        this.ofDroppedItems = OptiFineConfig.limit(this.ofDroppedItems, 0, 2);
                    }

                    if (astring1[0].equals("ofRain") && astring1.length >= 2) {
                        this.ofRain = Integer.valueOf(astring1[1]);
                        this.ofRain = OptiFineConfig.limit(this.ofRain, 0, 3);
                    }

                    if (astring1[0].equals("ofAnimatedWater") && astring1.length >= 2) {
                        this.ofAnimatedWater = Integer.valueOf(astring1[1]);
                        this.ofAnimatedWater = OptiFineConfig.limit(this.ofAnimatedWater, 0, 2);
                    }

                    if (astring1[0].equals("ofAnimatedLava") && astring1.length >= 2) {
                        this.ofAnimatedLava = Integer.valueOf(astring1[1]);
                        this.ofAnimatedLava = OptiFineConfig.limit(this.ofAnimatedLava, 0, 2);
                    }

                    if (astring1[0].equals("ofAnimatedFire") && astring1.length >= 2) {
                        this.ofAnimatedFire = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofAnimatedPortal") && astring1.length >= 2) {
                        this.ofAnimatedPortal = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofAnimatedRedstone") && astring1.length >= 2) {
                        this.ofAnimatedRedstone = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofAnimatedExplosion") && astring1.length >= 2) {
                        this.ofAnimatedExplosion = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofAnimatedFlame") && astring1.length >= 2) {
                        this.ofAnimatedFlame = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofAnimatedSmoke") && astring1.length >= 2) {
                        this.ofAnimatedSmoke = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofVoidParticles") && astring1.length >= 2) {
                        this.ofVoidParticles = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofWaterParticles") && astring1.length >= 2) {
                        this.ofWaterParticles = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofPortalParticles") && astring1.length >= 2) {
                        this.ofPortalParticles = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofPotionParticles") && astring1.length >= 2) {
                        this.ofPotionParticles = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofFireworkParticles") && astring1.length >= 2) {
                        this.ofFireworkParticles = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofDrippingWaterLava") && astring1.length >= 2) {
                        this.ofDrippingWaterLava = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofAnimatedTerrain") && astring1.length >= 2) {
                        this.ofAnimatedTerrain = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofAnimatedTextures") && astring1.length >= 2) {
                        this.ofAnimatedTextures = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofRainSplash") && astring1.length >= 2) {
                        this.ofRainSplash = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofLagometer") && astring1.length >= 2) {
                        this.ofLagometer = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofShowFps") && astring1.length >= 2) {
                        this.ofShowFps = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofAutoSaveTicks") && astring1.length >= 2) {
                        this.ofAutoSaveTicks = Integer.valueOf(astring1[1]);
                        this.ofAutoSaveTicks =
                                OptiFineConfig.limit(this.ofAutoSaveTicks, 40, 40000);
                    }

                    if (astring1[0].equals("ofBetterGrass") && astring1.length >= 2) {
                        this.ofBetterGrass = Integer.valueOf(astring1[1]);
                        this.ofBetterGrass = OptiFineConfig.limit(this.ofBetterGrass, 1, 3);
                    }

                    if (astring1[0].equals("ofConnectedTextures") && astring1.length >= 2) {
                        this.ofConnectedTextures = Integer.valueOf(astring1[1]);
                        this.ofConnectedTextures =
                                OptiFineConfig.limit(this.ofConnectedTextures, 1, 3);
                    }

                    if (astring1[0].equals("ofWeather") && astring1.length >= 2) {
                        this.ofWeather = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofSky") && astring1.length >= 2) {
                        this.ofSky = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofStars") && astring1.length >= 2) {
                        this.ofStars = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofSunMoon") && astring1.length >= 2) {
                        this.ofSunMoon = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofVignette") && astring1.length >= 2) {
                        this.ofVignette = Integer.valueOf(astring1[1]);
                        this.ofVignette = OptiFineConfig.limit(this.ofVignette, 0, 2);
                    }

                    if (astring1[0].equals("ofChunkUpdates") && astring1.length >= 2) {
                        this.ofChunkUpdates = Integer.valueOf(astring1[1]);
                        this.ofChunkUpdates = OptiFineConfig.limit(this.ofChunkUpdates, 1, 5);
                    }

                    if (astring1[0].equals("ofChunkUpdatesDynamic") && astring1.length >= 2) {
                        this.ofChunkUpdatesDynamic = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofTime") && astring1.length >= 2) {
                        this.ofTime = Integer.valueOf(astring1[1]);
                        this.ofTime = OptiFineConfig.limit(this.ofTime, 0, 2);
                    }

                    if (astring1[0].equals("ofClearWater") && astring1.length >= 2) {
                        this.ofClearWater = Boolean.valueOf(astring1[1]);
                        this.updateWaterOpacity();
                    }

                    if (astring1[0].equals("ofAaLevel") && astring1.length >= 2) {
                        this.ofAaLevel = Integer.valueOf(astring1[1]);
                        this.ofAaLevel = OptiFineConfig.limit(this.ofAaLevel, 0, 16);
                    }

                    if (astring1[0].equals("ofAfLevel") && astring1.length >= 2) {
                        this.ofAfLevel = Integer.valueOf(astring1[1]);
                        this.ofAfLevel = OptiFineConfig.limit(this.ofAfLevel, 1, 16);
                    }

                    if (astring1[0].equals("ofProfiler") && astring1.length >= 2) {
                        this.ofProfiler = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofBetterSnow") && astring1.length >= 2) {
                        this.ofBetterSnow = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofSwampColors") && astring1.length >= 2) {
                        this.ofSwampColors = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofRandomMobs") && astring1.length >= 2) {
                        this.ofRandomMobs = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofSmoothBiomes") && astring1.length >= 2) {
                        this.ofSmoothBiomes = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofCustomFonts") && astring1.length >= 2) {
                        this.ofCustomFonts = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofCustomColors") && astring1.length >= 2) {
                        this.ofCustomColors = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofCustomItems") && astring1.length >= 2) {
                        this.ofCustomItems = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofCustomSky") && astring1.length >= 2) {
                        this.ofCustomSky = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofShowCapes") && astring1.length >= 2) {
                        this.ofShowCapes = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofNaturalTextures") && astring1.length >= 2) {
                        this.ofNaturalTextures = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofLazyChunkLoading") && astring1.length >= 2) {
                        this.ofLazyChunkLoading = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofDynamicFov") && astring1.length >= 2) {
                        this.ofDynamicFov = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofDynamicLights") && astring1.length >= 2) {
                        this.ofDynamicLights = Integer.valueOf(astring1[1]);
                        this.ofDynamicLights =
                                limit(
                                        this.ofDynamicLights,
                                        net.minecraft.client.settings.GameSettings
                                                .OF_DYNAMIC_LIGHTS);
                    }

                    if (astring1[0].equals("ofFullscreenMode") && astring1.length >= 2) {
                        this.ofFullscreenMode = astring1[1];
                    }

                    if (astring1[0].equals("ofFastMath") && astring1.length >= 2) {
                        this.ofFastMath = Boolean.valueOf(astring1[1]);
                        MathHelper.fastMath = this.ofFastMath;
                    }

                    if (astring1[0].equals("ofFastRender") && astring1.length >= 2) {
                        this.ofFastRender = Boolean.valueOf(astring1[1]);
                    }

                    if (astring1[0].equals("ofTranslucentBlocks") && astring1.length >= 2) {
                        this.ofTranslucentBlocks = Integer.valueOf(astring1[1]);
                        this.ofTranslucentBlocks =
                                OptiFineConfig.limit(this.ofTranslucentBlocks, 0, 2);
                    }

                    if (astring1[0].equals("key_" + this.ofKeyBindZoom.getKeyDescription())) {
                        this.ofKeyBindZoom.setKeyCode(Integer.parseInt(astring1[1]));
                    }
                } catch (Exception exception) {
                    OptiFineConfig.dbg("Skipping bad option: " + text);
                    exception.printStackTrace();
                }
            }

            KeyBinding.resetKeyBindingArrayAndHash();
            bufferedreader.close();
        } catch (Exception exception1) {
            OptiFineConfig.warn("Failed to load options");
            exception1.printStackTrace();
        }
    }

    public void sendSettingsToServer() {
        BK();

        if (this.mc.player != null) {
            int i = 0;

            for (java.util.Iterator<?> __recoveryIterator171_47824 =
                            (this.setModelParts).iterator();
                    (__recoveryIterator171_47824).hasNext(); ) {
                EnumPlayerModelParts enumPlayerModelParts =
                        (EnumPlayerModelParts) (__recoveryIterator171_47824).next();
                i |= enumPlayerModelParts.getPartMask();
            }

            this.mc.player.connection.addToSendQueue(
                    new C15PacketClientSettings(
                            this.language,
                            this.renderDistanceChunks,
                            this.chatVisibility,
                            this.chatColours,
                            i));
        }
    }

    // region String decoding support

    // endregion

    public void resetSettings() {
        this.renderDistanceChunks = 8;
        this.viewBobbing = true;
        this.anaglyph = false;
        this.limitFramerate = (int) GameSettingsOptions.FRAMERATE_LIMIT.getValueMax();
        this.enableVsync = false;
        this.updateVSync();
        this.mipmapLevels = 4;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.clouds = true;
        this.fovSetting = 70.0F;
        this.gammaSetting = 0.0F;
        this.guiScale = 0;
        this.particleSetting = 0;
        this.heldItemTooltips = true;
        this.useVbo = false;
        this.allowBlockAlternatives = true;
        this.forceUnicodeFont = false;
        this.ofFogType = 1;
        this.ofFogStart = 0.8F;
        this.ofMipmapType = 0;
        this.ofOcclusionFancy = false;
        this.ofSmoothFps = false;
        OptiFineConfig.updateAvailableProcessors();
        this.ofSmoothWorld = OptiFineConfig.isSingleProcessor();
        this.ofLazyChunkLoading = OptiFineConfig.isSingleProcessor();
        this.ofFastMath = false;
        this.ofFastRender = false;
        this.ofTranslucentBlocks = 0;
        this.ofDynamicFov = true;
        this.ofDynamicLights = 3;
        this.ofAoLevel = 1.0F;
        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0F;
        this.ofTrees = 0;
        this.ofRain = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofShowFps = false;
        this.ofProfiler = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofVignette = 0;
        this.ofChunkUpdates = 1;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomItems = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofNaturalTextures = false;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofPotionParticles = true;
        this.ofFireworkParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedTextures = true;
        Shaders.setShaderPack(Shaders.packNameNone);
        Shaders.configAntialiasingLevel = 0;
        Shaders.uninit();
        Shaders.storeConfig();
        this.updateWaterOpacity();
        this.mc.refreshResources();
        this.saveOptions();
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public float getSoundLevel(SoundCategory soundCategory) {
        return this.mapSoundLevels.containsKey(soundCategory)
                ? (Float) this.mapSoundLevels.get(soundCategory)
                : 1.0F;
    }

    private static int limit(int p171_0, int[] p171_1) {
        int j = net.minecraft.client.settings.GameSettings.indexOf(p171_0, p171_1);
        return j < 0 ? p171_1[0] : p171_0;
    }

    public static String getKeyDisplayString(int i) {
        return i < 0
                ? I18n.format("key.mouseButton", new Object[] {i + 101})
                : (i < 256
                        ? Keyboard.getKeyName(i)
                        : String.format("%c", (char) (i - 256)).toUpperCase());
    }

    public void setOptionKeyBinding(KeyBinding keyBinding, int i) {
        keyBinding.setKeyCode(i);
        this.saveOptions();
    }

    public void setModelPartEnabled(EnumPlayerModelParts enumPlayerModelParts, boolean flag) {
        {
            BK();

            if (flag) {
                this.setModelParts.add(enumPlayerModelParts);

            } else {

                this.setModelParts.remove(enumPlayerModelParts);
            }
        }

        this.sendSettingsToServer();
    }

    public void saveOptions() {
        BK();

        if (Reflector.HY.exists()) {
            Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
            if (object != null && Reflector.callBoolean(object, Reflector.Ig, new Object[0])) {
                return;
            }
        }

        try {
            PrintWriter printwriter = new PrintWriter(new FileWriter(this.optionsFile));
            printwriter.println("invertYMouse:" + this.invertMouse);
            printwriter.println("mouseSensitivity:" + this.mouseSensitivity);
            printwriter.println("fov:" + (this.fovSetting - 70.0F) / 40.0F);
            printwriter.println("gamma:" + this.gammaSetting);
            printwriter.println("saturation:" + this.saturation);
            printwriter.println("renderDistance:" + this.renderDistanceChunks);
            printwriter.println("guiScale:" + this.guiScale);
            printwriter.println("particles:" + this.particleSetting);
            printwriter.println("bobView:" + this.viewBobbing);
            printwriter.println("anaglyph3d:" + this.anaglyph);
            printwriter.println("maxFps:" + this.limitFramerate);
            printwriter.println("fboEnable:" + this.fboEnable);
            printwriter.println("difficulty:" + this.difficulty.getDifficultyId());
            printwriter.println("fancyGraphics:" + this.fancyGraphics);
            printwriter.println("ao:" + this.ambientOcclusion);
            printwriter.println("renderClouds:" + this.clouds);
            printwriter.println(
                    "resourcePacks:"
                            + net.minecraft.client.settings.GameSettings.gson.toJson(
                                    this.resourcePacks));
            printwriter.println("lastServer:" + this.lastServer);
            printwriter.println("lang:" + this.language);
            printwriter.println("chatVisibility:" + this.chatVisibility.getChatVisibility());
            printwriter.println("chatColors:" + this.chatColours);
            printwriter.println("chatLinks:" + this.chatLinks);
            printwriter.println("chatLinksPrompt:" + this.chatLinksPrompt);
            printwriter.println("chatOpacity:" + this.chatOpacity);
            printwriter.println("snooperEnabled:" + this.snooperEnabled);
            printwriter.println("fullscreen:" + this.fullScreen);
            printwriter.println("enableVsync:" + this.enableVsync);
            printwriter.println("useVbo:" + this.useVbo);
            printwriter.println("hideServerAddress:" + this.hideServerAddress);
            printwriter.println("advancedItemTooltips:" + this.advancedItemTooltips);
            printwriter.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
            printwriter.println("touchscreen:" + this.touchscreen);
            printwriter.println("overrideWidth:" + this.L);
            printwriter.println("overrideHeight:" + this.ni);
            printwriter.println("heldItemTooltips:" + this.heldItemTooltips);
            printwriter.println("chatHeightFocused:" + this.chatHeightFocused);
            printwriter.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
            printwriter.println("chatScale:" + this.chatScale);
            printwriter.println("chatWidth:" + this.chatWidth);
            printwriter.println(
                    "showInventoryAchievementHint:" + this.showInventoryAchievementHint);
            printwriter.println("mipmapLevels:" + this.mipmapLevels);
            printwriter.println("streamBytesPerPixel:" + this.streamBytesPerPixel);
            printwriter.println("streamMicVolume:" + this.streamMicVolume);
            printwriter.println("streamSystemVolume:" + this.streamGameVolume);
            printwriter.println("streamKbps:" + this.streamKbps);
            printwriter.println("streamFps:" + this.streamFps);
            printwriter.println("streamCompression:" + this.streamCompression);
            printwriter.println("streamSendMetadata:" + this.streamSendMetadata);
            printwriter.println("streamPreferredServer:" + this.streamPreferredServer);
            printwriter.println("streamChatEnabled:" + this.streamChatEnabled);
            printwriter.println("streamChatUserFilter:" + this.streamChatUserFilter);
            printwriter.println("streamMicToggleBehavior:" + this.streamMicToggleBehavior);
            printwriter.println("forceUnicodeFont:" + this.forceUnicodeFont);
            printwriter.println("allowBlockAlternatives:" + this.allowBlockAlternatives);
            printwriter.println("reducedDebugInfo:" + this.reducedDebugInfo);

            for (KeyBinding keyBinding : this.keyBindings) {
                printwriter.println(
                        "key_"
                                + keyBinding.getKeyDescription()
                                + packedTextFragments[12]
                                + keyBinding.getKeyCode());
            }

            for (SoundCategory soundCategory : SoundCategory.values()) {
                printwriter.println(
                        "soundCategory_"
                                + soundCategory.getCategoryName()
                                + packedTextFragments[13]
                                + this.getSoundLevel(soundCategory));
            }

            for (EnumPlayerModelParts enumPlayerModelParts : EnumPlayerModelParts.values()) {
                printwriter.println(
                        "modelPart_"
                                + enumPlayerModelParts.getPartName()
                                + packedTextFragments[14]
                                + this.setModelParts.contains(enumPlayerModelParts));
            }

            printwriter.close();
        } catch (Exception exception) {
            net.minecraft.client.settings.GameSettings.logger.error(
                    "Failed to save options", exception);
        }

        this.dn();
        this.sendSettingsToServer();
    }

    public void updateVSync() {
        Display.setVSyncEnabled(this.enableVsync);
    }

    public void setOptionFloatValue(GameSettingsOptions gameSettingsOptions, float axFValue) {
        this.setOptionFloatValueOF(gameSettingsOptions, axFValue);
        if (gameSettingsOptions == GameSettingsOptions.SENSITIVITY) {
            this.mouseSensitivity = axFValue;
        }

        if (gameSettingsOptions == GameSettingsOptions.FOV) {
            this.fovSetting = axFValue;
        }

        if (gameSettingsOptions == GameSettingsOptions.GAMMA) {
            this.gammaSetting = axFValue;
        }

        if (gameSettingsOptions == GameSettingsOptions.FRAMERATE_LIMIT) {
            this.limitFramerate = (int) axFValue;
            this.enableVsync = false;
            if (this.limitFramerate <= 0) {
                this.limitFramerate = (int) GameSettingsOptions.FRAMERATE_LIMIT.getValueMax();
                this.enableVsync = true;
            }

            this.updateVSync();
        }

        if (gameSettingsOptions == GameSettingsOptions.CHAT_OPACITY) {
            this.chatOpacity = axFValue;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }

        if (gameSettingsOptions == GameSettingsOptions.CHAT_HEIGHT_FOCUSED) {
            this.chatHeightFocused = axFValue;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }

        if (gameSettingsOptions == GameSettingsOptions.CHAT_HEIGHT_UNFOCUSED) {
            this.chatHeightUnfocused = axFValue;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }

        if (gameSettingsOptions == GameSettingsOptions.CHAT_WIDTH) {
            this.chatWidth = axFValue;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }

        if (gameSettingsOptions == GameSettingsOptions.CHAT_SCALE) {
            this.chatScale = axFValue;
            this.mc.ingameGUI.getChatGUI().refreshChat();
        }

        if (gameSettingsOptions == GameSettingsOptions.MIPMAP_LEVELS) {
            int i = this.mipmapLevels;
            this.mipmapLevels = (int) axFValue;
            if (i != axFValue) {
                this.mc.getTextureMapBlocks().setMipmapLevels(this.mipmapLevels);
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.mc.getTextureMapBlocks().setBlurMipmapDirect(false, this.mipmapLevels > 0);
                this.mc.scheduleResourcesRefresh();
            }
        }

        if (gameSettingsOptions == GameSettingsOptions.BLOCK_ALTERNATIVES) {
            this.allowBlockAlternatives =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.allowBlockAlternatives ? 1 : 0));
            this.mc.renderGlobal.loadRenderers();
        }

        if (gameSettingsOptions == GameSettingsOptions.RENDER_DISTANCE) {
            this.renderDistanceChunks = (int) axFValue;
            this.mc.renderGlobal.setDisplayListEntitiesDirty();
        }

        if (gameSettingsOptions == GameSettingsOptions.STREAM_BYTES_PER_PIXEL) {
            this.streamBytesPerPixel = axFValue;
        }

        if (gameSettingsOptions == GameSettingsOptions.STREAM_VOLUME_MIC) {
            this.streamMicVolume = axFValue;
            this.mc.getTwitchStream().updateStreamVolume();
        }

        if (gameSettingsOptions == GameSettingsOptions.STREAM_VOLUME_SYSTEM) {
            this.streamGameVolume = axFValue;
            this.mc.getTwitchStream().updateStreamVolume();
        }

        if (gameSettingsOptions == GameSettingsOptions.STREAM_KBPS) {
            this.streamKbps = axFValue;
        }

        if (gameSettingsOptions == GameSettingsOptions.STREAM_FPS) {
            this.streamFps = axFValue;
        }
    }

    public void setOptionValue(GameSettingsOptions gameSettingsOptions, int i) {
        label220:
        {
            label221:
            {
                String[] astring;
                label222:
                {
                    label223:
                    {
                        label224:
                        {
                            label225:
                            {
                                label226:
                                {
                                    label227:
                                    {
                                        label228:
                                        {
                                            label229:
                                            {
                                                label208:
                                                {
                                                    label207:
                                                    {
                                                        label206:
                                                        {
                                                            label230:
                                                            {
                                                                label204:
                                                                {
                                                                    label203:
                                                                    {
                                                                        label202:
                                                                        {
                                                                            label231:
                                                                            {
                                                                                label232:
                                                                                {
                                                                                    String[]
                                                                                            astring1 =
                                                                                                    BK();
                                                                                    this
                                                                                            .setOptionValueOF(
                                                                                                    gameSettingsOptions,
                                                                                                    i);
                                                                                    astring =
                                                                                            astring1;
                                                                                    switch (gameSettingsOptions) {
                                                                                        case INVERT_MOUSE:
                                                                                            this
                                                                                                            .invertMouse =
                                                                                                    remix
                                                                                                            .compat
                                                                                                            .compiler
                                                                                                            .JvmBooleanSlots
                                                                                                            .fromInt(
                                                                                                                    (!this
                                                                                                                                    .invertMouse
                                                                                                                            ? 1
                                                                                                                            : 0));

                                                                                            break
                                                                                                    label220;

                                                                                        case aTo:
                                                                                            this
                                                                                                            .guiScale =
                                                                                                    this
                                                                                                                            .guiScale
                                                                                                                    + i
                                                                                                            & 3;

                                                                                            break
                                                                                                    label220;

                                                                                        case PARTICLES:
                                                                                            this
                                                                                                            .particleSetting =
                                                                                                    (this
                                                                                                                            .particleSetting
                                                                                                                    + i)
                                                                                                            % 3;

                                                                                            break
                                                                                                    label220;

                                                                                        case VIEW_BOBBING:
                                                                                            this
                                                                                                            .viewBobbing =
                                                                                                    remix
                                                                                                            .compat
                                                                                                            .compiler
                                                                                                            .JvmBooleanSlots
                                                                                                            .fromInt(
                                                                                                                    (!this
                                                                                                                                    .viewBobbing
                                                                                                                            ? 1
                                                                                                                            : 0));

                                                                                            break
                                                                                                    label220;

                                                                                        case RENDER_CLOUDS:
                                                                                            this
                                                                                                            .clouds =
                                                                                                    remix
                                                                                                            .compat
                                                                                                            .compiler
                                                                                                            .JvmBooleanSlots
                                                                                                            .fromInt(
                                                                                                                    (!this
                                                                                                                                    .clouds
                                                                                                                            ? 1
                                                                                                                            : 0));

                                                                                            break
                                                                                                    label220;

                                                                                        case FORCE_UNICODE_FONT:
                                                                                            this
                                                                                                            .forceUnicodeFont =
                                                                                                    remix
                                                                                                            .compat
                                                                                                            .compiler
                                                                                                            .JvmBooleanSlots
                                                                                                            .fromInt(
                                                                                                                    (!this
                                                                                                                                    .forceUnicodeFont
                                                                                                                            ? 1
                                                                                                                            : 0));
                                                                                            this.mc
                                                                                                    .fontRendererObj
                                                                                                    .setUnicodeFlag(
                                                                                                            this
                                                                                                                            .mc
                                                                                                                            .getLanguageManager()
                                                                                                                            .isCurrentLocaleUnicode()
                                                                                                                    || this
                                                                                                                            .forceUnicodeFont);

                                                                                            break
                                                                                                    label220;

                                                                                        case FBO_ENABLE:
                                                                                            this
                                                                                                            .fboEnable =
                                                                                                    remix
                                                                                                            .compat
                                                                                                            .compiler
                                                                                                            .JvmBooleanSlots
                                                                                                            .fromInt(
                                                                                                                    (!this
                                                                                                                                    .fboEnable
                                                                                                                            ? 1
                                                                                                                            : 0));

                                                                                            break
                                                                                                    label220;

                                                                                        case ANAGLYPH:
                                                                                            break;
                                                                                        case aRM:
                                                                                            break
                                                                                                    label232;
                                                                                        case aRU:
                                                                                            break
                                                                                                    label231;
                                                                                        case CHAT_VISIBILITY:
                                                                                            break
                                                                                                    label202;
                                                                                        case STREAM_COMPRESSION:
                                                                                            break
                                                                                                    label203;
                                                                                        case STREAM_SEND_METADATA:
                                                                                            break
                                                                                                    label204;
                                                                                        case STREAM_CHAT_ENABLED:
                                                                                            break
                                                                                                    label230;
                                                                                        case STREAM_CHAT_USER_FILTER:
                                                                                            break
                                                                                                    label206;
                                                                                        case STREAM_MIC_TOGGLE_BEHAVIOR:
                                                                                            break
                                                                                                    label207;
                                                                                        case CHAT_COLOR:
                                                                                            break
                                                                                                    label208;
                                                                                        case CHAT_LINKS:
                                                                                            break
                                                                                                    label229;
                                                                                        case CHAT_LINKS_PROMPT:
                                                                                            break
                                                                                                    label228;
                                                                                        case SNOOPER_ENABLED:
                                                                                            break
                                                                                                    label227;
                                                                                        case TOUCHSCREEN:
                                                                                            break
                                                                                                    label226;
                                                                                        case USE_FULLSCREEN:
                                                                                            break
                                                                                                    label225;
                                                                                        case ENABLE_VSYNC:
                                                                                            break
                                                                                                    label224;
                                                                                        case USE_VBO:
                                                                                            break
                                                                                                    label223;
                                                                                        case BLOCK_ALTERNATIVES:
                                                                                            break
                                                                                                    label222;
                                                                                        case REDUCED_DEBUG_INFO:
                                                                                            break
                                                                                                    label221;
                                                                                        default:
                                                                                            break
                                                                                                    label220;
                                                                                    }

                                                                                    this.anaglyph =
                                                                                            remix
                                                                                                    .compat
                                                                                                    .compiler
                                                                                                    .JvmBooleanSlots
                                                                                                    .fromInt(
                                                                                                            (!this
                                                                                                                            .anaglyph
                                                                                                                    ? 1
                                                                                                                    : 0));
                                                                                    this.mc
                                                                                            .refreshResources();

                                                                                    break label220;
                                                                                }

                                                                                this.fancyGraphics =
                                                                                        remix.compat
                                                                                                .compiler
                                                                                                .JvmBooleanSlots
                                                                                                .fromInt(
                                                                                                        (!this
                                                                                                                        .fancyGraphics
                                                                                                                ? 1
                                                                                                                : 0));
                                                                                this
                                                                                        .updateRenderClouds();
                                                                                this.mc.renderGlobal
                                                                                        .loadRenderers();

                                                                                break label220;
                                                                            }

                                                                            this.ambientOcclusion =
                                                                                    (this
                                                                                                            .ambientOcclusion
                                                                                                    + i)
                                                                                            % 3;
                                                                            this.mc.renderGlobal
                                                                                    .loadRenderers();

                                                                            break label220;
                                                                        }

                                                                        this.chatVisibility =
                                                                                EntityPlayerEnumChatVisibility
                                                                                        .getEnumChatVisibility(
                                                                                                (this
                                                                                                                        .chatVisibility
                                                                                                                        .getChatVisibility()
                                                                                                                + i)
                                                                                                        % 3);

                                                                        break label220;
                                                                    }

                                                                    this.streamCompression =
                                                                            (this.streamCompression
                                                                                            + i)
                                                                                    % 3;

                                                                    break label220;
                                                                }

                                                                this.streamSendMetadata =
                                                                        remix.compat.compiler
                                                                                .JvmBooleanSlots
                                                                                .fromInt(
                                                                                        (!this
                                                                                                        .streamSendMetadata
                                                                                                ? 1
                                                                                                : 0));

                                                                break label220;
                                                            }

                                                            this.streamChatEnabled =
                                                                    (this.streamChatEnabled + i)
                                                                            % 3;

                                                            break label220;
                                                        }

                                                        this.streamChatUserFilter =
                                                                (this.streamChatUserFilter + i) % 3;

                                                        break label220;
                                                    }

                                                    this.streamMicToggleBehavior =
                                                            (this.streamMicToggleBehavior + i) % 2;

                                                    break label220;
                                                }

                                                this.chatColours =
                                                        remix.compat.compiler.JvmBooleanSlots
                                                                .fromInt(
                                                                        (!this.chatColours
                                                                                ? 1
                                                                                : 0));

                                                break label220;
                                            }

                                            this.chatLinks =
                                                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                                                            (!this.chatLinks ? 1 : 0));

                                            break label220;
                                        }

                                        this.chatLinksPrompt =
                                                remix.compat.compiler.JvmBooleanSlots.fromInt(
                                                        (!this.chatLinksPrompt ? 1 : 0));

                                        break label220;
                                    }

                                    this.snooperEnabled =
                                            remix.compat.compiler.JvmBooleanSlots.fromInt(
                                                    (!this.snooperEnabled ? 1 : 0));

                                    break label220;
                                }

                                this.touchscreen =
                                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                                (!this.touchscreen ? 1 : 0));

                                break label220;
                            }

                            this.fullScreen =
                                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                                            (!this.fullScreen ? 1 : 0));
                            if (this.mc.isFullScreen() == this.fullScreen) {
                                break label220;
                            }

                            this.mc.toggleFullscreen();

                            break label220;
                        }

                        this.enableVsync =
                                remix.compat.compiler.JvmBooleanSlots.fromInt(
                                        (!this.enableVsync ? 1 : 0));
                        Display.setVSyncEnabled(this.enableVsync);

                        break label220;
                    }

                    this.useVbo =
                            remix.compat.compiler.JvmBooleanSlots.fromInt((!this.useVbo ? 1 : 0));
                    this.mc.renderGlobal.loadRenderers();

                    break label220;
                }

                this.allowBlockAlternatives =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!this.allowBlockAlternatives ? 1 : 0));
                this.mc.renderGlobal.loadRenderers();

                break label220;
            }

            this.reducedDebugInfo =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.reducedDebugInfo ? 1 : 0));
        }

        this.saveOptions();
    }

    public void setSoundLevel(SoundCategory soundCategory, float f) {
        this.mc.getSoundHandler().setSoundLevel(soundCategory, f);
        this.mapSoundLevels.put(soundCategory, f);
    }

    public void method_8d9b536bcdc9991c48fb() {
        BK();

        try {
            if (!this.optionsFile.exists()) {
                return;
            }

            BufferedReader bufferedreader = new BufferedReader(new FileReader(this.optionsFile));
            String text = packedTextFragments[10];
            this.mapSoundLevels.clear();

            while ((text = bufferedreader.readLine()) != null) {
                try {
                    String[] astring1 = text.split(packedTextFragments[11]);
                    if (astring1[0].equals("mouseSensitivity")) {
                        this.mouseSensitivity = this.parseFloat(astring1[1]);
                    }

                    if (astring1[0].equals("fov")) {
                        this.fovSetting = this.parseFloat(astring1[1]) * 40.0F + 70.0F;
                    }

                    if (astring1[0].equals("gamma")) {
                        this.gammaSetting = this.parseFloat(astring1[1]);
                    }

                    if (astring1[0].equals("saturation")) {
                        this.saturation = this.parseFloat(astring1[1]);
                    }

                    if (astring1[0].equals("invertYMouse")) {
                        this.invertMouse = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("renderDistance")) {
                        this.renderDistanceChunks = Integer.parseInt(astring1[1]);
                    }

                    if (astring1[0].equals("guiScale")) {
                        this.guiScale = Integer.parseInt(astring1[1]);
                    }

                    if (astring1[0].equals("particles")) {
                        this.particleSetting = Integer.parseInt(astring1[1]);
                    }

                    if (astring1[0].equals("bobView")) {
                        this.viewBobbing = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("anaglyph3d")) {
                        this.anaglyph = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("maxFps")) {
                        this.limitFramerate = Integer.parseInt(astring1[1]);
                        this.enableVsync = false;
                        if (this.limitFramerate <= 0) {
                            this.limitFramerate =
                                    (int) GameSettingsOptions.FRAMERATE_LIMIT.getValueMax();
                            this.enableVsync = true;
                        }

                        this.updateVSync();
                    }

                    if (astring1[0].equals("fboEnable")) {
                        this.fboEnable = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("difficulty")) {
                        this.difficulty =
                                EnumDifficulty.getDifficultyEnum(Integer.parseInt(astring1[1]));
                    }

                    if (astring1[0].equals("fancyGraphics")) {
                        this.fancyGraphics = astring1[1].equals("true");
                        this.updateRenderClouds();
                    }

                    if (astring1[0].equals("ao")) {
                        if (astring1[1].equals("true")) {
                            this.ambientOcclusion = 2;

                        } else {

                            if (astring1[1].equals("false")) {
                                this.ambientOcclusion = 0;

                            } else {

                                this.ambientOcclusion = Integer.parseInt(astring1[1]);
                            }
                        }
                    }

                    if (astring1[0].equals("renderClouds")) {
                        this.clouds = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("resourcePacks")) {
                        this.resourcePacks =
                                (List)
                                        net.minecraft.client.settings.GameSettings.gson.fromJson(
                                                text.substring(text.indexOf(58) + 1),
                                                net.minecraft.client.settings.GameSettings
                                                        .typeListString);
                        if (this.resourcePacks == null) {
                            this.resourcePacks = Lists.newArrayList();
                        }
                    }

                    if (astring1[0].equals("lastServer") && astring1.length >= 2) {
                        this.lastServer = text.substring(text.indexOf(58) + 1);
                    }

                    if (astring1[0].equals("lang") && astring1.length >= 2) {
                        this.language = astring1[1];
                    }

                    if (astring1[0].equals("chatVisibility")) {
                        this.chatVisibility =
                                EntityPlayerEnumChatVisibility.getEnumChatVisibility(
                                        Integer.parseInt(astring1[1]));
                    }

                    if (astring1[0].equals("chatColors")) {
                        this.chatColours = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("chatLinks")) {
                        this.chatLinks = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("chatLinksPrompt")) {
                        this.chatLinksPrompt = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("chatOpacity")) {
                        this.chatOpacity = this.parseFloat(astring1[1]);
                    }

                    if (astring1[0].equals("snooperEnabled")) {
                        this.snooperEnabled = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("fullscreen")) {
                        this.fullScreen = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("enableVsync")) {
                        this.enableVsync = astring1[1].equals("true");
                        this.updateVSync();
                    }

                    if (astring1[0].equals("useVbo")) {
                        this.useVbo = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("hideServerAddress")) {
                        this.hideServerAddress = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("advancedItemTooltips")) {
                        this.advancedItemTooltips = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("pauseOnLostFocus")) {
                        this.pauseOnLostFocus = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("touchscreen")) {
                        this.touchscreen = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("overrideHeight")) {
                        this.ni = Integer.parseInt(astring1[1]);
                    }

                    if (astring1[0].equals("overrideWidth")) {
                        this.L = Integer.parseInt(astring1[1]);
                    }

                    if (astring1[0].equals("heldItemTooltips")) {
                        this.heldItemTooltips = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("chatHeightFocused")) {
                        this.chatHeightFocused = this.parseFloat(astring1[1]);
                    }

                    if (astring1[0].equals("chatHeightUnfocused")) {
                        this.chatHeightUnfocused = this.parseFloat(astring1[1]);
                    }

                    if (astring1[0].equals("chatScale")) {
                        this.chatScale = this.parseFloat(astring1[1]);
                    }

                    if (astring1[0].equals("chatWidth")) {
                        this.chatWidth = this.parseFloat(astring1[1]);
                    }

                    if (astring1[0].equals("showInventoryAchievementHint")) {
                        this.showInventoryAchievementHint = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("mipmapLevels")) {
                        this.mipmapLevels = Integer.parseInt(astring1[1]);
                    }

                    if (astring1[0].equals("streamBytesPerPixel")) {
                        this.streamBytesPerPixel = this.parseFloat(astring1[1]);
                    }

                    if (astring1[0].equals("streamMicVolume")) {
                        this.streamMicVolume = this.parseFloat(astring1[1]);
                    }

                    if (astring1[0].equals("streamSystemVolume")) {
                        this.streamGameVolume = this.parseFloat(astring1[1]);
                    }

                    if (astring1[0].equals("streamKbps")) {
                        this.streamKbps = this.parseFloat(astring1[1]);
                    }

                    if (astring1[0].equals("streamFps")) {
                        this.streamFps = this.parseFloat(astring1[1]);
                    }

                    if (astring1[0].equals("streamCompression")) {
                        this.streamCompression = Integer.parseInt(astring1[1]);
                    }

                    if (astring1[0].equals("streamSendMetadata")) {
                        this.streamSendMetadata = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("streamPreferredServer") && astring1.length >= 2) {
                        this.streamPreferredServer = text.substring(text.indexOf(58) + 1);
                    }

                    if (astring1[0].equals("streamChatEnabled")) {
                        this.streamChatEnabled = Integer.parseInt(astring1[1]);
                    }

                    if (astring1[0].equals("streamChatUserFilter")) {
                        this.streamChatUserFilter = Integer.parseInt(astring1[1]);
                    }

                    if (astring1[0].equals("streamMicToggleBehavior")) {
                        this.streamMicToggleBehavior = Integer.parseInt(astring1[1]);
                    }

                    if (astring1[0].equals("forceUnicodeFont")) {
                        this.forceUnicodeFont = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("allowBlockAlternatives")) {
                        this.allowBlockAlternatives = astring1[1].equals("true");
                    }

                    if (astring1[0].equals("reducedDebugInfo")) {
                        this.reducedDebugInfo = astring1[1].equals("true");
                    }

                    for (KeyBinding keyBinding : this.keyBindings) {
                        if (astring1[0].equals("key_" + keyBinding.getKeyDescription())) {
                            keyBinding.setKeyCode(Integer.parseInt(astring1[1]));
                        }
                    }

                    for (SoundCategory soundCategory : SoundCategory.values()) {
                        if (astring1[0].equals(
                                "soundCategory_" + soundCategory.getCategoryName())) {
                            this.mapSoundLevels.put(soundCategory, this.parseFloat(astring1[1]));
                        }
                    }

                    for (EnumPlayerModelParts enumPlayerModelParts :
                            EnumPlayerModelParts.values()) {
                        if (astring1[0].equals("modelPart_" + enumPlayerModelParts.getPartName())) {
                            this.setModelPartEnabled(
                                    enumPlayerModelParts, astring1[1].equals("true"));
                        }
                    }
                } catch (Exception exception) {
                    net.minecraft.client.settings.GameSettings.logger.warn(
                            "Skipping bad option: " + text);
                    exception.printStackTrace();
                }
            }

            KeyBinding.resetKeyBindingArrayAndHash();
            bufferedreader.close();
        } catch (Exception exception1) {
            net.minecraft.client.settings.GameSettings.logger.error(
                    "Failed to load options", exception1);
        }

        this.cS();
    }

    public boolean shouldRenderClouds() {
        return this.renderDistanceChunks >= 4 && this.clouds;
    }

    public float getOptionFloatValue(GameSettingsOptions gameSettingsOptions) {
        return gameSettingsOptions == net.minecraft.client.settings.GameSettingsOptions.CLOUD_HEIGHT
                ? this.ofCloudsHeight
                : (gameSettingsOptions == net.minecraft.client.settings.GameSettingsOptions.AO_LEVEL
                        ? this.ofAoLevel
                        : (gameSettingsOptions
                                        == net.minecraft.client.settings.GameSettingsOptions
                                                .AA_LEVEL
                                ? this.ofAaLevel
                                : (gameSettingsOptions
                                                == net.minecraft.client.settings.GameSettingsOptions
                                                        .AF_LEVEL
                                        ? this.ofAfLevel
                                        : (gameSettingsOptions
                                                        == net.minecraft.client.settings
                                                                .GameSettingsOptions.MIPMAP_TYPE
                                                ? this.ofMipmapType
                                                : (gameSettingsOptions
                                                                == net.minecraft.client.settings
                                                                        .GameSettingsOptions
                                                                        .FRAMERATE_LIMIT
                                                        ? (this.limitFramerate
                                                                                == net.minecraft
                                                                                        .client
                                                                                        .settings
                                                                                        .GameSettingsOptions
                                                                                        .FRAMERATE_LIMIT
                                                                                        .getValueMax()
                                                                        && this.enableVsync
                                                                ? 0.0F
                                                                : this.limitFramerate)
                                                        : (gameSettingsOptions
                                                                        == net.minecraft.client
                                                                                .settings
                                                                                .GameSettingsOptions
                                                                                .FOV
                                                                ? this.fovSetting
                                                                : (gameSettingsOptions
                                                                                == net.minecraft
                                                                                        .client
                                                                                        .settings
                                                                                        .GameSettingsOptions
                                                                                        .GAMMA
                                                                        ? this.gammaSetting
                                                                        : (gameSettingsOptions
                                                                                        == net
                                                                                                .minecraft
                                                                                                .client
                                                                                                .settings
                                                                                                .GameSettingsOptions
                                                                                                .SATURATION
                                                                                ? this.saturation
                                                                                : (gameSettingsOptions
                                                                                                == net
                                                                                                        .minecraft
                                                                                                        .client
                                                                                                        .settings
                                                                                                        .GameSettingsOptions
                                                                                                        .SENSITIVITY
                                                                                        ? this
                                                                                                .mouseSensitivity
                                                                                        : (gameSettingsOptions
                                                                                                        == net
                                                                                                                .minecraft
                                                                                                                .client
                                                                                                                .settings
                                                                                                                .GameSettingsOptions
                                                                                                                .CHAT_OPACITY
                                                                                                ? this
                                                                                                        .chatOpacity
                                                                                                : (gameSettingsOptions
                                                                                                                == net
                                                                                                                        .minecraft
                                                                                                                        .client
                                                                                                                        .settings
                                                                                                                        .GameSettingsOptions
                                                                                                                        .CHAT_HEIGHT_FOCUSED
                                                                                                        ? this
                                                                                                                .chatHeightFocused
                                                                                                        : (gameSettingsOptions
                                                                                                                        == net
                                                                                                                                .minecraft
                                                                                                                                .client
                                                                                                                                .settings
                                                                                                                                .GameSettingsOptions
                                                                                                                                .CHAT_HEIGHT_UNFOCUSED
                                                                                                                ? this
                                                                                                                        .chatHeightUnfocused
                                                                                                                : (gameSettingsOptions
                                                                                                                                == net
                                                                                                                                        .minecraft
                                                                                                                                        .client
                                                                                                                                        .settings
                                                                                                                                        .GameSettingsOptions
                                                                                                                                        .CHAT_SCALE
                                                                                                                        ? this
                                                                                                                                .chatScale
                                                                                                                        : (gameSettingsOptions
                                                                                                                                        == net
                                                                                                                                                .minecraft
                                                                                                                                                .client
                                                                                                                                                .settings
                                                                                                                                                .GameSettingsOptions
                                                                                                                                                .CHAT_WIDTH
                                                                                                                                ? this
                                                                                                                                        .chatWidth
                                                                                                                                : (gameSettingsOptions
                                                                                                                                                == net
                                                                                                                                                        .minecraft
                                                                                                                                                        .client
                                                                                                                                                        .settings
                                                                                                                                                        .GameSettingsOptions
                                                                                                                                                        .FRAMERATE_LIMIT
                                                                                                                                        ? this
                                                                                                                                                .limitFramerate
                                                                                                                                        : (gameSettingsOptions
                                                                                                                                                        == net
                                                                                                                                                                .minecraft
                                                                                                                                                                .client
                                                                                                                                                                .settings
                                                                                                                                                                .GameSettingsOptions
                                                                                                                                                                .MIPMAP_LEVELS
                                                                                                                                                ? this
                                                                                                                                                        .mipmapLevels
                                                                                                                                                : (gameSettingsOptions
                                                                                                                                                                == net
                                                                                                                                                                        .minecraft
                                                                                                                                                                        .client
                                                                                                                                                                        .settings
                                                                                                                                                                        .GameSettingsOptions
                                                                                                                                                                        .RENDER_DISTANCE
                                                                                                                                                        ? this
                                                                                                                                                                .renderDistanceChunks
                                                                                                                                                        : (gameSettingsOptions
                                                                                                                                                                        == net
                                                                                                                                                                                .minecraft
                                                                                                                                                                                .client
                                                                                                                                                                                .settings
                                                                                                                                                                                .GameSettingsOptions
                                                                                                                                                                                .STREAM_BYTES_PER_PIXEL
                                                                                                                                                                ? this
                                                                                                                                                                        .streamBytesPerPixel
                                                                                                                                                                : (gameSettingsOptions
                                                                                                                                                                                == net
                                                                                                                                                                                        .minecraft
                                                                                                                                                                                        .client
                                                                                                                                                                                        .settings
                                                                                                                                                                                        .GameSettingsOptions
                                                                                                                                                                                        .STREAM_VOLUME_MIC
                                                                                                                                                                        ? this
                                                                                                                                                                                .streamMicVolume
                                                                                                                                                                        : (gameSettingsOptions
                                                                                                                                                                                        == net
                                                                                                                                                                                                .minecraft
                                                                                                                                                                                                .client
                                                                                                                                                                                                .settings
                                                                                                                                                                                                .GameSettingsOptions
                                                                                                                                                                                                .STREAM_VOLUME_SYSTEM
                                                                                                                                                                                ? this
                                                                                                                                                                                        .streamGameVolume
                                                                                                                                                                                : (gameSettingsOptions
                                                                                                                                                                                                == net
                                                                                                                                                                                                        .minecraft
                                                                                                                                                                                                        .client
                                                                                                                                                                                                        .settings
                                                                                                                                                                                                        .GameSettingsOptions
                                                                                                                                                                                                        .STREAM_KBPS
                                                                                                                                                                                        ? this
                                                                                                                                                                                                .streamKbps
                                                                                                                                                                                        : (gameSettingsOptions
                                                                                                                                                                                                        == net
                                                                                                                                                                                                                .minecraft
                                                                                                                                                                                                                .client
                                                                                                                                                                                                                .settings
                                                                                                                                                                                                                .GameSettingsOptions
                                                                                                                                                                                                                .STREAM_FPS
                                                                                                                                                                                                ? this
                                                                                                                                                                                                        .streamFps
                                                                                                                                                                                                : 0.0F))))))))))))))))))))));
    }

    public GameSettings() {
        BK();
        this.renderDistanceChunks = -1;
        this.viewBobbing = true;
        this.fboEnable = true;
        this.limitFramerate = 120;
        this.clouds = true;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.resourcePacks = Lists.newArrayList();
        this.ofFogType = 1;
        this.ofFogStart = 0.8F;
        this.ofMipmapType = 0;
        this.ofOcclusionFancy = false;
        this.ofSmoothFps = false;
        this.ofSmoothWorld = OptiFineConfig.isSingleProcessor();
        this.ofLazyChunkLoading = OptiFineConfig.isSingleProcessor();
        this.ofAoLevel = 1.0F;
        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0F;
        this.ofTrees = 0;
        this.ofRain = 0;
        this.ofDroppedItems = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofProfiler = false;
        this.ofShowFps = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofVignette = 0;
        this.ofChunkUpdates = 1;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofCustomItems = true;
        this.ofNaturalTextures = false;
        this.ofFastMath = false;
        this.ofFastRender = true;
        this.ofTranslucentBlocks = 0;
        this.ofDynamicFov = true;
        this.ofDynamicLights = 3;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofPotionParticles = true;
        this.ofFireworkParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedTextures = true;
        this.chatVisibility = EntityPlayerEnumChatVisibility.FULL;
        this.chatColours = true;
        this.chatLinks = true;
        this.chatLinksPrompt = true;
        this.chatOpacity = 1.0F;
        this.snooperEnabled = true;
        this.enableVsync = true;
        this.useVbo = false;
        this.allowBlockAlternatives = true;
        this.reducedDebugInfo = false;
        this.pauseOnLostFocus = true;
        this.setModelParts = Sets.newHashSet(EnumPlayerModelParts.values());
        this.heldItemTooltips = true;
        this.chatScale = 1.0F;
        this.chatWidth = 1.0F;
        this.chatHeightUnfocused = 0.44366196F;
        this.chatHeightFocused = 1.0F;
        this.showInventoryAchievementHint = true;
        this.mipmapLevels = 4;
        this.mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
        this.streamBytesPerPixel = 0.5F;
        this.streamMicVolume = 1.0F;
        this.streamGameVolume = 1.0F;
        this.streamKbps = 0.5412844F;
        this.streamFps = 0.31690142F;
        this.streamCompression = 1;
        this.streamSendMetadata = true;
        this.streamPreferredServer = packedTextFragments[2];
        this.streamChatEnabled = 0;
        this.streamChatUserFilter = 0;
        this.streamMicToggleBehavior = 0;
        this.keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
        this.keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
        this.keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
        this.keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
        this.keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
        this.keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
        this.keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
        this.keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
        this.keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
        this.keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
        this.keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
        this.keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
        this.keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
        this.keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
        this.keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
        this.keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
        this.keyBindTogglePerspective =
                new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
        this.keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
        this.keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
        this.keyBindSpectatorOutlines =
                new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
        this.keyBindStreamStartStop =
                new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
        this.keyBindStreamPauseUnpause =
                new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
        this.keyBindStreamCommercials =
                new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
        this.keyBindStreamToggleMic =
                new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
        this.keyBindsHotbar =
                new KeyBinding[] {
                    new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.9", 10, "key.categories.inventory")
                };
        this.keyBindings =
                (KeyBinding[])
                        ArrayUtils.addAll(
                                new KeyBinding[] {
                                    this.keyBindAttack, this.keyBindUseItem, this.keyBindForward,
                                            this.keyBindLeft, this.keyBindBack, this.keyBindRight,
                                    this.keyBindJump, this.keyBindSneak, this.keyBindDrop,
                                            this.keyBindInventory, this.keyBindChat,
                                            this.keyBindPlayerList,
                                    this.keyBindPickBlock, this.keyBindCommand,
                                            this.keyBindScreenshot, this.keyBindTogglePerspective,
                                            this.keyBindSmoothCamera, this.keyBindSprint,
                                    this.keyBindStreamStartStop, this.keyBindStreamPauseUnpause,
                                            this.keyBindStreamCommercials,
                                            this.keyBindStreamToggleMic, this.keyBindFullscreen,
                                            this.keyBindSpectatorOutlines
                                },
                                this.keyBindsHotbar);
        this.difficulty = EnumDifficulty.NORMAL;
        this.lastServer = packedTextFragments[3];
        this.fovSetting = 70.0F;
        this.language = "en_US";
        this.forceUnicodeFont = false;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(new String[4]);
        }
    }

    private void setOptionValueOF(GameSettingsOptions gameSettingsOptions, int j) {
        BK();

        if (gameSettingsOptions == GameSettingsOptions.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1:
                    this.ofFogType = 2;
                    if (OptiFineConfig.isFancyFogAvailable()) {
                        break;
                    }

                    this.ofFogType = 3;

                    break;

                case 2:
                    this.ofFogType = 3;

                    break;

                case 3:
                    this.ofFogType = 1;

                    break;

                default:
                    this.ofFogType = 1;
            }
        }

        if (gameSettingsOptions == GameSettingsOptions.FOG_START) {
            this.ofFogStart += 0.2F;
            if (this.ofFogStart > 0.81F) {
                this.ofFogStart = 0.2F;
            }
        }

        if (gameSettingsOptions == GameSettingsOptions.SMOOTH_FPS) {
            this.ofSmoothFps =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofSmoothFps ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.SMOOTH_WORLD) {
            this.ofSmoothWorld =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofSmoothWorld ? 1 : 0));
            OptiFineConfig.updateThreadPriorities();
        }

        if (gameSettingsOptions == GameSettingsOptions.CLOUDS) {
            this.ofClouds++;
            if (this.ofClouds > 3) {
                this.ofClouds = 0;
            }

            this.updateRenderClouds();
            this.mc.renderGlobal.resetClouds();
        }

        if (gameSettingsOptions == GameSettingsOptions.TREES) {
            this.ofTrees =
                    nextValue(
                            this.ofTrees,
                            net.minecraft.client.settings.GameSettings.OF_TREES_VALUES);
            this.mc.renderGlobal.loadRenderers();
        }

        if (gameSettingsOptions == GameSettingsOptions.DROPPED_ITEMS) {
            this.ofDroppedItems++;
            if (this.ofDroppedItems > 2) {
                this.ofDroppedItems = 0;
            }
        }

        if (gameSettingsOptions == GameSettingsOptions.RAIN) {
            this.ofRain++;
            if (this.ofRain > 3) {
                this.ofRain = 0;
            }
        }

        if (gameSettingsOptions == GameSettingsOptions.ANIMATED_WATER) {
            this.ofAnimatedWater++;
            if (this.ofAnimatedWater == 1) {
                this.ofAnimatedWater++;
            }

            if (this.ofAnimatedWater > 2) {
                this.ofAnimatedWater = 0;
            }
        }

        if (gameSettingsOptions == GameSettingsOptions.ANIMATED_LAVA) {
            this.ofAnimatedLava++;
            if (this.ofAnimatedLava == 1) {
                this.ofAnimatedLava++;
            }

            if (this.ofAnimatedLava > 2) {
                this.ofAnimatedLava = 0;
            }
        }

        if (gameSettingsOptions == GameSettingsOptions.ANIMATED_FIRE) {
            this.ofAnimatedFire =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofAnimatedFire ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.ANIMATED_PORTAL) {
            this.ofAnimatedPortal =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofAnimatedPortal ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.ANIMATED_REDSTONE) {
            this.ofAnimatedRedstone =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.ofAnimatedRedstone ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.ANIMATED_EXPLOSION) {
            this.ofAnimatedExplosion =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.ofAnimatedExplosion ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.ANIMATED_FLAME) {
            this.ofAnimatedFlame =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofAnimatedFlame ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.ANIMATED_SMOKE) {
            this.ofAnimatedSmoke =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofAnimatedSmoke ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.VOID_PARTICLES) {
            this.ofVoidParticles =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofVoidParticles ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.WATER_PARTICLES) {
            this.ofWaterParticles =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofWaterParticles ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.PORTAL_PARTICLES) {
            this.ofPortalParticles =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.ofPortalParticles ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.POTION_PARTICLES) {
            this.ofPotionParticles =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.ofPotionParticles ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.FIREWORK_PARTICLES) {
            this.ofFireworkParticles =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.ofFireworkParticles ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.DRIPPING_WATER_LAVA) {
            this.ofDrippingWaterLava =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.ofDrippingWaterLava ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.ANIMATED_TERRAIN) {
            this.ofAnimatedTerrain =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.ofAnimatedTerrain ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.ANIMATED_TEXTURES) {
            this.ofAnimatedTextures =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.ofAnimatedTextures ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.RAIN_SPLASH) {
            this.ofRainSplash =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofRainSplash ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.LAGOMETER) {
            this.ofLagometer =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofLagometer ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.SHOW_FPS) {
            this.ofShowFps =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofShowFps ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.AUTOSAVE_TICKS) {
            this.ofAutoSaveTicks *= 10;
            if (this.ofAutoSaveTicks > 40000) {
                this.ofAutoSaveTicks = 40;
            }
        }

        if (gameSettingsOptions == GameSettingsOptions.BETTER_GRASS) {
            this.ofBetterGrass++;
            if (this.ofBetterGrass > 3) {
                this.ofBetterGrass = 1;
            }

            this.mc.renderGlobal.loadRenderers();
        }

        if (gameSettingsOptions == GameSettingsOptions.CONNECTED_TEXTURES) {
            this.ofConnectedTextures++;
            if (this.ofConnectedTextures > 3) {
                this.ofConnectedTextures = 1;
            }

            if (this.ofConnectedTextures != 2) {
                this.mc.refreshResources();
            }
        }

        if (gameSettingsOptions == GameSettingsOptions.WEATHER) {
            this.ofWeather =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofWeather ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.SKY) {
            this.ofSky = remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofSky ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.STARS) {
            this.ofStars = remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofStars ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.SUN_MOON) {
            this.ofSunMoon =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofSunMoon ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.VIGNETTE) {
            this.ofVignette++;
            if (this.ofVignette > 2) {
                this.ofVignette = 0;
            }
        }

        if (gameSettingsOptions == GameSettingsOptions.CHUNK_UPDATES) {
            this.ofChunkUpdates++;
            if (this.ofChunkUpdates > 5) {
                this.ofChunkUpdates = 1;
            }
        }

        if (gameSettingsOptions == GameSettingsOptions.CHUNK_UPDATES_DYNAMIC) {
            this.ofChunkUpdatesDynamic =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.ofChunkUpdatesDynamic ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.TIME) {
            this.ofTime++;
            if (this.ofTime > 2) {
                this.ofTime = 0;
            }
        }

        if (gameSettingsOptions == GameSettingsOptions.CLEAR_WATER) {
            this.ofClearWater =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofClearWater ? 1 : 0));
            this.updateWaterOpacity();
        }

        if (gameSettingsOptions == GameSettingsOptions.PROFILER) {
            this.ofProfiler =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofProfiler ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.BETTER_SNOW) {
            this.ofBetterSnow =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofBetterSnow ? 1 : 0));
            this.mc.renderGlobal.loadRenderers();
        }

        if (gameSettingsOptions == GameSettingsOptions.SWAMP_COLORS) {
            this.ofSwampColors =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofSwampColors ? 1 : 0));
            CustomColors.updateUseDefaultGrassFoliageColors();
            this.mc.renderGlobal.loadRenderers();
        }

        if (gameSettingsOptions == GameSettingsOptions.RANDOM_MOBS) {
            this.ofRandomMobs =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofRandomMobs ? 1 : 0));
            RandomMobs.resetTextures();
        }

        if (gameSettingsOptions == GameSettingsOptions.SMOOTH_BIOMES) {
            this.ofSmoothBiomes =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofSmoothBiomes ? 1 : 0));
            CustomColors.updateUseDefaultGrassFoliageColors();
            this.mc.renderGlobal.loadRenderers();
        }

        if (gameSettingsOptions == GameSettingsOptions.CUSTOM_FONTS) {
            this.ofCustomFonts =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofCustomFonts ? 1 : 0));
            this.mc.fontRendererObj.onResourceManagerReload(OptiFineConfig.getResourceManager());
            this.mc.standardGalacticFontRenderer.onResourceManagerReload(
                    OptiFineConfig.getResourceManager());
        }

        if (gameSettingsOptions == GameSettingsOptions.CUSTOM_COLORS) {
            this.ofCustomColors =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofCustomColors ? 1 : 0));
            CustomColors.update();
            this.mc.renderGlobal.loadRenderers();
        }

        if (gameSettingsOptions == GameSettingsOptions.CUSTOM_ITEMS) {
            this.ofCustomItems =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofCustomItems ? 1 : 0));
            this.mc.refreshResources();
        }

        if (gameSettingsOptions == GameSettingsOptions.CUSTOM_SKY) {
            this.ofCustomSky =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofCustomSky ? 1 : 0));
            CustomSky.update();
        }

        if (gameSettingsOptions == GameSettingsOptions.SHOW_CAPES) {
            this.ofShowCapes =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofShowCapes ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.NATURAL_TEXTURES) {
            this.ofNaturalTextures =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.ofNaturalTextures ? 1 : 0));
            NaturalTextures.update();
            this.mc.renderGlobal.loadRenderers();
        }

        if (gameSettingsOptions == GameSettingsOptions.FAST_MATH) {
            this.ofFastMath =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofFastMath ? 1 : 0));
            MathHelper.fastMath = this.ofFastMath;
        }

        if (gameSettingsOptions == GameSettingsOptions.FAST_RENDER) {
            if (!this.ofFastRender && OptiFineConfig.isShaders()) {
                OptiFineConfig.showGuiMessage(
                        Lang.get("of.message.fr.shaders1"), Lang.get("of.message.fr.shaders2"));
                return;
            }

            this.ofFastRender =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofFastRender ? 1 : 0));
            if (this.ofFastRender) {
                this.mc.entityRenderer.stopUseShader();
            }

            OptiFineConfig.updateFramebufferSize();
        }

        if (gameSettingsOptions == GameSettingsOptions.TRANSLUCENT_BLOCKS) {
            {
                if (this.ofTranslucentBlocks == 0) {
                    this.ofTranslucentBlocks = 1;

                } else {

                    if (this.ofTranslucentBlocks == 1) {
                        this.ofTranslucentBlocks = 2;

                    } else {

                        if (this.ofTranslucentBlocks == 2) {
                            this.ofTranslucentBlocks = 0;

                        } else {

                            this.ofTranslucentBlocks = 0;
                        }
                    }
                }
            }

            this.mc.renderGlobal.loadRenderers();
        }

        if (gameSettingsOptions == GameSettingsOptions.LAZY_CHUNK_LOADING) {
            this.ofLazyChunkLoading =
                    remix.compat.compiler.JvmBooleanSlots.fromInt(
                            (!this.ofLazyChunkLoading ? 1 : 0));
            OptiFineConfig.updateAvailableProcessors();
            if (!OptiFineConfig.isSingleProcessor()) {
                this.ofLazyChunkLoading = false;
            }

            this.mc.renderGlobal.loadRenderers();
        }

        if (gameSettingsOptions == GameSettingsOptions.FULLSCREEN_MODE) {
            List list = Arrays.asList(OptiFineConfig.getDisplayModeNames());
            if (this.ofFullscreenMode.equals("Default")) {
                this.ofFullscreenMode = (String) list.get(0);

            } else {

                int i = list.indexOf(this.ofFullscreenMode);
                if (i < 0) {
                    this.ofFullscreenMode = "Default";

                } else {

                    if (++i >= list.size()) {
                        this.ofFullscreenMode = "Default";

                    } else {

                        this.ofFullscreenMode = (String) list.get(i);
                    }
                }
            }
        }

        if (gameSettingsOptions == GameSettingsOptions.DYNAMIC_FOV) {
            this.ofDynamicFov =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.ofDynamicFov ? 1 : 0));
        }

        if (gameSettingsOptions == GameSettingsOptions.DYNAMIC_LIGHTS) {
            this.ofDynamicLights =
                    nextValue(
                            this.ofDynamicLights,
                            net.minecraft.client.settings.GameSettings.OF_DYNAMIC_LIGHTS);
            DynamicLights.removeLights(this.mc.renderGlobal);
        }

        if (gameSettingsOptions == GameSettingsOptions.HELD_ITEM_TOOLTIPS) {
            this.heldItemTooltips =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((!this.heldItemTooltips ? 1 : 0));
        }
    }

    private static int indexOf(int i, int[] aint) {
        BK();

        int j = 0;

        while (j < aint.length) {
            if (aint[j] == i) {
                return j;
            }

            j++;
        }

        return -1;
    }

    public String getKeyBinding(GameSettingsOptions gameSettingsOptions) {
        String text = this.getKeyBindingOF(gameSettingsOptions);
        if (text != null) {
            return text;
        }

        String textValue = I18n.format(gameSettingsOptions.getEnumString(), new Object[0]) + ": ";
        if (gameSettingsOptions.getEnumFloat()) {
            float f1 = this.getOptionFloatValue(gameSettingsOptions);
            float fx = gameSettingsOptions.normalizeValue(f1);
            return gameSettingsOptions == GameSettingsOptions.SENSITIVITY
                    ? (fx == 0.0F
                            ? textValue + I18n.format("options.sensitivity.min", new Object[0])
                            : (fx == 1.0F
                                    ? textValue
                                            + I18n.format("options.sensitivity.max", new Object[0])
                                    : textValue + (int) (fx * 200.0F) + packedTextFragments[4]))
                    : (gameSettingsOptions == GameSettingsOptions.FOV
                            ? (f1 == 70.0F
                                    ? textValue + I18n.format("options.fov.min", new Object[0])
                                    : (f1 == 110.0F
                                            ? textValue
                                                    + I18n.format("options.fov.max", new Object[0])
                                            : textValue + (int) f1))
                            : (gameSettingsOptions == GameSettingsOptions.FRAMERATE_LIMIT
                                    ? (f1 == GameSettingsOptions.access$000(gameSettingsOptions)
                                            ? textValue
                                                    + I18n.format(
                                                            "options.framerateLimit.max",
                                                            new Object[0])
                                            : textValue + (int) f1 + " fps")
                                    : (gameSettingsOptions == GameSettingsOptions.RENDER_CLOUDS
                                            ? (f1
                                                            == GameSettingsOptions.access$100(
                                                                    gameSettingsOptions)
                                                    ? textValue
                                                            + I18n.format(
                                                                    "options.cloudHeight.min",
                                                                    new Object[0])
                                                    : textValue + ((int) f1 + 128))
                                            : (gameSettingsOptions == GameSettingsOptions.GAMMA
                                                    ? (fx == 0.0F
                                                            ? textValue
                                                                    + I18n.format(
                                                                            "options.gamma.min",
                                                                            new Object[0])
                                                            : (fx == 1.0F
                                                                    ? textValue
                                                                            + I18n.format(
                                                                                    "options.gamma.max",
                                                                                    new Object[0])
                                                                    : textValue
                                                                            + packedTextFragments[5]
                                                                            + (int) (fx * 100.0F)
                                                                            + packedTextFragments[
                                                                                    6]))
                                                    : (gameSettingsOptions
                                                                    == GameSettingsOptions
                                                                            .SATURATION
                                                            ? textValue
                                                                    + (int) (fx * 400.0F)
                                                                    + packedTextFragments[7]
                                                            : (gameSettingsOptions
                                                                            == GameSettingsOptions
                                                                                    .CHAT_OPACITY
                                                                    ? textValue
                                                                            + (int)
                                                                                    (fx * 90.0F
                                                                                            + 10.0F)
                                                                            + packedTextFragments[8]
                                                                    : (gameSettingsOptions
                                                                                    == GameSettingsOptions
                                                                                            .CHAT_HEIGHT_UNFOCUSED
                                                                            ? textValue
                                                                                    + GuiNewChat
                                                                                            .calculateChatboxHeight(
                                                                                                    fx)
                                                                                    + "px"
                                                                            : (gameSettingsOptions
                                                                                            == GameSettingsOptions
                                                                                                    .CHAT_HEIGHT_FOCUSED
                                                                                    ? textValue
                                                                                            + GuiNewChat
                                                                                                    .calculateChatboxHeight(
                                                                                                            fx)
                                                                                            + "px"
                                                                                    : (gameSettingsOptions
                                                                                                    == GameSettingsOptions
                                                                                                            .CHAT_WIDTH
                                                                                            ? textValue
                                                                                                    + GuiNewChat
                                                                                                            .calculateChatboxWidth(
                                                                                                                    fx)
                                                                                                    + "px"
                                                                                            : (gameSettingsOptions
                                                                                                            == GameSettingsOptions
                                                                                                                    .RENDER_DISTANCE
                                                                                                    ? textValue
                                                                                                            + (int)
                                                                                                                    f1
                                                                                                            + " chunks"
                                                                                                    : (gameSettingsOptions
                                                                                                                    == GameSettingsOptions
                                                                                                                            .MIPMAP_LEVELS
                                                                                                            ? (f1
                                                                                                                            == 0.0F
                                                                                                                    ? textValue
                                                                                                                            + I18n
                                                                                                                                    .format(
                                                                                                                                            "options.off",
                                                                                                                                            new Object
                                                                                                                                                    [0])
                                                                                                                    : textValue
                                                                                                                            + (int)
                                                                                                                                    f1)
                                                                                                            : (gameSettingsOptions
                                                                                                                            == GameSettingsOptions
                                                                                                                                    .STREAM_FPS
                                                                                                                    ? textValue
                                                                                                                            + TwitchStream
                                                                                                                                    .formatStreamFps(
                                                                                                                                            fx)
                                                                                                                            + " fps"
                                                                                                                    : (gameSettingsOptions
                                                                                                                                    == GameSettingsOptions
                                                                                                                                            .STREAM_KBPS
                                                                                                                            ? textValue
                                                                                                                                    + TwitchStream
                                                                                                                                            .formatStreamKbps(
                                                                                                                                                    fx)
                                                                                                                                    + " Kbps"
                                                                                                                            : (gameSettingsOptions
                                                                                                                                            == GameSettingsOptions
                                                                                                                                                    .STREAM_BYTES_PER_PIXEL
                                                                                                                                    ? textValue
                                                                                                                                            + String
                                                                                                                                                    .format(
                                                                                                                                                            "%.3f bpp",
                                                                                                                                                            TwitchStream
                                                                                                                                                                    .formatStreamBps(
                                                                                                                                                                            fx))
                                                                                                                                    : (fx
                                                                                                                                                    == 0.0F
                                                                                                                                            ? textValue
                                                                                                                                                    + I18n
                                                                                                                                                            .format(
                                                                                                                                                                    "options.off",
                                                                                                                                                                    new Object
                                                                                                                                                                            [0])
                                                                                                                                            : textValue
                                                                                                                                                    + (int)
                                                                                                                                                            (fx
                                                                                                                                                                    * 100.0F)
                                                                                                                                                    + packedTextFragments[
                                                                                                                                                            9])))))))))))))));
        }

        if (gameSettingsOptions.getEnumBoolean()) {
            boolean optionOrdinalValue = this.getOptionOrdinalValue(gameSettingsOptions);
            return optionOrdinalValue
                    ? textValue + I18n.format("options.on", new Object[0])
                    : textValue + I18n.format("options.off", new Object[0]);
        }

        if (gameSettingsOptions == GameSettingsOptions.aTo) {
            return textValue
                    + getTranslation(
                            net.minecraft.client.settings.GameSettings.GUISCALES, this.guiScale);
        }

        if (gameSettingsOptions == GameSettingsOptions.CHAT_VISIBILITY) {
            return textValue + I18n.format(this.chatVisibility.getResourceKey(), new Object[0]);
        }

        if (gameSettingsOptions == GameSettingsOptions.PARTICLES) {
            return textValue
                    + getTranslation(
                            net.minecraft.client.settings.GameSettings.PARTICLES,
                            this.particleSetting);
        }

        if (gameSettingsOptions == GameSettingsOptions.aRU) {
            return textValue
                    + getTranslation(
                            net.minecraft.client.settings.GameSettings.AMBIENT_OCCLUSIONS,
                            this.ambientOcclusion);
        }

        if (gameSettingsOptions == GameSettingsOptions.STREAM_COMPRESSION) {
            return textValue
                    + getTranslation(
                            net.minecraft.client.settings.GameSettings.STREAM_COMPRESSIONS,
                            this.streamCompression);
        }

        if (gameSettingsOptions == GameSettingsOptions.STREAM_CHAT_ENABLED) {
            return textValue
                    + getTranslation(
                            net.minecraft.client.settings.GameSettings.STREAM_CHAT_MODES,
                            this.streamChatEnabled);
        }

        if (gameSettingsOptions == GameSettingsOptions.STREAM_CHAT_USER_FILTER) {
            return textValue
                    + getTranslation(
                            net.minecraft.client.settings.GameSettings.STREAM_CHAT_FILTER_MODES,
                            this.streamChatUserFilter);
        }

        if (gameSettingsOptions == GameSettingsOptions.STREAM_MIC_TOGGLE_BEHAVIOR) {
            return textValue
                    + getTranslation(
                            net.minecraft.client.settings.GameSettings.STREAM_MIC_MODES,
                            this.streamMicToggleBehavior);
        }

        if (gameSettingsOptions == GameSettingsOptions.aRM) {
            if (this.fancyGraphics) {
                return textValue + I18n.format("options.graphics.fancy", new Object[0]);
            }

            return textValue + I18n.format("options.graphics.fast", new Object[0]);
        } else {
            return textValue;
        }
    }

    public void dn() {
        try {
            PrintWriter printwriter = new PrintWriter(new FileWriter(this.optionsFileOF));
            printwriter.println("ofRenderDistanceChunks:" + this.renderDistanceChunks);
            printwriter.println("ofFogType:" + this.ofFogType);
            printwriter.println("ofFogStart:" + this.ofFogStart);
            printwriter.println("ofMipmapType:" + this.ofMipmapType);
            printwriter.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
            printwriter.println("ofSmoothFps:" + this.ofSmoothFps);
            printwriter.println("ofSmoothWorld:" + this.ofSmoothWorld);
            printwriter.println("ofAoLevel:" + this.ofAoLevel);
            printwriter.println("ofClouds:" + this.ofClouds);
            printwriter.println("ofCloudsHeight:" + this.ofCloudsHeight);
            printwriter.println("ofTrees:" + this.ofTrees);
            printwriter.println("ofDroppedItems:" + this.ofDroppedItems);
            printwriter.println("ofRain:" + this.ofRain);
            printwriter.println("ofAnimatedWater:" + this.ofAnimatedWater);
            printwriter.println("ofAnimatedLava:" + this.ofAnimatedLava);
            printwriter.println("ofAnimatedFire:" + this.ofAnimatedFire);
            printwriter.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
            printwriter.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
            printwriter.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
            printwriter.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
            printwriter.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
            printwriter.println("ofVoidParticles:" + this.ofVoidParticles);
            printwriter.println("ofWaterParticles:" + this.ofWaterParticles);
            printwriter.println("ofPortalParticles:" + this.ofPortalParticles);
            printwriter.println("ofPotionParticles:" + this.ofPotionParticles);
            printwriter.println("ofFireworkParticles:" + this.ofFireworkParticles);
            printwriter.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
            printwriter.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
            printwriter.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
            printwriter.println("ofRainSplash:" + this.ofRainSplash);
            printwriter.println("ofLagometer:" + this.ofLagometer);
            printwriter.println("ofShowFps:" + this.ofShowFps);
            printwriter.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
            printwriter.println("ofBetterGrass:" + this.ofBetterGrass);
            printwriter.println("ofConnectedTextures:" + this.ofConnectedTextures);
            printwriter.println("ofWeather:" + this.ofWeather);
            printwriter.println("ofSky:" + this.ofSky);
            printwriter.println("ofStars:" + this.ofStars);
            printwriter.println("ofSunMoon:" + this.ofSunMoon);
            printwriter.println("ofVignette:" + this.ofVignette);
            printwriter.println("ofChunkUpdates:" + this.ofChunkUpdates);
            printwriter.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
            printwriter.println("ofTime:" + this.ofTime);
            printwriter.println("ofClearWater:" + this.ofClearWater);
            printwriter.println("ofAaLevel:" + this.ofAaLevel);
            printwriter.println("ofAfLevel:" + this.ofAfLevel);
            printwriter.println("ofProfiler:" + this.ofProfiler);
            printwriter.println("ofBetterSnow:" + this.ofBetterSnow);
            printwriter.println("ofSwampColors:" + this.ofSwampColors);
            printwriter.println("ofRandomMobs:" + this.ofRandomMobs);
            printwriter.println("ofSmoothBiomes:" + this.ofSmoothBiomes);
            printwriter.println("ofCustomFonts:" + this.ofCustomFonts);
            printwriter.println("ofCustomColors:" + this.ofCustomColors);
            printwriter.println("ofCustomItems:" + this.ofCustomItems);
            printwriter.println("ofCustomSky:" + this.ofCustomSky);
            printwriter.println("ofShowCapes:" + this.ofShowCapes);
            printwriter.println("ofNaturalTextures:" + this.ofNaturalTextures);
            printwriter.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
            printwriter.println("ofDynamicFov:" + this.ofDynamicFov);
            printwriter.println("ofDynamicLights:" + this.ofDynamicLights);
            printwriter.println("ofFullscreenMode:" + this.ofFullscreenMode);
            printwriter.println("ofFastMath:" + this.ofFastMath);
            printwriter.println("ofFastRender:" + this.ofFastRender);
            printwriter.println("ofTranslucentBlocks:" + this.ofTranslucentBlocks);
            printwriter.println(
                    "key_"
                            + this.ofKeyBindZoom.getKeyDescription()
                            + packedTextFragments[22]
                            + this.ofKeyBindZoom.getKeyCode());
            printwriter.close();
        } catch (Exception exception) {
            OptiFineConfig.warn("Failed to save options");
            exception.printStackTrace();
        }
    }

    public static void b(String[] astring) {
        aJQ = astring;
    }

    private void setOptionFloatValueOF(GameSettingsOptions gameSettingsOptions, float aICValue) {
        BK();

        if (gameSettingsOptions == GameSettingsOptions.CLOUD_HEIGHT) {
            this.ofCloudsHeight = aICValue;
            this.mc.renderGlobal.resetClouds();
        }

        if (gameSettingsOptions == GameSettingsOptions.AO_LEVEL) {
            this.ofAoLevel = aICValue;
            this.mc.renderGlobal.loadRenderers();
        }

        if (gameSettingsOptions == GameSettingsOptions.AA_LEVEL) {
            int i = (int) aICValue;
            if (i > 0 && OptiFineConfig.isShaders()) {
                OptiFineConfig.showGuiMessage(
                        Lang.get("of.message.aa.shaders1"), Lang.get("of.message.aa.shaders2"));
                return;
            }

            int[] aint = new int[] {0, 2, 4, 6, 8, 12, 16};
            this.ofAaLevel = 0;
            int j = 0;

            while (j < aint.length) {
                if (i >= aint[j]) {
                    this.ofAaLevel = aint[j];
                }

                j++;
            }

            this.ofAaLevel = OptiFineConfig.limit(this.ofAaLevel, 0, 16);
        }

        if (gameSettingsOptions == GameSettingsOptions.AF_LEVEL) {
            int k = (int) aICValue;
            if (k > 1 && OptiFineConfig.isShaders()) {
                OptiFineConfig.showGuiMessage(
                        Lang.get("of.message.af.shaders1"), Lang.get("of.message.af.shaders2"));
                return;
            }

            this.ofAfLevel = 1;

            while (this.ofAfLevel * 2 <= k) {
                this.ofAfLevel *= 2;
            }

            this.ofAfLevel = OptiFineConfig.limit(this.ofAfLevel, 1, 16);
            this.mc.refreshResources();
        }

        if (gameSettingsOptions == GameSettingsOptions.MIPMAP_TYPE) {
            int l = (int) aICValue;
            this.ofMipmapType = OptiFineConfig.limit(l, 0, 3);
            this.mc.refreshResources();
        }
    }

    // region Static initialization and string tables
    static {
        net.minecraft.client.settings.GameSettings.packedTextFragments = new String[24];
        net.minecraft.client.settings.GameSettings.packedTextFragments[0] = new String("");
        net.minecraft.client.settings.GameSettings.packedTextFragments[1] = new String("");
        net.minecraft.client.settings.GameSettings.packedTextFragments[2] = new String("");
        net.minecraft.client.settings.GameSettings.packedTextFragments[3] = new String("");
        net.minecraft.client.settings.GameSettings.packedTextFragments[4] = new String("%");
        net.minecraft.client.settings.GameSettings.packedTextFragments[5] = new String("+");
        net.minecraft.client.settings.GameSettings.packedTextFragments[6] = new String("%");
        net.minecraft.client.settings.GameSettings.packedTextFragments[7] = new String("%");
        net.minecraft.client.settings.GameSettings.packedTextFragments[8] = new String("%");
        net.minecraft.client.settings.GameSettings.packedTextFragments[9] = new String("%");
        net.minecraft.client.settings.GameSettings.packedTextFragments[10] = new String("");
        net.minecraft.client.settings.GameSettings.packedTextFragments[11] = new String(":");
        net.minecraft.client.settings.GameSettings.packedTextFragments[12] = new String(":");
        net.minecraft.client.settings.GameSettings.packedTextFragments[13] = new String(":");
        net.minecraft.client.settings.GameSettings.packedTextFragments[14] = new String(":");
        net.minecraft.client.settings.GameSettings.packedTextFragments[15] = new String("+");
        net.minecraft.client.settings.GameSettings.packedTextFragments[16] = new String(" ");
        net.minecraft.client.settings.GameSettings.packedTextFragments[17] = new String("");
        net.minecraft.client.settings.GameSettings.packedTextFragments[18] = new String("");
        net.minecraft.client.settings.GameSettings.packedTextFragments[19] = new String(")");
        net.minecraft.client.settings.GameSettings.packedTextFragments[20] = new String("");
        net.minecraft.client.settings.GameSettings.packedTextFragments[21] = new String(":");
        net.minecraft.client.settings.GameSettings.packedTextFragments[22] = new String(":");
        String[] astring1 = new String[4];
        b(astring1);
        net.minecraft.client.settings.GameSettings.DEFAULT_STR = "Default";
        net.minecraft.client.settings.GameSettings.logger = LogManager.getLogger();
        net.minecraft.client.settings.GameSettings.gson = new Gson();
        net.minecraft.client.settings.GameSettings.typeListString =
                new GameSettingsTypeListStringParameterizedType();
        net.minecraft.client.settings.GameSettings.GUISCALES =
                new String[] {
                    "options.guiScale.auto",
                    "options.guiScale.small",
                    "options.guiScale.normal",
                    "options.guiScale.large"
                };
        net.minecraft.client.settings.GameSettings.PARTICLES =
                new String[] {
                    "options.particles.all",
                    "options.particles.decreased",
                    "options.particles.minimal"
                };
        net.minecraft.client.settings.GameSettings.AMBIENT_OCCLUSIONS =
                new String[] {"options.ao.off", "options.ao.min", "options.ao.max"};
        net.minecraft.client.settings.GameSettings.STREAM_COMPRESSIONS =
                new String[] {
                    "options.stream.compression.low",
                    "options.stream.compression.medium",
                    "options.stream.compression.high"
                };
        net.minecraft.client.settings.GameSettings.STREAM_CHAT_MODES =
                new String[] {
                    "options.stream.chat.enabled.streaming",
                    "options.stream.chat.enabled.always",
                    "options.stream.chat.enabled.never"
                };
        net.minecraft.client.settings.GameSettings.STREAM_CHAT_FILTER_MODES =
                new String[] {
                    "options.stream.chat.userFilter.all",
                    "options.stream.chat.userFilter.subs",
                    "options.stream.chat.userFilter.mods"
                };
        net.minecraft.client.settings.GameSettings.STREAM_MIC_MODES =
                new String[] {"options.stream.mic_toggle.mute", "options.stream.mic_toggle.talk"};
        net.minecraft.client.settings.GameSettings.OF_TREES_VALUES = new int[] {0, 1, 4, 2};
        net.minecraft.client.settings.GameSettings.OF_DYNAMIC_LIGHTS = new int[] {3, 1, 2};
        net.minecraft.client.settings.GameSettings.KEYS_DYNAMIC_LIGHTS =
                new String[] {"options.off", "options.graphics.fast", "options.graphics.fancy"};
    }

    // endregion

    private static int nextValue(int p171_0, int[] p171_1) {
        int j = net.minecraft.client.settings.GameSettings.indexOf(p171_0, p171_1);
        if (j < 0) {
            return p171_1[0];
        }

        if (++j >= p171_1.length) {
            j = 0;
        }

        return p171_1[j];
    }

    private void updateWaterOpacity() {
        if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
            optifine.OptiFineConfig.waterOpacityChanged = true;
        }

        optifine.ClearWater.updateWaterOpacity(this, this.mc.world);
    }

    public void switchModelPartEnabled(EnumPlayerModelParts enumPlayerModelParts) {
        {
            BK();

            if (!this.getModelParts().contains(enumPlayerModelParts)) {
                this.setModelParts.add(enumPlayerModelParts);

            } else {

                this.setModelParts.remove(enumPlayerModelParts);
            }
        }

        this.sendSettingsToServer();
    }

    // region String decoding support

    // endregion

    private float parseFloat(String text) {
        return text.equals("true") ? 1.0F : (text.equals("false") ? 0.0F : Float.parseFloat(text));
    }

    public GameSettings(MinecraftClient aJEValue, File file1) {
        this.renderDistanceChunks = -1;
        this.viewBobbing = true;
        this.fboEnable = true;
        this.limitFramerate = 120;
        this.clouds = true;
        this.fancyGraphics = true;
        this.ambientOcclusion = 2;
        this.resourcePacks = Lists.newArrayList();
        this.ofFogType = 1;
        this.ofFogStart = 0.8F;
        this.ofMipmapType = 0;
        this.ofOcclusionFancy = false;
        BK();

        this.ofSmoothFps = false;
        this.ofSmoothWorld = OptiFineConfig.isSingleProcessor();
        this.ofLazyChunkLoading = OptiFineConfig.isSingleProcessor();
        this.ofAoLevel = 1.0F;
        this.ofAaLevel = 0;
        this.ofAfLevel = 1;
        this.ofClouds = 0;
        this.ofCloudsHeight = 0.0F;
        this.ofTrees = 0;
        this.ofRain = 0;
        this.ofDroppedItems = 0;
        this.ofBetterGrass = 3;
        this.ofAutoSaveTicks = 4000;
        this.ofLagometer = false;
        this.ofProfiler = false;
        this.ofShowFps = false;
        this.ofWeather = true;
        this.ofSky = true;
        this.ofStars = true;
        this.ofSunMoon = true;
        this.ofVignette = 0;
        this.ofChunkUpdates = 1;
        this.ofChunkUpdatesDynamic = false;
        this.ofTime = 0;
        this.ofClearWater = false;
        this.ofBetterSnow = false;
        this.ofFullscreenMode = "Default";
        this.ofSwampColors = true;
        this.ofRandomMobs = true;
        this.ofSmoothBiomes = true;
        this.ofCustomFonts = true;
        this.ofCustomColors = true;
        this.ofCustomSky = true;
        this.ofShowCapes = true;
        this.ofConnectedTextures = 2;
        this.ofCustomItems = true;
        this.ofNaturalTextures = false;

        this.ofFastMath = false;
        this.ofFastRender = true;
        this.ofTranslucentBlocks = 0;
        this.ofDynamicFov = true;
        this.ofDynamicLights = 3;
        this.ofAnimatedWater = 0;
        this.ofAnimatedLava = 0;
        this.ofAnimatedFire = true;
        this.ofAnimatedPortal = true;
        this.ofAnimatedRedstone = true;
        this.ofAnimatedExplosion = true;
        this.ofAnimatedFlame = true;
        this.ofAnimatedSmoke = true;
        this.ofVoidParticles = true;
        this.ofWaterParticles = true;
        this.ofRainSplash = true;
        this.ofPortalParticles = true;
        this.ofPotionParticles = true;
        this.ofFireworkParticles = true;
        this.ofDrippingWaterLava = true;
        this.ofAnimatedTerrain = true;
        this.ofAnimatedTextures = true;
        this.chatVisibility = EntityPlayerEnumChatVisibility.FULL;
        this.chatColours = true;
        this.chatLinks = true;
        this.chatLinksPrompt = true;
        this.chatOpacity = 1.0F;
        this.snooperEnabled = true;
        this.enableVsync = true;
        this.useVbo = false;
        this.allowBlockAlternatives = true;
        this.reducedDebugInfo = false;
        this.pauseOnLostFocus = true;
        this.setModelParts = Sets.newHashSet(EnumPlayerModelParts.values());
        this.heldItemTooltips = true;
        this.chatScale = 1.0F;
        this.chatWidth = 1.0F;
        this.chatHeightUnfocused = 0.44366196F;
        this.chatHeightFocused = 1.0F;
        this.showInventoryAchievementHint = true;
        this.mipmapLevels = 4;
        this.mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
        this.streamBytesPerPixel = 0.5F;
        this.streamMicVolume = 1.0F;
        this.streamGameVolume = 1.0F;
        this.streamKbps = 0.5412844F;
        this.streamFps = 0.31690142F;
        this.streamCompression = 1;
        this.streamSendMetadata = true;
        this.streamPreferredServer = packedTextFragments[0];
        this.streamChatEnabled = 0;
        this.streamChatUserFilter = 0;
        this.streamMicToggleBehavior = 0;
        this.keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
        this.keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
        this.keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
        this.keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
        this.keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
        this.keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
        this.keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
        this.keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
        this.keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
        this.keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
        this.keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
        this.keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.gameplay");
        this.keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
        this.keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
        this.keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
        this.keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
        this.keyBindTogglePerspective =
                new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
        this.keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
        this.keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
        this.keyBindSpectatorOutlines =
                new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
        this.keyBindStreamStartStop =
                new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
        this.keyBindStreamPauseUnpause =
                new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
        this.keyBindStreamCommercials =
                new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
        this.keyBindStreamToggleMic =
                new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
        this.keyBindsHotbar =
                new KeyBinding[] {
                    new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"),
                    new KeyBinding("key.hotbar.9", 10, "key.categories.inventory")
                };
        this.keyBindings =
                (KeyBinding[])
                        ArrayUtils.addAll(
                                new KeyBinding[] {
                                    this.keyBindAttack, this.keyBindUseItem, this.keyBindForward,
                                            this.keyBindLeft, this.keyBindBack, this.keyBindRight,
                                    this.keyBindJump, this.keyBindSneak, this.keyBindDrop,
                                            this.keyBindInventory, this.keyBindChat,
                                            this.keyBindPlayerList,
                                    this.keyBindPickBlock, this.keyBindCommand,
                                            this.keyBindScreenshot, this.keyBindTogglePerspective,
                                            this.keyBindSmoothCamera, this.keyBindSprint,
                                    this.keyBindStreamStartStop, this.keyBindStreamPauseUnpause,
                                            this.keyBindStreamCommercials,
                                            this.keyBindStreamToggleMic, this.keyBindFullscreen,
                                            this.keyBindSpectatorOutlines
                                },
                                this.keyBindsHotbar);
        this.difficulty = EnumDifficulty.NORMAL;
        this.lastServer = packedTextFragments[1];
        this.fovSetting = 70.0F;
        this.language = "en_US";
        this.forceUnicodeFont = false;
        this.mc = aJEValue;
        this.optionsFile = new File(file1, "options.txt");
        this.optionsFileOF = new File(file1, "optionsof.txt");
        this.limitFramerate = (int) GameSettingsOptions.FRAMERATE_LIMIT.getValueMax();
        this.ofKeyBindZoom = new KeyBinding("of.key.zoom", 46, "key.categories.misc");
        this.keyBindings = (KeyBinding[]) ArrayUtils.add(this.keyBindings, this.ofKeyBindZoom);
        GameSettingsOptions.RENDER_DISTANCE.setValueMax(32.0F);
        this.renderDistanceChunks = 8;
        this.method_8d9b536bcdc9991c48fb();
        OptiFineConfig.initGameSettings(this);
    }

    private String getKeyBindingOF(GameSettingsOptions gameSettingsOptions) {
        String text = I18n.format(gameSettingsOptions.getEnumString(), new Object[0]) + ": ";
        if (text == null) {
            text = gameSettingsOptions.getEnumString();
        }

        if (gameSettingsOptions == GameSettingsOptions.RENDER_DISTANCE) {
            int k = (int) this.getOptionFloatValue(gameSettingsOptions);
            String textValue = I18n.format("options.renderDistance.tiny", new Object[0]);
            byte b0 = 2;
            if (k >= 4) {
                textValue = I18n.format("options.renderDistance.short", new Object[0]);
                b0 = 4;
            }

            if (k >= 8) {
                textValue = I18n.format("options.renderDistance.normal", new Object[0]);
                b0 = 8;
            }

            if (k >= 16) {
                textValue = I18n.format("options.renderDistance.far", new Object[0]);
                b0 = 16;
            }

            if (k >= 32) {
                textValue = Lang.get("of.options.renderDistance.extreme");
                b0 = 32;
            }

            int j = this.renderDistanceChunks - b0;
            String textValue2 = textValue;
            if (j > 0) {
                textValue2 = textValue + packedTextFragments[15];
            }

            return text + k + packedTextFragments[16] + textValue2 + packedTextFragments[17];
        } else if (gameSettingsOptions == GameSettingsOptions.FOG_FANCY) {
            switch (this.ofFogType) {
                case 1:
                    return text + Lang.getFast();
                case 2:
                    return text + Lang.getFancy();
                case 3:
                    return text + Lang.getOff();
                default:
                    return text + Lang.getOff();
            }
        } else {
            if (gameSettingsOptions == GameSettingsOptions.FOG_START) {
                return text + this.ofFogStart;
            }

            if (gameSettingsOptions == GameSettingsOptions.MIPMAP_TYPE) {
                switch (this.ofMipmapType) {
                    case 0:
                        return text + Lang.get("of.options.mipmap.nearest");
                    case 1:
                        return text + Lang.get("of.options.mipmap.linear");
                    case 2:
                        return text + Lang.get("of.options.mipmap.bilinear");
                    case 3:
                        return text + Lang.get("of.options.mipmap.trilinear");
                    default:
                        return text + "of.options.mipmap.nearest";
                }
            } else {
                if (gameSettingsOptions == GameSettingsOptions.SMOOTH_FPS) {
                    return this.ofSmoothFps ? text + Lang.getOn() : text + Lang.getOff();
                }

                if (gameSettingsOptions == GameSettingsOptions.SMOOTH_WORLD) {
                    return this.ofSmoothWorld ? text + Lang.getOn() : text + Lang.getOff();
                }

                if (gameSettingsOptions == GameSettingsOptions.CLOUDS) {
                    switch (this.ofClouds) {
                        case 1:
                            return text + Lang.getFast();
                        case 2:
                            return text + Lang.getFancy();
                        case 3:
                            return text + Lang.getOff();
                        default:
                            return text + Lang.getDefault();
                    }
                } else if (gameSettingsOptions == GameSettingsOptions.TREES) {
                    switch (this.ofTrees) {
                        case 1:
                            return text + Lang.getFast();
                        case 2:
                            return text + Lang.getFancy();
                        case 3:
                        default:
                            return text + Lang.getDefault();
                        case 4:
                            return text + Lang.get("of.general.smart");
                    }
                } else if (gameSettingsOptions == GameSettingsOptions.DROPPED_ITEMS) {
                    switch (this.ofDroppedItems) {
                        case 1:
                            return text + Lang.getFast();
                        case 2:
                            return text + Lang.getFancy();
                        default:
                            return text + Lang.getDefault();
                    }
                } else if (gameSettingsOptions == GameSettingsOptions.RAIN) {
                    switch (this.ofRain) {
                        case 1:
                            return text + Lang.getFast();
                        case 2:
                            return text + Lang.getFancy();
                        case 3:
                            return text + Lang.getOff();
                        default:
                            return text + Lang.getDefault();
                    }
                } else if (gameSettingsOptions == GameSettingsOptions.ANIMATED_WATER) {
                    switch (this.ofAnimatedWater) {
                        case 1:
                            return text + Lang.get("of.options.animation.dynamic");
                        case 2:
                            return text + Lang.getOff();
                        default:
                            return text + Lang.getOn();
                    }
                } else if (gameSettingsOptions == GameSettingsOptions.ANIMATED_LAVA) {
                    switch (this.ofAnimatedLava) {
                        case 1:
                            return text + Lang.get("of.options.animation.dynamic");
                        case 2:
                            return text + Lang.getOff();
                        default:
                            return text + Lang.getOn();
                    }
                } else {
                    if (gameSettingsOptions == GameSettingsOptions.ANIMATED_FIRE) {
                        return this.ofAnimatedFire ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.ANIMATED_PORTAL) {
                        return this.ofAnimatedPortal ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.ANIMATED_REDSTONE) {
                        return this.ofAnimatedRedstone ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.ANIMATED_EXPLOSION) {
                        return this.ofAnimatedExplosion
                                ? text + Lang.getOn()
                                : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.ANIMATED_FLAME) {
                        return this.ofAnimatedFlame ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.ANIMATED_SMOKE) {
                        return this.ofAnimatedSmoke ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.VOID_PARTICLES) {
                        return this.ofVoidParticles ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.WATER_PARTICLES) {
                        return this.ofWaterParticles ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.PORTAL_PARTICLES) {
                        return this.ofPortalParticles ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.POTION_PARTICLES) {
                        return this.ofPotionParticles ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.FIREWORK_PARTICLES) {
                        return this.ofFireworkParticles
                                ? text + Lang.getOn()
                                : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.DRIPPING_WATER_LAVA) {
                        return this.ofDrippingWaterLava
                                ? text + Lang.getOn()
                                : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.ANIMATED_TERRAIN) {
                        return this.ofAnimatedTerrain ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.ANIMATED_TEXTURES) {
                        return this.ofAnimatedTextures ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.RAIN_SPLASH) {
                        return this.ofRainSplash ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.LAGOMETER) {
                        return this.ofLagometer ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.SHOW_FPS) {
                        return this.ofShowFps ? text + Lang.getOn() : text + Lang.getOff();
                    }

                    if (gameSettingsOptions == GameSettingsOptions.AUTOSAVE_TICKS) {
                        return this.ofAutoSaveTicks <= 40
                                ? text + Lang.get("of.options.save.default")
                                : (this.ofAutoSaveTicks <= 400
                                        ? text + Lang.get("of.options.save.20s")
                                        : (this.ofAutoSaveTicks <= 4000
                                                ? text + Lang.get("of.options.save.3min")
                                                : text + Lang.get("of.options.save.30min")));
                    }

                    if (gameSettingsOptions == GameSettingsOptions.BETTER_GRASS) {
                        switch (this.ofBetterGrass) {
                            case 1:
                                return text + Lang.getFast();
                            case 2:
                                return text + Lang.getFancy();
                            default:
                                return text + Lang.getOff();
                        }
                    } else if (gameSettingsOptions == GameSettingsOptions.CONNECTED_TEXTURES) {
                        switch (this.ofConnectedTextures) {
                            case 1:
                                return text + Lang.getFast();
                            case 2:
                                return text + Lang.getFancy();
                            default:
                                return text + Lang.getOff();
                        }
                    } else {
                        if (gameSettingsOptions == GameSettingsOptions.WEATHER) {
                            return this.ofWeather ? text + Lang.getOn() : text + Lang.getOff();
                        }

                        if (gameSettingsOptions == GameSettingsOptions.SKY) {
                            return this.ofSky ? text + Lang.getOn() : text + Lang.getOff();
                        }

                        if (gameSettingsOptions == GameSettingsOptions.STARS) {
                            return this.ofStars ? text + Lang.getOn() : text + Lang.getOff();
                        }

                        if (gameSettingsOptions == GameSettingsOptions.SUN_MOON) {
                            return this.ofSunMoon ? text + Lang.getOn() : text + Lang.getOff();
                        }

                        if (gameSettingsOptions == GameSettingsOptions.VIGNETTE) {
                            switch (this.ofVignette) {
                                case 1:
                                    return text + Lang.getFast();
                                case 2:
                                    return text + Lang.getFancy();
                                default:
                                    return text + Lang.getDefault();
                            }
                        } else {
                            if (gameSettingsOptions == GameSettingsOptions.CHUNK_UPDATES) {
                                return text + this.ofChunkUpdates;
                            }

                            if (gameSettingsOptions == GameSettingsOptions.CHUNK_UPDATES_DYNAMIC) {
                                return this.ofChunkUpdatesDynamic
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            }

                            if (gameSettingsOptions == GameSettingsOptions.TIME) {
                                return this.ofTime == 1
                                        ? text + Lang.get("of.options.time.dayOnly")
                                        : (this.ofTime == 2
                                                ? text + Lang.get("of.options.time.nightOnly")
                                                : text + Lang.getDefault());
                            }

                            if (gameSettingsOptions == GameSettingsOptions.CLEAR_WATER) {
                                return this.ofClearWater
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            }

                            if (gameSettingsOptions == GameSettingsOptions.AA_LEVEL) {
                                String textValue3 = packedTextFragments[18];
                                if (this.ofAaLevel != OptiFineConfig.getAntialiasingLevel()) {
                                    textValue3 =
                                            " ("
                                                    + Lang.get("of.general.restart")
                                                    + packedTextFragments[19];
                                }

                                return this.ofAaLevel == 0
                                        ? text + Lang.getOff() + textValue3
                                        : text + this.ofAaLevel + textValue3;
                            } else if (gameSettingsOptions == GameSettingsOptions.AF_LEVEL) {
                                return this.ofAfLevel == 1
                                        ? text + Lang.getOff()
                                        : text + this.ofAfLevel;
                            } else if (gameSettingsOptions == GameSettingsOptions.PROFILER) {
                                return this.ofProfiler ? text + Lang.getOn() : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.BETTER_SNOW) {
                                return this.ofBetterSnow
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.SWAMP_COLORS) {
                                return this.ofSwampColors
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.RANDOM_MOBS) {
                                return this.ofRandomMobs
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.SMOOTH_BIOMES) {
                                return this.ofSmoothBiomes
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.CUSTOM_FONTS) {
                                return this.ofCustomFonts
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.CUSTOM_COLORS) {
                                return this.ofCustomColors
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.CUSTOM_SKY) {
                                return this.ofCustomSky
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.SHOW_CAPES) {
                                return this.ofShowCapes
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.CUSTOM_ITEMS) {
                                return this.ofCustomItems
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions
                                    == GameSettingsOptions.NATURAL_TEXTURES) {
                                return this.ofNaturalTextures
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.FAST_MATH) {
                                return this.ofFastMath ? text + Lang.getOn() : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.FAST_RENDER) {
                                return this.ofFastRender
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions
                                    == GameSettingsOptions.TRANSLUCENT_BLOCKS) {
                                return this.ofTranslucentBlocks == 1
                                        ? text + Lang.getFast()
                                        : (this.ofTranslucentBlocks == 2
                                                ? text + Lang.getFancy()
                                                : text + Lang.getDefault());
                            } else if (gameSettingsOptions
                                    == GameSettingsOptions.LAZY_CHUNK_LOADING) {
                                return this.ofLazyChunkLoading
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.DYNAMIC_FOV) {
                                return this.ofDynamicFov
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.DYNAMIC_LIGHTS) {
                                int i =
                                        indexOf(
                                                this.ofDynamicLights,
                                                net.minecraft.client.settings.GameSettings
                                                        .OF_DYNAMIC_LIGHTS);
                                return text
                                        + getTranslation(
                                                net.minecraft.client.settings.GameSettings
                                                        .KEYS_DYNAMIC_LIGHTS,
                                                i);
                            } else if (gameSettingsOptions == GameSettingsOptions.FULLSCREEN_MODE) {
                                return this.ofFullscreenMode.equals("Default")
                                        ? text + Lang.getDefault()
                                        : text + this.ofFullscreenMode;
                            } else if (gameSettingsOptions
                                    == GameSettingsOptions.HELD_ITEM_TOOLTIPS) {
                                return this.heldItemTooltips
                                        ? text + Lang.getOn()
                                        : text + Lang.getOff();
                            } else if (gameSettingsOptions == GameSettingsOptions.FRAMERATE_LIMIT) {
                                float fx = this.getOptionFloatValue(gameSettingsOptions);
                                return fx == 0.0F
                                        ? text + Lang.get("of.options.framerateLimit.vsync")
                                        : (fx == GameSettingsOptions.access$000(gameSettingsOptions)
                                                ? text
                                                        + I18n.format(
                                                                "options.framerateLimit.max",
                                                                new Object[0])
                                                : text + (int) fx + " fps");
                            } else {
                                return null;
                            }
                        }
                    }
                }
            }
        }
    }

    public void setAllAnimations(boolean aJaValue) {
        int ofAnimatedLavaValue = aJaValue ? 0 : 2;
        this.ofAnimatedWater = ofAnimatedLavaValue;
        this.ofAnimatedLava = ofAnimatedLavaValue;
        this.ofAnimatedFire = aJaValue;
        this.ofAnimatedPortal = aJaValue;
        this.ofAnimatedRedstone = aJaValue;
        this.ofAnimatedExplosion = aJaValue;
        this.ofAnimatedFlame = aJaValue;
        this.ofAnimatedSmoke = aJaValue;
        this.ofVoidParticles = aJaValue;
        this.ofWaterParticles = aJaValue;
        this.ofRainSplash = aJaValue;
        this.ofPortalParticles = aJaValue;
        this.ofPotionParticles = aJaValue;
        this.ofFireworkParticles = aJaValue;
        this.particleSetting = aJaValue ? 0 : 2;
        this.ofDrippingWaterLava = aJaValue;
        this.ofAnimatedTerrain = aJaValue;
        this.ofAnimatedTextures = aJaValue;
    }

    private static String getTranslation(String[] astring, int i) {
        if (i < 0 || i >= astring.length) {
            i = 0;
        }

        return I18n.format(astring[i], new Object[0]);
    }
}
