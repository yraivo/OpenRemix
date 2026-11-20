package net.minecraft.world.chunk;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.ClassInheratanceMultiMap;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxisDirection;
import net.minecraft.util.FacingPlane;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderDebug;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Chunk {
    private long lastSaveTime;

    private int heightMapMinimum;
    private boolean isGapLightingUpdated;
    private ExtendedBlockStorage[] storageArrays;
    private boolean isTerrainPopulated;

    private World worldObj;
    private boolean isModified;
    private Map chunkTileEntityMap;
    private boolean isChunkLoaded;
    private boolean hasBeenTicked;

    private boolean[] updateSkylightColumns;
    public int zPosition;
    private int queuedLightChecks;
    private ConcurrentLinkedQueue tileEntityPosQueue;
    private int[] heightMap;
    private ClassInheratanceMultiMap[] entityLists;
    private static Logger logger;
    private static int[] Qk;
    private byte[] blockBiomeArray;
    private boolean isLightPopulated;
    private long inhabitedTime;
    private boolean hasEntities;
    private int[] precipitationHeightMap;
    public int xPosition;

    private Block getBlock0(int i, int j, int k) {
        Block aXz = Blocks.air;
        if (j >= 0 && j >> 4 < this.storageArrays.length) {
            ExtendedBlockStorage extendedBlockStorage = this.storageArrays[j >> 4];
            if (extendedBlockStorage != null) {
                try {
                    aXz = extendedBlockStorage.getBlockByExtId(i, j & 15, k);
                } catch (Throwable throwable) {
                    CrashReport crashReport =
                            CrashReport.makeCrashReport(throwable, "Getting block");
                    throw new ReportedException(crashReport);
                }
            }
        }

        return aXz;
    }

    public void setLastSaveTime(long apjValue) {
        this.lastSaveTime = apjValue;
    }

    public void addTileEntity(TileEntity tileEntity) {
        try {
            this.addTileEntity(tileEntity.getPos(), tileEntity);
            if (this.isChunkLoaded) {
                this.worldObj.addTileEntity(tileEntity);
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.world.chunk.Chunk.method_b7e52da193241b30710d(reportedException));
        }
    }

    public void addEntity(Entity entity) {
        this.hasEntities = true;
        int i = MathHelper.floor(entity.posX / 16.0);
        int j = MathHelper.floor(entity.posZ / 16.0);
        if (i != this.xPosition || j != this.zPosition) {
            net.minecraft.world.chunk.Chunk.logger.warn(
                    "Wrong location! ("
                            + i
                            + ", "
                            + j
                            + ") should be ("
                            + this.xPosition
                            + ", "
                            + this.zPosition
                            + "), "
                            + entity,
                    new Object[] {entity});
            entity.setDead();
        }

        int k = MathHelper.floor(entity.posY / 16.0);
        if (k < 0) {
            k = 0;
        }

        if (k >= this.entityLists.length) {
            k = this.entityLists.length - 1;
        }

        entity.addedToChunk = true;
        entity.chunkCoordX = this.xPosition;
        entity.chunkCoordY = k;
        entity.chunkCoordZ = this.zPosition;
        this.entityLists[k].add(entity);
    }

    public long getInhabitedTime() {
        return this.inhabitedTime;
    }

    public boolean isLoaded() {
        return this.isChunkLoaded;
    }

    public Chunk(World world, int i, int j) {
        super();
        int[] initialFlowMarker = Wj();

        int[] aint1 = initialFlowMarker;

        this.storageArrays = new ExtendedBlockStorage[16];
        int[] aint = aint1;
        this.blockBiomeArray = new byte[256];
        this.precipitationHeightMap = new int[256];
        this.updateSkylightColumns = new boolean[256];
        this.chunkTileEntityMap = Maps.newHashMap();
        this.queuedLightChecks = 4096;
        this.tileEntityPosQueue = Queues.newConcurrentLinkedQueue();
        this.entityLists = new ClassInheratanceMultiMap[16];
        this.worldObj = world;
        this.xPosition = i;
        this.zPosition = j;
        this.heightMap = new int[256];
        int k = 0;

        while (k < this.entityLists.length) {
            this.entityLists[k] = new ClassInheratanceMultiMap(Entity.class);
            k++;
            if (aint != null) {
                break;
            }
        }

        Arrays.fill(this.precipitationHeightMap, -999);
        Arrays.fill(this.blockBiomeArray, (byte) -1);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(new int[5]);
        }
    }

    public Block getBlock(BlockPos blockPos) {
        try {
            return this.getBlock0(blockPos.getX() & 15, blockPos.getY(), blockPos.getZ() & 15);
        } catch (ReportedException reportedException) {
            CrashReportCategory crashReportCategory =
                    reportedException.getCrashReport().makeCategory("Block being got");
            crashReportCategory.addCrashSectionCallable(
                    "Location", new ChunkBlockCrashLocation(this, blockPos));
            throw reportedException;
        }
    }

    public static int[] Wj() {
        return Qk;
    }

    public boolean canSeeSky(BlockPos blockPos) {
        int i = blockPos.getX() & 15;
        int j = blockPos.getY();
        int k = blockPos.getZ() & 15;
        return j >= this.heightMap[k << 4 | i];
    }

    public Block getBlock(int i, int j, int k) {
        try {
            return this.getBlock0(i & 15, j, k & 15);
        } catch (ReportedException reportedException) {
            CrashReportCategory crashReportCategory =
                    reportedException.getCrashReport().makeCategory("Block being got");
            crashReportCategory.addCrashSectionCallable(
                    "Location", new ChunkLocalBlockCrashLocation(this, i, j, k));
            throw reportedException;
        }
    }

    public void addTileEntity(BlockPos blockPos, TileEntity tileEntity) {
        tileEntity.setWorldObj(this.worldObj);
        tileEntity.setPos(blockPos);
        if (this.getBlock(blockPos) instanceof net.minecraft.block.ITileEntityProvider) {
            if (((boolean)
                    (((java.util.Map) (this.chunkTileEntityMap))
                            .containsKey(((java.lang.Object) (blockPos)))))) {
                ((net.minecraft.tileentity.TileEntity)
                                ((java.lang.Object)
                                        (((java.util.Map) (this.chunkTileEntityMap))
                                                .get(((java.lang.Object) (blockPos))))))
                        .invalidate();
            }

            tileEntity.validate();
            ((java.util.Map) (this.chunkTileEntityMap))
                    .put(((java.lang.Object) (blockPos)), ((java.lang.Object) (tileEntity)));
        }
    }

    public void setChunkModified() {
        this.isModified = true;
    }

    // region String decoding support

    // endregion

    public int[] getHeightMap() {
        return this.heightMap;
    }

    public void onChunkLoad() {
        int[] aint1 = Wj();
        this.isChunkLoaded = true;
        this.worldObj.addTileEntities(this.chunkTileEntityMap.values());
        int[] aint = aint1;
        int i = 0;

        while (i < this.entityLists.length) {
            Iterator iterator = this.entityLists[i].iterator();

            while (true) {
                if (iterator.hasNext()) {
                    Entity entity = (Entity) iterator.next();
                    entity.onChunkLoaded();
                    if (aint == null) {
                        continue;
                    }
                }

                this.worldObj.loadEntities(this.entityLists[i]);
                i++;
                if (aint != null) {
                    return;
                }
                break;
            }
        }
    }

    public void removeEntity(Entity entity) {
        this.removeEntityAtIndex(entity, entity.chunkCoordY);
    }

    public void setHeightMap(int[] aint) {
        int[] aint1 = Wj();
        if (this.heightMap.length != aint.length) {
            net.minecraft.world.chunk.Chunk.logger.warn(
                    "Could not set level chunk heightmap, array length is "
                            + aint.length
                            + " instead of "
                            + this.heightMap.length);
            if (aint1 == null) {
                return;
            }
        }

        int i = 0;

        while (i < this.heightMap.length) {
            this.heightMap[i] = aint[i];
            i++;
            if (aint1 != null) {
                break;
            }
        }
    }

    public ClassInheratanceMultiMap[] getEntityLists() {
        return this.entityLists;
    }

    public void setInhabitedTime(long inhabitedTimeValue) {
        this.inhabitedTime = inhabitedTimeValue;
    }

    public void setLightPopulated(boolean isLightPopulatedValue) {
        this.isLightPopulated = isLightPopulatedValue;
    }

    public void setLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos, int i) {
        int j = blockPos.getX() & 15;
        int k = blockPos.getY();
        int[] aint1 = Wj();
        int l = blockPos.getZ() & 15;
        ExtendedBlockStorage extendedBlockStorage = this.storageArrays[k >> 4];
        int[] aint = aint1;
        if (extendedBlockStorage == null) {
            extendedBlockStorage =
                    this.storageArrays[k >> 4] =
                            new ExtendedBlockStorage(
                                    k >> 4 << 4, !this.worldObj.provider.getHasNoSky());
            this.generateSkylightMap();
        }

        this.isModified = true;
        if (enumSkyBlock == EnumSkyBlock.SKY) {
            if (this.worldObj.provider.getHasNoSky()) {
                return;
            }

            extendedBlockStorage.setExtSkylightValue(j, k & 15, l, i);
            if (aint == null) {
                return;
            }
        }

        if (enumSkyBlock == EnumSkyBlock.BLOCK) {
            extendedBlockStorage.setExtBlocklightValue(j, k & 15, l, i);
        }
    }

    private void recheckGaps(boolean flag) {
        int[] aint1 = Wj();
        this.worldObj.theProfiler.startSection("recheckGaps");
        int[] aint = aint1;
        if (this.worldObj.isAreaLoaded(
                new BlockPos(this.xPosition * 16 + 8, 0, this.zPosition * 16 + 8), 16)) {
            int i = 0;

            while (i < 16) {
                int j = 0;

                while (j < 16) {
                    if (this.updateSkylightColumns[i + j * 16]) {
                        this.updateSkylightColumns[i + j * 16] = false;
                        int k = this.getHeight(i, j);
                        int l = this.xPosition * 16 + i;
                        int i1 = this.zPosition * 16 + j;
                        int j1 = Integer.MAX_VALUE;

                        for (java.util.Iterator<?> __recoveryIterator171_14264 =
                                        (FacingPlane.HORIZONTAL).iterator();
                                (__recoveryIterator171_14264).hasNext(); ) {
                            Facing facing = (Facing) (__recoveryIterator171_14264).next();
                            j1 =
                                    Math.min(
                                            j1,
                                            this.worldObj.getChunksLowestHorizon(
                                                    l + facing.getFrontOffsetX(),
                                                    i1 + facing.getFrontOffsetZ()));
                            if (aint != null) {
                                break;
                            }
                        }

                        this.checkSkylightNeighborHeight(l, i1, j1);

                        for (java.util.Iterator<?> __recoveryIterator171_14718 =
                                        (FacingPlane.HORIZONTAL).iterator();
                                (__recoveryIterator171_14718).hasNext(); ) {
                            Facing facingValue = (Facing) (__recoveryIterator171_14718).next();
                            this.checkSkylightNeighborHeight(
                                    l + facingValue.getFrontOffsetX(),
                                    i1 + facingValue.getFrontOffsetZ(),
                                    k);
                            if (aint != null) {
                                break;
                            }
                        }

                        if (flag) {
                            this.worldObj.theProfiler.endSection();
                            return;
                        }
                    }

                    j++;
                    if (aint != null) {
                        break;
                    }
                }

                i++;
                if (aint != null) {
                    break;
                }
            }

            this.isGapLightingUpdated = false;
        }

        this.worldObj.theProfiler.endSection();
    }

    private void relightBlock(int i, int j, int k) {
        int[] aint1 = Wj();
        int l = this.heightMap[k << 4 | i] & 0xFF;
        int[] aint = aint1;
        int i1 = l;
        if (j > l) {
            i1 = j;
        }

        while (i1 > 0 && this.getBlockLightOpacity(i, i1 - 1, k) == 0) {
            i1--;
            if (aint != null) {
                break;
            }
        }

        if (i1 != l) {
            this.worldObj.markBlocksDirtyVertical(
                    i + this.xPosition * 16, k + this.zPosition * 16, i1, l);
            this.heightMap[k << 4 | i] = i1;
            int j1 = this.xPosition * 16 + i;
            int k1 = this.zPosition * 16 + k;
            if (!this.worldObj.provider.getHasNoSky()) {
                label113:
                {
                    if (i1 < l) {
                        int l1 = i1;

                        do {
                            if (l1 >= l) {
                                break label113;
                            }

                            ExtendedBlockStorage extendedBlockStorage = this.storageArrays[l1 >> 4];
                            if (extendedBlockStorage != null) {
                                extendedBlockStorage.setExtSkylightValue(i, l1 & 15, k, 15);
                                this.worldObj.notifyLightSet(
                                        new BlockPos(
                                                (this.xPosition << 4) + i,
                                                l1,
                                                (this.zPosition << 4) + k));
                            }

                            l1++;
                        } while (aint == null);
                    }

                    int j2 = l;

                    while (j2 < i1) {
                        ExtendedBlockStorage extendedBlockStorageValue =
                                this.storageArrays[j2 >> 4];
                        if (extendedBlockStorageValue != null) {
                            extendedBlockStorageValue.setExtSkylightValue(i, j2 & 15, k, 0);
                            this.worldObj.notifyLightSet(
                                    new BlockPos(
                                            (this.xPosition << 4) + i,
                                            j2,
                                            (this.zPosition << 4) + k));
                        }

                        j2++;
                        if (aint != null) {
                            break;
                        }
                    }
                }

                int k2 = 15;

                while (i1 > 0 && k2 > 0) {
                    int blockLightOpacity = this.getBlockLightOpacity(i, --i1, k);
                    if (blockLightOpacity == 0) {
                        blockLightOpacity = 1;
                    }

                    k2 -= blockLightOpacity;
                    if (k2 < 0) {
                        k2 = 0;
                    }

                    ExtendedBlockStorage extendedBlockStorageValue2 = this.storageArrays[i1 >> 4];
                    if (extendedBlockStorageValue2 != null) {
                        extendedBlockStorageValue2.setExtSkylightValue(i, i1 & 15, k, k2);
                    }

                    if (aint != null) {
                        break;
                    }
                }
            }

            int l2 = this.heightMap[k << 4 | i];
            int i3 = l;
            int j3 = l2;
            if (l2 < l) {
                i3 = l2;
                j3 = l;
            }

            if (l2 < this.heightMapMinimum) {
                this.heightMapMinimum = l2;
            }

            if (!this.worldObj.provider.getHasNoSky()) {
                for (java.util.Iterator<?> __recoveryIterator171_18586 =
                                (FacingPlane.HORIZONTAL).iterator();
                        (__recoveryIterator171_18586).hasNext(); ) {
                    Facing facing = (Facing) (__recoveryIterator171_18586).next();
                    this.updateSkylightNeighborHeight(
                            j1 + facing.getFrontOffsetX(), k1 + facing.getFrontOffsetZ(), i3, j3);
                    if (aint != null) {
                        break;
                    }
                }

                this.updateSkylightNeighborHeight(j1, k1, i3, j3);
            }

            this.isModified = true;
        }
    }

    protected void cj() {
        int i = this.getTopFilledSegment();
        int[] aint1 = Wj();
        this.heightMapMinimum = Integer.MAX_VALUE;
        int j = 0;
        int[] aint = aint1;

        label41:
        while (j < 16) {
            int k = 0;

            while (true) {
                if (k < 16) {
                    this.precipitationHeightMap[j + (k << 4)] = -999;
                    int l = i + 16;

                    label34:
                    {
                        do {
                            if (l <= 0) {
                                break label34;
                            }

                            Block block = this.getBlock0(j, l - 1, k);
                            if (block.getLightOpacity() != 0) {
                                break;
                            }

                            l--;
                        } while (aint == null);

                        this.heightMap[k << 4 | j] = l;
                        if (l < this.heightMapMinimum) {
                            this.heightMapMinimum = l;
                        }
                    }

                    k++;
                    if (aint == null) {
                        continue;
                    }
                }

                j++;
                if (aint != null) {
                    break label41;
                }
                break;
            }
        }

        this.isModified = true;
    }

    public int getLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
        int i = blockPos.getX() & 15;
        int j = blockPos.getY();
        int k = blockPos.getZ() & 15;
        ExtendedBlockStorage extendedBlockStorage = this.storageArrays[j >> 4];
        return extendedBlockStorage == null
                ? (this.canSeeSky(blockPos) ? enumSkyBlock.defaultLightValue : 0)
                : (enumSkyBlock == EnumSkyBlock.SKY
                        ? (this.worldObj.provider.getHasNoSky()
                                ? 0
                                : extendedBlockStorage.getExtSkylightValue(i, j & 15, k))
                        : (enumSkyBlock == EnumSkyBlock.BLOCK
                                ? extendedBlockStorage.getExtBlocklightValue(i, j & 15, k)
                                : enumSkyBlock.defaultLightValue));
    }

    public void getEntitiesWithinAABBForEntity(
            Entity entity, BoundingBox boundingBox, List list, Predicate predicate) {
        int i = MathHelper.floor((boundingBox.minY - 2.0) / 16.0);
        int j = MathHelper.floor((boundingBox.maxY + 2.0) / 16.0);
        int[] aint1 = Wj();
        i = MathHelper.clamp(i, 0, this.entityLists.length - 1);
        j = MathHelper.clamp(j, 0, this.entityLists.length - 1);
        int[] aint = aint1;
        int k = i;

        while (k <= j) {
            Iterator iterator = this.entityLists[k].iterator();

            while (true) {
                if (iterator.hasNext()) {
                    Entity entityValue = (Entity) iterator.next();
                    if (entityValue != entity
                            && entityValue.getEntityBoundingBox().intersectsWith(boundingBox)
                            && (predicate == null || predicate.apply(entityValue))) {
                        list.add(entityValue);
                        Entity[] entityArray = entityValue.getParts();
                        if (entityArray != null) {
                            int l = 0;

                            while (l < entityArray.length) {
                                entityValue = entityArray[l];
                                if (entityValue != entity
                                        && entityValue
                                                .getEntityBoundingBox()
                                                .intersectsWith(boundingBox)
                                        && (predicate == null || predicate.apply(entityValue))) {
                                    list.add(entityValue);
                                }

                                l++;
                                if (aint != null) {
                                    break;
                                }
                            }
                        }
                    }

                    if (aint == null) {
                        continue;
                    }
                }

                k++;
                if (aint != null) {
                    return;
                }
                break;
            }
        }
    }

    public int getHeight(int p171_0, int p171_1) {
        return this.heightMap[p171_1 << 4 | p171_0];
    }

    private void method_a4bcfe686dfb14d08471(Facing facing) {
        int[] aint = Wj();
        if (this.isTerrainPopulated) {
            if (facing == Facing.EAST) {
                int i = 0;

                do {
                    if (i >= 16) {
                        return;
                    }

                    this.b(15, i);
                    i++;
                } while (aint == null);
            }

            if (facing == Facing.WEST) {
                int j = 0;

                do {
                    if (j >= 16) {
                        return;
                    }

                    this.b(0, j);
                    j++;
                } while (aint == null);
            }

            if (facing == Facing.SOUTH) {
                int k = 0;

                do {
                    if (k >= 16) {
                        return;
                    }

                    this.b(k, 15);
                    k++;
                } while (aint == null);
            }

            if (facing == Facing.NORTH) {
                int l = 0;

                while (l < 16) {
                    this.b(l, 0);
                    l++;
                    if (aint != null) {
                        break;
                    }
                }
            }
        }
    }

    public boolean isEmpty() {
        return false;
    }

    public World getWorld() {
        return this.worldObj;
    }

    public BiomeGenBase getBiome(BlockPos blockPos, WorldChunkManager worldChunkManager) {
        int i = blockPos.getX() & 15;
        int j = blockPos.getZ() & 15;
        int k = this.blockBiomeArray[j << 4 | i] & 255;
        if (k == 255) {
            BiomeGenBase biomeGenBase =
                    worldChunkManager.getBiomeGenerator(blockPos, BiomeGenBase.plains);
            k = biomeGenBase.biomeID;
            this.blockBiomeArray[j << 4 | i] = (byte) (k & 0xFF);
        }

        BiomeGenBase biomeGenBaseValue = BiomeGenBase.getBiome(k);
        return biomeGenBaseValue == null ? BiomeGenBase.plains : biomeGenBaseValue;
    }

    public boolean isTerrainPopulated() {
        return this.isTerrainPopulated;
    }

    public void removeTileEntity(BlockPos blockPos) {
        if (this.isChunkLoaded) {
            net.minecraft.tileentity.TileEntity tileEntity =
                    (net.minecraft.tileentity.TileEntity) this.chunkTileEntityMap.remove(blockPos);

            try {
                if (tileEntity != null) {
                    tileEntity.invalidate();
                }
            } catch (net.minecraft.util.ReportedException reportedException) {
                throw remix.compat.compiler.JvmThrowTypes.flow(
                        net.minecraft.world.chunk.Chunk.method_b7e52da193241b30710d(
                                reportedException));
            }
        }
    }

    public int getHeight(BlockPos blockPos) {
        return this.getHeight(blockPos.getX() & 15, blockPos.getZ() & 15);
    }

    public void setStorageArrays(ExtendedBlockStorage[] extendedBlockStorageArray) {
        int[] aint = Wj();
        if (this.storageArrays.length != extendedBlockStorageArray.length) {
            net.minecraft.world.chunk.Chunk.logger.warn(
                    "Could not set level chunk sections, array length is "
                            + extendedBlockStorageArray.length
                            + " instead of "
                            + this.storageArrays.length);
            if (aint == null) {
                return;
            }
        }

        int i = 0;

        while (i < this.storageArrays.length) {
            this.storageArrays[i] = extendedBlockStorageArray[i];
            i++;
            if (aint != null) {
                break;
            }
        }
    }

    public void resetRelightChecks() {
        this.queuedLightChecks = 0;
    }

    public byte[] getBiomeArray() {
        return this.blockBiomeArray;
    }

    public void getEntitiesOfTypeWithinAAAB(
            Class oclass, BoundingBox boundingBox, List list, Predicate predicate) {
        int i = MathHelper.floor((boundingBox.minY - 2.0) / 16.0);
        int j = MathHelper.floor((boundingBox.maxY + 2.0) / 16.0);
        int[] aint1 = Wj();
        i = MathHelper.clamp(i, 0, this.entityLists.length - 1);
        j = MathHelper.clamp(j, 0, this.entityLists.length - 1);
        int k = i;
        int[] aint = aint1;

        while (k <= j) {
            Iterator iterator = this.entityLists[k].getByClass(oclass).iterator();

            while (true) {
                if (iterator.hasNext()) {
                    Entity entity = (Entity) iterator.next();
                    if (entity.getEntityBoundingBox().intersectsWith(boundingBox)
                            && (predicate == null || predicate.apply(entity))) {
                        list.add(entity);
                    }

                    if (aint == null) {
                        continue;
                    }
                }

                k++;
                if (aint != null) {
                    return;
                }
                break;
            }
        }
    }

    static {
        b(null);
        net.minecraft.world.chunk.Chunk.logger = LogManager.getLogger();
    }

    public void fillChunk(byte[] abyte, int i, boolean flag) {
        int[] aint1 = Wj();
        int j = 0;
        int[] aint = aint1;
        int k = !this.worldObj.provider.getHasNoSky() ? 1 : 0;
        int l = 0;

        while (l < this.storageArrays.length) {
            {
                if ((i & 1 << l) != 0) {
                    if (this.storageArrays[l] == null) {
                        this.storageArrays[l] =
                                new ExtendedBlockStorage(
                                        l << 4, remix.compat.compiler.JvmBooleanSlots.fromInt(k));
                    }

                    char[] achar = this.storageArrays[l].getData();
                    int i1 = 0;

                    while (i1 < achar.length) {
                        achar[i1] = (char) ((abyte[j + 1] & 255) << 8 | abyte[j] & 0xFF);
                        j += 2;
                        i1++;
                        if (aint != null) {
                            break;
                        }
                    }

                    if (!(aint == null)) {

                        if (flag && this.storageArrays[l] != null) {
                            this.storageArrays[l] = null;
                        }
                    }
                } else {

                    if (flag && this.storageArrays[l] != null) {
                        this.storageArrays[l] = null;
                    }
                }
            }

            l++;
            if (aint != null) {
                break;
            }
        }

        l = 0;

        while (l < this.storageArrays.length) {
            if ((i & 1 << l) != 0 && this.storageArrays[l] != null) {
                NibbleArray blocklightArray = this.storageArrays[l].getBlocklightArray();
                System.arraycopy(
                        abyte, j, blocklightArray.getData(), 0, blocklightArray.getData().length);
                j += blocklightArray.getData().length;
            }

            l++;
            if (aint != null) {
                break;
            }
        }

        if (k != 0) {
            l = 0;

            while (l < this.storageArrays.length) {
                if ((i & 1 << l) != 0 && this.storageArrays[l] != null) {
                    NibbleArray skylightArray = this.storageArrays[l].getSkylightArray();
                    System.arraycopy(
                            abyte, j, skylightArray.getData(), 0, skylightArray.getData().length);
                    j += skylightArray.getData().length;
                }

                l++;
                if (aint != null) {
                    break;
                }
            }
        }

        if (flag) {
            System.arraycopy(abyte, j, this.blockBiomeArray, 0, this.blockBiomeArray.length);
            int j1 = j + this.blockBiomeArray.length;
        }

        l = 0;

        while (l < this.storageArrays.length) {
            if (this.storageArrays[l] != null && (i & 1 << l) != 0) {
                this.storageArrays[l].l();
            }

            l++;
            if (aint != null) {
                break;
            }
        }

        this.isLightPopulated = true;
        this.isTerrainPopulated = true;
        this.cj();

        for (java.util.Iterator<?> __recoveryIterator171_36434 =
                        (this.chunkTileEntityMap.values()).iterator();
                (__recoveryIterator171_36434).hasNext(); ) {
            TileEntity tileEntity = (TileEntity) (__recoveryIterator171_36434).next();
            tileEntity.updateContainingBlockInfo();
            if (aint != null) {
                break;
            }
        }
    }

    public void setChunkLoaded(boolean isChunkLoadedValue) {
        this.isChunkLoaded = isChunkLoadedValue;
    }

    public Chunk(World world, ChunkPrimer chunkPrimer, int i, int j) {
        this(world, chunkPrimer, i, j, Wj());
    }

    private Chunk(World world, ChunkPrimer chunkPrimer, int i, int j, int[] initialFlowMarker) {
        this(world, i, j);

        int[] aint1 = initialFlowMarker;

        int[] aint = aint1;
        short short1 = 256;
        int k = !world.provider.getHasNoSky() ? 1 : 0;
        int l = 0;

        while (l < 16) {
            int i1 = 0;

            while (true) {
                if (i1 < 16) {
                    int j1 = 0;

                    while (j1 < short1) {
                        int k1 = l * short1 * 16 | i1 * short1 | j1;
                        BlockState blockState = chunkPrimer.getBlockState(k1);
                        if (blockState.getBlock().getMaterial() != Material.AIR) {
                            int l1 = j1 >> 4;
                            if (this.storageArrays[l1] == null) {
                                this.storageArrays[l1] =
                                        new ExtendedBlockStorage(
                                                l1 << 4,
                                                remix.compat.compiler.JvmBooleanSlots.fromInt(k));
                            }

                            this.storageArrays[l1].set(l, j1 & 15, i1, blockState);
                        }

                        j1++;
                        if (aint != null) {
                            break;
                        }
                    }

                    i1++;
                    if (aint == null) {
                        continue;
                    }
                }

                l++;
                if (aint != null) {
                    return;
                }
                break;
            }
        }
    }

    private void np() {
        int[] aint1 = Wj();
        int i = 0;
        int[] aint = aint1;

        while (i < this.updateSkylightColumns.length) {
            this.updateSkylightColumns[i] = true;
            i++;
            if (aint != null) {
                break;
            }
        }

        this.recheckGaps(false);
    }

    private TileEntity createNewTileEntity(BlockPos blockPos) {
        net.minecraft.block.Block block = this.getBlock(blockPos);

        try {
            if (!block.hasTileEntity()) {
                return null;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.world.chunk.Chunk.method_b7e52da193241b30710d(reportedException));
        }

        return ((net.minecraft.block.ITileEntityProvider) block)
                .createNewTileEntity(this.worldObj, this.getBlockMetadata(blockPos));
    }

    public void setModified(boolean isModifiedValue) {
        this.isModified = isModifiedValue;
    }

    public BlockState getBlockState(BlockPos blockPos) {
        if (this.worldObj.getWorldType() == WorldType.DEBUG_WORLD) {
            BlockState blockState = null;
            if (blockPos.getY() == 60) {
                blockState = Blocks.barrier.getDefaultState();
            }

            if (blockPos.getY() == 70) {
                blockState = ChunkProviderDebug.func_177461_b(blockPos.getX(), blockPos.getZ());
            }

            return blockState == null ? Blocks.air.getDefaultState() : blockState;
        } else {
            try {
                if (blockPos.getY() >= 0 && blockPos.getY() >> 4 < this.storageArrays.length) {
                    ExtendedBlockStorage extendedBlockStorage =
                            this.storageArrays[blockPos.getY() >> 4];
                    if (extendedBlockStorage != null) {
                        int j = blockPos.getX() & 15;
                        int k = blockPos.getY() & 15;
                        int i = blockPos.getZ() & 15;
                        return extendedBlockStorage.get(j, k, i);
                    }
                }

                return Blocks.air.getDefaultState();
            } catch (Throwable throwable) {
                CrashReport crashReport =
                        CrashReport.makeCrashReport(throwable, "Getting block state");
                CrashReportCategory crashReportCategory =
                        crashReport.makeCategory("Block being got");
                crashReportCategory.addCrashSectionCallable(
                        "Location", new ChunkBlockStateCrashLocation(this, blockPos));
                throw new ReportedException(crashReport);
            }
        }
    }

    private int getBlockMetadata(int p171_0, int p171_1, int p171_2) {
        try {
            if (p171_1 >> 4 >= this.storageArrays.length) {
                return 0;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.world.chunk.Chunk.method_b7e52da193241b30710d(reportedException));
        }

        net.minecraft.world.chunk.storage.ExtendedBlockStorage extendedBlockStorage =
                this.storageArrays[p171_1 >> 4];

        try {
            if (extendedBlockStorage != null) {
                return extendedBlockStorage.getExtBlockMetadata(p171_0, p171_1 & 15, p171_2);
            }
        } catch (net.minecraft.util.ReportedException reportedExceptionValue) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    net.minecraft.world.chunk.Chunk.method_b7e52da193241b30710d(
                            reportedExceptionValue));
        }

        return 0;
    }

    public void onChunkUnload() {
        int[] aint1 = Wj();
        this.isChunkLoaded = false;
        int[] aint = aint1;

        for (java.util.Iterator<?> __recoveryIterator171_43410 =
                        (this.chunkTileEntityMap.values()).iterator();
                (__recoveryIterator171_43410).hasNext(); ) {
            TileEntity tileEntity = (TileEntity) (__recoveryIterator171_43410).next();
            this.worldObj.markTileEntityForRemoval(tileEntity);
            if (aint != null) {
                break;
            }
        }

        int i = 0;

        while (i < this.entityLists.length) {
            this.worldObj.unloadEntities(this.entityLists[i]);
            i++;
            if (aint != null) {
                break;
            }
        }
    }

    private boolean b(int i, int j) {
        BlockPos blockPos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
        int[] aint1 = Wj();
        int k = this.getTopFilledSegment();
        int[] aint = aint1;
        byte b0 = 0;
        byte b1 = 0;
        int l = k + 16 - 1;

        while (l > 63 || l > 0 && b1 == 0) {
            BlockPos blockPosValue = blockPos.add(i, l, j);
            int blockLightOpacity = this.getBlockLightOpacity(blockPosValue);
            if (blockLightOpacity == 255 && l < 63) {
                b1 = 1;
            }

            {
                if (b0 == 0 && blockLightOpacity > 0) {
                    b0 = 1;
                    if (!(aint == null)) {

                        if (b0 != 0
                                && blockLightOpacity == 0
                                && !this.worldObj.checkLight(blockPosValue)) {
                            return false;
                        }
                    }
                } else {

                    if (b0 != 0
                            && blockLightOpacity == 0
                            && !this.worldObj.checkLight(blockPosValue)) {
                        return false;
                    }
                }
            }

            l--;
            if (aint != null) {
                break;
            }
        }

        while (l > 0) {
            BlockPos blockPosValue2 = blockPos.add(i, l, j);
            if (this.getBlock(blockPosValue2).getLightValue() > 0) {
                this.worldObj.checkLight(blockPosValue2);
            }

            l--;
            if (aint != null) {
                break;
            }
        }

        return true;
    }

    public Map getTileEntityMap() {
        return this.chunkTileEntityMap;
    }

    public boolean e(int i, int j) {
        int[] aint = Wj();
        if (i < 0) {
            i = 0;
        }

        if (j >= 256) {
            j = 255;
        }

        int k = i;

        while (k <= j) {
            ExtendedBlockStorage extendedBlockStorage = this.storageArrays[k >> 4];
            if (extendedBlockStorage != null && !extendedBlockStorage.isEmpty()) {
                return false;
            }

            k += 16;
            if (aint != null) {
                break;
            }
        }

        return true;
    }

    private void propagateSkylightOcclusion(int p171_0, int p171_1) {
        this.updateSkylightColumns[p171_0 + p171_1 * 16] = true;
        this.isGapLightingUpdated = true;
    }

    private void updateSkylightNeighborHeight(int i, int j, int k, int l) {
        int[] aint = Wj();
        if (l > k && this.worldObj.isAreaLoaded(new BlockPos(i, 0, j), 16)) {
            int i1 = k;

            while (i1 < l) {
                this.worldObj.checkLightFor(EnumSkyBlock.SKY, new BlockPos(i, i1, j));
                i1++;
                if (aint != null) {
                    break;
                }
            }

            this.isModified = true;
        }
    }

    public int getLowestHeight() {
        return this.heightMapMinimum;
    }

    public int getTopFilledSegment() {
        int[] aint1 = Wj();
        int i = this.storageArrays.length - 1;
        int[] aint = aint1;

        while (i >= 0) {
            if (this.storageArrays[i] != null) {
                return this.storageArrays[i].getYLocation();
            }

            i--;
            if (aint != null) {
                break;
            }
        }

        return 0;
    }

    private int getBlockLightOpacity(int p171_0, int p171_1, int p171_2) {
        return this.getBlock0(p171_0, p171_1, p171_2).getLightOpacity();
    }

    public ExtendedBlockStorage[] getBlockStorageArray() {
        return this.storageArrays;
    }

    public void removeEntityAtIndex(Entity entity, int p171_1) {
        if (p171_1 < 0) {
            p171_1 = 0;
        }

        if (p171_1 >= this.entityLists.length) {
            p171_1 = this.entityLists.length - 1;
        }

        this.entityLists[p171_1].remove(entity);
    }

    public BlockState setBlockState(BlockPos blockPos, BlockState blockState) {
        int i = blockPos.getX() & 15;
        int j = blockPos.getY();
        int[] aint1 = Wj();
        int k = blockPos.getZ() & 15;
        int l = k << 4 | i;
        int[] aint = aint1;
        if (j >= this.precipitationHeightMap[l] - 1) {
            this.precipitationHeightMap[l] = -999;
        }

        int i1 = this.heightMap[l];
        BlockState blockStateValue = this.getBlockState(blockPos);
        if (blockStateValue == blockState) {
            return null;
        }

        Block block = blockState.getBlock();
        Block blockValue = blockStateValue.getBlock();
        ExtendedBlockStorage extendedBlockStorage = this.storageArrays[j >> 4];
        byte b0 = 0;
        if (extendedBlockStorage == null) {
            if (block == Blocks.air) {
                return null;
            }

            extendedBlockStorage =
                    this.storageArrays[j >> 4] =
                            new ExtendedBlockStorage(
                                    j >> 4 << 4, !this.worldObj.provider.getHasNoSky());
            b0 = (byte) (j >= i1 ? 1 : 0);
        }

        extendedBlockStorage.set(i, j & 15, k, blockState);
        if (blockValue != block) {
            if (!this.worldObj.isRemote) {
                blockValue.breakBlock(this.worldObj, blockPos, blockStateValue);
                if (!(aint == null)) {

                    if (blockValue instanceof ITileEntityProvider) {
                        this.worldObj.removeTileEntity(blockPos);
                    }
                }
            } else {

                if (blockValue instanceof ITileEntityProvider) {
                    this.worldObj.removeTileEntity(blockPos);
                }
            }
        }

        if (extendedBlockStorage.getBlockByExtId(i, j & 15, k) != block) {
            return null;
        }

        label114:
        {
            if (b0 != 0) {
                this.generateSkylightMap();
                if (aint == null) {
                    break label114;
                }
            }

            int j1;
            int k1;
            label94:
            {
                j1 = block.getLightOpacity();
                k1 = blockValue.getLightOpacity();
                if (j1 > 0) {
                    if (j < i1) {
                        break label94;
                    }

                    this.relightBlock(i, j + 1, k);
                    if (aint == null) {
                        break label94;
                    }
                }

                if (j == i1 - 1) {
                    this.relightBlock(i, j, k);
                }
            }

            if (j1 != k1
                    && (j1 < k1
                            || this.getLightFor(EnumSkyBlock.SKY, blockPos) > 0
                            || this.getLightFor(EnumSkyBlock.BLOCK, blockPos) > 0)) {
                this.propagateSkylightOcclusion(i, k);
            }
        }

        if (blockValue instanceof ITileEntityProvider) {
            TileEntity tileEntity = this.getTileEntity(blockPos, ChunkEnumCreateEntityType.CHECK);
            if (tileEntity != null) {
                tileEntity.updateContainingBlockInfo();
            }
        }

        if (!this.worldObj.isRemote && blockValue != block) {
            block.onBlockAdded(this.worldObj, blockPos, blockState);
        }

        if (block instanceof ITileEntityProvider) {
            TileEntity tileEntityValue =
                    this.getTileEntity(blockPos, ChunkEnumCreateEntityType.CHECK);
            if (tileEntityValue == null) {
                tileEntityValue =
                        ((ITileEntityProvider) block)
                                .createNewTileEntity(
                                        this.worldObj, block.getMetaFromState(blockState));
                this.worldObj.setTileEntity(blockPos, tileEntityValue);
            }

            if (tileEntityValue != null) {
                tileEntityValue.updateContainingBlockInfo();
            }
        }

        this.isModified = true;
        return blockStateValue;
    }

    public Random getRandomWithSeed(long i) {
        long j =
                this.worldObj.getSeed()
                        + this.xPosition * this.xPosition * 4987142
                        + this.xPosition * 5947611;
        long k = this.zPosition * this.zPosition;

        return new Random(j + k * 4392871L + this.zPosition * 389711 ^ i);
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public boolean isPopulated() {
        return (this.hasBeenTicked && this.isTerrainPopulated && this.isLightPopulated);
    }

    // region String decoding support

    // endregion

    public TileEntity getTileEntity(
            BlockPos blockPos, ChunkEnumCreateEntityType chunkEnumCreateEntityType) {
        int[] aint1 = Wj();
        TileEntity tileEntity = (TileEntity) this.chunkTileEntityMap.get(blockPos);
        int[] aint = aint1;
        if (tileEntity == null) {
            if (chunkEnumCreateEntityType == ChunkEnumCreateEntityType.IMMEDIATE) {
                tileEntity = this.createNewTileEntity(blockPos);
                this.worldObj.setTileEntity(blockPos, tileEntity);
                if (aint == null) {
                    return tileEntity;
                }
            }

            if (chunkEnumCreateEntityType != ChunkEnumCreateEntityType.QUEUED) {
                return tileEntity;
            }

            this.tileEntityPosQueue.add(blockPos);
            if (aint == null) {
                return tileEntity;
            }
        }

        if (tileEntity.isInvalid()) {
            this.chunkTileEntityMap.remove(blockPos);
            return null;
        } else {
            return tileEntity;
        }
    }

    public void enqueueRelightChecks() {
        int[] aint1 = Wj();
        BlockPos blockPos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
        int[] aint = aint1;
        int i = 0;

        while (i < 8) {
            if (this.queuedLightChecks >= 4096) {
                return;
            }

            int j = this.queuedLightChecks % 16;
            int k = this.queuedLightChecks / 16 % 16;
            int l = this.queuedLightChecks / 256;
            this.queuedLightChecks++;
            int i1 = 0;

            while (true) {
                if (i1 < 16) {
                    BlockPos blockPosValue = blockPos.add(k, (j << 4) + i1, l);
                    int j1 = i1 != 0 && i1 != 15 && k != 0 && k != 15 && l != 0 && l != 15 ? 0 : 1;
                    if (this.storageArrays[j] == null && j1 != 0
                            || this.storageArrays[j] != null
                                    && this.storageArrays[j].getBlockByExtId(k, i1, l).getMaterial()
                                            == Material.AIR) {
                        for (Facing facing : Facing.values()) {
                            BlockPos blockPosValue2 = blockPosValue.offset(facing);
                            if (this.worldObj
                                            .getBlockState(blockPosValue2)
                                            .getBlock()
                                            .getLightValue()
                                    > 0) {
                                this.worldObj.checkLight(blockPosValue2);
                            }

                            if (aint != null) {
                                break;
                            }
                        }

                        this.worldObj.checkLight(blockPosValue);
                    }

                    i1++;
                    if (aint == null) {
                        continue;
                    }
                }

                i++;
                if (aint != null) {
                    return;
                }
                break;
            }
        }
    }

    public BlockPos getPrecipitationHeight(BlockPos blockPos) {
        int[] aint1 = Wj();
        int i = blockPos.getX() & 15;
        int j = blockPos.getZ() & 15;
        int k = i | j << 4;
        int[] aint = aint1;
        BlockPos blockPosValue =
                new BlockPos(blockPos.getX(), this.precipitationHeightMap[k], blockPos.getZ());
        if (blockPosValue.getY() == -999) {
            int l = this.getTopFilledSegment() + 15;
            blockPosValue = new BlockPos(blockPos.getX(), l, blockPos.getZ());
            int i1 = -1;

            while (blockPosValue.getY() > 0 && i1 == -1) {
                {
                    Block block = this.getBlock(blockPosValue);
                    Material material = block.getMaterial();
                    if (!material.blocksMovement() && !material.isLiquid()) {
                        blockPosValue = blockPosValue.offsetDown();
                        if (!(aint == null)) {

                            i1 = blockPosValue.getY() + 1;
                        }
                    } else {

                        i1 = blockPosValue.getY() + 1;
                    }
                }

                if (aint != null) {
                    break;
                }
            }

            this.precipitationHeightMap[k] = i1;
        }

        return new BlockPos(blockPos.getX(), this.precipitationHeightMap[k], blockPos.getZ());
    }

    public void populateChunk(
            IChunkProvider iChunkProvider, IChunkProvider iChunkProviderValue, int i, int j) {
        int[] aint;
        boolean flag;
        boolean flag1;
        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag6;
        boolean flag7;
        flag = iChunkProvider.chunkExists(i, j - 1);
        flag1 = iChunkProvider.chunkExists(i + 1, j);
        int[] aint1 = Wj();
        flag2 = iChunkProvider.chunkExists(i, j + 1);
        flag3 = iChunkProvider.chunkExists(i - 1, j);
        aint = aint1;
        flag4 = iChunkProvider.chunkExists(i - 1, j - 1);
        boolean flag5 = iChunkProvider.chunkExists(i + 1, j + 1);
        flag6 = iChunkProvider.chunkExists(i - 1, j + 1);
        flag7 = iChunkProvider.chunkExists(i + 1, j - 1);
        label74:
        if (flag1 && flag2 && flag5) {
            if (!this.isTerrainPopulated) {
                iChunkProvider.populate(iChunkProviderValue, i, j);
                if (aint == null) {
                    break label74;
                }

                Entity.setLegacyGlobalFlowMarker(new String[2]);
            }

            iChunkProvider.func_177460_a(iChunkProviderValue, this, i, j);
        }

        if (flag3 && flag2 && flag6) {
            Chunk chunk = iChunkProvider.provideChunk(i - 1, j);
            if (!chunk.isTerrainPopulated) {
                iChunkProvider.populate(iChunkProviderValue, i - 1, j);
                if (!(aint == null)) {

                    iChunkProvider.func_177460_a(iChunkProviderValue, chunk, i - 1, j);
                }
            } else {

                iChunkProvider.func_177460_a(iChunkProviderValue, chunk, i - 1, j);
            }
        }

        if (flag && flag1 && flag7) {
            Chunk chunkValue = iChunkProvider.provideChunk(i, j - 1);
            if (!chunkValue.isTerrainPopulated) {
                iChunkProvider.populate(iChunkProviderValue, i, j - 1);
                if (!(aint == null)) {

                    iChunkProvider.func_177460_a(iChunkProviderValue, chunkValue, i, j - 1);
                }
            } else {

                iChunkProvider.func_177460_a(iChunkProviderValue, chunkValue, i, j - 1);
            }
        }

        if (flag4 && flag && flag3) {
            Chunk chunkValue2 = iChunkProvider.provideChunk(i - 1, j - 1);
            if (!chunkValue2.isTerrainPopulated) {
                iChunkProvider.populate(iChunkProviderValue, i - 1, j - 1);
                if (aint == null) {
                    return;
                }
            }

            iChunkProvider.func_177460_a(iChunkProviderValue, chunkValue2, i - 1, j - 1);
        }
    }

    public void setTerrainPopulated(boolean isTerrainPopulatedValue) {
        this.isTerrainPopulated = isTerrainPopulatedValue;
    }

    public int setLight(BlockPos blockPos, int i) {
        int j = blockPos.getX() & 15;
        int k = blockPos.getY();
        int l = blockPos.getZ() & 15;
        ExtendedBlockStorage extendedBlockStorage = this.storageArrays[k >> 4];
        if (extendedBlockStorage != null) {
            int i1 =
                    this.worldObj.provider.getHasNoSky()
                            ? 0
                            : extendedBlockStorage.getExtSkylightValue(j, k & 15, l);
            i1 -= i;
            int j1 = extendedBlockStorage.getExtBlocklightValue(j, k & 15, l);
            if (j1 > i1) {
                i1 = j1;
            }

            return i1;
        } else {
            return !this.worldObj.provider.getHasNoSky() && i < EnumSkyBlock.SKY.defaultLightValue
                    ? EnumSkyBlock.SKY.defaultLightValue - i
                    : 0;
        }
    }

    public void tickChunk(boolean flag) {
        int[] aint = Wj();
        if (this.isGapLightingUpdated && !this.worldObj.provider.getHasNoSky() && !flag) {
            this.recheckGaps(this.worldObj.isRemote);
        }

        this.hasBeenTicked = true;
        if (!this.isLightPopulated && this.isTerrainPopulated) {
            this.func_150809_p();
        }

        while (!this.tileEntityPosQueue.isEmpty()) {
            BlockPos blockPos = (BlockPos) this.tileEntityPosQueue.poll();
            if (this.getTileEntity(blockPos, ChunkEnumCreateEntityType.CHECK) == null
                    && this.getBlock(blockPos).hasTileEntity()) {
                TileEntity tileEntity = this.createNewTileEntity(blockPos);
                this.worldObj.setTileEntity(blockPos, tileEntity);
                this.worldObj.markBlockRangeForRenderUpdate(blockPos, blockPos);
            }

            if (aint != null) {
                break;
            }
        }
    }

    public static void b(int[] aint) {
        Qk = aint;
    }

    public void setBiomeArray(byte[] abyte) {
        int[] aint = Wj();
        if (this.blockBiomeArray.length != abyte.length) {
            net.minecraft.world.chunk.Chunk.logger.warn(
                    "Could not set level chunk biomes, array length is "
                            + abyte.length
                            + " instead of "
                            + this.blockBiomeArray.length);
            if (aint == null) {
                return;
            }
        }

        int i = 0;

        while (i < this.blockBiomeArray.length) {
            this.blockBiomeArray[i] = abyte[i];
            i++;
            if (aint != null) {
                break;
            }
        }
    }

    public void setHasEntities(boolean hasEntitiesValue) {
        this.hasEntities = hasEntitiesValue;
    }

    public boolean isAtLocation(int p171_0, int p171_1) {
        return (p171_0 == this.xPosition && p171_1 == this.zPosition);
    }

    public ChunkCoordIntPair getChunkCoordIntPair() {
        return new net.minecraft.world.ChunkCoordIntPair(this.xPosition, this.zPosition);
    }

    public int getBlockMetadata(BlockPos blockPos) {
        return this.getBlockMetadata(blockPos.getX() & 15, blockPos.getY(), blockPos.getZ() & 15);
    }

    public void func_150809_p() {
        int[] aint1 = Wj();
        this.isTerrainPopulated = true;
        int[] aint = aint1;
        this.isLightPopulated = true;
        BlockPos blockPos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
        if (!this.worldObj.provider.getHasNoSky()) {
            if (this.worldObj.isAreaLoaded(blockPos.add(-1, 0, -1), blockPos.add(16, 63, 16))) {
                int i = 0;

                label54:
                while (i < 16) {
                    int j = 0;

                    while (j < 16) {
                        if (!this.b(i, j)) {
                            this.isLightPopulated = false;
                            if (aint == null) {
                                break label54;
                            }
                        }

                        j++;
                        if (aint != null) {
                            break;
                        }
                    }

                    i++;
                    if (aint != null) {
                        break;
                    }
                }

                if (!this.isLightPopulated) {
                    return;
                }

                for (java.util.Iterator<?> __recoveryIterator171_91096 =
                                (FacingPlane.HORIZONTAL).iterator();
                        (__recoveryIterator171_91096).hasNext(); ) {
                    Facing facing = (Facing) (__recoveryIterator171_91096).next();
                    int k = facing.getAxisDirection() == FacingAxisDirection.POSITIVE ? 16 : 1;
                    this.worldObj
                            .getChunkFromBlockCoords(blockPos.offset(facing, k))
                            .method_a4bcfe686dfb14d08471(facing.getOpposite());
                    if (aint != null) {
                        break;
                    }
                }

                this.np();
                if (aint == null) {
                    return;
                }
            }

            this.isLightPopulated = false;
        }
    }

    public void generateSkylightMap() {
        int[] aint1 = Wj();
        int i = this.getTopFilledSegment();
        int[] aint = aint1;
        this.heightMapMinimum = Integer.MAX_VALUE;
        int j = 0;

        label69:
        while (j < 16) {
            int k = 0;

            while (true) {
                if (k < 16) {
                    this.precipitationHeightMap[j + (k << 4)] = -999;
                    int l = i + 16;

                    label62:
                    {
                        do {
                            if (l <= 0) {
                                break label62;
                            }

                            if (this.getBlockLightOpacity(j, l - 1, k) != 0) {
                                break;
                            }

                            l--;
                        } while (aint == null);

                        this.heightMap[k << 4 | j] = l;
                        if (l < this.heightMapMinimum) {
                            this.heightMapMinimum = l;
                        }
                    }

                    if (!this.worldObj.provider.getHasNoSky()) {
                        l = 15;
                        int i1 = i + 16 - 1;

                        do {
                            int j1 = this.getBlockLightOpacity(j, i1, k);
                            if (j1 == 0 && l != 15) {
                                j1 = 1;
                            }

                            l -= j1;
                            if (l > 0) {
                                ExtendedBlockStorage extendedBlockStorage =
                                        this.storageArrays[i1 >> 4];
                                if (extendedBlockStorage != null) {
                                    extendedBlockStorage.setExtSkylightValue(j, i1 & 15, k, l);
                                    this.worldObj.notifyLightSet(
                                            new BlockPos(
                                                    (this.xPosition << 4) + j,
                                                    i1,
                                                    (this.zPosition << 4) + k));
                                }
                            }
                        } while (--i1 > 0 && l > 0);
                    }

                    k++;
                    if (aint == null) {
                        continue;
                    }
                }

                j++;
                if (aint != null) {
                    break label69;
                }
                break;
            }
        }

        this.isModified = true;
    }

    public boolean needsSaving(boolean flag) {
        if (flag) {
            if (this.hasEntities && this.worldObj.getTotalWorldTime() != this.lastSaveTime
                    || this.isModified) {
                return true;
            }
        } else if (this.hasEntities) {
            long totalWorldTime = this.worldObj.getTotalWorldTime();

            if (totalWorldTime >= this.lastSaveTime + 600L) {
                return true;
            }
        }

        return this.isModified;
    }

    public boolean isLightPopulated() {
        return this.isLightPopulated;
    }

    private void checkSkylightNeighborHeight(int i, int j, int k) {
        int[] aint1 = Wj();
        int l = this.worldObj.getHorizon(new BlockPos(i, 0, j)).getY();
        int[] aint = aint1;
        if (l > k) {
            this.updateSkylightNeighborHeight(i, j, k, l + 1);
            if (aint == null) {
                return;
            }
        }

        if (l < k) {
            this.updateSkylightNeighborHeight(i, j, l, k + 1);
        }
    }

    public int getBlockLightOpacity(BlockPos blockPos) {
        return this.getBlock(blockPos).getLightOpacity();
    }

    // region String decoding support

    // endregion
}
