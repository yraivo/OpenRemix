package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.IResourceManager;
import shadersmod.client.MultiTexID;

import java.io.IOException;

public interface ITextureObject {
    void setBlurMipmap(boolean flag, boolean flag1);

    void restoreLastBlurMipmap();

    void loadTexture(IResourceManager iResourceManager) throws IOException;

    int getGlTextureId();

    MultiTexID getMultiTexID();
}
