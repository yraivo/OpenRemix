package net.minecraft.world.gen;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.List;
import java.util.Random;

public class ChunkProviderEnd implements IChunkProvider {
    private World endWorld;
    private NoiseGeneratorOctaves noiseGen3;
    private double[] densities;
    double[] noiseData2;
    public NoiseGeneratorOctaves noiseGen4;
    private NoiseGeneratorOctaves noiseGen1;
    double[] noiseData1;

    private NoiseGeneratorOctaves noiseGen2;
    public NoiseGeneratorOctaves noiseGen5;
    double[] noiseData4;
    private BiomeGenBase[] biomesForGeneration;
    private Random endRNG;
    double[] noiseData3;
    private static String ks;
    double[] noiseData5;

    @Override
    public void saveExtraData() {}

    @Override
    public boolean func_177460_a(IChunkProvider iChunkProvider, Chunk chunk, int i, int j) {
        return false;
    }

    @Override
    public boolean saveChunks(boolean flag, IProgressUpdate iProgressUpdate) {
        return true;
    }

    @Override
    public BlockPos getStrongholdGen(World world, String text, BlockPos blockPos) {
        return null;
    }

    @Override
    public String makeString() {
        return ks;
    }

    public ChunkProviderEnd(World bHfValue, long i) {
        this.endWorld = bHfValue;
        this.endRNG = new Random(i);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 10);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 16);
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    // region String decoding support

    // endregion

    @Override
    public Chunk provideChunk(BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    @Override
    public void populate(IChunkProvider iChunkProvider, int i, int j) {
        BlockFalling.fallInstantly = true;
        BlockPos blockPos = new BlockPos(i * 16, 0, j * 16);
        this.endWorld
                .getBiomeGenForCoords(blockPos.add(16, 0, 16))
                .decorate(this.endWorld, this.endWorld.rand, blockPos);
        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    private double[] initializeNoiseField(
            double[] adouble, int i, int j, int k, int l, int i1, int j1) {
        MapGenBase.b();

        if (adouble == null) {
            adouble = new double[l * i1 * j1];
        }

        double d0 = 684.412;
        double d1 = 684.412;
        this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, i, k, l, j1, 1.121, 1.121, 0.5);
        this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, i, k, l, j1, 200.0, 200.0, 0.5);
        d0 *= 2.0;
        this.noiseData1 =
                this.noiseGen3.generateNoiseOctaves(
                        this.noiseData1, i, j, k, l, i1, j1, d0 / 80.0, d1 / 160.0, d0 / 80.0);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, i, j, k, l, i1, j1, d0, d1, d0);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, i, j, k, l, i1, j1, d0, d1, d0);
        int k1 = 0;
        int l1 = 0;

        while (l1 < l) {
            int i2 = 0;

            while (true) {
                if (i2 < j1) {
                    float f = (l1 + i) / 1.0F;
                    float f1 = (i2 + k) / 1.0F;
                    float f2 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * 8.0F;
                    if (f2 > 80.0F) {
                        f2 = 80.0F;
                    }

                    if (f2 < -100.0F) {
                        f2 = -100.0F;
                    }

                    int j2 = 0;

                    while (j2 < i1) {
                        double d6;
                        {
                            d6 = 0.0;
                            double d2 = this.noiseData2[k1] / 512.0;
                            double d3 = this.noiseData3[k1] / 512.0;
                            double d4 = (this.noiseData1[k1] / 10.0 + 1.0) / 2.0;
                            if (d4 < 0.0) {
                                d6 = d2;

                                
                            } else {

                                if (d4 > 1.0) {
                                    d6 = d3;


                                } else {

                                    d6 = d2 + (d3 - d2) * d4;
                                }
                            }
                        }

                        d6 -= 8.0;
                        d6 += f2;
                        byte b0 = 2;
                        if (j2 > i1 / 2 - b0) {
                            double d5 = (j2 - (i1 / 2 - b0)) / 64.0F;
                            d5 = MathHelper.clamp(d5, 0.0, 1.0);
                            d6 = d6 * (1.0 - d5) + -3000.0 * d5;
                        }

                        b0 = 8;
                        if (j2 < b0) {
                            double d7 = (b0 - j2) / (b0 - 1.0F);
                            d6 = d6 * (1.0 - d7) + -30.0 * d7;
                        }

                        adouble[k1] = d6;
                        k1++;
                        j2++;
                    }

                    i2++;

                    continue;
                }

                l1++;

                break;
            }
        }

        return adouble;
    }

    @Override
    public boolean chunkExists(int i, int j) {
        return true;
    }

    public void method_555f90a9a5e190fe5a2d(ChunkPrimer chunkPrimer) {
        MapGenBase.b();

        int i = 0;

        while (i < 16) {
            int j = 0;

            while (true) {
                if (j < 16) {
                    byte b0 = 1;
                    int k = -1;
                    BlockState blockState = Blocks.end_stone.getDefaultState();
                    BlockState blockStateValue = Blocks.end_stone.getDefaultState();
                    int l = 127;

                    while (l >= 0) {
                        {
                            BlockState blockStateValue2 = chunkPrimer.getBlockState(i, l, j);
                            if (blockStateValue2.getBlock().getMaterial() == Material.AIR) {
                                k = -1;

                                
                            } else {

                                if (blockStateValue2.getBlock() == Blocks.stone) {
                                    if (k == -1) {

                                        k = b0;
                                        if (l >= 0) {
                                            chunkPrimer.setBlockState(i, l, j, blockState);


                                        } else {

                                            chunkPrimer.setBlockState(i, l, j, blockStateValue);


                                        }
                                    } else {

                                        if (k > 0) {
                                            k--;
                                            chunkPrimer.setBlockState(i, l, j, blockStateValue);
                                        }
                                    }
                                }
                            }
                        }

                        l--;
                    }

                    j++;

                    continue;
                }

                i++;

                break;
            }
        }
    }

    @Override
    public void recreateStructures(Chunk chunk, int i, int j) {}

    @Override
    public boolean canSave() {
        return true;
    }

    // region String decoding support

    // endregion

    static {
        ks = "RandomLevelSource";
    }

    @Override
    public Chunk provideChunk(int i, int j) {
        MapGenBase.b();

        long l = i;

        l *= 341873128712L;
        long i1 = j;

        this.endRNG.setSeed(l + i1 * 132897987541L);
        ChunkPrimer chunkPrimer = new ChunkPrimer();
        this.biomesForGeneration =
                this.endWorld
                        .getWorldChunkManager()
                        .loadBlockGeneratorData(this.biomesForGeneration, i * 16, j * 16, 16, 16);
        this.method_6eb086dc02c02700ceb2(i, j, chunkPrimer);
        this.method_555f90a9a5e190fe5a2d(chunkPrimer);
        Chunk chunk = new Chunk(this.endWorld, chunkPrimer, i, j);

        byte[] abyte = chunk.getBiomeArray();
        int k = 0;

        while (k < abyte.length) {
            abyte[k] = (byte) this.biomesForGeneration[k].biomeID;
            k++;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    public void method_6eb086dc02c02700ceb2(int i, int j, ChunkPrimer chunkPrimer) {
        MapGenBase.b();

        byte b0 = 2;
        int k = b0 + 1;
        byte b1 = 33;
        int l = b0 + 1;
        this.densities = this.initializeNoiseField(this.densities, i * b0, 0, j * b0, k, b1, l);
        int i1 = 0;

        while (i1 < b0) {
            int j1 = 0;

            while (true) {
                if (j1 < b0) {
                    int k1 = 0;

                    while (k1 < 32) {
                        double d0 = 0.25;
                        double d1 = this.densities[((i1 + 0) * l + j1 + 0) * b1 + k1 + 0];
                        double d2 = this.densities[((i1 + 0) * l + j1 + 1) * b1 + k1 + 0];
                        double d3 = this.densities[((i1 + 1) * l + j1 + 0) * b1 + k1 + 0];
                        double d4 = this.densities[((i1 + 1) * l + j1 + 1) * b1 + k1 + 0];
                        double d5 = (this.densities[((i1 + 0) * l + j1 + 0) * b1 + k1 + 1] - d1) * d0;
                        double d6 = (this.densities[((i1 + 0) * l + j1 + 1) * b1 + k1 + 1] - d2) * d0;
                        double d7 = (this.densities[((i1 + 1) * l + j1 + 0) * b1 + k1 + 1] - d3) * d0;
                        double d8 = (this.densities[((i1 + 1) * l + j1 + 1) * b1 + k1 + 1] - d4) * d0;
                        int l1 = 0;

                        while (l1 < 4) {
                            double d9 = 0.125;
                            double d10 = d1;
                            double d11 = d2;
                            double d12 = (d3 - d1) * d9;
                            double d13 = (d4 - d2) * d9;
                            int i2 = 0;

                            while (i2 < 8) {
                                double d14 = 0.125;
                                double d15 = d10;
                                double d16 = (d11 - d10) * d14;
                                int j2 = 0;

                                while (j2 < 8) {
                                    BlockState blockState = null;
                                    if (d15 > 0.0) {
                                        blockState = Blocks.end_stone.getDefaultState();
                                    }

                                    int k2 = i2 + i1 * 8;
                                    int l2 = l1 + k1 * 4;
                                    int i3 = j2 + j1 * 8;
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

    @Override
    public List getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
        return this.endWorld.getBiomeGenForCoords(blockPos).getSpawnableList(enumCreatureType);
    }
}
