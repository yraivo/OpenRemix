package net.minecraft.world.biome;

import net.minecraft.block.BlockDoublePlantEnumPlantType;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;

import java.util.Random;

public class BiomeGenSavanna extends BiomeGenBase {
    private static final WorldGenSavannaTree field_150627_aC = new WorldGenSavannaTree(false);

    protected BiomeGenSavanna(int i) {
        super(i);
        this.spawnableCreatureList.add(new BiomeGenBaseSpawnListEntry(EntityHorse.class, 1, 2, 6));
        this.theBiomeDecorator.treesPerChunk = 1;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 20;
    }

    public void decorate(World world, Random random, BlockPos blockPos) {
        boolean flag1 = WorldChunkManager.method_d87039b07bd6961907f2();
        net.minecraft.world.biome.BiomeGenBase.DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlantEnumPlantType.GRASS);
        int i = 0;
        boolean flag = flag1;

        while (i < 7) {
            int j = random.nextInt(16) + 8;
            int k = random.nextInt(16) + 8;
            int l = random.nextInt(world.getHorizon(blockPos.add(j, 0, k)).getY() + 32);
            net.minecraft.world.biome.BiomeGenBase.DOUBLE_PLANT_GENERATOR.generate(
                    world, random, blockPos.add(j, l, k));
            i++;
            if (!flag) {
                break;
            }
        }

        super.decorate(world, random, blockPos);
    }

    public WorldGenAbstractTree genBigTreeChance(Random random) {
        return (net.minecraft.world.gen.feature.WorldGenAbstractTree)
                (random.nextInt(5) > 0
                        ? net.minecraft.world.biome.BiomeGenSavanna.field_150627_aC
                        : this.worldGeneratorTrees);
    }

    protected BiomeGenBase createMutatedBiome(int p171_0) {
        net.minecraft.world.biome.BiomeGenSavannaMutated biomeGenSavannaMutated =
                new net.minecraft.world.biome.BiomeGenSavannaMutated(p171_0, this);
        biomeGenSavannaMutated.temperature = (this.temperature + 1.0F) * 0.5F;
        biomeGenSavannaMutated.minHeight = this.minHeight * 0.5F + 0.3F;
        biomeGenSavannaMutated.maxHeight = this.maxHeight * 0.5F + 1.2F;
        return biomeGenSavannaMutated;
    }
}
