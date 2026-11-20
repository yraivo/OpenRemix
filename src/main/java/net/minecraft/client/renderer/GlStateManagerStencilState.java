package net.minecraft.client.renderer;

class GlStateManagerStencilState {
    public GlStateManagerStencilFunc field_179078_a = new GlStateManagerStencilFunc(null);
    public int field_179074_d;
    public int field_179076_b = -1;
    public int field_179075_e;
    public int field_179077_c = 7680;

    private GlStateManagerStencilState() {
        this.field_179074_d = 7680;
        this.field_179075_e = 7680;
    }

    GlStateManagerStencilState(GlStateManagerAccessMarker glStateManagerSwitchTexGen) {
        this();
    }
}
