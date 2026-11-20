package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ModelSheep1 extends ModelQuadruped {
    private float headRotationAngleX;

    public void setRotationAngles(
            float p171_0,
            float p171_1,
            float p171_2,
            float p171_3,
            float p171_4,
            float p171_5,
            Entity entity) {
        super.setRotationAngles(p171_0, p171_1, p171_2, p171_3, p171_4, p171_5, entity);
        this.head.rotateAngleX = this.headRotationAngleX;
    }

    public void setLivingAnimations(
            LivingEntity livingEntity, float p171_1, float p171_2, float p171_3) {
        super.setLivingAnimations(livingEntity, p171_1, p171_2, p171_3);
        this.head.rotationPointY =
                6.0F + ((net.minecraft.entity.passive.EntitySheep) livingEntity).getHeadRotationPointY(p171_3) * 9.0F;
        this.headRotationAngleX = ((net.minecraft.entity.passive.EntitySheep) livingEntity).getHeadRotationAngleX(p171_3);
    }

    public ModelSheep1() {
        super(12, 0.0F);
        this.head = new net.minecraft.client.model.ModelRenderer(this, 0, 0);
        this.head.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
        this.head.setRotationPoint(0.0F, 6.0F, -8.0F);
        this.body = new net.minecraft.client.model.ModelRenderer(this, 28, 8);
        this.body.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
        this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
        float f = 0.5F;
        this.leg1 = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
        this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg1.setRotationPoint(-3.0F, 12.0F, 7.0F);
        this.leg2 = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
        this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg2.setRotationPoint(3.0F, 12.0F, 7.0F);
        this.leg3 = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
        this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg3.setRotationPoint(-3.0F, 12.0F, -5.0F);
        this.leg4 = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
        this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, f);
        this.leg4.setRotationPoint(3.0F, 12.0F, -5.0F);
    }
}
