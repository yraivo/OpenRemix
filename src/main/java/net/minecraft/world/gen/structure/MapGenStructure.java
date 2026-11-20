package net.minecraft.world.gen.structure;

import com.google.common.collect.Maps;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.storage.MapStorage;

import optifine.Reflector;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class MapGenStructure extends MapGenBase {
    protected Map structureMap = Maps.newHashMap();

    private LongHashMap structureLongMap = new LongHashMap();

    private MapGenStructureData structureData;

    protected List getCoordList() {
        return null;
    }

    public boolean generateStructure(
            World world, Random random, ChunkCoordIntPair chunkCoordIntPair) {
        int l = StructureComponent.cR();
        this.initializeStructureData(world);
        int j = (chunkCoordIntPair.chunkXPos << 4) + 8;
        int k = (chunkCoordIntPair.chunkZPos << 4) + 8;
        int i = l;
        byte b0 = 0;

        for (java.util.Iterator<?> __recoveryIterator171_1482 =
                        (this.structureMap.values()).iterator();
                (__recoveryIterator171_1482).hasNext(); ) {
            StructureStart structureStart = (StructureStart) (__recoveryIterator171_1482).next();
            if (structureStart.isSizeableStructure()
                    && structureStart.method_8f6348b78777a95640a1(chunkCoordIntPair)
                    && structureStart.getBoundingBox().intersectsWith(j, k, j + 15, k + 15)) {
                structureStart.generateStructure(
                        world, random, new StructureBoundingBox(j, k, j + 15, k + 15));
                structureStart.method_21c9d6746214a41e69d7(chunkCoordIntPair);
                b0 = 1;
                this.setStructureStart(
                        structureStart.getChunkPosX(),
                        structureStart.getChunkPosZ(),
                        structureStart);
            }

            if (i == 0) {
                break;
            }
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    public abstract String getStructureName();

    public abstract boolean canSpawnStructureAtCoords(int i, int j);

    private void setStructureStart(int i, int j, StructureStart structureStart) {
        this.structureData.writeInstance(structureStart.writeStructureComponentsToNBT(i, j), i, j);
        this.structureData.markDirty();
    }

    protected StructureStart w(BlockPos blockPos) {
        int j = StructureComponent.cR();
        Iterator iterator = this.structureMap.values().iterator();
        int i = j;

        while (iterator.hasNext()) {
            StructureStart structureStart = (StructureStart) iterator.next();
            if (structureStart.isSizeableStructure()
                    && structureStart.getBoundingBox().isVecInside(blockPos)) {
                for (java.util.Iterator<?> __recoveryIterator171_4290 =
                                (structureStart.getComponents()).iterator();
                        (__recoveryIterator171_4290).hasNext(); ) {
                    StructureComponent structureComponent =
                            (StructureComponent) (__recoveryIterator171_4290).next();
                    if (structureComponent.getBoundingBox().isVecInside(blockPos)) {
                        return structureStart;
                    }

                    if (i == 0) {
                        break;
                    }
                }
            }

            if (i == 0) {
                break;
            }
        }

        return null;
    }


    private static ReportedException method_7b8b77b0e24b2985ecf5(
            ReportedException reportedException) {
        return reportedException;
    }

    public boolean isPositionInStructure(World world, BlockPos blockPos) {
        this.initializeStructureData(world);

        for (java.util.Iterator<?> __recoveryIterator171_8727 =
                        (this.structureMap.values()).iterator();
                (__recoveryIterator171_8727).hasNext(); ) {
            StructureStart structureStart = (StructureStart) (__recoveryIterator171_8727).next();
            if (structureStart.isSizeableStructure()
                    && structureStart.getBoundingBox().isVecInside(blockPos)) {
                return true;
            }
        }

        return false;
    }

    protected final void recursiveGenerate(
            World world, int i, int j, int k, int l, ChunkPrimer chunkPrimer) {
        this.initializeStructureData(world);
        if (!this.structureLongMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(i, j))) {
            this.rand.nextInt();

            try {
                if (this.canSpawnStructureAtCoords(i, j)) {
                    StructureStart structureStart = this.getStructureStart(i, j);
                    this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(i, j), structureStart);
                    this.structureLongMap.add(ChunkCoordIntPair.chunkXZ2Int(i, j), structureStart);
                    this.setStructureStart(i, j, structureStart);
                }
            } catch (Throwable throwable) {
                CrashReport crashReport =
                        CrashReport.makeCrashReport(
                                throwable, "Exception preparing structure feature");
                CrashReportCategory crashReportCategory =
                        crashReport.makeCategory("Feature being prepared");
                crashReportCategory.addCrashSectionCallable(
                        "Is feature chunk", new MapGenStructureIsFeatureChunkCallable(this, i, j));
                crashReportCategory.addCrashSection("Chunk location", String.format("%d,%d", i, j));
                crashReportCategory.addCrashSectionCallable(
                        "Chunk pos hash", new MapGenStructureChunkPosHashCallable(this, i, j));
                crashReportCategory.addCrashSectionCallable(
                        "Structure type", new MapGenStructureStructureTypeCallable(this));
                throw new ReportedException(crashReport);
            }
        }
    }

    protected abstract StructureStart getStructureStart(int i, int j);

    public BlockPos getClosestStrongholdPos(World bhTValue, BlockPos blockPos) {
        int j1 = StructureComponent.cR();
        this.worldObj = bhTValue;
        this.initializeStructureData(bhTValue);
        this.rand.setSeed(bhTValue.getSeed());
        long j = this.rand.nextLong();
        long k = this.rand.nextLong();
        long l = (blockPos.getX() >> 4) * j;
        long i1 = (blockPos.getZ() >> 4) * k;
        int i = j1;
        this.rand.setSeed(l ^ i1 ^ bhTValue.getSeed());
        this.recursiveGenerate(
                bhTValue, blockPos.getX() >> 4, blockPos.getZ() >> 4, 0, 0, (ChunkPrimer) null);
        double MAX_VALUE = Double.MAX_VALUE;
        BlockPos blockPosValue = null;

        for (java.util.Iterator<?> __recoveryIterator171_12989 =
                        (this.structureMap.values()).iterator();
                (__recoveryIterator171_12989).hasNext(); ) {
            StructureStart structureStart = (StructureStart) (__recoveryIterator171_12989).next();
            if (structureStart.isSizeableStructure()) {
                StructureComponent structureComponent =
                        (StructureComponent) structureStart.getComponents().get(0);
                BlockPos blockPosValue2 = structureComponent.getBoundingBoxCenter();
                double d1 = blockPosValue2.distanceSq(blockPos);
                if (d1 < MAX_VALUE) {
                    MAX_VALUE = d1;
                    blockPosValue = blockPosValue2;
                }
            }

            if (i == 0) {
                break;
            }
        }

        if (blockPosValue != null) {
            return blockPosValue;
        }

        List list = this.getCoordList();
        if (list == null) {
            return null;
        }

        BlockPos blockPosValue3 = null;

        for (java.util.Iterator<?> __recoveryIterator171_13969 = (list).iterator();
                (__recoveryIterator171_13969).hasNext(); ) {
            BlockPos blockPosValue4 = (BlockPos) (__recoveryIterator171_13969).next();
            double d2 = blockPosValue4.distanceSq(blockPos);
            if (d2 < MAX_VALUE) {
                MAX_VALUE = d2;
                blockPosValue3 = blockPosValue4;
                if (i == 0) {
                    break;
                }
            }
        }

        return blockPosValue3;
    }

    public boolean method_51a343cb461860600ef7(BlockPos blockPos) {
        this.initializeStructureData(this.worldObj);
        return this.w(blockPos) != null;
    }

    private void initializeStructureData(World world) {
        int i = StructureComponent.cR();
        if (this.structureData == null) {
            {
                if (Reflector.Gu.exists()) {
                    MapStorage mapStorage =
                            (MapStorage) Reflector.call(world, Reflector.Gu, new Object[0]);
                    this.structureData =
                            (MapGenStructureData)
                                    mapStorage.loadData(
                                            MapGenStructureData.class, this.getStructureName());
                    if (!(i != 0)) {

                        this.structureData =
                                (MapGenStructureData)
                                        world.loadItemData(
                                                MapGenStructureData.class, this.getStructureName());
                    }
                } else {

                    this.structureData =
                            (MapGenStructureData)
                                    world.loadItemData(
                                            MapGenStructureData.class, this.getStructureName());
                }
            }

            if (this.structureData == null) {
                this.structureData = new MapGenStructureData(this.getStructureName());
                if (Reflector.Gu.exists()) {
                    MapStorage mapStorageValue =
                            (MapStorage) Reflector.call(world, Reflector.Gu, new Object[0]);
                    mapStorageValue.setData(this.getStructureName(), this.structureData);
                    if (i != 0) {
                        return;
                    }
                }

                world.setItemData(this.getStructureName(), this.structureData);
                if (i != 0) {
                    return;
                }
            }

            CompoundTag compoundTag = this.structureData.getTagCompound();

            for (java.util.Iterator<?> __recoveryIterator171_36982 =
                            (compoundTag.getKeySet()).iterator();
                    (__recoveryIterator171_36982).hasNext(); ) {
                String text = (String) (__recoveryIterator171_36982).next();
                NBTBase tag = compoundTag.getTag(text);
                if (tag.getId() == 10) {
                    CompoundTag compoundTagValue = (CompoundTag) tag;
                    if (compoundTagValue.hasKey("ChunkX") && compoundTagValue.hasKey("ChunkZ")) {
                        int j = compoundTagValue.getInteger("ChunkX");
                        int k = compoundTagValue.getInteger("ChunkZ");
                        StructureStart structureStart =
                                MapGenStructureIO.getStructureStart(compoundTagValue, world);
                        if (structureStart != null) {
                            this.structureMap.put(
                                    ChunkCoordIntPair.chunkXZ2Int(j, k), structureStart);
                            this.structureLongMap.add(
                                    ChunkCoordIntPair.chunkXZ2Int(j, k), structureStart);
                        }
                    }
                }

                if (i == 0) {
                    break;
                }
            }
        }
    }
}
