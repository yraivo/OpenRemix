package remix.compat.legacy.auth;

import remix.compat.legacy.http.HttpResponseData;
import remix.util.collection.FluentMapWrapper;

import remix.client.AbstractClientBootstrap;

public interface RemoteAuthServiceContract {
    void incrementResponseCount();

    HttpResponseData recordResponse(
            Object object, HttpResponseData httpResponseData);

    FluentMapWrapper getResponseBodiesByKey();

    void validateLegacyProfile(AbstractClientBootstrap abstractClientBootstrap);
}
