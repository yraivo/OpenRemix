package net.minecraft.network.play.client;

public enum C19PacketResourcePackStatusAction {
    SUCCESSFULLY_LOADED("SUCCESSFULLY_LOADED", 0),
    DECLINED("DECLINED", 1),
    FAILED_DOWNLOAD("FAILED_DOWNLOAD", 2),
    ACCEPTED("ACCEPTED", 3);

    private static C19PacketResourcePackStatusAction[] abK;

    // region String decoding support

    // endregion

    static {
        abK =
                new C19PacketResourcePackStatusAction[] {
                    net.minecraft.network.play.client.C19PacketResourcePackStatusAction.SUCCESSFULLY_LOADED,
                    net.minecraft.network.play.client.C19PacketResourcePackStatusAction.DECLINED,
                    net.minecraft.network.play.client.C19PacketResourcePackStatusAction.FAILED_DOWNLOAD,
                    net.minecraft.network.play.client.C19PacketResourcePackStatusAction.ACCEPTED
                };
    }

    C19PacketResourcePackStatusAction(String text, int j) {}

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
