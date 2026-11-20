package net.minecraft.client.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import optifine.HttpPipeline;
import optifine.HttpRequest;
import optifine.HttpResponse;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

public class ThreadDownloadImageData extends SimpleTexture {
    private static AtomicInteger threadDownloadCounter;
    private String imageUrl;

    private BufferedImage bufferedImage;
    private boolean textureUploaded;
    private Thread imageThread;
    public boolean pipeline;
    private static Logger logger;
    private File cacheFile;
    public Boolean imageFound = null;

    private IImageBuffer imageBuffer;

    public ThreadDownloadImageData(
            File file1, String text, ResourceLocation resourceLocation, IImageBuffer aGFValue) {
        super(resourceLocation);
        this.pipeline = false;
        this.cacheFile = file1;
        this.imageUrl = text;
        this.imageBuffer = aGFValue;
    }

    protected void loadTextureFromServer() {
        this.imageThread =
                new HttpTextureDownloadThread(
                        this,
                        "Texture Downloader #"
                                + net.minecraft.client.renderer.ThreadDownloadImageData.threadDownloadCounter
                                        .incrementAndGet());
        this.imageThread.setDaemon(true);
        this.imageThread.start();
    }

    static IImageBuffer access$300(ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.imageBuffer;
    }

    static BufferedImage accessBufferedImage(ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.bufferedImage;
    }

    static {
        net.minecraft.client.renderer.ThreadDownloadImageData.logger = LogManager.getLogger();
        net.minecraft.client.renderer.ThreadDownloadImageData.threadDownloadCounter = new AtomicInteger(0);
    }

    private void loadPipelined() {
        int i = WorldRenderer.getLegacyRenderFlowToken();

        try {
            HttpRequest httpRequest =
                    HttpPipeline.makeRequest(
                            this.imageUrl, MinecraftClient.getMinecraft().getProxy());
            HttpResponse httpResponse = HttpPipeline.executeRequest(httpRequest);
            if (httpResponse.getStatus() / 100 == 2) {
                BufferedImage bufferedimage;
                {
                    byte[] abyte = httpResponse.getBody();
                    ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte);
                    if (this.cacheFile != null) {
                        FileUtils.copyInputStreamToFile(bytearrayinputstream, this.cacheFile);
                        bufferedimage = ImageIO.read(this.cacheFile);
                        if (!(i == 0)) {

                            bufferedimage = TextureUtil.readBufferedImage(bytearrayinputstream);
                        }
                    } else {

                        bufferedimage = TextureUtil.readBufferedImage(bytearrayinputstream);
                    }
                }

                if (this.imageBuffer != null) {
                    bufferedimage = this.imageBuffer.parseUserSkin(bufferedimage);
                }

                this.setBufferedImage(bufferedimage);
                return;
            }
        } catch (Exception exception) {
            net.minecraft.client.renderer.ThreadDownloadImageData.logger.error(
                    "Couldn't download http texture: "
                            + exception.getClass().getName()
                            + ": "
                            + exception.getMessage());
            return;
        } finally {
            this.imageFound = this.bufferedImage != null;
        }
    }

    public void loadTexture(IResourceManager iResourceManager) throws IOException {
        int i = WorldRenderer.getLegacyRenderFlowMarker();
        if (this.bufferedImage == null && this.textureLocation != null) {
            super.loadTexture(iResourceManager);
        }

        if (this.imageThread == null) {
            if (this.cacheFile != null && this.cacheFile.isFile()) {
                net.minecraft.client.renderer.ThreadDownloadImageData.logger.debug(
                        "Loading http texture from local cache ({})",
                        new Object[] {this.cacheFile});

                try {
                    this.bufferedImage = ImageIO.read(this.cacheFile);
                    if (this.imageBuffer != null) {
                        this.setBufferedImage(
                                this.imageBuffer.parseUserSkin(this.bufferedImage));
                    }

                    this.imageFound = this.bufferedImage != null;
                    return;
                } catch (IOException ioexception) {
                    net.minecraft.client.renderer.ThreadDownloadImageData.logger.error(
                            "Couldn't load skin " + this.cacheFile, ioexception);
                    this.loadTextureFromServer();
                    if (i != 0) {
                        return;
                    }
                }
            }

            this.loadTextureFromServer();
        }
    }

    static String access$000(ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.imageUrl;
    }

    private boolean shouldPipeline() {
        if (!this.pipeline) {
            return false;
        }

        Proxy proxy = MinecraftClient.getMinecraft().getProxy();
        return proxy.type() != Type.DIRECT && proxy.type() != Type.SOCKS
                ? false
                : this.imageUrl.startsWith("http://");
    }

    static void accessLoadPipelined(ThreadDownloadImageData threadDownloadImageData) {
        threadDownloadImageData.loadPipelined();
    }

    static File access$100(ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.cacheFile;
    }

    public int getGlTextureId() {
        this.checkTextureUploaded();
        return super.getGlTextureId();
    }

    private void checkTextureUploaded() {
        if (!this.textureUploaded && this.bufferedImage != null) {
            this.textureUploaded = true;
            if (this.textureLocation != null) {
                this.cj();
            }

            TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
        }
    }

    static boolean accessShouldPipeline(ThreadDownloadImageData threadDownloadImageData) {
        return threadDownloadImageData.shouldPipeline();
    }

    static Logger access$200() {
        return net.minecraft.client.renderer.ThreadDownloadImageData.logger;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public void setBufferedImage(BufferedImage bufferedimage) {
        this.bufferedImage = bufferedimage;
        if (this.imageBuffer != null) {
            this.imageBuffer.skinAvailable();
        }

        this.imageFound = this.bufferedImage != null;
    }
}
