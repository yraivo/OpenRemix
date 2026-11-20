package remix.ui.clickgui;

import net.minecraft.entity.Entity;

import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;
import remix.ui.hud.HudMode;

public abstract class ClickGuiSettingControl<T> {
    public float top;
    public T entryValue;
    public float rowHeight;
    private static int legacySettingRowFlowState;
    public Setting<HudMode> setting;
    public float left;
    public ClientFontRenderer clientFontRenderer;
    public boolean capturingKeys;
    public float width;
    public int styleColor;

    public static int getLegacySettingRowFlowToken() {
        int i = getLegacySettingRowFlowState();
        return i == 0 ? 116 : 0;
    }

    public abstract int getLabelColor();

    public abstract String getLabel();

    public ClickGuiSettingControl(
            Setting<HudMode> setting,
            ClientFontRenderer clientFontRenderer,
            float f,
            float f1,
            float f2,
            float f3,
            T object,
            int i) {
        super();
        int initialFlowToken = getLegacySettingRowFlowToken();

        int k = initialFlowToken;

        this.setting = setting;
        this.clientFontRenderer = clientFontRenderer;
        int j = k;
        this.left = f;
        this.top = f1;
        this.width = f2;
        this.rowHeight = f3;
        this.entryValue = (T) object;
        this.styleColor = i;
        if (j != 0) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }
    }

    static {
        if (getLegacySettingRowFlowToken() != 0) {
            setLegacySettingRowFlowState(106);
        }
    }

    public abstract void handleKey(int i);

    public static void setLegacySettingRowFlowState(int i) {
        remix.ui.clickgui.ClickGuiSettingControl.legacySettingRowFlowState = i;
    }

    public static int getLegacySettingRowFlowState() {
        return remix.ui.clickgui.ClickGuiSettingControl.legacySettingRowFlowState;
    }

    public void drawRow() {
        int j = getLegacySettingRowFlowToken();
        this.clientFontRenderer.drawString(
                this.getLabel(),
                this.left + 5.0F,
                this.top
                        + this.rowHeight / 2.0F
                        - this.clientFontRenderer.getHeight() / 2.0F
                        + 0.5F,
                this.getLabelColor());
        int i = j;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacySettingRowFlowState(++i);
        }
    }
}
