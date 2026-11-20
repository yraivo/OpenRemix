package remix.compat.vecmath;

import java.io.Serializable;

public class Point2d extends Tuple2d implements Serializable {
    static final long serialVersionUID = 1133748791492571954L;

    public Point2d(double p171_0, double p171_1) {
        super(p171_0, p171_1);
    }

    public final double distance(Point2d p171_0) {
        double d0 = this.x - p171_0.x;
        double d1 = this.y - p171_0.y;
        return java.lang.Math.sqrt(d0 * d0 + d1 * d1);
    }

    public Point2d(Tuple2f tuple2f) {
        super(tuple2f);
    }

    public final double distanceSquared(Point2d p171_0) {
        double d0 = this.x - p171_0.x;
        double d1 = this.y - p171_0.y;
        return d0 * d0 + d1 * d1;
    }

    public final double distanceL1(Point2d p171_0) {
        return java.lang.Math.abs(this.x - p171_0.x) + java.lang.Math.abs(this.y - p171_0.y);
    }

    public Point2d(double[] adouble) {
        super(adouble);
    }

    public Point2d(Tuple2d tuple2d) {
        super(tuple2d);
    }

    public Point2d() {}

    public Point2d(Point2f point2f) {
        super(point2f);
    }

    public final double distanceLinf(Point2d p171_0) {
        return java.lang.Math.max(
                java.lang.Math.abs(this.x - p171_0.x), java.lang.Math.abs(this.y - p171_0.y));
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Point2d(Point2d point2d) {
        super(point2d);
    }
}
