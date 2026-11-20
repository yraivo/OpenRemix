package shadersmod.client;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumWorldBlockLayer;
import optifine.Reflector;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ShadersRender {

    public static void beginCutoutMippedTerrain() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(7);
        }
    }

    public static void renderEnchantedGlintBegin() {
        Shaders.useProgram(17);
    }

    public static void iJ() {}

    public static void beginBlockDamage() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(11);
            if (Shaders.programsID[11] == Shaders.programsID[7]) {
                Shaders.setDrawBuffers(Shaders.drawBuffersColorAtt0);
                GlStateManager.depthMask(false);
            }
        }
    }


    public static void beginCutoutTerrain() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(7);
        }
    }

    public static void endTerrain() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(3);
        }
    }

    public static void setupTerrain(
            RenderGlobal renderGlobal,
            Entity entity,
            double d0,
            ICamera iCamera,
            int i,
            boolean flag) {
        renderGlobal.setupTerrain(entity, d0, iCamera, i, flag);
    }

    public static void renderHand1(EntityRenderer entityRenderer, float f, int i) {
        if (!Shaders.isShadowPass && !Shaders.isHandRenderedMain) {
            Shaders.readCenterDepth();
            GlStateManager.enableBlend();
            Shaders.beginHand();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            entityRenderer.renderHand(f, i);
            Shaders.endHand();
            Shaders.isHandRenderedMain = true;
        }
    }

    public static void endBlockDamage() {
        if (Shaders.isRenderingWorld) {
            GlStateManager.depthMask(true);
            Shaders.useProgram(3);
        }
    }

    public static void renderOpaqueHand(EntityRenderer entityRenderer, float f, int i) {
        if (!Shaders.isShadowPass) {
            Item item = Shaders.shaderHeldItem != null ? Shaders.shaderHeldItem.getItem() : null;
            Block block = item instanceof ItemBlock ? ((ItemBlock) item).getBlock() : null;
            if (!(item instanceof ItemBlock)
                    || !(block instanceof Block)
                    || block.getBlockLayer() == EnumWorldBlockLayer.SOLID) {
                Shaders.readCenterDepth();
                Shaders.beginHand();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                entityRenderer.renderHand(f, i);
                Shaders.endHand();
                Shaders.isHandRenderedMain = true;
            }
        }
    }

    public static void setShaderFrustumPosition(
            Frustrum frustrum,
            double d0,
            double d1,
            double d2) {
        frustrum.setPosition(d0, d1, d2);
    }

    public static void beaconBeamDraw2() {
        GlStateManager.disableBlend();
    }

    public static void beginTerrainSolid() {
        if (Shaders.isRenderingWorld) {
            Shaders.fogEnabled = true;
            Shaders.useProgram(7);
        }
    }

    public static void ha() {
        if (OpenGlHelper.useVbo()) {
            GL11.glDisableClientState(32885);
            GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
            GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
            GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
        }
    }

    public static void np() {}

    public static void fZ() {
        if (OpenGlHelper.useVbo()) {
            GL11.glEnableClientState(32885);
            GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
            GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
            GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
        }
    }

    public static void beaconBeamBegin() {
        Shaders.useProgram(14);
    }

    public static void renderFPOverlay(EntityRenderer entityRenderer, float f, int i) {
        if (!Shaders.isShadowPass) {
            Shaders.beginFPOverlay();
            entityRenderer.renderHand(f, i);
            Shaders.ne();
        }
    }

    public static void updateShaderChunks(RenderGlobal renderGlobal, long i) {
        renderGlobal.updateChunks(i);
    }

    public static void renderHandWithShaders(ItemRenderer itemRenderer, float f) {
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        itemRenderer.renderItemInFirstPerson(f);
    }

    public static void setupArrayPointersVbo() {

        GL11.glVertexPointer(3, 5126, 56, 0L);

        GL11.glColorPointer(4, 5121, 56, 12L);

        GL11.glTexCoordPointer(2, 5126, 56, 16L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);

        GL11.glTexCoordPointer(2, 5122, 56, 24L);
        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);

        GL11.glNormalPointer(5120, 56, 28L);

        GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, 56, 32L);

        GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, 56, 40L);

        GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, 56, 48L);
    }

    public static void beginTranslucent() {
        if (Shaders.isRenderingWorld) {
            if (Shaders.usedDepthBuffers >= 2) {
                GlStateManager.setActiveTexture(33995);
                Shaders.checkGLError("pre copy depth");
                GL11.glCopyTexSubImage2D(
                        3553, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
                Shaders.checkGLError("copy depth");
                GlStateManager.setActiveTexture(33984);
            }

            Shaders.useProgram(12);
        }
    }

    public static void renderShadowMap(
            EntityRenderer entityRenderer, int j, float f, long k) {
        if (Shaders.usedShadowDepthBuffers > 0 && --Shaders.shadowPassCounter <= 0) {
            MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
            minecraftClient.mcProfiler.endStartSection("shadow pass");
            RenderGlobal renderGlobal = minecraftClient.renderGlobal;
            Shaders.isShadowPass = true;
            Shaders.shadowPassCounter = Shaders.shadowPassInterval;
            Shaders.preShadowPassThirdPersonView = minecraftClient.gameSettings.thirdPersonView;
            minecraftClient.gameSettings.thirdPersonView = 1;
            Shaders.checkGLError("pre shadow");
            GL11.glMatrixMode(5889);
            GL11.glPushMatrix();
            GL11.glMatrixMode(5888);
            GL11.glPushMatrix();
            minecraftClient.mcProfiler.endStartSection("shadow clear");
            EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.sfb);
            Shaders.checkGLError("shadow bind sfb");
            Shaders.useProgram(30);
            minecraftClient.mcProfiler.endStartSection("shadow camera");
            entityRenderer.setupCameraTransform(f, 2);
            Shaders.setCameraShadow(f);
            ActiveRenderInfo.updateRenderInfo(
                    minecraftClient.player, minecraftClient.gameSettings.thirdPersonView == 2);
            Shaders.checkGLError("shadow camera");
            GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
            Shaders.checkGLError("shadow drawbuffers");
            GL11.glReadBuffer(0);
            Shaders.checkGLError("shadow readbuffer");
            EXTFramebufferObject.glFramebufferTexture2DEXT(
                    36160, 36096, 3553, Shaders.sfbDepthTextures.get(0), 0);
            if (Shaders.usedShadowColorBuffers != 0) {
                EXTFramebufferObject.glFramebufferTexture2DEXT(
                        36160, 36064, 3553, Shaders.sfbColorTextures.get(0), 0);
            }

            Shaders.checkFramebufferStatus("shadow fb");
            GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glClear(Shaders.usedShadowColorBuffers != 0 ? 16640 : 256);
            Shaders.checkGLError("shadow clear");
            minecraftClient.mcProfiler.endStartSection("shadow frustum");
            ClippingHelper instance = ClippingHelperShadow.getInstance();
            minecraftClient.mcProfiler.endStartSection("shadow culling");
            Frustrum frustrum =
                    new Frustrum(instance);
            Entity entity = minecraftClient.getRenderViewEntity();
            double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * f;
            double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * f;
            double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * f;
            frustrum.setPosition(d0, d1, d2);
            GlStateManager.shadeModel(7425);
            GlStateManager.enableDepth();
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.disableCull();
            minecraftClient.mcProfiler.endStartSection("shadow prepareterrain");
            minecraftClient.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            minecraftClient.mcProfiler.endStartSection("shadow setupterrain");

            int i = entityRenderer.frameCount++;
            renderGlobal.setupTerrain(
                    entity, f, frustrum, i, minecraftClient.player.isSpectator());
            minecraftClient.mcProfiler.endStartSection("shadow updatechunks");
            minecraftClient.mcProfiler.endStartSection("shadow terrain");
            GlStateManager.matrixMode(5888);
            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            renderGlobal.renderBlockLayer(EnumWorldBlockLayer.SOLID, f, 2, entity);
            Shaders.checkGLError("shadow terrain solid");
            GlStateManager.enableAlpha();
            renderGlobal.renderBlockLayer(
                    EnumWorldBlockLayer.CUTOUT_MIPPED, f, 2, entity);
            Shaders.checkGLError("shadow terrain cutoutmipped");
            minecraftClient
                    .getTextureManager()
                    .getTexture(TextureMap.locationBlocksTexture)
                    .setBlurMipmap(false, false);
            renderGlobal.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, f, 2, entity);
            Shaders.checkGLError("shadow terrain cutout");
            minecraftClient
                    .getTextureManager()
                    .getTexture(TextureMap.locationBlocksTexture)
                    .restoreLastBlurMipmap();
            GlStateManager.shadeModel(7424);
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            minecraftClient.mcProfiler.endStartSection("shadow entities");
            if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] {0});
            }

            renderGlobal.renderEntities(entity, frustrum, f);
            Shaders.checkGLError("shadow entities");
            GlStateManager.matrixMode(5888);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.disableBlend();
            GlStateManager.enableCull();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.alphaFunc(516, 0.1F);
            if (Shaders.usedShadowDepthBuffers >= 2) {
                GlStateManager.setActiveTexture(33989);
                Shaders.checkGLError("pre copy shadow depth");
                GL11.glCopyTexSubImage2D(
                        3553, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
                Shaders.checkGLError("copy shadow depth");
                GlStateManager.setActiveTexture(33984);
            }

            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
            minecraftClient.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            GlStateManager.shadeModel(7425);
            Shaders.checkGLError("shadow pre-translucent");
            GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
            Shaders.checkGLError("shadow drawbuffers pre-translucent");
            Shaders.checkFramebufferStatus("shadow pre-translucent");
            minecraftClient.mcProfiler.endStartSection("shadow translucent");
            renderGlobal.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, f, 2, entity);
            Shaders.checkGLError("shadow translucent");
            if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
                RenderHelper.enableStandardItemLighting();
                Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] {1});
                renderGlobal.renderEntities(entity, frustrum, f);
                Reflector.call(Reflector.ForgeHooksClient_setRenderPass, new Object[] {-1});
                RenderHelper.disableStandardItemLighting();
                Shaders.checkGLError("shadow entities 1");
            }

            GlStateManager.shadeModel(7424);
            GlStateManager.depthMask(true);
            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GL11.glFlush();
            Shaders.checkGLError("shadow flush");
            Shaders.isShadowPass = false;
            minecraftClient.gameSettings.thirdPersonView = Shaders.preShadowPassThirdPersonView;
            minecraftClient.mcProfiler.endStartSection("shadow postprocess");
            if (Shaders.hasGlGenMipmap) {
                if (Shaders.usedShadowDepthBuffers >= 1) {
                    if (Shaders.shadowMipmapEnabled[0]) {
                        GlStateManager.setActiveTexture(33988);
                        GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(0));
                        GL30.glGenerateMipmap(3553);
                        GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[0] ? 9984 : 9987);
                    }

                    if (Shaders.usedShadowDepthBuffers >= 2 && Shaders.shadowMipmapEnabled[1]) {
                        GlStateManager.setActiveTexture(33989);
                        GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(1));
                        GL30.glGenerateMipmap(3553);
                        GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[1] ? 9984 : 9987);
                    }

                    GlStateManager.setActiveTexture(33984);
                }

                if (Shaders.usedShadowColorBuffers >= 1) {
                    if (Shaders.shadowColorMipmapEnabled[0]) {
                        GlStateManager.setActiveTexture(33997);
                        GlStateManager.bindTexture(Shaders.sfbColorTextures.get(0));
                        GL30.glGenerateMipmap(3553);
                        GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[0] ? 9984 : 9987);
                    }

                    if (Shaders.usedShadowColorBuffers >= 2 && Shaders.shadowColorMipmapEnabled[1]) {
                        GlStateManager.setActiveTexture(33998);
                        GlStateManager.bindTexture(Shaders.sfbColorTextures.get(1));
                        GL30.glGenerateMipmap(3553);
                        GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[1] ? 9984 : 9987);
                    }

                    GlStateManager.setActiveTexture(33984);
                }
            }

            Shaders.checkGLError("shadow postprocess");
            EXTFramebufferObject.glBindFramebufferEXT(36160, Shaders.dfb);
            GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
            Shaders.activeDrawBuffers = null;
            minecraftClient.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            Shaders.useProgram(7);
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5889);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            Shaders.checkGLError("shadow end");
        }
    }

    public static void iw() {}

    public static void renderEnchantedGlintEnd() {
        int[] aint = ShaderOption.lz();
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(16);
            if (aint == null) {
                return;
            }
        }

        Shaders.useProgram(0);
    }

    public static void endTranslucent() {
        if (Shaders.isRenderingWorld) {
            Shaders.useProgram(3);
        }
    }
}
