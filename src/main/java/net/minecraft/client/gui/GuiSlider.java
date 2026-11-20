package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiSlider extends GuiButton {
    private static String[] packedTextFragments;
    private GuiSliderFormatHelper formatHelper;
    public boolean isMouseDown;
    private float min;
    private float max;
    private GuiPageButtonListGuiResponder responder;
    private float sliderPosition = 1.0F;
    private String name;
    private static String jU;

    public void method_f244da1c65e8123ad041(float sliderPositionValue) {
        this.sliderPosition = sliderPositionValue;
        this.displayString = this.getDisplayString();
        this.responder.onTick(this.id, this.aK());
    }

    static {
        net.minecraft.client.gui.GuiSlider.packedTextFragments = new String[2];
        net.minecraft.client.gui.GuiSlider.packedTextFragments[0] = new String("");
        net.minecraft.client.gui.GuiSlider.packedTextFragments[1] = new String(")S");
        jU = ": ";
    }

    private String getDisplayString() {
        return this.formatHelper == null
                ? I18n.format(this.name, new Object[0]) + jU + this.aK()
                : this.formatHelper.getText(
                        this.id, I18n.format(this.name, new Object[0]), this.aK());
    }

    @Override
    public void mouseReleased(int i, int j) {
        this.isMouseDown = false;
    }

    // region String decoding support

    // endregion

    @Override
    protected int getHoverState(boolean flag) {
        return 0;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public float aK() {
        return this.min + (this.max - this.min) * this.sliderPosition;
    }

    public void method_b692b0816d225bcf968a(float f, boolean flag) {
        this.sliderPosition = (f - this.min) / (this.max - this.min);
        this.displayString = this.getDisplayString();
        if (flag) {
            this.responder.onTick(this.id, this.aK());
        }
    }

    @Override
    protected void mouseDragged(MinecraftClient minecraftClient, int i, int j) {
        if (this.visible) {
            if (this.isMouseDown) {
                this.sliderPosition = (float) (i - (this.xPosition + 4)) / (this.width - 8);
                if (this.sliderPosition < 0.0F) {
                    this.sliderPosition = 0.0F;
                }

                if (this.sliderPosition > 1.0F) {
                    this.sliderPosition = 1.0F;
                }

                this.displayString = this.getDisplayString();
                this.responder.onTick(this.id, this.aK());
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(
                    this.xPosition + (int) (this.sliderPosition * (this.width - 8)),
                    this.yPosition,
                    0,
                    66,
                    4,
                    20);
            this.drawTexturedModalRect(
                    this.xPosition + (int) (this.sliderPosition * (this.width - 8)) + 4,
                    this.yPosition,
                    196,
                    66,
                    4,
                    20);
        }
    }

    @Override
    public boolean mousePressed(MinecraftClient minecraftClient, int i, int j) {
        if (super.mousePressed(minecraftClient, i, j)) {
            this.sliderPosition = (float) (i - (this.xPosition + 4)) / (this.width - 8);
            if (this.sliderPosition < 0.0F) {
                this.sliderPosition = 0.0F;
            }

            if (this.sliderPosition > 1.0F) {
                this.sliderPosition = 1.0F;
            }

            this.displayString = this.getDisplayString();
            this.responder.onTick(this.id, this.aK());
            this.isMouseDown = true;
            return true;
        } else {
            return false;
        }
    }

    public float getSliderPosition() {
        return this.sliderPosition;
    }

    public GuiSlider(
            GuiPageButtonListGuiResponder aoDValue,
            int i,
            int j,
            int k,
            String text,
            float minValue,
            float ajnValue,
            float f2,
            GuiSliderFormatHelper aoCValue) {
        super(i, j, k, 150, 20, packedTextFragments[0]);
        this.name = text;
        this.min = minValue;
        this.max = ajnValue;
        this.sliderPosition = (f2 - minValue) / (ajnValue - minValue);
        this.formatHelper = aoCValue;
        this.responder = aoDValue;
        this.displayString = this.getDisplayString();
    }
}
