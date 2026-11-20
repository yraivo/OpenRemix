package net.minecraft.client.renderer;

class GlStateManagerDepthState {
    public boolean maskEnabled;
    public int depthFunc;
    public GlStateManagerBooleanState depthTest = new GlStateManagerBooleanState(2929);

    GlStateManagerDepthState(GlStateManagerAccessMarker glStateManagerSwitchTexGen) {
        this();
    }

    private GlStateManagerDepthState() {
        this.maskEnabled = true;
        this.depthFunc = 513;
    }
}
