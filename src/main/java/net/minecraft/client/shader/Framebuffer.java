package net.minecraft.client.shader;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

public class Framebuffer {
    public int framebufferWidth;

    public int depthBuffer;
    public boolean useDepth;
    public float[] framebufferColor;
    public int framebufferFilter;
    public int framebufferObject;
    public int framebufferTextureWidth;
    private static Entity[] wC;
    public int framebufferTexture;

    public int framebufferHeight;
    public int framebufferTextureHeight;

    public void framebufferRender(int i, int j) {
        this.framebufferRenderExt(i, j, true);
    }

    private static RuntimeException method_1a1f08aee5fe72868e61(RuntimeException runtimeexception) {
        return runtimeexception;
    }

    // region String decoding support

    // endregion

    public void createFramebuffer(int framebufferWidthValue, int j) {
        Entity[] entityArray = p();
        this.framebufferWidth = framebufferWidthValue;
        this.framebufferHeight = j;
        this.framebufferTextureWidth = framebufferWidthValue;
        Entity[] entityArrayValue = entityArray;
        this.framebufferTextureHeight = j;
        if (!OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferClear();
            if (entityArrayValue == null) {
                return;
            }
        }

        this.framebufferObject = OpenGlHelper.glGenFramebuffers();
        this.framebufferTexture = TextureUtil.glGenTextures();
        if (this.useDepth) {
            this.depthBuffer = OpenGlHelper.glGenRenderbuffers();
        }

        this.setFramebufferFilter(9728);
        GlStateManager.bindTexture(this.framebufferTexture);
        GL11.glTexImage2D(3553, 0, 32856, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6408, 5121, (ByteBuffer) null);
        OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
        OpenGlHelper.glFramebufferTexture2D(
                OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.dD, 3553, this.framebufferTexture, 0);
        if (this.useDepth) {
            OpenGlHelper.glBindRenderbuffer(OpenGlHelper.c, this.depthBuffer);
            OpenGlHelper.glRenderbufferStorage(OpenGlHelper.c, 33190, this.framebufferTextureWidth, this.framebufferTextureHeight);
            OpenGlHelper.glFramebufferRenderbuffer(
                    OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.ef, OpenGlHelper.c, this.depthBuffer);
        }

        this.framebufferClear();
        this.unbindFramebufferTexture();
    }

    public void deleteFramebuffer() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            this.unbindFramebufferTexture();
            this.unbindFramebuffer();
            if (this.depthBuffer > -1) {
                OpenGlHelper.glDeleteRenderbuffers(this.depthBuffer);
                this.depthBuffer = -1;
            }

            if (this.framebufferTexture > -1) {
                TextureUtil.deleteTexture(this.framebufferTexture);
                this.framebufferTexture = -1;
            }

            if (this.framebufferObject > -1) {
                OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
                OpenGlHelper.glDeleteFramebuffers(this.framebufferObject);
                this.framebufferObject = -1;
            }
        }
    }

    public void framebufferRenderExt(int i, int j, boolean flag) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.colorMask(true, true, true, false);
            GlStateManager.disableDepth();
            GlStateManager.depthMask(false);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, i, j, 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, 0.0F, -2000.0F);
            GlStateManager.viewport(0, 0, i, j);
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableAlpha();
            if (flag) {
                GlStateManager.disableBlend();
                GlStateManager.enableColorMaterial();
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.bindFramebufferTexture();
            float fx = i;
            float f1 = j;
            float f2 = (float) this.framebufferWidth / this.framebufferTextureWidth;
            float f3 = (float) this.framebufferHeight / this.framebufferTextureHeight;
            Tessellator instance = Tessellator.getInstance();
            WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.startDrawingQuads();
            worldRenderer.setOpaqueVertexColorFromRgb(-1);
            worldRenderer.addVertexWithUV(0.0, f1, 0.0, 0.0, 0.0);
            worldRenderer.addVertexWithUV(fx, f1, 0.0, f2, 0.0);
            worldRenderer.addVertexWithUV(fx, 0.0, 0.0, f2, f3);
            worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, 0.0, f3);
            instance.draw();
            this.unbindFramebufferTexture();
            GlStateManager.depthMask(true);
            GlStateManager.colorMask(true, true, true, true);
        }
    }

    public void setFramebufferFilter(int framebufferFilterValue) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferFilter = framebufferFilterValue;
            GlStateManager.bindTexture(this.framebufferTexture);
            GL11.glTexParameterf(3553, 10241, framebufferFilterValue);
            GL11.glTexParameterf(3553, 10240, framebufferFilterValue);
            GL11.glTexParameterf(3553, 10242, 10496.0F);
            GL11.glTexParameterf(3553, 10243, 10496.0F);
            GlStateManager.bindTexture(0);
        }
    }

    public void checkFramebufferComplete() {
        int i = OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER);
        if (i != OpenGlHelper.GL_FRAMEBUFFER_COMPLETE) {
            if (i == OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT) {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
            } else if (i == OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH) {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
            } else if (i == OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER) {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
            } else if (i == OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER) {
                throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
            } else {
                throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + i);
            }
        }
    }

    public void bindFramebufferTexture() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture(this.framebufferTexture);
        }
    }

    public void unbindFramebuffer() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
        }
    }

    static {
        b(null);
    }

    public void unbindFramebufferTexture() {
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture(0);
        }
    }

    public void framebufferClear() {
        this.bindFramebuffer(true);
        GlStateManager.clearColor(
                this.framebufferColor[0],
                this.framebufferColor[1],
                this.framebufferColor[2],
                this.framebufferColor[3]);
        short short1 = 16384;
        if (this.useDepth) {
            GlStateManager.clearDepth(1.0);
            short1 |= 256;
        }

        GlStateManager.clear(short1);
        this.unbindFramebuffer();
    }

    public void setFramebufferColor(float f, float f1, float f2, float f3) {
        this.framebufferColor[0] = f;
        this.framebufferColor[1] = f1;
        this.framebufferColor[2] = f2;
        this.framebufferColor[3] = f3;
    }

    public static void b(Entity[] entityArray) {
        wC = entityArray;
    }

    // region String decoding support

    // endregion

    public Framebuffer(int i, int j, boolean useDepthValue) {
        this.useDepth = useDepthValue;
        this.framebufferObject = -1;
        this.framebufferTexture = -1;
        this.depthBuffer = -1;
        this.framebufferColor = new float[4];
        this.framebufferColor[0] = 1.0F;
        this.framebufferColor[1] = 1.0F;
        this.framebufferColor[2] = 1.0F;
        this.framebufferColor[3] = 0.0F;
        this.createBindFramebuffer(i, j);
    }

    // region String decoding support

    // endregion

    public void bindFramebuffer(boolean flag) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
            if (flag) {
                GlStateManager.viewport(0, 0, this.framebufferWidth, this.framebufferHeight);
            }
        }
    }

    public void createBindFramebuffer(int framebufferWidthValue, int j) {
        Entity[] entityArray = p();
        if (!OpenGlHelper.isFramebufferEnabled()) {
            this.framebufferWidth = framebufferWidthValue;
            this.framebufferHeight = j;
            if (entityArray == null) {
                return;
            }
        }

        GlStateManager.enableDepth();
        if (this.framebufferObject >= 0) {
            this.deleteFramebuffer();
        }

        this.createFramebuffer(framebufferWidthValue, j);
        this.checkFramebufferComplete();
        OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
    }

    public static Entity[] p() {
        return wC;
    }
}
