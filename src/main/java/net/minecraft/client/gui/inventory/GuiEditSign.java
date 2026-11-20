package net.minecraft.client.gui.inventory;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.network.ClientPlayHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiEditSign extends GuiScreen {

    private GuiButton doneBtn;
    private int updateCounter;

    private int editLine;
    private TileEntitySign tileSign;

    public GuiEditSign(TileEntitySign arKValue) {
        this.tileSign = arKValue;
    }


    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.enabled && guiButton.id == 0) {
            this.tileSign.markDirty();
            this.mc.displayGuiScreen((GuiScreen) null);
        }
    }

    protected void keyTyped(char c0, int i) throws IOException {
        if (i == 200) {
            this.editLine = this.editLine - 1 & 3;
        }

        if (i == 208 || i == 28 || i == 156) {
            this.editLine = this.editLine + 1 & 3;
        }

        String text = this.tileSign.signText[this.editLine].getUnformattedText();
        if (i == 14 && text.length() > 0) {
            text = text.substring(0, text.length() - 1);
        }

        if (ChatAllowedCharacters.isAllowedCharacter(c0)
                && this.fontRendererObj.getStringWidth(text + c0) <= 90) {
            text = text + c0;
        }

        this.tileSign.signText[this.editLine] = new ChatComponentText(text);
        if (i == 1) {
            this.actionPerformed(this.doneBtn);
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void updateScreen() {
        this.updateCounter++;
    }

    public void drawScreen(int i, int j, float f) {
        label30:
        {
            this.drawDefaultBackground();
            this.drawCenteredString(
                    this.fontRendererObj,
                    I18n.format("sign.edit", new Object[0]),
                    this.width / 2,
                    40,
                    16777215);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.translate(this.width / 2, 0.0F, 50.0F);
            float f1 = 93.75F;
            GlStateManager.scale(-f1, -f1, -f1);
            int[] aint1 = CreativeCrafting.getLegacyCreativeFlowMarker();
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            int[] aint = aint1;
            Block block = this.tileSign.getBlockType();
            if (block == Blocks.standing_sign) {
                float f2 = this.tileSign.getBlockMetadata() * 360 / 16.0F;
                GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(0.0F, -1.0625F, 0.0F);
                if (aint == null) {
                    break label30;
                }
            }

            int k = this.tileSign.getBlockMetadata();
            float f3 = 0.0F;
            if (k == 2) {
                f3 = 180.0F;
            }

            if (k == 4) {
                f3 = 90.0F;
            }

            if (k == 5) {
                f3 = -90.0F;
            }

            GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -1.0625F, 0.0F);
        }

        if (this.updateCounter / 6 % 2 == 0) {
            this.tileSign.lineBeingEdited = this.editLine;
        }

        TileEntityRendererDispatcher.instance.renderTileEntityAt(
                this.tileSign, -0.5, -0.75, -0.5, 0.0F);
        this.tileSign.lineBeingEdited = -1;
        GlStateManager.popMatrix();
        super.drawScreen(i, j, f);
    }

    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(
                this.doneBtn =
                        new GuiButton(
                                0,
                                this.width / 2 - 100,
                                this.height / 4 + 120,
                                I18n.format("gui.done", new Object[0])));
        this.tileSign.setEditable(false);
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        ClientPlayHandler networkHandler = this.mc.getNetworkHandler();
        if (networkHandler != null) {
            networkHandler.addToSendQueue(
                    new C12PacketUpdateSign(this.tileSign.getPos(), this.tileSign.signText));
        }

        this.tileSign.setEditable(true);
    }
}
