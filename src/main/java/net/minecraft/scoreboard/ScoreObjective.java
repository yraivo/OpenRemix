package net.minecraft.scoreboard;

public class ScoreObjective {
    private String displayName;
    private IScoreObjectiveCriteria objectiveCriteria;
    private IScoreObjectiveCriteriaEnumRenderType renderType;
    private String name;
    private Scoreboard theScoreboard;

    public Scoreboard getScoreboard() {
        return this.theScoreboard;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public ScoreObjective(Scoreboard bymValue, String text, IScoreObjectiveCriteria bAGValue) {
        this.theScoreboard = bymValue;
        this.name = text;
        this.objectiveCriteria = bAGValue;
        this.displayName = text;
        this.renderType = bAGValue.getRenderType();
    }

    public String getName() {
        return this.name;
    }

    public IScoreObjectiveCriteriaEnumRenderType getRenderType() {
        return this.renderType;
    }

    public void setRenderType(IScoreObjectiveCriteriaEnumRenderType bARValue) {
        this.renderType = bARValue;
        this.theScoreboard.func_96532_b(this);
    }

    public IScoreObjectiveCriteria getCriteria() {
        return this.objectiveCriteria;
    }

    public void setDisplayName(String text) {
        this.displayName = text;
        this.theScoreboard.func_96532_b(this);
    }
}
