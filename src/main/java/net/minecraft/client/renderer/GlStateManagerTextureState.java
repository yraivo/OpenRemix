package net.minecraft.client.renderer;

class GlStateManagerTextureState {
    public GlStateManagerBooleanState texture2DState = new GlStateManagerBooleanState(3553);
    public int textureName = 0;

    private GlStateManagerTextureState() {}

    GlStateManagerTextureState(GlStateManagerAccessMarker glStateManagerSwitchTexGen) {
        this();
    }
}
