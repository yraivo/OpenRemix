package net.minecraft.stats;

import net.minecraft.stats.IStatType;
import net.minecraft.stats.StatBase;

public final class StatBaseDistanceStatTypeIStatType implements IStatType {

    public String format(int i) {
        double d0 = i / 100.0;
        double d1 = d0 / 1000.0;
        return d1 > 0.5
                ? StatBase.access$100().format(d1) + " km"
                : (d0 > 0.5 ? StatBase.access$100().format(d0) + " m" : i + " cm");
    }


}
