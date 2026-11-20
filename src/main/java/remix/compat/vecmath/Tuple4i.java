package remix.compat.vecmath;

import java.io.Serializable;

public abstract class Tuple4i implements Serializable, Cloneable {
    static final long serialVersionUID = 8064614250942616720L;

    public int x;
    public int w;

    public int y;
    public int z;
    private static String[] packedTextFragments;

    public final int getX() {
        return this.x;
    }

    @Override
    public int hashCode() {

        long i = 1L;

        i = 31L * i + this.x;

        i = 31L * i + this.y;

        i = 31L * i + this.z;

        i = 31L * i + this.w;
        return (int) (i ^ i >> 32);
    }

    public final void sub(
            Tuple4i p171_0, Tuple4i p171_1) {
        this.x = p171_0.x - p171_1.x;
        this.y = p171_0.y - p171_1.y;
        this.z = p171_0.z - p171_1.z;
        this.w = p171_0.w - p171_1.w;
    }

    public final void scaleAdd(
            int p171_0, Tuple4i p171_1, Tuple4i p171_2) {
        this.x = p171_0 * p171_1.x + p171_2.x;
        this.y = p171_0 * p171_1.y + p171_2.y;
        this.z = p171_0 * p171_1.z + p171_2.z;
        this.w = p171_0 * p171_1.w + p171_2.w;
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Tuple4i tuple4i =
                    (remix.compat.vecmath.Tuple4i) p171_0;
            return (this.x == tuple4i.x
                    && this.y == tuple4i.y
                    && this.z == tuple4i.z
                    && this.w == tuple4i.w);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        }
    }

    public final void clamp(int p171_0, int p171_1) {
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

        {
            if (this.y > p171_1) {
                this.y = p171_1;
                if (!(astring == null)) {

                    if (this.y < p171_0) {
                        this.y = p171_0;
                    }
                }
            } else {

                if (this.y < p171_0) {
                    this.y = p171_0;
                }
            }
        }

        {
            if (this.z > p171_1) {
                this.z = p171_1;
                if (!(astring == null)) {

                    if (this.z < p171_0) {
                        this.z = p171_0;
                    }
                }
            } else {

                if (this.z < p171_0) {
                    this.z = p171_0;
                }
            }
        }

        if (this.w > p171_1) {
            this.w = p171_1;
            if (astring == null) {
                return;
            }
        }

        if (this.w < p171_0) {
            this.w = p171_0;
        }
    }

    // region String decoding support

    // endregion

    public final int getW() {
        return this.w;
    }

    public final int getZ() {
        return this.z;
    }

    static {
        remix.compat.vecmath.Tuple4i.packedTextFragments = new String[3];
        remix.compat.vecmath.Tuple4i.packedTextFragments[0] = new String("(");
        remix.compat.vecmath.Tuple4i.packedTextFragments[1] = new String(")");
    }

    public final void set(int p171_0, int p171_1, int p171_2, int p171_3) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
        this.w = p171_3;
    }

    public final void clamp(
            int p171_0, int p171_1, Tuple4i p171_2) {
        java.lang.String[] astring;
        label74:
        {
            astring = remix.compat.vecmath.Tuple3f.hF();
            if (p171_2.x > p171_1) {
                this.x = p171_1;
                if (astring == null) {
                    break label74;
                }
            }

            if (p171_2.x < p171_0) {
                this.x = p171_0;
                if (astring == null) {
                    break label74;
                }
            }

            this.x = p171_2.x;
        }

        label75:
        {
            if (p171_2.y > p171_1) {
                this.y = p171_1;
                if (astring == null) {
                    break label75;
                }
            }

            if (p171_2.y < p171_0) {
                this.y = p171_0;
                if (astring == null) {
                    break label75;
                }
            }

            this.y = p171_2.y;
        }

        label76:
        {
            if (p171_2.z > p171_1) {
                this.z = p171_1;
                if (astring == null) {
                    break label76;
                }
            }

            if (p171_2.z < p171_0) {
                this.z = p171_0;
                if (astring == null) {
                    break label76;
                }
            }

            this.z = p171_2.z;
        }

        if (p171_2.w > p171_1) {
            this.w = p171_1;
            if (astring == null) {
                return;
            }
        }

        if (p171_2.w < p171_0) {
            this.w = p171_0;
            if (astring == null) {
                return;
            }
        }

        this.w = p171_2.w;
    }

    public final void scaleAdd(int p171_0, Tuple4i p171_1) {
        this.x = p171_0 * this.x + p171_1.x;
        this.y = p171_0 * this.y + p171_1.y;
        this.z = p171_0 * this.z + p171_1.z;
        this.w = p171_0 * this.w + p171_1.w;
    }

    public final void setW(int i) {
        this.w = i;
    }

    public Tuple4i(int p171_0, int p171_1, int p171_2, int p171_3) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
        this.w = p171_3;
    }

    public final void add(Tuple4i p171_0) {
        this.x = this.x + p171_0.x;
        this.y = this.y + p171_0.y;
        this.z = this.z + p171_0.z;
        this.w = this.w + p171_0.w;
    }

    public final int getY() {
        return this.y;
    }

    private static NullPointerException wrapNullPointerException(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    public final void scale(int p171_0, Tuple4i p171_1) {
        this.x = p171_0 * p171_1.x;
        this.y = p171_0 * p171_1.y;
        this.z = p171_0 * p171_1.z;
        this.w = p171_0 * p171_1.w;
    }

    public final void get(
            Tuple4i tuple4i) {
        tuple4i.x = this.x;
        tuple4i.y = this.y;
        tuple4i.z = this.z;
        tuple4i.w = this.w;
    }

    public final void clampMax(int p171_0) {
        try {
            if (this.x > p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception3) {
            throw remix.compat.vecmath.Tuple4i.wrapNullPointerException(
                    nullpointerexception3);
        }

        try {
            if (this.y > p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple4i.wrapNullPointerException(
                    nullpointerexception1);
        }

        try {
            if (this.z > p171_0) {
                this.z = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple4i.wrapNullPointerException(
                    nullpointerexception2);
        }

        try {
            if (this.w > p171_0) {
                this.w = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple4i.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void absolute(Tuple4i p171_0) {
        this.x = java.lang.Math.abs(p171_0.x);
        this.y = java.lang.Math.abs(p171_0.y);
        this.z = java.lang.Math.abs(p171_0.z);
        this.w = java.lang.Math.abs(p171_0.w);
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public final void scale(int p171_0) {
        this.x *= p171_0;
        this.y *= p171_0;
        this.z *= p171_0;
        this.w *= p171_0;
    }

    public final void get(int[] p171_0) {
        p171_0[0] = this.x;
        p171_0[1] = this.y;
        p171_0[2] = this.z;
        p171_0[3] = this.w;
    }

    @Override
    public String toString() {
        return packedTextFragments[0]
                + this.x
                + ", "
                + this.y
                + ", "
                + this.z
                + ", "
                + this.w
                + packedTextFragments[1];
    }

    public final void b(int p171_0) {
        try {
            if (this.x < p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception3) {
            throw remix.compat.vecmath.Tuple4i.wrapNullPointerException(
                    nullpointerexception3);
        }

        try {
            if (this.y < p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple4i.wrapNullPointerException(
                    nullpointerexception1);
        }

        try {
            if (this.z < p171_0) {
                this.z = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple4i.wrapNullPointerException(
                    nullpointerexception2);
        }

        try {
            if (this.w < p171_0) {
                this.w = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple4i.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public Tuple4i(int[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
        this.w = p171_0[3];
    }

    public final void setZ(int i) {
        this.z = i;
    }

    public final void clampMin(int p171_0, Tuple4i p171_1) {
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

        {
            if (p171_1.y < p171_0) {
                this.y = p171_0;
                if (!(astring == null)) {

                    this.y = p171_1.y;
                }
            } else {

                this.y = p171_1.y;
            }
        }

        {
            if (p171_1.z < p171_0) {
                this.z = p171_0;
                if (!(astring == null)) {

                    this.z = p171_1.z;
                }
            } else {

                this.z = p171_1.z;
            }
        }

        if (p171_1.w < p171_0) {
            this.w = p171_0;
            if (astring == null) {
                return;
            }
        }

        this.w = p171_1.w;
    }

    public Tuple4i() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }

    public final void add(
            Tuple4i p171_0, Tuple4i p171_1) {
        this.x = p171_0.x + p171_1.x;
        this.y = p171_0.y + p171_1.y;
        this.z = p171_0.z + p171_1.z;
        this.w = p171_0.w + p171_1.w;
    }

    public final void negate(Tuple4i p171_0) {
        this.x = -p171_0.x;
        this.y = -p171_0.y;
        this.z = -p171_0.z;
        this.w = -p171_0.w;
    }

    public final void set(
            Tuple4i tuple4i) {
        this.x = tuple4i.x;
        this.y = tuple4i.y;
        this.z = tuple4i.z;
        this.w = tuple4i.w;
    }

    public final void absolute() {
        this.x = java.lang.Math.abs(this.x);
        this.y = java.lang.Math.abs(this.y);
        this.z = java.lang.Math.abs(this.z);
        this.w = java.lang.Math.abs(this.w);
    }

    public final void setX(int i) {
        this.x = i;
    }

    public final void sub(Tuple4i p171_0) {
        this.x = this.x - p171_0.x;
        this.y = this.y - p171_0.y;
        this.z = this.z - p171_0.z;
        this.w = this.w - p171_0.w;
    }

    public final void clampMax(int p171_0, Tuple4i p171_1) {
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

        {
            if (p171_1.y > p171_0) {
                this.y = p171_0;
                if (!(astring == null)) {

                    this.y = p171_1.y;
                }
            } else {

                this.y = p171_1.y;
            }
        }

        {
            if (p171_1.z > p171_0) {
                this.z = p171_0;
                if (!(astring == null)) {

                    this.z = p171_1.z;
                }
            } else {

                this.z = p171_1.z;
            }
        }

        if (p171_1.w > p171_0) {
            this.w = p171_0;
            if (astring == null) {
                return;
            }
        }

        this.w = p171_1.z;
    }

    public final void set(int[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
        this.w = p171_0[3];
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }
    }

    public final void setY(int i) {
        this.y = i;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Tuple4i(Tuple4i tuple4i) {
        super();
        this.x = tuple4i.x;
        this.y = tuple4i.y;
        this.z = tuple4i.z;
        this.w = tuple4i.w;
    }
}
