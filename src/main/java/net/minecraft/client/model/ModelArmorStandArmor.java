package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelArmorStandArmor extends ModelBiped {
    public ModelArmorStandArmor() {
        this(0.0F);
    }

    protected ModelArmorStandArmor(float p171_0, int p171_1, int p171_2) {
        super(p171_0, 0.0F, p171_1, p171_2);
    }

    public void setRotationAngles(
            float p171_0,
            float p171_1,
            float p171_2,
            float p171_3,
            float p171_4,
            float p171_5,
            Entity entity) {
        if (entity instanceof net.minecraft.entity.item.EntityArmorStand) {
            net.minecraft.entity.item.EntityArmorStand entityArmorStand = (net.minecraft.entity.item.EntityArmorStand) entity;
            this.bipedHead.rotateAngleX =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getHeadRotation().getX();
            this.bipedHead.rotateAngleY =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getHeadRotation().getY();
            this.bipedHead.rotateAngleZ =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getHeadRotation().getZ();
            this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
            this.bipedBody.rotateAngleX =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getBodyRotation().getX();
            this.bipedBody.rotateAngleY =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getBodyRotation().getY();
            this.bipedBody.rotateAngleZ =
                    (float) (java.lang.Math.PI / 180.0) * entityArmorStand.getBodyRotation().getZ();
            this.bipedLeftArm.rotateAngleX =
                    (float) (java.lang.Math.PI / 180.0)
                            * entityArmorStand.getLeftArmRotation().getX();
            this.bipedLeftArm.rotateAngleY =
                    (float) (java.lang.Math.PI / 180.0)
                            * entityArmorStand.getLeftArmRotation().getY();
            this.bipedLeftArm.rotateAngleZ =
                    (float) (java.lang.Math.PI / 180.0)
                            * entityArmorStand.getLeftArmRotation().getZ();
            this.bipedRightArm.rotateAngleX =
                    (float) (java.lang.Math.PI / 180.0)
                            * entityArmorStand.getRightArmRotation().getX();
            this.bipedRightArm.rotateAngleY =
                    (float) (java.lang.Math.PI / 180.0)
                            * entityArmorStand.getRightArmRotation().getY();
            this.bipedRightArm.rotateAngleZ =
                    (float) (java.lang.Math.PI / 180.0)
                            * entityArmorStand.getRightArmRotation().getZ();
            this.bipedLeftLeg.rotateAngleX =
                    (float) (java.lang.Math.PI / 180.0)
                            * entityArmorStand.getLeftLegRotation().getX();
            this.bipedLeftLeg.rotateAngleY =
                    (float) (java.lang.Math.PI / 180.0)
                            * entityArmorStand.getLeftLegRotation().getY();
            this.bipedLeftLeg.rotateAngleZ =
                    (float) (java.lang.Math.PI / 180.0)
                            * entityArmorStand.getLeftLegRotation().getZ();
            this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
            this.bipedRightLeg.rotateAngleX =
                    (float) (java.lang.Math.PI / 180.0)
                            * entityArmorStand.getRightLegRotation().getX();
            this.bipedRightLeg.rotateAngleY =
                    (float) (java.lang.Math.PI / 180.0)
                            * entityArmorStand.getRightLegRotation().getY();
            this.bipedRightLeg.rotateAngleZ =
                    (float) (java.lang.Math.PI / 180.0)
                            * entityArmorStand.getRightLegRotation().getZ();
            this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
            net.minecraft.client.model.ModelBase.copyModelAngles(this.bipedHead, this.bipedHeadwear);
        }
    }

    public ModelArmorStandArmor(float p171_0) {
        this(p171_0, 64, 32);
    }
}
