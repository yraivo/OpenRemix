package optifine;

import net.minecraft.client.gui.GuiScreen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;

import java.awt.Desktop;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

public class OptiFineCapeEditorScreen extends GuiScreen {
    private GuiScreen guiScreen;
    private static String[] packedTextFragments;

    protected void actionPerformed(GuiButton guiButton) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        switch (guiButton.id) {
            case 0:
                this.method_8d9b536bcdc9991c48fb();
                if (astring == null) {
                    break;
                }
            case 1:
                this.mc.displayGuiScreen(this.guiScreen);
        }
    }

    static {
        optifine.OptiFineCapeEditorScreen.packedTextFragments = new String[4];
        optifine.OptiFineCapeEditorScreen.packedTextFragments[0] = new String("-");
        optifine.OptiFineCapeEditorScreen.packedTextFragments[1] = new String("");
    }

    private void method_8d9b536bcdc9991c48fb() {
        try {
            GameProfile gameprofile = MinecraftClient.getMinecraft().player.getGameProfile();
            String name = gameprofile.getName();
            String text =
                    gameprofile
                            .getId()
                            .toString()
                            .replace(packedTextFragments[0], packedTextFragments[1]);
            String textValue = MinecraftClient.getMinecraft().getSession().getToken();
            Random random = new Random();
            Random random1 = new Random(System.identityHashCode(new Object()));
            BigInteger biginteger = new BigInteger(128, random);
            BigInteger biginteger1 = new BigInteger(128, random1);
            BigInteger biginteger2 = biginteger.xor(biginteger1);
            String textValue2 = biginteger2.toString(16);
            MinecraftClient.getMinecraft()
                    .getSessionService()
                    .joinServer(gameprofile, textValue, textValue2);
            String textValue3 =
                    "https://optifine.net/capeChange?u=" + text + "&n=" + name + "&s=" + textValue2;
            Desktop.getDesktop().browse(new URL(textValue3).toURI());
        } catch (AuthenticationException
                | URISyntaxException
                | IOException authenticationexception) {
            authenticationexception.printStackTrace();
        }
    }

    public void drawScreen(int i, int j, float f) {
        super.drawDefaultBackground();
        super.drawScreen(i, j, f);
    }

    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(
                new GuiButton(
                        0,
                        this.width / 2 - 155,
                        this.method_7eec03da329a017ec143(),
                        155,
                        20,
                        "Open Cape Editor"));
        this.buttonList.add(
                new GuiButton(
                        1,
                        this.width / 2 + 5,
                        this.method_7eec03da329a017ec143(),
                        155,
                        20,
                        I18n.format("gui.done", new Object[0])));
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    private int method_7eec03da329a017ec143() {
        return 147;
    }

    public OptiFineCapeEditorScreen(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }
}
