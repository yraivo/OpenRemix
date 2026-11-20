package remix.compat.vecmath;

import java.io.Serializable;

public class GMatrix implements Serializable, Cloneable {

    int nCol;
    private static final boolean debug = false;
    private static String[] packedTextFragments;
    private static final double EPS = 1.0E-10;
    int nRow;
    static final long serialVersionUID = 2777097312029690941L;

    double[][] values;

    public final void set(
            Matrix3f matrix3f) {
        String[] astring = Tuple3f.hF();
        if (this.nCol < 3 || this.nRow < 3) {
            this.nCol = 3;
            this.nRow = 3;
            this.values = new double[this.nRow][this.nCol];
        }

        this.values[0][0] = matrix3f.m00;
        this.values[0][1] = matrix3f.m01;
        this.values[0][2] = matrix3f.m02;
        this.values[1][0] = matrix3f.m10;
        this.values[1][1] = matrix3f.m11;
        this.values[1][2] = matrix3f.m12;
        this.values[2][0] = matrix3f.m20;
        this.values[2][1] = matrix3f.m21;
        this.values[2][2] = matrix3f.m22;
        int i = 3;

        while (i < this.nRow) {
            int j = 3;

            while (true) {
                if (j < this.nCol) {
                    this.values[i][j] = 0.0;
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public boolean equals(GMatrix p171_0) {
        java.lang.String[] astring = remix.compat.vecmath.Tuple3f.hF();

        try {
            try {
                if (this.nRow != p171_0.nRow || this.nCol != p171_0.nCol) {
                    return false;
                }
            } catch (java.lang.NullPointerException nullpointerexception) {
                throw remix.compat.vecmath.GMatrix.wrapException(
                        nullpointerexception);
            }

            int i = 0;

            while (i < this.nRow) {
                int j = 0;

                while (true) {
                    if (j < this.nCol) {
                        if (this.values[i][j] != p171_0.values[i][j]) {
                            return false;
                        }

                        j++;
                        if (astring == null) {
                            continue;
                        }
                    }

                    i++;
                    if (astring != null) {
                        return true;
                    }
                    break;
                }
            }

            return true;
        } catch (java.lang.NullPointerException nullpointerexception1) {
            return false;
        }
    }

    private static RuntimeException wrapException(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    public final void mul(
            GMatrix gMatrix,
            GMatrix gMatrixValue) {
        String[] astring = Tuple3f.hF();
        if (gMatrix.nCol == gMatrixValue.nRow
                && this.nRow == gMatrix.nRow
                && this.nCol == gMatrixValue.nCol) {
            double[][] adouble = new double[this.nRow][this.nCol];
            int i = 0;

            label37:
            while (i < gMatrix.nRow) {
                int j = 0;

                while (true) {
                    if (j < gMatrixValue.nCol) {
                        adouble[i][j] = 0.0;
                        int k = 0;

                        while (k < gMatrix.nCol) {
                            adouble[i][j] =
                                    adouble[i][j]
                                            + gMatrix.values[i][k]
                                                    * gMatrixValue.values[k][j];
                            k++;
                            if (astring != null) {
                                break;
                            }
                        }

                        j++;
                        if (astring == null) {
                            continue;
                        }
                    }

                    i++;
                    if (astring != null) {
                        break label37;
                    }
                    break;
                }
            }

            this.values = adouble;
        } else {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix1"));
        }
    }

    public final void getRow(
            int i, GVector gVector) {
        String[] astring1 = Tuple3f.hF();
        int j = 0;
        String[] astring = astring1;

        while (j < this.nRow) {
            this.values[j][i] = gVector.values[j];
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void set(
            GMatrix gMatrix) {
        String[] astring = Tuple3f.hF();
        if (this.nRow == gMatrix.nCol && this.nCol == gMatrix.nRow) {
            if (gMatrix != this) {
                int i = 0;

                do {
                    if (i >= this.nRow) {
                        return;
                    }

                    int j = 0;

                    while (j < this.nCol) {
                        this.values[i][j] = gMatrix.values[j][i];
                        j++;
                        if (astring != null) {
                            break;
                        }
                    }

                    i++;
                } while (astring == null);
            }

            this.fF();
        } else {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix17"));
        }
    }

    public final int getNumRow() {
        return this.nRow;
    }

    public final void set(
            Matrix4f matrix4f) {
        String[] astring = Tuple3f.hF();
        if (this.nRow < 4 || this.nCol < 4) {
            matrix4f.setZero();
            if (this.nCol <= 0) {
                return;
            }

            if (this.nRow > 0) {
                matrix4f.m00 = (float) this.values[0][0];
                if (this.nRow > 1) {
                    matrix4f.m10 = (float) this.values[1][0];
                    if (this.nRow > 2) {
                        matrix4f.m20 = (float) this.values[2][0];
                        if (this.nRow > 3) {
                            matrix4f.m30 = (float) this.values[3][0];
                        }
                    }
                }
            }

            if (this.nCol <= 1) {
                return;
            }

            if (this.nRow > 0) {
                matrix4f.m01 = (float) this.values[0][1];
                if (this.nRow > 1) {
                    matrix4f.m11 = (float) this.values[1][1];
                    if (this.nRow > 2) {
                        matrix4f.m21 = (float) this.values[2][1];
                        if (this.nRow > 3) {
                            matrix4f.m31 = (float) this.values[3][1];
                        }
                    }
                }
            }

            if (this.nCol <= 2) {
                return;
            }

            if (this.nRow > 0) {
                matrix4f.m02 = (float) this.values[0][2];
                if (this.nRow > 1) {
                    matrix4f.m12 = (float) this.values[1][2];
                    if (this.nRow > 2) {
                        matrix4f.m22 = (float) this.values[2][2];
                        if (this.nRow > 3) {
                            matrix4f.m32 = (float) this.values[3][2];
                        }
                    }
                }
            }

            if (this.nCol <= 3 || this.nRow <= 0) {
                return;
            }

            matrix4f.m03 = (float) this.values[0][3];
            if (this.nRow <= 1) {
                return;
            }

            matrix4f.m13 = (float) this.values[1][3];
            if (this.nRow <= 2) {
                return;
            }

            matrix4f.m23 = (float) this.values[2][3];
            if (this.nRow <= 3) {
                return;
            }

            matrix4f.m33 = (float) this.values[3][3];
            if (astring == null) {
                return;
            }
        }

        matrix4f.m00 = (float) this.values[0][0];
        matrix4f.m01 = (float) this.values[0][1];
        matrix4f.m02 = (float) this.values[0][2];
        matrix4f.m03 = (float) this.values[0][3];
        matrix4f.m10 = (float) this.values[1][0];
        matrix4f.m11 = (float) this.values[1][1];
        matrix4f.m12 = (float) this.values[1][2];
        matrix4f.m13 = (float) this.values[1][3];
        matrix4f.m20 = (float) this.values[2][0];
        matrix4f.m21 = (float) this.values[2][1];
        matrix4f.m22 = (float) this.values[2][2];
        matrix4f.m23 = (float) this.values[2][3];
        matrix4f.m30 = (float) this.values[3][0];
        matrix4f.m31 = (float) this.values[3][1];
        matrix4f.m32 = (float) this.values[3][2];
        matrix4f.m33 = (float) this.values[3][3];
    }

    public final void setRow(
            int i, GVector gVector) {
        String[] astring = Tuple3f.hF();
        if (gVector.getSize() < this.nRow) {
            gVector.a(this.nRow);
        }

        int j = 0;

        while (j < this.nRow) {
            gVector.values[j] = this.values[j][i];
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    public boolean epsilonEquals(
            GMatrix gMatrix, double d0) {
        String[] astring = Tuple3f.hF();
        if (this.nRow == gMatrix.nRow && this.nCol == gMatrix.nCol) {
            int i = 0;

            while (i < this.nRow) {
                int j = 0;

                while (true) {
                    if (j < this.nCol) {
                        double d1 = this.values[i][j] - gMatrix.values[i][j];
                        if ((d1 < 0.0 ? -d1 : d1) > d0) {
                            return false;
                        }

                        j++;
                        if (astring == null) {
                            continue;
                        }
                    }

                    i++;
                    if (astring != null) {
                        return true;
                    }
                    break;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    static int computeSVD(
            GMatrix gMatrix,
            GMatrix gMatrixValue,
            GMatrix gMatrixValue2,
            GMatrix gMatrixValue3) {
        String[] astring;
        int k1;
        int l1;
        GMatrix gMatrixValue4;
        GMatrix gMatrixValue5;
        GMatrix gMatrixValue6;
        GMatrix gMatrixValue7;
        {
            gMatrixValue4 =
                    new GMatrix(
                            gMatrix.nRow, gMatrix.nCol);
            gMatrixValue5 =
                    new GMatrix(
                            gMatrix.nRow, gMatrix.nCol);
            String[] astring1 = Tuple3f.hF();
            gMatrixValue6 =
                    new GMatrix(
                            gMatrix.nRow, gMatrix.nCol);
            astring = astring1;
            gMatrixValue7 =
                    new GMatrix(gMatrix);
            if (gMatrixValue7.nRow >= gMatrixValue7.nCol) {
                l1 = gMatrixValue7.nCol;
                k1 = gMatrixValue7.nCol - 1;
                if (!(astring == null)) {

                    l1 = gMatrixValue7.nRow;
                    k1 = gMatrixValue7.nRow;
                }
            } else {

                l1 = gMatrixValue7.nRow;
                k1 = gMatrixValue7.nRow;
            }
        }

        int i2;
        {
            if (gMatrixValue7.nRow > gMatrixValue7.nCol) {
                i2 = gMatrixValue7.nRow;
                if (!(astring == null)) {

                    i2 = gMatrixValue7.nCol;
                }
            } else {

                i2 = gMatrixValue7.nCol;
            }
        }

        double[] adouble = new double[i2];
        double[] adouble1 = new double[l1];
        double[] adouble2 = new double[k1];

        gMatrixValue.setIdentity();
        gMatrixValue3.setIdentity();
        int l = gMatrixValue7.nRow;
        int i1 = gMatrixValue7.nCol;
        int j1 = 0;

        while (j1 < l1) {
            if (l > 1) {
                double d0 = 0.0;
                int i = 0;

                while (i < l) {
                    d0 +=
                            gMatrixValue7.values[i + j1][j1]
                                    * gMatrixValue7.values[i + j1][j1];
                    i++;
                    if (astring != null) {
                        break;
                    }
                }

                {
                    d0 = Math.sqrt(d0);
                    if (gMatrixValue7.values[j1][j1] == 0.0) {
                        adouble[0] = d0;
                        if (!(astring == null)) {

                            adouble[0] =
                                    gMatrixValue7.values[j1][j1]
                                            + d_sign(
                                                    d0, gMatrixValue7.values[j1][j1]);
                        }
                    } else {

                        adouble[0] =
                                gMatrixValue7.values[j1][j1]
                                        + d_sign(
                                                d0, gMatrixValue7.values[j1][j1]);
                    }
                }

                i = 1;

                while (i < l) {
                    adouble[i] = gMatrixValue7.values[j1 + i][j1];
                    i++;
                    if (astring != null) {
                        break;
                    }
                }

                double d1 = 0.0;
                i = 0;

                while (i < l) {
                    d1 += adouble[i] * adouble[i];
                    i++;
                    if (astring != null) {
                        break;
                    }
                }

                d1 = 2.0 / d1;
                int j = j1;

                while (j < gMatrixValue7.nRow) {
                    int k = j1;

                    while (k < gMatrixValue7.nRow) {
                        gMatrixValue5.values[j][k] =
                                -d1 * adouble[j - j1] * adouble[k - j1];
                        k++;
                        if (astring != null) {
                            break;
                        }
                    }

                    j++;
                    if (astring != null) {
                        break;
                    }
                }

                i = j1;

                while (i < gMatrixValue7.nRow) {
                    gMatrixValue5.values[i][i]++;
                    i++;
                    if (astring != null) {
                        break;
                    }
                }

                double d2 = 0.0;
                i = j1;

                while (i < gMatrixValue7.nRow) {
                    d2 +=
                            gMatrixValue5.values[j1][i]
                                    * gMatrixValue7.values[i][j1];
                    i++;
                    if (astring != null) {
                        break;
                    }
                }

                gMatrixValue7.values[j1][j1] = d2;
                j = j1;

                while (j < gMatrixValue7.nRow) {
                    int i3 = j1 + 1;

                    while (i3 < gMatrixValue7.nCol) {
                        gMatrixValue4.values[j][i3] = 0.0;
                        i = j1;

                        while (i < gMatrixValue7.nCol) {
                            gMatrixValue4.values[j][i3] =
                                    gMatrixValue4.values[j][i3]
                                            + gMatrixValue5.values[j][i]
                                                    * gMatrixValue7.values[i][i3];
                            i++;
                            if (astring != null) {
                                break;
                            }
                        }

                        i3++;
                        if (astring != null) {
                            break;
                        }
                    }

                    j++;
                    if (astring != null) {
                        break;
                    }
                }

                j = j1;

                while (j < gMatrixValue7.nRow) {
                    int j3 = j1 + 1;

                    while (j3 < gMatrixValue7.nCol) {
                        gMatrixValue7.values[j][j3] =
                                gMatrixValue4.values[j][j3];
                        j3++;
                        if (astring != null) {
                            break;
                        }
                    }

                    j++;
                    if (astring != null) {
                        break;
                    }
                }

                j = j1;

                while (j < gMatrixValue7.nRow) {
                    int k3 = 0;

                    while (k3 < gMatrixValue7.nCol) {
                        gMatrixValue4.values[j][k3] = 0.0;
                        i = j1;

                        while (i < gMatrixValue7.nCol) {
                            gMatrixValue4.values[j][k3] =
                                    gMatrixValue4.values[j][k3]
                                            + gMatrixValue5.values[j][i]
                                                    * gMatrixValue.values[i][k3];
                            i++;
                            if (astring != null) {
                                break;
                            }
                        }

                        k3++;
                        if (astring != null) {
                            break;
                        }
                    }

                    j++;
                    if (astring != null) {
                        break;
                    }
                }

                j = j1;

                while (j < gMatrixValue7.nRow) {
                    int l3 = 0;

                    while (l3 < gMatrixValue7.nCol) {
                        gMatrixValue.values[j][l3] =
                                gMatrixValue4.values[j][l3];
                        l3++;
                        if (astring != null) {
                            break;
                        }
                    }

                    j++;
                    if (astring != null) {
                        break;
                    }
                }

                l--;
            }

            if (i1 > 2) {
                double d3 = 0.0;
                int j2 = 1;

                while (j2 < i1) {
                    d3 +=
                            gMatrixValue7.values[j1][j1 + j2]
                                    * gMatrixValue7.values[j1][j1 + j2];
                    j2++;
                    if (astring != null) {
                        break;
                    }
                }

                {
                    d3 = Math.sqrt(d3);
                    if (gMatrixValue7.values[j1][j1 + 1] == 0.0) {
                        adouble[0] = d3;
                        if (!(astring == null)) {

                            adouble[0] =
                                    gMatrixValue7.values[j1][j1 + 1]
                                            + d_sign(
                                                    d3, gMatrixValue7.values[j1][j1 + 1]);
                        }
                    } else {

                        adouble[0] =
                                gMatrixValue7.values[j1][j1 + 1]
                                        + d_sign(
                                                d3, gMatrixValue7.values[j1][j1 + 1]);
                    }
                }

                j2 = 1;

                while (j2 < i1 - 1) {
                    adouble[j2] = gMatrixValue7.values[j1][j1 + j2 + 1];
                    j2++;
                    if (astring != null) {
                        break;
                    }
                }

                double d4 = 0.0;
                j2 = 0;

                while (j2 < i1 - 1) {
                    d4 += adouble[j2] * adouble[j2];
                    j2++;
                    if (astring != null) {
                        break;
                    }
                }

                d4 = 2.0 / d4;
                int l2 = j1 + 1;

                while (l2 < i1) {
                    int i4 = j1 + 1;

                    while (i4 < gMatrixValue7.nCol) {
                        gMatrixValue6.values[l2][i4] =
                                -d4 * adouble[l2 - j1 - 1] * adouble[i4 - j1 - 1];
                        i4++;
                        if (astring != null) {
                            break;
                        }
                    }

                    l2++;
                    if (astring != null) {
                        break;
                    }
                }

                j2 = j1 + 1;

                while (j2 < gMatrixValue7.nCol) {
                    gMatrixValue6.values[j2][j2]++;
                    j2++;
                    if (astring != null) {
                        break;
                    }
                }

                double d5 = 0.0;
                j2 = j1;

                while (j2 < gMatrixValue7.nCol) {
                    d5 +=
                            gMatrixValue6.values[j2][j1 + 1]
                                    * gMatrixValue7.values[j1][j2];
                    j2++;
                    if (astring != null) {
                        break;
                    }
                }

                gMatrixValue7.values[j1][j1 + 1] = d5;
                l2 = j1 + 1;

                while (l2 < gMatrixValue7.nRow) {
                    int j4 = j1 + 1;

                    while (j4 < gMatrixValue7.nCol) {
                        gMatrixValue4.values[l2][j4] = 0.0;
                        j2 = j1 + 1;

                        while (j2 < gMatrixValue7.nCol) {
                            gMatrixValue4.values[l2][j4] =
                                    gMatrixValue4.values[l2][j4]
                                            + gMatrixValue6.values[j2][j4]
                                                    * gMatrixValue7.values[l2][j2];
                            j2++;
                            if (astring != null) {
                                break;
                            }
                        }

                        j4++;
                        if (astring != null) {
                            break;
                        }
                    }

                    l2++;
                    if (astring != null) {
                        break;
                    }
                }

                l2 = j1 + 1;

                while (l2 < gMatrixValue7.nRow) {
                    int k4 = j1 + 1;

                    while (k4 < gMatrixValue7.nCol) {
                        gMatrixValue7.values[l2][k4] =
                                gMatrixValue4.values[l2][k4];
                        k4++;
                        if (astring != null) {
                            break;
                        }
                    }

                    l2++;
                    if (astring != null) {
                        break;
                    }
                }

                l2 = 0;

                while (l2 < gMatrixValue7.nRow) {
                    int l4 = j1 + 1;

                    while (l4 < gMatrixValue7.nCol) {
                        gMatrixValue4.values[l2][l4] = 0.0;
                        j2 = j1 + 1;

                        while (j2 < gMatrixValue7.nCol) {
                            gMatrixValue4.values[l2][l4] =
                                    gMatrixValue4.values[l2][l4]
                                            + gMatrixValue6.values[j2][l4]
                                                    * gMatrixValue3.values[l2][j2];
                            j2++;
                            if (astring != null) {
                                break;
                            }
                        }

                        l4++;
                        if (astring != null) {
                            break;
                        }
                    }

                    l2++;
                    if (astring != null) {
                        break;
                    }
                }

                l2 = 0;

                while (l2 < gMatrixValue7.nRow) {
                    int i5 = j1 + 1;

                    while (i5 < gMatrixValue7.nCol) {
                        gMatrixValue3.values[l2][i5] =
                                gMatrixValue4.values[l2][i5];
                        i5++;
                        if (astring != null) {
                            break;
                        }
                    }

                    l2++;
                    if (astring != null) {
                        break;
                    }
                }

                i1--;
            }

            j1++;
            if (astring != null) {
                break;
            }
        }

        int k2 = 0;

        while (k2 < l1) {
            adouble1[k2] = gMatrixValue7.values[k2][k2];
            k2++;
            if (astring != null) {
                break;
            }
        }

        k2 = 0;

        while (k2 < k1) {
            adouble2[k2] = gMatrixValue7.values[k2][k2 + 1];
            k2++;
            if (astring != null) {
                break;
            }
        }

        if (gMatrixValue7.nRow == 2 && gMatrixValue7.nCol == 2) {
            double[] adouble3 = new double[1];
            double[] adouble4 = new double[1];
            double[] adouble5 = new double[1];
            double[] adouble6 = new double[1];
            compute_2X2(
                    adouble1[0],
                    adouble2[0],
                    adouble1[1],
                    adouble1,
                    adouble5,
                    adouble3,
                    adouble6,
                    adouble4,
                    0);
            update_u(0, gMatrixValue, adouble3, adouble5);
            update_v(0, gMatrixValue3, adouble4, adouble6);
            return 2;
        } else {
            compute_qr(
                    0,
                    adouble2.length - 1,
                    adouble1,
                    adouble2,
                    gMatrixValue,
                    gMatrixValue3);
            return adouble1.length;
        }
    }

    static int compute_2X2(
            double d0,
            double d1,
            double d2,
            double[] adouble,
            double[] adouble1,
            double[] adouble2,
            double[] adouble3,
            double[] adouble4,
            int i) {
        String[] astring;
        double d3;
        double d4;
        byte b0;
        byte b1;
        double d14;
        double d15;
        double d17;
        double d18;
        double d20;
        double d23;
        double d24;
        double d25;
        double d26;
        double d27;
        double d28;
        {
            d3 = 2.0;
            d4 = 1.0;
            String[] astring1 = Tuple3f.hF();
            d28 = adouble[0];
            d27 = adouble[1];
            d23 = 0.0;
            d24 = 0.0;
            d25 = 0.0;
            d26 = 0.0;
            d14 = 0.0;
            d18 = d0;
            d15 = Math.abs(d18);
            d20 = d2;
            astring = astring1;
            d17 = Math.abs(d2);
            b0 = 1;
            if (d17 > d15) {
                b1 = 1;
                if (!(astring == null)) {

                    b1 = 0;
                }
            } else {

                b1 = 0;
            }
        }

        if (b1 != 0) {
            b0 = 3;
            double d6 = d18;
            d18 = d20;
            d20 = d6;
            d6 = d15;
            d15 = d17;
            d17 = d6;
        }

        double d19 = d1;
        double d16 = Math.abs(d19);
        if (d16 == 0.0) {
            adouble[1] = d17;
            adouble[0] = d15;
            d23 = 1.0;
            d24 = 1.0;
            d25 = 0.0;
            d26 = 0.0;
            if (astring == null) {
                return 0;
            }
        }

        byte b2 = 1;
        if (d16 > d15) {
            b0 = 2;
            if (d15 / d16 < 1.0E-10) {
                {
                    b2 = 0;
                    d28 = d16;
                    if (d17 > 1.0) {
                        d27 = d15 / (d16 / d17);
                        if (!(astring == null)) {

                            d27 = d15 / d16 * d17;
                        }
                    } else {

                        d27 = d15 / d16 * d17;
                    }
                }

                d23 = 1.0;
                d25 = d20 / d19;
                d26 = 1.0;
                d24 = d18 / d19;
            }
        }

        if (b2 != 0) {
            double d9;
            {
                double d8 = d15 - d17;
                if (d8 == d15) {
                    d9 = 1.0;
                    if (!(astring == null)) {

                        d9 = d8 / d15;
                    }
                } else {

                    d9 = d8 / d15;
                }
            }

            double d11;
            double d12;
            {
                double d10 = d19 / d18;
                double d13 = 2.0 - d9;
                double d21 = d10 * d10;
                double d22 = d13 * d13;
                d12 = Math.sqrt(d22 + d21);
                if (d9 == 0.0) {
                    d11 = Math.abs(d10);
                    if (!(astring == null)) {

                        d11 = Math.sqrt(d9 * d9 + d21);
                    }
                } else {

                    d11 = Math.sqrt(d9 * d9 + d21);
                }
            }

            double d7 = (d12 + d11) * 0.5;
            if (d16 > d15) {
                b0 = 2;
                if (d15 / d16 < 1.0E-10) {
                    {
                        b2 = 0;
                        d28 = d16;
                        if (d17 > 1.0) {
                            d27 = d15 / (d16 / d17);
                            if (!(astring == null)) {

                                d27 = d15 / d16 * d17;
                            }
                        } else {

                            d27 = d15 / d16 * d17;
                        }
                    }

                    d23 = 1.0;
                    d25 = d20 / d19;
                    d26 = 1.0;
                    d24 = d18 / d19;
                }
            }

            if (b2 != 0) {
                double d29;
                {
                    d29 = d15 - d17;
                    if (d29 == d15) {
                        d9 = 1.0;
                        if (!(astring == null)) {

                            d9 = d29 / d15;
                        }
                    } else {

                        d9 = d29 / d15;
                    }
                }

                double d30;
                double d31;
                double d32;
                {
                    d30 = d19 / d18;
                    d31 = 2.0 - d9;
                    d32 = d30 * d30;
                    double d33 = d31 * d31;
                    d12 = Math.sqrt(d33 + d32);
                    if (d9 == 0.0) {
                        d11 = Math.abs(d30);
                        if (!(astring == null)) {

                            d11 = Math.sqrt(d9 * d9 + d32);
                        }
                    } else {

                        d11 = Math.sqrt(d9 * d9 + d32);
                    }
                }

                label77:
                {
                    d7 = (d12 + d11) * 0.5;
                    d27 = d17 / d7;
                    d28 = d15 * d7;
                    if (d32 == 0.0) {
                        if (d9 == 0.0) {
                            d31 =
                                    d_sign(d3, d18)
                                            * d_sign(d4, d19);
                            if (astring == null) {
                                break label77;
                            }
                        }

                        d31 = d19 / d_sign(d29, d18) + d30 / d31;
                        if (astring == null) {
                            break label77;
                        }
                    }

                    d31 = (d30 / (d12 + d31) + d30 / (d11 + d9)) * (d7 + 1.0);
                }

                d9 = Math.sqrt(d31 * d31 + 4.0);
                d24 = 2.0 / d9;
                d26 = d31 / d9;
                d23 = (d24 + d26 * d30) / d7;
                d25 = d20 / d18 * d26 / d7;
            }
        }

        label66:
        {
            if (b1 != 0) {
                adouble2[0] = d26;
                adouble1[0] = d24;
                adouble4[0] = d25;
                adouble3[0] = d23;
                if (astring == null) {
                    break label66;
                }
            }

            adouble2[0] = d23;
            adouble1[0] = d25;
            adouble4[0] = d24;
            adouble3[0] = d26;
        }

        if (b0 == 1) {
            d14 =
                    d_sign(d4, adouble4[0])
                            * d_sign(d4, adouble2[0])
                            * d_sign(d4, d0);
        }

        if (b0 == 2) {
            d14 =
                    d_sign(d4, adouble3[0])
                            * d_sign(d4, adouble2[0])
                            * d_sign(d4, d1);
        }

        if (b0 == 3) {
            d14 =
                    d_sign(d4, adouble3[0])
                            * d_sign(d4, adouble1[0])
                            * d_sign(d4, d2);
        }

        adouble[i] = d_sign(d28, d14);
        double d5 = d14 * d_sign(d4, d0) * d_sign(d4, d2);
        adouble[i + 1] = d_sign(d27, d5);
        return 0;
    }

    public final void set(
            Matrix3d matrix3d) {
        String[] astring = Tuple3f.hF();
        if (this.nRow < 3 || this.nCol < 3) {
            this.values = new double[3][3];
            this.nRow = 3;
            this.nCol = 3;
        }

        this.values[0][0] = matrix3d.m00;
        this.values[0][1] = matrix3d.m01;
        this.values[0][2] = matrix3d.m02;
        this.values[1][0] = matrix3d.m10;
        this.values[1][1] = matrix3d.m11;
        this.values[1][2] = matrix3d.m12;
        this.values[2][0] = matrix3d.m20;
        this.values[2][1] = matrix3d.m21;
        this.values[2][2] = matrix3d.m22;
        int i = 3;

        while (i < this.nRow) {
            int j = 3;

            while (true) {
                if (j < this.nCol) {
                    this.values[i][j] = 0.0;
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    // region String decoding support

    // endregion

    public final double dR() {
        java.lang.String[] astring;
        int j;
        {
            astring = remix.compat.vecmath.Tuple3f.hF();
            if (this.nRow < this.nCol) {
                j = this.nRow;
                if (!(astring == null)) {

                    j = this.nCol;
                }
            } else {

                j = this.nCol;
            }
        }

        double d0 = 0.0;
        int i = 0;

        while (i < j) {
            d0 += this.values[i][i];
            i++;
            if (astring != null) {
                break;
            }
        }

        return d0;
    }

    public GMatrix(int i, int j, double[] adouble) {
        String[] astring1 = Tuple3f.hF();
        this.values = new double[i][j];
        String[] astring = astring1;
        this.nRow = i;
        this.nCol = j;
        int k = 0;

        while (k < i) {
            int l = 0;

            while (true) {
                if (l < j) {
                    this.values[k][l] = adouble[k * j + l];
                    l++;
                    if (astring == null) {
                        continue;
                    }
                }

                k++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public final void set(
            Matrix4d matrix4d) {
        String[] astring = Tuple3f.hF();
        if (this.nRow < 4 || this.nCol < 4) {
            this.values = new double[4][4];
            this.nRow = 4;
            this.nCol = 4;
        }

        this.values[0][0] = matrix4d.m00;
        this.values[0][1] = matrix4d.m01;
        this.values[0][2] = matrix4d.m02;
        this.values[0][3] = matrix4d.m03;
        this.values[1][0] = matrix4d.m10;
        this.values[1][1] = matrix4d.m11;
        this.values[1][2] = matrix4d.m12;
        this.values[1][3] = matrix4d.m13;
        this.values[2][0] = matrix4d.m20;
        this.values[2][1] = matrix4d.m21;
        this.values[2][2] = matrix4d.m22;
        this.values[2][3] = matrix4d.m23;
        this.values[3][0] = matrix4d.m30;
        this.values[3][1] = matrix4d.m31;
        this.values[3][2] = matrix4d.m32;
        this.values[3][3] = matrix4d.m33;
        int i = 4;

        while (i < this.nRow) {
            int j = 4;

            while (true) {
                if (j < this.nCol) {
                    this.values[i][j] = 0.0;
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    @Override
    public String toString() {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        java.lang.StringBuffer stringbuffer = new java.lang.StringBuffer(this.nRow * this.nCol * 8);
        java.lang.String[] astring = astring1;
        int i = 0;

        while (i < this.nRow) {
            int j = 0;

            while (true) {
                if (j < this.nCol) {
                    stringbuffer
                            .append(this.values[i][j])
                            .append(remix.compat.vecmath.GMatrix.packedTextFragments[0]);
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                stringbuffer.append(remix.compat.vecmath.GMatrix.packedTextFragments[1]);
                i++;
                if (astring != null) {
                    return stringbuffer.toString();
                }
                break;
            }
        }

        return stringbuffer.toString();
    }

    static double max(double d0, double d1) {
        return d0 > d1 ? d0 : d1;
    }

    @Override
    public int hashCode() {

        long i = 1L;
        String[] astring1 = Tuple3f.hF();
        i = VecMathUtil.method_610bb001c28d547cfb8f(i, this.nRow);
        String[] astring = astring1;
        i = VecMathUtil.method_610bb001c28d547cfb8f(i, this.nCol);
        int j = 0;

        while (j < this.nRow) {
            int k = 0;

            while (true) {
                if (k < this.nCol) {
                    i = VecMathUtil.hashDoubleToLongBits(i, this.values[j][k]);
                    k++;
                    if (astring == null) {
                        continue;
                    }
                }

                j++;
                if (astring != null) {
                    return VecMathUtil.hashFinish(i);
                }
                break;
            }
        }

        return VecMathUtil.hashFinish(i);
    }

    private static void compute_2X2(
            int i,
            int j,
            GMatrix gMatrix,
            double[] adouble,
            double[] adouble1,
            GMatrix gMatrixValue,
            GMatrix gMatrixValue2) {
        String[] astring1 = Tuple3f.hF();
        int k = 0;
        String[] astring = astring1;

        while (k < gMatrix.nCol) {
            double d0 = gMatrix.values[i][k];
            gMatrix.values[i][k] =
                    adouble[0] * d0 - adouble1[0] * gMatrix.values[j][k];
            gMatrix.values[j][k] =
                    adouble1[0] * d0 + adouble[0] * gMatrix.values[j][k];
            k++;
            if (astring != null) {
                break;
            }
        }

        System.out.println("\nm=");
        checkMatrix(gMatrixValue2);
        System.out.println("\nu=");
        checkMatrix(gMatrixValue);
        gMatrixValue2.mul(
                gMatrixValue, gMatrixValue2);
        System.out.println("\nt*m=");
        checkMatrix(gMatrixValue2);
    }

    private static void compute_qr(
            double[] adouble,
            double[] adouble1,
            int i,
            GMatrix gMatrix) {
        double[] adouble2 = new double[1];
        String[] astring1 = Tuple3f.hF();
        double[] adouble3 = new double[1];
        GMatrix gMatrixValue =
                new GMatrix(
                        gMatrix.nRow, gMatrix.nCol);
        String[] astring = astring1;
        GMatrix gMatrixValue2 =
                new GMatrix(
                        gMatrix.nRow, gMatrix.nCol);
        double d0 = adouble1[i];
        double d1 = adouble[i];
        int j = i;

        while (j > 0) {
            double d2 = compute_rot(d0, d1, adouble3, adouble2);
            d0 = -adouble1[j - 1] * adouble3[0];
            d1 = adouble[j - 1];
            adouble[j] = d2;
            adouble1[j - 1] = adouble1[j - 1] * adouble2[0];
            compute_svd(
                    j,
                    i + 1,
                    gMatrix,
                    adouble2,
                    adouble3,
                    gMatrixValue,
                    gMatrixValue2);
            j--;
            if (astring != null) {
                break;
            }
        }

        adouble[j + 1] = compute_rot(d0, d1, adouble3, adouble2);
        compute_svd(
                j,
                i + 1,
                gMatrix,
                adouble2,
                adouble3,
                gMatrixValue,
                gMatrixValue2);
    }

    public final void setScale(double p171_0) {
        java.lang.String[] astring;
        int k;
        {
            astring = remix.compat.vecmath.Tuple3f.hF();
            if (this.nRow < this.nCol) {
                k = this.nRow;
                if (!(astring == null)) {

                    k = this.nCol;
                }
            } else {

                k = this.nCol;
            }
        }

        int i = 0;

        label34:
        while (i < this.nRow) {
            int j = 0;

            while (true) {
                if (j < this.nCol) {
                    this.values[i][j] = 0.0;
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    break label34;
                }
                break;
            }
        }

        i = 0;

        while (i < k) {
            this.values[i][i] = p171_0;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void get(
            Matrix4d matrix4d) {
        String[] astring = Tuple3f.hF();
        if (this.nRow < 4 || this.nCol < 4) {
            matrix4d.setZero();
            if (this.nCol <= 0) {
                return;
            }

            if (this.nRow > 0) {
                matrix4d.m00 = this.values[0][0];
                if (this.nRow > 1) {
                    matrix4d.m10 = this.values[1][0];
                    if (this.nRow > 2) {
                        matrix4d.m20 = this.values[2][0];
                        if (this.nRow > 3) {
                            matrix4d.m30 = this.values[3][0];
                        }
                    }
                }
            }

            if (this.nCol <= 1) {
                return;
            }

            if (this.nRow > 0) {
                matrix4d.m01 = this.values[0][1];
                if (this.nRow > 1) {
                    matrix4d.m11 = this.values[1][1];
                    if (this.nRow > 2) {
                        matrix4d.m21 = this.values[2][1];
                        if (this.nRow > 3) {
                            matrix4d.m31 = this.values[3][1];
                        }
                    }
                }
            }

            if (this.nCol <= 2) {
                return;
            }

            if (this.nRow > 0) {
                matrix4d.m02 = this.values[0][2];
                if (this.nRow > 1) {
                    matrix4d.m12 = this.values[1][2];
                    if (this.nRow > 2) {
                        matrix4d.m22 = this.values[2][2];
                        if (this.nRow > 3) {
                            matrix4d.m32 = this.values[3][2];
                        }
                    }
                }
            }

            if (this.nCol <= 3 || this.nRow <= 0) {
                return;
            }

            matrix4d.m03 = this.values[0][3];
            if (this.nRow <= 1) {
                return;
            }

            matrix4d.m13 = this.values[1][3];
            if (this.nRow <= 2) {
                return;
            }

            matrix4d.m23 = this.values[2][3];
            if (this.nRow <= 3) {
                return;
            }

            matrix4d.m33 = this.values[3][3];
            if (astring == null) {
                return;
            }
        }

        matrix4d.m00 = this.values[0][0];
        matrix4d.m01 = this.values[0][1];
        matrix4d.m02 = this.values[0][2];
        matrix4d.m03 = this.values[0][3];
        matrix4d.m10 = this.values[1][0];
        matrix4d.m11 = this.values[1][1];
        matrix4d.m12 = this.values[1][2];
        matrix4d.m13 = this.values[1][3];
        matrix4d.m20 = this.values[2][0];
        matrix4d.m21 = this.values[2][1];
        matrix4d.m22 = this.values[2][2];
        matrix4d.m23 = this.values[2][3];
        matrix4d.m30 = this.values[3][0];
        matrix4d.m31 = this.values[3][1];
        matrix4d.m32 = this.values[3][2];
        matrix4d.m33 = this.values[3][3];
    }

    private static void update_u(
            int p171_0, GMatrix p171_1, double[] p171_2, double[] p171_3) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        int j = 0;
        java.lang.String[] astring = astring1;

        while (j < p171_1.nCol) {
            double d0 = p171_1.values[p171_0][j];
            p171_1.values[p171_0][j] = p171_2[0] * d0 + p171_3[0] * p171_1.values[p171_0 + 1][j];
            p171_1.values[p171_0 + 1][j] = -p171_3[0] * d0 + p171_2[0] * p171_1.values[p171_0 + 1][j];
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    private static void svdReorder(
            double[] adouble,
            double[] adouble1,
            int i,
            GMatrix gMatrix) {
        double[] adouble2 = new double[1];
        String[] astring1 = Tuple3f.hF();
        double[] adouble3 = new double[1];
        GMatrix gMatrixValue =
                new GMatrix(
                        gMatrix.nRow, gMatrix.nCol);
        String[] astring = astring1;
        GMatrix gMatrixValue2 =
                new GMatrix(
                        gMatrix.nRow, gMatrix.nCol);
        double d1 = adouble1[i];
        double d0 = adouble[i + 1];
        int j = i;

        while (j < gMatrix.nCol - 2) {
            double d2 = compute_rot(d0, d1, adouble3, adouble2);
            d1 = -adouble1[j + 1] * adouble3[0];
            d0 = adouble[j + 2];
            adouble[j + 1] = d2;
            adouble1[j + 1] = adouble1[j + 1] * adouble2[0];
            compute_2X2(
                    i,
                    j + 1,
                    gMatrix,
                    adouble2,
                    adouble3,
                    gMatrixValue,
                    gMatrixValue2);
            j++;
            if (astring != null) {
                break;
            }
        }

        adouble[j + 1] = compute_rot(d0, d1, adouble3, adouble2);
        compute_2X2(
                i,
                j + 1,
                gMatrix,
                adouble2,
                adouble3,
                gMatrixValue,
                gMatrixValue2);
    }

    private static void compute_svd(
            int i,
            int j,
            GMatrix gMatrix,
            double[] adouble,
            double[] adouble1,
            GMatrix gMatrixValue,
            GMatrix gMatrixValue2) {
        String[] astring1 = Tuple3f.hF();
        int k = 0;
        String[] astring = astring1;

        while (k < gMatrix.nRow) {
            double d0 = gMatrix.values[k][i];
            gMatrix.values[k][i] =
                    adouble[0] * d0 - adouble1[0] * gMatrix.values[k][j];
            gMatrix.values[k][j] =
                    adouble1[0] * d0 + adouble[0] * gMatrix.values[k][j];
            k++;
            if (astring != null) {
                break;
            }
        }

        System.out.println("topr    =" + i);
        System.out.println("bottomr =" + j);
        System.out.println("cosr =" + adouble[0]);
        System.out.println("sinr =" + adouble1[0]);
        System.out.println("\nm =");
        checkMatrix(gMatrixValue2);
        System.out.println("\nv =");
        checkMatrix(gMatrixValue);
        gMatrixValue2.mul(
                gMatrixValue2, gMatrixValue);
        System.out.println("\nt*m =");
        checkMatrix(gMatrixValue2);
    }

    private static void getScaleRotate(double[] adouble, double[] adouble1) {
        System.out.println(
                "\ns ="
                        + adouble[0]
                        + packedTextFragments[4]
                        + adouble[1]
                        + packedTextFragments[5]
                        + adouble[2]);
        System.out.println("e =" + adouble1[0] + packedTextFragments[6] + adouble1[1]);
    }

    public final void setIdentity() {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        int i = 0;
        java.lang.String[] astring = astring1;

        label39:
        while (i < this.nRow) {
            int j = 0;

            while (true) {
                if (j < this.nCol) {
                    this.values[i][j] = 0.0;
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    break label39;
                }
                break;
            }
        }

        int k;
        {
            if (this.nRow < this.nCol) {
                k = this.nRow;
                if (!(astring == null)) {

                    k = this.nCol;
                }
            } else {

                k = this.nCol;
            }
        }

        i = 0;

        while (i < k) {
            this.values[i][i] = 1.0;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void setColumn(
            int i, GVector gVector) {
        String[] astring = Tuple3f.hF();
        if (gVector.getSize() < this.nCol) {
            gVector.a(this.nCol);
        }

        int j = 0;

        while (j < this.nCol) {
            gVector.values[j] = this.values[i][j];
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void copySubMatrix(
            int p171_0,
            int p171_1,
            int p171_2,
            int p171_3,
            int p171_4,
            int p171_5,
            GMatrix p171_6) {
        java.lang.String[] astring = remix.compat.vecmath.Tuple3f.hF();
        if (this != p171_6) {
            int k1 = 0;

            do {
                if (k1 >= p171_2) {
                    return;
                }

                int l1 = 0;

                while (l1 < p171_3) {
                    p171_6.values[p171_4 + k1][p171_5 + l1] = this.values[p171_0 + k1][p171_1 + l1];
                    l1++;
                    if (astring != null) {
                        break;
                    }
                }

                k1++;
            } while (astring == null);
        }

        double[][] adouble = new double[p171_2][p171_3];
        int i2 = 0;

        while (i2 < p171_2) {
            int j2 = 0;

            while (j2 < p171_3) {
                adouble[i2][j2] = this.values[p171_0 + i2][p171_1 + j2];
                j2++;
                if (astring != null) {
                    break;
                }
            }

            i2++;
            if (astring != null) {
                break;
            }
        }

        i2 = 0;

        while (i2 < p171_2) {
            int k2 = 0;

            while (k2 < p171_3) {
                p171_6.values[p171_4 + i2][p171_5 + k2] = adouble[i2][k2];
                k2++;
                if (astring != null) {
                    break;
                }
            }

            i2++;
            if (astring != null) {
                break;
            }
        }
    }

    /** @deprecated */
    public boolean epsilonEquals(GMatrix p171_0, float p171_1) {
        return this.epsilonEquals(p171_0, p171_1);
    }

    @Override
    public boolean equals(Object p171_0) {
        java.lang.String[] astring = remix.compat.vecmath.Tuple3f.hF();

        try {
            remix.compat.vecmath.GMatrix gMatrix =
                    (remix.compat.vecmath.GMatrix) p171_0;

            try {
                if (this.nRow != gMatrix.nRow
                        || this.nCol != gMatrix.nCol) {
                    return false;
                }
            } catch (java.lang.ClassCastException classcastexception) {
                throw remix.compat.vecmath.GMatrix.wrapException(
                        classcastexception);
            }

            int i = 0;

            while (i < this.nRow) {
                int j = 0;

                while (true) {
                    if (j < this.nCol) {
                        if (this.values[i][j] != gMatrix.values[i][j]) {
                            return false;
                        }

                        j++;
                        if (astring == null) {
                            continue;
                        }
                    }

                    i++;
                    if (astring != null) {
                        return true;
                    }
                    break;
                }
            }

            return true;
        } catch (java.lang.ClassCastException classcastexception1) {
            return false;
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final void mulTransposeRight(
            GMatrix gMatrix,
            GMatrix gMatrixValue) {
        String[] astring = Tuple3f.hF();
        if (gMatrix.nCol == gMatrixValue.nCol
                && this.nCol == gMatrixValue.nRow
                && this.nRow == gMatrix.nRow) {
            if (gMatrix == this || gMatrixValue == this) {
                double[][] adouble = new double[this.nRow][this.nCol];
                int i = 0;

                while (i < this.nRow) {
                    int j = 0;

                    while (j < this.nCol) {
                        adouble[i][j] = 0.0;
                        int k = 0;

                        while (k < gMatrix.nCol) {
                            adouble[i][j] =
                                    adouble[i][j]
                                            + gMatrix.values[i][k]
                                                    * gMatrixValue.values[j][k];
                            k++;
                            if (astring != null) {
                                break;
                            }
                        }

                        j++;
                        if (astring != null) {
                            break;
                        }
                    }

                    i++;
                    if (astring != null) {
                        break;
                    }
                }

                this.values = adouble;
                if (astring == null) {
                    return;
                }
            }

            int l = 0;

            while (l < this.nRow) {
                int i1 = 0;

                while (i1 < this.nCol) {
                    this.values[l][i1] = 0.0;
                    int j1 = 0;

                    while (j1 < gMatrix.nCol) {
                        this.values[l][i1] =
                                this.values[l][i1]
                                        + gMatrix.values[l][j1]
                                                * gMatrixValue.values[i1][j1];
                        j1++;
                        if (astring != null) {
                            break;
                        }
                    }

                    i1++;
                    if (astring != null) {
                        break;
                    }
                }

                l++;
                if (astring != null) {
                    break;
                }
            }
        } else {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix15"));
        }
    }

    public final void add(
            GMatrix gMatrix,
            GMatrix gMatrixValue) {
        String[] astring = Tuple3f.hF();
        if (gMatrixValue.nRow != gMatrix.nRow) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix10"));
        }

        if (gMatrixValue.nCol != gMatrix.nCol) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix11"));
        }

        if (this.nRow == gMatrix.nRow && this.nCol == gMatrix.nCol) {
            int i = 0;

            while (i < this.nRow) {
                int j = 0;

                while (true) {
                    if (j < this.nCol) {
                        this.values[i][j] =
                                gMatrix.values[i][j]
                                        - gMatrixValue.values[i][j];
                        j++;
                        if (astring == null) {
                            continue;
                        }
                    }

                    i++;
                    if (astring != null) {
                        return;
                    }
                    break;
                }
            }
        } else {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix12"));
        }
    }

    public final void cj() {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        int i = 0;
        java.lang.String[] astring = astring1;

        while (i < this.nRow) {
            int j = 0;

            while (true) {
                if (j < this.nCol) {
                    this.values[i][j] = 0.0;
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    static double d_sign(double d0, double d1) {
        double d2 = d0 >= 0.0 ? d0 : -d0;
        return d1 >= 0.0 ? d2 : -d2;
    }

    @Override
    public Object clone() {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        remix.compat.vecmath.GMatrix gMatrix = null;
        java.lang.String[] astring = astring1;

        try {
            gMatrix = (remix.compat.vecmath.GMatrix) super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }

        gMatrix.values = new double[this.nRow][this.nCol];
        int i = 0;

        while (i < this.nRow) {
            int j = 0;

            while (true) {
                if (j < this.nCol) {
                    gMatrix.values[i][j] = this.values[i][j];
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return gMatrix;
                }
                break;
            }
        }

        return gMatrix;
    }

    private static void checkMatrix(
            GMatrix gMatrix) {
        String[] astring1 = Tuple3f.hF();
        int i = 0;
        String[] astring = astring1;

        while (i < gMatrix.nRow) {
            int j = 0;

            while (true) {
                if (j < gMatrix.nCol) {
                    {
                        if (Math.abs(gMatrix.values[i][j]) < 1.0E-10) {
                            System.out.print(" 0.0     ");
                            if (!(astring == null)) {

                                System.out.print(
                                        packedTextFragments[2] + gMatrix.values[i][j]);
                            }
                        } else {

                            System.out.print(
                                    packedTextFragments[2] + gMatrix.values[i][j]);
                        }
                    }

                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                System.out.print(packedTextFragments[3]);
                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    final void invertGeneral(GMatrix gMatrix) {
        int i = gMatrix.nRow * gMatrix.nCol;
        String[] astring1 = Tuple3f.hF();
        double[] adouble = new double[i];
        double[] adouble1 = new double[i];
        int[] aint = new int[gMatrix.nRow];
        int[] aint1 = new int[1];
        String[] astring = astring1;
        if (gMatrix.nRow != gMatrix.nCol) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix22"));
        }

        int j = 0;

        label68:
        while (j < this.nRow) {
            int k = 0;

            while (true) {
                if (k < this.nCol) {
                    adouble[j * this.nCol + k] = gMatrix.values[j][k];
                    k++;
                    if (astring == null) {
                        continue;
                    }
                }

                j++;
                if (astring != null) {
                    break label68;
                }
                break;
            }
        }

        if (!luDecomposition(gMatrix.nRow, adouble, aint, aint1)) {
            throw new SingularMatrixException(VecMathI18N.getString("GMatrix21"));
        }

        j = 0;

        while (j < i) {
            adouble1[j] = 0.0;
            j++;
            if (astring != null) {
                break;
            }
        }

        j = 0;

        while (j < this.nCol) {
            adouble1[j + j * this.nCol] = 1.0;
            j++;
            if (astring != null) {
                break;
            }
        }

        luDecomposition(gMatrix.nRow, adouble, aint, adouble1);
        j = 0;

        while (j < this.nRow) {
            int l = 0;

            while (true) {
                if (l < this.nCol) {
                    this.values[j][l] = adouble1[j * this.nCol + l];
                    l++;
                    if (astring == null) {
                        continue;
                    }
                }

                j++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public final void getColumn(int i, double[] adouble) {
        String[] astring1 = Tuple3f.hF();
        int j = 0;
        String[] astring = astring1;

        while (j < this.nRow) {
            adouble[j] = this.values[j][i];
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void mul(
            GVector gVector,
            GVector gVectorValue) {
        String[] astring = Tuple3f.hF();
        if (this.nRow < gVector.getSize()) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix2"));
        }

        if (this.nCol < gVectorValue.getSize()) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix3"));
        }

        int i = 0;

        while (i < gVector.getSize()) {
            int j = 0;

            while (true) {
                if (j < gVectorValue.getSize()) {
                    this.values[i][j] =
                            gVector.values[i] * gVectorValue.values[j];
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public final void invert() {
        this.invertGeneral(this);
    }

    public GMatrix(int i, int j) {
        this.values = new double[i][j];
        String[] astring1 = Tuple3f.hF();
        this.nRow = i;
        this.nCol = j;
        String[] astring = astring1;
        int k = 0;

        label39:
        while (k < i) {
            int l = 0;

            while (true) {
                if (l < j) {
                    this.values[k][l] = 0.0;
                    l++;
                    if (astring == null) {
                        continue;
                    }
                }

                k++;
                if (astring != null) {
                    break label39;
                }
                break;
            }
        }

        int i1;
        {
            if (i < j) {
                i1 = i;
                if (!(astring == null)) {

                    i1 = j;
                }
            } else {

                i1 = j;
            }
        }

        k = 0;

        while (k < i1) {
            this.values[k][k] = 1.0;
            k++;
            if (astring != null) {
                break;
            }
        }
    }

    static double min(double d0, double d1) {
        return d0 < d1 ? d0 : d1;
    }

    static void luDecomposition(
            int i, double[] adouble, int[] aint, double[] adouble1) {
        String[] astring1 = Tuple3f.hF();
        byte b0 = 0;
        String[] astring = astring1;
        int j1 = 0;

        while (j1 < i) {
            int k1 = j1;
            int k = -1;
            int j = 0;

            while (j < i) {
                double d1;
                label52:
                {
                    int l = aint[b0 + j];
                    d1 = adouble1[k1 + i * l];
                    adouble1[k1 + i * l] = adouble1[k1 + i * j];
                    if (k >= 0) {
                        int l1 = j * i;
                        int i1 = k;

                        do {
                            if (i1 > j - 1) {
                                break label52;
                            }

                            d1 -= adouble[l1 + i1] * adouble1[k1 + i * i1];
                            i1++;
                        } while (astring == null);
                    }

                    if (d1 != 0.0) {
                        k = j;
                    }
                }

                adouble1[k1 + i * j] = d1;
                j++;
                if (astring != null) {
                    break;
                }
            }

            j = 0;

            while (true) {
                if (j < i) {
                    int i2 = i - 1 - j;
                    int k2 = i * i2;
                    double d0 = 0.0;
                    int j2 = 1;

                    while (j2 <= j) {
                        d0 += adouble[k2 + i - j2] * adouble1[k1 + i * (i - j2)];
                        j2++;
                        if (astring != null) {
                            break;
                        }
                    }

                    adouble1[k1 + i * i2] = (adouble1[k1 + i * i2] - d0) / adouble[k2 + i2];
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                j1++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public final void setRow(int i, double[] adouble) {
        String[] astring1 = Tuple3f.hF();
        int j = 0;
        String[] astring = astring1;

        while (j < this.nCol) {
            adouble[j] = this.values[i][j];
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void setElement(int p171_0, int p171_1, double p171_2) {
        this.values[p171_0][p171_1] = p171_2;
    }

    public final void get(
            Matrix3f matrix3f) {
        String[] astring = Tuple3f.hF();
        if (this.nRow < 3 || this.nCol < 3) {
            matrix3f.setZero();
            if (this.nCol <= 0) {
                return;
            }

            if (this.nRow > 0) {
                matrix3f.m00 = (float) this.values[0][0];
                if (this.nRow > 1) {
                    matrix3f.m10 = (float) this.values[1][0];
                    if (this.nRow > 2) {
                        matrix3f.m20 = (float) this.values[2][0];
                    }
                }
            }

            if (this.nCol <= 1) {
                return;
            }

            if (this.nRow > 0) {
                matrix3f.m01 = (float) this.values[0][1];
                if (this.nRow > 1) {
                    matrix3f.m11 = (float) this.values[1][1];
                    if (this.nRow > 2) {
                        matrix3f.m21 = (float) this.values[2][1];
                    }
                }
            }

            if (this.nCol <= 2 || this.nRow <= 0) {
                return;
            }

            matrix3f.m02 = (float) this.values[0][2];
            if (this.nRow <= 1) {
                return;
            }

            matrix3f.m12 = (float) this.values[1][2];
            if (this.nRow <= 2) {
                return;
            }

            matrix3f.m22 = (float) this.values[2][2];
            if (astring == null) {
                return;
            }
        }

        matrix3f.m00 = (float) this.values[0][0];
        matrix3f.m01 = (float) this.values[0][1];
        matrix3f.m02 = (float) this.values[0][2];
        matrix3f.m10 = (float) this.values[1][0];
        matrix3f.m11 = (float) this.values[1][1];
        matrix3f.m12 = (float) this.values[1][2];
        matrix3f.m20 = (float) this.values[2][0];
        matrix3f.m21 = (float) this.values[2][1];
        matrix3f.m22 = (float) this.values[2][2];
    }

    public final void sub(
            GMatrix gMatrix) {
        String[] astring = Tuple3f.hF();
        if (this.nRow < gMatrix.nRow || this.nCol < gMatrix.nCol) {
            this.nRow = gMatrix.nRow;
            this.nCol = gMatrix.nCol;
            this.values = new double[this.nRow][this.nCol];
        }

        int i = 0;

        label44:
        while (i < Math.min(this.nRow, gMatrix.nRow)) {
            int j = 0;

            while (true) {
                if (j < Math.min(this.nCol, gMatrix.nCol)) {
                    this.values[i][j] = gMatrix.values[i][j];
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    break label44;
                }
                break;
            }
        }

        i = gMatrix.nRow;

        while (i < this.nRow) {
            int k = gMatrix.nCol;

            while (true) {
                if (k < this.nCol) {
                    this.values[i][k] = 0.0;
                    k++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    private static void print_m(
            GMatrix gMatrix,
            GMatrix gMatrixValue,
            GMatrix gMatrixValue2) {
        GMatrix gMatrixValue3 =
                new GMatrix(
                        gMatrix.nCol, gMatrix.nRow);
        gMatrixValue3.mul(
                gMatrixValue, gMatrixValue3);
        gMatrixValue3.mul(
                gMatrixValue3, gMatrixValue2);
        System.out.println("\n m = \n" + toString(gMatrixValue3));
    }

    public final void mul(GMatrix p171_0) {
        java.lang.String[] astring;
        int k;
        {
            astring = remix.compat.vecmath.Tuple3f.hF();
            if (this.nCol < p171_0.nCol) {
                k = this.nCol;
                if (!(astring == null)) {

                    k = p171_0.nCol;
                }
            } else {

                k = p171_0.nCol;
            }
        }

        int l;
        {
            if (this.nRow < p171_0.nRow) {
                l = this.nRow;
                if (!(astring == null)) {

                    l = p171_0.nRow;
                }
            } else {

                l = p171_0.nRow;
            }
        }

        int i = 0;

        label65:
        while (i < l) {
            int j = 0;

            while (true) {
                if (j < k) {
                    p171_0.values[i][j] = this.values[i][j];
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    break label65;
                }
                break;
            }
        }

        i = l;

        label52:
        while (i < p171_0.nRow) {
            int i1 = 0;

            while (true) {
                if (i1 < p171_0.nCol) {
                    p171_0.values[i][i1] = 0.0;
                    i1++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    break label52;
                }
                break;
            }
        }

        int j1 = k;

        while (j1 < p171_0.nCol) {
            i = 0;

            while (true) {
                if (i < l) {
                    p171_0.values[i][j1] = 0.0;
                    i++;
                    if (astring == null) {
                        continue;
                    }
                }

                j1++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    static void compute_qr(
            int i,
            int j,
            double[] adouble,
            double[] adouble1,
            GMatrix gMatrix,
            GMatrix gMatrixValue) {
        String[] astring1 = Tuple3f.hF();
        double[] adouble2 = new double[1];
        String[] astring = astring1;
        double[] adouble3 = new double[1];
        double[] adouble4 = new double[1];
        double[] adouble5 = new double[1];
        new GMatrix(
                gMatrix.nCol, gMatrixValue.nRow);

        double d5 = 1.0;

        byte b0 = 0;
        double d2 = 0.0;
        double d3 = 0.0;
        int l = 0;

        while (l < 2 && b0 == 0) {
            int k = i;

            while (k <= j) {
                if (k == i) {
                    int j1;
                    {
                        if (adouble1.length == adouble.length) {
                            j1 = j;
                            if (!(astring == null)) {

                                j1 = j + 1;
                            }
                        } else {

                            j1 = j + 1;
                        }
                    }

                    double d0 =
                            compute_shift(adouble[j1 - 1], adouble1[j], adouble[j1]);
                    d2 =
                            (Math.abs(adouble[k]) - d0)
                                    * (d_sign(d5, adouble[k])
                                            + d0 / adouble[k]);
                    d3 = adouble1[k];
                }

                double d1 = compute_rot(d2, d3, adouble5, adouble3);
                if (k != i) {
                    adouble1[k - 1] = d1;
                }

                d2 = adouble3[0] * adouble[k] + adouble5[0] * adouble1[k];
                adouble1[k] = adouble3[0] * adouble1[k] - adouble5[0] * adouble[k];
                d3 = adouble5[0] * adouble[k + 1];
                adouble[k + 1] = adouble3[0] * adouble[k + 1];
                update_v(k, gMatrixValue, adouble3, adouble5);
                d1 = compute_rot(d2, d3, adouble4, adouble2);
                adouble[k] = d1;
                d2 = adouble2[0] * adouble1[k] + adouble4[0] * adouble[k + 1];
                adouble[k + 1] = adouble2[0] * adouble[k + 1] - adouble4[0] * adouble1[k];
                if (k < j) {
                    d3 = adouble4[0] * adouble1[k + 1];
                    adouble1[k + 1] = adouble2[0] * adouble1[k + 1];
                }

                update_u(k, gMatrix, adouble2, adouble4);
                k++;
                if (astring != null) {
                    break;
                }
            }

            if (adouble.length == adouble1.length) {
                double d7 = compute_rot(d2, d3, adouble5, adouble3);
                d2 = adouble3[0] * adouble[k] + adouble5[0] * adouble1[k];
                adouble1[k] = adouble3[0] * adouble1[k] - adouble5[0] * adouble[k];
                adouble[k + 1] = adouble3[0] * adouble[k + 1];
                update_v(k, gMatrixValue, adouble3, adouble5);
            }

            while (j - i > 1 && Math.abs(adouble1[j]) < 4.89E-15) {
                j--;
                if (astring != null) {
                    break;
                }
            }

            int i1 = j - 2;

            while (i1 > i) {
                if (Math.abs(adouble1[i1]) < 4.89E-15) {
                    compute_qr(
                            i1 + 1,
                            j,
                            adouble,
                            adouble1,
                            gMatrix,
                            gMatrixValue);
                    j = i1 - 1;

                    while (j - i > 1 && Math.abs(adouble1[j]) < 4.89E-15) {
                        j--;
                        if (astring != null) {
                            break;
                        }
                    }
                }

                i1--;
                if (astring != null) {
                    break;
                }
            }

            if (j - i <= 1 && Math.abs(adouble1[i + 1]) < 4.89E-15) {
                b0 = 1;
            }

            l++;
            if (astring != null) {
                break;
            }
        }

        if (Math.abs(adouble1[1]) < 4.89E-15) {
            compute_2X2(
                    adouble[i],
                    adouble1[i],
                    adouble[i + 1],
                    adouble,
                    adouble4,
                    adouble2,
                    adouble5,
                    adouble3,
                    0);
            adouble1[i] = 0.0;
            adouble1[i + 1] = 0.0;
        }

        int k1 = i;
        update_u(k1, gMatrix, adouble2, adouble4);
        update_v(k1, gMatrixValue, adouble3, adouble5);
    }

    public final void getColumn(
            int i, GVector gVector) {
        String[] astring1 = Tuple3f.hF();
        int j = 0;
        String[] astring = astring1;

        while (j < this.nCol) {
            this.values[i][j] = gVector.values[j];
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    static double compute_rot(
            double d0, double d1, double[] adouble, double[] adouble1) {
        double d2;
        double d3;
        double d7;
        label72:
        {
            String[] astring1 = Tuple3f.hF();

            String[] astring = astring1;
            if (d1 == 0.0) {
                d2 = 1.0;
                d3 = 0.0;
                d7 = d0;
                if (astring == null) {
                    break label72;
                }
            }

            if (d0 == 0.0) {
                d2 = 0.0;
                d3 = 1.0;
                d7 = d1;
                if (astring == null) {
                    break label72;
                }
            }

            label73:
            {
                double d5 = d0;
                double d6 = d1;
                double d4 = max(Math.abs(d5), Math.abs(d6));
                if (d4 >= 4.994797680505588E145) {
                    int j = 0;

                    while (d4 >= 4.994797680505588E145) {
                        j++;
                        d5 *= 2.002083095183101E-146;
                        d6 *= 2.002083095183101E-146;
                        d4 = max(Math.abs(d5), Math.abs(d6));
                        if (astring != null) {
                            break;
                        }
                    }

                    d7 = Math.sqrt(d5 * d5 + d6 * d6);
                    d2 = d5 / d7;
                    d3 = d6 / d7;
                    int i = 1;

                    do {
                        if (i > j) {
                            break label73;
                        }

                        d7 *= 4.994797680505588E145;
                        i++;
                    } while (astring == null);
                }

                if (d4 <= 2.002083095183101E-146) {
                    int l = 0;

                    while (d4 <= 2.002083095183101E-146) {
                        l++;
                        d5 *= 4.994797680505588E145;
                        d6 *= 4.994797680505588E145;
                        d4 = max(Math.abs(d5), Math.abs(d6));
                        if (astring != null) {
                            break;
                        }
                    }

                    d7 = Math.sqrt(d5 * d5 + d6 * d6);
                    d2 = d5 / d7;
                    d3 = d6 / d7;
                    int k = 1;

                    do {
                        if (k > l) {
                            break label73;
                        }

                        d7 *= 2.002083095183101E-146;
                        k++;
                    } while (astring == null);
                }

                d7 = Math.sqrt(d5 * d5 + d6 * d6);
                d2 = d5 / d7;
                d3 = d6 / d7;
            }

            if (Math.abs(d0) > Math.abs(d1) && d2 < 0.0) {
                d2 = -d2;
                d3 = -d3;
                d7 = -d7;
            }
        }

        adouble[0] = d3;
        adouble1[0] = d2;
        return d7;
    }

    static double compute_shift(double d0, double d1, double d2) {
        String[] astring1 = Tuple3f.hF();
        double d8 = Math.abs(d0);
        String[] astring = astring1;
        double d9 = Math.abs(d1);
        double d10 = Math.abs(d2);
        double d5 = min(d8, d10);
        double d6 = max(d8, d10);
        if (d5 == 0.0) {
            double d14 = 0.0;
            if (d6 == 0.0) {
                return d14;
            }

            double d3 = min(d6, d9) / max(d6, d9);
            if (astring == null) {
                return d14;
            }
        }

        if (d9 < d6) {
            double d11 = d5 / d6 + 1.0;
            double d12 = (d6 - d5) / d6;
            double d15 = d9 / d6;
            double d13 = d15 * d15;
            double d7 = 2.0 / (Math.sqrt(d11 * d11 + d13) + Math.sqrt(d12 * d12 + d13));
            double d22 = d5 * d7;
            if (astring == null) {
                return d22;
            }
        }

        double d20 = d6 / d9;
        if (d20 == 0.0) {
            double d23 = d5 * d6 / d9;
            if (astring == null) {
                return d23;
            }
        }

        double d18 = d5 / d6 + 1.0;
        double d19 = (d6 - d5) / d6;
        double d16 = d18 * d20;
        double d4 = d19 * d20;
        double d17 = 1.0 / (Math.sqrt(d16 * d16 + 1.0) + Math.sqrt(d4 * d4 + 1.0));
        double d21 = d5 * d17 * d20;
        return d21 + d21;
    }

    public final int getNumCol() {
        return this.nCol;
    }

    public final void fF() {
        java.lang.String[] astring = remix.compat.vecmath.Tuple3f.hF();
        if (this.nRow != this.nCol) {
            int i = this.nRow;
            this.nRow = this.nCol;
            this.nCol = i;
            double[][] adouble = new double[this.nRow][this.nCol];
            i = 0;

            while (i < this.nRow) {
                int j = 0;

                while (j < this.nCol) {
                    adouble[i][j] = this.values[j][i];
                    j++;
                    if (astring != null) {
                        break;
                    }
                }

                i++;
                if (astring != null) {
                    break;
                }
            }

            this.values = adouble;
            if (astring == null) {
                return;
            }
        }

        int k = 0;

        while (k < this.nRow) {
            int l = 0;

            while (l < k) {
                double d0 = this.values[k][l];
                this.values[k][l] = this.values[l][k];
                this.values[l][k] = d0;
                l++;
                if (astring != null) {
                    break;
                }
            }

            k++;
            if (astring != null) {
                break;
            }
        }
    }

    public final double getElement(int p171_0, int p171_1) {
        return this.values[p171_0][p171_1];
    }

    public final void get(
            Matrix3d matrix3d) {
        String[] astring = Tuple3f.hF();
        if (this.nRow < 3 || this.nCol < 3) {
            matrix3d.setZero();
            if (this.nCol <= 0) {
                return;
            }

            if (this.nRow > 0) {
                matrix3d.m00 = this.values[0][0];
                if (this.nRow > 1) {
                    matrix3d.m10 = this.values[1][0];
                    if (this.nRow > 2) {
                        matrix3d.m20 = this.values[2][0];
                    }
                }
            }

            if (this.nCol <= 1) {
                return;
            }

            if (this.nRow > 0) {
                matrix3d.m01 = this.values[0][1];
                if (this.nRow > 1) {
                    matrix3d.m11 = this.values[1][1];
                    if (this.nRow > 2) {
                        matrix3d.m21 = this.values[2][1];
                    }
                }
            }

            if (this.nCol <= 2 || this.nRow <= 0) {
                return;
            }

            matrix3d.m02 = this.values[0][2];
            if (this.nRow <= 1) {
                return;
            }

            matrix3d.m12 = this.values[1][2];
            if (this.nRow <= 2) {
                return;
            }

            matrix3d.m22 = this.values[2][2];
            if (astring == null) {
                return;
            }
        }

        matrix3d.m00 = this.values[0][0];
        matrix3d.m01 = this.values[0][1];
        matrix3d.m02 = this.values[0][2];
        matrix3d.m10 = this.values[1][0];
        matrix3d.m11 = this.values[1][1];
        matrix3d.m12 = this.values[1][2];
        matrix3d.m20 = this.values[2][0];
        matrix3d.m21 = this.values[2][1];
        matrix3d.m22 = this.values[2][2];
    }

    private static void update_v(
            int p171_0, GMatrix p171_1, double[] p171_2, double[] p171_3) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        int j = 0;
        java.lang.String[] astring = astring1;

        while (j < p171_1.nRow) {
            double d0 = p171_1.values[j][p171_0];
            p171_1.values[j][p171_0] = p171_2[0] * d0 + p171_3[0] * p171_1.values[j][p171_0 + 1];
            p171_1.values[j][p171_0 + 1] = -p171_3[0] * d0 + p171_2[0] * p171_1.values[j][p171_0 + 1];
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    // region String decoding support

    // endregion

    public final void setSize(int p171_0, int p171_1) {
        java.lang.String[] astring;
        double[][] adouble;
        int i1;
        {
            java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
            adouble = new double[p171_0][p171_1];
            astring = astring1;
            if (this.nRow < p171_0) {
                i1 = this.nRow;
                if (!(astring == null)) {

                    i1 = p171_0;
                }
            } else {

                i1 = p171_0;
            }
        }

        int j1;
        {
            if (this.nCol < p171_1) {
                j1 = this.nCol;
                if (!(astring == null)) {

                    j1 = p171_1;
                }
            } else {

                j1 = p171_1;
            }
        }

        int k = 0;

        label27:
        while (k < i1) {
            int l = 0;

            while (true) {
                if (l < j1) {
                    adouble[k][l] = this.values[k][l];
                    l++;
                    if (astring == null) {
                        continue;
                    }
                }

                k++;
                if (astring != null) {
                    break label27;
                }
                break;
            }
        }

        this.nRow = p171_0;
        this.nCol = p171_1;
        this.values = adouble;
    }

    public final void getRow(int i, double[] adouble) {
        String[] astring1 = Tuple3f.hF();
        int j = 0;
        String[] astring = astring1;

        while (j < this.nRow) {
            this.values[j][i] = adouble[j];
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void negate(
            GMatrix gMatrix) {
        String[] astring = Tuple3f.hF();
        if (this.nRow == gMatrix.nRow && this.nCol == gMatrix.nCol) {
            int i = 0;

            while (i < this.nRow) {
                int j = 0;

                while (true) {
                    if (j < this.nCol) {
                        this.values[i][j] = -gMatrix.values[i][j];
                        j++;
                        if (astring == null) {
                            continue;
                        }
                    }

                    i++;
                    if (astring != null) {
                        return;
                    }
                    break;
                }
            }
        } else {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix13"));
        }
    }

    public final void invert(
            GMatrix gMatrix) {
        this.invertGeneral(gMatrix);
    }

    public final void setColumn(int i, double[] adouble) {
        String[] astring1 = Tuple3f.hF();
        int j = 0;
        String[] astring = astring1;

        while (j < this.nCol) {
            this.values[i][j] = adouble[j];
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    private static void print_svd(
            double[] adouble,
            double[] adouble1,
            GMatrix gMatrix,
            GMatrix gMatrixValue) {
        GMatrix gMatrixValue2 =
                new GMatrix(
                        gMatrix.nCol, gMatrixValue.nRow);
        String[] astring1 = Tuple3f.hF();
        System.out.println(" \ns = ");
        int i = 0;
        String[] astring = astring1;

        while (i < adouble.length) {
            System.out.println(packedTextFragments[9] + adouble[i]);
            i++;
            if (astring != null) {
                break;
            }
        }

        System.out.println(" \ne = ");
        i = 0;

        while (i < adouble1.length) {
            System.out.println(packedTextFragments[10] + adouble1[i]);
            i++;
            if (astring != null) {
                break;
            }
        }

        System.out.println(" \nu  = \n" + gMatrix.toString());
        System.out.println(" \nv  = \n" + gMatrixValue.toString());
        gMatrixValue2.setIdentity();
        i = 0;

        while (i < adouble.length) {
            gMatrixValue2.values[i][i] = adouble[i];
            i++;
            if (astring != null) {
                break;
            }
        }

        i = 0;

        while (i < adouble1.length) {
            gMatrixValue2.values[i][i + 1] = adouble1[i];
            i++;
            if (astring != null) {
                break;
            }
        }

        System.out.println(" \nm  = \n" + gMatrixValue2.toString());
        gMatrixValue2.mulTransposeLeft(
                gMatrix, gMatrixValue2);
        gMatrixValue2.mulTransposeRight(
                gMatrixValue2, gMatrixValue);
        System.out.println(
                " \n u.transpose*m*v.transpose  = \n" + gMatrixValue2.toString());
    }

    public final int LUD(
            GMatrix gMatrix,
            GVector gVector) {
        String[] astring1 = Tuple3f.hF();
        int i = gMatrix.nRow * gMatrix.nCol;
        double[] adouble = new double[i];
        int[] aint = new int[1];
        String[] astring = astring1;
        int[] aint1 = new int[gMatrix.nRow];
        if (this.nRow != this.nCol) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix19"));
        }

        if (this.nRow != gMatrix.nRow) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix27"));
        }

        if (this.nCol != gMatrix.nCol) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix27"));
        }

        if (gMatrix.nRow != gVector.getSize()) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix20"));
        }

        int j = 0;

        label70:
        while (j < this.nRow) {
            int k = 0;

            while (true) {
                if (k < this.nCol) {
                    adouble[j * this.nCol + k] = this.values[j][k];
                    k++;
                    if (astring == null) {
                        continue;
                    }
                }

                j++;
                if (astring != null) {
                    break label70;
                }
                break;
            }
        }

        if (!luDecomposition(gMatrix.nRow, adouble, aint1, aint)) {
            throw new SingularMatrixException(VecMathI18N.getString("GMatrix21"));
        }

        j = 0;

        label57:
        while (j < this.nRow) {
            int l = 0;

            while (true) {
                if (l < this.nCol) {
                    gMatrix.values[j][l] = adouble[j * this.nCol + l];
                    l++;
                    if (astring == null) {
                        continue;
                    }
                }

                j++;
                if (astring != null) {
                    break label57;
                }
                break;
            }
        }

        j = 0;

        while (j < gMatrix.nRow) {
            gVector.values[j] = aint1[j];
            j++;
            if (astring != null) {
                break;
            }
        }

        return aint[0];
    }

    private static String toString(
            GMatrix gMatrix) {
        String[] astring1 = Tuple3f.hF();
        StringBuffer stringbuffer =
                new StringBuffer(gMatrix.nRow * gMatrix.nCol * 8);
        int i = 0;
        String[] astring = astring1;

        while (i < gMatrix.nRow) {
            int j = 0;

            while (true) {
                if (j < gMatrix.nCol) {
                    {
                        if (Math.abs(gMatrix.values[i][j]) < 1.0E-9) {
                            stringbuffer.append("0.0000 ");
                            if (!(astring == null)) {

                                stringbuffer
                                        .append(gMatrix.values[i][j])
                                        .append(packedTextFragments[7]);
                            }
                        } else {

                            stringbuffer
                                    .append(gMatrix.values[i][j])
                                    .append(packedTextFragments[7]);
                        }
                    }

                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                stringbuffer.append(packedTextFragments[8]);
                i++;
                if (astring != null) {
                    return stringbuffer.toString();
                }
                break;
            }
        }

        return stringbuffer.toString();
    }

    public final int SVD(
            GMatrix gMatrix,
            GMatrix gMatrixValue,
            GMatrix gMatrixValue2) {
        if (this.nCol != gMatrixValue2.nCol || this.nCol != gMatrixValue2.nRow) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix18"));
        }

        if (this.nRow != gMatrix.nRow || this.nRow != gMatrix.nCol) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix25"));
        }

        if (this.nRow == gMatrixValue.nRow && this.nCol == gMatrixValue.nCol) {
            if (this.nRow == 2 && this.nCol == 2 && this.values[1][0] == 0.0) {
                gMatrix.setIdentity();
                gMatrixValue2.setIdentity();
                if (this.values[0][1] == 0.0) {
                    return 2;
                }

                double[] adouble = new double[1];
                double[] adouble1 = new double[1];
                double[] adouble2 = new double[1];
                double[] adouble3 = new double[1];
                double[] adouble4 = new double[] {this.values[0][0], this.values[1][1]};
                compute_2X2(
                        this.values[0][0],
                        this.values[0][1],
                        this.values[1][1],
                        adouble4,
                        adouble,
                        adouble2,
                        adouble1,
                        adouble3,
                        0);
                update_u(0, gMatrix, adouble2, adouble);
                update_v(0, gMatrixValue2, adouble3, adouble1);
                return 2;
            } else {
                return computeSVD(
                        this,
                        gMatrix,
                        gMatrixValue,
                        gMatrixValue2);
            }
        } else {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix26"));
        }
    }

    public final void set(double[] p171_0) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        int i = 0;
        java.lang.String[] astring = astring1;

        while (i < this.nRow) {
            int j = 0;

            while (true) {
                if (j < this.nCol) {
                    this.values[i][j] = p171_0[this.nCol * i + j];
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public final void add(
            GMatrix gMatrix) {
        String[] astring = Tuple3f.hF();
        if (this.nRow != gMatrix.nRow) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix9"));
        }

        if (this.nCol != gMatrix.nCol) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix28"));
        }

        int i = 0;

        while (i < this.nRow) {
            int j = 0;

            while (true) {
                if (j < this.nCol) {
                    this.values[i][j] = this.values[i][j] - gMatrix.values[i][j];
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public final void sub(
            GMatrix gMatrix,
            GMatrix gMatrixValue) {
        String[] astring = Tuple3f.hF();
        if (gMatrixValue.nRow != gMatrix.nRow) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix6"));
        }

        if (gMatrixValue.nCol != gMatrix.nCol) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix7"));
        }

        if (this.nCol == gMatrix.nCol && this.nRow == gMatrix.nRow) {
            int i = 0;

            while (i < this.nRow) {
                int j = 0;

                while (true) {
                    if (j < this.nCol) {
                        this.values[i][j] =
                                gMatrix.values[i][j]
                                        + gMatrixValue.values[i][j];
                        j++;
                        if (astring == null) {
                            continue;
                        }
                    }

                    i++;
                    if (astring != null) {
                        return;
                    }
                    break;
                }
            }
        } else {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix8"));
        }
    }

    static {
        remix.compat.vecmath.GMatrix.packedTextFragments = new String[13];
        remix.compat.vecmath.GMatrix.packedTextFragments[0] = new String(" ");
        remix.compat.vecmath.GMatrix.packedTextFragments[1] = new String("\n");
        remix.compat.vecmath.GMatrix.packedTextFragments[2] = new String(" ");
        remix.compat.vecmath.GMatrix.packedTextFragments[3] = new String("\n");
        remix.compat.vecmath.GMatrix.packedTextFragments[4] = new String(" ");
        remix.compat.vecmath.GMatrix.packedTextFragments[5] = new String(" ");
        remix.compat.vecmath.GMatrix.packedTextFragments[6] = new String(" ");
        remix.compat.vecmath.GMatrix.packedTextFragments[7] = new String(" ");
        remix.compat.vecmath.GMatrix.packedTextFragments[8] = new String("\n");
        remix.compat.vecmath.GMatrix.packedTextFragments[9] = new String(" ");
        remix.compat.vecmath.GMatrix.packedTextFragments[10] = new String(" ");
    }

    public final void l() {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        int i = 0;
        java.lang.String[] astring = astring1;

        label39:
        while (i < this.nRow) {
            int j = 0;

            while (true) {
                if (j < this.nCol) {
                    this.values[i][j] = -this.values[i][j];
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    break label39;
                }
                break;
            }
        }

        int k;
        {
            if (this.nRow < this.nCol) {
                k = this.nRow;
                if (!(astring == null)) {

                    k = this.nCol;
                }
            } else {

                k = this.nCol;
            }
        }

        i = 0;

        while (i < k) {
            this.values[i][i]++;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void get(
            Matrix4f matrix4f) {
        String[] astring = Tuple3f.hF();
        if (this.nRow < 4 || this.nCol < 4) {
            this.values = new double[4][4];
            this.nRow = 4;
            this.nCol = 4;
        }

        this.values[0][0] = matrix4f.m00;
        this.values[0][1] = matrix4f.m01;
        this.values[0][2] = matrix4f.m02;
        this.values[0][3] = matrix4f.m03;
        this.values[1][0] = matrix4f.m10;
        this.values[1][1] = matrix4f.m11;
        this.values[1][2] = matrix4f.m12;
        this.values[1][3] = matrix4f.m13;
        this.values[2][0] = matrix4f.m20;
        this.values[2][1] = matrix4f.m21;
        this.values[2][2] = matrix4f.m22;
        this.values[2][3] = matrix4f.m23;
        this.values[3][0] = matrix4f.m30;
        this.values[3][1] = matrix4f.m31;
        this.values[3][2] = matrix4f.m32;
        this.values[3][3] = matrix4f.m33;
        int i = 4;

        while (i < this.nRow) {
            int j = 4;

            while (true) {
                if (j < this.nCol) {
                    this.values[i][j] = 0.0;
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public final void transpose(
            GMatrix gMatrix) {
        String[] astring = Tuple3f.hF();
        if (this.nRow != gMatrix.nRow) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix4"));
        }

        if (this.nCol != gMatrix.nCol) {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix5"));
        }

        int i = 0;

        while (i < this.nRow) {
            int j = 0;

            while (true) {
                if (j < this.nCol) {
                    this.values[i][j] = this.values[i][j] + gMatrix.values[i][j];
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    static boolean luDecomposition(int i, double[] adouble, int[] aint, int[] aint1) {
        double[] adouble1 = new double[i];
        int l = 0;
        String[] astring1 = Tuple3f.hF();
        int i1 = 0;
        aint1[0] = 1;
        String[] astring = astring1;
        int j = i;

        while (j-- != 0) {
            double d0 = 0.0;
            int k = i;

            while (k-- != 0) {
                double d1 = adouble[l++];
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

            adouble1[i1++] = 1.0 / d0;
            if (astring != null) {
                break;
            }
        }

        byte b0 = 0;
        int k2 = 0;

        while (k2 < i) {
            j = 0;

            label96:
            while (j < k2) {
                int l1 = b0 + i * j + k2;
                double d2 = adouble[l1];
                int k1 = j;
                int i2 = b0 + i * j;
                int j2 = b0 + k2;

                while (true) {
                    if (k1-- != 0) {
                        d2 -= adouble[i2] * adouble[j2];
                        i2++;
                        j2 += i;
                        if (astring == null) {
                            continue;
                        }
                    }

                    adouble[l1] = d2;
                    j++;
                    if (astring != null) {
                        break label96;
                    }
                    break;
                }
            }

            double d3 = 0.0;
            int j1 = -1;
            j = k2;

            while (j < i) {
                int j3 = b0 + i * j + k2;
                double d7 = adouble[j3];
                int l2 = k2;
                int l3 = b0 + i * j;
                int j4 = b0 + k2;

                while (l2-- != 0) {
                    d7 -= adouble[l3] * adouble[j4];
                    l3++;
                    j4 += i;
                    if (astring != null) {
                        break;
                    }
                }

                adouble[j3] = d7;
                double d4;
                if ((d4 = adouble1[j] * Math.abs(d7)) >= d3) {
                    d3 = d4;
                    j1 = j;
                }

                j++;
                if (astring != null) {
                    break;
                }
            }

            if (j1 < 0) {
                throw new RuntimeException(VecMathI18N.getString("GMatrix24"));
            }

            if (k2 != j1) {
                int i3 = i;
                int i4 = b0 + i * j1;
                int k4 = b0 + i * k2;

                while (i3-- != 0) {
                    double d5 = adouble[i4];
                    adouble[i4++] = adouble[k4];
                    adouble[k4++] = d5;
                    if (astring != null) {
                        break;
                    }
                }

                adouble1[j1] = adouble1[k2];
                aint1[0] = -aint1[0];
            }

            aint[k2] = j1;
            if (adouble[b0 + i * k2 + k2] == 0.0) {
                return false;
            }

            if (k2 != i - 1) {
                double d6 = 1.0 / adouble[b0 + i * k2 + k2];
                int k3 = b0 + i * (k2 + 1) + k2;
                j = i - 1 - k2;

                while (j-- != 0) {
                    adouble[k3] *= d6;
                    k3 += i;
                    if (astring != null) {
                        break;
                    }
                }
            }

            k2++;
            if (astring != null) {
                break;
            }
        }

        return true;
    }

    public final void multiplyInPlace(
            GMatrix gMatrix) {
        String[] astring = Tuple3f.hF();
        if (this.nCol == gMatrix.nRow && this.nCol == gMatrix.nCol) {
            double[][] adouble = new double[this.nRow][this.nCol];
            int i = 0;

            label35:
            while (i < this.nRow) {
                int j = 0;

                while (true) {
                    if (j < this.nCol) {
                        adouble[i][j] = 0.0;
                        int k = 0;

                        while (k < this.nCol) {
                            adouble[i][j] =
                                    adouble[i][j]
                                            + this.values[i][k] * gMatrix.values[k][j];
                            k++;
                            if (astring != null) {
                                break;
                            }
                        }

                        j++;
                        if (astring == null) {
                            continue;
                        }
                    }

                    i++;
                    if (astring != null) {
                        break label35;
                    }
                    break;
                }
            }

            this.values = adouble;
        } else {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix0"));
        }
    }

    public final void setIdentityInternal() {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        int i = 0;
        java.lang.String[] astring = astring1;

        while (i < this.nRow) {
            int j = 0;

            while (true) {
                if (j < this.nCol) {
                    this.values[i][j] = -this.values[i][j];
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public final void mulTransposeLeft(
            GMatrix gMatrix,
            GMatrix gMatrixValue) {
        String[] astring = Tuple3f.hF();
        if (gMatrix.nRow == gMatrixValue.nRow
                && this.nCol == gMatrixValue.nCol
                && this.nRow == gMatrix.nCol) {
            if (gMatrix == this || gMatrixValue == this) {
                double[][] adouble = new double[this.nRow][this.nCol];
                int i = 0;

                while (i < this.nRow) {
                    int j = 0;

                    while (j < this.nCol) {
                        adouble[i][j] = 0.0;
                        int k = 0;

                        while (k < gMatrix.nRow) {
                            adouble[i][j] =
                                    adouble[i][j]
                                            + gMatrix.values[k][i]
                                                    * gMatrixValue.values[k][j];
                            k++;
                            if (astring != null) {
                                break;
                            }
                        }

                        j++;
                        if (astring != null) {
                            break;
                        }
                    }

                    i++;
                    if (astring != null) {
                        break;
                    }
                }

                this.values = adouble;
                if (astring == null) {
                    return;
                }
            }

            int l = 0;

            while (l < this.nRow) {
                int i1 = 0;

                while (i1 < this.nCol) {
                    this.values[l][i1] = 0.0;
                    int j1 = 0;

                    while (j1 < gMatrix.nRow) {
                        this.values[l][i1] =
                                this.values[l][i1]
                                        + gMatrix.values[j1][l]
                                                * gMatrixValue.values[j1][i1];
                        j1++;
                        if (astring != null) {
                            break;
                        }
                    }

                    i1++;
                    if (astring != null) {
                        break;
                    }
                }

                l++;
                if (astring != null) {
                    break;
                }
            }
        } else {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix16"));
        }
    }

    public final void multiplyTransposedBoth(
            GMatrix gMatrix,
            GMatrix gMatrixValue) {
        String[] astring = Tuple3f.hF();
        if (gMatrix.nRow == gMatrixValue.nCol
                && this.nRow == gMatrix.nCol
                && this.nCol == gMatrixValue.nRow) {
            if (gMatrix == this || gMatrixValue == this) {
                double[][] adouble = new double[this.nRow][this.nCol];
                int i = 0;

                while (i < this.nRow) {
                    int j = 0;

                    while (j < this.nCol) {
                        adouble[i][j] = 0.0;
                        int k = 0;

                        while (k < gMatrix.nRow) {
                            adouble[i][j] =
                                    adouble[i][j]
                                            + gMatrix.values[k][i]
                                                    * gMatrixValue.values[j][k];
                            k++;
                            if (astring != null) {
                                break;
                            }
                        }

                        j++;
                        if (astring != null) {
                            break;
                        }
                    }

                    i++;
                    if (astring != null) {
                        break;
                    }
                }

                this.values = adouble;
                if (astring == null) {
                    return;
                }
            }

            int l = 0;

            while (l < this.nRow) {
                int i1 = 0;

                while (i1 < this.nCol) {
                    this.values[l][i1] = 0.0;
                    int j1 = 0;

                    while (j1 < gMatrix.nRow) {
                        this.values[l][i1] =
                                this.values[l][i1]
                                        + gMatrix.values[j1][l]
                                                * gMatrixValue.values[i1][j1];
                        j1++;
                        if (astring != null) {
                            break;
                        }
                    }

                    i1++;
                    if (astring != null) {
                        break;
                    }
                }

                l++;
                if (astring != null) {
                    break;
                }
            }
        } else {
            throw new MismatchedSizeException(VecMathI18N.getString("GMatrix14"));
        }
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public GMatrix(GMatrix gMatrix) {
        super();
        this.nRow = gMatrix.nRow;
        String[] astring1 = Tuple3f.hF();
        this.nCol = gMatrix.nCol;
        this.values = new double[this.nRow][this.nCol];
        int i = 0;
        String[] astring = astring1;

        while (i < this.nRow) {
            int j = 0;

            while (true) {
                if (j < this.nCol) {
                    this.values[i][j] = gMatrix.values[i][j];
                    j++;
                    if (astring == null) {
                        continue;
                    }
                }

                i++;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }
}
