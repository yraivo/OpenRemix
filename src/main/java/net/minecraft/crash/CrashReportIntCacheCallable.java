package net.minecraft.crash;

import net.minecraft.crash.CrashReport;
import net.minecraft.world.gen.layer.IntCache;

import java.util.concurrent.Callable;

public class CrashReportIntCacheCallable implements Callable {
    CrashReport aYJ;

    public CrashReportIntCacheCallable(CrashReport aYJValue) {
        this.aYJ = aYJValue;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        return IntCache.getCacheSizes();
    }
}
