package net.minecraft.crash;

import net.minecraft.crash.CrashReport;

import java.util.concurrent.Callable;

public class CrashReportJavaVMVersionCallable implements Callable {

    CrashReport aYJ;

    public CrashReportJavaVMVersionCallable(CrashReport aYJValue) {
        this.aYJ = aYJValue;
    }


    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        return System.getProperty("java.vm.name")
                + " ("
                + System.getProperty("java.vm.info")
                + "), "
                + System.getProperty("java.vm.vendor");
    }
}
