package net.minecraft.client.shader;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.entity.Entity;
import remix.compat.vecmath.Matrix4f;

import com.google.common.collect.Lists;

import org.lwjgl.opengl.GL11;

import java.util.List;

public class Shader {

    private List listAuxHeights;

    private Matrix4f projectionMatrix;
    private List listAuxNames;
    private List listAuxWidths;
    private final List listAuxFramebuffers = Lists.newArrayList();
    private ShaderManager manager;
    public Framebuffer framebufferIn;
    public Framebuffer framebufferOut;

    private void preLoadShader() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableAlpha();
        GlStateManager.disableFog();
        GlStateManager.disableLighting();
        GlStateManager.disableColorMaterial();
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(0);
    }


    public void renderBlended(float f) {
        Entity[] entityArray = Framebuffer.p();
        GL11.glEnable(3042);
        this.framebufferIn.unbindFramebuffer();
        float f1 = this.framebufferOut.framebufferTextureWidth;
        Entity[] entityArrayValue = entityArray;
        float f2 = this.framebufferOut.framebufferTextureHeight;
        GlStateManager.viewport(0, 0, (int) f1, (int) f2);
        this.manager.addSamplerTexture("DiffuseSampler", this.framebufferIn);
        int i = 0;

        while (i < this.listAuxFramebuffers.size()) {
            this.manager.addSamplerTexture(
                    (String) this.listAuxNames.get(i), this.listAuxFramebuffers.get(i));
            this.manager
                    .aq("AuxSize" + i)
                    .set(
                            ((Integer) this.listAuxWidths.get(i)).intValue(),
                            ((Integer) this.listAuxHeights.get(i)).intValue());
            i++;
            if (entityArrayValue != null) {
                break;
            }
        }

        this.manager.aq("ProjMat").set(this.projectionMatrix);
        this.manager
                .aq("InSize")
                .set(this.framebufferIn.framebufferTextureWidth, this.framebufferIn.framebufferTextureHeight);
        this.manager.aq("OutSize").set(f1, f2);
        this.manager.aq("Time").set(f);
        MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
        this.manager
                .aq("ScreenSize")
                .set(
                        minecraftClient.displayWidth, minecraftClient.displayHeight);
        this.manager.cj();
        this.framebufferOut.bindFramebuffer(false);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.setOpaqueVertexColorFromRgb(-1);
        worldRenderer.addVertex(0.0, f2, 500.0);
        worldRenderer.addVertex(f1, f2, 500.0);
        worldRenderer.addVertex(f1, 0.0, 500.0);
        worldRenderer.addVertex(0.0, 0.0, 500.0);
        instance.draw();
        this.manager.useShader();
        this.framebufferOut.unbindFramebuffer();
        this.framebufferIn.unbindFramebufferTexture();

        for (Object object : this.listAuxFramebuffers) {
            if (object instanceof Framebuffer) {
                ((Framebuffer) object).unbindFramebufferTexture();
            }

            if (entityArrayValue != null) {
                break;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            Framebuffer.b(new Entity[3]);
        }
    }

    public void loadShader(float f) {
        this.preLoadShader();
        Entity[] entityArray = Framebuffer.p();
        this.framebufferIn.unbindFramebuffer();
        float f1 = this.framebufferOut.framebufferTextureWidth;
        float f2 = this.framebufferOut.framebufferTextureHeight;
        Entity[] entityArrayValue = entityArray;
        GlStateManager.viewport(0, 0, (int) f1, (int) f2);
        this.manager.addSamplerTexture("DiffuseSampler", this.framebufferIn);
        int i = 0;

        while (i < this.listAuxFramebuffers.size()) {
            this.manager.addSamplerTexture(
                    (String) this.listAuxNames.get(i), this.listAuxFramebuffers.get(i));
            this.manager
                    .aq("AuxSize" + i)
                    .set(
                            ((Integer) this.listAuxWidths.get(i)).intValue(),
                            ((Integer) this.listAuxHeights.get(i)).intValue());
            i++;
            if (entityArrayValue != null) {
                Entity.setLegacyGlobalFlowMarker(new String[1]);
                break;
            }
        }

        this.manager.aq("ProjMat").set(this.projectionMatrix);
        this.manager
                .aq("InSize")
                .set(this.framebufferIn.framebufferTextureWidth, this.framebufferIn.framebufferTextureHeight);
        this.manager.aq("OutSize").set(f1, f2);
        this.manager.aq("Time").set(f);
        MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
        this.manager
                .aq("ScreenSize")
                .set(
                        minecraftClient.displayWidth, minecraftClient.displayHeight);
        this.manager.cj();
        this.framebufferOut.framebufferClear();
        this.framebufferOut.bindFramebuffer(false);
        GlStateManager.depthMask(false);
        GlStateManager.colorMask(true, true, true, true);
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.setOpaqueVertexColorFromRgb(-1);
        worldRenderer.addVertex(0.0, f2, 500.0);
        worldRenderer.addVertex(f1, f2, 500.0);
        worldRenderer.addVertex(f1, 0.0, 500.0);
        worldRenderer.addVertex(0.0, 0.0, 500.0);
        instance.draw();
        GlStateManager.depthMask(true);
        GlStateManager.colorMask(true, true, true, true);
        this.manager.useShader();
        this.framebufferOut.unbindFramebuffer();
        this.framebufferIn.unbindFramebufferTexture();

        for (Object object : this.listAuxFramebuffers) {
            if (object instanceof Framebuffer) {
                ((Framebuffer) object).unbindFramebufferTexture();
            }

            if (entityArrayValue != null) {
                break;
            }
        }
    }

    public void setProjectionMatrix(Matrix4f aHaValue) {
        this.projectionMatrix = aHaValue;
    }

    public ShaderManager getShaderManager() {
        return this.manager;
    }

    public void deleteShader() {
        this.manager.deleteShader();
    }

    public void addAuxFramebuffer(String text, Object object, int i, int j) {
        this.listAuxNames.add(this.listAuxNames.size(), text);
        this.listAuxFramebuffers.add(this.listAuxFramebuffers.size(), object);
        this.listAuxWidths.add(this.listAuxWidths.size(), i);
        this.listAuxHeights.add(this.listAuxHeights.size(), j);
    }

    public Shader(
            IResourceManager iResourceManager,
            String text,
            Framebuffer aHcValue,
            Framebuffer aHdValue)
            throws JsonException {
        this.listAuxNames = Lists.newArrayList();
        this.listAuxWidths = Lists.newArrayList();
        this.listAuxHeights = Lists.newArrayList();
        this.manager = new ShaderManager(iResourceManager, text);
        this.framebufferIn = aHcValue;
        this.framebufferOut = aHdValue;
    }
}
