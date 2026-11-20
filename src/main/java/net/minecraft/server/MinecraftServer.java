package net.minecraft.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandResultStatsType;
import net.minecraft.command.CommandSender;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.ServerStatusResponseMinecraftProtocolVersionIdentifier;
import net.minecraft.network.ServerStatusResponsePlayerCountData;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.imageio.ImageIO;

public abstract class MinecraftServer
        implements Runnable, CommandSender, IPlayerUsage, IThreadListener {
    private File anvilFile;

    public Profiler theProfiler;
    private int buildLimit;
    private String jT;
    private int maxPlayerIdleMinutes;
    private long timeOfLastWarning;
    private ServerStatusResponse statusResponse;
    protected Proxy serverProxy;
    private String resourcePackHash;
    private boolean isDemo;
    private PlayerUsageSnooper usageSnooper;

    private String userMessage;
    private String resourcePackUrl;
    private boolean worldIsBeingDeleted;
    public long[][] timeOfLastDimensionTick;
    private Thread serverThread;
    private boolean serverInRunLoop;
    private static MinecraftServer mcServer;
    private int tickCounter;
    private boolean onlineMode;
    private PlayerProfileCache profileCache;
    private long nanoTimeSinceStatusRefresh;
    private boolean allowFlight;
    private long currentTime;
    private YggdrasilAuthenticationService authService;
    private boolean lL;
    private boolean canSpawnAnimals;
    public long[] tickTimeArray;
    private static String[] packedTextFragments;
    protected Queue futureTaskQueue;
    public String currentTask;
    private NetworkSystem networkSystem;
    private MinecraftSessionService sessionService;
    private List playersOnline;
    private int serverPort;
    private String serverOwner;
    public WorldServer[] worldServers;
    private Random random;
    private boolean serverRunning;
    private KeyPair serverKeyPair;
    private boolean pvpEnabled;
    public int percentDone;
    private ISaveFormat anvilConverterForAnvilFile;
    public static File USER_CACHE_FILE;
    private static Logger logger;
    private ServerConfigurationManager serverConfigManager;
    private GameProfileRepository profileRepo;
    private boolean canSpawnNPCs;
    private String folderName;
    private boolean isGamemodeForced;
    private boolean bonusChestEnabled;
    private String EW;
    private ICommandManager commandManager;
    private static boolean aJG;
    private boolean startProfiling;

    public boolean isServerInOnlineMode() {
        return this.onlineMode;
    }

    public static void method_ffaa23ce3617e7a7bd47(boolean aJGValue) {
        aJG = aJGValue;
    }

    protected void setInstance() {
        net.minecraft.server.MinecraftServer.mcServer = this;
    }

    public ICommandManager getCommandManager() {
        return this.commandManager;
    }

    public MinecraftServer(Proxy proxy, File file1) {
        this.usageSnooper = new PlayerUsageSnooper("server", this, getCurrentTimeMillis());
        this.playersOnline = Lists.newArrayList();
        this.theProfiler = new Profiler();
        this.statusResponse = new ServerStatusResponse();
        this.random = new Random();
        this.serverPort = -1;
        this.serverRunning = true;
        this.maxPlayerIdleMinutes = 0;
        this.tickTimeArray = new long[100];
        this.resourcePackUrl = packedTextFragments[0];
        this.resourcePackHash = packedTextFragments[1];

        this.nanoTimeSinceStatusRefresh = 0L;
        boolean flag1 = kq();
        this.futureTaskQueue = Queues.newArrayDeque();
        this.currentTime = getCurrentTimeMillis();
        this.serverProxy = proxy;
        net.minecraft.server.MinecraftServer.mcServer = this;
        this.anvilFile = null;
        this.networkSystem = null;
        this.profileCache = new PlayerProfileCache(this, file1);
        this.commandManager = null;
        this.anvilConverterForAnvilFile = null;
        this.authService = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
        this.sessionService = this.authService.createMinecraftSessionService();
        this.profileRepo = this.authService.createProfileRepository();
        boolean flag = flag1;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            method_ffaa23ce3617e7a7bd47(!flag);
        }
    }

    protected void outputPercentRemaining(String text, int i) {
        this.currentTask = text;
        this.percentDone = i;
        net.minecraft.server.MinecraftServer.logger.info(text + ": " + i + packedTextFragments[6]);
    }

    public int getMaxWorldSize() {
        return 29999984;
    }

    public static MinecraftServer getServer() {
        return net.minecraft.server.MinecraftServer.mcServer;
    }

    public boolean isDemo() {
        return this.isDemo;
    }

    public ServerStatusResponse getServerStatusResponse() {
        return this.statusResponse;
    }

    public File getDataDirectory() {
        return new File(packedTextFragments[7]);
    }

    public void setCanSpawnAnimals(boolean canSpawnAnimalsValue) {
        this.canSpawnAnimals = canSpawnAnimalsValue;
    }

    public void v(String text) {
        this.EW = text;
    }

    public void setAllowPvp(boolean pvpEnabledValue) {
        this.pvpEnabled = pvpEnabledValue;
    }

    public abstract String shareToLAN(WorldSettingsGameType worldSettingsGameType, boolean flag);

    private void addFaviconToStatusResponse(ServerStatusResponse serverStatusResponse) {
        File file1 = this.getFile("server-icon.png");
        if (file1.isFile()) {
            ByteBuf bytebuf = Unpooled.buffer();

            try {
                BufferedImage bufferedimage = ImageIO.read(file1);
                Validate.validState(
                        bufferedimage.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                Validate.validState(
                        bufferedimage.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                ImageIO.write(bufferedimage, "PNG", new ByteBufOutputStream(bytebuf));
                ByteBuf bytebuf1 = Base64.encode(bytebuf);
                serverStatusResponse.setFavicon(
                        "data:image/png;base64," + bytebuf1.toString(Charsets.UTF_8));
            } catch (Exception exception) {
                net.minecraft.server.MinecraftServer.logger.error("Couldn't load server icon", exception);
            } finally {
                bytebuf.release();
            }
        }
    }

    public int getBuildLimit() {
        return this.buildLimit;
    }

    public void na() {
        Ra();

        this.theProfiler.startSection("jobs");

        Queue queue = this.futureTaskQueue;
        synchronized (this.futureTaskQueue) {
            while (!this.futureTaskQueue.isEmpty()) {
                try {
                    ((FutureTask) this.futureTaskQueue.poll()).run();
                } catch (Throwable throwable2) {
                    net.minecraft.server.MinecraftServer.logger.fatal(throwable2);
                }
            }
        }

        this.theProfiler.endStartSection("levels");
        int j = 0;

        while (j < this.worldServers.length) {
            long i = System.nanoTime();
            if (j == 0 || this.getAllowNether()) {
                WorldServer worldServer = this.worldServers[j];
                this.theProfiler.startSection(worldServer.getWorldInfo().getWorldName());
                if (this.tickCounter % 20 == 0) {
                    this.theProfiler.startSection("timeSync");
                    this.serverConfigManager.sendPacketToAllPlayersInDimension(
                            new S03PacketTimeUpdate(
                                    worldServer.getTotalWorldTime(),
                                    worldServer.getWorldTime(),
                                    worldServer
                                            .getGameRules()
                                            .getGameRuleBooleanValue("doDaylightCycle")),
                            worldServer.provider.getDimensionId());
                    this.theProfiler.endSection();
                }

                this.theProfiler.startSection("tick");

                try {
                    worldServer.tick();
                } catch (Throwable throwable1) {
                    CrashReport crashReport =
                            CrashReport.makeCrashReport(throwable1, "Exception ticking world");
                    worldServer.addWorldInfoToCrashReport(crashReport);
                    throw new ReportedException(crashReport);
                }

                try {
                    worldServer.updateEntities();
                } catch (Throwable throwable) {
                    CrashReport crashReportValue =
                            CrashReport.makeCrashReport(
                                    throwable, "Exception ticking world entities");
                    worldServer.addWorldInfoToCrashReport(crashReportValue);
                    throw new ReportedException(crashReportValue);
                }

                this.theProfiler.endSection();
                this.theProfiler.startSection("tracker");
                worldServer.getEntityTracker().updateTrackedEntities();
                this.theProfiler.endSection();
                this.theProfiler.endSection();
            }

            this.timeOfLastDimensionTick[j][this.tickCounter % 100] = System.nanoTime() - i;
            j++;
        }

        this.theProfiler.endStartSection("connection");
        this.getNetworkSystem().tickConnections();
        this.theProfiler.endStartSection("players");
        this.serverConfigManager.onTick();
        this.theProfiler.endStartSection("tickables");
        int k = 0;

        while (k < this.playersOnline.size()) {
            ((IUpdatePlayerListBox) this.playersOnline.get(k)).update();
            k++;
        }

        this.theProfiler.endSection();
    }

    protected void nG() {}

    public MinecraftServer(File file1, Proxy proxy, File file2) {
        this.usageSnooper = new PlayerUsageSnooper("server", this, getCurrentTimeMillis());
        Ra();

        this.playersOnline = Lists.newArrayList();
        this.theProfiler = new Profiler();
        this.statusResponse = new ServerStatusResponse();
        this.random = new Random();

        this.serverPort = -1;
        this.serverRunning = true;
        this.maxPlayerIdleMinutes = 0;
        this.tickTimeArray = new long[100];
        this.resourcePackUrl = packedTextFragments[2];
        this.resourcePackHash = packedTextFragments[3];

        this.nanoTimeSinceStatusRefresh = 0L;
        this.futureTaskQueue = Queues.newArrayDeque();
        this.currentTime = getCurrentTimeMillis();
        this.serverProxy = proxy;
        net.minecraft.server.MinecraftServer.mcServer = this;
        this.anvilFile = file1;
        this.networkSystem = new NetworkSystem(this);
        this.profileCache = new PlayerProfileCache(this, file2);
        this.commandManager = this.createNewCommandManager();
        this.anvilConverterForAnvilFile = new AnvilSaveConverter(file1);
        this.authService = new YggdrasilAuthenticationService(proxy, UUID.randomUUID().toString());
        this.sessionService = this.authService.createMinecraftSessionService();
        this.profileRepo = this.authService.createProfileRepository();
    }

    public abstract boolean isCommandBlockEnabled();

    protected void saveAllWorlds(boolean flag) {
        kq();

        if (!this.worldIsBeingDeleted) {
            for (WorldServer worldServer : this.worldServers) {
                if (worldServer != null) {
                    if (!flag) {
                        net.minecraft.server.MinecraftServer.logger.info(
                                "Saving chunks for level '"
                                        + worldServer.getWorldInfo().getWorldName()
                                        + "'/"
                                        + worldServer.provider.getDimensionName());
                    }

                    try {
                        worldServer.saveAllChunks(true, (IProgressUpdate) null);
                    } catch (MinecraftException minecraftException) {
                        net.minecraft.server.MinecraftServer.logger.warn(minecraftException.getMessage());
                    }
                }
            }
        }
    }

    public void deleteWorldAndStopServer() {
        this.worldIsBeingDeleted = true;
        Ra();

        this.getActiveAnvilConverter().flushCache();
        int i = 0;

        while (i < this.worldServers.length) {
            WorldServer worldServer = this.worldServers[i];
            if (worldServer != null) {
                worldServer.flush();
            }

            i++;
        }

        this.getActiveAnvilConverter()
                .deleteWorldDirectory(
                        this.worldServers[0].getSaveHandler().getWorldDirectoryName());
        this.initiateShutdown();
    }

    public boolean isServerInRunLoop() {
        return this.serverInRunLoop;
    }

    public KeyPair getKeyPair() {
        return this.serverKeyPair;
    }

    public void setOnlineMode(boolean flag) {
        this.onlineMode = flag;
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    protected synchronized void setUserMessage(String text) {
        this.userMessage = text;
    }

    public String getName() {
        return "Server";
    }

    public Proxy getServerProxy() {
        return this.serverProxy;
    }

    public abstract WorldSettingsGameType getGameType();

    public boolean getGuiEnabled() {
        return false;
    }

    public void setPlayerIdleTimeout(int i) {
        this.maxPlayerIdleMinutes = i;
    }

    public void setDifficultyForAllWorlds(EnumDifficulty enumDifficulty) {
        kq();

        int i = 0;

        while (i < this.worldServers.length) {
            WorldServer worldServer = this.worldServers[i];
            if (worldServer != null) {
                if (worldServer.getWorldInfo().isHardcoreModeEnabled()) {
                    worldServer.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
                    worldServer.setAllowedSpawnTypes(true, true);

                    
                } else {

                    if (this.isSinglePlayer()) {
                        worldServer.getWorldInfo().setDifficulty(enumDifficulty);
                        worldServer.setAllowedSpawnTypes(
                                worldServer.getDifficulty() != EnumDifficulty.PEACEFUL, true);


                    } else {

                        worldServer.getWorldInfo().setDifficulty(enumDifficulty);
                        worldServer.setAllowedSpawnTypes(this.allowSpawnMonsters(), this.canSpawnAnimals);
                    }
                }
            }

            i++;
        }
    }

    public String QL() {
        return this.jT;
    }

    // region String decoding support

    // endregion

    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public CrashReport addServerInfoToCrashReport(CrashReport crashReport) {
        crashReport
                .getCategory()
                .addCrashSectionCallable("Profiler Position", new ServerProfilerCrashDetail(this));
        if (this.serverConfigManager != null) {
            crashReport
                    .getCategory()
                    .addCrashSectionCallable(
                            "Player Count", new ServerPlayerCountCrashDetail(this));
        }

        return crashReport;
    }

    protected void setResourcePackFromWorld(String text, ISaveHandler iSaveHandler) {
        File file1 = new File(iSaveHandler.getWorldDirectory(), "resources.zip");
        if (file1.isFile()) {
            this.setResourcePack(
                    "level://" + text + packedTextFragments[4] + file1.getName(),
                    packedTextFragments[5]);
        }
    }

    public void setResourcePack(String text, String textValue) {
        this.resourcePackUrl = text;
        this.resourcePackHash = textValue;
    }

    public void setGameType(WorldSettingsGameType worldSettingsGameType) {
        Ra();

        int i = 0;

        while (i < this.worldServers.length) {
            getServer().worldServers[i].getWorldInfo().setGameType(worldSettingsGameType);
            i++;
        }
    }

    public boolean isServerRunning() {
        return this.serverRunning;
    }

    public void setDemo(boolean aWfValue) {
        this.isDemo = aWfValue;
    }

    public void tick() {
        Ra();

        long nanoTimeSinceStatusRefreshValue = System.nanoTime();

        this.tickCounter++;
        if (this.startProfiling) {
            this.startProfiling = false;
            this.theProfiler.profilingEnabled = true;
            this.theProfiler.clearProfiling();
        }

        this.theProfiler.startSection("root");
        this.na();
        long l = nanoTimeSinceStatusRefreshValue - this.nanoTimeSinceStatusRefresh;

        if (l >= 5000000000L) {
            this.nanoTimeSinceStatusRefresh = nanoTimeSinceStatusRefreshValue;
            this.statusResponse.setPlayerCountData(
                    new ServerStatusResponsePlayerCountData(
                            this.getMaxPlayers(), this.getCurrentPlayerCount()));
            GameProfile[] agameprofile =
                    new GameProfile[Math.min(this.getCurrentPlayerCount(), 12)];
            int j =
                    MathHelper.nextIntInclusive(
                            this.random, 0, this.getCurrentPlayerCount() - agameprofile.length);
            int k = 0;

            while (k < agameprofile.length) {
                agameprofile[k] =
                        ((EntityPlayerMP) this.serverConfigManager.playerEntityList.get(j + k))
                                .getGameProfile();
                k++;
            }

            Collections.shuffle(Arrays.asList(agameprofile));
            this.statusResponse.getPlayerCountData().setPlayers(agameprofile);
        }

        if (this.tickCounter % 900 == 0) {
            this.theProfiler.startSection("save");
            this.serverConfigManager.saveAllPlayerData();
            this.saveAllWorlds(true);
            this.theProfiler.endSection();
        }

        this.theProfiler.startSection("tallying");
        this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - nanoTimeSinceStatusRefreshValue;
        this.theProfiler.endSection();
        this.theProfiler.startSection("snooper");
        if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100) {
            this.usageSnooper.clearRunningFlag();
        }

        if (this.tickCounter % 6000 == 0) {
            this.usageSnooper.addMemoryStatsToSnooper();
        }

        this.theProfiler.endSection();
        this.theProfiler.endSection();
    }

    public void startServerThread() {
        this.serverThread = new Thread(this, "Server thread");
        this.serverThread.start();
    }

    public void initiateShutdown() {
        this.serverRunning = false;
    }

    public PlayerProfileCache getPlayerProfileCache() {
        return this.profileCache;
    }

    public boolean canCommandSenderUseCommand(int i, String text) {
        return true;
    }

    protected void convertMapIfNeeded(String text) {
        if (this.getActiveAnvilConverter().isOldMapFormat(text)) {
            net.minecraft.server.MinecraftServer.logger.info("Converting map!");
            this.setUserMessage("menu.convertingLevel");
            this.getActiveAnvilConverter()
                    .convertMapFormat(text, new WorldConversionProgressLogger(this));
        }
    }

    public void logWarning(String text) {
        net.minecraft.server.MinecraftServer.logger.warn(text);
    }

    public ChatComponent getDisplayName() {
        return new ChatComponentText(this.getName());
    }

    public String getServerModName() {
        return "vanilla";
    }

    public void refreshStatusNextTick() {

        this.nanoTimeSinceStatusRefresh = 0L;
    }

    public ListenableFuture addScheduledTask(Runnable runnable) {
        Validate.notNull(runnable);
        return this.callFromMainThread(Executors.callable(runnable));
    }

    public Entity getEntityFromUuid(UUID uuid) {
        WorldServer[] byCValue = this.worldServers;
        int length = byCValue.length;
        Ra();

        int j = 0;

        while (j < length) {
            WorldServer worldServer = byCValue[j];
            if (worldServer != null) {
                Entity entity = worldServer.getEntityFromUuid(uuid);
                if (entity != null) {
                    return entity;
                }
            }

            j++;
        }

        return null;
    }

    public String getServerOwner() {
        return this.serverOwner;
    }

    public ListenableFuture callFromMainThread(Callable callable) {
        Validate.notNull(callable);
        if (!this.isCallingFromMinecraftThread()) {
            ListenableFutureTask listenablefuturetask = ListenableFutureTask.create(callable);
            Queue queue = this.futureTaskQueue;
            synchronized (this.futureTaskQueue) {
                this.futureTaskQueue.add(listenablefuturetask);
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

    // region String decoding support

    // endregion

    public abstract boolean f();

    public static boolean kq() {
        boolean flag = Ra();
        return !flag;
    }

    public void setCommandStat(CommandResultStatsType commandResultStatsType, int i) {}

    public Entity getCommandSenderEntity() {
        return null;
    }

    protected boolean allowSpawnMonsters() {
        return true;
    }

    public String QP() {
        return "1.8";
    }

    public boolean isPVPEnabled() {
        return this.pvpEnabled;
    }

    public abstract boolean isDedicatedServer();

    public World getEntityWorld() {
        return this.worldServers[0];
    }

    public int getMaxPlayerIdleMinutes() {
        return this.maxPlayerIdleMinutes;
    }

    protected void loadAllWorlds(
            String text, String textValue, long i, WorldType worldType, String textValue2) {
        boolean flag;
        ISaveHandler iSaveHandler;
        WorldInfo worldInfo;
        WorldSettings worldSettings;
        {
            boolean flag1 = Ra();
            this.convertMapIfNeeded(text);
            flag = flag1;
            this.setUserMessage("menu.loadingLevel");
            this.worldServers = new WorldServer[3];
            this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
            iSaveHandler = this.anvilConverterForAnvilFile.getSaveLoader(text, true);
            this.setResourcePackFromWorld(this.getFolderName(), iSaveHandler);
            worldInfo = iSaveHandler.loadWorldInfo();
            if (worldInfo == null) {
                {
                    if (this.isDemo()) {
                        worldSettings = DemoWorldServer.demoWorldSettings;

                        
                    } else {

                        worldSettings =
                                new WorldSettings(
                                        i, this.getGameType(), this.f(), this.isHardcore(), worldType);
                        worldSettings.setWorldName(textValue2);
                        if (this.bonusChestEnabled) {
                            worldSettings.enableBonusChest();
                        }
                    }
                }

                worldInfo = new WorldInfo(worldSettings, textValue);

                
            } else {

                worldInfo.setWorldName(textValue);
                worldSettings = new WorldSettings(worldInfo);
            }
        }

        int j = 0;

        while (j < this.worldServers.length) {
            byte b0 = 0;
            if (j == 1) {
                b0 = -1;
            }

            if (j == 2) {
                b0 = 1;
            }

            {
                if (j == 0) {
                    {
                        if (this.isDemo()) {
                            this.worldServers[j] =
                                    (WorldServer)
                                            new DemoWorldServer(
                                                            this,
                                                            iSaveHandler,
                                                            worldInfo,
                                                            b0,
                                                            this.theProfiler)
                                                    .init();

                            
                        } else {

                            this.worldServers[j] =
                                    (WorldServer)
                                            new WorldServer(
                                                            this,
                                                            iSaveHandler,
                                                            worldInfo,
                                                            b0,
                                                            this.theProfiler)
                                                    .init();
                        }
                    }

                    this.worldServers[j].initialize(worldSettings);

                    
                } else {

                    this.worldServers[j] =
                            (WorldServer)
                                    new WorldServerMulti(
                                                    this,
                                                    iSaveHandler,
                                                    b0,
                                                    this.worldServers[0],
                                                    this.theProfiler)
                                            .init();
                }
            }

            this.worldServers[j].addWorldAccess(new WorldManager(this, this.worldServers[j]));
            if (!this.isSinglePlayer()) {
                this.worldServers[j].getWorldInfo().setGameType(this.getGameType());
            }

            j++;
        }

        this.serverConfigManager.setPlayerManager(this.worldServers);
        this.setDifficultyForAllWorlds(this.getDifficulty());
        this.initialWorldChunkLoad();
    }

    public ServerConfigurationManager getConfigurationManager() {
        return this.serverConfigManager;
    }

    public boolean getAllowNether() {
        return true;
    }

    public int getNetworkCompressionTreshold() {
        return 256;
    }

    // region String decoding support

    // endregion

    protected void finalTick(CrashReport crashReport) {}

    public WorldServer worldServerForDimension(int i) {
        return i == -1
                ? this.worldServers[1]
                : (i == 1 ? this.worldServers[2] : this.worldServers[0]);
    }

    public GameProfileRepository getGameProfileRepository() {
        return this.profileRepo;
    }

    public int getTickCounter() {
        return this.tickCounter;
    }

    public synchronized String getUserMessage() {
        return this.userMessage;
    }

    public MinecraftSessionService getMinecraftSessionService() {
        return this.sessionService;
    }

    public String getResourcePackHash() {
        return this.resourcePackHash;
    }

    protected ServerCommandManager createNewCommandManager() {
        return new ServerCommandManager();
    }

    public ISaveFormat getActiveAnvilConverter() {
        return this.anvilConverterForAnvilFile;
    }

    @Override
    public void run() {
        boolean flag = kq();
        boolean flag1 = false /* VF: Semaphore variable */;

        label392:
        {
            try {
                flag1 = true;
                if (this.startServer()) {
                    this.currentTime = getCurrentTimeMillis();

                    long i = 0L;
                    this.statusResponse.setServerDescription(new ChatComponentText(this.jT));
                    this.statusResponse.setProtocolVersionInfo(
                            new ServerStatusResponseMinecraftProtocolVersionIdentifier("1.8", 47));
                    this.addFaviconToStatusResponse(this.statusResponse);

                    while (this.serverRunning) {
                        long k = getCurrentTimeMillis();
                        long j = k - this.currentTime;

                        if (j > 2000L) {
                            long i1 = this.currentTime - this.timeOfLastWarning;

                            if (i1 >= 15000L) {
                                Logger logger = net.minecraft.server.MinecraftServer.logger;
                                String text =
                                        "Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)";
                                Object[] aobject = new Object[] {j, null};

                                aobject[1] = j / 50L;
                                logger.warn(text, aobject);

                                j = 2000L;
                                this.timeOfLastWarning = this.currentTime;
                            }
                        }

                        if (j < 0L) {
                            net.minecraft.server.MinecraftServer.logger.warn(
                                    "Time ran backwards! Did the system time change?");

                            j = 0L;
                        }

                        {
                            i += j;
                            this.currentTime = k;
                            if (this.worldServers[0].areAllPlayersAsleep()) {
                                this.tick();

                                i = 0L;

                                
                            } else {

                                do {

                                    if (i <= 50L) {
                                        break;
                                    }

                                    i -= 50L;
                                    this.tick();
                                } while (flag);
                            }
                        }

                        long l = 1L;

                        Thread.sleep(Math.max(l, 50L - i));
                        this.serverInRunLoop = true;
                    }

                    flag1 = false;
                    break label392;
                }

                this.finalTick((CrashReport) null);
                flag1 = false;
                break label392;
            } catch (Throwable throwable3) {
                net.minecraft.server.MinecraftServer.logger.error(
                        "Encountered an unexpected exception", throwable3);
                CrashReport crashReport = null;
                if (throwable3 instanceof ReportedException) {
                    crashReport =
                            this.addServerInfoToCrashReport(
                                    ((ReportedException) throwable3).getCrashReport());
                } else {
                    crashReport =
                            this.addServerInfoToCrashReport(
                                    new CrashReport("Exception in server tick loop", throwable3));
                }

                {
                    File file1 =
                            new File(
                                    new File(this.getDataDirectory(), "crash-reports"),
                                    "crash-"
                                            + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")
                                                    .format(new Date())
                                            + "-server.txt");
                    if (crashReport.saveToFile(file1)) {
                        net.minecraft.server.MinecraftServer.logger.error(
                                "This crash report has been saved to: " + file1.getAbsolutePath());

                        
                    } else {

                        net.minecraft.server.MinecraftServer.logger.error(
                                "We were unable to save this crash report to disk.");
                    }
                }

                this.finalTick(crashReport);
                flag1 = false;
            } finally {
                if (flag1) {
                    try {
                        this.stopServer();
                        this.lL = true;
                    } catch (Throwable throwable) {
                        net.minecraft.server.MinecraftServer.logger.error(
                                "Exception stopping the server", throwable);
                    } finally {
                        this.nG();
                    }
                }
            }

            try {
                try {
                    this.stopServer();
                    this.lL = true;
                } catch (Throwable throwable1) {
                    net.minecraft.server.MinecraftServer.logger.error("Exception stopping the server", throwable1);
                }

                return;
            } finally {
                this.nG();
            }
        }

        try {
            this.stopServer();
            this.lL = true;
        } catch (Throwable throwable2) {
            net.minecraft.server.MinecraftServer.logger.error("Exception stopping the server", throwable2);
        } finally {
            this.nG();
        }
    }

    public String getFolderName() {
        return this.folderName;
    }

    public boolean isAnvilFileSet() {
        return this.anvilFile != null;
    }

    public boolean isBlockProtected(World world, BlockPos blockPos, PlayerEntity playerEntity) {
        return false;
    }

    public abstract int getOpPermissionLevel();

    public void addServerTypeToSnooper(PlayerUsageSnooper playerUsageSnooper) {
        playerUsageSnooper.addStatToSnooper("singleplayer", this.isSinglePlayer());
        playerUsageSnooper.addStatToSnooper("server_brand", this.getServerModName());
        playerUsageSnooper.addStatToSnooper(
                "gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        playerUsageSnooper.addStatToSnooper("dedicated", this.isDedicatedServer());
    }

    static {
        net.minecraft.server.MinecraftServer.packedTextFragments = new String[13];
        net.minecraft.server.MinecraftServer.packedTextFragments[0] = new String("");
        net.minecraft.server.MinecraftServer.packedTextFragments[1] = new String("");
        net.minecraft.server.MinecraftServer.packedTextFragments[2] = new String("");
        net.minecraft.server.MinecraftServer.packedTextFragments[3] = new String("");
        net.minecraft.server.MinecraftServer.packedTextFragments[4] = new String("/");
        net.minecraft.server.MinecraftServer.packedTextFragments[5] = new String("");
        net.minecraft.server.MinecraftServer.packedTextFragments[6] = new String("%");
        net.minecraft.server.MinecraftServer.packedTextFragments[7] = new String(".");
        net.minecraft.server.MinecraftServer.packedTextFragments[8] = new String("/");
        net.minecraft.server.MinecraftServer.packedTextFragments[9] = new String(" ");
        net.minecraft.server.MinecraftServer.packedTextFragments[10] = new String("/");
        net.minecraft.server.MinecraftServer.packedTextFragments[11] = new String(" ");
        method_ffaa23ce3617e7a7bd47(false);
        net.minecraft.server.MinecraftServer.logger = LogManager.getLogger("net.minecraft.server.MinecraftServer");
        net.minecraft.server.MinecraftServer.USER_CACHE_FILE = new File("usercache.json");
    }

    public boolean isCallingFromMinecraftThread() {
        return Thread.currentThread() == this.serverThread;
    }

    public abstract EnumDifficulty getDifficulty();

    public PlayerUsageSnooper getPlayerUsageSnooper() {
        return this.usageSnooper;
    }

    protected void initialWorldChunkLoad() {

        Ra();

        int i = 0;
        this.setUserMessage("menu.generatingTerrain");

        byte b4 = 0;
        net.minecraft.server.MinecraftServer.logger.info("Preparing start region for level " + b4);
        WorldServer worldServer = this.worldServers[b4];
        BlockPos blockPos = worldServer.getSpawnPoint();
        long j = getCurrentTimeMillis();
        short short1 = -192;

        while (short1 <= 192 && this.isServerRunning()) {
            short short2 = -192;

            while (true) {
                if (short2 <= 192 && this.isServerRunning()) {
                    long k = getCurrentTimeMillis();
                    long l = k - j;

                    if (l > 1000L) {
                        this.outputPercentRemaining("Preparing spawn area", i * 100 / 625);
                        j = k;
                    }

                    i++;
                    worldServer.theChunkProviderServer.loadChunk(
                            blockPos.getX() + short1 >> 4, blockPos.getZ() + short2 >> 4);
                    short2 += 16;

                    continue;
                }

                short1 += 16;

                break;
            }
        }

        this.clearCurrentTask();
    }

    public boolean isFlightAllowed() {
        return this.allowFlight;
    }

    public boolean isAnnouncingPlayerAchievements() {
        return true;
    }

    protected void clearCurrentTask() {
        this.currentTask = null;
        this.percentDone = 0;
    }

    static ServerConfigurationManager access$100(MinecraftServer minecraftServer) {
        return minecraftServer.serverConfigManager;
    }

    public void setAllowFlight(boolean flag) {
        this.allowFlight = flag;
    }

    public String HM() {
        return this.EW;
    }

    public String getResourcePackUrl() {
        return this.resourcePackUrl;
    }

    public abstract boolean isHardcore();

    public void setCanSpawnNPCs(boolean canSpawnNPCsValue) {
        this.canSpawnNPCs = canSpawnNPCsValue;
    }

    public void setKeyPair(KeyPair keypair) {
        this.serverKeyPair = keypair;
    }

    public boolean getForceGamemode() {
        return this.isGamemodeForced;
    }

    public boolean isSinglePlayer() {
        return this.serverOwner != null;
    }

    public static boolean Ra() {
        return aJG;
    }

    public void setConfigManager(ServerConfigurationManager byGValue) {
        this.serverConfigManager = byGValue;
    }

    public void addChatMessage(ChatComponent chatComponent) {
        net.minecraft.server.MinecraftServer.logger.info(chatComponent.getUnformattedText());
    }

    public void setBonusChestEnabled(boolean flag) {
        this.bonusChestEnabled = flag;
    }

    static Logger access$000() {
        return net.minecraft.server.MinecraftServer.logger;
    }

    public boolean isSnooperEnabled() {
        return true;
    }

    public void bg(String text) {
        this.jT = text;
    }

    public int getCurrentPlayerCount() {
        return this.serverConfigManager.getCurrentPlayerCount();
    }

    public boolean getCanSpawnNPCs() {
        return this.canSpawnNPCs;
    }

    // region String decoding support

    // endregion

    public GameProfile[] getGameProfiles() {
        return this.serverConfigManager.getAllProfiles();
    }

    public String[] getAllUsernames() {
        return this.serverConfigManager.getAllUsernames();
    }

    public List getTabCompletions(
            CommandSender commandSender, String text, BlockPos blockPos) {
        kq();

        ArrayList arrayList = Lists.newArrayList();

        if (text.startsWith(packedTextFragments[8])) {
            text = text.substring(1);
            int i = !text.contains(packedTextFragments[9]) ? 1 : 0;
            List tabCompletionOptions =
                    this.commandManager.getTabCompletionOptions(commandSender, text, blockPos);
            if (tabCompletionOptions != null) {
                for (java.util.Iterator<?> __recoveryIterator171_74995 =
                                (tabCompletionOptions).iterator();
                        (__recoveryIterator171_74995).hasNext(); ) {
                    String textValue = (String) (__recoveryIterator171_74995).next();
                    {
                        if (i != 0) {
                            arrayList.add(packedTextFragments[10] + textValue);

                            
                        } else {

                            arrayList.add(textValue);
                        }
                    }
                }
            }

            return arrayList;
        } else {
            String[] astring = text.split(packedTextFragments[11], -1);
            String textValue2 = astring[astring.length - 1];

            for (String textValue3 : this.serverConfigManager.getAllUsernames()) {
                if (CommandBase.doesStringStartWith(textValue2, textValue3)) {
                    arrayList.add(textValue3);
                }
            }

            return arrayList;
        }
    }

    public void setFolderName(String text) {
        this.folderName = text;
    }

    public int getSpawnProtectionSize() {
        return 16;
    }

    public boolean sendCommandFeedback() {
        return getServer()
                .worldServers[0]
                .getGameRules()
                .getGameRuleBooleanValue("sendCommandFeedback");
    }

    protected abstract boolean startServer() throws IOException;

    public void enableProfiling() {
        this.startProfiling = true;
    }

    public Vec3 getPositionVector() {
        return new Vec3(0.0, 0.0, 0.0);
    }

    public NetworkSystem getNetworkSystem() {
        return this.networkSystem;
    }

    public void stopServer() {
        Ra();

        if (!this.worldIsBeingDeleted) {
            net.minecraft.server.MinecraftServer.logger.info("Stopping server");
            if (this.getNetworkSystem() != null) {
                this.getNetworkSystem().terminateEndpoints();
            }

            if (this.serverConfigManager != null) {
                net.minecraft.server.MinecraftServer.logger.info("Saving players");
                this.serverConfigManager.saveAllPlayerData();
                this.serverConfigManager.removeAllPlayers();
            }

            if (this.worldServers != null) {
                net.minecraft.server.MinecraftServer.logger.info("Saving worlds");
                this.saveAllWorlds(false);
                int i = 0;

                while (i < this.worldServers.length) {
                    WorldServer worldServer = this.worldServers[i];
                    worldServer.flush();
                    i++;
                }
            }

            if (this.usageSnooper.isSnooperRunning()) {
                this.usageSnooper.stopSnooper();
            }
        }
    }

    public File getFile(String text) {
        return new File(this.getDataDirectory(), text);
    }

    public int getMaxPlayers() {
        return this.serverConfigManager.getMaxPlayers();
    }

    public void setServerOwner(String text) {
        this.serverOwner = text;
    }

    public boolean getCanSpawnAnimals() {
        return this.canSpawnAnimals;
    }

    public BlockPos getPosition() {
        return BlockPos.ORIGIN;
    }

    public void setBuildLimit(int i) {
        this.buildLimit = i;
    }

    public void addServerStatsToSnooper(PlayerUsageSnooper playerUsageSnooper) {
        kq();

        playerUsageSnooper.addClientStat("whitelist_enabled", false);

        playerUsageSnooper.addClientStat("whitelist_count", 0);
        if (this.serverConfigManager != null) {
            playerUsageSnooper.addClientStat("players_current", this.getCurrentPlayerCount());
            playerUsageSnooper.addClientStat("players_max", this.getMaxPlayers());
            playerUsageSnooper.addClientStat(
                    "players_seen", this.serverConfigManager.getAvailablePlayerDat().length);
        }

        playerUsageSnooper.addClientStat("uses_auth", this.onlineMode);
        playerUsageSnooper.addClientStat("gui_state", this.getGuiEnabled() ? "enabled" : "disabled");
        String text = "run_time";
        long k = getCurrentTimeMillis() - playerUsageSnooper.getMinecraftStartTimeMillis();

        k /= 60L;

        playerUsageSnooper.addClientStat(text, k * 1000L);
        playerUsageSnooper.addClientStat(
                "avg_tick_ms", (int) (MathHelper.average(this.tickTimeArray) * 1.0E-6));
        int i = 0;
        if (this.worldServers != null) {
            int j = 0;

            while (j < this.worldServers.length) {
                if (this.worldServers[j] != null) {
                    WorldServer worldServer = this.worldServers[j];
                    WorldInfo worldInfo = worldServer.getWorldInfo();
                    playerUsageSnooper.addClientStat(
                            "world[" + i + "][dimension]", worldServer.provider.getDimensionId());
                    playerUsageSnooper.addClientStat(
                            "world[" + i + "][mode]", worldInfo.getGameType());
                    playerUsageSnooper.addClientStat(
                            "world[" + i + "][difficulty]", worldServer.getDifficulty());
                    playerUsageSnooper.addClientStat(
                            "world[" + i + "][hardcore]", worldInfo.isHardcoreModeEnabled());
                    playerUsageSnooper.addClientStat(
                            "world[" + i + "][generator_name]",
                            worldInfo.getTerrainType().getWorldTypeName());
                    playerUsageSnooper.addClientStat(
                            "world[" + i + "][generator_version]",
                            worldInfo.getTerrainType().getGeneratorVersion());
                    playerUsageSnooper.addClientStat("world[" + i + "][height]", this.buildLimit);
                    playerUsageSnooper.addClientStat(
                            "world[" + i + "][chunks_loaded]",
                            worldServer.getChunkProvider().getLoadedChunkCount());
                    i++;
                }

                j++;
            }
        }

        playerUsageSnooper.addClientStat("worlds", i);
    }
}
