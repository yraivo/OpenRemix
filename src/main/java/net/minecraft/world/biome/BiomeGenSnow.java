package net.minecraft.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenIcePath;
import net.minecraft.world.gen.feature.WorldGenIceSpike;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

import java.util.Random;

public class BiomeGenSnow extends BiomeGenBase {

    private WorldGenIcePath field_150617_aE;
    private boolean field_150615_aC;
    private static String bIv;
    private WorldGenIceSpike field_150616_aD = new WorldGenIceSpike();

    // region String decoding support

    // endregion

    protected BiomeGenBase createMutatedBiome(int i) {
        BiomeGenBase biomeGenBase =
                new BiomeGenSnow(i, true)
                        .func_150557_a(13828095, true)
                        .setBiomeName(this.biomeName + bIv)
                        .setEnableSnow()
                        .setTemperatureRainfall(0.0F, 0.5F)
                        .setHeight(
                                new BiomeGenBaseHeight(
                                        this.minHeight + 0.1F, this.maxHeight + 0.1F));
        biomeGenBase.minHeight = this.minHeight + 0.3F;
        biomeGenBase.maxHeight = this.maxHeight + 0.4F;
        return biomeGenBase;
    }

    static {
        bIv = " Spikes";
    }

    public void decorate(World world, Random random, BlockPos blockPos) {
        boolean flag = WorldChunkManager.method_d87039b07bd6961907f2();
        if (this.field_150615_aC) {
            int i = 0;

            while (i < 3) {
                int j = random.nextInt(16) + 8;
                int k = random.nextInt(16) + 8;
                this.field_150616_aD.generate(
                        world, random, world.getHorizon(blockPos.add(j, 0, k)));
                i++;
                if (!flag) {
                    break;
                }
            }

            i = 0;

            while (i < 2) {
                int l = random.nextInt(16) + 8;
                int i1 = random.nextInt(16) + 8;
                this.field_150617_aE.generate(
                        world, random, world.getHorizon(blockPos.add(l, 0, i1)));
                i++;
                if (!flag) {
                    break;
                }
            }
        }

        super.decorate(world, random, blockPos);
    }

    public WorldGenAbstractTree genBigTreeChance(Random random) {
        return new WorldGenTaiga2(false);
    }

    public BiomeGenSnow(int i, boolean amEValue) {
        super(i);
        this.field_150617_aE = new WorldGenIcePath(4);
        this.field_150615_aC = amEValue;
        if (amEValue) {
            this.topBlock = Blocks.snow.getDefaultState();
        }

        this.spawnableCreatureList.clear();
    }

    // region String decoding support

    // endregion
}
