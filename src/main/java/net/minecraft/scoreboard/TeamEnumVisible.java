package net.minecraft.scoreboard;

import com.google.common.collect.Maps;

import java.util.Map;

public enum TeamEnumVisible {
    ALWAYS("ALWAYS", 0, "always", 0),
    NEVER("NEVER", 1, "never", 1),
    HIDE_FOR_OTHER_TEAMS("HIDE_FOR_OTHER_TEAMS", 2, "hideForOtherTeams", 2),
    HIDE_FOR_OWN_TEAM("HIDE_FOR_OWN_TEAM", 3, "hideForOwnTeam", 3);

    private static TeamEnumVisible[] acN;

    public String protocolKey;
    private static Map field_178828_g;
    public int field_178827_f;

    TeamEnumVisible(String text, int k, String acTValue, int j) {
        this.protocolKey = acTValue;
        this.field_178827_f = j;
    }

    public static TeamEnumVisible fromProtocolKey(String text) {
        return (TeamEnumVisible) net.minecraft.scoreboard.TeamEnumVisible.field_178828_g.get(text);
    }

    public static String[] y() {
        return (String[])
                net.minecraft.scoreboard.TeamEnumVisible.field_178828_g
                        .keySet()
                        .toArray(new String[net.minecraft.scoreboard.TeamEnumVisible.field_178828_g.size()]);
    }

    // region String decoding support

    // endregion

    static {
        acN =
                new TeamEnumVisible[] {
                    net.minecraft.scoreboard.TeamEnumVisible.ALWAYS,
                    net.minecraft.scoreboard.TeamEnumVisible.NEVER,
                    net.minecraft.scoreboard.TeamEnumVisible.HIDE_FOR_OTHER_TEAMS,
                    net.minecraft.scoreboard.TeamEnumVisible.HIDE_FOR_OWN_TEAM
                };
        net.minecraft.scoreboard.TeamEnumVisible.field_178828_g = Maps.newHashMap();
        for (TeamEnumVisible class_338d03c5e644cf97b902 : values()) {
            net.minecraft.scoreboard.TeamEnumVisible.field_178828_g.put(
                    class_338d03c5e644cf97b902.protocolKey, class_338d03c5e644cf97b902);
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
