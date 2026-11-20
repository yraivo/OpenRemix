package remix.compat.legacy.http;

import java.io.IOException;

public interface HttpConnectionOpener {
    int a = 443;
    String b = new String("https");

    UrlConnectionWrapper method_17f82745b501361129cb(
            HttpEndpointUrl address, TlsConnectionConfig request) throws IOException;

    default boolean method_7e48edbdc3b09eeab618(HttpEndpointUrl p171_0) {
        return p171_0.method_7eec03da329a017ec143() == 443
                || ((boolean)
                        (((java.lang.String)
                                        (((java.lang.String)
                                                (((java.lang.String)
                                                                (p171_0
                                                                        .method_e0e3092a59fee58fae52()))
                                                        .trim()))))
                                .equalsIgnoreCase(
                                        ((java.lang.String)
                                                (remix.compat.legacy.http.HttpConnectionOpener
                                                        .b)))));
    }
}
