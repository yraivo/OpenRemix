package net.minecraft.world.gen.structure;

import net.minecraft.world.gen.structure.MapGenStructure;

import java.util.concurrent.Callable;

public class MapGenStructureIsFeatureChunkCallable implements Callable {
    int a;
    MapGenStructure bLY;

    int cz;


    public MapGenStructureIsFeatureChunkCallable(MapGenStructure bLYValue, int i, int j) {
        this.bLY = bLYValue;
        this.a = i;
        this.cz = j;
    }

    public String getCrashDetail() {
        return this.bLY.canSpawnStructureAtCoords(this.a, this.cz) ? "True" : "False";
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }
}
