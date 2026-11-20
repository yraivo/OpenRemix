package net.minecraft.world.storage;

import java.util.concurrent.Callable;

class WorldSeedCrashDetail implements Callable {
    WorldInfo field_85143_a;

    public String getCrashDetail() {
        return String.valueOf(this.field_85143_a.getSeed());
    }

    WorldSeedCrashDetail(WorldInfo bNyValue) {
        this.field_85143_a = bNyValue;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }
}
