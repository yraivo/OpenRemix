package net.minecraft.client.renderer;

class GlStateManagerPolygonOffsetState {
    public float field_179043_c;
    public GlStateManagerBooleanState field_179044_a = new GlStateManagerBooleanState(32823);
    public float field_179041_d;
    public GlStateManagerBooleanState field_179042_b = new GlStateManagerBooleanState(10754);

    private GlStateManagerPolygonOffsetState() {
        this.field_179043_c = 0.0F;
        this.field_179041_d = 0.0F;
    }

    GlStateManagerPolygonOffsetState(GlStateManagerAccessMarker glStateManagerSwitchTexGen) {
        this();
    }
}
