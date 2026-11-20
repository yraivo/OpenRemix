package remix.compat.vecmath;

import java.io.Serializable;

public abstract class Tuple3f implements Serializable, Cloneable {
    private static String[] packedTextFragments;

    private static String[] d;
    public float y;
    public float z;
    static final long serialVersionUID = 5019834619484343712L;

    public float x;

    @Override
    public int hashCode() {

        long i = 1L;
        i = VecMathUtil.hashFloatToIntBits(i, this.x);
        i = VecMathUtil.hashFloatToIntBits(i, this.y);
        i = VecMathUtil.hashFloatToIntBits(i, this.z);
        return VecMathUtil.hashFinish(i);
    }

    public final void absolute(Tuple3f p171_0) {
        this.x = java.lang.Math.abs(p171_0.x);
        this.y = java.lang.Math.abs(p171_0.y);
        this.z = java.lang.Math.abs(p171_0.z);
    }

    public final void clampMin(float p171_0, Tuple3f p171_1) {
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

    public final void clampMax(float p171_0, Tuple3f p171_1) {
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

    public Tuple3f(Tuple3d p171_0) {
        this.x = (float) p171_0.x;
        this.y = (float) p171_0.y;
        this.z = (float) p171_0.z;
    }

    private static NullPointerException wrapNullPointerException(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    public final void setX(float f) {
        this.x = f;
    }

    public boolean epsilonEquals(Tuple3f p171_0, float p171_1) {
        float f1 = this.x - p171_0.x;

        try {
            if (java.lang.Float.isNaN(f1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception3) {
            throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                    nullpointerexception3);
        }

        float f2;
        label93:
        {
            try {
                if (f1 < 0.0F) {
                    f2 = -f1;
                    break label93;
                }
            } catch (java.lang.NullPointerException nullpointerexception8) {
                throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                        nullpointerexception8);
            }

            f2 = f1;
        }

        try {
            if (f2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception7) {
            throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                    nullpointerexception7);
        }

        f1 = this.y - p171_0.y;

        try {
            if (java.lang.Float.isNaN(f1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                    nullpointerexception2);
        }

        label79:
        {
            try {
                if (f1 < 0.0F) {
                    f2 = -f1;
                    break label79;
                }
            } catch (java.lang.NullPointerException nullpointerexception6) {
                throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                        nullpointerexception6);
            }

            f2 = f1;
        }

        try {
            if (f2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception5) {
            throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                    nullpointerexception5);
        }

        f1 = this.z - p171_0.z;

        try {
            if (java.lang.Float.isNaN(f1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                    nullpointerexception1);
        }

        label65:
        {
            try {
                if (f1 < 0.0F) {
                    f2 = -f1;
                    break label65;
                }
            } catch (java.lang.NullPointerException nullpointerexception4) {
                throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                        nullpointerexception4);
            }

            f2 = f1;
        }

        try {
            return !(f2 > p171_1);
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public Tuple3f() {
        this.x = 0.0F;
        this.y = 0.0F;
        this.z = 0.0F;
    }

    public final void clamp(float p171_0, float p171_1) {
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

    public final void setZ(float f) {
        this.z = f;
    }

    public final void scaleAdd(float p171_0, Tuple3f p171_1) {
        this.x = p171_0 * this.x + p171_1.x;
        this.y = p171_0 * this.y + p171_1.y;
        this.z = p171_0 * this.z + p171_1.z;
    }

    public final void get(float[] p171_0) {
        p171_0[0] = this.x;
        p171_0[1] = this.y;
        p171_0[2] = this.z;
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

    public final void scale(float p171_0, Tuple3f p171_1) {
        this.x = p171_0 * p171_1.x;
        this.y = p171_0 * p171_1.y;
        this.z = p171_0 * p171_1.z;
    }

    public final void sub(Tuple3f p171_0) {
        this.x = this.x - p171_0.x;
        this.y = this.y - p171_0.y;
        this.z = this.z - p171_0.z;
    }

    public static String[] hF() {
        return d;
    }

    // region String decoding support

    // endregion

    public final void absolute() {
        this.x = java.lang.Math.abs(this.x);
        this.y = java.lang.Math.abs(this.y);
        this.z = java.lang.Math.abs(this.z);
    }

    public boolean equals(Tuple3f p171_0) {
        try {
            return (this.x == p171_0.x && this.y == p171_0.y && this.z == p171_0.z);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final void negate(Tuple3f p171_0) {
        this.x = -p171_0.x;
        this.y = -p171_0.y;
        this.z = -p171_0.z;
    }

    public final void setY(float f) {
        this.y = f;
    }

    public final void clampMax(float p171_0) {
        try {
            if (this.x > p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                    nullpointerexception1);
        }

        try {
            if (this.y > p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                    nullpointerexception2);
        }

        try {
            if (this.z > p171_0) {
                this.z = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    static {
        remix.compat.vecmath.Tuple3f.packedTextFragments = new String[3];
        remix.compat.vecmath.Tuple3f.packedTextFragments[0] = new String("(");
        remix.compat.vecmath.Tuple3f.packedTextFragments[1] = new String(")");
        b(null);
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public Tuple3f(float[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
    }

    public Tuple3f(float p171_0, float p171_1, float p171_2) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
    }

    public final void add(
            Tuple3f p171_0, Tuple3f p171_1) {
        this.x = p171_0.x + p171_1.x;
        this.y = p171_0.y + p171_1.y;
        this.z = p171_0.z + p171_1.z;
    }

    public final void set(float p171_0, float p171_1, float p171_2) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Tuple3f tuple3f =
                    (remix.compat.vecmath.Tuple3f) p171_0;
            return (this.x == tuple3f.x
                    && this.y == tuple3f.y
                    && this.z == tuple3f.z);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        }
    }

    public final void clamp(
            float p171_0, float p171_1, Tuple3f p171_2) {
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

    public final void interpolate(
            Tuple3f p171_0, Tuple3f p171_1, float p171_2) {
        this.x = (1.0F - p171_2) * p171_0.x + p171_2 * p171_1.x;
        this.y = (1.0F - p171_2) * p171_0.y + p171_2 * p171_1.y;
        this.z = (1.0F - p171_2) * p171_0.z + p171_2 * p171_1.z;
    }

    public final void add(Tuple3f p171_0) {
        this.x = this.x + p171_0.x;
        this.y = this.y + p171_0.y;
        this.z = this.z + p171_0.z;
    }

    public final void sub(
            Tuple3f p171_0, Tuple3f p171_1) {
        this.x = p171_0.x - p171_1.x;
        this.y = p171_0.y - p171_1.y;
        this.z = p171_0.z - p171_1.z;
    }

    public final void get(
            Tuple3f tuple3f) {
        tuple3f.x = this.x;
        tuple3f.y = this.y;
        tuple3f.z = this.z;
    }

    public final void clampMin(float p171_0) {
        try {
            if (this.x < p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                    nullpointerexception1);
        }

        try {
            if (this.y < p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                    nullpointerexception2);
        }

        try {
            if (this.z < p171_0) {
                this.z = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple3f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final float getY() {
        return this.y;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }
    }

    public final float getX() {
        return this.x;
    }

    public final void interpolate(Tuple3f p171_0, float p171_1) {
        this.x = (1.0F - p171_1) * this.x + p171_1 * p171_0.x;
        this.y = (1.0F - p171_1) * this.y + p171_1 * p171_0.y;
        this.z = (1.0F - p171_1) * this.z + p171_1 * p171_0.z;
    }

    public final void scaleAdd(
            float p171_0, Tuple3f p171_1, Tuple3f p171_2) {
        this.x = p171_0 * p171_1.x + p171_2.x;
        this.y = p171_0 * p171_1.y + p171_2.y;
        this.z = p171_0 * p171_1.z + p171_2.z;
    }

    public static void b(String[] astring) {
        d = astring;
    }

    public final void set(Tuple3d p171_0) {
        this.x = (float) p171_0.x;
        this.y = (float) p171_0.y;
        this.z = (float) p171_0.z;
    }

    public final void set(float[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
    }

    public final void scale(float p171_0) {
        this.x *= p171_0;
        this.y *= p171_0;
        this.z *= p171_0;
    }

    public final float getZ() {
        return this.z;
    }

    public final void set(
            Tuple3f tuple3f) {
        this.x = tuple3f.x;
        this.y = tuple3f.y;
        this.z = tuple3f.z;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Tuple3f(Tuple3f tuple3f) {
        super();
        this.x = tuple3f.x;
        this.y = tuple3f.y;
        this.z = tuple3f.z;
    }
}
