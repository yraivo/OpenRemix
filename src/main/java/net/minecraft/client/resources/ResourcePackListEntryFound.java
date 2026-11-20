package net.minecraft.client.resources;

import net.minecraft.client.gui.GuiScreenResourcePacks;

public class ResourcePackListEntryFound extends ResourcePackListEntry {
    private ResourcePackRepositoryEntry aKl;

    protected void cj() {
        this.aKl.bindTexturePackIcon(this.mc.getTextureManager());
    }

    protected String method_c60693f457aa70be7160() {
        return this.aKl.getResourcePackName();
    }

    public ResourcePackRepositoryEntry BV() {
        return this.aKl;
    }

    public ResourcePackListEntryFound(
            GuiScreenResourcePacks guiScreenResourcePacks, ResourcePackRepositoryEntry aKlValue) {
        super(guiScreenResourcePacks);
        this.aKl = aKlValue;
    }

    protected String method_e0e3092a59fee58fae52() {
        return this.aKl.getTexturePackDescription();
    }
}
