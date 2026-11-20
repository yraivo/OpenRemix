package net.minecraft.world.gen.structure;

import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBaseSpawnListEntry;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesSwampHut;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class MapGenScatteredFeature extends MapGenStructure {
    private int minDistanceBetweenScatteredFeatures;
    private int maxDistanceBetweenScatteredFeatures;
    private static List biomelist;

    private List scatteredFeatureSpawnList = Lists.newArrayList();

    public List getScatteredFeatureSpawnList() {
        return this.scatteredFeatureSpawnList;
    }

    public boolean method_08c26e1ff2b44fcfa823(BlockPos blockPos) {
        StructureStart structureStart = this.w(blockPos);
        if (structureStart != null
                && structureStart instanceof MapGenScatteredFeatureStart
                && !structureStart.components.isEmpty()) {
            StructureComponent first = (StructureComponent) structureStart.components.getFirst();
            return first instanceof ComponentScatteredFeaturePiecesSwampHut;
        } else {
            return false;
        }
    }

    static {
        net.minecraft.world.gen.structure.MapGenScatteredFeature.biomelist =
                Arrays.asList(
                        BiomeGenBase.desert,
                        BiomeGenBase.desertHills,
                        BiomeGenBase.jungle,
                        BiomeGenBase.jungleHills,
                        BiomeGenBase.swampland);
    }

    public String getStructureName() {
        return "Temple";
    }

    public MapGenScatteredFeature() {
        this.maxDistanceBetweenScatteredFeatures = 32;
        this.minDistanceBetweenScatteredFeatures = 8;
        this.scatteredFeatureSpawnList.add(new BiomeGenBaseSpawnListEntry(EntityWitch.class, 1, 1, 1));
    }

    public MapGenScatteredFeature(Map map) {
        this();
        int j = StructureComponent.f();
        Iterator iterator = map.entrySet().iterator();
        int i = j;

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            if (((String) entry.getKey()).equals("distance")) {
                this.maxDistanceBetweenScatteredFeatures =
                        MathHelper.parseIntWithDefaultAndMinimum(
                                (String) entry.getValue(), this.maxDistanceBetweenScatteredFeatures, this.minDistanceBetweenScatteredFeatures + 1);
            }

            if (i != 0) {
                break;
            }
        }
    }

    public boolean canSpawnStructureAtCoords(int i, int j) {
        int l1 = StructureComponent.f();
        int l = i;
        int k = l1;
        int i1 = j;
        if (i < 0) {
            i -= this.maxDistanceBetweenScatteredFeatures - 1;
        }

        if (j < 0) {
            j -= this.maxDistanceBetweenScatteredFeatures - 1;
        }

        int j1 = i / this.maxDistanceBetweenScatteredFeatures;
        int k1 = j / this.maxDistanceBetweenScatteredFeatures;
        Random random = this.worldObj.setRandomSeed(j1, k1, 14357617);
        j1 *= this.maxDistanceBetweenScatteredFeatures;
        k1 *= this.maxDistanceBetweenScatteredFeatures;
        j1 += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
        k1 += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
        if (l == j1 && i1 == k1) {
            BiomeGenBase biomeGenBase =
                    this.worldObj
                            .getWorldChunkManager()
                            .getBiomeGenerator(new BlockPos(l * 16 + 8, 0, i1 * 16 + 8));
            if (biomeGenBase == null) {
                return false;
            }

            for (java.util.Iterator<?> __recoveryIterator171_27813 = (net.minecraft.world.gen.structure.MapGenScatteredFeature.biomelist).iterator();
                    (__recoveryIterator171_27813).hasNext(); ) {
                BiomeGenBase biomeGenBaseValue =
                        (BiomeGenBase) (__recoveryIterator171_27813).next();
                if (biomeGenBase == biomeGenBaseValue) {
                    return true;
                }

                if (k != 0) {
                    break;
                }
            }
        }

        return false;
    }

    protected StructureStart getStructureStart(int i, int j) {
        return new MapGenScatteredFeatureStart(this.worldObj, this.rand, i, j);
    }
}
