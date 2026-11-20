package net.minecraft.crash;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.concurrent.Callable;

public class CrashReportJVMFlagsCallable implements Callable {
    private static String[] packedTextFragments;
    CrashReport aYJ;

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    static {
        net.minecraft.crash.CrashReportJVMFlagsCallable.packedTextFragments = new String[2];
        net.minecraft.crash.CrashReportJVMFlagsCallable.packedTextFragments[0] = new String(" ");
    }

    public String getCrashDetail() {
        String[] astring1 = CrashReportCategory.b();
        RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
        List inputArguments = runtimemxbean.getInputArguments();
        int i = 0;
        String[] astring = astring1;
        StringBuilder stringbuilder = new StringBuilder();

        for (java.util.Iterator<?> __recoveryIterator171_24890 = (inputArguments).iterator();
                (__recoveryIterator171_24890).hasNext(); ) {
            String text = (String) (__recoveryIterator171_24890).next();
            if (text.startsWith("-X")) {
                if (i++ > 0) {
                    stringbuilder.append(packedTextFragments[0]);
                }

                stringbuilder.append(text);
            }

            if (astring != null) {
                break;
            }
        }

        return String.format("%d total; %s", i, stringbuilder.toString());
    }

    public CrashReportJVMFlagsCallable(CrashReport aYJValue) {
        this.aYJ = aYJValue;
    }
}
