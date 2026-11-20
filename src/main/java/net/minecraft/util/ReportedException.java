package net.minecraft.util;

import net.minecraft.crash.CrashReport;

public class ReportedException extends RuntimeException {
    private CrashReport theReportedExceptionCrashReport;

    @Override
    public String getMessage() {
        return this.theReportedExceptionCrashReport.getDescription();
    }

    @Override
    public Throwable getCause() {
        return this.theReportedExceptionCrashReport.getCrashCause();
    }

    public ReportedException(CrashReport aYJValue) {
        this.theReportedExceptionCrashReport = aYJValue;
    }

    public CrashReport getCrashReport() {
        return this.theReportedExceptionCrashReport;
    }
}
