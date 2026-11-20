package net.minecraft.world;

import net.minecraft.nbt.CompoundTag;

public class LockCode {
    private static String[] packedTextFragments;

    public static LockCode EMPTY_CODE;
    private String lock;

    static {
        net.minecraft.world.LockCode.packedTextFragments = new String[2];
        net.minecraft.world.LockCode.packedTextFragments[1] = new String("");
        net.minecraft.world.LockCode.EMPTY_CODE = new LockCode(packedTextFragments[1]);
    }

    public static LockCode fromNBT(CompoundTag compoundTag) {
        if (compoundTag.hasKey("Lock", 8)) {
            String text = compoundTag.getString("Lock");
            return new LockCode(text);
        } else {
            return net.minecraft.world.LockCode.EMPTY_CODE;
        }
    }

    public String getLock() {
        return this.lock;
    }

    public boolean isEmpty() {
        return this.lock == null || this.lock.isEmpty();
    }

    public void toNBT(CompoundTag compoundTag) {
        compoundTag.setString("Lock", this.lock);
    }

    public LockCode(String text) {
        this.lock = text;
    }
}
