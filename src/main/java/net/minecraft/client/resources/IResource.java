package net.minecraft.client.resources;

import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;

import java.io.InputStream;

public interface IResource {
    String getResourcePackName();

    InputStream getInputStream();

    ResourceLocation getResourceLocation();

    boolean hasMetadata();

    IMetadataSection getMetadata(String text);
}
