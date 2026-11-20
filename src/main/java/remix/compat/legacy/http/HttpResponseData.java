package remix.compat.legacy.http;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;

public final class HttpResponseData {
    private static String[] packedTextFragments;
    private int statusCode;
    private FluentMapWrapper fluentMapWrapper;
    private static String[] d;
    private String body;

    public String getBody() {
        return this.body;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    // region String decoding support

    // endregion

    public FluentMapWrapper getFluentMapWrapper() {
        return this.fluentMapWrapper;
    }

    public static String[] kM() {
        return d;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.http.HttpResponseData.packedTextFragments[0] = new String("");
    }

    // endregion

    public static HttpResponseData method_a1e45e2243c35188855e(
            HttpURLConnection httpurlconnection,
            UrlResponseReader urlResponseReader,
            boolean flag)
            throws IOException {
        kM();

        String text =
                method_3ad8948c997104bd2f5c(httpurlconnection, urlResponseReader, flag);
        int responseCode = httpurlconnection.getResponseCode();

        httpurlconnection.disconnect();
        HttpResponseData httpResponseData =
                new HttpResponseData(
                        responseCode,
                        text == null ? packedTextFragments[0] : text,
                        FluentCollections.method_a09b51431db5c67c4348(
                                httpurlconnection.getHeaderFields()));

        return httpResponseData;
    }

    private static String method_3ad8948c997104bd2f5c(
            URLConnection urlconnection,
            UrlResponseReader urlResponseReader,
            boolean flag)
            throws IOException {
        return urlResponseReader.method_3ae681d954869fc1a1b0(urlconnection, flag);
    }

    private HttpResponseData(int i, String text, FluentMapWrapper bOHValue) {
        this.statusCode = i;
        this.body = text;
        this.fluentMapWrapper = bOHValue;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (kM() == null) {
            b(new String[5]);
        }
    }

    public static void b(String[] astring) {
        d = astring;
    }
}
