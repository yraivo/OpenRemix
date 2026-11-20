package net.minecraft.world.biome;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;

public class BiomeGenHell extends BiomeGenBase {
    public BiomeGenHell(int i) {
        super(i);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new BiomeGenBaseSpawnListEntry(EntityGhast.class, 50, 4, 4));
        this.spawnableMonsterList.add(
                new BiomeGenBaseSpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(
                new BiomeGenBaseSpawnListEntry(EntityMagmaCube.class, 1, 4, 4));
    }
}
