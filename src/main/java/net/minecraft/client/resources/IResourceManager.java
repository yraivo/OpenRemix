package net.minecraft.client.resources;

import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface IResourceManager {
    Set getResourceDomains();

    IResource getResource(ResourceLocation resourceLocation) throws IOException;

    List getAllResources(ResourceLocation resourceLocation) throws IOException;
}
