package net.minecraft.network.play.client;

public enum UseEntityAction {
    INTERACT("INTERACT", 0),
    ATTACK("ATTACK", 1),
    INTERACT_AT("INTERACT_AT", 2);

    private static UseEntityAction[] abe;

    // region Static initialization and string tables
    static {
        abe = new UseEntityAction[] {INTERACT, ATTACK, INTERACT_AT};
    }

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    UseEntityAction(String text, int j) {}
}
