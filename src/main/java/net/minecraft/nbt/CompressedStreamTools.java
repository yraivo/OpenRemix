package net.minecraft.nbt;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ReportedException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressedStreamTools {

    private static String[] packedTextFragments;

    public static void writeCompressed(CompoundTag compoundTag, OutputStream outputstream)
            throws IOException {
        DataOutputStream dataoutputstream =
                new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputstream)));

        try {
            write(compoundTag, dataoutputstream);
        } finally {
            dataoutputstream.close();
        }
    }

    public static void write(CompoundTag compoundTag, DataOutput dataoutput) throws IOException {
        writeTag(compoundTag, dataoutput);
    }

    public static CompoundTag read(File file1) throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (!file1.exists()) {
                return null;
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }

        DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));

        CompoundTag compoundTag;
        try {
            compoundTag = read(datainputstream, NBTSizeTracker.INFINITE);
        } finally {
            datainputstream.close();
        }

        return compoundTag;
    }

    public static void safeWrite(CompoundTag compoundTag, File file1) throws IOException {
        String[] astring1 = NBTBase.xf();
        File file2 = new File(file1.getAbsolutePath() + "_tmp");
        String[] astring = astring1;

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (file2.exists()) {
                file2.delete();
            }
        } catch (IOException ioexception3) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception3));
        }

        try {
            write(compoundTag, file2);
            if (file1.exists()) {
                file1.delete();
            }
        } catch (IOException ioexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception1));
        }

        try {
            if (file1.exists()) {
                throw new IOException("Failed to delete " + file1);
            }
        } catch (IOException ioexception2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception2));
        }

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            file2.renameTo(file1);
            if (astring != null) {
                Entity.setLegacyGlobalFlowMarker(new String[5]);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    private static NBTBase method_33e0123288f9dfe02548(
            DataInput datainput, int i, NBTSizeTracker nbtSizeTracker) throws IOException {
        byte b0 = datainput.readByte();

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (b0 == 0) {
                return new NBTTagEnd();
            }
        } catch (IOException ioexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception1));
        }

        datainput.readUTF();
        NBTBase nbtBase = NBTBase.createNewByType(b0);

        try {
            nbtBase.read(datainput, i, nbtSizeTracker);
            return nbtBase;
        } catch (IOException ioexception) {
            CrashReport crashReport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("NBT Tag");
            crashReportCategory.addCrashSection("Tag name", "[UNNAMED TAG]");
            crashReportCategory.addCrashSection("Tag type", b0);
            throw new ReportedException(crashReport);
        }
    }

    public static CompoundTag read(DataInput datainput, NBTSizeTracker nbtSizeTracker)
            throws IOException {
        NBTBase nbtBase = method_33e0123288f9dfe02548(datainput, 0, nbtSizeTracker);

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (nbtBase instanceof CompoundTag) {
                return (CompoundTag) nbtBase;
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }

        throw new IOException("Root tag must be a named compound tag");
    }

    public static CompoundTag readCompressed(InputStream inputstream) throws IOException {
        DataInputStream datainputstream =
                new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputstream)));

        CompoundTag compoundTag;
        try {
            compoundTag = read(datainputstream, NBTSizeTracker.INFINITE);
        } finally {
            datainputstream.close();
        }

        return compoundTag;
    }

    public static void write(CompoundTag compoundTag, File file1) throws IOException {
        DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));

        try {
            write(compoundTag, dataoutputstream);
        } finally {
            dataoutputstream.close();
        }
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    static {
        net.minecraft.nbt.CompressedStreamTools.packedTextFragments = new String[3];
        net.minecraft.nbt.CompressedStreamTools.packedTextFragments[0] = new String("");
    }

    private static void writeTag(NBTBase nbtBase, DataOutput dataoutput) throws IOException {
        try {
            dataoutput.writeByte(nbtBase.getId());
            if (nbtBase.getId() != 0) {
                dataoutput.writeUTF(packedTextFragments[0]);
                nbtBase.write(dataoutput);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }
    }

    public static CompoundTag read(DataInputStream datainputstream) throws IOException {
        return read(datainputstream, NBTSizeTracker.INFINITE);
    }
}
