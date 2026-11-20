package net.minecraft.world.storage;

import java.util.concurrent.Callable;

class WorldDimensionCrashDetail implements Callable {
    WorldInfo field_85115_a;

    public String getCrashDetail() {
        return String.valueOf(WorldInfo.access$800(this.field_85115_a));
    }

    WorldDimensionCrashDetail(WorldInfo bNyValue) {
        this.field_85115_a = bNyValue;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }
}
