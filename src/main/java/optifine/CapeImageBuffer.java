package optifine;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;

final class CapeImageBuffer implements IImageBuffer {
    AbstractClientPlayer BB;
    ImageBufferDownload imageBufferDownload;
    ResourceLocation resourceLocation;

    CapeImageBuffer(
            AbstractClientPlayer abstractClientPlayer,
            ResourceLocation resourceLocation) {
        this.BB = abstractClientPlayer;
        this.resourceLocation = resourceLocation;
        this.imageBufferDownload = new ImageBufferDownload();
    }

    public void skinAvailable() {
        this.BB.setLocationOfCape(this.resourceLocation);
    }

    public BufferedImage parseUserSkin(BufferedImage bufferedimage) {
        return CapeUtils.parseCape(bufferedimage);
    }
}
