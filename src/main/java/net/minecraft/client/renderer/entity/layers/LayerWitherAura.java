package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWither;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class LayerWitherAura implements LayerRenderer {
    private static ResourceLocation WITHER_ARMOR;

    private final ModelWither witherModel = new ModelWither(0.5F);
    private RenderWither witherRenderer;

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        this.method_f52a37d78a76b723ea9d((EntityWither) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    public LayerWitherAura(RenderWither aGqValue) {
        this.witherRenderer = aGqValue;
    }

    static {
        net.minecraft.client.renderer.entity.layers.LayerWitherAura.WITHER_ARMOR =
                new ResourceLocation("textures/entity/wither/wither_armor.png");
    }

    public void method_f52a37d78a76b723ea9d(
            EntityWither entityWither,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        if (entityWither.isArmored()) {
            GlStateManager.depthMask(!entityWither.isInvisible());
            this.witherRenderer.bindTexture(net.minecraft.client.renderer.entity.layers.LayerWitherAura.WITHER_ARMOR);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f7 = entityWither.ticksExisted + f2;
            float f8 = MathHelper.cos(f7 * 0.02F) * 3.0F;
            float f9 = f7 * 0.01F;
            GlStateManager.translate(f8, f9, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float f10 = 0.5F;
            GlStateManager.color(f10, f10, f10, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(1, 1);
            this.witherModel.setLivingAnimations(entityWither, f, f1, f2);
            this.witherModel.setModelAttributes(this.witherRenderer.getMainModel());
            this.witherModel.render(entityWither, f, f1, f3, f4, f5, f6);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
