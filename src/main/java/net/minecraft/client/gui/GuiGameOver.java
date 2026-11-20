package net.minecraft.client.gui;

import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;

import remix.client.RemixClient;
import remix.ui.screen.RemixMainMenuScreen;

import java.io.IOException;

public class GuiGameOver extends GuiScreen implements GuiYesNoCallback {
    private boolean field_146346_f = false;
    private int enableButtonsTimer;
    private static String[] packedTextFragments;

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            switch (guiButton.id) {
                case 0:
                    this.mc.player.respawnPlayer();
                    this.mc.displayGuiScreen((GuiScreen) null);
                    if (astring == null) {
                        return;
                    }
                case 1:
                    break;
                default:
                    return;
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(ioexception);
        }

        GuiYesNo guiYesNo =
                new GuiYesNo(
                        this,
                        I18n.format("deathScreen.quit.confirm", new Object[0]),
                        packedTextFragments[0],
                        I18n.format("deathScreen.titleScreen", new Object[0]),
                        I18n.format("deathScreen.respawn", new Object[0]),
                        0);
        this.mc.displayGuiScreen(guiYesNo);
        guiYesNo.setButtonDelay(20);
    }

    protected void keyTyped(char c0, int i) throws IOException {}

    static {
        net.minecraft.client.gui.GuiGameOver.packedTextFragments = new String[3];
        net.minecraft.client.gui.GuiGameOver.packedTextFragments[0] = new String("");
    }

    public void drawScreen(int i, int j, float f) {
        this.drawGradientRect(0.0, 0.0, this.width, this.height, 1615855616, -1602211792);
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        boolean hardcoreModeEnabled = this.mc.world.getWorldInfo().isHardcoreModeEnabled();
        String text =
                hardcoreModeEnabled
                        ? I18n.format("deathScreen.title.hardcore", new Object[0])
                        : I18n.format("deathScreen.title", new Object[0]);
        this.drawCenteredString(this.fontRendererObj, text, this.width / 2 / 2, 30, 16777215);
        GlStateManager.popMatrix();
        if (hardcoreModeEnabled) {
            this.drawCenteredString(
                    this.fontRendererObj,
                    I18n.format("deathScreen.hardcoreInfo", new Object[0]),
                    this.width / 2,
                    144,
                    16777215);
        }

        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("deathScreen.score", new Object[0])
                        + ": "
                        + EnumChatFormatting.YELLOW
                        + this.mc.player.getScore(),
                this.width / 2,
                100,
                16777215);
        super.drawScreen(i, j, f);
    }

    public void updateScreen() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        super.updateScreen();
        this.enableButtonsTimer++;
        String[] astring = astring1;
        if (this.enableButtonsTimer == 20) {
            for (GuiButton guiButton : this.buttonList) {
                guiButton.enabled = true;
                if (astring != null) {
                    break;
                }
            }
        }
    }

    public void initGui() {
        boolean legacyFlow = Gui.getLegacyGuiRenderMarker() != null;
        this.buttonList.clear();
        boolean hardcore = this.mc.world.getWorldInfo().isHardcoreModeEnabled();
        int x = this.width / 2 - 100;
        int y = this.height / 4;
        if (hardcore) {
            boolean singleplayer = this.mc.isIntegratedServerRunning();
            if (singleplayer) {
                this.buttonList.add(
                        new GuiButton(
                                1,
                                x,
                                y + 96,
                                I18n.format("deathScreen.deleteWorld", new Object[0])));
            }
            if (!singleplayer || legacyFlow) {
                this.buttonList.add(
                        new GuiButton(
                                1,
                                x,
                                y + 96,
                                I18n.format("deathScreen.leaveServer", new Object[0])));
            }
        }
        if (!hardcore || legacyFlow) {
            this.buttonList.add(
                    new GuiButton(0, x, y + 72, I18n.format("deathScreen.respawn", new Object[0])));
            this.buttonList.add(
                    new GuiButton(
                            1, x, y + 96, I18n.format("deathScreen.titleScreen", new Object[0])));
            if (this.mc.getSession() == null) {
                this.buttonList.get(1).enabled = false;
            }
        }

        for (GuiButton button : this.buttonList) {
            button.enabled = false;
            if (legacyFlow) {
                break;
            }
        }
    }

    public void confirmClicked(boolean flag, int i) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (flag) {
            this.mc.world.sendQuittingDisconnectingPacket();
            this.mc.loadWorld((ClientWorld) null);
            this.mc.displayGuiScreen(new RemixMainMenuScreen(RemixClient.INSTANCE));
            if (astring == null) {
                return;
            }
        }

        this.mc.player.respawnPlayer();
        this.mc.displayGuiScreen((GuiScreen) null);
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
