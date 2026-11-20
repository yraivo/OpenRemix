package net.minecraft.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;

import java.util.List;
import java.util.Random;

public class ChunkProviderGenerate implements IChunkProvider {
    private NoiseGeneratorOctaves bHn;
    private NoiseGeneratorOctaves bHg;

    public NoiseGeneratorOctaves mobSpawnerNoise;
    private NoiseGeneratorPerlin bHz;
    double[] bHA;
    private NoiseGeneratorOctaves bHk;
    private BiomeGenBase[] biomesForGeneration;
    double[] bHC;
    private ChunkProviderSettings settings;
    double[] bHE;
    private boolean mapFeaturesEnabled;
    private WorldType bHF;
    public NoiseGeneratorOctaves noiseGen6;
    private World worldObj;
    private double[] bHr;
    private StructureOceanMonument oceanMonumentGenerator;
    private float[] parabolicField;

    private MapGenBase ravineGenerator;
    private MapGenBase caveGenerator;
    private MapGenVillage villageGenerator;
    private Random rand;
    private double[] stoneNoise;

    double[] lf;
    private Block bmc = Blocks.water;
    public NoiseGeneratorOctaves noiseGen5;
    private MapGenScatteredFeature scatteredFeatureGenerator;
    private MapGenStronghold strongholdGenerator;
    private MapGenMineshaft mineshaftGenerator;

    public boolean chunkExists(int i, int j) {
        return true;
    }

    public Chunk provideChunk(BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    public BlockPos getStrongholdGen(World world, String text, BlockPos blockPos) {
        return "Stronghold".equals(text) && this.strongholdGenerator != null
                ? this.strongholdGenerator.getClosestStrongholdPos(world, blockPos)
                : null;
    }

    public String makeString() {
        return "RandomLevelSource";
    }


    public boolean func_177460_a(IChunkProvider iChunkProvider, Chunk chunk, int i, int j) {
        byte b0 = 0;
        if (this.settings.useMonuments && this.mapFeaturesEnabled) {
            long k = chunk.getInhabitedTime();

            if (k < 3600L) {
                b0 |=
                        remix.compat.compiler.JvmBooleanSlots.toInt(
                                this.oceanMonumentGenerator.generateStructure(
                                        this.worldObj, this.rand, new ChunkCoordIntPair(i, j)));
            }
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    public boolean unloadQueuedChunks() {
        return false;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public boolean canSave() {
        return true;
    }

    public void replaceBlocksForBiome(
            int i, int j, ChunkPrimer chunkPrimer, BiomeGenBase[] biomeGenBaseArray) {
        double d0 = 0.03125;
        MapGenBase.b();

        this.stoneNoise =
                this.bHz.func_151599_a(
                        this.stoneNoise, i * 16, j * 16, 16, 16, d0 * 2.0, d0 * 2.0, 1.0);
        int k = 0;

        while (k < 16) {
            int l = 0;

            while (true) {
                if (l < 16) {
                    BiomeGenBase biomeGenBase = biomeGenBaseArray[l + k * 16];
                    biomeGenBase.genTerrainBlocks(
                            this.worldObj,
                            this.rand,
                            chunkPrimer,
                            i * 16 + k,
                            j * 16 + l,
                            this.stoneNoise[l + k * 16]);
                    l++;

                    continue;
                }

                k++;

                break;
            }
        }
    }

    public void saveExtraData() {}

    public ChunkProviderGenerate(World aDMValue, long i, boolean flag, String text) {
        this.stoneNoise = new double[256];
        this.caveGenerator = new MapGenCaves();
        this.strongholdGenerator = new MapGenStronghold();
        this.villageGenerator = new MapGenVillage();
        this.mineshaftGenerator = new MapGenMineshaft();
        MapGenBase.b();

        this.scatteredFeatureGenerator = new MapGenScatteredFeature();
        this.ravineGenerator = new MapGenRavine();
        this.oceanMonumentGenerator = new StructureOceanMonument();
        this.worldObj = aDMValue;
        this.mapFeaturesEnabled = flag;
        this.bHF = aDMValue.getWorldInfo().getTerrainType();
        this.rand = new Random(i);
        this.bHk = new NoiseGeneratorOctaves(this.rand, 16);
        this.bHn = new NoiseGeneratorOctaves(this.rand, 16);
        this.bHg = new NoiseGeneratorOctaves(this.rand, 8);
        this.bHz = new NoiseGeneratorPerlin(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);

        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.bHr = new double[825];
        this.parabolicField = new float[25];
        int j = -2;

        while (j <= 2) {
            int k = -2;

            while (true) {
                if (k <= 2) {
                    float f = 10.0F / MathHelper.sqrt(j * j + k * k + 0.2F);
                    this.parabolicField[j + 2 + (k + 2) * 5] = f;
                    k++;

                    continue;
                }

                j++;

                break;
            }
        }

        if (text != null) {
            this.settings = ChunkProviderSettingsFactory.jsonToFactory(text).WS();
            this.bmc = this.settings.useLavaOceans ? Blocks.lava : Blocks.water;
        }
    }

    public void recreateStructures(Chunk chunk, int i, int j) {
        if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generate(this, this.worldObj, i, j, (ChunkPrimer) null);
        }

        if (this.settings.useVillages && this.mapFeaturesEnabled) {
            this.villageGenerator.generate(this, this.worldObj, i, j, (ChunkPrimer) null);
        }

        if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
            this.strongholdGenerator.generate(this, this.worldObj, i, j, (ChunkPrimer) null);
        }

        if (this.settings.useTemples && this.mapFeaturesEnabled) {
            this.scatteredFeatureGenerator.generate(this, this.worldObj, i, j, (ChunkPrimer) null);
        }

        if (this.settings.useMonuments && this.mapFeaturesEnabled) {
            this.oceanMonumentGenerator.generate(this, this.worldObj, i, j, (ChunkPrimer) null);
        }
    }

    public boolean saveChunks(boolean flag, IProgressUpdate iProgressUpdate) {
        return true;
    }

    public Chunk provideChunk(int i, int j) {
        MapGenBase.b();

        long l = i;

        l *= 341873128712L;
        long i1 = j;

        this.rand.setSeed(l + i1 * 132897987541L);

        ChunkPrimer chunkPrimer = new ChunkPrimer();
        this.setBlocksInChunk(i, j, chunkPrimer);
        this.biomesForGeneration =
                this.worldObj
                        .getWorldChunkManager()
                        .loadBlockGeneratorData(this.biomesForGeneration, i * 16, j * 16, 16, 16);
        this.replaceBlocksForBiome(i, j, chunkPrimer, this.biomesForGeneration);
        if (this.settings.useCaves) {
            this.caveGenerator.generate(this, this.worldObj, i, j, chunkPrimer);
        }

        if (this.settings.useRavines) {
            this.ravineGenerator.generate(this, this.worldObj, i, j, chunkPrimer);
        }

        if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generate(this, this.worldObj, i, j, chunkPrimer);
        }

        if (this.settings.useVillages && this.mapFeaturesEnabled) {
            this.villageGenerator.generate(this, this.worldObj, i, j, chunkPrimer);
        }

        if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
            this.strongholdGenerator.generate(this, this.worldObj, i, j, chunkPrimer);
        }

        if (this.settings.useTemples && this.mapFeaturesEnabled) {
            this.scatteredFeatureGenerator.generate(this, this.worldObj, i, j, chunkPrimer);
        }

        if (this.settings.useMonuments && this.mapFeaturesEnabled) {
            this.oceanMonumentGenerator.generate(this, this.worldObj, i, j, chunkPrimer);
        }

        Chunk chunk = new Chunk(this.worldObj, chunkPrimer, i, j);
        byte[] abyte = chunk.getBiomeArray();
        int k = 0;

        while (k < abyte.length) {
            abyte[k] = (byte) this.biomesForGeneration[k].biomeID;
            k++;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    public void populate(IChunkProvider iChunkProvider, int i, int j) {
        BlockFalling.fallInstantly = true;
        int k = i * 16;
        MapGenBase.b();

        int l = j * 16;

        BlockPos blockPos = new BlockPos(k, 0, l);
        BiomeGenBase biomeGenForCoords =
                this.worldObj.getBiomeGenForCoords(blockPos.add(16, 0, 16));
        this.rand.setSeed(this.worldObj.getSeed());
        long k4 = this.rand.nextLong();

        long l4 = k4 / 2L;

        long i5 = l4 * 2L;

        long i1 = i5 + 1L;
        long j5 = this.rand.nextLong();

        long k5 = j5 / 2L;

        long l5 = k5 * 2L;

        long j1 = l5 + 1L;
        this.rand.setSeed(i * i1 + j * j1 ^ this.worldObj.getSeed());
        byte b0 = 0;
        ChunkCoordIntPair chunkCoordIntPair = new ChunkCoordIntPair(i, j);
        if (this.settings.useMineShafts && this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generateStructure(this.worldObj, this.rand, chunkCoordIntPair);
        }

        if (this.settings.useVillages && this.mapFeaturesEnabled) {
            b0 =
                    remix.compat.compiler.JvmBooleanSlots.toByte(
                            this.villageGenerator.generateStructure(
                                    this.worldObj, this.rand, chunkCoordIntPair));
        }

        if (this.settings.useStrongholds && this.mapFeaturesEnabled) {
            this.strongholdGenerator.generateStructure(this.worldObj, this.rand, chunkCoordIntPair);
        }

        if (this.settings.useTemples && this.mapFeaturesEnabled) {
            this.scatteredFeatureGenerator.generateStructure(
                    this.worldObj, this.rand, chunkCoordIntPair);
        }

        if (this.settings.useMonuments && this.mapFeaturesEnabled) {
            this.oceanMonumentGenerator.generateStructure(
                    this.worldObj, this.rand, chunkCoordIntPair);
        }

        if (biomeGenForCoords != BiomeGenBase.desert
                && biomeGenForCoords != BiomeGenBase.desertHills
                && this.settings.useWaterLakes
                && b0 == 0
                && this.rand.nextInt(this.settings.waterLakeChance) == 0) {
            int k1 = this.rand.nextInt(16) + 8;
            int l1 = this.rand.nextInt(256);
            int i2 = this.rand.nextInt(16) + 8;
            new WorldGenLakes(Blocks.water)
                    .generate(this.worldObj, this.rand, blockPos.add(k1, l1, i2));
        }

        if (b0 == 0
                && this.rand.nextInt(this.settings.lavaLakeChance / 10) == 0
                && this.settings.useLavaLakes) {
            int k2 = this.rand.nextInt(16) + 8;
            int j3 = this.rand.nextInt(this.rand.nextInt(248) + 8);
            int i4 = this.rand.nextInt(16) + 8;
            if (j3 < 63 || this.rand.nextInt(this.settings.lavaLakeChance / 8) == 0) {
                new WorldGenLakes(Blocks.lava)
                        .generate(this.worldObj, this.rand, blockPos.add(k2, j3, i4));
            }
        }

        if (this.settings.useDungeons) {
            int l2 = 0;

            while (l2 < this.settings.dungeonChance) {
                int k3 = this.rand.nextInt(16) + 8;
                int j4 = this.rand.nextInt(256);
                int j2 = this.rand.nextInt(16) + 8;
                new WorldGenDungeons().generate(this.worldObj, this.rand, blockPos.add(k3, j4, j2));
                l2++;
            }
        }

        biomeGenForCoords.decorate(this.worldObj, this.rand, new BlockPos(k, 0, l));
        SpawnerAnimals.performWorldGenSpawning(
                this.worldObj, biomeGenForCoords, k + 8, l + 8, 16, 16, this.rand);
        blockPos = blockPos.add(8, 0, 8);
        int i3 = 0;

        while (i3 < 16) {
            int l3 = 0;

            while (true) {
                if (l3 < 16) {
                    BlockPos blockPosValue =
                            this.worldObj.getPrecipitationHeight(blockPos.add(i3, 0, l3));
                    BlockPos blockPosValue2 = blockPosValue.offsetDown();
                    if (this.worldObj.canBlockFreezeWater(blockPosValue2)) {
                        this.worldObj.setBlockState(
                                blockPosValue2, Blocks.ice.getDefaultState(), 2);
                    }

                    if (this.worldObj.canSnowAt(blockPosValue, true)) {
                        this.worldObj.setBlockState(
                                blockPosValue, Blocks.snow_layer.getDefaultState(), 2);
                    }

                    l3++;

                    continue;
                }

                i3++;

                break;
            }
        }

        BlockFalling.fallInstantly = false;
    }

    public List getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
        BiomeGenBase biomeGenForCoords = this.worldObj.getBiomeGenForCoords(blockPos);
        if (this.mapFeaturesEnabled) {
            if (enumCreatureType == EnumCreatureType.MONSTER
                    && this.scatteredFeatureGenerator.method_08c26e1ff2b44fcfa823(blockPos)) {
                return this.scatteredFeatureGenerator.getScatteredFeatureSpawnList();
            }

            if (enumCreatureType == EnumCreatureType.MONSTER
                    && this.settings.useMonuments
                    && this.oceanMonumentGenerator.isPositionInStructure(this.worldObj, blockPos)) {
                return this.oceanMonumentGenerator.i();
            }
        }

        return biomeGenForCoords.getSpawnableList(enumCreatureType);
    }

    public void setBlocksInChunk(int i, int j, ChunkPrimer chunkPrimer) {
        this.biomesForGeneration =
                this.worldObj
                        .getWorldChunkManager()
                        .getBiomesForGeneration(
                                this.biomesForGeneration, i * 4 - 2, j * 4 - 2, 10, 10);
        MapGenBase.b();

        this.method_a8908bd0f12e21ee1ad3(i * 4, 0, j * 4);

        int k = 0;

        while (k < 4) {
            int l = k * 5;
            int i1 = (k + 1) * 5;
            int j1 = 0;

            while (true) {
                if (j1 < 4) {
                    int k1 = (l + j1) * 33;
                    int l1 = (l + j1 + 1) * 33;
                    int i2 = (i1 + j1) * 33;
                    int j2 = (i1 + j1 + 1) * 33;
                    int k2 = 0;

                    while (k2 < 32) {
                        double d0 = 0.125;
                        double d1 = this.bHr[k1 + k2];
                        double d2 = this.bHr[l1 + k2];
                        double d3 = this.bHr[i2 + k2];
                        double d4 = this.bHr[j2 + k2];
                        double d5 = (this.bHr[k1 + k2 + 1] - d1) * d0;
                        double d6 = (this.bHr[l1 + k2 + 1] - d2) * d0;
                        double d7 = (this.bHr[i2 + k2 + 1] - d3) * d0;
                        double d8 = (this.bHr[j2 + k2 + 1] - d4) * d0;
                        int l2 = 0;

                        while (l2 < 8) {
                            double d9 = 0.25;
                            double d10 = d1;
                            double d11 = d2;
                            double d12 = (d3 - d1) * d9;
                            double d13 = (d4 - d2) * d9;
                            int i3 = 0;

                            while (i3 < 4) {
                                double d14 = 0.25;
                                double d15 = (d11 - d10) * d14;
                                double d16 = d10 - d15;
                                int j3 = 0;

                                while (j3 < 4) {
                                    {
                                        if ((d16 += d15) > 0.0) {
                                            chunkPrimer.setBlockState(
                                                    k * 4 + i3,
                                                    k2 * 8 + l2,
                                                    j1 * 4 + j3,
                                                    Blocks.stone.getDefaultState());

                                        } else {

                                            if (k2 * 8 + l2 < this.settings.seaLevel) {
                                                chunkPrimer.setBlockState(
                                                        k * 4 + i3,
                                                        k2 * 8 + l2,
                                                        j1 * 4 + j3,
                                                        this.bmc.getDefaultState());
                                            }
                                        }
                                    }

                                    j3++;
                                }

                                d10 += d12;
                                d11 += d13;
                                i3++;
                            }

                            d1 += d5;
                            d2 += d6;
                            d3 += d7;
                            d4 += d8;
                            l2++;
                        }

                        k2++;
                    }

                    j1++;

                    continue;
                }

                k++;

                break;
            }
        }
    }

    private void method_a8908bd0f12e21ee1ad3(int i, int j, int k) {
        this.bHE =
                this.noiseGen6.generateNoiseOctaves(
                        this.bHE,
                        i,
                        k,
                        5,
                        5,
                        this.settings.depthNoiseScaleX,
                        this.settings.depthNoiseScaleZ,
                        this.settings.depthNoiseScaleExponent);
        float coordinateScale = this.settings.coordinateScale;
        MapGenBase.b();

        float heightScale = this.settings.heightScale;
        this.bHC =
                this.bHg.generateNoiseOctaves(
                        this.bHC,
                        i,
                        j,
                        k,
                        5,
                        33,
                        5,
                        coordinateScale / this.settings.mainNoiseScaleX,
                        heightScale / this.settings.mainNoiseScaleY,
                        coordinateScale / this.settings.mainNoiseScaleZ);

        this.lf =
                this.bHk.generateNoiseOctaves(
                        this.lf, i, j, k, 5, 33, 5, coordinateScale, heightScale, coordinateScale);
        this.bHA =
                this.bHn.generateNoiseOctaves(
                        this.bHA, i, j, k, 5, 33, 5, coordinateScale, heightScale, coordinateScale);

        int l = 0;
        int i1 = 0;
        int j1 = 0;

        while (j1 < 5) {
            int k1 = 0;

            while (true) {
                if (k1 < 5) {
                    float f2 = 0.0F;
                    float f3 = 0.0F;
                    float f4 = 0.0F;
                    byte b2 = 2;
                    BiomeGenBase biomeGenBase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];
                    int l1 = -b2;

                    while (l1 <= b2) {
                        int i2 = -b2;

                        while (i2 <= b2) {
                            BiomeGenBase biomeGenBaseValue =
                                    this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                            float f5 =
                                    this.settings.biomeDepthOffSet
                                            + biomeGenBaseValue.minHeight
                                                    * this.settings.biomeDepthWeight;
                            float f6 =
                                    this.settings.biomeScaleOffset
                                            + biomeGenBaseValue.maxHeight
                                                    * this.settings.biomeScaleWeight;
                            if (this.bHF == WorldType.AMPLIFIED && f5 > 0.0F) {
                                f5 = 1.0F + f5 * 2.0F;
                                f6 = 1.0F + f6 * 4.0F;
                            }

                            float f7 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f5 + 2.0F);
                            if (biomeGenBaseValue.minHeight > biomeGenBase.minHeight) {
                                f7 /= 2.0F;
                            }

                            f2 += f6 * f7;
                            f3 += f5 * f7;
                            f4 += f7;
                            i2++;
                        }

                        l1++;
                    }

                    f2 /= f4;
                    f3 /= f4;
                    f2 = f2 * 0.9F + 0.1F;
                    f3 = (f3 * 4.0F - 1.0F) / 8.0F;
                    double d7 = this.bHE[i1] / 8000.0;
                    if (d7 < 0.0) {
                        d7 = -d7 * 0.3;
                    }

                    double d8;
                    {
                        d8 = d7 * 3.0 - 2.0;
                        if (d8 < 0.0) {
                            double d9 = d8 / 2.0;
                            if (d9 < -1.0) {
                                d9 = -1.0;
                            }

                            double d10 = d9 / 1.4;
                            d8 = d10 / 2.0;

                        } else {

                            if (d8 > 1.0) {
                                d8 = 1.0;
                            }

                            d8 /= 8.0;
                        }
                    }

                    i1++;
                    double d11 = f3;
                    double d12 = f2;
                    d11 += d8 * 0.2;
                    d11 = d11 * this.settings.baseSize / 8.0;
                    double d0 = this.settings.baseSize + d11 * 4.0;
                    int j2 = 0;

                    while (j2 < 33) {
                        double d1 = (j2 - d0) * this.settings.stretchY * 128.0 / 256.0 / d12;
                        if (d1 < 0.0) {
                            d1 *= 4.0;
                        }

                        double d2 = this.lf[l] / this.settings.lowerLimitScale;
                        double d3 = this.bHA[l] / this.settings.upperLimitScale;
                        double d4 = (this.bHC[l] / 10.0 + 1.0) / 2.0;
                        double d5 = MathHelper.clampedLerp(d2, d3, d4) - d1;
                        if (j2 > 29) {
                            double d6 = (j2 - 29) / 3.0F;
                            d5 = d5 * (1.0 - d6) + -10.0 * d6;
                        }

                        this.bHr[l] = d5;
                        l++;
                        j2++;
                    }

                    k1++;

                    continue;
                }

                j1++;

                break;
            }
        }
    }
}
