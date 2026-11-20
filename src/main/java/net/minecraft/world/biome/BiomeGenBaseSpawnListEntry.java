package net.minecraft.world.biome;

import net.minecraft.util.WeightedRandomItem;

public class BiomeGenBaseSpawnListEntry extends WeightedRandomItem {

    public Class entityClass;
    public int minGroupCount;
    public int maxGroupCount;
    private static String[] packedTextFragments;

    public String toString() {
        return this.entityClass.getSimpleName()
                + "*("
                + this.minGroupCount
                + packedTextFragments[0]
                + this.maxGroupCount
                + "):"
                + this.itemWeight;
    }

    static {
        net.minecraft.world.biome.BiomeGenBaseSpawnListEntry.packedTextFragments = new String[2];
        net.minecraft.world.biome.BiomeGenBaseSpawnListEntry.packedTextFragments[0] = new String("-");
    }

    public BiomeGenBaseSpawnListEntry(Class oclass, int i, int j, int k) {
        super(i);
        this.entityClass = oclass;
        this.minGroupCount = j;
        this.maxGroupCount = k;
    }
}
