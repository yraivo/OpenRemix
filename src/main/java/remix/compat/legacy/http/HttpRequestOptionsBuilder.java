package remix.compat.legacy.http;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

public class HttpRequestOptionsBuilder {
    private int dD;
    private FluentMapWrapper fluentMapWrapper;
    private int c;
    private AbstractHttpRequest abstractHttpRequest;

    public HttpRequestOptionsBuilder bC(int i) {
        this.dD = i;
        return this;
    }

    public HttpRequestOptionsBuilder bD(int i) {
        this.c = i;
        return this;
    }

    HttpRequestOptionsBuilder(HttpOptionsAccessMarker accessMarker2) {
        this();
    }

    public HttpRequestOptionsBuilder UE(String text, String textValue) {
        if (this.fluentMapWrapper == null) {
            this.fluentMapWrapper = FluentCollections.newMutableMap();
        }

        this.fluentMapWrapper.putEntry(text, textValue);
        return this;
    }

    public HttpRequestOptions Zp() {
        HttpRequestOptions.p();

        if (this.abstractHttpRequest == null) {}

        FluentMapWrapper fluentMapWrapperValue =
                this.fluentMapWrapper == null
                        ? FluentCollections.newMutableMap()
                        : this.fluentMapWrapper;
        HttpRequestOptions httpRequestOptions =
                new HttpRequestOptions(
                        this.abstractHttpRequest, fluentMapWrapperValue, this.dD, this.c, null);

        return httpRequestOptions;
    }

    private HttpRequestOptionsBuilder() {}

    public HttpRequestOptionsBuilder method_cc3e4bb27b515f27a1d5(AbstractHttpRequest bOxValue) {
        this.abstractHttpRequest = bOxValue;
        return this;
    }
}
