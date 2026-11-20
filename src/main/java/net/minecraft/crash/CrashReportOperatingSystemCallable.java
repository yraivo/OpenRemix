package net.minecraft.crash;

import net.minecraft.crash.CrashReport;

import java.util.concurrent.Callable;

public class CrashReportOperatingSystemCallable implements Callable {
    CrashReport aYJ;

    public CrashReportOperatingSystemCallable(CrashReport aYJValue) {
        this.aYJ = aYJValue;
    }

    public String getCrashDetail() {
        return System.getProperty("os.name")
                + " ("
                + System.getProperty("os.arch")
                + ") version "
                + System.getProperty("os.version");
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }


}
