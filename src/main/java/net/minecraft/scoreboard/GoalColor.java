package net.minecraft.scoreboard;

import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class GoalColor implements IScoreObjectiveCriteria {
    private String criterionName;

    public String getName() {
        return this.criterionName;
    }

    public boolean isReadOnly() {
        return false;
    }

    public int setScore(List list) {
        return 0;
    }

    public IScoreObjectiveCriteriaEnumRenderType getRenderType() {
        return IScoreObjectiveCriteriaEnumRenderType.INTEGER;
    }

    public GoalColor(String text, EnumChatFormatting enumChatFormatting) {
        this.criterionName = text + enumChatFormatting.getFriendlyName();
        IScoreObjectiveCriteria.INSTANCES.put(this.criterionName, this);
    }
}
