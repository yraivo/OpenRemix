package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelArmorStand extends ModelArmorStandArmor {
    public ModelRenderer standLeftSide;
    public ModelRenderer standWaist;
    public ModelRenderer standBase;
    public ModelRenderer standRightSide;

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        label17:
        {
            String[] astring1 = ModelBase.getLegacyModelFlowMarker();
            super.render(entity, f, f1, f2, f3, f4, f5);
            GlStateManager.pushMatrix();
            String[] astring = astring1;
            if (this.isChild) {
                float f6 = 2.0F;
                GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
                GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
                this.standRightSide.render(f5);
                this.standLeftSide.render(f5);
                this.standWaist.render(f5);
                this.standBase.render(f5);
                if (astring == null) {
                    break label17;
                }
            }

            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            this.standRightSide.render(f5);
            this.standLeftSide.render(f5);
            this.standWaist.render(f5);
            this.standBase.render(f5);
        }

        GlStateManager.popMatrix();
    }

    public void postRenderHiddenArm(float p171_0) {
        boolean showModel = this.bipedRightArm.showModel;
        this.bipedRightArm.showModel = true;
        super.postRenderHiddenArm(p171_0);
        this.bipedRightArm.showModel = showModel;
    }

    public ModelArmorStand() {
        this(0.0F);
    }

    public ModelArmorStand(float p171_0) {
        super(p171_0, 64, 64);
        this.bipedHead = new net.minecraft.client.model.ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-1.0F, -7.0F, -1.0F, 2, 7, 2, p171_0);
        this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedBody = new net.minecraft.client.model.ModelRenderer(this, 0, 26);
        this.bipedBody.addBox(-6.0F, 0.0F, -1.5F, 12, 3, 3, p171_0);
        this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.bipedRightArm = new net.minecraft.client.model.ModelRenderer(this, 24, 0);
        this.bipedRightArm.addBox(-2.0F, -2.0F, -1.0F, 2, 12, 2, p171_0);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.bipedLeftArm = new net.minecraft.client.model.ModelRenderer(this, 32, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(0.0F, -2.0F, -1.0F, 2, 12, 2, p171_0);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedRightLeg = new net.minecraft.client.model.ModelRenderer(this, 8, 0);
        this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, p171_0);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.bipedLeftLeg = new net.minecraft.client.model.ModelRenderer(this, 40, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, p171_0);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.standRightSide = new net.minecraft.client.model.ModelRenderer(this, 16, 0);
        this.standRightSide.addBox(-3.0F, 3.0F, -1.0F, 2, 7, 2, p171_0);
        this.standRightSide.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.standRightSide.showModel = true;
        this.standLeftSide = new net.minecraft.client.model.ModelRenderer(this, 48, 16);
        this.standLeftSide.addBox(1.0F, 3.0F, -1.0F, 2, 7, 2, p171_0);
        this.standLeftSide.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.standWaist = new net.minecraft.client.model.ModelRenderer(this, 0, 48);
        this.standWaist.addBox(-4.0F, 10.0F, -1.0F, 8, 2, 2, p171_0);
        this.standWaist.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.standBase = new net.minecraft.client.model.ModelRenderer(this, 0, 32);
        this.standBase.addBox(-6.0F, 11.0F, -6.0F, 12, 1, 12, p171_0);
        this.standBase.setRotationPoint(0.0F, 12.0F, 0.0F);
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
        if (entity instanceof net.minecraft.entity.item.EntityArmorStand) {
            net.minecraft.entity.item.EntityArmorStand entityArmorStand = (net.minecraft.entity.item.EntityArmorStand) entity;
            this.bipedLeftArm.showModel = entityArmorStand.getShowArms();
            this.bipedRightArm.showModel = entityArmorStand.getShowArms();
            this.standBase.showModel = (!entityArmorStand.hasNoBasePlate());
            this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
            this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
            this.standRightSide.rotateAngleX =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getBodyRotation().getX();
            this.standRightSide.rotateAngleY =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getBodyRotation().getY();
            this.standRightSide.rotateAngleZ =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getBodyRotation().getZ();
            this.standLeftSide.rotateAngleX =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getBodyRotation().getX();
            this.standLeftSide.rotateAngleY =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getBodyRotation().getY();
            this.standLeftSide.rotateAngleZ =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getBodyRotation().getZ();
            this.standWaist.rotateAngleX =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getBodyRotation().getX();
            this.standWaist.rotateAngleY =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getBodyRotation().getY();
            this.standWaist.rotateAngleZ =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getBodyRotation().getZ();
            float f6 =
                    (entityArmorStand.getLeftLegRotation().getX()
                                    + entityArmorStand.getRightLegRotation().getX())
                            / 2.0F;
            float f7 =
                    (entityArmorStand.getLeftLegRotation().getY()
                                    + entityArmorStand.getRightLegRotation().getY())
                            / 2.0F;
            float f8 =
                    (entityArmorStand.getLeftLegRotation().getZ()
                                    + entityArmorStand.getRightLegRotation().getZ())
                            / 2.0F;
            this.standBase.rotateAngleX = 0.0F;
            this.standBase.rotateAngleY = (float) (java.lang.Math.PI / 180.0) * -entity.rotationYaw;
            this.standBase.rotateAngleZ = 0.0F;
        }
    }
}
