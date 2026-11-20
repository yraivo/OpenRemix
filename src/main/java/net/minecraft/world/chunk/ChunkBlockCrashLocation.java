package net.minecraft.world.chunk;

import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;

import java.util.concurrent.Callable;

class ChunkBlockCrashLocation implements Callable {
    Chunk field_177456_b;
    BlockPos field_177457_a;

    @Override
    public Object call() {
        return this.func_177455_a();
    }

    public String func_177455_a() {
        return CrashReportCategory.getCoordinateInfo(this.field_177457_a);
    }

    ChunkBlockCrashLocation(Chunk awtValue, BlockPos blockPos) {
        this.field_177456_b = awtValue;
        this.field_177457_a = blockPos;
    }
}
