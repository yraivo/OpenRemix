package net.minecraft.client.renderer;

class GlStateManagerFogState {
    public float field_179048_c;
    public float field_179046_e;
    public GlStateManagerBooleanState field_179049_a = new GlStateManagerBooleanState(2912);
    public int field_179047_b = 2048;
    public float field_179045_d;

    private GlStateManagerFogState() {
        this.field_179048_c = 1.0F;
        this.field_179045_d = 0.0F;
        this.field_179046_e = 1.0F;
    }

    GlStateManagerFogState(GlStateManagerAccessMarker glStateManagerSwitchTexGen) {
        this();
    }
}
