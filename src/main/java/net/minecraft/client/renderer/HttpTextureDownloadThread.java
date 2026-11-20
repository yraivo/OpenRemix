package net.minecraft.client.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.texture.TextureUtil;
import optifine.OptiFineConfig;

import org.apache.commons.io.FileUtils;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

class HttpTextureDownloadThread extends Thread {
    ThreadDownloadImageData aOF;

    @Override
    public void run() {
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        HttpURLConnection httpurlconnection = null;
        int i = j;
        ThreadDownloadImageData.access$200()
                .debug(
                        "Downloading http texture from {} to {}",
                        new Object[] {
                            ThreadDownloadImageData.access$000(this.aOF),
                            ThreadDownloadImageData.access$100(this.aOF)
                        });
        if (ThreadDownloadImageData.accessShouldPipeline(this.aOF)) {
            ThreadDownloadImageData.accessLoadPipelined(this.aOF);
            if (i != 0) {
                return;
            }
        }

        try {
            httpurlconnection =
                    (HttpURLConnection)
                            new URL(ThreadDownloadImageData.access$000(this.aOF))
                                    .openConnection(MinecraftClient.getMinecraft().getProxy());
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(false);
            httpurlconnection.connect();
            if (httpurlconnection.getResponseCode() / 100 == 2) {
                BufferedImage bufferedimage;
                {
                    if (ThreadDownloadImageData.access$100(this.aOF) != null) {
                        FileUtils.copyInputStreamToFile(
                                httpurlconnection.getInputStream(),
                                ThreadDownloadImageData.access$100(this.aOF));
                        bufferedimage = ImageIO.read(ThreadDownloadImageData.access$100(this.aOF));
                        if (!(i != 0)) {

                            bufferedimage =
                                    TextureUtil.readBufferedImage(
                                            httpurlconnection.getInputStream());
                        }
                    } else {

                        bufferedimage =
                                TextureUtil.readBufferedImage(
                                        httpurlconnection.getInputStream());
                    }
                }

                if (ThreadDownloadImageData.access$300(this.aOF) != null) {
                    bufferedimage =
                            ThreadDownloadImageData.access$300(this.aOF)
                                    .parseUserSkin(bufferedimage);
                }

                this.aOF.setBufferedImage(bufferedimage);
                return;
            }

            if (httpurlconnection.getErrorStream() != null) {
                OptiFineConfig.readAll(httpurlconnection.getErrorStream());
            }

            return;
        } catch (Exception exception) {
            ThreadDownloadImageData.access$200()
                    .error(
                            "Couldn't download http texture: "
                                    + exception.getClass().getName()
                                    + ": "
                                    + exception.getMessage());
        } finally {
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
            }

            this.aOF.imageFound = ThreadDownloadImageData.accessBufferedImage(this.aOF) != null;
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    HttpTextureDownloadThread(ThreadDownloadImageData aOFValue, String text) {
        super(text);
        this.aOF = aOFValue;
    }


}
