package net.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.MathHelper;

public class ScaledResolution {
    private int scaleFactor;
    private double widthDouble;
    private double heightDouble;
    private int width;
    private int height;

    public double getWidthDouble() {
        return this.widthDouble;
    }

    public int getScaleFactor() {
        return this.scaleFactor;
    }

    public ScaledResolution(MinecraftClient minecraftClient, int widthValue, int j) {
        this.width = widthValue;
        this.height = j;
        this.scaleFactor = 1;
        boolean unicode = minecraftClient.isUnicode();
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        int k = minecraftClient.gameSettings.guiScale;
        String[] astring = astring1;
        if (k == 0) {
            k = 1000;
        }

        while (this.scaleFactor < k
                && this.width / (this.scaleFactor + 1) >= 320
                && this.height / (this.scaleFactor + 1) >= 240) {
            this.scaleFactor++;
            if (astring != null) {
                break;
            }
        }

        if (unicode && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
            this.scaleFactor--;
        }

        this.widthDouble = (double) this.width / this.scaleFactor;
        this.heightDouble = (double) this.height / this.scaleFactor;
        this.width = MathHelper.ceil(this.widthDouble);
        this.height = MathHelper.ceil(this.heightDouble);
    }

    public int getWidth() {
        return this.width;
    }

    public double getHeightDouble() {
        return this.heightDouble;
    }

    public int getHeight() {
        return this.height;
    }
}
