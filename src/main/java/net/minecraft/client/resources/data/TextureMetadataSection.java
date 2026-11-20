package net.minecraft.client.resources.data;

import java.util.Collections;
import java.util.List;

public class TextureMetadataSection implements IMetadataSection {
    private boolean textureClamp;
    private boolean textureBlur;
    private List listMipmaps;

    public List getListMipmaps() {
        return Collections.unmodifiableList(this.listMipmaps);
    }

    public boolean getTextureClamp() {
        return this.textureClamp;
    }

    public boolean getTextureBlur() {
        return this.textureBlur;
    }

    public TextureMetadataSection(boolean flag, boolean flag1, List list) {
        this.textureBlur = flag;
        this.textureClamp = flag1;
        this.listMipmaps = list;
    }
}
