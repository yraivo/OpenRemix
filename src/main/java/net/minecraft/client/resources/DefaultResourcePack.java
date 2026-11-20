package net.minecraft.client.resources;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import optifine.ReflectorForge;

import com.google.common.collect.ImmutableSet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class DefaultResourcePack implements IResourcePack {
    public static Set defaultResourceDomains;
    private static String[] packedTextFragments;

    private Map mapAssets;

    public InputStream getInputStream(ResourceLocation resourceLocation) throws IOException {
        InputStream inputstream = this.getResourceStream(resourceLocation);
        if (inputstream != null) {
            return inputstream;
        } else {
            InputStream inputstream1 = this.getInputStreamAssets(resourceLocation);
            if (inputstream1 != null) {
                return inputstream1;
            } else {
                throw new FileNotFoundException(resourceLocation.getResourcePath());
            }
        }
    }

    public Set getResourceDomains() {
        return net.minecraft.client.resources.DefaultResourcePack.defaultResourceDomains;
    }

    public IMetadataSection getPackMetadata(IMetadataSerializer iMetadataSerializer, String text)
            throws IOException {
        try {
            FileInputStream fileinputstream =
                    new FileInputStream((File) this.mapAssets.get("pack.mcmeta"));
            return AbstractResourcePack.readMetadata(iMetadataSerializer, fileinputstream, text);
        } catch (RuntimeException runtimeexception) {
            return null;
        } catch (FileNotFoundException filenotfoundexception) {
            return null;
        }
    }

    public InputStream getInputStreamAssets(ResourceLocation resourceLocation) throws IOException {
        File file1 = (File) this.mapAssets.get(resourceLocation.toString());
        return file1 != null && file1.isFile() ? new FileInputStream(file1) : null;
    }

    public boolean resourceExists(ResourceLocation resourceLocation) {
        return this.getResourceStream(resourceLocation) != null
                || this.mapAssets.containsKey(resourceLocation.toString());
    }

    public DefaultResourcePack(Map map) {
        this.mapAssets = map;
    }

    static {
        net.minecraft.client.resources.DefaultResourcePack.packedTextFragments = new String[5];
        net.minecraft.client.resources.DefaultResourcePack.packedTextFragments[0] = new String("/");
        net.minecraft.client.resources.DefaultResourcePack.packedTextFragments[1] = new String("/");
        net.minecraft.client.resources.DefaultResourcePack.packedTextFragments[2] = new String("/");
        net.minecraft.client.resources.DefaultResourcePack.defaultResourceDomains = ImmutableSet.of("minecraft", "realms");
    }

    public String getPackName() {
        return "Default";
    }

    public BufferedImage getPackImage() throws IOException {
        return TextureUtil.readBufferedImage(
                DefaultResourcePack.class.getResourceAsStream(
                        packedTextFragments[2]
                                + new ResourceLocation("pack.png").getResourcePath()));
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    private InputStream getResourceStream(ResourceLocation resourceLocation) {
        String text =
                "/assets/"
                        + resourceLocation.getResourceDomain()
                        + packedTextFragments[0]
                        + resourceLocation.getResourcePath();
        InputStream inputstream = ReflectorForge.getOptiFineResourceStream(text);
        return inputstream != null
                ? inputstream
                : DefaultResourcePack.class.getResourceAsStream(
                        "/assets/"
                                + resourceLocation.getResourceDomain()
                                + packedTextFragments[1]
                                + resourceLocation.getResourcePath());
    }
}
