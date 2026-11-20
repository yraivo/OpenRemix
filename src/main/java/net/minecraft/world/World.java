package net.minecraft.world;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlabEnumBlockHalf;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairsEnumHalf;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkEnumCreateEntityType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.render.NoRenderModule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public abstract class World implements IBlockAccess {
    protected boolean spawnHostileMobs;
    protected IntHashMap entitiesById;
    protected boolean findingSpawnPoint;
    private List<TileEntity> tileEntitiesToBeRemoved;
    private Calendar theCalendar;
    protected Scoreboard worldScoreboard;
    public final List<Entity> loadedEntities = Lists.newArrayList();
    protected float prevThunderingStrength;
    private WorldBorder worldBorder;
    public List<TileEntity> loadedTileEntityList;
    int[] lightUpdateBlockList;

    private static int[] legacyWorldFlowMarker;
    public Random rand;
    private int skylightSubtracted;
    protected boolean spawnPeacefulMobs;
    protected IChunkProvider chunkProvider;
    protected final List<Entity> unloadedEntityList = Lists.newArrayList();
    private long cloudColour;
    public boolean isRemote;
    public List<Entity> weatherEffects;
    protected int updateLCG;

    private int lastLightningBolt;
    public Profiler theProfiler;
    public List<TileEntity> tickableTileEntities;
    protected int DIST_HASH_MAGIC;
    protected float rainingStrength;
    public WorldProvider provider;
    private List<TileEntity> addedTileEntityList;
    protected float prevRainingStrength;
    protected WorldInfo worldInfo;
    public List<PlayerEntity> playerEntities;
    protected MapStorage mapStorage;
    protected VillageCollection villageCollectionObj;
    protected List worldAccesses;
    protected ISaveHandler saveHandler;
    private boolean processingLoadedTiles;
    protected boolean scheduledUpdatesAreImmediate;
    protected Set activeChunkSet;
    protected float thunderingStrength;
    private int ambientTickCountdown;

    public boolean isAreaLoaded(BlockPos blockPos, BlockPos blockPosValue) {
        return this.isAreaLoaded(blockPos, blockPosValue, true);
    }

    protected void calculateInitialWeather() {
        if (this.worldInfo.isRaining()) {
            this.rainingStrength = 1.0F;
            if (this.worldInfo.isThundering()) {
                this.thunderingStrength = 1.0F;
            }
        }
    }

    public RayTraceResult rayTraceBlocks(
            Vec3 start,
            Vec3 end,
            boolean stopOnLiquid,
            boolean ignoreBlocksWithoutBox,
            boolean returnLastMiss) {
        getLegacyWorldFlowMarker();
        if (hasNaNCoordinate(start) || hasNaNCoordinate(end)) {
            return null;
        }

        int targetX = MathHelper.floor(end.x);
        int targetY = MathHelper.floor(end.y);
        int targetZ = MathHelper.floor(end.z);
        Vec3 cursor = start;
        int cellX = MathHelper.floor(cursor.x);
        int cellY = MathHelper.floor(cursor.y);
        int cellZ = MathHelper.floor(cursor.z);
        BlockPos position = new BlockPos(cellX, cellY, cellZ);
        BlockState state = this.getBlockState(position);
        Block block = state.getBlock();
        if ((!ignoreBlocksWithoutBox
                        || block.getCollisionBoundingBox(this, position, state) != null)
                && block.canCollideCheck(state, stopOnLiquid)) {
            RayTraceResult hit = block.collisionRayTrace(this, position, cursor, end);
            if (hit != null) {
                return hit;
            }
        }

        RayTraceResult lastMiss = null;
        for (int remaining = 200; remaining >= 0; remaining--) {
            if (hasNaNCoordinate(cursor)) {
                return null;
            }
            if (cellX == targetX && cellY == targetY && cellZ == targetZ) {
                break;
            }

            double nextX = cellX == targetX ? 999.0 : cellX + (targetX > cellX ? 1.0 : 0.0);
            double nextY = cellY == targetY ? 999.0 : cellY + (targetY > cellY ? 1.0 : 0.0);
            double nextZ = cellZ == targetZ ? 999.0 : cellZ + (targetZ > cellZ ? 1.0 : 0.0);
            double dx = end.x - cursor.x;
            double dy = end.y - cursor.y;
            double dz = end.z - cursor.z;
            double crossX = gridCrossingFraction(cellX != targetX, nextX - cursor.x, dx);
            double crossY = gridCrossingFraction(cellY != targetY, nextY - cursor.y, dy);
            double crossZ = gridCrossingFraction(cellZ != targetZ, nextZ - cursor.z, dz);

            // Use the 1.8 tie order (Z, then Y, then X) for edge and corner hits.
            Facing face;
            if (crossX < crossY && crossX < crossZ) {
                face = targetX > cellX ? Facing.WEST : Facing.EAST;
                cursor = new Vec3(nextX, cursor.y + dy * crossX, cursor.z + dz * crossX);
            } else if (crossY < crossZ) {
                face = targetY > cellY ? Facing.DOWN : Facing.UP;
                cursor = new Vec3(cursor.x + dx * crossY, nextY, cursor.z + dz * crossY);
            } else {
                face = targetZ > cellZ ? Facing.NORTH : Facing.SOUTH;
                cursor = new Vec3(cursor.x + dx * crossZ, cursor.y + dy * crossZ, nextZ);
            }

            cellX = MathHelper.floor(cursor.x) - (face == Facing.EAST ? 1 : 0);
            cellY = MathHelper.floor(cursor.y) - (face == Facing.UP ? 1 : 0);
            cellZ = MathHelper.floor(cursor.z) - (face == Facing.SOUTH ? 1 : 0);
            position = new BlockPos(cellX, cellY, cellZ);
            state = this.getBlockState(position);
            block = state.getBlock();
            if (ignoreBlocksWithoutBox
                    && block.getCollisionBoundingBox(this, position, state) == null) {
                continue;
            }

            if (block.canCollideCheck(state, stopOnLiquid)) {
                RayTraceResult hit = block.collisionRayTrace(this, position, cursor, end);
                if (hit != null) {
                    return hit;
                }
            } else {
                lastMiss = new RayTraceResult(RayHitType.MISS, cursor, face, position);
            }
        }

        return returnLastMiss ? lastMiss : null;
    }

    private static boolean hasNaNCoordinate(Vec3 position) {
        return Double.isNaN(position.x) || Double.isNaN(position.y) || Double.isNaN(position.z);
    }

    private static double gridCrossingFraction(
            boolean crossesCell, double deltaToBoundary, double travel) {
        double fraction = crossesCell ? deltaToBoundary / travel : 999.0;
        // Preserve the vanilla adjustment at a signed-zero crossing.
        return fraction == -0.0 ? -1.0E-4 : fraction;
    }

    protected void onEntityRemoved(Entity entity) {
        getLegacyWorldFlowMarker();

        int i = 0;

        while (i < this.worldAccesses.size()) {
            ((IWorldAccess) this.worldAccesses.get(i)).onEntityRemoved(entity);
            i++;
        }
    }

    public BlockPos getStrongholdPos(String text, BlockPos blockPos) {
        return this.getChunkProvider().getStrongholdGen(this, text, blockPos);
    }

    private boolean isWater(BlockPos blockPos) {
        return this.getBlockState(blockPos).getBlock().getMaterial() == Material.water;
    }

    public void forceBlockUpdateTick(Block block, BlockPos blockPos, Random random) {
        this.scheduledUpdatesAreImmediate = true;
        block.updateTick(this, blockPos, this.getBlockState(blockPos), random);
        this.scheduledUpdatesAreImmediate = false;
    }

    public IChunkProvider getChunkProvider() {
        return this.chunkProvider;
    }

    public void markBlockRangeForRenderUpdate(BlockPos blockPos, BlockPos blockPosValue) {
        this.markBlockRangeForRenderUpdate(
                blockPos.getX(),
                blockPos.getY(),
                blockPos.getZ(),
                blockPosValue.getX(),
                blockPosValue.getY(),
                blockPosValue.getZ());
    }

    public void joinEntityInSurroundings(Entity entity) {
        getLegacyWorldFlowMarker();

        int i = MathHelper.floor(entity.posX / 16.0);
        int j = MathHelper.floor(entity.posZ / 16.0);
        byte b0 = 2;

        int k = i - b0;

        while (k <= i + b0) {
            int l = j - b0;

            while (true) {
                if (l <= j + b0) {
                    this.getChunkFromChunkCoords(k, l);
                    l++;

                    continue;
                }

                k++;

                break;
            }
        }

        if (!this.loadedEntities.contains(entity)) {
            this.loadedEntities.add(entity);
        }
    }

    public void setEntityState(Entity entity, byte b0) {}

    public Explosion createExplosion(
            Entity entity,
            double p171_1,
            double p171_2,
            double p171_3,
            float p171_4,
            boolean p171_5) {
        return this.newExplosion(entity, p171_1, p171_2, p171_3, p171_4, false, p171_5);
    }

    public CrashReportCategory addWorldInfoToCrashReport(CrashReport crashReport) {
        CrashReportCategory crashReportCategory =
                crashReport.makeCategoryDepth("Affected level", 1);
        crashReportCategory.addCrashSection(
                "Level name", this.worldInfo == null ? "????" : this.worldInfo.getWorldName());
        crashReportCategory.addCrashSectionCallable(
                "All players", new WorldPlayersCrashDetail(this));
        crashReportCategory.addCrashSectionCallable(
                "Chunk stats", new WorldChunkProviderCrashDetail(this));

        try {
            this.worldInfo.addToCrashReport(crashReportCategory);
        } catch (Throwable throwable) {
            crashReportCategory.addCrashSectionThrowable("Level Data Unobtainable", throwable);
        }

        return crashReportCategory;
    }

    // region String decoding support

    // endregion

    public boolean canBlockFreezeNoWater(BlockPos blockPos) {
        return this.canBlockFreeze(blockPos, true);
    }

    public void markBlocksDirtyVertical(int i, int j, int k, int l) {
        getLegacyWorldFlowMarker();

        if (k > l) {
            int i1 = l;
            l = k;
            k = i1;
        }

        if (!this.provider.getHasNoSky()) {
            int j1 = k;

            while (j1 <= l) {
                this.checkLightFor(EnumSkyBlock.SKY, new BlockPos(i, j1, j));
                j1++;
            }
        }

        this.markBlockRangeForRenderUpdate(i, k, j, i, l, j);
    }

    public boolean isBlockinHighHumidity(BlockPos blockPos) {
        net.minecraft.world.biome.BiomeGenBase biomeGenForCoords =
                this.getBiomeGenForCoords(blockPos);
        return biomeGenForCoords.isHighHumidity();
    }

    public List<Entity> getLoadedEntities() {
        return this.loadedEntities;
    }

    public GameRules getGameRules() {
        return this.worldInfo.getGameRulesInstance();
    }

    public int calculateSkylightSubtracted(float f) {
        float celestialAngle = this.getCelestialAngle(f);
        float f2 = 1.0F - (MathHelper.cos(celestialAngle * (float) Math.PI * 2.0F) * 2.0F + 0.5F);
        f2 = MathHelper.clamp(f2, 0.0F, 1.0F);
        f2 = 1.0F - f2;
        f2 = (float) (f2 * (1.0 - this.getRainStrength(f) * 5.0F / 16.0));
        f2 = (float) (f2 * (1.0 - this.getWeightedThunderStrength(f) * 5.0F / 16.0));
        f2 = 1.0F - f2;
        return (int) (f2 * 11.0F);
    }

    public int getActualHeight() {
        return this.provider.getHasNoSky() ? 128 : 256;
    }

    public void makeFireworks(
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            CompoundTag compoundTag) {}

    public boolean isAgainstSky(BlockPos blockPos) {
        return this.getChunkFromBlockCoords(blockPos).canSeeSky(blockPos);
    }

    public WorldBorder getWorldBorder() {
        return this.worldBorder;
    }

    public Scoreboard getScoreboard() {
        return this.worldScoreboard;
    }

    public boolean setBlockToAir(BlockPos blockPos) {
        return this.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
    }

    public void playSoundEffect(double d0, double d1, double d2, String text, float f, float f1) {
        getLegacyWorldFlowMarker();

        int i = 0;

        while (i < this.worldAccesses.size()) {
            ((IWorldAccess) this.worldAccesses.get(i)).playSound(text, d0, d1, d2, f, f1);
            i++;
        }
    }

    private int getRawLight(BlockPos blockPos, EnumSkyBlock enumSkyBlock) {
        getLegacyWorldFlowMarker();

        if (enumSkyBlock == EnumSkyBlock.SKY && this.isAgainstSky(blockPos)) {
            return 15;
        }

        Block block = this.getBlockState(blockPos).getBlock();
        int i = enumSkyBlock == EnumSkyBlock.SKY ? 0 : block.getLightValue();
        int j = block.getLightOpacity();
        if (j >= 15 && block.getLightValue() > 0) {
            j = 1;
        }

        if (j < 1) {
            j = 1;
        }

        if (j >= 15) {
            return 0;
        }

        if (i >= 14) {
            return i;
        }

        for (Facing facing : Facing.values()) {
            BlockPos blockPosValue = blockPos.offset(facing);
            int k = this.getLightFor(enumSkyBlock, blockPosValue) - j;
            if (k > i) {
                i = k;
            }

            if (i >= 14) {
                return i;
            }
        }

        return i;
    }

    public boolean checkLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
        getLegacyWorldFlowMarker();

        if (!this.isAreaLoaded(blockPos, 17, false)) {
            return false;
        }

        int i;
        int j;
        int i1;
        int j1;
        int k1;
        {
            i = 0;
            j = 0;
            this.theProfiler.startSection("getBrightness");
            int k = this.getLightFor(enumSkyBlock, blockPos);
            int l = this.getRawLight(blockPos, enumSkyBlock);
            i1 = blockPos.getX();
            j1 = blockPos.getY();
            k1 = blockPos.getZ();
            if (l > k) {
                this.lightUpdateBlockList[j++] = 133152;

            } else {

                if (l < k) {
                    this.lightUpdateBlockList[j++] = 133152 | k << 18;

                    while (i < j) {
                        int l1 = this.lightUpdateBlockList[i++];
                        int i2 = (l1 & 63) - 32 + i1;
                        int j2 = (l1 >> 6 & 63) - 32 + j1;
                        int k2 = (l1 >> 12 & 63) - 32 + k1;
                        int l3 = l1 >> 18 & 15;
                        BlockPos blockPosValue = new BlockPos(i2, j2, k2);
                        int l2 = this.getLightFor(enumSkyBlock, blockPosValue);
                        if (l2 == l3) {
                            this.setLightFor(enumSkyBlock, blockPosValue, 0);
                            if (l3 > 0) {
                                int i3 = MathHelper.abs(i2 - i1);
                                int j3 = MathHelper.abs(j2 - j1);
                                int k3 = MathHelper.abs(k2 - k1);
                                if (i3 + j3 + k3 < 17) {
                                    for (Facing facing : Facing.values()) {
                                        int i4 = i2 + facing.getFrontOffsetX();
                                        int j4 = j2 + facing.getFrontOffsetY();
                                        int k4 = k2 + facing.getFrontOffsetZ();
                                        BlockPos blockPosValue2 = new BlockPos(i4, j4, k4);
                                        int l4 =
                                                Math.max(
                                                        1,
                                                        this.getBlockState(blockPosValue2)
                                                                .getBlock()
                                                                .getLightOpacity());
                                        l2 = this.getLightFor(enumSkyBlock, blockPosValue2);
                                        if (l2 == l3 - l4 && j < this.lightUpdateBlockList.length) {
                                            this.lightUpdateBlockList[j++] =
                                                    i4 - i1 + 32
                                                            | j4 - j1 + 32 << 6
                                                            | k4 - k1 + 32 << 12
                                                            | l3 - l4 << 18;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    i = 0;
                }
            }
        }

        this.theProfiler.endSection();
        this.theProfiler.startSection("checkedPosition < toCheckCount");

        while (i < j) {
            int i5 = this.lightUpdateBlockList[i++];
            int j5 = (i5 & 63) - 32 + i1;
            int k5 = (i5 >> 6 & 63) - 32 + j1;
            int l5 = (i5 >> 12 & 63) - 32 + k1;
            BlockPos blockPosValue3 = new BlockPos(j5, k5, l5);
            int lightFor = this.getLightFor(enumSkyBlock, blockPosValue3);
            int rawLight = this.getRawLight(blockPosValue3, enumSkyBlock);
            if (rawLight != lightFor) {
                this.setLightFor(enumSkyBlock, blockPosValue3, rawLight);
                if (rawLight > lightFor) {
                    int j6 = Math.abs(j5 - i1);
                    int k6 = Math.abs(k5 - j1);
                    int l6 = Math.abs(l5 - k1);
                    int j7 = j < this.lightUpdateBlockList.length - 6 ? 1 : 0;
                    if (j6 + k6 + l6 < 17 && j7 != 0) {
                        if (this.getLightFor(enumSkyBlock, blockPosValue3.offsetWest())
                                < rawLight) {
                            this.lightUpdateBlockList[j++] =
                                    j5 - 1 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
                        }

                        if (this.getLightFor(enumSkyBlock, blockPosValue3.offsetEast())
                                < rawLight) {
                            this.lightUpdateBlockList[j++] =
                                    j5 + 1 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
                        }

                        if (this.getLightFor(enumSkyBlock, blockPosValue3.offsetDown())
                                < rawLight) {
                            this.lightUpdateBlockList[j++] =
                                    j5 - i1 + 32 + (k5 - 1 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
                        }

                        if (this.getLightFor(enumSkyBlock, blockPosValue3.offsetUp()) < rawLight) {
                            this.lightUpdateBlockList[j++] =
                                    j5 - i1 + 32 + (k5 + 1 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
                        }

                        if (this.getLightFor(enumSkyBlock, blockPosValue3.offsetNorth())
                                < rawLight) {
                            this.lightUpdateBlockList[j++] =
                                    j5 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - 1 - k1 + 32 << 12);
                        }

                        if (this.getLightFor(enumSkyBlock, blockPosValue3.offsetSouth())
                                < rawLight) {
                            this.lightUpdateBlockList[j++] =
                                    j5 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 + 1 - k1 + 32 << 12);
                        }
                    }
                }
            }
        }

        this.theProfiler.endSection();
        return true;
    }

    public void setThunderStrength(float thunderingStrengthValue) {
        this.prevThunderingStrength = thunderingStrengthValue;
        this.thunderingStrength = thunderingStrengthValue;
    }

    protected void updateBlocks() {
        this.setActivePlayerChunksAndCheckLight();
    }

    public int getLight(BlockPos blockPos, boolean flag) {
        if (blockPos.getX() < -30000000
                || blockPos.getZ() < -30000000
                || blockPos.getX() >= 30000000
                || blockPos.getZ() >= 30000000) {
            return 15;
        }

        if (flag && this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
            int light = this.getLight(blockPos.offsetUp(), false);
            int lightValue = this.getLight(blockPos.offsetEast(), false);
            int j = this.getLight(blockPos.offsetWest(), false);
            int k = this.getLight(blockPos.offsetSouth(), false);
            int l = this.getLight(blockPos.offsetNorth(), false);
            if (lightValue > light) {
                light = lightValue;
            }

            if (j > light) {
                light = j;
            }

            if (k > light) {
                light = k;
            }

            if (l > light) {
                light = l;
            }

            return light;
        } else {
            if (blockPos.getY() < 0) {
                return 0;
            }

            if (blockPos.getY() >= 256) {
                blockPos = new BlockPos(blockPos.getX(), 255, blockPos.getZ());
            }

            Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
            return chunkFromBlockCoords.setLight(blockPos, this.skylightSubtracted);
        }
    }

    public float getCelestialAngle(float f) {
        return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), f);
    }

    public void setSpawnLocation(BlockPos blockPos) {
        this.worldInfo.setSpawn(blockPos);
    }

    public void playRecord(BlockPos blockPos, String text) {
        getLegacyWorldFlowMarker();

        int i = 0;

        while (i < this.worldAccesses.size()) {
            ((IWorldAccess) this.worldAccesses.get(i)).playRecord(text, blockPos);
            i++;
        }
    }

    public boolean canLightningStrike(BlockPos blockPos) {
        if (!this.isRaining()) {
            return false;
        }

        if (!this.isAgainstSky(blockPos)) {
            return false;
        }

        if (this.getPrecipitationHeight(blockPos).getY() > blockPos.getY()) {
            return false;
        }

        BiomeGenBase biomeGenForCoords = this.getBiomeGenForCoords(blockPos);
        return biomeGenForCoords.getEnableSnow()
                ? false
                : (this.canSnowAt(blockPos, false) ? false : biomeGenForCoords.canRain());
    }

    private void spawnParticle(
            int i,
            boolean flag,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int... aint) {
        getLegacyWorldFlowMarker();

        int j = 0;

        while (j < this.worldAccesses.size()) {
            ((IWorldAccess) this.worldAccesses.get(j))
                    .spawnParticle(i, flag, d0, d1, d2, d3, d4, d5, aint);
            j++;
        }
    }

    public MapStorage getMapStorage() {
        return this.mapStorage;
    }

    public void updateEntities() {
        int[] aint1 = getLegacyWorldFlowMarker();
        this.theProfiler.startSection("entities");
        int[] aint = aint1;
        this.theProfiler.startSection("global");
        int i = 0;

        while (i < this.weatherEffects.size()) {
            Entity entity = this.weatherEffects.get(i);

            try {
                entity.ticksExisted++;
                entity.onUpdate();
            } catch (Throwable throwable2) {
                CrashReport crashReport = CrashReport.makeCrashReport(throwable2, "Ticking entity");
                CrashReportCategory crashReportCategory =
                        crashReport.makeCategory("Entity being ticked");
                if (entity == null) {
                    crashReportCategory.addCrashSection("Entity", "~~NULL~~");

                    throw new ReportedException(crashReport);
                }

                entity.addEntityCrashInfo(crashReportCategory);
                throw new ReportedException(crashReport);
            }

            if (entity.isDead) {
                this.weatherEffects.remove(i--);
            }

            i++;
        }

        this.theProfiler.endStartSection("remove");
        this.loadedEntities.removeAll(this.unloadedEntityList);
        i = 0;

        while (i < this.unloadedEntityList.size()) {
            Entity entityValue = this.unloadedEntityList.get(i);
            int j = entityValue.chunkCoordX;
            int k = entityValue.chunkCoordZ;
            if (entityValue.addedToChunk && this.isChunkLoaded(j, k, true)) {
                this.getChunkFromChunkCoords(j, k).removeEntity(entityValue);
            }

            i++;
        }

        i = 0;

        while (i < this.unloadedEntityList.size()) {
            this.onEntityRemoved(this.unloadedEntityList.get(i));
            i++;
        }

        this.unloadedEntityList.clear();
        this.theProfiler.endStartSection("regular");
        i = 0;

        while (i < this.loadedEntities.size()) {
            Entity entity = this.loadedEntities.get(i);
            Entity mount = entity.ridingEntity;
            if (mount != null) {
                if (!mount.isDead && mount.riddenByEntity == entity && aint != null) {
                    i++;
                    continue;
                }
                mount.riddenByEntity = null;
                entity.ridingEntity = null;
            }

            this.theProfiler.startSection("tick");
            if (!entity.isDead) {
                try {
                    this.updateEntity(entity);
                } catch (Throwable failure) {
                    CrashReport report = CrashReport.makeCrashReport(failure, "Ticking entity");
                    CrashReportCategory details = report.makeCategory("Entity being ticked");
                    entity.addEntityCrashInfo(details);
                    throw new ReportedException(report);
                }
            }

            this.theProfiler.endSection();
            this.theProfiler.startSection("remove");
            if (entity.isDead) {
                int chunkX = entity.chunkCoordX;
                int chunkCoordZ = entity.chunkCoordZ;
                if (entity.addedToChunk && this.isChunkLoaded(chunkX, chunkCoordZ, true)) {
                    this.getChunkFromChunkCoords(chunkX, chunkCoordZ).removeEntity(entity);
                }

                this.loadedEntities.remove(i--);
                this.onEntityRemoved(entity);
            }

            this.theProfiler.endSection();
            i++;
        }

        this.theProfiler.endStartSection("blockEntities");
        this.processingLoadedTiles = true;
        Iterator iterator = this.tickableTileEntities.iterator();

        while (iterator.hasNext()) {
            TileEntity tileEntity = (TileEntity) iterator.next();
            if (!tileEntity.isInvalid() && tileEntity.hasWorldObj()) {
                BlockPos blockPos = tileEntity.getPos();
                if (this.isBlockLoaded(blockPos) && this.worldBorder.contains(blockPos)) {
                    try {
                        ((IUpdatePlayerListBox) tileEntity).update();
                    } catch (Throwable throwable) {
                        CrashReport crashReportValue2 =
                                CrashReport.makeCrashReport(throwable, "Ticking block entity");
                        CrashReportCategory crashReportCategoryValue2 =
                                crashReportValue2.makeCategory("Block entity being ticked");
                        tileEntity.addInfoToCrashReport(crashReportCategoryValue2);
                        throw new ReportedException(crashReportValue2);
                    }
                }
            }

            if (tileEntity.isInvalid()) {
                iterator.remove();
                this.loadedTileEntityList.remove(tileEntity);
                if (this.isBlockLoaded(tileEntity.getPos())) {
                    this.getChunkFromBlockCoords(tileEntity.getPos())
                            .removeTileEntity(tileEntity.getPos());
                }
            }
        }

        this.processingLoadedTiles = false;
        if (!this.tileEntitiesToBeRemoved.isEmpty()) {
            this.tickableTileEntities.removeAll(this.tileEntitiesToBeRemoved);
            this.loadedTileEntityList.removeAll(this.tileEntitiesToBeRemoved);
            this.tileEntitiesToBeRemoved.clear();
        }

        this.theProfiler.endStartSection("pendingBlockEntities");
        if (!this.addedTileEntityList.isEmpty()) {
            int j1 = 0;

            while (j1 < this.addedTileEntityList.size()) {
                TileEntity tileEntityValue = this.addedTileEntityList.get(j1);
                if (!tileEntityValue.isInvalid()) {
                    if (!this.loadedTileEntityList.contains(tileEntityValue)) {
                        this.addTileEntity(tileEntityValue);
                    }

                    if (this.isBlockLoaded(tileEntityValue.getPos())) {
                        this.getChunkFromBlockCoords(tileEntityValue.getPos())
                                .addTileEntity(tileEntityValue.getPos(), tileEntityValue);
                    }

                    this.markBlockForUpdate(tileEntityValue.getPos());
                }

                j1++;
            }

            this.addedTileEntityList.clear();
        }

        this.theProfiler.endSection();
        this.theProfiler.endSection();
    }

    public RayTraceResult rayTraceBlocks(Vec3 vec3, Vec3 vec3Value) {
        return this.rayTraceBlocks(vec3, vec3Value, false, false, false);
    }

    protected void updateWeather() {
        getLegacyWorldFlowMarker();

        if (!this.provider.getHasNoSky() && !this.isRemote) {
            int cleanWeatherTime = this.worldInfo.getCleanWeatherTime();
            if (cleanWeatherTime > 0) {
                this.worldInfo.setCleanWeatherTime(--cleanWeatherTime);
                this.worldInfo.setThunderTime(this.worldInfo.isThundering() ? 1 : 2);
                this.worldInfo.setRainTime(this.worldInfo.isRaining() ? 1 : 2);
            }

            {
                int j = this.worldInfo.getThunderTime();
                if (j <= 0) {
                    if (this.worldInfo.isThundering()) {
                        this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);

                    } else {

                        this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
                    }
                } else {

                    this.worldInfo.setThunderTime(--j);
                    if (j <= 0) {
                        this.worldInfo.setThundering(!this.worldInfo.isThundering());
                    }
                }
            }

            {
                this.prevThunderingStrength = this.thunderingStrength;
                if (this.worldInfo.isThundering()) {
                    this.thunderingStrength = (float) (this.thunderingStrength + 0.01);

                } else {

                    this.thunderingStrength = (float) (this.thunderingStrength - 0.01);
                }
            }

            {
                this.thunderingStrength = MathHelper.clamp(this.thunderingStrength, 0.0F, 1.0F);
                int k = this.worldInfo.getRainTime();
                if (k <= 0) {
                    if (this.worldInfo.isRaining()) {
                        this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);

                    } else {

                        this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
                    }
                } else {

                    this.worldInfo.setRainTime(--k);
                    if (k <= 0) {
                        this.worldInfo.setRaining(!this.worldInfo.isRaining());
                    }
                }
            }

            {
                this.prevRainingStrength = this.rainingStrength;
                if (this.worldInfo.isRaining()) {
                    this.rainingStrength = (float) (this.rainingStrength + 0.01);

                } else {

                    this.rainingStrength = (float) (this.rainingStrength - 0.01);
                }
            }

            this.rainingStrength = MathHelper.clamp(this.rainingStrength, 0.0F, 1.0F);
        }
    }

    public boolean chunkExists(int i, int j) {
        BlockPos blockPos = this.getSpawnPoint();
        int k = i * 16 + 8 - blockPos.getX();
        int l = j * 16 + 8 - blockPos.getZ();
        short short1 = 128;
        return k >= -short1 && k <= short1 && l >= -short1 && l <= short1;
    }

    public boolean isSidePowered(BlockPos blockPos, Facing facing) {
        try {
            if (this.getRedstonePower(blockPos, facing) > 0) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        return false;
    }

    public int getMoonPhase() {
        return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
    }

    public boolean canBlockFreezeWater(BlockPos blockPos) {
        return this.canBlockFreeze(blockPos, false);
    }

    public void notifyNeighborsOfStateChange(BlockPos blockPos, Block block) {
        this.notifyBlockOfStateChange(blockPos.offsetWest(), block);
        this.notifyBlockOfStateChange(blockPos.offsetEast(), block);
        this.notifyBlockOfStateChange(blockPos.offsetDown(), block);
        this.notifyBlockOfStateChange(blockPos.offsetUp(), block);
        this.notifyBlockOfStateChange(blockPos.offsetNorth(), block);
        this.notifyBlockOfStateChange(blockPos.offsetSouth(), block);
    }

    public void setItemData(String text, WorldSavedData worldSavedData) {
        this.mapStorage.setData(text, worldSavedData);
    }

    public void updateEntityWithOptionalForce(Entity entity, boolean flag) {
        getLegacyWorldFlowMarker();

        int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.posZ);
        byte b0 = 32;

        if (!flag || this.isAreaLoaded(i - b0, 0, j - b0, i + b0, 0, j + b0, true)) {
            entity.lastTickPosX = entity.posX;
            entity.lastTickPosY = entity.posY;
            entity.lastTickPosZ = entity.posZ;
            entity.prevRotationYaw = entity.rotationYaw;
            entity.prevRotationPitch = entity.rotationPitch;
            if (flag && entity.addedToChunk) {
                entity.ticksExisted++;
                if (entity.ridingEntity != null) {
                    entity.updateRidden();

                } else {

                    entity.onUpdate();
                }
            }

            this.theProfiler.startSection("chunkCheck");
            if (Double.isNaN(entity.posX) || Double.isInfinite(entity.posX)) {
                entity.posX = entity.lastTickPosX;
            }

            if (Double.isNaN(entity.posY) || Double.isInfinite(entity.posY)) {
                entity.posY = entity.lastTickPosY;
            }

            if (Double.isNaN(entity.posZ) || Double.isInfinite(entity.posZ)) {
                entity.posZ = entity.lastTickPosZ;
            }

            if (Double.isNaN(entity.rotationPitch) || Double.isInfinite(entity.rotationPitch)) {
                entity.rotationPitch = entity.prevRotationPitch;
            }

            if (Double.isNaN(entity.rotationYaw) || Double.isInfinite(entity.rotationYaw)) {
                entity.rotationYaw = entity.prevRotationYaw;
            }

            int k = MathHelper.floor(entity.posX / 16.0);
            int l = MathHelper.floor(entity.posY / 16.0);
            int i1 = MathHelper.floor(entity.posZ / 16.0);
            if (!entity.addedToChunk
                    || entity.chunkCoordX != k
                    || entity.chunkCoordY != l
                    || entity.chunkCoordZ != i1) {
                if (entity.addedToChunk
                        && this.isChunkLoaded(entity.chunkCoordX, entity.chunkCoordZ, true)) {
                    this.getChunkFromChunkCoords(entity.chunkCoordX, entity.chunkCoordZ)
                            .removeEntityAtIndex(entity, entity.chunkCoordY);
                }

                if (this.isChunkLoaded(k, i1, true)) {
                    entity.addedToChunk = true;
                    this.getChunkFromChunkCoords(k, i1).addEntity(entity);

                } else {

                    entity.addedToChunk = false;
                }
            }

            this.theProfiler.endSection();
            if (flag && entity.addedToChunk && entity.riddenByEntity != null) {
                if (!entity.riddenByEntity.isDead && entity.riddenByEntity.ridingEntity == entity) {
                    this.updateEntity(entity.riddenByEntity);

                    return;
                }

                entity.riddenByEntity.ridingEntity = null;
                entity.riddenByEntity = null;
            }
        }
    }

    public void removeTileEntity(BlockPos blockPos) {
        net.minecraft.world.World.getLegacyWorldFlowMarker();

        net.minecraft.tileentity.TileEntity tileEntity = this.getTileEntity(blockPos);

        if (tileEntity != null && this.processingLoadedTiles) {
            tileEntity.invalidate();
            ((java.util.List) (this.addedTileEntityList)).remove(((java.lang.Object) (tileEntity)));

            return;
        }

        if (tileEntity != null) {
            ((java.util.List) (this.addedTileEntityList)).remove(((java.lang.Object) (tileEntity)));
            ((java.util.List) (this.loadedTileEntityList))
                    .remove(((java.lang.Object) (tileEntity)));
            ((java.util.List) (this.tickableTileEntities))
                    .remove(((java.lang.Object) (tileEntity)));
        }

        this.getChunkFromBlockCoords(blockPos).removeTileEntity(blockPos);
    }

    public boolean setBlockState(BlockPos blockPos, BlockState blockState, int i) {
        if (!this.isValid(blockPos)) {
            return false;
        }

        if (!this.isRemote && this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            return false;
        }

        Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
        Block block = blockState.getBlock();
        BlockState blockStateValue = chunkFromBlockCoords.setBlockState(blockPos, blockState);
        if (blockStateValue == null) {
            return false;
        }

        Block blockValue = blockStateValue.getBlock();
        if (block.getLightOpacity() != blockValue.getLightOpacity()
                || block.getLightValue() != blockValue.getLightValue()) {
            this.theProfiler.startSection("checkLight");
            this.checkLight(blockPos);
            this.theProfiler.endSection();
        }

        if ((i & 2) != 0
                && (!this.isRemote || (i & 4) == 0)
                && chunkFromBlockCoords.isPopulated()) {
            this.markBlockForUpdate(blockPos);
        }

        if (!this.isRemote && (i & 1) != 0) {
            this.notifyNeighborsRespectDebug(blockPos, blockStateValue.getBlock());
            if (block.hasComparatorInputOverride()) {
                this.updateComparatorOutputLevel(blockPos, block);
            }
        }

        return true;
    }

    // region String decoding support

    // endregion

    public void notifyLightSet(BlockPos blockPos) {
        getLegacyWorldFlowMarker();

        int i = 0;

        while (i < this.worldAccesses.size()) {
            ((IWorldAccess) this.worldAccesses.get(i)).notifyLightSet(blockPos);
            i++;
        }
    }

    public long getSeed() {
        return this.worldInfo.getSeed();
    }

    public void updateBlockTick(BlockPos blockPos, Block block, int i, int j) {}

    public boolean isBlockNormalCube(BlockPos blockPos, boolean flag) {
        if (!this.isValid(blockPos)) {
            return flag;
        }

        Chunk chunk = this.chunkProvider.provideChunk(blockPos);
        if (chunk.isEmpty()) {
            return flag;
        }

        Block block = this.getBlockState(blockPos).getBlock();
        return block.getMaterial().isOpaque() && block.isFullCube();
    }

    public double getHorizonHeight() {
        return this.worldInfo.getTerrainType() == WorldType.FLAT ? 0.0 : 63.0;
    }

    public void setWorldTime(long i) {
        this.worldInfo.setWorldTime(i);
    }

    public BiomeGenBase getBiomeGenForCoords(BlockPos blockPos) {
        if (this.isBlockLoaded(blockPos)) {
            Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);

            try {
                return chunkFromBlockCoords.getBiome(
                        blockPos, this.provider.getWorldChunkManager());
            } catch (Throwable throwable) {
                CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Getting biome");
                CrashReportCategory crashReportCategory =
                        crashReport.makeCategory("Coordinates of biome request");
                crashReportCategory.addCrashSectionCallable(
                        "Location", new WorldBiomeRequestCrashLocation(this, blockPos));
                throw new ReportedException(crashReport);
            }
        } else {
            return this.provider
                    .getWorldChunkManager()
                    .getBiomeGenerator(blockPos, BiomeGenBase.plains);
        }
    }

    public boolean handleMaterialAcceleration(
            BoundingBox boundingBox, Material material, Entity entity) {
        getLegacyWorldFlowMarker();

        int i = MathHelper.floor(boundingBox.minX);
        int j = MathHelper.floor(boundingBox.maxX + 1.0);
        int k = MathHelper.floor(boundingBox.minY);
        int l = MathHelper.floor(boundingBox.maxY + 1.0);

        int i1 = MathHelper.floor(boundingBox.minZ);
        int j1 = MathHelper.floor(boundingBox.maxZ + 1.0);
        if (!this.isAreaLoaded(i, k, i1, j, l, j1, true)) {
            return false;
        }

        byte b0 = 0;
        Vec3 vec3 = new Vec3(0.0, 0.0, 0.0);
        int k1 = i;

        while (k1 < j) {
            int l1 = k;

            while (true) {
                if (l1 < l) {
                    int i2 = i1;

                    while (i2 < j1) {
                        BlockPos blockPos = new BlockPos(k1, l1, i2);
                        BlockState blockState = this.getBlockState(blockPos);
                        Block block = blockState.getBlock();
                        if (block.getMaterial() == material) {
                            double d0 =
                                    l1
                                            + 1
                                            - BlockLiquid.getLiquidHeightPercent(
                                                    (Integer)
                                                            blockState.getValue(BlockLiquid.LEVEL));
                            if (l >= d0) {
                                b0 = 1;
                                vec3 = block.modifyAcceleration(this, blockPos, entity, vec3);
                            }
                        }

                        i2++;
                    }

                    l1++;

                    continue;
                }

                k1++;

                break;
            }
        }

        if (vec3.lengthVector() > 0.0 && entity.isPushedByWater()) {
            vec3 = vec3.normalize();
            double d1 = 0.014;
            entity.motionX = entity.motionX + vec3.x * d1;
            entity.motionY = entity.motionY + vec3.y * d1;
            entity.motionZ = entity.motionZ + vec3.z * d1;
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    public int getChunksLowestHorizon(int p171_0, int p171_1) {
        if (p171_0 >= -30000000 && p171_1 >= -30000000 && p171_0 < 30000000 && p171_1 < 30000000) {
            if (!this.isChunkLoaded(p171_0 >> 4, p171_1 >> 4, true)) {
                return 0;
            }

            net.minecraft.world.chunk.Chunk chunkFromChunkCoords =
                    this.getChunkFromChunkCoords(p171_0 >> 4, p171_1 >> 4);
            return chunkFromChunkCoords.getLowestHeight();
        } else {
            return 64;
        }
    }

    public int getHeight() {
        return 256;
    }

    public int countEntities(Class oclass) {
        getLegacyWorldFlowMarker();

        int i = 0;

        for (java.util.Iterator<?> __recoveryIterator171_82831 = (this.loadedEntities).iterator();
                (__recoveryIterator171_82831).hasNext(); ) {
            Entity entity = (Entity) (__recoveryIterator171_82831).next();
            if ((!(entity instanceof EntityLiving)
                            || !((EntityLiving) entity).isNoDespawnRequired())
                    && oclass.isAssignableFrom(entity.getClass())) {
                i++;
            }
        }

        return i;
    }

    public float getRainStrength(float p171_0) {
        return this.prevRainingStrength
                + (this.rainingStrength - this.prevRainingStrength) * p171_0;
    }

    public int getRedstonePower(BlockPos blockPos, Facing facing) {
        net.minecraft.block.state.BlockState blockState = this.getBlockState(blockPos);
        net.minecraft.block.Block block = blockState.getBlock();

        try {
            if (block.isNormalCube()) {
                return this.getStrongPower(blockPos);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        return block.isProvidingWeakPower(this, blockPos, blockState, facing);
    }

    public void setLastLightningBolt(int lastLightningBoltValue) {
        this.lastLightningBolt = lastLightningBoltValue;
    }

    public boolean checkLight(BlockPos blockPos) {
        byte b0 = 0;
        if (!this.provider.getHasNoSky()) {
            b0 |=
                    remix.compat.compiler.JvmBooleanSlots.toInt(
                            this.checkLightFor(EnumSkyBlock.SKY, blockPos));
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(
                (b0
                        | remix.compat.compiler.JvmBooleanSlots.toInt(
                                this.checkLightFor(EnumSkyBlock.BLOCK, blockPos))));
    }

    public boolean checkBlockCollision(BoundingBox boundingBox) {
        int i = MathHelper.floor(boundingBox.minX);
        getLegacyWorldFlowMarker();

        int j = MathHelper.floor(boundingBox.maxX);

        int k = MathHelper.floor(boundingBox.minY);
        int l = MathHelper.floor(boundingBox.maxY);
        int i1 = MathHelper.floor(boundingBox.minZ);
        int j1 = MathHelper.floor(boundingBox.maxZ);
        int k1 = i;

        while (k1 <= j) {
            int l1 = k;

            while (true) {
                if (l1 <= l) {
                    int i2 = i1;

                    while (i2 <= j1) {
                        Block block = this.getBlockState(new BlockPos(k1, l1, i2)).getBlock();
                        if (block.getMaterial() != Material.AIR) {
                            return true;
                        }

                        i2++;
                    }

                    l1++;

                    continue;
                }

                k1++;

                break;
            }
        }

        return false;
    }

    public void setSkylightSubtracted(int skylightSubtractedValue) {
        this.skylightSubtracted = skylightSubtractedValue;
    }

    public float getLightBrightness(BlockPos blockPos) {
        return this.provider.getLightBrightnessTable()[this.getLightFromNeighbors(blockPos)];
    }

    public Explosion newExplosion(
            Entity entity,
            double p171_1,
            double p171_2,
            double p171_3,
            float p171_4,
            boolean p171_5,
            boolean p171_6) {
        net.minecraft.world.Explosion explosion =
                new net.minecraft.world.Explosion(
                        this, entity, p171_1, p171_2, p171_3, p171_4, p171_5, p171_6);
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        return explosion;
    }

    public void playSound(
            double d0, double d1, double d2, String text, float f, float f1, boolean flag) {}

    public void markTileEntityForRemoval(TileEntity tileEntity) {
        this.tileEntitiesToBeRemoved.add(tileEntity);
    }

    public WorldSavedData loadItemData(Class type, String text) {
        return this.mapStorage.loadData(type, text);
    }

    public void updateAllPlayersSleepingFlag() {}

    public void loadEntities(Collection collection) {
        getLegacyWorldFlowMarker();

        this.loadedEntities.addAll(collection);
        Iterator iterator = collection.iterator();

        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();
            this.onEntityAdded(entity);
        }
    }

    public void setRainStrength(float rainingStrengthValue) {
        this.prevRainingStrength = rainingStrengthValue;
        this.rainingStrength = rainingStrengthValue;
    }

    public void spawnParticle(
            EnumParticleTypes enumParticleTypes,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int... aint) {
        this.spawnParticle(
                enumParticleTypes.getParticleID(),
                enumParticleTypes.getShouldIgnoreRange(),
                d0,
                d1,
                d2,
                d3,
                d4,
                d5,
                aint);
    }

    public int getLightFromNeighbors(BlockPos blockPos) {
        return this.getLight(blockPos, true);
    }

    public PlayerEntity getPlayerEntityByUUID(UUID uuid) {
        net.minecraft.world.World.getLegacyWorldFlowMarker();

        int i = 0;

        while (i < this.playerEntities.size()) {
            net.minecraft.entity.player.PlayerEntity playerEntity =
                    (net.minecraft.entity.player.PlayerEntity) this.playerEntities.get(i);

            try {
                if (uuid.equals(playerEntity.getUniqueId())) {
                    return playerEntity;
                }
            } catch (net.minecraft.util.ReportedException reportedException) {
                throw (reportedException);
            }

            i++;
        }

        return null;
    }

    public void sendQuittingDisconnectingPacket() {}

    public static int[] getLegacyWorldFlowMarker() {
        return net.minecraft.world.World.legacyWorldFlowMarker;
    }

    public boolean checkNoEntityCollision(BoundingBox boundingBox) {
        return this.checkNoEntityCollision(boundingBox, (net.minecraft.entity.Entity) null);
    }

    public long getWorldTime() {
        return this.worldInfo.getWorldTime();
    }

    public List getPendingBlockUpdates(Chunk chunk, boolean flag) {
        return null;
    }

    public boolean isBlockFullCube(BlockPos blockPos) {
        net.minecraft.block.state.BlockState blockState = this.getBlockState(blockPos);
        net.minecraft.util.BoundingBox boundingBox =
                blockState.getBlock().getCollisionBoundingBox(this, blockPos, blockState);
        return (boundingBox != null && boundingBox.getAverageEdgeLength() >= 1.0);
    }

    public VillageCollection getVillageCollection() {
        return this.villageCollectionObj;
    }

    public void addWorldAccess(IWorldAccess iWorldAccess) {
        this.worldAccesses.add(iWorldAccess);
    }

    public boolean isInsideBorder(WorldBorder worldBorderValue, Entity entity) {
        double d0 = worldBorderValue.minX();
        getLegacyWorldFlowMarker();

        double d1 = worldBorderValue.minZ();

        double d2 = worldBorderValue.maxX();
        double d3 = worldBorderValue.maxZ();
        if (entity.isOutsideBorder()) {
            d0++;
            d1++;
            d2--;
            d3--;

            return entity.posX > d0 && entity.posX < d2 && entity.posZ > d1 && entity.posZ < d3;
        }

        d0--;
        d1--;
        d2++;
        d3++;
        return entity.posX > d0 && entity.posX < d2 && entity.posZ > d1 && entity.posZ < d3;
    }

    public WorldChunkManager getWorldChunkManager() {
        return this.provider.getWorldChunkManager();
    }

    public boolean spawnEntityInWorld(Entity entity) {
        int i = MathHelper.floor(entity.posX / 16.0);
        int j = MathHelper.floor(entity.posZ / 16.0);
        byte b0 = remix.compat.compiler.JvmBooleanSlots.toByte(entity.forceSpawn);
        if (entity instanceof PlayerEntity) {
            b0 = 1;
        }

        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        NoRenderModule noRender = ClientServices.moduleManager.noRender;
        int k =
                noRender != null
                                && noRender.isEnabled()
                                && noRender.tntSetting.asBoolean()
                                && entity instanceof EntityTNTPrimed
                        ? 1
                        : 0;
        if ((b0 != 0 || this.isChunkLoaded(i, j, true)) && k == 0) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) entity;
                this.playerEntities.add(playerEntity);
                this.updateAllPlayersSleepingFlag();
            }

            this.getChunkFromChunkCoords(i, j).addEntity(entity);
            this.loadedEntities.add(entity);
            this.onEntityAdded(entity);
            return true;
        } else {
            return false;
        }
    }

    public void setAllowedSpawnTypes(boolean spawnHostileMobsValue, boolean aWfValue) {
        this.spawnHostileMobs = spawnHostileMobsValue;
        this.spawnPeacefulMobs = aWfValue;
    }

    public boolean isAABBInMaterial(BoundingBox boundingBox, Material material) {
        getLegacyWorldFlowMarker();

        int i = MathHelper.floor(boundingBox.minX);
        int j = MathHelper.floor(boundingBox.maxX + 1.0);
        int k = MathHelper.floor(boundingBox.minY);
        int l = MathHelper.floor(boundingBox.maxY + 1.0);
        int i1 = MathHelper.floor(boundingBox.minZ);
        int j1 = MathHelper.floor(boundingBox.maxZ + 1.0);
        int k1 = i;

        while (k1 < j) {
            int l1 = k;

            while (true) {
                if (l1 < l) {
                    int i2 = i1;

                    while (i2 < j1) {
                        BlockPos blockPos = new BlockPos(k1, l1, i2);
                        BlockState blockState = this.getBlockState(blockPos);
                        Block block = blockState.getBlock();
                        if (block.getMaterial() == material) {
                            int j2 = (Integer) blockState.getValue(BlockLiquid.LEVEL);
                            double d0 = l1 + 1;
                            if (j2 < 8) {
                                d0 = l1 + 1 - j2 / 8.0;
                            }

                            if (d0 >= boundingBox.minY) {
                                return true;
                            }
                        }

                        i2++;
                    }

                    l1++;

                    continue;
                }

                k1++;

                break;
            }
        }

        return false;
    }

    public float getWeightedThunderStrength(float p171_0) {
        return (this.prevThunderingStrength
                        + (this.thunderingStrength - this.prevThunderingStrength) * p171_0)
                * this.getRainStrength(p171_0);
    }

    protected void setActivePlayerChunksAndCheckLight() {
        this.activeChunkSet.clear();
        getLegacyWorldFlowMarker();

        this.theProfiler.startSection("buildList");
        int i = 0;

        while (i < this.playerEntities.size()) {
            PlayerEntity playerEntity = this.playerEntities.get(i);
            int j = MathHelper.floor(playerEntity.posX / 16.0);
            int k = MathHelper.floor(playerEntity.posZ / 16.0);
            int l = this.getRenderDistanceChunks();
            int i1 = -l;

            while (true) {
                if (i1 <= l) {
                    int j1 = -l;

                    while (j1 <= l) {
                        this.activeChunkSet.add(new ChunkCoordIntPair(i1 + j, j1 + k));
                        j1++;
                    }

                    i1++;

                    continue;
                }

                i++;

                break;
            }
        }

        this.theProfiler.endSection();
        if (this.ambientTickCountdown > 0) {
            this.ambientTickCountdown--;
        }

        this.theProfiler.startSection("playerCheckLight");
        if (!this.playerEntities.isEmpty()) {
            i = this.rand.nextInt(this.playerEntities.size());
            PlayerEntity playerEntityValue = this.playerEntities.get(i);
            int k1 = MathHelper.floor(playerEntityValue.posX) + this.rand.nextInt(11) - 5;
            int l1 = MathHelper.floor(playerEntityValue.posY) + this.rand.nextInt(11) - 5;
            int i2 = MathHelper.floor(playerEntityValue.posZ) + this.rand.nextInt(11) - 5;
            this.checkLight(new BlockPos(k1, l1, i2));
        }

        this.theProfiler.endSection();
    }

    public boolean isBlockLoaded(BlockPos blockPos, boolean flag) {
        return !this.isValid(blockPos)
                ? false
                : this.isChunkLoaded(blockPos.getX() >> 4, blockPos.getZ() >> 4, flag);
    }

    protected abstract IChunkProvider createChunkProvider();

    public boolean setBlockState(BlockPos blockPos, BlockState blockState) {
        return this.setBlockState(blockPos, blockState, 3);
    }

    public boolean tickUpdates(boolean flag) {
        return false;
    }

    public ISaveHandler getSaveHandler() {
        return this.saveHandler;
    }

    public void removePlayerEntityDangerously(Entity entity) {
        entity.setDead();
        if (entity instanceof net.minecraft.entity.player.PlayerEntity) {
            ((java.util.List) (this.playerEntities)).remove(((java.lang.Object) (entity)));
            this.updateAllPlayersSleepingFlag();
        }

        int chunkCoordX = entity.chunkCoordX;
        int j = entity.chunkCoordZ;
        if (entity.addedToChunk && this.isChunkLoaded(chunkCoordX, j, true)) {
            this.getChunkFromChunkCoords(chunkCoordX, j).removeEntity(entity);
        }

        ((java.util.List) (this.loadedEntities)).remove(((java.lang.Object) (entity)));
        this.onEntityRemoved(entity);
    }

    public Vec3 getCloudColour(float f) {
        float celestialAngle = this.getCelestialAngle(f);
        float f2 = MathHelper.cos(celestialAngle * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
        f2 = MathHelper.clamp(f2, 0.0F, 1.0F);
        long i = this.cloudColour >> 16;

        float f3 = (float) (i & 255L) / 255.0F;
        i = this.cloudColour >> 8;

        float f4 = (float) (i & 255L) / 255.0F;

        float f5 = (float) (this.cloudColour & 255L) / 255.0F;
        float rainStrength = this.getRainStrength(f);
        if (rainStrength > 0.0F) {
            float f7 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F;
            float f8 = 1.0F - rainStrength * 0.95F;
            f3 = f3 * f8 + f7 * (1.0F - f8);
            f4 = f4 * f8 + f7 * (1.0F - f8);
            f5 = f5 * f8 + f7 * (1.0F - f8);
        }

        f3 *= f2 * 0.9F + 0.1F;
        f4 *= f2 * 0.9F + 0.1F;
        f5 *= f2 * 0.85F + 0.15F;
        float weightedThunderStrength = this.getWeightedThunderStrength(f);
        if (weightedThunderStrength > 0.0F) {
            float f11 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
            float f9 = 1.0F - weightedThunderStrength * 0.95F;
            f3 = f3 * f9 + f11 * (1.0F - f9);
            f4 = f4 * f9 + f11 * (1.0F - f9);
            f5 = f5 * f9 + f11 * (1.0F - f9);
        }

        return new Vec3(f3, f4, f5);
    }

    public EnumDifficulty getDifficulty() {
        return this.getWorldInfo().getDifficulty();
    }

    public void scheduleUpdate(BlockPos blockPos, Block block, int i) {}

    public boolean isAreaLoaded(BlockPos blockPos, BlockPos blockPosValue, boolean flag) {
        return this.isAreaLoaded(
                blockPos.getX(),
                blockPos.getY(),
                blockPos.getZ(),
                blockPosValue.getX(),
                blockPosValue.getY(),
                blockPosValue.getZ(),
                flag);
    }

    public void playAuxSFXAtEntity(PlayerEntity playerEntity, int i, BlockPos blockPos, int j) {
        getLegacyWorldFlowMarker();

        try {
            int k = 0;

            while (k < this.worldAccesses.size()) {
                ((IWorldAccess) this.worldAccesses.get(k)).playAuxSFX(playerEntity, i, blockPos, j);
                k++;
            }
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Playing level event");
            CrashReportCategory crashReportCategory =
                    crashReport.makeCategory("Level event being played");
            crashReportCategory.addCrashSection(
                    "Block coordinates", CrashReportCategory.getCoordinateInfo(blockPos));
            crashReportCategory.addCrashSection("Event source", playerEntity);
            crashReportCategory.addCrashSection("Event type", i);
            crashReportCategory.addCrashSection("Event data", j);
            throw new ReportedException(crashReport);
        }
    }

    public boolean canBlockFreeze(BlockPos blockPos, boolean flag) {
        BiomeGenBase biomeGenForCoords = this.getBiomeGenForCoords(blockPos);
        float floatTemperature = biomeGenForCoords.getFloatTemperature(blockPos);
        if (floatTemperature > 0.15F) {
            return false;
        }

        if (blockPos.getY() >= 0
                && blockPos.getY() < 256
                && this.getLightFor(EnumSkyBlock.BLOCK, blockPos) < 10) {
            BlockState blockState = this.getBlockState(blockPos);
            Block block = blockState.getBlock();
            if ((block == Blocks.water || block == Blocks.flowing_water)
                    && (Integer) blockState.getValue(BlockLiquid.LEVEL) == 0) {
                if (!flag) {
                    return true;
                }

                int i =
                        this.isWater(blockPos.offsetWest())
                                        && this.isWater(blockPos.offsetEast())
                                        && this.isWater(blockPos.offsetNorth())
                                        && this.isWater(blockPos.offsetSouth())
                                ? 1
                                : 0;
                if (i == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public int getStrongPower(BlockPos blockPos, Facing facing) {
        net.minecraft.block.state.BlockState blockState = this.getBlockState(blockPos);
        return blockState.getBlock().isProvidingStrongPower(this, blockPos, blockState, facing);
    }

    public static void setLegacyWorldFlowMarker(int[] aint) {
        net.minecraft.world.World.legacyWorldFlowMarker = aint;
    }

    public Vec3 getSkyColor(Entity entity, float f) {
        getLegacyWorldFlowMarker();

        float celestialAngle = this.getCelestialAngle(f);
        float f2 = MathHelper.cos(celestialAngle * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
        f2 = MathHelper.clamp(f2, 0.0F, 1.0F);
        int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.posY);

        int k = MathHelper.floor(entity.posZ);
        BlockPos blockPos = new BlockPos(i, j, k);
        BiomeGenBase biomeGenForCoords = this.getBiomeGenForCoords(blockPos);
        float floatTemperature = biomeGenForCoords.getFloatTemperature(blockPos);
        int l = biomeGenForCoords.getSkyColorByTemp(floatTemperature);
        float f4 = (l >> 16 & 0xFF) / 255.0F;
        float f5 = (l >> 8 & 0xFF) / 255.0F;
        float f6 = (l & 0xFF) / 255.0F;
        f4 *= f2;
        f5 *= f2;
        f6 *= f2;
        float rainStrength = this.getRainStrength(f);
        if (rainStrength > 0.0F) {
            float f8 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.6F;
            float f9 = 1.0F - rainStrength * 0.75F;
            f4 = f4 * f9 + f8 * (1.0F - f9);
            f5 = f5 * f9 + f8 * (1.0F - f9);
            f6 = f6 * f9 + f8 * (1.0F - f9);
        }

        float weightedThunderStrength = this.getWeightedThunderStrength(f);
        if (weightedThunderStrength > 0.0F) {
            float f12 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.2F;
            float f10 = 1.0F - weightedThunderStrength * 0.75F;
            f4 = f4 * f10 + f12 * (1.0F - f10);
            f5 = f5 * f10 + f12 * (1.0F - f10);
            f6 = f6 * f10 + f12 * (1.0F - f10);
        }

        if (this.lastLightningBolt > 0) {
            float f13 = this.lastLightningBolt - f;
            if (f13 > 1.0F) {
                f13 = 1.0F;
            }

            f13 *= 0.45F;
            f4 = f4 * (1.0F - f13) + 0.8F * f13;
            f5 = f5 * (1.0F - f13) + 0.8F * f13;
            f6 = f6 * (1.0F - f13) + 1.0F * f13;
        }

        Vec3 vec3 = new Vec3(f4, f5, f6);

        return vec3;
    }

    public List getEntitiesInAABBexcluding(
            Entity entity, BoundingBox boundingBox, Predicate predicate) {
        ArrayList arrayList = Lists.newArrayList();
        getLegacyWorldFlowMarker();

        int i = MathHelper.floor((boundingBox.minX - 2.0) / 16.0);
        int j = MathHelper.floor((boundingBox.maxX + 2.0) / 16.0);

        int k = MathHelper.floor((boundingBox.minZ - 2.0) / 16.0);
        int l = MathHelper.floor((boundingBox.maxZ + 2.0) / 16.0);
        int i1 = i;

        while (i1 <= j) {
            int j1 = k;

            while (true) {
                if (j1 <= l) {
                    if (this.isChunkLoaded(i1, j1, true)) {
                        this.getChunkFromChunkCoords(i1, j1)
                                .getEntitiesWithinAABBForEntity(
                                        entity, boundingBox, arrayList, predicate);
                    }

                    j1++;

                    continue;
                }

                i1++;

                break;
            }
        }

        return arrayList;
    }

    public void playSoundToNearExcept(PlayerEntity playerEntity, String text, float f, float f1) {
        getLegacyWorldFlowMarker();

        int i = 0;

        while (i < this.worldAccesses.size()) {
            ((IWorldAccess) this.worldAccesses.get(i))
                    .playSoundToNearExcept(
                            playerEntity,
                            text,
                            playerEntity.posX,
                            playerEntity.posY,
                            playerEntity.posZ,
                            f,
                            f1);
            i++;
        }
    }

    public PlayerEntity getPlayerEntityByName(String text) {
        net.minecraft.world.World.getLegacyWorldFlowMarker();

        int i = 0;

        while (i < this.playerEntities.size()) {
            net.minecraft.entity.player.PlayerEntity playerEntity =
                    (net.minecraft.entity.player.PlayerEntity) this.playerEntities.get(i);

            try {
                if (text.equals(playerEntity.getName())) {
                    return playerEntity;
                }
            } catch (net.minecraft.util.ReportedException reportedException) {
                throw (reportedException);
            }

            i++;
        }

        return null;
    }

    public void addTileEntities(Collection collection) {
        getLegacyWorldFlowMarker();

        if (this.processingLoadedTiles) {
            this.addedTileEntityList.addAll(collection);

            return;
        }

        for (java.util.Iterator<?> __recoveryIterator171_113848 = (collection).iterator();
                (__recoveryIterator171_113848).hasNext(); ) {
            TileEntity tileEntity = (TileEntity) (__recoveryIterator171_113848).next();
            this.loadedTileEntityList.add(tileEntity);
            if (tileEntity instanceof IUpdatePlayerListBox) {
                this.tickableTileEntities.add(tileEntity);
            }
        }
    }

    public List getPendingBlockUpdatesInBounds(
            StructureBoundingBox structureBoundingBox, boolean flag) {
        return null;
    }

    public boolean isAnyPlayerWithinRangeAt(
            double p171_0, double p171_1, double p171_2, double p171_3) {
        net.minecraft.world.World.getLegacyWorldFlowMarker();

        int i = 0;

        while (i < ((int) (((java.util.List) (this.playerEntities)).size()))) {
            net.minecraft.entity.player.PlayerEntity playerEntity =
                    (net.minecraft.entity.player.PlayerEntity)
                            ((java.lang.Object)
                                    (((java.util.List) (this.playerEntities)).get(((int) (i)))));
            if (net.minecraft.command.IEntitySelector.NOT_SPECTATING.apply(playerEntity)) {
                double distanceSq = playerEntity.getDistanceSq(p171_0, p171_1, p171_2);
                if (p171_3 < 0.0 || distanceSq < p171_3 * p171_3) {
                    return true;
                }
            }

            i++;
        }

        return false;
    }

    public void setLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos, int i) {
        if (this.isValid(blockPos) && this.isBlockLoaded(blockPos)) {
            Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
            chunkFromBlockCoords.setLightFor(enumSkyBlock, blockPos, i);
            this.notifyLightSet(blockPos);
        }
    }

    public void setInitialSpawnLocation() {
        this.setSpawnLocation(new net.minecraft.util.BlockPos(8, 64, 8));
    }

    public float getCelestialAngleRadians(float p171_0) {
        float celestialAngle = this.getCelestialAngle(p171_0);
        return celestialAngle * (float) java.lang.Math.PI * 2.0F;
    }

    public BlockPos getSpawnPoint() {
        BlockPos blockPos =
                new BlockPos(
                        this.worldInfo.getSpawnX(),
                        this.worldInfo.getSpawnY(),
                        this.worldInfo.getSpawnZ());
        if (!this.getWorldBorder().contains(blockPos)) {
            blockPos =
                    this.getHorizon(
                            new BlockPos(
                                    this.getWorldBorder().getCenterX(),
                                    0.0,
                                    this.getWorldBorder().getCenterZ()));
        }

        return blockPos;
    }

    public int getLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
        if (blockPos.getY() < 0) {
            blockPos = new BlockPos(blockPos.getX(), 0, blockPos.getZ());
        }

        if (!this.isValid(blockPos)) {
            return enumSkyBlock.defaultLightValue;
        }

        if (!this.isBlockLoaded(blockPos)) {
            return enumSkyBlock.defaultLightValue;
        }

        Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
        return chunkFromBlockCoords.getLightFor(enumSkyBlock, blockPos);
    }

    public Chunk getChunkFromChunkCoords(int p171_0, int p171_1) {
        return this.chunkProvider.provideChunk(p171_0, p171_1);
    }

    public World init() {
        return this;
    }

    public boolean canBlockSeeSky(BlockPos blockPos) {
        getLegacyWorldFlowMarker();

        if (blockPos.getY() >= 63) {
            return this.isAgainstSky(blockPos);
        }

        BlockPos blockPosValue = new BlockPos(blockPos.getX(), 63, blockPos.getZ());
        if (!this.isAgainstSky(blockPosValue)) {
            return false;
        }

        blockPosValue = blockPosValue.offsetDown();

        while (blockPosValue.getY() > blockPos.getY()) {
            Block block = this.getBlockState(blockPosValue).getBlock();
            if (block.getLightOpacity() > 0 && !block.getMaterial().isLiquid()) {
                return false;
            }

            blockPosValue = blockPosValue.offsetDown();
        }

        return true;
    }

    public boolean extinguishFire(PlayerEntity playerEntity, BlockPos blockPos, Facing facing) {
        blockPos = blockPos.offset(facing);
        if (this.getBlockState(blockPos).getBlock() == Blocks.fire) {
            this.playAuxSFXAtEntity(playerEntity, 1004, blockPos, 0);
            this.setBlockToAir(blockPos);
            return true;
        } else {
            return false;
        }
    }

    public boolean isAreaLoaded(StructureBoundingBox structureBoundingBox, boolean flag) {
        return this.isAreaLoaded(
                structureBoundingBox.minX,
                structureBoundingBox.minY,
                structureBoundingBox.minZ,
                structureBoundingBox.maxX,
                structureBoundingBox.maxY,
                structureBoundingBox.maxZ,
                flag);
    }

    public int getSkylightSubtracted() {
        return this.skylightSubtracted;
    }

    public void markBlockForUpdate(BlockPos blockPos) {
        getLegacyWorldFlowMarker();

        int i = 0;

        while (i < this.worldAccesses.size()) {
            ((IWorldAccess) this.worldAccesses.get(i)).markBlockForUpdate(blockPos);
            i++;
        }
    }

    public void removeWorldAccess(IWorldAccess iWorldAccess) {
        this.worldAccesses.remove(iWorldAccess);
    }

    public List getEntitiesWithinAABB(Class type, BoundingBox boundingBox) {
        return this.getEntitiesWithinAABB(
                type, boundingBox, net.minecraft.command.IEntitySelector.NOT_SPECTATING);
    }

    public void updateSkylightSubtracted() {
        int lightReduction = this.calculateSkylightSubtracted(1.0F);
        if (lightReduction != this.skylightSubtracted) {
            this.skylightSubtracted = lightReduction;
        }
    }

    public boolean addTileEntity(TileEntity tileEntity) {
        boolean flag = this.loadedTileEntityList.add(tileEntity);
        if (flag && tileEntity instanceof IUpdatePlayerListBox) {
            this.tickableTileEntities.add(tileEntity);
        }

        return flag;
    }

    public boolean isAreaLoaded(StructureBoundingBox structureBoundingBox) {
        return this.isAreaLoaded(structureBoundingBox, true);
    }

    public Random setRandomSeed(int i, int j, int k) {
        long i1 = i;

        i1 *= 341873128712L;
        long j1 = j;

        long l = i1 + j1 * 132897987541L + this.getWorldInfo().getSeed() + k;
        this.rand.setSeed(l);
        return this.rand;
    }

    public int getLightFromNeighborsFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
        if (this.provider.getHasNoSky() && enumSkyBlock == EnumSkyBlock.SKY) {
            return 0;
        }

        if (blockPos.getY() < 0) {
            blockPos = new BlockPos(blockPos.getX(), 0, blockPos.getZ());
        }

        if (!this.isValid(blockPos)) {
            return enumSkyBlock.defaultLightValue;
        }

        if (!this.isBlockLoaded(blockPos)) {
            return enumSkyBlock.defaultLightValue;
        }

        if (this.getBlockState(blockPos).getBlock().getUseNeighborBrightness()) {
            int lightFor = this.getLightFor(enumSkyBlock, blockPos.offsetUp());
            int lightForValue = this.getLightFor(enumSkyBlock, blockPos.offsetEast());
            int j = this.getLightFor(enumSkyBlock, blockPos.offsetWest());
            int k = this.getLightFor(enumSkyBlock, blockPos.offsetSouth());
            int l = this.getLightFor(enumSkyBlock, blockPos.offsetNorth());
            if (lightForValue > lightFor) {
                lightFor = lightForValue;
            }

            if (j > lightFor) {
                lightFor = j;
            }

            if (k > lightFor) {
                lightFor = k;
            }

            if (l > lightFor) {
                lightFor = l;
            }

            return lightFor;
        } else {
            Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
            return chunkFromBlockCoords.getLightFor(enumSkyBlock, blockPos);
        }
    }

    public List getPlayers(Class type, Predicate p171_1) {
        net.minecraft.world.World.getLegacyWorldFlowMarker();

        java.util.ArrayList arrayList = com.google.common.collect.Lists.newArrayList();

        java.util.Iterator iterator =
                ((java.util.Iterator) (((java.util.List) (this.playerEntities)).iterator()));

        while (((boolean) (((java.util.Iterator) (iterator)).hasNext()))) {
            net.minecraft.entity.Entity entity =
                    (net.minecraft.entity.Entity)
                            ((java.lang.Object) (((java.util.Iterator) (iterator)).next()));
            if (((boolean)
                            (((java.lang.Class) (type))
                                    .isAssignableFrom(
                                            ((java.lang.Class)
                                                    (((java.lang.Class)
                                                            (((java.lang.Object) (entity))
                                                                    .getClass())))))))
                    && p171_1.apply(entity)) {
                ((java.util.ArrayList) (arrayList)).add(((java.lang.Object) (entity)));
            }
        }

        return arrayList;
    }

    public int getLastLightningBolt() {
        return this.lastLightningBolt;
    }

    public float getCurrentMoonPhaseFactor() {
        return WorldProvider.moonPhaseFactors[
                this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
    }

    public void markChunkDirty(BlockPos blockPos, TileEntity tileEntity) {
        try {
            if (this.isBlockLoaded(blockPos)) {
                this.getChunkFromBlockCoords(blockPos).setChunkModified();
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }
    }

    public boolean isThundering() {
        try {
            if (this.getWeightedThunderStrength(1.0F) > 0.9) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        return false;
    }

    public boolean isBlockLoaded(BlockPos blockPos) {
        return this.isBlockLoaded(blockPos, true);
    }

    public BlockState getBlockState(BlockPos blockPos) {
        if (!this.isValid(blockPos)) {
            return Blocks.air.getDefaultState();
        }

        Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
        return chunkFromBlockCoords.getBlockState(blockPos);
    }

    public boolean canBlockBePlaced(
            Block block,
            BlockPos blockPos,
            boolean flag,
            Facing facing,
            Entity entity,
            ItemStack itemStack) {
        Block blockValue = this.getBlockState(blockPos).getBlock();
        BoundingBox boundingBox =
                flag
                        ? null
                        : block.getCollisionBoundingBox(this, blockPos, block.getDefaultState());
        return (boundingBox == null || this.checkNoEntityCollision(boundingBox, entity))
                && (blockValue.getMaterial() == Material.circuits && block == Blocks.anvil
                        || blockValue.getMaterial().isReplaceable()
                                && block.canReplace(this, blockPos, facing, itemStack));
    }

    public void updateComparatorOutputLevel(BlockPos blockPos, Block block) {
        getLegacyWorldFlowMarker();

        Iterator iterator = FacingPlane.HORIZONTAL.iterator();

        while (iterator.hasNext()) {
            Facing facing = (Facing) iterator.next();
            BlockPos blockPosValue = blockPos.offset(facing);
            if (this.isBlockLoaded(blockPosValue)) {
                BlockState blockState = this.getBlockState(blockPosValue);
                if (Blocks.unpowered_comparator.isAssociated(blockState.getBlock())) {
                    blockState
                            .getBlock()
                            .onNeighborBlockChange(this, blockPosValue, blockState, block);

                } else {

                    if (blockState.getBlock().isNormalCube()) {
                        blockPosValue = blockPosValue.offset(facing);
                        blockState = this.getBlockState(blockPosValue);
                        if (Blocks.unpowered_comparator.isAssociated(blockState.getBlock())) {
                            blockState
                                    .getBlock()
                                    .onNeighborBlockChange(this, blockPosValue, blockState, block);
                        }
                    }
                }
            }
        }
    }

    protected void playMoodSoundAndCheckLight(int i, int j, Chunk chunk) {
        this.theProfiler.endStartSection("moodSound");
        if (this.ambientTickCountdown == 0 && !this.isRemote) {
            this.updateLCG = this.updateLCG * 3 + 1013904223;
            int k = this.updateLCG >> 2;
            int l = k & 15;
            int i1 = k >> 8 & 15;
            int j1 = k >> 16 & 0xFF;
            BlockPos blockPos = new BlockPos(l, j1, i1);
            Block block = chunk.getBlock(blockPos);
            l += i;
            i1 += j;
            if (block.getMaterial() == Material.AIR
                    && this.getLight(blockPos) <= this.rand.nextInt(8)
                    && this.getLightFor(EnumSkyBlock.SKY, blockPos) <= 0) {
                PlayerEntity playerEntity = this.getClosestPlayer(l + 0.5, j1 + 0.5, i1 + 0.5, 8.0);
                if (playerEntity != null
                        && playerEntity.getDistanceSq(l + 0.5, j1 + 0.5, i1 + 0.5) > 4.0) {
                    this.playSoundEffect(
                            l + 0.5,
                            j1 + 0.5,
                            i1 + 0.5,
                            "ambient.cave.cave",
                            0.7F,
                            0.8F + this.rand.nextFloat() * 0.2F);
                    this.ambientTickCountdown = this.rand.nextInt(12000) + 6000;
                }
            }
        }

        this.theProfiler.endStartSection("checkLight");
        chunk.enqueueRelightChecks();
    }

    public List getEntitiesWithinAABB(Class oclass, BoundingBox boundingBox, Predicate predicate) {
        int i = MathHelper.floor((boundingBox.minX - 2.0) / 16.0);
        getLegacyWorldFlowMarker();

        int j = MathHelper.floor((boundingBox.maxX + 2.0) / 16.0);

        int k = MathHelper.floor((boundingBox.minZ - 2.0) / 16.0);
        int l = MathHelper.floor((boundingBox.maxZ + 2.0) / 16.0);
        ArrayList arrayList = Lists.newArrayList();
        int i1 = i;

        while (i1 <= j) {
            int j1 = k;

            while (true) {
                if (j1 <= l) {
                    if (this.isChunkLoaded(i1, j1, true)) {
                        this.getChunkFromChunkCoords(i1, j1)
                                .getEntitiesOfTypeWithinAAAB(
                                        oclass, boundingBox, arrayList, predicate);
                    }

                    j1++;

                    continue;
                }

                i1++;

                break;
            }
        }

        return arrayList;
    }

    protected boolean isChunkLoaded(int p171_0, int p171_1, boolean p171_2) {
        return (!(!this.chunkProvider.chunkExists(p171_0, p171_1)
                || !p171_2 && this.chunkProvider.provideChunk(p171_0, p171_1).isEmpty()));
    }

    public void removeEntity(Entity entity) {
        try {
            if (entity.riddenByEntity != null) {
                entity.riddenByEntity.mountEntity((net.minecraft.entity.Entity) null);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        try {
            if (entity.ridingEntity != null) {
                entity.mountEntity((net.minecraft.entity.Entity) null);
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue) {
            throw (reportedExceptionValue);
        }

        try {
            entity.setDead();
            if (entity instanceof net.minecraft.entity.player.PlayerEntity) {
                this.playerEntities.remove(entity);
                this.updateAllPlayersSleepingFlag();
                this.onEntityRemoved(entity);
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue2) {
            throw (reportedExceptionValue2);
        }
    }

    public PlayerEntity getClosestPlayerToEntity(Entity entity, double d0) {
        return this.getClosestPlayer(entity.posX, entity.posY, entity.posZ, d0);
    }

    public boolean isBlockModifiable(PlayerEntity playerEntity, BlockPos blockPos) {
        return true;
    }

    public Chunk getChunkFromBlockCoords(BlockPos blockPos) {
        return this.getChunkFromChunkCoords(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    public boolean isRaining() {
        try {
            if (this.getRainStrength(1.0F) > 0.2) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        return false;
    }

    private boolean isAreaLoaded(
            int p171_0,
            int p171_1,
            int p171_2,
            int p171_3,
            int p171_4,
            int p171_5,
            boolean p171_6) {
        net.minecraft.world.World.getLegacyWorldFlowMarker();

        try {
            if (p171_4 < 0 || p171_1 >= 256) {
                return false;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        p171_0 >>= 4;
        p171_2 >>= 4;
        p171_3 >>= 4;
        p171_5 >>= 4;
        int k1 = p171_0;

        while (k1 <= p171_3) {
            int l1 = p171_2;

            while (true) {
                if (l1 <= p171_5) {
                    try {
                        if (!this.isChunkLoaded(k1, l1, p171_6)) {
                            return false;
                        }
                    } catch (net.minecraft.util.ReportedException reportedExceptionValue) {
                        throw (reportedExceptionValue);
                    }

                    l1++;

                    continue;
                }

                k1++;

                break;
            }
        }

        return true;
    }

    public void updateEntity(Entity entity) {
        this.updateEntityWithOptionalForce(entity, true);
    }

    public void notifyBlockOfStateChange(BlockPos blockPos, Block block) {
        if (!this.isRemote) {
            BlockState blockState = this.getBlockState(blockPos);

            try {
                blockState.getBlock().onNeighborBlockChange(this, blockPos, blockState, block);
            } catch (Throwable throwable) {
                CrashReport crashReport =
                        CrashReport.makeCrashReport(
                                throwable, "Exception while updating neighbours");
                CrashReportCategory crashReportCategory =
                        crashReport.makeCategory("Block being updated");
                crashReportCategory.addCrashSectionCallable(
                        "Source block type", new WorldSourceBlockTypeCallable(this, block));
                CrashReportCategory.addBlockInfo(crashReportCategory, blockPos, blockState);
                throw new ReportedException(crashReport);
            }
        }
    }

    public String getProviderName() {
        return this.chunkProvider.makeString();
    }

    public Block getGroundAboveSeaLevel(BlockPos blockPos) {
        getLegacyWorldFlowMarker();

        BlockPos blockPosValue = new BlockPos(blockPos.getX(), 63, blockPos.getZ());

        while (!this.isAirBlock(blockPosValue.offsetUp())) {
            blockPosValue = blockPosValue.offsetUp();
        }

        return this.getBlockState(blockPosValue).getBlock();
    }

    public BlockPos getTopSolidOrLiquidBlock(BlockPos blockPos) {
        int[] aint1 = getLegacyWorldFlowMarker();
        Chunk chunkFromBlockCoords = this.getChunkFromBlockCoords(blockPos);
        BlockPos blockPosValue =
                new BlockPos(
                        blockPos.getX(),
                        chunkFromBlockCoords.getTopFilledSegment() + 16,
                        blockPos.getZ());
        int[] aint = aint1;

        while (blockPosValue.getY() >= 0) {
            BlockPos blockPosValue2 = blockPosValue.offsetDown();
            Material material = chunkFromBlockCoords.getBlock(blockPosValue2).getMaterial();
            if (material.blocksMovement() && material != Material.leaves && aint != null) {
                break;
            }

            blockPosValue = blockPosValue2;
        }

        return blockPosValue;
    }

    private boolean isValid(BlockPos blockPos) {
        return blockPos.getX() >= -30000000
                && blockPos.getZ() >= -30000000
                && blockPos.getX() < 30000000
                && blockPos.getZ() < 30000000
                && blockPos.getY() >= 0
                && blockPos.getY() < 256;
    }

    public boolean isAreaLoaded(BlockPos blockPos, int p171_1) {
        return this.isAreaLoaded(blockPos, p171_1, true);
    }

    public String getDebugLoadedEntities() {
        return "All: " + this.loadedEntities.size();
    }

    public boolean addWeatherEffect(Entity entity) {
        this.weatherEffects.add(entity);
        return true;
    }

    public Entity findNearestEntityWithinAABB(Class type, BoundingBox boundingBox, Entity origin) {
        getLegacyWorldFlowMarker();
        List<?> candidates = this.getEntitiesWithinAABB(type, boundingBox);
        Entity nearest = null;
        double nearestDistanceSq = Double.MAX_VALUE;
        for (int index = 0; index < candidates.size(); index++) {
            Entity candidate = (Entity) candidates.get(index);
            if (candidate != origin && IEntitySelector.NOT_SPECTATING.apply(candidate)) {
                double distanceSq = origin.getDistanceSqToEntity(candidate);
                if (distanceSq <= nearestDistanceSq) {
                    nearest = candidate;
                    nearestDistanceSq = distanceSq;
                }
            }
        }
        return nearest;
    }

    public void playSoundAtEntity(Entity entity, String text, float f, float f1) {
        getLegacyWorldFlowMarker();

        int i = 0;

        while (i < this.worldAccesses.size()) {
            ((IWorldAccess) this.worldAccesses.get(i))
                    .playSound(text, entity.posX, entity.posY, entity.posZ, f, f1);
            i++;
        }
    }

    public void markBlockRangeForRenderUpdate(int i, int j, int k, int l, int i1, int j1) {
        getLegacyWorldFlowMarker();

        int k1 = 0;

        while (k1 < this.worldAccesses.size()) {
            ((IWorldAccess) this.worldAccesses.get(k1))
                    .markBlockRangeForRenderUpdate(i, j, k, l, i1, j1);
            k1++;
        }
    }

    public List getEntitiesWithinAABBExcludingEntity(Entity entity, BoundingBox boundingBox) {
        return this.getEntitiesInAABBexcluding(
                entity, boundingBox, net.minecraft.command.IEntitySelector.NOT_SPECTATING);
    }

    public boolean isMaterialInBB(BoundingBox boundingBox, Material material) {
        getLegacyWorldFlowMarker();

        int i = MathHelper.floor(boundingBox.minX);
        int j = MathHelper.floor(boundingBox.maxX + 1.0);
        int k = MathHelper.floor(boundingBox.minY);
        int l = MathHelper.floor(boundingBox.maxY + 1.0);
        int i1 = MathHelper.floor(boundingBox.minZ);
        int j1 = MathHelper.floor(boundingBox.maxZ + 1.0);
        int k1 = i;

        while (k1 < j) {
            int l1 = k;

            while (true) {
                if (l1 < l) {
                    int i2 = i1;

                    while (i2 < j1) {
                        if (this.getBlockState(new BlockPos(k1, l1, i2)).getBlock().getMaterial()
                                == material) {
                            return true;
                        }

                        i2++;
                    }

                    l1++;

                    continue;
                }

                k1++;

                break;
            }
        }

        return false;
    }

    public boolean checkNoEntityCollision(BoundingBox boundingBox, Entity entity) {
        List entitiesWithinAABBExcludingEntity =
                this.getEntitiesWithinAABBExcludingEntity((Entity) null, boundingBox);
        getLegacyWorldFlowMarker();

        int i = 0;

        while (i < entitiesWithinAABBExcludingEntity.size()) {
            Entity entityValue = (Entity) entitiesWithinAABBExcludingEntity.get(i);
            if (!entityValue.isDead
                    && entityValue.preventEntitySpawning
                    && entityValue != entity
                    && (entity == null
                            || entity.ridingEntity != entityValue
                                    && entity.riddenByEntity != entityValue)) {
                return false;
            }

            i++;
        }

        return true;
    }

    public BlockPos getHorizon(BlockPos blockPos) {
        getLegacyWorldFlowMarker();

        if (blockPos.getX() >= -30000000
                && blockPos.getZ() >= -30000000
                && blockPos.getX() < 30000000
                && blockPos.getZ() < 30000000) {
            if (this.isChunkLoaded(blockPos.getX() >> 4, blockPos.getZ() >> 4, true)) {
                int height =
                        this.getChunkFromChunkCoords(blockPos.getX() >> 4, blockPos.getZ() >> 4)
                                .getHeight(blockPos.getX() & 15, blockPos.getZ() & 15);

                return new BlockPos(blockPos.getX(), height, blockPos.getZ());
            }

            byte b0 = 0;

            return new BlockPos(blockPos.getX(), b0, blockPos.getZ());
        }

        byte b1 = 64;
        return new BlockPos(blockPos.getX(), b1, blockPos.getZ());
    }

    public void addBlockEvent(BlockPos blockPos, Block block, int p171_2, int p171_3) {
        block.onBlockEventReceived(this, blockPos, this.getBlockState(blockPos), p171_2, p171_3);
    }

    public boolean isAreaLoaded(BlockPos blockPos, int i, boolean flag) {
        return this.isAreaLoaded(
                blockPos.getX() - i,
                blockPos.getY() - i,
                blockPos.getZ() - i,
                blockPos.getX() + i,
                blockPos.getY() + i,
                blockPos.getZ() + i,
                flag);
    }

    // region Static initialization and string tables
    static {
        int[] aint = new int[3];
        setLegacyWorldFlowMarker(aint);
    }

    // endregion

    public void playBroadcastSound(int i, BlockPos blockPos, int j) {
        getLegacyWorldFlowMarker();

        int k = 0;

        while (k < this.worldAccesses.size()) {
            ((IWorldAccess) this.worldAccesses.get(k)).broadcastSound(i, blockPos, j);
            k++;
        }
    }

    public void tick() {
        this.updateWeather();
    }

    public WorldInfo getWorldInfo() {
        return this.worldInfo;
    }

    public boolean isBlockPowered(BlockPos blockPos) {
        try {
            if (this.getRedstonePower(blockPos.offsetDown(), net.minecraft.util.Facing.DOWN) > 0) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        try {
            if (this.getRedstonePower(blockPos.offsetUp(), net.minecraft.util.Facing.UP) > 0) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue) {
            throw (reportedExceptionValue);
        }

        try {
            if (this.getRedstonePower(blockPos.offsetNorth(), net.minecraft.util.Facing.NORTH)
                    > 0) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue2) {
            throw (reportedExceptionValue2);
        }

        try {
            if (this.getRedstonePower(blockPos.offsetSouth(), net.minecraft.util.Facing.SOUTH)
                    > 0) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue3) {
            throw (reportedExceptionValue3);
        }

        try {
            if (this.getRedstonePower(blockPos.offsetWest(), net.minecraft.util.Facing.WEST) > 0) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue4) {
            throw (reportedExceptionValue4);
        }

        try {
            if (this.getRedstonePower(blockPos.offsetEast(), net.minecraft.util.Facing.EAST) > 0) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue5) {
            throw (reportedExceptionValue5);
        }

        return false;
    }

    public int getLight(BlockPos blockPos) {
        if (blockPos.getY() < 0) {
            return 0;
        }

        if (blockPos.getY() >= 256) {
            blockPos = new BlockPos(blockPos.getX(), 255, blockPos.getZ());
        }

        return this.getChunkFromBlockCoords(blockPos).setLight(blockPos, 0);
    }

    public List getCollisionBoxes(BoundingBox boundingBox) {
        getLegacyWorldFlowMarker();

        ArrayList arrayList = Lists.newArrayList();
        int i = MathHelper.floor(boundingBox.minX);
        int j = MathHelper.floor(boundingBox.maxX + 1.0);
        int k = MathHelper.floor(boundingBox.minY);
        int l = MathHelper.floor(boundingBox.maxY + 1.0);
        int i1 = MathHelper.floor(boundingBox.minZ);
        int j1 = MathHelper.floor(boundingBox.maxZ + 1.0);
        int k1 = i;

        while (k1 < j) {
            int l1 = i1;

            while (true) {
                if (l1 < j1) {
                    if (this.isBlockLoaded(new BlockPos(k1, 64, l1))) {
                        int i2 = k - 1;

                        while (i2 < l) {
                            BlockPos blockPos;
                            BlockState blockState;
                            {
                                blockPos = new BlockPos(k1, i2, l1);
                                if (k1 >= -30000000
                                        && k1 < 30000000
                                        && l1 >= -30000000
                                        && l1 < 30000000) {
                                    blockState = this.getBlockState(blockPos);

                                } else {

                                    blockState = Blocks.bedrock.getDefaultState();
                                }
                            }

                            blockState
                                    .getBlock()
                                    .addCollisionBoxesToList(
                                            this,
                                            blockPos,
                                            blockState,
                                            boundingBox,
                                            arrayList,
                                            (Entity) null);
                            i2++;
                        }
                    }

                    l1++;

                    continue;
                }

                k1++;

                break;
            }
        }

        return arrayList;
    }

    public int isBlockIndirectlyGettingPowered(BlockPos blockPos) {
        getLegacyWorldFlowMarker();

        int i = 0;
        Facing[] facingArray = Facing.values();
        int j = facingArray.length;
        int k = 0;

        while (k < j) {
            Facing facing = facingArray[k];
            int l = this.getRedstonePower(blockPos.offset(facing), facing);
            if (l >= 15) {
                return 15;
            }

            if (l > i) {
                i = l;
            }

            k++;
        }

        return i;
    }

    public List<BoundingBox> getCollidingBoundingBoxes(Entity entity, BoundingBox boundingBox) {
        getLegacyWorldFlowMarker();

        ArrayList arrayList = Lists.newArrayList();
        int i = MathHelper.floor(boundingBox.minX);

        int j = MathHelper.floor(boundingBox.maxX + 1.0);
        int k = MathHelper.floor(boundingBox.minY);
        int l = MathHelper.floor(boundingBox.maxY + 1.0);
        int i1 = MathHelper.floor(boundingBox.minZ);
        int j1 = MathHelper.floor(boundingBox.maxZ + 1.0);
        int k1 = i;

        while (k1 < j) {
            int l1 = i1;

            while (true) {
                if (l1 < j1) {
                    if (this.isBlockLoaded(new BlockPos(k1, 64, l1))) {
                        int i2 = k - 1;

                        while (i2 < l) {
                            BlockPos blockPos;
                            boolean flag1;
                            {
                                blockPos = new BlockPos(k1, i2, l1);
                                boolean flag = entity.isOutsideBorder();
                                flag1 = this.isInsideBorder(this.getWorldBorder(), entity);
                                if (flag && flag1) {
                                    entity.setOutsideBorder(false);

                                } else {

                                    if (!flag && !flag1) {
                                        entity.setOutsideBorder(true);
                                    }
                                }
                            }

                            BlockState blockState;
                            {
                                if (!this.getWorldBorder().contains(blockPos) && flag1) {
                                    blockState = Blocks.stone.getDefaultState();

                                } else {

                                    blockState = this.getBlockState(blockPos);
                                }
                            }

                            blockState
                                    .getBlock()
                                    .addCollisionBoxesToList(
                                            this,
                                            blockPos,
                                            blockState,
                                            boundingBox,
                                            arrayList,
                                            entity);
                            i2++;
                        }
                    }

                    l1++;

                    continue;
                }

                k1++;

                break;
            }
        }

        double d0 = 0.25;
        List entitiesWithinAABBExcludingEntity =
                this.getEntitiesWithinAABBExcludingEntity(entity, boundingBox.expand(d0, d0, d0));
        int j2 = 0;

        while (j2 < entitiesWithinAABBExcludingEntity.size()) {
            if (entity.riddenByEntity != entitiesWithinAABBExcludingEntity
                    && entity.ridingEntity != entitiesWithinAABBExcludingEntity) {
                BoundingBox boundingBoxValue =
                        ((Entity) entitiesWithinAABBExcludingEntity.get(j2))
                                .getCollisionBoundingBox();
                if (boundingBoxValue != null && boundingBoxValue.intersectsWith(boundingBox)) {
                    arrayList.add(boundingBoxValue);
                }

                boundingBoxValue =
                        entity.getCollisionBox((Entity) entitiesWithinAABBExcludingEntity.get(j2));
                if (boundingBoxValue != null && boundingBoxValue.intersectsWith(boundingBox)) {
                    arrayList.add(boundingBoxValue);
                }
            }

            j2++;
        }

        return arrayList;
    }

    public Calendar getCurrentDate() {
        long totalWorldTime = this.getTotalWorldTime();

        totalWorldTime %= 600L;

        if (totalWorldTime == 0L) {
            this.theCalendar.setTimeInMillis(MinecraftServer.getCurrentTimeMillis());
        }

        return this.theCalendar;
    }

    public List getEntities(Class type, Predicate p171_1) {
        net.minecraft.world.World.getLegacyWorldFlowMarker();

        java.util.ArrayList arrayList = com.google.common.collect.Lists.newArrayList();

        java.util.Iterator iterator =
                ((java.util.Iterator) (((java.util.List) (this.loadedEntities)).iterator()));

        while (((boolean) (((java.util.Iterator) (iterator)).hasNext()))) {
            net.minecraft.entity.Entity entity =
                    (net.minecraft.entity.Entity)
                            ((java.lang.Object) (((java.util.Iterator) (iterator)).next()));
            if (((boolean)
                            (((java.lang.Class) (type))
                                    .isAssignableFrom(
                                            ((java.lang.Class)
                                                    (((java.lang.Class)
                                                            (((java.lang.Object) (entity))
                                                                    .getClass())))))))
                    && p171_1.apply(entity)) {
                ((java.util.ArrayList) (arrayList)).add(((java.lang.Object) (entity)));
            }
        }

        return arrayList;
    }

    public World(
            ISaveHandler bGcValue,
            WorldInfo bFZValue,
            WorldProvider bFYValue,
            Profiler bFXValue,
            boolean isRemoteValue) {
        this.loadedTileEntityList = Lists.newArrayList();
        this.tickableTileEntities = Lists.newArrayList();
        this.addedTileEntityList = Lists.newArrayList();
        this.tileEntitiesToBeRemoved = Lists.newArrayList();
        this.playerEntities = Lists.newArrayList();
        this.weatherEffects = Lists.newArrayList();
        this.entitiesById = new IntHashMap();

        this.cloudColour = 16777215L;
        this.updateLCG = new Random().nextInt();
        this.DIST_HASH_MAGIC = 1013904223;
        this.rand = new Random();
        this.worldAccesses = Lists.newArrayList();
        this.theCalendar = Calendar.getInstance();
        this.worldScoreboard = new Scoreboard();
        this.activeChunkSet = Sets.newHashSet();
        this.ambientTickCountdown = this.rand.nextInt(12000);
        this.spawnHostileMobs = true;
        this.spawnPeacefulMobs = true;
        this.lightUpdateBlockList = new int[32768];
        this.saveHandler = bGcValue;
        this.theProfiler = bFXValue;
        this.worldInfo = bFZValue;
        this.provider = bFYValue;
        this.isRemote = isRemoteValue;
        this.worldBorder = bFYValue.getWorldBorder();
    }

    public float getBlockDensity(Vec3 vec3, BoundingBox boundingBox) {
        double d0 = 1.0 / ((boundingBox.maxX - boundingBox.minX) * 2.0 + 1.0);
        getLegacyWorldFlowMarker();

        double d1 = 1.0 / ((boundingBox.maxY - boundingBox.minY) * 2.0 + 1.0);

        double d2 = 1.0 / ((boundingBox.maxZ - boundingBox.minZ) * 2.0 + 1.0);
        if (d0 >= 0.0 && d1 >= 0.0 && d2 >= 0.0) {
            int i = 0;
            int j = 0;
            float f = 0.0F;

            while (f <= 1.0F) {
                float f1 = 0.0F;

                while (true) {
                    if (f1 <= 1.0F) {
                        float f2 = 0.0F;

                        while (f2 <= 1.0F) {
                            double d3 =
                                    boundingBox.minX + (boundingBox.maxX - boundingBox.minX) * f;
                            double d4 =
                                    boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * f1;
                            double d5 =
                                    boundingBox.minZ + (boundingBox.maxZ - boundingBox.minZ) * f2;
                            if (this.rayTraceBlocks(new Vec3(d3, d4, d5), vec3) == null) {
                                i++;
                            }

                            j++;
                            f2 = (float) (f2 + d2);
                        }

                        f1 = (float) (f1 + d1);

                        continue;
                    }

                    f = (float) (f + d0);

                    break;
                }
            }

            return (float) i / j;
        } else {
            return 0.0F;
        }
    }

    public boolean isFlammableWithin(BoundingBox boundingBox) {
        int i = MathHelper.floor(boundingBox.minX);
        int j = MathHelper.floor(boundingBox.maxX + 1.0);
        getLegacyWorldFlowMarker();

        int k = MathHelper.floor(boundingBox.minY);
        int l = MathHelper.floor(boundingBox.maxY + 1.0);

        int i1 = MathHelper.floor(boundingBox.minZ);
        int j1 = MathHelper.floor(boundingBox.maxZ + 1.0);
        if (this.isAreaLoaded(i, k, i1, j, l, j1, true)) {
            int k1 = i;

            while (k1 < j) {
                int l1 = k;

                while (true) {
                    if (l1 < l) {
                        int i2 = i1;

                        while (i2 < j1) {
                            Block block = this.getBlockState(new BlockPos(k1, l1, i2)).getBlock();
                            if (block == Blocks.fire
                                    || block == Blocks.flowing_lava
                                    || block == Blocks.lava) {
                                return true;
                            }

                            i2++;
                        }

                        l1++;

                        continue;
                    }

                    k1++;

                    break;
                }
            }
        }

        return false;
    }

    public void spawnParticle(
            EnumParticleTypes enumParticleTypes,
            boolean flag,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int... aint) {
        this.spawnParticle(
                enumParticleTypes.getParticleID(),
                enumParticleTypes.getShouldIgnoreRange() | flag,
                d0,
                d1,
                d2,
                d3,
                d4,
                d5,
                aint);
    }

    public WorldType getWorldType() {
        return this.worldInfo.getTerrainType();
    }

    public void notifyNeighborsOfStateExcept(BlockPos blockPos, Block block, Facing facing) {
        try {
            if (facing != net.minecraft.util.Facing.WEST) {
                this.notifyBlockOfStateChange(blockPos.offsetWest(), block);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        try {
            if (facing != net.minecraft.util.Facing.EAST) {
                this.notifyBlockOfStateChange(blockPos.offsetEast(), block);
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue) {
            throw (reportedExceptionValue);
        }

        try {
            if (facing != net.minecraft.util.Facing.DOWN) {
                this.notifyBlockOfStateChange(blockPos.offsetDown(), block);
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue2) {
            throw (reportedExceptionValue2);
        }

        try {
            if (facing != net.minecraft.util.Facing.UP) {
                this.notifyBlockOfStateChange(blockPos.offsetUp(), block);
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue3) {
            throw (reportedExceptionValue3);
        }

        try {
            if (facing != net.minecraft.util.Facing.NORTH) {
                this.notifyBlockOfStateChange(blockPos.offsetNorth(), block);
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue4) {
            throw (reportedExceptionValue4);
        }

        try {
            if (facing != net.minecraft.util.Facing.SOUTH) {
                this.notifyBlockOfStateChange(blockPos.offsetSouth(), block);
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue5) {
            throw (reportedExceptionValue5);
        }
    }

    public Entity getEntityByID(int i) {
        return (Entity) this.entitiesById.lookup(i);
    }

    public PlayerEntity getClosestPlayer(double d0, double d1, double d2, double d3) {
        double d4 = -1.0;
        getLegacyWorldFlowMarker();

        PlayerEntity playerEntity = null;
        int i = 0;

        while (i < this.playerEntities.size()) {
            PlayerEntity playerEntityValue = this.playerEntities.get(i);
            if (IEntitySelector.NOT_SPECTATING.apply(playerEntityValue)) {
                double distanceSq = playerEntityValue.getDistanceSq(d0, d1, d2);
                if ((d3 < 0.0 || distanceSq < d3 * d3) && (d4 == -1.0 || distanceSq < d4)) {
                    d4 = distanceSq;
                    playerEntity = playerEntityValue;
                }
            }

            i++;
        }

        return playerEntity;
    }

    public void playAuxSFX(int i, BlockPos blockPos, int j) {
        this.playAuxSFXAtEntity((PlayerEntity) null, i, blockPos, j);
    }

    public Vec3 getFogColor(float p171_0) {
        float celestialAngle = this.getCelestialAngle(p171_0);
        return this.provider.getFogColor(celestialAngle, p171_0);
    }

    public void setTotalWorldTime(long i) {
        this.worldInfo.incrementTotalWorldTime(i);
    }

    public void unloadEntities(Collection collection) {
        this.unloadedEntityList.addAll(collection);
    }

    public boolean isAnyLiquid(BoundingBox boundingBox) {
        getLegacyWorldFlowMarker();

        int i = MathHelper.floor(boundingBox.minX);
        int j = MathHelper.floor(boundingBox.maxX);

        int k = MathHelper.floor(boundingBox.minY);
        int l = MathHelper.floor(boundingBox.maxY);
        int i1 = MathHelper.floor(boundingBox.minZ);
        int j1 = MathHelper.floor(boundingBox.maxZ);
        int k1 = i;

        while (k1 <= j) {
            int l1 = k;

            while (true) {
                if (l1 <= l) {
                    int i2 = i1;

                    while (i2 <= j1) {
                        Block block = this.getBlockState(new BlockPos(k1, l1, i2)).getBlock();
                        if (block.getMaterial().isLiquid()) {
                            return true;
                        }

                        i2++;
                    }

                    l1++;

                    continue;
                }

                k1++;

                break;
            }
        }

        return false;
    }

    public boolean isBlockTickPending(BlockPos blockPos, Block block) {
        return false;
    }

    public boolean destroyBlock(BlockPos blockPos, boolean flag) {
        BlockState blockState = this.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block.getMaterial() == Material.AIR) {
            return false;
        }

        this.playAuxSFX(2001, blockPos, Block.getStateId(blockState));
        if (flag) {
            block.dropBlockAsItem(this, blockPos, blockState, 0);
        }

        return this.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
    }

    public float getSunBrightness(float f) {
        float celestialAngle = this.getCelestialAngle(f);
        float f2 = 1.0F - (MathHelper.cos(celestialAngle * (float) Math.PI * 2.0F) * 2.0F + 0.2F);
        f2 = MathHelper.clamp(f2, 0.0F, 1.0F);
        f2 = 1.0F - f2;
        f2 = (float) (f2 * (1.0 - this.getRainStrength(f) * 5.0F / 16.0));
        f2 = (float) (f2 * (1.0 - this.getWeightedThunderStrength(f) * 5.0F / 16.0));
        return f2 * 0.8F + 0.2F;
    }

    public DifficultyInstance getDifficultyForLocation(BlockPos blockPos) {

        long i = 0L;
        float f = 0.0F;
        if (this.isBlockLoaded(blockPos)) {
            f = this.getCurrentMoonPhaseFactor();
            i = this.getChunkFromBlockCoords(blockPos).getInhabitedTime();
        }

        return new DifficultyInstance(this.getDifficulty(), this.getWorldTime(), i, f);
    }

    public void checkSessionLock() throws MinecraftException {
        this.saveHandler.checkSessionLock();
    }

    public void initialize(WorldSettings worldSettings) {
        this.worldInfo.setServerInitialized(true);
    }

    public int getStrongPower(BlockPos blockPos) {
        byte b0 = 0;
        int i =
                java.lang.Math.max(
                        b0,
                        this.getStrongPower(blockPos.offsetDown(), net.minecraft.util.Facing.DOWN));

        try {
            if (i >= 15) {
                return i;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        i =
                java.lang.Math.max(
                        i, this.getStrongPower(blockPos.offsetUp(), net.minecraft.util.Facing.UP));

        try {
            if (i >= 15) {
                return i;
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue) {
            throw (reportedExceptionValue);
        }

        i =
                java.lang.Math.max(
                        i,
                        this.getStrongPower(
                                blockPos.offsetNorth(), net.minecraft.util.Facing.NORTH));

        try {
            if (i >= 15) {
                return i;
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue2) {
            throw (reportedExceptionValue2);
        }

        i =
                java.lang.Math.max(
                        i,
                        this.getStrongPower(
                                blockPos.offsetSouth(), net.minecraft.util.Facing.SOUTH));

        try {
            if (i >= 15) {
                return i;
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue3) {
            throw (reportedExceptionValue3);
        }

        i =
                java.lang.Math.max(
                        i,
                        this.getStrongPower(blockPos.offsetWest(), net.minecraft.util.Facing.WEST));

        try {
            if (i >= 15) {
                return i;
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue4) {
            throw (reportedExceptionValue4);
        }

        i =
                java.lang.Math.max(
                        i,
                        this.getStrongPower(blockPos.offsetEast(), net.minecraft.util.Facing.EAST));

        try {
            if (i >= 15) {
                return i;
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue5) {
            throw (reportedExceptionValue5);
        }

        return i;
    }

    public int getUniqueDataId(String text) {
        return this.mapStorage.getUniqueDataId(text);
    }

    public static boolean doesBlockHaveSolidTopSurface(
            IBlockAccess iBlockAccess, BlockPos blockPos) {
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        Block block = blockState.getBlock();
        return block.getMaterial().isOpaque() && block.isFullCube()
                ? true
                : (block instanceof BlockStairs
                        ? blockState.getValue(BlockStairs.HALF) == BlockStairsEnumHalf.TOP
                        : (block instanceof BlockSlab
                                ? blockState.getValue(BlockSlab.HALF_PROP)
                                        == BlockSlabEnumBlockHalf.TOP
                                : (block instanceof BlockHopper
                                        ? true
                                        : (block instanceof BlockSnow
                                                ? (Integer)
                                                                blockState.getValue(
                                                                        BlockSnow.LAYERS_PROP)
                                                        == 7
                                                : false))));
    }

    public int getCombinedLight(BlockPos blockPos, int i) {
        int j = this.getLightFromNeighborsFor(EnumSkyBlock.SKY, blockPos);
        int k = this.getLightFromNeighborsFor(EnumSkyBlock.BLOCK, blockPos);
        if (k < i) {
            k = i;
        }

        return j << 20 | k << 4;
    }

    public void notifyNeighborsRespectDebug(BlockPos blockPos, Block block) {
        if (this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
            this.notifyNeighborsOfStateChange(blockPos, block);
        }
    }

    public void setTileEntity(BlockPos blockPos, TileEntity tileEntity) {
        getLegacyWorldFlowMarker();

        if (tileEntity != null && !tileEntity.isInvalid()) {
            if (this.processingLoadedTiles) {
                tileEntity.setPos(blockPos);
                Iterator iterator = this.addedTileEntityList.iterator();

                while (iterator.hasNext()) {
                    TileEntity tileEntityValue = (TileEntity) iterator.next();
                    if (tileEntityValue.getPos().equals(blockPos)) {
                        tileEntityValue.invalidate();
                        iterator.remove();
                    }
                }

                this.addedTileEntityList.add(tileEntity);

                return;
            }

            this.addTileEntity(tileEntity);
            this.getChunkFromBlockCoords(blockPos).addTileEntity(blockPos, tileEntity);
        }
    }

    protected abstract int getRenderDistanceChunks();

    public void sendBlockBreakProgress(int i, BlockPos blockPos, int j) {
        getLegacyWorldFlowMarker();

        int k = 0;

        while (k < this.worldAccesses.size()) {
            IWorldAccess iWorldAccess = (IWorldAccess) this.worldAccesses.get(k);
            iWorldAccess.sendBlockBreakProgress(i, blockPos, j);
            k++;
        }
    }

    public boolean isAirBlock(BlockPos blockPos) {
        return this.getBlockState(blockPos).getBlock().getMaterial() == Material.AIR;
    }

    public float getStarBrightness(float f) {
        float celestialAngle = this.getCelestialAngle(f);
        float f2 = 1.0F - (MathHelper.cos(celestialAngle * (float) Math.PI * 2.0F) * 2.0F + 0.25F);
        f2 = MathHelper.clamp(f2, 0.0F, 1.0F);
        return f2 * f2 * 0.5F;
    }

    public boolean extendedLevelsInChunkCache() {
        return false;
    }

    public boolean isFindingSpawnPoint() {
        return this.findingSpawnPoint;
    }

    protected void onEntityAdded(Entity entity) {
        getLegacyWorldFlowMarker();

        int i = 0;

        while (i < this.worldAccesses.size()) {
            ((IWorldAccess) this.worldAccesses.get(i)).onEntityAdded(entity);
            i++;
        }
    }

    public boolean canSnowAt(BlockPos blockPos, boolean flag) {
        BiomeGenBase biomeGenForCoords = this.getBiomeGenForCoords(blockPos);
        float floatTemperature = biomeGenForCoords.getFloatTemperature(blockPos);
        if (floatTemperature > 0.15F) {
            return false;
        }

        if (!flag) {
            return true;
        }

        if (blockPos.getY() >= 0
                && blockPos.getY() < 256
                && this.getLightFor(EnumSkyBlock.BLOCK, blockPos) < 10) {
            Block block = this.getBlockState(blockPos).getBlock();
            if (block.getMaterial() == Material.AIR
                    && Blocks.snow_layer.canPlaceBlockAt(this, blockPos)) {
                return true;
            }
        }

        return false;
    }

    public boolean isDaytime() {
        try {
            if (this.skylightSubtracted < 4) {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw (reportedException);
        }

        return false;
    }

    public RayTraceResult rayTraceBlocks(Vec3 vec3, Vec3 vec3Value, boolean flag) {
        return this.rayTraceBlocks(vec3, vec3Value, flag, false, false);
    }

    public void scheduleBlockUpdate(BlockPos blockPos, Block block, int i, int j) {}

    public long getTotalWorldTime() {
        return this.worldInfo.getWorldTotalTime();
    }

    public BlockPos getPrecipitationHeight(BlockPos blockPos) {
        return this.getChunkFromBlockCoords(blockPos).getPrecipitationHeight(blockPos);
    }

    public TileEntity getTileEntity(BlockPos blockPos) {
        getLegacyWorldFlowMarker();

        if (!this.isValid(blockPos)) {
            return null;
        }

        TileEntity tileEntity = null;
        if (this.processingLoadedTiles) {
            for (int i = 0; i < this.addedTileEntityList.size(); i++) {
                TileEntity tileEntityValue = this.addedTileEntityList.get(i);
                if (!tileEntityValue.isInvalid() && tileEntityValue.getPos().equals(blockPos)) {
                    tileEntity = tileEntityValue;
                    break;
                }
            }
        }

        if (tileEntity == null) {
            tileEntity =
                    this.getChunkFromBlockCoords(blockPos)
                            .getTileEntity(blockPos, ChunkEnumCreateEntityType.IMMEDIATE);
        }

        if (tileEntity == null) {
            int j = 0;

            while (j < this.addedTileEntityList.size()) {
                TileEntity tileEntityValue2 = this.addedTileEntityList.get(j);
                if (!tileEntityValue2.isInvalid() && tileEntityValue2.getPos().equals(blockPos)) {
                    tileEntity = tileEntityValue2;

                    break;
                }

                j++;
            }
        }

        return tileEntity;
    }
}
