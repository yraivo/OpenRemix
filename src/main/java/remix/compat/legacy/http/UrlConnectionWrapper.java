package remix.compat.legacy.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class UrlConnectionWrapper {
    private URLConnection uRLConnection;

    public OutputStream Zf() throws IOException {
        return this.uRLConnection.getOutputStream();
    }

    public boolean d() {
        return this.uRLConnection.getDoOutput();
    }

    public URLConnection getURLConnection() {
        return this.uRLConnection;
    }

    public void method_5a0449839d28b4a71843(boolean flag) {
        this.uRLConnection.setDoOutput(flag);
    }

    public HttpURLConnection Zh() {
        return (HttpURLConnection) this.uRLConnection;
    }

    public HttpsURLConnection Zi() {
        return (HttpsURLConnection) this.uRLConnection;
    }

    public UrlConnectionWrapper(URLConnection urlconnection) {
        this.uRLConnection = urlconnection;
    }
}
