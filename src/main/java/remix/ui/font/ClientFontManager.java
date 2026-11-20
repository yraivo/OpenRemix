package remix.ui.font;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ResourceLocation;

import java.awt.Font;
import java.io.InputStream;

public class ClientFontManager {
    public ClientFontRenderer roboto16;

    public ClientFontRenderer comfortaa24;
    public ClientFontRenderer roboto32;
    public ClientFontRenderer robotoBold14;
    public ClientFontRenderer roboto19;
    public ClientFontRenderer comfortaa18;
    public ClientFontRenderer roboto21;

    public ClientFontRenderer menuRoboto16;
    public ClientFontRenderer montserratMedium19;
    public ClientFontRenderer comfortaa16 =
            new ClientFontRenderer(this.loadFont(16, "comfortaa"), true, true);
    public ClientFontRenderer roboto18;

    public ClientFontManager() {
        this.roboto16 =
                new ClientFontRenderer(this.loadFont(16, "roboto"), true, true);
        this.menuRoboto16 =
                new ClientFontRenderer(this.loadFont(16, "roboto"), true, true);
        this.roboto18 =
                new ClientFontRenderer(this.loadFont(18, "roboto"), true, true);
        this.roboto19 =
                new ClientFontRenderer(this.loadFont(19, "roboto"), true, true);
        this.roboto21 =
                new ClientFontRenderer(this.loadFont(21, "roboto"), true, true);
        this.roboto32 =
                new ClientFontRenderer(this.loadFont(32, "roboto"), true, true);
        this.robotoBold14 =
                new ClientFontRenderer(
                        this.loadFont(14, "roboto-bold"), true, true);
        this.comfortaa18 =
                new ClientFontRenderer(
                        this.loadFont(18, "comfortaa"), true, true);
        this.comfortaa24 =
                new ClientFontRenderer(
                        this.loadFont(24, "comfortaa"), true, true);
        this.montserratMedium19 =
                new ClientFontRenderer(
                        this.loadFont(19, "Montserrat-Medium"), true, true);
    }


    private Font loadFont(int i, String text) {
        try {
            InputStream inputstream =
                    MinecraftClient.getMinecraft()
                            .getResourceManager()
                            .getResource(new ResourceLocation("remix/fonts/" + text + ".ttf"))
                            .getInputStream();
            return Font.createFont(0, inputstream).deriveFont(0, i);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
