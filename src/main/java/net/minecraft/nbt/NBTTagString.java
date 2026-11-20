package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {

    private static String[] packedTextFragments;
    private String data;

    public NBTTagString() {
        this.data = packedTextFragments[0];
    }

    public byte getId() {
        return 8;
    }

    public boolean hasNoTags() {
        return this.data.isEmpty();
    }

    public int hashCode() {
        return super.hashCode() ^ this.data.hashCode();
    }

    public NBTTagString(String text) {
        this.data = text;
        if (text == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    public String toString() {
        return packedTextFragments[1]
                + this.data.replace(packedTextFragments[2], "\\\"")
                + packedTextFragments[3];
    }

    void write(DataOutput dataoutput) throws IOException {
        dataoutput.writeUTF(this.data);
    }

    public NBTBase copy() {
        return new NBTTagString(this.data);
    }

    public String getString() {
        return this.data;
    }

    void read(DataInput datainput, int i, NBTSizeTracker nbtSizeTracker) throws IOException {
        this.data = datainput.readUTF();
        nbtSizeTracker.read(16 * this.data.length());
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public boolean equals(Object object) {
        if (!super.equals(object)) {
            return false;
        }

        NBTTagString nbtTagString = (NBTTagString) object;
        return this.data == null && nbtTagString.data == null
                || this.data != null && this.data.equals(nbtTagString.data);
    }

    static {
        net.minecraft.nbt.NBTTagString.packedTextFragments = new String[5];
        net.minecraft.nbt.NBTTagString.packedTextFragments[0] = new String("");
        net.minecraft.nbt.NBTTagString.packedTextFragments[1] = new String("\"");
        net.minecraft.nbt.NBTTagString.packedTextFragments[2] = new String("\"");
        net.minecraft.nbt.NBTTagString.packedTextFragments[3] = new String("\"");
    }
}
