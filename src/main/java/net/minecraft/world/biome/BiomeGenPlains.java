package net.minecraft.world.biome;

import net.minecraft.block.BlockDoublePlantEnumPlantType;
import net.minecraft.block.BlockFlowerEnumFlowerType;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BiomeGenPlains extends BiomeGenBase {
    protected boolean field_150628_aC;
    private static String bIv;

    protected BiomeGenBase createMutatedBiome(int i) {
        BiomeGenPlains biomeGenPlains = new BiomeGenPlains(i);
        biomeGenPlains.setBiomeName(bIv);
        biomeGenPlains.field_150628_aC = true;
        biomeGenPlains.setColor(9286496);
        biomeGenPlains.field_150609_ah = 14273354;
        return biomeGenPlains;
    }

    // region String decoding support

    // endregion

    protected BiomeGenPlains(int i) {
        super(i);
        this.setTemperatureRainfall(0.8F, 0.4F);
        this.setHeight(net.minecraft.world.biome.BiomeGenBase.height_LowPlains);
        this.spawnableCreatureList.add(new BiomeGenBaseSpawnListEntry(EntityHorse.class, 5, 2, 6));
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 10;
    }

    public BlockFlowerEnumFlowerType pickRandomFlower(Random random, BlockPos blockPos) {
        double d0 =
                net.minecraft.world.biome.BiomeGenBase.GRASS_COLOR_NOISE.func_151601_a(
                        blockPos.getX() / 200.0, blockPos.getZ() / 200.0);
        if (d0 < -0.8) {
            int j = random.nextInt(4);
            switch (j) {
                case 0:
                    return BlockFlowerEnumFlowerType.ORANGE_TULIP;
                case 1:
                    return BlockFlowerEnumFlowerType.RED_TULIP;
                case 2:
                    return BlockFlowerEnumFlowerType.PINK_TULIP;
                case 3:
                default:
                    return BlockFlowerEnumFlowerType.WHITE_TULIP;
            }
        } else if (random.nextInt(3) > 0) {
            int i = random.nextInt(3);
            return i == 0
                    ? BlockFlowerEnumFlowerType.POPPY
                    : (i == 1
                            ? BlockFlowerEnumFlowerType.HOUSTONIA
                            : BlockFlowerEnumFlowerType.OXEYE_DAISY);
        } else {
            return BlockFlowerEnumFlowerType.DANDELION;
        }
    }

    // region String decoding support

    // endregion

    static {
        bIv = "Sunflower Plains";
    }

    public void decorate(World world, Random random, BlockPos blockPos) {
        boolean flag;
        label34:
        {
            boolean flag1 = WorldChunkManager.d();
            double d0 =
                    net.minecraft.world.biome.BiomeGenBase.GRASS_COLOR_NOISE.func_151601_a(
                            (blockPos.getX() + 8) / 200.0, (blockPos.getZ() + 8) / 200.0);
            flag = flag1;
            if (d0 < -0.8) {
                this.theBiomeDecorator.flowersPerChunk = 15;
                this.theBiomeDecorator.grassPerChunk = 5;
                if (!flag) {
                    break label34;
                }
            }

            this.theBiomeDecorator.flowersPerChunk = 4;
            this.theBiomeDecorator.grassPerChunk = 10;
            net.minecraft.world.biome.BiomeGenBase.DOUBLE_PLANT_GENERATOR.setPlantType(
                    BlockDoublePlantEnumPlantType.GRASS);
            int i = 0;

            while (i < 7) {
                int j = random.nextInt(16) + 8;
                int k = random.nextInt(16) + 8;
                int l = random.nextInt(world.getHorizon(blockPos.add(j, 0, k)).getY() + 32);
                net.minecraft.world.biome.BiomeGenBase.DOUBLE_PLANT_GENERATOR.generate(
                        world, random, blockPos.add(j, l, k));
                i++;
                if (flag) {
                    break;
                }
            }
        }

        if (this.field_150628_aC) {
            net.minecraft.world.biome.BiomeGenBase.DOUBLE_PLANT_GENERATOR.setPlantType(
                    BlockDoublePlantEnumPlantType.SUNFLOWER);
            int i1 = 0;

            while (i1 < 10) {
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
        }

        super.decorate(world, random, blockPos);
    }
}
