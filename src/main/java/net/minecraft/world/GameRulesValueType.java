package net.minecraft.world;

public enum GameRulesValueType {
    ANY_VALUE("ANY_VALUE", 0, "ANY_VALUE", 0),
    BOOLEAN_VALUE("BOOLEAN_VALUE", 1, "BOOLEAN_VALUE", 1),
    NUMERICAL_VALUE("NUMERICAL_VALUE", 2, "NUMERICAL_VALUE", 2);
    private static GameRulesValueType[] VALUES;

    // region String decoding support

    // endregion

    GameRulesValueType(String text, int j, String textValue, int k) {}

    // region String decoding support

    // endregion

    static {
        net.minecraft.world.GameRulesValueType.VALUES =
                new GameRulesValueType[] {
                    net.minecraft.world.GameRulesValueType.ANY_VALUE,
                    net.minecraft.world.GameRulesValueType.BOOLEAN_VALUE,
                    net.minecraft.world.GameRulesValueType.NUMERICAL_VALUE
                };
    }

    // region String decoding support

    // endregion

}
