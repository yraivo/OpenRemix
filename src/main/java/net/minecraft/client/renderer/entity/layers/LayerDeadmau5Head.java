package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.LivingEntity;

public class LayerDeadmau5Head implements LayerRenderer {
    private static String b;
    private RenderPlayer playerRenderer;

    public boolean shouldCombineTextures() {
        return true;
    }

    public void method_5024988a07400532cdc0(
            AbstractClientPlayer abstractClientPlayer,
            float f4,
            float f5,
            float f,
            float f6,
            float f7,
            float f8,
            float f9) {
        int[] aint = LayerArmorBase.method_650532ba484532b3efb0();
        if (abstractClientPlayer.getName().equals(b)
                && abstractClientPlayer.hasSkin()
                && !abstractClientPlayer.isInvisible()) {
            this.playerRenderer.bindTexture(abstractClientPlayer.getLocationSkin());
            int i = 0;

            while (i < 2) {
                float f1 =
                        abstractClientPlayer.prevRotationYaw
                                + (abstractClientPlayer.rotationYaw
                                                - abstractClientPlayer.prevRotationYaw)
                                        * f
                                - (abstractClientPlayer.prevRenderYawOffset
                                        + (abstractClientPlayer.renderYawOffset
                                                        - abstractClientPlayer.prevRenderYawOffset)
                                                * f);
                float f2 =
                        abstractClientPlayer.prevRotationPitch
                                + (abstractClientPlayer.rotationPitch
                                                - abstractClientPlayer.prevRotationPitch)
                                        * f;
                GlStateManager.pushMatrix();
                GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(f2, 1.0F, 0.0F, 0.0F);
                GlStateManager.translate(0.375F * (i * 2 - 1), 0.0F, 0.0F);
                GlStateManager.translate(0.0F, -0.375F, 0.0F);
                GlStateManager.rotate(-f2, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(-f1, 0.0F, 1.0F, 0.0F);
                float f3 = 1.3333334F;
                GlStateManager.scale(f3, f3, f3);
                this.playerRenderer.getPlayerModel().renderDeadmau5Head(0.0625F);
                GlStateManager.popMatrix();
                i++;
                if (aint != null) {
                    break;
                }
            }
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        this.method_5024988a07400532cdc0(
                (AbstractClientPlayer) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    public LayerDeadmau5Head(RenderPlayer renderPlayer) {
        this.playerRenderer = renderPlayer;
    }

    static {
        b = "deadmau5";
    }
}
