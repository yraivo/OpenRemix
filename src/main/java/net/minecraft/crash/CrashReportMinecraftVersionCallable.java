package net.minecraft.crash;

import net.minecraft.crash.CrashReport;

import java.util.concurrent.Callable;

public class CrashReportMinecraftVersionCallable implements Callable {
    private static String b;

    CrashReport aYJ;

    public String getCrashDetail() {
        return b;
    }

    public CrashReportMinecraftVersionCallable(CrashReport aYJValue) {
        this.aYJ = aYJValue;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    static {
        b = "1.8";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
