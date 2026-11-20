package remix.compat.vecmath;

import java.io.Serializable;

public class AxisAngle4d implements Serializable, Cloneable {
    public double x;

    private static String[] packedTextFragments;
    public double angle;
    static final long serialVersionUID = 3644296204459140589L;
    public double y;
    public double z;
    static final double EPS = 1.0E-12;

    public boolean epsilonEquals(
            AxisAngle4d axisAngle4d, double d0) {
        double d1 = this.x - axisAngle4d.x;
        if ((d1 < 0.0 ? -d1 : d1) > d0) {
            return false;
        }

        d1 = this.y - axisAngle4d.y;
        if ((d1 < 0.0 ? -d1 : d1) > d0) {
            return false;
        }

        d1 = this.z - axisAngle4d.z;
        if ((d1 < 0.0 ? -d1 : d1) > d0) {
            return false;
        }

        d1 = this.angle - axisAngle4d.angle;
        return !((d1 < 0.0 ? -d1 : d1) > d0);
    }

    public AxisAngle4d(double d0, double d1, double d2, double d3) {
        this.x = d0;
        this.y = d1;
        this.z = d2;
        this.angle = d3;
    }

    public AxisAngle4d(
            Vector3d vector3d, double d0) {
        this.x = vector3d.x;
        this.y = vector3d.y;
        this.z = vector3d.z;
        this.angle = d0;
    }

    public final void set(
            AxisAngle4f axisAngle4f) {
        this.x = axisAngle4f.x;
        this.y = axisAngle4f.y;
        this.z = axisAngle4f.z;
        this.angle = axisAngle4f.angle;
    }

    public double getZ() {
        return this.z;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException clonenotsupportedexception) {
            throw new InternalError();
        }
    }

    public final void setX(double d0) {
        this.x = d0;
    }

    public AxisAngle4d() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 1.0;
        this.angle = 0.0;
    }

    public final void set(
            Quat4f quat4f) {
        String[] astring1 = Tuple3f.hF();
        double d0 =
                quat4f.x * quat4f.x
                        + quat4f.y * quat4f.y
                        + quat4f.z * quat4f.z;
        String[] astring = astring1;
        if (d0 > 1.0E-12) {
            d0 = Math.sqrt(d0);
            double d1 = 1.0 / d0;
            this.x = quat4f.x * d1;
            this.y = quat4f.y * d1;
            this.z = quat4f.z * d1;
            this.angle = 2.0 * Math.atan2(d0, quat4f.w);
            if (astring == null) {
                return;
            }
        }

        this.x = 0.0;
        this.y = 1.0;
        this.z = 0.0;
        this.angle = 0.0;
    }

    public final void set(
            Vector3d vector3d, double d0) {
        this.x = vector3d.x;
        this.y = vector3d.y;
        this.z = vector3d.z;
        this.angle = d0;
    }

    public final void set(
            Matrix4d matrix4d) {
        String[] astring1 = Tuple3f.hF();
        Matrix3d matrix3d = new Matrix3d();
        matrix4d.get(matrix3d);
        this.x = (float) (matrix3d.m21 - matrix3d.m12);
        this.y = (float) (matrix3d.m02 - matrix3d.m20);
        String[] astring = astring1;
        this.z = (float) (matrix3d.m10 - matrix3d.m01);
        double d0 = this.x * this.x + this.y * this.y + this.z * this.z;
        if (d0 > 1.0E-12) {
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
            this.x *= d3;
            this.y *= d3;
            this.z *= d3;
            if (astring == null) {
                return;
            }
        }

        this.x = 0.0;
        this.y = 1.0;
        this.z = 0.0;
        this.angle = 0.0;
    }

    public AxisAngle4d(double[] adouble) {
        this.x = adouble[0];
        this.y = adouble[1];
        this.z = adouble[2];
        this.angle = adouble[3];
    }

    public final void set(
            Matrix4f matrix4f) {
        Matrix3d matrix3d = new Matrix3d();
        matrix4f.get(matrix3d);
        this.x = (float) (matrix3d.m21 - matrix3d.m12);
        this.y = (float) (matrix3d.m02 - matrix3d.m20);
        String[] astring1 = Tuple3f.hF();
        this.z = (float) (matrix3d.m10 - matrix3d.m01);
        String[] astring = astring1;
        double d0 = this.x * this.x + this.y * this.y + this.z * this.z;
        if (d0 > 1.0E-12) {
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
            this.x *= d3;
            this.y *= d3;
            this.z *= d3;
            if (astring == null) {
                return;
            }
        }

        this.x = 0.0;
        this.y = 1.0;
        this.z = 0.0;
        this.angle = 0.0;
    }

    public final void set(
            Quat4d quat4d) {
        String[] astring1 = Tuple3f.hF();
        double d0 =
                quat4d.x * quat4d.x
                        + quat4d.y * quat4d.y
                        + quat4d.z * quat4d.z;
        String[] astring = astring1;
        if (d0 > 1.0E-12) {
            d0 = Math.sqrt(d0);
            double d1 = 1.0 / d0;
            this.x = quat4d.x * d1;
            this.y = quat4d.y * d1;
            this.z = quat4d.z * d1;
            this.angle = 2.0 * Math.atan2(d0, quat4d.w);
            if (astring == null) {
                return;
            }
        }

        this.x = 0.0;
        this.y = 1.0;
        this.z = 0.0;
        this.angle = 0.0;
    }

    public final void set(
            AxisAngle4d axisAngle4d) {
        this.x = axisAngle4d.x;
        this.y = axisAngle4d.y;
        this.z = axisAngle4d.z;
        this.angle = axisAngle4d.angle;
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

    public double getX() {
        return this.x;
    }

    public final void set(double[] adouble) {
        this.x = adouble[0];
        this.y = adouble[1];
        this.z = adouble[2];
        this.angle = adouble[3];
    }

    public final double getAngle() {
        return this.angle;
    }

    public final double getY() {
        return this.y;
    }

    public final void set(
            Matrix3f matrix3f) {
        this.x = matrix3f.m21 - matrix3f.m12;
        this.y = matrix3f.m02 - matrix3f.m20;
        String[] astring1 = Tuple3f.hF();
        this.z = matrix3f.m10 - matrix3f.m01;
        double d0 = this.x * this.x + this.y * this.y + this.z * this.z;
        String[] astring = astring1;
        if (d0 > 1.0E-12) {
            d0 = Math.sqrt(d0);
            double d1 = 0.5 * d0;
            double d2 =
                    0.5
                            * (matrix3f.m00
                                    + matrix3f.m11
                                    + matrix3f.m22
                                    - 1.0);
            this.angle = (float) Math.atan2(d1, d2);
            double d3 = 1.0 / d0;
            this.x *= d3;
            this.y *= d3;
            this.z *= d3;
            if (astring == null) {
                return;
            }
        }

        this.x = 0.0;
        this.y = 1.0;
        this.z = 0.0;
        this.angle = 0.0;
    }

    @Override
    public boolean equals(Object object) {
        try {
            AxisAngle4d axisAngle4d =
                    (AxisAngle4d) object;
            return this.x == axisAngle4d.x
                    && this.y == axisAngle4d.y
                    && this.z == axisAngle4d.z
                    && this.angle == axisAngle4d.angle;
        } catch (NullPointerException nullpointerexception) {
            return false;
        } catch (ClassCastException classcastexception) {
            return false;
        }
    }

    public final void setY(double d0) {
        this.y = d0;
    }

    public final void setZ(double d0) {
        this.z = d0;
    }

    public boolean equals(
            AxisAngle4d axisAngle4d) {
        try {
            return this.x == axisAngle4d.x
                    && this.y == axisAngle4d.y
                    && this.z == axisAngle4d.z
                    && this.angle == axisAngle4d.angle;
        } catch (NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final void set(
            Matrix3d matrix3d) {
        String[] astring1 = Tuple3f.hF();
        this.x = (float) (matrix3d.m21 - matrix3d.m12);
        this.y = (float) (matrix3d.m02 - matrix3d.m20);
        this.z = (float) (matrix3d.m10 - matrix3d.m01);
        double d0 = this.x * this.x + this.y * this.y + this.z * this.z;
        String[] astring = astring1;
        if (d0 > 1.0E-12) {
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
            this.x *= d3;
            this.y *= d3;
            this.z *= d3;
            if (astring == null) {
                return;
            }
        }

        this.x = 0.0;
        this.y = 1.0;
        this.z = 0.0;
        this.angle = 0.0;
    }

    private static NullPointerException wrapNullPointerException(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    public final void setAngle(double d0) {
        this.angle = d0;
    }

    @Override
    public int hashCode() {

        long i = 1L;
        i = VecMathUtil.hashDoubleToLongBits(i, this.x);
        i = VecMathUtil.hashDoubleToLongBits(i, this.y);
        i = VecMathUtil.hashDoubleToLongBits(i, this.z);
        i = VecMathUtil.hashDoubleToLongBits(i, this.angle);
        return VecMathUtil.hashFinish(i);
    }

    static {
        remix.compat.vecmath.AxisAngle4d.packedTextFragments = new String[3];
        remix.compat.vecmath.AxisAngle4d.packedTextFragments[0] = new String("(");
        remix.compat.vecmath.AxisAngle4d.packedTextFragments[1] = new String(")");
    }

    public final void get(double[] adouble) {
        adouble[0] = this.x;
        adouble[1] = this.y;
        adouble[2] = this.z;
        adouble[3] = this.angle;
    }

    public final void set(double d0, double d1, double d2, double d3) {
        this.x = d0;
        this.y = d1;
        this.z = d2;
        this.angle = d3;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public AxisAngle4d(AxisAngle4f axisAngle4f) {
        super();
        this.x = axisAngle4f.x;
        this.y = axisAngle4f.y;
        this.z = axisAngle4f.z;
        this.angle = axisAngle4f.angle;
    }

    public AxisAngle4d(AxisAngle4d axisAngle4d) {
        super();
        this.x = axisAngle4d.x;
        this.y = axisAngle4d.y;
        this.z = axisAngle4d.z;
        this.angle = axisAngle4d.angle;
    }
}
