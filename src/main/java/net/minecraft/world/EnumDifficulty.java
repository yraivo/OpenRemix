package net.minecraft.world;

public enum EnumDifficulty {
    PEACEFUL("PEACEFUL", 0, 0, "options.difficulty.peaceful"),
    EASY("EASY", 1, 1, "options.difficulty.easy"),
    NORMAL("NORMAL", 2, 2, "options.difficulty.normal"),
    HARD("HARD", 3, 3, "options.difficulty.hard");
    private static EnumDifficulty[] difficultyEnums;

    private static EnumDifficulty[] ahH;

    private int difficultyId;

    private String difficultyResourceKey;

    static {
        net.minecraft.world.EnumDifficulty.difficultyEnums = new EnumDifficulty[values().length];
        ahH =
                new EnumDifficulty[] {
                    net.minecraft.world.EnumDifficulty.PEACEFUL,
                    net.minecraft.world.EnumDifficulty.EASY,
                    net.minecraft.world.EnumDifficulty.NORMAL,
                    net.minecraft.world.EnumDifficulty.HARD
                };
        for (EnumDifficulty class_dcff67a2707d4eb3ff05 : values()) {
            net.minecraft.world.EnumDifficulty.difficultyEnums[class_dcff67a2707d4eb3ff05.difficultyId] =
                    class_dcff67a2707d4eb3ff05;
        }
    }

    public static EnumDifficulty getDifficultyEnum(int i) {
        return net.minecraft.world.EnumDifficulty.difficultyEnums[
                i % net.minecraft.world.EnumDifficulty.difficultyEnums.length];
    }

    EnumDifficulty(String text, int k, int j, String textValue) {
        this.difficultyId = j;
        this.difficultyResourceKey = textValue;
    }

    // region String decoding support

    // endregion

    public String getDifficultyResourceKey() {
        return this.difficultyResourceKey;
    }

    // region String decoding support

    // endregion

    public int getDifficultyId() {
        return this.difficultyId;
    }

    // region String decoding support

    // endregion

}
