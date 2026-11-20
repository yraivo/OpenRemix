package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBaseNBTPrimitive {
    private short data;
    private static String[] packedTextFragments;

    public int hashCode() {
        return super.hashCode() ^ this.data;
    }

    public float getFloat() {
        return this.data;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.nbt.NBTTagShort.packedTextFragments[0] = new String("");
        net.minecraft.nbt.NBTTagShort.packedTextFragments[1] = new String("s");
    }

    // endregion

    public short getShort() {
        return this.data;
    }

    public byte getId() {
        return 2;
    }

    void read(DataInput datainput, int j, NBTSizeTracker nbtSizeTracker) throws IOException {

        nbtSizeTracker.read(16L);
        this.data = datainput.readShort();
    }

    // region String decoding support

    // endregion

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagShort nbtTagShort = (NBTTagShort) object;
            return this.data == nbtTagShort.data;
        } else {
            return false;
        }
    }

    public NBTBase copy() {
        return new NBTTagShort(this.data);
    }

    public NBTTagShort(short short1) {
        this.data = short1;
    }

    public long getLong() {
        return this.data;
    }

    public int getInt() {
        return this.data;
    }

    public double getDouble() {
        return this.data;
    }

    public NBTTagShort() {}

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    public byte getByte() {
        return (byte) (this.data & 0xFF);
    }

    public String toString() {
        return packedTextFragments[0] + this.data + packedTextFragments[1];
    }

    void write(DataOutput dataoutput) throws IOException {
        dataoutput.writeShort(this.data);
    }
}
