package net.minecraft.nbt;

import java.util.concurrent.Callable;

class ActualNbtTypeCrashDetail implements Callable {
    String field_82585_a;
    CompoundTag field_82584_b;

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    ActualNbtTypeCrashDetail(CompoundTag bvDValue, String text) {
        this.field_82584_b = bvDValue;
        this.field_82585_a = text;
    }

    public String getCrashDetail() {
        return NBTBase.NBT_TYPES[
                ((NBTBase) CompoundTag.access$000(this.field_82584_b).get(this.field_82585_a))
                        .getId()];
    }
}
