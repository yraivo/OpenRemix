package net.minecraft.world.biome;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockFlowerEnumFlowerType;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneEnumType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.ChunkProviderSettingsFactory;
import net.minecraft.world.gen.GeneratorBushFeature;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeDecorator {
    protected WorldGenerator gravelAsSandGen;
    protected int sandPerChunk2;
    private static String[] packedTextFragments;
    protected WorldGenerator clayGen = new WorldGenClay(4);
    protected int treesPerChunk;
    protected WorldGenerator ironGen;
    protected WorldGenerator andesiteGen;
    protected WorldGenerator dirtGen;
    protected WorldGenFlowers yellowFlowerGen;
    protected int cactiPerChunk;
    protected World currentWorld;
    protected int mushroomsPerChunk;
    protected int flowersPerChunk;
    protected int reedsPerChunk;
    protected Random randomGenerator;
    protected WorldGenerator bigMushroomGen;
    private static String Eo;
    protected int bigMushroomsPerChunk;
    protected WorldGenerator cactusGen;
    protected WorldGenerator waterlilyGen;
    protected WorldGenerator reedGen;
    protected WorldGenerator redstoneGen;
    protected WorldGenerator diamondGen;
    protected WorldGenerator sandGen = new WorldGenSand(Blocks.sand, 7);
    protected WorldGenerator goldGen;
    protected WorldGenerator mushroomBrownGen;
    protected int clayPerChunk;
    protected WorldGenerator graniteGen;
    protected BlockPos field_180294_c;
    public boolean generateLakes;
    protected WorldGenerator dioriteGen;
    protected ChunkProviderSettings chunkProviderSettings;
    protected int grassPerChunk;
    protected int sandPerChunk;
    protected WorldGenerator coalGen;
    protected WorldGenerator gravelGen;
    protected WorldGenerator lapisGen;
    protected int waterlilyPerChunk;
    protected int deadBushPerChunk;
    protected WorldGenerator mushroomRedGen;

    public void decorate(World world, Random random, BiomeGenBase biomeGenBase, BlockPos blockPos) {
        boolean flag = WorldChunkManager.method_d87039b07bd6961907f2();
        if (this.currentWorld != null) {
            throw new RuntimeException(Eo);
        }

        label17:
        {
            this.currentWorld = world;
            String text = world.getWorldInfo().getGeneratorOptions();
            if (text != null) {
                this.chunkProviderSettings = ChunkProviderSettingsFactory.jsonToFactory(text).WS();
                if (flag) {
                    break label17;
                }

                Entity.setLegacyGlobalFlowMarker(new String[1]);
            }

            this.chunkProviderSettings =
                    ChunkProviderSettingsFactory.jsonToFactory(packedTextFragments[0]).WS();
        }

        this.randomGenerator = random;
        this.field_180294_c = blockPos;
        this.dirtGen =
                new WorldGenMinable(
                        Blocks.dirt.getDefaultState(), this.chunkProviderSettings.dirtSize);
        this.gravelGen =
                new WorldGenMinable(
                        Blocks.gravel.getDefaultState(), this.chunkProviderSettings.gravelSize);
        this.graniteGen =
                new WorldGenMinable(
                        Blocks.stone
                                .getDefaultState()
                                .withProperty(BlockStone.VARIANT_PROP, BlockStoneEnumType.GRANITE),
                        this.chunkProviderSettings.graniteSize);
        this.dioriteGen =
                new WorldGenMinable(
                        Blocks.stone
                                .getDefaultState()
                                .withProperty(BlockStone.VARIANT_PROP, BlockStoneEnumType.DIORITE),
                        this.chunkProviderSettings.dioriteSize);
        this.andesiteGen =
                new WorldGenMinable(
                        Blocks.stone
                                .getDefaultState()
                                .withProperty(BlockStone.VARIANT_PROP, BlockStoneEnumType.ANDESITE),
                        this.chunkProviderSettings.andesiteSize);
        this.coalGen =
                new WorldGenMinable(
                        Blocks.coal_ore.getDefaultState(), this.chunkProviderSettings.coalSize);
        this.ironGen =
                new WorldGenMinable(
                        Blocks.iron_ore.getDefaultState(), this.chunkProviderSettings.ironSize);
        this.goldGen =
                new WorldGenMinable(
                        Blocks.gold_ore.getDefaultState(), this.chunkProviderSettings.goldSize);
        this.redstoneGen =
                new WorldGenMinable(
                        Blocks.redstone_ore.getDefaultState(),
                        this.chunkProviderSettings.redstoneSize);
        this.diamondGen =
                new WorldGenMinable(
                        Blocks.diamond_ore.getDefaultState(),
                        this.chunkProviderSettings.diamondSize);
        this.lapisGen =
                new WorldGenMinable(
                        Blocks.lapis_ore.getDefaultState(), this.chunkProviderSettings.lapisSize);
        this.genDecorations(biomeGenBase);
        this.currentWorld = null;
        this.randomGenerator = null;
    }

    // region String decoding support

    // endregion

    protected void genStandardOre1(int i, WorldGenerator worldGenerator, int j, int k) {
        boolean flag;
        label32:
        {
            flag = WorldChunkManager.method_d87039b07bd6961907f2();
            if (k < j) {
                int l = j;
                j = k;
                k = l;
                if (flag) {
                    break label32;
                }
            }

            if (k == j) {
                if (j < 255) {
                    k++;
                    if (!(flag)) {

                        j--;
                    }
                } else {

                    j--;
                }
            }
        }

        int i1 = 0;

        while (i1 < i) {
            BlockPos blockPos =
                    this.field_180294_c.add(
                            this.randomGenerator.nextInt(16),
                            this.randomGenerator.nextInt(k - j) + j,
                            this.randomGenerator.nextInt(16));
            worldGenerator.generate(this.currentWorld, this.randomGenerator, blockPos);
            i1++;
            if (!flag) {
                break;
            }
        }
    }

    // region String decoding support

    // endregion

    protected void generateOres() {
        boolean flag1 = WorldChunkManager.method_d87039b07bd6961907f2();
        this.genStandardOre1(
                this.chunkProviderSettings.dirtCount,
                this.dirtGen,
                this.chunkProviderSettings.dirtMinHeight,
                this.chunkProviderSettings.dirtMaxHeight);
        this.genStandardOre1(
                this.chunkProviderSettings.gravelCount,
                this.gravelGen,
                this.chunkProviderSettings.gravelMinHeight,
                this.chunkProviderSettings.gravelMaxHeight);
        this.genStandardOre1(
                this.chunkProviderSettings.dioriteCount,
                this.dioriteGen,
                this.chunkProviderSettings.dioriteMinHeight,
                this.chunkProviderSettings.dioriteMaxHeight);
        this.genStandardOre1(
                this.chunkProviderSettings.graniteCount,
                this.graniteGen,
                this.chunkProviderSettings.graniteMinHeight,
                this.chunkProviderSettings.graniteMaxHeight);
        this.genStandardOre1(
                this.chunkProviderSettings.andesiteCount,
                this.andesiteGen,
                this.chunkProviderSettings.andesiteMinHeight,
                this.chunkProviderSettings.andesiteMaxHeight);
        boolean flag = flag1;
        this.genStandardOre1(
                this.chunkProviderSettings.coalCount,
                this.coalGen,
                this.chunkProviderSettings.coalMinHeight,
                this.chunkProviderSettings.coalMaxHeight);
        this.genStandardOre1(
                this.chunkProviderSettings.ironCount,
                this.ironGen,
                this.chunkProviderSettings.ironMinHeight,
                this.chunkProviderSettings.ironMaxHeight);
        this.genStandardOre1(
                this.chunkProviderSettings.goldCount,
                this.goldGen,
                this.chunkProviderSettings.goldMinHeight,
                this.chunkProviderSettings.goldMaxHeight);
        this.genStandardOre1(
                this.chunkProviderSettings.redstoneCount,
                this.redstoneGen,
                this.chunkProviderSettings.redstoneMinHeight,
                this.chunkProviderSettings.redstoneMaxHeight);
        this.genStandardOre1(
                this.chunkProviderSettings.diamondCount,
                this.diamondGen,
                this.chunkProviderSettings.diamondMinHeight,
                this.chunkProviderSettings.diamondMaxHeight);
        this.genStandardOre2(
                this.chunkProviderSettings.lapisCount,
                this.lapisGen,
                this.chunkProviderSettings.lapisCenterHeight,
                this.chunkProviderSettings.lapisSpread);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            WorldChunkManager.method_a8745b073fa8adfa1857(!flag);
        }
    }

    public BiomeDecorator() {
        this.gravelAsSandGen = new WorldGenSand(Blocks.gravel, 6);
        this.yellowFlowerGen =
                new WorldGenFlowers(Blocks.yellow_flower, BlockFlowerEnumFlowerType.DANDELION);
        this.mushroomBrownGen = new GeneratorBushFeature(Blocks.brown_mushroom);
        this.mushroomRedGen = new GeneratorBushFeature(Blocks.red_mushroom);
        this.bigMushroomGen = new WorldGenBigMushroom();
        this.reedGen = new WorldGenReed();
        this.cactusGen = new WorldGenCactus();
        this.waterlilyGen = new WorldGenWaterlily();
        this.flowersPerChunk = 2;
        this.grassPerChunk = 1;
        this.sandPerChunk = 1;
        this.sandPerChunk2 = 3;
        this.clayPerChunk = 1;
        this.generateLakes = true;
    }

    protected void genDecorations(BiomeGenBase biomeGenBase) {
        this.generateOres();
        boolean flag1 = WorldChunkManager.d();
        int i = 0;
        boolean flag = flag1;

        while (i < this.sandPerChunk2) {
            int j = this.randomGenerator.nextInt(16) + 8;
            int k = this.randomGenerator.nextInt(16) + 8;
            this.sandGen.generate(
                    this.currentWorld,
                    this.randomGenerator,
                    this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(j, 0, k)));
            i++;
            if (flag) {
                break;
            }
        }

        i = 0;

        while (i < this.clayPerChunk) {
            int i1 = this.randomGenerator.nextInt(16) + 8;
            int l1 = this.randomGenerator.nextInt(16) + 8;
            this.clayGen.generate(
                    this.currentWorld,
                    this.randomGenerator,
                    this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(i1, 0, l1)));
            i++;
            if (flag) {
                break;
            }
        }

        i = 0;

        while (i < this.sandPerChunk) {
            int j1 = this.randomGenerator.nextInt(16) + 8;
            int i2 = this.randomGenerator.nextInt(16) + 8;
            this.gravelAsSandGen.generate(
                    this.currentWorld,
                    this.randomGenerator,
                    this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(j1, 0, i2)));
            i++;
            if (flag) {
                break;
            }
        }

        i = this.treesPerChunk;
        if (this.randomGenerator.nextInt(10) == 0) {
            i++;
        }

        int k1 = 0;

        while (k1 < i) {
            int j2 = this.randomGenerator.nextInt(16) + 8;
            int l = this.randomGenerator.nextInt(16) + 8;
            WorldGenAbstractTree worldGenAbstractTree =
                    biomeGenBase.genBigTreeChance(this.randomGenerator);
            worldGenAbstractTree.func_175904_e();
            BlockPos blockPos = this.currentWorld.getHorizon(this.field_180294_c.add(j2, 0, l));
            if (worldGenAbstractTree.generate(this.currentWorld, this.randomGenerator, blockPos)) {
                worldGenAbstractTree.generatePodzol(
                        this.currentWorld, this.randomGenerator, blockPos);
            }

            k1++;
            if (flag) {
                break;
            }
        }

        k1 = 0;

        while (k1 < this.bigMushroomsPerChunk) {
            int k2 = this.randomGenerator.nextInt(16) + 8;
            int l5 = this.randomGenerator.nextInt(16) + 8;
            this.bigMushroomGen.generate(
                    this.currentWorld,
                    this.randomGenerator,
                    this.currentWorld.getHorizon(this.field_180294_c.add(k2, 0, l5)));
            k1++;
            if (flag) {
                break;
            }
        }

        k1 = 0;

        while (k1 < this.flowersPerChunk) {
            int l2 = this.randomGenerator.nextInt(16) + 8;
            int i6 = this.randomGenerator.nextInt(16) + 8;
            int i9 =
                    this.randomGenerator.nextInt(
                            this.currentWorld.getHorizon(this.field_180294_c.add(l2, 0, i6)).getY()
                                    + 32);
            BlockPos blockPosValue = this.field_180294_c.add(l2, i9, i6);
            BlockFlowerEnumFlowerType blockFlowerEnumFlowerType =
                    biomeGenBase.pickRandomFlower(this.randomGenerator, blockPosValue);
            BlockFlower blockFlower = blockFlowerEnumFlowerType.getBlockType().getBlock();
            if (blockFlower.getMaterial() != Material.AIR) {
                this.yellowFlowerGen.setGeneratedBlock(blockFlower, blockFlowerEnumFlowerType);
                this.yellowFlowerGen.generate(
                        this.currentWorld, this.randomGenerator, blockPosValue);
            }

            k1++;
            if (flag) {
                break;
            }
        }

        k1 = 0;

        while (k1 < this.grassPerChunk) {
            int i3 = this.randomGenerator.nextInt(16) + 8;
            int j6 = this.randomGenerator.nextInt(16) + 8;
            int j9 =
                    this.randomGenerator.nextInt(
                            this.currentWorld.getHorizon(this.field_180294_c.add(i3, 0, j6)).getY()
                                    * 2);
            biomeGenBase
                    .getRandomWorldGenForGrass(this.randomGenerator)
                    .generate(
                            this.currentWorld,
                            this.randomGenerator,
                            this.field_180294_c.add(i3, j9, j6));
            k1++;
            if (flag) {
                break;
            }
        }

        k1 = 0;

        while (k1 < this.deadBushPerChunk) {
            int j3 = this.randomGenerator.nextInt(16) + 8;
            int k6 = this.randomGenerator.nextInt(16) + 8;
            int k9 =
                    this.randomGenerator.nextInt(
                            this.currentWorld.getHorizon(this.field_180294_c.add(j3, 0, k6)).getY()
                                    * 2);
            new WorldGenDeadBush()
                    .generate(
                            this.currentWorld,
                            this.randomGenerator,
                            this.field_180294_c.add(j3, k9, k6));
            k1++;
            if (flag) {
                break;
            }
        }

        k1 = 0;

        label139:
        while (k1 < this.waterlilyPerChunk) {
            int k3 = this.randomGenerator.nextInt(16) + 8;
            int l6 = this.randomGenerator.nextInt(16) + 8;
            int l9 =
                    this.randomGenerator.nextInt(
                            this.currentWorld.getHorizon(this.field_180294_c.add(k3, 0, l6)).getY()
                                    * 2);
            BlockPos blockPosValue2 = this.field_180294_c.add(k3, l9, l6);

            while (true) {
                if (blockPosValue2.getY() > 0) {
                    BlockPos blockPosValue3 = blockPosValue2.offsetDown();
                    if (this.currentWorld.isAirBlock(blockPosValue3)) {
                        blockPosValue2 = blockPosValue3;
                        if (!flag) {
                            continue;
                        }
                    }
                }

                this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, blockPosValue2);
                k1++;
                if (flag) {
                    break label139;
                }
                break;
            }
        }

        k1 = 0;

        while (k1 < this.mushroomsPerChunk) {
            if (this.randomGenerator.nextInt(4) == 0) {
                int l3 = this.randomGenerator.nextInt(16) + 8;
                int i7 = this.randomGenerator.nextInt(16) + 8;
                BlockPos blockPosValue4 =
                        this.currentWorld.getHorizon(this.field_180294_c.add(l3, 0, i7));
                this.mushroomBrownGen.generate(
                        this.currentWorld, this.randomGenerator, blockPosValue4);
            }

            if (this.randomGenerator.nextInt(8) == 0) {
                int i4 = this.randomGenerator.nextInt(16) + 8;
                int j7 = this.randomGenerator.nextInt(16) + 8;
                int i10 =
                        this.randomGenerator.nextInt(
                                this.currentWorld
                                                .getHorizon(this.field_180294_c.add(i4, 0, j7))
                                                .getY()
                                        * 2);
                BlockPos blockPosValue5 = this.field_180294_c.add(i4, i10, j7);
                this.mushroomRedGen.generate(
                        this.currentWorld, this.randomGenerator, blockPosValue5);
            }

            k1++;
            if (flag) {
                break;
            }
        }

        if (this.randomGenerator.nextInt(4) == 0) {
            k1 = this.randomGenerator.nextInt(16) + 8;
            int j4 = this.randomGenerator.nextInt(16) + 8;
            int k7 =
                    this.randomGenerator.nextInt(
                            this.currentWorld.getHorizon(this.field_180294_c.add(k1, 0, j4)).getY()
                                    * 2);
            this.mushroomBrownGen.generate(
                    this.currentWorld, this.randomGenerator, this.field_180294_c.add(k1, k7, j4));
        }

        if (this.randomGenerator.nextInt(8) == 0) {
            k1 = this.randomGenerator.nextInt(16) + 8;
            int k4 = this.randomGenerator.nextInt(16) + 8;
            int l7 =
                    this.randomGenerator.nextInt(
                            this.currentWorld.getHorizon(this.field_180294_c.add(k1, 0, k4)).getY()
                                    * 2);
            this.mushroomRedGen.generate(
                    this.currentWorld, this.randomGenerator, this.field_180294_c.add(k1, l7, k4));
        }

        k1 = 0;

        while (k1 < this.reedsPerChunk) {
            int l4 = this.randomGenerator.nextInt(16) + 8;
            int i8 = this.randomGenerator.nextInt(16) + 8;
            int j10 =
                    this.randomGenerator.nextInt(
                            this.currentWorld.getHorizon(this.field_180294_c.add(l4, 0, i8)).getY()
                                    * 2);
            this.reedGen.generate(
                    this.currentWorld, this.randomGenerator, this.field_180294_c.add(l4, j10, i8));
            k1++;
            if (flag) {
                break;
            }
        }

        k1 = 0;

        while (k1 < 10) {
            int i5 = this.randomGenerator.nextInt(16) + 8;
            int j8 = this.randomGenerator.nextInt(16) + 8;
            int k10 =
                    this.randomGenerator.nextInt(
                            this.currentWorld.getHorizon(this.field_180294_c.add(i5, 0, j8)).getY()
                                    * 2);
            this.reedGen.generate(
                    this.currentWorld, this.randomGenerator, this.field_180294_c.add(i5, k10, j8));
            k1++;
            if (flag) {
                break;
            }
        }

        if (this.randomGenerator.nextInt(32) == 0) {
            k1 = this.randomGenerator.nextInt(16) + 8;
            int j5 = this.randomGenerator.nextInt(16) + 8;
            int k8 =
                    this.randomGenerator.nextInt(
                            this.currentWorld.getHorizon(this.field_180294_c.add(k1, 0, j5)).getY()
                                    * 2);
            new WorldGenPumpkin()
                    .generate(
                            this.currentWorld,
                            this.randomGenerator,
                            this.field_180294_c.add(k1, k8, j5));
        }

        k1 = 0;

        while (k1 < this.cactiPerChunk) {
            int k5 = this.randomGenerator.nextInt(16) + 8;
            int l8 = this.randomGenerator.nextInt(16) + 8;
            int l10 =
                    this.randomGenerator.nextInt(
                            this.currentWorld.getHorizon(this.field_180294_c.add(k5, 0, l8)).getY()
                                    * 2);
            this.cactusGen.generate(
                    this.currentWorld, this.randomGenerator, this.field_180294_c.add(k5, l10, l8));
            k1++;
            if (flag) {
                break;
            }
        }

        if (this.generateLakes) {
            k1 = 0;

            while (k1 < 50) {
                BlockPos blockPosValue6 =
                        this.field_180294_c.add(
                                this.randomGenerator.nextInt(16) + 8,
                                this.randomGenerator.nextInt(this.randomGenerator.nextInt(248) + 8),
                                this.randomGenerator.nextInt(16) + 8);
                new WorldGenLiquids(Blocks.flowing_water)
                        .generate(this.currentWorld, this.randomGenerator, blockPosValue6);
                k1++;
                if (flag) {
                    break;
                }
            }

            k1 = 0;

            while (k1 < 20) {
                BlockPos blockPosValue7 =
                        this.field_180294_c.add(
                                this.randomGenerator.nextInt(16) + 8,
                                this.randomGenerator.nextInt(
                                        this.randomGenerator.nextInt(
                                                        this.randomGenerator.nextInt(240) + 8)
                                                + 8),
                                this.randomGenerator.nextInt(16) + 8);
                new WorldGenLiquids(Blocks.flowing_lava)
                        .generate(this.currentWorld, this.randomGenerator, blockPosValue7);
                k1++;
                if (flag) {
                    break;
                }
            }
        }
    }

    static {
        net.minecraft.world.biome.BiomeDecorator.packedTextFragments = new String[2];
        net.minecraft.world.biome.BiomeDecorator.packedTextFragments[0] = new String("");
        net.minecraft.world.biome.BiomeDecorator.packedTextFragments[1] =
                new String("4t .\u0018\u0002\u0017U|7(\u0016\u0014\u000f\u0001q<,");
        Eo = "Already decorating";
    }

    private static RuntimeException method_1a1f08aee5fe72868e61(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    protected void genStandardOre2(int i, WorldGenerator worldGenerator, int j, int k) {
        boolean flag1 = WorldChunkManager.d();
        int l = 0;
        boolean flag = flag1;

        while (l < i) {
            BlockPos blockPos =
                    this.field_180294_c.add(
                            this.randomGenerator.nextInt(16),
                            this.randomGenerator.nextInt(k)
                                    + this.randomGenerator.nextInt(k)
                                    + j
                                    - k,
                            this.randomGenerator.nextInt(16));
            worldGenerator.generate(this.currentWorld, this.randomGenerator, blockPos);
            l++;
            if (flag) {
                break;
            }
        }
    }
}
