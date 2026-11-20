package remix.compat.vecmath;

import java.awt.Color;
import java.io.Serializable;

public class Color4f extends Tuple4f implements Serializable {
    static final long serialVersionUID = 8577680141580006740L;

    public Color4f(Color color) {
        super(
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                color.getAlpha() / 255.0F);
    }

    public Color4f(Tuple4d tuple4d) {
        super(tuple4d);
    }

    public final Color get() {
        int i = java.lang.Math.round(this.x * 255.0F);
        int j = java.lang.Math.round(this.y * 255.0F);
        int k = java.lang.Math.round(this.z * 255.0F);
        int l = java.lang.Math.round(this.w * 255.0F);
        return new java.awt.Color(i, j, k, l);
    }

    public Color4f() {}

    public Color4f(float[] afloat) {
        super(afloat);
    }

    public Color4f(float p171_0, float p171_1, float p171_2, float p171_3) {
        super(p171_0, p171_1, p171_2, p171_3);
    }

    public Color4f(Tuple4f tuple4f) {
        super(tuple4f);
    }

    public final void set(Color color) {
        this.x = color.getRed() / 255.0F;
        this.y = color.getGreen() / 255.0F;
        this.z = color.getBlue() / 255.0F;
        this.w = color.getAlpha() / 255.0F;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Color4f(Color4f color4f) {
        super(color4f);
    }
}
