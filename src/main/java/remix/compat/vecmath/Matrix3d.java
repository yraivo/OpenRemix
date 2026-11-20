package remix.compat.vecmath;

import java.io.Serializable;

public class Matrix3d implements Serializable, Cloneable {

    public double m20;
    public double m00;
    public double m10;
    private static final double sG = 1.110223024E-16;
    public double m02;
    static final long serialVersionUID = 6837536777072402710L;
    private static String[] packedTextFragments;
    public double m11;
    public double m12;
    public double m01;
    public double m22;

    public double m21;

    public final void setM01(double d0) {
        this.m01 = d0;
    }

    public boolean epsilonEquals(Matrix3d p171_0, double p171_1) {
        double d1 = this.m00 - p171_0.m00;

        double d2;
        label216:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label216;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception17) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception17);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception16) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception16);
        }

        d1 = this.m01 - p171_0.m01;

        label202:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label202;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception15) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception15);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception14) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception14);
        }

        d1 = this.m02 - p171_0.m02;

        label188:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label188;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception13) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception13);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception12) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception12);
        }

        d1 = this.m10 - p171_0.m10;

        label174:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label174;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception11) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception11);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception10) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception10);
        }

        d1 = this.m11 - p171_0.m11;

        label160:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label160;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception9) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception9);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception8) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception8);
        }

        d1 = this.m12 - p171_0.m12;

        label146:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label146;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception7) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception7);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception6) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception6);
        }

        d1 = this.m20 - p171_0.m20;

        label132:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label132;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception5) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception5);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception4) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception4);
        }

        d1 = this.m21 - p171_0.m21;

        label118:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label118;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception3) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception3);
            }

            d2 = d1;
        }

        try {
            if (d2 > p171_1) {
                return false;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception2) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception2);
        }

        d1 = this.m22 - p171_0.m22;

        label104:
        {
            try {
                if (d1 < 0.0) {
                    d2 = -d1;
                    break label104;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception1) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception1);
            }

            d2 = d1;
        }

        try {
            return !(d2 > p171_1);
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception);
        }
    }

    public final void getRow(
            int i, Vector3d vector3d) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            vector3d.x = this.m00;
            vector3d.y = this.m10;
            vector3d.z = this.m20;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            vector3d.x = this.m01;
            vector3d.y = this.m11;
            vector3d.z = this.m21;
            if (astring == null) {
                return;
            }
        }

        if (i != 2) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d4"));
        }

        vector3d.x = this.m02;
        vector3d.y = this.m12;
        vector3d.z = this.m22;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d4"));
        }
    }

    public final void transform(
            Tuple3d p171_0, Tuple3d p171_1) {
        double d0 = this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z;
        double d1 = this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z;
        p171_1.z = this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z;
        p171_1.x = d0;
        p171_1.y = d1;
    }

    public final void setZero() {
        this.m00 = 0.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m10 = 0.0;
        this.m11 = 0.0;
        this.m12 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 0.0;
    }

    static int compute_2X2(
            double p171_0,
            double p171_1,
            double p171_2,
            double[] p171_3,
            double[] p171_4,
            double[] p171_5,
            double[] p171_6,
            double[] p171_7,
            int p171_8) {
        double d3 = 2.0;
        double d4 = 1.0;
        double d28 = p171_3[0];
        double d27 = p171_3[1];
        double d23 = 0.0;
        double d24 = 0.0;
        double d25 = 0.0;
        double d26 = 0.0;
        double d14 = 0.0;
        double d18 = p171_0;
        double d15 = java.lang.Math.abs(d18);
        double d20 = p171_2;
        double d17 = java.lang.Math.abs(p171_2);
        int j = 1;
        byte flag;
        if (d17 > d15) {
            flag = 1;
        } else {
            flag = 0;
        }

        if (flag != 0) {
            j = 3;
            double d6 = d18;
            d18 = d20;
            d20 = d6;
            d6 = d15;
            d15 = d17;
            d17 = d6;
        }

        double d19 = p171_1;
        double d16 = java.lang.Math.abs(d19);
        if (d16 == 0.0) {
            p171_3[1] = d17;
            p171_3[0] = d15;
            d23 = 1.0;
            d24 = 1.0;
            d25 = 0.0;
            d26 = 0.0;
        } else {
            boolean flag1 = true;
            if (d16 > d15) {
                j = 2;
                if (d15 / d16 < 1.110223024E-16) {
                    flag1 = false;
                    d28 = d16;
                    if (d17 > 1.0) {
                        d27 = d15 / (d16 / d17);
                    } else {
                        d27 = d15 / d16 * d17;
                    }

                    d23 = 1.0;
                    d25 = d20 / d19;
                    d26 = 1.0;
                    d24 = d18 / d19;
                }
            }

            if ((flag1)) {
                double d8 = d15 - d17;
                double d9;
                if (d8 == d15) {
                    d9 = 1.0;
                } else {
                    d9 = d8 / d15;
                }

                double d10 = d19 / d18;
                double d13 = 2.0 - d9;
                double d21 = d10 * d10;
                double d22 = d13 * d13;
                double d12 = java.lang.Math.sqrt(d22 + d21);
                double d11;
                if (d9 == 0.0) {
                    d11 = java.lang.Math.abs(d10);
                } else {
                    d11 = java.lang.Math.sqrt(d9 * d9 + d21);
                }

                double d7 = (d12 + d11) * 0.5;
                if (d16 > d15) {
                    j = 2;
                    if (d15 / d16 < 1.110223024E-16) {
                        flag1 = false;
                        d28 = d16;
                        if (d17 > 1.0) {
                            d27 = d15 / (d16 / d17);
                        } else {
                            d27 = d15 / d16 * d17;
                        }

                        d23 = 1.0;
                        d25 = d20 / d19;
                        d26 = 1.0;
                        d24 = d18 / d19;
                    }
                }

                if ((flag1)) {
                    d8 = d15 - d17;
                    if (d8 == d15) {
                        d9 = 1.0;
                    } else {
                        d9 = d8 / d15;
                    }

                    d10 = d19 / d18;
                    d13 = 2.0 - d9;
                    d21 = d10 * d10;
                    d22 = d13 * d13;
                    d12 = java.lang.Math.sqrt(d22 + d21);
                    if (d9 == 0.0) {
                        d11 = java.lang.Math.abs(d10);
                    } else {
                        d11 = java.lang.Math.sqrt(d9 * d9 + d21);
                    }

                    d7 = (d12 + d11) * 0.5;
                    d27 = d17 / d7;
                    d28 = d15 * d7;

                    label109:
                    {
                        label108:
                        {
                            label107:
                            {
                                try {
                                    if (d21 == 0.0) {
                                        if (d9 == 0.0) {
                                            break label108;
                                        }
                                        break label107;
                                    }
                                } catch (
                                        java.lang.ArrayIndexOutOfBoundsException
                                                arrayindexoutofboundsexception1) {
                                    throw remix.compat.vecmath.Matrix3d
                                            .wrapException(
                                                    arrayindexoutofboundsexception1);
                                }

                                d13 = (d10 / (d12 + d13) + d10 / (d11 + d9)) * (d7 + 1.0);
                                break label109;
                            }

                            d13 =
                                    d19
                                                    / remix.compat.vecmath.Matrix3d
                                                            .d_sign(d8, d18)
                                            + d10 / d13;
                            break label109;
                        }

                        d13 =
                                remix.compat.vecmath.Matrix3d.d_sign(
                                                d3, d18)
                                        * remix.compat.vecmath.Matrix3d
                                                .d_sign(d4, d19);
                    }

                    d9 = java.lang.Math.sqrt(d13 * d13 + 4.0);
                    d24 = 2.0 / d9;
                    d26 = d13 / d9;
                    d23 = (d24 + d26 * d10) / d7;
                    d25 = d20 / d18 * d26 / d7;
                }
            }

            label91:
            {
                try {
                    if (flag != 0) {
                        p171_5[0] = d26;
                        p171_4[0] = d24;
                        p171_7[0] = d25;
                        p171_6[0] = d23;
                        break label91;
                    }
                } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
                    throw remix.compat.vecmath.Matrix3d.wrapException(
                            arrayindexoutofboundsexception);
                }

                p171_5[0] = d23;
                p171_4[0] = d25;
                p171_7[0] = d24;
                p171_6[0] = d26;
            }

            if (j == 1) {
                d14 =
                        remix.compat.vecmath.Matrix3d.d_sign(d4, p171_7[0])
                                * remix.compat.vecmath.Matrix3d.d_sign(
                                        d4, p171_5[0])
                                * remix.compat.vecmath.Matrix3d.d_sign(
                                        d4, p171_0);
            }

            if (j == 2) {
                d14 =
                        remix.compat.vecmath.Matrix3d.d_sign(d4, p171_6[0])
                                * remix.compat.vecmath.Matrix3d.d_sign(
                                        d4, p171_5[0])
                                * remix.compat.vecmath.Matrix3d.d_sign(
                                        d4, p171_1);
            }

            if (j == 3) {
                d14 =
                        remix.compat.vecmath.Matrix3d.d_sign(d4, p171_6[0])
                                * remix.compat.vecmath.Matrix3d.d_sign(
                                        d4, p171_4[0])
                                * remix.compat.vecmath.Matrix3d.d_sign(
                                        d4, p171_2);
            }

            p171_3[p171_8] = remix.compat.vecmath.Matrix3d.d_sign(d28, d14);
            double d5 =
                    d14
                            * remix.compat.vecmath.Matrix3d.d_sign(
                                    d4, p171_0)
                            * remix.compat.vecmath.Matrix3d.d_sign(
                                    d4, p171_2);
            p171_3[p171_8 + 1] =
                    remix.compat.vecmath.Matrix3d.d_sign(d27, d5);
        }

        return 0;
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

    public final double getM10() {
        return this.m10;
    }

    static double compute_rot(
            double p171_0,
            double p171_1,
            double[] p171_2,
            double[] p171_3,
            int p171_4,
            int p171_5) {

        double d2;
        double d3;
        double d7;
        if (p171_1 == 0.0) {
            d2 = 1.0;
            d3 = 0.0;
            d7 = p171_0;
        } else if (p171_0 == 0.0) {
            d2 = 0.0;
            d3 = 1.0;
            d7 = p171_1;
        } else {
            {
                double d5 = p171_0;
                double d6 = p171_1;
                double d4 =
                        remix.compat.vecmath.Matrix3d.min(
                                java.lang.Math.abs(d5), java.lang.Math.abs(d6));
                if (d4 >= 4.994797680505588E145) {
                    int j1 = 0;

                    while (d4 >= 4.994797680505588E145) {
                        j1++;
                        d5 *= 2.002083095183101E-146;
                        d6 *= 2.002083095183101E-146;
                        d4 =
                                remix.compat.vecmath.Matrix3d.min(
                                        java.lang.Math.abs(d5), java.lang.Math.abs(d6));
                    }

                    d7 = java.lang.Math.sqrt(d5 * d5 + d6 * d6);
                    d2 = d5 / d7;
                    d3 = d6 / d7;

                    for (int i1 = 1; i1 <= j1; i1++) {
                        d7 *= 4.994797680505588E145;
                    }
                } else if (!(d4 <= 2.002083095183101E-146)) {
                    d7 = java.lang.Math.sqrt(d5 * d5 + d6 * d6);
                    d2 = d5 / d7;
                    d3 = d6 / d7;
                } else {
                    int l = 0;

                    while (d4 <= 2.002083095183101E-146) {
                        l++;
                        d5 *= 4.994797680505588E145;
                        d6 *= 4.994797680505588E145;
                        d4 =
                                remix.compat.vecmath.Matrix3d.min(
                                        java.lang.Math.abs(d5), java.lang.Math.abs(d6));
                    }

                    d7 = java.lang.Math.sqrt(d5 * d5 + d6 * d6);
                    d2 = d5 / d7;
                    d3 = d6 / d7;

                    for (int k = 1; k <= l; k++) {
                        d7 *= 2.002083095183101E-146;
                    }
                }

                boolean runGuardedCode;
                try {
                    runGuardedCode = !(!(java.lang.Math.abs(p171_0) > java.lang.Math.abs(p171_1)) || !(d2 < 0.0));
                } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
                    throw remix.compat.vecmath.Matrix3d.wrapException(
                            arrayindexoutofboundsexception);
                }
                if (runGuardedCode) {

                    d2 = -d2;
                    d3 = -d3;
                    d7 = -d7;
                }
            }
        }

        p171_2[p171_4] = d3;
        p171_3[p171_4] = d2;
        return d7;
    }

    public final void set(double p171_0) {
        this.m00 = p171_0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m10 = 0.0;
        this.m11 = p171_0;
        this.m12 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = p171_0;
    }

    public final void normalize() {
        double[] adouble = new double[9];
        double[] adouble1 = new double[3];
        this.getScaleRotate(adouble1, adouble);
        this.m00 = adouble[0];
        this.m01 = adouble[1];
        this.m02 = adouble[2];
        this.m10 = adouble[3];
        this.m11 = adouble[4];
        this.m12 = adouble[5];
        this.m20 = adouble[6];
        this.m21 = adouble[7];
        this.m22 = adouble[8];
    }

    public final double getM12() {
        return this.m12;
    }

    static double d_sign(double p171_0, double p171_1) {
        double d3;
        label30:
        {
            try {
                if (p171_0 >= 0.0) {
                    d3 = p171_0;
                    break label30;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception1) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception1);
            }

            d3 = -p171_0;
        }

        double d2 = d3;

        try {
            if (p171_1 >= 0.0) {
                return d2;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception);
        }

        return -d2;
    }

    public final void setRow(
            int i, Vector3d vector3d) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            vector3d.x = this.m00;
            vector3d.y = this.m01;
            vector3d.z = this.m02;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            vector3d.x = this.m10;
            vector3d.y = this.m11;
            vector3d.z = this.m12;
            if (astring == null) {
                return;
            }
        }

        if (i != 2) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d2"));
        }

        vector3d.x = this.m20;
        vector3d.y = this.m21;
        vector3d.z = this.m22;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d2"));
        }
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

        throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d1"));
    }

    public final void rotX(double p171_0) {
        double d1 = java.lang.Math.sin(p171_0);
        double d2 = java.lang.Math.cos(p171_0);
        this.m00 = 1.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m10 = 0.0;
        this.m11 = d2;
        this.m12 = -d1;
        this.m20 = 0.0;
        this.m21 = d1;
        this.m22 = d2;
    }

    private static RuntimeException wrapException(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    public final void mulTransposeRight(
            Matrix3d matrix3d,
            Matrix3d matrix3dValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix3d && this != matrix3dValue) {
            this.m00 =
                    matrix3d.m00 * matrix3dValue.m00
                            + matrix3d.m01 * matrix3dValue.m01
                            + matrix3d.m02 * matrix3dValue.m02;
            this.m01 =
                    matrix3d.m00 * matrix3dValue.m10
                            + matrix3d.m01 * matrix3dValue.m11
                            + matrix3d.m02 * matrix3dValue.m12;
            this.m02 =
                    matrix3d.m00 * matrix3dValue.m20
                            + matrix3d.m01 * matrix3dValue.m21
                            + matrix3d.m02 * matrix3dValue.m22;
            this.m10 =
                    matrix3d.m10 * matrix3dValue.m00
                            + matrix3d.m11 * matrix3dValue.m01
                            + matrix3d.m12 * matrix3dValue.m02;
            this.m11 =
                    matrix3d.m10 * matrix3dValue.m10
                            + matrix3d.m11 * matrix3dValue.m11
                            + matrix3d.m12 * matrix3dValue.m12;
            this.m12 =
                    matrix3d.m10 * matrix3dValue.m20
                            + matrix3d.m11 * matrix3dValue.m21
                            + matrix3d.m12 * matrix3dValue.m22;
            this.m20 =
                    matrix3d.m20 * matrix3dValue.m00
                            + matrix3d.m21 * matrix3dValue.m01
                            + matrix3d.m22 * matrix3dValue.m02;
            this.m21 =
                    matrix3d.m20 * matrix3dValue.m10
                            + matrix3d.m21 * matrix3dValue.m11
                            + matrix3d.m22 * matrix3dValue.m12;
            this.m22 =
                    matrix3d.m20 * matrix3dValue.m20
                            + matrix3d.m21 * matrix3dValue.m21
                            + matrix3d.m22 * matrix3dValue.m22;
            if (astring == null) {
                return;
            }
        }

        double d0 =
                matrix3d.m00 * matrix3dValue.m00
                        + matrix3d.m01 * matrix3dValue.m01
                        + matrix3d.m02 * matrix3dValue.m02;
        double d1 =
                matrix3d.m00 * matrix3dValue.m10
                        + matrix3d.m01 * matrix3dValue.m11
                        + matrix3d.m02 * matrix3dValue.m12;
        double d2 =
                matrix3d.m00 * matrix3dValue.m20
                        + matrix3d.m01 * matrix3dValue.m21
                        + matrix3d.m02 * matrix3dValue.m22;
        double d3 =
                matrix3d.m10 * matrix3dValue.m00
                        + matrix3d.m11 * matrix3dValue.m01
                        + matrix3d.m12 * matrix3dValue.m02;
        double d4 =
                matrix3d.m10 * matrix3dValue.m10
                        + matrix3d.m11 * matrix3dValue.m11
                        + matrix3d.m12 * matrix3dValue.m12;
        double d5 =
                matrix3d.m10 * matrix3dValue.m20
                        + matrix3d.m11 * matrix3dValue.m21
                        + matrix3d.m12 * matrix3dValue.m22;
        double d6 =
                matrix3d.m20 * matrix3dValue.m00
                        + matrix3d.m21 * matrix3dValue.m01
                        + matrix3d.m22 * matrix3dValue.m02;
        double d7 =
                matrix3d.m20 * matrix3dValue.m10
                        + matrix3d.m21 * matrix3dValue.m11
                        + matrix3d.m22 * matrix3dValue.m12;
        double d8 =
                matrix3d.m20 * matrix3dValue.m20
                        + matrix3d.m21 * matrix3dValue.m21
                        + matrix3d.m22 * matrix3dValue.m22;
        this.m00 = d0;
        this.m01 = d1;
        this.m02 = d2;
        this.m10 = d3;
        this.m11 = d4;
        this.m12 = d5;
        this.m20 = d6;
        this.m21 = d7;
        this.m22 = d8;
    }

    public final void transpose() {
        double d0 = this.m10;
        this.m10 = this.m01;
        this.m01 = d0;
        d0 = this.m20;
        this.m20 = this.m02;
        this.m02 = d0;
        d0 = this.m21;
        this.m21 = this.m12;
        this.m12 = d0;
    }

    private final void mulTransposeRight(
            Matrix3d matrix3d) {
        double[] adouble = new double[9];
        int[] aint = new int[3];
        double[] adouble1 = new double[9];
        adouble1[0] = matrix3d.m00;
        String[] astring1 = Tuple3f.hF();
        adouble1[1] = matrix3d.m01;
        adouble1[2] = matrix3d.m02;
        String[] astring = astring1;
        adouble1[3] = matrix3d.m10;
        adouble1[4] = matrix3d.m11;
        adouble1[5] = matrix3d.m12;
        adouble1[6] = matrix3d.m20;
        adouble1[7] = matrix3d.m21;
        adouble1[8] = matrix3d.m22;
        if (!luDecomposition(adouble1, aint)) {
            throw new SingularMatrixException(VecMathI18N.getString("Matrix3d12"));
        }

        int i = 0;

        while (i < 9) {
            adouble[i] = 0.0;
            i++;
            if (astring != null) {
                break;
            }
        }

        adouble[0] = 1.0;
        adouble[4] = 1.0;
        adouble[8] = 1.0;
        luBacksubstitution(adouble1, aint, adouble);
        this.m00 = adouble[0];
        this.m01 = adouble[1];
        this.m02 = adouble[2];
        this.m10 = adouble[3];
        this.m11 = adouble[4];
        this.m12 = adouble[5];
        this.m20 = adouble[6];
        this.m21 = adouble[7];
        this.m22 = adouble[8];
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

    public final void setM10(double d0) {
        this.m10 = d0;
    }

    public final void setColumn(int i, double d0, double d1, double d2) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = d0;
                this.m01 = d1;
                this.m02 = d2;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m10 = d0;
                this.m11 = d1;
                this.m12 = d2;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m20 = d0;
                this.m21 = d1;
                this.m22 = d2;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix3d6"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d6"));
        }
    }

    public final void getRow(int i, double[] adouble) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = adouble[0];
                this.m10 = adouble[1];
                this.m20 = adouble[2];
                if (astring == null) {
                    break;
                }
            case 1:
                this.m01 = adouble[0];
                this.m11 = adouble[1];
                this.m21 = adouble[2];
                if (astring == null) {
                    break;
                }
            case 2:
                this.m02 = adouble[0];
                this.m12 = adouble[1];
                this.m22 = adouble[2];
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix3d9"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d9"));
        }
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

    public final void setIdentity() {
        this.m00 = 1.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m10 = 0.0;
        this.m11 = 1.0;
        this.m12 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 1.0;
    }

    static double min(double p171_0, double p171_1) {
        try {
            return p171_0 > p171_1 ? p171_0 : p171_1;
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception);
        }
    }

    public final void setRotationScale(Matrix3d p171_0) {
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

    public final void getColumn(int i, double[] adouble) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            adouble[0] = this.m00;
            adouble[1] = this.m10;
            adouble[2] = this.m20;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            adouble[0] = this.m01;
            adouble[1] = this.m11;
            adouble[2] = this.m21;
            if (astring == null) {
                return;
            }
        }

        if (i != 2) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d4"));
        }

        adouble[0] = this.m02;
        adouble[1] = this.m12;
        adouble[2] = this.m22;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d4"));
        }
    }

    public final void setM02(double d0) {
        this.m02 = d0;
    }

    public final void setColumn(int i, double[] adouble) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = adouble[0];
                this.m01 = adouble[1];
                this.m02 = adouble[2];
                if (astring == null) {
                    break;
                }
            case 1:
                this.m10 = adouble[0];
                this.m11 = adouble[1];
                this.m12 = adouble[2];
                if (astring == null) {
                    break;
                }
            case 2:
                this.m20 = adouble[0];
                this.m21 = adouble[1];
                this.m22 = adouble[2];
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix3d6"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d6"));
        }
    }

    public final void l() {
        this.mulTransposeRight(this);
    }

    static double method_1729c4b03acd01b02385(double p171_0, double p171_1) {
        try {
            return p171_0 < p171_1 ? p171_0 : p171_1;
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception);
        }
    }

    public final void setRow(int i, double d0, double d1, double d2) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = d0;
                this.m10 = d1;
                this.m20 = d2;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m01 = d0;
                this.m11 = d1;
                this.m21 = d2;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m02 = d0;
                this.m12 = d1;
                this.m22 = d2;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix3d9"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d9"));
        }
    }

    public final void mul(Matrix3d p171_0) {
        double d0 = this.m00 * p171_0.m00 + this.m01 * p171_0.m10 + this.m02 * p171_0.m20;
        double d1 = this.m00 * p171_0.m01 + this.m01 * p171_0.m11 + this.m02 * p171_0.m21;
        double d2 = this.m00 * p171_0.m02 + this.m01 * p171_0.m12 + this.m02 * p171_0.m22;
        double d3 = this.m10 * p171_0.m00 + this.m11 * p171_0.m10 + this.m12 * p171_0.m20;
        double d4 = this.m10 * p171_0.m01 + this.m11 * p171_0.m11 + this.m12 * p171_0.m21;
        double d5 = this.m10 * p171_0.m02 + this.m11 * p171_0.m12 + this.m12 * p171_0.m22;
        double d6 = this.m20 * p171_0.m00 + this.m21 * p171_0.m10 + this.m22 * p171_0.m20;
        double d7 = this.m20 * p171_0.m01 + this.m21 * p171_0.m11 + this.m22 * p171_0.m21;
        double d8 = this.m20 * p171_0.m02 + this.m21 * p171_0.m12 + this.m22 * p171_0.m22;
        this.m00 = d0;
        this.m01 = d1;
        this.m02 = d2;
        this.m10 = d3;
        this.m11 = d4;
        this.m12 = d5;
        this.m20 = d6;
        this.m21 = d7;
        this.m22 = d8;
    }

    public final void mulTransposeBoth(
            Matrix3d matrix3d,
            Matrix3d matrix3dValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix3d && this != matrix3dValue) {
            this.m00 =
                    matrix3d.m00 * matrix3dValue.m00
                            + matrix3d.m10 * matrix3dValue.m10
                            + matrix3d.m20 * matrix3dValue.m20;
            this.m01 =
                    matrix3d.m00 * matrix3dValue.m01
                            + matrix3d.m10 * matrix3dValue.m11
                            + matrix3d.m20 * matrix3dValue.m21;
            this.m02 =
                    matrix3d.m00 * matrix3dValue.m02
                            + matrix3d.m10 * matrix3dValue.m12
                            + matrix3d.m20 * matrix3dValue.m22;
            this.m10 =
                    matrix3d.m01 * matrix3dValue.m00
                            + matrix3d.m11 * matrix3dValue.m10
                            + matrix3d.m21 * matrix3dValue.m20;
            this.m11 =
                    matrix3d.m01 * matrix3dValue.m01
                            + matrix3d.m11 * matrix3dValue.m11
                            + matrix3d.m21 * matrix3dValue.m21;
            this.m12 =
                    matrix3d.m01 * matrix3dValue.m02
                            + matrix3d.m11 * matrix3dValue.m12
                            + matrix3d.m21 * matrix3dValue.m22;
            this.m20 =
                    matrix3d.m02 * matrix3dValue.m00
                            + matrix3d.m12 * matrix3dValue.m10
                            + matrix3d.m22 * matrix3dValue.m20;
            this.m21 =
                    matrix3d.m02 * matrix3dValue.m01
                            + matrix3d.m12 * matrix3dValue.m11
                            + matrix3d.m22 * matrix3dValue.m21;
            this.m22 =
                    matrix3d.m02 * matrix3dValue.m02
                            + matrix3d.m12 * matrix3dValue.m12
                            + matrix3d.m22 * matrix3dValue.m22;
            if (astring == null) {
                return;
            }
        }

        double d0 =
                matrix3d.m00 * matrix3dValue.m00
                        + matrix3d.m10 * matrix3dValue.m10
                        + matrix3d.m20 * matrix3dValue.m20;
        double d1 =
                matrix3d.m00 * matrix3dValue.m01
                        + matrix3d.m10 * matrix3dValue.m11
                        + matrix3d.m20 * matrix3dValue.m21;
        double d2 =
                matrix3d.m00 * matrix3dValue.m02
                        + matrix3d.m10 * matrix3dValue.m12
                        + matrix3d.m20 * matrix3dValue.m22;
        double d3 =
                matrix3d.m01 * matrix3dValue.m00
                        + matrix3d.m11 * matrix3dValue.m10
                        + matrix3d.m21 * matrix3dValue.m20;
        double d4 =
                matrix3d.m01 * matrix3dValue.m01
                        + matrix3d.m11 * matrix3dValue.m11
                        + matrix3d.m21 * matrix3dValue.m21;
        double d5 =
                matrix3d.m01 * matrix3dValue.m02
                        + matrix3d.m11 * matrix3dValue.m12
                        + matrix3d.m21 * matrix3dValue.m22;
        double d6 =
                matrix3d.m02 * matrix3dValue.m00
                        + matrix3d.m12 * matrix3dValue.m10
                        + matrix3d.m22 * matrix3dValue.m20;
        double d7 =
                matrix3d.m02 * matrix3dValue.m01
                        + matrix3d.m12 * matrix3dValue.m11
                        + matrix3d.m22 * matrix3dValue.m21;
        double d8 =
                matrix3d.m02 * matrix3dValue.m02
                        + matrix3d.m12 * matrix3dValue.m12
                        + matrix3d.m22 * matrix3dValue.m22;
        this.m00 = d0;
        this.m01 = d1;
        this.m02 = d2;
        this.m10 = d3;
        this.m11 = d4;
        this.m12 = d5;
        this.m20 = d6;
        this.m21 = d7;
        this.m22 = d8;
    }

    @Override
    public int hashCode() {

        long i = 1L;
        i = VecMathUtil.hashDoubleToLongBits(i, this.m00);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m01);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m02);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m10);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m11);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m12);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m20);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m21);
        i = VecMathUtil.hashDoubleToLongBits(i, this.m22);
        return VecMathUtil.hashFinish(i);
    }

    public final void mulTransposeLeft(
            Matrix3d matrix3d,
            Matrix3d matrix3dValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix3d && this != matrix3dValue) {
            this.m00 =
                    matrix3d.m00 * matrix3dValue.m00
                            + matrix3d.m01 * matrix3dValue.m10
                            + matrix3d.m02 * matrix3dValue.m20;
            this.m01 =
                    matrix3d.m00 * matrix3dValue.m01
                            + matrix3d.m01 * matrix3dValue.m11
                            + matrix3d.m02 * matrix3dValue.m21;
            this.m02 =
                    matrix3d.m00 * matrix3dValue.m02
                            + matrix3d.m01 * matrix3dValue.m12
                            + matrix3d.m02 * matrix3dValue.m22;
            this.m10 =
                    matrix3d.m10 * matrix3dValue.m00
                            + matrix3d.m11 * matrix3dValue.m10
                            + matrix3d.m12 * matrix3dValue.m20;
            this.m11 =
                    matrix3d.m10 * matrix3dValue.m01
                            + matrix3d.m11 * matrix3dValue.m11
                            + matrix3d.m12 * matrix3dValue.m21;
            this.m12 =
                    matrix3d.m10 * matrix3dValue.m02
                            + matrix3d.m11 * matrix3dValue.m12
                            + matrix3d.m12 * matrix3dValue.m22;
            this.m20 =
                    matrix3d.m20 * matrix3dValue.m00
                            + matrix3d.m21 * matrix3dValue.m10
                            + matrix3d.m22 * matrix3dValue.m20;
            this.m21 =
                    matrix3d.m20 * matrix3dValue.m01
                            + matrix3d.m21 * matrix3dValue.m11
                            + matrix3d.m22 * matrix3dValue.m21;
            this.m22 =
                    matrix3d.m20 * matrix3dValue.m02
                            + matrix3d.m21 * matrix3dValue.m12
                            + matrix3d.m22 * matrix3dValue.m22;
            if (astring == null) {
                return;
            }
        }

        double d0 =
                matrix3d.m00 * matrix3dValue.m00
                        + matrix3d.m01 * matrix3dValue.m10
                        + matrix3d.m02 * matrix3dValue.m20;
        double d1 =
                matrix3d.m00 * matrix3dValue.m01
                        + matrix3d.m01 * matrix3dValue.m11
                        + matrix3d.m02 * matrix3dValue.m21;
        double d2 =
                matrix3d.m00 * matrix3dValue.m02
                        + matrix3d.m01 * matrix3dValue.m12
                        + matrix3d.m02 * matrix3dValue.m22;
        double d3 =
                matrix3d.m10 * matrix3dValue.m00
                        + matrix3d.m11 * matrix3dValue.m10
                        + matrix3d.m12 * matrix3dValue.m20;
        double d4 =
                matrix3d.m10 * matrix3dValue.m01
                        + matrix3d.m11 * matrix3dValue.m11
                        + matrix3d.m12 * matrix3dValue.m21;
        double d5 =
                matrix3d.m10 * matrix3dValue.m02
                        + matrix3d.m11 * matrix3dValue.m12
                        + matrix3d.m12 * matrix3dValue.m22;
        double d6 =
                matrix3d.m20 * matrix3dValue.m00
                        + matrix3d.m21 * matrix3dValue.m10
                        + matrix3d.m22 * matrix3dValue.m20;
        double d7 =
                matrix3d.m20 * matrix3dValue.m01
                        + matrix3d.m21 * matrix3dValue.m11
                        + matrix3d.m22 * matrix3dValue.m21;
        double d8 =
                matrix3d.m20 * matrix3dValue.m02
                        + matrix3d.m21 * matrix3dValue.m12
                        + matrix3d.m22 * matrix3dValue.m22;
        this.m00 = d0;
        this.m01 = d1;
        this.m02 = d2;
        this.m10 = d3;
        this.m11 = d4;
        this.m12 = d5;
        this.m20 = d6;
        this.m21 = d7;
        this.m22 = d8;
    }

    static {
        remix.compat.vecmath.Matrix3d.packedTextFragments = new String[8];
        remix.compat.vecmath.Matrix3d.packedTextFragments[0] = new String("\n");
        remix.compat.vecmath.Matrix3d.packedTextFragments[1] = new String("\n");
        remix.compat.vecmath.Matrix3d.packedTextFragments[2] = new String("\n");
        remix.compat.vecmath.Matrix3d.packedTextFragments[3] = new String(" ");
        remix.compat.vecmath.Matrix3d.packedTextFragments[4] = new String(" ");
        remix.compat.vecmath.Matrix3d.packedTextFragments[5] = new String("\n");
    }

    public Matrix3d(double[] p171_0) {
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

    final void getScaleRotate(double[] adouble, double[] adouble1) {
        double[] adouble2 =
                new double[] {
                    this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22
                };
        compute_svd(adouble2, adouble, adouble1);
    }

    static void svdReorder(
            double[] p171_0,
            double[] p171_1,
            double[] p171_2,
            double[] p171_3,
            double[] p171_4,
            double[] p171_5) {
        int[] var6 = new int[3];
        int[] var7 = new int[3];
        double[] var13 = new double[3];
        double[] var14 = new double[9];
        if (p171_3[0] < 0.0) {
            p171_3[0] = -p171_3[0];
            p171_2[0] = -p171_2[0];
            p171_2[1] = -p171_2[1];
            p171_2[2] = -p171_2[2];
        }

        if (p171_3[1] < 0.0) {
            p171_3[1] = -p171_3[1];
            p171_2[3] = -p171_2[3];
            p171_2[4] = -p171_2[4];
            p171_2[5] = -p171_2[5];
        }

        if (p171_3[2] < 0.0) {
            p171_3[2] = -p171_3[2];
            p171_2[6] = -p171_2[6];
            p171_2[7] = -p171_2[7];
            p171_2[8] = -p171_2[8];
        }

        remix.compat.vecmath.Matrix3d.multiply3x3Arrays(p171_1, p171_2, var14);
        if (remix.compat.vecmath.Matrix3d.method_92ec38fcaab9adf4c70a(
                        ((double) (java.lang.Math.abs(((double) (p171_3[0]))))),
                        ((double) (java.lang.Math.abs(((double) (p171_3[1]))))))
                && remix.compat.vecmath.Matrix3d.method_92ec38fcaab9adf4c70a(
                        ((double) (java.lang.Math.abs(((double) (p171_3[1]))))),
                        ((double) (java.lang.Math.abs(((double) (p171_3[2]))))))) {
            for (int var12 = 0; var12 < 9; var12++) {
                p171_4[var12] = var14[var12];
            }

            for (int var26 = 0; var26 < 3; var26++) {
                p171_5[var26] = p171_3[var26];
            }
        } else {
            if (p171_3[0] > p171_3[1]) {
                if (p171_3[0] > p171_3[2]) {
                    if (p171_3[2] > p171_3[1]) {
                        var6[0] = 0;
                        var6[1] = 2;
                        var6[2] = 1;
                    } else {
                        var6[0] = 0;
                        var6[1] = 1;
                        var6[2] = 2;
                    }
                } else {
                    var6[0] = 2;
                    var6[1] = 0;
                    var6[2] = 1;
                }
            } else if (p171_3[1] > p171_3[2]) {
                if (p171_3[2] > p171_3[0]) {
                    var6[0] = 1;
                    var6[1] = 2;
                    var6[2] = 0;
                } else {
                    var6[0] = 1;
                    var6[1] = 0;
                    var6[2] = 2;
                }
            } else {
                var6[0] = 2;
                var6[1] = 1;
                var6[2] = 0;
            }

            var13[0] = p171_0[0] * p171_0[0] + p171_0[1] * p171_0[1] + p171_0[2] * p171_0[2];
            var13[1] = p171_0[3] * p171_0[3] + p171_0[4] * p171_0[4] + p171_0[5] * p171_0[5];
            var13[2] = p171_0[6] * p171_0[6] + p171_0[7] * p171_0[7] + p171_0[8] * p171_0[8];
            byte var8;
            byte var9;
            byte var10;
            if (var13[0] > var13[1]) {
                if (var13[0] > var13[2]) {
                    if (var13[2] > var13[1]) {
                        var8 = 0;
                        var10 = 1;
                        var9 = 2;
                    } else {
                        var8 = 0;
                        var9 = 1;
                        var10 = 2;
                    }
                } else {
                    var10 = 0;
                    var8 = 1;
                    var9 = 2;
                }
            } else if (var13[1] > var13[2]) {
                if (var13[2] > var13[0]) {
                    var9 = 0;
                    var10 = 1;
                    var8 = 2;
                } else {
                    var9 = 0;
                    var8 = 1;
                    var10 = 2;
                }
            } else {
                var10 = 0;
                var9 = 1;
                var8 = 2;
            }

            int var11 = var6[var8];
            p171_5[0] = p171_3[var11];
            var11 = var6[var9];
            p171_5[1] = p171_3[var11];
            var11 = var6[var10];
            p171_5[2] = p171_3[var11];
            var11 = var6[var8];
            p171_4[0] = var14[var11];
            var11 = var6[var8] + 3;
            p171_4[3] = var14[var11];
            var11 = var6[var8] + 6;
            p171_4[6] = var14[var11];
            var11 = var6[var9];
            p171_4[1] = var14[var11];
            var11 = var6[var9] + 3;
            p171_4[4] = var14[var11];
            var11 = var6[var9] + 6;
            p171_4[7] = var14[var11];
            var11 = var6[var10];
            p171_4[2] = var14[var11];
            var11 = var6[var10] + 3;
            p171_4[5] = var14[var11];
            var11 = var6[var10] + 6;
            p171_4[8] = var14[var11];
        }
    }

    public final void setM21(double d0) {
        this.m21 = d0;
    }

    public final void transform(Tuple3d p171_0) {
        double d0 = this.m00 * p171_0.x + this.m01 * p171_0.y + this.m02 * p171_0.z;
        double d1 = this.m10 * p171_0.x + this.m11 * p171_0.y + this.m12 * p171_0.z;
        double d2 = this.m20 * p171_0.x + this.m21 * p171_0.y + this.m22 * p171_0.z;
        p171_0.set(d0, d1, d2);
    }

    public boolean equals(Matrix3d p171_0) {
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

    public final void setM12(double d0) {
        this.m12 = d0;
    }

    static double max3(double[] p171_0) {
        if (p171_0[0] > p171_0[1]) {
            return p171_0[0] > p171_0[2] ? p171_0[0] : p171_0[2];
        } else {
            return p171_0[1] > p171_0[2] ? p171_0[1] : p171_0[2];
        }
    }

    public final double getM02() {
        return this.m02;
    }

    // region String decoding support

    // endregion

    public final void setColumn(
            int i, Vector3d vector3d) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = vector3d.x;
                this.m10 = vector3d.y;
                this.m20 = vector3d.z;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m01 = vector3d.x;
                this.m11 = vector3d.y;
                this.m21 = vector3d.z;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m02 = vector3d.x;
                this.m12 = vector3d.y;
                this.m22 = vector3d.z;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix3d9"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d9"));
        }
    }

    public final void sub(
            Matrix3d p171_0, Matrix3d p171_1) {
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

    public Matrix3d(Matrix3f p171_0) {
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

    @Override
    public Object clone() {

        try {
            return (remix.compat.vecmath.Matrix3d) super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }
    }

    public final void set(AxisAngle4f p171_0) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        double d0 =
                java.lang.Math.sqrt(
                        p171_0.x * p171_0.x + p171_0.y * p171_0.y + p171_0.z * p171_0.z);
        java.lang.String[] astring = astring1;

        try {
            if (d0 < 1.110223024E-16) {
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
                    return;
                }
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
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

    public final double getM00() {
        return this.m00;
    }

    static void print_det(double[] adouble) {
        double d0 =
                adouble[0] * adouble[4] * adouble[8]
                        + adouble[1] * adouble[5] * adouble[6]
                        + adouble[2] * adouble[3] * adouble[7]
                        - adouble[2] * adouble[4] * adouble[6]
                        - adouble[0] * adouble[5] * adouble[7]
                        - adouble[1] * adouble[3] * adouble[8];
        System.out.println("det= " + d0);
    }

    public final double getM11() {
        return this.m11;
    }

    public final double getScale() {
        double[] adouble = new double[3];
        double[] adouble1 = new double[9];
        this.getScaleRotate(adouble, adouble1);
        return remix.compat.vecmath.Matrix3d.max3(adouble);
    }

    public final void dn() {
        double d0 =
                1.0
                        / java.lang.Math.sqrt(
                                this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20);
        this.m00 *= d0;
        this.m10 *= d0;
        this.m20 *= d0;
        d0 = 1.0 / java.lang.Math.sqrt(this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21);
        this.m01 *= d0;
        this.m11 *= d0;
        this.m21 *= d0;
        this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
        this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
        this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
    }

    // region String decoding support

    // endregion

    public final void invert(
            Matrix3d matrix3d,
            Matrix3d matrix3dValue) {
        String[] astring = Tuple3f.hF();
        if (this != matrix3d && this != matrix3dValue) {
            this.m00 =
                    matrix3d.m00 * matrix3dValue.m00
                            + matrix3d.m10 * matrix3dValue.m01
                            + matrix3d.m20 * matrix3dValue.m02;
            this.m01 =
                    matrix3d.m00 * matrix3dValue.m10
                            + matrix3d.m10 * matrix3dValue.m11
                            + matrix3d.m20 * matrix3dValue.m12;
            this.m02 =
                    matrix3d.m00 * matrix3dValue.m20
                            + matrix3d.m10 * matrix3dValue.m21
                            + matrix3d.m20 * matrix3dValue.m22;
            this.m10 =
                    matrix3d.m01 * matrix3dValue.m00
                            + matrix3d.m11 * matrix3dValue.m01
                            + matrix3d.m21 * matrix3dValue.m02;
            this.m11 =
                    matrix3d.m01 * matrix3dValue.m10
                            + matrix3d.m11 * matrix3dValue.m11
                            + matrix3d.m21 * matrix3dValue.m12;
            this.m12 =
                    matrix3d.m01 * matrix3dValue.m20
                            + matrix3d.m11 * matrix3dValue.m21
                            + matrix3d.m21 * matrix3dValue.m22;
            this.m20 =
                    matrix3d.m02 * matrix3dValue.m00
                            + matrix3d.m12 * matrix3dValue.m01
                            + matrix3d.m22 * matrix3dValue.m02;
            this.m21 =
                    matrix3d.m02 * matrix3dValue.m10
                            + matrix3d.m12 * matrix3dValue.m11
                            + matrix3d.m22 * matrix3dValue.m12;
            this.m22 =
                    matrix3d.m02 * matrix3dValue.m20
                            + matrix3d.m12 * matrix3dValue.m21
                            + matrix3d.m22 * matrix3dValue.m22;
            if (astring == null) {
                return;
            }
        }

        double d0 =
                matrix3d.m00 * matrix3dValue.m00
                        + matrix3d.m10 * matrix3dValue.m01
                        + matrix3d.m20 * matrix3dValue.m02;
        double d1 =
                matrix3d.m00 * matrix3dValue.m10
                        + matrix3d.m10 * matrix3dValue.m11
                        + matrix3d.m20 * matrix3dValue.m12;
        double d2 =
                matrix3d.m00 * matrix3dValue.m20
                        + matrix3d.m10 * matrix3dValue.m21
                        + matrix3d.m20 * matrix3dValue.m22;
        double d3 =
                matrix3d.m01 * matrix3dValue.m00
                        + matrix3d.m11 * matrix3dValue.m01
                        + matrix3d.m21 * matrix3dValue.m02;
        double d4 =
                matrix3d.m01 * matrix3dValue.m10
                        + matrix3d.m11 * matrix3dValue.m11
                        + matrix3d.m21 * matrix3dValue.m12;
        double d5 =
                matrix3d.m01 * matrix3dValue.m20
                        + matrix3d.m11 * matrix3dValue.m21
                        + matrix3d.m21 * matrix3dValue.m22;
        double d6 =
                matrix3d.m02 * matrix3dValue.m00
                        + matrix3d.m12 * matrix3dValue.m01
                        + matrix3d.m22 * matrix3dValue.m02;
        double d7 =
                matrix3d.m02 * matrix3dValue.m10
                        + matrix3d.m12 * matrix3dValue.m11
                        + matrix3d.m22 * matrix3dValue.m12;
        double d8 =
                matrix3d.m02 * matrix3dValue.m20
                        + matrix3d.m12 * matrix3dValue.m21
                        + matrix3d.m22 * matrix3dValue.m22;
        this.m00 = d0;
        this.m01 = d1;
        this.m02 = d2;
        this.m10 = d3;
        this.m11 = d4;
        this.m12 = d5;
        this.m20 = d6;
        this.m21 = d7;
        this.m22 = d8;
    }

    public final void mulNormalize(
            Matrix3d p171_0, Matrix3d p171_1) {
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
        this.m00 = adouble1[0];
        this.m01 = adouble1[1];
        this.m02 = adouble1[2];
        this.m10 = adouble1[3];
        this.m11 = adouble1[4];
        this.m12 = adouble1[5];
        this.m20 = adouble1[6];
        this.m21 = adouble1[7];
        this.m22 = adouble1[8];
    }

    public final double getM22() {
        return this.m22;
    }

    static void multiply3x3Arrays(
            double[] adouble, double[] adouble1, double[] adouble2) {
        double[] adouble3 =
                new double[] {
                    adouble[0] * adouble1[0] + adouble[1] * adouble1[3] + adouble[2] * adouble1[6],
                    adouble[0] * adouble1[1] + adouble[1] * adouble1[4] + adouble[2] * adouble1[7],
                    adouble[0] * adouble1[2] + adouble[1] * adouble1[5] + adouble[2] * adouble1[8],
                    adouble[3] * adouble1[0] + adouble[4] * adouble1[3] + adouble[5] * adouble1[6],
                    adouble[3] * adouble1[1] + adouble[4] * adouble1[4] + adouble[5] * adouble1[7],
                    adouble[3] * adouble1[2] + adouble[4] * adouble1[5] + adouble[5] * adouble1[8],
                    adouble[6] * adouble1[0] + adouble[7] * adouble1[3] + adouble[8] * adouble1[6],
                    adouble[6] * adouble1[1] + adouble[7] * adouble1[4] + adouble[8] * adouble1[7],
                    adouble[6] * adouble1[2] + adouble[7] * adouble1[5] + adouble[8] * adouble1[8]
                };

        for (int i = 0; i < 9; i++) {
            adouble2[i] = adouble3[i];
        }
    }

    public final void subtractInPlace(Matrix3d p171_0) {
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

    public final void mul(
            double p171_0, Matrix3d p171_1) {
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

    public final void setM11(double d0) {
        this.m11 = d0;
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Matrix3d matrix3d =
                    (remix.compat.vecmath.Matrix3d) p171_0;
            return (this.m00 == matrix3d.m00
                    && this.m01 == matrix3d.m01
                    && this.m02 == matrix3d.m02
                    && this.m10 == matrix3d.m10
                    && this.m11 == matrix3d.m11
                    && this.m12 == matrix3d.m12
                    && this.m20 == matrix3d.m20
                    && this.m21 == matrix3d.m21
                    && this.m22 == matrix3d.m22);
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final void rotY(double p171_0) {
        double d1 = java.lang.Math.sin(p171_0);
        double d2 = java.lang.Math.cos(p171_0);
        this.m00 = d2;
        this.m01 = 0.0;
        this.m02 = d1;
        this.m10 = 0.0;
        this.m11 = 1.0;
        this.m12 = 0.0;
        this.m20 = -d1;
        this.m21 = 0.0;
        this.m22 = d2;
    }

    public final double getM21() {
        return this.m21;
    }

    public final void setRow(int i, double[] adouble) {
        String[] astring = Tuple3f.hF();
        if (i == 0) {
            adouble[0] = this.m00;
            adouble[1] = this.m01;
            adouble[2] = this.m02;
            if (astring == null) {
                return;
            }
        }

        if (i == 1) {
            adouble[0] = this.m10;
            adouble[1] = this.m11;
            adouble[2] = this.m12;
            if (astring == null) {
                return;
            }
        }

        if (i != 2) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d2"));
        }

        adouble[0] = this.m20;
        adouble[1] = this.m21;
        adouble[2] = this.m22;
        if (astring != null) {
            throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d2"));
        }
    }

    public final void set(double[] p171_0) {
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

    public final void set(
            AxisAngle4d axisAngle4d) {
        String[] astring1 = Tuple3f.hF();
        double d0 =
                Math.sqrt(
                        axisAngle4d.x * axisAngle4d.x
                                + axisAngle4d.y * axisAngle4d.y
                                + axisAngle4d.z * axisAngle4d.z);
        String[] astring = astring1;
        if (d0 < 1.110223024E-16) {
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

    public final void getColumn(
            int i, Vector3d vector3d) {
        String[] astring = Tuple3f.hF();
        switch (i) {
            case 0:
                this.m00 = vector3d.x;
                this.m01 = vector3d.y;
                this.m02 = vector3d.z;
                if (astring == null) {
                    break;
                }
            case 1:
                this.m10 = vector3d.x;
                this.m11 = vector3d.y;
                this.m12 = vector3d.z;
                if (astring == null) {
                    break;
                }
            case 2:
                this.m20 = vector3d.x;
                this.m21 = vector3d.y;
                this.m22 = vector3d.z;
                if (astring != null) {
                    throw new ArrayIndexOutOfBoundsException(
                            VecMathI18N.getString("Matrix3d6"));
                }
                break;
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d6"));
        }
    }

    public final void setM00(double d0) {
        this.m00 = d0;
    }

    public final void normalizeCP(Matrix3d p171_0) {
        double d0 =
                1.0
                        / java.lang.Math.sqrt(
                                p171_0.m00 * p171_0.m00
                                        + p171_0.m10 * p171_0.m10
                                        + p171_0.m20 * p171_0.m20);
        this.m00 = p171_0.m00 * d0;
        this.m10 = p171_0.m10 * d0;
        this.m20 = p171_0.m20 * d0;
        d0 =
                1.0
                        / java.lang.Math.sqrt(
                                p171_0.m01 * p171_0.m01
                                        + p171_0.m11 * p171_0.m11
                                        + p171_0.m21 * p171_0.m21);
        this.m01 = p171_0.m01 * d0;
        this.m11 = p171_0.m11 * d0;
        this.m21 = p171_0.m21 * d0;
        this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
        this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
        this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
    }

    public final void mulNormalize(Matrix3d p171_0) {
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
        this.m00 = adouble1[0];
        this.m01 = adouble1[1];
        this.m02 = adouble1[2];
        this.m10 = adouble1[3];
        this.m11 = adouble1[4];
        this.m12 = adouble1[5];
        this.m20 = adouble1[6];
        this.m21 = adouble1[7];
        this.m22 = adouble1[8];
    }

    static void print_mat(double[] p171_0) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        int i = 0;
        java.lang.String[] astring = astring1;

        while (i < 3) {
            java.lang.System.out.println(
                    p171_0[i * 3 + 0]
                            + remix.compat.vecmath.Matrix3d.packedTextFragments[3]
                            + p171_0[i * 3 + 1]
                            + remix.compat.vecmath.Matrix3d.packedTextFragments[4]
                            + p171_0[i * 3 + 2]
                            + remix.compat.vecmath.Matrix3d.packedTextFragments[5]);
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void transpose(Matrix3d p171_0) {
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

    public Matrix3d() {
        this.m00 = 0.0;
        this.m01 = 0.0;
        this.m02 = 0.0;
        this.m10 = 0.0;
        this.m11 = 0.0;
        this.m12 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 0.0;
    }

    static boolean luDecomposition(double[] adouble, int[] aint) {
        String[] astring1 = Tuple3f.hF();
        double[] adouble1 = new double[3];
        int k = 0;
        int l = 0;
        String[] astring = astring1;
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
                throw new RuntimeException(VecMathI18N.getString("Matrix3d13"));
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

    public final double getM01() {
        return this.m01;
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
    }

    static double compute_shift(double p171_0, double p171_1, double p171_2) {
        double d8 = java.lang.Math.abs(p171_0);
        double d9 = java.lang.Math.abs(p171_1);
        double d10 = java.lang.Math.abs(p171_2);
        double d5 = remix.compat.vecmath.Matrix3d.method_1729c4b03acd01b02385(d8, d10);
        double d6 = remix.compat.vecmath.Matrix3d.min(d8, d10);
        double d14;
        if (d5 == 0.0) {
            d14 = 0.0;

            try {
                if (d6 == 0.0) {
                    return d14;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception);
            }

            double d3 =
                    remix.compat.vecmath.Matrix3d.method_1729c4b03acd01b02385(d6, d9)
                            / remix.compat.vecmath.Matrix3d.min(d6, d9);
        } else if (d9 < d6) {
            double d11 = d5 / d6 + 1.0;
            double d12 = (d6 - d5) / d6;
            double d15 = d9 / d6;
            double d13 = d15 * d15;
            double d7 =
                    2.0
                            / (java.lang.Math.sqrt(d11 * d11 + d13)
                                    + java.lang.Math.sqrt(d12 * d12 + d13));
            d14 = d5 * d7;
        } else {
            double d20 = d6 / d9;
            if (d20 == 0.0) {
                d14 = d5 * d6 / d9;
            } else {
                double d18 = d5 / d6 + 1.0;
                double d19 = (d6 - d5) / d6;
                double d16 = d18 * d20;
                double d4 = d19 * d20;
                double d17 =
                        1.0
                                / (java.lang.Math.sqrt(d16 * d16 + 1.0)
                                        + java.lang.Math.sqrt(d4 * d4 + 1.0));
                d14 = d5 * d17 * d20;
                d14 += d14;
            }
        }

        return d14;
    }

    public final void normalize(Matrix3d p171_0) {
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
        this.m00 = adouble1[0];
        this.m01 = adouble1[1];
        this.m02 = adouble1[2];
        this.m10 = adouble1[3];
        this.m11 = adouble1[4];
        this.m12 = adouble1[5];
        this.m20 = adouble1[6];
        this.m21 = adouble1[7];
        this.m22 = adouble1[8];
    }

    public final double ev() {
        return this.m00 * (this.m11 * this.m22 - this.m12 * this.m21)
                + this.m01 * (this.m12 * this.m20 - this.m10 * this.m22)
                + this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
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
    }

    static void transpose_mat(double[] p171_0, double[] p171_1) {
        p171_1[0] = p171_0[0];
        p171_1[1] = p171_0[3];
        p171_1[2] = p171_0[6];
        p171_1[3] = p171_0[1];
        p171_1[4] = p171_0[4];
        p171_1[5] = p171_0[7];
        p171_1[6] = p171_0[2];
        p171_1[7] = p171_0[5];
        p171_1[8] = p171_0[8];
    }

    public final void invertGeneral(
            Matrix3d matrix3d) {
        this.mulTransposeRight(matrix3d);
    }

    public final void rotZ(double p171_0) {
        double d1 = java.lang.Math.sin(p171_0);
        double d2 = java.lang.Math.cos(p171_0);
        this.m00 = d2;
        this.m01 = -d1;
        this.m02 = 0.0;
        this.m10 = d1;
        this.m11 = d2;
        this.m12 = 0.0;
        this.m20 = 0.0;
        this.m21 = 0.0;
        this.m22 = 1.0;
    }

    public final void negate(Matrix3d p171_0) {
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

    static int compute_qr(
            double[] p171_0, double[] p171_1, double[] p171_2, double[] p171_3) {
        double[] adouble4 = new double[2];
        double[] adouble5 = new double[2];
        double[] adouble6 = new double[2];
        double[] adouble7 = new double[2];
        double[] adouble8 = new double[9];

        double d7 = 1.0;

        boolean flag = false;
        int k = 1;

        {
            boolean runGuardedCode;
            try {
                runGuardedCode = !(!(java.lang.Math.abs(p171_1[1]) < 4.89E-15)
                        && !(java.lang.Math.abs(p171_1[0]) < 4.89E-15));
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception2) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception2);
            }
            if (runGuardedCode) {

                flag = true;
            }
        }

        int i = 0;

        while (true) {
            try {
                if (i >= 10 || (flag)) {
                    break;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception);
            }

            double d0 =
                    remix.compat.vecmath.Matrix3d.compute_shift(
                            p171_0[1], p171_1[1], p171_0[2]);
            double d4 =
                    (java.lang.Math.abs(p171_0[0]) - d0)
                            * (remix.compat.vecmath.Matrix3d.d_sign(
                                            d7, p171_0[0])
                                    + d0 / p171_0[0]);
            double d5 = p171_1[0];
            double d1 =
                    remix.compat.vecmath.Matrix3d.compute_rot(
                            d4, d5, adouble7, adouble5, 0, k);
            d4 = adouble5[0] * p171_0[0] + adouble7[0] * p171_1[0];
            p171_1[0] = adouble5[0] * p171_1[0] - adouble7[0] * p171_0[0];
            d5 = adouble7[0] * p171_0[1];
            p171_0[1] = adouble5[0] * p171_0[1];
            d1 =
                    remix.compat.vecmath.Matrix3d.compute_rot(
                            d4, d5, adouble6, adouble4, 0, k);
            k = 0;
            p171_0[0] = d1;
            d4 = adouble4[0] * p171_1[0] + adouble6[0] * p171_0[1];
            p171_0[1] = adouble4[0] * p171_0[1] - adouble6[0] * p171_1[0];
            d5 = adouble6[0] * p171_1[1];
            p171_1[1] = adouble4[0] * p171_1[1];
            d1 =
                    remix.compat.vecmath.Matrix3d.compute_rot(
                            d4, d5, adouble7, adouble5, 1, k);
            p171_1[0] = d1;
            d4 = adouble5[1] * p171_0[1] + adouble7[1] * p171_1[1];
            p171_1[1] = adouble5[1] * p171_1[1] - adouble7[1] * p171_0[1];
            d5 = adouble7[1] * p171_0[2];
            p171_0[2] = adouble5[1] * p171_0[2];
            d1 =
                    remix.compat.vecmath.Matrix3d.compute_rot(
                            d4, d5, adouble6, adouble4, 1, k);
            p171_0[1] = d1;
            d4 = adouble4[1] * p171_1[1] + adouble6[1] * p171_0[2];
            p171_0[2] = adouble4[1] * p171_0[2] - adouble6[1] * p171_1[1];
            p171_1[1] = d4;
            double d2 = p171_2[0];
            p171_2[0] = adouble4[0] * d2 + adouble6[0] * p171_2[3];
            p171_2[3] = -adouble6[0] * d2 + adouble4[0] * p171_2[3];
            d2 = p171_2[1];
            p171_2[1] = adouble4[0] * d2 + adouble6[0] * p171_2[4];
            p171_2[4] = -adouble6[0] * d2 + adouble4[0] * p171_2[4];
            d2 = p171_2[2];
            p171_2[2] = adouble4[0] * d2 + adouble6[0] * p171_2[5];
            p171_2[5] = -adouble6[0] * d2 + adouble4[0] * p171_2[5];
            d2 = p171_2[3];
            p171_2[3] = adouble4[1] * d2 + adouble6[1] * p171_2[6];
            p171_2[6] = -adouble6[1] * d2 + adouble4[1] * p171_2[6];
            d2 = p171_2[4];
            p171_2[4] = adouble4[1] * d2 + adouble6[1] * p171_2[7];
            p171_2[7] = -adouble6[1] * d2 + adouble4[1] * p171_2[7];
            d2 = p171_2[5];
            p171_2[5] = adouble4[1] * d2 + adouble6[1] * p171_2[8];
            p171_2[8] = -adouble6[1] * d2 + adouble4[1] * p171_2[8];
            double d3 = p171_3[0];
            p171_3[0] = adouble5[0] * d3 + adouble7[0] * p171_3[1];
            p171_3[1] = -adouble7[0] * d3 + adouble5[0] * p171_3[1];
            d3 = p171_3[3];
            p171_3[3] = adouble5[0] * d3 + adouble7[0] * p171_3[4];
            p171_3[4] = -adouble7[0] * d3 + adouble5[0] * p171_3[4];
            d3 = p171_3[6];
            p171_3[6] = adouble5[0] * d3 + adouble7[0] * p171_3[7];
            p171_3[7] = -adouble7[0] * d3 + adouble5[0] * p171_3[7];
            d3 = p171_3[1];
            p171_3[1] = adouble5[1] * d3 + adouble7[1] * p171_3[2];
            p171_3[2] = -adouble7[1] * d3 + adouble5[1] * p171_3[2];
            d3 = p171_3[4];
            p171_3[4] = adouble5[1] * d3 + adouble7[1] * p171_3[5];
            p171_3[5] = -adouble7[1] * d3 + adouble5[1] * p171_3[5];
            d3 = p171_3[7];

            {
                boolean runGuardedCode;
                try {
                    p171_3[7] = adouble5[1] * d3 + adouble7[1] * p171_3[8];
                    p171_3[8] = -adouble7[1] * d3 + adouble5[1] * p171_3[8];
                    adouble8[0] = p171_0[0];
                    adouble8[1] = p171_1[0];
                    adouble8[2] = 0.0;
                    adouble8[3] = 0.0;
                    adouble8[4] = p171_0[1];
                    adouble8[5] = p171_1[1];
                    adouble8[6] = 0.0;
                    adouble8[7] = 0.0;
                    adouble8[8] = p171_0[2];
                    runGuardedCode = !(!(java.lang.Math.abs(p171_1[1]) < 4.89E-15)
                            && !(java.lang.Math.abs(p171_1[0]) < 4.89E-15));
                } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception1) {
                    throw remix.compat.vecmath.Matrix3d.wrapException(
                            arrayindexoutofboundsexception1);
                }
                if (runGuardedCode) {

                    flag = true;
                }
            }

            i++;
        }

        if (java.lang.Math.abs(p171_1[1]) < 4.89E-15) {
            remix.compat.vecmath.Matrix3d.compute_2X2(
                    p171_0[0], p171_1[0], p171_0[1], p171_0, adouble6, adouble4, adouble7, adouble5,
                    0);
            double d9 = p171_2[0];
            p171_2[0] = adouble4[0] * d9 + adouble6[0] * p171_2[3];
            p171_2[3] = -adouble6[0] * d9 + adouble4[0] * p171_2[3];
            d9 = p171_2[1];
            p171_2[1] = adouble4[0] * d9 + adouble6[0] * p171_2[4];
            p171_2[4] = -adouble6[0] * d9 + adouble4[0] * p171_2[4];
            d9 = p171_2[2];
            p171_2[2] = adouble4[0] * d9 + adouble6[0] * p171_2[5];
            p171_2[5] = -adouble6[0] * d9 + adouble4[0] * p171_2[5];
            double d11 = p171_3[0];
            p171_3[0] = adouble5[0] * d11 + adouble7[0] * p171_3[1];
            p171_3[1] = -adouble7[0] * d11 + adouble5[0] * p171_3[1];
            d11 = p171_3[3];
            p171_3[3] = adouble5[0] * d11 + adouble7[0] * p171_3[4];
            p171_3[4] = -adouble7[0] * d11 + adouble5[0] * p171_3[4];
            d11 = p171_3[6];
            p171_3[6] = adouble5[0] * d11 + adouble7[0] * p171_3[7];
            p171_3[7] = -adouble7[0] * d11 + adouble5[0] * p171_3[7];
        } else {
            remix.compat.vecmath.Matrix3d.compute_2X2(
                    p171_0[1], p171_1[1], p171_0[2], p171_0, adouble6, adouble4, adouble7, adouble5,
                    1);
            double d10 = p171_2[3];
            p171_2[3] = adouble4[0] * d10 + adouble6[0] * p171_2[6];
            p171_2[6] = -adouble6[0] * d10 + adouble4[0] * p171_2[6];
            d10 = p171_2[4];
            p171_2[4] = adouble4[0] * d10 + adouble6[0] * p171_2[7];
            p171_2[7] = -adouble6[0] * d10 + adouble4[0] * p171_2[7];
            d10 = p171_2[5];
            p171_2[5] = adouble4[0] * d10 + adouble6[0] * p171_2[8];
            p171_2[8] = -adouble6[0] * d10 + adouble4[0] * p171_2[8];
            double d12 = p171_3[1];
            p171_3[1] = adouble5[0] * d12 + adouble7[0] * p171_3[2];
            p171_3[2] = -adouble7[0] * d12 + adouble5[0] * p171_3[2];
            d12 = p171_3[4];
            p171_3[4] = adouble5[0] * d12 + adouble7[0] * p171_3[5];
            p171_3[5] = -adouble7[0] * d12 + adouble5[0] * p171_3[5];
            d12 = p171_3[7];
            p171_3[7] = adouble5[0] * d12 + adouble7[0] * p171_3[8];
            p171_3[8] = -adouble7[0] * d12 + adouble5[0] * p171_3[8];
        }

        return 0;
    }

    public final void add(double p171_0) {
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

    public final void add(
            Matrix3d p171_0, Matrix3d p171_1) {
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
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix3d0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix3d0"));
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
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix3d0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix3d0"));
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
                        if (astring != null) {
                            throw new ArrayIndexOutOfBoundsException(
                                    VecMathI18N.getString("Matrix3d0"));
                        }

                        return;
                    default:
                        throw new ArrayIndexOutOfBoundsException(
                                VecMathI18N.getString("Matrix3d0"));
                }
            default:
                throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3d0"));
        }
    }

    public Matrix3d(
            double p171_0,
            double p171_1,
            double p171_2,
            double p171_3,
            double p171_4,
            double p171_5,
            double p171_6,
            double p171_7,
            double p171_8) {
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

    static void compute_svd(double[] p171_0, double[] p171_1, double[] p171_2) {
        double[] var9 = new double[9];
        double[] var10 = new double[9];
        double[] var11 = new double[9];
        double[] var12 = new double[9];
        double[] var13 = var11;
        double[] var14 = var12;
        double[] var15 = new double[9];
        double[] var16 = new double[3];
        double[] var17 = new double[3];
        int var19 = 0;

        for (int var3 = 0; var3 < 9; var3++) {
            var15[var3] = p171_0[var3];
        }

        if (p171_0[3] * p171_0[3] < 1.110223024E-16) {
            var9[0] = 1.0;
            var9[1] = 0.0;
            var9[2] = 0.0;
            var9[3] = 0.0;
            var9[4] = 1.0;
            var9[5] = 0.0;
            var9[6] = 0.0;
            var9[7] = 0.0;
            var9[8] = 1.0;
        } else if (p171_0[0] * p171_0[0] < 1.110223024E-16) {
            var13[0] = p171_0[0];
            var13[1] = p171_0[1];
            var13[2] = p171_0[2];
            p171_0[0] = p171_0[3];
            p171_0[1] = p171_0[4];
            p171_0[2] = p171_0[5];
            p171_0[3] = -var13[0];
            p171_0[4] = -var13[1];
            p171_0[5] = -var13[2];
            var9[0] = 0.0;
            var9[1] = 1.0;
            var9[2] = 0.0;
            var9[3] = -1.0;
            var9[4] = 0.0;
            var9[5] = 0.0;
            var9[6] = 0.0;
            var9[7] = 0.0;
            var9[8] = 1.0;
        } else {
            double var5 =
                    1.0
                            / ((double)
                                    (java.lang.Math.sqrt(
                                            ((double)
                                                    (p171_0[0] * p171_0[0]
                                                            + p171_0[3] * p171_0[3])))));
            double var24 = p171_0[0] * var5;
            double var32 = p171_0[3] * var5;
            var13[0] = var24 * p171_0[0] + var32 * p171_0[3];
            var13[1] = var24 * p171_0[1] + var32 * p171_0[4];
            var13[2] = var24 * p171_0[2] + var32 * p171_0[5];
            p171_0[3] = -var32 * p171_0[0] + var24 * p171_0[3];
            p171_0[4] = -var32 * p171_0[1] + var24 * p171_0[4];
            p171_0[5] = -var32 * p171_0[2] + var24 * p171_0[5];
            p171_0[0] = var13[0];
            p171_0[1] = var13[1];
            p171_0[2] = var13[2];
            var9[0] = var24;
            var9[1] = var32;
            var9[2] = 0.0;
            var9[3] = -var32;
            var9[4] = var24;
            var9[5] = 0.0;
            var9[6] = 0.0;
            var9[7] = 0.0;
            var9[8] = 1.0;
        }

        if (!(p171_0[6] * p171_0[6] < 1.110223024E-16)) {
            if (p171_0[0] * p171_0[0] < 1.110223024E-16) {
                var13[0] = p171_0[0];
                var13[1] = p171_0[1];
                var13[2] = p171_0[2];
                p171_0[0] = p171_0[6];
                p171_0[1] = p171_0[7];
                p171_0[2] = p171_0[8];
                p171_0[6] = -var13[0];
                p171_0[7] = -var13[1];
                p171_0[8] = -var13[2];
                var13[0] = var9[0];
                var13[1] = var9[1];
                var13[2] = var9[2];
                var9[0] = var9[6];
                var9[1] = var9[7];
                var9[2] = var9[8];
                var9[6] = -var13[0];
                var9[7] = -var13[1];
                var9[8] = -var13[2];
            } else {
                double var42 =
                        1.0
                                / ((double)
                                        (java.lang.Math.sqrt(
                                                ((double)
                                                        (p171_0[0] * p171_0[0]
                                                                + p171_0[6] * p171_0[6])))));
                double var26 = p171_0[0] * var42;
                double var34 = p171_0[6] * var42;
                var13[0] = var26 * p171_0[0] + var34 * p171_0[6];
                var13[1] = var26 * p171_0[1] + var34 * p171_0[7];
                var13[2] = var26 * p171_0[2] + var34 * p171_0[8];
                p171_0[6] = -var34 * p171_0[0] + var26 * p171_0[6];
                p171_0[7] = -var34 * p171_0[1] + var26 * p171_0[7];
                p171_0[8] = -var34 * p171_0[2] + var26 * p171_0[8];
                p171_0[0] = var13[0];
                p171_0[1] = var13[1];
                p171_0[2] = var13[2];
                var13[0] = var26 * var9[0];
                var13[1] = var26 * var9[1];
                var9[2] = var34;
                var13[6] = -var9[0] * var34;
                var13[7] = -var9[1] * var34;
                var9[8] = var26;
                var9[0] = var13[0];
                var9[1] = var13[1];
                var9[6] = var13[6];
                var9[7] = var13[7];
            }
        }

        if (p171_0[2] * p171_0[2] < 1.110223024E-16) {
            var10[0] = 1.0;
            var10[1] = 0.0;
            var10[2] = 0.0;
            var10[3] = 0.0;
            var10[4] = 1.0;
            var10[5] = 0.0;
            var10[6] = 0.0;
            var10[7] = 0.0;
            var10[8] = 1.0;
        } else if (p171_0[1] * p171_0[1] < 1.110223024E-16) {
            var13[2] = p171_0[2];
            var13[5] = p171_0[5];
            var13[8] = p171_0[8];
            p171_0[2] = -p171_0[1];
            p171_0[5] = -p171_0[4];
            p171_0[8] = -p171_0[7];
            p171_0[1] = var13[2];
            p171_0[4] = var13[5];
            p171_0[7] = var13[8];
            var10[0] = 1.0;
            var10[1] = 0.0;
            var10[2] = 0.0;
            var10[3] = 0.0;
            var10[4] = 0.0;
            var10[5] = -1.0;
            var10[6] = 0.0;
            var10[7] = 1.0;
            var10[8] = 0.0;
        } else {
            double var43 =
                    1.0
                            / ((double)
                                    (java.lang.Math.sqrt(
                                            ((double)
                                                    (p171_0[1] * p171_0[1]
                                                            + p171_0[2] * p171_0[2])))));
            double var28 = p171_0[1] * var43;
            double var36 = p171_0[2] * var43;
            var13[1] = var28 * p171_0[1] + var36 * p171_0[2];
            p171_0[2] = -var36 * p171_0[1] + var28 * p171_0[2];
            p171_0[1] = var13[1];
            var13[4] = var28 * p171_0[4] + var36 * p171_0[5];
            p171_0[5] = -var36 * p171_0[4] + var28 * p171_0[5];
            p171_0[4] = var13[4];
            var13[7] = var28 * p171_0[7] + var36 * p171_0[8];
            p171_0[8] = -var36 * p171_0[7] + var28 * p171_0[8];
            p171_0[7] = var13[7];
            var10[0] = 1.0;
            var10[1] = 0.0;
            var10[2] = 0.0;
            var10[3] = 0.0;
            var10[4] = var28;
            var10[5] = -var36;
            var10[6] = 0.0;
            var10[7] = var36;
            var10[8] = var28;
        }

        if (!(p171_0[7] * p171_0[7] < 1.110223024E-16)) {
            if (p171_0[4] * p171_0[4] < 1.110223024E-16) {
                var13[3] = p171_0[3];
                var13[4] = p171_0[4];
                var13[5] = p171_0[5];
                p171_0[3] = p171_0[6];
                p171_0[4] = p171_0[7];
                p171_0[5] = p171_0[8];
                p171_0[6] = -var13[3];
                p171_0[7] = -var13[4];
                p171_0[8] = -var13[5];
                var13[3] = var9[3];
                var13[4] = var9[4];
                var13[5] = var9[5];
                var9[3] = var9[6];
                var9[4] = var9[7];
                var9[5] = var9[8];
                var9[6] = -var13[3];
                var9[7] = -var13[4];
                var9[8] = -var13[5];
            } else {
                double var44 =
                        1.0
                                / ((double)
                                        (java.lang.Math.sqrt(
                                                ((double)
                                                        (p171_0[4] * p171_0[4]
                                                                + p171_0[7] * p171_0[7])))));
                double var30 = p171_0[4] * var44;
                double var38 = p171_0[7] * var44;
                var13[3] = var30 * p171_0[3] + var38 * p171_0[6];
                p171_0[6] = -var38 * p171_0[3] + var30 * p171_0[6];
                p171_0[3] = var13[3];
                var13[4] = var30 * p171_0[4] + var38 * p171_0[7];
                p171_0[7] = -var38 * p171_0[4] + var30 * p171_0[7];
                p171_0[4] = var13[4];
                var13[5] = var30 * p171_0[5] + var38 * p171_0[8];
                p171_0[8] = -var38 * p171_0[5] + var30 * p171_0[8];
                p171_0[5] = var13[5];
                var13[3] = var30 * var9[3] + var38 * var9[6];
                var9[6] = -var38 * var9[3] + var30 * var9[6];
                var9[3] = var13[3];
                var13[4] = var30 * var9[4] + var38 * var9[7];
                var9[7] = -var38 * var9[4] + var30 * var9[7];
                var9[4] = var13[4];
                var13[5] = var30 * var9[5] + var38 * var9[8];
                var9[8] = -var38 * var9[5] + var30 * var9[8];
                var9[5] = var13[5];
            }
        }

        var14[0] = p171_0[0];
        var14[1] = p171_0[4];
        var14[2] = p171_0[8];
        var16[0] = p171_0[1];
        var16[1] = p171_0[5];
        if (!(var16[0] * var16[0] < 1.110223024E-16) || !(var16[1] * var16[1] < 1.110223024E-16)) {
            remix.compat.vecmath.Matrix3d.compute_qr(var14, var16, var9, var10);
        }

        var17[0] = var14[0];
        var17[1] = var14[1];
        var17[2] = var14[2];
        if (remix.compat.vecmath.Matrix3d.method_92ec38fcaab9adf4c70a(
                        ((double) (java.lang.Math.abs(((double) (var17[0]))))), 1.0)
                && remix.compat.vecmath.Matrix3d.method_92ec38fcaab9adf4c70a(
                        ((double) (java.lang.Math.abs(((double) (var17[1]))))), 1.0)
                && remix.compat.vecmath.Matrix3d.method_92ec38fcaab9adf4c70a(
                        ((double) (java.lang.Math.abs(((double) (var17[2]))))), 1.0)) {
            for (int var40 = 0; var40 < 3; var40++) {
                if (var17[var40] < 0.0) {
                    var19++;
                }
            }

            if (var19 == 0 || var19 == 2) {
                p171_1[0] = p171_1[1] = p171_1[2] = 1.0;

                for (int var41 = 0; var41 < 9; var41++) {
                    p171_2[var41] = var15[var41];
                }

                return;
            }
        }

        remix.compat.vecmath.Matrix3d.transpose_mat(var9, var11);
        remix.compat.vecmath.Matrix3d.transpose_mat(var10, var12);
        remix.compat.vecmath.Matrix3d.svdReorder(
                p171_0, var11, var12, var17, p171_2, p171_1);
    }

    static void luBacksubstitution(double[] adouble, int[] aint, double[] adouble1) {
        String[] astring1 = Tuple3f.hF();
        byte b0 = 0;
        int i1 = 0;
        String[] astring = astring1;

        while (i1 < 3) {
            int j1 = i1;
            int j = -1;
            int i = 0;

            while (true) {
                if (i < 3) {
                    double d0;
                    label32:
                    {
                        int k = aint[b0 + i];
                        d0 = adouble1[j1 + 3 * k];
                        adouble1[j1 + 3 * k] = adouble1[j1 + 3 * i];
                        if (j >= 0) {
                            int k1 = i * 3;
                            int l = j;

                            do {
                                if (l > i - 1) {
                                    break label32;
                                }

                                d0 -= adouble[k1 + l] * adouble1[j1 + 3 * l];
                                l++;
                            } while (astring == null);
                        }

                        if (d0 != 0.0) {
                            j = i;
                        }
                    }

                    adouble1[j1 + 3 * i] = d0;
                    i++;
                    if (astring == null) {
                        continue;
                    }
                }

                int l1 = 6;
                adouble1[j1 + 6] = adouble1[j1 + 6] / adouble[l1 + 2];
                l1 -= 3;
                adouble1[j1 + 3] =
                        (adouble1[j1 + 3] - adouble[l1 + 2] * adouble1[j1 + 6]) / adouble[l1 + 1];
                l1 -= 3;
                adouble1[j1 + 0] =
                        (adouble1[j1 + 0]
                                        - adouble[l1 + 1] * adouble1[j1 + 3]
                                        - adouble[l1 + 2] * adouble1[j1 + 6])
                                / adouble[l1 + 0];
                i1++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public final double getM20() {
        return this.m20;
    }

    public final void set(Matrix3d p171_0) {
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

    public final void mul(double p171_0) {
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

    public final void add(
            double p171_0, Matrix3d p171_1) {
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

    private static final boolean method_92ec38fcaab9adf4c70a(double p171_0, double p171_1) {
        try {
            if (p171_0 == p171_1) {
                return true;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception3) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception3);
        }

        double d4 = java.lang.Math.abs(p171_0 - p171_1);
        double d5 = java.lang.Math.abs(p171_0);
        double d6 = java.lang.Math.abs(p171_1);

        double d8;
        label40:
        {
            try {
                if (d5 >= d6) {
                    d8 = d5;
                    break label40;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception2) {
                throw remix.compat.vecmath.Matrix3d.wrapException(
                        arrayindexoutofboundsexception2);
            }

            d8 = d6;
        }

        double d7 = d8;

        try {
            if (d4 < 1.0E-6) {
                return true;
            }
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception1) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception1);
        }

        try {
            return d4 / d7 < 1.0E-4;
        } catch (java.lang.ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            throw remix.compat.vecmath.Matrix3d.wrapException(
                    arrayindexoutofboundsexception);
        }
    }

    public final void setM22(double d0) {
        this.m22 = d0;
    }

    public final void setM20(double d0) {
        this.m20 = d0;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Matrix3d(Matrix3d matrix3d) {
        super();
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
}
