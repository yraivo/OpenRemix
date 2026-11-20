package net.minecraft.network.play.server;

public enum S3CPacketUpdateScoreAction {
    CHANGE("CHANGE", 0),
    REMOVE("REMOVE", 1);
    private static S3CPacketUpdateScoreAction[] ack;

    S3CPacketUpdateScoreAction(String text, int j) {}

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        ack =
                new S3CPacketUpdateScoreAction[] {
                    net.minecraft.network.play.server.S3CPacketUpdateScoreAction.CHANGE, net.minecraft.network.play.server.S3CPacketUpdateScoreAction.REMOVE
                };
    }
}
