package net.minecraft.client.renderer;

import net.minecraft.crash.CrashReportCategory;

import java.util.concurrent.Callable;

class RenderParticlePositionCrashDetail implements Callable {
    double field_85101_a;
    RenderGlobal field_85098_d;
    double field_85100_c;
    double field_85099_b;

    RenderParticlePositionCrashDetail(RenderGlobal aAbValue, double d0, double d1, double d2) {
        this.field_85098_d = aAbValue;
        this.field_85101_a = d0;
        this.field_85099_b = d1;
        this.field_85100_c = d2;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        return CrashReportCategory.getCoordinateInfo(
                this.field_85101_a, this.field_85099_b, this.field_85100_c);
    }
}
