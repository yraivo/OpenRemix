package net.minecraft.world.chunk;

import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;

import java.util.concurrent.Callable;

class ChunkLocalBlockCrashLocation implements Callable {
    int field_150822_b;
    int field_150823_c;
    int field_150824_a;
    Chunk field_150821_d;

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        return CrashReportCategory.getCoordinateInfo(
                new BlockPos(
                        this.field_150821_d.xPosition * 16 + this.field_150824_a,
                        this.field_150822_b,
                        this.field_150821_d.zPosition * 16 + this.field_150823_c));
    }

    ChunkLocalBlockCrashLocation(Chunk bIOValue, int i, int j, int k) {
        this.field_150821_d = bIOValue;
        this.field_150824_a = i;
        this.field_150822_b = j;
        this.field_150823_c = k;
    }
}
