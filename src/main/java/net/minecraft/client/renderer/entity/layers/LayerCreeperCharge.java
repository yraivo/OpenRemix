package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

public class LayerCreeperCharge implements LayerRenderer {

    private static ResourceLocation LIGHTNING_TEXTURE;
    private final ModelCreeper creeperModel = new ModelCreeper(2.0F);
    private RenderCreeper creeperRenderer;

    public void doRenderLayer(
            EntityCreeper entityCreeper,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        if (entityCreeper.getPowered()) {
            GlStateManager.depthMask(!entityCreeper.isInvisible());
            this.creeperRenderer.bindTexture(net.minecraft.client.renderer.entity.layers.LayerCreeperCharge.LIGHTNING_TEXTURE);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f7 = entityCreeper.ticksExisted + f2;
            GlStateManager.translate(f7 * 0.01F, f7 * 0.01F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float f8 = 0.5F;
            GlStateManager.color(f8, f8, f8, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(1, 1);
            this.creeperModel.setModelAttributes(this.creeperRenderer.getMainModel());
            this.creeperModel.render(entityCreeper, f, f1, f3, f4, f5, f6);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
        }
    }

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        this.doRenderLayer((EntityCreeper) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    // region String decoding support

    // endregion

    public LayerCreeperCharge(RenderCreeper aDbValue) {
        this.creeperRenderer = aDbValue;
    }

    static {
        net.minecraft.client.renderer.entity.layers.LayerCreeperCharge.LIGHTNING_TEXTURE =
                new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    }

    public boolean shouldCombineTextures() {
        return false;
    }

    // region String decoding support

    // endregion
}
