package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd extends NBTBase {

    private static String c;

    public NBTBase copy() {
        return new NBTTagEnd();
    }

    public String toString() {
        return c;
    }

    // region String decoding support

    // endregion

    static {
        c = "END";
    }

    // region String decoding support

    // endregion

    void read(DataInput datainput, int i, NBTSizeTracker nbtSizeTracker) throws IOException {}

    void write(DataOutput dataoutput) throws IOException {}

    public byte getId() {
        return 0;
    }
}
