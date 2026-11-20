package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase {
    private int[] intArray;
    private static String[] packedTextFragments;

    public byte getId() {
        return 11;
    }

    NBTTagIntArray() {}

    // region String decoding support

    // endregion

    void write(DataOutput dataoutput) throws IOException {
        String[] astring1 = NBTBase.xf();
        dataoutput.writeInt(this.intArray.length);
        String[] astring = astring1;
        int i = 0;

        while (i < this.intArray.length) {
            dataoutput.writeInt(this.intArray[i]);
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public NBTBase copy() {
        int[] aint = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, aint, 0, this.intArray.length);
        return new NBTTagIntArray(aint);
    }

    public String toString() {
        String[] astring1 = NBTBase.xf();
        String text = packedTextFragments[0];
        String[] astring = astring1;

        for (int i : this.intArray) {
            text = text + i + packedTextFragments[1];
            if (astring != null) {
                break;
            }
        }

        return text + packedTextFragments[2];
    }

    public NBTTagIntArray(int[] aint) {
        this.intArray = aint;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.nbt.NBTTagIntArray.packedTextFragments[0] = new String("[");
        net.minecraft.nbt.NBTTagIntArray.packedTextFragments[1] = new String(",");
        net.minecraft.nbt.NBTTagIntArray.packedTextFragments[2] = new String("]");
    }

    // endregion

    void read(DataInput datainput, int k, NBTSizeTracker nbtSizeTracker) throws IOException {
        String[] astring1 = NBTBase.xf();
        int i = datainput.readInt();
        String[] astring = astring1;
        nbtSizeTracker.read(32 * i);
        this.intArray = new int[i];
        int j = 0;

        while (j < i) {
            this.intArray[j] = datainput.readInt();
            j++;
            if (astring != null) {
                break;
            }
        }
    }

    public int[] getIntArray() {
        return this.intArray;
    }

    static {
        packedTextFragments = new String[3];
        unpackLiteralFragments();
    }

    public boolean equals(Object object) {
        return super.equals(object)
                ? Arrays.equals(this.intArray, ((NBTTagIntArray) object).intArray)
                : false;
    }

    // region String decoding support

    // endregion

    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.intArray);
    }
}
