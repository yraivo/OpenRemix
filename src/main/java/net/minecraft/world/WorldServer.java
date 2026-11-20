package net.minecraft.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.ListenableFuture;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEventData;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.INpc;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.ScoreboardSaveData;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.village.VillageCollection;
import net.minecraft.village.VillageSiege;
import net.minecraft.world.biome.BiomeGenBaseSpawnListEntry;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class WorldServer extends World implements IThreadListener {
    private Teleporter worldTeleporter;

    private List pendingTickListEntriesThisTick;
    private TreeSet pendingTickListEntriesTreeSet;
    private Map entitiesByUuid;
    private int updateEntityTick;

    private int blockEventCacheIndex;
    private MinecraftServer mcServer;
    private static Logger logger;
    private SpawnerAnimals mobSpawner;
    public ChunkProviderServer theChunkProviderServer;
    public boolean disableLevelSaving;
    private final Set pendingTickListEntriesHashSet = Sets.newHashSet();
    protected VillageSiege villageSiege;
    private boolean allPlayersSleeping;
    private static List bonusChestContent;
    private WorldServerServerBlockEventList[] blockEventQueues;
    private PlayerManager thePlayerManager;

    private EntityTracker theEntityTracker;

    public void flush() {
        this.saveHandler.flush();
    }

    public boolean isCallingFromMinecraftThread() {
        return this.mcServer.isCallingFromMinecraftThread();
    }

    public void addBlockEvent(BlockPos blockPos, Block block, int i, int j) {
        BlockEventData blockEventData = new BlockEventData(blockPos, block, i, j);

        for (java.util.Iterator<?> __recoveryIterator171_3419 =
                        (this.blockEventQueues[this.blockEventCacheIndex]).iterator();
                (__recoveryIterator171_3419).hasNext(); ) {
            BlockEventData blockEventDataValue =
                    (BlockEventData) (__recoveryIterator171_3419).next();
            if (blockEventDataValue.equals(blockEventData)) {
                return;
            }
        }

        this.blockEventQueues[this.blockEventCacheIndex].add(blockEventData);
    }

    public Explosion newExplosion(
            Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
        World.getLegacyWorldFlowMarker();

        Explosion explosion = new Explosion(this, entity, d0, d1, d2, f, flag, flag1);
        explosion.doExplosionA();

        explosion.doExplosionB(false);
        if (!flag1) {
            explosion.clearAffectedBlockPositions();
        }

        for (java.util.Iterator<?> __recoveryIterator171_4382 = (this.playerEntities).iterator();
                (__recoveryIterator171_4382).hasNext(); ) {
            PlayerEntity playerEntity = (PlayerEntity) (__recoveryIterator171_4382).next();
            if (playerEntity.getDistanceSq(d0, d1, d2) < 4096.0) {
                ((EntityPlayerMP) playerEntity)
                        .playerNetServerHandler.sendPacket(
                                new S27PacketExplosion(
                                        d0,
                                        d1,
                                        d2,
                                        f,
                                        explosion.getAffectedBlockPositions(),
                                        (Vec3)
                                                explosion
                                                        .getPlayerKnockbackMap()
                                                        .get(playerEntity)));
            }
        }

        return explosion;
    }

    public void spawnParticle(
            EnumParticleTypes enumParticleTypes,
            double d0,
            double d1,
            double d2,
            int i,
            double d3,
            double d4,
            double d5,
            double d6,
            int... aint) {
        this.spawnParticle(enumParticleTypes, false, d0, d1, d2, i, d3, d4, d5, d6, aint);
    }

    public void setInitialSpawnLocation() {
        if (this.worldInfo.getSpawnY() <= 0) {
            this.worldInfo.setSpawnY(64);
        }

        int spawnX = this.worldInfo.getSpawnX();
        int j = this.worldInfo.getSpawnZ();
        int k = 0;

        while (this.getGroundAboveSeaLevel(new BlockPos(spawnX, 0, j)).getMaterial()
                == Material.AIR) {
            spawnX += this.rand.nextInt(8) - this.rand.nextInt(8);
            j += this.rand.nextInt(8) - this.rand.nextInt(8);
            if (++k == 10000) {
                break;
            }
        }

        this.worldInfo.setSpawnX(spawnX);
        this.worldInfo.setSpawnZ(j);
    }

    public PlayerManager getPlayerManager() {
        return this.thePlayerManager;
    }

    private static Throwable preserveWorldException(Throwable throwable) {
        return throwable;
    }

    protected void createBonusChest() {
        int[] aint1 = World.getLegacyWorldFlowMarker();
        WorldGeneratorBonusChest worldGeneratorBonusChest =
                new WorldGeneratorBonusChest(net.minecraft.world.WorldServer.bonusChestContent, 10);
        int[] aint = aint1;
        int i = 0;

        while (i < 10) {
            int j = this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6);
            int k = this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6);
            BlockPos blockPos = this.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k)).offsetUp();
            if (worldGeneratorBonusChest.generate(this, this.rand, blockPos) && aint != null) {
                break;
            }

            i++;
        }
    }

    public void scheduleUpdate(BlockPos blockPos, Block block, int p171_2) {
        this.updateBlockTick(blockPos, block, p171_2, 0);
    }

    protected void onEntityAdded(Entity entity) {
        super.onEntityAdded(entity);
        this.entitiesById.addKey(entity.getEntityId(), entity);
        this.entitiesByUuid.put(entity.getUniqueId(), entity);
        World.getLegacyWorldFlowMarker();

        Entity[] entityArray = entity.getParts();

        if (entityArray != null) {
            int i = 0;

            while (i < entityArray.length) {
                this.entitiesById.addKey(entityArray[i].getEntityId(), entityArray[i]);
                i++;
            }
        }
    }

    public void saveExtraChunkData() {
        try {
            if (this.chunkProvider.canSave()) {
                this.chunkProvider.saveExtraData();
            }
        } catch (java.lang.IllegalStateException illegalstateexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.world.WorldServer.preserveWorldException(illegalstateexception));
        }
    }

    protected void updateWeather() {
        World.getLegacyWorldFlowMarker();

        boolean flag = this.isRaining();

        super.updateWeather();
        if (this.prevRainingStrength != this.rainingStrength) {
            this.mcServer
                    .getConfigurationManager()
                    .sendPacketToAllPlayersInDimension(
                            new S2BPacketChangeGameState(7, this.rainingStrength),
                            this.provider.getDimensionId());
        }

        if (this.prevThunderingStrength != this.thunderingStrength) {
            this.mcServer
                    .getConfigurationManager()
                    .sendPacketToAllPlayersInDimension(
                            new S2BPacketChangeGameState(8, this.thunderingStrength),
                            this.provider.getDimensionId());
        }

        if (flag != this.isRaining()) {
            if (flag) {
                this.mcServer
                        .getConfigurationManager()
                        .sendPacketToAllPlayers(new S2BPacketChangeGameState(2, 0.0F));

            } else {
                this.mcServer
                        .getConfigurationManager()
                        .sendPacketToAllPlayers(new S2BPacketChangeGameState(1, 0.0F));
            }

            this.mcServer
                    .getConfigurationManager()
                    .sendPacketToAllPlayers(new S2BPacketChangeGameState(7, this.rainingStrength));
            this.mcServer
                    .getConfigurationManager()
                    .sendPacketToAllPlayers(
                            new S2BPacketChangeGameState(8, this.thunderingStrength));
        }
    }

    protected void saveLevel() throws MinecraftException {
        this.checkSessionLock();
        this.worldInfo.setBorderSize(this.getWorldBorder().getDiameter());
        this.worldInfo.getBorderCenterX(this.getWorldBorder().getCenterX());
        this.worldInfo.getBorderCenterZ(this.getWorldBorder().getCenterZ());
        this.worldInfo.setBorderSafeZone(this.getWorldBorder().getDamageBuffer());
        this.worldInfo.setBorderDamagePerBlock(this.getWorldBorder().getDamageAmount());
        this.worldInfo.setBorderWarningDistance(this.getWorldBorder().getWarningDistance());
        this.worldInfo.setBorderWarningTime(this.getWorldBorder().getWarningTime());
        this.worldInfo.setBorderLerpTarget(this.getWorldBorder().getTargetSize());
        this.worldInfo.setBorderLerpTime(this.getWorldBorder().getTimeUntilTarget());
        this.saveHandler.saveWorldInfoWithPlayer(
                this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
        this.mapStorage.saveAllData();
    }

    protected void updateBlocks() {
        World.getLegacyWorldFlowMarker();

        super.updateBlocks();

        if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            for (java.util.Iterator<?> __recoveryIterator171_12376 =
                            (this.activeChunkSet).iterator();
                    (__recoveryIterator171_12376).hasNext(); ) {
                ChunkCoordIntPair chunkCoordIntPair =
                        (ChunkCoordIntPair) (__recoveryIterator171_12376).next();
                this.getChunkFromChunkCoords(
                                chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos)
                        .tickChunk(false);
            }

            return;
        }

        int i2 = 0;
        int j2 = 0;

        for (java.util.Iterator<?> __recoveryIterator171_12798 = (this.activeChunkSet).iterator();
                (__recoveryIterator171_12798).hasNext(); ) {
            ChunkCoordIntPair chunkCoordIntPairValue =
                    (ChunkCoordIntPair) (__recoveryIterator171_12798).next();
            int i = chunkCoordIntPairValue.chunkXPos * 16;
            int j = chunkCoordIntPairValue.chunkZPos * 16;
            this.theProfiler.startSection("getChunk");
            Chunk chunkFromChunkCoords =
                    this.getChunkFromChunkCoords(
                            chunkCoordIntPairValue.chunkXPos, chunkCoordIntPairValue.chunkZPos);
            this.playMoodSoundAndCheckLight(i, j, chunkFromChunkCoords);
            this.theProfiler.endStartSection("tickChunk");
            chunkFromChunkCoords.tickChunk(false);
            this.theProfiler.endStartSection("thunder");
            if (this.rand.nextInt(100000) == 0 && this.isRaining() && this.isThundering()) {
                this.updateLCG = this.updateLCG * 3 + 1013904223;
                int k = this.updateLCG >> 2;
                BlockPos blockPos =
                        this.adjustPosToNearbyEntity(
                                new BlockPos(i + (k & 15), 0, j + (k >> 8 & 15)));
                if (this.canLightningStrike(blockPos)) {
                    this.addWeatherEffect(
                            new EntityLightningBolt(
                                    this, blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                }
            }

            this.theProfiler.endStartSection("iceandsnow");
            if (this.rand.nextInt(16) == 0) {
                this.updateLCG = this.updateLCG * 3 + 1013904223;
                int k2 = this.updateLCG >> 2;
                BlockPos blockPosValue =
                        this.getPrecipitationHeight(
                                new BlockPos(i + (k2 & 15), 0, j + (k2 >> 8 & 15)));
                BlockPos blockPosValue2 = blockPosValue.offsetDown();
                if (this.canBlockFreezeNoWater(blockPosValue2)) {
                    this.setBlockState(blockPosValue2, Blocks.ice.getDefaultState());
                }

                if (this.isRaining() && this.canSnowAt(blockPosValue, true)) {
                    this.setBlockState(blockPosValue, Blocks.snow_layer.getDefaultState());
                }

                if (this.isRaining() && this.getBiomeGenForCoords(blockPosValue2).canRain()) {
                    this.getBlockState(blockPosValue2)
                            .getBlock()
                            .fillWithRain(this, blockPosValue2);
                }
            }

            this.theProfiler.endStartSection("tickBlocks");
            int l2 = this.getGameRules().getInt("randomTickSpeed");
            if (l2 > 0) {
                for (ExtendedBlockStorage extendedBlockStorage :
                        chunkFromChunkCoords.getBlockStorageArray()) {
                    if (extendedBlockStorage != null && extendedBlockStorage.getNeedsRandomTick()) {
                        int l = 0;

                        while (l < l2) {
                            this.updateLCG = this.updateLCG * 3 + 1013904223;
                            int i1 = this.updateLCG >> 2;
                            int j1 = i1 & 15;
                            int k1 = i1 >> 8 & 15;
                            int l1 = i1 >> 16 & 15;
                            j2++;
                            BlockPos blockPosValue3 =
                                    new BlockPos(
                                            j1 + i,
                                            l1 + extendedBlockStorage.getYLocation(),
                                            k1 + j);
                            BlockState blockState = extendedBlockStorage.get(j1, l1, k1);
                            Block block = blockState.getBlock();
                            if (block.getTickRandomly()) {
                                i2++;
                                block.randomTick(this, blockPosValue3, blockState, this.rand);
                            }

                            l++;
                        }
                    }
                }
            }

            this.theProfiler.endSection();
        }
    }

    private void sendQueuedBlockEvents() {
        World.getLegacyWorldFlowMarker();

        while (!this.blockEventQueues[this.blockEventCacheIndex].isEmpty()) {
            int blockEventCacheIndexValue = this.blockEventCacheIndex;
            this.blockEventCacheIndex ^= 1;
            Iterator iterator = this.blockEventQueues[blockEventCacheIndexValue].iterator();

            while (true) {
                if (iterator.hasNext()) {
                    BlockEventData blockEventData = (BlockEventData) iterator.next();
                    if (this.fireBlockEvent(blockEventData)) {
                        this.mcServer
                                .getConfigurationManager()
                                .sendToAllNear(
                                        blockEventData.getPosition().getX(),
                                        blockEventData.getPosition().getY(),
                                        blockEventData.getPosition().getZ(),
                                        64.0,
                                        this.provider.getDimensionId(),
                                        new S24PacketBlockAction(
                                                blockEventData.getPosition(),
                                                blockEventData.getBlock(),
                                                blockEventData.getEventID(),
                                                blockEventData.getEventParameter()));
                    }

                    continue;
                }

                this.blockEventQueues[blockEventCacheIndexValue].clear();

                break;
            }
        }
    }

    public void updateEntityWithOptionalForce(Entity entity, boolean flag) {
        if (!this.canSpawnAnimals()
                && (entity instanceof AnimalEntity || entity instanceof EntityWaterMob)) {
            entity.setDead();
        }

        if (!this.jvm171_f_7b08d3d2b18e44() && entity instanceof INpc) {
            entity.setDead();
        }

        super.updateEntityWithOptionalForce(entity, flag);
    }

    public boolean addWeatherEffect(Entity entity) {
        if (super.addWeatherEffect(entity)) {
            this.mcServer
                    .getConfigurationManager()
                    .sendToAllNear(
                            entity.posX,
                            entity.posY,
                            entity.posZ,
                            512.0,
                            this.provider.getDimensionId(),
                            new S2CPacketSpawnGlobalEntity(entity));
            return true;
        } else {
            return false;
        }
    }

    public boolean isBlockModifiable(PlayerEntity playerEntity, BlockPos blockPos) {
        return !this.mcServer.isBlockProtected(this, blockPos, playerEntity)
                && this.getWorldBorder().contains(blockPos);
    }

    public boolean isBlockTickPending(BlockPos blockPos, Block block) {
        net.minecraft.world.NextTickListEntry nextTickListEntry =
                new net.minecraft.world.NextTickListEntry(blockPos, block);
        return this.pendingTickListEntriesThisTick.contains(nextTickListEntry);
    }

    private boolean jvm171_f_7b08d3d2b18e44() {
        return this.mcServer.getCanSpawnNPCs();
    }

    public boolean canCreatureTypeSpawnHere(
            EnumCreatureType enumCreatureType,
            BiomeGenBaseSpawnListEntry biomeGenBaseSpawnListEntry,
            BlockPos blockPos) {
        List possibleCreatures =
                this.getChunkProvider().getPossibleCreatures(enumCreatureType, blockPos);
        return possibleCreatures != null && !possibleCreatures.isEmpty()
                ? possibleCreatures.contains(biomeGenBaseSpawnListEntry)
                : false;
    }

    protected void onEntityRemoved(Entity entity) {
        super.onEntityRemoved(entity);
        World.getLegacyWorldFlowMarker();

        this.entitiesById.removeObject(entity.getEntityId());

        this.entitiesByUuid.remove(entity.getUniqueId());
        Entity[] entityArray = entity.getParts();
        if (entityArray != null) {
            int i = 0;

            while (i < entityArray.length) {
                this.entitiesById.removeObject(entityArray[i].getEntityId());
                i++;
            }
        }
    }

    public Teleporter getDefaultTeleporter() {
        return this.worldTeleporter;
    }

    public List getPendingBlockUpdatesInBounds(StructureBoundingBox bounds, boolean removeUpdates) {
        World.getLegacyWorldFlowMarker();
        List<NextTickListEntry> matchingUpdates = null;

        for (int pass = 0; pass < 2; pass++) {
            Iterator iterator;
            if (pass == 0) {
                iterator = this.pendingTickListEntriesTreeSet.iterator();
            } else {
                iterator = this.pendingTickListEntriesThisTick.iterator();
                if (!this.pendingTickListEntriesThisTick.isEmpty()) {
                    net.minecraft.world.WorldServer.logger.debug(
                            "toBeTicked = " + this.pendingTickListEntriesThisTick.size());
                }
            }

            while (iterator.hasNext()) {
                NextTickListEntry entry = (NextTickListEntry) iterator.next();
                BlockPos pos = entry.position;
                if (pos.getX() >= bounds.minX
                        && pos.getX() < bounds.maxX
                        && pos.getZ() >= bounds.minZ
                        && pos.getZ() < bounds.maxZ) {
                    if (removeUpdates) {
                        this.pendingTickListEntriesHashSet.remove(entry);
                        iterator.remove();
                    }
                    if (matchingUpdates == null) {
                        matchingUpdates = Lists.newArrayList();
                    }
                    matchingUpdates.add(entry);
                }
            }
        }

        return matchingUpdates;
    }

    protected IChunkProvider createChunkProvider() {
        net.minecraft.world.chunk.storage.IChunkLoader chunkLoader =
                this.saveHandler.getChunkLoader(this.provider);
        this.theChunkProviderServer =
                new net.minecraft.world.gen.ChunkProviderServer(
                        this, chunkLoader, this.provider.createChunkGenerator());
        return this.theChunkProviderServer;
    }

    public void resetUpdateEntityTick() {
        this.updateEntityTick = 0;
    }

    protected void wakeAllPlayers() {
        this.allPlayersSleeping = false;
        World.getLegacyWorldFlowMarker();

        Iterator iterator = this.playerEntities.iterator();

        while (iterator.hasNext()) {
            PlayerEntity playerEntity = (PlayerEntity) iterator.next();
            if (playerEntity.isPlayerSleeping()) {
                playerEntity.wakeUpPlayer(false, false, true);
            }
        }

        this.resetRainAndThunder();
    }

    private void createSpawnPosition(WorldSettings worldSettings) {
        World.getLegacyWorldFlowMarker();

        if (!this.provider.canRespawnHere()) {
            this.worldInfo.setSpawn(
                    BlockPos.ORIGIN.offsetUp(this.provider.getAverageGroundLevel()));

            return;
        }

        if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            this.worldInfo.setSpawn(BlockPos.ORIGIN.offsetUp());

            return;
        }

        Random random;
        int i;
        int j;
        int k;
        this.findingSpawnPoint = true;
        WorldChunkManager worldChunkManager = this.provider.getWorldChunkManager();
        List biomesToSpawnIn = worldChunkManager.getBiomesToSpawnIn();
        random = new Random(this.getSeed());
        BlockPos blockPos = worldChunkManager.findBiomePosition(0, 0, 256, biomesToSpawnIn, random);
        i = 0;
        j = this.provider.getAverageGroundLevel();
        k = 0;
        if (blockPos != null) {
            i = blockPos.getX();
            k = blockPos.getZ();

        } else {
            net.minecraft.world.WorldServer.logger.warn("Unable to find spawn biome");
        }

        int l = 0;

        while (!this.provider.canCoordinateBeSpawn(i, k)) {
            i += random.nextInt(64) - random.nextInt(64);
            k += random.nextInt(64) - random.nextInt(64);
            if (++l == 1000) {
                break;
            }
        }

        this.worldInfo.setSpawn(new BlockPos(i, j, k));
        this.findingSpawnPoint = false;
        if (worldSettings.isBonusChestEnabled()) {
            this.createBonusChest();
        }
    }

    public boolean areAllPlayersAsleep() {
        try {
            if (!this.allPlayersSleeping || this.isRemote) {
                return false;
            }
        } catch (java.lang.IllegalStateException illegalstateexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.world.WorldServer.preserveWorldException(illegalstateexception1));
        }

        for (java.util.Iterator<?> __recoveryIterator171_28216 =
                        ((java.lang.Iterable<net.minecraft.entity.player.PlayerEntity>)
                                        (java.lang.Iterable<?>) (this.playerEntities))
                                .iterator();
                (__recoveryIterator171_28216).hasNext(); ) {
            net.minecraft.entity.player.PlayerEntity playerEntity =
                    (net.minecraft.entity.player.PlayerEntity) (__recoveryIterator171_28216).next();
            try {
                if (playerEntity.isSpectator() || !playerEntity.isPlayerFullyAsleep()) {
                    return false;
                }
            } catch (java.lang.IllegalStateException illegalstateexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.world.WorldServer.preserveWorldException(
                                illegalstateexception));
            }
        }

        return true;
    }

    public void setEntityState(Entity entity, byte p171_1) {
        this.getEntityTracker()
                .sendToTrackingPlayersAndEntity(
                        entity,
                        new net.minecraft.network.play.server.S19PacketEntityStatus(
                                entity, p171_1));
    }

    protected BlockPos adjustPosToNearbyEntity(BlockPos blockPos) {
        BlockPos blockPosValue = this.getPrecipitationHeight(blockPos);
        BoundingBox boundingBox =
                new BoundingBox(
                                blockPosValue,
                                new BlockPos(
                                        blockPosValue.getX(),
                                        this.getHeight(),
                                        blockPosValue.getZ()))
                        .expand(3.0, 3.0, 3.0);
        List entitiesWithinAABB =
                this.getEntitiesWithinAABB(
                        LivingEntity.class,
                        boundingBox,
                        new AdjustPosToNearbyEntityPredicate(this));
        return !entitiesWithinAABB.isEmpty()
                ? ((LivingEntity)
                                entitiesWithinAABB.get(
                                        this.rand.nextInt(entitiesWithinAABB.size())))
                        .getPosition()
                : blockPosValue;
    }

    public void initialize(WorldSettings worldSettings) {
        if (!this.worldInfo.isInitialized()) {
            try {
                this.createSpawnPosition(worldSettings);
                if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
                    this.setDebugWorldSettings();
                }

                super.initialize(worldSettings);
            } catch (Throwable throwable1) {
                CrashReport crashReport =
                        CrashReport.makeCrashReport(throwable1, "Exception initializing level");

                try {
                    this.addWorldInfoToCrashReport(crashReport);
                } catch (Throwable throwable) {
                }

                throw new ReportedException(crashReport);
            }

            this.worldInfo.setServerInitialized(true);
        }
    }

    public EntityTracker getEntityTracker() {
        return this.theEntityTracker;
    }

    public void saveAllChunks(boolean flag, IProgressUpdate iProgressUpdate)
            throws MinecraftException {
        World.getLegacyWorldFlowMarker();

        if (this.chunkProvider.canSave()) {
            if (iProgressUpdate != null) {
                iProgressUpdate.displaySavingString("Saving level");
            }

            this.saveLevel();
            if (iProgressUpdate != null) {
                iProgressUpdate.displayLoadingString("Saving chunks");
            }

            this.chunkProvider.saveChunks(flag, iProgressUpdate);

            for (java.util.Iterator<?> __recoveryIterator171_31831 =
                            (this.theChunkProviderServer.getLoadedChunks()).iterator();
                    (__recoveryIterator171_31831).hasNext(); ) {
                Chunk chunk = (Chunk) (__recoveryIterator171_31831).next();
                if (!this.thePlayerManager.hasPlayerInstance(chunk.xPosition, chunk.zPosition)) {
                    this.theChunkProviderServer.dropChunk(chunk.xPosition, chunk.zPosition);
                }
            }
        }
    }

    public void tick() {
        super.tick();
        if (this.getWorldInfo().isHardcoreModeEnabled()
                && this.getDifficulty() != EnumDifficulty.HARD) {
            this.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
        }

        this.provider.getWorldChunkManager().cleanupCache();
        if (this.areAllPlayersAsleep()) {
            if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
                long worldTime = this.worldInfo.getWorldTime();

                long i = worldTime + 24000L;

                this.worldInfo.setWorldTime(i - i % 24000L);
            }

            this.wakeAllPlayers();
        }

        this.theProfiler.startSection("mobSpawner");
        if (this.getGameRules().getGameRuleBooleanValue("doMobSpawning")
                && this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
            SpawnerAnimals bGkValue = this.mobSpawner;
            boolean spawnHostileMobsValue = this.spawnHostileMobs;
            boolean aWfValue = this.spawnPeacefulMobs;
            long l = this.worldInfo.getWorldTotalTime();

            l %= 400L;

            bGkValue.findChunksForSpawning(this, spawnHostileMobsValue, aWfValue, l == 0L);
        }

        this.theProfiler.endStartSection("chunkSource");
        this.chunkProvider.unloadQueuedChunks();
        int j = this.calculateSkylightSubtracted(1.0F);
        if (j != this.getSkylightSubtracted()) {
            this.setSkylightSubtracted(j);
        }

        WorldInfo bFZValue = this.worldInfo;
        long j1 = this.worldInfo.getWorldTotalTime();

        bFZValue.incrementTotalWorldTime(j1 + 1L);
        if (this.getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
            bFZValue = this.worldInfo;
            j1 = this.worldInfo.getWorldTime();

            bFZValue.setWorldTime(j1 + 1L);
        }

        this.theProfiler.endStartSection("tickPending");
        this.tickUpdates(false);
        this.theProfiler.endStartSection("tickBlocks");
        this.updateBlocks();
        this.theProfiler.endStartSection("chunkMap");
        this.thePlayerManager.updatePlayerInstances();
        this.theProfiler.endStartSection("village");
        this.villageCollectionObj.tick();
        this.villageSiege.tick();
        this.theProfiler.endStartSection("portalForcer");
        this.worldTeleporter.removeStalePortalLocations(this.getTotalWorldTime());
        this.theProfiler.endSection();
        this.sendQueuedBlockEvents();
    }

    public void updateEntities() {
        if (((boolean) (((java.util.List) (this.playerEntities)).isEmpty()))) {
            if (this.updateEntityTick++ >= 1200) {
                return;
            }
        } else {
            this.resetUpdateEntityTick();
        }

        super.updateEntities();
    }

    public List getPendingBlockUpdates(Chunk chunk, boolean p171_1) {
        net.minecraft.world.ChunkCoordIntPair chunkCoordIntPair = chunk.getChunkCoordIntPair();
        int i = (chunkCoordIntPair.chunkXPos << 4) - 2;
        int j = i + 16 + 2;
        int k = (chunkCoordIntPair.chunkZPos << 4) - 2;
        int l = k + 16 + 2;
        return this.getPendingBlockUpdatesInBounds(
                new net.minecraft.world.gen.structure.StructureBoundingBox(i, 0, k, j, 256, l),
                p171_1);
    }

    public MinecraftServer getMinecraftServer() {
        return this.mcServer;
    }

    private boolean canSpawnAnimals() {
        return this.mcServer.getCanSpawnAnimals();
    }

    public Entity getEntityFromUuid(UUID uuid) {
        return (net.minecraft.entity.Entity) this.entitiesByUuid.get(uuid);
    }

    private boolean fireBlockEvent(BlockEventData blockEventData) {
        BlockState blockState = this.getBlockState(blockEventData.getPosition());
        return blockState.getBlock() == blockEventData.getBlock()
                ? blockState
                        .getBlock()
                        .onBlockEventReceived(
                                this,
                                blockEventData.getPosition(),
                                blockState,
                                blockEventData.getEventID(),
                                blockEventData.getEventParameter())
                : false;
    }

    private void setDebugWorldSettings() {
        this.worldInfo.setMapFeaturesEnabled(false);
        this.worldInfo.setAllowCommands(true);
        this.worldInfo.setRaining(false);
        this.worldInfo.setThundering(false);
        this.worldInfo.setCleanWeatherTime(1000000000);

        this.worldInfo.setWorldTime(6000L);
        this.worldInfo.setGameType(WorldSettingsGameType.SPECTATOR);
        this.worldInfo.setHardcore(false);
        this.worldInfo.setDifficulty(EnumDifficulty.PEACEFUL);
        this.worldInfo.setDifficultyLocked(true);
        this.getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
    }

    public BlockPos getSpawnCoordinate() {
        return this.provider.getSpawnCoordinate();
    }

    public BiomeGenBaseSpawnListEntry getSpawnListEntryForTypeAt(
            EnumCreatureType enumCreatureType, BlockPos blockPos) {
        List possibleCreatures =
                this.getChunkProvider().getPossibleCreatures(enumCreatureType, blockPos);
        return possibleCreatures != null && !possibleCreatures.isEmpty()
                ? (BiomeGenBaseSpawnListEntry)
                        WeightedRandom.getRandomItem(this.rand, possibleCreatures)
                : null;
    }

    public WorldServer(
            MinecraftServer bGiValue,
            ISaveHandler iSaveHandler,
            WorldInfo worldInfoValue,
            int i,
            Profiler profiler) {
        super(
                iSaveHandler,
                worldInfoValue,
                WorldProvider.getProviderForDimension(i),
                profiler,
                false);
        this.pendingTickListEntriesTreeSet = new TreeSet();
        this.entitiesByUuid = Maps.newHashMap();
        this.mobSpawner = new SpawnerAnimals();
        this.villageSiege = new VillageSiege(this);
        this.blockEventQueues =
                new WorldServerServerBlockEventList[] {
                    new WorldServerServerBlockEventList(null),
                    new WorldServerServerBlockEventList(null)
                };
        this.pendingTickListEntriesThisTick = Lists.newArrayList();
        this.mcServer = bGiValue;
        this.theEntityTracker = new EntityTracker(this);
        this.thePlayerManager = new PlayerManager(this);
        this.provider.registerWorld(this);
        this.chunkProvider = this.createChunkProvider();
        this.worldTeleporter = new Teleporter(this);
        this.updateSkylightSubtracted();
        this.calculateInitialWeather();
        this.getWorldBorder().setSize(bGiValue.getMaxWorldSize());
    }

    public void scheduleBlockUpdate(BlockPos blockPos, Block block, int i, int j) {
        NextTickListEntry nextTickListEntry = new NextTickListEntry(blockPos, block);
        nextTickListEntry.setPriority(j);
        if (block.getMaterial() != Material.AIR) {
            nextTickListEntry.setScheduledTime(i + this.worldInfo.getWorldTotalTime());
        }

        if (!this.pendingTickListEntriesHashSet.contains(nextTickListEntry)) {
            this.pendingTickListEntriesHashSet.add(nextTickListEntry);
            this.pendingTickListEntriesTreeSet.add(nextTickListEntry);
        }
    }

    public ListenableFuture addScheduledTask(Runnable runnable) {
        return this.mcServer.addScheduledTask(runnable);
    }

    protected int getRenderDistanceChunks() {
        return this.mcServer.getConfigurationManager().getViewDistance();
    }

    public void updateAllPlayersSleepingFlag() {
        World.getLegacyWorldFlowMarker();
        this.allPlayersSleeping = false;
        if (this.playerEntities.isEmpty()) {
            return;
        }

        int spectatorCount = 0;
        int sleepingCount = 0;
        for (Object entry : this.playerEntities) {
            PlayerEntity player = (PlayerEntity) entry;
            if (player.isSpectator()) {
                spectatorCount++;
            } else if (player.isPlayerSleeping()) {
                sleepingCount++;
            }
        }
        this.allPlayersSleeping =
                sleepingCount > 0 && sleepingCount >= this.playerEntities.size() - spectatorCount;
    }

    private void resetRainAndThunder() {
        this.worldInfo.setRainTime(0);
        this.worldInfo.setRaining(false);
        this.worldInfo.setThunderTime(0);
        this.worldInfo.setThundering(false);
    }

    public void spawnParticle(
            EnumParticleTypes enumParticleTypes,
            boolean flag,
            double d0,
            double d1,
            double d2,
            int i,
            double d3,
            double d4,
            double d5,
            double d6,
            int... aint) {
        World.getLegacyWorldFlowMarker();

        S2APacketParticles s2APacketParticles =
                new S2APacketParticles(
                        enumParticleTypes,
                        flag,
                        (float) d0,
                        (float) d1,
                        (float) d2,
                        (float) d3,
                        (float) d4,
                        (float) d5,
                        (float) d6,
                        i,
                        aint);
        int j = 0;

        while (j < this.playerEntities.size()) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) this.playerEntities.get(j);
            BlockPos position = entityPlayerMP.getPosition();
            double d7 = position.distanceSq(d0, d1, d2);
            if (d7 <= 256.0 || flag && d7 <= 65536.0) {
                entityPlayerMP.playerNetServerHandler.sendPacket(s2APacketParticles);
            }

            j++;
        }
    }

    public void updateBlockTick(BlockPos blockPos, Block block, int i, int j) {
        NextTickListEntry nextTickListEntry = new NextTickListEntry(blockPos, block);
        byte b0 = 0;
        if (this.scheduledUpdatesAreImmediate && block.getMaterial() != Material.AIR) {
            if (block.requiresUpdates()) {
                b0 = 8;
                if (this.isAreaLoaded(
                        nextTickListEntry.position.add(-b0, -b0, -b0),
                        nextTickListEntry.position.add(b0, b0, b0))) {
                    BlockState blockState = this.getBlockState(nextTickListEntry.position);
                    if (blockState.getBlock().getMaterial() != Material.AIR
                            && blockState.getBlock() == nextTickListEntry.getBlock()) {
                        blockState
                                .getBlock()
                                .updateTick(
                                        this, nextTickListEntry.position, blockState, this.rand);
                    }
                }

                return;
            }

            i = 1;
        }

        if (this.isAreaLoaded(blockPos.add(-b0, -b0, -b0), blockPos.add(b0, b0, b0))) {
            if (block.getMaterial() != Material.AIR) {
                nextTickListEntry.setScheduledTime(i + this.worldInfo.getWorldTotalTime());
                nextTickListEntry.setPriority(j);
            }

            if (!this.pendingTickListEntriesHashSet.contains(nextTickListEntry)) {
                this.pendingTickListEntriesHashSet.add(nextTickListEntry);
                this.pendingTickListEntriesTreeSet.add(nextTickListEntry);
            }
        }
    }

    public List getTileEntitiesIn(int i, int j, int k, int l, int i1, int j1) {
        World.getLegacyWorldFlowMarker();

        ArrayList arrayList = Lists.newArrayList();

        int k1 = 0;

        while (k1 < this.loadedTileEntityList.size()) {
            TileEntity tileEntity = (TileEntity) this.loadedTileEntityList.get(k1);
            BlockPos blockPos = tileEntity.getPos();
            if (blockPos.getX() >= i
                    && blockPos.getY() >= j
                    && blockPos.getZ() >= k
                    && blockPos.getX() < l
                    && blockPos.getY() < i1
                    && blockPos.getZ() < j1) {
                arrayList.add(tileEntity);
            }

            k1++;
        }

        return arrayList;
    }

    public World init() {
        int[] aint;
        int[] aint1 = World.getLegacyWorldFlowMarker();
        this.mapStorage = new MapStorage(this.saveHandler);
        aint = aint1;
        String text = VillageCollection.fileNameForProvider(this.provider);
        VillageCollection bGbValue =
                (VillageCollection) this.mapStorage.loadData(VillageCollection.class, text);
        if (bGbValue == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData(text, this.villageCollectionObj);

        } else {
            this.villageCollectionObj = bGbValue;
            this.villageCollectionObj.setWorldsForAll(this);
        }

        this.worldScoreboard = new ServerScoreboard(this.mcServer);
        ScoreboardSaveData scoreboardSaveData =
                (ScoreboardSaveData)
                        this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
        if (scoreboardSaveData == null) {
            scoreboardSaveData = new ScoreboardSaveData();
            this.mapStorage.setData("scoreboard", scoreboardSaveData);
        }

        scoreboardSaveData.setScoreboard(this.worldScoreboard);
        ((ServerScoreboard) this.worldScoreboard).setScoreboardSaveData(scoreboardSaveData);
        this.getWorldBorder()
                .setCenter(this.worldInfo.getBorderCenterX(), this.worldInfo.getBorderCenterZ());
        this.getWorldBorder().setDamageAmount(this.worldInfo.getBorderDamagePerBlock());
        this.getWorldBorder().setDamageBuffer(this.worldInfo.getBorderSafeZone());
        this.getWorldBorder().setWarningDistance(this.worldInfo.getBorderWarningDistance());
        this.getWorldBorder().setWarningTime(this.worldInfo.getBorderWarningTime());
        long borderLerpTime = this.worldInfo.getBorderLerpTime();

        if (borderLerpTime > 0L) {
            this.getWorldBorder()
                    .setTransition(
                            this.worldInfo.getBorderSize(),
                            this.worldInfo.getBorderLerpTarget(),
                            this.worldInfo.getBorderLerpTime());

            return this;
        }

        this.getWorldBorder().setTransition(this.worldInfo.getBorderSize());
        return this;
    }

    static {
        net.minecraft.world.WorldServer.logger = LogManager.getLogger();
        net.minecraft.world.WorldServer.bonusChestContent =
                Lists.newArrayList(
                        new WeightedRandomChestContent[] {
                            new WeightedRandomChestContent(Items.stick, 0, 1, 3, 10),
                            new WeightedRandomChestContent(
                                    Item.getItemFromBlock(Blocks.planks), 0, 1, 3, 10),
                            new WeightedRandomChestContent(
                                    Item.getItemFromBlock(Blocks.log), 0, 1, 3, 10),
                            new WeightedRandomChestContent(Items.stone_axe, 0, 1, 1, 3),
                            new WeightedRandomChestContent(Items.wooden_axe, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.stone_pickaxe, 0, 1, 1, 3),
                            new WeightedRandomChestContent(Items.wooden_pickaxe, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.apple, 0, 2, 3, 5),
                            new WeightedRandomChestContent(Items.bread, 0, 2, 3, 3),
                            new WeightedRandomChestContent(
                                    Item.getItemFromBlock(Blocks.log2), 0, 1, 3, 10)
                        });
    }

    public boolean tickUpdates(boolean flag) {
        int[] aint = World.getLegacyWorldFlowMarker();
        if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            return false;
        }

        int i = this.pendingTickListEntriesTreeSet.size();
        if (i != this.pendingTickListEntriesHashSet.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        }

        if (i > 1000) {
            i = 1000;
        }

        this.theProfiler.startSection("cleaning");
        int j = 0;

        while (j < i) {
            NextTickListEntry nextTickListEntry =
                    (NextTickListEntry) this.pendingTickListEntriesTreeSet.first();
            if (!flag
                    && nextTickListEntry.scheduledTime > this.worldInfo.getWorldTotalTime()
                    && aint != null) {
                break;
            }

            this.pendingTickListEntriesTreeSet.remove(nextTickListEntry);
            this.pendingTickListEntriesHashSet.remove(nextTickListEntry);
            this.pendingTickListEntriesThisTick.add(nextTickListEntry);
            j++;
        }

        this.theProfiler.endSection();
        this.theProfiler.startSection("ticking");
        Iterator iterator = this.pendingTickListEntriesThisTick.iterator();

        while (iterator.hasNext()) {
            NextTickListEntry nextTickListEntryValue = (NextTickListEntry) iterator.next();
            iterator.remove();
            byte b0 = 0;
            if (this.isAreaLoaded(
                    nextTickListEntryValue.position.add(-b0, -b0, -b0),
                    nextTickListEntryValue.position.add(b0, b0, b0))) {
                BlockState blockState = this.getBlockState(nextTickListEntryValue.position);
                if (blockState.getBlock().getMaterial() != Material.AIR
                        && Block.isEqualTo(
                                blockState.getBlock(), nextTickListEntryValue.getBlock())) {
                    try {
                        blockState
                                .getBlock()
                                .updateTick(
                                        this,
                                        nextTickListEntryValue.position,
                                        blockState,
                                        this.rand);
                    } catch (Throwable throwable) {
                        CrashReport crashReport =
                                CrashReport.makeCrashReport(
                                        throwable, "Exception while ticking a block");
                        CrashReportCategory crashReportCategory =
                                crashReport.makeCategory("Block being ticked");
                        CrashReportCategory.addBlockInfo(
                                crashReportCategory, nextTickListEntryValue.position, blockState);
                        throw new ReportedException(crashReport);
                    }
                }

            } else {
                this.scheduleUpdate(
                        nextTickListEntryValue.position, nextTickListEntryValue.getBlock(), 0);
            }
        }

        this.theProfiler.endSection();
        this.pendingTickListEntriesThisTick.clear();
        return !this.pendingTickListEntriesTreeSet.isEmpty();
    }

    // Helper used only by this class.
    public static class AdjustPosToNearbyEntityPredicate
            implements com.google.common.base.Predicate {
        net.minecraft.world.WorldServer bFE;

        public boolean apply(java.lang.Object object) {
            return this.method_17c8fea8d6cc28fb72ce((net.minecraft.entity.LivingEntity) object);
        }

        public AdjustPosToNearbyEntityPredicate(net.minecraft.world.WorldServer bFEValue) {
            this.bFE = bFEValue;
        }

        public boolean method_17c8fea8d6cc28fb72ce(net.minecraft.entity.LivingEntity livingEntity) {
            return livingEntity != null
                    && livingEntity.isEntityAlive()
                    && this.bFE.isAgainstSky(livingEntity.getPosition());
        }
    }
}
