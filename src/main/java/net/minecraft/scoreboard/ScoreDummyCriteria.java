package net.minecraft.scoreboard;

import java.util.List;

public class ScoreDummyCriteria implements IScoreObjectiveCriteria {
    private String criterionName;

    public boolean isReadOnly() {
        return false;
    }

    public String getName() {
        return this.criterionName;
    }

    public ScoreDummyCriteria(String criterionNameValue) {
        this.criterionName = criterionNameValue;
        IScoreObjectiveCriteria.INSTANCES.put(criterionNameValue, this);
    }

    public IScoreObjectiveCriteriaEnumRenderType getRenderType() {
        return IScoreObjectiveCriteriaEnumRenderType.INTEGER;
    }

    public int setScore(List list) {
        return 0;
    }
}
