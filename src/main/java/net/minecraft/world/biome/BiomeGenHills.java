package net.minecraft.world.biome;

import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSilverfishEnumType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeGenHills extends BiomeGenBase {
    private int field_150635_aE;
    private WorldGenTaiga2 field_150634_aD;

    private static String bIv;
    private int field_150638_aH;
    private WorldGenerator theWorldGenerator =
            new WorldGenMinable(
                    Blocks.monster_egg
                            .getDefaultState()
                            .withProperty(
                                    BlockSilverfish.VARIANT_PROP, BlockSilverfishEnumType.STONE),
                    9);
    private int field_150636_aF;
    private int field_150637_aG;

    protected BiomeGenBase createMutatedBiome(int i) {
        return new BiomeGenHills(i, false).mutateHills(this);
    }

    // region String decoding support

    // endregion

    private BiomeGenHills mutateHills(BiomeGenBase biomeGenBase) {
        this.field_150638_aH = this.field_150637_aG;
        this.func_150557_a(biomeGenBase.color, true);
        this.setBiomeName(biomeGenBase.biomeName + bIv);
        this.setHeight(new BiomeGenBaseHeight(biomeGenBase.minHeight, biomeGenBase.maxHeight));
        this.setTemperatureRainfall(biomeGenBase.temperature, biomeGenBase.rainfall);
        return this;
    }

    protected BiomeGenHills(int i, boolean flag) {
        super(i);
        this.field_150634_aD = new WorldGenTaiga2(false);
        this.field_150635_aE = 0;
        this.field_150636_aF = 1;
        this.field_150637_aG = 2;
        this.field_150638_aH = this.field_150635_aE;
        if (flag) {
            this.theBiomeDecorator.treesPerChunk = 3;
            this.field_150638_aH = this.field_150636_aF;
        }
    }

    public void decorate(World world, Random random, BlockPos blockPos) {
        super.decorate(world, random, blockPos);
        int i = 3 + random.nextInt(6);
        boolean flag1 = WorldChunkManager.method_d87039b07bd6961907f2();
        int j = 0;
        boolean flag = flag1;

        while (j < i) {
            int k = random.nextInt(16);
            int l = random.nextInt(28) + 4;
            int i1 = random.nextInt(16);
            BlockPos blockPosValue = blockPos.add(k, l, i1);
            if (world.getBlockState(blockPosValue).getBlock() == Blocks.stone) {
                world.setBlockState(blockPosValue, Blocks.emerald_ore.getDefaultState(), 2);
            }

            j++;
            if (!flag) {
                break;
            }
        }

        i = 0;

        while (i < 7) {
            j = random.nextInt(16);
            int j1 = random.nextInt(64);
            int k1 = random.nextInt(16);
            this.theWorldGenerator.generate(world, random, blockPos.add(j, j1, k1));
            i++;
            if (!flag) {
                break;
            }
        }
    }

    public void genTerrainBlocks(
            World world, Random random, ChunkPrimer chunkPrimer, int i, int j, double d0) {
        {
            this.topBlock = Blocks.grass.getDefaultState();
            boolean flag1 = WorldChunkManager.d();
            this.fillerBlock = Blocks.dirt.getDefaultState();
            boolean flag = flag1;
            if ((d0 < -1.0 || d0 > 2.0) && this.field_150638_aH == this.field_150637_aG) {
                this.topBlock = Blocks.gravel.getDefaultState();
                this.fillerBlock = Blocks.gravel.getDefaultState();
                if (!(!flag)) {

                    if (d0 > 1.0 && this.field_150638_aH != this.field_150636_aF) {
                        this.topBlock = Blocks.stone.getDefaultState();
                        this.fillerBlock = Blocks.stone.getDefaultState();
                    }
                }
            } else {

                if (d0 > 1.0 && this.field_150638_aH != this.field_150636_aF) {
                    this.topBlock = Blocks.stone.getDefaultState();
                    this.fillerBlock = Blocks.stone.getDefaultState();
                }
            }
        }

        this.generateBiomeTerrain(world, random, chunkPrimer, i, j, d0);
    }

    // region String decoding support

    // endregion

    public WorldGenAbstractTree genBigTreeChance(Random random) {
        return (WorldGenAbstractTree)
                (random.nextInt(3) > 0 ? this.field_150634_aD : super.genBigTreeChance(random));
    }

    static {
        bIv = " M";
    }
}
