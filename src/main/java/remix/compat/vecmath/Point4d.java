package remix.compat.vecmath;

import java.io.Serializable;

public class Point4d extends Tuple4d implements Serializable {
    static final long serialVersionUID = 1733471895962736949L;

    public Point4d(Tuple4d tuple4d) {
        super(tuple4d);
    }

    public final double distanceL1(Point4d p171_0) {
        return java.lang.Math.abs(this.x - p171_0.x)
                + java.lang.Math.abs(this.y - p171_0.y)
                + java.lang.Math.abs(this.z - p171_0.z)
                + java.lang.Math.abs(this.w - p171_0.w);
    }

    public final double distanceLinf(Point4d p171_0) {
        double d0 =
                java.lang.Math.max(
                        java.lang.Math.abs(this.x - p171_0.x),
                        java.lang.Math.abs(this.y - p171_0.y));
        double d1 =
                java.lang.Math.max(
                        java.lang.Math.abs(this.z - p171_0.z),
                        java.lang.Math.abs(this.w - p171_0.w));
        return java.lang.Math.max(d0, d1);
    }

    public final double distance(Point4d p171_0) {
        double d0 = this.x - p171_0.x;
        double d1 = this.y - p171_0.y;
        double d2 = this.z - p171_0.z;
        double d3 = this.w - p171_0.w;
        return java.lang.Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2 + d3 * d3);
    }

    public Point4d(double[] adouble) {
        super(adouble);
    }

    public Point4d() {}

    public final void set(Tuple3d p171_0) {
        this.x = p171_0.x;
        this.y = p171_0.y;
        this.z = p171_0.z;
        this.w = 1.0;
    }

    public Point4d(double p171_0, double p171_1, double p171_2, double p171_3) {
        super(p171_0, p171_1, p171_2, p171_3);
    }

    public Point4d(Point4f point4f) {
        super(point4f);
    }

    public Point4d(Tuple3d p171_0) {
        super(p171_0.x, p171_0.y, p171_0.z, 1.0);
    }

    public final void project(Point4d p171_0) {
        double d0 = 1.0 / p171_0.w;
        this.x = p171_0.x * d0;
        this.y = p171_0.y * d0;
        this.z = p171_0.z * d0;
        this.w = 1.0;
    }

    public Point4d(Tuple4f tuple4f) {
        super(tuple4f);
    }

    public final double distanceSquared(Point4d p171_0) {
        double d0 = this.x - p171_0.x;
        double d1 = this.y - p171_0.y;
        double d2 = this.z - p171_0.z;
        double d3 = this.w - p171_0.w;
        return d0 * d0 + d1 * d1 + d2 * d2 + d3 * d3;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Point4d(Point4d point4d) {
        super(point4d);
    }
}
