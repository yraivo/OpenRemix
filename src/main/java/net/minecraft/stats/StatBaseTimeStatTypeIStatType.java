package net.minecraft.stats;

import net.minecraft.entity.Entity;
import net.minecraft.stats.IStatType;
import net.minecraft.stats.StatBase;

public final class StatBaseTimeStatTypeIStatType implements IStatType {


    public String format(int i) {
        Entity[] entityArray = StatBase.GU();
        double d0 = i / 20.0;
        double d1 = d0 / 60.0;
        Entity[] entityArrayValue = entityArray;
        double d2 = d1 / 60.0;
        double d3 = d2 / 24.0;
        double d4 = d3 / 365.0;
        String text =
                d4 > 0.5
                        ? StatBase.access$100().format(d4) + " y"
                        : (d3 > 0.5
                                ? StatBase.access$100().format(d3) + " d"
                                : (d2 > 0.5
                                        ? StatBase.access$100().format(d2) + " h"
                                        : (d1 > 0.5
                                                ? StatBase.access$100().format(d1) + " m"
                                                : d0 + " s")));
        if (entityArrayValue != null) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }

        return text;
    }
}
