package net.minecraft.crash;

import net.minecraft.block.Block;

import java.util.concurrent.Callable;

final class BlockTypeCrashDetail implements Callable {

    Block field_147151_b;
    int field_85080_a;


    BlockTypeCrashDetail(int i, Block block) {
        this.field_85080_a = i;
        this.field_147151_b = block;
    }

    public String getCrashDetail() {
        try {
            return String.format(
                    "ID #%d (%s // %s)",
                    this.field_85080_a,
                    this.field_147151_b.getUnlocalizedName(),
                    this.field_147151_b.getClass().getCanonicalName());
        } catch (Throwable throwable) {
            return "ID #" + this.field_85080_a;
        }
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }
}
