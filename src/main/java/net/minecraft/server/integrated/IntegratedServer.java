package net.minecraft.server.integrated;

import com.google.common.util.concurrent.Futures;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.CryptManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import optifine.Reflector;
import optifine.WorldServerOF;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Queue;
import java.util.concurrent.FutureTask;

public class IntegratedServer extends MinecraftServer {
    private static Logger logger;
    private ThreadLanServerPing lanServerPing;
    private MinecraftClient mc;
    private WorldSettings theWorldSettings;

    private boolean isGamePaused;

    private boolean isPublic;
    private static String[] packedTextFragments;
    private static String[] worldLoadFlowMarker;

    public static void setWorldLoadFlowMarker(String[] astring) {
        net.minecraft.server.integrated.IntegratedServer.worldLoadFlowMarker = astring;
    }

    // region String decoding support

    // endregion

    public void initiateShutdown() {
        Futures.getUnchecked(this.addScheduledTask(new IntegratedServerPlayerLogoutTask(this)));
        super.initiateShutdown();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }

    public int getOpPermissionLevel() {
        return 4;
    }

    public void tick() {
        String[] astring1 = getWorldLoadFlowMarker();
        boolean angValue = this.isGamePaused;
        String[] astring = astring1;
        this.isGamePaused =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (MinecraftClient.getMinecraft().getNetworkHandler() != null
                                        && MinecraftClient.getMinecraft().isGamePaused()
                                ? 1
                                : 0));
        if (!angValue && this.isGamePaused) {
            net.minecraft.server.integrated.IntegratedServer.logger.info(
                    "Saving and pausing game...");
            this.getConfigurationManager().saveAllPlayerData();
            this.saveAllWorlds(false);
        }

        if (this.isGamePaused) {
            Queue queue = this.futureTaskQueue;
            Queue queue1 = this.futureTaskQueue;
            synchronized (this.futureTaskQueue) {
                while (!this.futureTaskQueue.isEmpty()) {
                    try {
                        if (Reflector.Gw.exists()) {
                            Reflector.callVoid(
                                    Reflector.Gw,
                                    new Object[] {(FutureTask) this.futureTaskQueue.poll()});
                            if (astring != null) {
                                continue;
                            }
                        }

                        ((FutureTask) this.futureTaskQueue.poll()).run();
                    } catch (Throwable throwable) {
                        net.minecraft.server.integrated.IntegratedServer.logger.fatal(throwable);
                        if (astring == null) {
                            break;
                        }
                    }
                }
            }

            if (astring != null) {
                return;
            }
        }

        super.tick();
        if (this.mc.gameSettings.renderDistanceChunks
                != this.getConfigurationManager().getViewDistance()) {
            net.minecraft.server.integrated.IntegratedServer.logger.info(
                    "Changing view distance to {}, from {}",
                    new Object[] {
                        this.mc.gameSettings.renderDistanceChunks,
                        this.getConfigurationManager().getViewDistance()
                    });
            this.getConfigurationManager().a(this.mc.gameSettings.renderDistanceChunks);
        }

        if (this.mc.world != null) {
            WorldInfo worldInfo = this.worldServers[0].getWorldInfo();
            WorldInfo worldInfoValue = this.mc.world.getWorldInfo();
            if (!worldInfo.isDifficultyLocked()
                    && worldInfoValue.getDifficulty() != worldInfo.getDifficulty()) {
                net.minecraft.server.integrated.IntegratedServer.logger.info(
                        "Changing difficulty to {}, from {}",
                        new Object[] {worldInfoValue.getDifficulty(), worldInfo.getDifficulty()});
                this.setDifficultyForAllWorlds(worldInfoValue.getDifficulty());
                if (astring != null) {
                    return;
                }
            }

            if (worldInfoValue.isDifficultyLocked() && !worldInfo.isDifficultyLocked()) {
                net.minecraft.server.integrated.IntegratedServer.logger.info(
                        "Locking difficulty to {}", new Object[] {worldInfoValue.getDifficulty()});

                for (WorldServer worldServer : this.worldServers) {
                    if (worldServer != null) {
                        worldServer.getWorldInfo().setDifficultyLocked(true);
                    }

                    if (astring == null) {
                        break;
                    }
                }
            }
        }
    }

    static {
        net.minecraft.server.integrated.IntegratedServer.packedTextFragments = new String[4];
        net.minecraft.server.integrated.IntegratedServer.packedTextFragments[0] = new String("");
        net.minecraft.server.integrated.IntegratedServer.packedTextFragments[1] = new String("");
        String[] astring1 = new String[4];
        setWorldLoadFlowMarker(astring1);
        net.minecraft.server.integrated.IntegratedServer.logger = LogManager.getLogger();
    }

    public boolean f() {
        return false;
    }

    public boolean isSnooperEnabled() {
        return MinecraftClient.getMinecraft().isSnooperEnabled();
    }

    public IntegratedServer(
            MinecraftClient byLValue, String text, String textValue, WorldSettings worldSettings) {
        super(
                new File(byLValue.gameDirectory, "saves"),
                byLValue.getProxy(),
                new File(
                        byLValue.gameDirectory,
                        net.minecraft.server.MinecraftServer.USER_CACHE_FILE.getName()));
        this.setServerOwner(byLValue.getSession().getUsername());
        getWorldLoadFlowMarker();
        this.setFolderName(text);
        this.v(textValue);
        this.setDemo(byLValue.isDemo());
        this.setBonusChestEnabled(worldSettings.isBonusChestEnabled());
        this.setBuildLimit(256);
        this.setConfigManager(new IntegratedPlayerList(this));
        this.mc = byLValue;
        this.theWorldSettings = this.isDemo() ? DemoWorldServer.demoWorldSettings : worldSettings;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setWorldLoadFlowMarker(new String[4]);
        }
    }

    public String shareToLAN(WorldSettingsGameType worldSettingsGameType, boolean flag) {
        try {
            int i = -1;

            try {
                i = HttpUtil.getSuitableLanPort();
            } catch (IOException ioexception) {
            }

            if (i <= 0) {
                i = 25564;
            }

            this.getNetworkSystem().addLanEndpoint((InetAddress) null, i);
            net.minecraft.server.integrated.IntegratedServer.logger.info("Started on " + i);
            this.isPublic = true;
            this.lanServerPing = new ThreadLanServerPing(this.QL(), i + packedTextFragments[0]);
            this.lanServerPing.start();
            this.getConfigurationManager().setGameType(worldSettingsGameType);
            this.getConfigurationManager().setCommandsAllowedForAll(flag);
            return i + packedTextFragments[1];
        } catch (IOException ioexception1) {
            return null;
        }
    }

    protected ServerCommandManager createNewCommandManager() {
        return new IntegratedServerCommandManager();
    }

    public File getDataDirectory() {
        return this.mc.gameDirectory;
    }

    // region String decoding support

    // endregion

    public void publishServerInstance() {
        this.setInstance();
    }

    public boolean isCommandBlockEnabled() {
        return true;
    }

    public boolean isDedicatedServer() {
        return false;
    }

    public void setDifficultyForAllWorlds(EnumDifficulty enumDifficulty) {
        super.setDifficultyForAllWorlds(enumDifficulty);
        if (this.mc.world != null) {
            this.mc.world.getWorldInfo().setDifficulty(enumDifficulty);
        }
    }

    public void addServerStatsToSnooper(PlayerUsageSnooper playerUsageSnooper) {
        super.addServerStatsToSnooper(playerUsageSnooper);
        playerUsageSnooper.addClientStat(
                "snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
    }

    protected void finalTick(CrashReport crashReport) {
        this.mc.crashed(crashReport);
    }

    protected boolean startServer() throws IOException {
        net.minecraft.server.integrated.IntegratedServer.logger.info(
                "Starting integrated minecraft server version 1.8");
        this.setOnlineMode(true);
        this.setCanSpawnAnimals(true);
        this.setCanSpawnNPCs(true);
        this.setAllowPvp(true);
        this.setAllowFlight(true);
        net.minecraft.server.integrated.IntegratedServer.logger.info("Generating keypair");
        this.setKeyPair(CryptManager.generateKeyPair());
        if (Reflector.Ib.exists()) {
            Object object = Reflector.call(Reflector.FS, new Object[0]);
            if (!Reflector.callBoolean(object, Reflector.Ib, new Object[] {this})) {
                return false;
            }
        }

        this.loadAllWorlds(
                this.getFolderName(),
                this.HM(),
                this.theWorldSettings.getSeed(),
                this.theWorldSettings.getTerrainType(),
                this.theWorldSettings.getWorldName());
        this.bg(this.getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());
        if (Reflector.Hh.exists()) {
            Object object1 = Reflector.call(Reflector.FS, new Object[0]);
            if (Reflector.Hh.getReturnType() == boolean.class) {
                return Reflector.callBoolean(object1, Reflector.Hh, new Object[] {this});
            }

            Reflector.callVoid(object1, Reflector.Hh, new Object[] {this});
        }

        return true;
    }

    // region String decoding support

    // endregion

    public boolean isHardcore() {
        return this.theWorldSettings.getHardcoreEnabled();
    }

    public void setGameType(WorldSettingsGameType worldSettingsGameType) {
        this.getConfigurationManager().setGameType(worldSettingsGameType);
    }

    public CrashReport addServerInfoToCrashReport(CrashReport crashReport) {
        crashReport = super.addServerInfoToCrashReport(crashReport);
        crashReport
                .getCategory()
                .addCrashSectionCallable("Type", new IntegratedServerTypeCrashDetail(this));
        crashReport
                .getCategory()
                .addCrashSectionCallable("Is Modded", new IntegratedServerModdedCrashDetail(this));
        return crashReport;
    }

    protected void loadAllWorlds(
            String folderName,
            String worldName,
            long seed,
            WorldType worldType,
            String generatorOptions) {
        boolean legacyFlow = getWorldLoadFlowMarker() == null;
        this.convertMapIfNeeded(folderName);
        ISaveHandler saveHandler = this.getActiveAnvilConverter().getSaveLoader(folderName, true);
        this.setResourcePackFromWorld(this.getFolderName(), saveHandler);
        WorldInfo worldInfo = saveHandler.loadWorldInfo();

        boolean forgeDimensions = Reflector.forgeDimensionManager.exists();
        if (forgeDimensions) {
            this.loadForgeDimensions(saveHandler, worldInfo, legacyFlow);
        }
        if (!forgeDimensions || legacyFlow) {
            this.loadVanillaDimensions(saveHandler, worldInfo, worldName, legacyFlow);
        }
        this.initialWorldChunkLoad();
    }

    private void loadForgeDimensions(
            ISaveHandler saveHandler, WorldInfo worldInfo, boolean legacyFlow) {
        WorldServer overworld =
                this.isDemo()
                        ? (WorldServer)
                                new DemoWorldServer(
                                                this, saveHandler, worldInfo, 0, this.theProfiler)
                                        .init()
                        : (WorldServer)
                                new WorldServerOF(this, saveHandler, worldInfo, 0, this.theProfiler)
                                        .init();
        overworld.initialize(this.theWorldSettings);

        Integer[] dimensionIds =
                (Integer[]) Reflector.call(Reflector.forgeGetStaticDimensionIds, new Object[0]);
        for (int dimensionId : dimensionIds) {
            WorldServer dimension =
                    dimensionId == 0
                            ? overworld
                            : this.createSecondaryWorld(saveHandler, dimensionId, overworld);
            dimension.addWorldAccess(new WorldManager(this, dimension));
            if (!this.isSinglePlayer()) {
                dimension.getWorldInfo().setGameType(this.getGameType());
            }
            if (Reflector.forgeEventBus.exists()) {
                Reflector.postForgeBusEvent(
                        Reflector.WorldEvent_Load_Constructor, new Object[] {dimension});
            }
            if (legacyFlow) {
                Entity.setLegacyGlobalFlowMarker(new String[2]);
                break;
            }
        }

        this.getConfigurationManager().setPlayerManager(new WorldServer[] {overworld});
        this.applyDefaultWorldDifficulty(overworld);
    }

    private void loadVanillaDimensions(
            ISaveHandler saveHandler, WorldInfo worldInfo, String worldName, boolean legacyFlow) {
        this.worldServers = new WorldServer[3];
        this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
        this.setResourcePackFromWorld(this.getFolderName(), saveHandler);
        if (worldInfo == null) {
            worldInfo = new WorldInfo(this.theWorldSettings, worldName);
            if (legacyFlow) {
                worldInfo.setWorldName(worldName);
            }
        } else {
            worldInfo.setWorldName(worldName);
        }

        for (int slot = 0; slot < this.worldServers.length; slot++) {
            int dimensionId = slot == 1 ? -1 : slot == 2 ? 1 : 0;
            if (slot == 0) {
                boolean demo = this.isDemo();
                if (demo) {
                    this.worldServers[slot] =
                            (WorldServer)
                                    new DemoWorldServer(
                                                    this,
                                                    saveHandler,
                                                    worldInfo,
                                                    dimensionId,
                                                    this.theProfiler)
                                            .init();
                }
                if (!demo || legacyFlow) {
                    this.worldServers[slot] =
                            (WorldServer)
                                    new WorldServerOF(
                                                    this,
                                                    saveHandler,
                                                    worldInfo,
                                                    dimensionId,
                                                    this.theProfiler)
                                            .init();
                }
                this.worldServers[slot].initialize(this.theWorldSettings);
            }
            if (slot != 0 || legacyFlow) {
                this.worldServers[slot] =
                        this.createSecondaryWorld(saveHandler, dimensionId, this.worldServers[0]);
            }

            WorldServer dimension = this.worldServers[slot];
            dimension.addWorldAccess(new WorldManager(this, dimension));
            if (legacyFlow) {
                break;
            }
        }
        this.getConfigurationManager().setPlayerManager(this.worldServers);
        this.applyDefaultWorldDifficulty(this.worldServers[0]);
    }

    private WorldServer createSecondaryWorld(
            ISaveHandler saveHandler, int dimensionId, WorldServer overworld) {
        return (WorldServer)
                new WorldServerMulti(this, saveHandler, dimensionId, overworld, this.theProfiler)
                        .init();
    }

    private void applyDefaultWorldDifficulty(WorldServer overworld) {
        if (overworld.getWorldInfo().getDifficulty() == null) {
            this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
        }
    }

    public static String[] getWorldLoadFlowMarker() {
        return net.minecraft.server.integrated.IntegratedServer.worldLoadFlowMarker;
    }

    public WorldSettingsGameType getGameType() {
        return this.theWorldSettings.getGameType();
    }

    public boolean isPublishedToLan() {
        return this.isPublic;
    }

    public EnumDifficulty getDifficulty() {
        return this.mc.world == null
                ? this.mc.gameSettings.difficulty
                : this.mc.world.getWorldInfo().getDifficulty();
    }

    // region String decoding support

    // endregion

    public void stopServer() {
        super.stopServer();
        if (this.lanServerPing != null) {
            this.lanServerPing.interrupt();
            this.lanServerPing = null;
        }
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public IntegratedServer(MinecraftClient byLValue) {
        super(
                byLValue.getProxy(),
                new java.io.File(
                        byLValue.gameDirectory,
                        net.minecraft.server.MinecraftServer.USER_CACHE_FILE.getName()));
        this.mc = byLValue;
        this.theWorldSettings = null;
    }
}
