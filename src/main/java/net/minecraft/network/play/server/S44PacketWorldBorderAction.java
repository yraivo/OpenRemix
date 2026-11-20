package net.minecraft.network.play.server;

public enum S44PacketWorldBorderAction {
    SET_SIZE("SET_SIZE", 0),
    LERP_SIZE("LERP_SIZE", 1),
    SET_CENTER("SET_CENTER", 2),
    INITIALIZE("INITIALIZE", 3),
    SET_WARNING_TIME("SET_WARNING_TIME", 4),
    SET_WARNING_BLOCKS("SET_WARNING_BLOCKS", 5);

    private static S44PacketWorldBorderAction[] acw;

    // region String decoding support

    // endregion

    S44PacketWorldBorderAction(String text, int j) {}

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        acw =
                new S44PacketWorldBorderAction[] {
                    net.minecraft.network.play.server.S44PacketWorldBorderAction.SET_SIZE,
                    net.minecraft.network.play.server.S44PacketWorldBorderAction.LERP_SIZE,
                    net.minecraft.network.play.server.S44PacketWorldBorderAction.SET_CENTER,
                    net.minecraft.network.play.server.S44PacketWorldBorderAction.INITIALIZE,
                    net.minecraft.network.play.server.S44PacketWorldBorderAction.SET_WARNING_TIME,
                    net.minecraft.network.play.server.S44PacketWorldBorderAction.SET_WARNING_BLOCKS
                };
    }
}
