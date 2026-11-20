package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.ResourceLocation;

import optifine.OptiFineConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import shadersmod.client.ShadersTex;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SimpleTexture extends AbstractTexture {

    private static Logger logger;
    protected ResourceLocation textureLocation;

    public void loadTexture(IResourceManager iResourceManager) throws IOException {
        this.cj();
        int j = TextureManager.method_7eec03da329a017ec143();
        InputStream inputstream = null;
        int i = j;

        try {
            IResource resource = iResourceManager.getResource(this.textureLocation);
            inputstream = resource.getInputStream();
            BufferedImage bufferedimage = TextureUtil.readBufferedImage(inputstream);
            byte b0 = 0;
            byte b1 = 0;
            if (resource.hasMetadata()) {
                try {
                    TextureMetadataSection metadata =
                            (TextureMetadataSection) resource.getMetadata("texture");
                    if (metadata != null) {
                        b0 =
                                remix.compat.compiler.JvmBooleanSlots.toByte(
                                        metadata.getTextureBlur());
                        b1 =
                                remix.compat.compiler.JvmBooleanSlots.toByte(
                                        metadata.getTextureClamp());
                    }
                } catch (RuntimeException runtimeexception) {
                    net.minecraft.client.renderer.texture.SimpleTexture.logger.warn(
                            "Failed reading metadata of: " + this.textureLocation,
                            runtimeexception);
                }
            }

            if (OptiFineConfig.isShaders()) {
                ShadersTex.loadSimpleTexture(
                        this.getGlTextureId(),
                        bufferedimage,
                        remix.compat.compiler.JvmBooleanSlots.fromInt(b0),
                        remix.compat.compiler.JvmBooleanSlots.fromInt(b1),
                        iResourceManager,
                        this.textureLocation,
                        this.getMultiTexID());
                if (i != 0) {
                    return;
                }
            }

            TextureUtil.uploadTextureImageAllocate(
                    this.getGlTextureId(),
                    bufferedimage,
                    remix.compat.compiler.JvmBooleanSlots.fromInt(b0),
                    remix.compat.compiler.JvmBooleanSlots.fromInt(b1));
        } finally {
            if (inputstream != null) {
                inputstream.close();
            }
        }
    }

    public SimpleTexture(ResourceLocation resourceLocation) {
        this.textureLocation = resourceLocation;
    }

    static {
        net.minecraft.client.renderer.texture.SimpleTexture.logger = LogManager.getLogger();
    }

    private static RuntimeException method_1a1f08aee5fe72868e61(RuntimeException runtimeexception) {
        return runtimeexception;
    }
}
