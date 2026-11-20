package net.minecraft.server;

import java.util.concurrent.Callable;

class ServerProfilerCrashDetail implements Callable {
    private static String b;
    MinecraftServer field_73716_a;

    ServerProfilerCrashDetail(MinecraftServer awJValue) {
        this.field_73716_a = awJValue;
    }

    @Override
    public Object call() {
        return this.method_e0e3092a59fee58fae52();
    }

    public String method_e0e3092a59fee58fae52() {
        return this.field_73716_a.theProfiler.profilingEnabled
                ? this.field_73716_a.theProfiler.getNameOfLastSection()
                : b;
    }

    static {
        b = "N/A (disabled)";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
