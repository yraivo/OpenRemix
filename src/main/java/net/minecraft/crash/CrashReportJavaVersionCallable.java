package net.minecraft.crash;

import net.minecraft.crash.CrashReport;

import java.util.concurrent.Callable;

public class CrashReportJavaVersionCallable implements Callable {
    CrashReport aYJ;

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public CrashReportJavaVersionCallable(CrashReport aYJValue) {
        this.aYJ = aYJValue;
    }

    public String getCrashDetail() {
        return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
    }


}
