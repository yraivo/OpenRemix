package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettingsOptions;
import net.minecraft.util.MathHelper;

public class GuiOptionSlider extends GuiButton {
    private static String[] packedTextFragments;
    private GameSettingsOptions options;
    private float sb;
    private float sliderValue = 1.0F;
    private float ajn;
    public boolean dragging;

    public boolean mousePressed(MinecraftClient minecraftClient, int i, int j) {
        if (super.mousePressed(minecraftClient, i, j)) {
            this.sliderValue = (float) (i - (this.xPosition + 4)) / (this.width - 8);
            this.sliderValue = MathHelper.clamp(this.sliderValue, 0.0F, 1.0F);
            minecraftClient.gameSettings.setOptionFloatValue(
                    this.options, this.options.denormalizeValue(this.sliderValue));
            this.displayString = minecraftClient.gameSettings.getKeyBinding(this.options);
            this.dragging = true;
            return true;
        } else {
            return false;
        }
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    protected int getHoverState(boolean flag) {
        return 0;
    }

    // region String decoding support

    // endregion

    public void mouseReleased(int i, int j) {
        this.dragging = false;
    }

    public GuiOptionSlider(
            int i, int j, int k, GameSettingsOptions ardValue, float f, float ajnValue) {
        super(i, j, k, 150, 20, packedTextFragments[0]);
        this.options = ardValue;
        this.sb = f;
        this.ajn = ajnValue;
        MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
        this.sliderValue =
                ardValue.normalizeValue(
                        minecraftClient.gameSettings.getOptionFloatValue(ardValue));
        this.displayString = minecraftClient.gameSettings.getKeyBinding(ardValue);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.gui.GuiOptionSlider.packedTextFragments[0] = new String("");
    }

    // endregion

    protected void mouseDragged(MinecraftClient minecraftClient, int i, int j) {
        if (this.visible) {
            if (this.dragging) {
                this.sliderValue = (float) (i - (this.xPosition + 4)) / (this.width - 8);
                this.sliderValue = MathHelper.clamp(this.sliderValue, 0.0F, 1.0F);
                float f = this.options.denormalizeValue(this.sliderValue);
                minecraftClient.gameSettings.setOptionFloatValue(this.options, f);
                this.sliderValue = this.options.normalizeValue(f);
                this.displayString = minecraftClient.gameSettings.getKeyBinding(this.options);
            }

            minecraftClient.getTextureManager().bindTexture(net.minecraft.client.gui.GuiButton.buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(
                    this.xPosition + (int) (this.sliderValue * (this.width - 8)),
                    this.yPosition,
                    0,
                    66,
                    4,
                    20);
            this.drawTexturedModalRect(
                    this.xPosition + (int) (this.sliderValue * (this.width - 8)) + 4,
                    this.yPosition,
                    196,
                    66,
                    4,
                    20);
        }
    }

    public GuiOptionSlider(int i, int j, int k, GameSettingsOptions gameSettingsOptions) {
        this(i, j, k, gameSettingsOptions, 0.0F, 1.0F);
    }
}
