package net.minecraft.world.gen.structure;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.biome.BiomeGenBaseSpawnListEntry;

import com.google.common.collect.Lists;

import java.util.List;

public class MapGenNetherBridge extends MapGenStructure {

    private List spawnList = Lists.newArrayList();
    private static String te;

    public boolean canSpawnStructureAtCoords(int i, int j) {
        int k = i >> 4;
        int l = j >> 4;
        this.rand.setSeed(k ^ l << 4 ^ this.worldObj.getSeed());
        this.rand.nextInt();
        return this.rand.nextInt(3) != 0
                ? false
                : (i != (k << 4) + 4 + this.rand.nextInt(8)
                        ? false
                        : j == (l << 4) + 4 + this.rand.nextInt(8));
    }

    protected StructureStart getStructureStart(int i, int j) {
        return new MapGenNetherBridgeStart(this.worldObj, this.rand, i, j);
    }

    // region String decoding support

    // endregion

    static {
        te = "Fortress";
    }

    public MapGenNetherBridge() {
        this.spawnList.add(new BiomeGenBaseSpawnListEntry(EntityBlaze.class, 10, 2, 3));
        this.spawnList.add(new BiomeGenBaseSpawnListEntry(EntityPigZombie.class, 5, 4, 4));
        this.spawnList.add(new BiomeGenBaseSpawnListEntry(EntitySkeleton.class, 10, 4, 4));
        this.spawnList.add(new BiomeGenBaseSpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
    }

    public String getStructureName() {
        return te;
    }

    public List getSpawnList() {
        return this.spawnList;
    }

    // region String decoding support

    // endregion
}
