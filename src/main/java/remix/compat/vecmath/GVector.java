package remix.compat.vecmath;

import java.io.Serializable;

public class GVector implements Serializable, Cloneable {
    static final long serialVersionUID = 1398850036893875112L;
    private static String[] packedTextFragments;
    private int length;

    double[] values;

    public final void set(
            Tuple4d tuple4d) {
        String[] astring = Tuple3f.hF();
        if (this.length < 4) {
            this.length = 4;
            this.values = new double[4];
        }

        this.values[0] = tuple4d.x;
        this.values[1] = tuple4d.y;
        this.values[2] = tuple4d.z;
        this.values[3] = tuple4d.w;
        int i = 4;

        while (i < this.length) {
            this.values[i] = 0.0;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public GVector(Tuple3d p171_0) {
        this.values = new double[3];
        this.values[0] = p171_0.x;
        this.values[1] = p171_0.y;
        this.values[2] = p171_0.z;
        this.length = 3;
    }

    public GVector(Tuple3f p171_0) {
        this.values = new double[3];
        this.values[0] = p171_0.x;
        this.values[1] = p171_0.y;
        this.values[2] = p171_0.z;
        this.length = 3;
    }

    public final void l() {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        int i = this.length - 1;
        java.lang.String[] astring = astring1;

        while (i >= 0) {
            this.values[i] = this.values[i] * -1.0;
            i--;
            if (astring != null) {
                break;
            }
        }
    }

    public boolean epsilonEquals(
            GVector gVector, double d0) {
        String[] astring = Tuple3f.hF();
        if (this.length != gVector.length) {
            return false;
        }

        int i = 0;

        while (i < this.length) {
            double d1 = this.values[i] - gVector.values[i];
            if ((d1 < 0.0 ? -d1 : d1) > d0) {
                return false;
            }

            i++;
            if (astring != null) {
                break;
            }
        }

        return true;
    }

    public final void scaleAdd(
            double d0,
            GVector gVector,
            GVector gVectorValue) {
        String[] astring = Tuple3f.hF();
        if (gVectorValue.length != gVector.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector2"));
        }

        if (this.length != gVector.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector3"));
        }

        int i = 0;

        while (i < this.length) {
            this.values[i] = gVector.values[i] * d0 + gVectorValue.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void set(
            GVector gVector) {
        String[] astring1 = Tuple3f.hF();
        double d0 = 0.0;
        String[] astring = astring1;
        if (this.length != gVector.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector0"));
        }

        int i = 0;

        while (i < this.length) {
            d0 += gVector.values[i] * gVector.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }

        double d1 = 1.0 / Math.sqrt(d0);
        i = 0;

        while (i < this.length) {
            this.values[i] = gVector.values[i] * d1;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void setElement(int p171_0, double p171_1) {
        this.values[p171_0] = p171_1;
    }

    public final int getSize() {
        return this.values.length;
    }

    public final void interpolate(
            GVector gVector, double d0) {
        String[] astring = Tuple3f.hF();
        if (gVector.length != this.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector22"));
        }

        int i = 0;

        while (i < this.length) {
            this.values[i] = (1.0 - d0) * this.values[i] + d0 * gVector.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    @Override
    public boolean equals(Object p171_0) {
        java.lang.String[] astring = remix.compat.vecmath.Tuple3f.hF();

        try {
            remix.compat.vecmath.GVector gVector =
                    (remix.compat.vecmath.GVector) p171_0;
            if (this.length != gVector.length) {
                return false;
            }

            int i = 0;

            while (i < this.length) {
                if (this.values[i] != gVector.values[i]) {
                    return false;
                }

                i++;
                if (astring != null) {
                    break;
                }
            }

            return true;
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final double cl() {
        String[] astring1 = Tuple3f.hF();
        double d0 = 0.0;
        String[] astring = astring1;
        int i = 0;

        while (i < this.length) {
            d0 += this.values[i] * this.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }

        return d0;
    }

    public final void add(
            GVector gVector,
            GVector gVectorValue) {
        String[] astring = Tuple3f.hF();
        if (gVector.length != gVectorValue.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector5"));
        }

        if (this.length != gVector.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector6"));
        }

        int i = 0;

        while (i < this.length) {
            this.values[i] = gVector.values[i] + gVectorValue.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public GVector(Tuple4f p171_0) {
        this.values = new double[4];
        this.values[0] = p171_0.x;
        this.values[1] = p171_0.y;
        this.values[2] = p171_0.z;
        this.values[3] = p171_0.w;
        this.length = 4;
    }

    @Override
    public Object clone() {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        remix.compat.vecmath.GVector gVector = null;
        java.lang.String[] astring = astring1;

        try {
            gVector = (remix.compat.vecmath.GVector) super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }

        gVector.values = new double[this.length];
        int i = 0;

        while (i < this.length) {
            gVector.values[i] = this.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }

        return gVector;
    }

    public boolean equals(
            GVector gVector) {
        String[] astring = Tuple3f.hF();

        try {
            if (this.length != gVector.length) {
                return false;
            }

            int i = 0;

            while (i < this.length) {
                if (this.values[i] != gVector.values[i]) {
                    return false;
                }

                i++;
                if (astring != null) {
                    break;
                }
            }

            return true;
        } catch (NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final double norm() {
        String[] astring1 = Tuple3f.hF();
        double d0 = 0.0;
        String[] astring = astring1;
        int i = 0;

        while (i < this.length) {
            d0 += this.values[i] * this.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }

        return Math.sqrt(d0);
    }

    public GVector(Tuple4d p171_0) {
        this.values = new double[4];
        this.values[0] = p171_0.x;
        this.values[1] = p171_0.y;
        this.values[2] = p171_0.z;
        this.values[3] = p171_0.w;
        this.length = 4;
    }

    public final void a(int p171_0) {
        java.lang.String[] astring;
        double[] adouble;
        int k;
        {
            java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
            adouble = new double[p171_0];
            astring = astring1;
            if (this.length < p171_0) {
                k = this.length;
                if (!(astring == null)) {

                    k = p171_0;
                }
            } else {

                k = p171_0;
            }
        }

        int j = 0;

        while (j < k) {
            adouble[j] = this.values[j];
            j++;
            if (astring != null) {
                break;
            }
        }

        this.length = p171_0;
        this.values = adouble;
    }

    public final void set(
            Tuple4f tuple4f) {
        String[] astring = Tuple3f.hF();
        if (this.length < 4) {
            this.length = 4;
            this.values = new double[4];
        }

        this.values[0] = tuple4f.x;
        this.values[1] = tuple4f.y;
        this.values[2] = tuple4f.z;
        this.values[3] = tuple4f.w;
        int i = 4;

        while (i < this.length) {
            this.values[i] = 0.0;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    static {
        remix.compat.vecmath.GVector.packedTextFragments = new String[3];
        remix.compat.vecmath.GVector.packedTextFragments[0] = new String(" ");
    }

    public final void interpolate(
            GVector gVector,
            GVector gVectorValue,
            double d0) {
        String[] astring = Tuple3f.hF();
        if (gVectorValue.length != gVector.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector20"));
        }

        if (this.length != gVector.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector21"));
        }

        int i = 0;

        while (i < this.length) {
            this.values[i] =
                    (1.0 - d0) * gVector.values[i]
                            + d0 * gVectorValue.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void SVDBackSolve(
            GMatrix gMatrix,
            GMatrix gMatrixValue,
            GMatrix gMatrixValue2,
            GVector gVector) {
        if (gMatrix.nRow != gVector.getSize()
                || gMatrix.nRow != gMatrix.nCol
                || gMatrix.nRow != gMatrixValue.nRow) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector15"));
        }

        if (gMatrixValue.nCol == this.values.length
                && gMatrixValue.nCol == gMatrixValue2.nCol
                && gMatrixValue.nCol == gMatrixValue2.nRow) {
            GMatrix gMatrixValue3 =
                    new GMatrix(
                            gMatrix.nRow, gMatrixValue.nCol);
            gMatrixValue3.mul(
                    gMatrix, gMatrixValue2);
            gMatrixValue3.mulTransposeRight(
                    gMatrix, gMatrixValue);
            gMatrixValue3.invert();
            this.mul(
                    gMatrixValue3, gVector);
        } else {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector23"));
        }
    }

    public final void scale(double p171_0) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        int i = 0;
        java.lang.String[] astring = astring1;

        while (i < this.length) {
            this.values[i] = this.values[i] * p171_0;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final double getElement(int p171_0) {
        return this.values[p171_0];
    }

    public final void add(
            GVector gVector) {
        String[] astring = Tuple3f.hF();
        if (this.length != gVector.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector4"));
        }

        int i = 0;

        while (i < this.length) {
            this.values[i] = this.values[i] + gVector.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void cj() {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        double d0 = 0.0;
        java.lang.String[] astring = astring1;
        int i = 0;

        while (i < this.length) {
            d0 += this.values[i] * this.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }

        double d1 = 1.0 / java.lang.Math.sqrt(d0);
        i = 0;

        while (i < this.length) {
            this.values[i] = this.values[i] * d1;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    @Override
    public int hashCode() {

        long i = 1L;
        String[] astring1 = Tuple3f.hF();
        int j = 0;
        String[] astring = astring1;

        while (j < this.length) {
            i = VecMathUtil.hashDoubleToLongBits(i, this.values[j]);
            j++;
            if (astring != null) {
                break;
            }
        }

        return VecMathUtil.hashFinish(i);
    }

    public final void cQ() {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        int i = 0;
        java.lang.String[] astring = astring1;

        while (i < this.length) {
            this.values[i] = 0.0;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void scale(
            double d0, GVector gVector) {
        String[] astring = Tuple3f.hF();
        if (this.length != gVector.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector1"));
        }

        int i = 0;

        while (i < this.length) {
            this.values[i] = gVector.values[i] * d0;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    /** @deprecated */
    public final void interpolate(
            GVector p171_0, GVector p171_1, float p171_2) {
        this.interpolate(p171_0, p171_1, p171_2);
    }

    /** @deprecated */
    public final void interpolate(GVector p171_0, float p171_1) {
        this.interpolate(p171_0, p171_1);
    }

    @Override
    public String toString() {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        java.lang.StringBuffer stringbuffer = new java.lang.StringBuffer(this.length * 8);
        java.lang.String[] astring = astring1;
        int i = 0;

        while (i < this.length) {
            stringbuffer
                    .append(this.values[i])
                    .append(remix.compat.vecmath.GVector.packedTextFragments[0]);
            i++;
            if (astring != null) {
                break;
            }
        }

        return stringbuffer.toString();
    }

    public final double dot(
            GVector gVector) {
        String[] astring = Tuple3f.hF();
        if (this.length != gVector.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector14"));
        }

        double d0 = 0.0;
        int i = 0;

        while (i < this.length) {
            d0 += this.values[i] * gVector.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }

        return d0;
    }

    public GVector(double[] adouble, int i) {
        String[] astring1 = Tuple3f.hF();
        this.length = i;
        this.values = new double[i];
        String[] astring = astring1;
        int j = 0;

        while (j < i) {
            this.values[j] = adouble[j];
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void sub(
            GVector gVector,
            GVector gVectorValue) {
        String[] astring = Tuple3f.hF();
        if (gVector.length != gVectorValue.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector8"));
        }

        if (this.length != gVector.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector9"));
        }

        int i = 0;

        while (i < this.length) {
            this.values[i] = gVector.values[i] - gVectorValue.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void set(
            Tuple3f tuple3f) {
        String[] astring = Tuple3f.hF();
        if (this.length < 3) {
            this.length = 3;
            this.values = new double[3];
        }

        this.values[0] = tuple3f.x;
        this.values[1] = tuple3f.y;
        this.values[2] = tuple3f.z;
        int i = 3;

        while (i < this.length) {
            this.values[i] = 0.0;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void negate(GVector p171_0) {
        java.lang.String[] astring = remix.compat.vecmath.Tuple3f.hF();
        if (this.length < p171_0.length) {
            this.length = p171_0.length;
            this.values = new double[this.length];
            int i = 0;

            do {
                if (i >= this.length) {
                    return;
                }

                this.values[i] = p171_0.values[i];
                i++;
            } while (astring == null);
        }

        int j = 0;

        while (j < p171_0.length) {
            this.values[j] = p171_0.values[j];
            j++;
            if (astring != null) {
                break;
            }
        }

        j = p171_0.length;

        while (j < this.length) {
            this.values[j] = 0.0;
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void set(
            Tuple2f tuple2f) {
        String[] astring = Tuple3f.hF();
        if (this.length < 2) {
            this.length = 2;
            this.values = new double[2];
        }

        this.values[0] = tuple2f.x;
        this.values[1] = tuple2f.y;
        int i = 2;

        while (i < this.length) {
            this.values[i] = 0.0;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public GVector(Tuple2f p171_0) {
        this.values = new double[2];
        this.values[0] = p171_0.x;
        this.values[1] = p171_0.y;
        this.length = 2;
    }

    public final void sub(
            GVector gVector) {
        String[] astring = Tuple3f.hF();
        if (this.length != gVector.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector7"));
        }

        int i = 0;

        while (i < this.length) {
            this.values[i] = this.values[i] - gVector.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final double angle(GVector p171_0) {
        return java.lang.Math.acos(
                this.dot(p171_0) / (this.norm() * p171_0.norm()));
    }

    public final void mul(
            GMatrix gMatrix,
            GVector gVector) {
        String[] astring = Tuple3f.hF();
        if (gMatrix.getNumCol() != gVector.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector10"));
        }

        if (this.length != gMatrix.getNumRow()) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector11"));
        }

        double[] adouble;
        {
            if (gVector != this) {
                adouble = gVector.values;
                if (!(astring == null)) {

                    adouble = (double[]) this.values.clone();
                }
            } else {

                adouble = (double[]) this.values.clone();
            }
        }

        int i = this.length - 1;

        while (i >= 0) {
            this.values[i] = 0.0;
            int j = gVector.length - 1;

            while (true) {
                if (j >= 0) {
                    this.values[i] = this.values[i] + gMatrix.values[i][j] * adouble[j];
                    j--;
                    if (astring == null) {
                        continue;
                    }
                }

                i--;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    private static MismatchedSizeException wrapMismatchedSizeException(
            MismatchedSizeException mismatchedSizeException) {
        return mismatchedSizeException;
    }

    public final void set(double[] p171_0) {
        java.lang.String[] astring1 = remix.compat.vecmath.Tuple3f.hF();
        int i = this.length - 1;
        java.lang.String[] astring = astring1;

        while (i >= 0) {
            this.values[i] = p171_0[i];
            i--;
            if (astring != null) {
                break;
            }
        }
    }

    public final void mul(
            GVector gVector,
            GMatrix gMatrix) {
        String[] astring = Tuple3f.hF();
        if (gMatrix.getNumRow() != gVector.length) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector12"));
        }

        if (this.length != gMatrix.getNumCol()) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector13"));
        }

        double[] adouble;
        {
            if (gVector != this) {
                adouble = gVector.values;
                if (!(astring == null)) {

                    adouble = (double[]) this.values.clone();
                }
            } else {

                adouble = (double[]) this.values.clone();
            }
        }

        int i = this.length - 1;

        while (i >= 0) {
            this.values[i] = 0.0;
            int j = gVector.length - 1;

            while (true) {
                if (j >= 0) {
                    this.values[i] = this.values[i] + gMatrix.values[j][i] * adouble[j];
                    j--;
                    if (astring == null) {
                        continue;
                    }
                }

                i--;
                if (astring != null) {
                    return;
                }
                break;
            }
        }
    }

    public GVector(double[] adouble) {
        String[] astring1 = Tuple3f.hF();
        this.length = adouble.length;
        this.values = new double[adouble.length];
        String[] astring = astring1;
        int i = 0;

        while (i < this.length) {
            this.values[i] = adouble[i];
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void set(
            Tuple3d tuple3d) {
        String[] astring = Tuple3f.hF();
        if (this.length < 3) {
            this.length = 3;
            this.values = new double[3];
        }

        this.values[0] = tuple3d.x;
        this.values[1] = tuple3d.y;
        this.values[2] = tuple3d.z;
        int i = 3;

        while (i < this.length) {
            this.values[i] = 0.0;
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public GVector(int i) {
        String[] astring1 = Tuple3f.hF();
        this.length = i;
        this.values = new double[i];
        int j = 0;
        String[] astring = astring1;

        while (j < i) {
            this.values[j] = 0.0;
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    public final void LUDBackSolve(
            GMatrix gMatrix,
            GVector gVector,
            GVector gVectorValue) {
        int i = gMatrix.nRow * gMatrix.nCol;
        String[] astring1 = Tuple3f.hF();
        double[] adouble = new double[i];
        double[] adouble1 = new double[i];
        int[] aint = new int[gVector.getSize()];
        String[] astring = astring1;
        if (gMatrix.nRow != gVector.getSize()) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector16"));
        }

        if (gMatrix.nRow != gVectorValue.getSize()) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector24"));
        }

        if (gMatrix.nRow != gMatrix.nCol) {
            throw new MismatchedSizeException(VecMathI18N.getString("GVector25"));
        }

        int j = 0;

        label73:
        while (j < gMatrix.nRow) {
            int k = 0;

            while (true) {
                if (k < gMatrix.nCol) {
                    adouble[j * gMatrix.nCol + k] =
                            gMatrix.values[j][k];
                    k++;
                    if (astring == null) {
                        continue;
                    }
                }

                j++;
                if (astring != null) {
                    break label73;
                }
                break;
            }
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

        while (j < gMatrix.nRow) {
            adouble1[j * gMatrix.nCol] = gVector.values[j];
            j++;
            if (astring != null) {
                break;
            }
        }

        j = 0;

        while (j < gMatrix.nCol) {
            aint[j] = (int) gVectorValue.values[j];
            j++;
            if (astring != null) {
                break;
            }
        }

        GMatrix.luDecomposition(
                gMatrix.nRow, adouble, aint, adouble1);
        j = 0;

        while (j < gMatrix.nRow) {
            this.values[j] = adouble1[j * gMatrix.nCol];
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public GVector(GVector gVector) {
        super();
        this.values = new double[gVector.length];
        String[] astring1 = Tuple3f.hF();
        this.length = gVector.length;
        String[] astring = astring1;
        int i = 0;

        while (i < this.length) {
            this.values[i] = gVector.values[i];
            i++;
            if (astring != null) {
                break;
            }
        }
    }
}
