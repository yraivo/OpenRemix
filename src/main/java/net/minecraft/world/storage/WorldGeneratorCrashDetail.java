package net.minecraft.world.storage;

import java.util.concurrent.Callable;

class WorldGeneratorCrashDetail implements Callable {
    private static String b;

    WorldInfo field_85139_a;

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        return String.format(
                b,
                WorldInfo.access$000(this.field_85139_a).getWorldTypeID(),
                WorldInfo.access$000(this.field_85139_a).getWorldTypeName(),
                WorldInfo.access$000(this.field_85139_a).getGeneratorVersion(),
                WorldInfo.access$100(this.field_85139_a));
    }

    WorldGeneratorCrashDetail(WorldInfo bNyValue) {
        this.field_85139_a = bNyValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        b = "ID %02d - %s, ver %d. Features enabled: %b";
    }
}
