package net.minecraft.client.gui;

import io.netty.buffer.Unpooled;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatComponent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiCommandBlock extends GuiScreen {
    private CommandBlockLogic localCommandBlock;

    private GuiButton anE;
    private GuiButton aog;
    private boolean field_175389_t;
    private GuiButton anZ;
    private GuiTextField commandTextField;
    private static Logger field_146488_a;
    private GuiTextField previousOutputTextField;

    private static String[] packedTextFragments;

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(
                this.fontRendererObj,
                I18n.format("advMode.setCommand", new Object[0]),
                this.width / 2,
                20,
                16777215);
        this.drawString(
                this.fontRendererObj,
                I18n.format("advMode.command", new Object[0]),
                this.width / 2 - 150,
                37,
                10526880);
        this.commandTextField.drawTextBox();
        byte b0 = 75;
        byte b1 = 0;
        FontRenderer apkValue = this.fontRendererObj;
        String text = I18n.format("advMode.nearestPlayer", new Object[0]);
        int k = this.width / 2 - 150;
        int l = b1 + 1;
        this.drawString(apkValue, text, k, b0 + b1 * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(
                this.fontRendererObj,
                I18n.format("advMode.randomPlayer", new Object[0]),
                this.width / 2 - 150,
                b0 + l++ * this.fontRendererObj.FONT_HEIGHT,
                10526880);
        this.drawString(
                this.fontRendererObj,
                I18n.format("advMode.allPlayers", new Object[0]),
                this.width / 2 - 150,
                b0 + l++ * this.fontRendererObj.FONT_HEIGHT,
                10526880);
        this.drawString(
                this.fontRendererObj,
                I18n.format("advMode.allEntities", new Object[0]),
                this.width / 2 - 150,
                b0 + l++ * this.fontRendererObj.FONT_HEIGHT,
                10526880);
        this.drawString(
                this.fontRendererObj,
                packedTextFragments[2],
                this.width / 2 - 150,
                b0 + l++ * this.fontRendererObj.FONT_HEIGHT,
                10526880);
        if (this.previousOutputTextField.getText().length() > 0) {
            int i1 = b0 + l * this.fontRendererObj.FONT_HEIGHT + 16;
            this.drawString(
                    this.fontRendererObj,
                    I18n.format("advMode.previousOutput", new Object[0]),
                    this.width / 2 - 150,
                    i1,
                    10526880);
            this.previousOutputTextField.drawTextBox();
        }

        super.drawScreen(i, j, f);
    }

    static {
        net.minecraft.client.gui.GuiCommandBlock.packedTextFragments = new String[8];
        net.minecraft.client.gui.GuiCommandBlock.packedTextFragments[0] = new String("O");
        net.minecraft.client.gui.GuiCommandBlock.packedTextFragments[1] = new String("-");
        net.minecraft.client.gui.GuiCommandBlock.packedTextFragments[2] = new String("");
        net.minecraft.client.gui.GuiCommandBlock.packedTextFragments[3] = new String("O");
        net.minecraft.client.gui.GuiCommandBlock.packedTextFragments[4] = new String("X");
        net.minecraft.client.gui.GuiCommandBlock.packedTextFragments[5] = new String("-");
        net.minecraft.client.gui.GuiCommandBlock.field_146488_a = LogManager.getLogger();
    }

    public GuiCommandBlock(CommandBlockLogic apuValue) {
        this.localCommandBlock = apuValue;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    protected void keyTyped(char c0, int i) throws IOException {
        this.commandTextField.textboxKeyTyped(c0, i);
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        this.previousOutputTextField.textboxKeyTyped(c0, i);
        String[] astring = astring1;
        this.aog.enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.commandTextField.getText().trim().length() > 0 ? 1 : 0));
        if (i != 28 && i != 156) {
            if (i != 1) {
                return;
            }

            this.actionPerformed(this.anE);
            if (astring == null) {
                return;
            }
        }

        this.actionPerformed(this.aog);
    }

    public void updateScreen() {
        this.commandTextField.updateCursorCounter();
    }

    private void method_8d9b536bcdc9991c48fb() {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.localCommandBlock.shouldTrackOutput()) {
            this.anZ.displayString = packedTextFragments[3];
            if (this.localCommandBlock.getLastOutput() == null) {
                return;
            }

            this.previousOutputTextField.setText(
                    this.localCommandBlock.getLastOutput().getUnformattedText());
            if (astring == null) {
                return;
            }
        }

        this.anZ.displayString = packedTextFragments[4];
        this.previousOutputTextField.setText(packedTextFragments[5]);
    }

    protected void mouseClicked(int i, int j, int k) throws IOException {
        super.mouseClicked(i, j, k);
        this.commandTextField.mouseClicked(i, j, k);
        this.previousOutputTextField.mouseClicked(i, j, k);
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                this.localCommandBlock.setTrackOutput(this.field_175389_t);
                this.mc.displayGuiScreen((GuiScreen) null);
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 0) {
                PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                packetBuffer.writeByte(this.localCommandBlock.func_145751_f());
                this.localCommandBlock.func_145757_a(packetBuffer);
                packetBuffer.writeString(this.commandTextField.getText());
                packetBuffer.writeBoolean(this.localCommandBlock.shouldTrackOutput());
                this.mc
                        .getNetworkHandler()
                        .addToSendQueue(new C17PacketCustomPayload("MC|AdvCdm", packetBuffer));
                if (!this.localCommandBlock.shouldTrackOutput()) {
                    this.localCommandBlock.setLastOutput((ChatComponent) null);
                }

                this.mc.displayGuiScreen((GuiScreen) null);
                if (astring == null) {
                    return;
                }
            }

            if (guiButton.id == 4) {
                this.localCommandBlock.setTrackOutput(!this.localCommandBlock.shouldTrackOutput());
                this.method_8d9b536bcdc9991c48fb();
            }
        }
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(
                this.aog =
                        new GuiButton(
                                0,
                                this.width / 2 - 4 - 150,
                                this.height / 4 + 120 + 12,
                                150,
                                20,
                                I18n.format("gui.done", new Object[0])));
        this.buttonList.add(
                this.anE =
                        new GuiButton(
                                1,
                                this.width / 2 + 4,
                                this.height / 4 + 120 + 12,
                                150,
                                20,
                                I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(
                this.anZ =
                        new GuiButton(
                                4, this.width / 2 + 150 - 20, 150, 20, 20, packedTextFragments[0]));
        this.commandTextField =
                new GuiTextField(2, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
        this.commandTextField.setMaxStringLength(32767);
        this.commandTextField.setFocused(true);
        this.commandTextField.setText(this.localCommandBlock.getCustomName());
        this.previousOutputTextField =
                new GuiTextField(3, this.fontRendererObj, this.width / 2 - 150, 150, 276, 20);
        this.previousOutputTextField.setMaxStringLength(32767);
        this.previousOutputTextField.setEnabled(false);
        this.previousOutputTextField.setText(packedTextFragments[1]);
        this.field_175389_t = this.localCommandBlock.shouldTrackOutput();
        this.method_8d9b536bcdc9991c48fb();
        this.aog.enabled =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (this.commandTextField.getText().trim().length() > 0 ? 1 : 0));
    }
}
