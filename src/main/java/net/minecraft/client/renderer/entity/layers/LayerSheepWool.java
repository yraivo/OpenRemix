package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import optifine.CustomColors;
import optifine.OptiFineConfig;

public class LayerSheepWool implements LayerRenderer {
    private static ResourceLocation TEXTURE;

    private RenderSheep sheepRenderer;
    private final ModelSheep1 sheepModel = new ModelSheep1();

    public LayerSheepWool(RenderSheep aGhValue) {
        this.sheepRenderer = aGhValue;
    }

    public void doRenderLayer(
            EntitySheep entitySheep,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        int[] aint = LayerArmorBase.method_650532ba484532b3efb0();
        if (!entitySheep.getSheared() && !entitySheep.isInvisible()) {
            {
                this.sheepRenderer.bindTexture(net.minecraft.client.renderer.entity.layers.LayerSheepWool.TEXTURE);
                if (entitySheep.jvm171_d_45ffeb83d14f3c()
                        && "jeb_".equals(entitySheep.getCustomNameTag())) {

                    int i = entitySheep.ticksExisted / 25 + entitySheep.getEntityId();
                    int j = EnumDyeColor.values().length;
                    int k = i % j;
                    int l = (i + 1) % j;
                    float f7 = (entitySheep.ticksExisted % 25 + f2) / 25.0F;
                    float[] afloat = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(k));
                    float[] afloat1 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(l));
                    if (OptiFineConfig.isCustomColors()) {
                        afloat = CustomColors.getSheepColors(EnumDyeColor.byMetadata(k), afloat);
                        afloat1 = CustomColors.getSheepColors(EnumDyeColor.byMetadata(l), afloat1);
                    }

                    GlStateManager.color(
                            afloat[0] * (1.0F - f7) + afloat1[0] * f7,
                            afloat[1] * (1.0F - f7) + afloat1[1] * f7,
                            afloat[2] * (1.0F - f7) + afloat1[2] * f7);
                    if (!(aint == null)) {

                        float[] afloat2 = EntitySheep.getDyeRgb(entitySheep.getFleeceColor());
                        if (OptiFineConfig.isCustomColors()) {
                            afloat2 = CustomColors.getSheepColors(entitySheep.getFleeceColor(), afloat2);
                        }

                        GlStateManager.color(afloat2[0], afloat2[1], afloat2[2]);
                    }
                } else {

                    float[] afloat2 = EntitySheep.getDyeRgb(entitySheep.getFleeceColor());
                    if (OptiFineConfig.isCustomColors()) {
                        afloat2 = CustomColors.getSheepColors(entitySheep.getFleeceColor(), afloat2);
                    }

                    GlStateManager.color(afloat2[0], afloat2[1], afloat2[2]);
                }
            }

            this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
            this.sheepModel.setLivingAnimations(entitySheep, f, f1, f2);
            this.sheepModel.render(entitySheep, f, f1, f3, f4, f5, f6);
        }
    }

    public boolean shouldCombineTextures() {
        return true;
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
        this.doRenderLayer((EntitySheep) livingEntity, f, f1, f2, f3, f4, f5, f6);
    }

    static {
        net.minecraft.client.renderer.entity.layers.LayerSheepWool.TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    }
}
