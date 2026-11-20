package net.minecraft.crash;

import net.minecraft.block.state.BlockState;

import java.util.concurrent.Callable;

final class BlockStateCrashDetail implements Callable {
    BlockState field_175754_a;

    BlockStateCrashDetail(BlockState aYTValue) {
        this.field_175754_a = aYTValue;
    }

    @Override
    public Object call() {
        return this.func_175753_a();
    }

    public String func_175753_a() {
        return this.field_175754_a.toString();
    }
}
