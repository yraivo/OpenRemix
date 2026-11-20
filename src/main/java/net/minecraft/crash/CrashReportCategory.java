package net.minecraft.crash;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.BlockPos;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.Callable;

public class CrashReportCategory {

    private StackTraceElement[] stackTrace;
    private String name;
    private static String[] e;
    private final List children = Lists.newArrayList();
    private CrashReport theCrashReport;

    public StackTraceElement[] getStackTrace() {
        return this.stackTrace;
    }

    public static void addBlockInfo(
            CrashReportCategory crashReportCategory, BlockPos blockPos, Block block, int i) {
        int j = Block.getIdFromBlock(block);
        crashReportCategory.addCrashSectionCallable(
                "Block type", new BlockTypeCrashDetail(j, block));
        crashReportCategory.addCrashSectionCallable(
                "Block data value", new BlockMetadataCrashDetail(i));
        crashReportCategory.addCrashSectionCallable(
                "Block location", new BlockLocationCrashDetail(blockPos));
    }

    public static String getCoordinateInfo(BlockPos blockPos) {
        int x = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();
        StringBuilder stringbuilder = new StringBuilder();

        try {
            stringbuilder.append(String.format("World: (%d,%d,%d)", x, j, k));
        } catch (Throwable throwable2) {
            stringbuilder.append("(Error finding world loc)");
        }

        stringbuilder.append(", ");

        try {
            int l = x >> 4;
            int i1 = k >> 4;
            int j1 = x & 15;
            int k1 = j >> 4;
            int l1 = k & 15;
            int i2 = l << 4;
            int j2 = i1 << 4;
            int k2 = (l + 1 << 4) - 1;
            int l2 = (i1 + 1 << 4) - 1;
            stringbuilder.append(
                    String.format(
                            "Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)",
                            j1, k1, l1, l, i1, i2, j2, k2, l2));
        } catch (Throwable throwable1) {
            stringbuilder.append("(Error finding chunk loc)");
        }

        stringbuilder.append(", ");

        try {
            int j3 = x >> 9;
            int k3 = k >> 9;
            int l3 = j3 << 5;
            int i4 = k3 << 5;
            int j4 = (j3 + 1 << 5) - 1;
            int k4 = (k3 + 1 << 5) - 1;
            int l4 = j3 << 9;
            int i5 = k3 << 9;
            int j5 = (j3 + 1 << 9) - 1;
            int i3 = (k3 + 1 << 9) - 1;
            stringbuilder.append(
                    String.format(
                            "Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)",
                            j3, k3, l3, i4, j4, k4, l4, i5, j5, i3));
        } catch (Throwable throwable) {
            stringbuilder.append("(Error finding world loc)");
        }

        return stringbuilder.toString();
    }

    public void addCrashSectionThrowable(String text, Throwable throwable) {
        this.addCrashSection(text, throwable);
    }

    public static void addBlockInfo(
            CrashReportCategory crashReportCategory, BlockPos blockPos, BlockState blockState) {
        crashReportCategory.addCrashSectionCallable("Block", new BlockStateCrashDetail(blockState));
        crashReportCategory.addCrashSectionCallable(
                "Block location", new BlockStateLocationCrashDetail(blockPos));
    }

    public boolean firstTwoElementsOfStackTraceMatch(
            StackTraceElement stacktraceelement, StackTraceElement stacktraceelement1) {
        if (this.stackTrace.length != 0 && stacktraceelement != null) {
            StackTraceElement stacktraceelement2 = this.stackTrace[0];
            if (stacktraceelement2.isNativeMethod() == stacktraceelement.isNativeMethod()
                    && stacktraceelement2.getClassName().equals(stacktraceelement.getClassName())
                    && java.util.Objects.equals(
                            stacktraceelement2.getFileName(), stacktraceelement.getFileName())
                    && stacktraceelement2
                            .getMethodName()
                            .equals(stacktraceelement.getMethodName())) {
                if ((stacktraceelement1 != null ? 1 : 0) != (this.stackTrace.length > 1 ? 1 : 0)) {
                    return false;
                }

                if (stacktraceelement1 != null && !this.stackTrace[1].equals(stacktraceelement1)) {
                    return false;
                }

                this.stackTrace[0] = stacktraceelement;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // region String decoding support

    // endregion

    public int getPrunedStackTrace(int i) {
        StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
        if (astacktraceelement.length <= 0) {
            return 0;
        }

        this.stackTrace = new StackTraceElement[astacktraceelement.length - 3 - i];
        System.arraycopy(astacktraceelement, 3 + i, this.stackTrace, 0, this.stackTrace.length);
        return this.stackTrace.length;
    }

    public CrashReportCategory(CrashReport aYJValue, String text) {
        this.stackTrace = new StackTraceElement[0];
        this.theCrashReport = aYJValue;
        this.name = text;
    }

    static {
        b(null);
    }

    // region String decoding support

    // endregion

    public void addCrashSection(String text, Object object) {
        this.children.add(new CrashReportCategoryEntry(text, object));
    }

    public void addCrashSectionCallable(String text, Callable callable) {
        try {
            this.addCrashSection(text, callable.call());
        } catch (Throwable throwable) {
            this.addCrashSectionThrowable(text, throwable);
        }
    }

    public static String getCoordinateInfo(double d0, double d1, double d2) {
        return String.format(
                "%.2f,%.2f,%.2f - %s", d0, d1, d2, getCoordinateInfo(new BlockPos(d0, d1, d2)));
    }

    public static void b(String[] astring) {
        e = astring;
    }

    public void appendToStringBuilder(StringBuilder stringbuilder) {
        String[] astring1 = b();
        stringbuilder.append("-- ").append(this.name).append(" --\n");
        String[] astring = astring1;
        stringbuilder.append("Details:");

        for (java.util.Iterator<?> __recoveryIterator171_33149 = (this.children).iterator();
                (__recoveryIterator171_33149).hasNext(); ) {
            CrashReportCategoryEntry crashReportCategoryEntry =
                    (CrashReportCategoryEntry) (__recoveryIterator171_33149).next();
            stringbuilder.append("\n\t");
            stringbuilder.append(crashReportCategoryEntry.getKey());
            stringbuilder.append(": ");
            stringbuilder.append(crashReportCategoryEntry.getValue());
            if (astring != null) {
                break;
            }
        }

        if (this.stackTrace != null && this.stackTrace.length > 0) {
            stringbuilder.append("\nStacktrace:");

            for (StackTraceElement stacktraceelement : this.stackTrace) {
                stringbuilder.append("\n\tat ");
                stringbuilder.append(stacktraceelement.toString());
                if (astring != null) {
                    break;
                }
            }
        }
    }

    public void trimStackTraceEntriesFromBottom(int i) {
        StackTraceElement[] astacktraceelement = new StackTraceElement[this.stackTrace.length - i];
        System.arraycopy(this.stackTrace, 0, astacktraceelement, 0, astacktraceelement.length);
        this.stackTrace = astacktraceelement;
    }

    public static String[] b() {
        return e;
    }
}
