package net.minecraft.world.storage;

import net.minecraft.crash.CrashReportCategory;

import java.util.concurrent.Callable;

class WorldSpawnCrashDetail implements Callable {
    WorldInfo field_85135_a;

    public String getCrashDetail() {
        return CrashReportCategory.getCoordinateInfo(
                WorldInfo.access$300(this.field_85135_a),
                WorldInfo.access$400(this.field_85135_a),
                WorldInfo.access$500(this.field_85135_a));
    }

    WorldSpawnCrashDetail(WorldInfo bNyValue) {
        this.field_85135_a = bNyValue;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }
}
