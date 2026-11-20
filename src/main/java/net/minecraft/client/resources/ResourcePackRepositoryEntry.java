package net.minecraft.client.resources;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.IOUtils;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;

public class ResourcePackRepositoryEntry {
    private File resourcePackFile;
    private ResourceLocation locationTexturePackIcon;
    private BufferedImage texturePackIcon;
    private PackMetadataSection rePackMetadataSection;
    private IResourcePack reResourcePack;

    ResourcePackRepository repository;

    public String getResourcePackName() {
        return this.reResourcePack.getPackName();
    }


    public String getTexturePackDescription() {
        return this.rePackMetadataSection == null
                ? EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing 'pack' section)"
                : this.rePackMetadataSection.getPackDescription().getFormattedText();
    }

    public void loadPackMetadataAndIcon() throws IOException {
        this.reResourcePack = this.resourcePackFile.isDirectory()
                ? new FolderResourcePack(this.resourcePackFile) : new FileResourcePack(this.resourcePackFile);
        this.rePackMetadataSection = (PackMetadataSection)
                this.reResourcePack.getPackMetadata(this.repository.rprMetadataSerializer, "pack");
        try {
            this.texturePackIcon = this.reResourcePack.getPackImage();
        } catch (IOException missingIcon) {
            // Packs without an icon use the default image.
        }
        if (this.texturePackIcon == null) {
            this.texturePackIcon = this.repository.rprDefaultResourcePack.getPackImage();
        }
        this.closeResourcePack();
    }

    @Override
    public boolean equals(Object object) {
        return this == object
                ? true
                : (object instanceof ResourcePackRepositoryEntry
                        ? this.toString().equals(object.toString())
                        : false);
    }

    @Override
    public String toString() {
        return String.format(
                "%s:%s:%d",
                this.resourcePackFile.getName(),
                this.resourcePackFile.isDirectory() ? "folder" : "zip",
                this.resourcePackFile.lastModified());
    }

    public void bindTexturePackIcon(TextureManager textureManager) {
        if (this.locationTexturePackIcon == null) {
            this.locationTexturePackIcon =
                    textureManager.getDynamicTextureLocation(
                            "texturepackicon", new DynamicTexture(this.texturePackIcon));
        }

        textureManager.bindTexture(this.locationTexturePackIcon);
    }

    public void closeResourcePack() {
        if (this.reResourcePack instanceof Closeable) {
            IOUtils.closeQuietly((Closeable) this.reResourcePack);
        }
    }

    public IResourcePack getResourcePack() {
        return this.reResourcePack;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    private ResourcePackRepositoryEntry(ResourcePackRepository aKDValue, File file1) {
        this.repository = aKDValue;
        this.resourcePackFile = file1;
    }

    ResourcePackRepositoryEntry(
            ResourcePackRepository resourcePackRepository, File file1, Object object) {
        this(resourcePackRepository, file1);
    }
}
