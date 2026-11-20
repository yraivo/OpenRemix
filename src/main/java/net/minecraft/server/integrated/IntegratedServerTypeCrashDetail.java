package net.minecraft.server.integrated;

import java.util.concurrent.Callable;

class IntegratedServerTypeCrashDetail implements Callable {
    IntegratedServer field_76974_a;

    private static String b;

    IntegratedServerTypeCrashDetail(IntegratedServer byTValue) {
        this.field_76974_a = byTValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getCrashDetail() {
        return b;
    }

    static {
        b = "Integrated Server (map_client.txt)";
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }
}
