package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelWitch extends ModelVillager {
    private ModelRenderer witchHat;
    public boolean jj;
    private ModelRenderer auu = new ModelRenderer(this).setTextureSize(64, 128);

    public ModelWitch(float f) {
        super(f, 0.0F, 64, 128);
        this.auu.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.auu.setTextureOffset(0, 0).addBox(0.0F, 3.0F, -6.75F, 1, 1, 1, -0.25F);
        this.villagerNose.addChild(this.auu);
        this.witchHat = new ModelRenderer(this).setTextureSize(64, 128);
        this.witchHat.setRotationPoint(-5.0F, -10.03125F, -5.0F);
        this.witchHat.setTextureOffset(0, 64).addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
        this.villagerHead.addChild(this.witchHat);
        ModelRenderer modelRenderer = new ModelRenderer(this).setTextureSize(64, 128);
        modelRenderer.setRotationPoint(1.75F, -4.0F, 2.0F);
        modelRenderer.setTextureOffset(0, 76).addBox(0.0F, 0.0F, 0.0F, 7, 4, 7);
        modelRenderer.rotateAngleX = -0.05235988F;
        modelRenderer.rotateAngleZ = 0.02617994F;
        this.witchHat.addChild(modelRenderer);
        ModelRenderer modelRendererValue = new ModelRenderer(this).setTextureSize(64, 128);
        modelRendererValue.setRotationPoint(1.75F, -4.0F, 2.0F);
        modelRendererValue.setTextureOffset(0, 87).addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
        modelRendererValue.rotateAngleX = -0.10471976F;
        modelRendererValue.rotateAngleZ = 0.05235988F;
        modelRenderer.addChild(modelRendererValue);
        ModelRenderer modelRendererValue2 = new ModelRenderer(this).setTextureSize(64, 128);
        modelRendererValue2.setRotationPoint(1.75F, -2.0F, 2.0F);
        modelRendererValue2.setTextureOffset(0, 95).addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
        modelRendererValue2.rotateAngleX = (float) (-Math.PI / 15);
        modelRendererValue2.rotateAngleZ = 0.10471976F;
        modelRendererValue.addChild(modelRendererValue2);
    }

    public void setRotationAngles(
            float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        this.villagerNose.offsetX = this.villagerNose.offsetY = this.villagerNose.offsetZ = 0.0F;
        float f6 = 0.01F * (entity.getEntityId() % 10);
        this.villagerNose.rotateAngleX =
                MathHelper.sin(entity.ticksExisted * f6) * 4.5F * (float) Math.PI / 180.0F;
        this.villagerNose.rotateAngleY = 0.0F;
        this.villagerNose.rotateAngleZ =
                MathHelper.cos(entity.ticksExisted * f6) * 2.5F * (float) Math.PI / 180.0F;
        if (this.jj) {
            this.villagerNose.rotateAngleX = -0.9F;
            this.villagerNose.offsetZ = -0.09375F;
            this.villagerNose.offsetY = 0.1875F;
        }
    }
}
