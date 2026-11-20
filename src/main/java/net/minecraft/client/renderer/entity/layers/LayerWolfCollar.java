package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import optifine.CustomColors;
import optifine.OptiFineConfig;

public class LayerWolfCollar implements LayerRenderer {

    private static ResourceLocation WOLF_COLLAR;
    private RenderWolf wolfRenderer;

    static {
        net.minecraft.client.renderer.entity.layers.LayerWolfCollar.WOLF_COLLAR =
                new ResourceLocation("textures/entity/wolf/wolf_collar.png");
    }

    public boolean shouldCombineTextures() {
        return true;
    }

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
        this.doRenderLayer((EntityWolf) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    public LayerWolfCollar(RenderWolf aGsValue) {
        this.wolfRenderer = aGsValue;
    }

    public void doRenderLayer(
            EntityWolf entityWolf,
            float f,
            float f1,
            float f6,
            float f2,
            float f3,
            float f4,
            float f5) {
        if (entityWolf.isTamed() && !entityWolf.isInvisible()) {
            this.wolfRenderer.bindTexture(net.minecraft.client.renderer.entity.layers.LayerWolfCollar.WOLF_COLLAR);
            EnumDyeColor enumDyeColor =
                    EnumDyeColor.byMetadata(entityWolf.getCollarColor().getMetadata());
            float[] afloat = EntitySheep.getDyeRgb(enumDyeColor);
            if (OptiFineConfig.isCustomColors()) {
                afloat = CustomColors.getWolfCollarColors(enumDyeColor, afloat);
            }

            GlStateManager.color(afloat[0], afloat[1], afloat[2]);
            this.wolfRenderer.getMainModel().render(entityWolf, f, f1, f2, f3, f4, f5);
        }
    }

    // region String decoding support

    // endregion
}
