package net.minecraft.client.renderer;

class GlStateManagerColorMask {
    public boolean green;
    public boolean alpha;
    public boolean red = true;
    public boolean blue;

    GlStateManagerColorMask(GlStateManagerAccessMarker glStateManagerSwitchTexGen) {
        this();
    }

    private GlStateManagerColorMask() {
        this.green = true;
        this.blue = true;
        this.alpha = true;
    }
}
