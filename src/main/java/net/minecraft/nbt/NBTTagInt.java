package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBaseNBTPrimitive {
    private static String[] packedTextFragments;
    private int data;

    NBTTagInt() {}

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagInt nbtTagInt = (NBTTagInt) object;
            return this.data == nbtTagInt.data;
        } else {
            return false;
        }
    }

    public double getDouble() {
        return this.data;
    }

    public long getLong() {
        return this.data;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public byte getId() {
        return 3;
    }

    public int hashCode() {
        return super.hashCode() ^ this.data;
    }

    public float getFloat() {
        return this.data;
    }

    public String toString() {
        return packedTextFragments[0] + this.data;
    }

    void write(DataOutput dataoutput) throws IOException {
        dataoutput.writeInt(this.data);
    }

    public NBTTagInt(int dataValue) {
        this.data = dataValue;
    }

    public short getShort() {
        return (short) (this.data & 65535);
    }

    // region String decoding support

    // endregion

    void read(DataInput datainput, int j, NBTSizeTracker nbtSizeTracker) throws IOException {

        nbtSizeTracker.read(32L);
        this.data = datainput.readInt();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.nbt.NBTTagInt.packedTextFragments[0] = new String("");
    }

    // endregion

    public NBTBase copy() {
        return new NBTTagInt(this.data);
    }

    public byte getByte() {
        return (byte) (this.data & 0xFF);
    }

    public int getInt() {
        return this.data;
    }
}
