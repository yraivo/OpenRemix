package remix.compat.legacy.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

public final class HttpStatusStreamSelector implements UrlStreamSelector {
    public InputStream method_735770407f173dba28d4(URLConnection urlConnection) throws IOException {
        java.net.HttpURLConnection httpurlconnection = (java.net.HttpURLConnection) urlConnection;

        try {
            if (httpurlconnection.getResponseCode() < 400) {
                return urlConnection.getInputStream();
            }
        } catch (java.io.IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    remix.compat.legacy.http.HttpStatusStreamSelector.a(ioexception));
        }

        return httpurlconnection.getErrorStream();
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }
}
