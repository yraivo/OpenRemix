package net.minecraft.world.gen.structure;

import net.minecraft.util.MathHelper;

import java.util.Map;
import java.util.Map.Entry;

public class MapGenMineshaft extends MapGenStructure {
    private double iw = 0.004;

    public MapGenMineshaft() {}

    public MapGenMineshaft(Map map) {
        this(map, StructureComponent.f());
    }

    private MapGenMineshaft(Map map, int initialFlowToken) {
        super();

        int j = initialFlowToken;

        this.iw = 0.004;
        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_1298 = (map.entrySet()).iterator();
                (__recoveryIterator171_1298).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_1298).next();
            if (((String) entry.getKey()).equals("chance")) {
                this.iw = MathHelper.parseDoubleWithDefault((String) entry.getValue(), this.iw);
            }

            if (i != 0) {
                break;
            }
        }
    }

    public boolean canSpawnStructureAtCoords(int i, int j) {
        return this.rand.nextDouble() < this.iw
                && this.rand.nextInt(80) < Math.max(Math.abs(i), Math.abs(j));
    }

    public String getStructureName() {
        return "Mineshaft";
    }

    protected StructureStart getStructureStart(int i, int j) {
        return new StructureMineshaftStart(this.worldObj, this.rand, i, j);
    }


}
