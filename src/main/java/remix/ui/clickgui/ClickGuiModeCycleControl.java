package remix.ui.clickgui;

import net.minecraft.entity.Entity;

import remix.setting.Setting;
import remix.setting.SettingOption;
import remix.ui.font.ClientFontRenderer;
import remix.ui.hud.HudMode;

public class ClickGuiModeCycleControl extends ClickGuiSettingControl<Setting> {

    private static String labelSeparator;
    private static Entity[] legacyModeCycleFlowMarker;

    public static Entity[] getLegacyModeCycleFlowMarker() {
        return remix.ui.clickgui.ClickGuiModeCycleControl.legacyModeCycleFlowMarker;
    }

    public static void setLegacyModeCycleFlowMarker(Entity[] entityArray) {
        remix.ui.clickgui.ClickGuiModeCycleControl.legacyModeCycleFlowMarker = entityArray;
    }

    public ClickGuiModeCycleControl(
            Setting<HudMode> setting,
            ClientFontRenderer clientFontRenderer,
            float f,
            float f1,
            float f2,
            float f3,
            Setting settingValue,
            int i) {
        super(setting, clientFontRenderer, f, f1, f2, f3, settingValue, i);
    }

    public String getLabel() {
        getLegacyModeCycleFlowMarker();
        Setting setting = (Setting) this.entryValue;
        String text =
                ((Setting) this.entryValue).getName()
                        + remix.ui.clickgui.ClickGuiModeCycleControl.labelSeparator
                        + ((SettingOption) ((Setting) this.entryValue).getValue()).getDisplayName();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyModeCycleFlowMarker(new Entity[3]);
        }

        return text;
    }

    public void handleKey(int i) {
        Setting setting = (Setting) this.entryValue;
        Entity[] entityArray = getLegacyModeCycleFlowMarker();
        int j = setting.getOptionIndex();
        Entity[] entityArrayValue = entityArray;
        switch (i) {
            case 28:
                this.capturingKeys =
                        remix.compat.compiler.JvmBooleanSlots.fromInt(
                                (!this.capturingKeys ? 1 : 0));
                if (entityArrayValue == null) {
                    break;
                }
            case 200:
                if (--j < 0) {
                    j = setting.getEnumValues().length - 1;
                }

                setting.setValue(setting.getEnumValues()[j]);
                if (entityArrayValue == null) {
                    break;
                }
            case 208:
                if (++j > setting.getEnumValues().length - 1) {
                    j = 0;
                }

                setting.setValue(setting.getEnumValues()[j]);
        }
    }

    // region String decoding support

    // endregion

    static {
        if (getLegacyModeCycleFlowMarker() != null) {
            setLegacyModeCycleFlowMarker(new Entity[1]);
        }
        remix.ui.clickgui.ClickGuiModeCycleControl.labelSeparator = ": ";
    }

    public int getLabelColor() {
        return this.capturingKeys ? -1 : -1761607681;
    }

    // region String decoding support

    // endregion
}
