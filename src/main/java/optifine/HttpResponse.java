package optifine;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {
    private int status = 0;
    private String statusLine = null;
    private byte[] body;
    private Map<String, String> headers = new LinkedHashMap<>();

    public byte[] getBody() {
        return this.body;
    }

    public Map getHeaders() {
        return this.headers;
    }

    public HttpResponse(int statusValue, String text, Map map, byte[] bodyValue) {
        this.status = statusValue;
        this.statusLine = text;
        this.headers = map;
        this.body = bodyValue;
    }

    public String getStatusLine() {
        return this.statusLine;
    }

    public int getStatus() {
        return this.status;
    }

    public String getHeader(String text) {
        return (java.lang.String) this.headers.get(text);
    }
}
