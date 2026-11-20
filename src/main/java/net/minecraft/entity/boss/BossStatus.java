package net.minecraft.entity.boss;

public final class BossStatus {
    public static boolean hasColorModifier;
    public static int statusBarTime;
    public static String bossName;
    private static String e;
    public static float healthScale;
    private static String[] packedTextFragments;

    public static void setBossStatus(
            IBossDisplayData iBossDisplayData, boolean hasColorModifierValue) {
        net.minecraft.entity.boss.BossStatus.healthScale =
                iBossDisplayData.getHealth() / iBossDisplayData.getMaxHealth();
        net.minecraft.entity.boss.BossStatus.statusBarTime = 100;
        net.minecraft.entity.boss.BossStatus.bossName = iBossDisplayData.getDisplayName().getFormattedText();
        net.minecraft.entity.boss.BossStatus.hasColorModifier = hasColorModifierValue;
    }

    public static String method_c60693f457aa70be7160() {
        return e;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.entity.boss.BossStatus.packedTextFragments[0] = new String("xUQ5S");
    }

    // endregion

    public static void b(String text) {
        e = text;
    }

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (method_c60693f457aa70be7160() != null) {
            b(packedTextFragments[0]);
        }
    }
}
