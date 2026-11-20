package remix.compat.vecmath;

import java.io.Serializable;

public abstract class Tuple3d implements Serializable, Cloneable {
    public double z;
    private static String[] packedTextFragments;
    public double y;

    static final long serialVersionUID = 5542096614926168415L;

    public double x;

    public final void interpolate(
            Tuple3d p171_0, Tuple3d p171_1, double p171_2) {
        this.x = (1.0 - p171_2) * p171_0.x + p171_2 * p171_1.x;
        this.y = (1.0 - p171_2) * p171_0.y + p171_2 * p171_1.y;
        this.z = (1.0 - p171_2) * p171_0.z + p171_2 * p171_1.z;
    }

    public final void clampMin(double p171_0) {
        try {
            if (this.x < p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple3d.wrapException(
                    nullpointerexception1);
        }

        try {
            if (this.y < p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple3d.wrapException(
                    nullpointerexception2);
        }

        try {
            if (this.z < p171_0) {
                this.z = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple3d.wrapException(
                    nullpointerexception);
        }
    }

    public final void scale(double p171_0) {
        this.x *= p171_0;
        this.y *= p171_0;
        this.z *= p171_0;
    }

    public final void set(Tuple3f p171_0) {
        this.x = p171_0.x;
        this.y = p171_0.y;
        this.z = p171_0.z;
    }

    public final void add(
            Tuple3d p171_0, Tuple3d p171_1) {
        this.x = p171_0.x + p171_1.x;
        this.y = p171_0.y + p171_1.y;
        this.z = p171_0.z + p171_1.z;
    }

    /** @deprecated */
    public final void clamp(
            float p171_0, float p171_1, Tuple3d p171_2) {
        this.clamp(p171_0, p171_1, p171_2);
    }

    public final void sub(
            Tuple3d p171_0, Tuple3d p171_1) {
        this.x = p171_0.x - p171_1.x;
        this.y = p171_0.y - p171_1.y;
        this.z = p171_0.z - p171_1.z;
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Tuple3d tuple3d =
                    (remix.compat.vecmath.Tuple3d) p171_0;
            return (this.x == tuple3d.x
                    && this.y == tuple3d.y
                    && this.z == tuple3d.z);
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final void scaleAdd(
            double p171_0, Tuple3d p171_1, Tuple3d p171_2) {
        this.x = p171_0 * p171_1.x + p171_2.x;
        this.y = p171_0 * p171_1.y + p171_2.y;
        this.z = p171_0 * p171_1.z + p171_2.z;
    }

    /** @deprecated */
    public final void clamp(float f, float f1) {
        this.clamp(f, f1);
    }

    public final void get(double[] p171_0) {
        p171_0[0] = this.x;
        p171_0[1] = this.y;
        p171_0[2] = this.z;
    }

    /** @deprecated */
    public final void clampMin(
            float f, Tuple3d tuple3d) {
        this.clampMin(f, tuple3d);
    }

    public final void absolute() {
        this.x = java.lang.Math.abs(this.x);
        this.y = java.lang.Math.abs(this.y);
        this.z = java.lang.Math.abs(this.z);
    }

    public final void interpolate(
            Tuple3d p171_0, double p171_1) {
        this.x = (1.0 - p171_1) * this.x + p171_1 * p171_0.x;
        this.y = (1.0 - p171_1) * this.y + p171_1 * p171_0.y;
        this.z = (1.0 - p171_1) * this.z + p171_1 * p171_0.z;
    }

    private static RuntimeException wrapException(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    /** @deprecated */
    public final void clampMin(float f) {
        this.clampMax(f);
    }

    public final void set(double[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
    }

    @Override
    public int hashCode() {

        long i = 1L;
        i = VecMathUtil.hashDoubleToLongBits(i, this.x);
        i = VecMathUtil.hashDoubleToLongBits(i, this.y);
        i = VecMathUtil.hashDoubleToLongBits(i, this.z);
        return VecMathUtil.hashFinish(i);
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public final double getZ() {
        return this.z;
    }

    public boolean epsilonEquals(Tuple3d p171_0, double p171_1) {
        double d1 = this.x - p171_0.x;

        try {
            if (java.lang.Double.isNaN(d1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception3) {
            throw remix.compat.vecmath.Tuple3d.wrapException(
                    nullpointerexception3);
        }

        double d2;
        label93:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label93;
                }
            } catch (java.lang.NullPointerException nullpointerexception8) {
                throw remix.compat.vecmath.Tuple3d.wrapException(
                        nullpointerexception8);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception7) {
            throw remix.compat.vecmath.Tuple3d.wrapException(
                    nullpointerexception7);
        }

        d1 = this.y - p171_0.y;

        try {
            if (java.lang.Double.isNaN(d1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple3d.wrapException(
                    nullpointerexception2);
        }

        label79:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label79;
                }
            } catch (java.lang.NullPointerException nullpointerexception6) {
                throw remix.compat.vecmath.Tuple3d.wrapException(
                        nullpointerexception6);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception5) {
            throw remix.compat.vecmath.Tuple3d.wrapException(
                    nullpointerexception5);
        }

        d1 = this.z - p171_0.z;

        try {
            if (java.lang.Double.isNaN(d1)) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple3d.wrapException(
                    nullpointerexception1);
        }

        label65:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label65;
                }
            } catch (java.lang.NullPointerException nullpointerexception4) {
                throw remix.compat.vecmath.Tuple3d.wrapException(
                        nullpointerexception4);
            }

            d2 = d1;
        }

        try {
            return !(d2 > p171_1);
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple3d.wrapException(
                    nullpointerexception);
        }
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

    public Tuple3d(double[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
    }

    public final void get(
            Tuple3d tuple3d) {
        tuple3d.x = this.x;
        tuple3d.y = this.y;
        tuple3d.z = this.z;
    }

    public final void clampMin(
            double p171_0, Tuple3d p171_1) {
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

    public boolean equals(Tuple3d p171_0) {
        try {
            return (this.x == p171_0.x && this.y == p171_0.y && this.z == p171_0.z);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    /** @deprecated */
    public final void clampMax(float f) {
        this.clampMin(f);
    }

    public final void sub(Tuple3d p171_0) {
        this.x = this.x - p171_0.x;
        this.y = this.y - p171_0.y;
        this.z = this.z - p171_0.z;
    }

    /** @deprecated */
    public final void scaleAdd(
            double p171_0, Tuple3f p171_1) {
        this.scaleAdd(p171_0, new remix.compat.vecmath.Point3d(p171_1));
    }

    public Tuple3d() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    /** @deprecated */
    public final void clampMax(
            float f, Tuple3d tuple3d) {
        this.clampMax(f, tuple3d);
    }

    public final void clampMax(
            double p171_0, Tuple3d p171_1) {
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

    public final void set(double p171_0, double p171_1, double p171_2) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
    }

    public final void scaleAdd(
            double p171_0, Tuple3d p171_1) {
        this.x = p171_0 * this.x + p171_1.x;
        this.y = p171_0 * this.y + p171_1.y;
        this.z = p171_0 * this.z + p171_1.z;
    }

    public final void setX(double d0) {
        this.x = d0;
    }

    public final void clampMax(double p171_0) {
        try {
            if (this.x > p171_0) {
                this.x = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception1) {
            throw remix.compat.vecmath.Tuple3d.wrapException(
                    nullpointerexception1);
        }

        try {
            if (this.y > p171_0) {
                this.y = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.Tuple3d.wrapException(
                    nullpointerexception2);
        }

        try {
            if (this.z > p171_0) {
                this.z = p171_0;
            }
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.Tuple3d.wrapException(
                    nullpointerexception);
        }
    }

    public final void setY(double d0) {
        this.y = d0;
    }

    public Tuple3d(double p171_0, double p171_1, double p171_2) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
    }

    public final void setZ(double d0) {
        this.z = d0;
    }

    public Tuple3d(Tuple3f p171_0) {
        this.x = p171_0.x;
        this.y = p171_0.y;
        this.z = p171_0.z;
    }

    static {
        remix.compat.vecmath.Tuple3d.packedTextFragments = new String[3];
        remix.compat.vecmath.Tuple3d.packedTextFragments[0] = new String("(");
        remix.compat.vecmath.Tuple3d.packedTextFragments[1] = new String(")");
    }

    public final void scale(
            double p171_0, Tuple3d p171_1) {
        this.x = p171_0 * p171_1.x;
        this.y = p171_0 * p171_1.y;
        this.z = p171_0 * p171_1.z;
    }

    /** @deprecated */
    public final void interpolate(
            Tuple3d p171_0, Tuple3d p171_1, float p171_2) {
        this.interpolate(p171_0, p171_1, p171_2);
    }

    public final double getY() {
        return this.y;
    }

    public final void absolute(Tuple3d p171_0) {
        this.x = java.lang.Math.abs(p171_0.x);
        this.y = java.lang.Math.abs(p171_0.y);
        this.z = java.lang.Math.abs(p171_0.z);
    }

    /** @deprecated */
    public final void interpolate(
            Tuple3d tuple3d, float f) {
        this.interpolate(tuple3d, f);
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

    public final void negate(Tuple3d p171_0) {
        this.x = -p171_0.x;
        this.y = -p171_0.y;
        this.z = -p171_0.z;
    }

    public final void add(Tuple3d p171_0) {
        this.x = this.x + p171_0.x;
        this.y = this.y + p171_0.y;
        this.z = this.z + p171_0.z;
    }

    public final double getX() {
        return this.x;
    }

    public final void scale(Tuple3d p171_0) {
        this.x = p171_0.x;
        this.y = p171_0.y;
        this.z = p171_0.z;
    }

    public final void clamp(
            double p171_0, double p171_1, Tuple3d p171_2) {
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

    // Recovered explicit constructors from original mapped instruction streams.
    public Tuple3d(Tuple3d tuple3d) {
        super();
        this.x = tuple3d.x;
        this.y = tuple3d.y;
        this.z = tuple3d.z;
    }
}
