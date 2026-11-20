package remix.ui.hud;

import net.minecraft.client.gui.ScaledResolution;
import remix.ui.hud.HudColor;
import remix.ui.hud.HudElement;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;

public final class WatermarkHudElement implements HudElement {

    private ClientFontRenderer clientFontRenderer;
    public String watermarkText =
            ClientServices.CLIENT_NAME.charAt(0) + "\u00a77" + ClientServices.CLIENT_NAME.substring(1) + " v" + "1.7.1";
    public String previousWatermarkText;
    private Setting<HudColor> setting;
    private static boolean watermarkEnabled;

    public static boolean isWatermarkEnabled() {
        return remix.ui.hud.WatermarkHudElement.watermarkEnabled;
    }

    public void render(ScaledResolution scaledResolution) {
        HudColor value = (HudColor) this.setting.getValue();
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        int i = value.getColorForModule(ClientServices.moduleManager.hud);
        this.clientFontRenderer.drawStringWithShadow(this.watermarkText, 5.0, ec.gameSettings.ofShowFps ? 11.0 : 6.0, i);
    }

    public static void setWatermarkEnabled(boolean flag) {
        remix.ui.hud.WatermarkHudElement.watermarkEnabled = flag;
    }

    public static boolean isWatermarkDisabled() {
        boolean flag = isWatermarkEnabled();
        return !flag;
    }

    static {
        setWatermarkEnabled(false);
    }

    // region String decoding support

    // endregion

    public WatermarkHudElement(
            Setting<HudColor> setting, ClientFontRenderer clientFontRenderer) {
        this.setting = setting;
        this.clientFontRenderer = clientFontRenderer;
        this.previousWatermarkText = this.watermarkText;
    }

    // region String decoding support

    // endregion
}
