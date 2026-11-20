package remix.compat.vecmath;

import java.io.Serializable;

public class Quat4d extends Tuple4d implements Serializable {
    static final double ix = 1.0E-30;
    static final long serialVersionUID = 7577479888820201099L;
    static final double iz = 1.57079632679;
    static final double rd = 1.0E-12;

    public final void normalize(Quat4d p171_0) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        double d0 =
                p171_0.x * p171_0.x
                        + p171_0.y * p171_0.y
                        + p171_0.z * p171_0.z
                        + p171_0.w * p171_0.w;
        java.lang.String[] astring = astring1;
        if (d0 > 0.0) {
            d0 = 1.0 / java.lang.Math.sqrt(d0);
            this.x = d0 * p171_0.x;
            this.y = d0 * p171_0.y;
            this.z = d0 * p171_0.z;
            this.w = d0 * p171_0.w;
            if (astring == null) {
                return;
            }
        }

        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.w = 0.0;
    }

    public final void conjugate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public Quat4d(double p171_0, double p171_1, double p171_2, double p171_3) {
        double d4 =
                1.0
                        / java.lang.Math.sqrt(
                                p171_0 * p171_0
                                        + p171_1 * p171_1
                                        + p171_2 * p171_2
                                        + p171_3 * p171_3);
        this.x = p171_0 * d4;
        this.y = p171_1 * d4;
        this.z = p171_2 * d4;
        this.w = p171_3 * d4;
    }

    public Quat4d(double[] p171_0) {
        double d0 =
                1.0
                        / java.lang.Math.sqrt(
                                p171_0[0] * p171_0[0]
                                        + p171_0[1] * p171_0[1]
                                        + p171_0[2] * p171_0[2]
                                        + p171_0[3] * p171_0[3]);
        this.x = p171_0[0] * d0;
        this.y = p171_0[1] * d0;
        this.z = p171_0[2] * d0;
        this.w = p171_0[3] * d0;
    }

    public final void inverse() {
        double d0 =
                1.0
                        / (this.w * this.w
                                + this.x * this.x
                                + this.y * this.y
                                + this.z * this.z);
        this.w *= d0;
        this.x *= -d0;
        this.y *= -d0;
        this.z *= -d0;
    }

    public final void conjugate(Quat4d p171_0) {
        this.x = -p171_0.x;
        this.y = -p171_0.y;
        this.z = -p171_0.z;
        this.w = p171_0.w;
    }

    public final void interpolate(
            Quat4d p171_0, double p171_1) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        double d1 =
                this.x * p171_0.x
                        + this.y * p171_0.y
                        + this.z * p171_0.z
                        + this.w * p171_0.w;
        java.lang.String[] astring = astring1;
        if (d1 < 0.0) {
            p171_0.x = -p171_0.x;
            p171_0.y = -p171_0.y;
            p171_0.z = -p171_0.z;
            p171_0.w = -p171_0.w;
            d1 = -d1;
        }

        double d2;
        double d3;
        {
            if (1.0 - d1 > 1.0E-12) {
                double d4 = java.lang.Math.acos(d1);
                double d5 = java.lang.Math.sin(d4);
                d2 = java.lang.Math.sin((1.0 - p171_1) * d4) / d5;
                d3 = java.lang.Math.sin(p171_1 * d4) / d5;
                if (!(astring == null)) {

                    d2 = 1.0 - p171_1;
                    d3 = p171_1;
                }
            } else {

                d2 = 1.0 - p171_1;
                d3 = p171_1;
            }
        }

        this.w = d2 * this.w + d3 * p171_0.w;
        this.x = d2 * this.x + d3 * p171_0.x;
        this.y = d2 * this.y + d3 * p171_0.y;
        this.z = d2 * this.z + d3 * p171_0.z;
    }

    public final void set(
            AxisAngle4d axisAngle4d) {
        String[] astring1 = Tuple3f.hF();
        double d1 =
                Math.sqrt(
                        axisAngle4d.x * axisAngle4d.x
                                + axisAngle4d.y * axisAngle4d.y
                                + axisAngle4d.z * axisAngle4d.z);
        String[] astring = astring1;
        if (d1 < 1.0E-12) {
            this.w = 0.0;
            this.x = 0.0;
            this.y = 0.0;
            this.z = 0.0;
            if (astring == null) {
                return;
            }
        }

        d1 = 1.0 / d1;
        double d0 = Math.sin(axisAngle4d.angle / 2.0);
        this.w = Math.cos(axisAngle4d.angle / 2.0);
        this.x = axisAngle4d.x * d1 * d0;
        this.y = axisAngle4d.y * d1 * d0;
        this.z = axisAngle4d.z * d1 * d0;
    }

    public final void cQ() {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        double d0 = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
        java.lang.String[] astring = astring1;
        if (d0 > 0.0) {
            d0 = 1.0 / java.lang.Math.sqrt(d0);
            this.x *= d0;
            this.y *= d0;
            this.z *= d0;
            this.w *= d0;
            if (astring == null) {
                return;
            }
        }

        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.w = 0.0;
    }

    public final void mul(
            Quat4d p171_0, Quat4d p171_1) {
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

        double d2 =
                p171_0.w * p171_1.w
                        - p171_0.x * p171_1.x
                        - p171_0.y * p171_1.y
                        - p171_0.z * p171_1.z;
        double d0 =
                p171_0.w * p171_1.x
                        + p171_1.w * p171_0.x
                        + p171_0.y * p171_1.z
                        - p171_0.z * p171_1.y;
        double d1 =
                p171_0.w * p171_1.y
                        + p171_1.w * p171_0.y
                        - p171_0.x * p171_1.z
                        + p171_0.z * p171_1.x;
        this.z =
                p171_0.w * p171_1.z
                        + p171_1.w * p171_0.z
                        + p171_0.x * p171_1.y
                        - p171_0.y * p171_1.x;
        this.w = d2;
        this.x = d0;
        this.y = d1;
    }

    public final void set(Matrix3d p171_0) {
        double d0 = 0.25 * (p171_0.m00 + p171_0.m11 + p171_0.m22 + 1.0);
        if (d0 >= 0.0) {
            if (d0 >= 1.0E-30) {
                this.w = java.lang.Math.sqrt(d0);
                d0 = 0.25 / this.w;
                this.x = (p171_0.m21 - p171_0.m12) * d0;
                this.y = (p171_0.m02 - p171_0.m20) * d0;
                this.z = (p171_0.m10 - p171_0.m01) * d0;
            } else {
                this.w = 0.0;
                d0 = -0.5 * (p171_0.m11 + p171_0.m22);
                if (d0 >= 0.0) {
                    if (d0 >= 1.0E-30) {
                        this.x = java.lang.Math.sqrt(d0);
                        d0 = 0.5 / this.x;
                        this.y = p171_0.m10 * d0;
                        this.z = p171_0.m20 * d0;
                    } else {
                        this.x = 0.0;
                        d0 = 0.5 * (1.0 - p171_0.m22);
                        if (d0 >= 1.0E-30) {
                            this.y = java.lang.Math.sqrt(d0);
                            this.z = p171_0.m21 / (2.0 * this.y);
                        } else {
                            this.y = 0.0;
                            this.z = 1.0;
                        }
                    }
                } else {
                    this.x = 0.0;
                    this.y = 0.0;
                    this.z = 1.0;
                }
            }
        } else {
            this.w = 0.0;
            this.x = 0.0;
            this.y = 0.0;
            this.z = 1.0;
        }
    }

    public final void mulInverse(Quat4d p171_0) {
        remix.compat.vecmath.Quat4d quat4d =
                new remix.compat.vecmath.Quat4d(p171_0);
        quat4d.inverse();
        this.mul(quat4d);
    }

    public final void inverse(Quat4d p171_0) {
        double d0 =
                1.0
                        / (p171_0.w * p171_0.w
                                + p171_0.x * p171_0.x
                                + p171_0.y * p171_0.y
                                + p171_0.z * p171_0.z);
        this.w = d0 * p171_0.w;
        this.x = -d0 * p171_0.x;
        this.y = -d0 * p171_0.y;
        this.z = -d0 * p171_0.z;
    }

    public final void mul(Quat4d p171_0) {
        double d2 =
                this.w * p171_0.w
                        - this.x * p171_0.x
                        - this.y * p171_0.y
                        - this.z * p171_0.z;
        double d0 =
                this.w * p171_0.x
                        + p171_0.w * this.x
                        + this.y * p171_0.z
                        - this.z * p171_0.y;
        double d1 =
                this.w * p171_0.y
                        + p171_0.w * this.y
                        - this.x * p171_0.z
                        + this.z * p171_0.x;
        this.z =
                this.w * p171_0.z
                        + p171_0.w * this.z
                        + this.x * p171_0.y
                        - this.y * p171_0.x;
        this.w = d2;
        this.x = d0;
        this.y = d1;
    }

    public final void mulInverse(
            Quat4d p171_0, Quat4d p171_1) {
        remix.compat.vecmath.Quat4d quat4d =
                new remix.compat.vecmath.Quat4d(p171_1);
        quat4d.inverse();
        this.mul(p171_0, quat4d);
    }

    public Quat4d(Tuple4d p171_0) {
        double d0 =
                1.0
                        / java.lang.Math.sqrt(
                                p171_0.x * p171_0.x
                                        + p171_0.y * p171_0.y
                                        + p171_0.z * p171_0.z
                                        + p171_0.w * p171_0.w);
        this.x = p171_0.x * d0;
        this.y = p171_0.y * d0;
        this.z = p171_0.z * d0;
        this.w = p171_0.w * d0;
    }

    public final void interpolate(
            Quat4d p171_0, Quat4d p171_1, double p171_2) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        double d1 =
                p171_1.x * p171_0.x
                        + p171_1.y * p171_0.y
                        + p171_1.z * p171_0.z
                        + p171_1.w * p171_0.w;
        java.lang.String[] astring = astring1;
        if (d1 < 0.0) {
            p171_0.x = -p171_0.x;
            p171_0.y = -p171_0.y;
            p171_0.z = -p171_0.z;
            p171_0.w = -p171_0.w;
            d1 = -d1;
        }

        double d2;
        double d3;
        {
            if (1.0 - d1 > 1.0E-12) {
                double d4 = java.lang.Math.acos(d1);
                double d5 = java.lang.Math.sin(d4);
                d2 = java.lang.Math.sin((1.0 - p171_2) * d4) / d5;
                d3 = java.lang.Math.sin(p171_2 * d4) / d5;
                if (!(astring == null)) {

                    d2 = 1.0 - p171_2;
                    d3 = p171_2;
                }
            } else {

                d2 = 1.0 - p171_2;
                d3 = p171_2;
            }
        }

        this.w = d2 * p171_0.w + d3 * p171_1.w;
        this.x = d2 * p171_0.x + d3 * p171_1.x;
        this.y = d2 * p171_0.y + d3 * p171_1.y;
        this.z = d2 * p171_0.z + d3 * p171_1.z;
    }

    public final void set(Matrix3f p171_0) {
        double d0 = 0.25 * (p171_0.m00 + p171_0.m11 + p171_0.m22 + 1.0);
        if (d0 >= 0.0) {
            if (d0 >= 1.0E-30) {
                this.w = java.lang.Math.sqrt(d0);
                d0 = 0.25 / this.w;
                this.x = (p171_0.m21 - p171_0.m12) * d0;
                this.y = (p171_0.m02 - p171_0.m20) * d0;
                this.z = (p171_0.m10 - p171_0.m01) * d0;
            } else {
                this.w = 0.0;
                d0 = -0.5 * (p171_0.m11 + p171_0.m22);
                if (d0 >= 0.0) {
                    if (d0 >= 1.0E-30) {
                        this.x = java.lang.Math.sqrt(d0);
                        d0 = 0.5 / this.x;
                        this.y = p171_0.m10 * d0;
                        this.z = p171_0.m20 * d0;
                    } else {
                        this.x = 0.0;
                        d0 = 0.5 * (1.0 - p171_0.m22);
                        if (d0 >= 1.0E-30) {
                            this.y = java.lang.Math.sqrt(d0);
                            this.z = p171_0.m21 / (2.0 * this.y);
                        }

                        this.y = 0.0;
                        this.z = 1.0;
                    }
                } else {
                    this.x = 0.0;
                    this.y = 0.0;
                    this.z = 1.0;
                }
            }
        } else {
            this.w = 0.0;
            this.x = 0.0;
            this.y = 0.0;
            this.z = 1.0;
        }
    }

    public final void set(Matrix4f p171_0) {
        double d0 = 0.25 * (p171_0.m00 + p171_0.m11 + p171_0.m22 + p171_0.m33);
        if (d0 >= 0.0) {
            if (d0 >= 1.0E-30) {
                this.w = java.lang.Math.sqrt(d0);
                d0 = 0.25 / this.w;
                this.x = (p171_0.m21 - p171_0.m12) * d0;
                this.y = (p171_0.m02 - p171_0.m20) * d0;
                this.z = (p171_0.m10 - p171_0.m01) * d0;
            } else {
                this.w = 0.0;
                d0 = -0.5 * (p171_0.m11 + p171_0.m22);
                if (d0 >= 0.0) {
                    if (d0 >= 1.0E-30) {
                        this.x = java.lang.Math.sqrt(d0);
                        d0 = 1.0 / (2.0 * this.x);
                        this.y = p171_0.m10 * d0;
                        this.z = p171_0.m20 * d0;
                    } else {
                        this.x = 0.0;
                        d0 = 0.5 * (1.0 - p171_0.m22);
                        if (d0 >= 1.0E-30) {
                            this.y = java.lang.Math.sqrt(d0);
                            this.z = p171_0.m21 / (2.0 * this.y);
                        } else {
                            this.y = 0.0;
                            this.z = 1.0;
                        }
                    }
                } else {
                    this.x = 0.0;
                    this.y = 0.0;
                    this.z = 1.0;
                }
            }
        } else {
            this.w = 0.0;
            this.x = 0.0;
            this.y = 0.0;
            this.z = 1.0;
        }
    }

    public Quat4d(Tuple4f p171_0) {
        double d0 =
                1.0
                        / java.lang.Math.sqrt(
                                p171_0.x * p171_0.x
                                        + p171_0.y * p171_0.y
                                        + p171_0.z * p171_0.z
                                        + p171_0.w * p171_0.w);
        this.x = p171_0.x * d0;
        this.y = p171_0.y * d0;
        this.z = p171_0.z * d0;
        this.w = p171_0.w * d0;
    }

    public Quat4d() {}

    public final void set(AxisAngle4f p171_0) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        double d1 =
                java.lang.Math.sqrt(
                        p171_0.x * p171_0.x + p171_0.y * p171_0.y + p171_0.z * p171_0.z);
        java.lang.String[] astring = astring1;
        if (d1 < 1.0E-12) {
            this.w = 0.0;
            this.x = 0.0;
            this.y = 0.0;
            this.z = 0.0;
            if (astring == null) {
                return;
            }
        }

        double d0 = java.lang.Math.sin(p171_0.angle / 2.0);
        d1 = 1.0 / d1;
        this.w = java.lang.Math.cos(p171_0.angle / 2.0);
        this.x = p171_0.x * d1 * d0;
        this.y = p171_0.y * d1 * d0;
        this.z = p171_0.z * d1 * d0;
    }

    public final void set(Matrix4d p171_0) {
        double d0 = 0.25 * (p171_0.m00 + p171_0.m11 + p171_0.m22 + p171_0.m33);
        if (d0 >= 0.0) {
            if (d0 >= 1.0E-30) {
                this.w = java.lang.Math.sqrt(d0);
                d0 = 0.25 / this.w;
                this.x = (p171_0.m21 - p171_0.m12) * d0;
                this.y = (p171_0.m02 - p171_0.m20) * d0;
                this.z = (p171_0.m10 - p171_0.m01) * d0;
            } else {
                this.w = 0.0;
                d0 = -0.5 * (p171_0.m11 + p171_0.m22);
                if (d0 >= 0.0) {
                    if (d0 >= 1.0E-30) {
                        this.x = java.lang.Math.sqrt(d0);
                        d0 = 0.5 / this.x;
                        this.y = p171_0.m10 * d0;
                        this.z = p171_0.m20 * d0;
                    } else {
                        this.x = 0.0;
                        d0 = 0.5 * (1.0 - p171_0.m22);
                        if (d0 >= 1.0E-30) {
                            this.y = java.lang.Math.sqrt(d0);
                            this.z = p171_0.m21 / (2.0 * this.y);
                        } else {
                            this.y = 0.0;
                            this.z = 1.0;
                        }
                    }
                } else {
                    this.x = 0.0;
                    this.y = 0.0;
                    this.z = 1.0;
                }
            }
        } else {
            this.w = 0.0;
            this.x = 0.0;
            this.y = 0.0;
            this.z = 1.0;
        }
    }

    public Quat4d(Quat4f quat4f) {
        super(quat4f);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Quat4d(Quat4d quat4d) {
        super(quat4d);
    }
}
