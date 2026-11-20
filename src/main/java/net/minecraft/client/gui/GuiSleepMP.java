package net.minecraft.client.gui;

import net.minecraft.client.network.ClientPlayHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityActionAction;

import java.io.IOException;

public class GuiSleepMP extends GuiChat {
    private static String zd;
    private static String[] packedTextFragments;

    protected void keyTyped(char c0, int i) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (i == 1) {
            this.wakeFromSleep();
            if (astring == null) {
                return;
            }
        }

        if (i != 28 && i != 156) {
            super.keyTyped(c0, i);
            if (astring == null) {
                return;
            }
        }

        String text = this.inputField.getText().trim();
        if (!text.isEmpty()) {
            this.mc.player.sendChatMessage(text);
        }

        this.inputField.setText(packedTextFragments[0]);
        this.mc.ingameGUI.getChatGUI().resetScroll();
    }

    // region String decoding support

    // endregion

    private void wakeFromSleep() {
        ClientPlayHandler connection = this.mc.player.connection;
        connection.addToSendQueue(
                new C0BPacketEntityAction(
                        this.mc.player, C0BPacketEntityActionAction.STOP_SLEEPING));
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.id == 1) {
            this.wakeFromSleep();
            if (astring == null) {
                return;
            }
        }

        super.actionPerformed(guiButton);
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(
                new GuiButton(
                        1, this.width / 2 - 100, this.height - 40, I18n.format(zd, new Object[0])));
    }

    private static IOException method_02836a3a008661c0ef77(IOException ioexception) {
        return ioexception;
    }

    static {
        net.minecraft.client.gui.GuiSleepMP.packedTextFragments = new String[2];
        net.minecraft.client.gui.GuiSleepMP.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiSleepMP.packedTextFragments[1] =
                new String("6+6.G\u0007a:'?(\u0000\u0004y4.\t6K\u0012}20=");
        zd = "multiplayer.stopSleeping";
    }

    // region String decoding support

    // endregion
}
