package net.minecraft.stats;

import net.minecraft.stats.IStatType;

public final class StatBaseIStatType4 implements IStatType {
    public String format(int p171_0) {
        return net.minecraft.stats.StatBase.access$100().format(p171_0 * 0.1);
    }
}
