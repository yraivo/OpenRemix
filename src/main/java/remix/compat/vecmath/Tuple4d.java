package remix.compat.vecmath;

import java.io.Serializable;

public abstract class Tuple4d implements Serializable, Cloneable {
    static final long serialVersionUID = -4748953690425311052L;
    public double z;

    public double x;
    public double w;

    private static String[] packedTextFragments;
    public double y;

    public boolean equals(Tuple4d p171_0) {
        try {
            return (this.x == p171_0.x
                    && this.y == p171_0.y
                    && this.z == p171_0.z
                    && this.w == p171_0.w);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final double getY() {
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

    public final void set(
            double p171_0, double p171_1, double p171_2, double p171_3) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
        this.w = p171_3;
    }

    public final void setW(double d0) {
        this.w = d0;
    }

    public final void sub(
            Tuple4d p171_0, Tuple4d p171_1) {
        this.x = p171_0.x - p171_1.x;
        this.y = p171_0.y - p171_1.y;
        this.z = p171_0.z - p171_1.z;
        this.w = p171_0.w - p171_1.w;
    }

    public final void set(Tuple4f p171_0) {
        this.x = p171_0.x;
        this.y = p171_0.y;
        this.z = p171_0.z;
        this.w = p171_0.w;
    }

    public final void add(Tuple4d p171_0) {
        this.x = this.x + p171_0.x;
        this.y = this.y + p171_0.y;
        this.z = this.z + p171_0.z;
        this.w = this.w + p171_0.w;
    }

    public final void setX(double d0) {
        this.x = d0;
    }

    public final void absolute(Tuple4d p171_0) {
        this.x = java.lang.Math.abs(p171_0.x);
        this.y = java.lang.Math.abs(p171_0.y);
        this.z = java.lang.Math.abs(p171_0.z);
        this.w = java.lang.Math.abs(p171_0.w);
    }

    /** @deprecated */
    public final void scaleAdd(
            float f, Tuple4d tuple4d) {
        this.scaleAdd(f, tuple4d);
    }

    public Tuple4d(Tuple4f p171_0) {
        this.x = p171_0.x;
        this.y = p171_0.y;
        this.z = p171_0.z;
        this.w = p171_0.w;
    }

    /** @deprecated */
    public final void clampMin(float f) {
        this.clampMax(f);
    }

    /** @deprecated */
    public final void clamp(
            float p171_0, float p171_1, Tuple4d p171_2) {
        this.clamp(p171_0, p171_1, p171_2);
    }

    public final void sub(Tuple4d p171_0) {
        this.x = this.x - p171_0.x;
        this.y = this.y - p171_0.y;
        this.z = this.z - p171_0.z;
        this.w = this.w - p171_0.w;
    }

    public final void add(
            Tuple4d p171_0, Tuple4d p171_1) {
        this.x = p171_0.x + p171_1.x;
        this.y = p171_0.y + p171_1.y;
        this.z = p171_0.z + p171_1.z;
        this.w = p171_0.w + p171_1.w;
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

    /** @deprecated */
    public final void clampMax(float f) {
        this.clampMin(f);
    }

    public final void clamp(
            double p171_0, double p171_1, Tuple4d p171_2) {
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

    /** @deprecated */
    public final void clamp(float f, float f1) {
        this.clamp(f, f1);
    }

    public final void set(
            Tuple4d tuple4d) {
        this.x = tuple4d.x;
        this.y = tuple4d.y;
        this.z = tuple4d.z;
        this.w = tuple4d.w;
    }

    public final void clampMin(
            double p171_0, Tuple4d p171_1) {
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

    private static NullPointerException wrapNullPointerException(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    public Tuple4d(double p171_0, double p171_1, double p171_2, double p171_3) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
        this.w = p171_3;
    }

    public boolean epsilonEquals(Tuple4d p171_0, double p171_1) {
        double d1 = this.x - p171_0.x;

        try {
            if (java.lang.Double.isNaN(d1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception4) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception4);
        }

        double d2;
        label124:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label124;
                }
            } catch (java.lang.NullPointerException nullpointerexception11) {
                throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                        nullpointerexception11);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception10) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception10);
        }

        d1 = this.y - p171_0.y;

        try {
            if (java.lang.Double.isNaN(d1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception3) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception3);
        }

        label110:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label110;
                }
            } catch (java.lang.NullPointerException nullpointerexception9) {
                throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                        nullpointerexception9);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception8) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception8);
        }

        d1 = this.z - p171_0.z;

        try {
            if (java.lang.Double.isNaN(d1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception2);
        }

        label96:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label96;
                }
            } catch (java.lang.NullPointerException nullpointerexception7) {
                throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                        nullpointerexception7);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception6) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception6);
        }

        d1 = this.w - p171_0.w;

        try {
            if (java.lang.Double.isNaN(d1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception1);
        }

        label82:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label82;
                }
            } catch (java.lang.NullPointerException nullpointerexception5) {
                throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                        nullpointerexception5);
            }

            d2 = d1;
        }

        try {
            return !(d2 > p171_1);
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void clampMax(double p171_0) {
        try {
            if (this.x > p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception3) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception3);
        }

        try {
            if (this.y > p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception1);
        }

        try {
            if (this.z > p171_0) {
                this.z = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception2);
        }

        try {
            if (this.w > p171_0) {
                this.w = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void scale(
            double p171_0, Tuple4d p171_1) {
        this.x = p171_0 * p171_1.x;
        this.y = p171_0 * p171_1.y;
        this.z = p171_0 * p171_1.z;
        this.w = p171_0 * p171_1.w;
    }

    public final void scaleAdd(
            double p171_0, Tuple4d p171_1) {
        this.x = p171_0 * this.x + p171_1.x;
        this.y = p171_0 * this.y + p171_1.y;
        this.z = p171_0 * this.z + p171_1.z;
        this.w = p171_0 * this.w + p171_1.w;
    }

    public void interpolate(Tuple4d p171_0, double p171_1) {
        this.x = (1.0 - p171_1) * this.x + p171_1 * p171_0.x;
        this.y = (1.0 - p171_1) * this.y + p171_1 * p171_0.y;
        this.z = (1.0 - p171_1) * this.z + p171_1 * p171_0.z;
        this.w = (1.0 - p171_1) * this.w + p171_1 * p171_0.w;
    }

    public final void get(double[] p171_0) {
        p171_0[0] = this.x;
        p171_0[1] = this.y;
        p171_0[2] = this.z;
        p171_0[3] = this.w;
    }

    public final void setY(double d0) {
        this.y = d0;
    }

    public final double getW() {
        return this.w;
    }

    static {
        remix.compat.vecmath.Tuple4d.packedTextFragments = new String[3];
        remix.compat.vecmath.Tuple4d.packedTextFragments[0] = new String("(");
        remix.compat.vecmath.Tuple4d.packedTextFragments[1] = new String(")");
    }

    public final double getX() {
        return this.x;
    }

    public Tuple4d(double[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
        this.w = p171_0[3];
    }

    public final void scaleAdd(
            double p171_0, Tuple4d p171_1, Tuple4d p171_2) {
        this.x = p171_0 * p171_1.x + p171_2.x;
        this.y = p171_0 * p171_1.y + p171_2.y;
        this.z = p171_0 * p171_1.z + p171_2.z;
        this.w = p171_0 * p171_1.w + p171_2.w;
    }

    public final void set(double[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
        this.w = p171_0[3];
    }

    /** @deprecated */
    public final void clampMin(
            float f, Tuple4d tuple4d) {
        this.clampMin(f, tuple4d);
    }

    public final void scale(double p171_0) {
        this.x *= p171_0;
        this.y *= p171_0;
        this.z *= p171_0;
        this.w *= p171_0;
    }

    public final double getZ() {
        return this.z;
    }

    public void interpolate(
            Tuple4d p171_0, Tuple4d p171_1, double p171_2) {
        this.x = (1.0 - p171_2) * p171_0.x + p171_2 * p171_1.x;
        this.y = (1.0 - p171_2) * p171_0.y + p171_2 * p171_1.y;
        this.z = (1.0 - p171_2) * p171_0.z + p171_2 * p171_1.z;
        this.w = (1.0 - p171_2) * p171_0.w + p171_2 * p171_1.w;
    }

    /** @deprecated */
    public final void clampMax(
            float f, Tuple4d tuple4d) {
        this.clampMax(f, tuple4d);
    }

    public final void clampMin(double p171_0) {
        try {
            if (this.x < p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception3) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception3);
        }

        try {
            if (this.y < p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception1);
        }

        try {
            if (this.z < p171_0) {
                this.z = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception2);
        }

        try {
            if (this.w < p171_0) {
                this.w = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple4d.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Tuple4d tuple4d =
                    (remix.compat.vecmath.Tuple4d) p171_0;
            return (this.x == tuple4d.x
                    && this.y == tuple4d.y
                    && this.z == tuple4d.z
                    && this.w == tuple4d.w);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        }
    }

    @Override
    public int hashCode() {

        long i = 1L;
        i = VecMathUtil.hashDoubleToLongBits(i, this.x);
        i = VecMathUtil.hashDoubleToLongBits(i, this.y);
        i = VecMathUtil.hashDoubleToLongBits(i, this.z);
        i = VecMathUtil.hashDoubleToLongBits(i, this.w);
        return VecMathUtil.hashFinish(i);
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public Tuple4d() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.w = 0.0;
    }

    /** @deprecated */
    public void interpolate(Tuple4d p171_0, float p171_1) {
        this.interpolate(p171_0, p171_1);
    }

    public final void clampMax(
            double p171_0, Tuple4d p171_1) {
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

    public final void negate(Tuple4d p171_0) {
        this.x = -p171_0.x;
        this.y = -p171_0.y;
        this.z = -p171_0.z;
        this.w = -p171_0.w;
    }

    public final void get(
            Tuple4d tuple4d) {
        tuple4d.x = this.x;
        tuple4d.y = this.y;
        tuple4d.z = this.z;
        tuple4d.w = this.w;
    }

    /** @deprecated */
    public void interpolate(
            Tuple4d p171_0, Tuple4d p171_1, float p171_2) {
        this.interpolate(p171_0, p171_1, p171_2);
    }

    public final void setZ(double d0) {
        this.z = d0;
    }

    public final void absolute() {
        this.x = java.lang.Math.abs(this.x);
        this.y = java.lang.Math.abs(this.y);
        this.z = java.lang.Math.abs(this.z);
        this.w = java.lang.Math.abs(this.w);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Tuple4d(Tuple4d tuple4d) {
        super();
        this.x = tuple4d.x;
        this.y = tuple4d.y;
        this.z = tuple4d.z;
        this.w = tuple4d.w;
    }
}
