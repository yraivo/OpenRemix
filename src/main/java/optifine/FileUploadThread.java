package optifine;

import java.util.Map;

public class FileUploadThread extends Thread {
    private Map headers;
    private IFileUploadListener listener;
    private String urlString;
    private byte[] content;

    @Override
    public void run() {
        this.listener.fileUploadFinished(
                this.urlString, this.content, new java.io.IOException("Automatic crash upload is disabled"));
    }

    public FileUploadThread(
            String text,
            Map map,
            byte[] abyte,
            IFileUploadListener listenerValue) {
        this.urlString = text;
        this.headers = map;
        this.content = abyte;
        this.listener = listenerValue;
    }

    public byte[] getContent() {
        return this.content;
    }

    public IFileUploadListener getListener() {
        return this.listener;
    }

    public String getUrlString() {
        return this.urlString;
    }
}
