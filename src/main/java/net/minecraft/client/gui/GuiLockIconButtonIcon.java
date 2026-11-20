package net.minecraft.client.gui;

enum GuiLockIconButtonIcon {
    LOCKED("LOCKED", 0, 0, 146),
    LOCKED_HOVER("LOCKED_HOVER", 1, 0, 166),
    LOCKED_DISABLED("LOCKED_DISABLED", 2, 0, 186),
    UNLOCKED("UNLOCKED", 3, 20, 146),
    UNLOCKED_HOVER("UNLOCKED_HOVER", 4, 20, 166),
    UNLOCKED_DISABLED("UNLOCKED_DISABLED", 5, 20, 186);
    private static GuiLockIconButtonIcon[] aLb;

    private int a;

    private int cz;

    public int method_9b417d27949cba33a0d0() {
        return this.a;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        aLb =
                new GuiLockIconButtonIcon[] {
                    net.minecraft.client.gui.GuiLockIconButtonIcon.LOCKED,
                    net.minecraft.client.gui.GuiLockIconButtonIcon.LOCKED_HOVER,
                    net.minecraft.client.gui.GuiLockIconButtonIcon.LOCKED_DISABLED,
                    net.minecraft.client.gui.GuiLockIconButtonIcon.UNLOCKED,
                    net.minecraft.client.gui.GuiLockIconButtonIcon.UNLOCKED_HOVER,
                    net.minecraft.client.gui.GuiLockIconButtonIcon.UNLOCKED_DISABLED
                };
    }

    public int method_7eec03da329a017ec143() {
        return this.cz;
    }

    // region String decoding support

    // endregion

    GuiLockIconButtonIcon(String text, int l, int j, int k) {
        this.a = j;
        this.cz = k;
    }
}
