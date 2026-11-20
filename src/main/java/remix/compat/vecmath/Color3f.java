package remix.compat.vecmath;

import java.awt.Color;
import java.io.Serializable;

public class Color3f extends Tuple3f implements Serializable {
    static final long serialVersionUID = -1861792981817493659L;

    public final void set(Color color) {
        this.x = color.getRed() / 255.0F;
        this.y = color.getGreen() / 255.0F;
        this.z = color.getBlue() / 255.0F;
    }

    public Color3f() {}

    public Color3f(Color color) {
        super(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
    }

    public Color3f(float[] afloat) {
        super(afloat);
    }

    public Color3f(float p171_0, float p171_1, float p171_2) {
        super(p171_0, p171_1, p171_2);
    }

    public final Color get() {
        int i = java.lang.Math.round(this.x * 255.0F);
        int j = java.lang.Math.round(this.y * 255.0F);
        int k = java.lang.Math.round(this.z * 255.0F);
        return new java.awt.Color(i, j, k);
    }

    public Color3f(Tuple3d tuple3d) {
        super(tuple3d);
    }

    public Color3f(Tuple3f tuple3f) {
        super(tuple3f);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Color3f(Color3f color3f) {
        super(color3f);
    }
}
