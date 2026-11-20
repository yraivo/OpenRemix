package remix.compat.vecmath;

import java.io.Serializable;

public class Vector4d extends Tuple4d implements Serializable {
    static final long serialVersionUID = 3938123424117448700L;

    public Vector4d() {}

    public final double angle(Vector4d p171_0) {
        double d0 = this.dot(p171_0) / (this.length() * p171_0.length());
        if (d0 < -1.0) {
            d0 = -1.0;
        }

        if (d0 > 1.0) {
            d0 = 1.0;
        }

        return java.lang.Math.acos(d0);
    }

    public final void normalize(Vector4d p171_0) {
        double d0 =
                1.0
                        / java.lang.Math.sqrt(
                                p171_0.x * p171_0.x
                                        + p171_0.y * p171_0.y
                                        + p171_0.z * p171_0.z
                                        + p171_0.w * p171_0.w);
        this.x = p171_0.x * d0;
        this.y = p171_0.y * d0;
        this.z = p171_0.z * d0;
        this.w = p171_0.w * d0;
    }

    public Vector4d(Vector4f vector4f) {
        super(vector4f);
    }

    public Vector4d(double[] adouble) {
        super(adouble);
    }

    public Vector4d(Tuple4f tuple4f) {
        super(tuple4f);
    }

    public final void normalize() {
        double d0 =
                1.0
                        / java.lang.Math.sqrt(
                                this.x * this.x
                                        + this.y * this.y
                                        + this.z * this.z
                                        + this.w * this.w);
        this.x *= d0;
        this.y *= d0;
        this.z *= d0;
        this.w *= d0;
    }

    public final double dot(Vector4d p171_0) {
        return this.x * p171_0.x
                + this.y * p171_0.y
                + this.z * p171_0.z
                + this.w * p171_0.w;
    }

    public Vector4d(Tuple3d p171_0) {
        super(p171_0.x, p171_0.y, p171_0.z, 0.0);
    }

    public Vector4d(Tuple4d tuple4d) {
        super(tuple4d);
    }

    public final double length() {
        return java.lang.Math.sqrt(
                this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
    }

    public Vector4d(double p171_0, double p171_1, double p171_2, double p171_3) {
        super(p171_0, p171_1, p171_2, p171_3);
    }

    public final void set(Tuple3d p171_0) {
        this.x = p171_0.x;
        this.y = p171_0.y;
        this.z = p171_0.z;
        this.w = 0.0;
    }

    public final double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Vector4d(Vector4d vector4d) {
        super(vector4d);
    }
}
