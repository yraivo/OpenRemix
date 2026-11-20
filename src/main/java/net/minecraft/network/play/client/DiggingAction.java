package net.minecraft.network.play.client;

public enum DiggingAction {
    START_DESTROY_BLOCK("START_DESTROY_BLOCK", 0),
    ABORT_DESTROY_BLOCK("ABORT_DESTROY_BLOCK", 1),
    STOP_DESTROY_BLOCK("STOP_DESTROY_BLOCK", 2),
    DROP_ALL_ITEMS("DROP_ALL_ITEMS", 3),
    DROP_ITEM("DROP_ITEM", 4),
    RELEASE_USE_ITEM("RELEASE_USE_ITEM", 5);
    private static DiggingAction[] abr;

    DiggingAction(String text, int j) {}

    // region Static initialization and string tables
    static {
        abr =
                new DiggingAction[] {
                    START_DESTROY_BLOCK,
                    ABORT_DESTROY_BLOCK,
                    STOP_DESTROY_BLOCK,
                    DROP_ALL_ITEMS,
                    DROP_ITEM,
                    RELEASE_USE_ITEM
                };
    }
    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
