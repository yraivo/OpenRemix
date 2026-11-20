package remix.ui.clickgui;

import remix.ui.clickgui.ClickGuiComponent;
import remix.client.RemixClient;
import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;

public class ClickGuiSettingComponent extends ClickGuiComponent {
    public static final ClientFontRenderer fontRenderer = RemixClient.fonts.menuRoboto16;
    protected Setting setting;

    public ClickGuiSettingComponent(Setting setting, double d0, double d1, double d2, double d3) {
        super(d0, d1, d2, d3);
        this.setting = setting;
    }
}
