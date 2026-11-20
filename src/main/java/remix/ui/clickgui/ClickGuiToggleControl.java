package remix.ui.clickgui;

import net.minecraft.entity.Entity;
import remix.ui.hud.HudMode;
import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;

public class ClickGuiToggleControl extends ClickGuiSettingControl<Setting> {

    public int getLabelColor() {
        return ((Setting) this.entryValue).asBoolean() ? -1 : -1761607681;
    }


    public ClickGuiToggleControl(
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

    public void handleKey(int i) {
        Setting setting = (Setting) this.entryValue;
        switch (i) {
            case 28:
                setting.setBooleanValue(!setting.asBoolean());
        }
    }

    public String getLabel() {
        Entity[] entityArray = ClickGuiModeCycleControl.getLegacyModeCycleFlowMarker();
        String text = ((Setting) this.entryValue).asBoolean() ? "On" : "Off";
        String textValue = ((Setting) this.entryValue).getName() + ": " + text;
        if (entityArray != null) {
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }

        return textValue;
    }
}
