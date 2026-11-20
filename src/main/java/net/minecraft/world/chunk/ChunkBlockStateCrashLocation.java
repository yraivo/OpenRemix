package net.minecraft.world.chunk;

import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;

import java.util.concurrent.Callable;

class ChunkBlockStateCrashLocation implements Callable {
    Chunk field_177449_b;
    BlockPos field_177450_a;

    public String func_177448_a() {
        return CrashReportCategory.getCoordinateInfo(this.field_177450_a);
    }

    @Override
    public Object call() {
        return this.func_177448_a();
    }

    ChunkBlockStateCrashLocation(Chunk awtValue, BlockPos blockPos) {
        this.field_177449_b = awtValue;
        this.field_177450_a = blockPos;
    }
}
