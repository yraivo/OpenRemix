package optifine;

import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequest {
    public static String kE;
    private int redirects;
    private byte[] body;
    public static String vI;
    private String http;
    public static String sT;
    private Map<String, String> headers;
    private int port;
    private String method;
    private String file;
    private Proxy proxy;
    public static String mC;
    private String host = null;

    public static String kG;

    public int getPort() {
        return this.port;
    }

    public String getMethod() {
        return this.method;
    }

    // region String decoding support

    // endregion

    static {
        kG = "HTTP/1.0";
        vI = "HEAD";
        sT = "GET";
        kE = "HTTP/1.1";
        mC = "POST";
    }

    public HttpRequest(
            String text,
            int portValue,
            Proxy proxy,
            String textValue,
            String textValue2,
            String textValue3,
            Map<String, String> map,
            byte[] abyte) {
        this.port = 0;
        this.proxy = Proxy.NO_PROXY;
        this.method = null;
        this.file = null;
        this.http = null;
        this.headers = new LinkedHashMap<>();
        this.body = null;
        this.redirects = 0;
        this.host = text;
        this.port = portValue;
        this.proxy = proxy;
        this.method = textValue;
        this.file = textValue2;
        this.http = textValue3;
        this.headers = map;
        this.body = abyte;
    }

    public void setRedirects(int redirectsValue) {
        this.redirects = redirectsValue;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    // region String decoding support

    // endregion

    public String getFile() {
        return this.file;
    }

    // region String decoding support

    // endregion

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getHttp() {
        return this.http;
    }

    public byte[] getBody() {
        return this.body;
    }

    public int getRedirects() {
        return this.redirects;
    }

    public String getHost() {
        return this.host;
    }
}
