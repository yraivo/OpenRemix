package net.minecraft.scoreboard;

import com.google.common.collect.Maps;

import java.util.Map;

public enum IScoreObjectiveCriteriaEnumRenderType {
    INTEGER("INTEGER", 0, "integer"),
    HEARTS("HEARTS", 1, "hearts");

    private static IScoreObjectiveCriteriaEnumRenderType[] acI;

    private String field_178798_d;
    private static Map field_178801_c;

    // region String decoding support

    // endregion

    public static IScoreObjectiveCriteriaEnumRenderType func_178795_a(String text) {
        IScoreObjectiveCriteriaEnumRenderType iScoreObjectiveCriteriaEnumRenderType =
                (IScoreObjectiveCriteriaEnumRenderType)
                        net.minecraft.scoreboard.IScoreObjectiveCriteriaEnumRenderType.field_178801_c.get(text);
        return iScoreObjectiveCriteriaEnumRenderType == null
                ? net.minecraft.scoreboard.IScoreObjectiveCriteriaEnumRenderType.INTEGER
                : iScoreObjectiveCriteriaEnumRenderType;
    }

    public String func_178796_a() {
        return this.field_178798_d;
    }

    // region String decoding support

    // endregion

    static {
        acI =
                new IScoreObjectiveCriteriaEnumRenderType[] {
                    net.minecraft.scoreboard.IScoreObjectiveCriteriaEnumRenderType.INTEGER,
                    net.minecraft.scoreboard.IScoreObjectiveCriteriaEnumRenderType.HEARTS
                };
        net.minecraft.scoreboard.IScoreObjectiveCriteriaEnumRenderType.field_178801_c = Maps.newHashMap();
        for (IScoreObjectiveCriteriaEnumRenderType class_f4b1aeaca5f9c945dce0 : values()) {
            net.minecraft.scoreboard.IScoreObjectiveCriteriaEnumRenderType.field_178801_c.put(
                    class_f4b1aeaca5f9c945dce0.func_178796_a(), class_f4b1aeaca5f9c945dce0);
        }
    }

    IScoreObjectiveCriteriaEnumRenderType(String text, int j, String textValue) {
        this.field_178798_d = textValue;
    }

    // region String decoding support

    // endregion

}
