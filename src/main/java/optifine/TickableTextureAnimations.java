package optifine;

import net.minecraft.client.renderer.texture.ITickableTextureObject;
import net.minecraft.client.resources.IResourceManager;
import shadersmod.client.MultiTexID;

import java.io.IOException;

final class TickableTextureAnimations implements ITickableTextureObject {
    public int getGlTextureId() {
        return 0;
    }

    public void loadTexture(IResourceManager iResourceManager) throws IOException {}

    public void setBlurMipmap(boolean flag, boolean flag1) {}

    public void tick() {
        TextureAnimations.updateCustomAnimations();
    }

    public void restoreLastBlurMipmap() {}

    public MultiTexID getMultiTexID() {
        return null;
    }
}
