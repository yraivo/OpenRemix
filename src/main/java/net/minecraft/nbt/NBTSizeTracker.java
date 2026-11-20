package net.minecraft.nbt;

import net.minecraft.nbt.NBTSizeTrackerInfiniteNBTSizeTracker;

public class NBTSizeTracker {

    private long read;
    private long max;

    public static NBTSizeTracker INFINITE;

    private static RuntimeException method_1a1f08aee5fe72868e61(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    public void read(long i) {
        try {

            this.read += i / 8L;
            if (this.read > this.max) {
                throw new RuntimeException(
                        "Tried to read NBT tag that was too big; tried to allocate: "
                                + this.read
                                + "bytes where max allowed: "
                                + this.max);
            }
        } catch (RuntimeException runtimeexception) {
            throw method_1a1f08aee5fe72868e61(runtimeexception);
        }
    }

    static {
        net.minecraft.nbt.NBTSizeTracker.INFINITE = new NBTSizeTrackerInfiniteNBTSizeTracker(0L);
    }

    public NBTSizeTracker(long maxValue) {
        this.max = maxValue;
    }
}
