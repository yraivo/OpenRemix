package remix.compat.legacy.http;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

public final class DelegatingHttpRequest extends AbstractHttpRequest {
    private HttpRequestSpec httpRequestSpec;
    private String g;

    public String method_c60693f457aa70be7160() {
        return this.httpRequestSpec.method_c60693f457aa70be7160();
    }

    public String q() throws Exception {
        try {
            if (this.g != null) {
                return this.g;
            }
        } catch (java.lang.Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    remix.compat.legacy.http.DelegatingHttpRequest.method_bbe44e843daabb729dea(
                            exception));
        }

        java.lang.String text = this.httpRequestSpec.q();
        this.g = text;
        return text;
    }

    public int aW() {
        try {
            if (this.g != null) {
                return this.g.length();
            }
        } catch (java.lang.Exception exception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    remix.compat.legacy.http.DelegatingHttpRequest.method_bbe44e843daabb729dea(
                            exception1));
        }

        try {
            this.g = this.q();
        } catch (java.lang.Exception exception) {
            exception.printStackTrace();
        }

        return this.g.length();
    }

    DelegatingHttpRequest(HttpRequestSpec bOCValue) {
        this.httpRequestSpec = bOCValue;
    }

    public FluentMapWrapper Zs() {
        return this.httpRequestSpec instanceof AbstractHttpRequest
                ? ((AbstractHttpRequest) this.httpRequestSpec).Zs()
                : FluentCollections.newMutableMap();
    }

    public HttpEndpointUrl method_e4fc96dd4a272a88e831(HttpEndpointUrl httpEndpointUrl)
            throws Exception {
        try {
            return this.httpRequestSpec instanceof AbstractHttpRequest
                    ? ((AbstractHttpRequest) this.httpRequestSpec)
                            .method_e4fc96dd4a272a88e831(httpEndpointUrl)
                    : httpEndpointUrl;
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(method_bbe44e843daabb729dea(exception));
        }
    }

    public MediaType Zu() {
        return this.httpRequestSpec.Zu();
    }

    private static Exception method_bbe44e843daabb729dea(Exception exception) {
        return exception;
    }
}
