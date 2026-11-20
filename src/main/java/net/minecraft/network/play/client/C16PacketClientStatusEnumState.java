package net.minecraft.network.play.client;

public enum C16PacketClientStatusEnumState {
    PERFORM_RESPAWN("PERFORM_RESPAWN", 0),
    REQUEST_STATS("REQUEST_STATS", 1),
    OPEN_INVENTORY_ACHIEVEMENT("OPEN_INVENTORY_ACHIEVEMENT", 2);

    private static C16PacketClientStatusEnumState[] abD;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        abD =
                new C16PacketClientStatusEnumState[] {
                    net.minecraft.network.play.client.C16PacketClientStatusEnumState.PERFORM_RESPAWN,
                    net.minecraft.network.play.client.C16PacketClientStatusEnumState.REQUEST_STATS,
                    net.minecraft.network.play.client.C16PacketClientStatusEnumState.OPEN_INVENTORY_ACHIEVEMENT
                };
    }

    C16PacketClientStatusEnumState(String text, int j) {}
}
