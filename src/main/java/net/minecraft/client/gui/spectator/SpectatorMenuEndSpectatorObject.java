package net.minecraft.client.gui.spectator;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;

class SpectatorMenuEndSpectatorObject implements ISpectatorMenuObject {
    private static String a;

    private SpectatorMenuEndSpectatorObject() {}

    public void method_22ae0a8170d5c831fff3(SpectatorMenu spectatorMenu) {
        spectatorMenu.l();
    }

    public void method_8b0a960c841ff568c052(float f, int i) {
        MinecraftClient.getMinecraft().getTextureManager().bindTexture(GuiSpectator.sJ);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 128.0F, 0.0F, 16, 16, 256.0F, 256.0F);
    }

    // region String decoding support

    // endregion

    public boolean method_b417d7500ec59d69e373() {
        return true;
    }

    static {
        a = "Close menu";
    }

    SpectatorMenuEndSpectatorObject(Object object) {
        this();
    }

    public ChatComponent getSpectatorName() {
        return new ChatComponentText(a);
    }

    // region String decoding support

    // endregion
}
