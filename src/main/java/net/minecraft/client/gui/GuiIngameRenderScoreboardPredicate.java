package net.minecraft.client.gui;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.scoreboard.Score;

import com.google.common.base.Predicate;

public class GuiIngameRenderScoreboardPredicate implements Predicate {
    private static String[] packedTextFragments;
    GuiIngame asq;

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public GuiIngameRenderScoreboardPredicate(GuiIngame asqValue) {
        this.asq = asqValue;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.gui.GuiIngameRenderScoreboardPredicate.packedTextFragments[0] = new String("#");
    }

    // endregion

    public boolean hasVisibleScoreName(Score score) {
        return score.getPlayerName() != null
                && !score.getPlayerName().startsWith(packedTextFragments[0]);
    }

    public boolean apply(Object object) {
        return this.hasVisibleScoreName((Score) object);
    }

    // region String decoding support

    // endregion
}
