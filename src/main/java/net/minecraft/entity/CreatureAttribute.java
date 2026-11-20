package net.minecraft.entity;

public enum CreatureAttribute {
    UNDEFINED("UNDEFINED", 0),
    UNDEAD("UNDEAD", 1),
    ARTHROPOD("ARTHROPOD", 2);

    private static CreatureAttribute[] afa;

    CreatureAttribute(String text, int j) {}

    // region Static initialization and string tables
    static {
        afa = new CreatureAttribute[] {UNDEFINED, UNDEAD, ARTHROPOD};
    }
    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
