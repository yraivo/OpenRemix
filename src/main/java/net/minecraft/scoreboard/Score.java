package net.minecraft.scoreboard;

import java.util.Comparator;
import java.util.List;

public class Score {
    private String scorePlayerName;
    private ScoreObjective theScoreObjective;
    private int scorePoints;

    private boolean locked;
    private boolean field_178818_g;
    private Scoreboard theScoreboard;
    public static Comparator scoreComparator;

    public void incrementScore() {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }

        this.increseScore(1);
    }

    private static IllegalStateException method_b3985d2d68c259fdadde(
            IllegalStateException illegalstateexception) {
        return illegalstateexception;
    }

    public ScoreObjective getObjective() {
        return this.theScoreObjective;
    }

    public void setScorePoints(int scorePointsValue) {
        int j = this.scorePoints;
        this.scorePoints = scorePointsValue;
        if (j != scorePointsValue || this.field_178818_g) {
            this.field_178818_g = false;
            this.getScoreScoreboard().func_96536_a(this);
        }
    }

    public Score(Scoreboard bANValue, ScoreObjective bAMValue, String text) {
        this.theScoreboard = bANValue;
        this.theScoreObjective = bAMValue;
        this.scorePlayerName = text;
        this.field_178818_g = true;
    }

    public int getScorePoints() {
        return this.scorePoints;
    }

    public void decreaseScore(int i) {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }

        this.setScorePoints(this.getScorePoints() - i);
    }

    public Scoreboard getScoreScoreboard() {
        return this.theScoreboard;
    }

    public boolean isLocked() {
        return this.locked;
    }

    static {
        net.minecraft.scoreboard.Score.scoreComparator = new ScoreComparator();
    }

    public String getPlayerName() {
        return this.scorePlayerName;
    }

    public void func_96651_a(List list) {
        this.setScorePoints(this.theScoreObjective.getCriteria().setScore(list));
    }

    public void setLocked(boolean lockedValue) {
        this.locked = lockedValue;
    }

    public void increseScore(int i) {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }

        this.setScorePoints(this.getScorePoints() + i);
    }

    // Helper used only by this class.
    public static final class ScoreComparator implements java.util.Comparator {
        public int compare(
                net.minecraft.scoreboard.Score score, net.minecraft.scoreboard.Score scoreValue) {
            return score.getScorePoints() > scoreValue.getScorePoints()
                    ? 1
                    : (score.getScorePoints() < scoreValue.getScorePoints()
                            ? -1
                            : scoreValue
                                    .getPlayerName()
                                    .compareToIgnoreCase(score.getPlayerName()));
        }

        @java.lang.Override
        public int compare(java.lang.Object p171_0, java.lang.Object p171_1) {
            return this.compare(
                    (net.minecraft.scoreboard.Score) p171_0,
                    (net.minecraft.scoreboard.Score) p171_1);
        }
    }
}
