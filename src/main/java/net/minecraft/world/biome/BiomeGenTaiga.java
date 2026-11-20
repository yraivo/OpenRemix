package net.minecraft.world.biome;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirtDirtType;
import net.minecraft.block.BlockDoublePlantEnumPlantType;
import net.minecraft.block.BlockTallGrassEnumType;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeGenTaiga extends BiomeGenBase {

    private static WorldGenBlockBlob bIy;
    private static String bIv;
    private int field_150644_aH;
    private static WorldGenMegaPineTree bIz;
    private static WorldGenMegaPineTree bIA;
    private static WorldGenTaiga1 bIB;
    private static WorldGenTaiga2 field_150640_aD;

    public WorldGenAbstractTree genBigTreeChance(Random random) {
        return (WorldGenAbstractTree)
                ((this.field_150644_aH == 1 || this.field_150644_aH == 2) && random.nextInt(3) == 0
                        ? (this.field_150644_aH != 2 && random.nextInt(13) != 0 ? bIz : bIA)
                        : (random.nextInt(3) == 0 ? bIB : net.minecraft.world.biome.BiomeGenTaiga.field_150640_aD));
    }

    // region String decoding support

    // endregion

    public WorldGenerator getRandomWorldGenForGrass(Random random) {
        return random.nextInt(5) > 0
                ? new WorldGenTallGrass(BlockTallGrassEnumType.FERN)
                : new WorldGenTallGrass(BlockTallGrassEnumType.GRASS);
    }

    public BiomeGenTaiga(int i, int j) {
        super(i);
        this.field_150644_aH = j;
        this.spawnableCreatureList.add(new BiomeGenBaseSpawnListEntry(EntityWolf.class, 8, 4, 4));
        this.theBiomeDecorator.treesPerChunk = 10;
        if (j != 1 && j != 2) {
            this.theBiomeDecorator.grassPerChunk = 1;
            this.theBiomeDecorator.mushroomsPerChunk = 1;
        } else {
            this.theBiomeDecorator.grassPerChunk = 7;
            this.theBiomeDecorator.deadBushPerChunk = 1;
            this.theBiomeDecorator.mushroomsPerChunk = 3;
        }
    }

    // region String decoding support

    // endregion

    public void decorate(World world, Random random, BlockPos blockPos) {
        boolean flag = WorldChunkManager.d();
        if (this.field_150644_aH == 1 || this.field_150644_aH == 2) {
            int i = random.nextInt(3);
            int j = 0;

            while (j < i) {
                int k = random.nextInt(16) + 8;
                int l = random.nextInt(16) + 8;
                BlockPos blockPosValue = world.getHorizon(blockPos.add(k, 0, l));
                bIy.generate(world, random, blockPosValue);
                j++;
                if (flag) {
                    break;
                }
            }
        }

        net.minecraft.world.biome.BiomeGenBase.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlantEnumPlantType.FERN);
        int i1 = 0;

        while (i1 < 7) {
            int j1 = random.nextInt(16) + 8;
            int k1 = random.nextInt(16) + 8;
            int l1 = random.nextInt(world.getHorizon(blockPos.add(j1, 0, k1)).getY() + 32);
            net.minecraft.world.biome.BiomeGenBase.DOUBLE_PLANT_GENERATOR.generate(
                    world, random, blockPos.add(j1, l1, k1));
            i1++;
            if (flag) {
                break;
            }
        }

        super.decorate(world, random, blockPos);
    }

    static {
        bIv = "Mega Spruce Taiga";
        bIB = new WorldGenTaiga1();
        net.minecraft.world.biome.BiomeGenTaiga.field_150640_aD = new WorldGenTaiga2(false);
        bIz = new WorldGenMegaPineTree(false, false);
        bIA = new WorldGenMegaPineTree(false, true);
        bIy = new WorldGenBlockBlob(Blocks.mossy_cobblestone, 0);
    }

    protected BiomeGenBase createMutatedBiome(int i) {
        return this.biomeID == BiomeGenBase.megaTaiga.biomeID
                ? new BiomeGenTaiga(i, 2)
                        .func_150557_a(5858897, true)
                        .setBiomeName(bIv)
                        .setFillerBlockMetadata(5159473)
                        .setTemperatureRainfall(0.25F, 0.8F)
                        .setHeight(new BiomeGenBaseHeight(this.minHeight, this.maxHeight))
                : super.createMutatedBiome(i);
    }

    public void genTerrainBlocks(
            World world, Random random, ChunkPrimer chunkPrimer, int i, int j, double d0) {
        boolean flag = WorldChunkManager.d();
        if (this.field_150644_aH == 1 || this.field_150644_aH == 2) {
            this.topBlock = Blocks.grass.getDefaultState();
            this.fillerBlock = Blocks.dirt.getDefaultState();
            if (d0 > 1.75) {
                this.topBlock =
                        Blocks.dirt
                                .getDefaultState()
                                .withProperty(BlockDirt.VARIANT, BlockDirtDirtType.COARSE_DIRT);
                if (!(!flag)) {

                    if (d0 > -0.95) {
                        this.topBlock =
                                Blocks.dirt
                                        .getDefaultState()
                                        .withProperty(BlockDirt.VARIANT, BlockDirtDirtType.PODZOL);
                    }
                }
            } else {

                if (d0 > -0.95) {
                    this.topBlock =
                            Blocks.dirt
                                    .getDefaultState()
                                    .withProperty(BlockDirt.VARIANT, BlockDirtDirtType.PODZOL);
                }
            }
        }

        this.generateBiomeTerrain(world, random, chunkPrimer, i, j, d0);
    }
}
