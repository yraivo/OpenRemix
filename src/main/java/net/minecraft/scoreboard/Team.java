package net.minecraft.scoreboard;

import java.util.Collection;

public abstract class Team {
    private static String[] packedTextFragments;
    private static String b;

    public abstract boolean getSeeFriendlyInvisiblesEnabled();

    public abstract boolean getAllowFriendlyFire();

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (cI() == null) {
            b(packedTextFragments[0]);
        }
    }

    public static void b(String text) {
        b = text;
    }

    public boolean isSameTeam(Team team) {
        return this == team;
    }

    public abstract TeamEnumVisible getNameTagVisibility();

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.scoreboard.Team.packedTextFragments[0] = new String("ktuDtb");
    }

    // endregion

    public abstract String formatString(String text);

    public abstract TeamEnumVisible getDeathMessageVisibility();

    // region String decoding support

    // endregion

    public static String cI() {
        return b;
    }

    public abstract String getRegisteredName();

    public abstract Collection getMembershipCollection();
}
