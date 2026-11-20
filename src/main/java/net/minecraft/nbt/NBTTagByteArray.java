package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase {
    private static String d;
    private static String[] packedTextFragments;
    private byte[] data;

    static {
        net.minecraft.nbt.NBTTagByteArray.packedTextFragments = new String[2];
        net.minecraft.nbt.NBTTagByteArray.packedTextFragments[0] = new String("[");
        net.minecraft.nbt.NBTTagByteArray.packedTextFragments[1] = new String(")^\u001a:$\u001f0");
        d = " bytes]";
    }

    NBTTagByteArray() {}

    public String toString() {
        return packedTextFragments[0] + this.data.length + d;
    }

    // region String decoding support

    // endregion

    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.data);
    }

    void write(DataOutput dataoutput) throws IOException {
        dataoutput.writeInt(this.data.length);
        dataoutput.write(this.data);
    }

    // region String decoding support

    // endregion

    public byte getId() {
        return 7;
    }

    // region String decoding support

    // endregion

    public NBTTagByteArray(byte[] abyte) {
        this.data = abyte;
    }

    public byte[] getByteArray() {
        return this.data;
    }

    public boolean equals(Object object) {
        return super.equals(object)
                ? Arrays.equals(this.data, ((NBTTagByteArray) object).data)
                : false;
    }

    public NBTBase copy() {
        byte[] abyte = new byte[this.data.length];
        System.arraycopy(this.data, 0, abyte, 0, this.data.length);
        return new NBTTagByteArray(abyte);
    }

    void read(DataInput datainput, int j, NBTSizeTracker nbtSizeTracker) throws IOException {
        int i = datainput.readInt();
        nbtSizeTracker.read(8 * i);
        this.data = new byte[i];
        datainput.readFully(this.data);
    }
}
