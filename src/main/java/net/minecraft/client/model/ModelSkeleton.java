package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class ModelSkeleton extends ModelZombie {
    public void setLivingAnimations(
            LivingEntity livingEntity, float p171_1, float p171_2, float p171_3) {
        this.aimedBow = (((net.minecraft.entity.monster.EntitySkeleton) livingEntity).getSkeletonType() == 1);
        super.setLivingAnimations(livingEntity, p171_1, p171_2, p171_3);
    }

    public ModelSkeleton(float p171_0, boolean p171_1) {
        super(p171_0, 0.0F, 64, 32);
        if (!p171_1) {
            this.bipedRightArm = new net.minecraft.client.model.ModelRenderer(this, 40, 16);
            this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p171_0);
            this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
            this.bipedLeftArm = new net.minecraft.client.model.ModelRenderer(this, 40, 16);
            this.bipedLeftArm.mirror = true;
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, p171_0);
            this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
            this.bipedRightLeg = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
            this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p171_0);
            this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
            this.bipedLeftLeg = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
            this.bipedLeftLeg.mirror = true;
            this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, p171_0);
            this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        }
    }

    public ModelSkeleton() {
        this(0.0F, false);
    }

    public void setRotationAngles(
            float p171_0,
            float p171_1,
            float p171_2,
            float p171_3,
            float p171_4,
            float p171_5,
            Entity entity) {
        super.setRotationAngles(p171_0, p171_1, p171_2, p171_3, p171_4, p171_5, entity);
    }
}
