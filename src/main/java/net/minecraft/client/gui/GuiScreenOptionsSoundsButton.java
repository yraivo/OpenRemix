package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

class GuiScreenOptionsSoundsButton extends GuiButton {
    GuiScreenOptionsSounds arg;
    public float rj;
    private String oF;

    public boolean kX;
    private SoundCategory arh;

    private static String[] packedTextFragments;

    protected void mouseDragged(MinecraftClient minecraftClient, int i, int j) {
        if (this.visible) {
            if (this.kX) {
                this.rj = (float) (i - (this.xPosition + 4)) / (this.width - 8);
                this.rj = MathHelper.clamp(this.rj, 0.0F, 1.0F);
                minecraftClient.gameSettings.setSoundLevel(this.arh, this.rj);
                minecraftClient.gameSettings.saveOptions();
                this.displayString =
                        this.oF + ": " + this.arg.getSoundVolume(this.arh);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(
                    this.xPosition + (int) (this.rj * (this.width - 8)),
                    this.yPosition,
                    0,
                    66,
                    4,
                    20);
            this.drawTexturedModalRect(
                    this.xPosition + (int) (this.rj * (this.width - 8)) + 4,
                    this.yPosition,
                    196,
                    66,
                    4,
                    20);
        }
    }

    static {
        net.minecraft.client.gui.GuiScreenOptionsSoundsButton.packedTextFragments = new String[3];
        net.minecraft.client.gui.GuiScreenOptionsSoundsButton.packedTextFragments[0] = new String("");
    }

    public void playPressSound(SoundHandler soundHandler) {}

    public boolean mousePressed(MinecraftClient minecraftClient, int i, int j) {
        if (super.mousePressed(minecraftClient, i, j)) {
            this.rj = (float) (i - (this.xPosition + 4)) / (this.width - 8);
            this.rj = MathHelper.clamp(this.rj, 0.0F, 1.0F);
            minecraftClient.gameSettings.setSoundLevel(this.arh, this.rj);
            minecraftClient.gameSettings.saveOptions();
            this.displayString = this.oF + ": " + this.arg.getSoundVolume(this.arh);
            this.kX = true;
            return true;
        } else {
            return false;
        }
    }

    protected int getHoverState(boolean flag) {
        return 0;
    }

    public void mouseReleased(int i, int j) {
        String[] astring = Gui.getLegacyGuiRenderMarker();
        if (this.kX) {
            {
                if (!((this.arh == SoundCategory.MASTER) && (astring == null))) {

                    GuiScreenOptionsSounds.method_74e12e7806f43704480e(this.arg)
                            .getSoundLevel(this.arh);
                }
            }

            this.arg
                    .mc
                    .getSoundHandler()
                    .playSound(
                            PositionedSoundRecord.createPositionedSoundRecord(
                                    new ResourceLocation("gui.button.press"), 1.0F));
        }

        this.kX = false;
    }

    public GuiScreenOptionsSoundsButton(
            GuiScreenOptionsSounds argValue,
            int i,
            int j,
            int k,
            SoundCategory arhValue,
            boolean flag) {
        super(i, j, k, flag ? 310 : 150, 20, packedTextFragments[0]);
        this.arg = argValue;
        this.rj = 1.0F;
        this.arh = arhValue;
        this.oF = I18n.format("soundCategory." + arhValue.getCategoryName(), new Object[0]);
        this.displayString = this.oF + ": " + argValue.getSoundVolume(arhValue);
        this.rj =
                GuiScreenOptionsSounds.method_74e12e7806f43704480e(argValue)
                        .getSoundLevel(arhValue);
    }
}
