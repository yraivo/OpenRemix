package net.minecraft.world.storage;

import java.util.concurrent.Callable;

class WorldStorageVersionCrashDetail implements Callable {
    WorldInfo field_85113_a;


    public String getCrashDetail() {
        WorldInfo.YP();

        String text = "Unknown?";

        try {
            switch (WorldInfo.access$900(this.field_85113_a)) {
                case 19132:
                    text = "McRegion";

                    break;

                case 19133:
                    text = "Anvil";
            }
        } catch (Throwable throwable) {
        }

        return String.format("0x%05X - %s", WorldInfo.access$900(this.field_85113_a), text);
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    WorldStorageVersionCrashDetail(WorldInfo bNyValue) {
        this.field_85113_a = bNyValue;
    }
}
