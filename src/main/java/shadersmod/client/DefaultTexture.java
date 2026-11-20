package shadersmod.client;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.resources.IResourceManager;

public class DefaultTexture extends AbstractTexture {
    public void loadTexture(IResourceManager iResourceManager) {
        int[] aint = shadersmod.client.ShadersTex.createAIntImage(1, -1);
        shadersmod.client.ShadersTex.setupTexture(this.getMultiTexID(), aint, 1, 1, false, false);
    }

    public DefaultTexture() {
        this.loadTexture((net.minecraft.client.resources.IResourceManager) null);
    }
}
