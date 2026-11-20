package net.minecraft.network.play.client;

public enum C0BPacketEntityActionAction {
    START_SNEAKING("START_SNEAKING", 0),
    STOP_SNEAKING("STOP_SNEAKING", 1),
    STOP_SLEEPING("STOP_SLEEPING", 2),
    START_SPRINTING("START_SPRINTING", 3),
    STOP_SPRINTING("STOP_SPRINTING", 4),
    RIDING_JUMP("RIDING_JUMP", 5),
    OPEN_INVENTORY("OPEN_INVENTORY", 6);
    private static C0BPacketEntityActionAction[] abA;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        abA =
                new C0BPacketEntityActionAction[] {
                    net.minecraft.network.play.client.C0BPacketEntityActionAction.START_SNEAKING,
                    net.minecraft.network.play.client.C0BPacketEntityActionAction.STOP_SNEAKING,
                    net.minecraft.network.play.client.C0BPacketEntityActionAction.STOP_SLEEPING,
                    net.minecraft.network.play.client.C0BPacketEntityActionAction.START_SPRINTING,
                    net.minecraft.network.play.client.C0BPacketEntityActionAction.STOP_SPRINTING,
                    net.minecraft.network.play.client.C0BPacketEntityActionAction.RIDING_JUMP,
                    net.minecraft.network.play.client.C0BPacketEntityActionAction.OPEN_INVENTORY
                };
    }

    // region String decoding support

    // endregion

    C0BPacketEntityActionAction(String text, int j) {}
}
