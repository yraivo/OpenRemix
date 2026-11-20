package net.minecraft.nbt;

import net.minecraft.util.MathHelper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBaseNBTPrimitive {
    private double data;
    private static String[] packedTextFragments;

    public NBTTagDouble(double dataValue) {
        this.data = dataValue;
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagDouble nbtTagDouble = (NBTTagDouble) object;
            return this.data == nbtTagDouble.data;
        } else {
            return false;
        }
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    public long getLong() {
        return (long) Math.floor(this.data);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.nbt.NBTTagDouble.packedTextFragments[0] = new String("");
        net.minecraft.nbt.NBTTagDouble.packedTextFragments[1] = new String("d");
    }

    // endregion

    public byte getByte() {
        return (byte) (MathHelper.floor(this.data) & 0xFF);
    }

    public short getShort() {
        return (short) (MathHelper.floor(this.data) & 65535);
    }

    public NBTBase copy() {
        return new NBTTagDouble(this.data);
    }

    // region String decoding support

    // endregion

    void read(DataInput datainput, int j, NBTSizeTracker nbtSizeTracker) throws IOException {

        nbtSizeTracker.read(64L);
        this.data = datainput.readDouble();
    }

    public byte getId() {
        return 6;
    }

    public String toString() {
        return packedTextFragments[0] + this.data + packedTextFragments[1];
    }

    NBTTagDouble() {}

    public double getDouble() {
        return this.data;
    }

    void write(DataOutput dataoutput) throws IOException {
        dataoutput.writeDouble(this.data);
    }

    public float getFloat() {
        return (float) this.data;
    }

    public int hashCode() {
        long i = Double.doubleToLongBits(this.data);
        return super.hashCode() ^ (int) (i ^ i >>> 32);
    }

    public int getInt() {
        return MathHelper.floor(this.data);
    }
}
