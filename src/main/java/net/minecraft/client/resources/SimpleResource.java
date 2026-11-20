package net.minecraft.client.resources;

import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class SimpleResource implements IResource {
    private JsonObject mcmetaJson;
    private IMetadataSerializer srMetadataSerializer;
    private ResourceLocation srResourceLocation;
    private final Map mapMetadataSections = Maps.newHashMap();
    private String resourcePackName;
    private InputStream mcmetaInputStream;
    private InputStream resourceInputStream;
    private boolean mcmetaJsonChecked;

    public IMetadataSection getMetadata(String text) {
        if (!this.hasMetadata()) {
            return null;
        }

        if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
            this.mcmetaJsonChecked = true;
            BufferedReader bufferedreader = null;

            try {
                bufferedreader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
                this.mcmetaJson = new JsonParser().parse(bufferedreader).getAsJsonObject();
            } finally {
                IOUtils.closeQuietly(bufferedreader);
            }
        }

        IMetadataSection iMetadataSection = (IMetadataSection) this.mapMetadataSections.get(text);
        if (iMetadataSection == null) {
            iMetadataSection = this.srMetadataSerializer.parseMetadataSection(text, this.mcmetaJson);
        }

        return iMetadataSection;
    }

    public String getResourcePackName() {
        return this.resourcePackName;
    }

    public SimpleResource(
            String text,
            ResourceLocation resourceLocation,
            InputStream inputstream,
            InputStream inputstream1,
            IMetadataSerializer aKmValue) {
        this.resourcePackName = text;
        this.srResourceLocation = resourceLocation;
        this.resourceInputStream = inputstream;
        this.mcmetaInputStream = inputstream1;
        this.srMetadataSerializer = aKmValue;
    }

    @Override
    public int hashCode() {
        int i = this.resourcePackName != null ? this.resourcePackName.hashCode() : 0;
        return 31 * i + (this.srResourceLocation != null ? this.srResourceLocation.hashCode() : 0);
    }

    public boolean hasMetadata() {
        return this.mcmetaInputStream != null;
    }

    public InputStream getInputStream() {
        return this.resourceInputStream;
    }

    public ResourceLocation getResourceLocation() {
        return this.srResourceLocation;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof SimpleResource)) {
            return false;
        }

        SimpleResource simpleResource = (SimpleResource) object;
        if (this.srResourceLocation != null) {
            if (!this.srResourceLocation.equals(simpleResource.srResourceLocation)) {
                return false;
            }
        } else if (simpleResource.srResourceLocation != null) {
            return false;
        }

        if (this.resourcePackName != null) {
            if (!this.resourcePackName.equals(simpleResource.resourcePackName)) {
                return false;
            }
        } else if (simpleResource.resourcePackName != null) {
            return false;
        }

        return true;
    }
}
