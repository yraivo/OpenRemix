package net.minecraft.client.resources;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class AbstractResourcePack implements IResourcePack {

    public static final int c = 64;

    public File resourcePackFile;
    private static Logger resourceLog;

    private static String locationToName(ResourceLocation resourceLocation) {
        return String.format(
                "%s/%s/%s",
                "assets", resourceLocation.getResourceDomain(), resourceLocation.getResourcePath());
    }

    protected abstract boolean hasResourceName(String text);

    public static BufferedImage method_063e0c42d843c32deba2(BufferedImage bufferedimage)
            throws IOException {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (bufferedimage == null) {
                return null;
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
        }

        BufferedImage bufferedimage1 = new BufferedImage(64, 64, 2);
        Graphics graphics = bufferedimage1.getGraphics();
        graphics.drawImage(bufferedimage, 0, 0, 64, 64, null);
        graphics.dispose();
        return bufferedimage1;
    }

    public IMetadataSection getPackMetadata(IMetadataSerializer iMetadataSerializer, String text)
            throws IOException {
        return readMetadata(iMetadataSerializer, this.getInputStreamByName("pack.mcmeta"), text);
    }

    static IMetadataSection readMetadata(
            IMetadataSerializer iMetadataSerializer, InputStream inputstream, String text) {
        JsonObject jsonobject = null;
        BufferedReader bufferedreader = null;

        try {
            bufferedreader = new BufferedReader(new InputStreamReader(inputstream, Charsets.UTF_8));
            jsonobject = new JsonParser().parse(bufferedreader).getAsJsonObject();
        } catch (RuntimeException runtimeexception) {
            throw new JsonParseException(runtimeexception);
        } finally {
            IOUtils.closeQuietly(bufferedreader);
        }

        return iMetadataSerializer.parseMetadataSection(text, jsonobject);
    }

    protected static String getRelativeName(File file1, File file2) {
        return file1.toURI().relativize(file2.toURI()).getPath();
    }

    static {
        net.minecraft.client.resources.AbstractResourcePack.resourceLog = LogManager.getLogger();
    }

    protected abstract InputStream getInputStreamByName(String text) throws IOException;

    protected void logNameNotLowercase(String text) {
        net.minecraft.client.resources.AbstractResourcePack.resourceLog.warn(
                "ResourcePack: ignored non-lowercase namespace: {} in {}",
                new Object[] {text, this.resourcePackFile});
    }

    public BufferedImage getPackImage() throws IOException {
        return method_063e0c42d843c32deba2(
                TextureUtil.readBufferedImage(this.getInputStreamByName("pack.png")));
    }

    public boolean resourceExists(ResourceLocation resourceLocation) {
        return this.hasResourceName(locationToName(resourceLocation));
    }

    public AbstractResourcePack(File file1) {
        this.resourcePackFile = file1;
    }

    public InputStream getInputStream(ResourceLocation resourceLocation) throws IOException {
        return this.getInputStreamByName(locationToName(resourceLocation));
    }

    public String getPackName() {
        return this.resourcePackFile.getName();
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }
}
