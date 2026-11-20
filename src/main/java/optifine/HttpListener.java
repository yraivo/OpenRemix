package optifine;

public interface HttpListener {
    void failed(HttpRequest httpRequest, Exception exception);

    void finished(HttpRequest httpRequest, HttpResponse httpResponse);
}
