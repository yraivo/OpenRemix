package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class MapGenStronghold extends MapGenStructure {

    private int mE;
    private ChunkCoordIntPair[] structureCoords;
    private List wE;
    private boolean ranBiomeCheck;
    private double sV;

    protected List getCoordList() {
        int l = StructureComponent.cR();
        ArrayList arrayList = Lists.newArrayList();
        ChunkCoordIntPair[] bLJValue = this.structureCoords;
        int j = bLJValue.length;
        int k = 0;
        int i = l;

        while (k < j) {
            ChunkCoordIntPair chunkCoordIntPair = bLJValue[k];
            if (chunkCoordIntPair != null) {
                arrayList.add(chunkCoordIntPair.getCenterBlock(64));
            }

            k++;
            if (i == 0) {
                break;
            }
        }

        return arrayList;
    }

    public boolean canSpawnStructureAtCoords(int i, int j) {
        int k = StructureComponent.cR();
        if (!this.ranBiomeCheck) {
            Random random = new Random();
            random.setSeed(this.worldObj.getSeed());
            double d0 = random.nextDouble() * Math.PI * 2.0;
            int l = 1;
            int i1 = 0;

            while (i1 < this.structureCoords.length) {
                double d1 = (1.25 * l + random.nextDouble()) * this.sV * l;
                int j1 = (int) Math.round(Math.cos(d0) * d1);
                int k1 = (int) Math.round(Math.sin(d0) * d1);
                BlockPos blockPos =
                        this.worldObj
                                .getWorldChunkManager()
                                .findBiomePosition(
                                        (j1 << 4) + 8, (k1 << 4) + 8, 112, this.wE, random);
                if (blockPos != null) {
                    j1 = blockPos.getX() >> 4;
                    k1 = blockPos.getZ() >> 4;
                }

                this.structureCoords[i1] = new ChunkCoordIntPair(j1, k1);
                d0 += (Math.PI * 2) * l / this.mE;
                if (i1 == this.mE) {
                    l += 2 + random.nextInt(5);
                    this.mE = this.mE + 1 + random.nextInt(2);
                }

                i1++;
                if (k == 0) {
                    break;
                }
            }

            this.ranBiomeCheck = true;
        }

        for (ChunkCoordIntPair chunkCoordIntPair : this.structureCoords) {
            if (i == chunkCoordIntPair.chunkXPos && j == chunkCoordIntPair.chunkZPos) {
                return true;
            }

            if (k == 0) {
                break;
            }
        }

        return false;
    }


    public MapGenStronghold(Map map) {
        this(map, StructureComponent.cR());
    }

    private MapGenStronghold(Map map, int initialFlowToken) {
        this();

        int j = initialFlowToken;

        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_9613 = (map.entrySet()).iterator();
                (__recoveryIterator171_9613).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_9613).next();
            label33:
            {
                if (((String) entry.getKey()).equals("distance")) {
                    this.sV =
                            MathHelper.parseDoubleWithDefaultAndMinimum(
                                    (String) entry.getValue(), this.sV, 1.0);
                    if (i != 0) {
                        break label33;
                    }
                }

                if (((String) entry.getKey()).equals("count")) {
                    this.structureCoords =
                            new ChunkCoordIntPair
                                    [MathHelper.parseIntWithDefaultAndMinimum(
                                            (String) entry.getValue(),
                                            this.structureCoords.length,
                                            1)];
                    if (i != 0) {
                        break label33;
                    }
                }

                if (((String) entry.getKey()).equals("spread")) {
                    this.mE =
                            MathHelper.parseIntWithDefaultAndMinimum(
                                    (String) entry.getValue(), this.mE, 1);
                }
            }

            if (i == 0) {
                break;
            }
        }
    }

    public MapGenStronghold() {
        int j = StructureComponent.cR();
        this.structureCoords = new ChunkCoordIntPair[3];
        int i = j;
        this.sV = 32.0;
        this.mE = 3;
        this.wE = Lists.newArrayList();

        for (BiomeGenBase biomeGenBase : BiomeGenBase.getBiomeGenArray()) {
            if (biomeGenBase != null && biomeGenBase.minHeight > 0.0F) {
                this.wE.add(biomeGenBase);
            }

            if (i == 0) {
                break;
            }
        }
    }

    protected StructureStart getStructureStart(int i, int j) {
        int l = StructureComponent.f();
        MapGenStrongholdStart mapGenStrongholdStart =
                new MapGenStrongholdStart(this.worldObj, this.rand, i, j);
        int k = l;

        while (mapGenStrongholdStart.getComponents().isEmpty()
                || ((StructureStrongholdPiecesStairs2) mapGenStrongholdStart.getComponents().get(0))
                                .strongholdPortalRoom
                        == null) {
            mapGenStrongholdStart = new MapGenStrongholdStart(this.worldObj, this.rand, i, j);
            if (k != 0) {
                break;
            }
        }

        return mapGenStrongholdStart;
    }

    public String getStructureName() {
        return "Stronghold";
    }
}
