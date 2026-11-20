package net.minecraft.stats;

import net.minecraft.scoreboard.ScoreDummyCriteria;

public class ObjectiveStat extends ScoreDummyCriteria {
    private StatBase field_151459_g;

    public ObjectiveStat(StatBase bAQValue) {
        super(bAQValue.statId);
        this.field_151459_g = bAQValue;
    }
}
