package net.minecraft.client.resources;

import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public interface IResourcePack {
    BufferedImage getPackImage() throws IOException;

    InputStream getInputStream(ResourceLocation resourceLocation) throws IOException;

    Set getResourceDomains();

    IMetadataSection getPackMetadata(IMetadataSerializer iMetadataSerializer, String text)
            throws IOException;

    boolean resourceExists(ResourceLocation resourceLocation);

    String getPackName();
}
