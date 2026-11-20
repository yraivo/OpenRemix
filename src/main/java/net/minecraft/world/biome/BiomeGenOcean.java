package net.minecraft.world.biome;

import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeGenOcean extends BiomeGenBase {
    public void genTerrainBlocks(
            World world,
            Random random,
            ChunkPrimer chunkPrimer,
            int p171_3,
            int p171_4,
            double p171_5) {
        super.genTerrainBlocks(world, random, chunkPrimer, p171_3, p171_4, p171_5);
    }

    public BiomeGenOcean(int p171_0) {
        super(p171_0);
        this.spawnableCreatureList.clear();
    }

    public BiomeGenBaseTempCategory getTempCategory() {
        return BiomeGenBaseTempCategory.OCEAN;
    }
}
