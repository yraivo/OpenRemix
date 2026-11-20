package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.TeamEnumVisible;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import optifine.OptiFineConfig;
import optifine.Reflector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.client.friend.FriendEntry;
import remix.client.friend.FriendManager;
import remix.command.TargetsClientCommand;
import remix.event.impl.ModelRotationEvent;
import remix.module.player.AutoHypixelModule;
import remix.module.render.ChamsModule;
import remix.module.render.ESPModule;
import remix.module.render.NameProtectModule;
import remix.module.render.NameTagsModule;
import remix.server.hypixel.HypixelGameType;
import remix.server.hypixel.HypixelLocationData;
import remix.server.hypixel.HypixelServerDetector;
import remix.ui.hud.ChamsColor;
import remix.ui.hud.HudColor;

import shadersmod.client.Shaders;

import java.awt.Color;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public abstract class RendererLivingEntity extends Render {
    protected List layerRenderers;
    protected FloatBuffer brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
    private static Logger logger;

    public static float sneakingNameplateDistance;
    protected boolean renderOutlines;

    protected ModelBase mainModel;
    private static DynamicTexture brightnessTexture;
    public static float nameplateDistance;

    protected boolean canRenderName(Entity entity) {
        return this.canRenderName((LivingEntity) entity);
    }

    protected boolean setScoreTeamColor(LivingEntity livingEntity) {
        int i = 16777215;
        if (livingEntity instanceof PlayerEntity) {
            ScorePlayerTeam team = (ScorePlayerTeam) livingEntity.getTeam();
            if (team != null) {
                String text = FontRenderer.getFormatFromString(team.getColorPrefix());
                if (text.length() >= 2) {
                    i = this.getFontRendererFromRenderManager().getColorCode(text.charAt(1));
                }
            }
        }

        float f1 = (i >> 16 & 0xFF) / 255.0F;
        float f2 = (i >> 8 & 0xFF) / 255.0F;
        float f = (i & 0xFF) / 255.0F;
        GlStateManager.disableLighting();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.color(f1, f2, f, 1.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return true;
    }

    protected void unsetScoreTeamColor() {
        GlStateManager.enableLighting();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    protected void renderModel(
            LivingEntity entity,
            float swing,
            float swingAmount,
            float animationAge,
            float headBodyDifference,
            float pitch,
            float modelScale) {
        boolean visible = !entity.isInvisible();
        boolean translucent =
                !visible && !entity.isInvisibleToPlayer(MinecraftClient.getMinecraft().player);
        if ((!visible && !translucent) || !this.bindEntityTexture(entity)) {
            return;
        }

        if (translucent) {
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.003921569F);
        }
        this.mainModel.render(
                entity, swing, swingAmount, animationAge, headBodyDifference, pitch, modelScale);
        if (translucent) {
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
        }
    }

    protected boolean setBrightness(LivingEntity livingEntity, float f, boolean flag) {
        Render.getLegacyEntityRenderState();

        float brightness = livingEntity.getBrightness(f);

        int i = this.getColorMultiplier(livingEntity, brightness, f);
        int j = (i >> 24 & 0xFF) > 0 ? 1 : 0;
        int k = livingEntity.hurtTime <= 0 && livingEntity.deathTime <= 0 ? 0 : 1;
        if (j == 0 && k == 0) {
            return false;
        }

        if (j == 0 && !flag) {
            return false;
        }

        {
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
            GlStateManager.enableTexture2D();
            GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.enableTexture2D();
            GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND2_RGB, 770);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
            GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
            ((Buffer) this.brightnessBuffer).position(0);
            if (k != 0) {
                this.brightnessBuffer.put(1.0F);
                this.brightnessBuffer.put(0.0F);
                this.brightnessBuffer.put(0.0F);
                this.brightnessBuffer.put(0.3F);
                if (!(!OptiFineConfig.isShaders())) {

                    Shaders.setEntityColor(1.0F, 0.0F, 0.0F, 0.3F);
                }
            } else {

                float f2 = (i >> 24 & 0xFF) / 255.0F;
                float f3 = (i >> 16 & 0xFF) / 255.0F;
                float f4 = (i >> 8 & 0xFF) / 255.0F;
                float f5 = (i & 0xFF) / 255.0F;
                this.brightnessBuffer.put(f3);
                this.brightnessBuffer.put(f4);
                this.brightnessBuffer.put(f5);
                this.brightnessBuffer.put(1.0F - f2);
                if (OptiFineConfig.isShaders()) {
                    Shaders.setEntityColor(f3, f4, f5, 1.0F - f2);
                }
            }
        }

        ((Buffer) this.brightnessBuffer).flip();
        GL11.glTexEnv(8960, 8705, this.brightnessBuffer);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(
                net.minecraft.client.renderer.entity.RendererLivingEntity.brightnessTexture
                        .getGlTextureId());
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 7681);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        return true;
    }

    public void transformHeldFull3DItemLayer() {}

    public void renderName(Entity entity, double d0, double d1, double d2) {
        this.passSpecialRender((LivingEntity) entity, d0, d1, d2);
    }

    protected float getSwingProgress(LivingEntity livingEntity, float f) {
        return livingEntity.getSwingProgress(f);
    }

    protected float interpolateRotation(float f, float f1, float f2) {
        Render.getLegacyEntityRenderState();

        float f3 = f1 - f;

        while (f3 < -180.0F) {
            f3 += 360.0F;
        }

        while (f3 >= 180.0F) {
            f3 -= 360.0F;
        }

        return f + f2 * f3;
    }

    protected boolean setDoRenderBrightness(LivingEntity livingEntity, float f) {
        return this.setBrightness(livingEntity, f, true);
    }

    protected float getDeathMaxRotation(LivingEntity livingEntity) {
        return 90.0F;
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((LivingEntity) entity, d0, d1, d2, f, f1);
    }

    protected int getColorMultiplier(LivingEntity livingEntity, float f, float f1) {
        return 0;
    }

    public boolean addLayer(LayerRenderer layerRenderer) {
        return this.layerRenderers.add(layerRenderer);
    }

    public void setRenderOutlines(boolean renderOutlinesValue) {
        this.renderOutlines = renderOutlinesValue;
    }

    public RendererLivingEntity(
            RenderManager renderManagerValue, ModelBase aBkValue, float shadowSizeValue) {
        super(renderManagerValue);
        this.layerRenderers = Lists.newArrayList();
        this.renderOutlines = false;
        this.mainModel = aBkValue;
        this.shadowSize = shadowSizeValue;
    }

    protected float handleRotationFloat(LivingEntity livingEntity, float f) {
        return livingEntity.ticksExisted + f;
    }

    protected void renderLayers(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        Render.getLegacyEntityRenderState();

        Iterator iterator = this.layerRenderers.iterator();

        while (iterator.hasNext()) {
            LayerRenderer layerRenderer = (LayerRenderer) iterator.next();
            boolean flag =
                    this.setBrightness(livingEntity, f2, layerRenderer.shouldCombineTextures());
            layerRenderer.doRenderLayer(livingEntity, f, f1, f2, f3, f4, f5, f6);
            if (flag) {
                this.unsetBrightness();
            }
        }
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {}

    public void passSpecialRender(LivingEntity entity, double x, double y, double z) {
        Render.getLegacyEntityRenderState();
        if (Reflector.forgeRenderLivingSpecialsPre.exists()
                && Reflector.postForgeBusEvent(
                        Reflector.forgeRenderLivingSpecialsPre,
                        new Object[] {entity, this, x, y, z})) {
            return;
        }

        if (this.canRenderName(entity)) {
            if (this.hasRemixNameplate(entity)) {
                return;
            }
            double distanceSquared = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
            float range = entity.isSneaking() ? sneakingNameplateDistance : nameplateDistance;
            if (distanceSquared < range * range) {
                String name =
                        this.applyNameProtectAliases(entity.getDisplayName().getFormattedText());
                GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
                if (entity.isSneaking()) {
                    this.renderSneakingNameplate(entity, x, y, z, name);
                } else {
                    this.renderOffsetLivingLabel(
                            entity,
                            x,
                            y - (entity.isChild() ? entity.height / 2.0F : 0.0F),
                            z,
                            name,
                            0.02666667F,
                            distanceSquared);
                }
            }
        }

        if (Reflector.forgeRenderLivingSpecialsPost.exists()) {
            Reflector.postForgeBusEvent(
                    Reflector.forgeRenderLivingSpecialsPost, new Object[] {entity, this, x, y, z});
        }
    }

    private boolean hasRemixNameplate(LivingEntity entity) {
        RemixClient.INSTANCE.getClientServices();
        ESPModule esp = ClientServices.moduleManager.esp;
        RemixClient.INSTANCE.getClientServices();
        NameTagsModule nameTags = ClientServices.moduleManager.nameTags;
        return (!nameTags.isEnabled()
                        && esp.isEnabled()
                        && esp.labelsSetting.asBoolean()
                        && esp.shouldRenderEntity(entity))
                || (nameTags.isEnabled() && nameTags.shouldRenderEntity(entity));
    }

    private String applyNameProtectAliases(String name) {
        RemixClient.INSTANCE.getClientServices();
        NameProtectModule nameProtect = ClientServices.moduleManager.nameProtect;
        if (nameProtect.isEnabled() && nameProtect.friendsSetting.asBoolean()) {
            for (Entry<String, FriendEntry> friend : FriendManager.friendsMap.entrySet()) {
                if (name.contains(friend.getKey())) {
                    name = name.replace(friend.getKey(), friend.getValue().getAlias());
                }
            }
        }
        return name;
    }

    private void renderSneakingNameplate(
            LivingEntity entity, double x, double y, double z, String name) {
        FontRenderer font = this.getFontRendererFromRenderManager();
        GlStateManager.pushMatrix();
        GlStateManager.translate(
                (float) x,
                (float) y + entity.height + 0.5F - (entity.isChild() ? entity.height / 2.0F : 0.0F),
                (float) z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
        GlStateManager.translate(0.0F, 9.374999F, 0.0F);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(
                GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer vertices = tessellator.getWorldRenderer();
        vertices.startDrawingQuads();
        int halfWidth = font.getStringWidth(name) / 2;
        vertices.setVertexColorFromFloats(0.0F, 0.0F, 0.0F, 0.25F);
        vertices.addVertex(-halfWidth - 1, -1.0, 0.0);
        vertices.addVertex(-halfWidth - 1, 8.0, 0.0);
        vertices.addVertex(halfWidth + 1, 8.0, 0.0);
        vertices.addVertex(halfWidth + 1, -1.0, 0.0);
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.depthMask(true);
        font.drawString(name, -font.getStringWidth(name) / 2, 0, 553648127);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    protected void renderLivingAt(LivingEntity livingEntity, double d0, double d1, double d2) {
        GlStateManager.translate((float) d0, (float) d1, (float) d2);
    }

    protected void unsetBrightness() {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.disableTexture2D();
        GlStateManager.bindTexture(0);
        GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        if (OptiFineConfig.isShaders()) {
            Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    protected boolean removeLayer(LayerRenderer layerRenderer) {
        return this.layerRenderers.remove(layerRenderer);
    }

    public ModelBase getMainModel() {
        return this.mainModel;
    }

    public void doRender(
            LivingEntity entity,
            double x,
            double y,
            double z,
            float entityYaw,
            float partialTicks) {
        RemixClient.INSTANCE.getClientServices();
        Render.getLegacyEntityRenderState();
        if (this.shouldHideHypixelEntity(entity)) {
            return;
        }
        if (Reflector.forgeRenderLivingPre.exists()
                && Reflector.postForgeBusEvent(
                        Reflector.forgeRenderLivingPre, new Object[] {entity, this, x, y, z})) {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
        this.mainModel.isRiding = entity.isRiding();
        if (Reflector.forgeShouldRiderSit.exists()) {
            this.mainModel.isRiding =
                    entity.isRiding()
                            && entity.ridingEntity != null
                            && Reflector.callBoolean(
                                    entity.ridingEntity,
                                    Reflector.forgeShouldRiderSit,
                                    new Object[0]);
        }
        this.mainModel.isChild = entity.isChild();

        try {
            this.renderLivingModelAndLayers(entity, x, y, z, partialTicks);
            GlStateManager.disableRescaleNormal();
        } catch (Exception exception) {
            logger.error("Couldn't render entity", exception);
        }

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        if (!this.renderOutlines) {
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
        if (Reflector.forgeRenderLivingPost.exists()) {
            Reflector.postForgeBusEvent(
                    Reflector.forgeRenderLivingPost, new Object[] {entity, this, x, y, z});
        }
    }

    private boolean shouldHideHypixelEntity(LivingEntity entity) {
        AutoHypixelModule autoHypixel = ClientServices.moduleManager.autoHypixel;
        if (!autoHypixel.isEnabled() || !HypixelServerDetector.isOnHypixel()) {
            return false;
        }
        if (autoHypixel.npcHiderSetting.asBoolean()
                && (entity.getUniqueId().version() == 2 || entity instanceof EntityVillager)) {
            return true;
        }
        if (!autoHypixel.pitAntiLagSetting.asBoolean()) {
            return false;
        }
        RemixClient.INSTANCE.getClientServices();
        HypixelLocationData location =
                ClientServices.moduleManager.hypixelLocrawListener.getCurrentLocation();
        if (location == null || location.getHypixelGameType() != HypixelGameType.PIT) {
            return true;
        }
        if (autoHypixel.nameTagHeightCutoff == -1.0) {
            if (entity instanceof EntityArmorStand
                    && entity.getName().equals("\u00a7a\u00a7lJUMP! \u00a7c\u00a7lFIGHT!")) {
                autoHypixel.nameTagHeightCutoff = entity.posY - 5.0;
            }
            return false;
        }
        return entity.posY > autoHypixel.nameTagHeightCutoff
                && MinecraftClient.getMinecraft().player.posY < autoHypixel.nameTagHeightCutoff;
    }

    private void renderLivingModelAndLayers(
            LivingEntity entity, double x, double y, double z, float partialTicks) {
        ModelRotationEvent rotation =
                new ModelRotationEvent(
                        entity,
                        entity.prevRenderYawOffset,
                        entity.renderYawOffset,
                        entity.prevRotationYawHead,
                        entity.rotationYawHead,
                        entity.rotationPitch,
                        entity.prevRotationPitch);
        RemixClient.eventBus.post(rotation);
        float bodyYaw =
                this.interpolateRotation(
                        rotation.getPreviousBodyYaw(), rotation.getBodyYaw(), partialTicks);
        float headYaw =
                this.interpolateRotation(
                        rotation.getPreviousHeadYaw(), rotation.getHeadYaw(), partialTicks);
        float headBodyDifference = headYaw - bodyYaw;
        if (this.mainModel.isRiding && entity.ridingEntity instanceof LivingEntity) {
            // Keep the Remix event's rider yaw here; Minecraft alone would use the mount's yaw.
            bodyYaw =
                    this.interpolateRotation(
                            rotation.getPreviousBodyYaw(), rotation.getBodyYaw(), partialTicks);
            headBodyDifference = headYaw - bodyYaw;
            float mountHeadDifference = MathHelper.wrapAngleTo180(headBodyDifference);
            mountHeadDifference = MathHelper.clamp(mountHeadDifference, -85.0F, 85.0F);
            bodyYaw = headYaw - mountHeadDifference;
            if (mountHeadDifference * mountHeadDifference > 2500.0F) {
                bodyYaw += mountHeadDifference * 0.2F;
            }
        }
        float pitch =
                rotation.getPreviousPitch()
                        + (rotation.getPitch() - rotation.getPreviousPitch()) * partialTicks;
        this.renderLivingAt(entity, x, y, z);
        float animationAge = this.handleRotationFloat(entity, partialTicks);
        this.rotateCorpse(entity, animationAge, bodyYaw, partialTicks);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        this.preRenderCallback(entity, partialTicks);
        GlStateManager.translate(0.0F, -1.5078125F, 0.0F);

        float swingAmount =
                entity.prevLimbSwingAmount
                        + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
        float swing = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);
        if (entity.isChild()) {
            swing *= 3.0F;
        }
        if (swingAmount > 1.0F) {
            swingAmount = 1.0F;
        }

        float modelScale = 0.0625F;
        GlStateManager.enableAlpha();
        this.mainModel.setLivingAnimations(entity, swing, swingAmount, partialTicks);
        this.mainModel.setRotationAngles(
                swing, swingAmount, animationAge, headBodyDifference, pitch, modelScale, entity);
        if (this.renderOutlines) {
            boolean colored = this.setScoreTeamColor(entity);
            this.renderModel(
                    entity,
                    swing,
                    swingAmount,
                    animationAge,
                    headBodyDifference,
                    pitch,
                    modelScale);
            if (colored) {
                this.unsetScoreTeamColor();
            }
            return;
        }

        boolean brightnessApplied = this.setDoRenderBrightness(entity, partialTicks);
        ClientServices services = (ClientServices) RemixClient.INSTANCE.getClientServices();
        ChamsModule chams = ClientServices.moduleManager.chams;
        if (chams.isEnabled() && chams.shouldRenderEntity(entity)) {
            Color color = this.resolveChamsColor(entity, services, chams);
            this.renderChamsModel(
                    entity,
                    swing,
                    swingAmount,
                    animationAge,
                    headBodyDifference,
                    pitch,
                    modelScale,
                    chams,
                    color);
        } else {
            this.renderModel(
                    entity,
                    swing,
                    swingAmount,
                    animationAge,
                    headBodyDifference,
                    pitch,
                    modelScale);
        }
        if (!(entity instanceof PlayerEntity) || !((PlayerEntity) entity).isSpectator()) {
            this.renderLayers(
                    entity,
                    swing,
                    swingAmount,
                    partialTicks,
                    animationAge,
                    headBodyDifference,
                    pitch,
                    modelScale);
        }
        if (brightnessApplied) {
            this.unsetBrightness();
        }
        GlStateManager.depthMask(true);
    }

    private Color resolveChamsColor(
            LivingEntity entity, ClientServices services, ChamsModule chams) {
        if (TargetsClientCommand.isTarget(entity)) {
            return HudColor.getRainbowColor(800.0, 1);
        }
        if (chams.colorSetting.getValue() == ChamsColor.CLIENT) {
            return new Color(services.getThemeColor());
        }
        if (entity instanceof PlayerEntity) {
            ScorePlayerTeam team = (ScorePlayerTeam) entity.getTeam();
            if (team != null) {
                String teamFormat = FontRenderer.getFormatFromString(team.getColorPrefix());
                if (teamFormat.length() >= 2) {
                    return new Color(
                            this.getFontRendererFromRenderManager()
                                    .getColorCode(teamFormat.charAt(1)));
                }
            }
        }
        return Color.WHITE;
    }

    private void renderChamsModel(
            LivingEntity entity,
            float swing,
            float swingAmount,
            float animationAge,
            float headBodyDifference,
            float pitch,
            float modelScale,
            ChamsModule chams,
            Color color) {
        if (chams.coloredSetting.asBoolean()) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableLighting();
        }
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        if (!chams.textureSetting.asBoolean()) {
            GlStateManager.disableTexture2D();
        }
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        if (chams.coloredSetting.asBoolean()) {
            GlStateManager.color(
                    color.getRed() / 255.0F,
                    color.getGreen() / 255.0F,
                    color.getBlue() / 255.0F,
                    (float) chams.alphaSetting.asDouble() + 0.001F);
        }
        this.renderModel(
                entity, swing, swingAmount, animationAge, headBodyDifference, pitch, modelScale);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        this.renderModel(
                entity, swing, swingAmount, animationAge, headBodyDifference, pitch, modelScale);
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        if (!chams.textureSetting.asBoolean()) {
            GlStateManager.enableTexture2D();
        }
    }

    protected boolean canRenderName(LivingEntity livingEntity) {
        LocalPlayer player = MinecraftClient.getMinecraft().player;
        if (livingEntity instanceof PlayerEntity && livingEntity != player) {
            Team team = livingEntity.getTeam();
            Team teamValue = player.getTeam();
            if (team != null) {
                TeamEnumVisible teamEnumVisible = team.getNameTagVisibility();
                switch (teamEnumVisible) {
                    case ALWAYS:
                        return true;
                    case NEVER:
                        return false;
                    case HIDE_FOR_OTHER_TEAMS:
                        return teamValue == null || team.isSameTeam(teamValue);
                    case HIDE_FOR_OWN_TEAM:
                        return teamValue == null || !team.isSameTeam(teamValue);
                    default:
                        return true;
                }
            }
        }

        return MinecraftClient.isGuiEnabled()
                && livingEntity != this.renderManager.livingPlayer
                && !livingEntity.isInvisibleToPlayer(player)
                && livingEntity.riddenByEntity == null;
    }

    protected void rotateCorpse(LivingEntity livingEntity, float f3, float f, float f1) {
        Render.getLegacyEntityRenderState();

        GlStateManager.rotate(180.0F - f, 0.0F, 1.0F, 0.0F);

        if (livingEntity.deathTime > 0) {
            float f2 = (livingEntity.deathTime + f1 - 1.0F) / 20.0F * 1.6F;
            f2 = MathHelper.sqrt(f2);
            if (f2 > 1.0F) {
                f2 = 1.0F;
            }

            GlStateManager.rotate(f2 * this.getDeathMaxRotation(livingEntity), 0.0F, 0.0F, 1.0F);

            return;
        }

        String text = EnumChatFormatting.getTextWithoutFormattingCodes(livingEntity.getName());
        if (text != null
                && (text.equals("Dinnerbone") || text.equals("Grumm"))
                && (!(livingEntity instanceof PlayerEntity)
                        || ((PlayerEntity) livingEntity).isWearing(EnumPlayerModelParts.CAPE))) {
            GlStateManager.translate(0.0F, livingEntity.height + 0.1F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        }
    }

    static {
        net.minecraft.client.renderer.entity.RendererLivingEntity.logger = LogManager.getLogger();
        net.minecraft.client.renderer.entity.RendererLivingEntity.brightnessTexture =
                new DynamicTexture(16, 16);
        net.minecraft.client.renderer.entity.RendererLivingEntity.nameplateDistance = 64.0F;
        net.minecraft.client.renderer.entity.RendererLivingEntity.sneakingNameplateDistance = 32.0F;
        int[] aint =
                net.minecraft.client.renderer.entity.RendererLivingEntity.brightnessTexture
                        .getTextureData();
        for (int l = 0; l < 256; l++) {
            aint[l] = -1;
        }
        net.minecraft.client.renderer.entity.RendererLivingEntity.brightnessTexture
                .updateDynamicTexture();
    }
}
