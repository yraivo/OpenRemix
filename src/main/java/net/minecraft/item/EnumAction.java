package net.minecraft.item;

public enum EnumAction {
    NONE("NONE", 0),
    EAT("EAT", 1),
    DRINK("DRINK", 2),
    BLOCK("BLOCK", 3),
    BOW("BOW", 4);
    private static EnumAction[] ZG;

    static {
        ZG =
                new EnumAction[] {
                    net.minecraft.item.EnumAction.NONE,
                    net.minecraft.item.EnumAction.EAT,
                    net.minecraft.item.EnumAction.DRINK,
                    net.minecraft.item.EnumAction.BLOCK,
                    net.minecraft.item.EnumAction.BOW
                };
    }

    EnumAction(String text, int j) {}

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
