package net.minecraft.client.gui.spectator;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;

class SpectatorMenuMoveMenuObject implements ISpectatorMenuObject {
    private boolean b;

    private int a;

    public ChatComponent getSpectatorName() {
        return this.a < 0
                ? new ChatComponentText("Previous Page")
                : new ChatComponentText("Next Page");
    }

    public void method_8b0a960c841ff568c052(float f, int i) {
        String text = SpectatorMenu.cI();
        MinecraftClient.getMinecraft().getTextureManager().bindTexture(GuiSpectator.sJ);
        String textValue = text;
        if (this.a < 0) {
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 144.0F, 0.0F, 16, 16, 256.0F, 256.0F);
            if (textValue != null) {
                return;
            }
        }

        Gui.drawModalRectWithCustomSizedTexture(0, 0, 160.0F, 0.0F, 16, 16, 256.0F, 256.0F);
    }

    public boolean method_b417d7500ec59d69e373() {
        return this.b;
    }

    public SpectatorMenuMoveMenuObject(int i, boolean flag) {
        this.a = i;
        this.b = flag;
    }


    public void method_22ae0a8170d5c831fff3(SpectatorMenu spectatorMenu) {
        SpectatorMenu.access$112(spectatorMenu, this.a);
    }
}
