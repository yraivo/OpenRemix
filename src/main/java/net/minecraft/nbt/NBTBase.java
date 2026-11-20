package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {
    private static String[] b;

    public static String[] NBT_TYPES;

    @Override
    public int hashCode() {
        return this.getId();
    }

    abstract void read(DataInput datainput, int i, NBTSizeTracker nbtSizeTracker)
            throws IOException;

    public static String[] xf() {
        return b;
    }

    public boolean hasNoTags() {
        return false;
    }

    @Override
    public abstract String toString();

    public abstract NBTBase copy();

    static {
        b(null);
        net.minecraft.nbt.NBTBase.NBT_TYPES =
                new String[] {
                    "END",
                    "BYTE",
                    "SHORT",
                    "INT",
                    "LONG",
                    "FLOAT",
                    "DOUBLE",
                    "BYTE[]",
                    "STRING",
                    "LIST",
                    "COMPOUND",
                    "INT[]"
                };
    }

    public abstract byte getId();

    protected String getString() {
        return this.toString();
    }

    // region String decoding support

    // endregion

    public static void b(String[] astring) {
        b = astring;
    }

    abstract void write(DataOutput dataoutput) throws IOException;

    protected static NBTBase createNewByType(byte b0) {
        switch (b0) {
            case 0:
                return new NBTTagEnd();
            case 1:
                return new NBTTagByte();
            case 2:
                return new NBTTagShort();
            case 3:
                return new NBTTagInt();
            case 4:
                return new NBTTagLong();
            case 5:
                return new NBTTagFloat();
            case 6:
                return new NBTTagDouble();
            case 7:
                return new NBTTagByteArray();
            case 8:
                return new NBTTagString();
            case 9:
                return new NBTTagList();
            case 10:
                return new CompoundTag();
            case 11:
                return new NBTTagIntArray();
            default:
                return null;
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NBTBase)) {
            return false;
        }

        NBTBase nbtBase = (NBTBase) object;
        return this.getId() == nbtBase.getId();
    }
}
