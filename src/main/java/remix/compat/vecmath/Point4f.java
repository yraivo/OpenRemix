package remix.compat.vecmath;

import java.io.Serializable;

public class Point4f extends Tuple4f implements Serializable {
    static final long serialVersionUID = 4643134103185764459L;

    public final float distanceLinf(Point4f p171_0) {
        float f =
                java.lang.Math.max(
                        java.lang.Math.abs(this.x - p171_0.x),
                        java.lang.Math.abs(this.y - p171_0.y));
        float f1 =
                java.lang.Math.max(
                        java.lang.Math.abs(this.z - p171_0.z),
                        java.lang.Math.abs(this.w - p171_0.w));
        return java.lang.Math.max(f, f1);
    }

    public final float distance(Point4f p171_0) {
        float f = this.x - p171_0.x;
        float f1 = this.y - p171_0.y;
        float f2 = this.z - p171_0.z;
        float f3 = this.w - p171_0.w;
        return (float) java.lang.Math.sqrt(f * f + f1 * f1 + f2 * f2 + f3 * f3);
    }

    public final float distanceSquared(Point4f p171_0) {
        float f = this.x - p171_0.x;
        float f1 = this.y - p171_0.y;
        float f2 = this.z - p171_0.z;
        float f3 = this.w - p171_0.w;
        return f * f + f1 * f1 + f2 * f2 + f3 * f3;
    }

    public Point4f(Tuple3f p171_0) {
        super(p171_0.x, p171_0.y, p171_0.z, 1.0F);
    }

    public final void project(Point4f p171_0) {
        float f = 1.0F / p171_0.w;
        this.x = p171_0.x * f;
        this.y = p171_0.y * f;
        this.z = p171_0.z * f;
        this.w = 1.0F;
    }

    public Point4f() {}

    public Point4f(Point4d point4d) {
        super(point4d);
    }

    public Point4f(float p171_0, float p171_1, float p171_2, float p171_3) {
        super(p171_0, p171_1, p171_2, p171_3);
    }

    public Point4f(Tuple4d tuple4d) {
        super(tuple4d);
    }

    public final void set(Tuple3f p171_0) {
        this.x = p171_0.x;
        this.y = p171_0.y;
        this.z = p171_0.z;
        this.w = 1.0F;
    }

    public final float distanceL1(Point4f p171_0) {
        return java.lang.Math.abs(this.x - p171_0.x)
                + java.lang.Math.abs(this.y - p171_0.y)
                + java.lang.Math.abs(this.z - p171_0.z)
                + java.lang.Math.abs(this.w - p171_0.w);
    }

    public Point4f(float[] afloat) {
        super(afloat);
    }

    public Point4f(Tuple4f tuple4f) {
        super(tuple4f);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Point4f(Point4f point4f) {
        super(point4f);
    }
}
