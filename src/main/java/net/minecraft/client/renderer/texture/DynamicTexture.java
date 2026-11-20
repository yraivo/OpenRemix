package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.IResourceManager;
import optifine.OptiFineConfig;
import shadersmod.client.ShadersTex;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class DynamicTexture extends AbstractTexture {
    private int width;
    private int height;
    private int[] dynamicTextureData;
    private boolean shadersInitialized = false;

    public DynamicTexture(int i, int j) {
        this.width = i;
        this.height = j;
        this.dynamicTextureData = new int[i * j * 3];
        if (OptiFineConfig.isShaders()) {
            ShadersTex.initDynamicTexture(this.getGlTextureId(), i, j, this);
            this.shadersInitialized = true;
        } else {
            TextureUtil.allocateTexture(this.getGlTextureId(), i, j);
        }
    }

    public DynamicTexture(BufferedImage bufferedimage) {
        this(bufferedimage.getWidth(), bufferedimage.getHeight());
        bufferedimage.getRGB(
                0,
                0,
                bufferedimage.getWidth(),
                bufferedimage.getHeight(),
                this.dynamicTextureData,
                0,
                bufferedimage.getWidth());
        this.updateDynamicTexture();
    }

    public int[] getTextureData() {
        return this.dynamicTextureData;
    }

    public void loadTexture(IResourceManager iResourceManager) throws IOException {}

    public void updateDynamicTexture() {
        if (OptiFineConfig.isShaders()) {
            if (!this.shadersInitialized) {
                ShadersTex.initDynamicTexture(this.getGlTextureId(), this.width, this.height, this);
                this.shadersInitialized = true;
            }

            ShadersTex.updateDynamicTexture(
                    this.getGlTextureId(), this.dynamicTextureData, this.width, this.height, this);
        } else {
            TextureUtil.uploadTexture(
                    this.getGlTextureId(), this.dynamicTextureData, this.width, this.height);
        }
    }
}
