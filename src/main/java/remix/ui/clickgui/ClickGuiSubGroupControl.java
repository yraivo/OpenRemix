package remix.ui.clickgui;

import net.minecraft.entity.Entity;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;
import remix.ui.hud.HudMode;
import remix.ui.hud.TabGuiHudElement;

public class ClickGuiSubGroupControl extends ClickGuiSettingControl {
    private static int[] legacySubGroupFlowState;
    private ClickGuiSettingControl childControl;

    public int getLabelColor() {
        return this.childControl.getLabelColor();
    }

    public String getLabel() {
        return this.childControl.getLabel();
    }

    public static void setLegacySubGroupFlowState(int[] aint) {
        remix.ui.clickgui.ClickGuiSubGroupControl.legacySubGroupFlowState = aint;
    }

    public void createChildControl() {
        getLegacySubGroupFlowState();

        Setting setting = (Setting) this.entryValue;

        switch (setting.getType()) {
            case BOOLEAN:
                this.childControl =
                        new ClickGuiToggleControl(
                                this.setting,
                                this.clientFontRenderer,
                                this.left,
                                this.top,
                                this.width,
                                this.rowHeight,
                                setting,
                                this.styleColor);

                break;

            case NUMERIC:
                this.childControl =
                        new ClickGuiSliderControl(
                                this.setting,
                                this.clientFontRenderer,
                                this.left,
                                this.top,
                                this.width,
                                this.rowHeight,
                                setting,
                                this.styleColor);

                break;

            case ENUM:
                this.childControl =
                        new ClickGuiModeCycleControl(
                                this.setting,
                                this.clientFontRenderer,
                                this.left,
                                this.top,
                                this.width,
                                this.rowHeight,
                                setting,
                                this.styleColor);
        }
    }

    public static int[] getLegacySubGroupFlowState() {
        return remix.ui.clickgui.ClickGuiSubGroupControl.legacySubGroupFlowState;
    }

    static {
        if (getLegacySubGroupFlowState() == null) {
            setLegacySubGroupFlowState(new int[4]);
        }
    }

    public ClickGuiSubGroupControl(
            Setting<HudMode> setting,
            ClientFontRenderer clientFontRenderer,
            float f,
            float f1,
            float f2,
            float f3,
            Setting settingValue,
            int i) {
        super(setting, clientFontRenderer, f, f1, f2, f3, settingValue, i);
        this.createChildControl();
    }

    public void handleKey(int keyCode) {
        getLegacySubGroupFlowState();
        if (keyCode == 203) {
            RemixClient.INSTANCE.getClientServices();
            TabGuiHudElement tabGui = ClientServices.moduleManager.hud.tabGuiRenderer;
            if (!tabGui.openPanels.isEmpty()) {
                tabGui.openPanels.remove(tabGui.openPanels.size() - 1);
            }
        } else {
            this.childControl.handleKey(keyCode);
        }

        this.capturingKeys = this.childControl.capturingKeys;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacySubGroupFlowState(new int[5]);
        }
    }
}
