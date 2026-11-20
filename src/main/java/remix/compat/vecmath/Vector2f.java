package remix.compat.vecmath;

import java.io.Serializable;

public class Vector2f extends Tuple2f implements Serializable {
    static final long serialVersionUID = -2168194326883512320L;

    public final float dot(Vector2f p171_0) {
        return this.x * p171_0.x + this.y * p171_0.y;
    }

    public final float length() {
        return (float) java.lang.Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public final float angle(Vector2f p171_0) {
        double d0 = this.dot(p171_0) / (this.length() * p171_0.length());
        if (d0 < -1.0) {
            d0 = -1.0;
        }

        if (d0 > 1.0) {
            d0 = 1.0;
        }

        return (float) java.lang.Math.acos(d0);
    }

    public Vector2f(Tuple2f tuple2f) {
        super(tuple2f);
    }

    public Vector2f() {}

    public final void normalize() {
        float f = (float) (1.0 / java.lang.Math.sqrt(this.x * this.x + this.y * this.y));
        this.x *= f;
        this.y *= f;
    }

    public Vector2f(Vector2d vector2d) {
        super(vector2d);
    }

    public Vector2f(Tuple2d tuple2d) {
        super(tuple2d);
    }

    public Vector2f(float[] afloat) {
        super(afloat);
    }

    public final void normalize(Vector2f p171_0) {
        float f =
                (float) (1.0 / java.lang.Math.sqrt(p171_0.x * p171_0.x + p171_0.y * p171_0.y));
        this.x = p171_0.x * f;
        this.y = p171_0.y * f;
    }

    public Vector2f(float p171_0, float p171_1) {
        super(p171_0, p171_1);
    }

    public final float lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Vector2f(Vector2f vector2f) {
        super(vector2f);
    }
}
