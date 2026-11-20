package remix.compat.vecmath;

import java.io.Serializable;

public abstract class Tuple3i implements Serializable, Cloneable {
    public int y;
    private static String[] packedTextFragments;

    public int x;
    public int z;
    static final long serialVersionUID = -732740491767276200L;

    public final void setZ(int i) {
        this.z = i;
    }

    public final void setX(int i) {
        this.x = i;
    }

    public final int getY() {
        return this.y;
    }

    public final void scaleAdd(
            int p171_0, Tuple3i p171_1, Tuple3i p171_2) {
        this.x = p171_0 * p171_1.x + p171_2.x;
        this.y = p171_0 * p171_1.y + p171_2.y;
        this.z = p171_0 * p171_1.z + p171_2.z;
    }

    public final int getX() {
        return this.x;
    }

    public final void set(int[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
    }

    public Tuple3i(int p171_0, int p171_1, int p171_2) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
    }

    public final void clampMin(int p171_0, Tuple3i p171_1) {
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

        if (p171_1.z < p171_0) {
            this.z = p171_0;
            if (astring == null) {
                return;
            }
        }

        this.z = p171_1.z;
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

        if (this.z > p171_1) {
            this.z = p171_1;
            if (astring == null) {
                return;
            }
        }

        if (this.z < p171_0) {
            this.z = p171_0;
        }
    }

    static {
        remix.compat.vecmath.Tuple3i.packedTextFragments = new String[3];
        remix.compat.vecmath.Tuple3i.packedTextFragments[0] = new String("(");
        remix.compat.vecmath.Tuple3i.packedTextFragments[1] = new String(")");
    }

    public final int getZ() {
        return this.z;
    }

    @Override
    public int hashCode() {

        long i = 1L;

        i = 31L * i + this.x;

        i = 31L * i + this.y;

        i = 31L * i + this.z;
        return (int) (i ^ i >> 32);
    }

    public final void scale(int p171_0) {
        this.x *= p171_0;
        this.y *= p171_0;
        this.z *= p171_0;
    }

    public final void clampMax(int p171_0) {
        try {
            if (this.x > p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple3i.wrapNullPointerException(
                    nullpointerexception1);
        }

        try {
            if (this.y > p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple3i.wrapNullPointerException(
                    nullpointerexception2);
        }

        try {
            if (this.z > p171_0) {
                this.z = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple3i.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    // region String decoding support

    // endregion

    public final void b(int p171_0) {
        try {
            if (this.x < p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple3i.wrapNullPointerException(
                    nullpointerexception1);
        }

        try {
            if (this.y < p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple3i.wrapNullPointerException(
                    nullpointerexception2);
        }

        try {
            if (this.z < p171_0) {
                this.z = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple3i.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void clamp(
            int p171_0, int p171_1, Tuple3i p171_2) {
        java.lang.String[] astring;
        label57:
        {
            astring = remix.compat.vecmath.Tuple3f.hF();
            if (p171_2.x > p171_1) {
                this.x = p171_1;
                if (astring == null) {
                    break label57;
                }
            }

            if (p171_2.x < p171_0) {
                this.x = p171_0;
                if (astring == null) {
                    break label57;
                }
            }

            this.x = p171_2.x;
        }

        label58:
        {
            if (p171_2.y > p171_1) {
                this.y = p171_1;
                if (astring == null) {
                    break label58;
                }
            }

            if (p171_2.y < p171_0) {
                this.y = p171_0;
                if (astring == null) {
                    break label58;
                }
            }

            this.y = p171_2.y;
        }

        if (p171_2.z > p171_1) {
            this.z = p171_1;
            if (astring == null) {
                return;
            }
        }

        if (p171_2.z < p171_0) {
            this.z = p171_0;
            if (astring == null) {
                return;
            }
        }

        this.z = p171_2.z;
    }

    public final void sub(
            Tuple3i p171_0, Tuple3i p171_1) {
        this.x = p171_0.x - p171_1.x;
        this.y = p171_0.y - p171_1.y;
        this.z = p171_0.z - p171_1.z;
    }

    public final void scale(int p171_0, Tuple3i p171_1) {
        this.x = p171_0 * p171_1.x;
        this.y = p171_0 * p171_1.y;
        this.z = p171_0 * p171_1.z;
    }

    public final void scaleAdd(int p171_0, Tuple3i p171_1) {
        this.x = p171_0 * this.x + p171_1.x;
        this.y = p171_0 * this.y + p171_1.y;
        this.z = p171_0 * this.z + p171_1.z;
    }

    public Tuple3i(int[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
    }

    public final void negate(Tuple3i p171_0) {
        this.x = -p171_0.x;
        this.y = -p171_0.y;
        this.z = -p171_0.z;
    }

    public final void clampMax(int p171_0, Tuple3i p171_1) {
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

        if (p171_1.z > p171_0) {
            this.z = p171_0;
            if (astring == null) {
                return;
            }
        }

        this.z = p171_1.z;
    }

    public final void set(int p171_0, int p171_1, int p171_2) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
    }

    @Override
    public String toString() {
        return packedTextFragments[0]
                + this.x
                + ", "
                + this.y
                + ", "
                + this.z
                + packedTextFragments[1];
    }

    public final void get(int[] p171_0) {
        p171_0[0] = this.x;
        p171_0[1] = this.y;
        p171_0[2] = this.z;
    }

    private static NullPointerException wrapNullPointerException(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    public final void absolute(Tuple3i p171_0) {
        this.x = java.lang.Math.abs(p171_0.x);
        this.y = java.lang.Math.abs(p171_0.y);
        this.z = java.lang.Math.abs(p171_0.z);
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Tuple3i tuple3i =
                    (remix.compat.vecmath.Tuple3i) p171_0;
            return (this.x == tuple3i.x
                    && this.y == tuple3i.y
                    && this.z == tuple3i.z);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        }
    }

    public final void set(
            Tuple3i tuple3i) {
        this.x = tuple3i.x;
        this.y = tuple3i.y;
        this.z = tuple3i.z;
    }

    public final void sub(Tuple3i p171_0) {
        this.x = this.x - p171_0.x;
        this.y = this.y - p171_0.y;
        this.z = this.z - p171_0.z;
    }

    public Tuple3i() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public final void get(
            Tuple3i tuple3i) {
        tuple3i.x = this.x;
        tuple3i.y = this.y;
        tuple3i.z = this.z;
    }

    public final void add(
            Tuple3i p171_0, Tuple3i p171_1) {
        this.x = p171_0.x + p171_1.x;
        this.y = p171_0.y + p171_1.y;
        this.z = p171_0.z + p171_1.z;
    }

    public final void add(Tuple3i p171_0) {
        this.x = this.x + p171_0.x;
        this.y = this.y + p171_0.y;
        this.z = this.z + p171_0.z;
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

    public final void absolute() {
        this.x = java.lang.Math.abs(this.x);
        this.y = java.lang.Math.abs(this.y);
        this.z = java.lang.Math.abs(this.z);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Tuple3i(Tuple3i tuple3i) {
        super();
        this.x = tuple3i.x;
        this.y = tuple3i.y;
        this.z = tuple3i.z;
    }
}
