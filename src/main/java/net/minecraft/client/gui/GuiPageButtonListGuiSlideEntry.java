package net.minecraft.client.gui;

public class GuiPageButtonListGuiSlideEntry extends GuiPageButtonListGuiListEntry {
    private float ia;
    private float vw;
    private float hY;
    private GuiSliderFormatHelper asC;

    public GuiPageButtonListGuiSlideEntry(
            int i,
            String text,
            boolean flag,
            GuiSliderFormatHelper asCValue,
            float f,
            float f1,
            float f2) {
        super(i, text, flag);
        this.asC = asCValue;
        this.hY = f;
        this.ia = f1;
        this.vw = f2;
    }

    public float aM() {
        return this.ia;
    }

    public float aN() {
        return this.hY;
    }

    public float aJ() {
        return this.vw;
    }

    public GuiSliderFormatHelper vl() {
        return this.asC;
    }
}
