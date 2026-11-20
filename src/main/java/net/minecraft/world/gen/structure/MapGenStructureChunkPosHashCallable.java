package net.minecraft.world.gen.structure;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.gen.structure.MapGenStructure;

import java.util.concurrent.Callable;

public class MapGenStructureChunkPosHashCallable implements Callable {
    int a;
    MapGenStructure bLY;
    int cz;

    public String getCrashDetail() {
        return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(this.a, this.cz));
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public MapGenStructureChunkPosHashCallable(MapGenStructure bLYValue, int i, int j) {
        this.bLY = bLYValue;
        this.a = i;
        this.cz = j;
    }
}
