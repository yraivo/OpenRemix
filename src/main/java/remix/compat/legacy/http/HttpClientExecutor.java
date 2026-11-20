package remix.compat.legacy.http;

import remix.compat.legacy.script.NashornScriptService;
import remix.util.collection.FluentMapWrapper;

import net.minecraft.entity.Entity;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class HttpClientExecutor {
    public static String a;

    private TlsConnectionConfig tlsConnectionConfig;

    public HttpClientExecutor(TlsConnectionConfig bOZValue) {
        this.tlsConnectionConfig = bOZValue;
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    static {
        a =
                "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
    }

    public HttpResponseData method_66c671f0cc63c9c4633d(
            String text, HttpRequestOptions httpRequestOptions) throws Exception {
        int i = TlsConnectionConfig.f();
        HttpResponseData httpResponseData =
                this.method_68831363f106cf19368b(
                        (HttpEndpointUrl)
                                NashornScriptService.bYK
                                        .afn()
                                        .method_ac2483d759ac3aef8c49("tUrl", new Object[] {text}),
                        httpRequestOptions,
                        true);
        if (i == 0) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }

        return httpResponseData;
    }

    public HttpClientExecutor() throws KeyManagementException, NoSuchAlgorithmException {
        this(new TlsConnectionConfig(new DefaultHttpConnectionOpener1()));
    }

    public HttpResponseData method_68831363f106cf19368b(
            HttpEndpointUrl httpEndpointUrl,
            HttpRequestOptions httpRequestOptions,
            boolean flag)
            throws Exception {
        AbstractHttpRequest abstractHttpRequest = httpRequestOptions.getAbstractHttpRequest();
        String text = abstractHttpRequest.q();
        int l = TlsConnectionConfig.g();
        httpEndpointUrl =
                abstractHttpRequest.method_e4fc96dd4a272a88e831(httpEndpointUrl);
        int i = l;
        UrlConnectionWrapper urlConnectionWrapper =
                this.tlsConnectionConfig.getHttpConnectionOpener().method_17f82745b501361129cb(httpEndpointUrl, this.tlsConnectionConfig);
        String textValue = abstractHttpRequest.method_c60693f457aa70be7160();
        if (textValue.equals(HttpMethod.POST.method_e0e3092a59fee58fae52())) {
            urlConnectionWrapper.method_5a0449839d28b4a71843(true);
        }

        HttpURLConnection httpurlconnection = urlConnectionWrapper.Zh();
        int j = httpRequestOptions.method_9b417d27949cba33a0d0();
        int k = httpRequestOptions.method_beea3e16fe3ec0995eb9();
        if (k >= 0) {
            httpurlconnection.setReadTimeout(k);
        }

        if (j >= 0) {
            httpurlconnection.setConnectTimeout(j);
        }

        httpurlconnection.setRequestMethod(textValue);
        this.method_950d124e6e6d14471439(httpurlconnection, abstractHttpRequest.Zs());
        this.method_950d124e6e6d14471439(httpurlconnection, httpRequestOptions.getFluentMapWrapper());
        if (urlConnectionWrapper.d()) {
            DataOutputStream dataoutputstream =
                    new DataOutputStream(urlConnectionWrapper.Zf());
            Throwable throwable = null;

            try {
                dataoutputstream.writeBytes(text);
                dataoutputstream.flush();
            } catch (Throwable throwable1) {
                throwable = throwable1;
                throw throwable1;
            } finally {
                label87:
                if (dataoutputstream != null) {
                    if (throwable != null) {
                        try {
                            dataoutputstream.close();
                            break label87;
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                            if (i == 0) {
                                break label87;
                            }
                        }
                    }

                    dataoutputstream.close();
                }
            }
        }

        HttpResponseData httpResponseData =
                HttpResponseData.method_a1e45e2243c35188855e(
                        httpurlconnection,
                        new BufferedUrlResponseReader(new HttpStatusStreamSelector()),
                        flag);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            TlsConnectionConfig.b(++i);
        }

        return httpResponseData;
    }

    private void method_950d124e6e6d14471439(
            URLConnection urlconnection, FluentMapWrapper fluentMapWrapper) {
        int j = TlsConnectionConfig.f();
        Iterator iterator = ((Map) fluentMapWrapper.Qu()).entrySet().iterator();
        int i = j;

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            urlconnection.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
            if (i == 0) {
                break;
            }
        }
    }

    public HttpResponseData method_4d450e740594676c32d5(
            HttpEndpointUrl httpEndpointUrl,
            HttpRequestOptions httpRequestOptions)
            throws Exception {
        return this.method_68831363f106cf19368b(
                httpEndpointUrl, httpRequestOptions, true);
    }

    public HttpResponseData method_429ada31eae41b20e214(
            URI uri, HttpRequestOptions httpRequestOptions) throws Exception {
        return this.method_68831363f106cf19368b(
                new HttpEndpointUrl(uri), httpRequestOptions, true);
    }
}
