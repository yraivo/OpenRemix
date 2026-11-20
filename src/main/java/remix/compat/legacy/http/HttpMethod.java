package remix.compat.legacy.http;

public enum HttpMethod {
    OPTIONS(new String("OPTIONS")),
    PUT(new String("PUT")),
    TRACE(new String("TRACE")),
    GET(new String("GET")),
    HEAD(new String("HEAD")),
    POST(new String("POST")),
    DELETE(new String("DELETE"));

    private String a;

    // region String decoding support

    // endregion


    // region String decoding support

    // endregion

    public String method_e0e3092a59fee58fae52() {
        return this.a;
    }

    HttpMethod(String text) {
        this.a = text;
    }
}
