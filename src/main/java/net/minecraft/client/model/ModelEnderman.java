package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelEnderman extends ModelBiped {
    public boolean isAttacking;
    public boolean isCarrying;

    public ModelEnderman(float p171_0) {
        super(0.0F, -14.0F, 64, 32);
        float f1 = -14.0F;
        this.bipedHeadwear = new net.minecraft.client.model.ModelRenderer(this, 0, 16);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, p171_0 - 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.bipedBody = new net.minecraft.client.model.ModelRenderer(this, 32, 16);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p171_0);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + f1, 0.0F);
        this.bipedRightArm = new net.minecraft.client.model.ModelRenderer(this, 56, 0);
        this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, p171_0);
        this.bipedRightArm.setRotationPoint(-3.0F, 2.0F + f1, 0.0F);
        this.bipedLeftArm = new net.minecraft.client.model.ModelRenderer(this, 56, 0);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, p171_0);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + f1, 0.0F);
        this.bipedRightLeg = new net.minecraft.client.model.ModelRenderer(this, 56, 0);
        this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, p171_0);
        this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F + f1, 0.0F);
        this.bipedLeftLeg = new net.minecraft.client.model.ModelRenderer(this, 56, 0);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, p171_0);
        this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F + f1, 0.0F);
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
        this.bipedHead.showModel = true;
        float rotationPointY = -14.0F;
        this.bipedBody.rotateAngleX = 0.0F;
        this.bipedBody.rotationPointY = rotationPointY;
        this.bipedBody.rotationPointZ = -0.0F;
        this.bipedRightLeg.rotateAngleX -= 0.0F;
        this.bipedLeftLeg.rotateAngleX -= 0.0F;
        this.bipedRightArm.rotateAngleX = (float) (this.bipedRightArm.rotateAngleX * 0.5);
        this.bipedLeftArm.rotateAngleX = (float) (this.bipedLeftArm.rotateAngleX * 0.5);
        this.bipedRightLeg.rotateAngleX = (float) (this.bipedRightLeg.rotateAngleX * 0.5);
        this.bipedLeftLeg.rotateAngleX = (float) (this.bipedLeftLeg.rotateAngleX * 0.5);
        float rotateAngleX = 0.4F;
        if (this.bipedRightArm.rotateAngleX > rotateAngleX) {
            this.bipedRightArm.rotateAngleX = rotateAngleX;
        }

        if (this.bipedLeftArm.rotateAngleX > rotateAngleX) {
            this.bipedLeftArm.rotateAngleX = rotateAngleX;
        }

        if (this.bipedRightArm.rotateAngleX < -rotateAngleX) {
            this.bipedRightArm.rotateAngleX = -rotateAngleX;
        }

        if (this.bipedLeftArm.rotateAngleX < -rotateAngleX) {
            this.bipedLeftArm.rotateAngleX = -rotateAngleX;
        }

        if (this.bipedRightLeg.rotateAngleX > rotateAngleX) {
            this.bipedRightLeg.rotateAngleX = rotateAngleX;
        }

        if (this.bipedLeftLeg.rotateAngleX > rotateAngleX) {
            this.bipedLeftLeg.rotateAngleX = rotateAngleX;
        }

        if (this.bipedRightLeg.rotateAngleX < -rotateAngleX) {
            this.bipedRightLeg.rotateAngleX = -rotateAngleX;
        }

        if (this.bipedLeftLeg.rotateAngleX < -rotateAngleX) {
            this.bipedLeftLeg.rotateAngleX = -rotateAngleX;
        }

        if (this.isCarrying) {
            this.bipedRightArm.rotateAngleX = -0.5F;
            this.bipedLeftArm.rotateAngleX = -0.5F;
            this.bipedRightArm.rotateAngleZ = 0.05F;
            this.bipedLeftArm.rotateAngleZ = -0.05F;
        }

        this.bipedRightArm.rotationPointZ = 0.0F;
        this.bipedLeftArm.rotationPointZ = 0.0F;
        this.bipedRightLeg.rotationPointZ = 0.0F;
        this.bipedLeftLeg.rotationPointZ = 0.0F;
        this.bipedRightLeg.rotationPointY = 9.0F + rotationPointY;
        this.bipedLeftLeg.rotationPointY = 9.0F + rotationPointY;
        this.bipedHead.rotationPointZ = -0.0F;
        this.bipedHead.rotationPointY = rotationPointY + 1.0F;
        this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
        this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
        this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
        if (this.isAttacking) {
            float f8 = 1.0F;
            this.bipedHead.rotationPointY -= f8 * 5.0F;
        }
    }
}
