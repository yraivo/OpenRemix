package net.minecraft.client.gui;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettingsGameType;

import java.io.IOException;

public class GuiShareToLan extends GuiScreen {
    private GuiButton asQ;

    private GuiScreen yQ;
    private GuiButton aow;
    private static String[] packedTextFragments;
    private boolean aor;
    private String jU = "survival";

    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(
                new GuiButton(
                        101,
                        this.width / 2 - 155,
                        this.height - 28,
                        150,
                        20,
                        I18n.format("lanServer.start", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        102,
                        this.width / 2 + 5,
                        this.height - 28,
                        150,
                        20,
                        I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(
                this.aow =
                        new GuiButton(
                                104,
                                this.width / 2 - 155,
                                100,
                                150,
                                20,
                                I18n.format("selectWorld.gameMode", new Object[0])));
        this.buttonList.add(
                this.asQ =
                        new GuiButton(
                                103,
                                this.width / 2 + 5,
                                100,
                                150,
                                20,
                                I18n.format("selectWorld.allowCommands", new Object[0])));
        this.method_8d9b536bcdc9991c48fb();
    }

    private void method_8d9b536bcdc9991c48fb() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.aow.displayString =
                I18n.format("selectWorld.gameMode", new Object[0])
                        + packedTextFragments[0]
                        + I18n.format("selectWorld.gameMode." + this.jU, new Object[0]);
        String[] astring = astring1;
        this.asQ.displayString =
                I18n.format("selectWorld.allowCommands", new Object[0]) + packedTextFragments[1];
        if (this.aor) {
            this.asQ.displayString =
                    this.asQ.displayString + I18n.format("options.on", new Object[0]);
            if (astring == null) {
                return;
            }
        }

        this.asQ.displayString = this.asQ.displayString + I18n.format("options.off", new Object[0]);
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.id == 102) {
            this.mc.displayGuiScreen(this.yQ);
            if (astring == null) {
                return;
            }
        }

        if (guiButton.id == 104) {
            label70:
            {
                if (this.jU.equals("spectator")) {
                    this.jU = "creative";
                    if (astring == null) {
                        break label70;
                    }
                }

                if (this.jU.equals("creative")) {
                    this.jU = "adventure";
                    if (astring == null) {
                        break label70;
                    }
                }

                if (this.jU.equals("adventure")) {
                    this.jU = "survival";
                    if (astring == null) {
                        break label70;
                    }
                }

                this.jU = "spectator";
            }

            this.method_8d9b536bcdc9991c48fb();
            if (astring == null) {
                return;
            }
        }

        if (guiButton.id == 103) {
            this.aor = remix.compat.compiler.JvmBooleanSlots.fromInt((!this.aor ? 1 : 0));
            this.method_8d9b536bcdc9991c48fb();
            if (astring == null) {
                return;
            }
        }

        if (guiButton.id == 101) {
            Object object;
            {
                this.mc.displayGuiScreen((GuiScreen) null);
                String text =
                        this.mc
                                .getIntegratedServer()
                                .shareToLAN(WorldSettingsGameType.getByName(this.jU), this.aor);
                if (text != null) {
                    object =
                            new ChatComponentTranslation(
                                    "commands.publish.started", new Object[] {text});
                    if (!(astring == null)) {

                        object = new ChatComponentText("commands.publish.failed");
                    }
                } else {

                    object = new ChatComponentText("commands.publish.failed");
                }
            }

            this.mc.ingameGUI.getChatGUI().printChatMessage((ChatComponent) object);
        }
    }

    static {
        net.minecraft.client.gui.GuiShareToLan.packedTextFragments = new String[4];
        net.minecraft.client.gui.GuiShareToLan.packedTextFragments[0] = new String(" ");
        net.minecraft.client.gui.GuiShareToLan.packedTextFragments[1] = new String(" ");
    }

    public GuiShareToLan(GuiScreen guiScreen) {
        this.yQ = guiScreen;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("lanServer.title", new Object[0]),
                this.width / 2,
                50,
                16777215);
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("lanServer.otherPlayers", new Object[0]),
                this.width / 2,
                82,
                16777215);
        super.drawScreen(i, j, f);
    }
}
