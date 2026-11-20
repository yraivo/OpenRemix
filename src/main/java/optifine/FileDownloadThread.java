package optifine;

import net.minecraft.client.MinecraftClient;

public class FileDownloadThread extends Thread {
    private String urlString = null;
    private IFileDownloadListener listener = null;

    @Override
    public void run() {
        try {
            byte[] abyte = HttpPipeline.get(this.urlString, MinecraftClient.getMinecraft().getProxy());
            this.listener.fileDownloadFinished(this.urlString, abyte, (Throwable) null);
        } catch (Exception exception) {
            this.listener.fileDownloadFinished(this.urlString, (byte[]) null, exception);
        }
    }

    public FileDownloadThread(
            String text, IFileDownloadListener listenerValue) {
        this.urlString = text;
        this.listener = listenerValue;
    }

    public String getUrlString() {
        return this.urlString;
    }

    public IFileDownloadListener getListener() {
        return this.listener;
    }
}
