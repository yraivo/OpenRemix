package remix.compat.legacy.http;

import remix.util.collection.FluentMapWrapper;

import java.nio.charset.StandardCharsets;

public interface HttpRequestSpec {
    String c = new String("=");
    String d = new String("?");
    String a = StandardCharsets.UTF_8.name();
    String b = new String("&");

    String method_c60693f457aa70be7160();

    MediaType Zu();

    FluentMapWrapper getFluentMapWrapper();

    HttpRequestSpec bD(String text) throws Exception;

    int aW();

    HttpRequestSpec UL(String text, String textValue) throws Exception;

    String q() throws Exception;

    default DelegatingHttpRequest Zx() {
        return new remix.compat.legacy.http.DelegatingHttpRequest(this);
    }

    default DelegatingHttpRequest method_18e88ad37ad0f462d16d(
            HttpRequestSpec p171_0) {
        return new remix.compat.legacy.http.DelegatingHttpRequest(p171_0);
    }
}
