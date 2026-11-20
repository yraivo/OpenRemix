package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.GlStateManager;
import shadersmod.client.MultiTexID;
import shadersmod.client.ShadersTex;

import org.lwjgl.opengl.GL11;

public abstract class AbstractTexture implements ITextureObject {
    protected boolean mipmapLast;
    protected boolean blurLast;
    protected boolean blur;
    protected int glTextureId = -1;
    protected boolean mipmap;
    public MultiTexID multiTex;

    @Override
    public int getGlTextureId() {
        if (this.glTextureId == -1) {
            this.glTextureId = net.minecraft.client.renderer.texture.TextureUtil.glGenTextures();
        }

        return this.glTextureId;
    }

    @Override
    public void restoreLastBlurMipmap() {
        this.setBlurMipmapDirect(this.blurLast, this.mipmapLast);
    }

    public void setBlurMipmapDirect(boolean blurValue, boolean mipmapValue) {
        int j;
        short short1;
        {
            int k = TextureManager.method_7eec03da329a017ec143();
            this.blur = blurValue;
            int i = k;
            this.mipmap = mipmapValue;

            if (blurValue) {
                j = mipmapValue ? 9987 : 9729;
                short1 = 9729;
                if (!(i != 0)) {

                    j = mipmapValue ? 9986 : 9728;
                    short1 = 9728;
                }
            } else {

                j = mipmapValue ? 9986 : 9728;
                short1 = 9728;
            }
        }

        GlStateManager.bindTexture(this.getGlTextureId());
        GL11.glTexParameteri(3553, 10241, j);
        GL11.glTexParameteri(3553, 10240, short1);
    }

    @Override
    public MultiTexID getMultiTexID() {
        return ShadersTex.getMultiTexID(this);
    }

    public void cj() {
        shadersmod.client.ShadersTex.deleteTextures(this, this.glTextureId);
        if (this.glTextureId != -1) {
            net.minecraft.client.renderer.texture.TextureUtil.deleteTexture(this.glTextureId);
            this.glTextureId = -1;
        }
    }

    @Override
    public void setBlurMipmap(boolean p171_0, boolean p171_1) {
        this.blurLast = this.blur;
        this.mipmapLast = this.mipmap;
        this.setBlurMipmapDirect(p171_0, p171_1);
    }
}
