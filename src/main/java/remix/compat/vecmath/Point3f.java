package remix.compat.vecmath;

import java.io.Serializable;

public class Point3f extends Tuple3f implements Serializable {
    static final long serialVersionUID = -8689337816398030143L;

    public Point3f(float p171_0, float p171_1, float p171_2) {
        super(p171_0, p171_1, p171_2);
    }

    public Point3f(float[] afloat) {
        super(afloat);
    }

    public Point3f(Point3d point3d) {
        super(point3d);
    }

    public final float distanceSquared(Point3f p171_0) {
        float f = this.x - p171_0.x;
        float f1 = this.y - p171_0.y;
        float f2 = this.z - p171_0.z;
        return f * f + f1 * f1 + f2 * f2;
    }

    public Point3f() {}

    public Point3f(Tuple3d tuple3d) {
        super(tuple3d);
    }

    public final float distanceL1(Point3f p171_0) {
        return java.lang.Math.abs(this.x - p171_0.x)
                + java.lang.Math.abs(this.y - p171_0.y)
                + java.lang.Math.abs(this.z - p171_0.z);
    }

    public final float distanceLinf(Point3f p171_0) {
        float f =
                java.lang.Math.max(
                        java.lang.Math.abs(this.x - p171_0.x),
                        java.lang.Math.abs(this.y - p171_0.y));
        return java.lang.Math.max(f, java.lang.Math.abs(this.z - p171_0.z));
    }

    public final float distance(Point3f p171_0) {
        float f = this.x - p171_0.x;
        float f1 = this.y - p171_0.y;
        float f2 = this.z - p171_0.z;
        return (float) java.lang.Math.sqrt(f * f + f1 * f1 + f2 * f2);
    }

    public final void project(Point4f p171_0) {
        float f = 1.0F / p171_0.w;
        this.x = p171_0.x * f;
        this.y = p171_0.y * f;
        this.z = p171_0.z * f;
    }

    public Point3f(Tuple3f tuple3f) {
        super(tuple3f);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Point3f(Point3f point3f) {
        super(point3f);
    }
}
