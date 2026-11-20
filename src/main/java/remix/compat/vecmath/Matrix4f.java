package remix.compat.vecmath;

import net.minecraft.entity.Entity;

import java.io.Serializable;

public class Matrix4f implements Serializable, Cloneable {
    public float m32;
    public float m30;
    public float m01;
    public float m00;
    public float m31;
    public float m33;
    private static final double EPS = 1.0E-8;
    public float m22;

    public float m12;
    public float m20;

    public float m03;
    public float m21;
    public float m11;
    public float m02;
    public float m10;
    static final long serialVersionUID = -8405036035410109353L;
    public float m13;
    private static String[] packedTextFragments;
    public float m23;

    public final void method_02384ba0b4822e59d6f8(int i, float[] afloat) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = afloat[0];
                this.m10 = afloat[1];
                this.m20 = afloat[2];
                this.m30 = afloat[3];
                if (astring == null) {
                    break;
                }
            case 1:
                this.m01 = afloat[0];
                this.m11 = afloat[1];
                this.m21 = afloat[2];
                this.m31 = afloat[3];
                if (astring == null) {
                    break;
                }
            case 2:
                this.m02 = afloat[0];
                this.m12 = afloat[1];
                this.m22 = afloat[2];
                this.m32 = afloat[3];
                if (astring == null) {
                    break;
                }
            case 3:
                this.m03 = afloat[0];
                this.m13 = afloat[1];
                this.m23 = afloat[2];
                this.m33 = afloat[3];
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix4f9"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
        }
    }

    public final void add(float p171_0) {
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

    public final void method_928af7c0266b884cdb3f(
            int i, Vector4f vector4f) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            vector4f.x = this.m00;
            vector4f.y = this.m01;
            vector4f.z = this.m02;
            vector4f.w = this.m03;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            vector4f.x = this.m10;
            vector4f.y = this.m11;
            vector4f.z = this.m12;
            vector4f.w = this.m13;
            if (astring == null) {
                return;
            }
        }

        if (i == 2) {
            vector4f.x = this.m20;
            vector4f.y = this.m21;
            vector4f.z = this.m22;
            vector4f.w = this.m23;
            if (astring == null) {
                return;
            }
        }

        if (i != 3) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
        }

        vector4f.x = this.m30;
        vector4f.y = this.m31;
        vector4f.z = this.m32;
        vector4f.w = this.m33;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
        }
    }

    public final void method_c3f3bbd0022138dc135d(
            int i, Vector4f vector4f) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            vector4f.x = this.m00;
            vector4f.y = this.m10;
            vector4f.z = this.m20;
            vector4f.w = this.m30;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            vector4f.x = this.m01;
            vector4f.y = this.m11;
            vector4f.z = this.m21;
            vector4f.w = this.m31;
            if (astring == null) {
                return;
            }
        }

        if (i == 2) {
            vector4f.x = this.m02;
            vector4f.y = this.m12;
            vector4f.z = this.m22;
            vector4f.w = this.m32;
            if (astring == null) {
                return;
            }
        }

        if (i != 3) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
        }

        vector4f.x = this.m03;
        vector4f.y = this.m13;
        vector4f.z = this.m23;
        vector4f.w = this.m33;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
        }
    }

    public final void method_6f0b65d4c2518f0af816(int i, float[] afloat) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = afloat[0];
                this.m01 = afloat[1];
                this.m02 = afloat[2];
                this.m03 = afloat[3];
                if (astring == null) {
                    break;
                }
            case 1:
                this.m10 = afloat[0];
                this.m11 = afloat[1];
                this.m12 = afloat[2];
                this.m13 = afloat[3];
                if (astring == null) {
                    break;
                }
            case 2:
                this.m20 = afloat[0];
                this.m21 = afloat[1];
                this.m22 = afloat[2];
                this.m23 = afloat[3];
                if (astring == null) {
                    break;
                }
            case 3:
                this.m30 = afloat[0];
                this.m31 = afloat[1];
                this.m32 = afloat[2];
                this.m33 = afloat[3];
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix4f6"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
        }
    }

    public final void set(
            Quat4d p171_0, Vector3d p171_1, double p171_2) {
        this.m00 =
                (float)
                        (p171_2
                                * (1.0
                                        - 2.0 * p171_0.y * p171_0.y
                                        - 2.0 * p171_0.z * p171_0.z));
        this.m10 = (float) (p171_2 * (2.0 * (p171_0.x * p171_0.y + p171_0.w * p171_0.z)));
        this.m20 = (float) (p171_2 * (2.0 * (p171_0.x * p171_0.z - p171_0.w * p171_0.y)));
        this.m01 = (float) (p171_2 * (2.0 * (p171_0.x * p171_0.y - p171_0.w * p171_0.z)));
        this.m11 =
                (float)
                        (p171_2
                                * (1.0
                                        - 2.0 * p171_0.x * p171_0.x
                                        - 2.0 * p171_0.z * p171_0.z));
        this.m21 = (float) (p171_2 * (2.0 * (p171_0.y * p171_0.z + p171_0.w * p171_0.x)));
        this.m02 = (float) (p171_2 * (2.0 * (p171_0.x * p171_0.z + p171_0.w * p171_0.y)));
        this.m12 = (float) (p171_2 * (2.0 * (p171_0.y * p171_0.z - p171_0.w * p171_0.x)));
        this.m22 =
                (float)
                        (p171_2
                                * (1.0
                                        - 2.0 * p171_0.x * p171_0.x
                                        - 2.0 * p171_0.y * p171_0.y));
        this.m03 = (float) p171_1.x;
        this.m13 = (float) p171_1.y;
        this.m23 = (float) p171_1.z;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final void set(
            Quat4f p171_0, Vector3f p171_1, float p171_2) {
        this.m00 = p171_2 * (1.0F - 2.0F * p171_0.y * p171_0.y - 2.0F * p171_0.z * p171_0.z);
        this.m10 = p171_2 * (2.0F * (p171_0.x * p171_0.y + p171_0.w * p171_0.z));
        this.m20 = p171_2 * (2.0F * (p171_0.x * p171_0.z - p171_0.w * p171_0.y));
        this.m01 = p171_2 * (2.0F * (p171_0.x * p171_0.y - p171_0.w * p171_0.z));
        this.m11 = p171_2 * (1.0F - 2.0F * p171_0.x * p171_0.x - 2.0F * p171_0.z * p171_0.z);
        this.m21 = p171_2 * (2.0F * (p171_0.y * p171_0.z + p171_0.w * p171_0.x));
        this.m02 = p171_2 * (2.0F * (p171_0.x * p171_0.z + p171_0.w * p171_0.y));
        this.m12 = p171_2 * (2.0F * (p171_0.y * p171_0.z - p171_0.w * p171_0.x));
        this.m22 = p171_2 * (1.0F - 2.0F * p171_0.x * p171_0.x - 2.0F * p171_0.y * p171_0.y);
        this.m03 = p171_1.x;
        this.m13 = p171_1.y;
        this.m23 = p171_1.z;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final void setIdentity() {
        this.m00 = 1.0F;
        this.m01 = 0.0F;
        this.m02 = 0.0F;
        this.m03 = 0.0F;
        this.m10 = 0.0F;
        this.m11 = 1.0F;
        this.m12 = 0.0F;
        this.m13 = 0.0F;
        this.m20 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = 1.0F;
        this.m23 = 0.0F;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final void set(Matrix3d p171_0) {
        this.m00 = (float) p171_0.m00;
        this.m01 = (float) p171_0.m01;
        this.m02 = (float) p171_0.m02;
        this.m03 = 0.0F;
        this.m10 = (float) p171_0.m10;
        this.m11 = (float) p171_0.m11;
        this.m12 = (float) p171_0.m12;
        this.m13 = 0.0F;
        this.m20 = (float) p171_0.m20;
        this.m21 = (float) p171_0.m21;
        this.m22 = (float) p171_0.m22;
        this.m23 = 0.0F;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
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

        throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f1"));
    }

    public final float getM01() {
        return this.m01;
    }

    public final float get(
            Matrix3f p171_0, Vector3f p171_1) {
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
        return (float) remix.compat.vecmath.Matrix3d.max3(adouble1);
    }

    public final void setM31(float f) {
        this.m31 = f;
    }

    public final void invertInPlace() {
        this.invertGeneral(this);
    }

    public final void rotZ(float p171_0) {
        float f1 = (float) java.lang.Math.sin(p171_0);
        float f2 = (float) java.lang.Math.cos(p171_0);
        this.m00 = f2;
        this.m01 = -f1;
        this.m02 = 0.0F;
        this.m03 = 0.0F;
        this.m10 = f1;
        this.m11 = f2;
        this.m12 = 0.0F;
        this.m13 = 0.0F;
        this.m20 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = 1.0F;
        this.m23 = 0.0F;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public Matrix4f(Matrix4d p171_0) {
        this.m00 = (float) p171_0.m00;
        this.m01 = (float) p171_0.m01;
        this.m02 = (float) p171_0.m02;
        this.m03 = (float) p171_0.m03;
        this.m10 = (float) p171_0.m10;
        this.m11 = (float) p171_0.m11;
        this.m12 = (float) p171_0.m12;
        this.m13 = (float) p171_0.m13;
        this.m20 = (float) p171_0.m20;
        this.m21 = (float) p171_0.m21;
        this.m22 = (float) p171_0.m22;
        this.m23 = (float) p171_0.m23;
        this.m30 = (float) p171_0.m30;
        this.m31 = (float) p171_0.m31;
        this.m32 = (float) p171_0.m32;
        this.m33 = (float) p171_0.m33;
    }

    public final void get(
            Vector3f vector3f) {
        vector3f.x = this.m03;
        vector3f.y = this.m13;
        vector3f.z = this.m23;
    }

    public final void transform(
            Tuple4f p171_0, Tuple4f p171_1) {
        float f =
                this.m00 * p171_0.x
                        + this.m01 * p171_0.y
                        + this.m02 * p171_0.z
                        + this.m03 * p171_0.w;
        float f1 =
                this.m10 * p171_0.x
                        + this.m11 * p171_0.y
                        + this.m12 * p171_0.z
                        + this.m13 * p171_0.w;
        float f2 =
                this.m20 * p171_0.x
                        + this.m21 * p171_0.y
                        + this.m22 * p171_0.z
                        + this.m23 * p171_0.w;
        p171_1.w =
                this.m30 * p171_0.x
                        + this.m31 * p171_0.y
                        + this.m32 * p171_0.z
                        + this.m33 * p171_0.w;
        p171_1.x = f;
        p171_1.y = f1;
        p171_1.z = f2;
    }

    public final void setM33(float f) {
        this.m33 = f;
    }

    public final float getM13() {
        return this.m13;
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Matrix4f matrix4f =
                    (remix.compat.vecmath.Matrix4f) p171_0;
            return (this.m00 == matrix4f.m00
                    && this.m01 == matrix4f.m01
                    && this.m02 == matrix4f.m02
                    && this.m03 == matrix4f.m03
                    && this.m10 == matrix4f.m10
                    && this.m11 == matrix4f.m11
                    && this.m12 == matrix4f.m12
                    && this.m13 == matrix4f.m13
                    && this.m20 == matrix4f.m20
                    && this.m21 == matrix4f.m21
                    && this.m22 == matrix4f.m22
                    && this.m23 == matrix4f.m23
                    && this.m30 == matrix4f.m30
                    && this.m31 == matrix4f.m31
                    && this.m32 == matrix4f.m32
                    && this.m33 == matrix4f.m33);
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final void sub(
            Matrix4f p171_0, Matrix4f p171_1) {
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

    public final void setM00(float f) {
        this.m00 = f;
    }

    public final void method_f9e4183b30591ca0aeb5(AxisAngle4f p171_0) {
        double[] adouble = new double[9];
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        java.lang.String[] astring = astring1;
        double d0 =
                java.lang.Math.sqrt(
                        p171_0.x * p171_0.x + p171_0.y * p171_0.y + p171_0.z * p171_0.z);

        try {
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
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            throw remix.compat.vecmath.Matrix4f.wrapException(
                    arrayindexoutofboundsexception);
        }

        d0 = 1.0 / d0;
        double d1 = p171_0.x * d0;
        double d2 = p171_0.y * d0;
        double d3 = p171_0.z * d0;
        double d4 = java.lang.Math.sin(p171_0.angle);
        double d5 = java.lang.Math.cos(p171_0.angle);
        double d6 = 1.0 - d5;
        double d7 = p171_0.x * p171_0.z;
        double d8 = p171_0.x * p171_0.y;
        double d9 = p171_0.y * p171_0.z;
        this.m00 = (float) ((d6 * d1 * d1 + d5) * adouble1[0]);
        this.m01 = (float) ((d6 * d8 - d4 * d3) * adouble1[1]);
        this.m02 = (float) ((d6 * d7 + d4 * d2) * adouble1[2]);
        this.m10 = (float) ((d6 * d8 + d4 * d3) * adouble1[0]);
        this.m11 = (float) ((d6 * d2 * d2 + d5) * adouble1[1]);
        this.m12 = (float) ((d6 * d9 - d4 * d1) * adouble1[2]);
        this.m20 = (float) ((d6 * d7 - d4 * d2) * adouble1[0]);
        this.m21 = (float) ((d6 * d9 + d4 * d1) * adouble1[1]);
        this.m22 = (float) ((d6 * d3 * d3 + d5) * adouble1[2]);
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

    public final float aO() {
        float f =
                this.m00
                        * (this.m11 * this.m22 * this.m33
                                + this.m12 * this.m23 * this.m31
                                + this.m13 * this.m21 * this.m32
                                - this.m13 * this.m22 * this.m31
                                - this.m11 * this.m23 * this.m32
                                - this.m12 * this.m21 * this.m33);
        f -=
                this.m01
                        * (this.m10 * this.m22 * this.m33
                                + this.m12 * this.m23 * this.m30
                                + this.m13 * this.m20 * this.m32
                                - this.m13 * this.m22 * this.m30
                                - this.m10 * this.m23 * this.m32
                                - this.m12 * this.m20 * this.m33);
        f +=
                this.m02
                        * (this.m10 * this.m21 * this.m33
                                + this.m11 * this.m23 * this.m30
                                + this.m13 * this.m20 * this.m31
                                - this.m13 * this.m21 * this.m30
                                - this.m10 * this.m23 * this.m31
                                - this.m11 * this.m20 * this.m33);
        return f
                - this.m03
                        * (this.m10 * this.m21 * this.m32
                                + this.m11 * this.m22 * this.m30
                                + this.m12 * this.m20 * this.m31
                                - this.m12 * this.m21 * this.m30
                                - this.m10 * this.m22 * this.m31
                                - this.m11 * this.m20 * this.m32);
    }

    public final void setM30(float f) {
        this.m30 = f;
    }

    public final void setRotation(Matrix3f p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        this.m00 = (float) (p171_0.m00 * adouble1[0]);
        this.m01 = (float) (p171_0.m01 * adouble1[1]);
        this.m02 = (float) (p171_0.m02 * adouble1[2]);
        this.m10 = (float) (p171_0.m10 * adouble1[0]);
        this.m11 = (float) (p171_0.m11 * adouble1[1]);
        this.m12 = (float) (p171_0.m12 * adouble1[2]);
        this.m20 = (float) (p171_0.m20 * adouble1[0]);
        this.m21 = (float) (p171_0.m21 * adouble1[1]);
        this.m22 = (float) (p171_0.m22 * adouble1[2]);
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

    final void invertGeneral(Matrix4f matrix4f) {
        double[] adouble = new double[16];
        double[] adouble1 = new double[16];
        int[] aint = new int[4];
        adouble[0] = matrix4f.m00;
        String[] astring1 = Tuple3f.hF();
        adouble[1] = matrix4f.m01;
        adouble[2] = matrix4f.m02;
        adouble[3] = matrix4f.m03;
        adouble[4] = matrix4f.m10;
        adouble[5] = matrix4f.m11;
        String[] astring = astring1;
        adouble[6] = matrix4f.m12;
        adouble[7] = matrix4f.m13;
        adouble[8] = matrix4f.m20;
        adouble[9] = matrix4f.m21;
        adouble[10] = matrix4f.m22;
        adouble[11] = matrix4f.m23;
        adouble[12] = matrix4f.m30;
        adouble[13] = matrix4f.m31;
        adouble[14] = matrix4f.m32;
        adouble[15] = matrix4f.m33;
        if (!method_46f13198e89824201e6a(adouble, aint)) {
            throw new SingularMatrixException(VecMathI18N.getString("Matrix4f12"));
        }

        int i = 0;

        while (i < 16) {
            adouble1[i] = 0.0;
            i++;
            if (astring != null) {
                break;
            }
        }

        adouble1[0] = 1.0;
        adouble1[5] = 1.0;
        adouble1[10] = 1.0;
        adouble1[15] = 1.0;
        method_05badd4d3eb80f51c381(adouble, aint, adouble1);
        this.m00 = (float) adouble1[0];
        this.m01 = (float) adouble1[1];
        this.m02 = (float) adouble1[2];
        this.m03 = (float) adouble1[3];
        this.m10 = (float) adouble1[4];
        this.m11 = (float) adouble1[5];
        this.m12 = (float) adouble1[6];
        this.m13 = (float) adouble1[7];
        this.m20 = (float) adouble1[8];
        this.m21 = (float) adouble1[9];
        this.m22 = (float) adouble1[10];
        this.m23 = (float) adouble1[11];
        this.m30 = (float) adouble1[12];
        this.m31 = (float) adouble1[13];
        this.m32 = (float) adouble1[14];
        this.m33 = (float) adouble1[15];
    }

    public final void setRotationScale(
            Matrix3f matrix3f) {
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

    public final void set(Vector3f p171_0, float p171_1) {
        this.m00 = p171_1;
        this.m01 = 0.0F;
        this.m02 = 0.0F;
        this.m03 = p171_1 * p171_0.x;
        this.m10 = 0.0F;
        this.m11 = p171_1;
        this.m12 = 0.0F;
        this.m13 = p171_1 * p171_0.y;
        this.m20 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = p171_1;
        this.m23 = p171_1 * p171_0.z;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final void add(float p171_0, Matrix4f p171_1) {
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

    public final void set(Matrix4d p171_0) {
        this.m00 = (float) p171_0.m00;
        this.m01 = (float) p171_0.m01;
        this.m02 = (float) p171_0.m02;
        this.m03 = (float) p171_0.m03;
        this.m10 = (float) p171_0.m10;
        this.m11 = (float) p171_0.m11;
        this.m12 = (float) p171_0.m12;
        this.m13 = (float) p171_0.m13;
        this.m20 = (float) p171_0.m20;
        this.m21 = (float) p171_0.m21;
        this.m22 = (float) p171_0.m22;
        this.m23 = (float) p171_0.m23;
        this.m30 = (float) p171_0.m30;
        this.m31 = (float) p171_0.m31;
        this.m32 = (float) p171_0.m32;
        this.m33 = (float) p171_0.m33;
    }

    public final void setM02(float f) {
        this.m02 = f;
    }

    public final void method_b49a0c8a4336c082a207(
            int i, Vector4f vector4f) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = vector4f.x;
                this.m01 = vector4f.y;
                this.m02 = vector4f.z;
                this.m03 = vector4f.w;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m10 = vector4f.x;
                this.m11 = vector4f.y;
                this.m12 = vector4f.z;
                this.m13 = vector4f.w;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m20 = vector4f.x;
                this.m21 = vector4f.y;
                this.m22 = vector4f.z;
                this.m23 = vector4f.w;
                if (astring == null) {
                    break;
                }
            case 3:
                this.m30 = vector4f.x;
                this.m31 = vector4f.y;
                this.m32 = vector4f.z;
                this.m33 = vector4f.w;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix4f6"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
        }
    }

    public final void set(float p171_0) {
        this.m00 = p171_0;
        this.m01 = 0.0F;
        this.m02 = 0.0F;
        this.m03 = 0.0F;
        this.m10 = 0.0F;
        this.m11 = p171_0;
        this.m12 = 0.0F;
        this.m13 = 0.0F;
        this.m20 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = p171_0;
        this.m23 = 0.0F;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final float getM00() {
        return this.m00;
    }

    public final float getM31() {
        return this.m31;
    }

    public final void mul(float p171_0, Matrix4f p171_1) {
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

    public final void transform(Vector3f p171_0) {
        float f = this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z;
        float f1 = this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z;
        p171_0.z = this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z;
        p171_0.x = f;
        p171_0.y = f1;
    }

    public final void setRotation(Quat4d p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        this.m00 =
                (float)
                        ((1.0 - 2.0 * p171_0.y * p171_0.y - 2.0 * p171_0.z * p171_0.z)
                                * adouble1[0]);
        this.m10 = (float) (2.0 * (p171_0.x * p171_0.y + p171_0.w * p171_0.z) * adouble1[0]);
        this.m20 = (float) (2.0 * (p171_0.x * p171_0.z - p171_0.w * p171_0.y) * adouble1[0]);
        this.m01 = (float) (2.0 * (p171_0.x * p171_0.y - p171_0.w * p171_0.z) * adouble1[1]);
        this.m11 =
                (float)
                        ((1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.z * p171_0.z)
                                * adouble1[1]);
        this.m21 = (float) (2.0 * (p171_0.y * p171_0.z + p171_0.w * p171_0.x) * adouble1[1]);
        this.m02 = (float) (2.0 * (p171_0.x * p171_0.z + p171_0.w * p171_0.y) * adouble1[2]);
        this.m12 = (float) (2.0 * (p171_0.y * p171_0.z - p171_0.w * p171_0.x) * adouble1[2]);
        this.m22 =
                (float)
                        ((1.0 - 2.0 * p171_0.x * p171_0.x - 2.0 * p171_0.y * p171_0.y)
                                * adouble1[2]);
    }

    // region String decoding support

    // endregion

    public final void setM20(float f) {
        this.m20 = f;
    }

    public final void setM21(float f) {
        this.m21 = f;
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
                        if (astring == null) {
                            return;
                        }
                    case 3:
                        this.m03 = f;
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix4f0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix4f0"));
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
                        if (astring == null) {
                            return;
                        }
                    case 3:
                        this.m13 = f;
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix4f0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix4f0"));
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
                        if (astring == null) {
                            return;
                        }
                    case 3:
                        this.m23 = f;
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix4f0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix4f0"));
                }
            case 3:
                switch (j) {
                    case 0:
                        this.m30 = f;
                        if (astring == null) {
                            return;
                        }
                    case 1:
                        this.m31 = f;
                        if (astring == null) {
                            return;
                        }
                    case 2:
                        this.m32 = f;
                        if (astring == null) {
                            return;
                        }
                    case 3:
                        this.m33 = f;
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix4f0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix4f0"));
                }
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
        }
    }

    public final float getM20() {
        return this.m20;
    }

    public final void mul(
            Matrix4f matrix4f,
            Matrix4f matrix4fValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix4f && this != matrix4fValue) {
            this.m00 =
                    matrix4f.m00 * matrix4fValue.m00
                            + matrix4f.m01 * matrix4fValue.m01
                            + matrix4f.m02 * matrix4fValue.m02
                            + matrix4f.m03 * matrix4fValue.m03;
            this.m01 =
                    matrix4f.m00 * matrix4fValue.m10
                            + matrix4f.m01 * matrix4fValue.m11
                            + matrix4f.m02 * matrix4fValue.m12
                            + matrix4f.m03 * matrix4fValue.m13;
            this.m02 =
                    matrix4f.m00 * matrix4fValue.m20
                            + matrix4f.m01 * matrix4fValue.m21
                            + matrix4f.m02 * matrix4fValue.m22
                            + matrix4f.m03 * matrix4fValue.m23;
            this.m03 =
                    matrix4f.m00 * matrix4fValue.m30
                            + matrix4f.m01 * matrix4fValue.m31
                            + matrix4f.m02 * matrix4fValue.m32
                            + matrix4f.m03 * matrix4fValue.m33;
            this.m10 =
                    matrix4f.m10 * matrix4fValue.m00
                            + matrix4f.m11 * matrix4fValue.m01
                            + matrix4f.m12 * matrix4fValue.m02
                            + matrix4f.m13 * matrix4fValue.m03;
            this.m11 =
                    matrix4f.m10 * matrix4fValue.m10
                            + matrix4f.m11 * matrix4fValue.m11
                            + matrix4f.m12 * matrix4fValue.m12
                            + matrix4f.m13 * matrix4fValue.m13;
            this.m12 =
                    matrix4f.m10 * matrix4fValue.m20
                            + matrix4f.m11 * matrix4fValue.m21
                            + matrix4f.m12 * matrix4fValue.m22
                            + matrix4f.m13 * matrix4fValue.m23;
            this.m13 =
                    matrix4f.m10 * matrix4fValue.m30
                            + matrix4f.m11 * matrix4fValue.m31
                            + matrix4f.m12 * matrix4fValue.m32
                            + matrix4f.m13 * matrix4fValue.m33;
            this.m20 =
                    matrix4f.m20 * matrix4fValue.m00
                            + matrix4f.m21 * matrix4fValue.m01
                            + matrix4f.m22 * matrix4fValue.m02
                            + matrix4f.m23 * matrix4fValue.m03;
            this.m21 =
                    matrix4f.m20 * matrix4fValue.m10
                            + matrix4f.m21 * matrix4fValue.m11
                            + matrix4f.m22 * matrix4fValue.m12
                            + matrix4f.m23 * matrix4fValue.m13;
            this.m22 =
                    matrix4f.m20 * matrix4fValue.m20
                            + matrix4f.m21 * matrix4fValue.m21
                            + matrix4f.m22 * matrix4fValue.m22
                            + matrix4f.m23 * matrix4fValue.m23;
            this.m23 =
                    matrix4f.m20 * matrix4fValue.m30
                            + matrix4f.m21 * matrix4fValue.m31
                            + matrix4f.m22 * matrix4fValue.m32
                            + matrix4f.m23 * matrix4fValue.m33;
            this.m30 =
                    matrix4f.m30 * matrix4fValue.m00
                            + matrix4f.m31 * matrix4fValue.m01
                            + matrix4f.m32 * matrix4fValue.m02
                            + matrix4f.m33 * matrix4fValue.m03;
            this.m31 =
                    matrix4f.m30 * matrix4fValue.m10
                            + matrix4f.m31 * matrix4fValue.m11
                            + matrix4f.m32 * matrix4fValue.m12
                            + matrix4f.m33 * matrix4fValue.m13;
            this.m32 =
                    matrix4f.m30 * matrix4fValue.m20
                            + matrix4f.m31 * matrix4fValue.m21
                            + matrix4f.m32 * matrix4fValue.m22
                            + matrix4f.m33 * matrix4fValue.m23;
            this.m33 =
                    matrix4f.m30 * matrix4fValue.m30
                            + matrix4f.m31 * matrix4fValue.m31
                            + matrix4f.m32 * matrix4fValue.m32
                            + matrix4f.m33 * matrix4fValue.m33;
            if (astring == null) {
                return;
            }
        }

        float f =
                matrix4f.m00 * matrix4fValue.m00
                        + matrix4f.m01 * matrix4fValue.m01
                        + matrix4f.m02 * matrix4fValue.m02
                        + matrix4f.m03 * matrix4fValue.m03;
        float f1 =
                matrix4f.m00 * matrix4fValue.m10
                        + matrix4f.m01 * matrix4fValue.m11
                        + matrix4f.m02 * matrix4fValue.m12
                        + matrix4f.m03 * matrix4fValue.m13;
        float f2 =
                matrix4f.m00 * matrix4fValue.m20
                        + matrix4f.m01 * matrix4fValue.m21
                        + matrix4f.m02 * matrix4fValue.m22
                        + matrix4f.m03 * matrix4fValue.m23;
        float f3 =
                matrix4f.m00 * matrix4fValue.m30
                        + matrix4f.m01 * matrix4fValue.m31
                        + matrix4f.m02 * matrix4fValue.m32
                        + matrix4f.m03 * matrix4fValue.m33;
        float f4 =
                matrix4f.m10 * matrix4fValue.m00
                        + matrix4f.m11 * matrix4fValue.m01
                        + matrix4f.m12 * matrix4fValue.m02
                        + matrix4f.m13 * matrix4fValue.m03;
        float f5 =
                matrix4f.m10 * matrix4fValue.m10
                        + matrix4f.m11 * matrix4fValue.m11
                        + matrix4f.m12 * matrix4fValue.m12
                        + matrix4f.m13 * matrix4fValue.m13;
        float f6 =
                matrix4f.m10 * matrix4fValue.m20
                        + matrix4f.m11 * matrix4fValue.m21
                        + matrix4f.m12 * matrix4fValue.m22
                        + matrix4f.m13 * matrix4fValue.m23;
        float f7 =
                matrix4f.m10 * matrix4fValue.m30
                        + matrix4f.m11 * matrix4fValue.m31
                        + matrix4f.m12 * matrix4fValue.m32
                        + matrix4f.m13 * matrix4fValue.m33;
        float f8 =
                matrix4f.m20 * matrix4fValue.m00
                        + matrix4f.m21 * matrix4fValue.m01
                        + matrix4f.m22 * matrix4fValue.m02
                        + matrix4f.m23 * matrix4fValue.m03;
        float f9 =
                matrix4f.m20 * matrix4fValue.m10
                        + matrix4f.m21 * matrix4fValue.m11
                        + matrix4f.m22 * matrix4fValue.m12
                        + matrix4f.m23 * matrix4fValue.m13;
        float f10 =
                matrix4f.m20 * matrix4fValue.m20
                        + matrix4f.m21 * matrix4fValue.m21
                        + matrix4f.m22 * matrix4fValue.m22
                        + matrix4f.m23 * matrix4fValue.m23;
        float f11 =
                matrix4f.m20 * matrix4fValue.m30
                        + matrix4f.m21 * matrix4fValue.m31
                        + matrix4f.m22 * matrix4fValue.m32
                        + matrix4f.m23 * matrix4fValue.m33;
        float f12 =
                matrix4f.m30 * matrix4fValue.m00
                        + matrix4f.m31 * matrix4fValue.m01
                        + matrix4f.m32 * matrix4fValue.m02
                        + matrix4f.m33 * matrix4fValue.m03;
        float f13 =
                matrix4f.m30 * matrix4fValue.m10
                        + matrix4f.m31 * matrix4fValue.m11
                        + matrix4f.m32 * matrix4fValue.m12
                        + matrix4f.m33 * matrix4fValue.m13;
        float f14 =
                matrix4f.m30 * matrix4fValue.m20
                        + matrix4f.m31 * matrix4fValue.m21
                        + matrix4f.m32 * matrix4fValue.m22
                        + matrix4f.m33 * matrix4fValue.m23;
        float f15 =
                matrix4f.m30 * matrix4fValue.m30
                        + matrix4f.m31 * matrix4fValue.m31
                        + matrix4f.m32 * matrix4fValue.m32
                        + matrix4f.m33 * matrix4fValue.m33;
        this.m00 = f;
        this.m01 = f1;
        this.m02 = f2;
        this.m03 = f3;
        this.m10 = f4;
        this.m11 = f5;
        this.m12 = f6;
        this.m13 = f7;
        this.m20 = f8;
        this.m21 = f9;
        this.m22 = f10;
        this.m23 = f11;
        this.m30 = f12;
        this.m31 = f13;
        this.m32 = f14;
        this.m33 = f15;
    }

    static {
        remix.compat.vecmath.Matrix4f.packedTextFragments = new String[6];
        remix.compat.vecmath.Matrix4f.packedTextFragments[0] = new String("\n");
        remix.compat.vecmath.Matrix4f.packedTextFragments[1] = new String("\n");
        remix.compat.vecmath.Matrix4f.packedTextFragments[2] = new String("\n");
        remix.compat.vecmath.Matrix4f.packedTextFragments[3] = new String("\n");
    }

    public Matrix4f(
            float p171_0,
            float p171_1,
            float p171_2,
            float p171_3,
            float p171_4,
            float p171_5,
            float p171_6,
            float p171_7,
            float p171_8,
            float p171_9,
            float p171_10,
            float p171_11,
            float p171_12,
            float p171_13,
            float p171_14,
            float p171_15) {
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

    public final float getM03() {
        return this.m03;
    }

    public final float getM32() {
        return this.m32;
    }

    public final float getM12() {
        return this.m12;
    }

    public final void transform(Point3f p171_0) {
        float f = this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z + this.m03;
        float f1 = this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z + this.m13;
        p171_0.z = this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z + this.m23;
        p171_0.x = f;
        p171_0.y = f1;
    }

    public final void mul(
            Matrix4f matrix4f) {
        float f =
                this.m00 * matrix4f.m00
                        + this.m01 * matrix4f.m10
                        + this.m02 * matrix4f.m20
                        + this.m03 * matrix4f.m30;
        float f1 =
                this.m00 * matrix4f.m01
                        + this.m01 * matrix4f.m11
                        + this.m02 * matrix4f.m21
                        + this.m03 * matrix4f.m31;
        float f2 =
                this.m00 * matrix4f.m02
                        + this.m01 * matrix4f.m12
                        + this.m02 * matrix4f.m22
                        + this.m03 * matrix4f.m32;
        String[] astring1 = Tuple3f.hF();
        float f3 =
                this.m00 * matrix4f.m03
                        + this.m01 * matrix4f.m13
                        + this.m02 * matrix4f.m23
                        + this.m03 * matrix4f.m33;
        float f4 =
                this.m10 * matrix4f.m00
                        + this.m11 * matrix4f.m10
                        + this.m12 * matrix4f.m20
                        + this.m13 * matrix4f.m30;
        float f5 =
                this.m10 * matrix4f.m01
                        + this.m11 * matrix4f.m11
                        + this.m12 * matrix4f.m21
                        + this.m13 * matrix4f.m31;
        float f6 =
                this.m10 * matrix4f.m02
                        + this.m11 * matrix4f.m12
                        + this.m12 * matrix4f.m22
                        + this.m13 * matrix4f.m32;
        float f7 =
                this.m10 * matrix4f.m03
                        + this.m11 * matrix4f.m13
                        + this.m12 * matrix4f.m23
                        + this.m13 * matrix4f.m33;
        float f8 =
                this.m20 * matrix4f.m00
                        + this.m21 * matrix4f.m10
                        + this.m22 * matrix4f.m20
                        + this.m23 * matrix4f.m30;
        float f9 =
                this.m20 * matrix4f.m01
                        + this.m21 * matrix4f.m11
                        + this.m22 * matrix4f.m21
                        + this.m23 * matrix4f.m31;
        float f10 =
                this.m20 * matrix4f.m02
                        + this.m21 * matrix4f.m12
                        + this.m22 * matrix4f.m22
                        + this.m23 * matrix4f.m32;
        float f11 =
                this.m20 * matrix4f.m03
                        + this.m21 * matrix4f.m13
                        + this.m22 * matrix4f.m23
                        + this.m23 * matrix4f.m33;
        float f12 =
                this.m30 * matrix4f.m00
                        + this.m31 * matrix4f.m10
                        + this.m32 * matrix4f.m20
                        + this.m33 * matrix4f.m30;
        float f13 =
                this.m30 * matrix4f.m01
                        + this.m31 * matrix4f.m11
                        + this.m32 * matrix4f.m21
                        + this.m33 * matrix4f.m31;
        float f14 =
                this.m30 * matrix4f.m02
                        + this.m31 * matrix4f.m12
                        + this.m32 * matrix4f.m22
                        + this.m33 * matrix4f.m32;
        String[] astring = astring1;
        float f15 =
                this.m30 * matrix4f.m03
                        + this.m31 * matrix4f.m13
                        + this.m32 * matrix4f.m23
                        + this.m33 * matrix4f.m33;
        this.m00 = f;
        this.m01 = f1;
        this.m02 = f2;
        this.m03 = f3;
        this.m10 = f4;
        this.m11 = f5;
        this.m12 = f6;
        this.m13 = f7;
        this.m20 = f8;
        this.m21 = f9;
        this.m22 = f10;
        this.m23 = f11;
        this.m30 = f12;
        this.m31 = f13;
        this.m32 = f14;
        this.m33 = f15;
        if (astring != null) {
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }
    }

    public final void method_4225c33ba98bdcea9805(int i, float[] afloat) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            afloat[0] = this.m00;
            afloat[1] = this.m01;
            afloat[2] = this.m02;
            afloat[3] = this.m03;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            afloat[0] = this.m10;
            afloat[1] = this.m11;
            afloat[2] = this.m12;
            afloat[3] = this.m13;
            if (astring == null) {
                return;
            }
        }

        if (i == 2) {
            afloat[0] = this.m20;
            afloat[1] = this.m21;
            afloat[2] = this.m22;
            afloat[3] = this.m23;
            if (astring == null) {
                return;
            }
        }

        if (i != 3) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
        }

        afloat[0] = this.m30;
        afloat[1] = this.m31;
        afloat[2] = this.m32;
        afloat[3] = this.m33;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
        }
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
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final void set(
            Matrix3d p171_0, Vector3d p171_1, double p171_2) {
        this.m00 = (float) (p171_0.m00 * p171_2);
        this.m01 = (float) (p171_0.m01 * p171_2);
        this.m02 = (float) (p171_0.m02 * p171_2);
        this.m03 = (float) p171_1.x;
        this.m10 = (float) (p171_0.m10 * p171_2);
        this.m11 = (float) (p171_0.m11 * p171_2);
        this.m12 = (float) (p171_0.m12 * p171_2);
        this.m13 = (float) p171_1.y;
        this.m20 = (float) (p171_0.m20 * p171_2);
        this.m21 = (float) (p171_0.m21 * p171_2);
        this.m22 = (float) (p171_0.m22 * p171_2);
        this.m23 = (float) p171_1.z;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
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

    public Matrix4f(float[] p171_0) {
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

    @Override
    public Object clone() {

        try {
            return (remix.compat.vecmath.Matrix4f) super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }
    }

    public final void set(float[] p171_0) {
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

    public final void invert(
            Matrix4f matrix4f) {
        this.invertGeneral(matrix4f);
    }

    public final void setM13(float f) {
        this.m13 = f;
    }

    public final void setM32(float f) {
        this.m32 = f;
    }

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
                    break label12;
                }
            }

            d0 = 1.0 / d0;
            double d1 = axisAngle4d.x * d0;
            double d2 = axisAngle4d.y * d0;
            double d3 = axisAngle4d.z * d0;
            float f = (float) Math.sin(axisAngle4d.angle);
            float f1 = (float) Math.cos(axisAngle4d.angle);
            float f2 = 1.0F - f1;
            float f3 = (float) (d1 * d3);
            float f4 = (float) (d1 * d2);
            float f5 = (float) (d2 * d3);
            this.m00 = f2 * (float) (d1 * d1) + f1;
            this.m01 = f2 * f4 - f * (float) d3;
            this.m02 = f2 * f3 + f * (float) d2;
            this.m10 = f2 * f4 + f * (float) d3;
            this.m11 = f2 * (float) (d2 * d2) + f1;
            this.m12 = f2 * f5 - f * (float) d1;
            this.m20 = f2 * f3 - f * (float) d2;
            this.m21 = f2 * f5 + f * (float) d1;
            this.m22 = f2 * (float) (d3 * d3) + f1;
        }

        this.m03 = 0.0F;
        this.m13 = 0.0F;
        this.m23 = 0.0F;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final void setM11(float f) {
        this.m11 = f;
    }

    public final void method_b7ff64c21ac14c0c1c9b(Matrix4f p171_0) {
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

    public final void set(Vector3f p171_0) {
        this.m00 = 1.0F;
        this.m01 = 0.0F;
        this.m02 = 0.0F;
        this.m03 = p171_0.x;
        this.m10 = 0.0F;
        this.m11 = 1.0F;
        this.m12 = 0.0F;
        this.m13 = p171_0.y;
        this.m20 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = 1.0F;
        this.m23 = p171_0.z;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final void setM23(float f) {
        this.m23 = f;
    }

    public boolean epsilonEquals(Matrix4f p171_0, float p171_1) {
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

        if (java.lang.Math.abs(this.m03 - p171_0.m03) > p171_1) {
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

        if (java.lang.Math.abs(this.m13 - p171_0.m13) > p171_1) {
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

        if (java.lang.Math.abs(this.m23 - p171_0.m23) > p171_1) {
            flag = false;
        }

        if (java.lang.Math.abs(this.m30 - p171_0.m30) > p171_1) {
            flag = false;
        }

        if (java.lang.Math.abs(this.m31 - p171_0.m31) > p171_1) {
            flag = false;
        }

        if (java.lang.Math.abs(this.m32 - p171_0.m32) > p171_1) {
            flag = false;
        }

        if (java.lang.Math.abs(this.m33 - p171_0.m33) > p171_1) {
            flag = false;
        }

        return flag;
    }

    public final void transform(
            Vector3f p171_0, Vector3f p171_1) {
        float f = this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z;
        float f1 = this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z;
        p171_1.z = this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z;
        p171_1.x = f;
        p171_1.y = f1;
    }

    public final float getM30() {
        return this.m30;
    }

    public final void setM22(float f) {
        this.m22 = f;
    }

    public Matrix4f() {
        this.m00 = 0.0F;
        this.m01 = 0.0F;
        this.m02 = 0.0F;
        this.m03 = 0.0F;
        this.m10 = 0.0F;
        this.m11 = 0.0F;
        this.m12 = 0.0F;
        this.m13 = 0.0F;
        this.m20 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = 0.0F;
        this.m23 = 0.0F;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 0.0F;
    }

    public final void setRotation(Matrix3d p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        this.m00 = (float) (p171_0.m00 * adouble1[0]);
        this.m01 = (float) (p171_0.m01 * adouble1[1]);
        this.m02 = (float) (p171_0.m02 * adouble1[2]);
        this.m10 = (float) (p171_0.m10 * adouble1[0]);
        this.m11 = (float) (p171_0.m11 * adouble1[1]);
        this.m12 = (float) (p171_0.m12 * adouble1[2]);
        this.m20 = (float) (p171_0.m20 * adouble1[0]);
        this.m21 = (float) (p171_0.m21 * adouble1[1]);
        this.m22 = (float) (p171_0.m22 * adouble1[2]);
    }

    public final void method_99c5f278139f348aa1fa(
            Matrix4f matrix4f,
            Matrix4f matrix4fValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix4f && this != matrix4fValue) {
            this.m00 =
                    matrix4f.m00 * matrix4fValue.m00
                            + matrix4f.m10 * matrix4fValue.m01
                            + matrix4f.m20 * matrix4fValue.m02
                            + matrix4f.m30 * matrix4fValue.m03;
            this.m01 =
                    matrix4f.m00 * matrix4fValue.m10
                            + matrix4f.m10 * matrix4fValue.m11
                            + matrix4f.m20 * matrix4fValue.m12
                            + matrix4f.m30 * matrix4fValue.m13;
            this.m02 =
                    matrix4f.m00 * matrix4fValue.m20
                            + matrix4f.m10 * matrix4fValue.m21
                            + matrix4f.m20 * matrix4fValue.m22
                            + matrix4f.m30 * matrix4fValue.m23;
            this.m03 =
                    matrix4f.m00 * matrix4fValue.m30
                            + matrix4f.m10 * matrix4fValue.m31
                            + matrix4f.m20 * matrix4fValue.m32
                            + matrix4f.m30 * matrix4fValue.m33;
            this.m10 =
                    matrix4f.m01 * matrix4fValue.m00
                            + matrix4f.m11 * matrix4fValue.m01
                            + matrix4f.m21 * matrix4fValue.m02
                            + matrix4f.m31 * matrix4fValue.m03;
            this.m11 =
                    matrix4f.m01 * matrix4fValue.m10
                            + matrix4f.m11 * matrix4fValue.m11
                            + matrix4f.m21 * matrix4fValue.m12
                            + matrix4f.m31 * matrix4fValue.m13;
            this.m12 =
                    matrix4f.m01 * matrix4fValue.m20
                            + matrix4f.m11 * matrix4fValue.m21
                            + matrix4f.m21 * matrix4fValue.m22
                            + matrix4f.m31 * matrix4fValue.m23;
            this.m13 =
                    matrix4f.m01 * matrix4fValue.m30
                            + matrix4f.m11 * matrix4fValue.m31
                            + matrix4f.m21 * matrix4fValue.m32
                            + matrix4f.m31 * matrix4fValue.m33;
            this.m20 =
                    matrix4f.m02 * matrix4fValue.m00
                            + matrix4f.m12 * matrix4fValue.m01
                            + matrix4f.m22 * matrix4fValue.m02
                            + matrix4f.m32 * matrix4fValue.m03;
            this.m21 =
                    matrix4f.m02 * matrix4fValue.m10
                            + matrix4f.m12 * matrix4fValue.m11
                            + matrix4f.m22 * matrix4fValue.m12
                            + matrix4f.m32 * matrix4fValue.m13;
            this.m22 =
                    matrix4f.m02 * matrix4fValue.m20
                            + matrix4f.m12 * matrix4fValue.m21
                            + matrix4f.m22 * matrix4fValue.m22
                            + matrix4f.m32 * matrix4fValue.m23;
            this.m23 =
                    matrix4f.m02 * matrix4fValue.m30
                            + matrix4f.m12 * matrix4fValue.m31
                            + matrix4f.m22 * matrix4fValue.m32
                            + matrix4f.m32 * matrix4fValue.m33;
            this.m30 =
                    matrix4f.m03 * matrix4fValue.m00
                            + matrix4f.m13 * matrix4fValue.m01
                            + matrix4f.m23 * matrix4fValue.m02
                            + matrix4f.m33 * matrix4fValue.m03;
            this.m31 =
                    matrix4f.m03 * matrix4fValue.m10
                            + matrix4f.m13 * matrix4fValue.m11
                            + matrix4f.m23 * matrix4fValue.m12
                            + matrix4f.m33 * matrix4fValue.m13;
            this.m32 =
                    matrix4f.m03 * matrix4fValue.m20
                            + matrix4f.m13 * matrix4fValue.m21
                            + matrix4f.m23 * matrix4fValue.m22
                            + matrix4f.m33 * matrix4fValue.m23;
            this.m33 =
                    matrix4f.m03 * matrix4fValue.m30
                            + matrix4f.m13 * matrix4fValue.m31
                            + matrix4f.m23 * matrix4fValue.m32
                            + matrix4f.m33 * matrix4fValue.m33;
            if (astring == null) {
                return;
            }
        }

        float f =
                matrix4f.m00 * matrix4fValue.m00
                        + matrix4f.m10 * matrix4fValue.m01
                        + matrix4f.m20 * matrix4fValue.m02
                        + matrix4f.m30 * matrix4fValue.m03;
        float f1 =
                matrix4f.m00 * matrix4fValue.m10
                        + matrix4f.m10 * matrix4fValue.m11
                        + matrix4f.m20 * matrix4fValue.m12
                        + matrix4f.m30 * matrix4fValue.m13;
        float f2 =
                matrix4f.m00 * matrix4fValue.m20
                        + matrix4f.m10 * matrix4fValue.m21
                        + matrix4f.m20 * matrix4fValue.m22
                        + matrix4f.m30 * matrix4fValue.m23;
        float f3 =
                matrix4f.m00 * matrix4fValue.m30
                        + matrix4f.m10 * matrix4fValue.m31
                        + matrix4f.m20 * matrix4fValue.m32
                        + matrix4f.m30 * matrix4fValue.m33;
        float f4 =
                matrix4f.m01 * matrix4fValue.m00
                        + matrix4f.m11 * matrix4fValue.m01
                        + matrix4f.m21 * matrix4fValue.m02
                        + matrix4f.m31 * matrix4fValue.m03;
        float f5 =
                matrix4f.m01 * matrix4fValue.m10
                        + matrix4f.m11 * matrix4fValue.m11
                        + matrix4f.m21 * matrix4fValue.m12
                        + matrix4f.m31 * matrix4fValue.m13;
        float f6 =
                matrix4f.m01 * matrix4fValue.m20
                        + matrix4f.m11 * matrix4fValue.m21
                        + matrix4f.m21 * matrix4fValue.m22
                        + matrix4f.m31 * matrix4fValue.m23;
        float f7 =
                matrix4f.m01 * matrix4fValue.m30
                        + matrix4f.m11 * matrix4fValue.m31
                        + matrix4f.m21 * matrix4fValue.m32
                        + matrix4f.m31 * matrix4fValue.m33;
        float f8 =
                matrix4f.m02 * matrix4fValue.m00
                        + matrix4f.m12 * matrix4fValue.m01
                        + matrix4f.m22 * matrix4fValue.m02
                        + matrix4f.m32 * matrix4fValue.m03;
        float f9 =
                matrix4f.m02 * matrix4fValue.m10
                        + matrix4f.m12 * matrix4fValue.m11
                        + matrix4f.m22 * matrix4fValue.m12
                        + matrix4f.m32 * matrix4fValue.m13;
        float f10 =
                matrix4f.m02 * matrix4fValue.m20
                        + matrix4f.m12 * matrix4fValue.m21
                        + matrix4f.m22 * matrix4fValue.m22
                        + matrix4f.m32 * matrix4fValue.m23;
        float f11 =
                matrix4f.m02 * matrix4fValue.m30
                        + matrix4f.m12 * matrix4fValue.m31
                        + matrix4f.m22 * matrix4fValue.m32
                        + matrix4f.m32 * matrix4fValue.m33;
        float f12 =
                matrix4f.m03 * matrix4fValue.m00
                        + matrix4f.m13 * matrix4fValue.m01
                        + matrix4f.m23 * matrix4fValue.m02
                        + matrix4f.m33 * matrix4fValue.m03;
        float f13 =
                matrix4f.m03 * matrix4fValue.m10
                        + matrix4f.m13 * matrix4fValue.m11
                        + matrix4f.m23 * matrix4fValue.m12
                        + matrix4f.m33 * matrix4fValue.m13;
        float f14 =
                matrix4f.m03 * matrix4fValue.m20
                        + matrix4f.m13 * matrix4fValue.m21
                        + matrix4f.m23 * matrix4fValue.m22
                        + matrix4f.m33 * matrix4fValue.m23;
        float f15 =
                matrix4f.m03 * matrix4fValue.m30
                        + matrix4f.m13 * matrix4fValue.m31
                        + matrix4f.m23 * matrix4fValue.m32
                        + matrix4f.m33 * matrix4fValue.m33;
        this.m00 = f;
        this.m01 = f1;
        this.m02 = f2;
        this.m03 = f3;
        this.m10 = f4;
        this.m11 = f5;
        this.m12 = f6;
        this.m13 = f7;
        this.m20 = f8;
        this.m21 = f9;
        this.m22 = f10;
        this.m23 = f11;
        this.m30 = f12;
        this.m31 = f13;
        this.m32 = f14;
        this.m33 = f15;
    }

    public final float getM21() {
        return this.m21;
    }

    public final void method_b04ce70e1737c12f2fa6(Matrix4f p171_0) {
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

    public final void method_fab7bfa34345b20e6f51(int i, float[] afloat) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            afloat[0] = this.m00;
            afloat[1] = this.m10;
            afloat[2] = this.m20;
            afloat[3] = this.m30;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            afloat[0] = this.m01;
            afloat[1] = this.m11;
            afloat[2] = this.m21;
            afloat[3] = this.m31;
            if (astring == null) {
                return;
            }
        }

        if (i == 2) {
            afloat[0] = this.m02;
            afloat[1] = this.m12;
            afloat[2] = this.m22;
            afloat[3] = this.m32;
            if (astring == null) {
                return;
            }
        }

        if (i != 3) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
        }

        afloat[0] = this.m03;
        afloat[1] = this.m13;
        afloat[2] = this.m23;
        afloat[3] = this.m33;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
        }
    }

    public final void getRotationScale(
            Matrix3f matrix3f) {
        matrix3f.m00 = this.m00;
        matrix3f.m01 = this.m01;
        matrix3f.m02 = this.m02;
        matrix3f.m10 = this.m10;
        matrix3f.m11 = this.m11;
        matrix3f.m12 = this.m12;
        matrix3f.m20 = this.m20;
        matrix3f.m21 = this.m21;
        matrix3f.m22 = this.m22;
    }

    public final void method_ecadaa21f568eb9126de(
            int i, Vector4f vector4f) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = vector4f.x;
                this.m10 = vector4f.y;
                this.m20 = vector4f.z;
                this.m30 = vector4f.w;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m01 = vector4f.x;
                this.m11 = vector4f.y;
                this.m21 = vector4f.z;
                this.m31 = vector4f.w;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m02 = vector4f.x;
                this.m12 = vector4f.y;
                this.m22 = vector4f.z;
                this.m32 = vector4f.w;
                if (astring == null) {
                    break;
                }
            case 3:
                this.m03 = vector4f.x;
                this.m13 = vector4f.y;
                this.m23 = vector4f.z;
                this.m33 = vector4f.w;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix4f9"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
        }
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

    public final void rotY(float p171_0) {
        float f1 = (float) java.lang.Math.sin(p171_0);
        float f2 = (float) java.lang.Math.cos(p171_0);
        this.m00 = f2;
        this.m01 = 0.0F;
        this.m02 = f1;
        this.m03 = 0.0F;
        this.m10 = 0.0F;
        this.m11 = 1.0F;
        this.m12 = 0.0F;
        this.m13 = 0.0F;
        this.m20 = -f1;
        this.m21 = 0.0F;
        this.m22 = f2;
        this.m23 = 0.0F;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final void transpose() {
        float f = this.m10;
        this.m10 = this.m01;
        this.m01 = f;
        f = this.m20;
        this.m20 = this.m02;
        this.m02 = f;
        f = this.m30;
        this.m30 = this.m03;
        this.m03 = f;
        f = this.m21;
        this.m21 = this.m12;
        this.m12 = f;
        f = this.m31;
        this.m31 = this.m13;
        this.m13 = f;
        f = this.m32;
        this.m32 = this.m23;
        this.m23 = f;
    }

    public Matrix4f(
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
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    // region String decoding support

    // endregion

    public final void method_367bd04bb9dd49b50ef1(int i, float f, float f1, float f2, float f3) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = f;
                this.m01 = f1;
                this.m02 = f2;
                this.m03 = f3;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m10 = f;
                this.m11 = f1;
                this.m12 = f2;
                this.m13 = f3;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m20 = f;
                this.m21 = f1;
                this.m22 = f2;
                this.m23 = f3;
                if (astring == null) {
                    break;
                }
            case 3:
                this.m30 = f;
                this.m31 = f1;
                this.m32 = f2;
                this.m33 = f3;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix4f6"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
        }
    }

    public final float getM02() {
        return this.m02;
    }

    private static RuntimeException wrapException(RuntimeException runtimeexception) {
        return runtimeexception;
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

    static void method_05badd4d3eb80f51c381(double[] adouble, int[] aint, double[] adouble1) {
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

    public final void method_f6ed0d860bd4d42b0479(
            Matrix4f matrix4f,
            Matrix4f matrix4fValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix4f && this != matrix4fValue) {
            this.m00 =
                    matrix4f.m00 * matrix4fValue.m00
                            + matrix4f.m10 * matrix4fValue.m10
                            + matrix4f.m20 * matrix4fValue.m20
                            + matrix4f.m30 * matrix4fValue.m30;
            this.m01 =
                    matrix4f.m00 * matrix4fValue.m01
                            + matrix4f.m10 * matrix4fValue.m11
                            + matrix4f.m20 * matrix4fValue.m21
                            + matrix4f.m30 * matrix4fValue.m31;
            this.m02 =
                    matrix4f.m00 * matrix4fValue.m02
                            + matrix4f.m10 * matrix4fValue.m12
                            + matrix4f.m20 * matrix4fValue.m22
                            + matrix4f.m30 * matrix4fValue.m32;
            this.m03 =
                    matrix4f.m00 * matrix4fValue.m03
                            + matrix4f.m10 * matrix4fValue.m13
                            + matrix4f.m20 * matrix4fValue.m23
                            + matrix4f.m30 * matrix4fValue.m33;
            this.m10 =
                    matrix4f.m01 * matrix4fValue.m00
                            + matrix4f.m11 * matrix4fValue.m10
                            + matrix4f.m21 * matrix4fValue.m20
                            + matrix4f.m31 * matrix4fValue.m30;
            this.m11 =
                    matrix4f.m01 * matrix4fValue.m01
                            + matrix4f.m11 * matrix4fValue.m11
                            + matrix4f.m21 * matrix4fValue.m21
                            + matrix4f.m31 * matrix4fValue.m31;
            this.m12 =
                    matrix4f.m01 * matrix4fValue.m02
                            + matrix4f.m11 * matrix4fValue.m12
                            + matrix4f.m21 * matrix4fValue.m22
                            + matrix4f.m31 * matrix4fValue.m32;
            this.m13 =
                    matrix4f.m01 * matrix4fValue.m03
                            + matrix4f.m11 * matrix4fValue.m13
                            + matrix4f.m21 * matrix4fValue.m23
                            + matrix4f.m31 * matrix4fValue.m33;
            this.m20 =
                    matrix4f.m02 * matrix4fValue.m00
                            + matrix4f.m12 * matrix4fValue.m10
                            + matrix4f.m22 * matrix4fValue.m20
                            + matrix4f.m32 * matrix4fValue.m30;
            this.m21 =
                    matrix4f.m02 * matrix4fValue.m01
                            + matrix4f.m12 * matrix4fValue.m11
                            + matrix4f.m22 * matrix4fValue.m21
                            + matrix4f.m32 * matrix4fValue.m31;
            this.m22 =
                    matrix4f.m02 * matrix4fValue.m02
                            + matrix4f.m12 * matrix4fValue.m12
                            + matrix4f.m22 * matrix4fValue.m22
                            + matrix4f.m32 * matrix4fValue.m32;
            this.m23 =
                    matrix4f.m02 * matrix4fValue.m03
                            + matrix4f.m12 * matrix4fValue.m13
                            + matrix4f.m22 * matrix4fValue.m23
                            + matrix4f.m32 * matrix4fValue.m33;
            this.m30 =
                    matrix4f.m03 * matrix4fValue.m00
                            + matrix4f.m13 * matrix4fValue.m10
                            + matrix4f.m23 * matrix4fValue.m20
                            + matrix4f.m33 * matrix4fValue.m30;
            this.m31 =
                    matrix4f.m03 * matrix4fValue.m01
                            + matrix4f.m13 * matrix4fValue.m11
                            + matrix4f.m23 * matrix4fValue.m21
                            + matrix4f.m33 * matrix4fValue.m31;
            this.m32 =
                    matrix4f.m03 * matrix4fValue.m02
                            + matrix4f.m13 * matrix4fValue.m12
                            + matrix4f.m23 * matrix4fValue.m22
                            + matrix4f.m33 * matrix4fValue.m32;
            this.m33 =
                    matrix4f.m03 * matrix4fValue.m03
                            + matrix4f.m13 * matrix4fValue.m13
                            + matrix4f.m23 * matrix4fValue.m23
                            + matrix4f.m33 * matrix4fValue.m33;
            if (astring == null) {
                return;
            }
        }

        float f =
                matrix4f.m00 * matrix4fValue.m00
                        + matrix4f.m10 * matrix4fValue.m10
                        + matrix4f.m20 * matrix4fValue.m20
                        + matrix4f.m30 * matrix4fValue.m30;
        float f1 =
                matrix4f.m00 * matrix4fValue.m01
                        + matrix4f.m10 * matrix4fValue.m11
                        + matrix4f.m20 * matrix4fValue.m21
                        + matrix4f.m30 * matrix4fValue.m31;
        float f2 =
                matrix4f.m00 * matrix4fValue.m02
                        + matrix4f.m10 * matrix4fValue.m12
                        + matrix4f.m20 * matrix4fValue.m22
                        + matrix4f.m30 * matrix4fValue.m32;
        float f3 =
                matrix4f.m00 * matrix4fValue.m03
                        + matrix4f.m10 * matrix4fValue.m13
                        + matrix4f.m20 * matrix4fValue.m23
                        + matrix4f.m30 * matrix4fValue.m33;
        float f4 =
                matrix4f.m01 * matrix4fValue.m00
                        + matrix4f.m11 * matrix4fValue.m10
                        + matrix4f.m21 * matrix4fValue.m20
                        + matrix4f.m31 * matrix4fValue.m30;
        float f5 =
                matrix4f.m01 * matrix4fValue.m01
                        + matrix4f.m11 * matrix4fValue.m11
                        + matrix4f.m21 * matrix4fValue.m21
                        + matrix4f.m31 * matrix4fValue.m31;
        float f6 =
                matrix4f.m01 * matrix4fValue.m02
                        + matrix4f.m11 * matrix4fValue.m12
                        + matrix4f.m21 * matrix4fValue.m22
                        + matrix4f.m31 * matrix4fValue.m32;
        float f7 =
                matrix4f.m01 * matrix4fValue.m03
                        + matrix4f.m11 * matrix4fValue.m13
                        + matrix4f.m21 * matrix4fValue.m23
                        + matrix4f.m31 * matrix4fValue.m33;
        float f8 =
                matrix4f.m02 * matrix4fValue.m00
                        + matrix4f.m12 * matrix4fValue.m10
                        + matrix4f.m22 * matrix4fValue.m20
                        + matrix4f.m32 * matrix4fValue.m30;
        float f9 =
                matrix4f.m02 * matrix4fValue.m01
                        + matrix4f.m12 * matrix4fValue.m11
                        + matrix4f.m22 * matrix4fValue.m21
                        + matrix4f.m32 * matrix4fValue.m31;
        float f10 =
                matrix4f.m02 * matrix4fValue.m02
                        + matrix4f.m12 * matrix4fValue.m12
                        + matrix4f.m22 * matrix4fValue.m22
                        + matrix4f.m32 * matrix4fValue.m32;
        float f11 =
                matrix4f.m02 * matrix4fValue.m03
                        + matrix4f.m12 * matrix4fValue.m13
                        + matrix4f.m22 * matrix4fValue.m23
                        + matrix4f.m32 * matrix4fValue.m33;
        float f12 =
                matrix4f.m03 * matrix4fValue.m00
                        + matrix4f.m13 * matrix4fValue.m10
                        + matrix4f.m23 * matrix4fValue.m20
                        + matrix4f.m33 * matrix4fValue.m30;
        float f13 =
                matrix4f.m03 * matrix4fValue.m01
                        + matrix4f.m13 * matrix4fValue.m11
                        + matrix4f.m23 * matrix4fValue.m21
                        + matrix4f.m33 * matrix4fValue.m31;
        float f14 =
                matrix4f.m03 * matrix4fValue.m02
                        + matrix4f.m13 * matrix4fValue.m12
                        + matrix4f.m23 * matrix4fValue.m22
                        + matrix4f.m33 * matrix4fValue.m32;
        float f15 =
                matrix4f.m03 * matrix4fValue.m03
                        + matrix4f.m13 * matrix4fValue.m13
                        + matrix4f.m23 * matrix4fValue.m23
                        + matrix4f.m33 * matrix4fValue.m33;
        this.m00 = f;
        this.m01 = f1;
        this.m02 = f2;
        this.m03 = f3;
        this.m10 = f4;
        this.m11 = f5;
        this.m12 = f6;
        this.m13 = f7;
        this.m20 = f8;
        this.m21 = f9;
        this.m22 = f10;
        this.m23 = f11;
        this.m30 = f12;
        this.m31 = f13;
        this.m32 = f14;
        this.m33 = f15;
    }

    public final void multiplyFrom(
            Matrix4f matrix4f,
            Matrix4f matrix4fValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix4f && this != matrix4fValue) {
            this.m00 =
                    matrix4f.m00 * matrix4fValue.m00
                            + matrix4f.m01 * matrix4fValue.m10
                            + matrix4f.m02 * matrix4fValue.m20
                            + matrix4f.m03 * matrix4fValue.m30;
            this.m01 =
                    matrix4f.m00 * matrix4fValue.m01
                            + matrix4f.m01 * matrix4fValue.m11
                            + matrix4f.m02 * matrix4fValue.m21
                            + matrix4f.m03 * matrix4fValue.m31;
            this.m02 =
                    matrix4f.m00 * matrix4fValue.m02
                            + matrix4f.m01 * matrix4fValue.m12
                            + matrix4f.m02 * matrix4fValue.m22
                            + matrix4f.m03 * matrix4fValue.m32;
            this.m03 =
                    matrix4f.m00 * matrix4fValue.m03
                            + matrix4f.m01 * matrix4fValue.m13
                            + matrix4f.m02 * matrix4fValue.m23
                            + matrix4f.m03 * matrix4fValue.m33;
            this.m10 =
                    matrix4f.m10 * matrix4fValue.m00
                            + matrix4f.m11 * matrix4fValue.m10
                            + matrix4f.m12 * matrix4fValue.m20
                            + matrix4f.m13 * matrix4fValue.m30;
            this.m11 =
                    matrix4f.m10 * matrix4fValue.m01
                            + matrix4f.m11 * matrix4fValue.m11
                            + matrix4f.m12 * matrix4fValue.m21
                            + matrix4f.m13 * matrix4fValue.m31;
            this.m12 =
                    matrix4f.m10 * matrix4fValue.m02
                            + matrix4f.m11 * matrix4fValue.m12
                            + matrix4f.m12 * matrix4fValue.m22
                            + matrix4f.m13 * matrix4fValue.m32;
            this.m13 =
                    matrix4f.m10 * matrix4fValue.m03
                            + matrix4f.m11 * matrix4fValue.m13
                            + matrix4f.m12 * matrix4fValue.m23
                            + matrix4f.m13 * matrix4fValue.m33;
            this.m20 =
                    matrix4f.m20 * matrix4fValue.m00
                            + matrix4f.m21 * matrix4fValue.m10
                            + matrix4f.m22 * matrix4fValue.m20
                            + matrix4f.m23 * matrix4fValue.m30;
            this.m21 =
                    matrix4f.m20 * matrix4fValue.m01
                            + matrix4f.m21 * matrix4fValue.m11
                            + matrix4f.m22 * matrix4fValue.m21
                            + matrix4f.m23 * matrix4fValue.m31;
            this.m22 =
                    matrix4f.m20 * matrix4fValue.m02
                            + matrix4f.m21 * matrix4fValue.m12
                            + matrix4f.m22 * matrix4fValue.m22
                            + matrix4f.m23 * matrix4fValue.m32;
            this.m23 =
                    matrix4f.m20 * matrix4fValue.m03
                            + matrix4f.m21 * matrix4fValue.m13
                            + matrix4f.m22 * matrix4fValue.m23
                            + matrix4f.m23 * matrix4fValue.m33;
            this.m30 =
                    matrix4f.m30 * matrix4fValue.m00
                            + matrix4f.m31 * matrix4fValue.m10
                            + matrix4f.m32 * matrix4fValue.m20
                            + matrix4f.m33 * matrix4fValue.m30;
            this.m31 =
                    matrix4f.m30 * matrix4fValue.m01
                            + matrix4f.m31 * matrix4fValue.m11
                            + matrix4f.m32 * matrix4fValue.m21
                            + matrix4f.m33 * matrix4fValue.m31;
            this.m32 =
                    matrix4f.m30 * matrix4fValue.m02
                            + matrix4f.m31 * matrix4fValue.m12
                            + matrix4f.m32 * matrix4fValue.m22
                            + matrix4f.m33 * matrix4fValue.m32;
            this.m33 =
                    matrix4f.m30 * matrix4fValue.m03
                            + matrix4f.m31 * matrix4fValue.m13
                            + matrix4f.m32 * matrix4fValue.m23
                            + matrix4f.m33 * matrix4fValue.m33;
            if (astring == null) {
                return;
            }
        }

        float f =
                matrix4f.m00 * matrix4fValue.m00
                        + matrix4f.m01 * matrix4fValue.m10
                        + matrix4f.m02 * matrix4fValue.m20
                        + matrix4f.m03 * matrix4fValue.m30;
        float f1 =
                matrix4f.m00 * matrix4fValue.m01
                        + matrix4f.m01 * matrix4fValue.m11
                        + matrix4f.m02 * matrix4fValue.m21
                        + matrix4f.m03 * matrix4fValue.m31;
        float f2 =
                matrix4f.m00 * matrix4fValue.m02
                        + matrix4f.m01 * matrix4fValue.m12
                        + matrix4f.m02 * matrix4fValue.m22
                        + matrix4f.m03 * matrix4fValue.m32;
        float f3 =
                matrix4f.m00 * matrix4fValue.m03
                        + matrix4f.m01 * matrix4fValue.m13
                        + matrix4f.m02 * matrix4fValue.m23
                        + matrix4f.m03 * matrix4fValue.m33;
        float f4 =
                matrix4f.m10 * matrix4fValue.m00
                        + matrix4f.m11 * matrix4fValue.m10
                        + matrix4f.m12 * matrix4fValue.m20
                        + matrix4f.m13 * matrix4fValue.m30;
        float f5 =
                matrix4f.m10 * matrix4fValue.m01
                        + matrix4f.m11 * matrix4fValue.m11
                        + matrix4f.m12 * matrix4fValue.m21
                        + matrix4f.m13 * matrix4fValue.m31;
        float f6 =
                matrix4f.m10 * matrix4fValue.m02
                        + matrix4f.m11 * matrix4fValue.m12
                        + matrix4f.m12 * matrix4fValue.m22
                        + matrix4f.m13 * matrix4fValue.m32;
        float f7 =
                matrix4f.m10 * matrix4fValue.m03
                        + matrix4f.m11 * matrix4fValue.m13
                        + matrix4f.m12 * matrix4fValue.m23
                        + matrix4f.m13 * matrix4fValue.m33;
        float f8 =
                matrix4f.m20 * matrix4fValue.m00
                        + matrix4f.m21 * matrix4fValue.m10
                        + matrix4f.m22 * matrix4fValue.m20
                        + matrix4f.m23 * matrix4fValue.m30;
        float f9 =
                matrix4f.m20 * matrix4fValue.m01
                        + matrix4f.m21 * matrix4fValue.m11
                        + matrix4f.m22 * matrix4fValue.m21
                        + matrix4f.m23 * matrix4fValue.m31;
        float f10 =
                matrix4f.m20 * matrix4fValue.m02
                        + matrix4f.m21 * matrix4fValue.m12
                        + matrix4f.m22 * matrix4fValue.m22
                        + matrix4f.m23 * matrix4fValue.m32;
        float f11 =
                matrix4f.m20 * matrix4fValue.m03
                        + matrix4f.m21 * matrix4fValue.m13
                        + matrix4f.m22 * matrix4fValue.m23
                        + matrix4f.m23 * matrix4fValue.m33;
        float f12 =
                matrix4f.m30 * matrix4fValue.m00
                        + matrix4f.m31 * matrix4fValue.m10
                        + matrix4f.m32 * matrix4fValue.m20
                        + matrix4f.m33 * matrix4fValue.m30;
        float f13 =
                matrix4f.m30 * matrix4fValue.m01
                        + matrix4f.m31 * matrix4fValue.m11
                        + matrix4f.m32 * matrix4fValue.m21
                        + matrix4f.m33 * matrix4fValue.m31;
        float f14 =
                matrix4f.m30 * matrix4fValue.m02
                        + matrix4f.m31 * matrix4fValue.m12
                        + matrix4f.m32 * matrix4fValue.m22
                        + matrix4f.m33 * matrix4fValue.m32;
        float f15 =
                matrix4f.m30 * matrix4fValue.m03
                        + matrix4f.m31 * matrix4fValue.m13
                        + matrix4f.m32 * matrix4fValue.m23
                        + matrix4f.m33 * matrix4fValue.m33;
        this.m00 = f;
        this.m01 = f1;
        this.m02 = f2;
        this.m03 = f3;
        this.m10 = f4;
        this.m11 = f5;
        this.m12 = f6;
        this.m13 = f7;
        this.m20 = f8;
        this.m21 = f9;
        this.m22 = f10;
        this.m23 = f11;
        this.m30 = f12;
        this.m31 = f13;
        this.m32 = f14;
        this.m33 = f15;
    }

    public final void add(
            Matrix4f p171_0, Matrix4f p171_1) {
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

    public final float getM22() {
        return this.m22;
    }

    public final float getM11() {
        return this.m11;
    }

    public final void setTranslation(
            Vector3f vector3f) {
        this.m03 = vector3f.x;
        this.m13 = vector3f.y;
        this.m23 = vector3f.z;
    }

    public final void setM03(float f) {
        this.m03 = f;
    }

    public final void setZero() {
        this.m00 = 0.0F;
        this.m01 = 0.0F;
        this.m02 = 0.0F;
        this.m03 = 0.0F;
        this.m10 = 0.0F;
        this.m11 = 0.0F;
        this.m12 = 0.0F;
        this.m13 = 0.0F;
        this.m20 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = 0.0F;
        this.m23 = 0.0F;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 0.0F;
    }

    public boolean equals(Matrix4f p171_0) {
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

    public final void mul(float p171_0) {
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

    private final void getScaleRotate(double[] adouble, double[] adouble1) {
        double[] adouble2 =
                new double[] {
                    this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22
                };
        Matrix3d.compute_svd(adouble2, adouble, adouble1);
    }

    @Override
    public int hashCode() {

        long i = 1L;
        i = VecMathUtil.hashFloatToIntBits(i, this.m00);
        i = VecMathUtil.hashFloatToIntBits(i, this.m01);
        i = VecMathUtil.hashFloatToIntBits(i, this.m02);
        i = VecMathUtil.hashFloatToIntBits(i, this.m03);
        i = VecMathUtil.hashFloatToIntBits(i, this.m10);
        i = VecMathUtil.hashFloatToIntBits(i, this.m11);
        i = VecMathUtil.hashFloatToIntBits(i, this.m12);
        i = VecMathUtil.hashFloatToIntBits(i, this.m13);
        i = VecMathUtil.hashFloatToIntBits(i, this.m20);
        i = VecMathUtil.hashFloatToIntBits(i, this.m21);
        i = VecMathUtil.hashFloatToIntBits(i, this.m22);
        i = VecMathUtil.hashFloatToIntBits(i, this.m23);
        i = VecMathUtil.hashFloatToIntBits(i, this.m30);
        i = VecMathUtil.hashFloatToIntBits(i, this.m31);
        i = VecMathUtil.hashFloatToIntBits(i, this.m32);
        i = VecMathUtil.hashFloatToIntBits(i, this.m33);
        return VecMathUtil.hashFinish(i);
    }

    public final void negate(Matrix4f p171_0) {
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

    public final void setM01(float f) {
        this.m01 = f;
    }

    public final float getM23() {
        return this.m23;
    }

    public final void transform(Tuple4f p171_0) {
        float f =
                this.m00 * p171_0.x
                        + this.m01 * p171_0.y
                        + this.m02 * p171_0.z
                        + this.m03 * p171_0.w;
        float f1 =
                this.m10 * p171_0.x
                        + this.m11 * p171_0.y
                        + this.m12 * p171_0.z
                        + this.m13 * p171_0.w;
        float f2 =
                this.m20 * p171_0.x
                        + this.m21 * p171_0.y
                        + this.m22 * p171_0.z
                        + this.m23 * p171_0.w;
        p171_0.w =
                this.m30 * p171_0.x
                        + this.m31 * p171_0.y
                        + this.m32 * p171_0.z
                        + this.m33 * p171_0.w;
        p171_0.x = f;
        p171_0.y = f1;
        p171_0.z = f2;
    }

    public final float getM10() {
        return this.m10;
    }

    public final void rotX(float p171_0) {
        float f1 = (float) java.lang.Math.sin(p171_0);
        float f2 = (float) java.lang.Math.cos(p171_0);
        this.m00 = 1.0F;
        this.m01 = 0.0F;
        this.m02 = 0.0F;
        this.m03 = 0.0F;
        this.m10 = 0.0F;
        this.m11 = f2;
        this.m12 = -f1;
        this.m13 = 0.0F;
        this.m20 = 0.0F;
        this.m21 = f1;
        this.m22 = f2;
        this.m23 = 0.0F;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
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
        this.m03 = 0.0F;
        this.m13 = 0.0F;
        this.m23 = 0.0F;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final void transform(
            Point3f p171_0, Point3f p171_1) {
        float f = this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z + this.m03;
        float f1 = this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z + this.m13;
        p171_1.z = this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z + this.m23;
        p171_1.x = f;
        p171_1.y = f1;
    }

    public final void method_646d5c84c8596b9bff9c(int i, float f, float f1, float f2, float f3) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = f;
                this.m10 = f1;
                this.m20 = f2;
                this.m30 = f3;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m01 = f;
                this.m11 = f1;
                this.m21 = f2;
                this.m31 = f3;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m02 = f;
                this.m12 = f1;
                this.m22 = f2;
                this.m32 = f3;
                if (astring == null) {
                    break;
                }
            case 3:
                this.m03 = f;
                this.m13 = f1;
                this.m23 = f2;
                this.m33 = f3;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix4f9"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
        }
    }

    public final void set(Matrix3f p171_0) {
        this.m00 = p171_0.m00;
        this.m01 = p171_0.m01;
        this.m02 = p171_0.m02;
        this.m03 = 0.0F;
        this.m10 = p171_0.m10;
        this.m11 = p171_0.m11;
        this.m12 = p171_0.m12;
        this.m13 = 0.0F;
        this.m20 = p171_0.m20;
        this.m21 = p171_0.m21;
        this.m22 = p171_0.m22;
        this.m23 = 0.0F;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final void setM10(float f) {
        this.m10 = f;
    }

    public final void method_993cb9aa4e4c9904460d(Matrix4f p171_0) {
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

    static boolean method_46f13198e89824201e6a(double[] adouble, int[] aint) {
        String[] astring1 = Tuple3f.hF();
        double[] adouble1 = new double[4];
        int k = 0;
        String[] astring = astring1;
        int l = 0;
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
                throw new RuntimeException(VecMathI18N.getString("Matrix4f13"));
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

    public Matrix4f(
            Quat4f p171_0, Vector3f p171_1, float p171_2) {
        this.m00 =
                (float)
                        (p171_2
                                * (1.0
                                        - 2.0 * p171_0.y * p171_0.y
                                        - 2.0 * p171_0.z * p171_0.z));
        this.m10 = (float) (p171_2 * (2.0 * (p171_0.x * p171_0.y + p171_0.w * p171_0.z)));
        this.m20 = (float) (p171_2 * (2.0 * (p171_0.x * p171_0.z - p171_0.w * p171_0.y)));
        this.m01 = (float) (p171_2 * (2.0 * (p171_0.x * p171_0.y - p171_0.w * p171_0.z)));
        this.m11 =
                (float)
                        (p171_2
                                * (1.0
                                        - 2.0 * p171_0.x * p171_0.x
                                        - 2.0 * p171_0.z * p171_0.z));
        this.m21 = (float) (p171_2 * (2.0 * (p171_0.y * p171_0.z + p171_0.w * p171_0.x)));
        this.m02 = (float) (p171_2 * (2.0 * (p171_0.x * p171_0.z + p171_0.w * p171_0.y)));
        this.m12 = (float) (p171_2 * (2.0 * (p171_0.y * p171_0.z - p171_0.w * p171_0.x)));
        this.m22 =
                (float)
                        (p171_2
                                * (1.0
                                        - 2.0 * p171_0.x * p171_0.x
                                        - 2.0 * p171_0.y * p171_0.y));
        this.m03 = p171_1.x;
        this.m13 = p171_1.y;
        this.m23 = p171_1.z;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final void setM12(float f) {
        this.m12 = f;
    }

    public final void method_2d381a0d8c4203b3f28f(AxisAngle4f p171_0) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        float f =
                (float)
                        java.lang.Math.sqrt(
                                p171_0.x * p171_0.x
                                        + p171_0.y * p171_0.y
                                        + p171_0.z * p171_0.z);
        java.lang.String[] astring = astring1;

        label19:
        {
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
                        break label19;
                    }
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
                throw remix.compat.vecmath.Matrix4f.wrapException(
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

        this.m03 = 0.0F;
        this.m13 = 0.0F;
        this.m23 = 0.0F;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
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
                throw remix.compat.vecmath.Matrix4f.wrapException(
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
                    throw remix.compat.vecmath.Matrix4f.wrapException(
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
                        throw remix.compat.vecmath.Matrix4f.wrapException(
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
                    throw remix.compat.vecmath.Matrix4f.wrapException(
                            arrayindexoutofboundsexception);
                }

                p171_0.y = 0.0F;
                p171_0.z = 1.0F;
            }
        }
    }

    public final void set(float p171_0, Vector3f p171_1) {
        this.m00 = p171_0;
        this.m01 = 0.0F;
        this.m02 = 0.0F;
        this.m03 = p171_1.x;
        this.m10 = 0.0F;
        this.m11 = p171_0;
        this.m12 = 0.0F;
        this.m13 = p171_1.y;
        this.m20 = 0.0F;
        this.m21 = 0.0F;
        this.m22 = p171_0;
        this.m23 = p171_1.z;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final void setRotation(Quat4f p171_0) {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        this.m00 =
                (float)
                        ((1.0F - 2.0F * p171_0.y * p171_0.y - 2.0F * p171_0.z * p171_0.z)
                                * adouble1[0]);
        this.m10 = (float) (2.0F * (p171_0.x * p171_0.y + p171_0.w * p171_0.z) * adouble1[0]);
        this.m20 = (float) (2.0F * (p171_0.x * p171_0.z - p171_0.w * p171_0.y) * adouble1[0]);
        this.m01 = (float) (2.0F * (p171_0.x * p171_0.y - p171_0.w * p171_0.z) * adouble1[1]);
        this.m11 =
                (float)
                        ((1.0F - 2.0F * p171_0.x * p171_0.x - 2.0F * p171_0.z * p171_0.z)
                                * adouble1[1]);
        this.m21 = (float) (2.0F * (p171_0.y * p171_0.z + p171_0.w * p171_0.x) * adouble1[1]);
        this.m02 = (float) (2.0F * (p171_0.x * p171_0.z + p171_0.w * p171_0.y) * adouble1[2]);
        this.m12 = (float) (2.0F * (p171_0.y * p171_0.z - p171_0.w * p171_0.x) * adouble1[2]);
        this.m22 =
                (float)
                        ((1.0F - 2.0F * p171_0.x * p171_0.x - 2.0F * p171_0.y * p171_0.y)
                                * adouble1[2]);
    }

    public final float getScale() {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        return (float) remix.compat.vecmath.Matrix3d.max3(adouble1);
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
        this.m03 = 0.0F;
        this.m13 = 0.0F;
        this.m23 = 0.0F;
        this.m30 = 0.0F;
        this.m31 = 0.0F;
        this.m32 = 0.0F;
        this.m33 = 1.0F;
    }

    public final float getM33() {
        return this.m33;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Matrix4f(Matrix4f matrix4f) {
        super();
        this.m00 = matrix4f.m00;
        this.m01 = matrix4f.m01;
        this.m02 = matrix4f.m02;
        this.m03 = matrix4f.m03;
        this.m10 = matrix4f.m10;
        this.m11 = matrix4f.m11;
        this.m12 = matrix4f.m12;
        this.m13 = matrix4f.m13;
        this.m20 = matrix4f.m20;
        this.m21 = matrix4f.m21;
        this.m22 = matrix4f.m22;
        this.m23 = matrix4f.m23;
        this.m30 = matrix4f.m30;
        this.m31 = matrix4f.m31;
        this.m32 = matrix4f.m32;
        this.m33 = matrix4f.m33;
    }
}
