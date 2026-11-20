package optifine;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpPipelineSender extends Thread {

    private static Charset ASCII;
    private HttpPipelineConnection httpPipelineConnection = null;
    private static String b;
    private static String[] packedTextFragments;

    @Override
    public void run() {
        int j = ReflectorMethod.cR();
        HttpPipelineRequest httpPipelineRequest = null;
        int i = j;

        try {
            this.connect();

            while (!Thread.interrupted()) {
                httpPipelineRequest = this.httpPipelineConnection.getNextRequestSend();
                HttpRequest httpRequest = httpPipelineRequest.getHttpRequest();
                OutputStream outputstream = this.httpPipelineConnection.getOutputStream();
                this.writeRequest(httpRequest, outputstream);
                this.httpPipelineConnection.onRequestSent(httpPipelineRequest);
                if (i == 0) {
                    break;
                }
            }
        } catch (InterruptedException interruptedexception) {
            return;
        } catch (Exception exception) {
            this.httpPipelineConnection.method_2fd693ed3597872b1ba3(httpPipelineRequest, exception);
        }
    }

    static {
        optifine.HttpPipelineSender.packedTextFragments = new String[4];
        optifine.HttpPipelineSender.packedTextFragments[0] = new String(" ");
        optifine.HttpPipelineSender.packedTextFragments[1] = new String(" ");
        b = "\r\n";
        optifine.HttpPipelineSender.ASCII = Charset.forName("ASCII");
    }

    private void connect() throws IOException {
        String text = this.httpPipelineConnection.getHost();
        int port = this.httpPipelineConnection.getPort();
        Proxy proxy = this.httpPipelineConnection.getProxy();
        Socket socket = new Socket(proxy);
        socket.connect(new InetSocketAddress(text, port), 5000);
        this.httpPipelineConnection.setSocket(socket);
    }

    private void writeRequest(HttpRequest httpRequest, OutputStream outputstream)
            throws IOException {
        int j = ReflectorMethod.cR();
        this.write(
                outputstream,
                httpRequest.getMethod()
                        + packedTextFragments[0]
                        + httpRequest.getFile()
                        + packedTextFragments[1]
                        + httpRequest.getHttp()
                        + "\r\n");
        int i = j;
        Map map = httpRequest.getHeaders();

        for (java.util.Iterator<?> __recoveryIterator171_27513 = (map.keySet()).iterator();
                (__recoveryIterator171_27513).hasNext(); ) {
            String text = (String) (__recoveryIterator171_27513).next();
            String textValue = (String) httpRequest.getHeaders().get(text);
            this.write(outputstream, text + ": " + textValue + "\r\n");
            if (i == 0) {
                break;
            }
        }

        this.write(outputstream, "\r\n");
    }

    public HttpPipelineSender(HttpPipelineConnection httpPipelineConnectionValue) {
        super("HttpPipelineSender");
        this.httpPipelineConnection = httpPipelineConnectionValue;
    }

    private void write(OutputStream outputstream, String text) throws IOException {
        byte[] abyte = text.getBytes(optifine.HttpPipelineSender.ASCII);
        outputstream.write(abyte);
    }
}
