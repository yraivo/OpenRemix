package net.minecraft.client.resources;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerFoliage;

import java.io.IOException;

public class FoliageColorReloadListener implements IResourceManagerReloadListener {
    private static ResourceLocation LOC_FOLIAGE_PNG;

    static {
        net.minecraft.client.resources.FoliageColorReloadListener.LOC_FOLIAGE_PNG = new ResourceLocation("textures/colormap/foliage.png");
    }

    // region String decoding support

    // endregion

    public void onResourceManagerReload(IResourceManager iResourceManager) {
        try {
            ColorizerFoliage.setFoliageBiomeColorizer(
                    TextureUtil.readImageData(iResourceManager, net.minecraft.client.resources.FoliageColorReloadListener.LOC_FOLIAGE_PNG));
        } catch (IOException ioexception) {
        }
    }

    // region String decoding support

    // endregion
}
