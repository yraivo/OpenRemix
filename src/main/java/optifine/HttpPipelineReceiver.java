package optifine;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;

public class HttpPipelineReceiver extends Thread {
    private HttpPipelineConnection httpPipelineConnection = null;
    private static final char Kb = '\r';

    private static String HEADER_CONTENT_LENGTH;
    private static String[] packedTextFragments;

    private static final char Kd = '\n';
    private static Charset ASCII;

    static {
        optifine.HttpPipelineReceiver.packedTextFragments = new String[4];
        optifine.HttpPipelineReceiver.packedTextFragments[0] = new String(" ");
        optifine.HttpPipelineReceiver.packedTextFragments[1] = new String(":");
        optifine.HttpPipelineReceiver.HEADER_CONTENT_LENGTH = "Content-Length";
        optifine.HttpPipelineReceiver.ASCII = Charset.forName("ASCII");
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    private byte[] readContentChunked(InputStream inputstream) throws IOException {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();

        int i;
        do {
            String text = this.readLine(inputstream);
            String[] astring = OptiFineConfig.tokenize(text, "; ");
            i = Integer.parseInt(astring[0], 16);
            byte[] abyte = new byte[i];
            this.readFull(abyte, inputstream);
            bytearrayoutputstream.write(abyte);
            this.readLine(inputstream);
        } while (i != 0);

        return bytearrayoutputstream.toByteArray();
    }

    private void readFull(byte[] abyte, InputStream inputstream)
            throws IOException {
        int l = ReflectorMethod.cR();
        int k = 0;
        int i = l;

        while (k < abyte.length) {
            int j = inputstream.read(abyte, k, abyte.length - k);
            if (j < 0) {
                throw new EOFException();
            }

            k += j;
            if (i == 0) {
                break;
            }
        }
    }

    private String readLine(InputStream inputstream) throws IOException {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        int l = ReflectorMethod.f();
        int j = -1;
        int i = l;
        byte b0 = 0;

        do {
            int k = inputstream.read();
            if (k < 0 && i == 0) {
                break;
            }

            bytearrayoutputstream.write(k);
            if (j == 13 && k == 10) {
                b0 = 1;
                if (i == 0) {
                    break;
                }
            }

            j = k;
        } while (i == 0);

        byte[] abyte = bytearrayoutputstream.toByteArray();
        String text = new String(abyte, optifine.HttpPipelineReceiver.ASCII);
        if (b0 != 0) {
            text = text.substring(0, text.length() - 2);
        }

        return text;
    }

    @Override
    public void run() {
        int i = ReflectorMethod.cR();

        while (!Thread.interrupted()) {
            HttpPipelineRequest httpPipelineRequest = null;

            try {
                httpPipelineRequest = this.httpPipelineConnection.getNextRequestReceive();
                InputStream inputstream = this.httpPipelineConnection.getInputStream();
                HttpResponse httpResponse = this.readResponse(inputstream);
                this.httpPipelineConnection.onResponseReceived(
                        httpPipelineRequest, httpResponse);
            } catch (InterruptedException interruptedexception) {
                return;
            } catch (Exception exception) {
                this.httpPipelineConnection.method_b6baa23f5475979d1ae8(
                        httpPipelineRequest, exception);
            }

            if (i == 0) {
                break;
            }
        }
    }

    public HttpPipelineReceiver(HttpPipelineConnection httpPipelineConnectionValue) {
        super("HttpPipelineReceiver");
        this.httpPipelineConnection = httpPipelineConnectionValue;
    }

    private HttpResponse readResponse(InputStream inputstream) throws IOException {
        String text = this.readLine(inputstream);
        String[] astring = OptiFineConfig.tokenize(text, packedTextFragments[0]);
        if (astring.length < 3) {
            throw new IOException("Invalid status line: " + text);
        }

        String textValue = astring[0];
        int i = OptiFineConfig.parseInt(astring[1], 0);
        String textValue2 = astring[2];
        LinkedHashMap linkedhashmap = new LinkedHashMap();

        while (true) {
            String textValue3 = this.readLine(inputstream);
            if (textValue3.length() <= 0) {
                byte[] abyte = null;
                String textValue4 = (String) linkedhashmap.get("Content-Length");
                if (textValue4 != null) {
                    int k = OptiFineConfig.parseInt(textValue4, -1);
                    if (k > 0) {
                        abyte = new byte[k];
                        this.readFull(abyte, inputstream);
                    }
                } else {
                    String textValue5 = (String) linkedhashmap.get("Transfer-Encoding");
                    if (OptiFineConfig.equals(textValue5, "chunked")) {
                        abyte = this.readContentChunked(inputstream);
                    }
                }

                return new HttpResponse(i, text, linkedhashmap, abyte);
            }

            int j = textValue3.indexOf(packedTextFragments[1]);
            if (j > 0) {
                String textValue6 = textValue3.substring(0, j).trim();
                String textValue7 = textValue3.substring(j + 1).trim();
                linkedhashmap.put(textValue6, textValue7);
            }
        }
    }
}
