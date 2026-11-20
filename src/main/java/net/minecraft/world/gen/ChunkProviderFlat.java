package net.minecraft.world.gen;

import com.google.common.collect.Lists;

import net.minecraft.block.state.BlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChunkProviderFlat implements IChunkProvider {
    private World worldObj;
    private boolean hasDecoration;
    private BlockState[] cachedBlockIDs;
    private List structureGenerators;

    private FlatGeneratorInfo flatWorldGenInfo;
    private static String[] packedTextFragments;
    private WorldGenLakes lavaLakeGenerator;
    private Random random;

    private WorldGenLakes waterLakeGenerator;
    private boolean hasDungeons;

    public Chunk provideChunk(int i, int j) {
        ChunkPrimer chunkPrimer = new ChunkPrimer();
        MapGenBase.b();

        int l = 0;

        while (l < this.cachedBlockIDs.length) {
            BlockState blockState = this.cachedBlockIDs[l];
            if (blockState != null) {
                int i1 = 0;

                while (i1 < 16) {
                    int k = 0;

                    while (k < 16) {
                        chunkPrimer.setBlockState(i1, l, k, blockState);
                        k++;
                    }

                    i1++;
                }
            }

            l++;
        }

        for (java.util.Iterator<?> __recoveryIterator171_2281 =
                        (this.structureGenerators).iterator();
                (__recoveryIterator171_2281).hasNext(); ) {
            MapGenBase mapGenBase = (MapGenBase) (__recoveryIterator171_2281).next();
            mapGenBase.generate(this, this.worldObj, i, j, chunkPrimer);
        }

        Chunk chunk = new Chunk(this.worldObj, chunkPrimer, i, j);
        BiomeGenBase[] biomeGenBaseArray =
                this.worldObj
                        .getWorldChunkManager()
                        .loadBlockGeneratorData((BiomeGenBase[]) null, i * 16, j * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();
        int j1 = 0;

        while (j1 < abyte.length) {
            abyte[j1] = (byte) biomeGenBaseArray[j1].biomeID;
            j1++;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    public boolean unloadQueuedChunks() {
        return false;
    }

    public boolean func_177460_a(IChunkProvider iChunkProvider, Chunk chunk, int i, int j) {
        return false;
    }

    public String makeString() {
        return "FlatLevelSource";
    }

    public void populate(IChunkProvider iChunkProvider, int i, int j) {
        int k = i * 16;
        int l = j * 16;
        MapGenBase.b();

        BlockPos blockPos = new BlockPos(k, 0, l);
        BiomeGenBase biomeGenForCoords =
                this.worldObj.getBiomeGenForCoords(new BlockPos(k + 16, 0, l + 16));
        byte b0 = 0;
        this.random.setSeed(this.worldObj.getSeed());
        long l1 = this.random.nextLong();

        l1 /= 2L;

        l1 *= 2L;

        long i1 = l1 + 1L;
        l1 = this.random.nextLong();

        l1 /= 2L;

        l1 *= 2L;

        long j1 = l1 + 1L;
        this.random.setSeed(i * i1 + j * j1 ^ this.worldObj.getSeed());

        ChunkCoordIntPair chunkCoordIntPair = new ChunkCoordIntPair(i, j);

        for (java.util.Iterator<?> __recoveryIterator171_5181 =
                        (this.structureGenerators).iterator();
                (__recoveryIterator171_5181).hasNext(); ) {
            MapGenStructure mapGenStructure = (MapGenStructure) (__recoveryIterator171_5181).next();
            byte b1 =
                    remix.compat.compiler.JvmBooleanSlots.toByte(
                            mapGenStructure.generateStructure(
                                    this.worldObj, this.random, chunkCoordIntPair));
            if (mapGenStructure instanceof MapGenVillage) {
                b0 |= b1;
            }
        }

        if (this.waterLakeGenerator != null && b0 == 0 && this.random.nextInt(4) == 0) {
            this.waterLakeGenerator.generate(
                    this.worldObj,
                    this.random,
                    blockPos.add(
                            this.random.nextInt(16) + 8,
                            this.random.nextInt(256),
                            this.random.nextInt(16) + 8));
        }

        if (this.lavaLakeGenerator != null && b0 == 0 && this.random.nextInt(8) == 0) {
            BlockPos blockPosValue =
                    blockPos.add(
                            this.random.nextInt(16) + 8,
                            this.random.nextInt(this.random.nextInt(248) + 8),
                            this.random.nextInt(16) + 8);
            if (blockPosValue.getY() < 63 || this.random.nextInt(10) == 0) {
                this.lavaLakeGenerator.generate(this.worldObj, this.random, blockPosValue);
            }
        }

        if (this.hasDungeons) {
            int k1 = 0;

            while (k1 < 8) {
                new WorldGenDungeons()
                        .generate(
                                this.worldObj,
                                this.random,
                                blockPos.add(
                                        this.random.nextInt(16) + 8,
                                        this.random.nextInt(256),
                                        this.random.nextInt(16) + 8));
                k1++;
            }
        }

        if (this.hasDecoration) {
            biomeGenForCoords.decorate(this.worldObj, this.random, new BlockPos(k, 0, l));
        }
    }

    public boolean canSave() {
        return true;
    }

    public boolean chunkExists(int i, int j) {
        return true;
    }

    public Chunk provideChunk(BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    public ChunkProviderFlat(World world, long i, boolean flag, String text) {
        super();
        MapGenBase.b();

        this.cachedBlockIDs = new BlockState[256];
        this.structureGenerators = Lists.newArrayList();
        this.worldObj = world;
        this.random = new Random(i);
        this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(text);
        if (flag) {
            Map map = this.flatWorldGenInfo.getWorldFeatures();
            if (map.containsKey("village")) {
                Map map1 = (Map) map.get("village");
                if (!map1.containsKey("size")) {
                    map1.put("size", packedTextFragments[0]);
                }

                this.structureGenerators.add(new MapGenVillage(map1));
            }

            if (map.containsKey("biome_1")) {
                this.structureGenerators.add(new MapGenScatteredFeature((Map) map.get("biome_1")));
            }

            if (map.containsKey("mineshaft")) {
                this.structureGenerators.add(new MapGenMineshaft((Map) map.get("mineshaft")));
            }

            if (map.containsKey("stronghold")) {
                this.structureGenerators.add(new MapGenStronghold((Map) map.get("stronghold")));
            }

            if (map.containsKey("oceanmonument")) {
                this.structureGenerators.add(
                        new StructureOceanMonument((Map) map.get("oceanmonument")));
            }
        }

        if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lake")) {
            this.waterLakeGenerator = new WorldGenLakes(Blocks.water);
        }

        if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lava_lake")) {
            this.lavaLakeGenerator = new WorldGenLakes(Blocks.lava);
        }

        this.hasDungeons = this.flatWorldGenInfo.getWorldFeatures().containsKey("dungeon");
        byte b0 = 1;

        for (java.util.Iterator<?> __recoveryIterator171_31891 =
                        (this.flatWorldGenInfo.getFlatLayers()).iterator();
                (__recoveryIterator171_31891).hasNext(); ) {
            FlatLayerInfo flatLayerInfo = (FlatLayerInfo) (__recoveryIterator171_31891).next();
            int j = flatLayerInfo.getMinY();

            while (true) {
                if (j < flatLayerInfo.getMinY() + flatLayerInfo.getLayerCount()) {
                    BlockState blockState = flatLayerInfo.ID();
                    if (blockState.getBlock() != Blocks.air) {
                        b0 = 0;
                        this.cachedBlockIDs[j] = blockState;
                    }

                    j++;

                    continue;
                }

                break;
            }
        }

        this.hasDecoration =
                (boolean)
                        (b0 != 0
                                ? 0
                                : this.flatWorldGenInfo
                                        .getWorldFeatures()
                                        .containsKey("decoration"));
    }

    public void recreateStructures(Chunk chunk, int i, int j) {
        MapGenBase.b();

        Iterator iterator = this.structureGenerators.iterator();

        while (iterator.hasNext()) {
            MapGenStructure mapGenStructure = (MapGenStructure) iterator.next();
            mapGenStructure.generate(this, this.worldObj, i, j, (ChunkPrimer) null);
        }
    }

    public List getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
        BiomeGenBase biomeGenForCoords = this.worldObj.getBiomeGenForCoords(blockPos);
        return biomeGenForCoords.getSpawnableList(enumCreatureType);
    }

    static {
        net.minecraft.world.gen.ChunkProviderFlat.packedTextFragments = new String[3];
        net.minecraft.world.gen.ChunkProviderFlat.packedTextFragments[0] = new String("1");
    }

    public BlockPos getStrongholdGen(World world, String text, BlockPos blockPos) {
        MapGenBase.b();

        if ("Stronghold".equals(text)) {
            for (java.util.Iterator<?> __recoveryIterator171_38928 =
                            (this.structureGenerators).iterator();
                    (__recoveryIterator171_38928).hasNext(); ) {
                MapGenStructure mapGenStructure =
                        (MapGenStructure) (__recoveryIterator171_38928).next();
                if (mapGenStructure instanceof MapGenStronghold) {
                    return mapGenStructure.getClosestStrongholdPos(world, blockPos);
                }
            }
        }

        return null;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void saveExtraData() {}

    public boolean saveChunks(boolean flag, IProgressUpdate iProgressUpdate) {
        return true;
    }
}
