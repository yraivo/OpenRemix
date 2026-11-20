package optifine;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpPipeline {
    public static String c;
    private static Map mapConnections;
    public static String e;
    public static String b;
    public static String te;
    public static String f;
    private static String[] packedTextFragments;

    public static String sT;

    public static String g;
    public static String d;
    public static String h;

    public static HttpRequest makeRequest(String text, Proxy proxy) throws IOException {
        URL url = new URL(text);

        try {
            if (!url.getProtocol().equals("http")) {
                throw new IOException("Only protocol http is supported: " + url);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }

        String file = url.getFile();
        String host = url.getHost();
        int port = url.getPort();
        if (port <= 0) {
            port = 80;
        }

        String textValue = "GET";
        String textValue2 = "HTTP/1.1";
        LinkedHashMap linkedhashmap = new LinkedHashMap();
        linkedhashmap.put("User-Agent", "Java/" + System.getProperty("java.version"));
        linkedhashmap.put("Host", host);
        linkedhashmap.put("Accept", "text/html, image/gif, image/png");
        linkedhashmap.put("Connection", "keep-alive");
        byte[] abyte = new byte[0];
        return new HttpRequest(
                host, port, proxy, textValue, file, textValue2, linkedhashmap, abyte);
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    private static String makeConnectionKey(String text, int i, Proxy proxy) {
        return text + packedTextFragments[0] + i + packedTextFragments[1] + proxy;
    }

    public static HttpResponse executeRequest(HttpRequest httpRequest) throws IOException {
        HashMap hashmap = new HashMap();

        BlockingHttpResponseListener blockingHttpResponseListener =
                new BlockingHttpResponseListener(hashmap);
        synchronized (hashmap) {
            HttpPipelineRequest httpPipelineRequest =
                    new HttpPipelineRequest(httpRequest, blockingHttpResponseListener);
            addRequest(httpPipelineRequest);

            try {
                hashmap.wait();
            } catch (InterruptedException interruptedexception) {
                throw new InterruptedIOException("Interrupted");
            }

            Exception exception = (Exception) hashmap.get("Exception");
            if (exception != null) {
                if (exception instanceof IOException) {
                    throw (IOException) exception;
                } else if (exception instanceof RuntimeException) {
                    throw (RuntimeException) exception;
                } else {
                    throw new RuntimeException(exception.getMessage(), exception);
                }
            } else {
                HttpResponse httpResponse = (HttpResponse) hashmap.get("Response");
                if (httpResponse == null) {
                    throw new IOException("Response is null");
                } else {
                    return httpResponse;
                }
            }
        }
    }

    private static synchronized HttpPipelineConnection getConnection(
            String text, int i, Proxy proxy) {
        String textValue = makeConnectionKey(text, i, proxy);
        HttpPipelineConnection httpPipelineConnection =
                (HttpPipelineConnection) optifine.HttpPipeline.mapConnections.get(textValue);
        if (httpPipelineConnection == null) {
            httpPipelineConnection = new HttpPipelineConnection(text, i, proxy);
            optifine.HttpPipeline.mapConnections.put(textValue, httpPipelineConnection);
        }

        return httpPipelineConnection;
    }

    public static boolean aY() {
        for (java.util.Iterator<?> __recoveryIterator171_5793 =
                        (optifine.HttpPipeline.mapConnections.values()).iterator();
                (__recoveryIterator171_5793).hasNext(); ) {
            HttpPipelineConnection httpPipelineConnection =
                    (HttpPipelineConnection) (__recoveryIterator171_5793).next();
            if (httpPipelineConnection.aR()) {
                return true;
            }
        }

        return false;
    }

    public static void addRequest(String text, HttpListener httpListener, Proxy proxy)
            throws IOException {
        HttpRequest httpRequest = makeRequest(text, proxy);
        HttpPipelineRequest httpPipelineRequest =
                new HttpPipelineRequest(httpRequest, httpListener);
        addRequest(httpPipelineRequest);
    }

    public static void addRequest(String text, HttpListener httpListener) throws IOException {
        addRequest(text, httpListener, Proxy.NO_PROXY);
    }

    public static byte[] get(String text) throws IOException {
        return get(text, Proxy.NO_PROXY);
    }

    public static void addRequest(HttpPipelineRequest httpPipelineRequest) {
        HttpRequest httpRequest = httpPipelineRequest.getHttpRequest();
        int j = ReflectorMethod.cR();
        HttpPipelineConnection connection =
                getConnection(httpRequest.getHost(), httpRequest.getPort(), httpRequest.getProxy());
        int i = j;

        while (!connection.addRequest(httpPipelineRequest)) {
            removeConnection(
                    httpRequest.getHost(),
                    httpRequest.getPort(),
                    httpRequest.getProxy(),
                    connection);
            connection =
                    getConnection(
                            httpRequest.getHost(), httpRequest.getPort(), httpRequest.getProxy());
            if (i == 0) {
                break;
            }
        }
    }

    private static synchronized void removeConnection(
            String text, int i, Proxy proxy, HttpPipelineConnection httpPipelineConnection) {
        String textValue = makeConnectionKey(text, i, proxy);
        HttpPipelineConnection httpPipelineConnectionValue =
                (HttpPipelineConnection) optifine.HttpPipeline.mapConnections.get(textValue);

        try {
            if (httpPipelineConnectionValue == httpPipelineConnection) {
                optifine.HttpPipeline.mapConnections.remove(textValue);
            }
        } catch (RuntimeException runtimeexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(runtimeexception));
        }
    }

    static {
        optifine.HttpPipeline.packedTextFragments = new String[4];
        optifine.HttpPipeline.packedTextFragments[0] = new String(":");
        optifine.HttpPipeline.packedTextFragments[1] = new String("-");
        g = "Connection";
        b = "User-Agent";
        te = "Transfer-Encoding";
        c = "Host";
        f = "Keep-Alive";
        sT = "chunked";
        h = "keep-alive";
        d = "Accept";
        e = "Location";
        optifine.HttpPipeline.mapConnections = new HashMap();
    }

    public static byte[] get(String text, Proxy proxy) throws IOException {
        HttpRequest httpRequest = makeRequest(text, proxy);
        HttpResponse httpResponse = executeRequest(httpRequest);

        try {
            if (httpResponse.getStatus() / 100 != 2) {
                throw new IOException("HTTP response: " + httpResponse.getStatus());
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }

        return httpResponse.getBody();
    }
}
