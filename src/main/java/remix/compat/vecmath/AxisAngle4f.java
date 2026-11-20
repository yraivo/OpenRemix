package remix.compat.vecmath;

import java.io.Serializable;

public class AxisAngle4f implements Serializable, Cloneable {

    private static String[] packedTextFragments;
    public float z;

    static final long serialVersionUID = -163246355858070601L;
    public float angle;
    public float y;
    public float x;
    static final double EPS = 1.0E-6;

    public final void set(
            AxisAngle4d axisAngle4d) {
        this.x = (float) axisAngle4d.x;
        this.y = (float) axisAngle4d.y;
        this.z = (float) axisAngle4d.z;
        this.angle = (float) axisAngle4d.angle;
    }

    private static NullPointerException wrapNullPointerException(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
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
                + this.angle
                + packedTextFragments[1];
    }

    public final void set(Quat4f p171_0) {
        label22:
        {
            java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
            double d0 = p171_0.x * p171_0.x + p171_0.y * p171_0.y + p171_0.z * p171_0.z;
            java.lang.String[] astring = astring1;
            if (d0 > 1.0E-6) {
                d0 = java.lang.Math.sqrt(d0);
                double d1 = 1.0 / d0;

                try {
                    this.x = (float) (p171_0.x * d1);
                    this.y = (float) (p171_0.y * d1);
                    this.z = (float) (p171_0.z * d1);
                    this.angle = (float) (2.0 * java.lang.Math.atan2(d0, p171_0.w));
                    if (astring == null) {
                        break label22;
                    }
                } catch (java.lang.NullPointerException nullpointerexception2) {
                    throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                            nullpointerexception2);
                }
            }

            try {
                this.x = 0.0F;
                this.y = 1.0F;
                this.z = 0.0F;
                this.angle = 0.0F;
            } catch (java.lang.NullPointerException nullpointerexception1) {
                throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                        nullpointerexception1);
            }
        }

        try {
            ;
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    static {
        remix.compat.vecmath.AxisAngle4f.packedTextFragments = new String[3];
        remix.compat.vecmath.AxisAngle4f.packedTextFragments[0] = new String("(");
        remix.compat.vecmath.AxisAngle4f.packedTextFragments[1] = new String(")");
    }

    public final void set(Matrix3f p171_0) {
        label22:
        {
            java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
            this.x = p171_0.m21 - p171_0.m12;
            java.lang.String[] astring = astring1;
            this.y = p171_0.m02 - p171_0.m20;
            this.z = p171_0.m10 - p171_0.m01;
            double d0 = this.x * this.x + this.y * this.y + this.z * this.z;
            if (d0 > 1.0E-6) {
                d0 = java.lang.Math.sqrt(d0);
                double d1 = 0.5 * d0;
                double d2 = 0.5 * (p171_0.m00 + p171_0.m11 + p171_0.m22 - 1.0);
                this.angle = (float) java.lang.Math.atan2(d1, d2);
                double d3 = 1.0 / d0;

                try {
                    this.x = (float) (this.x * d3);
                    this.y = (float) (this.y * d3);
                    this.z = (float) (this.z * d3);
                    if (astring == null) {
                        break label22;
                    }
                } catch (java.lang.NullPointerException nullpointerexception2) {
                    throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                            nullpointerexception2);
                }
            }

            try {
                this.x = 0.0F;
                this.y = 1.0F;
                this.z = 0.0F;
                this.angle = 0.0F;
            } catch (java.lang.NullPointerException nullpointerexception1) {
                throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                        nullpointerexception1);
            }
        }

        try {
            ;
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void set(Vector3f p171_0, float p171_1) {
        this.x = p171_0.x;
        this.y = p171_0.y;
        this.z = p171_0.z;
        this.angle = p171_1;
    }

    public boolean epsilonEquals(AxisAngle4f p171_0, float p171_1) {
        float f1 = this.x - p171_0.x;

        float f2;
        label96:
        {
            try {
                if (f1 < 0.0F) {
                    f2 = -f1;
                    break label96;
                }
            } catch (java.lang.NullPointerException nullpointerexception7) {
                throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                        nullpointerexception7);
            }

            f2 = f1;
        }

        try {
            if (f2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception6) {
            throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                    nullpointerexception6);
        }

        f1 = this.y - p171_0.y;

        label82:
        {
            try {
                if (f1 < 0.0F) {
                    f2 = -f1;
                    break label82;
                }
            } catch (java.lang.NullPointerException nullpointerexception5) {
                throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                        nullpointerexception5);
            }

            f2 = f1;
        }

        try {
            if (f2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception4) {
            throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                    nullpointerexception4);
        }

        f1 = this.z - p171_0.z;

        label68:
        {
            try {
                if (f1 < 0.0F) {
                    f2 = -f1;
                    break label68;
                }
            } catch (java.lang.NullPointerException nullpointerexception3) {
                throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                        nullpointerexception3);
            }

            f2 = f1;
        }

        try {
            if (f2 > p171_1) {
                return false;
            }
        } catch (java.lang.NullPointerException nullpointerexception2) {
            throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                    nullpointerexception2);
        }

        f1 = this.angle - p171_0.angle;

        label54:
        {
            try {
                if (f1 < 0.0F) {
                    f2 = -f1;
                    break label54;
                }
            } catch (java.lang.NullPointerException nullpointerexception1) {
                throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                        nullpointerexception1);
            }

            f2 = f1;
        }

        try {
            return !(f2 > p171_1);
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void setAngle(float f) {
        this.angle = f;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }
    }

    public AxisAngle4f(float p171_0, float p171_1, float p171_2, float p171_3) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
        this.angle = p171_3;
    }

    public AxisAngle4f(float[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
        this.angle = p171_0[3];
    }

    public final void setY(float f) {
        this.y = f;
    }

    public final void get(float[] p171_0) {
        p171_0[0] = this.x;
        p171_0[1] = this.y;
        p171_0[2] = this.z;
        p171_0[3] = this.angle;
    }

    // region String decoding support

    // endregion

    public final void set(
            float p171_0, float p171_1, float p171_2, float p171_3) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
        this.angle = p171_3;
    }

    public final void set(
            Matrix3d matrix3d) {
        String[] astring1 = Tuple3f.hF();
        this.x = (float) (matrix3d.m21 - matrix3d.m12);
        this.y = (float) (matrix3d.m02 - matrix3d.m20);
        this.z = (float) (matrix3d.m10 - matrix3d.m01);
        double d0 = this.x * this.x + this.y * this.y + this.z * this.z;
        String[] astring = astring1;
        if (d0 > 1.0E-6) {
            d0 = Math.sqrt(d0);
            double d1 = 0.5 * d0;
            double d2 =
                    0.5
                            * (matrix3d.m00
                                    + matrix3d.m11
                                    + matrix3d.m22
                                    - 1.0);
            this.angle = (float) Math.atan2(d1, d2);
            double d3 = 1.0 / d0;
            this.x = (float) (this.x * d3);
            this.y = (float) (this.y * d3);
            this.z = (float) (this.z * d3);
            if (astring == null) {
                return;
            }
        }

        this.x = 0.0F;
        this.y = 1.0F;
        this.z = 0.0F;
        this.angle = 0.0F;
    }

    public final void set(
            Matrix4d matrix4d) {
        Matrix3d matrix3d = new Matrix3d();
        matrix4d.get(matrix3d);
        this.x = (float) (matrix3d.m21 - matrix3d.m12);
        this.y = (float) (matrix3d.m02 - matrix3d.m20);
        String[] astring1 = Tuple3f.hF();
        this.z = (float) (matrix3d.m10 - matrix3d.m01);
        String[] astring = astring1;
        double d0 = this.x * this.x + this.y * this.y + this.z * this.z;
        if (d0 > 1.0E-6) {
            d0 = Math.sqrt(d0);
            double d1 = 0.5 * d0;
            double d2 =
                    0.5
                            * (matrix3d.m00
                                    + matrix3d.m11
                                    + matrix3d.m22
                                    - 1.0);
            this.angle = (float) Math.atan2(d1, d2);
            double d3 = 1.0 / d0;
            this.x = (float) (this.x * d3);
            this.y = (float) (this.y * d3);
            this.z = (float) (this.z * d3);
            if (astring == null) {
                return;
            }
        }

        this.x = 0.0F;
        this.y = 1.0F;
        this.z = 0.0F;
        this.angle = 0.0F;
    }

    public final float getAngle() {
        return this.angle;
    }

    public final float getX() {
        return this.x;
    }

    public AxisAngle4f() {
        this.x = 0.0F;
        this.y = 0.0F;
        this.z = 1.0F;
        this.angle = 0.0F;
    }

    @Override
    public int hashCode() {

        long i = 1L;
        i = VecMathUtil.hashFloatToIntBits(i, this.x);
        i = VecMathUtil.hashFloatToIntBits(i, this.y);
        i = VecMathUtil.hashFloatToIntBits(i, this.z);
        i = VecMathUtil.hashFloatToIntBits(i, this.angle);
        return VecMathUtil.hashFinish(i);
    }

    public boolean equals(AxisAngle4f p171_0) {
        try {
            return (this.x == p171_0.x
                    && this.y == p171_0.y
                    && this.z == p171_0.z
                    && this.angle == p171_0.angle);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final void set(AxisAngle4f p171_0) {
        this.x = p171_0.x;
        this.y = p171_0.y;
        this.z = p171_0.z;
        this.angle = p171_0.angle;
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.AxisAngle4f axisAngle4f =
                    (remix.compat.vecmath.AxisAngle4f) p171_0;
            return (this.x == axisAngle4f.x
                    && this.y == axisAngle4f.y
                    && this.z == axisAngle4f.z
                    && this.angle == axisAngle4f.angle);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        }
    }

    public final float getY() {
        return this.y;
    }

    public final void set(Quat4d p171_0) {
        label22:
        {
            java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
            double d0 = p171_0.x * p171_0.x + p171_0.y * p171_0.y + p171_0.z * p171_0.z;
            java.lang.String[] astring = astring1;
            if (d0 > 1.0E-6) {
                d0 = java.lang.Math.sqrt(d0);
                double d1 = 1.0 / d0;

                try {
                    this.x = (float) (p171_0.x * d1);
                    this.y = (float) (p171_0.y * d1);
                    this.z = (float) (p171_0.z * d1);
                    this.angle = (float) (2.0 * java.lang.Math.atan2(d0, p171_0.w));
                    if (astring == null) {
                        break label22;
                    }
                } catch (java.lang.NullPointerException nullpointerexception2) {
                    throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                            nullpointerexception2);
                }
            }

            try {
                this.x = 0.0F;
                this.y = 1.0F;
                this.z = 0.0F;
                this.angle = 0.0F;
            } catch (java.lang.NullPointerException nullpointerexception1) {
                throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                        nullpointerexception1);
            }
        }

        try {
            ;
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    public final void set(float[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
        this.angle = p171_0[3];
    }

    public final void setX(float f) {
        this.x = f;
    }

    public final float getZ() {
        return this.z;
    }

    public final void setZ(float f) {
        this.z = f;
    }

    public AxisAngle4f(Vector3f p171_0, float p171_1) {
        this.x = p171_0.x;
        this.y = p171_0.y;
        this.z = p171_0.z;
        this.angle = p171_1;
    }

    public final void set(Matrix4f p171_0) {
        label22:
        {
            remix.compat.vecmath.Matrix3f matrix3f =
                    new remix.compat.vecmath.Matrix3f();
            java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
            p171_0.get(matrix3f);
            this.x = matrix3f.m21 - matrix3f.m12;
            this.y = matrix3f.m02 - matrix3f.m20;
            java.lang.String[] astring = astring1;
            this.z = matrix3f.m10 - matrix3f.m01;
            double d0 = this.x * this.x + this.y * this.y + this.z * this.z;
            if (d0 > 1.0E-6) {
                d0 = java.lang.Math.sqrt(d0);
                double d1 = 0.5 * d0;
                double d2 =
                        0.5
                                * (matrix3f.m00
                                        + matrix3f.m11
                                        + matrix3f.m22
                                        - 1.0);
                this.angle = (float) java.lang.Math.atan2(d1, d2);
                double d3 = 1.0 / d0;

                try {
                    this.x = (float) (this.x * d3);
                    this.y = (float) (this.y * d3);
                    this.z = (float) (this.z * d3);
                    if (astring == null) {
                        break label22;
                    }
                } catch (java.lang.NullPointerException nullpointerexception2) {
                    throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                            nullpointerexception2);
                }
            }

            try {
                this.x = 0.0F;
                this.y = 1.0F;
                this.z = 0.0F;
                this.angle = 0.0F;
            } catch (java.lang.NullPointerException nullpointerexception1) {
                throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                        nullpointerexception1);
            }
        }

        try {
            ;
        } catch (java.lang.NullPointerException nullpointerexception) {
            throw remix.compat.vecmath.AxisAngle4f.wrapNullPointerException(
                    nullpointerexception);
        }
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public AxisAngle4f(AxisAngle4d axisAngle4d) {
        super();
        this.x = (float) axisAngle4d.x;
        this.y = (float) axisAngle4d.y;
        this.z = (float) axisAngle4d.z;
        this.angle = (float) axisAngle4d.angle;
    }

    public AxisAngle4f(AxisAngle4f axisAngle4f) {
        super();
        this.x = axisAngle4f.x;
        this.y = axisAngle4f.y;
        this.z = axisAngle4f.z;
        this.angle = axisAngle4f.angle;
    }
}
