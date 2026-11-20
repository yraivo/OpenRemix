package net.minecraft.network;

public enum EnumPacketDirection {
    SERVERBOUND("SERVERBOUND", 0),
    CLIENTBOUND("CLIENTBOUND", 1);

    private static EnumPacketDirection[] abc;

    EnumPacketDirection(String text, int j) {}

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        abc =
                new EnumPacketDirection[] {
                    net.minecraft.network.EnumPacketDirection.SERVERBOUND, net.minecraft.network.EnumPacketDirection.CLIENTBOUND
                };
    }
}
