package net.minecraft.client.renderer;

class GlStateManagerColor {
    public float alpha;
    public float green;
    public float red = 1.0F;
    public float blue;

    public GlStateManagerColor() {
        this.green = 1.0F;
        this.blue = 1.0F;
        this.alpha = 1.0F;
    }

    public GlStateManagerColor(
            float redValue, float greenValue, float blueValue, float alphaValue) {
        this.green = 1.0F;
        this.blue = 1.0F;
        this.alpha = 1.0F;
        this.red = redValue;
        this.green = greenValue;
        this.blue = blueValue;
        this.alpha = alphaValue;
    }
}
