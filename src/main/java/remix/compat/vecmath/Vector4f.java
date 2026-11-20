package remix.compat.vecmath;

import java.io.Serializable;

public class Vector4f extends Tuple4f implements Serializable {
    static final long serialVersionUID = 8749319902347760659L;

    public Vector4f(Tuple4d tuple4d) {
        super(tuple4d);
    }

    public Vector4f(Tuple3f p171_0) {
        super(p171_0.x, p171_0.y, p171_0.z, 0.0F);
    }

    public final void normalize(Vector4f p171_0) {
        float f =
                (float)
                        (1.0
                                / java.lang.Math.sqrt(
                                        p171_0.x * p171_0.x
                                                + p171_0.y * p171_0.y
                                                + p171_0.z * p171_0.z
                                                + p171_0.w * p171_0.w));
        this.x = p171_0.x * f;
        this.y = p171_0.y * f;
        this.z = p171_0.z * f;
        this.w = p171_0.w * f;
    }

    public final float length() {
        return (float)
                java.lang.Math.sqrt(
                        this.x * this.x
                                + this.y * this.y
                                + this.z * this.z
                                + this.w * this.w);
    }

    public Vector4f(Vector4d vector4d) {
        super(vector4d);
    }

    public final float dot(Vector4f p171_0) {
        return this.x * p171_0.x
                + this.y * p171_0.y
                + this.z * p171_0.z
                + this.w * p171_0.w;
    }

    public Vector4f(float[] afloat) {
        super(afloat);
    }

    public Vector4f(Tuple4f tuple4f) {
        super(tuple4f);
    }

    public Vector4f(float p171_0, float p171_1, float p171_2, float p171_3) {
        super(p171_0, p171_1, p171_2, p171_3);
    }

    public Vector4f() {}

    public final void set(Tuple3f p171_0) {
        this.x = p171_0.x;
        this.y = p171_0.y;
        this.z = p171_0.z;
        this.w = 0.0F;
    }

    public final float angle(Vector4f p171_0) {
        double d0 = this.dot(p171_0) / (this.length() * p171_0.length());
        if (d0 < -1.0) {
            d0 = -1.0;
        }

        if (d0 > 1.0) {
            d0 = 1.0;
        }

        return (float) java.lang.Math.acos(d0);
    }

    public final float lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    public final void normalize() {
        float f =
                (float)
                        (1.0
                                / java.lang.Math.sqrt(
                                        this.x * this.x
                                                + this.y * this.y
                                                + this.z * this.z
                                                + this.w * this.w));
        this.x *= f;
        this.y *= f;
        this.z *= f;
        this.w *= f;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Vector4f(Vector4f vector4f) {
        super(vector4f);
    }
}
