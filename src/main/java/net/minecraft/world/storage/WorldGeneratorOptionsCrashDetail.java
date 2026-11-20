package net.minecraft.world.storage;

import java.util.concurrent.Callable;

class WorldGeneratorOptionsCrashDetail implements Callable {
    WorldInfo field_85141_a;

    public String getCrashDetail() {
        return WorldInfo.access$200(this.field_85141_a);
    }

    WorldGeneratorOptionsCrashDetail(WorldInfo bNyValue) {
        this.field_85141_a = bNyValue;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }
}
