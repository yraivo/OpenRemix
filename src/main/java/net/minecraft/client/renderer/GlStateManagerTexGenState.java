package net.minecraft.client.renderer;

class GlStateManagerTexGenState {
    public GlStateManagerTexGenCoord field_179063_c;
    public GlStateManagerTexGenCoord field_179064_a = new GlStateManagerTexGenCoord(8192, 3168);
    public GlStateManagerTexGenCoord field_179061_d;
    public GlStateManagerTexGenCoord field_179062_b = new GlStateManagerTexGenCoord(8193, 3169);

    private GlStateManagerTexGenState() {
        this.field_179063_c = new GlStateManagerTexGenCoord(8194, 3170);
        this.field_179061_d = new GlStateManagerTexGenCoord(8195, 3171);
    }

    GlStateManagerTexGenState(GlStateManagerAccessMarker glStateManagerSwitchTexGen) {
        this();
    }
}
