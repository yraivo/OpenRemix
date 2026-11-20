package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBaseNBTPrimitive {
    private static String[] packedTextFragments;
    private long data;

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.nbt.NBTTagLong.packedTextFragments[0] = new String("");
        net.minecraft.nbt.NBTTagLong.packedTextFragments[1] = new String("L");
    }

    // endregion

    public NBTTagLong(long awHValue) {
        this.data = awHValue;
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    public byte getByte() {

        return (byte) (this.data & 255L);
    }

    NBTTagLong() {}

    public long getLong() {
        return this.data;
    }

    public String toString() {
        return packedTextFragments[0] + this.data + packedTextFragments[1];
    }

    void read(DataInput datainput, int j, NBTSizeTracker nbtSizeTracker) throws IOException {

        nbtSizeTracker.read(64L);
        this.data = datainput.readLong();
    }

    public short getShort() {

        return (short) (this.data & 65535L);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagLong nbtTagLong = (NBTTagLong) object;
            return this.data == nbtTagLong.data;
        } else {
            return false;
        }
    }

    // region String decoding support

    // endregion

    void write(DataOutput dataoutput) throws IOException {
        dataoutput.writeLong(this.data);
    }

    // region String decoding support

    // endregion

    public float getFloat() {
        return (float) this.data;
    }

    public int getInt() {

        return (int) (this.data & -1L);
    }

    public double getDouble() {
        return this.data;
    }

    public NBTBase copy() {
        return new NBTTagLong(this.data);
    }

    public byte getId() {
        return 4;
    }

    public int hashCode() {
        return super.hashCode() ^ (int) (this.data ^ this.data >>> 32);
    }
}
