package net.minecraft.util;

public class MouseFilter {
    private float gw;
    private float hW;
    private float hX;

    public void reset() {
        this.gw = 0.0F;
        this.hX = 0.0F;
        this.hW = 0.0F;
    }

    public float smooth(float p171_0, float p171_1) {
        this.gw += p171_0;
        p171_0 = (this.gw - this.hX) * p171_1;
        this.hW = this.hW + (p171_0 - this.hW) * 0.5F;
        if (p171_0 > 0.0F && p171_0 > this.hW || p171_0 < 0.0F && p171_0 < this.hW) {
            p171_0 = this.hW;
        }

        this.hX += p171_0;
        return p171_0;
    }
}
