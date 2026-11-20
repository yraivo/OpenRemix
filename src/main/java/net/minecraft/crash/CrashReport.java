package net.minecraft.crash;

import net.minecraft.entity.Entity;
import net.minecraft.util.ReportedException;
import optifine.CrashReporter;
import optifine.Reflector;
import net.minecraft.crash.CrashReportIntCacheCallable;
import net.minecraft.crash.CrashReportJVMFlagsCallable;
import net.minecraft.crash.CrashReportJavaVMVersionCallable;
import net.minecraft.crash.CrashReportJavaVersionCallable;
import net.minecraft.crash.CrashReportMinecraftVersionCallable;
import net.minecraft.crash.CrashReportOperatingSystemCallable;
import net.minecraft.crash.MemoryUsageCrashDetail;

import com.google.common.collect.Lists;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CrashReport {
    private boolean sE;
    private String description;
    private final CrashReportCategory theReportCategory =
            new CrashReportCategory(this, "System Details");

    private boolean hZ;
    private final List crashReportSections = Lists.newArrayList();

    private Throwable cause;
    private static String[] packedTextFragments;
    private File crashReportFile;
    private StackTraceElement[] stacktrace;
    private static Logger logger;

    public String getDescription() {
        return this.description;
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public boolean saveToFile(File file1) {
        if (this.crashReportFile != null) {
            return false;
        }

        if (file1.getParentFile() != null) {
            file1.getParentFile().mkdirs();
        }

        try {
            FileWriter filewriter = new FileWriter(file1);
            filewriter.write(this.getCompleteReport());
            filewriter.close();
            this.crashReportFile = file1;
            return true;
        } catch (Throwable throwable) {
            net.minecraft.crash.CrashReport.logger.error("Could not save crash report to " + file1, throwable);
            return false;
        }
    }

    public CrashReportCategory makeCategoryDepth(String text, int i) {
        CrashReportCategory crashReportCategory = new CrashReportCategory(this, text);
        if (this.hZ) {
            int j = crashReportCategory.getPrunedStackTrace(i);
            StackTraceElement[] astacktraceelement = this.cause.getStackTrace();
            StackTraceElement stacktraceelement = null;
            StackTraceElement stacktraceelement1 = null;
            int k = astacktraceelement.length - j;
            if (k < 0) {
                System.out.println(
                        "Negative index in crash report handler ("
                                + astacktraceelement.length
                                + packedTextFragments[5]
                                + j
                                + packedTextFragments[6]);
            }

            if (astacktraceelement != null && 0 <= k && k < astacktraceelement.length) {
                stacktraceelement = astacktraceelement[k];
                if (astacktraceelement.length + 1 - j < astacktraceelement.length) {
                    stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - j];
                }
            }

            this.hZ =
                    crashReportCategory.firstTwoElementsOfStackTraceMatch(
                            stacktraceelement, stacktraceelement1);
            if (j > 0 && !this.crashReportSections.isEmpty()) {
                CrashReportCategory crashReportCategoryValue =
                        (CrashReportCategory)
                                this.crashReportSections.get(this.crashReportSections.size() - 1);
                crashReportCategoryValue.trimStackTraceEntriesFromBottom(j);
            } else if (astacktraceelement != null
                    && astacktraceelement.length >= j
                    && 0 <= k
                    && k < astacktraceelement.length) {
                this.stacktrace = new StackTraceElement[k];
                System.arraycopy(astacktraceelement, 0, this.stacktrace, 0, this.stacktrace.length);
            } else {
                this.hZ = false;
            }
        }

        this.crashReportSections.add(crashReportCategory);
        return crashReportCategory;
    }

    private void populateEnvironment() {
        this.theReportCategory.addCrashSectionCallable(
                "Minecraft Version", new CrashReportMinecraftVersionCallable(this));
        this.theReportCategory.addCrashSectionCallable(
                "Operating System", new CrashReportOperatingSystemCallable(this));
        this.theReportCategory.addCrashSectionCallable(
                "Java Version", new CrashReportJavaVersionCallable(this));
        this.theReportCategory.addCrashSectionCallable(
                "Java VM Version", new CrashReportJavaVMVersionCallable(this));
        this.theReportCategory.addCrashSectionCallable("Memory", new MemoryUsageCrashDetail(this));
        this.theReportCategory.addCrashSectionCallable(
                "JVM Flags", new CrashReportJVMFlagsCallable(this));
        this.theReportCategory.addCrashSectionCallable(
                "IntCache", new CrashReportIntCacheCallable(this));
        if (Reflector.HS.exists()) {
            Object object = Reflector.call(Reflector.FS, new Object[0]);
            Reflector.callString(object, Reflector.HS, new Object[] {this, this.theReportCategory});
        }
    }

    public Throwable getCrashCause() {
        return this.cause;
    }

    public String getCauseStackTraceOrString() {
        String[] astring1 = CrashReportCategory.b();
        StringWriter stringwriter = null;
        PrintWriter printwriter = null;
        String[] astring = astring1;
        Throwable throwable = this.cause;
        if (throwable.getMessage() == null) {
            label57:
            {
                if (throwable instanceof NullPointerException) {
                    throwable = new NullPointerException(this.description);
                    if (astring == null) {
                        break label57;
                    }
                }

                if (throwable instanceof StackOverflowError) {
                    throwable = new StackOverflowError(this.description);
                    if (astring == null) {
                        break label57;
                    }
                }

                if (throwable instanceof OutOfMemoryError) {
                    throwable = new OutOfMemoryError(this.description);
                }
            }

            throwable.setStackTrace(this.cause.getStackTrace());
        }

        String text = throwable.toString();

        try {
            stringwriter = new StringWriter();
            printwriter = new PrintWriter(stringwriter);
            throwable.printStackTrace(printwriter);
            text = stringwriter.toString();
        } finally {
            IOUtils.closeQuietly(stringwriter);
            IOUtils.closeQuietly(printwriter);
        }

        return text;
    }

    public static CrashReport makeCrashReport(Throwable throwable, String text) {
        CrashReport crashReport;
        if (throwable instanceof ReportedException) {
            crashReport = ((ReportedException) throwable).getCrashReport();
        } else {
            crashReport = new CrashReport(text, throwable);
        }

        return crashReport;
    }

    public File getFile() {
        return this.crashReportFile;
    }

    static {
        net.minecraft.crash.CrashReport.packedTextFragments = new String[8];
        net.minecraft.crash.CrashReport.packedTextFragments[0] = new String("\t");
        net.minecraft.crash.CrashReport.packedTextFragments[1] = new String("\n");
        net.minecraft.crash.CrashReport.packedTextFragments[2] = new String("\n");
        net.minecraft.crash.CrashReport.packedTextFragments[3] = new String("\n");
        net.minecraft.crash.CrashReport.packedTextFragments[4] = new String("-");
        net.minecraft.crash.CrashReport.packedTextFragments[5] = new String("/");
        net.minecraft.crash.CrashReport.packedTextFragments[6] = new String(")");
        net.minecraft.crash.CrashReport.logger = LogManager.getLogger();
    }

    private static String getWittyComment() {
        String[] astring =
                new String[] {
                    "Who set us up the TNT?",
                    "Everything's going to plan. No, really, that was supposed to happen.",
                    "Uh... Did I do that?",
                    "Oops.",
                    "Why did you do that?",
                    "I feel sad now :(",
                    "My bad.",
                    "I'm sorry, Dave.",
                    "I let you down. Sorry :(",
                    "On the bright side, I bought you a teddy bear!",
                    "Daisy, daisy...",
                    "Oh - I know what I did wrong!",
                    "Hey, that tickles! Hehehe!",
                    "I blame Dinnerbone.",
                    "You should try our sister game, Minceraft!",
                    "Don't be sad. I'll do better next time, I promise!",
                    "Don't be sad, have a hug! <3",
                    "I just don't know what went wrong :(",
                    "Shall we play a game?",
                    "Quite honestly, I wouldn't worry myself about that.",
                    "I bet Cylons wouldn't have this problem.",
                    "Sorry :(",
                    "Surprise! Haha. Well, this is awkward.",
                    "Would you like a cupcake?",
                    "Hi. I'm Minecraft, and I'm a crashaholic.",
                    "Ooh. Shiny.",
                    "This doesn't make any sense!",
                    "Why is it breaking :(",
                    "Don't do that.",
                    "Ouch. That hurt :(",
                    "You're mean.",
                    "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]",
                    "There are four lights!",
                    "But it works on my machine."
                };

        try {
            return astring[(int) (System.nanoTime() % astring.length)];
        } catch (Throwable throwable) {
            return "Witty comment unavailable :(";
        }
    }

    public CrashReportCategory makeCategory(String text) {
        return this.makeCategoryDepth(text, 1);
    }

    public String getCompleteReport() {
        String[] astring = CrashReportCategory.b();
        if (!this.sE) {
            this.sE = true;
            CrashReporter.onCrashReport(this, this.theReportCategory);
        }

        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("---- Minecraft Crash Report ----\n");
        Reflector.call(Reflector.HE, new Object[] {stringbuilder});
        Reflector.call(Reflector.Gz, new Object[] {stringbuilder});
        stringbuilder.append("// ");
        stringbuilder.append(getWittyComment());
        stringbuilder.append("\n\n");
        stringbuilder.append("Time: ");
        stringbuilder.append(new SimpleDateFormat().format(new Date()));
        stringbuilder.append(packedTextFragments[3]);
        stringbuilder.append("Description: ");
        stringbuilder.append(this.description);
        stringbuilder.append("\n\n");
        stringbuilder.append(this.getCauseStackTraceOrString());
        stringbuilder.append(
                "\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
        int i = 0;

        while (i < 87) {
            stringbuilder.append(packedTextFragments[4]);
            i++;
            if (astring != null) {
                Entity.setLegacyGlobalFlowMarker(new String[1]);
                break;
            }
        }

        stringbuilder.append("\n\n");
        this.getSectionsInStringBuilder(stringbuilder);
        return stringbuilder.toString();
    }

    public CrashReportCategory getCategory() {
        return this.theReportCategory;
    }

    public CrashReport(String text, Throwable throwable) {
        this.hZ = true;
        this.stacktrace = new StackTraceElement[0];
        this.sE = false;
        this.description = text;
        this.cause = throwable;
        this.populateEnvironment();
    }

    public void getSectionsInStringBuilder(StringBuilder stringbuilder) {
        String[] astring = CrashReportCategory.b();
        if ((this.stacktrace == null || this.stacktrace.length <= 0)
                && this.crashReportSections.size() > 0) {
            this.stacktrace =
                    (StackTraceElement[])
                            ArrayUtils.subarray(
                                    ((CrashReportCategory) this.crashReportSections.get(0))
                                            .getStackTrace(),
                                    0,
                                    1);
        }

        if (this.stacktrace != null && this.stacktrace.length > 0) {
            stringbuilder.append("-- Head --\n");
            stringbuilder.append("Stacktrace:\n");

            for (StackTraceElement stacktraceelement : this.stacktrace) {
                stringbuilder
                        .append(packedTextFragments[0])
                        .append("at ")
                        .append(stacktraceelement.toString());
                stringbuilder.append(packedTextFragments[1]);
                if (astring != null) {
                    break;
                }
            }

            stringbuilder.append(packedTextFragments[2]);
        }

        for (java.util.Iterator<?> __recoveryIterator171_47600 =
                        (this.crashReportSections).iterator();
                (__recoveryIterator171_47600).hasNext(); ) {
            CrashReportCategory crashReportCategory =
                    (CrashReportCategory) (__recoveryIterator171_47600).next();
            crashReportCategory.appendToStringBuilder(stringbuilder);
            stringbuilder.append("\n\n");
            if (astring != null) {
                break;
            }
        }

        this.theReportCategory.appendToStringBuilder(stringbuilder);
    }
}
