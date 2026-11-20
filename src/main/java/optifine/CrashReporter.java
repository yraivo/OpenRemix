package optifine;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import shadersmod.client.Shaders;

public class CrashReporter {

    private static String[] packedTextFragments;

    private static String makeReport(CrashReport crashReport) {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append(
                "OptiFineVersion: "
                        + OptiFineConfig.getVersion()
                        + packedTextFragments[0]);
        stringbuffer.append(
                "Summary: " + makeSummary(crashReport) + packedTextFragments[1]);
        stringbuffer.append(packedTextFragments[2]);
        stringbuffer.append(crashReport.getCompleteReport());
        stringbuffer.append(packedTextFragments[3]);
        return stringbuffer.toString();
    }

    public static void onCrashReport(
            CrashReport crashReport, CrashReportCategory crashReportCategory) {
        // Automatic crash transmission disabled.
    }

    static {
        optifine.CrashReporter.packedTextFragments = new String[17];
        optifine.CrashReporter.packedTextFragments[0] = new String("\n");
        optifine.CrashReporter.packedTextFragments[1] = new String("\n");
        optifine.CrashReporter.packedTextFragments[2] = new String("\n");
        optifine.CrashReporter.packedTextFragments[3] = new String("\n");
        optifine.CrashReporter.packedTextFragments[4] = new String("]");
        optifine.CrashReporter.packedTextFragments[5] = new String("");
        optifine.CrashReporter.packedTextFragments[6] = new String("");
        optifine.CrashReporter.packedTextFragments[7] = new String("");
        optifine.CrashReporter.packedTextFragments[8] = new String("");
        optifine.CrashReporter.packedTextFragments[9] = new String("");
        optifine.CrashReporter.packedTextFragments[10] = new String("");
        optifine.CrashReporter.packedTextFragments[11] = new String("");
        optifine.CrashReporter.packedTextFragments[12] = new String("");
        optifine.CrashReporter.packedTextFragments[13] = new String("");
        optifine.CrashReporter.packedTextFragments[14] = new String("");
    }

    private static String makeSummary(CrashReport crashReport) {
        Throwable throwable = crashReport.getCrashCause();
        if (throwable == null) {
            return "Unknown";
        }

        StackTraceElement[] astacktraceelement = throwable.getStackTrace();
        String text = "unknown";
        if (astacktraceelement.length > 0) {
            text = astacktraceelement[0].toString().trim();
        }

        return throwable.getClass().getName()
                + ": "
                + throwable.getMessage()
                + " ("
                + crashReport.getDescription()
                + ") ["
                + text
                + packedTextFragments[4];
    }

    public static void extendCrashReport(CrashReportCategory crashReportCategory) {
        crashReportCategory.addCrashSection(
                "OptiFine Version", OptiFineConfig.getVersion());
        if (OptiFineConfig.getGameSettings() != null) {
            crashReportCategory.addCrashSection(
                    "Render Distance Chunks", packedTextFragments[5] + OptiFineConfig.getChunkViewDistance());
            crashReportCategory.addCrashSection(
                    "Mipmaps", packedTextFragments[6] + OptiFineConfig.getMipmapLevels());
            crashReportCategory.addCrashSection(
                    "Anisotropic Filtering",
                    packedTextFragments[7] + OptiFineConfig.getAnisotropicFilterLevel());
            crashReportCategory.addCrashSection(
                    "Antialiasing", packedTextFragments[8] + OptiFineConfig.getAntialiasingLevel());
            crashReportCategory.addCrashSection(
                    "Multitexture", packedTextFragments[9] + OptiFineConfig.isMultiTexture());
        }

        crashReportCategory.addCrashSection("Shaders", packedTextFragments[10] + Shaders.getShaderPackName());
        crashReportCategory.addCrashSection(
                "OpenGlVersion", packedTextFragments[11] + OptiFineConfig.openGlVersion);
        crashReportCategory.addCrashSection(
                "OpenGlRenderer", packedTextFragments[12] + OptiFineConfig.openGlRenderer);
        crashReportCategory.addCrashSection(
                "OpenGlVendor", packedTextFragments[13] + OptiFineConfig.openGlVendor);
        crashReportCategory.addCrashSection(
                "CpuCount", packedTextFragments[14] + OptiFineConfig.getAvailableProcessors());
    }
}
