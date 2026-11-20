package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.block.BlockFlowerEnumFlowerType;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandEnumType;
import net.minecraft.block.BlockTallGrassEnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenSwamp;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class BiomeGenBase {
    protected static BiomeGenBaseHeight height_MidHills;
    protected List spawnableMonsterList;
    public int field_150609_ah;
    protected static BiomeGenBaseHeight height_Default;
    public static BiomeGenBase coldTaigaHills;
    public static BiomeGenBase icePlains;
    public static BiomeGenBase frozenOcean;
    public float temperature;
    protected static BiomeGenBaseHeight height_ShallowWaters;
    public static BiomeGenBase taigaHills;
    public int waterColorMultiplier;
    public int fillerBlockMetadata;
    public static BiomeGenBase megaTaigaHills;
    public static BiomeGenBase forestHills;
    public static BiomeGenBase beach;
    protected static WorldGenDoublePlant DOUBLE_PLANT_GENERATOR;
    public static BiomeGenBase forest;
    private static BiomeGenBase[] biomeList;
    public static BiomeGenBase iceMountains;
    public String biomeName;
    protected WorldGenBigTree worldGeneratorBigTree;
    public static BiomeGenBase hell;
    public static BiomeGenBase field_180279_ad;
    public static Map BIOME_ID_MAP;
    protected static BiomeGenBaseHeight height_LowIslands;

    protected static BiomeGenBaseHeight height_PartiallySubmerged;
    public static BiomeGenBase deepOcean;
    public static BiomeGenBase jungle;
    public static BiomeGenBase jungleHills;
    protected static BiomeGenBaseHeight height_LowHills;
    protected WorldGenSwamp worldGeneratorSwamp;
    public float maxHeight;
    public static BiomeGenBase taiga;
    public static BiomeGenBase mesaPlateau;
    public static BiomeGenBase megaTaiga;
    public static BiomeGenBase desert;
    public static BiomeGenBase savanna;
    public static BiomeGenBase savannaPlateau;
    protected List spawnableWaterCreatureList;
    public int color;
    public static BiomeGenBase birchForestHills;
    public static Set explorationBiomesList;
    public BiomeDecorator theBiomeDecorator;
    public static BiomeGenBase desertHills;
    public float minHeight;
    public static BiomeGenBase jungleEdge;
    protected static BiomeGenBaseHeight height_Shores;
    public static BiomeGenBase birchForest;
    public static BiomeGenBase extremeHills;
    public static BiomeGenBase coldTaiga;
    public static BiomeGenBase mesaPlateau_F;
    private static Logger logger;
    public static BiomeGenBase roofedForest;
    public static BiomeGenBase ocean;
    public static BiomeGenBase extremeHillsPlus;
    public float rainfall;
    public static BiomeGenBase extremeHillsEdge;
    protected static NoiseGeneratorPerlin temperatureNoise;
    protected static BiomeGenBaseHeight height_LowPlains;
    protected static BiomeGenBaseHeight height_Oceans;

    public static BiomeGenBase mushroomIsland;
    protected boolean enableRain;
    public static BiomeGenBase mesa;
    public BlockState topBlock = Blocks.grass.getDefaultState();
    private static int[] bKM;
    protected WorldGenTrees worldGeneratorTrees;
    public static BiomeGenBase swampland;
    public BlockState fillerBlock = Blocks.dirt.getDefaultState();
    protected static BiomeGenBaseHeight height_MidPlains;
    protected boolean enableSnow;

    public static BiomeGenBase plains;
    public static BiomeGenBase stoneBeach;
    protected List spawnableCaveCreatureList;
    protected static BiomeGenBaseHeight height_DeepOceans;
    protected static BiomeGenBaseHeight height_HighPlateaus;
    public int biomeID;
    protected List spawnableCreatureList;
    public static BiomeGenBase sky;
    public static BiomeGenBase frozenRiver;
    public static BiomeGenBase coldBeach;
    public static BiomeGenBase mushroomIslandShore;
    protected static NoiseGeneratorPerlin GRASS_COLOR_NOISE;
    protected static BiomeGenBaseHeight height_RockyWaters;
    public static BiomeGenBase river;

    public final float getFloatRainfall() {
        return this.rainfall;
    }

    public final int getIntRainfall() {
        return (int) (this.rainfall * 65536.0F);
    }

    public List getSpawnableList(EnumCreatureType enumCreatureType) {
        switch (enumCreatureType) {
            case MONSTER:
                return this.spawnableMonsterList;
            case CREATURE:
                return this.spawnableCreatureList;
            case WATER_CREATURE:
                return this.spawnableWaterCreatureList;
            case AMBIENT:
                return this.spawnableCaveCreatureList;
            default:
                return Collections.emptyList();
        }
    }

    protected BiomeGenBase func_150563_c(int i) {
        this.field_150609_ah = i;
        return this;
    }

    public WorldGenAbstractTree genBigTreeChance(Random random) {
        return (WorldGenAbstractTree)
                (random.nextInt(10) == 0 ? this.worldGeneratorBigTree : this.worldGeneratorTrees);
    }

    public boolean isSnowyBiome() {
        return this.enableSnow;
    }

    protected BiomeGenBase setDisableRain() {
        this.enableRain = false;
        return this;
    }

    protected BiomeGenBase(int aLvValue) {
        this.fillerBlockMetadata = 5169201;
        this.minHeight = net.minecraft.world.biome.BiomeGenBase.height_Default.rootHeight;
        this.maxHeight = net.minecraft.world.biome.BiomeGenBase.height_Default.variation;
        this.temperature = 0.5F;
        this.rainfall = 0.5F;
        this.waterColorMultiplier = 16777215;
        this.spawnableMonsterList = Lists.newArrayList();
        this.spawnableCreatureList = Lists.newArrayList();
        this.spawnableWaterCreatureList = Lists.newArrayList();
        this.spawnableCaveCreatureList = Lists.newArrayList();
        this.enableRain = true;
        this.worldGeneratorTrees = new WorldGenTrees(false);
        this.worldGeneratorBigTree = new WorldGenBigTree(false);
        this.worldGeneratorSwamp = new WorldGenSwamp();
        this.biomeID = aLvValue;
        net.minecraft.world.biome.BiomeGenBase.biomeList[aLvValue] = this;
        this.theBiomeDecorator = this.createBiomeDecorator();
        this.spawnableCreatureList.add(new BiomeGenBaseSpawnListEntry(EntitySheep.class, 12, 4, 4));
        this.spawnableCreatureList.add(
                new BiomeGenBaseSpawnListEntry(EntityRabbit.class, 10, 3, 3));
        this.spawnableCreatureList.add(new BiomeGenBaseSpawnListEntry(EntityPig.class, 10, 4, 4));
        this.spawnableCreatureList.add(
                new BiomeGenBaseSpawnListEntry(EntityChicken.class, 10, 4, 4));
        this.spawnableCreatureList.add(new BiomeGenBaseSpawnListEntry(EntityCow.class, 8, 4, 4));
        this.spawnableMonsterList.add(
                new BiomeGenBaseSpawnListEntry(EntitySpider.class, 100, 4, 4));
        this.spawnableMonsterList.add(
                new BiomeGenBaseSpawnListEntry(EntityZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(
                new BiomeGenBaseSpawnListEntry(EntitySkeleton.class, 100, 4, 4));
        this.spawnableMonsterList.add(
                new BiomeGenBaseSpawnListEntry(EntityCreeper.class, 100, 4, 4));
        this.spawnableMonsterList.add(new BiomeGenBaseSpawnListEntry(SlimeEntity.class, 100, 4, 4));
        this.spawnableMonsterList.add(
                new BiomeGenBaseSpawnListEntry(EntityEnderman.class, 10, 1, 4));
        this.spawnableMonsterList.add(new BiomeGenBaseSpawnListEntry(EntityWitch.class, 5, 1, 1));
        this.spawnableWaterCreatureList.add(
                new BiomeGenBaseSpawnListEntry(SquidEntity.class, 10, 4, 4));
        this.spawnableCaveCreatureList.add(
                new BiomeGenBaseSpawnListEntry(BatEntity.class, 10, 8, 8));
    }

    public void genTerrainBlocks(
            World world, Random random, ChunkPrimer chunkPrimer, int i, int j, double d0) {
        this.generateBiomeTerrain(world, random, chunkPrimer, i, j, d0);
    }

    // region String decoding support

    // endregion

    public static BiomeGenBase getBiomeFromBiomeList(int i, BiomeGenBase biomeGenBase) {
        if (i >= 0 && i <= net.minecraft.world.biome.BiomeGenBase.biomeList.length) {
            BiomeGenBase biomeGenBaseValue = net.minecraft.world.biome.BiomeGenBase.biomeList[i];
            return biomeGenBaseValue == null ? biomeGenBase : biomeGenBaseValue;
        } else {
            net.minecraft.world.biome.BiomeGenBase.logger.warn(
                    "Biome ID is out of bounds: " + i + ", defaulting to 0 (Ocean)");
            return net.minecraft.world.biome.BiomeGenBase.ocean;
        }
    }

    protected BiomeGenBase setTemperatureRainfall(float bJQValue, float bKEValue) {
        if (bJQValue > 0.1F && bJQValue < 0.2F) {
            throw new IllegalArgumentException(
                    "Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        }

        this.temperature = bJQValue;
        this.rainfall = bKEValue;
        return this;
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public int getFoliageColorAtPos(BlockPos blockPos) {
        double d0 = MathHelper.clamp(this.getFloatTemperature(blockPos), 0.0F, 1.0F);
        double d1 = MathHelper.clamp(this.getFloatRainfall(), 0.0F, 1.0F);
        return ColorizerFoliage.getFoliageColor(d0, d1);
    }

    public final void generateBiomeTerrain(
            World world, Random random, ChunkPrimer chunkPrimer, int i, int j, double d0) {
        boolean flag1 = WorldChunkManager.method_d87039b07bd6961907f2();

        BlockState bKLValue = this.topBlock;
        boolean flag = flag1;
        BlockState bKPValue = this.fillerBlock;
        int k = -1;
        int l = (int) (d0 / 3.0 + 3.0 + random.nextDouble() * 0.25);
        int i1 = i & 15;
        int j1 = j & 15;
        int k1 = 255;

        while (k1 >= 0) {
            label97:
            {
                if (k1 <= random.nextInt(5)) {
                    chunkPrimer.setBlockState(j1, k1, i1, Blocks.bedrock.getDefaultState());
                    if (flag) {
                        break label97;
                    }
                }

                BlockState blockState = chunkPrimer.getBlockState(j1, k1, i1);
                if (blockState.getBlock().getMaterial() == Material.AIR) {
                    k = -1;
                    if (flag) {
                        break label97;
                    }
                }

                label81:
                if (blockState.getBlock() == Blocks.stone) {
                    if (k == -1) {
                        if (l <= 0) {
                            bKLValue = null;
                            bKPValue = Blocks.stone.getDefaultState();
                        } else if (k1 >= 59 && k1 <= 64) {
                            bKLValue = this.topBlock;
                            bKPValue = this.fillerBlock;
                        }

                        if (k1 < 63
                                && (bKLValue == null
                                        || bKLValue.getBlock().getMaterial() == Material.AIR)) {
                            if (this.getFloatTemperature(new BlockPos(i, k1, j)) < 0.15F) {
                                bKLValue = Blocks.ice.getDefaultState();
                                if (!(flag)) {

                                    bKLValue = Blocks.water.getDefaultState();
                                }
                            } else {

                                bKLValue = Blocks.water.getDefaultState();
                            }
                        }

                        k = l;
                        if (k1 >= 62) {
                            chunkPrimer.setBlockState(j1, k1, i1, bKLValue);
                            if (flag) {
                                break label81;
                            }
                        }

                        if (k1 < 56 - l) {
                            bKLValue = null;
                            bKPValue = Blocks.stone.getDefaultState();
                            chunkPrimer.setBlockState(j1, k1, i1, Blocks.gravel.getDefaultState());
                            break label81;
                        }

                        chunkPrimer.setBlockState(j1, k1, i1, bKPValue);
                        if (flag) {
                            break label81;
                        }
                    }

                    if (k > 0) {
                        k--;
                        chunkPrimer.setBlockState(j1, k1, i1, bKPValue);
                        if (k == 0 && bKPValue.getBlock() == Blocks.sand) {
                            k = random.nextInt(4) + Math.max(0, k1 - 63);
                            bKPValue =
                                    bKPValue.getValue(BlockSand.VARIANT_PROP)
                                                    == BlockSandEnumType.RED_SAND
                                            ? Blocks.red_sandstone.getDefaultState()
                                            : Blocks.sandstone.getDefaultState();
                        }
                    }
                }
            }

            k1--;
            if (!flag) {
                break;
            }
        }
    }

    protected BiomeDecorator createBiomeDecorator() {
        return new BiomeDecorator();
    }

    public static void b(int[] aint) {
        bKM = aint;
    }

    public WorldGenerator getRandomWorldGenForGrass(Random random) {
        return new WorldGenTallGrass(BlockTallGrassEnumType.GRASS);
    }

    protected BiomeGenBase createMutatedBiome(int i) {
        return new BiomeGenMutated(i, this);
    }

    static {
        int[] aint = new int[4];
        b(aint);
        net.minecraft.world.biome.BiomeGenBase.logger = LogManager.getLogger();
        net.minecraft.world.biome.BiomeGenBase.height_Default = new BiomeGenBaseHeight(0.1F, 0.2F);
        net.minecraft.world.biome.BiomeGenBase.height_ShallowWaters =
                new BiomeGenBaseHeight(-0.5F, 0.0F);
        net.minecraft.world.biome.BiomeGenBase.height_Oceans = new BiomeGenBaseHeight(-1.0F, 0.1F);
        net.minecraft.world.biome.BiomeGenBase.height_DeepOceans =
                new BiomeGenBaseHeight(-1.8F, 0.1F);
        net.minecraft.world.biome.BiomeGenBase.height_LowPlains =
                new BiomeGenBaseHeight(0.125F, 0.05F);
        net.minecraft.world.biome.BiomeGenBase.height_MidPlains =
                new BiomeGenBaseHeight(0.2F, 0.2F);
        net.minecraft.world.biome.BiomeGenBase.height_LowHills =
                new BiomeGenBaseHeight(0.45F, 0.3F);
        net.minecraft.world.biome.BiomeGenBase.height_HighPlateaus =
                new BiomeGenBaseHeight(1.5F, 0.025F);
        net.minecraft.world.biome.BiomeGenBase.height_MidHills = new BiomeGenBaseHeight(1.0F, 0.5F);
        net.minecraft.world.biome.BiomeGenBase.height_Shores = new BiomeGenBaseHeight(0.0F, 0.025F);
        net.minecraft.world.biome.BiomeGenBase.height_RockyWaters =
                new BiomeGenBaseHeight(0.1F, 0.8F);
        net.minecraft.world.biome.BiomeGenBase.height_LowIslands =
                new BiomeGenBaseHeight(0.2F, 0.3F);
        net.minecraft.world.biome.BiomeGenBase.height_PartiallySubmerged =
                new BiomeGenBaseHeight(-0.2F, 0.1F);
        net.minecraft.world.biome.BiomeGenBase.biomeList = new BiomeGenBase[256];
        net.minecraft.world.biome.BiomeGenBase.explorationBiomesList = Sets.newHashSet();
        net.minecraft.world.biome.BiomeGenBase.BIOME_ID_MAP = Maps.newHashMap();
        net.minecraft.world.biome.BiomeGenBase.ocean =
                new BiomeGenOcean(0)
                        .setColor(112)
                        .setBiomeName("Ocean")
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_Oceans);
        net.minecraft.world.biome.BiomeGenBase.plains =
                new BiomeGenPlains(1).setColor(9286496).setBiomeName("Plains");
        net.minecraft.world.biome.BiomeGenBase.desert =
                new BiomeGenDesert(2)
                        .setColor(16421912)
                        .setBiomeName("Desert")
                        .setDisableRain()
                        .setTemperatureRainfall(2.0F, 0.0F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowPlains);
        net.minecraft.world.biome.BiomeGenBase.extremeHills =
                new BiomeGenHills(3, false)
                        .setColor(6316128)
                        .setBiomeName("Extreme Hills")
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_MidHills)
                        .setTemperatureRainfall(0.2F, 0.3F);
        net.minecraft.world.biome.BiomeGenBase.forest =
                new BiomeGenForest(4, 0).setColor(353825).setBiomeName("Forest");
        net.minecraft.world.biome.BiomeGenBase.taiga =
                new BiomeGenTaiga(5, 0)
                        .setColor(747097)
                        .setBiomeName("Taiga")
                        .setFillerBlockMetadata(5159473)
                        .setTemperatureRainfall(0.25F, 0.8F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_MidPlains);
        net.minecraft.world.biome.BiomeGenBase.swampland =
                new BiomeGenSwamp(6)
                        .setColor(522674)
                        .setBiomeName("Swampland")
                        .setFillerBlockMetadata(9154376)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_PartiallySubmerged)
                        .setTemperatureRainfall(0.8F, 0.9F);
        net.minecraft.world.biome.BiomeGenBase.river =
                new BiomeGenRiver(7)
                        .setColor(255)
                        .setBiomeName("River")
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_ShallowWaters);
        net.minecraft.world.biome.BiomeGenBase.hell =
                new BiomeGenHell(8)
                        .setColor(16711680)
                        .setBiomeName("Hell")
                        .setDisableRain()
                        .setTemperatureRainfall(2.0F, 0.0F);
        net.minecraft.world.biome.BiomeGenBase.sky =
                new BiomeGenEnd(9).setColor(8421631).setBiomeName("The End").setDisableRain();
        net.minecraft.world.biome.BiomeGenBase.frozenOcean =
                new BiomeGenOcean(10)
                        .setColor(9474208)
                        .setBiomeName("FrozenOcean")
                        .setEnableSnow()
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_Oceans)
                        .setTemperatureRainfall(0.0F, 0.5F);
        net.minecraft.world.biome.BiomeGenBase.frozenRiver =
                new BiomeGenRiver(11)
                        .setColor(10526975)
                        .setBiomeName("FrozenRiver")
                        .setEnableSnow()
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_ShallowWaters)
                        .setTemperatureRainfall(0.0F, 0.5F);
        net.minecraft.world.biome.BiomeGenBase.icePlains =
                new BiomeGenSnow(12, false)
                        .setColor(16777215)
                        .setBiomeName("Ice Plains")
                        .setEnableSnow()
                        .setTemperatureRainfall(0.0F, 0.5F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowPlains);
        net.minecraft.world.biome.BiomeGenBase.iceMountains =
                new BiomeGenSnow(13, false)
                        .setColor(10526880)
                        .setBiomeName("Ice Mountains")
                        .setEnableSnow()
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowHills)
                        .setTemperatureRainfall(0.0F, 0.5F);
        net.minecraft.world.biome.BiomeGenBase.mushroomIsland =
                new BiomeGenMushroomIsland(14)
                        .setColor(16711935)
                        .setBiomeName("MushroomIsland")
                        .setTemperatureRainfall(0.9F, 1.0F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowIslands);
        net.minecraft.world.biome.BiomeGenBase.mushroomIslandShore =
                new BiomeGenMushroomIsland(15)
                        .setColor(10486015)
                        .setBiomeName("MushroomIslandShore")
                        .setTemperatureRainfall(0.9F, 1.0F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_Shores);
        net.minecraft.world.biome.BiomeGenBase.beach =
                new BiomeGenBeach(16)
                        .setColor(16440917)
                        .setBiomeName("Beach")
                        .setTemperatureRainfall(0.8F, 0.4F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_Shores);
        net.minecraft.world.biome.BiomeGenBase.desertHills =
                new BiomeGenDesert(17)
                        .setColor(13786898)
                        .setBiomeName("DesertHills")
                        .setDisableRain()
                        .setTemperatureRainfall(2.0F, 0.0F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowHills);
        net.minecraft.world.biome.BiomeGenBase.forestHills =
                new BiomeGenForest(18, 0)
                        .setColor(2250012)
                        .setBiomeName("ForestHills")
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowHills);
        net.minecraft.world.biome.BiomeGenBase.taigaHills =
                new BiomeGenTaiga(19, 0)
                        .setColor(1456435)
                        .setBiomeName("TaigaHills")
                        .setFillerBlockMetadata(5159473)
                        .setTemperatureRainfall(0.25F, 0.8F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowHills);
        net.minecraft.world.biome.BiomeGenBase.extremeHillsEdge =
                new BiomeGenHills(20, true)
                        .setColor(7501978)
                        .setBiomeName("Extreme Hills Edge")
                        .setHeight(
                                net.minecraft.world.biome.BiomeGenBase.height_MidHills.attenuate())
                        .setTemperatureRainfall(0.2F, 0.3F);
        net.minecraft.world.biome.BiomeGenBase.jungle =
                new BiomeGenJungle(21, false)
                        .setColor(5470985)
                        .setBiomeName("Jungle")
                        .setFillerBlockMetadata(5470985)
                        .setTemperatureRainfall(0.95F, 0.9F);
        net.minecraft.world.biome.BiomeGenBase.jungleHills =
                new BiomeGenJungle(22, false)
                        .setColor(2900485)
                        .setBiomeName("JungleHills")
                        .setFillerBlockMetadata(5470985)
                        .setTemperatureRainfall(0.95F, 0.9F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowHills);
        net.minecraft.world.biome.BiomeGenBase.jungleEdge =
                new BiomeGenJungle(23, true)
                        .setColor(6458135)
                        .setBiomeName("JungleEdge")
                        .setFillerBlockMetadata(5470985)
                        .setTemperatureRainfall(0.95F, 0.8F);
        net.minecraft.world.biome.BiomeGenBase.deepOcean =
                new BiomeGenOcean(24)
                        .setColor(48)
                        .setBiomeName("Deep Ocean")
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_DeepOceans);
        net.minecraft.world.biome.BiomeGenBase.stoneBeach =
                new BiomeGenStoneBeach(25)
                        .setColor(10658436)
                        .setBiomeName("Stone Beach")
                        .setTemperatureRainfall(0.2F, 0.3F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_RockyWaters);
        net.minecraft.world.biome.BiomeGenBase.coldBeach =
                new BiomeGenBeach(26)
                        .setColor(16445632)
                        .setBiomeName("Cold Beach")
                        .setTemperatureRainfall(0.05F, 0.3F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_Shores)
                        .setEnableSnow();
        net.minecraft.world.biome.BiomeGenBase.birchForest =
                new BiomeGenForest(27, 2).setBiomeName("Birch Forest").setColor(3175492);
        net.minecraft.world.biome.BiomeGenBase.birchForestHills =
                new BiomeGenForest(28, 2)
                        .setBiomeName("Birch Forest Hills")
                        .setColor(2055986)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowHills);
        net.minecraft.world.biome.BiomeGenBase.roofedForest =
                new BiomeGenForest(29, 3).setColor(4215066).setBiomeName("Roofed Forest");
        net.minecraft.world.biome.BiomeGenBase.coldTaiga =
                new BiomeGenTaiga(30, 0)
                        .setColor(3233098)
                        .setBiomeName("Cold Taiga")
                        .setFillerBlockMetadata(5159473)
                        .setEnableSnow()
                        .setTemperatureRainfall(-0.5F, 0.4F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_MidPlains)
                        .func_150563_c(16777215);
        net.minecraft.world.biome.BiomeGenBase.coldTaigaHills =
                new BiomeGenTaiga(31, 0)
                        .setColor(2375478)
                        .setBiomeName("Cold Taiga Hills")
                        .setFillerBlockMetadata(5159473)
                        .setEnableSnow()
                        .setTemperatureRainfall(-0.5F, 0.4F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowHills)
                        .func_150563_c(16777215);
        net.minecraft.world.biome.BiomeGenBase.megaTaiga =
                new BiomeGenTaiga(32, 1)
                        .setColor(5858897)
                        .setBiomeName("Mega Taiga")
                        .setFillerBlockMetadata(5159473)
                        .setTemperatureRainfall(0.3F, 0.8F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_MidPlains);
        net.minecraft.world.biome.BiomeGenBase.megaTaigaHills =
                new BiomeGenTaiga(33, 1)
                        .setColor(4542270)
                        .setBiomeName("Mega Taiga Hills")
                        .setFillerBlockMetadata(5159473)
                        .setTemperatureRainfall(0.3F, 0.8F)
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowHills);
        net.minecraft.world.biome.BiomeGenBase.extremeHillsPlus =
                new BiomeGenHills(34, true)
                        .setColor(5271632)
                        .setBiomeName("Extreme Hills+")
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_MidHills)
                        .setTemperatureRainfall(0.2F, 0.3F);
        net.minecraft.world.biome.BiomeGenBase.savanna =
                new BiomeGenSavanna(35)
                        .setColor(12431967)
                        .setBiomeName("Savanna")
                        .setTemperatureRainfall(1.2F, 0.0F)
                        .setDisableRain()
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowPlains);
        net.minecraft.world.biome.BiomeGenBase.savannaPlateau =
                new BiomeGenSavanna(36)
                        .setColor(10984804)
                        .setBiomeName("Savanna Plateau")
                        .setTemperatureRainfall(1.0F, 0.0F)
                        .setDisableRain()
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_HighPlateaus);
        net.minecraft.world.biome.BiomeGenBase.mesa =
                new BiomeGenMesa(37, false, false).setColor(14238997).setBiomeName("Mesa");
        net.minecraft.world.biome.BiomeGenBase.mesaPlateau_F =
                new BiomeGenMesa(38, false, true)
                        .setColor(11573093)
                        .setBiomeName("Mesa Plateau F")
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_HighPlateaus);
        net.minecraft.world.biome.BiomeGenBase.mesaPlateau =
                new BiomeGenMesa(39, false, false)
                        .setColor(13274213)
                        .setBiomeName("Mesa Plateau")
                        .setHeight(net.minecraft.world.biome.BiomeGenBase.height_HighPlateaus);
        net.minecraft.world.biome.BiomeGenBase.field_180279_ad =
                net.minecraft.world.biome.BiomeGenBase.ocean;
        net.minecraft.world.biome.BiomeGenBase.plains.createMutation();
        net.minecraft.world.biome.BiomeGenBase.desert.createMutation();
        net.minecraft.world.biome.BiomeGenBase.forest.createMutation();
        net.minecraft.world.biome.BiomeGenBase.taiga.createMutation();
        net.minecraft.world.biome.BiomeGenBase.swampland.createMutation();
        net.minecraft.world.biome.BiomeGenBase.icePlains.createMutation();
        net.minecraft.world.biome.BiomeGenBase.jungle.createMutation();
        net.minecraft.world.biome.BiomeGenBase.jungleEdge.createMutation();
        net.minecraft.world.biome.BiomeGenBase.coldTaiga.createMutation();
        net.minecraft.world.biome.BiomeGenBase.savanna.createMutation();
        net.minecraft.world.biome.BiomeGenBase.savannaPlateau.createMutation();
        net.minecraft.world.biome.BiomeGenBase.mesa.createMutation();
        net.minecraft.world.biome.BiomeGenBase.mesaPlateau_F.createMutation();
        net.minecraft.world.biome.BiomeGenBase.mesaPlateau.createMutation();
        net.minecraft.world.biome.BiomeGenBase.birchForest.createMutation();
        net.minecraft.world.biome.BiomeGenBase.birchForestHills.createMutation();
        net.minecraft.world.biome.BiomeGenBase.roofedForest.createMutation();
        net.minecraft.world.biome.BiomeGenBase.megaTaiga.createMutation();
        net.minecraft.world.biome.BiomeGenBase.extremeHills.createMutation();
        net.minecraft.world.biome.BiomeGenBase.extremeHillsPlus.createMutation();
        net.minecraft.world.biome.BiomeGenBase.megaTaiga
                .createMutatedBiome(
                        net.minecraft.world.biome.BiomeGenBase.megaTaigaHills.biomeID + 128)
                .setBiomeName("Redwood Taiga Hills M");
        for (BiomeGenBase class_7ceb382e394056308454 :
                net.minecraft.world.biome.BiomeGenBase.biomeList) {
            if (class_7ceb382e394056308454 != null) {
                if (net.minecraft.world.biome.BiomeGenBase.BIOME_ID_MAP.containsKey(
                        class_7ceb382e394056308454.biomeName)) {
                    throw new Error(
                            "Biome \""
                                    + class_7ceb382e394056308454.biomeName
                                    + "\" is defined as both ID "
                                    + ((BiomeGenBase)
                                                    net.minecraft.world.biome.BiomeGenBase
                                                            .BIOME_ID_MAP
                                                            .get(
                                                                    class_7ceb382e394056308454
                                                                            .biomeName))
                                            .biomeID
                                    + " and "
                                    + class_7ceb382e394056308454.biomeID);
                }

                net.minecraft.world.biome.BiomeGenBase.BIOME_ID_MAP.put(
                        class_7ceb382e394056308454.biomeName, class_7ceb382e394056308454);
                if (class_7ceb382e394056308454.biomeID < 128) {
                    net.minecraft.world.biome.BiomeGenBase.explorationBiomesList.add(
                            class_7ceb382e394056308454);
                }
            }
        }
        net.minecraft.world.biome.BiomeGenBase.explorationBiomesList.remove(
                net.minecraft.world.biome.BiomeGenBase.hell);
        net.minecraft.world.biome.BiomeGenBase.explorationBiomesList.remove(
                net.minecraft.world.biome.BiomeGenBase.sky);
        net.minecraft.world.biome.BiomeGenBase.explorationBiomesList.remove(
                net.minecraft.world.biome.BiomeGenBase.frozenOcean);
        net.minecraft.world.biome.BiomeGenBase.explorationBiomesList.remove(
                net.minecraft.world.biome.BiomeGenBase.extremeHillsEdge);
        net.minecraft.world.biome.BiomeGenBase.temperatureNoise =
                new NoiseGeneratorPerlin(new Random(1234L), 1);
        net.minecraft.world.biome.BiomeGenBase.GRASS_COLOR_NOISE =
                new NoiseGeneratorPerlin(new Random(2345L), 1);
        net.minecraft.world.biome.BiomeGenBase.DOUBLE_PLANT_GENERATOR = new WorldGenDoublePlant();
    }

    protected BiomeGenBase setEnableSnow() {
        this.enableSnow = true;
        return this;
    }

    protected BiomeGenBase func_150557_a(int colorValue, boolean flag) {
        this.color = colorValue;
        if (flag) {
            this.field_150609_ah = (colorValue & 16711422) >> 1;
        } else {
            this.field_150609_ah = colorValue;
        }

        return this;
    }

    public boolean canRain() {
        return this.isSnowyBiome() ? false : this.enableRain;
    }

    public int getGrassColorAtPos(BlockPos blockPos) {
        double d0 = MathHelper.clamp(this.getFloatTemperature(blockPos), 0.0F, 1.0F);
        double d1 = MathHelper.clamp(this.getFloatRainfall(), 0.0F, 1.0F);
        return ColorizerGrass.getGrassColor(d0, d1);
    }

    public static BiomeGenBase[] getBiomeGenArray() {
        return net.minecraft.world.biome.BiomeGenBase.biomeList;
    }

    protected final BiomeGenBase setHeight(BiomeGenBaseHeight biomeGenBaseHeight) {
        this.minHeight = biomeGenBaseHeight.rootHeight;
        this.maxHeight = biomeGenBaseHeight.variation;
        return this;
    }

    public Class getBiomeClass() {
        return this.getClass();
    }

    protected BiomeGenBase setFillerBlockMetadata(int fillerBlockMetadataValue) {
        this.fillerBlockMetadata = fillerBlockMetadataValue;
        return this;
    }

    public void decorate(World world, Random random, BlockPos blockPos) {
        this.theBiomeDecorator.decorate(world, random, this, blockPos);
    }

    protected BiomeGenBase createMutation() {
        return this.createMutatedBiome(this.biomeID + 128);
    }

    public BlockFlowerEnumFlowerType pickRandomFlower(Random random, BlockPos blockPos) {
        return random.nextInt(3) > 0
                ? BlockFlowerEnumFlowerType.DANDELION
                : BlockFlowerEnumFlowerType.POPPY;
    }

    public float getSpawningChance() {
        return 0.1F;
    }

    // region String decoding support

    // endregion

    public static BiomeGenBase getBiome(int i) {
        return getBiomeFromBiomeList(i, (BiomeGenBase) null);
    }

    public final float getFloatTemperature(BlockPos blockPos) {
        if (blockPos.getY() > 64) {
            float f =
                    (float)
                            (net.minecraft.world.biome.BiomeGenBase.temperatureNoise.func_151601_a(
                                            blockPos.getX() * 1.0 / 8.0,
                                            blockPos.getZ() * 1.0 / 8.0)
                                    * 4.0);
            return this.temperature - (f + blockPos.getY() - 64.0F) * 0.05F / 30.0F;
        } else {
            return this.temperature;
        }
    }

    public boolean getEnableSnow() {
        return this.isSnowyBiome();
    }

    protected BiomeGenBase setBiomeName(String text) {
        this.biomeName = text;
        return this;
    }

    public int getSkyColorByTemp(float f) {
        f /= 3.0F;
        f = MathHelper.clamp(f, -1.0F, 1.0F);
        return Color.getHSBColor(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F).getRGB();
    }

    protected BiomeGenBase setColor(int i) {
        this.func_150557_a(i, false);
        return this;
    }

    public BiomeGenBaseTempCategory getTempCategory() {
        return this.temperature < 0.2
                ? BiomeGenBaseTempCategory.COLD
                : (this.temperature < 1.0
                        ? BiomeGenBaseTempCategory.MEDIUM
                        : BiomeGenBaseTempCategory.WARM);
    }

    public static int[] WK() {
        return bKM;
    }

    public boolean isHighHumidity() {
        return this.rainfall > 0.85F;
    }

    public boolean isEqualTo(BiomeGenBase biomeGenBase) {
        return biomeGenBase == this
                ? true
                : (biomeGenBase == null
                        ? false
                        : this.getBiomeClass() == biomeGenBase.getBiomeClass());
    }
}
