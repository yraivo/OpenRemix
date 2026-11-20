package net.minecraft.client.renderer;

class GlStateManagerBlendState {
    public int srcFactorAlpha;
    public int dstFactor;
    public int srcFactor;
    public GlStateManagerBooleanState field_179213_a = new GlStateManagerBooleanState(3042);
    public int dstFactorAlpha;

    private GlStateManagerBlendState() {
        this.srcFactor = 1;
        this.dstFactor = 0;
        this.srcFactorAlpha = 1;
        this.dstFactorAlpha = 0;
    }

    GlStateManagerBlendState(GlStateManagerAccessMarker glStateManagerSwitchTexGen) {
        this();
    }
}
