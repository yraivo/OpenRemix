package net.minecraft.client.gui.inventory;

import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;

class GuiBeaconPowerButton extends GuiBeaconButton {
    private int sg;
    private static String jU;

    private int jB;
    GuiBeacon aoK;

    public GuiBeaconPowerButton(GuiBeacon aoKValue, int i, int j, int k, int l, int i1) {
        super(
                i,
                j,
                k,
                GuiContainer.inventoryBackground,
                0 + Potion.potionTypes[l].getStatusIconIndex() % 8 * 18,
                198 + Potion.potionTypes[l].getStatusIconIndex() / 8 * 18);
        this.aoK = aoKValue;
        this.sg = l;
        this.jB = i1;
    }

    // region String decoding support

    // endregion

    public void drawButtonForegroundLayer(int i, int j) {
        String text = I18n.format(Potion.potionTypes[this.sg].getName(), new Object[0]);
        if (this.jB >= 3 && this.sg != Potion.REGENERATION.id) {
            text = text + jU;
        }

        GuiBeacon.method_8bb211027a591da84ca5(this.aoK, text, i, j);
    }

    // region String decoding support

    // endregion

    static {
        jU = " II";
    }
}
