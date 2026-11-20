package net.minecraft.crash;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;

import java.util.concurrent.Callable;

public class MemoryUsageCrashDetail implements Callable {
    CrashReport aYJ;

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }


    public String getCrashDetail() {
        Runtime runtime = Runtime.getRuntime();
        CrashReportCategory.b();
        long i = runtime.maxMemory();
        long j = runtime.totalMemory();
        long k = runtime.freeMemory();

        long k1 = i / 1024L;

        long l = k1 / 1024L;

        k1 = j / 1024L;

        long i1 = k1 / 1024L;

        k1 = k / 1024L;

        long j1 = k1 / 1024L;
        String text =
                k
                        + " bytes ("
                        + j1
                        + " MB) / "
                        + j
                        + " bytes ("
                        + i1
                        + " MB) up to "
                        + i
                        + " bytes ("
                        + l
                        + " MB)";
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            CrashReportCategory.b(new String[4]);
        }

        return text;
    }

    public MemoryUsageCrashDetail(CrashReport aYJValue) {
        this.aYJ = aYJValue;
    }
}
