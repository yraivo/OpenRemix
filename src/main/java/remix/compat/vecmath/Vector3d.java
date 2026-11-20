package remix.compat.vecmath;

import java.io.Serializable;

public class Vector3d extends Tuple3d implements Serializable {
    static final long serialVersionUID = 3761969948420550442L;

    public final double length() {
        return java.lang.Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public final void normalize() {
        double d0 =
                1.0
                        / java.lang.Math.sqrt(
                                this.x * this.x + this.y * this.y + this.z * this.z);
        this.x *= d0;
        this.y *= d0;
        this.z *= d0;
    }

    public final void cross(
            Vector3d p171_0, Vector3d p171_1) {
        double d0 = p171_0.y * p171_1.z - p171_0.z * p171_1.y;
        double d1 = p171_1.x * p171_0.z - p171_1.z * p171_0.x;
        this.z = p171_0.x * p171_1.y - p171_0.y * p171_1.x;
        this.x = d0;
        this.y = d1;
    }

    public Vector3d() {}

    public final double dot(Vector3d p171_0) {
        return this.x * p171_0.x + this.y * p171_0.y + this.z * p171_0.z;
    }

    public Vector3d(Tuple3f tuple3f) {
        super(tuple3f);
    }

    public Vector3d(double p171_0, double p171_1, double p171_2) {
        super(p171_0, p171_1, p171_2);
    }

    public Vector3d(double[] adouble) {
        super(adouble);
    }

    public Vector3d(Tuple3d tuple3d) {
        super(tuple3d);
    }

    public final void normalize(Vector3d p171_0) {
        double d0 =
                1.0
                        / java.lang.Math.sqrt(
                                p171_0.x * p171_0.x
                                        + p171_0.y * p171_0.y
                                        + p171_0.z * p171_0.z);
        this.x = p171_0.x * d0;
        this.y = p171_0.y * d0;
        this.z = p171_0.z * d0;
    }

    public final double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public Vector3d(Vector3f vector3f) {
        super(vector3f);
    }

    public final double angle(Vector3d p171_0) {
        double d0 = this.dot(p171_0) / (this.length() * p171_0.length());
        if (d0 < -1.0) {
            d0 = -1.0;
        }

        if (d0 > 1.0) {
            d0 = 1.0;
        }

        return java.lang.Math.acos(d0);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Vector3d(Vector3d vector3d) {
        super(vector3d);
    }
}
