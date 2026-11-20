package remix.compat.vecmath;

import java.io.Serializable;

public abstract class Tuple4b implements Serializable, Cloneable {
    public byte z;
    static final long serialVersionUID = -8226727741811898211L;
    private static String[] packedTextFragments;

    public byte x;
    public byte y;
    public byte w;

    public final void method_fc789122969d6e572afc(
            Tuple4b tuple4b) {
        this.x = tuple4b.x;
        this.y = tuple4b.y;
        this.z = tuple4b.z;
        this.w = tuple4b.w;
    }

    public final byte getZ() {
        return this.z;
    }

    // region String decoding support

    // endregion

    public final void method_920ad5a8b8ff55a86e5a(
            Tuple4b tuple4b) {
        tuple4b.x = this.x;
        tuple4b.y = this.y;
        tuple4b.z = this.z;
        tuple4b.w = this.w;
    }

    public Tuple4b(byte[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
        this.w = p171_0[3];
    }

    public final byte getY() {
        return this.y;
    }

    public final void get(byte[] p171_0) {
        p171_0[0] = this.x;
        p171_0[1] = this.y;
        p171_0[2] = this.z;
        p171_0[3] = this.w;
    }

    public Tuple4b(byte p171_0, byte p171_1, byte p171_2, byte p171_3) {
        this.x = p171_0;
        this.y = p171_1;
        this.z = p171_2;
        this.w = p171_3;
    }

    public final byte getX() {
        return this.x;
    }

    public final void setY(byte b0) {
        this.y = b0;
    }

    @Override
    public int hashCode() {
        return (this.x & 0xFF) << 0
                | (this.y & 0xFF) << 8
                | (this.z & 0xFF) << 16
                | (this.w & 0xFF) << 24;
    }

    static {
        remix.compat.vecmath.Tuple4b.packedTextFragments = new String[3];
        remix.compat.vecmath.Tuple4b.packedTextFragments[0] = new String("(");
        remix.compat.vecmath.Tuple4b.packedTextFragments[1] = new String(")");
    }

    public final void setX(byte b0) {
        this.x = b0;
    }

    public final void set(byte[] p171_0) {
        this.x = p171_0[0];
        this.y = p171_0[1];
        this.z = p171_0[2];
        this.w = p171_0[3];
    }

    public Tuple4b() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.w = 0;
    }

    @Override
    public String toString() {
        return packedTextFragments[0]
                + (this.x & 0xFF)
                + ", "
                + (this.y & 0xFF)
                + ", "
                + (this.z & 0xFF)
                + ", "
                + (this.w & 0xFF)
                + packedTextFragments[1];
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
            throw new java.lang.InternalError();
        }
    }

    @Override
    public boolean equals(Object p171_0) {
        try {
            remix.compat.vecmath.Tuple4b tuple4b =
                    (remix.compat.vecmath.Tuple4b) p171_0;
            return (this.x == tuple4b.x
                    && this.y == tuple4b.y
                    && this.z == tuple4b.z
                    && this.w == tuple4b.w);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        } catch (java.lang.ClassCastException classcastexception) {
            return false;
        }
    }

    public boolean equals(Tuple4b p171_0) {
        try {
            return (this.x == p171_0.x
                    && this.y == p171_0.y
                    && this.z == p171_0.z
                    && this.w == p171_0.w);
        } catch (java.lang.NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final void setZ(byte b0) {
        this.z = b0;
    }

    private static NullPointerException method_339a46a0855ea6bd2c63(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    public final void setW(byte b0) {
        this.w = b0;
    }

    public final byte getW() {
        return this.w;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Tuple4b(Tuple4b tuple4b) {
        super();
        this.x = tuple4b.x;
        this.y = tuple4b.y;
        this.z = tuple4b.z;
        this.w = tuple4b.w;
    }
}
