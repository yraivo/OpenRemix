package remix.compat.vecmath;

import net.minecraft.entity.Entity;

import java.io.Serializable;

public class Matrix4d implements Serializable, Cloneable {
    public double m00;
    public double m10;
    private static String[] packedTextFragments;

    public double m30;
    public double m13;
    public double m33;
    public double m20;
    public double m31;
    public double m22;
    public double m32;

    public double m12;
    static final long serialVersionUID = 8223903484171633710L;
    private static final double EPS = 1.0E-10;
    public double m11;
    public double m03;
    public double m21;
    public double m02;
    public double m01;
    public double m23;

    public final void transform(
            Vector3f p171_0, Vector3f p171_1) {
        float f = (float) (this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z);
        float f1 = (float) (this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z);
        p171_1.z = (float) (this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z);
        p171_1.x = f;
        p171_1.y = f1;
    }

    public final void method_b70e132d508586a03fb2(
            Matrix4d matrix4d,
            Matrix4d matrix4dValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix4d && this != matrix4dValue) {
            this.m00 =
                    matrix4d.m00 * matrix4dValue.m00
                            + matrix4d.m10 * matrix4dValue.m01
                            + matrix4d.m20 * matrix4dValue.m02
                            + matrix4d.m30 * matrix4dValue.m03;
            this.m01 =
                    matrix4d.m00 * matrix4dValue.m10
                            + matrix4d.m10 * matrix4dValue.m11
                            + matrix4d.m20 * matrix4dValue.m12
                            + matrix4d.m30 * matrix4dValue.m13;
            this.m02 =
                    matrix4d.m00 * matrix4dValue.m20
                            + matrix4d.m10 * matrix4dValue.m21
                            + matrix4d.m20 * matrix4dValue.m22
                            + matrix4d.m30 * matrix4dValue.m23;
            this.m03 =
                    matrix4d.m00 * matrix4dValue.m30
                            + matrix4d.m10 * matrix4dValue.m31
                            + matrix4d.m20 * matrix4dValue.m32
                            + matrix4d.m30 * matrix4dValue.m33;
            this.m10 =
                    matrix4d.m01 * matrix4dValue.m00
                            + matrix4d.m11 * matrix4dValue.m01
                            + matrix4d.m21 * matrix4dValue.m02
                            + matrix4d.m31 * matrix4dValue.m03;
            this.m11 =
                    matrix4d.m01 * matrix4dValue.m10
                            + matrix4d.m11 * matrix4dValue.m11
                            + matrix4d.m21 * matrix4dValue.m12
                            + matrix4d.m31 * matrix4dValue.m13;
            this.m12 =
                    matrix4d.m01 * matrix4dValue.m20
                            + matrix4d.m11 * matrix4dValue.m21
                            + matrix4d.m21 * matrix4dValue.m22
                            + matrix4d.m31 * matrix4dValue.m23;
            this.m13 =
                    matrix4d.m01 * matrix4dValue.m30
                            + matrix4d.m11 * matrix4dValue.m31
                            + matrix4d.m21 * matrix4dValue.m32
                            + matrix4d.m31 * matrix4dValue.m33;
            this.m20 =
                    matrix4d.m02 * matrix4dValue.m00
                            + matrix4d.m12 * matrix4dValue.m01
                            + matrix4d.m22 * matrix4dValue.m02
                            + matrix4d.m32 * matrix4dValue.m03;
            this.m21 =
                    matrix4d.m02 * matrix4dValue.m10
                            + matrix4d.m12 * matrix4dValue.m11
                            + matrix4d.m22 * matrix4dValue.m12
                            + matrix4d.m32 * matrix4dValue.m13;
            this.m22 =
                    matrix4d.m02 * matrix4dValue.m20
                            + matrix4d.m12 * matrix4dValue.m21
                            + matrix4d.m22 * matrix4dValue.m22
                            + matrix4d.m32 * matrix4dValue.m23;
            this.m23 =
                    matrix4d.m02 * matrix4dValue.m30
                            + matrix4d.m12 * matrix4dValue.m31
                            + matrix4d.m22 * matrix4dValue.m32
                            + matrix4d.m32 * matrix4dValue.m33;
            this.m30 =
                    matrix4d.m03 * matrix4dValue.m00
                            + matrix4d.m13 * matrix4dValue.m01
                            + matrix4d.m23 * matrix4dValue.m02
                            + matrix4d.m33 * matrix4dValue.m03;
            this.m31 =
                    matrix4d.m03 * matrix4dValue.m10
                            + matrix4d.m13 * matrix4dValue.m11
                            + matrix4d.m23 * matrix4dValue.m12
                            + matrix4d.m33 * matrix4dValue.m13;
            this.m32 =
                    matrix4d.m03 * matrix4dValue.m20
                            + matrix4d.m13 * matrix4dValue.m21
                            + matrix4d.m23 * matrix4dValue.m22
                            + matrix4d.m33 * matrix4dValue.m23;
            this.m33 =
                    matrix4d.m03 * matrix4dValue.m30
                            + matrix4d.m13 * matrix4dValue.m31
                            + matrix4d.m23 * matrix4dValue.m32
                            + matrix4d.m33 * matrix4dValue.m33;
            if (astring == null) {
                return;
            }
        }

        double d0 =
                matrix4d.m00 * matrix4dValue.m00
                        + matrix4d.m10 * matrix4dValue.m01
                        + matrix4d.m20 * matrix4dValue.m02
                        + matrix4d.m30 * matrix4dValue.m03;
        double d1 =
                matrix4d.m00 * matrix4dValue.m10
                        + matrix4d.m10 * matrix4dValue.m11
                        + matrix4d.m20 * matrix4dValue.m12
                        + matrix4d.m30 * matrix4dValue.m13;
        double d2 =
                matrix4d.m00 * matrix4dValue.m20
                        + matrix4d.m10 * matrix4dValue.m21
                        + matrix4d.m20 * matrix4dValue.m22
                        + matrix4d.m30 * matrix4dValue.m23;
        double d3 =
                matrix4d.m00 * matrix4dValue.m30
                        + matrix4d.m10 * matrix4dValue.m31
                        + matrix4d.m20 * matrix4dValue.m32
                        + matrix4d.m30 * matrix4dValue.m33;
        double d4 =
                matrix4d.m01 * matrix4dValue.m00
                        + matrix4d.m11 * matrix4dValue.m01
                        + matrix4d.m21 * matrix4dValue.m02
                        + matrix4d.m31 * matrix4dValue.m03;
        double d5 =
                matrix4d.m01 * matrix4dValue.m10
                        + matrix4d.m11 * matrix4dValue.m11
                        + matrix4d.m21 * matrix4dValue.m12
                        + matrix4d.m31 * matrix4dValue.m13;
        double d6 =
                matrix4d.m01 * matrix4dValue.m20
                        + matrix4d.m11 * matrix4dValue.m21
                        + matrix4d.m21 * matrix4dValue.m22
                        + matrix4d.m31 * matrix4dValue.m23;
        double d7 =
                matrix4d.m01 * matrix4dValue.m30
                        + matrix4d.m11 * matrix4dValue.m31
                        + matrix4d.m21 * matrix4dValue.m32
                        + matrix4d.m31 * matrix4dValue.m33;
        double d8 =
                matrix4d.m02 * matrix4dValue.m00
                        + matrix4d.m12 * matrix4dValue.m01
                        + matrix4d.m22 * matrix4dValue.m02
                        + matrix4d.m32 * matrix4dValue.m03;
        double d9 =
                matrix4d.m02 * matrix4dValue.m10
                        + matrix4d.m12 * matrix4dValue.m11
                        + matrix4d.m22 * matrix4dValue.m12
                        + matrix4d.m32 * matrix4dValue.m13;
        double d10 =
                matrix4d.m02 * matrix4dValue.m20
                        + matrix4d.m12 * matrix4dValue.m21
                        + matrix4d.m22 * matrix4dValue.m22
                        + matrix4d.m32 * matrix4dValue.m23;
        double d11 =
                matrix4d.m02 * matrix4dValue.m30
                        + matrix4d.m12 * matrix4dValue.m31
                        + matrix4d.m22 * matrix4dValue.m32
                        + matrix4d.m32 * matrix4dValue.m33;
        double d12 =
                matrix4d.m03 * matrix4dValue.m00
                        + matrix4d.m13 * matrix4dValue.m01
                        + matrix4d.m23 * matrix4dValue.m02
                        + matrix4d.m33 * matrix4dValue.m03;
        double d13 =
                matrix4d.m03 * matrix4dValue.m10
                        + matrix4d.m13 * matrix4dValue.m11
                        + matrix4d.m23 * matrix4dValue.m12
                        + matrix4d.m33 * matrix4dValue.m13;
        double d14 =
                matrix4d.m03 * matrix4dValue.m20
                        + matrix4d.m13 * matrix4dValue.m21
                        + matrix4d.m23 * matrix4dValue.m22
                        + matrix4d.m33 * matrix4dValue.m23;
        double d15 =
                matrix4d.m03 * matrix4dValue.m30
                        + matrix4d.m13 * matrix4dValue.m31
                        + matrix4d.m23 * matrix4dValue.m32
                        + matrix4d.m33 * matrix4dValue.m33;
        this.m00 = d0;
        this.m01 = d1;
        this.m02 = d2;
        this.m03 = d3;
        this.m10 = d4;
        this.m11 = d5;
        this.m12 = d6;
        this.m13 = d7;
        this.m20 = d8;
        this.m21 = d9;
        this.m22 = d10;
        this.m23 = d11;
        this.m30 = d12;
        this.m31 = d13;
        this.m32 = d14;
        this.m33 = d15;
    }

    public final double get(
            Matrix3d p171_0, Vector3d p171_1) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        p171_0.m00 = adouble[0];
        p171_0.m01 = adouble[1];
        p171_0.m02 = adouble[2];
        p171_0.m10 = adouble[3];
        p171_0.m11 = adouble[4];
        p171_0.m12 = adouble[5];
        p171_0.m20 = adouble[6];
        p171_0.m21 = adouble[7];
        p171_0.m22 = adouble[8];
        p171_1.x = this.m03;
        p171_1.y = this.m13;
        p171_1.z = this.m23;
        return remix.compat.vecmath.Matrix3d.max3(adouble1);
    }

    public final double getM31() {
        return this.m31;
    }

    public final void sub(
            Matrix4d p171_0, Matrix4d p171_1) {
        this.m00 = p171_0.m00 - p171_1.m00;
        this.m01 = p171_0.m01 - p171_1.m01;
        this.m02 = p171_0.m02 - p171_1.m02;
        this.m03 = p171_0.m03 - p171_1.m03;
        this.m10 = p171_0.m10 - p171_1.m10;
        this.m11 = p171_0.m11 - p171_1.m11;
        this.m12 = p171_0.m12 - p171_1.m12;
        this.m13 = p171_0.m13 - p171_1.m13;
        this.m20 = p171_0.m20 - p171_1.m20;
        this.m21 = p171_0.m21 - p171_1.m21;
        this.m22 = p171_0.m22 - p171_1.m22;
        this.m23 = p171_0.m23 - p171_1.m23;
        this.m30 = p171_0.m30 - p171_1.m30;
        this.m31 = p171_0.m31 - p171_1.m31;
        this.m32 = p171_0.m32 - p171_1.m32;
        this.m33 = p171_0.m33 - p171_1.m33;
    }

    public final void method_9d0592d2cabdf852296a(
            int i, Vector4d vector4d) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = vector4d.x;
                this.m10 = vector4d.y;
                this.m20 = vector4d.z;
                this.m30 = vector4d.w;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m01 = vector4d.x;
                this.m11 = vector4d.y;
                this.m21 = vector4d.z;
                this.m31 = vector4d.w;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m02 = vector4d.x;
                this.m12 = vector4d.y;
                this.m22 = vector4d.z;
                this.m32 = vector4d.w;
                if (astring == null) {
                    break;
                }
            case 3:
                this.m03 = vector4d.x;
                this.m13 = vector4d.y;
                this.m23 = vector4d.z;
                this.m33 = vector4d.w;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix4d7"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
        }
    }

    public final void getRotationScale(
            Matrix3d matrix3d) {
        matrix3d.m00 = this.m00;
        matrix3d.m01 = this.m01;
        matrix3d.m02 = this.m02;
        matrix3d.m10 = this.m10;
        matrix3d.m11 = this.m11;
        matrix3d.m12 = this.m12;
        matrix3d.m20 = this.m20;
        matrix3d.m21 = this.m21;
        matrix3d.m22 = this.m22;
    }

    public final void set(Quat4f p171_0) {
        this.m00 = 1.0 - 2.0 * p171_0.y * p171_0.y - 2.0 * p171_0.z * p171_0.z;
        this.m10 = 2.0 * (p171_0.x * p171_0.y + p171_0.w * p171_0.z);
        this.m20 = 2.0 * (p171_0.x * p171_0.z - p171_0.w * p171_0.y);
        this.m01 = 2.0 * (p171_0.x * p171_0.y - p171_0.w * p171_0.z);
        this.m11 = 1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.z * p171_0.z;
        this.m21 = 2.0 * (p171_0.y * p171_0.z + p171_0.w * p171_0.x);
        this.m02 = 2.0 * (p171_0.x * p171_0.z + p171_0.w * p171_0.y);
        this.m12 = 2.0 * (p171_0.y * p171_0.z - p171_0.w * p171_0.x);
        this.m22 = 1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.y * p171_0.y;
        this.m03 = 0.0;
        this.m13 = 0.0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void setM31(double d0) {
        this.m31 = d0;
    }

    public final void mul(
            double p171_0, Matrix4d p171_1) {
        this.m00 = p171_1.m00 * p171_0;
        this.m01 = p171_1.m01 * p171_0;
        this.m02 = p171_1.m02 * p171_0;
        this.m03 = p171_1.m03 * p171_0;
        this.m10 = p171_1.m10 * p171_0;
        this.m11 = p171_1.m11 * p171_0;
        this.m12 = p171_1.m12 * p171_0;
        this.m13 = p171_1.m13 * p171_0;
        this.m20 = p171_1.m20 * p171_0;
        this.m21 = p171_1.m21 * p171_0;
        this.m22 = p171_1.m22 * p171_0;
        this.m23 = p171_1.m23 * p171_0;
        this.m30 = p171_1.m30 * p171_0;
        this.m31 = p171_1.m31 * p171_0;
        this.m32 = p171_1.m32 * p171_0;
        this.m33 = p171_1.m33 * p171_0;
    }

    public Matrix4d(
            Quat4f p171_0, Vector3d p171_1, double p171_2) {
        this.m00 = p171_2 * (1.0 - 2.0 * p171_0.y * p171_0.y - 2.0 * p171_0.z * p171_0.z);
        this.m10 = p171_2 * (2.0 * (p171_0.x * p171_0.y + p171_0.w * p171_0.z));
        this.m20 = p171_2 * (2.0 * (p171_0.x * p171_0.z - p171_0.w * p171_0.y));
        this.m01 = p171_2 * (2.0 * (p171_0.x * p171_0.y - p171_0.w * p171_0.z));
        this.m11 = p171_2 * (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.z * p171_0.z);
        this.m21 = p171_2 * (2.0 * (p171_0.y * p171_0.z + p171_0.w * p171_0.x));
        this.m02 = p171_2 * (2.0 * (p171_0.x * p171_0.z + p171_0.w * p171_0.y));
        this.m12 = p171_2 * (2.0 * (p171_0.y * p171_0.z - p171_0.w * p171_0.x));
        this.m22 = p171_2 * (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.y * p171_0.y);
        this.m03 = p171_1.x;
        this.m13 = p171_1.y;
        this.m23 = p171_1.z;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    static {
        remix.compat.vecmath.Matrix4d.packedTextFragments = new String[6];
        remix.compat.vecmath.Matrix4d.packedTextFragments[0] = new String("\n");
        remix.compat.vecmath.Matrix4d.packedTextFragments[1] = new String("\n");
        remix.compat.vecmath.Matrix4d.packedTextFragments[2] = new String("\n");
        remix.compat.vecmath.Matrix4d.packedTextFragments[3] = new String("\n");
    }

    public final void mul(double p171_0) {
        this.m00 *= p171_0;
        this.m01 *= p171_0;
        this.m02 *= p171_0;
        this.m03 *= p171_0;
        this.m10 *= p171_0;
        this.m11 *= p171_0;
        this.m12 *= p171_0;
        this.m13 *= p171_0;
        this.m20 *= p171_0;
        this.m21 *= p171_0;
        this.m22 *= p171_0;
        this.m23 *= p171_0;
        this.m30 *= p171_0;
        this.m31 *= p171_0;
        this.m32 *= p171_0;
        this.m33 *= p171_0;
    }

    @Override
    public int hashCode() {

        long i = 1L;
        i = VecMathUtil.hashDoubleToLongBits(i, this.m00);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m01);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m02);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m03);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m10);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m11);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m12);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m13);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m20);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m21);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m22);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m23);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m30);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m31);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m32);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m33);
        return VecMathUtil.hashFinish(i);
    }

    public final void rotY(double p171_0) {
        double d1 = java.lang.Math.sin(p171_0);
        double d2 = java.lang.Math.cos(p171_0);
        this.m00 = d2;
        this.m01 = 0.0;
        this.m02 = d1;
        this.m03 = 0.0;
        this.m10 = 0.0;
        this.m11 = 1.0;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = -d1;
        this.m21 = 0.0;
        this.m22 = d2;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void setM03(double d0) {
        this.m03 = d0;
    }

    public final double getM22() {
        return this.m22;
    }

    public final void method_f53818acdc557833fda4(
            Matrix4d matrix4d,
            Matrix4d matrix4dValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix4d && this != matrix4dValue) {
            this.m00 =
                    matrix4d.m00 * matrix4dValue.m00
                            + matrix4d.m10 * matrix4dValue.m10
                            + matrix4d.m20 * matrix4dValue.m20
                            + matrix4d.m30 * matrix4dValue.m30;
            this.m01 =
                    matrix4d.m00 * matrix4dValue.m01
                            + matrix4d.m10 * matrix4dValue.m11
                            + matrix4d.m20 * matrix4dValue.m21
                            + matrix4d.m30 * matrix4dValue.m31;
            this.m02 =
                    matrix4d.m00 * matrix4dValue.m02
                            + matrix4d.m10 * matrix4dValue.m12
                            + matrix4d.m20 * matrix4dValue.m22
                            + matrix4d.m30 * matrix4dValue.m32;
            this.m03 =
                    matrix4d.m00 * matrix4dValue.m03
                            + matrix4d.m10 * matrix4dValue.m13
                            + matrix4d.m20 * matrix4dValue.m23
                            + matrix4d.m30 * matrix4dValue.m33;
            this.m10 =
                    matrix4d.m01 * matrix4dValue.m00
                            + matrix4d.m11 * matrix4dValue.m10
                            + matrix4d.m21 * matrix4dValue.m20
                            + matrix4d.m31 * matrix4dValue.m30;
            this.m11 =
                    matrix4d.m01 * matrix4dValue.m01
                            + matrix4d.m11 * matrix4dValue.m11
                            + matrix4d.m21 * matrix4dValue.m21
                            + matrix4d.m31 * matrix4dValue.m31;
            this.m12 =
                    matrix4d.m01 * matrix4dValue.m02
                            + matrix4d.m11 * matrix4dValue.m12
                            + matrix4d.m21 * matrix4dValue.m22
                            + matrix4d.m31 * matrix4dValue.m32;
            this.m13 =
                    matrix4d.m01 * matrix4dValue.m03
                            + matrix4d.m11 * matrix4dValue.m13
                            + matrix4d.m21 * matrix4dValue.m23
                            + matrix4d.m31 * matrix4dValue.m33;
            this.m20 =
                    matrix4d.m02 * matrix4dValue.m00
                            + matrix4d.m12 * matrix4dValue.m10
                            + matrix4d.m22 * matrix4dValue.m20
                            + matrix4d.m32 * matrix4dValue.m30;
            this.m21 =
                    matrix4d.m02 * matrix4dValue.m01
                            + matrix4d.m12 * matrix4dValue.m11
                            + matrix4d.m22 * matrix4dValue.m21
                            + matrix4d.m32 * matrix4dValue.m31;
            this.m22 =
                    matrix4d.m02 * matrix4dValue.m02
                            + matrix4d.m12 * matrix4dValue.m12
                            + matrix4d.m22 * matrix4dValue.m22
                            + matrix4d.m32 * matrix4dValue.m32;
            this.m23 =
                    matrix4d.m02 * matrix4dValue.m03
                            + matrix4d.m12 * matrix4dValue.m13
                            + matrix4d.m22 * matrix4dValue.m23
                            + matrix4d.m32 * matrix4dValue.m33;
            this.m30 =
                    matrix4d.m03 * matrix4dValue.m00
                            + matrix4d.m13 * matrix4dValue.m10
                            + matrix4d.m23 * matrix4dValue.m20
                            + matrix4d.m33 * matrix4dValue.m30;
            this.m31 =
                    matrix4d.m03 * matrix4dValue.m01
                            + matrix4d.m13 * matrix4dValue.m11
                            + matrix4d.m23 * matrix4dValue.m21
                            + matrix4d.m33 * matrix4dValue.m31;
            this.m32 =
                    matrix4d.m03 * matrix4dValue.m02
                            + matrix4d.m13 * matrix4dValue.m12
                            + matrix4d.m23 * matrix4dValue.m22
                            + matrix4d.m33 * matrix4dValue.m32;
            this.m33 =
                    matrix4d.m03 * matrix4dValue.m03
                            + matrix4d.m13 * matrix4dValue.m13
                            + matrix4d.m23 * matrix4dValue.m23
                            + matrix4d.m33 * matrix4dValue.m33;
            if (astring == null) {
                return;
            }
        }

        double d0 =
                matrix4d.m00 * matrix4dValue.m00
                        + matrix4d.m10 * matrix4dValue.m10
                        + matrix4d.m20 * matrix4dValue.m20
                        + matrix4d.m30 * matrix4dValue.m30;
        double d1 =
                matrix4d.m00 * matrix4dValue.m01
                        + matrix4d.m10 * matrix4dValue.m11
                        + matrix4d.m20 * matrix4dValue.m21
                        + matrix4d.m30 * matrix4dValue.m31;
        double d2 =
                matrix4d.m00 * matrix4dValue.m02
                        + matrix4d.m10 * matrix4dValue.m12
                        + matrix4d.m20 * matrix4dValue.m22
                        + matrix4d.m30 * matrix4dValue.m32;
        double d3 =
                matrix4d.m00 * matrix4dValue.m03
                        + matrix4d.m10 * matrix4dValue.m13
                        + matrix4d.m20 * matrix4dValue.m23
                        + matrix4d.m30 * matrix4dValue.m33;
        double d4 =
                matrix4d.m01 * matrix4dValue.m00
                        + matrix4d.m11 * matrix4dValue.m10
                        + matrix4d.m21 * matrix4dValue.m20
                        + matrix4d.m31 * matrix4dValue.m30;
        double d5 =
                matrix4d.m01 * matrix4dValue.m01
                        + matrix4d.m11 * matrix4dValue.m11
                        + matrix4d.m21 * matrix4dValue.m21
                        + matrix4d.m31 * matrix4dValue.m31;
        double d6 =
                matrix4d.m01 * matrix4dValue.m02
                        + matrix4d.m11 * matrix4dValue.m12
                        + matrix4d.m21 * matrix4dValue.m22
                        + matrix4d.m31 * matrix4dValue.m32;
        double d7 =
                matrix4d.m01 * matrix4dValue.m03
                        + matrix4d.m11 * matrix4dValue.m13
                        + matrix4d.m21 * matrix4dValue.m23
                        + matrix4d.m31 * matrix4dValue.m33;
        double d8 =
                matrix4d.m02 * matrix4dValue.m00
                        + matrix4d.m12 * matrix4dValue.m10
                        + matrix4d.m22 * matrix4dValue.m20
                        + matrix4d.m32 * matrix4dValue.m30;
        double d9 =
                matrix4d.m02 * matrix4dValue.m01
                        + matrix4d.m12 * matrix4dValue.m11
                        + matrix4d.m22 * matrix4dValue.m21
                        + matrix4d.m32 * matrix4dValue.m31;
        double d10 =
                matrix4d.m02 * matrix4dValue.m02
                        + matrix4d.m12 * matrix4dValue.m12
                        + matrix4d.m22 * matrix4dValue.m22
                        + matrix4d.m32 * matrix4dValue.m32;
        double d11 =
                matrix4d.m02 * matrix4dValue.m03
                        + matrix4d.m12 * matrix4dValue.m13
                        + matrix4d.m22 * matrix4dValue.m23
                        + matrix4d.m32 * matrix4dValue.m33;
        double d12 =
                matrix4d.m03 * matrix4dValue.m00
                        + matrix4d.m13 * matrix4dValue.m10
                        + matrix4d.m23 * matrix4dValue.m20
                        + matrix4d.m33 * matrix4dValue.m30;
        double d13 =
                matrix4d.m03 * matrix4dValue.m01
                        + matrix4d.m13 * matrix4dValue.m11
                        + matrix4d.m23 * matrix4dValue.m21
                        + matrix4d.m33 * matrix4dValue.m31;
        double d14 =
                matrix4d.m03 * matrix4dValue.m02
                        + matrix4d.m13 * matrix4dValue.m12
                        + matrix4d.m23 * matrix4dValue.m22
                        + matrix4d.m33 * matrix4dValue.m32;
        double d15 =
                matrix4d.m03 * matrix4dValue.m03
                        + matrix4d.m13 * matrix4dValue.m13
                        + matrix4d.m23 * matrix4dValue.m23
                        + matrix4d.m33 * matrix4dValue.m33;
        this.m00 = d0;
        this.m01 = d1;
        this.m02 = d2;
        this.m03 = d3;
        this.m10 = d4;
        this.m11 = d5;
        this.m12 = d6;
        this.m13 = d7;
        this.m20 = d8;
        this.m21 = d9;
        this.m22 = d10;
        this.m23 = d11;
        this.m30 = d12;
        this.m31 = d13;
        this.m32 = d14;
        this.m33 = d15;
    }

    public final void method_e6697858e222ab8eb6ee(Matrix4d p171_0) {
        java.lang.String[] astring = remix.compat.vecmath.Tuple3f.hF();
        if (this != p171_0) {
            this.m00 = p171_0.m00;
            this.m01 = p171_0.m10;
            this.m02 = p171_0.m20;
            this.m03 = p171_0.m30;
            this.m10 = p171_0.m01;
            this.m11 = p171_0.m11;
            this.m12 = p171_0.m21;
            this.m13 = p171_0.m31;
            this.m20 = p171_0.m02;
            this.m21 = p171_0.m12;
            this.m22 = p171_0.m22;
            this.m23 = p171_0.m32;
            this.m30 = p171_0.m03;
            this.m31 = p171_0.m13;
            this.m32 = p171_0.m23;
            this.m33 = p171_0.m33;
            if (astring == null) {
                return;
            }
        }

        this.transpose();
    }

    public final void method_2a0917a8d406bcac9db2(Matrix4d p171_0) {
        this.m00 = this.m00 - p171_0.m00;
        this.m01 = this.m01 - p171_0.m01;
        this.m02 = this.m02 - p171_0.m02;
        this.m03 = this.m03 - p171_0.m03;
        this.m10 = this.m10 - p171_0.m10;
        this.m11 = this.m11 - p171_0.m11;
        this.m12 = this.m12 - p171_0.m12;
        this.m13 = this.m13 - p171_0.m13;
        this.m20 = this.m20 - p171_0.m20;
        this.m21 = this.m21 - p171_0.m21;
        this.m22 = this.m22 - p171_0.m22;
        this.m23 = this.m23 - p171_0.m23;
        this.m30 = this.m30 - p171_0.m30;
        this.m31 = this.m31 - p171_0.m31;
        this.m32 = this.m32 - p171_0.m32;
        this.m33 = this.m33 - p171_0.m33;
    }

    public final void transform(Point3f p171_0) {
        float f =
                (float) (this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z + this.m03);
        float f1 =
                (float) (this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z + this.m13);
        p171_0.z =
                (float) (this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z + this.m23);
        p171_0.x = f;
        p171_0.y = f1;
    }

    // region String decoding support

    // endregion

    public final void set(
            AxisAngle4d axisAngle4d) {
        label12:
        {
            String[] astring1 = Tuple3f.hF();
            double d0 =
                    Math.sqrt(
                            axisAngle4d.x * axisAngle4d.x
                                    + axisAngle4d.y * axisAngle4d.y
                                    + axisAngle4d.z
                                            * axisAngle4d.z);
            String[] astring = astring1;
            if (d0 < 1.0E-10) {
                this.m00 = 1.0;
                this.m01 = 0.0;
                this.m02 = 0.0;
                this.m10 = 0.0;
                this.m11 = 1.0;
                this.m12 = 0.0;
                this.m20 = 0.0;
                this.m21 = 0.0;
                this.m22 = 1.0;
                if (astring == null) {
                    break label12;
                }
            }

            d0 = 1.0 / d0;
            double d1 = axisAngle4d.x * d0;
            double d2 = axisAngle4d.y * d0;
            double d3 = axisAngle4d.z * d0;
            double d4 = Math.sin(axisAngle4d.angle);
            double d5 = Math.cos(axisAngle4d.angle);
            double d6 = 1.0 - d5;
            double d7 = d1 * d3;
            double d8 = d1 * d2;
            double d9 = d2 * d3;
            this.m00 = d6 * d1 * d1 + d5;
            this.m01 = d6 * d8 - d4 * d3;
            this.m02 = d6 * d7 + d4 * d2;
            this.m10 = d6 * d8 + d4 * d3;
            this.m11 = d6 * d2 * d2 + d5;
            this.m12 = d6 * d9 - d4 * d1;
            this.m20 = d6 * d7 - d4 * d2;
            this.m21 = d6 * d9 + d4 * d1;
            this.m22 = d6 * d3 * d3 + d5;
        }

        this.m03 = 0.0;
        this.m13 = 0.0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void setRotation(Quat4f p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        this.m00 = (1.0 - 2.0F * p171_0.y * p171_0.y - 2.0F * p171_0.z * p171_0.z) * adouble1[0];
        this.m10 = 2.0 * (p171_0.x * p171_0.y + p171_0.w * p171_0.z) * adouble1[0];
        this.m20 = 2.0 * (p171_0.x * p171_0.z - p171_0.w * p171_0.y) * adouble1[0];
        this.m01 = 2.0 * (p171_0.x * p171_0.y - p171_0.w * p171_0.z) * adouble1[1];
        this.m11 = (1.0 - 2.0F * p171_0.x * p171_0.x - 2.0F * p171_0.z * p171_0.z) * adouble1[1];
        this.m21 = 2.0 * (p171_0.y * p171_0.z + p171_0.w * p171_0.x) * adouble1[1];
        this.m02 = 2.0 * (p171_0.x * p171_0.z + p171_0.w * p171_0.y) * adouble1[2];
        this.m12 = 2.0 * (p171_0.y * p171_0.z - p171_0.w * p171_0.x) * adouble1[2];
        this.m22 = (1.0 - 2.0F * p171_0.x * p171_0.x - 2.0F * p171_0.y * p171_0.y) * adouble1[2];
    }

    public final double getM02() {
        return this.m02;
    }

    public final void setZero() {
        this.m00 = 0.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = 0.0;
        this.m11 = 0.0;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 0.0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 0.0;
    }

    public final void transform(
            Point3d p171_0, Point3d p171_1) {
        double d0 = this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z + this.m03;
        double d1 = this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z + this.m13;
        p171_1.z = this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z + this.m23;
        p171_1.x = d0;
        p171_1.y = d1;
    }

    private static RuntimeException wrapException(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    public final void setM13(double d0) {
        this.m13 = d0;
    }

    public final double getM23() {
        return this.m23;
    }

    public final void getColumn(int i, double[] adouble) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            adouble[0] = this.m00;
            adouble[1] = this.m10;
            adouble[2] = this.m20;
            adouble[3] = this.m30;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            adouble[0] = this.m01;
            adouble[1] = this.m11;
            adouble[2] = this.m21;
            adouble[3] = this.m31;
            if (astring == null) {
                return;
            }
        }

        if (i == 2) {
            adouble[0] = this.m02;
            adouble[1] = this.m12;
            adouble[2] = this.m22;
            adouble[3] = this.m32;
            if (astring == null) {
                return;
            }
        }

        if (i != 3) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d3"));
        }

        adouble[0] = this.m03;
        adouble[1] = this.m13;
        adouble[2] = this.m23;
        adouble[3] = this.m33;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d3"));
        }
    }

    public final void setM23(double d0) {
        this.m23 = d0;
    }

    public final double getElement(int i, int j) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                switch (j) {
                    case 0:
                        return this.m00;
                    case 1:
                        return this.m01;
                    case 2:
                        return this.m02;
                    case 3:
                        return this.m03;
                }

                if (astring == null) {
                    break;
                }
            case 1:
                switch (j) {
                    case 0:
                        return this.m10;
                    case 1:
                        return this.m11;
                    case 2:
                        return this.m12;
                    case 3:
                        return this.m13;
                }

                if (astring == null) {
                    break;
                }
            case 2:
                switch (j) {
                    case 0:
                        return this.m20;
                    case 1:
                        return this.m21;
                    case 2:
                        return this.m22;
                    case 3:
                        return this.m23;
                }

                if (astring == null) {
                    break;
                }
            case 3:
                switch (j) {
                    case 0:
                        return this.m30;
                    case 1:
                        return this.m31;
                    case 2:
                        return this.m32;
                    case 3:
                        return this.m33;
                }
        }

        throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d1"));
    }

    @Override
    public Object clone() {

        try {
            return (remix.compat.vecmath.Matrix4d) super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }
    }

    public final void set(Matrix3f p171_0) {
        this.m00 = p171_0.m00;
        this.m01 = p171_0.m01;
        this.m02 = p171_0.m02;
        this.m03 = 0.0;
        this.m10 = p171_0.m10;
        this.m11 = p171_0.m11;
        this.m12 = p171_0.m12;
        this.m13 = 0.0;
        this.m20 = p171_0.m20;
        this.m21 = p171_0.m21;
        this.m22 = p171_0.m22;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void method_b821c37974696c74e8dd(
            int i, Vector4d vector4d) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = vector4d.x;
                this.m01 = vector4d.y;
                this.m02 = vector4d.z;
                this.m03 = vector4d.w;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m10 = vector4d.x;
                this.m11 = vector4d.y;
                this.m12 = vector4d.z;
                this.m13 = vector4d.w;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m20 = vector4d.x;
                this.m21 = vector4d.y;
                this.m22 = vector4d.z;
                this.m23 = vector4d.w;
                if (astring == null) {
                    break;
                }
            case 3:
                this.m30 = vector4d.x;
                this.m31 = vector4d.y;
                this.m32 = vector4d.z;
                this.m33 = vector4d.w;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix4d4"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
        }
    }

    public final void setM33(double d0) {
        this.m33 = d0;
    }

    public final void set(Vector3d p171_0) {
        this.m00 = 1.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = p171_0.x;
        this.m10 = 0.0;
        this.m11 = 1.0;
        this.m12 = 0.0;
        this.m13 = p171_0.y;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 1.0;
        this.m23 = p171_0.z;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void transform(
            Vector3d p171_0, Vector3d p171_1) {
        double d0 = this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z;
        double d1 = this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z;
        p171_1.z = this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z;
        p171_1.x = d0;
        p171_1.y = d1;
    }

    public final double getM11() {
        return this.m11;
    }

    public final void set(Matrix4d p171_0) {
        this.m00 = p171_0.m00;
        this.m01 = p171_0.m01;
        this.m02 = p171_0.m02;
        this.m03 = p171_0.m03;
        this.m10 = p171_0.m10;
        this.m11 = p171_0.m11;
        this.m12 = p171_0.m12;
        this.m13 = p171_0.m13;
        this.m20 = p171_0.m20;
        this.m21 = p171_0.m21;
        this.m22 = p171_0.m22;
        this.m23 = p171_0.m23;
        this.m30 = p171_0.m30;
        this.m31 = p171_0.m31;
        this.m32 = p171_0.m32;
        this.m33 = p171_0.m33;
    }

    public final void add(
            Matrix4d p171_0, Matrix4d p171_1) {
        this.m00 = p171_0.m00 + p171_1.m00;
        this.m01 = p171_0.m01 + p171_1.m01;
        this.m02 = p171_0.m02 + p171_1.m02;
        this.m03 = p171_0.m03 + p171_1.m03;
        this.m10 = p171_0.m10 + p171_1.m10;
        this.m11 = p171_0.m11 + p171_1.m11;
        this.m12 = p171_0.m12 + p171_1.m12;
        this.m13 = p171_0.m13 + p171_1.m13;
        this.m20 = p171_0.m20 + p171_1.m20;
        this.m21 = p171_0.m21 + p171_1.m21;
        this.m22 = p171_0.m22 + p171_1.m22;
        this.m23 = p171_0.m23 + p171_1.m23;
        this.m30 = p171_0.m30 + p171_1.m30;
        this.m31 = p171_0.m31 + p171_1.m31;
        this.m32 = p171_0.m32 + p171_1.m32;
        this.m33 = p171_0.m33 + p171_1.m33;
    }

    public final void set(AxisAngle4f p171_0) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        double d0 =
                java.lang.Math.sqrt(
                        p171_0.x * p171_0.x + p171_0.y * p171_0.y + p171_0.z * p171_0.z);
        java.lang.String[] astring = astring1;

        label19:
        {
            try {
                if (d0 < 1.0E-10) {
                    this.m00 = 1.0;
                    this.m01 = 0.0;
                    this.m02 = 0.0;
                    this.m10 = 0.0;
                    this.m11 = 1.0;
                    this.m12 = 0.0;
                    this.m20 = 0.0;
                    this.m21 = 0.0;
                    this.m22 = 1.0;
                    if (astring == null) {
                        break label19;
                    }
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception);
            }

            d0 = 1.0 / d0;
            double d1 = p171_0.x * d0;
            double d2 = p171_0.y * d0;
            double d3 = p171_0.z * d0;
            double d4 = java.lang.Math.sin(p171_0.angle);
            double d5 = java.lang.Math.cos(p171_0.angle);
            double d6 = 1.0 - d5;
            double d7 = d1 * d3;
            double d8 = d1 * d2;
            double d9 = d2 * d3;
            this.m00 = d6 * d1 * d1 + d5;
            this.m01 = d6 * d8 - d4 * d3;
            this.m02 = d6 * d7 + d4 * d2;
            this.m10 = d6 * d8 + d4 * d3;
            this.m11 = d6 * d2 * d2 + d5;
            this.m12 = d6 * d9 - d4 * d1;
            this.m20 = d6 * d7 - d4 * d2;
            this.m21 = d6 * d9 + d4 * d1;
            this.m22 = d6 * d3 * d3 + d5;
        }

        this.m03 = 0.0;
        this.m13 = 0.0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void transform(
            Point3f p171_0, Point3f p171_1) {
        float f =
                (float) (this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z + this.m03);
        float f1 =
                (float) (this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z + this.m13);
        p171_1.z =
                (float) (this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z + this.m23);
        p171_1.x = f;
        p171_1.y = f1;
    }

    public final void transform(Point3d p171_0) {
        double d0 = this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z + this.m03;
        double d1 = this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z + this.m13;
        p171_0.z = this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z + this.m23;
        p171_0.x = d0;
        p171_0.y = d1;
    }

    public final void setM22(double d0) {
        this.m22 = d0;
    }

    public final void get(Quat4f p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        double d0 = 0.25 * (1.0 + adouble[0] + adouble[4] + adouble[8]);

        double d1;
        label63:
        {
            try {
                if (d0 < 0.0) {
                    d1 = -d0;
                    break label63;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception3) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception3);
            }

            d1 = d0;
        }

        if (!(d1 < 1.0E-30)) {
            p171_0.w = (float) java.lang.Math.sqrt(d0);
            d0 = 0.25 / p171_0.w;
            p171_0.x = (float) ((adouble[7] - adouble[5]) * d0);
            p171_0.y = (float) ((adouble[2] - adouble[6]) * d0);
            p171_0.z = (float) ((adouble[3] - adouble[1]) * d0);
        } else {
            p171_0.w = 0.0F;
            d0 = -0.5 * (adouble[4] + adouble[8]);

            label53:
            {
                try {
                    if (d0 < 0.0) {
                        d1 = -d0;
                        break label53;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception2) {
                    throw remix.compat.vecmath.Matrix4d.wrapException(
                            arrayindexoutofboundsexception2);
                }

                d1 = d0;
            }

            if (!(d1 < 1.0E-30)) {
                p171_0.x = (float) java.lang.Math.sqrt(d0);
                d0 = 0.5 / p171_0.x;
                p171_0.y = (float) (adouble[3] * d0);
                p171_0.z = (float) (adouble[6] * d0);
            } else {
                p171_0.x = 0.0F;
                d0 = 0.5 * (1.0 - adouble[8]);

                label43:
                {
                    try {
                        if (d0 < 0.0) {
                            d1 = -d0;
                            break label43;
                        }
                    } catch (
                            java.lang.ArrayIndexOutOfBoundsException
                                    arrayindexoutofboundsexception1) {
                        throw remix.compat.vecmath.Matrix4d.wrapException(
                                arrayindexoutofboundsexception1);
                    }

                    d1 = d0;
                }

                try {
                    if (!(d1 < 1.0E-30)) {
                        p171_0.y = (float) java.lang.Math.sqrt(d0);
                        p171_0.z = (float) (adouble[7] / (2.0 * p171_0.y));
                        return;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
                    throw remix.compat.vecmath.Matrix4d.wrapException(
                            arrayindexoutofboundsexception);
                }

                p171_0.y = 0.0F;
                p171_0.z = 1.0F;
            }
        }
    }

    public final void transform(Tuple4d p171_0) {
        double d0 =
                this.m00 * p171_0.x
                        + this.m01 * p171_0.y
                        + this.m02 * p171_0.z
                        + this.m03 * p171_0.w;
        double d1 =
                this.m10 * p171_0.x
                        + this.m11 * p171_0.y
                        + this.m12 * p171_0.z
                        + this.m13 * p171_0.w;
        double d2 =
                this.m20 * p171_0.x
                        + this.m21 * p171_0.y
                        + this.m22 * p171_0.z
                        + this.m23 * p171_0.w;
        p171_0.w =
                this.m30 * p171_0.x
                        + this.m31 * p171_0.y
                        + this.m32 * p171_0.z
                        + this.m33 * p171_0.w;
        p171_0.x = d0;
        p171_0.y = d1;
        p171_0.z = d2;
    }

    public final void method_4d83e2c419959c4e930b(Matrix4d p171_0) {
        this.m00 = this.m00 + p171_0.m00;
        this.m01 = this.m01 + p171_0.m01;
        this.m02 = this.m02 + p171_0.m02;
        this.m03 = this.m03 + p171_0.m03;
        this.m10 = this.m10 + p171_0.m10;
        this.m11 = this.m11 + p171_0.m11;
        this.m12 = this.m12 + p171_0.m12;
        this.m13 = this.m13 + p171_0.m13;
        this.m20 = this.m20 + p171_0.m20;
        this.m21 = this.m21 + p171_0.m21;
        this.m22 = this.m22 + p171_0.m22;
        this.m23 = this.m23 + p171_0.m23;
        this.m30 = this.m30 + p171_0.m30;
        this.m31 = this.m31 + p171_0.m31;
        this.m32 = this.m32 + p171_0.m32;
        this.m33 = this.m33 + p171_0.m33;
    }

    public Matrix4d(
            Quat4d p171_0, Vector3d p171_1, double p171_2) {
        this.m00 = p171_2 * (1.0 - 2.0 * p171_0.y * p171_0.y - 2.0 * p171_0.z * p171_0.z);
        this.m10 = p171_2 * (2.0 * (p171_0.x * p171_0.y + p171_0.w * p171_0.z));
        this.m20 = p171_2 * (2.0 * (p171_0.x * p171_0.z - p171_0.w * p171_0.y));
        this.m01 = p171_2 * (2.0 * (p171_0.x * p171_0.y - p171_0.w * p171_0.z));
        this.m11 = p171_2 * (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.z * p171_0.z);
        this.m21 = p171_2 * (2.0 * (p171_0.y * p171_0.z + p171_0.w * p171_0.x));
        this.m02 = p171_2 * (2.0 * (p171_0.x * p171_0.z + p171_0.w * p171_0.y));
        this.m12 = p171_2 * (2.0 * (p171_0.y * p171_0.z - p171_0.w * p171_0.x));
        this.m22 = p171_2 * (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.y * p171_0.y);
        this.m03 = p171_1.x;
        this.m13 = p171_1.y;
        this.m23 = p171_1.z;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public Matrix4d(
            double p171_0,
            double p171_1,
            double p171_2,
            double p171_3,
            double p171_4,
            double p171_5,
            double p171_6,
            double p171_7,
            double p171_8,
            double p171_9,
            double p171_10,
            double p171_11,
            double p171_12,
            double p171_13,
            double p171_14,
            double p171_15) {
        this.m00 = p171_0;
        this.m01 = p171_1;
        this.m02 = p171_2;
        this.m03 = p171_3;
        this.m10 = p171_4;
        this.m11 = p171_5;
        this.m12 = p171_6;
        this.m13 = p171_7;
        this.m20 = p171_8;
        this.m21 = p171_9;
        this.m22 = p171_10;
        this.m23 = p171_11;
        this.m30 = p171_12;
        this.m31 = p171_13;
        this.m32 = p171_14;
        this.m33 = p171_15;
    }

    public final void mul(
            Matrix4d p171_0, Matrix4d p171_1) {
        if (this != p171_0 && this != p171_1) {
            this.m00 =
                    p171_0.m00 * p171_1.m00
                            + p171_0.m01 * p171_1.m10
                            + p171_0.m02 * p171_1.m20
                            + p171_0.m03 * p171_1.m30;
            this.m01 =
                    p171_0.m00 * p171_1.m01
                            + p171_0.m01 * p171_1.m11
                            + p171_0.m02 * p171_1.m21
                            + p171_0.m03 * p171_1.m31;
            this.m02 =
                    p171_0.m00 * p171_1.m02
                            + p171_0.m01 * p171_1.m12
                            + p171_0.m02 * p171_1.m22
                            + p171_0.m03 * p171_1.m32;
            this.m03 =
                    p171_0.m00 * p171_1.m03
                            + p171_0.m01 * p171_1.m13
                            + p171_0.m02 * p171_1.m23
                            + p171_0.m03 * p171_1.m33;
            this.m10 =
                    p171_0.m10 * p171_1.m00
                            + p171_0.m11 * p171_1.m10
                            + p171_0.m12 * p171_1.m20
                            + p171_0.m13 * p171_1.m30;
            this.m11 =
                    p171_0.m10 * p171_1.m01
                            + p171_0.m11 * p171_1.m11
                            + p171_0.m12 * p171_1.m21
                            + p171_0.m13 * p171_1.m31;
            this.m12 =
                    p171_0.m10 * p171_1.m02
                            + p171_0.m11 * p171_1.m12
                            + p171_0.m12 * p171_1.m22
                            + p171_0.m13 * p171_1.m32;
            this.m13 =
                    p171_0.m10 * p171_1.m03
                            + p171_0.m11 * p171_1.m13
                            + p171_0.m12 * p171_1.m23
                            + p171_0.m13 * p171_1.m33;
            this.m20 =
                    p171_0.m20 * p171_1.m00
                            + p171_0.m21 * p171_1.m10
                            + p171_0.m22 * p171_1.m20
                            + p171_0.m23 * p171_1.m30;
            this.m21 =
                    p171_0.m20 * p171_1.m01
                            + p171_0.m21 * p171_1.m11
                            + p171_0.m22 * p171_1.m21
                            + p171_0.m23 * p171_1.m31;
            this.m22 =
                    p171_0.m20 * p171_1.m02
                            + p171_0.m21 * p171_1.m12
                            + p171_0.m22 * p171_1.m22
                            + p171_0.m23 * p171_1.m32;
            this.m23 =
                    p171_0.m20 * p171_1.m03
                            + p171_0.m21 * p171_1.m13
                            + p171_0.m22 * p171_1.m23
                            + p171_0.m23 * p171_1.m33;
            this.m30 =
                    p171_0.m30 * p171_1.m00
                            + p171_0.m31 * p171_1.m10
                            + p171_0.m32 * p171_1.m20
                            + p171_0.m33 * p171_1.m30;
            this.m31 =
                    p171_0.m30 * p171_1.m01
                            + p171_0.m31 * p171_1.m11
                            + p171_0.m32 * p171_1.m21
                            + p171_0.m33 * p171_1.m31;
            this.m32 =
                    p171_0.m30 * p171_1.m02
                            + p171_0.m31 * p171_1.m12
                            + p171_0.m32 * p171_1.m22
                            + p171_0.m33 * p171_1.m32;
            this.m33 =
                    p171_0.m30 * p171_1.m03
                            + p171_0.m31 * p171_1.m13
                            + p171_0.m32 * p171_1.m23
                            + p171_0.m33 * p171_1.m33;
        } else {
            double d0 =
                    p171_0.m00 * p171_1.m00
                            + p171_0.m01 * p171_1.m10
                            + p171_0.m02 * p171_1.m20
                            + p171_0.m03 * p171_1.m30;
            double d1 =
                    p171_0.m00 * p171_1.m01
                            + p171_0.m01 * p171_1.m11
                            + p171_0.m02 * p171_1.m21
                            + p171_0.m03 * p171_1.m31;
            double d2 =
                    p171_0.m00 * p171_1.m02
                            + p171_0.m01 * p171_1.m12
                            + p171_0.m02 * p171_1.m22
                            + p171_0.m03 * p171_1.m32;
            double d3 =
                    p171_0.m00 * p171_1.m03
                            + p171_0.m01 * p171_1.m13
                            + p171_0.m02 * p171_1.m23
                            + p171_0.m03 * p171_1.m33;
            double d4 =
                    p171_0.m10 * p171_1.m00
                            + p171_0.m11 * p171_1.m10
                            + p171_0.m12 * p171_1.m20
                            + p171_0.m13 * p171_1.m30;
            double d5 =
                    p171_0.m10 * p171_1.m01
                            + p171_0.m11 * p171_1.m11
                            + p171_0.m12 * p171_1.m21
                            + p171_0.m13 * p171_1.m31;
            double d6 =
                    p171_0.m10 * p171_1.m02
                            + p171_0.m11 * p171_1.m12
                            + p171_0.m12 * p171_1.m22
                            + p171_0.m13 * p171_1.m32;
            double d7 =
                    p171_0.m10 * p171_1.m03
                            + p171_0.m11 * p171_1.m13
                            + p171_0.m12 * p171_1.m23
                            + p171_0.m13 * p171_1.m33;
            double d8 =
                    p171_0.m20 * p171_1.m00
                            + p171_0.m21 * p171_1.m10
                            + p171_0.m22 * p171_1.m20
                            + p171_0.m23 * p171_1.m30;
            double d9 =
                    p171_0.m20 * p171_1.m01
                            + p171_0.m21 * p171_1.m11
                            + p171_0.m22 * p171_1.m21
                            + p171_0.m23 * p171_1.m31;
            double d10 =
                    p171_0.m20 * p171_1.m02
                            + p171_0.m21 * p171_1.m12
                            + p171_0.m22 * p171_1.m22
                            + p171_0.m23 * p171_1.m32;
            double d11 =
                    p171_0.m20 * p171_1.m03
                            + p171_0.m21 * p171_1.m13
                            + p171_0.m22 * p171_1.m23
                            + p171_0.m23 * p171_1.m33;
            double d12 =
                    p171_0.m30 * p171_1.m00
                            + p171_0.m31 * p171_1.m10
                            + p171_0.m32 * p171_1.m20
                            + p171_0.m33 * p171_1.m30;
            double d13 =
                    p171_0.m30 * p171_1.m01
                            + p171_0.m31 * p171_1.m11
                            + p171_0.m32 * p171_1.m21
                            + p171_0.m33 * p171_1.m31;
            double d14 =
                    p171_0.m30 * p171_1.m02
                            + p171_0.m31 * p171_1.m12
                            + p171_0.m32 * p171_1.m22
                            + p171_0.m33 * p171_1.m32;
            double d15 =
                    p171_0.m30 * p171_1.m03
                            + p171_0.m31 * p171_1.m13
                            + p171_0.m32 * p171_1.m23
                            + p171_0.m33 * p171_1.m33;
            this.m00 = d0;
            this.m01 = d1;
            this.m02 = d2;
            this.m03 = d3;
            this.m10 = d4;
            this.m11 = d5;
            this.m12 = d6;
            this.m13 = d7;
            this.m20 = d8;
            this.m21 = d9;
            this.m22 = d10;
            this.m23 = d11;
            this.m30 = d12;
            this.m31 = d13;
            this.m32 = d14;
            this.m33 = d15;
        }
    }

    public final void l() {
        this.invertGeneral(this);
    }

    public final void set(
            Vector3d p171_0, double p171_1) {
        this.m00 = p171_1;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = p171_1 * p171_0.x;
        this.m10 = 0.0;
        this.m11 = p171_1;
        this.m12 = 0.0;
        this.m13 = p171_1 * p171_0.y;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = p171_1;
        this.m23 = p171_1 * p171_0.z;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final double getM03() {
        return this.m03;
    }

    public final void set(
            Quat4d p171_0, Vector3d p171_1, double p171_2) {
        this.m00 = p171_2 * (1.0 - 2.0 * p171_0.y * p171_0.y - 2.0 * p171_0.z * p171_0.z);
        this.m10 = p171_2 * (2.0 * (p171_0.x * p171_0.y + p171_0.w * p171_0.z));
        this.m20 = p171_2 * (2.0 * (p171_0.x * p171_0.z - p171_0.w * p171_0.y));
        this.m01 = p171_2 * (2.0 * (p171_0.x * p171_0.y - p171_0.w * p171_0.z));
        this.m11 = p171_2 * (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.z * p171_0.z);
        this.m21 = p171_2 * (2.0 * (p171_0.y * p171_0.z + p171_0.w * p171_0.x));
        this.m02 = p171_2 * (2.0 * (p171_0.x * p171_0.z + p171_0.w * p171_0.y));
        this.m12 = p171_2 * (2.0 * (p171_0.y * p171_0.z - p171_0.w * p171_0.x));
        this.m22 = p171_2 * (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.y * p171_0.y);
        this.m03 = p171_1.x;
        this.m13 = p171_1.y;
        this.m23 = p171_1.z;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void getRotationScale(Matrix3f p171_0) {
        p171_0.m00 = (float) this.m00;
        p171_0.m01 = (float) this.m01;
        p171_0.m02 = (float) this.m02;
        p171_0.m10 = (float) this.m10;
        p171_0.m11 = (float) this.m11;
        p171_0.m12 = (float) this.m12;
        p171_0.m20 = (float) this.m20;
        p171_0.m21 = (float) this.m21;
        p171_0.m22 = (float) this.m22;
    }

    public final void setRotation(
            AxisAngle4d axisAngle4d) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        double d0 =
                1.0
                        / Math.sqrt(
                                axisAngle4d.x * axisAngle4d.x
                                        + axisAngle4d.y
                                                * axisAngle4d.y
                                        + axisAngle4d.z
                                                * axisAngle4d.z);
        double d1 = axisAngle4d.x * d0;
        double d2 = axisAngle4d.y * d0;
        double d3 = axisAngle4d.z * d0;
        double d4 = Math.sin(axisAngle4d.angle);
        double d5 = Math.cos(axisAngle4d.angle);
        double d6 = 1.0 - d5;
        double d7 = axisAngle4d.x * axisAngle4d.z;
        double d8 = axisAngle4d.x * axisAngle4d.y;
        double d9 = axisAngle4d.y * axisAngle4d.z;
        this.m00 = (d6 * d1 * d1 + d5) * adouble1[0];
        this.m01 = (d6 * d8 - d4 * d3) * adouble1[1];
        this.m02 = (d6 * d7 + d4 * d2) * adouble1[2];
        this.m10 = (d6 * d8 + d4 * d3) * adouble1[0];
        this.m11 = (d6 * d2 * d2 + d5) * adouble1[1];
        this.m12 = (d6 * d9 - d4 * d1) * adouble1[2];
        this.m20 = (d6 * d7 - d4 * d2) * adouble1[0];
        this.m21 = (d6 * d9 + d4 * d1) * adouble1[1];
        this.m22 = (d6 * d3 * d3 + d5) * adouble1[2];
    }

    /** @deprecated */
    public boolean epsilonEquals(Matrix4d p171_0, float p171_1) {
        return this.epsilonEquals(p171_0, p171_1);
    }

    public final void transform(Tuple4f p171_0) {
        float f =
                (float)
                        (this.m00 * p171_0.x
                                + this.m01 * p171_0.y
                                + this.m02 * p171_0.z
                                + this.m03 * p171_0.w);
        float f1 =
                (float)
                        (this.m10 * p171_0.x
                                + this.m11 * p171_0.y
                                + this.m12 * p171_0.z
                                + this.m13 * p171_0.w);
        float f2 =
                (float)
                        (this.m20 * p171_0.x
                                + this.m21 * p171_0.y
                                + this.m22 * p171_0.z
                                + this.m23 * p171_0.w);
        p171_0.w =
                (float)
                        (this.m30 * p171_0.x
                                + this.m31 * p171_0.y
                                + this.m32 * p171_0.z
                                + this.m33 * p171_0.w);
        p171_0.x = f;
        p171_0.y = f1;
        p171_0.z = f2;
    }

    public final void negate() {
        this.m00 = -this.m00;
        this.m01 = -this.m01;
        this.m02 = -this.m02;
        this.m03 = -this.m03;
        this.m10 = -this.m10;
        this.m11 = -this.m11;
        this.m12 = -this.m12;
        this.m13 = -this.m13;
        this.m20 = -this.m20;
        this.m21 = -this.m21;
        this.m22 = -this.m22;
        this.m23 = -this.m23;
        this.m30 = -this.m30;
        this.m31 = -this.m31;
        this.m32 = -this.m32;
        this.m33 = -this.m33;
    }

    public final void set(Matrix4f p171_0) {
        this.m00 = p171_0.m00;
        this.m01 = p171_0.m01;
        this.m02 = p171_0.m02;
        this.m03 = p171_0.m03;
        this.m10 = p171_0.m10;
        this.m11 = p171_0.m11;
        this.m12 = p171_0.m12;
        this.m13 = p171_0.m13;
        this.m20 = p171_0.m20;
        this.m21 = p171_0.m21;
        this.m22 = p171_0.m22;
        this.m23 = p171_0.m23;
        this.m30 = p171_0.m30;
        this.m31 = p171_0.m31;
        this.m32 = p171_0.m32;
        this.m33 = p171_0.m33;
    }

    public final void setM00(double d0) {
        this.m00 = d0;
    }

    public Matrix4d() {
        this.m00 = 0.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = 0.0;
        this.m11 = 0.0;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 0.0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 0.0;
    }

    public final double getM33() {
        return this.m33;
    }

    public final double getM10() {
        return this.m10;
    }

    public final void mul(
            Matrix4d matrix4d) {
        double d0 =
                this.m00 * matrix4d.m00
                        + this.m01 * matrix4d.m10
                        + this.m02 * matrix4d.m20
                        + this.m03 * matrix4d.m30;
        double d1 =
                this.m00 * matrix4d.m01
                        + this.m01 * matrix4d.m11
                        + this.m02 * matrix4d.m21
                        + this.m03 * matrix4d.m31;
        double d2 =
                this.m00 * matrix4d.m02
                        + this.m01 * matrix4d.m12
                        + this.m02 * matrix4d.m22
                        + this.m03 * matrix4d.m32;
        double d3 =
                this.m00 * matrix4d.m03
                        + this.m01 * matrix4d.m13
                        + this.m02 * matrix4d.m23
                        + this.m03 * matrix4d.m33;
        double d4 =
                this.m10 * matrix4d.m00
                        + this.m11 * matrix4d.m10
                        + this.m12 * matrix4d.m20
                        + this.m13 * matrix4d.m30;
        double d5 =
                this.m10 * matrix4d.m01
                        + this.m11 * matrix4d.m11
                        + this.m12 * matrix4d.m21
                        + this.m13 * matrix4d.m31;
        double d6 =
                this.m10 * matrix4d.m02
                        + this.m11 * matrix4d.m12
                        + this.m12 * matrix4d.m22
                        + this.m13 * matrix4d.m32;
        double d7 =
                this.m10 * matrix4d.m03
                        + this.m11 * matrix4d.m13
                        + this.m12 * matrix4d.m23
                        + this.m13 * matrix4d.m33;
        double d8 =
                this.m20 * matrix4d.m00
                        + this.m21 * matrix4d.m10
                        + this.m22 * matrix4d.m20
                        + this.m23 * matrix4d.m30;
        double d9 =
                this.m20 * matrix4d.m01
                        + this.m21 * matrix4d.m11
                        + this.m22 * matrix4d.m21
                        + this.m23 * matrix4d.m31;
        Tuple3f.hF();
        double d10 =
                this.m20 * matrix4d.m02
                        + this.m21 * matrix4d.m12
                        + this.m22 * matrix4d.m22
                        + this.m23 * matrix4d.m32;
        double d11 =
                this.m20 * matrix4d.m03
                        + this.m21 * matrix4d.m13
                        + this.m22 * matrix4d.m23
                        + this.m23 * matrix4d.m33;
        double d12 =
                this.m30 * matrix4d.m00
                        + this.m31 * matrix4d.m10
                        + this.m32 * matrix4d.m20
                        + this.m33 * matrix4d.m30;
        double d13 =
                this.m30 * matrix4d.m01
                        + this.m31 * matrix4d.m11
                        + this.m32 * matrix4d.m21
                        + this.m33 * matrix4d.m31;
        double d14 =
                this.m30 * matrix4d.m02
                        + this.m31 * matrix4d.m12
                        + this.m32 * matrix4d.m22
                        + this.m33 * matrix4d.m32;
        double d15 =
                this.m30 * matrix4d.m03
                        + this.m31 * matrix4d.m13
                        + this.m32 * matrix4d.m23
                        + this.m33 * matrix4d.m33;
        this.m00 = d0;
        this.m01 = d1;
        this.m02 = d2;
        this.m03 = d3;
        this.m10 = d4;
        this.m11 = d5;
        this.m12 = d6;
        this.m13 = d7;
        this.m20 = d8;
        this.m21 = d9;
        this.m22 = d10;
        this.m23 = d11;
        this.m30 = d12;
        this.m31 = d13;
        this.m32 = d14;
        this.m33 = d15;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            Tuple3f.b(new String[2]);
        }
    }

    public final void setScale(double p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        this.m00 = adouble[0] * p171_0;
        this.m01 = adouble[1] * p171_0;
        this.m02 = adouble[2] * p171_0;
        this.m10 = adouble[3] * p171_0;
        this.m11 = adouble[4] * p171_0;
        this.m12 = adouble[5] * p171_0;
        this.m20 = adouble[6] * p171_0;
        this.m21 = adouble[7] * p171_0;
        this.m22 = adouble[8] * p171_0;
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Matrix4d matrix4d =
                    (remix.compat.vecmath.Matrix4d) p171_0;
            return (this.m00 == matrix4d.m00
                    && this.m01 == matrix4d.m01
                    && this.m02 == matrix4d.m02
                    && this.m03 == matrix4d.m03
                    && this.m10 == matrix4d.m10
                    && this.m11 == matrix4d.m11
                    && this.m12 == matrix4d.m12
                    && this.m13 == matrix4d.m13
                    && this.m20 == matrix4d.m20
                    && this.m21 == matrix4d.m21
                    && this.m22 == matrix4d.m22
                    && this.m23 == matrix4d.m23
                    && this.m30 == matrix4d.m30
                    && this.m31 == matrix4d.m31
                    && this.m32 == matrix4d.m32
                    && this.m33 == matrix4d.m33);
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final void set(
            Quat4f p171_0, Vector3d p171_1, double p171_2) {
        this.m00 = p171_2 * (1.0 - 2.0 * p171_0.y * p171_0.y - 2.0 * p171_0.z * p171_0.z);
        this.m10 = p171_2 * (2.0 * (p171_0.x * p171_0.y + p171_0.w * p171_0.z));
        this.m20 = p171_2 * (2.0 * (p171_0.x * p171_0.z - p171_0.w * p171_0.y));
        this.m01 = p171_2 * (2.0 * (p171_0.x * p171_0.y - p171_0.w * p171_0.z));
        this.m11 = p171_2 * (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.z * p171_0.z);
        this.m21 = p171_2 * (2.0 * (p171_0.y * p171_0.z + p171_0.w * p171_0.x));
        this.m02 = p171_2 * (2.0 * (p171_0.x * p171_0.z + p171_0.w * p171_0.y));
        this.m12 = p171_2 * (2.0 * (p171_0.y * p171_0.z - p171_0.w * p171_0.x));
        this.m22 = p171_2 * (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.y * p171_0.y);
        this.m03 = p171_1.x;
        this.m13 = p171_1.y;
        this.m23 = p171_1.z;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void setRotationScale(
            Matrix3d matrix3d) {
        this.m00 = matrix3d.m00;
        this.m01 = matrix3d.m01;
        this.m02 = matrix3d.m02;
        this.m10 = matrix3d.m10;
        this.m11 = matrix3d.m11;
        this.m12 = matrix3d.m12;
        this.m20 = matrix3d.m20;
        this.m21 = matrix3d.m21;
        this.m22 = matrix3d.m22;
    }

    public final void set(double[] p171_0) {
        this.m00 = p171_0[0];
        this.m01 = p171_0[1];
        this.m02 = p171_0[2];
        this.m03 = p171_0[3];
        this.m10 = p171_0[4];
        this.m11 = p171_0[5];
        this.m12 = p171_0[6];
        this.m13 = p171_0[7];
        this.m20 = p171_0[8];
        this.m21 = p171_0[9];
        this.m22 = p171_0[10];
        this.m23 = p171_0[11];
        this.m30 = p171_0[12];
        this.m31 = p171_0[13];
        this.m32 = p171_0[14];
        this.m33 = p171_0[15];
    }

    public final void setTranslation(
            Vector3d vector3d) {
        this.m03 = vector3d.x;
        this.m13 = vector3d.y;
        this.m23 = vector3d.z;
    }

    public final void get(Matrix3f p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        p171_0.m00 = (float) adouble[0];
        p171_0.m01 = (float) adouble[1];
        p171_0.m02 = (float) adouble[2];
        p171_0.m10 = (float) adouble[3];
        p171_0.m11 = (float) adouble[4];
        p171_0.m12 = (float) adouble[5];
        p171_0.m20 = (float) adouble[6];
        p171_0.m21 = (float) adouble[7];
        p171_0.m22 = (float) adouble[8];
    }

    public final void setM32(double d0) {
        this.m32 = d0;
    }

    public final void set(
            double p171_0, Vector3d p171_1) {
        this.m00 = p171_0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = p171_1.x;
        this.m10 = 0.0;
        this.m11 = p171_0;
        this.m12 = 0.0;
        this.m13 = p171_1.y;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = p171_0;
        this.m23 = p171_1.z;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void setColumn(int i, double[] adouble) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = adouble[0];
                this.m01 = adouble[1];
                this.m02 = adouble[2];
                this.m03 = adouble[3];
                if (astring == null) {
                    break;
                }
            case 1:
                this.m10 = adouble[0];
                this.m11 = adouble[1];
                this.m12 = adouble[2];
                this.m13 = adouble[3];
                if (astring == null) {
                    break;
                }
            case 2:
                this.m20 = adouble[0];
                this.m21 = adouble[1];
                this.m22 = adouble[2];
                this.m23 = adouble[3];
                if (astring == null) {
                    break;
                }
            case 3:
                this.m30 = adouble[0];
                this.m31 = adouble[1];
                this.m32 = adouble[2];
                this.m33 = adouble[3];
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix4d4"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
        }
    }

    public final void get(Quat4d p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        double d0 = 0.25 * (1.0 + adouble[0] + adouble[4] + adouble[8]);

        double d1;
        label63:
        {
            try {
                if (d0 < 0.0) {
                    d1 = -d0;
                    break label63;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception3) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception3);
            }

            d1 = d0;
        }

        if (!(d1 < 1.0E-30)) {
            p171_0.w = java.lang.Math.sqrt(d0);
            d0 = 0.25 / p171_0.w;
            p171_0.x = (adouble[7] - adouble[5]) * d0;
            p171_0.y = (adouble[2] - adouble[6]) * d0;
            p171_0.z = (adouble[3] - adouble[1]) * d0;
        } else {
            p171_0.w = 0.0;
            d0 = -0.5 * (adouble[4] + adouble[8]);

            label53:
            {
                try {
                    if (d0 < 0.0) {
                        d1 = -d0;
                        break label53;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception2) {
                    throw remix.compat.vecmath.Matrix4d.wrapException(
                            arrayindexoutofboundsexception2);
                }

                d1 = d0;
            }

            if (!(d1 < 1.0E-30)) {
                p171_0.x = java.lang.Math.sqrt(d0);
                d0 = 0.5 / p171_0.x;
                p171_0.y = adouble[3] * d0;
                p171_0.z = adouble[6] * d0;
            } else {
                p171_0.x = 0.0;
                d0 = 0.5 * (1.0 - adouble[8]);

                label43:
                {
                    try {
                        if (d0 < 0.0) {
                            d1 = -d0;
                            break label43;
                        }
                    } catch (
                            java.lang.ArrayIndexOutOfBoundsException
                                    arrayindexoutofboundsexception1) {
                        throw remix.compat.vecmath.Matrix4d.wrapException(
                                arrayindexoutofboundsexception1);
                    }

                    d1 = d0;
                }

                try {
                    if (!(d1 < 1.0E-30)) {
                        p171_0.y = java.lang.Math.sqrt(d0);
                        p171_0.z = adouble[7] / (2.0 * p171_0.y);
                        return;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
                    throw remix.compat.vecmath.Matrix4d.wrapException(
                            arrayindexoutofboundsexception);
                }

                p171_0.y = 0.0;
                p171_0.z = 1.0;
            }
        }
    }

    public final void setM30(double d0) {
        this.m30 = d0;
    }

    final void invertGeneral(Matrix4d matrix4d) {
        String[] astring1 = Tuple3f.hF();
        double[] adouble = new double[16];
        int[] aint = new int[4];
        double[] adouble1 = new double[16];
        adouble1[0] = matrix4d.m00;
        adouble1[1] = matrix4d.m01;
        adouble1[2] = matrix4d.m02;
        adouble1[3] = matrix4d.m03;
        adouble1[4] = matrix4d.m10;
        String[] astring = astring1;
        adouble1[5] = matrix4d.m11;
        adouble1[6] = matrix4d.m12;
        adouble1[7] = matrix4d.m13;
        adouble1[8] = matrix4d.m20;
        adouble1[9] = matrix4d.m21;
        adouble1[10] = matrix4d.m22;
        adouble1[11] = matrix4d.m23;
        adouble1[12] = matrix4d.m30;
        adouble1[13] = matrix4d.m31;
        adouble1[14] = matrix4d.m32;
        adouble1[15] = matrix4d.m33;
        if (!luDecomposition(adouble1, aint)) {
            throw new SingularMatrixException(VecMathI18N.getString("Matrix4d10"));
        }

        int i = 0;

        while (i < 16) {
            adouble[i] = 0.0;
            i++;
            if (astring != null) {
                break;
            }
        }

        adouble[0] = 1.0;
        adouble[5] = 1.0;
        adouble[10] = 1.0;
        adouble[15] = 1.0;
        luBacksubstitution(adouble1, aint, adouble);
        this.m00 = adouble[0];
        this.m01 = adouble[1];
        this.m02 = adouble[2];
        this.m03 = adouble[3];
        this.m10 = adouble[4];
        this.m11 = adouble[5];
        this.m12 = adouble[6];
        this.m13 = adouble[7];
        this.m20 = adouble[8];
        this.m21 = adouble[9];
        this.m22 = adouble[10];
        this.m23 = adouble[11];
        this.m30 = adouble[12];
        this.m31 = adouble[13];
        this.m32 = adouble[14];
        this.m33 = adouble[15];
    }

    public final void setM11(double d0) {
        this.m11 = d0;
    }

    public final void add(double p171_0) {
        this.m00 += p171_0;
        this.m01 += p171_0;
        this.m02 += p171_0;
        this.m03 += p171_0;
        this.m10 += p171_0;
        this.m11 += p171_0;
        this.m12 += p171_0;
        this.m13 += p171_0;
        this.m20 += p171_0;
        this.m21 += p171_0;
        this.m22 += p171_0;
        this.m23 += p171_0;
        this.m30 += p171_0;
        this.m31 += p171_0;
        this.m32 += p171_0;
        this.m33 += p171_0;
    }

    public final double get(
            Matrix3f p171_0, Vector3d p171_1) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        p171_0.m00 = (float) adouble[0];
        p171_0.m01 = (float) adouble[1];
        p171_0.m02 = (float) adouble[2];
        p171_0.m10 = (float) adouble[3];
        p171_0.m11 = (float) adouble[4];
        p171_0.m12 = (float) adouble[5];
        p171_0.m20 = (float) adouble[6];
        p171_0.m21 = (float) adouble[7];
        p171_0.m22 = (float) adouble[8];
        p171_1.x = this.m03;
        p171_1.y = this.m13;
        p171_1.z = this.m23;
        return remix.compat.vecmath.Matrix3d.max3(adouble1);
    }

    public final double getM32() {
        return this.m32;
    }

    public final void method_b4b839925438f6000bc4(
            Matrix4d matrix4d,
            Matrix4d matrix4dValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix4d && this != matrix4dValue) {
            this.m00 =
                    matrix4d.m00 * matrix4dValue.m00
                            + matrix4d.m01 * matrix4dValue.m01
                            + matrix4d.m02 * matrix4dValue.m02
                            + matrix4d.m03 * matrix4dValue.m03;
            this.m01 =
                    matrix4d.m00 * matrix4dValue.m10
                            + matrix4d.m01 * matrix4dValue.m11
                            + matrix4d.m02 * matrix4dValue.m12
                            + matrix4d.m03 * matrix4dValue.m13;
            this.m02 =
                    matrix4d.m00 * matrix4dValue.m20
                            + matrix4d.m01 * matrix4dValue.m21
                            + matrix4d.m02 * matrix4dValue.m22
                            + matrix4d.m03 * matrix4dValue.m23;
            this.m03 =
                    matrix4d.m00 * matrix4dValue.m30
                            + matrix4d.m01 * matrix4dValue.m31
                            + matrix4d.m02 * matrix4dValue.m32
                            + matrix4d.m03 * matrix4dValue.m33;
            this.m10 =
                    matrix4d.m10 * matrix4dValue.m00
                            + matrix4d.m11 * matrix4dValue.m01
                            + matrix4d.m12 * matrix4dValue.m02
                            + matrix4d.m13 * matrix4dValue.m03;
            this.m11 =
                    matrix4d.m10 * matrix4dValue.m10
                            + matrix4d.m11 * matrix4dValue.m11
                            + matrix4d.m12 * matrix4dValue.m12
                            + matrix4d.m13 * matrix4dValue.m13;
            this.m12 =
                    matrix4d.m10 * matrix4dValue.m20
                            + matrix4d.m11 * matrix4dValue.m21
                            + matrix4d.m12 * matrix4dValue.m22
                            + matrix4d.m13 * matrix4dValue.m23;
            this.m13 =
                    matrix4d.m10 * matrix4dValue.m30
                            + matrix4d.m11 * matrix4dValue.m31
                            + matrix4d.m12 * matrix4dValue.m32
                            + matrix4d.m13 * matrix4dValue.m33;
            this.m20 =
                    matrix4d.m20 * matrix4dValue.m00
                            + matrix4d.m21 * matrix4dValue.m01
                            + matrix4d.m22 * matrix4dValue.m02
                            + matrix4d.m23 * matrix4dValue.m03;
            this.m21 =
                    matrix4d.m20 * matrix4dValue.m10
                            + matrix4d.m21 * matrix4dValue.m11
                            + matrix4d.m22 * matrix4dValue.m12
                            + matrix4d.m23 * matrix4dValue.m13;
            this.m22 =
                    matrix4d.m20 * matrix4dValue.m20
                            + matrix4d.m21 * matrix4dValue.m21
                            + matrix4d.m22 * matrix4dValue.m22
                            + matrix4d.m23 * matrix4dValue.m23;
            this.m23 =
                    matrix4d.m20 * matrix4dValue.m30
                            + matrix4d.m21 * matrix4dValue.m31
                            + matrix4d.m22 * matrix4dValue.m32
                            + matrix4d.m23 * matrix4dValue.m33;
            this.m30 =
                    matrix4d.m30 * matrix4dValue.m00
                            + matrix4d.m31 * matrix4dValue.m01
                            + matrix4d.m32 * matrix4dValue.m02
                            + matrix4d.m33 * matrix4dValue.m03;
            this.m31 =
                    matrix4d.m30 * matrix4dValue.m10
                            + matrix4d.m31 * matrix4dValue.m11
                            + matrix4d.m32 * matrix4dValue.m12
                            + matrix4d.m33 * matrix4dValue.m13;
            this.m32 =
                    matrix4d.m30 * matrix4dValue.m20
                            + matrix4d.m31 * matrix4dValue.m21
                            + matrix4d.m32 * matrix4dValue.m22
                            + matrix4d.m33 * matrix4dValue.m23;
            this.m33 =
                    matrix4d.m30 * matrix4dValue.m30
                            + matrix4d.m31 * matrix4dValue.m31
                            + matrix4d.m32 * matrix4dValue.m32
                            + matrix4d.m33 * matrix4dValue.m33;
            if (astring == null) {
                return;
            }
        }

        double d0 =
                matrix4d.m00 * matrix4dValue.m00
                        + matrix4d.m01 * matrix4dValue.m01
                        + matrix4d.m02 * matrix4dValue.m02
                        + matrix4d.m03 * matrix4dValue.m03;
        double d1 =
                matrix4d.m00 * matrix4dValue.m10
                        + matrix4d.m01 * matrix4dValue.m11
                        + matrix4d.m02 * matrix4dValue.m12
                        + matrix4d.m03 * matrix4dValue.m13;
        double d2 =
                matrix4d.m00 * matrix4dValue.m20
                        + matrix4d.m01 * matrix4dValue.m21
                        + matrix4d.m02 * matrix4dValue.m22
                        + matrix4d.m03 * matrix4dValue.m23;
        double d3 =
                matrix4d.m00 * matrix4dValue.m30
                        + matrix4d.m01 * matrix4dValue.m31
                        + matrix4d.m02 * matrix4dValue.m32
                        + matrix4d.m03 * matrix4dValue.m33;
        double d4 =
                matrix4d.m10 * matrix4dValue.m00
                        + matrix4d.m11 * matrix4dValue.m01
                        + matrix4d.m12 * matrix4dValue.m02
                        + matrix4d.m13 * matrix4dValue.m03;
        double d5 =
                matrix4d.m10 * matrix4dValue.m10
                        + matrix4d.m11 * matrix4dValue.m11
                        + matrix4d.m12 * matrix4dValue.m12
                        + matrix4d.m13 * matrix4dValue.m13;
        double d6 =
                matrix4d.m10 * matrix4dValue.m20
                        + matrix4d.m11 * matrix4dValue.m21
                        + matrix4d.m12 * matrix4dValue.m22
                        + matrix4d.m13 * matrix4dValue.m23;
        double d7 =
                matrix4d.m10 * matrix4dValue.m30
                        + matrix4d.m11 * matrix4dValue.m31
                        + matrix4d.m12 * matrix4dValue.m32
                        + matrix4d.m13 * matrix4dValue.m33;
        double d8 =
                matrix4d.m20 * matrix4dValue.m00
                        + matrix4d.m21 * matrix4dValue.m01
                        + matrix4d.m22 * matrix4dValue.m02
                        + matrix4d.m23 * matrix4dValue.m03;
        double d9 =
                matrix4d.m20 * matrix4dValue.m10
                        + matrix4d.m21 * matrix4dValue.m11
                        + matrix4d.m22 * matrix4dValue.m12
                        + matrix4d.m23 * matrix4dValue.m13;
        double d10 =
                matrix4d.m20 * matrix4dValue.m20
                        + matrix4d.m21 * matrix4dValue.m21
                        + matrix4d.m22 * matrix4dValue.m22
                        + matrix4d.m23 * matrix4dValue.m23;
        double d11 =
                matrix4d.m20 * matrix4dValue.m30
                        + matrix4d.m21 * matrix4dValue.m31
                        + matrix4d.m22 * matrix4dValue.m32
                        + matrix4d.m23 * matrix4dValue.m33;
        double d12 =
                matrix4d.m30 * matrix4dValue.m00
                        + matrix4d.m31 * matrix4dValue.m01
                        + matrix4d.m32 * matrix4dValue.m02
                        + matrix4d.m33 * matrix4dValue.m03;
        double d13 =
                matrix4d.m30 * matrix4dValue.m10
                        + matrix4d.m31 * matrix4dValue.m11
                        + matrix4d.m32 * matrix4dValue.m12
                        + matrix4d.m33 * matrix4dValue.m13;
        double d14 =
                matrix4d.m30 * matrix4dValue.m20
                        + matrix4d.m31 * matrix4dValue.m21
                        + matrix4d.m32 * matrix4dValue.m22
                        + matrix4d.m33 * matrix4dValue.m23;
        double d15 =
                matrix4d.m30 * matrix4dValue.m30
                        + matrix4d.m31 * matrix4dValue.m31
                        + matrix4d.m32 * matrix4dValue.m32
                        + matrix4d.m33 * matrix4dValue.m33;
        this.m00 = d0;
        this.m01 = d1;
        this.m02 = d2;
        this.m03 = d3;
        this.m10 = d4;
        this.m11 = d5;
        this.m12 = d6;
        this.m13 = d7;
        this.m20 = d8;
        this.m21 = d9;
        this.m22 = d10;
        this.m23 = d11;
        this.m30 = d12;
        this.m31 = d13;
        this.m32 = d14;
        this.m33 = d15;
    }

    public final void method_458c1a6d46255575b701(
            int i, Vector4d vector4d) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            vector4d.x = this.m00;
            vector4d.y = this.m10;
            vector4d.z = this.m20;
            vector4d.w = this.m30;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            vector4d.x = this.m01;
            vector4d.y = this.m11;
            vector4d.z = this.m21;
            vector4d.w = this.m31;
            if (astring == null) {
                return;
            }
        }

        if (i == 2) {
            vector4d.x = this.m02;
            vector4d.y = this.m12;
            vector4d.z = this.m22;
            vector4d.w = this.m32;
            if (astring == null) {
                return;
            }
        }

        if (i != 3) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d3"));
        }

        vector4d.x = this.m03;
        vector4d.y = this.m13;
        vector4d.z = this.m23;
        vector4d.w = this.m33;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d3"));
        }
    }

    public final void rotX(double p171_0) {
        double d1 = java.lang.Math.sin(p171_0);
        double d2 = java.lang.Math.cos(p171_0);
        this.m00 = 1.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = 0.0;
        this.m11 = d2;
        this.m12 = -d1;
        this.m13 = 0.0;
        this.m20 = 0.0;
        this.m21 = d1;
        this.m22 = d2;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public Matrix4d(Matrix4f p171_0) {
        this.m00 = p171_0.m00;
        this.m01 = p171_0.m01;
        this.m02 = p171_0.m02;
        this.m03 = p171_0.m03;
        this.m10 = p171_0.m10;
        this.m11 = p171_0.m11;
        this.m12 = p171_0.m12;
        this.m13 = p171_0.m13;
        this.m20 = p171_0.m20;
        this.m21 = p171_0.m21;
        this.m22 = p171_0.m22;
        this.m23 = p171_0.m23;
        this.m30 = p171_0.m30;
        this.m31 = p171_0.m31;
        this.m32 = p171_0.m32;
        this.m33 = p171_0.m33;
    }

    @Override
    public String toString() {
        return this.m00
                + ", "
                + this.m01
                + ", "
                + this.m02
                + ", "
                + this.m03
                + packedTextFragments[0]
                + this.m10
                + ", "
                + this.m11
                + ", "
                + this.m12
                + ", "
                + this.m13
                + packedTextFragments[1]
                + this.m20
                + ", "
                + this.m21
                + ", "
                + this.m22
                + ", "
                + this.m23
                + packedTextFragments[2]
                + this.m30
                + ", "
                + this.m31
                + ", "
                + this.m32
                + ", "
                + this.m33
                + packedTextFragments[3];
    }

    public final void setM01(double d0) {
        this.m01 = d0;
    }

    public Matrix4d(double[] p171_0) {
        this.m00 = p171_0[0];
        this.m01 = p171_0[1];
        this.m02 = p171_0[2];
        this.m03 = p171_0[3];
        this.m10 = p171_0[4];
        this.m11 = p171_0[5];
        this.m12 = p171_0[6];
        this.m13 = p171_0[7];
        this.m20 = p171_0[8];
        this.m21 = p171_0[9];
        this.m22 = p171_0[10];
        this.m23 = p171_0[11];
        this.m30 = p171_0[12];
        this.m31 = p171_0[13];
        this.m32 = p171_0[14];
        this.m33 = p171_0[15];
    }

    public final void transform(
            Tuple4f p171_0, Tuple4f p171_1) {
        float f =
                (float)
                        (this.m00 * p171_0.x
                                + this.m01 * p171_0.y
                                + this.m02 * p171_0.z
                                + this.m03 * p171_0.w);
        float f1 =
                (float)
                        (this.m10 * p171_0.x
                                + this.m11 * p171_0.y
                                + this.m12 * p171_0.z
                                + this.m13 * p171_0.w);
        float f2 =
                (float)
                        (this.m20 * p171_0.x
                                + this.m21 * p171_0.y
                                + this.m22 * p171_0.z
                                + this.m23 * p171_0.w);
        p171_1.w =
                (float)
                        (this.m30 * p171_0.x
                                + this.m31 * p171_0.y
                                + this.m32 * p171_0.z
                                + this.m33 * p171_0.w);
        p171_1.x = f;
        p171_1.y = f1;
        p171_1.z = f2;
    }

    public final void setM12(double d0) {
        this.m12 = d0;
    }

    private final void getScaleRotate(double[] adouble, double[] adouble1) {
        double[] adouble2 =
                new double[] {
                    this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22
                };
        Matrix3d.compute_svd(adouble2, adouble, adouble1);
    }

    public final void setRotation(Quat4d p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        this.m00 = (1.0 - 2.0 * p171_0.y * p171_0.y - 2.0 * p171_0.z * p171_0.z) * adouble1[0];
        this.m10 = 2.0 * (p171_0.x * p171_0.y + p171_0.w * p171_0.z) * adouble1[0];
        this.m20 = 2.0 * (p171_0.x * p171_0.z - p171_0.w * p171_0.y) * adouble1[0];
        this.m01 = 2.0 * (p171_0.x * p171_0.y - p171_0.w * p171_0.z) * adouble1[1];
        this.m11 = (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.z * p171_0.z) * adouble1[1];
        this.m21 = 2.0 * (p171_0.y * p171_0.z + p171_0.w * p171_0.x) * adouble1[1];
        this.m02 = 2.0 * (p171_0.x * p171_0.z + p171_0.w * p171_0.y) * adouble1[2];
        this.m12 = 2.0 * (p171_0.y * p171_0.z - p171_0.w * p171_0.x) * adouble1[2];
        this.m22 = (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.y * p171_0.y) * adouble1[2];
    }

    public final void negate(Matrix4d p171_0) {
        this.m00 = -p171_0.m00;
        this.m01 = -p171_0.m01;
        this.m02 = -p171_0.m02;
        this.m03 = -p171_0.m03;
        this.m10 = -p171_0.m10;
        this.m11 = -p171_0.m11;
        this.m12 = -p171_0.m12;
        this.m13 = -p171_0.m13;
        this.m20 = -p171_0.m20;
        this.m21 = -p171_0.m21;
        this.m22 = -p171_0.m22;
        this.m23 = -p171_0.m23;
        this.m30 = -p171_0.m30;
        this.m31 = -p171_0.m31;
        this.m32 = -p171_0.m32;
        this.m33 = -p171_0.m33;
    }

    public final void setRow(int i, double[] adouble) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            adouble[0] = this.m00;
            adouble[1] = this.m01;
            adouble[2] = this.m02;
            adouble[3] = this.m03;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            adouble[0] = this.m10;
            adouble[1] = this.m11;
            adouble[2] = this.m12;
            adouble[3] = this.m13;
            if (astring == null) {
                return;
            }
        }

        if (i == 2) {
            adouble[0] = this.m20;
            adouble[1] = this.m21;
            adouble[2] = this.m22;
            adouble[3] = this.m23;
            if (astring == null) {
                return;
            }
        }

        if (i != 3) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d2"));
        }

        adouble[0] = this.m30;
        adouble[1] = this.m31;
        adouble[2] = this.m32;
        adouble[3] = this.m33;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d2"));
        }
    }

    public Matrix4d(
            Matrix3d p171_0, Vector3d p171_1, double p171_2) {
        this.m00 = p171_0.m00 * p171_2;
        this.m01 = p171_0.m01 * p171_2;
        this.m02 = p171_0.m02 * p171_2;
        this.m03 = p171_1.x;
        this.m10 = p171_0.m10 * p171_2;
        this.m11 = p171_0.m11 * p171_2;
        this.m12 = p171_0.m12 * p171_2;
        this.m13 = p171_1.y;
        this.m20 = p171_0.m20 * p171_2;
        this.m21 = p171_0.m21 * p171_2;
        this.m22 = p171_0.m22 * p171_2;
        this.m23 = p171_1.z;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void set(
            Quat4f p171_0, Vector3f p171_1, float p171_2) {
        this.m00 = p171_2 * (1.0 - 2.0 * p171_0.y * p171_0.y - 2.0 * p171_0.z * p171_0.z);
        this.m10 = p171_2 * (2.0 * (p171_0.x * p171_0.y + p171_0.w * p171_0.z));
        this.m20 = p171_2 * (2.0 * (p171_0.x * p171_0.z - p171_0.w * p171_0.y));
        this.m01 = p171_2 * (2.0 * (p171_0.x * p171_0.y - p171_0.w * p171_0.z));
        this.m11 = p171_2 * (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.z * p171_0.z);
        this.m21 = p171_2 * (2.0 * (p171_0.y * p171_0.z + p171_0.w * p171_0.x));
        this.m02 = p171_2 * (2.0 * (p171_0.x * p171_0.z + p171_0.w * p171_0.y));
        this.m12 = p171_2 * (2.0 * (p171_0.y * p171_0.z - p171_0.w * p171_0.x));
        this.m22 = p171_2 * (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.y * p171_0.y);
        this.m03 = p171_1.x;
        this.m13 = p171_1.y;
        this.m23 = p171_1.z;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void transform(Vector3f p171_0) {
        float f = (float) (this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z);
        float f1 = (float) (this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z);
        p171_0.z = (float) (this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z);
        p171_0.x = f;
        p171_0.y = f1;
    }

    public final void method_11a791458aa2b803872a(
            int i, Vector4d vector4d) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            vector4d.x = this.m00;
            vector4d.y = this.m01;
            vector4d.z = this.m02;
            vector4d.w = this.m03;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            vector4d.x = this.m10;
            vector4d.y = this.m11;
            vector4d.z = this.m12;
            vector4d.w = this.m13;
            if (astring == null) {
                return;
            }
        }

        if (i == 2) {
            vector4d.x = this.m20;
            vector4d.y = this.m21;
            vector4d.z = this.m22;
            vector4d.w = this.m23;
            if (astring == null) {
                return;
            }
        }

        if (i != 3) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d2"));
        }

        vector4d.x = this.m30;
        vector4d.y = this.m31;
        vector4d.z = this.m32;
        vector4d.w = this.m33;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d2"));
        }
    }

    static void luBacksubstitution(double[] adouble, int[] aint, double[] adouble1) {
        String[] astring1 = Tuple3f.hF();
        byte b0 = 0;
        String[] astring = astring1;
        int i1 = 0;

        while (i1 < 4) {
            int j1 = i1;
            int j = -1;
            int i = 0;

            while (true) {
                if (i < 4) {
                    double d0;
                    label32:
                    {
                        int k = aint[b0 + i];
                        d0 = adouble1[j1 + 4 * k];
                        adouble1[j1 + 4 * k] = adouble1[j1 + 4 * i];
                        if (j >= 0) {
                            int k1 = i * 4;
                            int l = j;

                            do {
                                if (l > i - 1) {
                                    break label32;
                                }

                                d0 -= adouble[k1 + l] * adouble1[j1 + 4 * l];
                                l++;
                            } while (astring == null);
                        }

                        if (d0 != 0.0) {
                            j = i;
                        }
                    }

                    adouble1[j1 + 4 * i] = d0;
                    i++;
                    if (astring == null) {
                        continue;
                    }
                }

                int l1 = 12;
                adouble1[j1 + 12] = adouble1[j1 + 12] / adouble[l1 + 3];
                l1 -= 4;
                adouble1[j1 + 8] =
                        (adouble1[j1 + 8] - adouble[l1 + 3] * adouble1[j1 + 12]) / adouble[l1 + 2];
                l1 -= 4;
                adouble1[j1 + 4] =
                        (adouble1[j1 + 4]
                                        - adouble[l1 + 2] * adouble1[j1 + 8]
                                        - adouble[l1 + 3] * adouble1[j1 + 12])
                                / adouble[l1 + 1];
                l1 -= 4;
                adouble1[j1 + 0] =
                        (adouble1[j1 + 0]
                                        - adouble[l1 + 1] * adouble1[j1 + 4]
                                        - adouble[l1 + 2] * adouble1[j1 + 8]
                                        - adouble[l1 + 3] * adouble1[j1 + 12])
                                / adouble[l1 + 0];
                i1++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public final double getM00() {
        return this.m00;
    }

    public final double getScale() {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        return remix.compat.vecmath.Matrix3d.max3(adouble1);
    }

    public final void setRotationScale(Matrix3f p171_0) {
        this.m00 = p171_0.m00;
        this.m01 = p171_0.m01;
        this.m02 = p171_0.m02;
        this.m10 = p171_0.m10;
        this.m11 = p171_0.m11;
        this.m12 = p171_0.m12;
        this.m20 = p171_0.m20;
        this.m21 = p171_0.m21;
        this.m22 = p171_0.m22;
    }

    public final void transform(Vector3d p171_0) {
        double d0 = this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z;
        double d1 = this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z;
        p171_0.z = this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z;
        p171_0.x = d0;
        p171_0.y = d1;
    }

    static boolean luDecomposition(double[] adouble, int[] aint) {
        String[] astring1 = Tuple3f.hF();
        double[] adouble1 = new double[4];
        int k = 0;
        int l = 0;
        String[] astring = astring1;
        int i = 4;

        while (i-- != 0) {
            double d0 = 0.0;
            int j = 4;

            while (j-- != 0) {
                double d1 = adouble[k++];
                d1 = Math.abs(d1);
                if (d1 > d0) {
                    d0 = d1;
                    if (astring != null) {
                        break;
                    }
                }
            }

            if (d0 == 0.0) {
                return false;
            }

            adouble1[l++] = 1.0 / d0;
            if (astring != null) {
                break;
            }
        }

        byte b0 = 0;
        i = 0;

        while (i < 4) {
            k = 0;

            label96:
            while (k < i) {
                int i1 = b0 + 4 * k + i;
                double d2 = adouble[i1];
                int k1 = k;
                int l2 = b0 + 4 * k;
                int j1 = b0 + i;

                while (true) {
                    if (k1-- != 0) {
                        d2 -= adouble[l2] * adouble[j1];
                        l2++;
                        j1 += 4;
                        if (astring == null) {
                            continue;
                        }
                    }

                    adouble[i1] = d2;
                    k++;
                    if (astring != null) {
                        break label96;
                    }
                    break;
                }
            }

            double d3 = 0.0;
            l = -1;
            k = i;

            while (k < 4) {
                int j2 = b0 + 4 * k + i;
                double d5 = adouble[j2];
                int l1 = i;
                int i3 = b0 + 4 * k;
                int k3 = b0 + i;

                while (l1-- != 0) {
                    d5 -= adouble[i3] * adouble[k3];
                    i3++;
                    k3 += 4;
                    if (astring != null) {
                        break;
                    }
                }

                adouble[j2] = d5;
                double d4;
                if ((d4 = adouble1[k] * Math.abs(d5)) >= d3) {
                    d3 = d4;
                    l = k;
                }

                k++;
                if (astring != null) {
                    break;
                }
            }

            if (l < 0) {
                throw new RuntimeException(VecMathI18N.getString("Matrix4d11"));
            }

            if (i != l) {
                int i2 = 4;
                int j3 = b0 + 4 * l;
                int l3 = b0 + 4 * i;

                while (i2-- != 0) {
                    double d6 = adouble[j3];
                    adouble[j3++] = adouble[l3];
                    adouble[l3++] = d6;
                    if (astring != null) {
                        break;
                    }
                }

                adouble1[l] = adouble1[i];
            }

            aint[i] = l;
            if (adouble[b0 + 4 * i + i] == 0.0) {
                return false;
            }

            if (i != 3) {
                double d7 = 1.0 / adouble[b0 + 4 * i + i];
                int k2 = b0 + 4 * (i + 1) + i;
                k = 3 - i;

                while (k-- != 0) {
                    adouble[k2] *= d7;
                    k2 += 4;
                    if (astring != null) {
                        break;
                    }
                }
            }

            i++;
            if (astring != null) {
                break;
            }
        }

        return true;
    }

    public final double getM12() {
        return this.m12;
    }

    public final void setElement(int i, int j, double d0) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                switch (j) {
                    case 0:
                        this.m00 = d0;
                        if (astring == null) {
                            return;
                        }
                    case 1:
                        this.m01 = d0;
                        if (astring == null) {
                            return;
                        }
                    case 2:
                        this.m02 = d0;
                        if (astring == null) {
                            return;
                        }
                    case 3:
                        this.m03 = d0;
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix4d0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix4d0"));
                }
            case 1:
                switch (j) {
                    case 0:
                        this.m10 = d0;
                        if (astring == null) {
                            return;
                        }
                    case 1:
                        this.m11 = d0;
                        if (astring == null) {
                            return;
                        }
                    case 2:
                        this.m12 = d0;
                        if (astring == null) {
                            return;
                        }
                    case 3:
                        this.m13 = d0;
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix4d0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix4d0"));
                }
            case 2:
                switch (j) {
                    case 0:
                        this.m20 = d0;
                        if (astring == null) {
                            return;
                        }
                    case 1:
                        this.m21 = d0;
                        if (astring == null) {
                            return;
                        }
                    case 2:
                        this.m22 = d0;
                        if (astring == null) {
                            return;
                        }
                    case 3:
                        this.m23 = d0;
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix4d0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix4d0"));
                }
            case 3:
                switch (j) {
                    case 0:
                        this.m30 = d0;
                        if (astring == null) {
                            return;
                        }
                    case 1:
                        this.m31 = d0;
                        if (astring == null) {
                            return;
                        }
                    case 2:
                        this.m32 = d0;
                        if (astring == null) {
                            return;
                        }
                    case 3:
                        this.m33 = d0;
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix4d0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix4d0"));
                }
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
        }
    }

    public final void rotZ(double p171_0) {
        double d1 = java.lang.Math.sin(p171_0);
        double d2 = java.lang.Math.cos(p171_0);
        this.m00 = d2;
        this.m01 = -d1;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = d1;
        this.m11 = d2;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 1.0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void getRow(int i, double[] adouble) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = adouble[0];
                this.m10 = adouble[1];
                this.m20 = adouble[2];
                this.m30 = adouble[3];
                if (astring == null) {
                    break;
                }
            case 1:
                this.m01 = adouble[0];
                this.m11 = adouble[1];
                this.m21 = adouble[2];
                this.m31 = adouble[3];
                if (astring == null) {
                    break;
                }
            case 2:
                this.m02 = adouble[0];
                this.m12 = adouble[1];
                this.m22 = adouble[2];
                this.m32 = adouble[3];
                if (astring == null) {
                    break;
                }
            case 3:
                this.m03 = adouble[0];
                this.m13 = adouble[1];
                this.m23 = adouble[2];
                this.m33 = adouble[3];
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix4d7"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
        }
    }

    public boolean epsilonEquals(Matrix4d p171_0, double p171_1) {
        double d1 = this.m00 - p171_0.m00;

        double d2;
        label384:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label384;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception31) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception31);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception30) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception30);
        }

        d1 = this.m01 - p171_0.m01;

        label370:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label370;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception29) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception29);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception28) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception28);
        }

        d1 = this.m02 - p171_0.m02;

        label356:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label356;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception27) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception27);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception26) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception26);
        }

        d1 = this.m03 - p171_0.m03;

        label342:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label342;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception25) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception25);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception24) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception24);
        }

        d1 = this.m10 - p171_0.m10;

        label328:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label328;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception23) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception23);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception22) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception22);
        }

        d1 = this.m11 - p171_0.m11;

        label314:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label314;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception21) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception21);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception20) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception20);
        }

        d1 = this.m12 - p171_0.m12;

        label300:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label300;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception19) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception19);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception18) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception18);
        }

        d1 = this.m13 - p171_0.m13;

        label286:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label286;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception17) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception17);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception16) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception16);
        }

        d1 = this.m20 - p171_0.m20;

        label272:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label272;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception15) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception15);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception14) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception14);
        }

        d1 = this.m21 - p171_0.m21;

        label258:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label258;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception13) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception13);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception12) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception12);
        }

        d1 = this.m22 - p171_0.m22;

        label244:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label244;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception11) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception11);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception10) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception10);
        }

        d1 = this.m23 - p171_0.m23;

        label230:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label230;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception9) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception9);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception8) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception8);
        }

        d1 = this.m30 - p171_0.m30;

        label216:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label216;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception7) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception7);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception6) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception6);
        }

        d1 = this.m31 - p171_0.m31;

        label202:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label202;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception5) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception5);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception4) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception4);
        }

        d1 = this.m32 - p171_0.m32;

        label188:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label188;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception3) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception3);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception2) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception2);
        }

        d1 = this.m33 - p171_0.m33;

        label174:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label174;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception1) {
                throw remix.compat.vecmath.Matrix4d.wrapException(
                        arrayindexoutofboundsexception1);
            }

            d2 = d1;
        }

        try {
            return !(d2 > p171_1);
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            throw remix.compat.vecmath.Matrix4d.wrapException(
                    arrayindexoutofboundsexception);
        }
    }

    public final void setM02(double d0) {
        this.m02 = d0;
    }

    public final void setRotation(Matrix3d p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        this.m00 = p171_0.m00 * adouble1[0];
        this.m01 = p171_0.m01 * adouble1[1];
        this.m02 = p171_0.m02 * adouble1[2];
        this.m10 = p171_0.m10 * adouble1[0];
        this.m11 = p171_0.m11 * adouble1[1];
        this.m12 = p171_0.m12 * adouble1[2];
        this.m20 = p171_0.m20 * adouble1[0];
        this.m21 = p171_0.m21 * adouble1[1];
        this.m22 = p171_0.m22 * adouble1[2];
    }

    public final void set(double p171_0) {
        this.m00 = p171_0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = 0.0;
        this.m11 = p171_0;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = p171_0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void set(
            Matrix3d p171_0, Vector3d p171_1, double p171_2) {
        this.m00 = p171_0.m00 * p171_2;
        this.m01 = p171_0.m01 * p171_2;
        this.m02 = p171_0.m02 * p171_2;
        this.m03 = p171_1.x;
        this.m10 = p171_0.m10 * p171_2;
        this.m11 = p171_0.m11 * p171_2;
        this.m12 = p171_0.m12 * p171_2;
        this.m13 = p171_1.y;
        this.m20 = p171_0.m20 * p171_2;
        this.m21 = p171_0.m21 * p171_2;
        this.m22 = p171_0.m22 * p171_2;
        this.m23 = p171_1.z;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void transpose() {
        double d0 = this.m10;
        this.m10 = this.m01;
        this.m01 = d0;
        d0 = this.m20;
        this.m20 = this.m02;
        this.m02 = d0;
        d0 = this.m30;
        this.m30 = this.m03;
        this.m03 = d0;
        d0 = this.m21;
        this.m21 = this.m12;
        this.m12 = d0;
        d0 = this.m31;
        this.m31 = this.m13;
        this.m13 = d0;
        d0 = this.m32;
        this.m32 = this.m23;
        this.m23 = d0;
    }

    public final void setIdentity() {
        this.m00 = 1.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m03 = 0.0;
        this.m10 = 0.0;
        this.m11 = 1.0;
        this.m12 = 0.0;
        this.m13 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 1.0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void set(Quat4d p171_0) {
        this.m00 = 1.0 - 2.0 * p171_0.y * p171_0.y - 2.0 * p171_0.z * p171_0.z;
        this.m10 = 2.0 * (p171_0.x * p171_0.y + p171_0.w * p171_0.z);
        this.m20 = 2.0 * (p171_0.x * p171_0.z - p171_0.w * p171_0.y);
        this.m01 = 2.0 * (p171_0.x * p171_0.y - p171_0.w * p171_0.z);
        this.m11 = 1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.z * p171_0.z;
        this.m21 = 2.0 * (p171_0.y * p171_0.z + p171_0.w * p171_0.x);
        this.m02 = 2.0 * (p171_0.x * p171_0.z + p171_0.w * p171_0.y);
        this.m12 = 2.0 * (p171_0.y * p171_0.z - p171_0.w * p171_0.x);
        this.m22 = 1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.y * p171_0.y;
        this.m03 = 0.0;
        this.m13 = 0.0;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void setM20(double d0) {
        this.m20 = d0;
    }

    public final double ev() {
        double d0 =
                this.m00
                        * (this.m11 * this.m22 * this.m33
                                + this.m12 * this.m23 * this.m31
                                + this.m13 * this.m21 * this.m32
                                - this.m13 * this.m22 * this.m31
                                - this.m11 * this.m23 * this.m32
                                - this.m12 * this.m21 * this.m33);
        d0 -=
                this.m01
                        * (this.m10 * this.m22 * this.m33
                                + this.m12 * this.m23 * this.m30
                                + this.m13 * this.m20 * this.m32
                                - this.m13 * this.m22 * this.m30
                                - this.m10 * this.m23 * this.m32
                                - this.m12 * this.m20 * this.m33);
        d0 +=
                this.m02
                        * (this.m10 * this.m21 * this.m33
                                + this.m11 * this.m23 * this.m30
                                + this.m13 * this.m20 * this.m31
                                - this.m13 * this.m21 * this.m30
                                - this.m10 * this.m23 * this.m31
                                - this.m11 * this.m20 * this.m33);
        return d0
                - this.m03
                        * (this.m10 * this.m21 * this.m32
                                + this.m11 * this.m22 * this.m30
                                + this.m12 * this.m20 * this.m31
                                - this.m12 * this.m21 * this.m30
                                - this.m10 * this.m22 * this.m31
                                - this.m11 * this.m20 * this.m32);
    }

    public boolean equals(Matrix4d p171_0) {
        try {
            return (this.m00 == p171_0.m00
                    && this.m01 == p171_0.m01
                    && this.m02 == p171_0.m02
                    && this.m03 == p171_0.m03
                    && this.m10 == p171_0.m10
                    && this.m11 == p171_0.m11
                    && this.m12 == p171_0.m12
                    && this.m13 == p171_0.m13
                    && this.m20 == p171_0.m20
                    && this.m21 == p171_0.m21
                    && this.m22 == p171_0.m22
                    && this.m23 == p171_0.m23
                    && this.m30 == p171_0.m30
                    && this.m31 == p171_0.m31
                    && this.m32 == p171_0.m32
                    && this.m33 == p171_0.m33);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final double getM21() {
        return this.m21;
    }

    public final void invert(
            Matrix4d matrix4d) {
        this.invertGeneral(matrix4d);
    }

    public final double getM13() {
        return this.m13;
    }

    public final void method_149efabb3444b5ed1c94(
            int i, double d0, double d1, double d2, double d3) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = d0;
                this.m10 = d1;
                this.m20 = d2;
                this.m30 = d3;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m01 = d0;
                this.m11 = d1;
                this.m21 = d2;
                this.m31 = d3;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m02 = d0;
                this.m12 = d1;
                this.m22 = d2;
                this.m32 = d3;
                if (astring == null) {
                    break;
                }
            case 3:
                this.m03 = d0;
                this.m13 = d1;
                this.m23 = d2;
                this.m33 = d3;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix4d7"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
        }
    }

    public final void get(
            Vector3d vector3d) {
        vector3d.x = this.m03;
        vector3d.y = this.m13;
        vector3d.z = this.m23;
    }

    public final void set(
            Matrix3f p171_0, Vector3f p171_1, float p171_2) {
        this.m00 = p171_0.m00 * p171_2;
        this.m01 = p171_0.m01 * p171_2;
        this.m02 = p171_0.m02 * p171_2;
        this.m03 = p171_1.x;
        this.m10 = p171_0.m10 * p171_2;
        this.m11 = p171_0.m11 * p171_2;
        this.m12 = p171_0.m12 * p171_2;
        this.m13 = p171_1.y;
        this.m20 = p171_0.m20 * p171_2;
        this.m21 = p171_0.m21 * p171_2;
        this.m22 = p171_0.m22 * p171_2;
        this.m23 = p171_1.z;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void set(Matrix3d p171_0) {
        this.m00 = p171_0.m00;
        this.m01 = p171_0.m01;
        this.m02 = p171_0.m02;
        this.m03 = 0.0;
        this.m10 = p171_0.m10;
        this.m11 = p171_0.m11;
        this.m12 = p171_0.m12;
        this.m13 = 0.0;
        this.m20 = p171_0.m20;
        this.m21 = p171_0.m21;
        this.m22 = p171_0.m22;
        this.m23 = 0.0;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final void setM10(double d0) {
        this.m10 = d0;
    }

    public final double getM01() {
        return this.m01;
    }

    public final void setRotation(Matrix3f p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        this.m00 = p171_0.m00 * adouble1[0];
        this.m01 = p171_0.m01 * adouble1[1];
        this.m02 = p171_0.m02 * adouble1[2];
        this.m10 = p171_0.m10 * adouble1[0];
        this.m11 = p171_0.m11 * adouble1[1];
        this.m12 = p171_0.m12 * adouble1[2];
        this.m20 = p171_0.m20 * adouble1[0];
        this.m21 = p171_0.m21 * adouble1[1];
        this.m22 = p171_0.m22 * adouble1[2];
    }

    public final void add(
            double p171_0, Matrix4d p171_1) {
        this.m00 = p171_1.m00 + p171_0;
        this.m01 = p171_1.m01 + p171_0;
        this.m02 = p171_1.m02 + p171_0;
        this.m03 = p171_1.m03 + p171_0;
        this.m10 = p171_1.m10 + p171_0;
        this.m11 = p171_1.m11 + p171_0;
        this.m12 = p171_1.m12 + p171_0;
        this.m13 = p171_1.m13 + p171_0;
        this.m20 = p171_1.m20 + p171_0;
        this.m21 = p171_1.m21 + p171_0;
        this.m22 = p171_1.m22 + p171_0;
        this.m23 = p171_1.m23 + p171_0;
        this.m30 = p171_1.m30 + p171_0;
        this.m31 = p171_1.m31 + p171_0;
        this.m32 = p171_1.m32 + p171_0;
        this.m33 = p171_1.m33 + p171_0;
    }

    // region String decoding support

    // endregion

    public final double getM20() {
        return this.m20;
    }

    public final void setM21(double d0) {
        this.m21 = d0;
    }

    public Matrix4d(
            Matrix3f p171_0, Vector3d p171_1, double p171_2) {
        this.m00 = p171_0.m00 * p171_2;
        this.m01 = p171_0.m01 * p171_2;
        this.m02 = p171_0.m02 * p171_2;
        this.m03 = p171_1.x;
        this.m10 = p171_0.m10 * p171_2;
        this.m11 = p171_0.m11 * p171_2;
        this.m12 = p171_0.m12 * p171_2;
        this.m13 = p171_1.y;
        this.m20 = p171_0.m20 * p171_2;
        this.m21 = p171_0.m21 * p171_2;
        this.m22 = p171_0.m22 * p171_2;
        this.m23 = p171_1.z;
        this.m30 = 0.0;
        this.m31 = 0.0;
        this.m32 = 0.0;
        this.m33 = 1.0;
    }

    public final double getM30() {
        return this.m30;
    }

    public final void method_b9e3e1417d02456ef008(
            int i, double d0, double d1, double d2, double d3) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = d0;
                this.m01 = d1;
                this.m02 = d2;
                this.m03 = d3;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m10 = d0;
                this.m11 = d1;
                this.m12 = d2;
                this.m13 = d3;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m20 = d0;
                this.m21 = d1;
                this.m22 = d2;
                this.m23 = d3;
                if (astring == null) {
                    break;
                }
            case 3:
                this.m30 = d0;
                this.m31 = d1;
                this.m32 = d2;
                this.m33 = d3;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix4d4"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
        }
    }

    public final void transform(
            Tuple4d p171_0, Tuple4d p171_1) {
        double d0 =
                this.m00 * p171_0.x
                        + this.m01 * p171_0.y
                        + this.m02 * p171_0.z
                        + this.m03 * p171_0.w;
        double d1 =
                this.m10 * p171_0.x
                        + this.m11 * p171_0.y
                        + this.m12 * p171_0.z
                        + this.m13 * p171_0.w;
        double d2 =
                this.m20 * p171_0.x
                        + this.m21 * p171_0.y
                        + this.m22 * p171_0.z
                        + this.m23 * p171_0.w;
        p171_1.w =
                this.m30 * p171_0.x
                        + this.m31 * p171_0.y
                        + this.m32 * p171_0.z
                        + this.m33 * p171_0.w;
        p171_1.x = d0;
        p171_1.y = d1;
        p171_1.z = d2;
    }

    public final void get(Matrix3d p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        p171_0.m00 = adouble[0];
        p171_0.m01 = adouble[1];
        p171_0.m02 = adouble[2];
        p171_0.m10 = adouble[3];
        p171_0.m11 = adouble[4];
        p171_0.m12 = adouble[5];
        p171_0.m20 = adouble[6];
        p171_0.m21 = adouble[7];
        p171_0.m22 = adouble[8];
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Matrix4d(Matrix4d matrix4d) {
        super();
        this.m00 = matrix4d.m00;
        this.m01 = matrix4d.m01;
        this.m02 = matrix4d.m02;
        this.m03 = matrix4d.m03;
        this.m10 = matrix4d.m10;
        this.m11 = matrix4d.m11;
        this.m12 = matrix4d.m12;
        this.m13 = matrix4d.m13;
        this.m20 = matrix4d.m20;
        this.m21 = matrix4d.m21;
        this.m22 = matrix4d.m22;
        this.m23 = matrix4d.m23;
        this.m30 = matrix4d.m30;
        this.m31 = matrix4d.m31;
        this.m32 = matrix4d.m32;
        this.m33 = matrix4d.m33;
    }
}
