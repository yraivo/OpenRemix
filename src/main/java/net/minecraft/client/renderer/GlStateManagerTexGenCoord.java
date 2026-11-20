package net.minecraft.client.renderer;

class GlStateManagerTexGenCoord {
    public int field_179065_b;
    public int field_179066_c = -1;
    public GlStateManagerBooleanState field_179067_a;

    public GlStateManagerTexGenCoord(int i, int j) {
        this.field_179065_b = i;
        this.field_179067_a = new GlStateManagerBooleanState(j);
    }
}
