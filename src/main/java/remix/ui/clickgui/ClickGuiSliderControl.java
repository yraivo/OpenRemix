package remix.ui.clickgui;

import net.minecraft.entity.Entity;

import optifine.MathUtils;

import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;
import remix.ui.hud.HudMode;

public class ClickGuiSliderControl extends ClickGuiSettingControl<Setting> {
    private static String labelSeparator;

    public String getLabel() {
        Setting setting = (Setting) this.entryValue;
        return setting.getName()
                + remix.ui.clickgui.ClickGuiSliderControl.labelSeparator
                + MathUtils.formatNumeric(setting.asDouble());
    }

    static {
        remix.ui.clickgui.ClickGuiSliderControl.labelSeparator = ": ";
    }

    public void handleKey(int i) {
        Entity[] entityArray = ClickGuiModeCycleControl.getLegacyModeCycleFlowMarker();
        Setting setting = (Setting) this.entryValue;
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
                if (!(setting.asDouble() + setting.getIncrement() <= setting.getMaximum())) {
                    break;
                }

                setting.setNumericValue(
                        MathUtils.round(setting.asDouble() + setting.getIncrement(), 3));
                if (entityArrayValue == null) {
                    break;
                }
            case 208:
                if (setting.asDouble() - setting.getIncrement() >= setting.getMinimum()) {
                    setting.setNumericValue(
                            MathUtils.round(setting.asDouble() - setting.getIncrement(), 3));
                }
        }
    }

    // region String decoding support

    // endregion

    public ClickGuiSliderControl(
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

    public int getLabelColor() {
        return this.capturingKeys ? -1 : -1761607681;
    }

    // region String decoding support

    // endregion
}
