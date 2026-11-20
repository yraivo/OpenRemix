package net.minecraft.client.renderer.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;
import remix.event.impl.ModelRotationEvent;
import remix.client.RemixClient;

public class RenderPlayer extends RendererLivingEntity {
    private static String[] packedTextFragments;
    private boolean smallArms;

    public void renderLeftArm(AbstractClientPlayer player) {
        float f = 1.0F;
        GlStateManager.color(f, f, f);
        ModelPlayer modelPlayer = this.getPlayerModel();
        this.setModelVisibilities(player);
        modelPlayer.isSneak = false;
        modelPlayer.swingProgress = 0.0F;
        modelPlayer.setRotationAngles(
                0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, player);
        modelPlayer.renderLeftArm();
    }

    public RenderPlayer(RenderManager renderManagerValue) {
        this(renderManagerValue, false);
    }

    protected void renderOffsetLivingLabel(
            AbstractClientPlayer player,
            double d0,
            double d1,
            double d2,
            String text,
            float f,
            double d3) {
        if (d3 < 100.0) {
            Scoreboard worldScoreboard = player.getWorldScoreboard();
            ScoreObjective objectiveInDisplaySlot = worldScoreboard.getObjectiveInDisplaySlot(2);
            if (objectiveInDisplaySlot != null) {
                Score valueFromObjective =
                        worldScoreboard.getValueFromObjective(
                                player.getName(), objectiveInDisplaySlot);
                this.renderLivingLabel(
                        player,
                        valueFromObjective.getScorePoints()
                                + packedTextFragments[0]
                                + objectiveInDisplaySlot.getDisplayName(),
                        d0,
                        d1,
                        d2,
                        64);
                d1 += this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * f;
            }
        }

        super.renderOffsetLivingLabel(player, d0, d1, d2, text, f, d3);
    }

    protected void renderLivingAt(LivingEntity livingEntity, double d0, double d1, double d2) {
        this.renderLivingAt((AbstractClientPlayer) livingEntity, d0, d1, d2);
    }

    public ModelBase getMainModel() {
        return this.getPlayerModel();
    }

    public ModelPlayer getPlayerModel() {
        return (ModelPlayer) super.getMainModel();
    }

    protected void rotateCorpse(LivingEntity livingEntity, float f, float f1, float f2) {
        this.rotatePlayerModel((AbstractClientPlayer) livingEntity, f, f1, f2);
    }

    protected ResourceLocation getPlayerSkinTexture(
            AbstractClientPlayer player) {
        return player.getLocationSkin();
    }

    protected void renderLivingAt(
            AbstractClientPlayer player,
            double d0,
            double d1,
            double d2) {
        Render.getLegacyEntityRenderState();

        if (player.isEntityAlive()
                && player.isPlayerSleeping()) {
            super.renderLivingAt(
                    player,
                    d0 + player.sleepRenderOffsetX,
                    d1 + player.sleepRenderOffsetY,
                    d2 + player.sleepRenderOffsetZ);

            return;
        }

        super.renderLivingAt(player, d0, d1, d2);
    }

    protected void preRenderCallback(
            AbstractClientPlayer player, float f1) {
        float f = 0.9375F;
        GlStateManager.scale(f, f, f);
    }

    public void renderRightArm(AbstractClientPlayer player) {
        float f = 1.0F;
        GlStateManager.color(f, f, f);
        ModelPlayer modelPlayer = this.getPlayerModel();
        this.setModelVisibilities(player);
        modelPlayer.swingProgress = 0.0F;
        modelPlayer.isRiding = modelPlayer.isSneak = false;
        modelPlayer.setRotationAngles(
                0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, player);
        modelPlayer.renderRightArm();
    }

    private void setModelVisibilities(AbstractClientPlayer player) {
        Render.getLegacyEntityRenderState();
        ModelPlayer model = this.getPlayerModel();
        if (player.isSpectator()) {
            model.setAllPartsVisible(false);
            model.bipedHead.showModel = true;
            model.bipedHeadwear.showModel = true;
            return;
        }

        ModelRotationEvent pose = new ModelRotationEvent(player, player.isSneaking(), player.getItemInUseCount() > 0);
        RemixClient.eventBus.post(pose);
        ItemStack heldItem = player.inventory.getCurrentItem();
        updateSkinLayers(model, player);
        updateHeldItemPose(model, heldItem, pose);
    }

    private static void updateSkinLayers(ModelPlayer model, AbstractClientPlayer player) {
        model.setAllPartsVisible(true);
        model.bipedHeadwear.showModel = player.isWearing(EnumPlayerModelParts.HAT);
        model.bipedBodyWear.showModel = player.isWearing(EnumPlayerModelParts.JACKET);
        model.bipedLeftLegwear.showModel = player.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
        model.bipedRightLegwear.showModel = player.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
        model.bipedLeftArmwear.showModel = player.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
        model.bipedRightArmwear.showModel = player.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
    }

    private static void updateHeldItemPose(ModelPlayer model, ItemStack heldItem, ModelRotationEvent pose) {
        model.heldItemLeft = 0;
        model.aimedBow = false;
        model.isSneak = pose.isSneaking();
        model.heldItemRight = heldItem == null ? 0 : 1;
        if (heldItem == null || !pose.isUsingItem()) {
            return;
        }

        EnumAction action = heldItem.getItemUseAction();
        if (action == EnumAction.BLOCK) {
            model.heldItemRight = 3;
        } else if (action == EnumAction.BOW) {
            model.aimedBow = true;
        }
    }

    protected void renderOffsetLivingLabel(
            Entity entity, double d0, double d1, double d2, String text, float f, double d3) {
        this.renderOffsetLivingLabel(
                (AbstractClientPlayer) entity, d0, d1, d2, text, f, d3);
    }

    // region String decoding support

    // endregion

    protected void rotatePlayerModel(
            AbstractClientPlayer player, float f, float f1, float f2) {
        Render.getLegacyEntityRenderState();

        if (player.isEntityAlive()
                && player.isPlayerSleeping()) {
            GlStateManager.rotate(player.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(
                    this.getDeathMaxRotation(player), 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);

            return;
        }

        super.rotateCorpse(player, f, f1, f2);
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }

    public void doRender(
            LivingEntity livingEntity, double d0, double d1, double d2, float f, float f1) {
        this.renderPlayerModel(
                (AbstractClientPlayer) livingEntity, d0, d1, d2, f, f1);
    }

    public RenderPlayer(RenderManager renderManagerValue, boolean smallArmsValue) {
        super(renderManagerValue, new ModelPlayer(0.0F, smallArmsValue), 0.5F);
        this.smallArms = smallArmsValue;
        this.addLayer(new LayerBipedArmor(this));
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerArrow(this));
        this.addLayer(new LayerDeadmau5Head(this));
        this.addLayer(new LayerCape(this));
        this.addLayer(new LayerCustomHead(this.getPlayerModel().bipedHead));
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.preRenderCallback((AbstractClientPlayer) livingEntity, f);
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.renderPlayerModel((AbstractClientPlayer) entity, d0, d1, d2, f, f1);
    }

    public void renderPlayerModel(
            AbstractClientPlayer player,
            double d0,
            double d1,
            double d2,
            float f,
            float f1) {
        if (!player.isLocalUser()
                || this.renderManager.livingPlayer == player) {
            double d3 = d1;
            if (player.isSneaking()
                    && !(player instanceof LocalPlayer)) {
                d3 = d1 - 0.125;
            }

            this.setModelVisibilities(player);
            super.doRender(player, d0, d3, d2, f, f1);
        }
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getPlayerSkinTexture((AbstractClientPlayer) entity);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.renderer.entity.RenderPlayer.packedTextFragments[0] = new String(" ");
    }
    // endregion
}
