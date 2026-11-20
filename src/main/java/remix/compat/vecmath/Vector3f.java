package remix.compat.vecmath;

import java.io.Serializable;

public class Vector3f extends Tuple3f implements Serializable {
    static final long serialVersionUID = -7031930069184524614L;

    public final float dot(Vector3f p171_0) {
        return this.x * p171_0.x + this.y * p171_0.y + this.z * p171_0.z;
    }

    public final float angle(Vector3f p171_0) {
        double d0 = this.dot(p171_0) / (this.length() * p171_0.length());
        if (d0 < -1.0) {
            d0 = -1.0;
        }

        if (d0 > 1.0) {
            d0 = 1.0;
        }

        return (float) java.lang.Math.acos(d0);
    }

    public Vector3f(Vector3d vector3d) {
        super(vector3d);
    }

    public final float length() {
        return (float)
                java.lang.Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public final float lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public Vector3f(float[] afloat) {
        super(afloat);
    }

    public Vector3f() {}

    public Vector3f(Tuple3f tuple3f) {
        super(tuple3f);
    }

    public Vector3f(float p171_0, float p171_1, float p171_2) {
        super(p171_0, p171_1, p171_2);
    }

    public final void normalize(Vector3f p171_0) {
        float f =
                (float)
                        (1.0
                                / java.lang.Math.sqrt(
                                        p171_0.x * p171_0.x
                                                + p171_0.y * p171_0.y
                                                + p171_0.z * p171_0.z));
        this.x = p171_0.x * f;
        this.y = p171_0.y * f;
        this.z = p171_0.z * f;
    }

    public Vector3f(Tuple3d tuple3d) {
        super(tuple3d);
    }

    public final void normalize() {
        float f =
                (float)
                        (1.0
                                / java.lang.Math.sqrt(
                                        this.x * this.x + this.y * this.y + this.z * this.z));
        this.x *= f;
        this.y *= f;
        this.z *= f;
    }

    public final void cross(
            Vector3f p171_0, Vector3f p171_1) {
        float f = p171_0.y * p171_1.z - p171_0.z * p171_1.y;
        float f1 = p171_1.x * p171_0.z - p171_1.z * p171_0.x;
        this.z = p171_0.x * p171_1.y - p171_0.y * p171_1.x;
        this.x = f;
        this.y = f1;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Vector3f(Vector3f vector3f) {
        super(vector3f);
    }
}
