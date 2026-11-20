package net.minecraft.network.play.server;

public enum S38PacketPlayerListItemAction {
    ADD_PLAYER("ADD_PLAYER", 0),
    UPDATE_GAME_MODE("UPDATE_GAME_MODE", 1),
    UPDATE_LATENCY("UPDATE_LATENCY", 2),
    UPDATE_DISPLAY_NAME("UPDATE_DISPLAY_NAME", 3),
    REMOVE_PLAYER("REMOVE_PLAYER", 4);
    private static S38PacketPlayerListItemAction[] ace;

    // region String decoding support

    // endregion

    S38PacketPlayerListItemAction(String text, int j) {}

    static {
        ace =
                new S38PacketPlayerListItemAction[] {
                    net.minecraft.network.play.server.S38PacketPlayerListItemAction.ADD_PLAYER,
                    net.minecraft.network.play.server.S38PacketPlayerListItemAction.UPDATE_GAME_MODE,
                    net.minecraft.network.play.server.S38PacketPlayerListItemAction.UPDATE_LATENCY,
                    net.minecraft.network.play.server.S38PacketPlayerListItemAction.UPDATE_DISPLAY_NAME,
                    net.minecraft.network.play.server.S38PacketPlayerListItemAction.REMOVE_PLAYER
                };
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
