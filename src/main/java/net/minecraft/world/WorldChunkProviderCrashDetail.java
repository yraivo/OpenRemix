package net.minecraft.world;

import java.util.concurrent.Callable;

class WorldChunkProviderCrashDetail implements Callable {
    World field_151308_a;

    WorldChunkProviderCrashDetail(World world) {
        this.field_151308_a = world;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        return this.field_151308_a.chunkProvider.makeString();
    }
}
