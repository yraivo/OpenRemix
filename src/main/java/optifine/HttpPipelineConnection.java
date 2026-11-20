package optifine;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class HttpPipelineConnection {
    private HttpPipelineSender httpPipelineSender;
    private static Pattern patternFullUrl;
    private List<HttpPipelineRequest> listRequestsSend;
    private OutputStream outputStream;
    private HttpPipelineReceiver httpPipelineReceiver;
    private static final String Ba;
    private boolean responseReceived;
    private String host = null;
    private InputStream inputStream;
    private int countRequests;
    private int port = 0;
    public static final int jn = 5000;
    private int keepaliveMaxCount;
    private Socket socket;
    private static String[] packedTextFragments;

    private List<HttpPipelineRequest> listRequests;
    private boolean terminated;
    private long keepaliveTimeoutMs;
    public static final int nO = 5000;

    private long timeLastActivityMs;
    private Proxy proxy = Proxy.NO_PROXY;

    private HttpPipelineRequest getNextRequest(
            List<HttpPipelineRequest> httpPipelineRequestList, boolean flag)
            throws InterruptedException {
        int i = ReflectorMethod.cR();

        while (httpPipelineRequestList.size() <= 0) {
            this.checkTimeout();

            this.wait(1000L);
            if (i == 0) {
                break;
            }
        }

        this.onActivity();
        return flag
                ? (HttpPipelineRequest) httpPipelineRequestList.remove(0)
                : (HttpPipelineRequest) httpPipelineRequestList.get(0);
    }

    private void terminateRequests(Exception exception) {
        int i = ReflectorMethod.cR();
        if (this.listRequests.size() > 0) {
            if (!this.responseReceived) {
                HttpPipelineRequest httpPipelineRequest = this.listRequests.remove(0);
                httpPipelineRequest
                        .getHttpListener()
                        .failed(httpPipelineRequest.getHttpRequest(), exception);
                httpPipelineRequest.setClosed(true);
            }

            while (this.listRequests.size() > 0) {
                HttpPipelineRequest httpPipelineRequestValue = this.listRequests.remove(0);
                HttpPipeline.addRequest(httpPipelineRequestValue);
                if (i == 0) {
                    break;
                }
            }
        }
    }

    public HttpPipelineConnection(String text, int portValue, Proxy proxy) {
        this.listRequests = new LinkedList<>();
        this.listRequestsSend = new LinkedList<>();
        this.socket = null;
        this.inputStream = null;
        this.outputStream = null;
        this.httpPipelineSender = null;
        this.httpPipelineReceiver = null;
        this.countRequests = 0;
        this.responseReceived = false;

        this.keepaliveTimeoutMs = 5000L;
        this.keepaliveMaxCount = 1000;
        this.timeLastActivityMs = System.currentTimeMillis();
        this.terminated = false;
        this.host = text;
        this.port = portValue;
        this.proxy = proxy;
        this.httpPipelineSender = new HttpPipelineSender(this);
        this.httpPipelineSender.start();
        this.httpPipelineReceiver = new HttpPipelineReceiver(this);
        this.httpPipelineReceiver.start();
    }

    public synchronized boolean addRequest(HttpPipelineRequest httpPipelineRequest) {
        try {
            if (this.fo()) {
                return false;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.HttpPipelineConnection.method_4f1a99df4f683d12dd5b(
                            illegalargumentexception));
        }

        this.addRequest(httpPipelineRequest, this.listRequests);
        this.addRequest(httpPipelineRequest, this.listRequestsSend);
        this.countRequests++;
        return true;
    }

    private synchronized void terminate(Exception exception) {
        if (!this.terminated) {
            this.terminated = true;
            this.terminateRequests(exception);
            if (this.httpPipelineSender != null) {
                this.httpPipelineSender.interrupt();
            }

            if (this.httpPipelineReceiver != null) {
                this.httpPipelineReceiver.interrupt();
            }

            try {
                if (this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException ioexception) {
            }

            this.socket = null;
            this.inputStream = null;
            this.outputStream = null;
        }
    }

    // region String decoding support

    // endregion

    public synchronized void onResponseReceived(
            HttpPipelineRequest httpPipelineRequest, HttpResponse httpResponse) {
        int i = ReflectorMethod.cR();
        if (!this.terminated) {
            this.responseReceived = true;
            this.onActivity();
            if (this.listRequests.size() <= 0 || this.listRequests.get(0) != httpPipelineRequest) {
                throw new IllegalArgumentException("Response out of order: " + httpPipelineRequest);
            }

            label36:
            {
                this.listRequests.remove(0);
                httpPipelineRequest.setClosed(true);
                String text = httpResponse.getHeader("Location");
                if (httpResponse.getStatus() / 100 == 3
                        && text != null
                        && httpPipelineRequest.getHttpRequest().getRedirects() < 5) {
                    try {
                        text = this.normalizeUrl(text, httpPipelineRequest.getHttpRequest());
                        HttpRequest httpRequest =
                                HttpPipeline.makeRequest(
                                        text, httpPipelineRequest.getHttpRequest().getProxy());
                        httpRequest.setRedirects(
                                httpPipelineRequest.getHttpRequest().getRedirects() + 1);
                        HttpPipelineRequest httpPipelineRequestValue =
                                new HttpPipelineRequest(
                                        httpRequest, httpPipelineRequest.getHttpListener());
                        HttpPipeline.addRequest(httpPipelineRequestValue);
                        break label36;
                    } catch (IOException ioexception) {
                        httpPipelineRequest
                                .getHttpListener()
                                .failed(httpPipelineRequest.getHttpRequest(), ioexception);
                        if (i != 0) {
                            break label36;
                        }
                    }
                }

                HttpListener httpListener = httpPipelineRequest.getHttpListener();
                httpListener.finished(httpPipelineRequest.getHttpRequest(), httpResponse);
            }

            this.checkResponseHeader(httpResponse);
            if (i == 0) {
                throw new IllegalArgumentException("Response out of order: " + httpPipelineRequest);
            }
        }
    }

    public synchronized OutputStream getOutputStream() throws IOException, InterruptedException {
        int i = ReflectorMethod.f();

        while (this.outputStream == null) {
            this.checkTimeout();

            this.wait(1000L);
            if (i != 0) {
                break;
            }
        }

        return this.outputStream;
    }

    private String[] split(String text, char p171_1) {
        int i = text.indexOf(p171_1);

        try {
            if (i < 0) {
                return new java.lang.String[] {text};
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.HttpPipelineConnection.method_4f1a99df4f683d12dd5b(
                            illegalargumentexception));
        }

        java.lang.String textValue = text.substring(0, i);
        java.lang.String textValue2 = text.substring(i + 1);
        return new java.lang.String[] {textValue, textValue2};
    }

    static {
        optifine.HttpPipelineConnection.packedTextFragments = new String[7];
        optifine.HttpPipelineConnection.packedTextFragments[0] = new String(":");
        optifine.HttpPipelineConnection.packedTextFragments[1] = new String("/");
        optifine.HttpPipelineConnection.packedTextFragments[2] = new String("/");
        optifine.HttpPipelineConnection.packedTextFragments[3] = new String("/");
        optifine.HttpPipelineConnection.packedTextFragments[4] = new String("\n");
        Ba = HttpPipelineConnection.packedTextFragments[4];
        optifine.HttpPipelineConnection.patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");
    }

    public HttpPipelineConnection(String text, int p171_1) {
        this(text, p171_1, java.net.Proxy.NO_PROXY);
    }

    public String getHost() {
        return this.host;
    }

    public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException {
        return this.getNextRequest(this.listRequests, false);
    }

    public synchronized boolean fo() {
        try {
            if (this.terminated) {
                return true;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.HttpPipelineConnection.method_4f1a99df4f683d12dd5b(
                            illegalargumentexception1));
        }

        try {
            if (this.countRequests >= this.keepaliveMaxCount) {
                return true;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.HttpPipelineConnection.method_4f1a99df4f683d12dd5b(
                            illegalargumentexception));
        }

        return false;
    }

    private void addRequest(
            HttpPipelineRequest httpPipelineRequest,
            List<HttpPipelineRequest> httpPipelineRequestList) {
        httpPipelineRequestList.add(httpPipelineRequest);
        this.notifyAll();
    }

    private String normalizeUrl(String text, HttpRequest httpRequest) {
        if (optifine.HttpPipelineConnection.patternFullUrl.matcher(text).matches()) {
            return text;
        }

        if (text.startsWith("//")) {
            return "http:" + text;
        }

        String textValue = httpRequest.getHost();
        if (httpRequest.getPort() != 80) {
            textValue = textValue + packedTextFragments[0] + httpRequest.getPort();
        }

        if (text.startsWith(packedTextFragments[1])) {
            return "http://" + textValue + text;
        }

        String textValue2 = httpRequest.getFile();
        int i = textValue2.lastIndexOf(packedTextFragments[2]);
        return i >= 0
                ? "http://" + textValue + textValue2.substring(0, i + 1) + text
                : "http://" + textValue + packedTextFragments[3] + text;
    }

    private void checkResponseHeader(HttpResponse httpResponse) {
        int i1 = ReflectorMethod.f();
        String text = httpResponse.getHeader("Connection");
        int i = i1;
        if (text != null && !text.toLowerCase().equals("keep-alive")) {
            this.terminate(new EOFException("Connection not keep-alive"));
        }

        String textValue = httpResponse.getHeader("Keep-Alive");
        if (textValue != null) {
            String[] astring = OptiFineConfig.tokenize(textValue, ",;");
            int j = 0;

            while (j < astring.length) {
                String textValue2 = astring[j];
                String[] astring1 = this.split(textValue2, '=');
                if (astring1.length >= 2) {
                    if (astring1[0].equals("timeout")) {
                        int k = OptiFineConfig.parseInt(astring1[1], -1);
                        if (k > 0) {
                            this.keepaliveTimeoutMs = k * 1000;
                        }
                    }

                    if (astring1[0].equals("max")) {
                        int l = OptiFineConfig.parseInt(astring1[1], -1);
                        if (l > 0) {
                            this.keepaliveMaxCount = l;
                        }
                    }
                }

                j++;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    public synchronized HttpPipelineRequest getNextRequestSend()
            throws InterruptedException, IOException {
        if (((int) (((java.util.List) (this.listRequestsSend)).size())) <= 0
                && this.outputStream != null) {
            ((java.io.OutputStream) (this.outputStream)).flush();
        }

        return this.getNextRequest(this.listRequestsSend, true);
    }

    public int getCountRequests() {
        return this.countRequests;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public synchronized void method_2fd693ed3597872b1ba3(
            HttpPipelineRequest httpPipelineRequest, Exception exception) {
        this.terminate(exception);
    }

    private static Exception method_4f1a99df4f683d12dd5b(Exception exception) {
        return exception;
    }

    private void checkTimeout() {
        if (this.socket != null) {
            long keepaliveTimeoutMsValue = this.keepaliveTimeoutMs;
            if (this.listRequests.size() > 0) {

                keepaliveTimeoutMsValue = 5000L;
            }

            long j = System.currentTimeMillis();
            if (j > this.timeLastActivityMs + keepaliveTimeoutMsValue) {
                this.terminate(new InterruptedException("Timeout " + keepaliveTimeoutMsValue));
            }
        }
    }

    public synchronized void onRequestSent(HttpPipelineRequest httpPipelineRequest) {
        try {
            if (!this.terminated) {
                this.onActivity();
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.HttpPipelineConnection.method_4f1a99df4f683d12dd5b(
                            illegalargumentexception));
        }
    }

    // region String decoding support

    // endregion

    public int getPort() {
        return this.port;
    }

    public synchronized boolean aR() {
        try {
            if (this.listRequests.size() > 0) {
                return true;
            }
        } catch (java.lang.IllegalArgumentException illegalargumentexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    optifine.HttpPipelineConnection.method_4f1a99df4f683d12dd5b(
                            illegalargumentexception));
        }

        return false;
    }

    public synchronized void method_b6baa23f5475979d1ae8(
            HttpPipelineRequest httpPipelineRequest, Exception exception) {
        this.terminate(exception);
    }

    private void onActivity() {
        this.timeLastActivityMs = System.currentTimeMillis();
    }

    public synchronized InputStream getInputStream() throws IOException, InterruptedException {
        int i = ReflectorMethod.cR();

        while (this.inputStream == null) {
            this.checkTimeout();

            this.wait(1000L);
            if (i == 0) {
                break;
            }
        }

        return this.inputStream;
    }

    public synchronized void setSocket(Socket socket) throws IOException {
        if (!this.terminated) {
            if (this.socket != null) {
                throw new IllegalArgumentException("Already connected");
            }

            this.socket = socket;
            this.socket.setTcpNoDelay(true);
            this.inputStream = this.socket.getInputStream();
            this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
            this.onActivity();
            this.notifyAll();
        }
    }
}
