package net.minecraft.world.storage;

import java.util.concurrent.Callable;

class WorldGameModeCrashDetail implements Callable {
    WorldInfo field_85109_a;
    private static String b;

    // region String decoding support

    // endregion

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    WorldGameModeCrashDetail(WorldInfo bNyValue) {
        this.field_85109_a = bNyValue;
    }

    static {
        b = "Game mode: %s (ID %d). Hardcore: %b. Cheats: %b";
    }

    // region String decoding support

    // endregion

    public String getCrashDetail() {
        return String.format(
                b,
                WorldInfo.access$1400(this.field_85109_a).getName(),
                WorldInfo.access$1400(this.field_85109_a).getID(),
                WorldInfo.access$1500(this.field_85109_a),
                WorldInfo.access$1600(this.field_85109_a));
    }
}
