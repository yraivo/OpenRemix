package net.minecraft.world.gen;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenNetherBridge;

import java.util.List;
import java.util.Random;

public class ChunkProviderHell implements IChunkProvider {
    private NoiseGeneratorOctaves slowsandGravelNoiseGen;
    private boolean b;
    private GeneratorBushFeature bHR;
    double[] bHS;
    private double[] bHT;
    private double[] netherrackExclusivityNoise;
    private NoiseGeneratorOctaves netherNoiseGen3;
    private MapGenNetherBridge genNetherBridge;
    public NoiseGeneratorOctaves netherNoiseGen6;
    private GeneratorBushFeature bHY;
    private Random hellRNG;
    double[] bHA;
    private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    private World worldObj;
    public NoiseGeneratorOctaves netherNoiseGen7;
    private double[] gravelNoise;
    private NoiseGeneratorOctaves netherNoiseGen2;
    double[] bHC;
    private WorldGenerator bId;
    private WorldGenHellLava bIe;
    private WorldGenGlowStone1 bIf;
    private NoiseGeneratorOctaves netherNoiseGen1;
    private double[] slowsandNoise = new double[256];
    private WorldGenFire bIh;
    private WorldGenHellLava bIi;
    double[] bHE;
    double[] lf;

    private WorldGenGlowStone2 bIk;
    private static String zc;
    private MapGenBase netherCaveGenerator;

    public int getLoadedChunkCount() {
        return 0;
    }

    public void method_6eb086dc02c02700ceb2(int i, int j, ChunkPrimer chunkPrimer) {
        byte b0 = 4;
        MapGenBase.b();

        byte b1 = 32;
        int k = b0 + 1;
        byte b2 = 17;

        int l = b0 + 1;
        this.bHT = this.initializeNoiseField(this.bHT, i * b0, 0, j * b0, k, b2, l);
        int i1 = 0;

        while (i1 < b0) {
            int j1 = 0;

            while (true) {
                if (j1 < b0) {
                    int k1 = 0;

                    while (k1 < 16) {
                        double d0 = 0.125;
                        double d1 = this.bHT[((i1 + 0) * l + j1 + 0) * b2 + k1 + 0];
                        double d2 = this.bHT[((i1 + 0) * l + j1 + 1) * b2 + k1 + 0];
                        double d3 = this.bHT[((i1 + 1) * l + j1 + 0) * b2 + k1 + 0];
                        double d4 = this.bHT[((i1 + 1) * l + j1 + 1) * b2 + k1 + 0];
                        double d5 = (this.bHT[((i1 + 0) * l + j1 + 0) * b2 + k1 + 1] - d1) * d0;
                        double d6 = (this.bHT[((i1 + 0) * l + j1 + 1) * b2 + k1 + 1] - d2) * d0;
                        double d7 = (this.bHT[((i1 + 1) * l + j1 + 0) * b2 + k1 + 1] - d3) * d0;
                        double d8 = (this.bHT[((i1 + 1) * l + j1 + 1) * b2 + k1 + 1] - d4) * d0;
                        int l1 = 0;

                        while (l1 < 8) {
                            double d9 = 0.25;
                            double d10 = d1;
                            double d11 = d2;
                            double d12 = (d3 - d1) * d9;
                            double d13 = (d4 - d2) * d9;
                            int i2 = 0;

                            while (i2 < 4) {
                                double d14 = 0.25;
                                double d15 = d10;
                                double d16 = (d11 - d10) * d14;
                                int j2 = 0;

                                while (j2 < 4) {
                                    BlockState blockState = null;
                                    if (k1 * 8 + l1 < b1) {
                                        blockState = Blocks.lava.getDefaultState();
                                    }

                                    if (d15 > 0.0) {
                                        blockState = Blocks.netherrack.getDefaultState();
                                    }

                                    int k2 = i2 + i1 * 4;
                                    int l2 = l1 + k1 * 8;
                                    int i3 = j2 + j1 * 4;
                                    chunkPrimer.setBlockState(k2, l2, i3, blockState);
                                    d15 += d16;
                                    j2++;
                                }

                                d10 += d12;
                                d11 += d13;
                                i2++;
                            }

                            d1 += d5;
                            d2 += d6;
                            d3 += d7;
                            d4 += d8;
                            l1++;
                        }

                        k1++;
                    }

                    j1++;

                    continue;
                }

                i1++;

                break;
            }
        }
    }

    public ChunkProviderHell(World world, boolean flag, long i) {
        this.gravelNoise = new double[256];
        this.netherrackExclusivityNoise = new double[256];
        this.bIh = new WorldGenFire();
        this.bIf = new WorldGenGlowStone1();
        this.bIk = new WorldGenGlowStone2();
        this.bId =
                new WorldGenMinable(
                        Blocks.quartz_ore.getDefaultState(),
                        14,
                        BlockHelper.forBlock(Blocks.netherrack));
        this.bIe = new WorldGenHellLava(Blocks.flowing_lava, true);
        this.bIi = new WorldGenHellLava(Blocks.flowing_lava, false);
        this.bHY = new GeneratorBushFeature(Blocks.brown_mushroom);
        this.bHR = new GeneratorBushFeature(Blocks.red_mushroom);
        this.genNetherBridge = new MapGenNetherBridge();
        this.netherCaveGenerator = new MapGenCavesHell();
        this.worldObj = world;
        this.b = flag;
        this.hellRNG = new Random(i);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);
    }

    public boolean unloadQueuedChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    private double[] initializeNoiseField(
            double[] adouble, int i, int j, int k, int l, int i1, int j1) {
        MapGenBase.b();

        if (adouble == null) {
            adouble = new double[l * i1 * j1];
        }

        double d0 = 684.412;
        double d1 = 2053.236;
        this.bHE =
                this.netherNoiseGen6.generateNoiseOctaves(
                        this.bHE, i, j, k, l, 1, j1, 1.0, 0.0, 1.0);
        this.bHS =
                this.netherNoiseGen7.generateNoiseOctaves(
                        this.bHS, i, j, k, l, 1, j1, 100.0, 0.0, 100.0);
        this.bHC =
                this.netherNoiseGen3.generateNoiseOctaves(
                        this.bHC, i, j, k, l, i1, j1, d0 / 80.0, d1 / 60.0, d0 / 80.0);
        this.lf =
                this.netherNoiseGen1.generateNoiseOctaves(this.lf, i, j, k, l, i1, j1, d0, d1, d0);
        this.bHA =
                this.netherNoiseGen2.generateNoiseOctaves(this.bHA, i, j, k, l, i1, j1, d0, d1, d0);
        int k1 = 0;
        double[] adouble1 = new double[i1];
        int l1 = 0;

        while (l1 < i1) {
            adouble1[l1] = Math.cos(l1 * Math.PI * 6.0 / i1) * 2.0;
            double d2 = l1;
            if (l1 > i1 / 2) {
                d2 = i1 - 1 - l1;
            }

            if (d2 < 4.0) {
                d2 = 4.0 - d2;
                adouble1[l1] -= d2 * d2 * d2 * 10.0;
            }

            l1++;
        }

        l1 = 0;

        while (l1 < l) {
            int j2 = 0;

            while (true) {
                if (j2 < j1) {
                    double d3 = 0.0;
                    int i2 = 0;

                    while (i2 < i1) {
                        double d4;
                        double d9;
                        {
                            d9 = 0.0;
                            d4 = adouble1[i2];
                            double d5 = this.lf[k1] / 512.0;
                            double d6 = this.bHA[k1] / 512.0;
                            double d7 = (this.bHC[k1] / 10.0 + 1.0) / 2.0;
                            if (d7 < 0.0) {
                                d9 = d5;

                                
                            } else {

                                if (d7 > 1.0) {
                                    d9 = d6;


                                } else {

                                    d9 = d5 + (d6 - d5) * d7;
                                }
                            }
                        }

                        d9 -= d4;
                        if (i2 > i1 - 4) {
                            double d8 = (i2 - (i1 - 4)) / 3.0F;
                            d9 = d9 * (1.0 - d8) + -10.0 * d8;
                        }

                        if (i2 < d3) {
                            double d10 = (d3 - i2) / 4.0;
                            d10 = MathHelper.clamp(d10, 0.0, 1.0);
                            d9 = d9 * (1.0 - d10) + -10.0 * d10;
                        }

                        adouble[k1] = d9;
                        k1++;
                        i2++;
                    }

                    j2++;

                    continue;
                }

                l1++;

                break;
            }
        }

        return adouble;
    }

    public boolean func_177460_a(IChunkProvider iChunkProvider, Chunk chunk, int i, int j) {
        return false;
    }

    public Chunk provideChunk(int i, int j) {
        MapGenBase.b();

        long l = i;

        l *= 341873128712L;
        long i1 = j;

        this.hellRNG.setSeed(l + i1 * 132897987541L);

        ChunkPrimer chunkPrimer = new ChunkPrimer();
        this.method_6eb086dc02c02700ceb2(i, j, chunkPrimer);
        this.method_a55621be3007c0b831a8(i, j, chunkPrimer);
        this.netherCaveGenerator.generate(
                this, this.worldObj, i, j, chunkPrimer);
        if (this.b) {
            this.genNetherBridge.generate(
                    this, this.worldObj, i, j, chunkPrimer);
        }

        Chunk chunk = new Chunk(this.worldObj, chunkPrimer, i, j);
        BiomeGenBase[] biomeGenBaseArray =
                this.worldObj
                        .getWorldChunkManager()
                        .loadBlockGeneratorData((BiomeGenBase[]) null, i * 16, j * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();
        int k = 0;

        while (k < abyte.length) {
            abyte[k] = (byte) biomeGenBaseArray[k].biomeID;
            k++;
        }

        chunk.resetRelightChecks();
        return chunk;
    }

    public boolean saveChunks(boolean flag, IProgressUpdate iProgressUpdate) {
        return true;
    }

    public boolean chunkExists(int i, int j) {
        return true;
    }

    public void saveExtraData() {}

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String makeString() {
        return zc;
    }

    static {
        zc = "HellRandomLevelSource";
    }

    public BlockPos getStrongholdGen(World world, String text, BlockPos blockPos) {
        return null;
    }

    public List getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
        if (enumCreatureType == EnumCreatureType.MONSTER) {
            if (this.genNetherBridge.method_51a343cb461860600ef7(blockPos)) {
                return this.genNetherBridge.getSpawnList();
            }

            if (this.genNetherBridge.isPositionInStructure(this.worldObj, blockPos)
                    && this.worldObj.getBlockState(blockPos.offsetDown()).getBlock()
                            == Blocks.nether_brick) {
                return this.genNetherBridge.getSpawnList();
            }
        }

        BiomeGenBase biomeGenForCoords = this.worldObj.getBiomeGenForCoords(blockPos);
        return biomeGenForCoords.getSpawnableList(enumCreatureType);
    }

    public void recreateStructures(Chunk chunk, int i, int j) {
        this.genNetherBridge.generate(
                this, this.worldObj, i, j, (ChunkPrimer) null);
    }

    public Chunk provideChunk(BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    public void populate(IChunkProvider iChunkProvider, int i, int j) {
        BlockFalling.fallInstantly = true;
        BlockPos blockPos = new BlockPos(i * 16, 0, j * 16);
        ChunkCoordIntPair chunkCoordIntPair = new ChunkCoordIntPair(i, j);
        MapGenBase.b();

        this.genNetherBridge.generateStructure(
                this.worldObj, this.hellRNG, chunkCoordIntPair);
        int k = 0;

        while (k < 8) {
            this.bIi.generate(
                    this.worldObj,
                    this.hellRNG,
                    blockPos.add(
                            this.hellRNG.nextInt(16) + 8,
                            this.hellRNG.nextInt(120) + 4,
                            this.hellRNG.nextInt(16) + 8));
            k++;
        }

        k = 0;

        while (k < this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1) {
            this.bIh.generate(
                    this.worldObj,
                    this.hellRNG,
                    blockPos.add(
                            this.hellRNG.nextInt(16) + 8,
                            this.hellRNG.nextInt(120) + 4,
                            this.hellRNG.nextInt(16) + 8));
            k++;
        }

        k = 0;

        while (k < this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1)) {
            this.bIf.generate(
                    this.worldObj,
                    this.hellRNG,
                    blockPos.add(
                            this.hellRNG.nextInt(16) + 8,
                            this.hellRNG.nextInt(120) + 4,
                            this.hellRNG.nextInt(16) + 8));
            k++;
        }

        k = 0;

        while (k < 10) {
            this.bIk.generate(
                    this.worldObj,
                    this.hellRNG,
                    blockPos.add(
                            this.hellRNG.nextInt(16) + 8,
                            this.hellRNG.nextInt(128),
                            this.hellRNG.nextInt(16) + 8));
            k++;
        }

        if (this.hellRNG.nextBoolean()) {
            this.bHY.generate(
                    this.worldObj,
                    this.hellRNG,
                    blockPos.add(
                            this.hellRNG.nextInt(16) + 8,
                            this.hellRNG.nextInt(128),
                            this.hellRNG.nextInt(16) + 8));
        }

        if (this.hellRNG.nextBoolean()) {
            this.bHR.generate(
                    this.worldObj,
                    this.hellRNG,
                    blockPos.add(
                            this.hellRNG.nextInt(16) + 8,
                            this.hellRNG.nextInt(128),
                            this.hellRNG.nextInt(16) + 8));
        }

        k = 0;

        while (k < 16) {
            this.bId.generate(
                    this.worldObj,
                    this.hellRNG,
                    blockPos.add(
                            this.hellRNG.nextInt(16),
                            this.hellRNG.nextInt(108) + 10,
                            this.hellRNG.nextInt(16)));
            k++;
        }

        k = 0;

        while (k < 16) {
            this.bIe.generate(
                    this.worldObj,
                    this.hellRNG,
                    blockPos.add(
                            this.hellRNG.nextInt(16),
                            this.hellRNG.nextInt(108) + 10,
                            this.hellRNG.nextInt(16)));
            k++;
        }

        BlockFalling.fallInstantly = false;
    }

    public void method_a55621be3007c0b831a8(int i, int j, ChunkPrimer chunkPrimer) {
        byte b0 = 64;
        MapGenBase.b();

        double d0 = 0.03125;
        this.slowsandNoise =
                this.slowsandGravelNoiseGen.generateNoiseOctaves(
                        this.slowsandNoise, i * 16, j * 16, 0, 16, 16, 1, d0, d0, 1.0);
        this.gravelNoise =
                this.slowsandGravelNoiseGen.generateNoiseOctaves(
                        this.gravelNoise, i * 16, 109, j * 16, 16, 1, 16, d0, 1.0, d0);
        this.netherrackExclusivityNoise =
                this.netherrackExculsivityNoiseGen.generateNoiseOctaves(
                        this.netherrackExclusivityNoise,
                        i * 16,
                        j * 16,
                        0,
                        16,
                        16,
                        1,
                        d0 * 2.0,
                        d0 * 2.0,
                        d0 * 2.0);

        int k = 0;

        while (k < 16) {
            int l = 0;

            while (true) {
                if (l < 16) {
                    int i1 =
                            this.slowsandNoise[k + l * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0
                                    ? 1
                                    : 0;
                    int j1 =
                            this.gravelNoise[k + l * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0
                                    ? 1
                                    : 0;
                    int k1 =
                            (int)
                                    (this.netherrackExclusivityNoise[k + l * 16] / 3.0
                                            + 3.0
                                            + this.hellRNG.nextDouble() * 0.25);
                    int l1 = -1;
                    BlockState blockState = Blocks.netherrack.getDefaultState();
                    BlockState blockStateValue = Blocks.netherrack.getDefaultState();
                    int i2 = 127;

                    while (i2 >= 0) {
                        {
                            if (i2 < 127 - this.hellRNG.nextInt(5)
                                    && i2 > this.hellRNG.nextInt(5)) {
                                {
                                    BlockState blockStateValue2 =
                                            chunkPrimer.getBlockState(l, i2, k);
                                    if (blockStateValue2.getBlock() != null
                                            && blockStateValue2.getBlock().getMaterial()
                                                    != Material.AIR) {
                                        if (!(blockStateValue2.getBlock() != Blocks.netherrack)) {

                                            if (l1 == -1) {
                                                if (k1 <= 0) {
                                                    blockState = null;
                                                    blockStateValue =
                                                            Blocks.netherrack.getDefaultState();
                                                } else if (i2 >= b0 - 4 && i2 <= b0 + 1) {
                                                    blockState = Blocks.netherrack.getDefaultState();
                                                    blockStateValue =
                                                            Blocks.netherrack.getDefaultState();
                                                    if (j1 != 0) {
                                                        blockState = Blocks.gravel.getDefaultState();
                                                        blockStateValue =
                                                                Blocks.netherrack.getDefaultState();
                                                    }

                                                    if (i1 != 0) {
                                                        blockState = Blocks.soul_sand.getDefaultState();
                                                        blockStateValue =
                                                                Blocks.soul_sand.getDefaultState();
                                                    }
                                                }

                                                if (i2 < b0
                                                        && (blockState == null
                                                                || blockState.getBlock().getMaterial()
                                                                        == Material.AIR)) {
                                                    blockState = Blocks.lava.getDefaultState();
                                                }

                                                l1 = k1;
                                                if (i2 >= b0 - 1) {
                                                    chunkPrimer.setBlockState(l, i2, k, blockState);


                                                } else {

                                                    chunkPrimer.setBlockState(l, i2, k, blockStateValue);


                                                }
                                            } else {

                                                if (!(l1 <= 0)) {

                                                    l1--;
                                                    chunkPrimer.setBlockState(l, i2, k, blockStateValue);


                                                }
                                            }
                                        }
                                    } else {

                                        l1 = -1;
                                    }
                                }

                                
                            } else {

                                chunkPrimer.setBlockState(l, i2, k, Blocks.bedrock.getDefaultState());
                            }
                        }

                        i2--;
                    }

                    l++;

                    continue;
                }

                k++;

                break;
            }
        }
    }
}
