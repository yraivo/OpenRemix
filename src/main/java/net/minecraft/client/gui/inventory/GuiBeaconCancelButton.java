package net.minecraft.client.gui.inventory;

import net.minecraft.client.resources.I18n;

class GuiBeaconCancelButton extends GuiBeaconButton {

    private static String Cx;
    GuiBeacon aoH;

    // region String decoding support

    // endregion

    public GuiBeaconCancelButton(GuiBeacon aoHValue, int i, int j, int k) {
        super(i, j, k, GuiBeacon.access$000(), 112, 220);
        this.aoH = aoHValue;
    }

    // region String decoding support

    // endregion

    static {
        Cx = "gui.cancel";
    }

    public void drawButtonForegroundLayer(int i, int j) {
        GuiBeacon.method_dd4129e37ab6b15f1271(this.aoH, I18n.format(Cx, new Object[0]), i, j);
    }
}
