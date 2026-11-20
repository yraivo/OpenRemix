package net.minecraft.nbt;

import com.google.common.collect.Lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class NBTTagList extends NBTBase {
    private byte tagType;

    private static Logger LOGGER;
    private static String[] bvz;
    private List tagList = Lists.newArrayList();

    public boolean hasNoTags() {
        return this.tagList.isEmpty();
    }

    public int tagCount() {
        return this.tagList.size();
    }

    public double getDoubleAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            NBTBase nbtBase = (NBTBase) this.tagList.get(i);
            return nbtBase.getId() == 6 ? ((NBTTagDouble) nbtBase).getDouble() : 0.0;
        } else {
            return 0.0;
        }
    }

    public byte getId() {
        return 9;
    }

    public void appendTag(NBTBase nbtBase) {
        {
            String[] astring = NBTBase.xf();
            if (this.tagType == 0) {
                this.tagType = nbtBase.getId();
                if (!(astring == null)) {

                    if (this.tagType != nbtBase.getId()) {
                        net.minecraft.nbt.NBTTagList.LOGGER.warn("Adding mismatching tag types to tag list");
                        return;
                    }
                }
            } else {

                if (this.tagType != nbtBase.getId()) {
                    net.minecraft.nbt.NBTTagList.LOGGER.warn("Adding mismatching tag types to tag list");
                    return;
                }
            }
        }

        this.tagList.add(nbtBase);
    }

    public float getFloat(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            NBTBase nbtBase = (NBTBase) this.tagList.get(i);
            return nbtBase.getId() == 5 ? ((NBTTagFloat) nbtBase).getFloat() : 0.0F;
        } else {
            return 0.0F;
        }
    }

    void read(DataInput datainput, int i, NBTSizeTracker nbtSizeTracker) throws IOException {
        String[] astring = NBTBase.xf();
        if (i > 512) {
            throw new RuntimeException(
                    "Tried to read NBT tag with too high complexity, depth > 512");
        }

        nbtSizeTracker.read(8L);
        this.tagType = datainput.readByte();
        int j = datainput.readInt();
        this.tagList = Lists.newArrayList();
        int k = 0;

        while (k < j) {
            NBTBase nbtBase = NBTBase.createNewByType(this.tagType);
            nbtBase.read(datainput, i + 1, nbtSizeTracker);
            this.tagList.add(nbtBase);
            k++;
            if (astring != null) {
                break;
            }
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.tagList.hashCode();
    }

    public NBTTagList() {
        this.tagType = 0;
    }

    void write(DataOutput dataoutput) throws IOException {
        String[] astring;
        {
            astring = NBTBase.xf();
            if (!this.tagList.isEmpty()) {
                this.tagType = ((NBTBase) this.tagList.get(0)).getId();
                if (!(astring == null)) {

                    this.tagType = 0;
                }
            } else {

                this.tagType = 0;
            }
        }

        dataoutput.writeByte(this.tagType);
        dataoutput.writeInt(this.tagList.size());
        int i = 0;

        while (i < this.tagList.size()) {
            ((NBTBase) this.tagList.get(i)).write(dataoutput);
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public int[] getIntArrayAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            NBTBase nbtBase = (NBTBase) this.tagList.get(i);
            return nbtBase.getId() == 11 ? ((NBTTagIntArray) nbtBase).getIntArray() : new int[0];
        } else {
            return new int[0];
        }
    }

    public NBTBase copy() {
        String[] astring1 = NBTBase.xf();
        NBTTagList nbtTagList = new NBTTagList();
        String[] astring = astring1;
        nbtTagList.tagType = this.tagType;

        for (java.util.Iterator<?> __recoveryIterator171_7664 = (this.tagList).iterator();
                (__recoveryIterator171_7664).hasNext(); ) {
            NBTBase nbtBase = (NBTBase) (__recoveryIterator171_7664).next();
            NBTBase nbtBaseValue = nbtBase.copy();
            nbtTagList.tagList.add(nbtBaseValue);
            if (astring != null) {
                break;
            }
        }

        return nbtTagList;
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagList nbtTagList = (NBTTagList) object;
            if (this.tagType == nbtTagList.tagType) {
                return this.tagList.equals(nbtTagList.tagList);
            }
        }

        return false;
    }

    public NBTBase get(int i) {
        return (NBTBase)
                (i >= 0 && i < this.tagList.size()
                        ? (NBTBase) this.tagList.get(i)
                        : new NBTTagEnd());
    }

    public String toString() {
        String[] astring1 = NBTBase.xf();
        String text = bvz[0];
        String[] astring = astring1;
        int i = 0;

        for (java.util.Iterator<?> __recoveryIterator171_29085 = (this.tagList).iterator();
                (__recoveryIterator171_29085).hasNext(); ) {
            NBTBase nbtBase = (NBTBase) (__recoveryIterator171_29085).next();
            text = text + bvz[1] + i + ':' + nbtBase + ',';
            i++;
            if (astring != null) {
                break;
            }
        }

        return text + bvz[2];
    }

    public String getStringTagAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            NBTBase nbtBase = (NBTBase) this.tagList.get(i);
            return nbtBase.getId() == 8 ? nbtBase.getString() : nbtBase.toString();
        } else {
            return bvz[3];
        }
    }

    public CompoundTag getCompoundTagAt(int i) {
        if (i >= 0 && i < this.tagList.size()) {
            NBTBase nbtBase = (NBTBase) this.tagList.get(i);
            return nbtBase.getId() == 10 ? (CompoundTag) nbtBase : new CompoundTag();
        } else {
            return new CompoundTag();
        }
    }

    public NBTBase removeTag(int i) {
        return (NBTBase) this.tagList.remove(i);
    }

    public void set(int i, NBTBase nbtBase) {
        String[] astring = NBTBase.xf();
        if (i >= 0 && i < this.tagList.size()) {
            {
                if (this.tagType == 0) {
                    this.tagType = nbtBase.getId();
                    if (!(astring == null)) {

                        if (this.tagType != nbtBase.getId()) {
                            net.minecraft.nbt.NBTTagList.LOGGER.warn("Adding mismatching tag types to tag list");
                            return;
                        }
                    }
                } else {

                    if (this.tagType != nbtBase.getId()) {
                        net.minecraft.nbt.NBTTagList.LOGGER.warn("Adding mismatching tag types to tag list");
                        return;
                    }
                }
            }

            this.tagList.set(i, nbtBase);
            if (astring == null) {
                return;
            }
        }

        net.minecraft.nbt.NBTTagList.LOGGER.warn("index out of bounds to set tag in tag list");
    }

    static {
        net.minecraft.nbt.NBTTagList.bvz = new String[6];
        net.minecraft.nbt.NBTTagList.bvz[0] = new String("[");
        net.minecraft.nbt.NBTTagList.bvz[1] = new String("");
        net.minecraft.nbt.NBTTagList.bvz[2] = new String("]");
        net.minecraft.nbt.NBTTagList.bvz[3] = new String("");
        net.minecraft.nbt.NBTTagList.bvz[4] =
                new String(
                        "\"\u009fj\t\u000b\u00a0j{1\u00fftC\u0098\u00ce\b\u008e)\u0089fl\u0001\u0099|\u00ef\u00a9\u00b7\u00a4s\u00f46\u00f6g\u00e8\u000b\b\u0085\u00d7\u00de\u0013+(\\\u00d7\u00a5\u0000\u00f2\u0081U_\u00d6{\u0088\u00d3\u0007\u00dc\u00fb\u00ccW\u00c1\u00a9e\u00f8\u00b8C\u00cbN3X\u00e3k$\u0005%\u0096C\u00c7\u008c.\u00ff,\u000f");
        net.minecraft.nbt.NBTTagList.bvz[5] =
                new String(
                        "V\u0017\u00b0.\u0018`\u00f9 :9\u0093T\u0093\u0081\u00b3\u0089h\u008e\u0089\u00d8\u00ca\u0011z\u00a27Vp\u00bd\"\u00fd+\u00c0\u0006V\u008a\u0006\u0088F\u00e6\u0014<\u00ffy\u0080\u00e1\u00b8\u00c4-3\u0083!\u0000\u00a7\u00f4\u00e3\u00fc\u00d7U\u0096*\u00c2P\u00b5Y\b\u008e\u007f\u00e7\u00c6\u00ca\u00d85\u00f5\b\u0092\u0085s\u009a\u0090\u00d7\u001d\u00d6\u00a6\u00e6\u0000\u009b\u00a5\u009b\u00961rQ*\u0002\u00f4b\u00dc\u00ec\u00e9\u00fe\u00e3\u0089");
        net.minecraft.nbt.NBTTagList.LOGGER = LogManager.getLogger();
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public int getTagType() {
        return this.tagType;
    }
}
