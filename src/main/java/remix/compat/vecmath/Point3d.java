package remix.compat.vecmath;

import java.io.Serializable;

public class Point3d extends Tuple3d implements Serializable {
    static final long serialVersionUID = 5718062286069042927L;

    public final double distanceL1(Point3d p171_0) {
        return java.lang.Math.abs(this.x - p171_0.x)
                + java.lang.Math.abs(this.y - p171_0.y)
                + java.lang.Math.abs(this.z - p171_0.z);
    }

    public Point3d(Tuple3d tuple3d) {
        super(tuple3d);
    }

    public Point3d(double p171_0, double p171_1, double p171_2) {
        super(p171_0, p171_1, p171_2);
    }

    public final void project(Point4d p171_0) {
        double d0 = 1.0 / p171_0.w;
        this.x = p171_0.x * d0;
        this.y = p171_0.y * d0;
        this.z = p171_0.z * d0;
    }

    public final double distanceLinf(Point3d p171_0) {
        double d0 =
                java.lang.Math.max(
                        java.lang.Math.abs(this.x - p171_0.x),
                        java.lang.Math.abs(this.y - p171_0.y));
        return java.lang.Math.max(d0, java.lang.Math.abs(this.z - p171_0.z));
    }

    public Point3d(double[] adouble) {
        super(adouble);
    }

    public Point3d(Tuple3f tuple3f) {
        super(tuple3f);
    }

    public final double distance(Point3d p171_0) {
        double d0 = this.x - p171_0.x;
        double d1 = this.y - p171_0.y;
        double d2 = this.z - p171_0.z;
        return java.lang.Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public Point3d() {}

    public final double distanceSquared(Point3d p171_0) {
        double d0 = this.x - p171_0.x;
        double d1 = this.y - p171_0.y;
        double d2 = this.z - p171_0.z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public Point3d(Point3f point3f) {
        super(point3f);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Point3d(Point3d point3d) {
        super(point3d);
    }
}
