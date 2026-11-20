package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.MathHelper;

public class EntityLookHelper {
    private float deltaLookPitch;
    private double posY;
    private EntityLiving entity;
    private double posZ;
    private double posX;
    private boolean isLooking;
    private float deltaLookYaw;

    public void onUpdateLook() {
        {
            String text = EntityAIBase.cJ();
            this.entity.rotationPitch = 0.0F;
            String textValue = text;
            if (this.isLooking) {
                this.isLooking = false;
                double deltaPosX = this.posX - this.entity.posX;
                double d1 = this.posY - (this.entity.posY + this.entity.getEyeHeight());
                double deltaPosZ = this.posZ - this.entity.posZ;
                double d3 = MathHelper.sqrt(deltaPosX * deltaPosX + deltaPosZ * deltaPosZ);
                float f = (float) (Math.atan2(deltaPosZ, deltaPosX) * 180.0 / Math.PI) - 90.0F;
                float f1 = (float) (-(Math.atan2(d1, d3) * 180.0 / Math.PI));
                this.entity.rotationPitch =
                        this.updateRotation(this.entity.rotationPitch, f1, this.deltaLookPitch);
                this.entity.rotationYawHead =
                        this.updateRotation(this.entity.rotationYawHead, f, this.deltaLookYaw);
                if (!(textValue != null)) {

                    this.entity.rotationYawHead =
                            this.updateRotation(
                                    this.entity.rotationYawHead, this.entity.renderYawOffset, 10.0F);
                }
            } else {

                this.entity.rotationYawHead =
                        this.updateRotation(
                                this.entity.rotationYawHead, this.entity.renderYawOffset, 10.0F);
            }
        }

        float f2 =
                MathHelper.wrapAngleTo180(
                        this.entity.rotationYawHead - this.entity.renderYawOffset);
        if (!this.entity.getNavigator().noPath()) {
            if (f2 < -75.0F) {
                this.entity.rotationYawHead = this.entity.renderYawOffset - 75.0F;
            }

            if (f2 > 75.0F) {
                this.entity.rotationYawHead = this.entity.renderYawOffset + 75.0F;
            }
        }
    }

    public double getLookPosY() {
        return this.posY;
    }

    public double getLookPosZ() {
        return this.posZ;
    }

    public boolean getIsLooking() {
        return this.isLooking;
    }

    public EntityLookHelper(EntityLiving bjnValue) {
        this.entity = bjnValue;
    }

    public double getLookPosX() {
        return this.posX;
    }

    public void setLookPositionWithEntity(
            Entity entity, float deltaLookYawValue, float deltaLookPitchValue) {
        {
            String text = EntityAIBase.cJ();
            this.posX = entity.posX;
            String textValue = text;
            if (entity instanceof LivingEntity) {
                this.posY = entity.posY + entity.getEyeHeight();
                if (!(textValue != null)) {

                    this.posY =
                            (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0;
                }
            } else {

                this.posY =
                        (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0;
            }
        }

        this.posZ = entity.posZ;
        this.deltaLookYaw = deltaLookYawValue;
        this.deltaLookPitch = deltaLookPitchValue;
        this.isLooking = true;
    }

    public void setLookPosition(
            double posXValue,
            double posYValue,
            double posZValue,
            float deltaLookYawValue,
            float deltaLookPitchValue) {
        this.posX = posXValue;
        this.posY = posYValue;
        this.posZ = posZValue;
        this.deltaLookYaw = deltaLookYawValue;
        this.deltaLookPitch = deltaLookPitchValue;
        this.isLooking = true;
    }

    private float updateRotation(float f, float f1, float f2) {
        float f3 = MathHelper.wrapAngleTo180(f1 - f);
        if (f3 > f2) {
            f3 = f2;
        }

        if (f3 < -f2) {
            f3 = -f2;
        }

        return f + f3;
    }
}
