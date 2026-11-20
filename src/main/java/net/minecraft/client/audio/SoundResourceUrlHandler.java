package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.audio.SoundManagerOpenConnectionURLConnection;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public final class SoundResourceUrlHandler extends URLStreamHandler {
    public ResourceLocation field_148592_a;

    @Override
    protected URLConnection openConnection(URL url) {
        return new SoundManagerOpenConnectionURLConnection(this, url);
    }

    SoundResourceUrlHandler(ResourceLocation resourceLocation) {
        this.field_148592_a = resourceLocation;
    }
}
