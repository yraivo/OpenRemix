package net.minecraft.nbt;

import com.google.common.collect.Maps;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CompoundTag extends NBTBase {
    private static int ef;
    private Map tagMap = Maps.newHashMap();

    private static String[] packedTextFragments;
    private static Logger logger;

    private CrashReport createCrashReport(
            String text, int i, ClassCastException classcastexception) {
        CrashReport crashReport =
                CrashReport.makeCrashReport(classcastexception, "Reading NBT data");
        CrashReportCategory crashReportCategory =
                crashReport.makeCategoryDepth("Corrupt NBT tag", 1);
        crashReportCategory.addCrashSectionCallable(
                "Tag type found", new ActualNbtTypeCrashDetail(this, text));
        crashReportCategory.addCrashSectionCallable(
                "Tag type expected", new ExpectedNbtTypeCrashDetail(this, i));
        crashReportCategory.addCrashSection("Tag name", text);
        return crashReport;
    }

    static NBTBase readNBT(
            byte b0, String text, DataInput datainput, int i, NBTSizeTracker nbtSizeTracker) {
        NBTBase nbtBase = NBTBase.createNewByType(b0);

        try {
            nbtBase.read(datainput, i, nbtSizeTracker);
            return nbtBase;
        } catch (IOException ioexception) {
            CrashReport crashReport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("NBT Tag");
            crashReportCategory.addCrashSection("Tag name", text);
            crashReportCategory.addCrashSection("Tag type", b0);
            throw new ReportedException(crashReport);
        }
    }

    public void setString(String text, String textValue) {
        this.tagMap.put(text, new net.minecraft.nbt.NBTTagString(textValue));
    }

    void write(DataOutput dataoutput) throws IOException {
        String[] astring1 = NBTBase.xf();
        Iterator iterator = this.tagMap.keySet().iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            String text = (String) iterator.next();
            NBTBase nbtBase = (NBTBase) this.tagMap.get(text);
            writeEntry(text, nbtBase, dataoutput);
            if (astring != null) {
                break;
            }
        }

        dataoutput.writeByte(0);
    }

    public byte[] getByteArray(String text) {
        try {
            try {
                if (!this.hasKey(text, 7)) {
                    return new byte[0];
                }
            } catch (java.lang.ClassCastException classcastexception) {
                throw remix.compat.compiler.JvmThrowTypes.unchecked(
                        net.minecraft.nbt.CompoundTag.a(classcastexception));
            }

            return ((net.minecraft.nbt.NBTTagByteArray) this.tagMap.get(text)).getByteArray();
        } catch (java.lang.ClassCastException classcastexception1) {
            throw new net.minecraft.util.ReportedException(
                    this.createCrashReport(text, 7, classcastexception1));
        }
    }

    public void setDouble(String text, double p171_1) {
        this.tagMap.put(text, new net.minecraft.nbt.NBTTagDouble(p171_1));
    }

    static Map access$000(CompoundTag compoundTag) {
        return compoundTag.tagMap;
    }

    public String toString() {
        String[] astring1 = NBTBase.xf();
        String text = packedTextFragments[2];
        String[] astring = astring1;
        Iterator iterator = this.tagMap.keySet().iterator();

        while (iterator.hasNext()) {
            String textValue = (String) iterator.next();
            text =
                    text
                            + textValue
                            + ':'
                            + this.tagMap.get(textValue)
                            + (iterator.hasNext() ? ',' : packedTextFragments[3]);
            if (astring != null) {
                break;
            }
        }

        return text + packedTextFragments[4];
    }

    public boolean hasKey(String text, int p171_1) {
        byte b0 = this.getTagId(text);
        if (b0 == p171_1) {
            return true;
        }

        if (p171_1 != 99) {
            if (b0 > 0) {}

            return false;
        } else {
            return (!(b0 != 1 && b0 != 2 && b0 != 3 && b0 != 4 && b0 != 5 && b0 != 6));
        }
    }

    private static String readKey(DataInput datainput, NBTSizeTracker nbtSizeTracker)
            throws IOException {
        return datainput.readUTF();
    }

    public double getDouble(String text) {
        try {
            try {
                if (!this.hasKey(text, 99)) {
                    return 0.0;
                }
            } catch (java.lang.ClassCastException classcastexception) {
                throw remix.compat.compiler.JvmThrowTypes.unchecked(
                        net.minecraft.nbt.CompoundTag.a(classcastexception));
            }

            return ((net.minecraft.nbt.NBTBaseNBTPrimitive) this.tagMap.get(text)).getDouble();
        } catch (java.lang.ClassCastException classcastexception1) {
            return 0.0;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.tagMap.hashCode();
    }

    public static int method_beea3e16fe3ec0995eb9() {
        return ef;
    }

    public void setIntArray(String text, int[] p171_1) {
        this.tagMap.put(text, new net.minecraft.nbt.NBTTagIntArray(p171_1));
    }

    public byte getTagId(String text) {
        NBTBase nbtBase = (NBTBase) this.tagMap.get(text);
        return nbtBase != null ? nbtBase.getId() : 0;
    }

    public byte getByte(String text) {
        try {
            try {
                if (!this.hasKey(text, 99)) {
                    return 0;
                }
            } catch (java.lang.ClassCastException classcastexception) {
                throw remix.compat.compiler.JvmThrowTypes.unchecked(
                        net.minecraft.nbt.CompoundTag.a(classcastexception));
            }

            return ((net.minecraft.nbt.NBTBaseNBTPrimitive) this.tagMap.get(text)).getByte();
        } catch (java.lang.ClassCastException classcastexception1) {
            return 0;
        }
    }

    // region String decoding support

    // endregion

    private static byte readType(DataInput datainput, NBTSizeTracker nbtSizeTracker)
            throws IOException {
        return datainput.readByte();
    }

    void read(DataInput datainput, int i, NBTSizeTracker nbtSizeTracker) throws IOException {
        String[] astring = NBTBase.xf();
        if (i > 512) {
            throw new RuntimeException(
                    "Tried to read NBT tag with too high complexity, depth > 512");
        }

        this.tagMap.clear();

        byte b0;
        while ((b0 = readType(datainput, nbtSizeTracker)) != 0) {
            String text = readKey(datainput, nbtSizeTracker);
            nbtSizeTracker.read(16 * text.length());
            NBTBase nbtBase = readNBT(b0, text, datainput, i + 1, nbtSizeTracker);
            this.tagMap.put(text, nbtBase);
            if (astring != null) {
                break;
            }
        }
    }

    public int getInteger(String text) {
        try {
            try {
                if (!this.hasKey(text, 99)) {
                    return 0;
                }
            } catch (java.lang.ClassCastException classcastexception) {
                throw remix.compat.compiler.JvmThrowTypes.unchecked(
                        net.minecraft.nbt.CompoundTag.a(classcastexception));
            }

            return ((net.minecraft.nbt.NBTBaseNBTPrimitive) this.tagMap.get(text)).getInt();
        } catch (java.lang.ClassCastException classcastexception1) {
            return 0;
        }
    }

    public NBTTagList getTagList(String text, int p171_1) {
        try {
            if (this.getTagId(text) != 9) {
                return new net.minecraft.nbt.NBTTagList();
            }

            net.minecraft.nbt.NBTTagList nbtTagList =
                    (net.minecraft.nbt.NBTTagList)
                            ((java.lang.Object)
                                    (((java.util.Map) (this.tagMap))
                                            .get(((java.lang.Object) (text)))));
            return nbtTagList.tagCount() > 0 && nbtTagList.getTagType() != p171_1
                    ? new net.minecraft.nbt.NBTTagList()
                    : nbtTagList;
        } catch (java.lang.ClassCastException classcastexception) {
            throw new net.minecraft.util.ReportedException(
                    this.createCrashReport(text, 9, classcastexception));
        }
    }

    public int[] getIntArray(String text) {
        try {
            try {
                if (!this.hasKey(text, 11)) {
                    return new int[0];
                }
            } catch (java.lang.ClassCastException classcastexception) {
                throw remix.compat.compiler.JvmThrowTypes.unchecked(
                        net.minecraft.nbt.CompoundTag.a(classcastexception));
            }

            return ((net.minecraft.nbt.NBTTagIntArray) this.tagMap.get(text)).getIntArray();
        } catch (java.lang.ClassCastException classcastexception1) {
            throw new net.minecraft.util.ReportedException(
                    this.createCrashReport(text, 11, classcastexception1));
        }
    }

    public byte getId() {
        return 10;
    }

    public void setByteArray(String text, byte[] p171_1) {
        this.tagMap.put(text, new net.minecraft.nbt.NBTTagByteArray(p171_1));
    }

    public static int aW() {
        int i = method_beea3e16fe3ec0995eb9();
        return i == 0 ? 58 : 0;
    }

    public long getLong(String text) {
        try {
            long j;
            if (!this.hasKey(text, 99)) {

                j = 0L;
            } else {
                j = ((NBTBaseNBTPrimitive) this.tagMap.get(text)).getLong();
            }

            return j;
        } catch (ClassCastException classcastexception) {

            return 0L;
        }
    }

    public String getString(String text) {
        try {
            return !this.hasKey(text, 8)
                    ? packedTextFragments[0]
                    : ((NBTBase) this.tagMap.get(text)).getString();
        } catch (ClassCastException classcastexception) {
            return packedTextFragments[1];
        }
    }

    public void setBoolean(String text, boolean p171_1) {
        net.minecraft.nbt.CompoundTag compoundTag;
        java.lang.String textValue;
        byte b0;
        label16:
        {
            try {
                compoundTag = this;
                textValue = text;
                if (p171_1) {
                    b0 = 1;
                    break label16;
                }
            } catch (java.lang.RuntimeException runtimeexception) {
                throw remix.compat.compiler.JvmThrowTypes.unchecked(
                        net.minecraft.nbt.CompoundTag.a(runtimeexception));
            }

            b0 = 0;
        }

        compoundTag.setByte(textValue, (byte) b0);
    }

    public boolean hasNoTags() {
        return this.tagMap.isEmpty();
    }

    // region String decoding support

    // endregion

    private static void writeEntry(String text, NBTBase nbtBase, DataOutput dataoutput)
            throws IOException {
        dataoutput.writeByte(nbtBase.getId());
        if (nbtBase.getId() != 0) {
            dataoutput.writeUTF(text);
            nbtBase.write(dataoutput);
        }
    }

    public boolean getBoolean(String text) {
        try {
            if (this.getByte(text) != 0) {
                return true;
            }
        } catch (java.lang.RuntimeException runtimeexception) {
            throw remix.compat.compiler.JvmThrowTypes.unchecked(
                    net.minecraft.nbt.CompoundTag.a(runtimeexception));
        }

        return false;
    }

    public void setByte(String text, byte p171_1) {
        this.tagMap.put(text, new net.minecraft.nbt.NBTTagByte(p171_1));
    }

    public float getFloat(String text) {
        try {
            try {
                if (!this.hasKey(text, 99)) {
                    return 0.0F;
                }
            } catch (java.lang.ClassCastException classcastexception) {
                throw remix.compat.compiler.JvmThrowTypes.unchecked(
                        net.minecraft.nbt.CompoundTag.a(classcastexception));
            }

            return ((net.minecraft.nbt.NBTBaseNBTPrimitive) this.tagMap.get(text)).getFloat();
        } catch (java.lang.ClassCastException classcastexception1) {
            return 0.0F;
        }
    }

    public void setInteger(String text, int p171_1) {
        this.tagMap.put(text, new net.minecraft.nbt.NBTTagInt(p171_1));
    }

    public void merge(CompoundTag compoundTag) {
        String[] astring1 = NBTBase.xf();
        Iterator iterator = compoundTag.tagMap.keySet().iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            label24:
            {
                String text = (String) iterator.next();
                NBTBase nbtBase = (NBTBase) compoundTag.tagMap.get(text);
                if (nbtBase.getId() == 10) {
                    if (this.hasKey(text, 10)) {
                        CompoundTag compoundTagValue = this.getCompoundTag(text);
                        compoundTagValue.merge((CompoundTag) nbtBase);
                        if (astring == null) {
                            break label24;
                        }
                    }

                    this.setTag(text, nbtBase.copy());
                    if (astring == null) {
                        break label24;
                    }
                }

                this.setTag(text, nbtBase.copy());
            }

            if (astring != null) {
                break;
            }
        }
    }

    public boolean hasKey(String text) {
        return this.tagMap.containsKey(text);
    }

    // region Static initialization and string tables
    static {
        net.minecraft.nbt.CompoundTag.packedTextFragments = new String[7];
        net.minecraft.nbt.CompoundTag.packedTextFragments[0] = new String("");
        net.minecraft.nbt.CompoundTag.packedTextFragments[1] = new String("");
        net.minecraft.nbt.CompoundTag.packedTextFragments[2] = new String("{");
        net.minecraft.nbt.CompoundTag.packedTextFragments[3] = new String("");
        net.minecraft.nbt.CompoundTag.packedTextFragments[4] = new String("}");
        b(0);
        net.minecraft.nbt.CompoundTag.logger = LogManager.getLogger();
    }

    // endregion

    public void setShort(String text, short p171_1) {
        this.tagMap.put(text, new net.minecraft.nbt.NBTTagShort(p171_1));
    }

    public CompoundTag getCompoundTag(String text) {
        try {
            try {
                if (!this.hasKey(text, 10)) {
                    return new net.minecraft.nbt.CompoundTag();
                }
            } catch (java.lang.ClassCastException classcastexception) {
                throw remix.compat.compiler.JvmThrowTypes.unchecked(
                        net.minecraft.nbt.CompoundTag.a(classcastexception));
            }

            return (net.minecraft.nbt.CompoundTag) this.tagMap.get(text);
        } catch (java.lang.ClassCastException classcastexception1) {
            throw new net.minecraft.util.ReportedException(
                    this.createCrashReport(text, 10, classcastexception1));
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public NBTBase copy() {
        CompoundTag compoundTag = new CompoundTag();

        for (java.util.Iterator<?> __recoveryIterator171_27716 = (this.tagMap.keySet()).iterator();
                (__recoveryIterator171_27716).hasNext(); ) {
            String text = (String) (__recoveryIterator171_27716).next();
            compoundTag.setTag(text, ((NBTBase) this.tagMap.get(text)).copy());
        }

        return compoundTag;
    }

    public void setFloat(String text, float p171_1) {
        this.tagMap.put(text, new net.minecraft.nbt.NBTTagFloat(p171_1));
    }

    public short getShort(String text) {
        try {
            try {
                if (!this.hasKey(text, 99)) {
                    return 0;
                }
            } catch (java.lang.ClassCastException classcastexception) {
                throw remix.compat.compiler.JvmThrowTypes.unchecked(
                        net.minecraft.nbt.CompoundTag.a(classcastexception));
            }

            return ((net.minecraft.nbt.NBTBaseNBTPrimitive) this.tagMap.get(text)).getShort();
        } catch (java.lang.ClassCastException classcastexception1) {
            return 0;
        }
    }

    public void removeTag(String text) {
        this.tagMap.remove(text);
    }

    public void setLong(String text, long p171_1) {
        this.tagMap.put(text, new net.minecraft.nbt.NBTTagLong(p171_1));
    }

    public NBTBase getTag(String text) {
        return (NBTBase) this.tagMap.get(text);
    }

    public Set getKeySet() {
        return this.tagMap.keySet();
    }

    public void setTag(String text, NBTBase nbtBase) {
        this.tagMap.put(text, nbtBase);
    }

    public static void b(int i) {
        ef = i;
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            CompoundTag compoundTag = (CompoundTag) object;
            return this.tagMap.entrySet().equals(compoundTag.tagMap.entrySet());
        } else {
            return false;
        }
    }
}
