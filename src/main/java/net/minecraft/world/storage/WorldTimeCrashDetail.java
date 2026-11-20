package net.minecraft.world.storage;

import java.util.concurrent.Callable;

class WorldTimeCrashDetail implements Callable {
    WorldInfo field_85137_a;

    private static String b;

    static {
        b = "%d game time, %d day time";
    }

    WorldTimeCrashDetail(WorldInfo bNyValue) {
        this.field_85137_a = bNyValue;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        return String.format(
                b,
                WorldInfo.access$600(this.field_85137_a),
                WorldInfo.access$700(this.field_85137_a));
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
