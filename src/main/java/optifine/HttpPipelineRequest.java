package optifine;

public class HttpPipelineRequest {
    private HttpRequest httpRequest = null;
    private HttpListener httpListener = null;
    private boolean closed = false;

    public HttpPipelineRequest(HttpRequest httpRequestValue, HttpListener httpListenerValue) {
        this.httpRequest = httpRequestValue;
        this.httpListener = httpListenerValue;
    }

    public void setClosed(boolean closedValue) {
        this.closed = closedValue;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public HttpRequest getHttpRequest() {
        return this.httpRequest;
    }

    public HttpListener getHttpListener() {
        return this.httpListener;
    }
}
