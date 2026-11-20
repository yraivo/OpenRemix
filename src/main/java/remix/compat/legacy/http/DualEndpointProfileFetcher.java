package remix.compat.legacy.http;

import remix.compat.legacy.auth.AuthVerificationOutcome;
import remix.compat.legacy.auth.AuthVerificationResult;
import remix.compat.legacy.auth.DefaultLicenseSession;
import remix.compat.legacy.auth.LicenseProfileRecord;
import remix.compat.legacy.auth.RemoteAuthServiceContract;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;

import java.util.Collections;

public final class DualEndpointProfileFetcher {
    public static boolean primaryEmptyResultSeen;
    private RemoteAuthServiceContract remoteAuthServiceContract;
    private AbstractProfilePasteEndpoint fallbackEndpoint;
    private AbstractProfilePasteEndpoint primaryEndpoint;

    public RemoteAuthServiceContract getRemoteAuthServiceContract() {
        return this.remoteAuthServiceContract;
    }

    public DualEndpointProfileFetcher(
            RemoteAuthServiceContract bOWValue,
            AbstractProfilePasteEndpoint bOYValue,
            AbstractProfilePasteEndpoint bOXValue) {
        this.remoteAuthServiceContract = bOWValue;
        this.primaryEndpoint = bOYValue;
        this.fallbackEndpoint = bOXValue;
    }

    public FluentListWrapper fetchProfileLines(
            DefaultLicenseSession defaultLicenseSession) throws Exception {
        FluentListWrapper fluentListWrapper;
        try {
            fluentListWrapper =
                    this.primaryEndpoint.getPasteProfileFetcher().fetchProfileLines(defaultLicenseSession);
            if (fluentListWrapper.isEmpty()) {
                NullPointerException nullpointerexception1 = new NullPointerException();
                nullpointerexception1.setStackTrace(new StackTraceElement[0]);
                remix.compat.legacy.http.DualEndpointProfileFetcher.primaryEmptyResultSeen = true;
                throw nullpointerexception1;
            }
        } catch (Exception exception2) {
            Exception exception = exception2;

            try {
                exception.printStackTrace();
                fluentListWrapper =
                        this.fallbackEndpoint.getPasteProfileFetcher().fetchProfileLines(defaultLicenseSession);
                if (fluentListWrapper.isEmpty()) {
                    NullPointerException nullpointerexception = new NullPointerException();
                    nullpointerexception.setStackTrace(new StackTraceElement[0]);
                    throw nullpointerexception;
                }
            } catch (Exception exception1) {
                exception1.printStackTrace();
                fluentListWrapper = FluentCollections.ax();
            }
        }

        return fluentListWrapper;
    }

    public RemotePasteEndpoint getPrimaryEndpoint() {
        return this.primaryEndpoint;
    }

    public AbstractRemotePasteEndpoint getFallbackEndpoint() {
        return this.fallbackEndpoint;
    }

    public AuthVerificationResult evaluateProfile(
            LicenseProfileRecord licenseProfileRecord) throws Exception {
        AuthVerificationResult authVerificationResult;
        try {
            authVerificationResult =
                    this.primaryEndpoint.getLicenseProfileEvaluator().evaluateProfile(licenseProfileRecord);
            if (authVerificationResult == null) {
                NullPointerException nullpointerexception = new NullPointerException();
                nullpointerexception.setStackTrace(new StackTraceElement[0]);
                throw nullpointerexception;
            }
        } catch (Exception exception1) {
            try {
                authVerificationResult =
                        this.fallbackEndpoint.getLicenseProfileEvaluator().evaluateProfile(licenseProfileRecord);
                if (authVerificationResult == null) {
                    NullPointerException nullpointerexception1 = new NullPointerException();
                    nullpointerexception1.setStackTrace(new StackTraceElement[0]);
                    throw nullpointerexception1;
                }
            } catch (Exception exception) {
                authVerificationResult =
                        new AuthVerificationResult(
                                new LicenseProfileRecord(null, Collections.emptyList()),
                                AuthVerificationOutcome.FAILURE);
            }
        }

        return authVerificationResult;
    }
}
