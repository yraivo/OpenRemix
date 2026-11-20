package remix.compat.legacy.http;

import remix.compat.legacy.auth.RemoteAuthService;
import remix.util.collection.FluentListWrapper;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public abstract class AbstractRemotePasteEndpoint<T> implements RemotePasteEndpoint {
    private String baseUrl;
    private PasteProfileFilter pasteProfileFilter;
    private FluentListWrapper fluentListWrapper;
    public static HttpClientExecutor httpExecutor;

    public PasteProfileFilter getPasteProfileFilter() {
        return this.pasteProfileFilter;
    }

    public static HttpResponseData fetchPasteResponse(
            HttpEndpointUrl httpEndpointUrl) throws Exception {
        return RemoteAuthService.INSTANCE.recordResponse(
                httpEndpointUrl,
                remix.compat.legacy.http.AbstractRemotePasteEndpoint.httpExecutor.method_4d450e740594676c32d5(
                        httpEndpointUrl,
                        HttpRequestOptions.Zm()
                                .method_cc3e4bb27b515f27a1d5(new HttpGetRequest().Zx())
                                .Zp()));
    }

    static {
        try {
            remix.compat.legacy.http.AbstractRemotePasteEndpoint.httpExecutor = new HttpClientExecutor();
        } catch (KeyManagementException | NoSuchAlgorithmException keymanagementexception) {
        }
    }

    public AbstractRemotePasteEndpoint(
            String text, List<String> textList, PasteProfileFilter bPbValue) {
        this.baseUrl = text;
        this.fluentListWrapper = new FluentListWrapper(textList);
        this.pasteProfileFilter = bPbValue;
    }

    public FluentListWrapper getFluentListWrapper() {
        return this.fluentListWrapper;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public abstract FluentListWrapper fetchParsedProfiles() throws Exception;
}
