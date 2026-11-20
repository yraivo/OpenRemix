package remix.compat.vecmath;

import java.io.Serializable;

public class Vector2d extends Tuple2d implements Serializable {
    static final long serialVersionUID = 8572646365302599857L;

    public final double dot(Vector2d p171_0) {
        return this.x * p171_0.x + this.y * p171_0.y;
    }

    public Vector2d(Vector2f vector2f) {
        super(vector2f);
    }

    public Vector2d(Tuple2f tuple2f) {
        super(tuple2f);
    }

    public final void normalize(Vector2d p171_0) {
        double d0 = 1.0 / java.lang.Math.sqrt(p171_0.x * p171_0.x + p171_0.y * p171_0.y);
        this.x = p171_0.x * d0;
        this.y = p171_0.y * d0;
    }

    public Vector2d(double[] adouble) {
        super(adouble);
    }

    public final double length() {
        return java.lang.Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public final double angle(Vector2d p171_0) {
        double d0 = this.dot(p171_0) / (this.length() * p171_0.length());
        if (d0 < -1.0) {
            d0 = -1.0;
        }

        if (d0 > 1.0) {
            d0 = 1.0;
        }

        return java.lang.Math.acos(d0);
    }

    public Vector2d() {}

    public Vector2d(Tuple2d tuple2d) {
        super(tuple2d);
    }

    public final double lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public final void normalize() {
        double d0 = 1.0 / java.lang.Math.sqrt(this.x * this.x + this.y * this.y);
        this.x *= d0;
        this.y *= d0;
    }

    public Vector2d(double p171_0, double p171_1) {
        super(p171_0, p171_1);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Vector2d(Vector2d vector2d) {
        super(vector2d);
    }
}
