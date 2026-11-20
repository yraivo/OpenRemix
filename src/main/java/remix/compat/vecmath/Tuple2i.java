package remix.compat.vecmath;

import java.io.Serializable;

public abstract class Tuple2i implements Serializable, Cloneable {
    public int y;
    public int x;
    private static String[] packedTextFragments;
    private static String c;
    static final long serialVersionUID = -3555701650170169638L;

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }
    }

    public final void get(int[] p171_0) {
        p171_0[0] = this.x;
        p171_0[1] = this.y;
    }

    public final void scaleAdd(int p171_0, Tuple2i p171_1) {
        this.x = p171_0 * this.x + p171_1.x;
        this.y = p171_0 * this.y + p171_1.y;
    }

    static {
        remix.compat.vecmath.Tuple2i.packedTextFragments = new String[3];
        remix.compat.vecmath.Tuple2i.packedTextFragments[0] = new String("(");
        remix.compat.vecmath.Tuple2i.packedTextFragments[1] = new String(")");
        remix.compat.vecmath.Tuple2i.packedTextFragments[2] = new String("/2");
        c = ", ";
    }

    public final int getX() {
        return this.x;
    }

    public final void method_98d37317de9aef781741(int p171_0, int p171_1) {
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

    public final void absolute(Tuple2i p171_0) {
        this.x = java.lang.Math.abs(p171_0.x);
        this.y = java.lang.Math.abs(p171_0.y);
    }

    public final void scale(int p171_0, Tuple2i p171_1) {
        this.x = p171_0 * p171_1.x;
        this.y = p171_0 * p171_1.y;
    }

    public final void add(
            Tuple2i p171_0, Tuple2i p171_1) {
        this.x = p171_0.x + p171_1.x;
        this.y = p171_0.y + p171_1.y;
    }

    public Tuple2i(int[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
    }

    @Override
    public int hashCode() {

        long i = 1L;

        i = 31L * i + this.x;

        i = 31L * i + this.y;
        return (int) (i ^ i >> 32);
    }

    public final void setX(int i) {
        this.x = i;
    }

    public final void method_0cba0a717436634d48bb(int p171_0, Tuple2i p171_1) {
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

    // region String decoding support

    // endregion

    public final void sub(
            Tuple2i p171_0, Tuple2i p171_1) {
        this.x = p171_0.x - p171_1.x;
        this.y = p171_0.y - p171_1.y;
    }

    public final void negate(Tuple2i p171_0) {
        this.x = -p171_0.x;
        this.y = -p171_0.y;
    }

    public final void setY(int i) {
        this.y = i;
    }

    @Override
    public String toString() {
        return remix.compat.vecmath.Tuple2i.packedTextFragments[0]
                + this.x
                + remix.compat.vecmath.Tuple2i.c
                + this.y
                + remix.compat.vecmath.Tuple2i.packedTextFragments[1];
    }

    public final void method_88e5110bb5d9566b0cd1(Tuple2i p171_0) {
        this.x = this.x + p171_0.x;
        this.y = this.y + p171_0.y;
    }

    public final void scaleAdd(
            int p171_0, Tuple2i p171_1, Tuple2i p171_2) {
        this.x = p171_0 * p171_1.x + p171_2.x;
        this.y = p171_0 * p171_1.y + p171_2.y;
    }

    public final void method_571f52593fafd8dfba61(int p171_0) {
        try {
            if (this.x > p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple2i.method_339a46a0855ea6bd2c63(
                    nullpointerexception1);
        }

        try {
            if (this.y > p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple2i.method_339a46a0855ea6bd2c63(
                    nullpointerexception);
        }
    }

    public final void method_6d2f16dc0416ebadbea4(
            Tuple2i tuple2i) {
        this.x = tuple2i.x;
        this.y = tuple2i.y;
    }

    public final void scale(int p171_0) {
        this.x *= p171_0;
        this.y *= p171_0;
    }

    // region String decoding support

    // endregion

    public final void method_afd4423e1010806c7658(Tuple2i p171_0) {
        this.x = this.x - p171_0.x;
        this.y = this.y - p171_0.y;
    }

    public final void set(int[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
    }

    public final void method_a690260625812d1a536c(
            Tuple2i tuple2i) {
        tuple2i.x = this.x;
        tuple2i.y = this.y;
    }

    public final int getY() {
        return this.y;
    }

    public final void method_ce5a9e88bf530a8791ab(int p171_0, Tuple2i p171_1) {
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

    private static NullPointerException method_339a46a0855ea6bd2c63(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Tuple2i tuple2i =
                    (remix.compat.vecmath.Tuple2i) p171_0;
            return (this.x == tuple2i.x
                    && this.y == tuple2i.y);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        }
    }

    public Tuple2i(int p171_0, int p171_1) {
        this.x = p171_0;
        this.y = p171_1;
    }

    public Tuple2i() {
        this.x = 0;
        this.y = 0;
    }

    public final void absolute() {
        this.x = java.lang.Math.abs(this.x);
        this.y = java.lang.Math.abs(this.y);
    }

    public final void clamp(
            int p171_0, int p171_1, Tuple2i p171_2) {
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

    public final void set(int p171_0, int p171_1) {
        this.x = p171_0;
        this.y = p171_1;
    }

    public final void b(int p171_0) {
        try {
            if (this.x < p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple2i.method_339a46a0855ea6bd2c63(
                    nullpointerexception1);
        }

        try {
            if (this.y < p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple2i.method_339a46a0855ea6bd2c63(
                    nullpointerexception);
        }
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Tuple2i(Tuple2i tuple2i) {
        super();
        this.x = tuple2i.x;
        this.y = tuple2i.y;
    }
}
