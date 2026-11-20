package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;

public class LayerCape implements LayerRenderer {
    private RenderPlayer playerRenderer;

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        this.doRenderLayer(
                (AbstractClientPlayer) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    public LayerCape(RenderPlayer playerRendererValue) {
        this.playerRenderer = playerRendererValue;
    }

    public void doRenderLayer(
            AbstractClientPlayer abstractClientPlayer,
            float f6,
            float f7,
            float f,
            float f8,
            float f9,
            float f10,
            float f11) {
        if (abstractClientPlayer.hasPlayerInfo()
                && !abstractClientPlayer.isInvisible()
                && abstractClientPlayer.isWearing(EnumPlayerModelParts.CAPE)
                && abstractClientPlayer.getLocationCape() != null) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.playerRenderer.bindTexture(abstractClientPlayer.getLocationCape());
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 0.125F);
            double d0 =
                    abstractClientPlayer.previousChasingPosX
                            + (abstractClientPlayer.chasingPosX - abstractClientPlayer.previousChasingPosX) * f
                            - (abstractClientPlayer.prevPosX
                                    + (abstractClientPlayer.posX
                                                    - abstractClientPlayer.prevPosX)
                                            * f);
            double d1 =
                    abstractClientPlayer.previousChasingPosY
                            + (abstractClientPlayer.chasingPosY - abstractClientPlayer.previousChasingPosY) * f
                            - (abstractClientPlayer.prevPosY
                                    + (abstractClientPlayer.posY
                                                    - abstractClientPlayer.prevPosY)
                                            * f);
            double d2 =
                    abstractClientPlayer.previousChasingPosZ
                            + (abstractClientPlayer.chasingPosZ - abstractClientPlayer.previousChasingPosZ) * f
                            - (abstractClientPlayer.prevPosZ
                                    + (abstractClientPlayer.posZ
                                                    - abstractClientPlayer.prevPosZ)
                                            * f);
            float f1 =
                    abstractClientPlayer.prevRenderYawOffset
                            + (abstractClientPlayer.renderYawOffset
                                            - abstractClientPlayer.prevRenderYawOffset)
                                    * f;
            double d3 = MathHelper.sin(f1 * (float) Math.PI / 180.0F);
            double d4 = -MathHelper.cos(f1 * (float) Math.PI / 180.0F);
            float f2 = (float) d1 * 10.0F;
            f2 = MathHelper.clamp(f2, -6.0F, 32.0F);
            float f3 = (float) (d0 * d3 + d2 * d4) * 100.0F;
            float f4 = (float) (d0 * d4 - d2 * d3) * 100.0F;
            if (f3 < 0.0F) {
                f3 = 0.0F;
            }

            if (f3 > 165.0F) {
                f3 = 165.0F;
            }

            float f5 =
                    abstractClientPlayer.prevCameraYaw
                            + (abstractClientPlayer.cameraYaw - abstractClientPlayer.prevCameraYaw) * f;
            f2 +=
                    MathHelper.sin(
                                    (abstractClientPlayer.prevDistanceWalkedModified
                                                    + (abstractClientPlayer.distanceWalkedModified
                                                                    - abstractClientPlayer
                                                                            .prevDistanceWalkedModified)
                                                            * f)
                                            * 6.0F)
                            * 32.0F
                            * f5;
            if (abstractClientPlayer.isSneaking()) {
                f2 += 25.0F;
                GlStateManager.translate(0.0F, 0.142F, -0.0178F);
            }

            GlStateManager.rotate(6.0F + f3 / 2.0F + f2, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f4 / 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-f4 / 2.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            this.playerRenderer.getPlayerModel().renderCape(0.0625F);
            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}
