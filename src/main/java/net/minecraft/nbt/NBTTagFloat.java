package net.minecraft.nbt;

import net.minecraft.util.MathHelper;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTBaseNBTPrimitive {
    private float data;
    private static String[] packedTextFragments;

    public byte getByte() {
        return (byte) (MathHelper.floor(this.data) & 0xFF);
    }

    public short getShort() {
        return (short) (MathHelper.floor(this.data) & 65535);
    }

    public float getFloat() {
        return this.data;
    }

    public int getInt() {
        return MathHelper.floor(this.data);
    }

    public String toString() {
        return packedTextFragments[0] + this.data + packedTextFragments[1];
    }

    public byte getId() {
        return 5;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.nbt.NBTTagFloat.packedTextFragments[0] = new String("");
        net.minecraft.nbt.NBTTagFloat.packedTextFragments[1] = new String("f");
    }

    // endregion

    public long getLong() {
        return (long) this.data;
    }

    // region String decoding support

    // endregion

    public NBTBase copy() {
        return new NBTTagFloat(this.data);
    }

    void write(DataOutput dataoutput) throws IOException {
        dataoutput.writeFloat(this.data);
    }

    void read(DataInput datainput, int j, NBTSizeTracker nbtSizeTracker) throws IOException {

        nbtSizeTracker.read(32L);
        this.data = datainput.readFloat();
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagFloat nbtTagFloat = (NBTTagFloat) object;
            return this.data == nbtTagFloat.data;
        } else {
            return false;
        }
    }

    public NBTTagFloat(float dataValue) {
        this.data = dataValue;
    }

    public double getDouble() {
        return this.data;
    }

    NBTTagFloat() {}

    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }
}
