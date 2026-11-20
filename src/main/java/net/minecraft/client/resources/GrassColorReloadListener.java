package net.minecraft.client.resources;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerGrass;

import java.io.IOException;

public class GrassColorReloadListener implements IResourceManagerReloadListener {
    private static ResourceLocation LOC_GRASS_PNG;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.resources.GrassColorReloadListener.LOC_GRASS_PNG = new ResourceLocation("textures/colormap/grass.png");
    }

    public void onResourceManagerReload(IResourceManager iResourceManager) {
        try {
            ColorizerGrass.setGrassBiomeColorizer(
                    TextureUtil.readImageData(iResourceManager, net.minecraft.client.resources.GrassColorReloadListener.LOC_GRASS_PNG));
        } catch (IOException ioexception) {
        }
    }
}
