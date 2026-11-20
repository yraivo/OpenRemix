package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;

import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBaseSpawnListEntry;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class StructureOceanMonument extends MapGenStructure {
    private static List sH;

    private int mu;

    public static List tX;
    private int sR = 32;

    public String getStructureName() {
        return "Monument";
    }

    public StructureOceanMonument() {
        this.mu = 5;
    }

    static {
        tX =
                Arrays.asList(
                        BiomeGenBase.ocean,
                        BiomeGenBase.deepOcean,
                        BiomeGenBase.river,
                        BiomeGenBase.frozenOcean,
                        BiomeGenBase.frozenRiver);
        sH = Lists.newArrayList();
        sH.add(new BiomeGenBaseSpawnListEntry(EntityGuardian.class, 1, 2, 4));
    }

    public boolean canSpawnStructureAtCoords(int i, int j) {
        int k = i;
        int l = j;
        if (i < 0) {
            i -= this.sR - 1;
        }

        if (j < 0) {
            j -= this.sR - 1;
        }

        int i1 = i / this.sR;
        int j1 = j / this.sR;
        Random random = this.worldObj.setRandomSeed(i1, j1, 10387313);
        i1 *= this.sR;
        j1 *= this.sR;
        i1 += (random.nextInt(this.sR - this.mu) + random.nextInt(this.sR - this.mu)) / 2;
        j1 += (random.nextInt(this.sR - this.mu) + random.nextInt(this.sR - this.mu)) / 2;
        if (k == i1 && l == j1) {
            if (this.worldObj
                            .getWorldChunkManager()
                            .getBiomeGenerator(
                                    new BlockPos(k * 16 + 8, 64, l * 16 + 8), (BiomeGenBase) null)
                    != BiomeGenBase.deepOcean) {
                return false;
            }

            boolean flag =
                    this.worldObj
                            .getWorldChunkManager()
                            .areBiomesViable(k * 16 + 8, l * 16 + 8, 29, tX);
            if (flag) {
                return true;
            }
        }

        return false;
    }

    protected StructureStart getStructureStart(int i, int j) {
        return new StructureOceanMonumentStartMonument(this.worldObj, this.rand, i, j);
    }

    public List i() {
        return sH;
    }

    public StructureOceanMonument(Map map) {
        this(map, StructureComponent.f());
    }

    private StructureOceanMonument(Map map, int initialFlowToken) {
        this();

        int j = initialFlowToken;

        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_27618 = (map.entrySet()).iterator();
                (__recoveryIterator171_27618).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_27618).next();
            {
                if (((String) entry.getKey()).equals("spacing")) {
                    this.sR =
                            MathHelper.parseIntWithDefaultAndMinimum(
                                    (String) entry.getValue(), this.sR, 1);
                    if (!(i == 0)) {

                        if (((String) entry.getKey()).equals("separation")) {
                            this.mu =
                                    MathHelper.parseIntWithDefaultAndMinimum(
                                            (String) entry.getValue(), this.mu, 1);
                        }
                    }
                } else {

                    if (((String) entry.getKey()).equals("separation")) {
                        this.mu =
                                MathHelper.parseIntWithDefaultAndMinimum(
                                        (String) entry.getValue(), this.mu, 1);
                    }
                }
            }

            if (i != 0) {
                break;
            }
        }
    }
}
