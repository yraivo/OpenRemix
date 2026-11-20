package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBaseNBTPrimitive {
    private static String[] packedTextFragments;
    private byte data;

    void write(DataOutput dataoutput) throws IOException {
        dataoutput.writeByte(this.data);
    }

    public NBTBase copy() {
        return new NBTTagByte(this.data);
    }

    public double getDouble() {
        return this.data;
    }

    public NBTTagByte(byte b0) {
        this.data = b0;
    }

    public byte getId() {
        return 1;
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    public int getInt() {
        return this.data;
    }

    public String toString() {
        return packedTextFragments[0] + this.data + packedTextFragments[1];
    }

    public long getLong() {
        return this.data;
    }

    public short getShort() {
        return this.data;
    }

    public byte getByte() {
        return this.data;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.nbt.NBTTagByte.packedTextFragments[0] = new String("");
        net.minecraft.nbt.NBTTagByte.packedTextFragments[1] = new String("b");
    }

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    NBTTagByte() {}

    public float getFloat() {
        return this.data;
    }

    void read(DataInput datainput, int j, NBTSizeTracker nbtSizeTracker) throws IOException {

        nbtSizeTracker.read(8L);
        this.data = datainput.readByte();
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagByte nbtTagByte = (NBTTagByte) object;
            return this.data == nbtTagByte.data;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
}
