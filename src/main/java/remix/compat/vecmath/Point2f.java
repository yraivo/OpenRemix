package remix.compat.vecmath;

import java.io.Serializable;

public class Point2f extends Tuple2f implements Serializable {
    static final long serialVersionUID = -4801347926528714435L;

    public Point2f(Tuple2f tuple2f) {
        super(tuple2f);
    }

    public final float distance(Point2f p171_0) {
        float f = this.x - p171_0.x;
        float f1 = this.y - p171_0.y;
        return (float) java.lang.Math.sqrt(f * f + f1 * f1);
    }

    public Point2f(float[] afloat) {
        super(afloat);
    }

    public Point2f() {}

    public final float distanceLinf(Point2f p171_0) {
        return java.lang.Math.max(
                java.lang.Math.abs(this.x - p171_0.x), java.lang.Math.abs(this.y - p171_0.y));
    }

    public final float distanceL1(Point2f p171_0) {
        return java.lang.Math.abs(this.x - p171_0.x) + java.lang.Math.abs(this.y - p171_0.y);
    }

    public Point2f(float p171_0, float p171_1) {
        super(p171_0, p171_1);
    }

    public final float distanceSquared(Point2f p171_0) {
        float f = this.x - p171_0.x;
        float f1 = this.y - p171_0.y;
        return f * f + f1 * f1;
    }

    public Point2f(Point2d point2d) {
        super(point2d);
    }

    public Point2f(Tuple2d tuple2d) {
        super(tuple2d);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Point2f(Point2f point2f) {
        super(point2f);
    }
}
