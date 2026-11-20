package net.minecraft.nbt;

import java.util.concurrent.Callable;

class ExpectedNbtTypeCrashDetail implements Callable {
    int field_82588_a;
    CompoundTag field_82587_b;

    ExpectedNbtTypeCrashDetail(CompoundTag bvDValue, int i) {
        this.field_82587_b = bvDValue;
        this.field_82588_a = i;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        return NBTBase.NBT_TYPES[this.field_82588_a];
    }
}
