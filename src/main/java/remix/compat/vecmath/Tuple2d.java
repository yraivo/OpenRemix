package remix.compat.vecmath;

import java.io.Serializable;

public abstract class Tuple2d implements Serializable, Cloneable {
    private static String c;
    public double x;
    static final long serialVersionUID = 6205762482756093838L;
    private static String[] packedTextFragments;
    public double y;

    public boolean equals(Tuple2d tuple2d) {
        try {
            return this.x == tuple2d.x && this.y == tuple2d.y;
        } catch (NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final void add(Tuple2d tuple2d) {
        this.x = this.x + tuple2d.x;
        this.y = this.y + tuple2d.y;
    }

    public final void interpolate(Tuple2d tuple2d, double p171_1) {
        this.x = (1.0 - p171_1) * this.x + p171_1 * tuple2d.x;
        this.y = (1.0 - p171_1) * this.y + p171_1 * tuple2d.y;
    }

    public final void set(double[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
    }

    public final void scaleAdd(double p171_0, Tuple2d tuple2d) {
        this.x = p171_0 * this.x + tuple2d.x;
        this.y = p171_0 * this.y + tuple2d.y;
    }

    public final void interpolate(
            Tuple2d tuple2d, Tuple2d tuple2dValue, double p171_2) {
        this.x = (1.0 - p171_2) * tuple2d.x + p171_2 * tuple2dValue.x;
        this.y = (1.0 - p171_2) * tuple2d.y + p171_2 * tuple2dValue.y;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public final void absolute() {
        this.x = java.lang.Math.abs(this.x);
        this.y = java.lang.Math.abs(this.y);
    }

    public final void clampMin(double p171_0) {
        try {
            if (this.x < p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple2d.wrapNullPointerException(nullpointerexception1);
        }

        try {
            if (this.y < p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple2d.wrapNullPointerException(nullpointerexception);
        }
    }

    public final void clamp(double p171_0, double p171_1, Tuple2d tuple2d) {
        java.lang.String[] astring;
        label40:
        {
            astring = remix.compat.vecmath.Tuple3f.hF();
            if (tuple2d.x > p171_1) {
                this.x = p171_1;
                if (astring == null) {
                    break label40;
                }
            }

            if (tuple2d.x < p171_0) {
                this.x = p171_0;
                if (astring == null) {
                    break label40;
                }
            }

            this.x = tuple2d.x;
        }

        if (tuple2d.y > p171_1) {
            this.y = p171_1;
            if (astring == null) {
                return;
            }
        }

        if (tuple2d.y < p171_0) {
            this.y = p171_0;
            if (astring == null) {
                return;
            }
        }

        this.y = tuple2d.y;
    }

    public final void negate(Tuple2d tuple2d) {
        this.x = -tuple2d.x;
        this.y = -tuple2d.y;
    }

    public final void sub(Tuple2d tuple2d, Tuple2d tuple2dValue) {
        this.x = tuple2d.x - tuple2dValue.x;
        this.y = tuple2d.y - tuple2dValue.y;
    }

    // region String decoding support

    // endregion

    public final void scale(double p171_0, Tuple2d tuple2d) {
        this.x = p171_0 * tuple2d.x;
        this.y = p171_0 * tuple2d.y;
    }

    private static NullPointerException wrapNullPointerException(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    public final void clampMax(double p171_0) {
        try {
            if (this.x > p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple2d.wrapNullPointerException(nullpointerexception1);
        }

        try {
            if (this.y > p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple2d.wrapNullPointerException(nullpointerexception);
        }
    }

    // region Static initialization and string tables
    static {
        remix.compat.vecmath.Tuple2d.packedTextFragments = new String[3];
        remix.compat.vecmath.Tuple2d.packedTextFragments[0] = new String("(");
        remix.compat.vecmath.Tuple2d.packedTextFragments[1] = new String(")");
        remix.compat.vecmath.Tuple2d.packedTextFragments[2] = new String("'\u001f");
        c = ", ";
    }

    // endregion

    public final void scale(double p171_0) {
        this.x *= p171_0;
        this.y *= p171_0;
    }

    // region String decoding support

    // endregion

    public final void scaleAdd(
            double p171_0, Tuple2d tuple2d, Tuple2d tuple2dValue) {
        this.x = p171_0 * tuple2d.x + tuple2dValue.x;
        this.y = p171_0 * tuple2d.y + tuple2dValue.y;
    }

    @Override
    public String toString() {
        return remix.compat.vecmath.Tuple2d.packedTextFragments[0]
                + this.x
                + remix.compat.vecmath.Tuple2d.c
                + this.y
                + remix.compat.vecmath.Tuple2d.packedTextFragments[1];
    }

    public Tuple2d(Tuple2f p171_0) {
        this.x = p171_0.x;
        this.y = p171_0.y;
    }

    public Tuple2d(double[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
    }

    public final void set(double p171_0, double p171_1) {
        this.x = p171_0;
        this.y = p171_1;
    }

    public final void set(Tuple2d tuple2d) {
        this.x = tuple2d.x;
        this.y = tuple2d.y;
    }

    public final void setY(double d0) {
        this.y = d0;
    }

    public final void sub(Tuple2d tuple2d) {
        this.x = this.x - tuple2d.x;
        this.y = this.y - tuple2d.y;
    }

    public final double getX() {
        return this.x;
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Tuple2d tuple2d = (remix.compat.vecmath.Tuple2d) p171_0;
            return (this.x == tuple2d.x && this.y == tuple2d.y);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        }
    }

    public final void absolute(Tuple2d tuple2d) {
        this.x = java.lang.Math.abs(tuple2d.x);
        this.y = java.lang.Math.abs(tuple2d.y);
    }

    public final void clamp(double p171_0, double p171_1) {
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

    public Tuple2d(double p171_0, double p171_1) {
        this.x = p171_0;
        this.y = p171_1;
    }

    public final void clampMax(double p171_0, Tuple2d tuple2d) {
        java.lang.String[] astring;
        {
            astring = remix.compat.vecmath.Tuple3f.hF();
            if (tuple2d.x > p171_0) {
                this.x = p171_0;
                if (!(astring == null)) {

                    this.x = tuple2d.x;
                }
            } else {

                this.x = tuple2d.x;
            }
        }

        if (tuple2d.y > p171_0) {
            this.y = p171_0;
            if (astring == null) {
                return;
            }
        }

        this.y = tuple2d.y;
    }

    public final void get(double[] p171_0) {
        p171_0[0] = this.x;
        p171_0[1] = this.y;
    }

    public boolean epsilonEquals(Tuple2d tuple2d, double p171_1) {
        double d1 = this.x - tuple2d.x;

        try {
            if (java.lang.Double.isNaN(d1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple2d.wrapNullPointerException(nullpointerexception2);
        }

        double d2;
        label62:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label62;
                }
            } catch (java.lang.NullPointerException nullpointerexception5) {
                throw remix.compat.vecmath.Tuple2d.wrapNullPointerException(nullpointerexception5);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception4) {
            throw remix.compat.vecmath.Tuple2d.wrapNullPointerException(nullpointerexception4);
        }

        d1 = this.y - tuple2d.y;

        try {
            if (java.lang.Double.isNaN(d1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple2d.wrapNullPointerException(nullpointerexception1);
        }

        label48:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label48;
                }
            } catch (java.lang.NullPointerException nullpointerexception3) {
                throw remix.compat.vecmath.Tuple2d.wrapNullPointerException(nullpointerexception3);
            }

            d2 = d1;
        }

        try {
            return !(d2 > p171_1);
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple2d.wrapNullPointerException(nullpointerexception);
        }
    }

    public final void add(Tuple2d tuple2d, Tuple2d tuple2dValue) {
        this.x = tuple2d.x + tuple2dValue.x;
        this.y = tuple2d.y + tuple2dValue.y;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }
    }

    public Tuple2d() {
        this.x = 0.0;
        this.y = 0.0;
    }

    @Override
    public int hashCode() {

        long i = 1L;
        i = VecMathUtil.hashDoubleToLongBits(i, this.x);
        i = VecMathUtil.hashDoubleToLongBits(i, this.y);
        return VecMathUtil.hashFinish(i);
    }

    public final void set(Tuple2f p171_0) {
        this.x = p171_0.x;
        this.y = p171_0.y;
    }

    public final void setX(double d0) {
        this.x = d0;
    }

    public final void clampMin(double p171_0, Tuple2d tuple2d) {
        java.lang.String[] astring;
        {
            astring = remix.compat.vecmath.Tuple3f.hF();
            if (tuple2d.x < p171_0) {
                this.x = p171_0;
                if (!(astring == null)) {

                    this.x = tuple2d.x;
                }
            } else {

                this.x = tuple2d.x;
            }
        }

        if (tuple2d.y < p171_0) {
            this.y = p171_0;
            if (astring == null) {
                return;
            }
        }

        this.y = tuple2d.y;
    }

    public final double getY() {
        return this.y;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Tuple2d(Tuple2d tuple2d) {
        super();
        this.x = tuple2d.x;
        this.y = tuple2d.y;
    }
}
