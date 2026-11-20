package remix.compat.vecmath;

import java.io.Serializable;

public class Quat4f extends Tuple4f implements Serializable {
    static final long serialVersionUID = 2675933778405442383L;
    static final double ix = 1.0E-30;
    static final double rd = 1.0E-6;
    static final double iz = 1.57079632679;

    public final void set(Matrix4f p171_0) {
        float f = 0.25F * (p171_0.m00 + p171_0.m11 + p171_0.m22 + p171_0.m33);
        if (f >= 0.0F) {
            if (f >= 1.0E-30) {
                this.w = (float) java.lang.Math.sqrt(f);
                f = 0.25F / this.w;
                this.x = (p171_0.m21 - p171_0.m12) * f;
                this.y = (p171_0.m02 - p171_0.m20) * f;
                this.z = (p171_0.m10 - p171_0.m01) * f;
            } else {
                this.w = 0.0F;
                f = -0.5F * (p171_0.m11 + p171_0.m22);
                if (f >= 0.0F) {
                    if (f >= 1.0E-30) {
                        this.x = (float) java.lang.Math.sqrt(f);
                        f = 1.0F / (2.0F * this.x);
                        this.y = p171_0.m10 * f;
                        this.z = p171_0.m20 * f;
                    } else {
                        this.x = 0.0F;
                        f = 0.5F * (1.0F - p171_0.m22);
                        if (f >= 1.0E-30) {
                            this.y = (float) java.lang.Math.sqrt(f);
                            this.z = p171_0.m21 / (2.0F * this.y);
                        } else {
                            this.y = 0.0F;
                            this.z = 1.0F;
                        }
                    }
                } else {
                    this.x = 0.0F;
                    this.y = 0.0F;
                    this.z = 1.0F;
                }
            }
        } else {
            this.w = 0.0F;
            this.x = 0.0F;
            this.y = 0.0F;
            this.z = 1.0F;
        }
    }

    public final void interpolate(
            Quat4f p171_0, Quat4f p171_1, float p171_2) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        double d0 =
                p171_1.x * p171_0.x
                        + p171_1.y * p171_0.y
                        + p171_1.z * p171_0.z
                        + p171_1.w * p171_0.w;
        java.lang.String[] astring = astring1;
        if (d0 < 0.0) {
            p171_0.x = -p171_0.x;
            p171_0.y = -p171_0.y;
            p171_0.z = -p171_0.z;
            p171_0.w = -p171_0.w;
            d0 = -d0;
        }

        double d1;
        double d2;
        {
            if (1.0 - d0 > 1.0E-6) {
                double d3 = java.lang.Math.acos(d0);
                double d4 = java.lang.Math.sin(d3);
                d1 = java.lang.Math.sin((1.0 - p171_2) * d3) / d4;
                d2 = java.lang.Math.sin(p171_2 * d3) / d4;
                if (!(astring == null)) {

                    d1 = 1.0 - p171_2;
                    d2 = p171_2;
                }
            } else {

                d1 = 1.0 - p171_2;
                d2 = p171_2;
            }
        }

        this.w = (float) (d1 * p171_0.w + d2 * p171_1.w);
        this.x = (float) (d1 * p171_0.x + d2 * p171_1.x);
        this.y = (float) (d1 * p171_0.y + d2 * p171_1.y);
        this.z = (float) (d1 * p171_0.z + d2 * p171_1.z);
    }

    public final void set(
            AxisAngle4d axisAngle4d) {
        String[] astring1 = Tuple3f.hF();
        float f1 =
                (float)
                        (1.0
                                / Math.sqrt(
                                        axisAngle4d.x
                                                        * axisAngle4d.x
                                                + axisAngle4d.y
                                                        * axisAngle4d.y
                                                + axisAngle4d.z
                                                        * axisAngle4d.z));
        String[] astring = astring1;
        if (f1 < 1.0E-6) {
            this.w = 0.0F;
            this.x = 0.0F;
            this.y = 0.0F;
            this.z = 0.0F;
            if (astring == null) {
                return;
            }
        }

        f1 = 1.0F / f1;
        float f = (float) Math.sin(axisAngle4d.angle / 2.0);
        this.w = (float) Math.cos(axisAngle4d.angle / 2.0);
        this.x = (float) axisAngle4d.x * f1 * f;
        this.y = (float) axisAngle4d.y * f1 * f;
        this.z = (float) axisAngle4d.z * f1 * f;
    }

    public final void set(AxisAngle4f p171_0) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        float f1 =
                (float)
                        java.lang.Math.sqrt(
                                p171_0.x * p171_0.x
                                        + p171_0.y * p171_0.y
                                        + p171_0.z * p171_0.z);
        java.lang.String[] astring = astring1;
        if (f1 < 1.0E-6) {
            this.w = 0.0F;
            this.x = 0.0F;
            this.y = 0.0F;
            this.z = 0.0F;
            if (astring == null) {
                return;
            }
        }

        f1 = 1.0F / f1;
        float f = (float) java.lang.Math.sin(p171_0.angle / 2.0);
        this.w = (float) java.lang.Math.cos(p171_0.angle / 2.0);
        this.x = p171_0.x * f1 * f;
        this.y = p171_0.y * f1 * f;
        this.z = p171_0.z * f1 * f;
    }

    public final void mul(Quat4f p171_0) {
        float f2 =
                this.w * p171_0.w
                        - this.x * p171_0.x
                        - this.y * p171_0.y
                        - this.z * p171_0.z;
        float f =
                this.w * p171_0.x
                        + p171_0.w * this.x
                        + this.y * p171_0.z
                        - this.z * p171_0.y;
        float f1 =
                this.w * p171_0.y
                        + p171_0.w * this.y
                        - this.x * p171_0.z
                        + this.z * p171_0.x;
        this.z =
                this.w * p171_0.z
                        + p171_0.w * this.z
                        + this.x * p171_0.y
                        - this.y * p171_0.x;
        this.w = f2;
        this.x = f;
        this.y = f1;
    }

    public final void set(Matrix4d p171_0) {
        double d0 = 0.25 * (p171_0.m00 + p171_0.m11 + p171_0.m22 + p171_0.m33);
        if (d0 >= 0.0) {
            if (d0 >= 1.0E-30) {
                this.w = (float) java.lang.Math.sqrt(d0);
                d0 = 0.25 / this.w;
                this.x = (float) ((p171_0.m21 - p171_0.m12) * d0);
                this.y = (float) ((p171_0.m02 - p171_0.m20) * d0);
                this.z = (float) ((p171_0.m10 - p171_0.m01) * d0);
            } else {
                this.w = 0.0F;
                d0 = -0.5 * (p171_0.m11 + p171_0.m22);
                if (d0 >= 0.0) {
                    if (d0 >= 1.0E-30) {
                        this.x = (float) java.lang.Math.sqrt(d0);
                        d0 = 0.5 / this.x;
                        this.y = (float) (p171_0.m10 * d0);
                        this.z = (float) (p171_0.m20 * d0);
                    } else {
                        this.x = 0.0F;
                        d0 = 0.5 * (1.0 - p171_0.m22);
                        if (d0 >= 1.0E-30) {
                            this.y = (float) java.lang.Math.sqrt(d0);
                            this.z = (float) (p171_0.m21 / (2.0 * this.y));
                        } else {
                            this.y = 0.0F;
                            this.z = 1.0F;
                        }
                    }
                } else {
                    this.x = 0.0F;
                    this.y = 0.0F;
                    this.z = 1.0F;
                }
            }
        } else {
            this.w = 0.0F;
            this.x = 0.0F;
            this.y = 0.0F;
            this.z = 1.0F;
        }
    }

    public final void set(Matrix3f p171_0) {
        float f = 0.25F * (p171_0.m00 + p171_0.m11 + p171_0.m22 + 1.0F);
        if (f >= 0.0F) {
            if (f >= 1.0E-30) {
                this.w = (float) java.lang.Math.sqrt(f);
                f = 0.25F / this.w;
                this.x = (p171_0.m21 - p171_0.m12) * f;
                this.y = (p171_0.m02 - p171_0.m20) * f;
                this.z = (p171_0.m10 - p171_0.m01) * f;
            } else {
                this.w = 0.0F;
                f = -0.5F * (p171_0.m11 + p171_0.m22);
                if (f >= 0.0F) {
                    if (f >= 1.0E-30) {
                        this.x = (float) java.lang.Math.sqrt(f);
                        f = 0.5F / this.x;
                        this.y = p171_0.m10 * f;
                        this.z = p171_0.m20 * f;
                    } else {
                        this.x = 0.0F;
                        f = 0.5F * (1.0F - p171_0.m22);
                        if (f >= 1.0E-30) {
                            this.y = (float) java.lang.Math.sqrt(f);
                            this.z = p171_0.m21 / (2.0F * this.y);
                        } else {
                            this.y = 0.0F;
                            this.z = 1.0F;
                        }
                    }
                } else {
                    this.x = 0.0F;
                    this.y = 0.0F;
                    this.z = 1.0F;
                }
            }
        } else {
            this.w = 0.0F;
            this.x = 0.0F;
            this.y = 0.0F;
            this.z = 1.0F;
        }
    }

    public final void inverse() {
        float f =
                1.0F
                        / (this.w * this.w
                                + this.x * this.x
                                + this.y * this.y
                                + this.z * this.z);
        this.w *= f;
        this.x *= -f;
        this.y *= -f;
        this.z *= -f;
    }

    public Quat4f(float[] p171_0) {
        float f =
                (float)
                        (1.0
                                / java.lang.Math.sqrt(
                                        p171_0[0] * p171_0[0]
                                                + p171_0[1] * p171_0[1]
                                                + p171_0[2] * p171_0[2]
                                                + p171_0[3] * p171_0[3]));
        this.x = p171_0[0] * f;
        this.y = p171_0[1] * f;
        this.z = p171_0[2] * f;
        this.w = p171_0[3] * f;
    }

    public final void mul(
            Quat4f p171_0, Quat4f p171_1) {
        java.lang.String[] astring = remix.compat.vecmath.Tuple3f.hF();
        if (this != p171_0 && this != p171_1) {
            this.w =
                    p171_0.w * p171_1.w
                            - p171_0.x * p171_1.x
                            - p171_0.y * p171_1.y
                            - p171_0.z * p171_1.z;
            this.x =
                    p171_0.w * p171_1.x
                            + p171_1.w * p171_0.x
                            + p171_0.y * p171_1.z
                            - p171_0.z * p171_1.y;
            this.y =
                    p171_0.w * p171_1.y
                            + p171_1.w * p171_0.y
                            - p171_0.x * p171_1.z
                            + p171_0.z * p171_1.x;
            this.z =
                    p171_0.w * p171_1.z
                            + p171_1.w * p171_0.z
                            + p171_0.x * p171_1.y
                            - p171_0.y * p171_1.x;
            if (astring == null) {
                return;
            }
        }

        float f2 =
                p171_0.w * p171_1.w
                        - p171_0.x * p171_1.x
                        - p171_0.y * p171_1.y
                        - p171_0.z * p171_1.z;
        float f =
                p171_0.w * p171_1.x
                        + p171_1.w * p171_0.x
                        + p171_0.y * p171_1.z
                        - p171_0.z * p171_1.y;
        float f1 =
                p171_0.w * p171_1.y
                        + p171_1.w * p171_0.y
                        - p171_0.x * p171_1.z
                        + p171_0.z * p171_1.x;
        this.z =
                p171_0.w * p171_1.z
                        + p171_1.w * p171_0.z
                        + p171_0.x * p171_1.y
                        - p171_0.y * p171_1.x;
        this.w = f2;
        this.x = f;
        this.y = f1;
    }

    public final void cQ() {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        float f = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
        java.lang.String[] astring = astring1;
        if (f > 0.0F) {
            f = 1.0F / (float) java.lang.Math.sqrt(f);
            this.x *= f;
            this.y *= f;
            this.z *= f;
            this.w *= f;
            if (astring == null) {
                return;
            }
        }

        this.x = 0.0F;
        this.y = 0.0F;
        this.z = 0.0F;
        this.w = 0.0F;
    }

    public final void mulInverse(
            Quat4f p171_0, Quat4f p171_1) {
        remix.compat.vecmath.Quat4f quat4f =
                new remix.compat.vecmath.Quat4f(p171_1);
        quat4f.inverse();
        this.mul(p171_0, quat4f);
    }

    public final void conjugate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public final void inverse(Quat4f p171_0) {
        float f =
                1.0F
                        / (p171_0.w * p171_0.w
                                + p171_0.x * p171_0.x
                                + p171_0.y * p171_0.y
                                + p171_0.z * p171_0.z);
        this.w = f * p171_0.w;
        this.x = -f * p171_0.x;
        this.y = -f * p171_0.y;
        this.z = -f * p171_0.z;
    }

    public Quat4f(Tuple4d p171_0) {
        double d0 =
                1.0
                        / java.lang.Math.sqrt(
                                p171_0.x * p171_0.x
                                        + p171_0.y * p171_0.y
                                        + p171_0.z * p171_0.z
                                        + p171_0.w * p171_0.w);
        this.x = (float) (p171_0.x * d0);
        this.y = (float) (p171_0.y * d0);
        this.z = (float) (p171_0.z * d0);
        this.w = (float) (p171_0.w * d0);
    }

    public Quat4f(Quat4d quat4d) {
        super(quat4d);
    }

    public final void set(Matrix3d p171_0) {
        double d0 = 0.25 * (p171_0.m00 + p171_0.m11 + p171_0.m22 + 1.0);
        if (d0 >= 0.0) {
            if (d0 >= 1.0E-30) {
                this.w = (float) java.lang.Math.sqrt(d0);
                d0 = 0.25 / this.w;
                this.x = (float) ((p171_0.m21 - p171_0.m12) * d0);
                this.y = (float) ((p171_0.m02 - p171_0.m20) * d0);
                this.z = (float) ((p171_0.m10 - p171_0.m01) * d0);
            } else {
                this.w = 0.0F;
                d0 = -0.5 * (p171_0.m11 + p171_0.m22);
                if (d0 >= 0.0) {
                    if (d0 >= 1.0E-30) {
                        this.x = (float) java.lang.Math.sqrt(d0);
                        d0 = 0.5 / this.x;
                        this.y = (float) (p171_0.m10 * d0);
                        this.z = (float) (p171_0.m20 * d0);
                    } else {
                        this.x = 0.0F;
                        d0 = 0.5 * (1.0 - p171_0.m22);
                        if (d0 >= 1.0E-30) {
                            this.y = (float) java.lang.Math.sqrt(d0);
                            this.z = (float) (p171_0.m21 / (2.0 * this.y));
                        } else {
                            this.y = 0.0F;
                            this.z = 1.0F;
                        }
                    }
                } else {
                    this.x = 0.0F;
                    this.y = 0.0F;
                    this.z = 1.0F;
                }
            }
        } else {
            this.w = 0.0F;
            this.x = 0.0F;
            this.y = 0.0F;
            this.z = 1.0F;
        }
    }

    public Quat4f(Tuple4f p171_0) {
        float f =
                (float)
                        (1.0
                                / java.lang.Math.sqrt(
                                        p171_0.x * p171_0.x
                                                + p171_0.y * p171_0.y
                                                + p171_0.z * p171_0.z
                                                + p171_0.w * p171_0.w));
        this.x = p171_0.x * f;
        this.y = p171_0.y * f;
        this.z = p171_0.z * f;
        this.w = p171_0.w * f;
    }

    public final void mulInverse(Quat4f p171_0) {
        remix.compat.vecmath.Quat4f quat4f =
                new remix.compat.vecmath.Quat4f(p171_0);
        quat4f.inverse();
        this.mul(quat4f);
    }

    public Quat4f() {}

    public final void conjugate(Quat4f p171_0) {
        this.x = -p171_0.x;
        this.y = -p171_0.y;
        this.z = -p171_0.z;
        this.w = p171_0.w;
    }

    public final void interpolate(Quat4f p171_0, float p171_1) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        double d0 =
                this.x * p171_0.x
                        + this.y * p171_0.y
                        + this.z * p171_0.z
                        + this.w * p171_0.w;
        java.lang.String[] astring = astring1;
        if (d0 < 0.0) {
            p171_0.x = -p171_0.x;
            p171_0.y = -p171_0.y;
            p171_0.z = -p171_0.z;
            p171_0.w = -p171_0.w;
            d0 = -d0;
        }

        double d1;
        double d2;
        {
            if (1.0 - d0 > 1.0E-6) {
                double d3 = java.lang.Math.acos(d0);
                double d4 = java.lang.Math.sin(d3);
                d1 = java.lang.Math.sin((1.0 - p171_1) * d3) / d4;
                d2 = java.lang.Math.sin(p171_1 * d3) / d4;
                if (!(astring == null)) {

                    d1 = 1.0 - p171_1;
                    d2 = p171_1;
                }
            } else {

                d1 = 1.0 - p171_1;
                d2 = p171_1;
            }
        }

        this.w = (float) (d1 * this.w + d2 * p171_0.w);
        this.x = (float) (d1 * this.x + d2 * p171_0.x);
        this.y = (float) (d1 * this.y + d2 * p171_0.y);
        this.z = (float) (d1 * this.z + d2 * p171_0.z);
    }

    public Quat4f(float p171_0, float p171_1, float p171_2, float p171_3) {
        float f4 =
                (float)
                        (1.0
                                / java.lang.Math.sqrt(
                                        p171_0 * p171_0
                                                + p171_1 * p171_1
                                                + p171_2 * p171_2
                                                + p171_3 * p171_3));
        this.x = p171_0 * f4;
        this.y = p171_1 * f4;
        this.z = p171_2 * f4;
        this.w = p171_3 * f4;
    }

    public final void normalize(Quat4f p171_0) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        float f =
                p171_0.x * p171_0.x
                        + p171_0.y * p171_0.y
                        + p171_0.z * p171_0.z
                        + p171_0.w * p171_0.w;
        java.lang.String[] astring = astring1;
        if (f > 0.0F) {
            f = 1.0F / (float) java.lang.Math.sqrt(f);
            this.x = f * p171_0.x;
            this.y = f * p171_0.y;
            this.z = f * p171_0.z;
            this.w = f * p171_0.w;
            if (astring == null) {
                return;
            }
        }

        this.x = 0.0F;
        this.y = 0.0F;
        this.z = 0.0F;
        this.w = 0.0F;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Quat4f(Quat4f quat4f) {
        super(quat4f);
    }
}
