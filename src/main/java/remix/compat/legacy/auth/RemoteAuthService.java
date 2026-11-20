package remix.compat.legacy.auth;

import net.minecraft.entity.Entity;

import remix.client.AbstractClientBootstrap;
import remix.client.RemixClient;
import remix.compat.legacy.http.AbstractRemotePasteEndpoint;
import remix.compat.legacy.http.DualEndpointProfileFetcher;
import remix.compat.legacy.http.HttpResponseData;
import remix.compat.legacy.http.PasteProfileFilter;
import remix.compat.legacy.http.PastebinProfilePasteEndpoint;
import remix.compat.legacy.http.RemotePasteEndpoint;
import remix.compat.legacy.http.TeknikProfilePasteEndpoint;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

public enum RemoteAuthService implements RemoteAuthServiceContract {
    INSTANCE(initializeConstantState());
    private static RemoteAuthService[] valuesArray;

    private static int legacyAuthFlowState;
    public boolean profileAccepted;

    private FluentMapWrapper responseBodiesByKey;
    private int responseCount;

    public void validateLegacyProfile(AbstractClientBootstrap bootstrap) {
        boolean legacyFallthrough = getLegacyAuthFlowState() != 0;
        DualEndpointProfileFetcher fetcher = RemixClient.INSTANCE.getDualEndpointProfileFetcher();
        RemotePasteEndpoint primary = fetcher.getPrimaryEndpoint();
        if (!(primary instanceof PastebinProfilePasteEndpoint)) {
            bootstrap.handleLegacyLicenseFailure();
            return;
        }

        PastebinProfilePasteEndpoint pastebin = (PastebinProfilePasteEndpoint) primary;
        if (pastebin.getFluentListWrapper().isEmpty()) {
            bootstrap.handleLegacyLicenseFailure();
        }
        PasteProfileFilter primaryFilter = pastebin.getPasteProfileFilter();
        boolean primaryMatches =
                primaryFilter.matchesProfile(
                        pastebin, bootstrap.getClientServices().getVerifiedLicenseProfile());
        if (!primaryMatches) {
            AbstractRemotePasteEndpoint fallback = fetcher.getFallbackEndpoint();
            if (fallback instanceof TeknikProfilePasteEndpoint) {
                TeknikProfilePasteEndpoint teknik = (TeknikProfilePasteEndpoint) fallback;
                if (teknik.getFluentListWrapper().isEmpty()) {
                    bootstrap.handleLegacyLicenseFailure();
                }
                PasteProfileFilter fallbackFilter = teknik.getPasteProfileFilter();
                boolean fallbackMatches =
                        fallbackFilter.matchesProfile(
                                teknik, bootstrap.getClientServices().getVerifiedLicenseProfile());
                if (!fallbackMatches) {
                    bootstrap.handleLegacyLicenseFailure();
                    if (legacyFallthrough) {
                        Entity.setLegacyGlobalFlowMarker(new String[5]);
                    }
                }
                if (fallbackMatches || legacyFallthrough) {
                    this.profileAccepted = true;
                }
                if (legacyFallthrough) {
                    bootstrap.handleLegacyLicenseFailure();
                }
            } else {
                bootstrap.handleLegacyLicenseFailure();
            }
        }
        if (primaryMatches || legacyFallthrough) {
            this.profileAccepted = true;
        }
        if (legacyFallthrough) {
            bootstrap.handleLegacyLicenseFailure();
        }
    }

    // region String decoding support

    // endregion

    public int getResponseCount() {
        return this.responseCount;
    }

    public void incrementResponseCount() {
        this.responseCount++;
    }

    public FluentMapWrapper getResponseBodiesByKey() {
        return this.responseBodiesByKey;
    }

    public HttpResponseData recordResponse(Object object, HttpResponseData httpResponseData) {
        this.responseBodiesByKey.putEntry(object, httpResponseData.getBody());
        int j = getLegacyAuthFlowState();
        this.incrementResponseCount();
        int i = j;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setLegacyAuthFlowState(++i);
        }

        return httpResponseData;
    }

    public static int getLegacyAuthFlowState() {
        return remix.compat.legacy.auth.RemoteAuthService.legacyAuthFlowState;
    }

    static {
        remix.compat.legacy.auth.RemoteAuthService.valuesArray =
                new RemoteAuthService[] {remix.compat.legacy.auth.RemoteAuthService.INSTANCE};
    }

    public static int getLegacyAuthFlowToken() {
        int i = getLegacyAuthFlowState();
        return i == 0 ? 99 : 0;
    }

    public static void setLegacyAuthFlowState(int i) {
        remix.compat.legacy.auth.RemoteAuthService.legacyAuthFlowState = i;
    }

    RemoteAuthService(FluentMapWrapper bOlValue) {
        this.responseBodiesByKey = bOlValue;
        this.responseCount = 0;
    }

    // region String decoding support

    // endregion

    private static RemoteAuthService[] __recovery_values171() {
        return remix.compat.legacy.auth.RemoteAuthService.valuesArray.clone();
    }

    // Initialize shared state before the first constant is published.
    private static FluentMapWrapper initializeConstantState() {
        if (getLegacyAuthFlowState() != 0) {
            setLegacyAuthFlowState(93);
        }
        return FluentCollections.newMutableMap();
    }
}
