package net.minecraft.client.gui.inventory;

import net.minecraft.client.resources.I18n;

class GuiBeaconConfirmButton extends GuiBeaconButton {
    GuiBeacon aoH;

    private static String Cx;

    static {
        Cx = "gui.done";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public GuiBeaconConfirmButton(GuiBeacon aoHValue, int i, int j, int k) {
        super(i, j, k, GuiBeacon.access$000(), 90, 220);
        this.aoH = aoHValue;
    }

    public void drawButtonForegroundLayer(int i, int j) {
        GuiBeacon.method_2b4ff8d0b3c834b025bc(this.aoH, I18n.format(Cx, new Object[0]), i, j);
    }
}
