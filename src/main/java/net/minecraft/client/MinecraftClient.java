package net.minecraft.client;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.MusicTickerMusicType;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.ClientPlayHandler;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.ResourcePackRepositoryEntry;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.stream.IStream;
import net.minecraft.client.stream.NullStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayerEnumChatVisibility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.ConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatusEnumState;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.profiler.ProfilerResult;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.GameTimer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Util;
import net.minecraft.util.UtilEnumOS;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.config.ConfigFileManager;
import remix.event.InputType;
import remix.event.impl.ClientTickEvent;
import remix.input.InputEvent;
import remix.module.combat.AuraModule;
import remix.module.hidden.HypixelLocrawListenerModule;
import remix.module.hidden.MiniWitherStatusModule;
import remix.module.movement.BlinkModule;
import remix.module.player.AutoHypixelModule;
import remix.module.player.NoSwingModule;
import remix.module.render.CameraModule;
import remix.module.render.FreeCamModule;
import remix.module.world.AutoConfigModule;
import remix.module.world.CivBreakModule;
import remix.ui.screen.OfflineUuidResolver;
import remix.ui.screen.RemixLoginScreen;
import remix.ui.screen.RemixMainMenuScreen;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.imageio.ImageIO;

public class MinecraftClient implements IPlayerUsage, IThreadListener {
    int OF;
    public static byte[] memoryReserve;
    public boolean renderChunksMany;
    private String serverName;
    private boolean hasCrashed;
    private IStream stream;
    private RenderItem renderItem;
    private boolean alZ;
    public LocalPlayer player;
    private PlayerUsageSnooper usageSnooper;
    public FontRenderer standardGalacticFontRenderer;
    public GameSettings gameSettings;
    public EntityRenderer entityRenderer;
    private ServerData currentServerData;
    public RenderGlobal renderGlobal;

    private MinecraftSessionService sessionService;
    public GuiAchievement guiAchievement;
    public Profiler mcProfiler;
    public boolean amk;
    public volatile boolean running;
    public int displayWidth;
    public int leftClickCounter;
    private static String legacyClientFlowMarker;
    private Thread mcThread;
    private boolean integratedServerIsRunning;
    private int ticksSincePlayerChunkJoin;
    private boolean gamePaused;
    private int serverPort;
    long prevFrameTime;
    private long debugCrashKeyPressTime;
    private static ResourceLocation locationMojangPng;
    public EffectRenderer effectRenderer;
    public static boolean isRunningOnMac;
    private ItemRenderer itemRenderer;
    private String launchedVersion;
    private CrashReport crashReporter;
    private SoundHandler mcSoundHandler;
    private ISaveFormat saveLoader;
    public boolean skipWorldRender;
    private int tempDisplayWidth;
    private SkinManager skinManager;
    private File fileAssets;
    private static List macDisplayModes;
    private String debugProfilerName;
    private boolean enableGLErrorChecking = Boolean.getBoolean("remix.debugGlErrors");
    private MusicTicker mcMusicTicker;
    public GuiScreen currentScreen;
    private static MinecraftClient theMinecraft;
    public MouseHelper mouseHelper;
    private PropertyMap twitchDetails;
    public boolean amE;
    public ClientWorld world;
    private ResourceLocation mojangLogo;
    public int displayHeight;
    private Entity renderViewEntity;
    private NetworkManager myNetworkManager;
    private RenderManager renderManager;
    long systemTime;
    public File gameDirectory;
    public LoadingScreenRenderer loadingScreen;
    int framesSinceFpsUpdate;
    long debugUpdateTime;
    private IntegratedServer theIntegratedServer;
    public RayTraceResult objectMouseOver;
    private Proxy proxy;
    public Entity pointedEntity;
    public String debug;
    private ModelManager modelManager;
    private long amU;
    private Queue scheduledTasks;
    private static int debugFPS;
    public PlayerController playerController;
    public GameTimer timer = new GameTimer(20.0F);
    public boolean amY;
    private LanguageManager mcLanguageManager;
    public Session session;
    private boolean isDemo;
    private DefaultResourcePack mcDefaultResourcePack;

    public IReloadableResourceManager mcResourceManager;
    private List defaultResourcePacks;
    private IMetadataSerializer metadataSerializer_;
    public GuiIngame ingameGUI;
    private boolean jvm64bit;
    private int tempDisplayHeight;
    private BlockRendererDispatcher blockRenderDispatcher;
    private TextureManager renderEngine;
    public boolean inGameHasFocus;
    private TextureMap textureMapBlocks;
    private static String[] packedTextFragments;
    private static Logger logger;
    private boolean fullscreen;
    private ResourcePackRepository mcResourcePackRepository;
    public int rightClickDelayTimer;
    private Framebuffer framebufferMc;
    public FontRenderer fontRendererObj;
    private File fileResourcepacks;

    public static boolean isGuiEnabled() {
        return net.minecraft.client.MinecraftClient.theMinecraft == null
                || !net.minecraft.client.MinecraftClient.theMinecraft.gameSettings.hideGUI;
    }

    public static ByteBuffer readWindowIconPixels(InputStream stream) throws IOException {
        BufferedImage image = ImageIO.read(stream);
        if (image == null) {
            throw new IOException("Unable to decode window icon");
        }
        int width = image.getWidth();
        int[] pixels = image.getRGB(0, 0, width, image.getHeight(), null, 0, width);
        ByteBuffer rgba = ByteBuffer.allocate(4 * pixels.length);
        boolean legacyFirstPixelOnly = getLegacyClientFlowMarker() != null;
        for (int argb : pixels) {
            rgba.putInt(argb << 8 | argb >>> 24);
            if (legacyFirstPixelOnly) {
                break;
            }
        }

        ((Buffer) rgba).flip();
        return rgba;
    }

    public Entity getRenderViewEntity() {
        return this.renderViewEntity;
    }

    // region String decoding support

    // endregion

    public RenderManager getRenderManager() {
        return this.renderManager;
    }

    public IntegratedServer getIntegratedServer() {
        return this.theIntegratedServer;
    }

    public void shutdown() {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        ClientServices.configFileManager.saveConfigs();
        RemixClient.INSTANCE.stopClient();
        this.running = false;
    }

    private void runGameLoop() throws IOException {
        boolean legacyFrameFlow = getLegacyClientFlowMarker() != null;
        this.mcProfiler.startSection("root");
        if (Display.isCreated() && Display.isCloseRequested()) {
            this.shutdown();
        }
        this.advanceFrameTimer(legacyFrameFlow);
        this.runFrameTasks(legacyFrameFlow);

        long tickStart = System.nanoTime();
        this.mcProfiler.startSection("tick");
        for (int index = 0; index < this.timer.elapsedTicks; index++) {
            this.runTick();
            if (legacyFrameFlow) {
                break;
            }
        }
        this.mcProfiler.endStartSection("preRenderErrors");
        long tickDuration = System.nanoTime() - tickStart;

        this.renderClientFrame(tickDuration, legacyFrameFlow);
        this.mcProfiler.startSection("root");
        this.updateDisplay();
        Thread.yield();
        this.mcProfiler.startSection("stream");
        this.mcProfiler.startSection("update");
        this.stream.updateStreaming();
        this.mcProfiler.endStartSection("submit");
        this.stream.submitStreamFrame();
        this.mcProfiler.endSection();
        this.mcProfiler.endSection();
        this.checkGLError("Post render");

        this.framesSinceFpsUpdate++;
        this.gamePaused =
                this.isSingleplayer()
                        && this.currentScreen != null
                        && this.currentScreen.doesGuiPauseGame()
                        && !this.theIntegratedServer.isPublishedToLan();
        this.updateFrameStatistics(legacyFrameFlow);
        if (this.isFramerateLimitBelowMax()) {
            this.mcProfiler.startSection("fpslimit_wait");
            Display.sync(this.getLimitFramerate());
            this.mcProfiler.endSection();
        }
        this.mcProfiler.endSection();
    }

    private void advanceFrameTimer(boolean legacyFrameFlow) {
        if (this.gamePaused && this.world != null) {
            float pausedPartialTicks = this.timer.renderPartialTicks;
            this.timer.updateTimer();
            this.timer.renderPartialTicks = pausedPartialTicks;
            if (!legacyFrameFlow) {
                return;
            }
        }
        this.timer.updateTimer();
    }

    private void runFrameTasks(boolean legacyFrameFlow) {
        this.mcProfiler.startSection("scheduledExecutables");
        synchronized (this.scheduledTasks) {
            while (!this.scheduledTasks.isEmpty()) {
                ((FutureTask) this.scheduledTasks.poll()).run();
                if (legacyFrameFlow) {
                    break;
                }
            }
        }
        this.mcProfiler.endSection();
    }

    private void renderClientFrame(long tickDuration, boolean legacyFrameFlow) {
        this.checkGLError("Pre render");
        this.mcProfiler.endStartSection("sound");
        this.mcSoundHandler.setListener(this.player, this.timer.renderPartialTicks);
        this.mcProfiler.endSection();

        this.mcProfiler.startSection("render");
        GlStateManager.pushMatrix();
        GlStateManager.clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        this.framebufferMc.bindFramebuffer(true);
        this.mcProfiler.startSection("display");
        GlStateManager.enableTexture2D();
        this.keepCameraOutsideOpaqueBlocks();
        this.mcProfiler.endSection();

        if (!this.skipWorldRender) {
            this.mcProfiler.endStartSection("gameRenderer");
            this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks);
            this.mcProfiler.endSection();
        }
        this.mcProfiler.endSection();
        this.updateProfilerDisplay(tickDuration, legacyFrameFlow);

        this.guiAchievement.updateAchievementWindow();
        this.framebufferMc.unbindFramebuffer();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        this.framebufferMc.framebufferRender(this.displayWidth, this.displayHeight);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        this.entityRenderer.renderStreamIndicator(this.timer.renderPartialTicks);
        GlStateManager.popMatrix();
    }

    private void keepCameraOutsideOpaqueBlocks() {
        if (this.player != null && this.player.isEntityInsideOpaqueBlock()) {
            RemixClient.INSTANCE.getClientServices();
            CameraModule camera = ClientServices.moduleManager.camera;
            if (!camera.isEnabled() || !camera.viewClipSetting.asBoolean()) {
                this.gameSettings.thirdPersonView = 0;
            }
        }
    }

    private void updateProfilerDisplay(long tickDuration, boolean legacyFrameFlow) {
        if (this.gameSettings.showDebugInfo
                && this.gameSettings.showDebugProfilerChart
                && !this.gameSettings.hideGUI) {
            if (!this.mcProfiler.profilingEnabled) {
                this.mcProfiler.clearProfiling();
            }
            this.mcProfiler.profilingEnabled = true;
            this.displayDebugInfo(tickDuration);
            if (!legacyFrameFlow) {
                return;
            }
        }
        this.mcProfiler.profilingEnabled = false;
        this.prevFrameTime = System.nanoTime();
    }

    private void updateFrameStatistics(boolean legacyFrameFlow) {
        while (getSystemTime() >= this.debugUpdateTime + 1000L) {
            debugFPS = this.framesSinceFpsUpdate;
            int chunkUpdates = RenderChunk.renderChunksUpdated;
            this.debug =
                    String.format(
                            "%d fps (%d chunk update%s) T: %s%s%s%s%s",
                            debugFPS,
                            chunkUpdates,
                            chunkUpdates == 1 ? "" : "s",
                            this.gameSettings.limitFramerate
                                            == GameSettingsOptions.FRAMERATE_LIMIT.getValueMax()
                                    ? "inf"
                                    : this.gameSettings.limitFramerate,
                            this.gameSettings.enableVsync ? " vsync" : "",
                            this.gameSettings.fancyGraphics ? "" : " fast",
                            this.gameSettings.clouds ? " clouds" : "",
                            OpenGlHelper.useVbo() ? " vbo" : "");
            RenderChunk.renderChunksUpdated = 0;
            this.debugUpdateTime += 1000L;
            this.framesSinceFpsUpdate = 0;
            this.usageSnooper.addMemoryStatsToSnooper();
            if (!this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.clearRunningFlag();
                if (legacyFrameFlow) {
                    break;
                }
            }
        }
    }

    private void displayDebugInfo(long frameDuration) {
        boolean firstEntryOnly = getLegacyClientFlowMarker() != null;
        if (!this.mcProfiler.profilingEnabled) {
            return;
        }

        List<ProfilerResult> sections = this.mcProfiler.getProfilingData(this.debugProfilerName);
        ProfilerResult selectedSection = sections.remove(0);
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.enableColorMaterial();
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, this.displayWidth, this.displayHeight, 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
        GL11.glLineWidth(1.0F);
        GlStateManager.disableTexture2D();

        Tessellator tessellator = Tessellator.getInstance();
        int radius = 160;
        int centerX = this.displayWidth - radius - 10;
        int centerY = this.displayHeight - radius * 2;
        this.drawProfilerBackground(tessellator, centerX, centerY, radius);

        double startPercent = 0.0;
        for (int index = 0; index < sections.size(); index++) {
            ProfilerResult section = sections.get(index);
            this.drawProfilerSlice(
                    tessellator, section, startPercent, centerX, centerY, radius, firstEntryOnly);
            startPercent += section.parentPercent;
            if (firstEntryOnly) {
                break;
            }
        }
        this.drawProfilerLegend(
                selectedSection, sections, centerX, centerY, radius, firstEntryOnly);
    }

    private void drawProfilerBackground(
            Tessellator tessellator, int centerX, int centerY, int radius) {
        WorldRenderer vertices = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        vertices.startDrawingQuads();
        vertices.setVertexColorFromRgbAndAlpha(0, 200);
        vertices.addVertex(centerX - radius * 1.1F, centerY - radius * 0.6F - 16.0F, 0.0);
        vertices.addVertex(centerX - radius * 1.1F, centerY + radius * 2, 0.0);
        vertices.addVertex(centerX + radius * 1.1F, centerY + radius * 2, 0.0);
        vertices.addVertex(centerX + radius * 1.1F, centerY - radius * 0.6F - 16.0F, 0.0);
        tessellator.draw();
        GlStateManager.disableBlend();
    }

    private void drawProfilerSlice(
            Tessellator tessellator,
            ProfilerResult section,
            double startPercent,
            int centerX,
            int centerY,
            int radius,
            boolean firstEntryOnly) {
        WorldRenderer vertices = tessellator.getWorldRenderer();
        int steps = MathHelper.floor(section.parentPercent / 4.0) + 1;
        vertices.startDrawing(GL11.GL_TRIANGLE_FAN);
        vertices.setOpaqueVertexColorFromRgb(section.getColor());
        vertices.addVertex(centerX, centerY, 0.0);
        this.drawProfilerSliceEdge(
                vertices,
                section.parentPercent,
                startPercent,
                steps,
                centerX,
                centerY,
                radius,
                false,
                firstEntryOnly);
        tessellator.draw();

        vertices.startDrawing(GL11.GL_TRIANGLE_STRIP);
        vertices.setOpaqueVertexColorFromRgb((section.getColor() & 16711422) >> 1);
        this.drawProfilerSliceEdge(
                vertices,
                section.parentPercent,
                startPercent,
                steps,
                centerX,
                centerY,
                radius,
                true,
                firstEntryOnly);
        tessellator.draw();
    }

    private void drawProfilerSliceEdge(
            WorldRenderer vertices,
            double percent,
            double startPercent,
            int steps,
            int centerX,
            int centerY,
            int radius,
            boolean drawDepth,
            boolean firstEntryOnly) {
        for (int step = steps; step >= 0; step--) {
            float angle = (float) ((startPercent + percent * step / steps) * Math.PI * 2.0 / 100.0);
            float offsetX = MathHelper.sin(angle) * radius;
            float offsetY = MathHelper.cos(angle) * radius * 0.5F;
            vertices.addVertex(centerX + offsetX, centerY - offsetY, 0.0);
            if (drawDepth) {
                vertices.addVertex(centerX + offsetX, centerY - offsetY + 10.0F, 0.0);
            }
            if (firstEntryOnly) {
                break;
            }
        }
    }

    private void drawProfilerLegend(
            ProfilerResult selected,
            List<ProfilerResult> sections,
            int centerX,
            int centerY,
            int radius,
            boolean legacyFlow) {
        DecimalFormat formatter = new DecimalFormat("##0.00");
        GlStateManager.enableTexture2D();
        String title = packedTextFragments[11];
        if (!selected.sectionName.equals("unspecified")) {
            title += "[0] ";
        }
        if (selected.sectionName.isEmpty()) {
            title += "ROOT ";
        }
        if (!selected.sectionName.isEmpty() || legacyFlow) {
            title += selected.sectionName + packedTextFragments[12];
        }

        int headerY = centerY - radius / 2 - 16;
        this.fontRendererObj.drawStringWithShadow(title, centerX - radius, headerY, 16777215);
        String rootTime = formatter.format(selected.totalPercent) + packedTextFragments[13];
        this.fontRendererObj.drawStringWithShadow(
                rootTime,
                centerX + radius - this.fontRendererObj.getStringWidth(rootTime),
                headerY,
                16777215);
        for (int index = 0; index < sections.size(); index++) {
            ProfilerResult section = sections.get(index);
            String label = packedTextFragments[14];
            boolean unspecified = section.sectionName.equals("unspecified");
            if (unspecified) {
                label += "[?] ";
            }
            if (!unspecified || legacyFlow) {
                label += packedTextFragments[15] + (index + 1) + "] ";
            }

            int rowY = centerY + radius / 2 + index * 8 + 20;
            this.fontRendererObj.drawStringWithShadow(
                    label + section.sectionName, centerX - radius, rowY, section.getColor());
            String parentTime = formatter.format(section.parentPercent) + packedTextFragments[16];
            this.fontRendererObj.drawStringWithShadow(
                    parentTime,
                    centerX + radius - 50 - this.fontRendererObj.getStringWidth(parentTime),
                    rowY,
                    section.getColor());
            String totalTime = formatter.format(section.totalPercent) + packedTextFragments[17];
            this.fontRendererObj.drawStringWithShadow(
                    totalTime,
                    centerX + radius - this.fontRendererObj.getStringWidth(totalTime),
                    rowY,
                    section.getColor());
            if (legacyFlow) {
                break;
            }
        }
    }

    public boolean isJava64bit() {
        return this.jvm64bit;
    }

    public MinecraftSessionService getSessionService() {
        return this.sessionService;
    }

    // region String decoding support

    // endregion

    public ListenableFuture scheduleResourcesRefresh() {
        return this.addScheduledTask(new ResourceReloadTask(this));
    }

    private void clickMouse() {
        String text = getLegacyClientFlowMarker();
        InputEvent inputEvent = new InputEvent(InputType.LEFT_CLICK);
        String textValue = text;
        RemixClient.eventBus.post(inputEvent);
        if (!inputEvent.isCancelled()) {
            if (this.leftClickCounter <= 0) {
                this.player.swingItem();
                if (this.objectMouseOver == null) {
                    net.minecraft.client.MinecraftClient.logger.error(
                            "Null returned as 'hitResult', this shouldn't happen!");
                    if (!this.playerController.isNotCreative()) {
                        return;
                    }

                    this.leftClickCounter = 10;
                    if (textValue == null) {
                        return;
                    }
                }

                switch (this.objectMouseOver.typeOfHit) {
                    case ENTITY:
                        this.playerController.attackEntity(
                                this.player, this.objectMouseOver.entityHit);
                        if (textValue == null) {
                            break;
                        }
                    case BLOCK:
                        BlockPos blockPos = this.objectMouseOver.getBlockPos();
                        if (this.world.getBlockState(blockPos).getBlock().getMaterial()
                                != Material.AIR) {
                            this.playerController.clickBlock(
                                    blockPos, this.objectMouseOver.sideHit);
                            if (textValue == null) {
                                break;
                            }
                        }
                    case MISS:
                    default:
                        if (this.playerController.isNotCreative()) {
                            this.leftClickCounter = 10;
                        }
                }
            }
        }
    }

    // region String decoding support

    // endregion

    public boolean isSnooperEnabled() {
        return this.gameSettings.snooperEnabled;
    }

    public boolean isGamePaused() {
        return this.gamePaused;
    }

    public static Map getSessionInfo() {
        HashMap hashmap = Maps.newHashMap();
        hashmap.put("X-Minecraft-Username", getMinecraft().getSession().getUsername());
        hashmap.put("X-Minecraft-UUID", getMinecraft().getSession().getPlayerID());
        hashmap.put("X-Minecraft-Version", "1.8");
        return hashmap;
    }

    public void freeMemory() {
        try {
            net.minecraft.client.MinecraftClient.memoryReserve = new byte[0];
            this.renderGlobal.deleteAllDisplayLists();
        } catch (Throwable throwable1) {
        }

        try {
            this.loadWorld(null);
        } catch (Throwable throwable) {
        }

        System.gc();
    }

    public ListenableFuture addScheduledTask(Runnable runnable) {
        Validate.notNull(runnable);
        return this.addScheduledTask(Executors.callable(runnable));
    }

    private static boolean isJvm64bit() {
        String[] astring = new String[] {"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
        String[] astring1 = astring;
        String text = getLegacyClientFlowMarker();
        int length = astring.length;
        String textValue = text;
        int j = 0;

        while (j < length) {
            String textValue2 = astring1[j];
            String property = System.getProperty(textValue2);
            if (property != null && property.contains("64")) {
                return true;
            }

            j++;
            if (textValue != null) {
                break;
            }
        }

        return false;
    }

    public ClientPlayHandler getNetworkHandler() {
        return this.player != null ? this.player.connection : null;
    }

    public void updateDisplay() {
        this.mcProfiler.startSection("display_update");
        Display.update();
        this.mcProfiler.endSection();
        this.checkWindowResize();
    }

    private void setWindowIcon() {
        UtilEnumOS osType = Util.getOSType();
        if (osType != UtilEnumOS.OSX) {
            Display.setIcon(loadWindowIcons());
        }
    }

    private static Throwable passThroughThrowable(Throwable throwable) {
        return throwable;
    }

    private void startTimerHackThread() {
        MinecraftTimerHackThread minecraftTimerHackThread =
                new MinecraftTimerHackThread(this, "Timer hack thread");
        minecraftTimerHackThread.setDaemon(true);
        minecraftTimerHackThread.start();
    }

    private ByteBuffer readImageToBuffer(InputStream inputstream) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(inputstream);
        int[] aint =
                bufferedimage.getRGB(
                        0,
                        0,
                        bufferedimage.getWidth(),
                        bufferedimage.getHeight(),
                        null,
                        0,
                        bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        int[] aint1 = aint;
        String text = getLegacyClientFlowMarker();
        int length = aint.length;
        int j = 0;
        String textValue = text;

        while (j < length) {
            int k = aint1[j];
            bytebuffer.putInt(k << 8 | k >> 24 & 0xFF);
            j++;
            if (textValue != null) {
                break;
            }
        }

        ((Buffer) bytebuffer).flip();
        return bytebuffer;
    }

    public void scaledTessellator(int i, int j, int k, int l, int i1, int j1) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(i + 0, j + j1, 0.0, (k + 0) * f, (l + j1) * f1);
        worldRenderer.addVertexWithUV(i + i1, j + j1, 0.0, (k + i1) * f, (l + j1) * f1);
        worldRenderer.addVertexWithUV(i + i1, j + 0, 0.0, (k + i1) * f, (l + 0) * f1);
        worldRenderer.addVertexWithUV(i + 0, j + 0, 0.0, (k + 0) * f, (l + 0) * f1);
        Tessellator.getInstance().draw();
    }

    public boolean isFramerateLimitBelowMax() {
        return this.getLimitFramerate() < GameSettingsOptions.FRAMERATE_LIMIT.getValueMax();
    }

    private void startGame() throws LWJGLException {
        this.gameSettings = new GameSettings(this, this.gameDirectory);
        this.defaultResourcePacks.add(this.mcDefaultResourcePack);
        this.startTimerHackThread();
        if (this.gameSettings.ni > 0 && this.gameSettings.L > 0) {
            this.displayWidth = this.gameSettings.L;
            this.displayHeight = this.gameSettings.ni;
        }

        net.minecraft.client.MinecraftClient.logger.info("LWJGL Version: " + Sys.getVersion());
        this.setWindowIcon();
        this.setInitialDisplayMode();
        this.createDisplay();
        OpenGlHelper.initializeTextures();
        this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true);
        this.framebufferMc.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
        this.registerMetadataSerializers();
        this.mcResourcePackRepository =
                new ResourcePackRepository(
                        this.fileResourcepacks,
                        new File(this.gameDirectory, "server-resource-packs"),
                        this.mcDefaultResourcePack,
                        this.metadataSerializer_,
                        this.gameSettings);
        this.mcResourceManager = new SimpleReloadableResourceManager(this.metadataSerializer_);
        this.mcLanguageManager =
                new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
        this.mcResourceManager.registerReloadListener(this.mcLanguageManager);
        this.refreshResources();
        this.renderEngine = new TextureManager(this.mcResourceManager);
        this.mcResourceManager.registerReloadListener(this.renderEngine);
        this.drawSplashScreen(this.renderEngine);
        this.initStream();
        this.skinManager =
                new SkinManager(
                        this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService);
        this.saveLoader = new AnvilSaveConverter(new File(this.gameDirectory, "saves"));
        this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
        this.mcMusicTicker = new MusicTicker(this);
        this.fontRendererObj =
                new FontRenderer(
                        this.gameSettings,
                        new ResourceLocation("textures/font/ascii.png"),
                        this.renderEngine,
                        false);
        if (this.gameSettings.language != null) {
            this.fontRendererObj.setUnicodeFlag(this.isUnicode());
            this.fontRendererObj.setBidiFlag(
                    this.mcLanguageManager.isCurrentLanguageBidirectional());
        }

        {
            this.standardGalacticFontRenderer =
                    new FontRenderer(
                            this.gameSettings,
                            new ResourceLocation("textures/font/ascii_sga.png"),
                            this.renderEngine,
                            false);
            this.mcResourceManager.registerReloadListener(this.fontRendererObj);
            this.mcResourceManager.registerReloadListener(this.standardGalacticFontRenderer);
            this.mcResourceManager.registerReloadListener(new GrassColorReloadListener());
            this.mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
            AchievementList.openInventory.setStatStringFormatter(textValue2 -> {
                try {
                    return String.format(
                            textValue2,
                            GameSettings.getKeyDisplayString(
                                    this.gameSettings.keyBindInventory.getKeyCode()));
                } catch (Exception exception) {
                    return "Error: " + exception.getLocalizedMessage();
                }
            });
            this.mouseHelper = new MouseHelper();
            this.checkGLError("Pre startup");
            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7425);
            GlStateManager.clearDepth(1.0);
            GlStateManager.enableDepth();
            GlStateManager.depthFunc(515);
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.cullFace(1029);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            this.checkGLError("Startup");
            this.textureMapBlocks = new TextureMap("textures");
            this.textureMapBlocks.setMipmapLevels(this.gameSettings.mipmapLevels);
            this.renderEngine.loadTickableTexture(
                    TextureMap.locationBlocksTexture, this.textureMapBlocks);
            this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            this.textureMapBlocks.setBlurMipmapDirect(false, this.gameSettings.mipmapLevels > 0);
            this.modelManager = new ModelManager(this.textureMapBlocks);
            this.mcResourceManager.registerReloadListener(this.modelManager);
            this.renderItem = new RenderItem(this.renderEngine, this.modelManager);
            this.renderManager = new RenderManager(this.renderEngine, this.renderItem);
            this.itemRenderer = new ItemRenderer(this);
            this.mcResourceManager.registerReloadListener(this.renderItem);
            this.entityRenderer = new EntityRenderer(this, this.mcResourceManager);
            this.mcResourceManager.registerReloadListener(this.entityRenderer);
            this.blockRenderDispatcher =
                    new BlockRendererDispatcher(
                            this.modelManager.getBlockModelShapes(), this.gameSettings);
            this.mcResourceManager.registerReloadListener(this.blockRenderDispatcher);
            this.renderGlobal = new RenderGlobal(this);
            this.mcResourceManager.registerReloadListener(this.renderGlobal);
            this.guiAchievement = new GuiAchievement(this);
            GlStateManager.viewport(0, 0, this.displayWidth, this.displayHeight);
            this.effectRenderer = new EffectRenderer(this.world, this.renderEngine);
            this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
            this.checkGLError("Post startup");
            this.ingameGUI = new GuiIngame(this);
            this.displayGuiScreen(new RemixLoginScreen());
        }

        this.renderEngine.deleteTexture(this.mojangLogo);
        this.mojangLogo = null;
        this.loadingScreen = new LoadingScreenRenderer(this);
        if (this.gameSettings.fullScreen && !this.fullscreen) {
            this.toggleFullscreen();
        }

        try {
            Display.setVSyncEnabled(this.gameSettings.enableVsync);
        } catch (OpenGLException openglexception) {
            this.gameSettings.enableVsync = false;
            this.gameSettings.saveOptions();
        }

        this.renderGlobal.makeEntityOutlineShader();
        if (this.serverName != null) {
            // GuiConnecting unloads the current world, which needs the initialized modules.
            this.displayGuiScreen(
                    new GuiConnecting(this.currentScreen, this, this.serverName, this.serverPort));
        }
    }

    public void openIngameMenu() {
        if (this.currentScreen == null) {
            this.displayGuiScreen(new GuiIngameMenu());
            if (this.isSingleplayer() && !this.theIntegratedServer.isPublishedToLan()) {
                this.mcSoundHandler.pauseSounds();
            }
        }
    }

    public static MinecraftClient getMinecraft() {
        return net.minecraft.client.MinecraftClient.theMinecraft;
    }

    public void launchIntegratedServer(
            String folderName, String worldName, WorldSettings settings) {
        boolean firstProgressUpdateOnly = getLegacyClientFlowMarker() != null;
        this.loadWorld(null);
        System.gc();
        ISaveHandler saveHandler = this.saveLoader.getSaveLoader(folderName, false);
        WorldInfo worldInfo = saveHandler.loadWorldInfo();
        if (worldInfo == null && settings != null) {
            worldInfo = new WorldInfo(settings, folderName);
            saveHandler.saveWorldInfo(worldInfo);
        }
        if (settings == null) {
            settings = new WorldSettings(worldInfo);
        }

        try {
            this.theIntegratedServer = new IntegratedServer(this, folderName, worldName, settings);
            this.theIntegratedServer.startServerThread();
            this.integratedServerIsRunning = true;
        } catch (Throwable failure) {
            CrashReport report = CrashReport.makeCrashReport(failure, "Starting integrated server");
            CrashReportCategory details = report.makeCategory("Starting integrated server");
            details.addCrashSection("Level ID", folderName);
            details.addCrashSection("Level Name", worldName);
            throw new ReportedException(report);
        }

        this.waitForIntegratedServer(firstProgressUpdateOnly);
        this.displayGuiScreen(null);
        this.connectToIntegratedServer();
    }

    private void waitForIntegratedServer(boolean firstProgressUpdateOnly) {
        this.loadingScreen.displaySavingString(I18n.format("menu.loadingLevel", new Object[0]));
        while (!this.theIntegratedServer.isServerInRunLoop()) {
            String statusKey = this.theIntegratedServer.getUserMessage();
            if (statusKey != null) {
                this.loadingScreen.displayLoadingString(I18n.format(statusKey, new Object[0]));
            }
            if (statusKey == null || firstProgressUpdateOnly) {
                this.loadingScreen.displayLoadingString(packedTextFragments[19]);
            }

            try {
                Thread.sleep(200L);
            } catch (InterruptedException ignored) {
            }
            if (firstProgressUpdateOnly) {
                break;
            }
        }
    }

    private void connectToIntegratedServer() {
        SocketAddress localEndpoint =
                this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
        NetworkManager connection = NetworkManager.provideLocalClient(localEndpoint);
        connection.setPacketHandler(new NetHandlerLoginClient(connection, this, null));
        connection.sendPacket(
                new C00Handshake(47, localEndpoint.toString(), 0, ConnectionState.LOGIN));
        connection.sendPacket(new C00PacketLoginStart(this.getSession().getProfile()));
        this.myNetworkManager = connection;
    }

    public void loadWorld(ClientWorld nextWorld, String loadingMessage) {
        if (ClientServices.moduleManager != null) {
            ClientServices.moduleManager.fullBright.clearWorldState();
            ClientServices.moduleManager.xray.clearWorldState();
            ClientServices.moduleManager.spectator.stopSpectating();
        }
        boolean legacyWorldMarkerSet = getLegacyClientFlowMarker() != null;
        if (nextWorld == null) {
            ClientServices clientServices =
                    (ClientServices) RemixClient.INSTANCE.getClientServices();
            BlinkModule blink = ClientServices.moduleManager.blink;
            if (blink.isEnabled()) {
                blink.toggle();
            }

            clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
            FreeCamModule freeCam = ClientServices.moduleManager.freeCam;
            if (freeCam.isEnabled()) {
                freeCam.toggle();
            }

            ClientPlayHandler networkHandler = this.getNetworkHandler();
            if (networkHandler != null) {
                networkHandler.cleanup();
            }

            if (this.theIntegratedServer != null && this.theIntegratedServer.isAnvilFileSet()) {
                this.theIntegratedServer.initiateShutdown();
                this.theIntegratedServer.publishServerInstance();
            }

            this.theIntegratedServer = null;
            this.guiAchievement.clearAchievements();
            this.entityRenderer.getMapItemRenderer().clearLoadedMaps();
        }

        this.renderViewEntity = null;
        this.myNetworkManager = null;
        if (this.loadingScreen != null) {
            this.loadingScreen.resetProgressAndMessage(loadingMessage);
            this.loadingScreen.displayLoadingString(packedTextFragments[21]);
        }

        if (nextWorld == null && this.world != null) {
            if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
                this.mcResourcePackRepository.clearServerResourcePack();
                this.scheduleResourcesRefresh();
            }

            this.setServerData(null);
            this.integratedServerIsRunning = false;
        }

        this.mcSoundHandler.stopSounds();
        this.world = nextWorld;
        ClientServices clientServicesValue =
                (ClientServices) RemixClient.INSTANCE.getClientServices();
        MiniWitherStatusModule miniWitherStatus = ClientServices.moduleManager.miniWitherStatus;
        if (miniWitherStatus.objectiveDestroyed) {
            miniWitherStatus.objectiveDestroyed = false;
        }

        clientServicesValue = (ClientServices) RemixClient.INSTANCE.getClientServices();
        AutoHypixelModule autoHypixel = ClientServices.moduleManager.autoHypixel;
        autoHypixel.nameTagHeightCutoff = -1.0;
        clientServicesValue = (ClientServices) RemixClient.INSTANCE.getClientServices();
        HypixelLocrawListenerModule locrawListener =
                ClientServices.moduleManager.hypixelLocrawListener;
        locrawListener.ticksSinceWorldChange = 0;
        clientServicesValue = (ClientServices) RemixClient.INSTANCE.getClientServices();
        AutoConfigModule autoConfig = ClientServices.moduleManager.autoConfig;
        autoConfig.joinTicks = 0;
        clientServicesValue = (ClientServices) RemixClient.INSTANCE.getClientServices();
        AuraModule aura = ClientServices.moduleManager.aura;
        if (aura.isEnabled() && aura.disableOnWorldChange.asBoolean()) {
            aura.toggle();
        }

        if (nextWorld != null) {
            if (this.renderGlobal != null) {
                this.renderGlobal.setWorldAndLoadRenderers(nextWorld);
            }
            if (this.effectRenderer != null) {
                this.effectRenderer.clearEffects(nextWorld);
            }
            if (this.player == null) {
                this.player =
                        this.playerController.createLocalPlayer(nextWorld, new StatFileWriter());
                this.playerController.flipPlayer(this.player);
            }

            this.player.preparePlayerToSpawn();
            nextWorld.spawnEntityInWorld(this.player);
            this.player.movementInput = new MovementInputFromOptions(this.gameSettings);
            this.playerController.setPlayerCapabilities(this.player);
            this.renderViewEntity = this.player;
        }
        if (nextWorld == null || legacyWorldMarkerSet) {
            this.saveLoader.flushCache();
            this.player = null;
        }

        this.systemTime = 0L;
    }

    public MusicTickerMusicType getAmbientMusicType() {
        return this.currentScreen instanceof GuiWinGame
                ? MusicTickerMusicType.CREDITS
                : (this.player != null
                        ? (this.player.world.provider instanceof WorldProviderHell
                                ? MusicTickerMusicType.NETHER
                                : (this.player.world.provider instanceof WorldProviderEnd
                                        ? (BossStatus.bossName != null
                                                        && BossStatus.statusBarTime > 0
                                                ? MusicTickerMusicType.END_BOSS
                                                : MusicTickerMusicType.END)
                                        : (this.player.capabilities.isCreativeMode
                                                        && this.player.capabilities.allowFlying
                                                ? MusicTickerMusicType.CREATIVE
                                                : MusicTickerMusicType.GAME)))
                        : MusicTickerMusicType.MENU);
    }

    public TextureManager getTextureManager() {
        return this.renderEngine;
    }

    public void runGame() {
        String text = getLegacyClientFlowMarker();
        this.running = true;
        String textValue = text;

        try {
            this.startGame();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Initializing game");
            crashReport.makeCategory("Initialization");
            this.displayCrashReport(this.addGraphicsAndWorldToCrashReport(crashReport));
            return;
        }

        try {
            while (true) {
                if (!this.running) {
                    return;
                }

                if (this.hasCrashed && this.crashReporter != null) {
                    break;
                }

                try {
                    this.runGameLoop();
                } catch (OutOfMemoryError outofmemoryerror) {
                    this.freeMemory();
                    this.displayGuiScreen(new GuiMemoryErrorScreen());
                    System.gc();
                    if (textValue == null) {
                        continue;
                    }
                    break;
                }
            }

            this.displayCrashReport(this.crashReporter);
        } catch (MinecraftError minecraftError) {
            return;
        } catch (ReportedException reportedException) {
            this.addGraphicsAndWorldToCrashReport(reportedException.getCrashReport());
            this.freeMemory();
            net.minecraft.client.MinecraftClient.logger.fatal(
                    "Reported exception thrown!", reportedException);
            this.displayCrashReport(reportedException.getCrashReport());
            return;
        } catch (Throwable throwable1) {
            CrashReport crashReportValue =
                    this.addGraphicsAndWorldToCrashReport(
                            new CrashReport("Unexpected error", throwable1));
            this.freeMemory();
            net.minecraft.client.MinecraftClient.logger.fatal(
                    "Unreported exception thrown!", throwable1);
            this.displayCrashReport(crashReportValue);
            return;
        } finally {
            this.shutdownMinecraftApplet();
        }
    }

    // region Static initialization and string tables
    static {
        net.minecraft.client.MinecraftClient.packedTextFragments = new String[26];
        net.minecraft.client.MinecraftClient.packedTextFragments[0] = new String("");
        net.minecraft.client.MinecraftClient.packedTextFragments[2] = new String("Jvzxgc");
        net.minecraft.client.MinecraftClient.packedTextFragments[3] = new String("s");
        net.minecraft.client.MinecraftClient.packedTextFragments[4] = new String("");
        net.minecraft.client.MinecraftClient.packedTextFragments[5] = new String("");
        net.minecraft.client.MinecraftClient.packedTextFragments[6] = new String("");
        net.minecraft.client.MinecraftClient.packedTextFragments[7] = new String("");
        net.minecraft.client.MinecraftClient.packedTextFragments[8] = new String("");
        net.minecraft.client.MinecraftClient.packedTextFragments[9] = new String(".");
        net.minecraft.client.MinecraftClient.packedTextFragments[10] = new String(".");
        net.minecraft.client.MinecraftClient.packedTextFragments[11] = new String("");
        net.minecraft.client.MinecraftClient.packedTextFragments[12] = new String(" ");
        net.minecraft.client.MinecraftClient.packedTextFragments[13] = new String("%");
        net.minecraft.client.MinecraftClient.packedTextFragments[14] = new String("");
        net.minecraft.client.MinecraftClient.packedTextFragments[15] = new String("[");
        net.minecraft.client.MinecraftClient.packedTextFragments[16] = new String("%");
        net.minecraft.client.MinecraftClient.packedTextFragments[17] = new String("%");
        net.minecraft.client.MinecraftClient.packedTextFragments[18] = new String("/");
        net.minecraft.client.MinecraftClient.packedTextFragments[19] = new String("");
        net.minecraft.client.MinecraftClient.packedTextFragments[20] = new String("");
        net.minecraft.client.MinecraftClient.packedTextFragments[21] = new String("");
        net.minecraft.client.MinecraftClient.packedTextFragments[22] = new String("]");
        net.minecraft.client.MinecraftClient.packedTextFragments[23] = new String("");
        net.minecraft.client.MinecraftClient.packedTextFragments[24] = new String("'");
        setLegacyClientFlowMarker(null);
        net.minecraft.client.MinecraftClient.logger = LogManager.getLogger();
        net.minecraft.client.MinecraftClient.locationMojangPng =
                new ResourceLocation("textures/gui/title/mojang.png");
        net.minecraft.client.MinecraftClient.isRunningOnMac =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (Util.getOSType() == UtilEnumOS.OSX ? 1 : 0));
        net.minecraft.client.MinecraftClient.memoryReserve = new byte[10485760];
        net.minecraft.client.MinecraftClient.macDisplayModes =
                Lists.newArrayList(
                        new DisplayMode[] {
                            new DisplayMode(2560, 1600), new DisplayMode(2880, 1800)
                        });
    }

    // endregion

    public PropertyMap getTwitchDetails() {
        return this.twitchDetails;
    }

    public String getVersion() {
        return this.launchedVersion;
    }

    private void sendClickBlockToController(boolean flag) {
        String text = getLegacyClientFlowMarker();
        if (!flag) {
            this.leftClickCounter = 0;
        }

        if (this.leftClickCounter <= 0 && !this.player.isUsingItem()) {
            ClientServices clientServices =
                    (ClientServices) RemixClient.INSTANCE.getClientServices();
            CivBreakModule civBreak = ClientServices.moduleManager.civBreak;
            if (flag
                    && this.objectMouseOver != null
                    && this.objectMouseOver.typeOfHit == RayHitType.BLOCK) {
                BlockPos blockPos = this.objectMouseOver.getBlockPos();
                if (this.world.getBlockState(blockPos).getBlock().getMaterial() != Material.AIR
                        && this.playerController.onPlayerDamageBlock(
                                blockPos, this.objectMouseOver.sideHit)) {
                    this.effectRenderer.addBlockHitEffects(blockPos, this.objectMouseOver.sideHit);
                    this.player.swingItem();
                }

                if (text == null) {
                    return;
                }
            }

            if (!civBreak.isEnabled()) {
                this.playerController.resetBlockRemoving();
            }
        }
    }

    public void refreshResources() {
        String text = getLegacyClientFlowMarker();
        ArrayList arrayList = Lists.newArrayList(this.defaultResourcePacks);
        Iterator iterator = this.mcResourcePackRepository.getRepositoryEntries().iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            ResourcePackRepositoryEntry resourcePackRepositoryEntry =
                    (ResourcePackRepositoryEntry) iterator.next();
            arrayList.add(resourcePackRepositoryEntry.getResourcePack());
            if (textValue != null) {
                break;
            }
        }

        if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
            arrayList.add(this.mcResourcePackRepository.getResourcePackInstance());
        }

        try {
            this.mcResourceManager.reloadResources(arrayList);
        } catch (RuntimeException runtimeexception) {
            net.minecraft.client.MinecraftClient.logger.info(
                    "Caught error stitching, removing all assigned resourcepacks",
                    runtimeexception);
            arrayList.clear();
            arrayList.addAll(this.defaultResourcePacks);
            this.mcResourcePackRepository.setRepositories(Collections.emptyList());
            this.mcResourceManager.reloadResources(arrayList);
            this.gameSettings.resourcePacks.clear();
            this.gameSettings.saveOptions();
        }

        this.mcLanguageManager.parseLanguageMetadata(arrayList);
        if (this.renderGlobal != null) {
            this.renderGlobal.loadRenderers();
        }
    }

    public TextureMap getTextureMapBlocks() {
        return this.textureMapBlocks;
    }

    public static ByteBuffer[] loadWindowIcons() {
        boolean legacyFirstIconOnly = getLegacyClientFlowMarker() != null;
        String[] iconPaths = {
            "/assets/minecraft/remix/icons/Icon16.png", "/assets/minecraft/remix/icons/Icon64.png"
        };
        ByteBuffer[] icons = new ByteBuffer[iconPaths.length];
        for (int index = 0; index < iconPaths.length; index++) {
            try (InputStream stream = MinecraftClient.class.getResourceAsStream(iconPaths[index])) {
                if (stream == null) {
                    throw new IOException("Missing window icon: " + iconPaths[index]);
                }
                icons[index] = readWindowIconPixels(stream);
                if (legacyFirstIconOnly) {
                    break;
                }
            } catch (Exception failure) {
                logger.warn("Unable to read window icon", failure);
                break;
            }
        }

        return icons;
    }

    public void displayCrashReport(CrashReport crashReport) {
        File reports = new File(getMinecraft().gameDirectory, "crash-reports");
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());
        File destination = new File(reports, "crash-" + timestamp + "-client.txt");
        Bootstrap.printToSYSOUT(crashReport.getCompleteReport());
        saveClientConfigsIfInitialized();

        File existingReport = crashReport.getFile();
        String savedPath =
                existingReport != null
                        ? existingReport.toString()
                        : crashReport.saveToFile(destination)
                                ? destination.getAbsolutePath()
                                : null;
        if (savedPath != null) {
            Bootstrap.printToSYSOUT(
                    "#@!@# Game crashed! Crash report saved to: #@!@# " + savedPath);
            System.exit(-1);
            return;
        }

        Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#");
        System.exit(-2);
    }

    public static void setLegacyClientFlowMarker(String text) {
        net.minecraft.client.MinecraftClient.legacyClientFlowMarker = text;
    }

    public ISaveFormat getSaveLoader() {
        return this.saveLoader;
    }

    public void toggleFullscreen() {
        boolean legacyFullscreenFlow = getLegacyClientFlowMarker() != null;
        try {
            this.fullscreen = !this.fullscreen;
            this.gameSettings.fullScreen = this.fullscreen;
            boolean useWindowedSize = !this.fullscreen;
            if (this.fullscreen) {
                this.updateDisplayMode();
                DisplayMode mode = Display.getDisplayMode();
                this.displayWidth = Math.max(1, mode.getWidth());
                this.displayHeight = Math.max(1, mode.getHeight());
                useWindowedSize = legacyFullscreenFlow && mode.getHeight() <= 0;
            }
            if (useWindowedSize) {
                Display.setDisplayMode(
                        new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
                this.displayWidth = Math.max(1, this.tempDisplayWidth);
                this.displayHeight = Math.max(1, this.tempDisplayHeight);
            }

            boolean hadScreen = this.currentScreen != null;
            if (hadScreen) {
                this.resize(this.displayWidth, this.displayHeight);
            }
            if (!hadScreen || legacyFullscreenFlow) {
                this.updateFramebufferSize();
            }

            Display.setFullscreen(this.fullscreen);
            Display.setVSyncEnabled(this.gameSettings.enableVsync);
            this.updateDisplay();
        } catch (Exception exception) {
            net.minecraft.client.MinecraftClient.logger.error(
                    "Couldn't toggle fullscreen", exception);
        }
    }

    public RenderItem getRenderItem() {
        return this.renderItem;
    }

    public CrashReport addGraphicsAndWorldToCrashReport(CrashReport crashReport) {
        crashReport
                .getCategory()
                .addCrashSectionCallable("Launched Version", () -> this.launchedVersion);
        crashReport.getCategory().addCrashSectionCallable("LWJGL", () -> Sys.getVersion());
        crashReport
                .getCategory()
                .addCrashSectionCallable(
                        "OpenGL",
                        () ->
                                GL11.glGetString(7937)
                                        + " GL version "
                                        + GL11.glGetString(7938)
                                        + ", "
                                        + GL11.glGetString(7936));
        crashReport
                .getCategory()
                .addCrashSectionCallable("GL Caps", () -> OpenGlHelper.getLogText());
        crashReport
                .getCategory()
                .addCrashSectionCallable(
                        "Using VBOs", () -> this.gameSettings.useVbo ? "Yes" : "No");
        crashReport
                .getCategory()
                .addCrashSectionCallable(
                        "Is Modded",
                        () -> {
                            String text = ClientBrandRetriever.getClientModName();
                            return !text.equals("vanilla")
                                    ? "Definitely; Client brand changed to '"
                                            + text
                                            + packedTextFragments[24]
                                    : (MinecraftClient.class.getSigners() == null
                                            ? "Very likely; Jar signature invalidated"
                                            : "Probably not. Jar signature remains and client brand"
                                                    + " is untouched.");
                        });
        crashReport.getCategory().addCrashSectionCallable("Type", () -> "Client (map_client.txt)");
        crashReport
                .getCategory()
                .addCrashSectionCallable(
                        "Resource Packs", () -> this.gameSettings.resourcePacks.toString());
        crashReport
                .getCategory()
                .addCrashSectionCallable(
                        "Current Language",
                        () -> this.mcLanguageManager.getCurrentLanguage().toString());
        crashReport
                .getCategory()
                .addCrashSectionCallable(
                        "Profiler Position",
                        () ->
                                this.mcProfiler.profilingEnabled
                                        ? this.mcProfiler.getNameOfLastSection()
                                        : "N/A (disabled)");
        if (this.world != null) {
            this.world.addWorldInfoToCrashReport(crashReport);
        }

        return crashReport;
    }

    public void setRenderViewEntity(Entity amHValue) {
        this.renderViewEntity = amHValue;
        this.entityRenderer.loadEntityShader(amHValue);
    }

    public void setIngameFocus() {
        if (Display.isActive() && !this.inGameHasFocus) {
            this.inGameHasFocus = true;
            this.mouseHelper.grabMouseCursor();
            this.displayGuiScreen(null);
            this.leftClickCounter = 10000;
        }
    }

    public void toggleFullscreen(boolean flag) {
        String text = getLegacyClientFlowMarker();

        try {
            if (flag) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                Display.setLocation(0, 0);
                Display.setFullscreen(false);
                Display.setResizable(false);
                if (text == null) {
                    return;
                }
            }

            System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
            Display.setDisplayMode(
                    new DisplayMode(getMinecraft().displayWidth, getMinecraft().displayHeight));
            Display.setResizable(true);
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int i = (int) ((dimension.getWidth() - Display.getWidth()) / 2.0);
            int j = (int) ((dimension.getHeight() - Display.getHeight()) / 2.0);
            Display.setLocation(i, j);
        } catch (LWJGLException lwjglexception) {
            lwjglexception.printStackTrace();
        }
    }

    public SoundHandler getSoundHandler() {
        return this.mcSoundHandler;
    }

    public SkinManager getSkinManager() {
        return this.skinManager;
    }

    private void selectProfilerSection(int i) {
        String text = getLegacyClientFlowMarker();
        List list = this.mcProfiler.getProfilingData(this.debugProfilerName);
        String textValue = text;
        if (list != null && !list.isEmpty()) {
            ProfilerResult profilerResult = (ProfilerResult) list.remove(0);
            if (i == 0) {
                if (profilerResult.sectionName.length() <= 0) {
                    return;
                }

                int j = this.debugProfilerName.lastIndexOf(packedTextFragments[9]);
                if (j >= 0) {
                    this.debugProfilerName = this.debugProfilerName.substring(0, j);
                }

                if (textValue == null) {
                    return;
                }
            }

            i--;
            if (i < list.size()
                    && !((ProfilerResult) list.get(i)).sectionName.equals("unspecified")) {
                if (this.debugProfilerName.length() > 0) {
                    this.debugProfilerName = this.debugProfilerName + packedTextFragments[10];
                }

                this.debugProfilerName =
                        this.debugProfilerName + ((ProfilerResult) list.get(i)).sectionName;
            }
        }
    }

    public ResourcePackRepository getResourcePackRepository() {
        return this.mcResourcePackRepository;
    }

    public int getLimitFramerate() {
        return this.world == null && this.currentScreen != null
                ? 30
                : this.gameSettings.limitFramerate;
    }

    public IStream getTwitchStream() {
        return this.stream;
    }

    private void updateFramebufferSize() {
        this.framebufferMc.createBindFramebuffer(this.displayWidth, this.displayHeight);
        if (this.entityRenderer != null) {
            this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
        }
    }

    protected void checkWindowResize() {
        if (!this.fullscreen && Display.wasResized()) {
            int displayWidthValue = this.displayWidth;
            int j = this.displayHeight;
            this.displayWidth = Display.getWidth();
            this.displayHeight = Display.getHeight();
            if (this.displayWidth != displayWidthValue || this.displayHeight != j) {
                if (this.displayWidth <= 0) {
                    this.displayWidth = 1;
                }

                if (this.displayHeight <= 0) {
                    this.displayHeight = 1;
                }

                this.resize(this.displayWidth, this.displayHeight);
            }
        }
    }

    private void resize(int i, int j) {
        this.displayWidth = Math.max(1, i);
        this.displayHeight = Math.max(1, j);
        if (this.currentScreen != null) {
            ScaledResolution scaledResolution = new ScaledResolution(this, i, j);
            this.currentScreen.onResize(
                    this, scaledResolution.getWidth(), scaledResolution.getHeight());
        }

        this.loadingScreen = new LoadingScreenRenderer(this);
        this.updateFramebufferSize();
    }

    public void addServerTypeToSnooper(PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addStatToSnooper("opengl_version", GL11.glGetString(7938));
        playerUsageSnooper.addStatToSnooper("opengl_vendor", GL11.glGetString(7936));
        playerUsageSnooper.addStatToSnooper(
                "client_brand", ClientBrandRetriever.getClientModName());
        playerUsageSnooper.addStatToSnooper("launched_version", this.launchedVersion);
        ContextCapabilities contextcapabilities = GLContext.getCapabilities();
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_arrays_of_arrays]", contextcapabilities.GL_ARB_arrays_of_arrays);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_base_instance]", contextcapabilities.GL_ARB_base_instance);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_blend_func_extended]", contextcapabilities.GL_ARB_blend_func_extended);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_clear_buffer_object]", contextcapabilities.GL_ARB_clear_buffer_object);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_color_buffer_float]", contextcapabilities.GL_ARB_color_buffer_float);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_compatibility]", contextcapabilities.GL_ARB_compatibility);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_compressed_texture_pixel_storage]",
                contextcapabilities.GL_ARB_compressed_texture_pixel_storage);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_compute_shader]", contextcapabilities.GL_ARB_compute_shader);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_copy_buffer]", contextcapabilities.GL_ARB_copy_buffer);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_copy_image]", contextcapabilities.GL_ARB_copy_image);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_depth_buffer_float]", contextcapabilities.GL_ARB_depth_buffer_float);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_compute_shader]", contextcapabilities.GL_ARB_compute_shader);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_copy_buffer]", contextcapabilities.GL_ARB_copy_buffer);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_copy_image]", contextcapabilities.GL_ARB_copy_image);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_depth_buffer_float]", contextcapabilities.GL_ARB_depth_buffer_float);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_depth_clamp]", contextcapabilities.GL_ARB_depth_clamp);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_depth_texture]", contextcapabilities.GL_ARB_depth_texture);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_draw_buffers]", contextcapabilities.GL_ARB_draw_buffers);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_draw_buffers_blend]", contextcapabilities.GL_ARB_draw_buffers_blend);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_draw_elements_base_vertex]",
                contextcapabilities.GL_ARB_draw_elements_base_vertex);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_draw_indirect]", contextcapabilities.GL_ARB_draw_indirect);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_draw_instanced]", contextcapabilities.GL_ARB_draw_instanced);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_explicit_attrib_location]",
                contextcapabilities.GL_ARB_explicit_attrib_location);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_explicit_uniform_location]",
                contextcapabilities.GL_ARB_explicit_uniform_location);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_fragment_layer_viewport]",
                contextcapabilities.GL_ARB_fragment_layer_viewport);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_fragment_program]", contextcapabilities.GL_ARB_fragment_program);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_fragment_shader]", contextcapabilities.GL_ARB_fragment_shader);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_fragment_program_shadow]",
                contextcapabilities.GL_ARB_fragment_program_shadow);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_framebuffer_object]", contextcapabilities.GL_ARB_framebuffer_object);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_framebuffer_sRGB]", contextcapabilities.GL_ARB_framebuffer_sRGB);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_geometry_shader4]", contextcapabilities.GL_ARB_geometry_shader4);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_gpu_shader5]", contextcapabilities.GL_ARB_gpu_shader5);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_half_float_pixel]", contextcapabilities.GL_ARB_half_float_pixel);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_half_float_vertex]", contextcapabilities.GL_ARB_half_float_vertex);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_instanced_arrays]", contextcapabilities.GL_ARB_instanced_arrays);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_map_buffer_alignment]",
                contextcapabilities.GL_ARB_map_buffer_alignment);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_map_buffer_range]", contextcapabilities.GL_ARB_map_buffer_range);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_multisample]", contextcapabilities.GL_ARB_multisample);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_multitexture]", contextcapabilities.GL_ARB_multitexture);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_occlusion_query2]", contextcapabilities.GL_ARB_occlusion_query2);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_pixel_buffer_object]", contextcapabilities.GL_ARB_pixel_buffer_object);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_seamless_cube_map]", contextcapabilities.GL_ARB_seamless_cube_map);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_shader_objects]", contextcapabilities.GL_ARB_shader_objects);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_shader_stencil_export]",
                contextcapabilities.GL_ARB_shader_stencil_export);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_shader_texture_lod]", contextcapabilities.GL_ARB_shader_texture_lod);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_shadow]", contextcapabilities.GL_ARB_shadow);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_shadow_ambient]", contextcapabilities.GL_ARB_shadow_ambient);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_stencil_texturing]", contextcapabilities.GL_ARB_stencil_texturing);
        playerUsageSnooper.addStatToSnooper("gl_caps[ARB_sync]", contextcapabilities.GL_ARB_sync);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_tessellation_shader]", contextcapabilities.GL_ARB_tessellation_shader);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_texture_border_clamp]",
                contextcapabilities.GL_ARB_texture_border_clamp);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_texture_buffer_object]",
                contextcapabilities.GL_ARB_texture_buffer_object);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_texture_cube_map]", contextcapabilities.GL_ARB_texture_cube_map);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_texture_cube_map_array]",
                contextcapabilities.GL_ARB_texture_cube_map_array);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_texture_non_power_of_two]",
                contextcapabilities.GL_ARB_texture_non_power_of_two);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_uniform_buffer_object]",
                contextcapabilities.GL_ARB_uniform_buffer_object);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_vertex_blend]", contextcapabilities.GL_ARB_vertex_blend);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_vertex_buffer_object]",
                contextcapabilities.GL_ARB_vertex_buffer_object);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_vertex_program]", contextcapabilities.GL_ARB_vertex_program);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[ARB_vertex_shader]", contextcapabilities.GL_ARB_vertex_shader);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_bindable_uniform]", contextcapabilities.GL_EXT_bindable_uniform);
        String text = getLegacyClientFlowMarker();
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_blend_equation_separate]",
                contextcapabilities.GL_EXT_blend_equation_separate);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_blend_func_separate]", contextcapabilities.GL_EXT_blend_func_separate);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_blend_minmax]", contextcapabilities.GL_EXT_blend_minmax);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_blend_subtract]", contextcapabilities.GL_EXT_blend_subtract);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_draw_instanced]", contextcapabilities.GL_EXT_draw_instanced);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_framebuffer_multisample]",
                contextcapabilities.GL_EXT_framebuffer_multisample);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_framebuffer_object]", contextcapabilities.GL_EXT_framebuffer_object);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_framebuffer_sRGB]", contextcapabilities.GL_EXT_framebuffer_sRGB);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_geometry_shader4]", contextcapabilities.GL_EXT_geometry_shader4);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_gpu_program_parameters]",
                contextcapabilities.GL_EXT_gpu_program_parameters);
        String textValue = text;
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_gpu_shader4]", contextcapabilities.GL_EXT_gpu_shader4);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_multi_draw_arrays]", contextcapabilities.GL_EXT_multi_draw_arrays);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_packed_depth_stencil]",
                contextcapabilities.GL_EXT_packed_depth_stencil);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_paletted_texture]", contextcapabilities.GL_EXT_paletted_texture);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_rescale_normal]", contextcapabilities.GL_EXT_rescale_normal);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_separate_shader_objects]",
                contextcapabilities.GL_EXT_separate_shader_objects);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_shader_image_load_store]",
                contextcapabilities.GL_EXT_shader_image_load_store);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_shadow_funcs]", contextcapabilities.GL_EXT_shadow_funcs);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_shared_texture_palette]",
                contextcapabilities.GL_EXT_shared_texture_palette);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_stencil_clear_tag]", contextcapabilities.GL_EXT_stencil_clear_tag);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_stencil_two_side]", contextcapabilities.GL_EXT_stencil_two_side);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_stencil_wrap]", contextcapabilities.GL_EXT_stencil_wrap);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_texture_3d]", contextcapabilities.GL_EXT_texture_3d);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_texture_array]", contextcapabilities.GL_EXT_texture_array);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_texture_buffer_object]",
                contextcapabilities.GL_EXT_texture_buffer_object);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_texture_integer]", contextcapabilities.GL_EXT_texture_integer);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_texture_lod_bias]", contextcapabilities.GL_EXT_texture_lod_bias);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_texture_sRGB]", contextcapabilities.GL_EXT_texture_sRGB);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_vertex_shader]", contextcapabilities.GL_EXT_vertex_shader);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[EXT_vertex_weighting]", contextcapabilities.GL_EXT_vertex_weighting);
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[gl_max_vertex_uniforms]", GL11.glGetInteger(35658));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[gl_max_fragment_uniforms]", GL11.glGetInteger(35657));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[gl_max_vertex_attribs]", GL11.glGetInteger(34921));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[gl_max_vertex_texture_image_units]", GL11.glGetInteger(35660));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(34930));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper(
                "gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(35071));
        GL11.glGetError();
        playerUsageSnooper.addStatToSnooper("gl_max_texture_size", getGLMaximumTextureSize());
        if (textValue != null) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }
    }

    public void shutdownMinecraftApplet() {
        RemixClient.INSTANCE.getClientServices();
        saveClientConfigsIfInitialized();

        try {
            this.stream.shutdownStream();
            net.minecraft.client.MinecraftClient.logger.info("Stopping!");

            try {
                this.loadWorld(null);
            } catch (Throwable failure) {
                logger.debug("World unload failed during shutdown", failure);
            }

            this.mcSoundHandler.unloadSounds();
        } finally {
            RemixClient.INSTANCE.stopClient();
            Display.destroy();
            if (!this.hasCrashed) {
                System.exit(0);
            }
        }
    }

    private static void saveClientConfigsIfInitialized() {
        ConfigFileManager configs = ClientServices.configFileManager;
        if (configs != null) {
            configs.saveConfigs();
        }
    }

    public static int getGLMaximumTextureSize() {
        String text = getLegacyClientFlowMarker();
        short short1 = 16384;
        String textValue = text;

        while (short1 > 0) {
            GL11.glTexImage2D(32868, 0, 6408, short1, short1, 0, 6408, 5121, (ByteBuffer) null);
            int i = GL11.glGetTexLevelParameteri(32868, 0, 4096);
            if (i != 0) {
                return short1;
            }

            short1 >>= 1;
            if (textValue != null) {
                break;
            }
        }

        return -1;
    }

    public void runTick() throws IOException {
        boolean legacyTickBranches = getLegacyClientFlowMarker() != null;
        RemixClient.eventBus.post(new ClientTickEvent());
        if (this.rightClickDelayTimer > 0) {
            this.rightClickDelayTimer--;
        }

        this.mcProfiler.startSection("gui");
        if (!this.gamePaused) {
            this.ingameGUI.updateTick();
        }

        this.mcProfiler.endSection();
        this.entityRenderer.getMouseOver(1.0F);
        this.mcProfiler.startSection("gameMode");
        if (!this.gamePaused && this.world != null) {
            this.playerController.updateController();
        }

        this.mcProfiler.endStartSection("textures");
        if (!this.gamePaused) {
            this.renderEngine.tick();
        }

        this.updateTickScreenTransition(legacyTickBranches);

        if (this.currentScreen != null) {
            this.leftClickCounter = 10000;
        }

        if (this.currentScreen != null) {
            try {
                this.currentScreen.handleInput();
            } catch (Throwable throwable1) {
                CrashReport crashReport =
                        CrashReport.makeCrashReport(throwable1, "Updating screen events");
                CrashReportCategory crashReportCategory =
                        crashReport.makeCategory("Affected screen");
                crashReportCategory.addCrashSectionCallable(
                        "Screen name", new MinecraftScreenCanonicalNameCallable1(this));
                throw new ReportedException(crashReport);
            }

            if (this.currentScreen != null) {
                try {
                    this.currentScreen.updateScreen();
                } catch (Throwable throwable) {
                    CrashReport crashReportValue =
                            CrashReport.makeCrashReport(throwable, "Ticking screen");
                    CrashReportCategory crashReportCategoryValue =
                            crashReportValue.makeCategory("Affected screen");
                    crashReportCategoryValue.addCrashSectionCallable(
                            "Screen name", new MinecraftScreenCanonicalNameCallable2(this));
                    throw new ReportedException(crashReportValue);
                }
            }
        }

        if (this.currentScreen == null || this.currentScreen.allowUserInput) {
            this.mcProfiler.endStartSection("mouse");
            this.processTickMouse(legacyTickBranches);

            if (this.leftClickCounter > 0) {
                this.leftClickCounter--;
            }

            this.mcProfiler.endStartSection("keyboard");

            this.processTickKeyboard(legacyTickBranches);
            this.processTickBindings(legacyTickBranches);
        }

        if (this.world != null) {
            if (this.player != null) {
                this.ticksSincePlayerChunkJoin++;
                if (this.ticksSincePlayerChunkJoin == 30) {
                    this.ticksSincePlayerChunkJoin = 0;
                    this.world.joinEntityInSurroundings(this.player);
                }
            }

            this.mcProfiler.endStartSection("gameRenderer");
            if (!this.gamePaused) {
                this.entityRenderer.updateRenderer();
            }

            this.mcProfiler.endStartSection("levelRenderer");
            if (!this.gamePaused) {
                this.renderGlobal.tickLevelRenderer();
            }

            this.mcProfiler.endStartSection("level");
            if (!this.gamePaused) {
                if (this.world.getLastLightningBolt() > 0) {
                    this.world.setLastLightningBolt(this.world.getLastLightningBolt() - 1);
                }

                this.world.updateEntities();
            }
        }

        if (!this.gamePaused) {
            this.mcMusicTicker.update();
            this.mcSoundHandler.update();
        }

        this.tickWorldAndPendingConnection(legacyTickBranches);
        this.mcProfiler.endSection();
        this.systemTime = getSystemTime();
    }

    private void updateTickScreenTransition(boolean legacyTickBranches) {
        if (this.currentScreen == null && this.player != null) {
            if (this.player.getHealth() <= 0.0F) {
                this.displayGuiScreen(null);
                if (!legacyTickBranches) {
                    return;
                }
            }
            if (!this.player.isPlayerSleeping() || this.world == null) {
                return;
            }
            this.displayGuiScreen(new GuiSleepMP());
            if (!legacyTickBranches) {
                return;
            }
        }
        if (this.currentScreen instanceof GuiSleepMP && !this.player.isPlayerSleeping()) {
            this.displayGuiScreen(null);
        }
    }

    private void processTickMouse(boolean legacyTickBranches) throws IOException {
        while (Mouse.next()) {
            int button = Mouse.getEventButton();
            boolean buttonDown = Mouse.getEventButtonState();
            KeyBinding.setKeyBindState(button - 100, buttonDown);
            if (buttonDown) {
                boolean spectatorMenuClick = this.player.isSpectator() && button == 2;
                if (spectatorMenuClick) {
                    this.ingameGUI.getSpectatorGui().openOrConfirmMenu();
                }
                if (!spectatorMenuClick || legacyTickBranches) {
                    KeyBinding.onKeyPress(button - 100);
                }
            }

            if (getSystemTime() - this.systemTime <= 200L) {
                this.processTickMouseWheel(Mouse.getEventDWheel(), legacyTickBranches);
                if (this.currentScreen == null && !this.inGameHasFocus && buttonDown) {
                    this.setIngameFocus();
                    if (legacyTickBranches && this.currentScreen != null) {
                        this.currentScreen.handleMouseInput();
                    }
                } else if (this.currentScreen != null) {
                    this.currentScreen.handleMouseInput();
                }
            }
            if (legacyTickBranches) {
                break;
            }
        }
    }

    private void processTickMouseWheel(int wheel, boolean legacyTickBranches) {
        if (wheel == 0) {
            return;
        }
        if (this.player.isSpectator()) {
            wheel = wheel < 0 ? -1 : 1;
            GuiSpectator spectator = this.ingameGUI.getSpectatorGui();
            boolean menuOpen = spectator.isMenuOpen();
            if (menuOpen) {
                spectator.scrollMenu(-wheel);
            }
            if (!menuOpen || legacyTickBranches) {
                float flySpeed = this.player.capabilities.getFlySpeed();
                this.player.capabilities.setFlySpeed(
                        MathHelper.clamp(flySpeed + wheel * 0.005F, 0.0F, 0.2F));
            }
            if (!legacyTickBranches) {
                return;
            }
        }
        this.player.inventory.changeCurrentItem(wheel);
    }

    private void updateManualDebugCrash() {
        boolean crashKeysDown =
                Keyboard.isKeyDown(Keyboard.KEY_C) && Keyboard.isKeyDown(Keyboard.KEY_F3);
        if (this.debugCrashKeyPressTime > 0L) {
            if (getSystemTime() - this.debugCrashKeyPressTime >= 6000L) {
                throw new ReportedException(
                        new CrashReport("Manually triggered debug crash", new Throwable()));
            }
            if (!crashKeysDown) {
                this.debugCrashKeyPressTime = -1L;
            }
        } else if (crashKeysDown) {
            this.debugCrashKeyPressTime = getSystemTime();
        }
    }

    private void processTickKeyboard(boolean legacyTickBranches) throws IOException {
        while (Keyboard.next()) {
            int l =
                    Keyboard.getEventKey() == 0
                            ? Keyboard.getEventCharacter() + 256
                            : Keyboard.getEventKey();
            KeyBinding.setKeyBindState(l, Keyboard.getEventKeyState());
            if (Keyboard.getEventKeyState()) {
                KeyBinding.onKeyPress(l);
            }

            this.updateManualDebugCrash();

            this.dispatchGlobalKeypresses();
            if (Keyboard.getEventKeyState()) {
                RemixClient.eventBus.post(
                        new InputEvent(Keyboard.getEventKey(), Keyboard.getEventCharacter()));
                if (l == 62 && this.entityRenderer != null) {
                    this.entityRenderer.switchUseShader();
                }

                boolean screenHadKeyboardInput = this.currentScreen != null;
                if (screenHadKeyboardInput) {
                    this.currentScreen.handleKeyboardInput();
                }
                if (!screenHadKeyboardInput || legacyTickBranches) {

                    if (l == 1) {
                        this.openIngameMenu();
                    }

                    if (l == 32 && Keyboard.isKeyDown(61) && this.ingameGUI != null) {
                        this.ingameGUI.getChatGUI().clearChatMessages();
                    }

                    if (l == 31 && Keyboard.isKeyDown(61)) {
                        this.refreshResources();
                    }

                    if (l == 20 && Keyboard.isKeyDown(61)) {
                        this.refreshResources();
                    }

                    if (l == 33 && Keyboard.isKeyDown(61)) {
                        boolean flag = Keyboard.isKeyDown(42) | Keyboard.isKeyDown(54);
                        this.gameSettings.setOptionValue(
                                GameSettingsOptions.RENDER_DISTANCE, flag ? -1 : 1);
                    }

                    if (l == 30 && Keyboard.isKeyDown(61)) {
                        this.renderGlobal.loadRenderers();
                    }

                    if (l == 35 && Keyboard.isKeyDown(61)) {
                        this.gameSettings.advancedItemTooltips =
                                remix.compat.compiler.JvmBooleanSlots.fromInt(
                                        (!this.gameSettings.advancedItemTooltips ? 1 : 0));
                        this.gameSettings.saveOptions();
                    }

                    if (l == 48 && Keyboard.isKeyDown(61)) {
                        this.renderManager.setDebugBoundingBox(
                                !this.renderManager.isDebugBoundingBox());
                    }

                    if (l == 25 && Keyboard.isKeyDown(61)) {
                        this.gameSettings.pauseOnLostFocus =
                                remix.compat.compiler.JvmBooleanSlots.fromInt(
                                        (!this.gameSettings.pauseOnLostFocus ? 1 : 0));
                        this.gameSettings.saveOptions();
                    }

                    if (l == 59) {
                        this.gameSettings.hideGUI =
                                remix.compat.compiler.JvmBooleanSlots.fromInt(
                                        (!this.gameSettings.hideGUI ? 1 : 0));
                    }

                    if (l == 61) {
                        this.gameSettings.showDebugInfo =
                                remix.compat.compiler.JvmBooleanSlots.fromInt(
                                        (!this.gameSettings.showDebugInfo ? 1 : 0));
                        this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
                    }

                    if (this.gameSettings.keyBindTogglePerspective.isPressed()) {
                        this.gameSettings.thirdPersonView++;
                        if (this.gameSettings.thirdPersonView > 2) {
                            this.gameSettings.thirdPersonView = 0;
                        }
                        boolean reattachedView = this.gameSettings.thirdPersonView == 0;
                        if (reattachedView) {
                            this.entityRenderer.loadEntityShader(this.getRenderViewEntity());
                        }
                        if ((!reattachedView || legacyTickBranches)
                                && this.gameSettings.thirdPersonView == 1) {
                            this.entityRenderer.loadEntityShader(null);
                        }
                    }

                    if (this.gameSettings.keyBindSmoothCamera.isPressed()) {
                        this.gameSettings.smoothCamera =
                                remix.compat.compiler.JvmBooleanSlots.fromInt(
                                        (!this.gameSettings.smoothCamera ? 1 : 0));
                    }
                }

                if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart) {
                    if (l == 11) {
                        this.selectProfilerSection(0);
                    }

                    int j1 = 0;

                    while (j1 < 9) {
                        if (l == 2 + j1) {
                            this.selectProfilerSection(j1 + 1);
                        }

                        j1++;
                        if (legacyTickBranches) {
                            break;
                        }
                    }

                    if (legacyTickBranches) {
                        break;
                    }
                }
            }
        }
    }

    private void processTickBindings(boolean legacyTickBranches) {
        int currentItem = 0;

        while (currentItem < 9) {
            if (this.gameSettings.keyBindsHotbar[currentItem].isPressed()) {
                boolean spectator = this.player.isSpectator();
                if (spectator) {
                    this.ingameGUI.getSpectatorGui().selectHotbarSlot(currentItem);
                }
                if (!spectator || legacyTickBranches) {
                    this.player.inventory.currentItem = currentItem;
                }
            }

            currentItem++;
            if (legacyTickBranches) {
                break;
            }
        }

        boolean canOpenChat =
                this.gameSettings.chatVisibility != EntityPlayerEnumChatVisibility.HIDDEN;

        while (this.gameSettings.keyBindInventory.isPressed()) {
            if (this.playerController.isRidingHorse()) {
                this.player.sendHorseInventory();
                if (!legacyTickBranches) {
                    continue;
                }
            }

            this.getNetworkHandler()
                    .addToSendQueue(
                            new C16PacketClientStatus(
                                    C16PacketClientStatusEnumState.OPEN_INVENTORY_ACHIEVEMENT));
            this.displayGuiScreen(new GuiInventory(this.player));
            if (legacyTickBranches) {
                break;
            }
        }

        while (this.gameSettings.keyBindDrop.isPressed()) {
            if (!this.player.isSpectator()) {
                this.player.dropOneItem(GuiScreen.isCtrlKeyDown());
                if (legacyTickBranches) {
                    break;
                }
            }
        }

        while (this.gameSettings.keyBindChat.isPressed() && canOpenChat) {
            this.displayGuiScreen(new GuiChat());
            if (legacyTickBranches) {
                break;
            }
        }

        if (this.currentScreen == null
                && this.gameSettings.keyBindCommand.isPressed()
                && canOpenChat) {
            this.displayGuiScreen(new GuiChat(packedTextFragments[18]));
        }

        boolean dispatchItemActions = !this.player.isUsingItem();
        if (!dispatchItemActions) {
            if (!this.gameSettings.keyBindUseItem.isKeyDown()) {
                this.playerController.onStoppedUsingItem(this.player);
            }
            while (this.gameSettings.keyBindAttack.isPressed()) {}
            while (this.gameSettings.keyBindUseItem.isPressed()) {}
            if (legacyTickBranches) {
                dispatchItemActions = this.gameSettings.keyBindPickBlock.isPressed();
            } else {
                while (this.gameSettings.keyBindPickBlock.isPressed()) {}
            }
        }
        if (dispatchItemActions) {
            while (this.gameSettings.keyBindAttack.isPressed()) {
                this.clickMouse();
                if (legacyTickBranches) {
                    break;
                }
            }
            while (this.gameSettings.keyBindUseItem.isPressed()) {
                this.rightClickMouse();
                if (legacyTickBranches) {
                    break;
                }
            }
            while (this.gameSettings.keyBindPickBlock.isPressed()) {
                this.pickBlockFromCrosshair();
                if (legacyTickBranches) {
                    break;
                }
            }
        }

        if (this.gameSettings.keyBindUseItem.isKeyDown()
                && this.rightClickDelayTimer == 0
                && !this.player.isUsingItem()) {
            this.rightClickMouse();
        }

        this.sendClickBlockToController(
                this.currentScreen == null
                        && this.gameSettings.keyBindAttack.isKeyDown()
                        && this.inGameHasFocus);
    }

    private void tickWorldAndPendingConnection(boolean legacyTickBranches) {
        boolean pollPendingConnection = this.world == null;
        if (this.world != null) {
            if (!this.gamePaused) {
                this.world.setAllowedSpawnTypes(
                        this.world.getDifficulty() != EnumDifficulty.PEACEFUL, true);
                try {
                    this.world.tick();
                } catch (Throwable failure) {
                    CrashReport report =
                            CrashReport.makeCrashReport(failure, "Exception in world tick");
                    if (this.world == null) {
                        report.makeCategory("Affected level")
                                .addCrashSection("Problem", "Level is null!");
                    } else {
                        this.world.addWorldInfoToCrashReport(report);
                    }
                    throw new ReportedException(report);
                }
            }

            this.mcProfiler.endStartSection("animateTick");
            if (!this.gamePaused && this.world != null) {
                this.world.doVoidFogParticles(
                        MathHelper.floor(this.player.posX),
                        MathHelper.floor(this.player.posY),
                        MathHelper.floor(this.player.posZ));
            }

            this.mcProfiler.endStartSection("particles");
            if (!this.gamePaused) {
                this.effectRenderer.updateEffects();
                pollPendingConnection = legacyTickBranches;
            }
        }

        if (pollPendingConnection && this.myNetworkManager != null) {
            this.mcProfiler.endStartSection("pendingConnection");
            this.myNetworkManager.processReceivedPackets();
        }
    }

    public IResourceManager getResourceManager() {
        return this.mcResourceManager;
    }

    public final boolean isDemo() {
        return this.isDemo;
    }

    public void setServerData(ServerData amfValue) {
        this.currentServerData = amfValue;
    }

    public boolean isIntegratedServerRunning() {
        return this.integratedServerIsRunning;
    }

    public boolean isSingleplayer() {
        return this.integratedServerIsRunning && this.theIntegratedServer != null;
    }

    public LanguageManager getLanguageManager() {
        return this.mcLanguageManager;
    }

    public ServerData getCurrentServerData() {
        return this.currentServerData;
    }

    public boolean isUnicode() {
        return this.mcLanguageManager.isCurrentLocaleUnicode()
                || this.gameSettings.forceUnicodeFont;
    }

    private void drawSplashScreen(TextureManager textureManager) {
        ScaledResolution scaledResolution =
                new ScaledResolution(this, this.displayWidth, this.displayHeight);
        int scaleFactor = scaledResolution.getScaleFactor();
        Framebuffer framebuffer =
                new Framebuffer(
                        scaledResolution.getWidth() * scaleFactor,
                        scaledResolution.getHeight() * scaleFactor,
                        true);
        framebuffer.bindFramebuffer(false);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(
                0.0,
                scaledResolution.getWidth(),
                scaledResolution.getHeight(),
                0.0,
                1000.0,
                3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        InputStream inputstream = null;

        try {
            inputstream =
                    this.mcDefaultResourcePack.getInputStream(
                            net.minecraft.client.MinecraftClient.locationMojangPng);
            this.mojangLogo =
                    textureManager.getDynamicTextureLocation(
                            "logo", new DynamicTexture(ImageIO.read(inputstream)));
            textureManager.bindTexture(this.mojangLogo);
        } catch (IOException ioexception) {
            net.minecraft.client.MinecraftClient.logger.error(
                    "Unable to load logo: "
                            + net.minecraft.client.MinecraftClient.locationMojangPng,
                    ioexception);
        } finally {
            IOUtils.closeQuietly(inputstream);
        }

        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.setOpaqueVertexColorFromRgb(16777215);
        worldRenderer.addVertexWithUV(0.0, this.displayHeight, 0.0, 0.0, 0.0);
        worldRenderer.addVertexWithUV(this.displayWidth, this.displayHeight, 0.0, 0.0, 0.0);
        worldRenderer.addVertexWithUV(this.displayWidth, 0.0, 0.0, 0.0, 0.0);
        worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        instance.draw();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        worldRenderer.setOpaqueVertexColorFromRgb(16777215);
        short short1 = 256;
        short short2 = 256;
        this.scaledTessellator(
                (scaledResolution.getWidth() - short1) / 2,
                (scaledResolution.getHeight() - short2) / 2,
                0,
                0,
                short1,
                short2);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(
                scaledResolution.getWidth() * scaleFactor,
                scaledResolution.getHeight() * scaleFactor);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        this.updateDisplay();
    }

    public void displayGuiScreen(GuiScreen screen) {
        boolean legacyFlow = getLegacyClientFlowMarker() != null;
        if (this.currentScreen != null) {
            this.currentScreen.onGuiClosed();
        }

        if (screen == null) {
            if (this.world == null) {
                screen = new RemixMainMenuScreen(RemixClient.INSTANCE);
            } else if (this.player.getHealth() <= 0.0F) {
                screen = new GuiGameOver();
            }
        }
        if (screen instanceof RemixMainMenuScreen) {
            this.gameSettings.showDebugInfo = false;
            this.ingameGUI.getChatGUI().clearChatMessages();
        }

        this.currentScreen = screen;
        if (screen != null) {
            this.setIngameNotInFocus();
            ScaledResolution resolution =
                    new ScaledResolution(this, this.displayWidth, this.displayHeight);
            screen.setWorldAndResolution(this, resolution.getWidth(), resolution.getHeight());
            this.skipWorldRender = false;
            if (!legacyFlow) {
                return;
            }
        }

        this.mcSoundHandler.resumeSounds();
        this.setIngameFocus();
    }

    private void updateDisplayMode() throws LWJGLException {
        String text = getLegacyClientFlowMarker();
        HashSet hashset = Sets.newHashSet();
        String textValue = text;
        Collections.addAll(hashset, Display.getAvailableDisplayModes());
        DisplayMode displaymode = Display.getDesktopDisplayMode();
        if (!hashset.contains(displaymode) && Util.getOSType() == UtilEnumOS.OSX) {
            for (java.util.Iterator<?> __recoveryIterator171_143112 =
                            (net.minecraft.client.MinecraftClient.macDisplayModes).iterator();
                    (__recoveryIterator171_143112).hasNext(); ) {
                DisplayMode displaymode1 = (DisplayMode) (__recoveryIterator171_143112).next();
                byte b0 = 1;

                for (java.util.Iterator<?> __recoveryIterator171_143191 = (hashset).iterator();
                        (__recoveryIterator171_143191).hasNext(); ) {
                    DisplayMode displaymode2 = (DisplayMode) (__recoveryIterator171_143191).next();
                    if (displaymode2.getBitsPerPixel() == 32
                            && displaymode2.getWidth() == displaymode1.getWidth()
                            && displaymode2.getHeight() == displaymode1.getHeight()) {
                        b0 = 0;
                        break;
                    }
                }

                if (b0 == 0) {
                    for (java.util.Iterator<?> __recoveryIterator171_143574 = (hashset).iterator();
                            (__recoveryIterator171_143574).hasNext(); ) {
                        DisplayMode displaymode3 =
                                (DisplayMode) (__recoveryIterator171_143574).next();
                        if (displaymode3.getBitsPerPixel() == 32
                                && displaymode3.getWidth() == displaymode1.getWidth() / 2
                                && displaymode3.getHeight() == displaymode1.getHeight() / 2) {
                            displaymode = displaymode3;
                            break;
                        }
                    }
                }

                if (textValue != null) {
                    break;
                }
            }
        }

        Display.setDisplayMode(displaymode);
        this.displayWidth = displaymode.getWidth();
        this.displayHeight = displaymode.getHeight();
    }

    public static boolean isFancyGraphicsEnabled() {
        return net.minecraft.client.MinecraftClient.theMinecraft != null
                && net.minecraft.client.MinecraftClient.theMinecraft.gameSettings.fancyGraphics;
    }

    public void loadWorld(ClientWorld clientWorld) {
        this.loadWorld(clientWorld, packedTextFragments[20]);
    }

    public void rightClickMouse() {
        boolean legacyClickFlow = getLegacyClientFlowMarker() != null;
        InputEvent event = new InputEvent(this.rightClickDelayTimer);
        RemixClient.eventBus.post(event);
        if (event.isCancelled()) {
            return;
        }

        this.rightClickDelayTimer = 4;
        ItemStack heldStack = this.player.inventory.getCurrentItem();
        RayTraceResult hit = this.objectMouseOver;
        boolean useHeldItem = true;
        if (hit == null) {
            logger.warn("Null returned as 'hitResult', this shouldn't happen!");
        } else {
            boolean checkBlock = hit.typeOfHit == RayHitType.BLOCK;
            if (hit.typeOfHit == RayHitType.ENTITY) {
                boolean preciseHitUsed =
                        this.playerController.interactWithEntityAt(this.player, hit.entityHit, hit);
                if (preciseHitUsed && !legacyClickFlow) {
                    return;
                }
                boolean entityUsed =
                        this.playerController.interactWithEntitySendPacket(
                                this.player, hit.entityHit);
                useHeldItem = !preciseHitUsed && !entityUsed;
                checkBlock = entityUsed && legacyClickFlow;
            }

            if (checkBlock) {
                BlockPos position = hit.getBlockPos();
                if (position != null
                        && this.world.getBlockState(position).getBlock().getMaterial()
                                != Material.AIR) {
                    int previousSize = heldStack == null ? 0 : heldStack.stackSize;
                    if (this.playerController.onPlayerRightClick(
                            this.player,
                            this.world,
                            heldStack,
                            position,
                            hit.sideHit,
                            hit.hitVec)) {
                        useHeldItem = false;
                        this.swingForBlockUse();
                    }
                    if (heldStack == null) {
                        return;
                    }
                    this.finishBlockItemUse(heldStack, previousSize, legacyClickFlow);
                }
            }
        }
        if (useHeldItem) {
            this.useCurrentItemInAir();
        }
    }

    private void swingForBlockUse() {
        RemixClient.INSTANCE.getClientServices();
        NoSwingModule noSwing = ClientServices.moduleManager.noSwing;
        if (!noSwing.isEnabled() || !noSwing.shouldSuppressSwing()) {
            this.player.swingItem();
        }
    }

    private void finishBlockItemUse(ItemStack stack, int previousSize, boolean legacyClickFlow) {
        if (stack.stackSize == 0) {
            this.player.inventory.mainInventory[this.player.inventory.currentItem] = null;
            if (!legacyClickFlow) {
                return;
            }
        }
        if (stack.stackSize != previousSize || this.playerController.isInCreativeMode()) {
            this.entityRenderer.itemRenderer.resetEquippedProgressAfterBlockUse();
        }
    }

    private void useCurrentItemInAir() {
        ItemStack stack = this.player.inventory.getCurrentItem();
        if (stack != null && this.playerController.sendUseItem(this.player, this.world, stack)) {
            this.entityRenderer.itemRenderer.resetEquippedProgress();
        }
    }

    public static int getDebugFPS() {
        return net.minecraft.client.MinecraftClient.debugFPS;
    }

    public static boolean isAmbientOcclusionEnabled() {
        return net.minecraft.client.MinecraftClient.theMinecraft != null
                && net.minecraft.client.MinecraftClient.theMinecraft.gameSettings.ambientOcclusion
                        != 0;
    }

    public static void stopIntegratedServer() {
        if (net.minecraft.client.MinecraftClient.theMinecraft != null) {
            IntegratedServer integratedServer =
                    net.minecraft.client.MinecraftClient.theMinecraft.getIntegratedServer();
            if (integratedServer != null) {
                integratedServer.stopServer();
            }
        }
    }

    public void dispatchGlobalKeypresses() {
        String text = getLegacyClientFlowMarker();
        int i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
        if (i != 0 && !Keyboard.isRepeatEvent()) {
            if (this.currentScreen instanceof GuiControls) {
                long j = ((GuiControls) this.currentScreen).time;
                long k = getSystemTime();

                if (j > k - 20L) {
                    return;
                }
            }

            if (Keyboard.getEventKeyState()) {
                if (i == this.gameSettings.keyBindStreamStartStop.getKeyCode()) {
                    if (this.getTwitchStream().isBroadcasting()) {
                        this.getTwitchStream().ns();
                        if (text == null) {
                            return;
                        }
                    }

                    if (this.getTwitchStream().isReadyToBroadcast()) {
                        this.displayGuiScreen(
                                new GuiYesNo(
                                        new MinecraftDispatchKeypressesGuiYesNoCallback(this),
                                        I18n.format("stream.confirm_start", new Object[0]),
                                        packedTextFragments[23],
                                        0));
                        if (text == null) {
                            return;
                        }
                    }

                    if (this.getTwitchStream().func_152928_D()
                            && this.getTwitchStream().func_152936_l()) {
                        if (this.world == null) {
                            return;
                        }

                        this.ingameGUI
                                .getChatGUI()
                                .printChatMessage(
                                        new ChatComponentText("Not ready to start streaming yet!"));
                        if (text == null) {
                            return;
                        }
                    }

                    GuiStreamUnavailable.showUnavailableScreen(this.currentScreen);
                    if (text == null) {
                        return;
                    }
                }

                if (i == this.gameSettings.keyBindStreamPauseUnpause.getKeyCode()) {
                    if (!this.getTwitchStream().isBroadcasting()) {
                        return;
                    }

                    if (this.getTwitchStream().isPaused()) {
                        this.getTwitchStream().unpause();
                        if (text == null) {
                            return;
                        }
                    }

                    this.getTwitchStream().pause();
                    if (text == null) {
                        return;
                    }
                }

                if (i == this.gameSettings.keyBindStreamCommercials.getKeyCode()) {
                    if (!this.getTwitchStream().isBroadcasting()) {
                        return;
                    }

                    this.getTwitchStream().iJ();
                    if (text == null) {
                        return;
                    }
                }

                if (i == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
                    this.stream.muteMicrophone(true);
                    if (text == null) {
                        return;
                    }
                }

                if (i == this.gameSettings.keyBindFullscreen.getKeyCode()) {
                    this.toggleFullscreen();
                    if (text == null) {
                        return;
                    }
                }

                if (i != this.gameSettings.keyBindScreenshot.getKeyCode()) {
                    return;
                }

                this.ingameGUI
                        .getChatGUI()
                        .printChatMessage(
                                ScreenShotHelper.saveScreenshot(
                                        this.gameDirectory,
                                        this.displayWidth,
                                        this.displayHeight,
                                        this.framebufferMc));
                if (text == null) {
                    return;
                }
            }

            if (i == this.gameSettings.keyBindStreamToggleMic.getKeyCode()) {
                this.stream.muteMicrophone(false);
            }
        }
    }

    public void setDimensionAndSpawnPlayer(int dimension) {
        this.world.setInitialSpawnLocation();
        this.world.removeAllEntities();
        int j = 0;
        String text = null;
        if (this.player != null) {
            j = this.player.getEntityId();
            this.world.removeEntity(this.player);
            text = this.player.getClientBrand();
        }

        this.renderViewEntity = null;
        LocalPlayer playerValue = this.player;
        this.player =
                this.playerController.createLocalPlayer(
                        this.world,
                        this.player == null
                                ? new StatFileWriter()
                                : this.player.getStatFileWriter());
        this.player
                .getDataWatcher()
                .updateWatchedObjectsFromList(playerValue.getDataWatcher().getAllWatched());
        this.player.dimension = dimension;
        this.renderViewEntity = this.player;
        this.player.preparePlayerToSpawn();
        this.player.setClientBrand(text);
        this.world.spawnEntityInWorld(this.player);
        this.playerController.flipPlayer(this.player);
        this.player.movementInput = new MovementInputFromOptions(this.gameSettings);
        this.player.setEntityId(j);
        this.playerController.setPlayerCapabilities(this.player);
        this.player.setReducedDebug(playerValue.hasReducedDebug());
        if (this.currentScreen instanceof GuiGameOver) {
            this.displayGuiScreen(null);
        }
    }

    public MinecraftClient(GameConfiguration configuration) {
        this.usageSnooper =
                new PlayerUsageSnooper("client", this, MinecraftServer.getCurrentTimeMillis());
        this.systemTime = getSystemTime();
        this.mcProfiler = new Profiler();

        this.debugCrashKeyPressTime = -1L;
        this.metadataSerializer_ = new IMetadataSerializer();
        this.defaultResourcePacks = Lists.newArrayList();
        this.scheduledTasks = Queues.newArrayDeque();

        this.amU = 0L;
        getLegacyClientFlowMarker();
        this.mcThread = Thread.currentThread();
        this.running = true;
        this.debug = packedTextFragments[0];
        this.amE = false;
        this.amY = false;
        this.amk = false;
        this.renderChunksMany = true;
        this.debugUpdateTime = getSystemTime();

        this.prevFrameTime = -1L;
        this.debugProfilerName = "root";
        net.minecraft.client.MinecraftClient.theMinecraft = this;
        this.gameDirectory = configuration.folderInfo.mcDataDir;
        this.fileAssets = configuration.folderInfo.assetsDir;
        this.fileResourcepacks = configuration.folderInfo.resourcePacksDir;
        this.launchedVersion = configuration.gameInfo.version;
        this.twitchDetails = configuration.userInfo.userProperties;
        this.mcDefaultResourcePack =
                new DefaultResourcePack(
                        new ResourceIndex(
                                        configuration.folderInfo.assetsDir,
                                        configuration.folderInfo.assetIndex)
                                .getResourceMap());
        this.proxy =
                configuration.userInfo.proxy == null
                        ? Proxy.NO_PROXY
                        : configuration.userInfo.proxy;
        this.sessionService =
                new YggdrasilAuthenticationService(
                                configuration.userInfo.proxy, UUID.randomUUID().toString())
                        .createMinecraftSessionService();
        this.session = configuration.userInfo.session;
        net.minecraft.client.MinecraftClient.logger.info(
                "Setting user: " + this.session.getUsername());
        net.minecraft.client.MinecraftClient.logger.info("Session initialized.");
        this.isDemo = configuration.gameInfo.isDemo;
        this.displayWidth =
                configuration.displayInfo.width > 0 ? configuration.displayInfo.width : 1;
        this.displayHeight =
                configuration.displayInfo.height > 0 ? configuration.displayInfo.height : 1;
        this.tempDisplayWidth = configuration.displayInfo.width;
        this.tempDisplayHeight = configuration.displayInfo.height;
        this.fullscreen = configuration.displayInfo.fullscreen;
        this.jvm64bit = isJvm64bit();
        this.theIntegratedServer = new IntegratedServer(this);
        if (configuration.serverInfo.serverName != null) {
            this.serverName = configuration.serverInfo.serverName;
            this.serverPort = configuration.serverInfo.serverPort;
        }

        ImageIO.setUseCache(false);
        Bootstrap.register();
        OfflineUuidResolver.setSpoofedUsername(this.session.getUsername());
        OfflineUuidResolver.setSpoofedIp("127.0.0.1");
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyClientFlowMarker(packedTextFragments[2]);
        }
    }

    public ItemRenderer getItemRenderer() {
        return this.itemRenderer;
    }

    public boolean isFullScreen() {
        return this.fullscreen;
    }

    public Framebuffer getFramebuffer() {
        return this.framebufferMc;
    }

    public void crashed(CrashReport amtValue) {
        this.hasCrashed = true;
        this.crashReporter = amtValue;
    }

    public void setIngameNotInFocus() {
        if (this.inGameHasFocus) {
            KeyBinding.unPressAllKeys();
            this.inGameHasFocus = false;
            this.mouseHelper.ungrabMouseCursor();
        }
    }

    private void checkGLError(String text) {
        if (this.enableGLErrorChecking) {
            int i = GL11.glGetError();
            if (i != 0) {
                String textValue = GLU.gluErrorString(i);
                net.minecraft.client.MinecraftClient.logger.error("########## GL ERROR ##########");
                net.minecraft.client.MinecraftClient.logger.error("@ " + text);
                net.minecraft.client.MinecraftClient.logger.error(i + ": " + textValue);
            }
        }
    }

    private void registerMetadataSerializers() {
        this.metadataSerializer_.registerMetadataSectionType(
                new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(
                new FontMetadataSectionSerializer(), FontMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(
                new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(
                new PackMetadataSectionSerializer(), PackMetadataSection.class);
        this.metadataSerializer_.registerMetadataSectionType(
                new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
    }

    public static String getLegacyClientFlowMarker() {
        return net.minecraft.client.MinecraftClient.legacyClientFlowMarker;
    }

    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }

    public ListenableFuture addScheduledTask(Callable callable) {
        Validate.notNull(callable);
        if (!this.isCallingFromMinecraftThread()) {
            ListenableFutureTask listenablefuturetask = ListenableFutureTask.create(callable);
            Queue queue = this.scheduledTasks;
            synchronized (this.scheduledTasks) {
                this.scheduledTasks.add(listenablefuturetask);
                return listenablefuturetask;
            }
        } else {
            try {
                return Futures.immediateFuture(callable.call());
            } catch (Exception exception) {
                return Futures.immediateFailedFuture(exception);
            }
        }
    }

    public boolean isCallingFromMinecraftThread() {
        return Thread.currentThread() == this.mcThread;
    }

    private void createDisplay() throws LWJGLException {
        Display.setResizable(true);
        Display.setTitle("Minecraft 1.8");

        try {
            Display.create(new PixelFormat().withDepthBits(24));
        } catch (LWJGLException lwjglexception) {
            net.minecraft.client.MinecraftClient.logger.error(
                    "Couldn't set pixel format", lwjglexception);

            try {

                Thread.sleep(1000L);
            } catch (InterruptedException interruptedexception) {
            }

            if (this.fullscreen) {
                this.updateDisplayMode();
            }

            Display.create();
        }
    }

    public void addServerStatsToSnooper(PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addClientStat("fps", net.minecraft.client.MinecraftClient.debugFPS);
        String text = getLegacyClientFlowMarker();
        playerUsageSnooper.addClientStat("vsync_enabled", this.gameSettings.enableVsync);
        playerUsageSnooper.addClientStat(
                "display_frequency", Display.getDisplayMode().getFrequency());
        String textValue = text;
        playerUsageSnooper.addClientStat(
                "display_type", this.fullscreen ? "fullscreen" : "windowed");
        String textValue2 = "run_time";
        long j =
                MinecraftServer.getCurrentTimeMillis()
                        - playerUsageSnooper.getMinecraftStartTimeMillis();

        j /= 60L;

        playerUsageSnooper.addClientStat(textValue2, j * 1000L);
        String textValue3 = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
        playerUsageSnooper.addClientStat("endianness", textValue3);
        playerUsageSnooper.addClientStat(
                "resource_packs", this.mcResourcePackRepository.getRepositoryEntries().size());
        int i = 0;

        for (java.util.Iterator<?> __recoveryIterator171_161798 =
                        (this.mcResourcePackRepository.getRepositoryEntries()).iterator();
                (__recoveryIterator171_161798).hasNext(); ) {
            ResourcePackRepositoryEntry resourcePackRepositoryEntry =
                    (ResourcePackRepositoryEntry) (__recoveryIterator171_161798).next();
            playerUsageSnooper.addClientStat(
                    "resource_pack[" + i++ + packedTextFragments[22],
                    resourcePackRepositoryEntry.getResourcePackName());
            if (textValue != null) {
                break;
            }
        }

        if (this.theIntegratedServer != null
                && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
            playerUsageSnooper.addClientStat(
                    "snooper_partner",
                    this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
        }
    }

    private void pickBlockFromCrosshair() {
        InputEvent pickBlock = new InputEvent(InputType.MIDDLE_CLICK);
        RemixClient.eventBus.post(pickBlock);
        if (pickBlock.isCancelled() || this.objectMouseOver == null) {
            return;
        }

        boolean creative = this.player.capabilities.isCreativeMode;
        int metadata = 0;
        boolean hasSubtypes = false;
        TileEntity tile = null;
        Item item;
        if (this.objectMouseOver.typeOfHit == RayHitType.BLOCK) {
            BlockPos pos = this.objectMouseOver.getBlockPos();
            Block block = this.world.getBlockState(pos).getBlock();
            if (block.getMaterial() == Material.AIR) {
                return;
            }
            item = block.getItem(this.world, pos);
            if (item == null) {
                return;
            }
            if (creative && GuiScreen.isCtrlKeyDown()) {
                tile = this.world.getTileEntity(pos);
            }
            Block metadataBlock =
                    item instanceof ItemBlock && !block.isFlowerPot()
                            ? Block.getBlockFromItem(item)
                            : block;
            metadata = metadataBlock.getDamageValue(this.world, pos);
            hasSubtypes = item.getHasSubtypes();
        } else {
            Entity hit = this.objectMouseOver.entityHit;
            if (this.objectMouseOver.typeOfHit != RayHitType.ENTITY || hit == null || !creative) {
                return;
            }
            if (hit instanceof EntityPainting) {
                item = Items.painting;
            } else if (hit instanceof EntityLeashKnot) {
                item = Items.lead;
            } else if (hit instanceof EntityItemFrame) {
                ItemStack displayedItem = ((EntityItemFrame) hit).getDisplayedItem();
                if (displayedItem == null) {
                    item = Items.item_frame;
                } else {
                    item = displayedItem.getItem();
                    metadata = displayedItem.getItemDamage();
                    hasSubtypes = true;
                }
            } else if (hit instanceof EntityMinecart) {
                item = this.pickMinecartItem((EntityMinecart) hit);
            } else if (hit instanceof EntityBoat) {
                item = Items.boat;
            } else if (hit instanceof EntityArmorStand) {
                item = Items.armor_stand;
            } else {
                item = Items.spawn_egg;
                metadata = EntityList.getEntityID(hit);
                hasSubtypes = true;
                if (!EntityList.entityEggs.containsKey(metadata)) {
                    return;
                }
            }
        }

        InventoryPlayer inventory = this.player.inventory;
        if (tile == null) {
            inventory.setCurrentItem(item, metadata, hasSubtypes, creative);
        } else {
            CompoundTag blockEntityData = new CompoundTag();
            tile.writeToNBT(blockEntityData);
            ItemStack pickedStack = new ItemStack(item, 1, metadata);
            pickedStack.setTagInfo("BlockEntityTag", blockEntityData);
            CompoundTag display = new CompoundTag();
            NBTTagList lore = new NBTTagList();
            lore.appendTag(new NBTTagString("(+NBT)"));
            display.setTag("Lore", lore);
            pickedStack.setTagInfo("display", display);
            inventory.setInventorySlotContents(inventory.currentItem, pickedStack);
        }

        if (creative) {
            int slot =
                    this.player.inventoryContainer.inventorySlots.size()
                            - 9
                            + inventory.currentItem;
            this.playerController.sendSlotPacket(
                    inventory.getStackInSlot(inventory.currentItem), slot);
        }
    }

    private Item pickMinecartItem(EntityMinecart minecart) {
        switch (minecart.getMinecartType()) {
            case FURNACE:
                return Items.furnace_minecart;
            case CHEST:
                return Items.chest_minecart;
            case TNT:
                return Items.tnt_minecart;
            case HOPPER:
                return Items.hopper_minecart;
            case COMMAND_BLOCK:
                return Items.command_block_minecart;
            default:
                return Items.minecart;
        }
    }

    private void setInitialDisplayMode() throws LWJGLException {
        String text = getLegacyClientFlowMarker();
        if (this.fullscreen) {
            Display.setFullscreen(true);
            DisplayMode displaymode = Display.getDisplayMode();
            this.displayWidth = Math.max(1, displaymode.getWidth());
            this.displayHeight = Math.max(1, displaymode.getHeight());
            if (text == null) {
                return;
            }
        }

        Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
    }

    public BlockRendererDispatcher getBlockRendererDispatcher() {
        return this.blockRenderDispatcher;
    }

    public Session getSession() {
        return this.session;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    private void initStream() {
        // Keep the retired Twitch native integration disabled without bytecode rewriting.
        this.stream = new NullStream(
                new UnsupportedOperationException("Legacy Twitch streaming is disabled."));
    }

    // region String decoding support

    // endregion

    public static long getSystemTime() {
        long time = Sys.getTime();

        return time * 1000L / Sys.getTimerResolution();
    }

    // Helper used only by this class.
    public static class MinecraftDispatchKeypressesGuiYesNoCallback
            implements net.minecraft.client.gui.GuiYesNoCallback {
        net.minecraft.client.MinecraftClient ec;

        public MinecraftDispatchKeypressesGuiYesNoCallback(
                net.minecraft.client.MinecraftClient minecraftClient) {
            this.ec = minecraftClient;
        }

        public void confirmClicked(boolean flag, int i) {
            if (flag) {
                this.ec.getTwitchStream().nG();
            }

            this.ec.displayGuiScreen(null);
        }
    }
}
