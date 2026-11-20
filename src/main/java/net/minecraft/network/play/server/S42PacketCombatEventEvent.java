package net.minecraft.network.play.server;

public enum S42PacketCombatEventEvent {
    ENTER_COMBAT("ENTER_COMBAT", 0),
    END_COMBAT("END_COMBAT", 1),
    ENTITY_DIED("ENTITY_DIED", 2);
    private static S42PacketCombatEventEvent[] acn;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        acn =
                new S42PacketCombatEventEvent[] {
                    net.minecraft.network.play.server.S42PacketCombatEventEvent.ENTER_COMBAT,
                    net.minecraft.network.play.server.S42PacketCombatEventEvent.END_COMBAT,
                    net.minecraft.network.play.server.S42PacketCombatEventEvent.ENTITY_DIED
                };
    }

    S42PacketCombatEventEvent(String text, int j) {}
}
