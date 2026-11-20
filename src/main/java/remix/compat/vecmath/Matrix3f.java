package remix.compat.vecmath;

import java.io.Serializable;

public class Matrix3f implements Serializable, Cloneable {
    public float m01;

    public float m00;
    public float m12;
    private static final double EPS = 1.0E-8;
    public float m22;
    public float m20;
    public float m21;

    static final long serialVersionUID = 329697160112089834L;
    private static String[] packedTextFragments;
    public float m11;
    public float m10;
    public float m02;

    public final void transform(
            Tuple3f p171_0, Tuple3f p171_1) {
        float f = this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z;
        float f1 = this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z;
        p171_1.z = this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z;
        p171_1.x = f;
        p171_1.y = f1;
    }

    public final void transpose() {
        float f = this.m10;
        this.m10 = this.m01;
        this.m01 = f;
        f = this.m20;
        this.m20 = this.m02;
        this.m02 = f;
        f = this.m21;
        this.m21 = this.m12;
        this.m12 = f;
    }

    public final void rotZ(float p171_0) {
        float f1 = (float) java.lang.Math.sin(p171_0);
        float f2 = (float) java.lang.Math.cos(p171_0);
        this.m00 = f2;
        this.m01 = -f1;
        this.m02 = 0.0F;
        this.m10 = f1;
        this.m11 = f2;
        this.m12 = 0.0F;
        this.m20 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = 1.0F;
    }

    public Matrix3f() {
        this.m00 = 0.0F;
        this.m01 = 0.0F;
        this.m02 = 0.0F;
        this.m10 = 0.0F;
        this.m11 = 0.0F;
        this.m12 = 0.0F;
        this.m20 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = 0.0F;
    }

    public final void setZero() {
        this.m00 = 0.0F;
        this.m01 = 0.0F;
        this.m02 = 0.0F;
        this.m10 = 0.0F;
        this.m11 = 0.0F;
        this.m12 = 0.0F;
        this.m20 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = 0.0F;
    }

    public final void setM11(float f) {
        this.m11 = f;
    }

    public boolean epsilonEquals(Matrix3f p171_0, float p171_1) {
        boolean flag = true;
        if (java.lang.Math.abs(this.m00 - p171_0.m00) > p171_1) {
            flag = false;
        }

        if (java.lang.Math.abs(this.m01 - p171_0.m01) > p171_1) {
            flag = false;
        }

        if (java.lang.Math.abs(this.m02 - p171_0.m02) > p171_1) {
            flag = false;
        }

        if (java.lang.Math.abs(this.m10 - p171_0.m10) > p171_1) {
            flag = false;
        }

        if (java.lang.Math.abs(this.m11 - p171_0.m11) > p171_1) {
            flag = false;
        }

        if (java.lang.Math.abs(this.m12 - p171_0.m12) > p171_1) {
            flag = false;
        }

        if (java.lang.Math.abs(this.m20 - p171_0.m20) > p171_1) {
            flag = false;
        }

        if (java.lang.Math.abs(this.m21 - p171_0.m21) > p171_1) {
            flag = false;
        }

        if (java.lang.Math.abs(this.m22 - p171_0.m22) > p171_1) {
            flag = false;
        }

        return flag;
    }

    public final void method_02384ba0b4822e59d6f8(int i, float[] afloat) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = afloat[0];
                this.m10 = afloat[1];
                this.m20 = afloat[2];
                if (astring == null) {
                    break;
                }
            case 1:
                this.m01 = afloat[0];
                this.m11 = afloat[1];
                this.m21 = afloat[2];
                if (astring == null) {
                    break;
                }
            case 2:
                this.m02 = afloat[0];
                this.m12 = afloat[1];
                this.m22 = afloat[2];
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix3f9"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
        }
    }

    public final void method_9c2adb8ca25f6ca60cdc(
            Matrix3f matrix3f,
            Matrix3f matrix3fValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix3f && this != matrix3fValue) {
            this.m00 =
                    matrix3f.m00 * matrix3fValue.m00
                            + matrix3f.m10 * matrix3fValue.m01
                            + matrix3f.m20 * matrix3fValue.m02;
            this.m01 =
                    matrix3f.m00 * matrix3fValue.m10
                            + matrix3f.m10 * matrix3fValue.m11
                            + matrix3f.m20 * matrix3fValue.m12;
            this.m02 =
                    matrix3f.m00 * matrix3fValue.m20
                            + matrix3f.m10 * matrix3fValue.m21
                            + matrix3f.m20 * matrix3fValue.m22;
            this.m10 =
                    matrix3f.m01 * matrix3fValue.m00
                            + matrix3f.m11 * matrix3fValue.m01
                            + matrix3f.m21 * matrix3fValue.m02;
            this.m11 =
                    matrix3f.m01 * matrix3fValue.m10
                            + matrix3f.m11 * matrix3fValue.m11
                            + matrix3f.m21 * matrix3fValue.m12;
            this.m12 =
                    matrix3f.m01 * matrix3fValue.m20
                            + matrix3f.m11 * matrix3fValue.m21
                            + matrix3f.m21 * matrix3fValue.m22;
            this.m20 =
                    matrix3f.m02 * matrix3fValue.m00
                            + matrix3f.m12 * matrix3fValue.m01
                            + matrix3f.m22 * matrix3fValue.m02;
            this.m21 =
                    matrix3f.m02 * matrix3fValue.m10
                            + matrix3f.m12 * matrix3fValue.m11
                            + matrix3f.m22 * matrix3fValue.m12;
            this.m22 =
                    matrix3f.m02 * matrix3fValue.m20
                            + matrix3f.m12 * matrix3fValue.m21
                            + matrix3f.m22 * matrix3fValue.m22;
            if (astring == null) {
                return;
            }
        }

        float f =
                matrix3f.m00 * matrix3fValue.m00
                        + matrix3f.m10 * matrix3fValue.m01
                        + matrix3f.m20 * matrix3fValue.m02;
        float f1 =
                matrix3f.m00 * matrix3fValue.m10
                        + matrix3f.m10 * matrix3fValue.m11
                        + matrix3f.m20 * matrix3fValue.m12;
        float f2 =
                matrix3f.m00 * matrix3fValue.m20
                        + matrix3f.m10 * matrix3fValue.m21
                        + matrix3f.m20 * matrix3fValue.m22;
        float f3 =
                matrix3f.m01 * matrix3fValue.m00
                        + matrix3f.m11 * matrix3fValue.m01
                        + matrix3f.m21 * matrix3fValue.m02;
        float f4 =
                matrix3f.m01 * matrix3fValue.m10
                        + matrix3f.m11 * matrix3fValue.m11
                        + matrix3f.m21 * matrix3fValue.m12;
        float f5 =
                matrix3f.m01 * matrix3fValue.m20
                        + matrix3f.m11 * matrix3fValue.m21
                        + matrix3f.m21 * matrix3fValue.m22;
        float f6 =
                matrix3f.m02 * matrix3fValue.m00
                        + matrix3f.m12 * matrix3fValue.m01
                        + matrix3f.m22 * matrix3fValue.m02;
        float f7 =
                matrix3f.m02 * matrix3fValue.m10
                        + matrix3f.m12 * matrix3fValue.m11
                        + matrix3f.m22 * matrix3fValue.m12;
        float f8 =
                matrix3f.m02 * matrix3fValue.m20
                        + matrix3f.m12 * matrix3fValue.m21
                        + matrix3f.m22 * matrix3fValue.m22;
        this.m00 = f;
        this.m01 = f1;
        this.m02 = f2;
        this.m10 = f3;
        this.m11 = f4;
        this.m12 = f5;
        this.m20 = f6;
        this.m21 = f7;
        this.m22 = f8;
    }

    public final float getM12() {
        return this.m12;
    }

    public final float getScale() {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        return (float) remix.compat.vecmath.Matrix3d.max3(adouble1);
    }

    public final void normalize() {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        this.m00 = (float) adouble[0];
        this.m01 = (float) adouble[1];
        this.m02 = (float) adouble[2];
        this.m10 = (float) adouble[3];
        this.m11 = (float) adouble[4];
        this.m12 = (float) adouble[5];
        this.m20 = (float) adouble[6];
        this.m21 = (float) adouble[7];
        this.m22 = (float) adouble[8];
    }

    public final void mul(
            Matrix3f matrix3f,
            Matrix3f matrix3fValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix3f && this != matrix3fValue) {
            this.m00 =
                    matrix3f.m00 * matrix3fValue.m00
                            + matrix3f.m01 * matrix3fValue.m10
                            + matrix3f.m02 * matrix3fValue.m20;
            this.m01 =
                    matrix3f.m00 * matrix3fValue.m01
                            + matrix3f.m01 * matrix3fValue.m11
                            + matrix3f.m02 * matrix3fValue.m21;
            this.m02 =
                    matrix3f.m00 * matrix3fValue.m02
                            + matrix3f.m01 * matrix3fValue.m12
                            + matrix3f.m02 * matrix3fValue.m22;
            this.m10 =
                    matrix3f.m10 * matrix3fValue.m00
                            + matrix3f.m11 * matrix3fValue.m10
                            + matrix3f.m12 * matrix3fValue.m20;
            this.m11 =
                    matrix3f.m10 * matrix3fValue.m01
                            + matrix3f.m11 * matrix3fValue.m11
                            + matrix3f.m12 * matrix3fValue.m21;
            this.m12 =
                    matrix3f.m10 * matrix3fValue.m02
                            + matrix3f.m11 * matrix3fValue.m12
                            + matrix3f.m12 * matrix3fValue.m22;
            this.m20 =
                    matrix3f.m20 * matrix3fValue.m00
                            + matrix3f.m21 * matrix3fValue.m10
                            + matrix3f.m22 * matrix3fValue.m20;
            this.m21 =
                    matrix3f.m20 * matrix3fValue.m01
                            + matrix3f.m21 * matrix3fValue.m11
                            + matrix3f.m22 * matrix3fValue.m21;
            this.m22 =
                    matrix3f.m20 * matrix3fValue.m02
                            + matrix3f.m21 * matrix3fValue.m12
                            + matrix3f.m22 * matrix3fValue.m22;
            if (astring == null) {
                return;
            }
        }

        float f =
                matrix3f.m00 * matrix3fValue.m00
                        + matrix3f.m01 * matrix3fValue.m10
                        + matrix3f.m02 * matrix3fValue.m20;
        float f1 =
                matrix3f.m00 * matrix3fValue.m01
                        + matrix3f.m01 * matrix3fValue.m11
                        + matrix3f.m02 * matrix3fValue.m21;
        float f2 =
                matrix3f.m00 * matrix3fValue.m02
                        + matrix3f.m01 * matrix3fValue.m12
                        + matrix3f.m02 * matrix3fValue.m22;
        float f3 =
                matrix3f.m10 * matrix3fValue.m00
                        + matrix3f.m11 * matrix3fValue.m10
                        + matrix3f.m12 * matrix3fValue.m20;
        float f4 =
                matrix3f.m10 * matrix3fValue.m01
                        + matrix3f.m11 * matrix3fValue.m11
                        + matrix3f.m12 * matrix3fValue.m21;
        float f5 =
                matrix3f.m10 * matrix3fValue.m02
                        + matrix3f.m11 * matrix3fValue.m12
                        + matrix3f.m12 * matrix3fValue.m22;
        float f6 =
                matrix3f.m20 * matrix3fValue.m00
                        + matrix3f.m21 * matrix3fValue.m10
                        + matrix3f.m22 * matrix3fValue.m20;
        float f7 =
                matrix3f.m20 * matrix3fValue.m01
                        + matrix3f.m21 * matrix3fValue.m11
                        + matrix3f.m22 * matrix3fValue.m21;
        float f8 =
                matrix3f.m20 * matrix3fValue.m02
                        + matrix3f.m21 * matrix3fValue.m12
                        + matrix3f.m22 * matrix3fValue.m22;
        this.m00 = f;
        this.m01 = f1;
        this.m02 = f2;
        this.m10 = f3;
        this.m11 = f4;
        this.m12 = f5;
        this.m20 = f6;
        this.m21 = f7;
        this.m22 = f8;
    }

    public final void negate(Matrix3f p171_0) {
        this.m00 = -p171_0.m00;
        this.m01 = -p171_0.m01;
        this.m02 = -p171_0.m02;
        this.m10 = -p171_0.m10;
        this.m11 = -p171_0.m11;
        this.m12 = -p171_0.m12;
        this.m20 = -p171_0.m20;
        this.m21 = -p171_0.m21;
        this.m22 = -p171_0.m22;
    }

    public final void set(
            AxisAngle4d axisAngle4d) {
        String[] astring1 = Tuple3f.hF();
        double d0 =
                Math.sqrt(
                        axisAngle4d.x * axisAngle4d.x
                                + axisAngle4d.y * axisAngle4d.y
                                + axisAngle4d.z * axisAngle4d.z);
        String[] astring = astring1;
        if (d0 < 1.0E-8) {
            this.m00 = 1.0F;
            this.m01 = 0.0F;
            this.m02 = 0.0F;
            this.m10 = 0.0F;
            this.m11 = 1.0F;
            this.m12 = 0.0F;
            this.m20 = 0.0F;
            this.m21 = 0.0F;
            this.m22 = 1.0F;
            if (astring == null) {
                return;
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
        this.m00 = (float) (d6 * d1 * d1 + d5);
        this.m01 = (float) (d6 * d8 - d4 * d3);
        this.m02 = (float) (d6 * d7 + d4 * d2);
        this.m10 = (float) (d6 * d8 + d4 * d3);
        this.m11 = (float) (d6 * d2 * d2 + d5);
        this.m12 = (float) (d6 * d9 - d4 * d1);
        this.m20 = (float) (d6 * d7 - d4 * d2);
        this.m21 = (float) (d6 * d9 + d4 * d1);
        this.m22 = (float) (d6 * d3 * d3 + d5);
    }

    public final void set(Quat4d p171_0) {
        this.m00 = (float) (1.0 - 2.0 * p171_0.y * p171_0.y - 2.0 * p171_0.z * p171_0.z);
        this.m10 = (float) (2.0 * (p171_0.x * p171_0.y + p171_0.w * p171_0.z));
        this.m20 = (float) (2.0 * (p171_0.x * p171_0.z - p171_0.w * p171_0.y));
        this.m01 = (float) (2.0 * (p171_0.x * p171_0.y - p171_0.w * p171_0.z));
        this.m11 = (float) (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.z * p171_0.z);
        this.m21 = (float) (2.0 * (p171_0.y * p171_0.z + p171_0.w * p171_0.x));
        this.m02 = (float) (2.0 * (p171_0.x * p171_0.z + p171_0.w * p171_0.y));
        this.m12 = (float) (2.0 * (p171_0.y * p171_0.z - p171_0.w * p171_0.x));
        this.m22 = (float) (1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.y * p171_0.y);
    }

    public final void method_333602bd2f37f3ab96f1(
            int i, Vector3f vector3f) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = vector3f.x;
                this.m01 = vector3f.y;
                this.m02 = vector3f.z;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m10 = vector3f.x;
                this.m11 = vector3f.y;
                this.m12 = vector3f.z;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m20 = vector3f.x;
                this.m21 = vector3f.y;
                this.m22 = vector3f.z;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix3f6"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
        }
    }

    static boolean method_46f13198e89824201e6a(double[] adouble, int[] aint) {
        double[] adouble1 = new double[3];
        String[] astring1 = Tuple3f.hF();
        int k = 0;
        String[] astring = astring1;
        int l = 0;
        int i = 3;

        while (i-- != 0) {
            double d0 = 0.0;
            int j = 3;

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

        while (i < 3) {
            k = 0;

            label96:
            while (k < i) {
                int i1 = b0 + 3 * k + i;
                double d2 = adouble[i1];
                int k1 = k;
                int l2 = b0 + 3 * k;
                int j1 = b0 + i;

                while (true) {
                    if (k1-- != 0) {
                        d2 -= adouble[l2] * adouble[j1];
                        l2++;
                        j1 += 3;
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

            while (k < 3) {
                int j2 = b0 + 3 * k + i;
                double d5 = adouble[j2];
                int l1 = i;
                int i3 = b0 + 3 * k;
                int k3 = b0 + i;

                while (l1-- != 0) {
                    d5 -= adouble[i3] * adouble[k3];
                    i3++;
                    k3 += 3;
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
                throw new RuntimeException(VecMathI18N.getString("Matrix3f13"));
            }

            if (i != l) {
                int i2 = 3;
                int j3 = b0 + 3 * l;
                int l3 = b0 + 3 * i;

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
            if (adouble[b0 + 3 * i + i] == 0.0) {
                return false;
            }

            if (i != 2) {
                double d7 = 1.0 / adouble[b0 + 3 * i + i];
                int k2 = b0 + 3 * (i + 1) + i;
                k = 2 - i;

                while (k-- != 0) {
                    adouble[k2] *= d7;
                    k2 += 3;
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

    public final void sub(
            Matrix3f p171_0, Matrix3f p171_1) {
        this.m00 = p171_0.m00 - p171_1.m00;
        this.m01 = p171_0.m01 - p171_1.m01;
        this.m02 = p171_0.m02 - p171_1.m02;
        this.m10 = p171_0.m10 - p171_1.m10;
        this.m11 = p171_0.m11 - p171_1.m11;
        this.m12 = p171_0.m12 - p171_1.m12;
        this.m20 = p171_0.m20 - p171_1.m20;
        this.m21 = p171_0.m21 - p171_1.m21;
        this.m22 = p171_0.m22 - p171_1.m22;
    }

    public final void add(
            Matrix3f p171_0, Matrix3f p171_1) {
        this.m00 = p171_0.m00 + p171_1.m00;
        this.m01 = p171_0.m01 + p171_1.m01;
        this.m02 = p171_0.m02 + p171_1.m02;
        this.m10 = p171_0.m10 + p171_1.m10;
        this.m11 = p171_0.m11 + p171_1.m11;
        this.m12 = p171_0.m12 + p171_1.m12;
        this.m20 = p171_0.m20 + p171_1.m20;
        this.m21 = p171_0.m21 + p171_1.m21;
        this.m22 = p171_0.m22 + p171_1.m22;
    }

    public final void set(Quat4f p171_0) {
        this.m00 = 1.0F - 2.0F * p171_0.y * p171_0.y - 2.0F * p171_0.z * p171_0.z;
        this.m10 = 2.0F * (p171_0.x * p171_0.y + p171_0.w * p171_0.z);
        this.m20 = 2.0F * (p171_0.x * p171_0.z - p171_0.w * p171_0.y);
        this.m01 = 2.0F * (p171_0.x * p171_0.y - p171_0.w * p171_0.z);
        this.m11 = 1.0F - 2.0F * p171_0.x * p171_0.x - 2.0F * p171_0.z * p171_0.z;
        this.m21 = 2.0F * (p171_0.y * p171_0.z + p171_0.w * p171_0.x);
        this.m02 = 2.0F * (p171_0.x * p171_0.z + p171_0.w * p171_0.y);
        this.m12 = 2.0F * (p171_0.y * p171_0.z - p171_0.w * p171_0.x);
        this.m22 = 1.0F - 2.0F * p171_0.x * p171_0.x - 2.0F * p171_0.y * p171_0.y;
    }

    public final void transform(
            Tuple3f tuple3f) {
        float f =
                this.m00 * tuple3f.x
                        + this.m01 * tuple3f.y
                        + this.m02 * tuple3f.z;
        float f1 =
                this.m10 * tuple3f.x
                        + this.m11 * tuple3f.y
                        + this.m12 * tuple3f.z;
        float f2 =
                this.m20 * tuple3f.x
                        + this.m21 * tuple3f.y
                        + this.m22 * tuple3f.z;
        tuple3f.set(f, f1, f2);
    }

    @Override
    public Object clone() {

        try {
            return (remix.compat.vecmath.Matrix3f) super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }
    }

    public final void set(AxisAngle4f p171_0) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        float f =
                (float)
                        java.lang.Math.sqrt(
                                p171_0.x * p171_0.x
                                        + p171_0.y * p171_0.y
                                        + p171_0.z * p171_0.z);
        java.lang.String[] astring = astring1;

        try {
            if (f < 1.0E-8) {
                this.m00 = 1.0F;
                this.m01 = 0.0F;
                this.m02 = 0.0F;
                this.m10 = 0.0F;
                this.m11 = 1.0F;
                this.m12 = 0.0F;
                this.m20 = 0.0F;
                this.m21 = 0.0F;
                this.m22 = 1.0F;
                if (astring == null) {
                    return;
                }
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            throw remix.compat.vecmath.Matrix3f.wrapException(
                    arrayindexoutofboundsexception);
        }

        f = 1.0F / f;
        float f1 = p171_0.x * f;
        float f2 = p171_0.y * f;
        float f3 = p171_0.z * f;
        float f4 = (float) java.lang.Math.sin(p171_0.angle);
        float f5 = (float) java.lang.Math.cos(p171_0.angle);
        float f6 = 1.0F - f5;
        float f7 = f1 * f3;
        float f8 = f1 * f2;
        float f9 = f2 * f3;
        this.m00 = f6 * f1 * f1 + f5;
        this.m01 = f6 * f8 - f4 * f3;
        this.m02 = f6 * f7 + f4 * f2;
        this.m10 = f6 * f8 + f4 * f3;
        this.m11 = f6 * f2 * f2 + f5;
        this.m12 = f6 * f9 - f4 * f1;
        this.m20 = f6 * f7 - f4 * f2;
        this.m21 = f6 * f9 + f4 * f1;
        this.m22 = f6 * f3 * f3 + f5;
    }

    public final void mul(Matrix3f p171_0) {
        float f = this.m00 * p171_0.m00 + this.m01 * p171_0.m10 + this.m02 * p171_0.m20;
        float f1 = this.m00 * p171_0.m01 + this.m01 * p171_0.m11 + this.m02 * p171_0.m21;
        float f2 = this.m00 * p171_0.m02 + this.m01 * p171_0.m12 + this.m02 * p171_0.m22;
        float f3 = this.m10 * p171_0.m00 + this.m11 * p171_0.m10 + this.m12 * p171_0.m20;
        float f4 = this.m10 * p171_0.m01 + this.m11 * p171_0.m11 + this.m12 * p171_0.m21;
        float f5 = this.m10 * p171_0.m02 + this.m11 * p171_0.m12 + this.m12 * p171_0.m22;
        float f6 = this.m20 * p171_0.m00 + this.m21 * p171_0.m10 + this.m22 * p171_0.m20;
        float f7 = this.m20 * p171_0.m01 + this.m21 * p171_0.m11 + this.m22 * p171_0.m21;
        float f8 = this.m20 * p171_0.m02 + this.m21 * p171_0.m12 + this.m22 * p171_0.m22;
        this.m00 = f;
        this.m01 = f1;
        this.m02 = f2;
        this.m10 = f3;
        this.m11 = f4;
        this.m12 = f5;
        this.m20 = f6;
        this.m21 = f7;
        this.m22 = f8;
    }

    public final void rotY(float p171_0) {
        float f1 = (float) java.lang.Math.sin(p171_0);
        float f2 = (float) java.lang.Math.cos(p171_0);
        this.m00 = f2;
        this.m01 = 0.0F;
        this.m02 = f1;
        this.m10 = 0.0F;
        this.m11 = 1.0F;
        this.m12 = 0.0F;
        this.m20 = -f1;
        this.m21 = 0.0F;
        this.m22 = f2;
    }

    public final void normalizeCP(Matrix3f p171_0) {
        float f =
                1.0F
                        / (float)
                                java.lang.Math.sqrt(
                                        p171_0.m00 * p171_0.m00
                                                + p171_0.m10 * p171_0.m10
                                                + p171_0.m20 * p171_0.m20);
        this.m00 = p171_0.m00 * f;
        this.m10 = p171_0.m10 * f;
        this.m20 = p171_0.m20 * f;
        f =
                1.0F
                        / (float)
                                java.lang.Math.sqrt(
                                        p171_0.m01 * p171_0.m01
                                                + p171_0.m11 * p171_0.m11
                                                + p171_0.m21 * p171_0.m21);
        this.m01 = p171_0.m01 * f;
        this.m11 = p171_0.m11 * f;
        this.m21 = p171_0.m21 * f;
        this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
        this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
        this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
    }

    public final void method_fab7bfa34345b20e6f51(int i, float[] afloat) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            afloat[0] = this.m00;
            afloat[1] = this.m10;
            afloat[2] = this.m20;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            afloat[0] = this.m01;
            afloat[1] = this.m11;
            afloat[2] = this.m21;
            if (astring == null) {
                return;
            }
        }

        if (i != 2) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
        }

        afloat[0] = this.m02;
        afloat[1] = this.m12;
        afloat[2] = this.m22;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
        }
    }

    private static RuntimeException wrapException(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    public final void add(float p171_0) {
        this.m00 += p171_0;
        this.m01 += p171_0;
        this.m02 += p171_0;
        this.m10 += p171_0;
        this.m11 += p171_0;
        this.m12 += p171_0;
        this.m20 += p171_0;
        this.m21 += p171_0;
        this.m22 += p171_0;
    }

    public final void method_63d78e3d18a2e2936dca(
            Matrix3f matrix3f,
            Matrix3f matrix3fValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix3f && this != matrix3fValue) {
            this.m00 =
                    matrix3f.m00 * matrix3fValue.m00
                            + matrix3f.m10 * matrix3fValue.m10
                            + matrix3f.m20 * matrix3fValue.m20;
            this.m01 =
                    matrix3f.m00 * matrix3fValue.m01
                            + matrix3f.m10 * matrix3fValue.m11
                            + matrix3f.m20 * matrix3fValue.m21;
            this.m02 =
                    matrix3f.m00 * matrix3fValue.m02
                            + matrix3f.m10 * matrix3fValue.m12
                            + matrix3f.m20 * matrix3fValue.m22;
            this.m10 =
                    matrix3f.m01 * matrix3fValue.m00
                            + matrix3f.m11 * matrix3fValue.m10
                            + matrix3f.m21 * matrix3fValue.m20;
            this.m11 =
                    matrix3f.m01 * matrix3fValue.m01
                            + matrix3f.m11 * matrix3fValue.m11
                            + matrix3f.m21 * matrix3fValue.m21;
            this.m12 =
                    matrix3f.m01 * matrix3fValue.m02
                            + matrix3f.m11 * matrix3fValue.m12
                            + matrix3f.m21 * matrix3fValue.m22;
            this.m20 =
                    matrix3f.m02 * matrix3fValue.m00
                            + matrix3f.m12 * matrix3fValue.m10
                            + matrix3f.m22 * matrix3fValue.m20;
            this.m21 =
                    matrix3f.m02 * matrix3fValue.m01
                            + matrix3f.m12 * matrix3fValue.m11
                            + matrix3f.m22 * matrix3fValue.m21;
            this.m22 =
                    matrix3f.m02 * matrix3fValue.m02
                            + matrix3f.m12 * matrix3fValue.m12
                            + matrix3f.m22 * matrix3fValue.m22;
            if (astring == null) {
                return;
            }
        }

        float f =
                matrix3f.m00 * matrix3fValue.m00
                        + matrix3f.m10 * matrix3fValue.m10
                        + matrix3f.m20 * matrix3fValue.m20;
        float f1 =
                matrix3f.m00 * matrix3fValue.m01
                        + matrix3f.m10 * matrix3fValue.m11
                        + matrix3f.m20 * matrix3fValue.m21;
        float f2 =
                matrix3f.m00 * matrix3fValue.m02
                        + matrix3f.m10 * matrix3fValue.m12
                        + matrix3f.m20 * matrix3fValue.m22;
        float f3 =
                matrix3f.m01 * matrix3fValue.m00
                        + matrix3f.m11 * matrix3fValue.m10
                        + matrix3f.m21 * matrix3fValue.m20;
        float f4 =
                matrix3f.m01 * matrix3fValue.m01
                        + matrix3f.m11 * matrix3fValue.m11
                        + matrix3f.m21 * matrix3fValue.m21;
        float f5 =
                matrix3f.m01 * matrix3fValue.m02
                        + matrix3f.m11 * matrix3fValue.m12
                        + matrix3f.m21 * matrix3fValue.m22;
        float f6 =
                matrix3f.m02 * matrix3fValue.m00
                        + matrix3f.m12 * matrix3fValue.m10
                        + matrix3f.m22 * matrix3fValue.m20;
        float f7 =
                matrix3f.m02 * matrix3fValue.m01
                        + matrix3f.m12 * matrix3fValue.m11
                        + matrix3f.m22 * matrix3fValue.m21;
        float f8 =
                matrix3f.m02 * matrix3fValue.m02
                        + matrix3f.m12 * matrix3fValue.m12
                        + matrix3f.m22 * matrix3fValue.m22;
        this.m00 = f;
        this.m01 = f1;
        this.m02 = f2;
        this.m10 = f3;
        this.m11 = f4;
        this.m12 = f5;
        this.m20 = f6;
        this.m21 = f7;
        this.m22 = f8;
    }

    public final void invert(
            Matrix3f matrix3f) {
        this.invertGeneral(matrix3f);
    }

    static void method_05badd4d3eb80f51c381(double[] p171_0, int[] p171_1, double[] p171_2) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        byte b0 = 0;
        java.lang.String[] astring = astring1;
        int i1 = 0;

        while (i1 < 3) {
            int j1 = i1;
            int j = -1;
            int i = 0;

            while (true) {
                if (i < 3) {
                    double d0;
                    label32:
                    {
                        int k = p171_1[b0 + i];
                        d0 = p171_2[j1 + 3 * k];
                        p171_2[j1 + 3 * k] = p171_2[j1 + 3 * i];
                        if (j >= 0) {
                            int k1 = i * 3;
                            int l = j;

                            do {
                                if (l > i - 1) {
                                    break label32;
                                }

                                d0 -= p171_0[k1 + l] * p171_2[j1 + 3 * l];
                                l++;
                            } while (astring == null);
                        }

                        if (d0 != 0.0) {
                            j = i;
                        }
                    }

                    p171_2[j1 + 3 * i] = d0;
                    i++;
                    if (astring == null) {
                        continue;
                    }
                }

                int l1 = 6;
                p171_2[j1 + 6] = p171_2[j1 + 6] / p171_0[l1 + 2];
                l1 -= 3;
                p171_2[j1 + 3] =
                        (p171_2[j1 + 3] - p171_0[l1 + 2] * p171_2[j1 + 6]) / p171_0[l1 + 1];
                l1 -= 3;
                p171_2[j1 + 0] =
                        (p171_2[j1 + 0]
                                        - p171_0[l1 + 1] * p171_2[j1 + 3]
                                        - p171_0[l1 + 2] * p171_2[j1 + 6])
                                / p171_0[l1 + 0];
                i1++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public final void setIdentity() {
        this.m00 = 1.0F;
        this.m01 = 0.0F;
        this.m02 = 0.0F;
        this.m10 = 0.0F;
        this.m11 = 1.0F;
        this.m12 = 0.0F;
        this.m20 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = 1.0F;
    }

    public boolean equals(Matrix3f p171_0) {
        try {
            return (this.m00 == p171_0.m00
                    && this.m01 == p171_0.m01
                    && this.m02 == p171_0.m02
                    && this.m10 == p171_0.m10
                    && this.m11 == p171_0.m11
                    && this.m12 == p171_0.m12
                    && this.m20 == p171_0.m20
                    && this.m21 == p171_0.m21
                    && this.m22 == p171_0.m22);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final void method_2a4efdabbc8aad8f8624(
            int i, Vector3f vector3f) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            vector3f.x = this.m00;
            vector3f.y = this.m10;
            vector3f.z = this.m20;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            vector3f.x = this.m01;
            vector3f.y = this.m11;
            vector3f.z = this.m21;
            if (astring == null) {
                return;
            }
        }

        if (i != 2) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
        }

        vector3f.x = this.m02;
        vector3f.y = this.m12;
        vector3f.z = this.m22;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
        }
    }

    public final void method_8fe757c706238b745c5f(
            Matrix3f matrix3f,
            Matrix3f matrix3fValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix3f && this != matrix3fValue) {
            this.m00 =
                    matrix3f.m00 * matrix3fValue.m00
                            + matrix3f.m01 * matrix3fValue.m01
                            + matrix3f.m02 * matrix3fValue.m02;
            this.m01 =
                    matrix3f.m00 * matrix3fValue.m10
                            + matrix3f.m01 * matrix3fValue.m11
                            + matrix3f.m02 * matrix3fValue.m12;
            this.m02 =
                    matrix3f.m00 * matrix3fValue.m20
                            + matrix3f.m01 * matrix3fValue.m21
                            + matrix3f.m02 * matrix3fValue.m22;
            this.m10 =
                    matrix3f.m10 * matrix3fValue.m00
                            + matrix3f.m11 * matrix3fValue.m01
                            + matrix3f.m12 * matrix3fValue.m02;
            this.m11 =
                    matrix3f.m10 * matrix3fValue.m10
                            + matrix3f.m11 * matrix3fValue.m11
                            + matrix3f.m12 * matrix3fValue.m12;
            this.m12 =
                    matrix3f.m10 * matrix3fValue.m20
                            + matrix3f.m11 * matrix3fValue.m21
                            + matrix3f.m12 * matrix3fValue.m22;
            this.m20 =
                    matrix3f.m20 * matrix3fValue.m00
                            + matrix3f.m21 * matrix3fValue.m01
                            + matrix3f.m22 * matrix3fValue.m02;
            this.m21 =
                    matrix3f.m20 * matrix3fValue.m10
                            + matrix3f.m21 * matrix3fValue.m11
                            + matrix3f.m22 * matrix3fValue.m12;
            this.m22 =
                    matrix3f.m20 * matrix3fValue.m20
                            + matrix3f.m21 * matrix3fValue.m21
                            + matrix3f.m22 * matrix3fValue.m22;
            if (astring == null) {
                return;
            }
        }

        float f =
                matrix3f.m00 * matrix3fValue.m00
                        + matrix3f.m01 * matrix3fValue.m01
                        + matrix3f.m02 * matrix3fValue.m02;
        float f1 =
                matrix3f.m00 * matrix3fValue.m10
                        + matrix3f.m01 * matrix3fValue.m11
                        + matrix3f.m02 * matrix3fValue.m12;
        float f2 =
                matrix3f.m00 * matrix3fValue.m20
                        + matrix3f.m01 * matrix3fValue.m21
                        + matrix3f.m02 * matrix3fValue.m22;
        float f3 =
                matrix3f.m10 * matrix3fValue.m00
                        + matrix3f.m11 * matrix3fValue.m01
                        + matrix3f.m12 * matrix3fValue.m02;
        float f4 =
                matrix3f.m10 * matrix3fValue.m10
                        + matrix3f.m11 * matrix3fValue.m11
                        + matrix3f.m12 * matrix3fValue.m12;
        float f5 =
                matrix3f.m10 * matrix3fValue.m20
                        + matrix3f.m11 * matrix3fValue.m21
                        + matrix3f.m12 * matrix3fValue.m22;
        float f6 =
                matrix3f.m20 * matrix3fValue.m00
                        + matrix3f.m21 * matrix3fValue.m01
                        + matrix3f.m22 * matrix3fValue.m02;
        float f7 =
                matrix3f.m20 * matrix3fValue.m10
                        + matrix3f.m21 * matrix3fValue.m11
                        + matrix3f.m22 * matrix3fValue.m12;
        float f8 =
                matrix3f.m20 * matrix3fValue.m20
                        + matrix3f.m21 * matrix3fValue.m21
                        + matrix3f.m22 * matrix3fValue.m22;
        this.m00 = f;
        this.m01 = f1;
        this.m02 = f2;
        this.m10 = f3;
        this.m11 = f4;
        this.m12 = f5;
        this.m20 = f6;
        this.m21 = f7;
        this.m22 = f8;
    }

    public final void mul(float p171_0, Matrix3f p171_1) {
        this.m00 = p171_0 * p171_1.m00;
        this.m01 = p171_0 * p171_1.m01;
        this.m02 = p171_0 * p171_1.m02;
        this.m10 = p171_0 * p171_1.m10;
        this.m11 = p171_0 * p171_1.m11;
        this.m12 = p171_0 * p171_1.m12;
        this.m20 = p171_0 * p171_1.m20;
        this.m21 = p171_0 * p171_1.m21;
        this.m22 = p171_0 * p171_1.m22;
    }

    public final void method_3d3760a27e3c7157c1c7(int i, int j, float f) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                switch (j) {
                    case 0:
                        this.m00 = f;
                        if (astring == null) {
                            return;
                        }
                    case 1:
                        this.m01 = f;
                        if (astring == null) {
                            return;
                        }
                    case 2:
                        this.m02 = f;
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix3f0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix3f0"));
                }
            case 1:
                switch (j) {
                    case 0:
                        this.m10 = f;
                        if (astring == null) {
                            return;
                        }
                    case 1:
                        this.m11 = f;
                        if (astring == null) {
                            return;
                        }
                    case 2:
                        this.m12 = f;
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix3f0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix3f0"));
                }
            case 2:
                switch (j) {
                    case 0:
                        this.m20 = f;
                        if (astring == null) {
                            return;
                        }
                    case 1:
                        this.m21 = f;
                        if (astring == null) {
                            return;
                        }
                    case 2:
                        this.m22 = f;
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix3f0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix3f0"));
                }
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
        }
    }

    public final void set(float[] p171_0) {
        this.m00 = p171_0[0];
        this.m01 = p171_0[1];
        this.m02 = p171_0[2];
        this.m10 = p171_0[3];
        this.m11 = p171_0[4];
        this.m12 = p171_0[5];
        this.m20 = p171_0[6];
        this.m21 = p171_0[7];
        this.m22 = p171_0[8];
    }

    public final float method_c3494e6136dd53fcdf53(int i, int j) {
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
                }
        }

        throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f5"));
    }

    void getScaleRotate(double[] adouble, double[] adouble1) {
        double[] adouble2 =
                new double[] {
                    this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22
                };
        Matrix3d.compute_svd(adouble2, adouble, adouble1);
    }

    public final void method_6f0b65d4c2518f0af816(int i, float[] afloat) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = afloat[0];
                this.m01 = afloat[1];
                this.m02 = afloat[2];
                if (astring == null) {
                    break;
                }
            case 1:
                this.m10 = afloat[0];
                this.m11 = afloat[1];
                this.m12 = afloat[2];
                if (astring == null) {
                    break;
                }
            case 2:
                this.m20 = afloat[0];
                this.m21 = afloat[1];
                this.m22 = afloat[2];
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix3f6"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
        }
    }

    public final void setM22(float f) {
        this.m22 = f;
    }

    public final void negate() {
        this.m00 = -this.m00;
        this.m01 = -this.m01;
        this.m02 = -this.m02;
        this.m10 = -this.m10;
        this.m11 = -this.m11;
        this.m12 = -this.m12;
        this.m20 = -this.m20;
        this.m21 = -this.m21;
        this.m22 = -this.m22;
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Matrix3f matrix3f =
                    (remix.compat.vecmath.Matrix3f) p171_0;
            return (this.m00 == matrix3f.m00
                    && this.m01 == matrix3f.m01
                    && this.m02 == matrix3f.m02
                    && this.m10 == matrix3f.m10
                    && this.m11 == matrix3f.m11
                    && this.m12 == matrix3f.m12
                    && this.m20 == matrix3f.m20
                    && this.m21 == matrix3f.m21
                    && this.m22 == matrix3f.m22);
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final void method_5a86080058274c809f99(
            int i, Vector3f vector3f) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = vector3f.x;
                this.m10 = vector3f.y;
                this.m20 = vector3f.z;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m01 = vector3f.x;
                this.m11 = vector3f.y;
                this.m21 = vector3f.z;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m02 = vector3f.x;
                this.m12 = vector3f.y;
                this.m22 = vector3f.z;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix3f9"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
        }
    }

    public final void setM02(float f) {
        this.m02 = f;
    }

    static {
        remix.compat.vecmath.Matrix3f.packedTextFragments = new String[5];
        remix.compat.vecmath.Matrix3f.packedTextFragments[0] = new String("\n");
        remix.compat.vecmath.Matrix3f.packedTextFragments[1] = new String("\n");
        remix.compat.vecmath.Matrix3f.packedTextFragments[2] = new String("\n");
    }

    public Matrix3f(Matrix3d p171_0) {
        this.m00 = (float) p171_0.m00;
        this.m01 = (float) p171_0.m01;
        this.m02 = (float) p171_0.m02;
        this.m10 = (float) p171_0.m10;
        this.m11 = (float) p171_0.m11;
        this.m12 = (float) p171_0.m12;
        this.m20 = (float) p171_0.m20;
        this.m21 = (float) p171_0.m21;
        this.m22 = (float) p171_0.m22;
    }

    public final void normalize(Matrix3f p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[9];
        double[] adouble2 = new double[3];
        adouble[0] = p171_0.m00;
        adouble[1] = p171_0.m01;
        adouble[2] = p171_0.m02;
        adouble[3] = p171_0.m10;
        adouble[4] = p171_0.m11;
        adouble[5] = p171_0.m12;
        adouble[6] = p171_0.m20;
        adouble[7] = p171_0.m21;
        adouble[8] = p171_0.m22;
        remix.compat.vecmath.Matrix3d.compute_svd(adouble, adouble2, adouble1);
        this.m00 = (float) adouble1[0];
        this.m01 = (float) adouble1[1];
        this.m02 = (float) adouble1[2];
        this.m10 = (float) adouble1[3];
        this.m11 = (float) adouble1[4];
        this.m12 = (float) adouble1[5];
        this.m20 = (float) adouble1[6];
        this.m21 = (float) adouble1[7];
        this.m22 = (float) adouble1[8];
    }

    public final float getM20() {
        return this.m20;
    }

    public final void setScale(float f) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        this.m00 = (float) (adouble[0] * f);
        this.m01 = (float) (adouble[1] * f);
        this.m02 = (float) (adouble[2] * f);
        this.m10 = (float) (adouble[3] * f);
        this.m11 = (float) (adouble[4] * f);
        this.m12 = (float) (adouble[5] * f);
        this.m20 = (float) (adouble[6] * f);
        this.m21 = (float) (adouble[7] * f);
        this.m22 = (float) (adouble[8] * f);
    }

    public final float getM11() {
        return this.m11;
    }

    public final void set(float p171_0) {
        this.m00 = p171_0;
        this.m01 = 0.0F;
        this.m02 = 0.0F;
        this.m10 = 0.0F;
        this.m11 = p171_0;
        this.m12 = 0.0F;
        this.m20 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = p171_0;
    }

    public final void setM20(float f) {
        this.m20 = f;
    }

    public final void setM21(float f) {
        this.m21 = f;
    }

    public final void method_22667b6d1d9b2695d217(int i, float f, float f1, float f2) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = f;
                this.m01 = f1;
                this.m02 = f2;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m10 = f;
                this.m11 = f1;
                this.m12 = f2;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m20 = f;
                this.m21 = f1;
                this.m22 = f2;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix3f6"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
        }
    }

    @Override
    public int hashCode() {

        long i = 1L;
        i = VecMathUtil.hashFloatToIntBits(i, this.m00);
        i = VecMathUtil.hashFloatToIntBits(i, this.m01);
        i = VecMathUtil.hashFloatToIntBits(i, this.m02);
        i = VecMathUtil.hashFloatToIntBits(i, this.m10);
        i = VecMathUtil.hashFloatToIntBits(i, this.m11);
        i = VecMathUtil.hashFloatToIntBits(i, this.m12);
        i = VecMathUtil.hashFloatToIntBits(i, this.m20);
        i = VecMathUtil.hashFloatToIntBits(i, this.m21);
        i = VecMathUtil.hashFloatToIntBits(i, this.m22);
        return VecMathUtil.hashFinish(i);
    }

    public final void method_eb50dd6d28bba149f58e(Matrix3f p171_0) {
        this.m00 = this.m00 - p171_0.m00;
        this.m01 = this.m01 - p171_0.m01;
        this.m02 = this.m02 - p171_0.m02;
        this.m10 = this.m10 - p171_0.m10;
        this.m11 = this.m11 - p171_0.m11;
        this.m12 = this.m12 - p171_0.m12;
        this.m20 = this.m20 - p171_0.m20;
        this.m21 = this.m21 - p171_0.m21;
        this.m22 = this.m22 - p171_0.m22;
    }

    public final float getM00() {
        return this.m00;
    }

    public final void transpose(Matrix3f p171_0) {
        java.lang.String[] astring = remix.compat.vecmath.Tuple3f.hF();
        if (this != p171_0) {
            this.m00 = p171_0.m00;
            this.m01 = p171_0.m10;
            this.m02 = p171_0.m20;
            this.m10 = p171_0.m01;
            this.m11 = p171_0.m11;
            this.m12 = p171_0.m21;
            this.m20 = p171_0.m02;
            this.m21 = p171_0.m12;
            this.m22 = p171_0.m22;
            if (astring == null) {
                return;
            }
        }

        this.transpose();
    }

    public Matrix3f(float[] p171_0) {
        this.m00 = p171_0[0];
        this.m01 = p171_0[1];
        this.m02 = p171_0[2];
        this.m10 = p171_0[3];
        this.m11 = p171_0[4];
        this.m12 = p171_0[5];
        this.m20 = p171_0[6];
        this.m21 = p171_0[7];
        this.m22 = p171_0[8];
    }

    public final void setM12(float f) {
        this.m12 = f;
    }

    public final void mul(float p171_0) {
        this.m00 *= p171_0;
        this.m01 *= p171_0;
        this.m02 *= p171_0;
        this.m10 *= p171_0;
        this.m11 *= p171_0;
        this.m12 *= p171_0;
        this.m20 *= p171_0;
        this.m21 *= p171_0;
        this.m22 *= p171_0;
    }

    public Matrix3f(
            float p171_0,
            float p171_1,
            float p171_2,
            float p171_3,
            float p171_4,
            float p171_5,
            float p171_6,
            float p171_7,
            float p171_8) {
        this.m00 = p171_0;
        this.m01 = p171_1;
        this.m02 = p171_2;
        this.m10 = p171_3;
        this.m11 = p171_4;
        this.m12 = p171_5;
        this.m20 = p171_6;
        this.m21 = p171_7;
        this.m22 = p171_8;
    }

    public final float getM22() {
        return this.m22;
    }

    public final void dn() {
        float f =
                1.0F
                        / (float)
                                java.lang.Math.sqrt(
                                        this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20);
        this.m00 *= f;
        this.m10 *= f;
        this.m20 *= f;
        f =
                1.0F
                        / (float)
                                java.lang.Math.sqrt(
                                        this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21);
        this.m01 *= f;
        this.m11 *= f;
        this.m21 *= f;
        this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
        this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
        this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
    }

    public final void method_539e22714a735164753f(int i, float f, float f1, float f2) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = f;
                this.m10 = f1;
                this.m20 = f2;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m01 = f;
                this.m11 = f1;
                this.m21 = f2;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m02 = f;
                this.m12 = f1;
                this.m22 = f2;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix3f9"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
        }
    }

    private final void invertGeneral(
            Matrix3f matrix3f) {
        String[] astring1 = Tuple3f.hF();
        double[] adouble = new double[9];
        double[] adouble1 = new double[9];
        int[] aint = new int[3];
        adouble[0] = matrix3f.m00;
        String[] astring = astring1;
        adouble[1] = matrix3f.m01;
        adouble[2] = matrix3f.m02;
        adouble[3] = matrix3f.m10;
        adouble[4] = matrix3f.m11;
        adouble[5] = matrix3f.m12;
        adouble[6] = matrix3f.m20;
        adouble[7] = matrix3f.m21;
        adouble[8] = matrix3f.m22;
        if (!method_46f13198e89824201e6a(adouble, aint)) {
            throw new SingularMatrixException(VecMathI18N.getString("Matrix3f12"));
        }

        int i = 0;

        while (i < 9) {
            adouble1[i] = 0.0;
            i++;
            if (astring != null) {
                break;
            }
        }

        adouble1[0] = 1.0;
        adouble1[4] = 1.0;
        adouble1[8] = 1.0;
        method_05badd4d3eb80f51c381(adouble, aint, adouble1);
        this.m00 = (float) adouble1[0];
        this.m01 = (float) adouble1[1];
        this.m02 = (float) adouble1[2];
        this.m10 = (float) adouble1[3];
        this.m11 = (float) adouble1[4];
        this.m12 = (float) adouble1[5];
        this.m20 = (float) adouble1[6];
        this.m21 = (float) adouble1[7];
        this.m22 = (float) adouble1[8];
    }

    public final void rotX(float p171_0) {
        float f1 = (float) java.lang.Math.sin(p171_0);
        float f2 = (float) java.lang.Math.cos(p171_0);
        this.m00 = 1.0F;
        this.m01 = 0.0F;
        this.m02 = 0.0F;
        this.m10 = 0.0F;
        this.m11 = f2;
        this.m12 = -f1;
        this.m20 = 0.0F;
        this.m21 = f1;
        this.m22 = f2;
    }

    public final void set(Matrix3f p171_0) {
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

    public final float aO() {
        return this.m00 * (this.m11 * this.m22 - this.m12 * this.m21)
                + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22)
                + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
    }

    public final float getM02() {
        return this.m02;
    }

    public final float getM01() {
        return this.m01;
    }

    public final void setM00(float f) {
        this.m00 = f;
    }

    public final float getM10() {
        return this.m10;
    }

    public final void setM01(float f) {
        this.m01 = f;
    }

    public final void method_d097613f0f9562f65e01(
            int i, Vector3f vector3f) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            vector3f.x = this.m00;
            vector3f.y = this.m01;
            vector3f.z = this.m02;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            vector3f.x = this.m10;
            vector3f.y = this.m11;
            vector3f.z = this.m12;
            if (astring == null) {
                return;
            }
        }

        if (i != 2) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
        }

        vector3f.x = this.m20;
        vector3f.y = this.m21;
        vector3f.z = this.m22;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
        }
    }

    public final void mulNormalize(Matrix3f p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[9];
        double[] adouble2 = new double[3];
        adouble[0] = this.m00 * p171_0.m00 + this.m01 * p171_0.m10 + this.m02 * p171_0.m20;
        adouble[1] = this.m00 * p171_0.m01 + this.m01 * p171_0.m11 + this.m02 * p171_0.m21;
        adouble[2] = this.m00 * p171_0.m02 + this.m01 * p171_0.m12 + this.m02 * p171_0.m22;
        adouble[3] = this.m10 * p171_0.m00 + this.m11 * p171_0.m10 + this.m12 * p171_0.m20;
        adouble[4] = this.m10 * p171_0.m01 + this.m11 * p171_0.m11 + this.m12 * p171_0.m21;
        adouble[5] = this.m10 * p171_0.m02 + this.m11 * p171_0.m12 + this.m12 * p171_0.m22;
        adouble[6] = this.m20 * p171_0.m00 + this.m21 * p171_0.m10 + this.m22 * p171_0.m20;
        adouble[7] = this.m20 * p171_0.m01 + this.m21 * p171_0.m11 + this.m22 * p171_0.m21;
        adouble[8] = this.m20 * p171_0.m02 + this.m21 * p171_0.m12 + this.m22 * p171_0.m22;
        remix.compat.vecmath.Matrix3d.compute_svd(adouble, adouble2, adouble1);
        this.m00 = (float) adouble1[0];
        this.m01 = (float) adouble1[1];
        this.m02 = (float) adouble1[2];
        this.m10 = (float) adouble1[3];
        this.m11 = (float) adouble1[4];
        this.m12 = (float) adouble1[5];
        this.m20 = (float) adouble1[6];
        this.m21 = (float) adouble1[7];
        this.m22 = (float) adouble1[8];
    }

    public final void l() {
        this.invertGeneral(this);
    }

    public final void set(Matrix3d p171_0) {
        this.m00 = (float) p171_0.m00;
        this.m01 = (float) p171_0.m01;
        this.m02 = (float) p171_0.m02;
        this.m10 = (float) p171_0.m10;
        this.m11 = (float) p171_0.m11;
        this.m12 = (float) p171_0.m12;
        this.m20 = (float) p171_0.m20;
        this.m21 = (float) p171_0.m21;
        this.m22 = (float) p171_0.m22;
    }

    @Override
    public String toString() {
        return this.m00
                + ", "
                + this.m01
                + ", "
                + this.m02
                + packedTextFragments[0]
                + this.m10
                + ", "
                + this.m11
                + ", "
                + this.m12
                + packedTextFragments[1]
                + this.m20
                + ", "
                + this.m21
                + ", "
                + this.m22
                + packedTextFragments[2];
    }

    public final void setM10(float f) {
        this.m10 = f;
    }

    public final float getM21() {
        return this.m21;
    }

    public final void method_8191183be20cc54e5f87(Matrix3f p171_0) {
        this.m00 = this.m00 + p171_0.m00;
        this.m01 = this.m01 + p171_0.m01;
        this.m02 = this.m02 + p171_0.m02;
        this.m10 = this.m10 + p171_0.m10;
        this.m11 = this.m11 + p171_0.m11;
        this.m12 = this.m12 + p171_0.m12;
        this.m20 = this.m20 + p171_0.m20;
        this.m21 = this.m21 + p171_0.m21;
        this.m22 = this.m22 + p171_0.m22;
    }

    public final void add(float p171_0, Matrix3f p171_1) {
        this.m00 = p171_1.m00 + p171_0;
        this.m01 = p171_1.m01 + p171_0;
        this.m02 = p171_1.m02 + p171_0;
        this.m10 = p171_1.m10 + p171_0;
        this.m11 = p171_1.m11 + p171_0;
        this.m12 = p171_1.m12 + p171_0;
        this.m20 = p171_1.m20 + p171_0;
        this.m21 = p171_1.m21 + p171_0;
        this.m22 = p171_1.m22 + p171_0;
    }

    public final void mulNormalize(
            Matrix3f p171_0, Matrix3f p171_1) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[9];
        double[] adouble2 = new double[3];
        adouble[0] = p171_0.m00 * p171_1.m00 + p171_0.m01 * p171_1.m10 + p171_0.m02 * p171_1.m20;
        adouble[1] = p171_0.m00 * p171_1.m01 + p171_0.m01 * p171_1.m11 + p171_0.m02 * p171_1.m21;
        adouble[2] = p171_0.m00 * p171_1.m02 + p171_0.m01 * p171_1.m12 + p171_0.m02 * p171_1.m22;
        adouble[3] = p171_0.m10 * p171_1.m00 + p171_0.m11 * p171_1.m10 + p171_0.m12 * p171_1.m20;
        adouble[4] = p171_0.m10 * p171_1.m01 + p171_0.m11 * p171_1.m11 + p171_0.m12 * p171_1.m21;
        adouble[5] = p171_0.m10 * p171_1.m02 + p171_0.m11 * p171_1.m12 + p171_0.m12 * p171_1.m22;
        adouble[6] = p171_0.m20 * p171_1.m00 + p171_0.m21 * p171_1.m10 + p171_0.m22 * p171_1.m20;
        adouble[7] = p171_0.m20 * p171_1.m01 + p171_0.m21 * p171_1.m11 + p171_0.m22 * p171_1.m21;
        adouble[8] = p171_0.m20 * p171_1.m02 + p171_0.m21 * p171_1.m12 + p171_0.m22 * p171_1.m22;
        remix.compat.vecmath.Matrix3d.compute_svd(adouble, adouble2, adouble1);
        this.m00 = (float) adouble1[0];
        this.m01 = (float) adouble1[1];
        this.m02 = (float) adouble1[2];
        this.m10 = (float) adouble1[3];
        this.m11 = (float) adouble1[4];
        this.m12 = (float) adouble1[5];
        this.m20 = (float) adouble1[6];
        this.m21 = (float) adouble1[7];
        this.m22 = (float) adouble1[8];
    }

    public final void method_4225c33ba98bdcea9805(int i, float[] afloat) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            afloat[0] = this.m00;
            afloat[1] = this.m01;
            afloat[2] = this.m02;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            afloat[0] = this.m10;
            afloat[1] = this.m11;
            afloat[2] = this.m12;
            if (astring == null) {
                return;
            }
        }

        if (i != 2) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
        }

        afloat[0] = this.m20;
        afloat[1] = this.m21;
        afloat[2] = this.m22;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
        }
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Matrix3f(Matrix3f matrix3f) {
        super();
        this.m00 = matrix3f.m00;
        this.m01 = matrix3f.m01;
        this.m02 = matrix3f.m02;
        this.m10 = matrix3f.m10;
        this.m11 = matrix3f.m11;
        this.m12 = matrix3f.m12;
        this.m20 = matrix3f.m20;
        this.m21 = matrix3f.m21;
        this.m22 = matrix3f.m22;
    }
}
