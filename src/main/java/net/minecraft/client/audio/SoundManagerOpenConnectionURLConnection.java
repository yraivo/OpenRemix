package net.minecraft.client.audio;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.audio.SoundResourceUrlHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class SoundManagerOpenConnectionURLConnection extends URLConnection {
    SoundResourceUrlHandler aOL;

    @Override
    public InputStream getInputStream() throws IOException {
        return MinecraftClient.getMinecraft()
                .getResourceManager()
                .getResource(this.aOL.field_148592_a)
                .getInputStream();
    }

    public SoundManagerOpenConnectionURLConnection(SoundResourceUrlHandler aOLValue, URL url) {
        super(url);
        this.aOL = aOLValue;
    }

    @Override
    public void connect() {}
}
