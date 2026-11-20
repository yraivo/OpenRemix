package net.minecraft.crash;

import net.minecraft.util.BlockPos;

import java.util.concurrent.Callable;

final class BlockStateLocationCrashDetail implements Callable {
    BlockPos field_175752_a;

    BlockStateLocationCrashDetail(BlockPos blockPos) {
        this.field_175752_a = blockPos;
    }

    public String func_175751_a() {
        return CrashReportCategory.getCoordinateInfo(this.field_175752_a);
    }

    @Override
    public Object call() {
        return this.func_175751_a();
    }
}
