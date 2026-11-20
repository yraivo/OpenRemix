package net.minecraft.world;

import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;

import java.util.concurrent.Callable;

class WorldBiomeRequestCrashLocation implements Callable {
    BlockPos field_180253_a;
    World field_77485_a;

    WorldBiomeRequestCrashLocation(World awfValue, BlockPos blockPos) {
        this.field_77485_a = awfValue;
        this.field_180253_a = blockPos;
    }

    public String getCrashDetail() {
        return CrashReportCategory.getCoordinateInfo(this.field_180253_a);
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }
}
