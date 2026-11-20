package remix.compat.vecmath;

import java.io.Serializable;

public abstract class Tuple3b implements Serializable, Cloneable {
    public byte y;
    private static String[] packedTextFragments;
    public byte z;
    public byte x;
    static final long serialVersionUID = -483782685323607044L;

    public final void set(byte[] abyte) {
        this.x = abyte[0];
        this.y = abyte[1];
        this.z = abyte[2];
    }

    public Tuple3b(byte b0, byte b1, byte b2) {
        this.x = b0;
        this.y = b1;
        this.z = b2;
    }

    public final void method_546e8bb7cd032136dd2d(
            Tuple3b tuple3b) {
        tuple3b.x = this.x;
        tuple3b.y = this.y;
        tuple3b.z = this.z;
    }

    public final void setY(byte b0) {
        this.y = b0;
    }

    static {
        remix.compat.vecmath.Tuple3b.packedTextFragments = new String[3];
        remix.compat.vecmath.Tuple3b.packedTextFragments[0] = new String("(");
        remix.compat.vecmath.Tuple3b.packedTextFragments[1] = new String(")");
    }

    public boolean equals(
            Tuple3b tuple3b) {
        try {
            return this.x == tuple3b.x
                    && this.y == tuple3b.y
                    && this.z == tuple3b.z;
        } catch (NullPointerException nullpointerexception) {
            return false;
        }
    }

    public final byte getX() {
        return this.x;
    }

    public final void method_cb3ac53bf295a7bb7702(
            Tuple3b tuple3b) {
        this.x = tuple3b.x;
        this.y = tuple3b.y;
        this.z = tuple3b.z;
    }

    public final void setX(byte b0) {
        this.x = b0;
    }

    @Override
    public int hashCode() {
        return (this.x & 0xFF) << 0 | (this.y & 0xFF) << 8 | (this.z & 0xFF) << 16;
    }

    public final void setZ(byte b0) {
        this.z = b0;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException clonenotsupportedexception) {
            throw new InternalError();
        }
    }

    public Tuple3b() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Tuple3b(byte[] abyte) {
        this.x = abyte[0];
        this.y = abyte[1];
        this.z = abyte[2];
    }

    public final byte getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object object) {
        try {
            Tuple3b tuple3b =
                    (Tuple3b) object;
            return this.x == tuple3b.x
                    && this.y == tuple3b.y
                    && this.z == tuple3b.z;
        } catch (NullPointerException nullpointerexception) {
            return false;
        } catch (ClassCastException classcastexception) {
            return false;
        }
    }

    public final byte getZ() {
        return this.z;
    }

    @Override
    public String toString() {
        return packedTextFragments[0]
                + (this.x & 0xFF)
                + ", "
                + (this.y & 0xFF)
                + ", "
                + (this.z & 0xFF)
                + packedTextFragments[1];
    }

    public final void get(byte[] abyte) {
        abyte[0] = this.x;
        abyte[1] = this.y;
        abyte[2] = this.z;
    }

    private static NullPointerException method_339a46a0855ea6bd2c63(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Tuple3b(Tuple3b tuple3b) {
        super();
        this.x = tuple3b.x;
        this.y = tuple3b.y;
        this.z = tuple3b.z;
    }
}
