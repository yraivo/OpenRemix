package optifine;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;

final class TextureResourceReloadListener implements IResourceManagerReloadListener {
    public void onResourceManagerReload(IResourceManager iResourceManager) {
        TextureUtils.resourcesReloaded(iResourceManager);
    }
}
