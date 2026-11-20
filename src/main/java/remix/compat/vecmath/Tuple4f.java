package remix.compat.vecmath;

import java.io.Serializable;

public abstract class Tuple4f implements Serializable, Cloneable {
    public float z;
    public float x;

    static final long serialVersionUID = 7068460319248845763L;
    private static String[] packedTextFragments;
    public float y;

    public float w;

    public Tuple4f(float[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
        this.w = p171_0[3];
    }

    public final void set(float[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
        this.w = p171_0[3];
    }

    public Tuple4f() {
        this.x = 0.0F;
        this.y = 0.0F;
        this.z = 0.0F;
        this.w = 0.0F;
    }

    public final void setX(float f) {
        this.x = f;
    }

    @Override
    public int hashCode() {

        long i = 1L;
        i = VecMathUtil.hashFloatToIntBits(i, this.x);
        i = VecMathUtil.hashFloatToIntBits(i, this.y);
        i = VecMathUtil.hashFloatToIntBits(i, this.z);
        i = VecMathUtil.hashFloatToIntBits(i, this.w);
        return VecMathUtil.hashFinish(i);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }
    }

    public final void scaleAdd(
            float p171_0, Tuple4f p171_1, Tuple4f p171_2) {
        this.x = p171_0 * p171_1.x + p171_2.x;
        this.y = p171_0 * p171_1.y + p171_2.y;
        this.z = p171_0 * p171_1.z + p171_2.z;
        this.w = p171_0 * p171_1.w + p171_2.w;
    }

    private static NullPointerException wrapNullPointerException(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    public final void get(float[] p171_0) {
        p171_0[0] = this.x;
        p171_0[1] = this.y;
        p171_0[2] = this.z;
        p171_0[3] = this.w;
    }

    public Tuple4f(Tuple4d p171_0) {
        this.x = (float) p171_0.x;
        this.y = (float) p171_0.y;
        this.z = (float) p171_0.z;
        this.w = (float) p171_0.w;
    }

    public final float getY() {
        return this.y;
    }

    public final void clamp(
            float p171_0, float p171_1, Tuple4f p171_2) {
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

    // region String decoding support

    // endregion

    public Tuple4f(float p171_0, float p171_1, float p171_2, float p171_3) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
        this.w = p171_3;
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

    public final void clampMin(float p171_0, Tuple4f p171_1) {
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

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public final void negate(Tuple4f p171_0) {
        this.x = -p171_0.x;
        this.y = -p171_0.y;
        this.z = -p171_0.z;
        this.w = -p171_0.w;
    }

    public final void set(
            float p171_0, float p171_1, float p171_2, float p171_3) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
        this.w = p171_3;
    }

    public final void scale(float p171_0, Tuple4f p171_1) {
        this.x = p171_0 * p171_1.x;
        this.y = p171_0 * p171_1.y;
        this.z = p171_0 * p171_1.z;
        this.w = p171_0 * p171_1.w;
    }

    public final float getW() {
        return this.w;
    }

    public final void get(
            Tuple4f tuple4f) {
        tuple4f.x = this.x;
        tuple4f.y = this.y;
        tuple4f.z = this.z;
        tuple4f.w = this.w;
    }

    public final void clampMin(float p171_0) {
        try {
            if (this.x < p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception3) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception3);
        }

        try {
            if (this.y < p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception1);
        }

        try {
            if (this.z < p171_0) {
                this.z = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception2);
        }

        try {
            if (this.w < p171_0) {
                this.w = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void clampMax(float p171_0) {
        try {
            if (this.x > p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception3) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception3);
        }

        try {
            if (this.y > p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception1);
        }

        try {
            if (this.z > p171_0) {
                this.z = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception2);
        }

        try {
            if (this.w > p171_0) {
                this.w = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void scaleAdd(float p171_0, Tuple4f p171_1) {
        this.x = p171_0 * this.x + p171_1.x;
        this.y = p171_0 * this.y + p171_1.y;
        this.z = p171_0 * this.z + p171_1.z;
        this.w = p171_0 * this.w + p171_1.w;
    }

    public final void add(
            Tuple4f p171_0, Tuple4f p171_1) {
        this.x = p171_0.x + p171_1.x;
        this.y = p171_0.y + p171_1.y;
        this.z = p171_0.z + p171_1.z;
        this.w = p171_0.w + p171_1.w;
    }

    public void interpolate(
            Tuple4f p171_0, Tuple4f p171_1, float p171_2) {
        this.x = (1.0F - p171_2) * p171_0.x + p171_2 * p171_1.x;
        this.y = (1.0F - p171_2) * p171_0.y + p171_2 * p171_1.y;
        this.z = (1.0F - p171_2) * p171_0.z + p171_2 * p171_1.z;
        this.w = (1.0F - p171_2) * p171_0.w + p171_2 * p171_1.w;
    }

    public final void sub(Tuple4f p171_0) {
        this.x = this.x - p171_0.x;
        this.y = this.y - p171_0.y;
        this.z = this.z - p171_0.z;
        this.w = this.w - p171_0.w;
    }

    public final void scale(float p171_0) {
        this.x *= p171_0;
        this.y *= p171_0;
        this.z *= p171_0;
        this.w *= p171_0;
    }

    public final void sub(
            Tuple4f p171_0, Tuple4f p171_1) {
        this.x = p171_0.x - p171_1.x;
        this.y = p171_0.y - p171_1.y;
        this.z = p171_0.z - p171_1.z;
        this.w = p171_0.w - p171_1.w;
    }

    public final void setZ(float f) {
        this.z = f;
    }

    public final void setY(float f) {
        this.y = f;
    }

    public void interpolate(Tuple4f p171_0, float p171_1) {
        this.x = (1.0F - p171_1) * this.x + p171_1 * p171_0.x;
        this.y = (1.0F - p171_1) * this.y + p171_1 * p171_0.y;
        this.z = (1.0F - p171_1) * this.z + p171_1 * p171_0.z;
        this.w = (1.0F - p171_1) * this.w + p171_1 * p171_0.w;
    }

    public boolean epsilonEquals(Tuple4f p171_0, float p171_1) {
        float f1 = this.x - p171_0.x;

        try {
            if (java.lang.Float.isNaN(f1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception4) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception4);
        }

        float f2;
        label124:
        {
            try {
                if (f1 < 0.0F) {
                    f2 = -f1;
                    break label124;
                }
            } catch (java.lang.NullPointerException nullpointerexception11) {
                throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                        nullpointerexception11);
            }

            f2 = f1;
        }

        try {
            if (f2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception10) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception10);
        }

        f1 = this.y - p171_0.y;

        try {
            if (java.lang.Float.isNaN(f1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception3) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception3);
        }

        label110:
        {
            try {
                if (f1 < 0.0F) {
                    f2 = -f1;
                    break label110;
                }
            } catch (java.lang.NullPointerException nullpointerexception9) {
                throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                        nullpointerexception9);
            }

            f2 = f1;
        }

        try {
            if (f2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception8) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception8);
        }

        f1 = this.z - p171_0.z;

        try {
            if (java.lang.Float.isNaN(f1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception2);
        }

        label96:
        {
            try {
                if (f1 < 0.0F) {
                    f2 = -f1;
                    break label96;
                }
            } catch (java.lang.NullPointerException nullpointerexception7) {
                throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                        nullpointerexception7);
            }

            f2 = f1;
        }

        try {
            if (f2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception6) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception6);
        }

        f1 = this.w - p171_0.w;

        try {
            if (java.lang.Float.isNaN(f1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception1);
        }

        label82:
        {
            try {
                if (f1 < 0.0F) {
                    f2 = -f1;
                    break label82;
                }
            } catch (java.lang.NullPointerException nullpointerexception5) {
                throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                        nullpointerexception5);
            }

            f2 = f1;
        }

        try {
            return !(f2 > p171_1);
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple4f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void absolute(Tuple4f p171_0) {
        this.x = java.lang.Math.abs(p171_0.x);
        this.y = java.lang.Math.abs(p171_0.y);
        this.z = java.lang.Math.abs(p171_0.z);
        this.w = java.lang.Math.abs(p171_0.w);
    }

    public final void absolute() {
        this.x = java.lang.Math.abs(this.x);
        this.y = java.lang.Math.abs(this.y);
        this.z = java.lang.Math.abs(this.z);
        this.w = java.lang.Math.abs(this.w);
    }

    public final float getX() {
        return this.x;
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

    public final void setW(float f) {
        this.w = f;
    }

    public boolean equals(Tuple4f p171_0) {
        try {
            return (this.x == p171_0.x
                    && this.y == p171_0.y
                    && this.z == p171_0.z
                    && this.w == p171_0.w);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final void set(Tuple4f p171_0) {
        this.x = this.x + p171_0.x;
        this.y = this.y + p171_0.y;
        this.z = this.z + p171_0.z;
        this.w = this.w + p171_0.w;
    }

    public final void add(
            Tuple4f tuple4f) {
        this.x = tuple4f.x;
        this.y = tuple4f.y;
        this.z = tuple4f.z;
        this.w = tuple4f.w;
    }

    public final void clampMax(float p171_0, Tuple4f p171_1) {
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

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Tuple4f tuple4f =
                    (remix.compat.vecmath.Tuple4f) p171_0;
            return (this.x == tuple4f.x
                    && this.y == tuple4f.y
                    && this.z == tuple4f.z
                    && this.w == tuple4f.w);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        }
    }

    static {
        remix.compat.vecmath.Tuple4f.packedTextFragments = new String[3];
        remix.compat.vecmath.Tuple4f.packedTextFragments[0] = new String("(");
        remix.compat.vecmath.Tuple4f.packedTextFragments[1] = new String(")");
    }

    public final float getZ() {
        return this.z;
    }

    public final void set(Tuple4d p171_0) {
        this.x = (float) p171_0.x;
        this.y = (float) p171_0.y;
        this.z = (float) p171_0.z;
        this.w = (float) p171_0.w;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Tuple4f(Tuple4f tuple4f) {
        super();
        this.x = tuple4f.x;
        this.y = tuple4f.y;
        this.z = tuple4f.z;
        this.w = tuple4f.w;
    }
}
