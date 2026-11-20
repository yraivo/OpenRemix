package net.minecraft.crash;

import net.minecraft.util.BlockPos;

import java.util.concurrent.Callable;

final class BlockLocationCrashDetail implements Callable {
    BlockPos field_175749_a;

    BlockLocationCrashDetail(BlockPos blockPos) {
        this.field_175749_a = blockPos;
    }

    public String getCrashDetail() {
        return CrashReportCategory.getCoordinateInfo(this.field_175749_a);
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }
}
