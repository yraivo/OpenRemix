package net.minecraft.world.storage;

import java.util.concurrent.Callable;

class WorldWeatherCrashDetail implements Callable {
    WorldInfo field_85111_a;

    private static String b;

    // region String decoding support

    // endregion

    WorldWeatherCrashDetail(WorldInfo bNyValue) {
        this.field_85111_a = bNyValue;
    }

    // region String decoding support

    // endregion

    static {
        b = "Rain time: %d (now: %b), thunder time: %d (now: %b)";
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        return String.format(
                b,
                WorldInfo.access$1000(this.field_85111_a),
                WorldInfo.access$1100(this.field_85111_a),
                WorldInfo.access$1200(this.field_85111_a),
                WorldInfo.access$1300(this.field_85111_a));
    }
}
