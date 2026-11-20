package net.minecraft.world.gen.structure;

import net.minecraft.world.gen.structure.MapGenStructure;

import java.util.concurrent.Callable;

public class MapGenStructureStructureTypeCallable implements Callable {
    MapGenStructure bLZ;

    public MapGenStructureStructureTypeCallable(MapGenStructure bLZValue) {
        this.bLZ = bLZValue;
    }

    public String getCrashDetail() {
        return this.bLZ.getClass().getCanonicalName();
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }
}
