package net.minecraft.world.gen.structure;

import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class MapGenVillage extends MapGenStructure {
    private int tB = 32;
    private int mF = 8;

    public static List villageSpawnBiomes;

    private int terrainType;

    public boolean canSpawnStructureAtCoords(int i, int j) {
        int k = i;
        int l = j;
        if (i < 0) {
            i -= this.tB - 1;
        }

        if (j < 0) {
            j -= this.tB - 1;
        }

        int i1 = i / this.tB;
        int j1 = j / this.tB;
        Random random = this.worldObj.setRandomSeed(i1, j1, 10387312);
        i1 *= this.tB;
        j1 *= this.tB;
        i1 += random.nextInt(this.tB - this.mF);
        j1 += random.nextInt(this.tB - this.mF);
        if (k == i1 && l == j1) {
            boolean flag =
                    this.worldObj
                            .getWorldChunkManager()
                            .areBiomesViable(k * 16 + 8, l * 16 + 8, 0, net.minecraft.world.gen.structure.MapGenVillage.villageSpawnBiomes);
            if (flag) {
                return true;
            }
        }

        return false;
    }

    public String getStructureName() {
        return "Village";
    }

    public MapGenVillage(Map map) {
        this();
        int j = StructureComponent.f();
        Iterator iterator = map.entrySet().iterator();
        int i = j;

        while (iterator.hasNext()) {
            {
                Entry entry = (Entry) iterator.next();
                if (((String) entry.getKey()).equals("size")) {
                    this.terrainType =
                            MathHelper.parseIntWithDefaultAndMinimum(
                                    (String) entry.getValue(), this.terrainType, 0);
                    if (!(i == 0)) {

                        if (((String) entry.getKey()).equals("distance")) {
                            this.tB =
                                    MathHelper.parseIntWithDefaultAndMinimum(
                                            (String) entry.getValue(), this.tB, this.mF + 1);
                        }
                    }
                } else {

                    if (((String) entry.getKey()).equals("distance")) {
                        this.tB =
                                MathHelper.parseIntWithDefaultAndMinimum(
                                        (String) entry.getValue(), this.tB, this.mF + 1);
                    }
                }
            }

            if (i != 0) {
                break;
            }
        }
    }

    static {
        net.minecraft.world.gen.structure.MapGenVillage.villageSpawnBiomes = Arrays.asList(BiomeGenBase.plains, BiomeGenBase.desert, BiomeGenBase.savanna);
    }

    protected StructureStart getStructureStart(int i, int j) {
        return new MapGenVillageStart(this.worldObj, this.rand, i, j, this.terrainType);
    }

    public MapGenVillage() {}
}
