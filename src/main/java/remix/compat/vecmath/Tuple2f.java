package remix.compat.vecmath;

import java.io.Serializable;

public abstract class Tuple2f implements Serializable, Cloneable {
    private static String[] packedTextFragments;
    public float x;
    public float y;
    static final long serialVersionUID = 9011180388985266884L;
    private static String c;

    public final void sub(Tuple2f p171_0) {
        this.x = this.x - p171_0.x;
        this.y = this.y - p171_0.y;
    }

    public final void method_8774e6cf6814b26f2a15(float p171_0, Tuple2f p171_1) {
        java.lang.String[] astring;
        {
            astring = remix.compat.vecmath.Tuple3f.hF();
            if (p171_1.x > p171_0) {
                this.x = p171_0;
                if (!(astring == null)) {

                    this.x = p171_1.x;
                }
            } else {

                this.x = p171_1.x;
            }
        }

        if (p171_1.y > p171_0) {
            this.y = p171_0;
            if (astring == null) {
                return;
            }
        }

        this.y = p171_1.y;
    }

    private static NullPointerException wrapNullPointerException(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    public final void scaleAdd(
            float p171_0, Tuple2f p171_1, Tuple2f p171_2) {
        this.x = p171_0 * p171_1.x + p171_2.x;
        this.y = p171_0 * p171_1.y + p171_2.y;
    }

    public final void clampMax(float p171_0) {
        try {
            if (this.x > p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple2f.wrapNullPointerException(
                    nullpointerexception1);
        }

        try {
            if (this.y > p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple2f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void method_0e956967f149c1560e61(float p171_0, Tuple2f p171_1) {
        java.lang.String[] astring;
        {
            astring = remix.compat.vecmath.Tuple3f.hF();
            if (p171_1.x < p171_0) {
                this.x = p171_0;
                if (!(astring == null)) {

                    this.x = p171_1.x;
                }
            } else {

                this.x = p171_1.x;
            }
        }

        if (p171_1.y < p171_0) {
            this.y = p171_0;
            if (astring == null) {
                return;
            }
        }

        this.y = p171_1.y;
    }

    public final void interpolate(Tuple2f p171_0, float p171_1) {
        this.x = (1.0F - p171_1) * this.x + p171_1 * p171_0.x;
        this.y = (1.0F - p171_1) * this.y + p171_1 * p171_0.y;
    }

    @Override
    public String toString() {
        return remix.compat.vecmath.Tuple2f.packedTextFragments[0]
                + this.x
                + remix.compat.vecmath.Tuple2f.c
                + this.y
                + remix.compat.vecmath.Tuple2f.packedTextFragments[1];
    }

    public final void add(
            Tuple2f p171_0, Tuple2f p171_1) {
        this.x = p171_0.x + p171_1.x;
        this.y = p171_0.y + p171_1.y;
    }

    public final void set(Tuple2d tuple2d) {
        this.x = (float) tuple2d.x;
        this.y = (float) tuple2d.y;
    }

    static {
        remix.compat.vecmath.Tuple2f.packedTextFragments = new String[3];
        remix.compat.vecmath.Tuple2f.packedTextFragments[0] = new String("(");
        remix.compat.vecmath.Tuple2f.packedTextFragments[1] = new String(")");
        remix.compat.vecmath.Tuple2f.packedTextFragments[2] = new String("H.");
        c = ", ";
    }

    public final void setX(float f) {
        this.x = f;
    }

    public final void scale(float p171_0) {
        this.x *= p171_0;
        this.y *= p171_0;
    }

    public Tuple2f() {
        this.x = 0.0F;
        this.y = 0.0F;
    }

    // region String decoding support

    // endregion

    public Tuple2f(Tuple2d tuple2d) {
        this.x = (float) tuple2d.x;
        this.y = (float) tuple2d.y;
    }

    public Tuple2f(float[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
    }

    public boolean equals(Tuple2f p171_0) {
        try {
            return (this.x == p171_0.x && this.y == p171_0.y);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Tuple2f tuple2f =
                    (remix.compat.vecmath.Tuple2f) p171_0;
            return (this.x == tuple2f.x
                    && this.y == tuple2f.y);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        }
    }

    // region String decoding support

    // endregion

    public final void clampMin(float p171_0) {
        try {
            if (this.x < p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple2f.wrapNullPointerException(
                    nullpointerexception1);
        }

        try {
            if (this.y < p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple2f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void set(Tuple2f p171_0) {
        this.x = p171_0.x;
        this.y = p171_0.y;
    }

    public final void scaleAdd(float p171_0, Tuple2f p171_1) {
        this.x = p171_0 * this.x + p171_1.x;
        this.y = p171_0 * this.y + p171_1.y;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }
    }

    public final void set(float p171_0, float p171_1) {
        this.x = p171_0;
        this.y = p171_1;
    }

    @Override
    public int hashCode() {

        long i = 1L;
        i = VecMathUtil.hashFloatToIntBits(i, this.x);
        i = VecMathUtil.hashFloatToIntBits(i, this.y);
        return VecMathUtil.hashFinish(i);
    }

    public final void absolute() {
        this.x = java.lang.Math.abs(this.x);
        this.y = java.lang.Math.abs(this.y);
    }

    public final void scale(float p171_0, Tuple2f p171_1) {
        this.x = p171_0 * p171_1.x;
        this.y = p171_0 * p171_1.y;
    }

    public final void method_d6ea4f28134b0b2e2c32(float p171_0, float p171_1) {
        java.lang.String[] astring;
        {
            astring = remix.compat.vecmath.Tuple3f.hF();
            if (this.x > p171_1) {
                this.x = p171_1;
                if (!(astring == null)) {

                    if (this.x < p171_0) {
                        this.x = p171_0;
                    }
                }
            } else {

                if (this.x < p171_0) {
                    this.x = p171_0;
                }
            }
        }

        if (this.y > p171_1) {
            this.y = p171_1;
            if (astring == null) {
                return;
            }
        }

        if (this.y < p171_0) {
            this.y = p171_0;
        }
    }

    // region String decoding support

    // endregion

    public final float getY() {
        return this.y;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    // region String decoding support

    // endregion

    public final void interpolate(
            Tuple2f p171_0, Tuple2f p171_1, float p171_2) {
        this.x = (1.0F - p171_2) * p171_0.x + p171_2 * p171_1.x;
        this.y = (1.0F - p171_2) * p171_0.y + p171_2 * p171_1.y;
    }

    public final void absolute(Tuple2f p171_0) {
        this.x = java.lang.Math.abs(p171_0.x);
        this.y = java.lang.Math.abs(p171_0.y);
    }

    public Tuple2f(float p171_0, float p171_1) {
        this.x = p171_0;
        this.y = p171_1;
    }

    public final void setY(float f) {
        this.y = f;
    }

    public final float getX() {
        return this.x;
    }

    public final void get(float[] p171_0) {
        p171_0[0] = this.x;
        p171_0[1] = this.y;
    }

    public final void clamp(
            float p171_0, float p171_1, Tuple2f p171_2) {
        java.lang.String[] astring;
        label40:
        {
            astring = remix.compat.vecmath.Tuple3f.hF();
            if (p171_2.x > p171_1) {
                this.x = p171_1;
                if (astring == null) {
                    break label40;
                }
            }

            if (p171_2.x < p171_0) {
                this.x = p171_0;
                if (astring == null) {
                    break label40;
                }
            }

            this.x = p171_2.x;
        }

        if (p171_2.y > p171_1) {
            this.y = p171_1;
            if (astring == null) {
                return;
            }
        }

        if (p171_2.y < p171_0) {
            this.y = p171_0;
            if (astring == null) {
                return;
            }
        }

        this.y = p171_2.y;
    }

    public final void sub(
            Tuple2f p171_0, Tuple2f p171_1) {
        this.x = p171_0.x - p171_1.x;
        this.y = p171_0.y - p171_1.y;
    }

    public final void negate(Tuple2f p171_0) {
        this.x = -p171_0.x;
        this.y = -p171_0.y;
    }

    public final void add(Tuple2f p171_0) {
        this.x = this.x + p171_0.x;
        this.y = this.y + p171_0.y;
    }

    public boolean epsilonEquals(Tuple2f p171_0, float p171_1) {
        float f1 = this.x - p171_0.x;

        try {
            if (java.lang.Float.isNaN(f1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple2f.wrapNullPointerException(
                    nullpointerexception2);
        }

        float f2;
        label62:
        {
            try {
                if (f1 < 0.0F) {
                    f2 = -f1;
                    break label62;
                }
            } catch (java.lang.NullPointerException nullpointerexception5) {
                throw remix.compat.vecmath.Tuple2f.wrapNullPointerException(
                        nullpointerexception5);
            }

            f2 = f1;
        }

        try {
            if (f2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception4) {
            throw remix.compat.vecmath.Tuple2f.wrapNullPointerException(
                    nullpointerexception4);
        }

        f1 = this.y - p171_0.y;

        try {
            if (java.lang.Float.isNaN(f1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple2f.wrapNullPointerException(
                    nullpointerexception1);
        }

        label48:
        {
            try {
                if (f1 < 0.0F) {
                    f2 = -f1;
                    break label48;
                }
            } catch (java.lang.NullPointerException nullpointerexception3) {
                throw remix.compat.vecmath.Tuple2f.wrapNullPointerException(
                        nullpointerexception3);
            }

            f2 = f1;
        }

        try {
            return !(f2 > p171_1);
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple2f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void set(float[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Tuple2f(Tuple2f tuple2f) {
        super();
        this.x = tuple2f.x;
        this.y = tuple2f.y;
    }
}
