package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.MathHelper;

public class EntityMoveHelper {
    protected EntityLiving entity;
    protected double posX;
    protected double speed;
    protected double posY;
    protected double posZ;
    protected boolean update;

    public double getZ() {
        return this.posZ;
    }

    public void onUpdateMoveHelper() {
        this.entity.setMoveForward(0.0F);
        if (this.update) {
            this.update = false;
            int i = MathHelper.floor(this.entity.getEntityBoundingBox().minY + 0.5);
            double deltaPosX = this.posX - this.entity.posX;
            double deltaPosZ = this.posZ - this.entity.posZ;
            double d2 = this.posY - i;
            double d3 = deltaPosX * deltaPosX + d2 * d2 + deltaPosZ * deltaPosZ;
            if (d3 >= 2.5000003E-7F) {
                float fx = (float) (Math.atan2(deltaPosZ, deltaPosX) * 180.0 / Math.PI) - 90.0F;
                this.entity.rotationYaw =
                        this.limitAngle(this.entity.rotationYaw, fx, 30.0F);
                this.entity.setAIMoveSpeed(
                        (float)
                                (this.speed
                                        * this.entity
                                                .getEntityAttribute(
                                                        SharedMonsterAttributes.movementSpeed)
                                                .getAttributeValue()));
                if (d2 > 0.0 && deltaPosX * deltaPosX + deltaPosZ * deltaPosZ < 1.0) {
                    this.entity.getJumpHelper().setJumping();
                }
            }
        }
    }

    public boolean isUpdating() {
        return this.update;
    }

    public void setMoveTo(double posXValue, double posYValue, double posZValue, double speedValue) {
        this.posX = posXValue;
        this.posY = posYValue;
        this.posZ = posZValue;
        this.speed = speedValue;
        this.update = true;
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getY() {
        return this.posY;
    }

    protected float limitAngle(float f, float f1, float f2) {
        String text = EntityAIBase.cJ();
        float f3 = MathHelper.wrapAngleTo180(f1 - f);
        String textValue = text;
        if (f3 > f2) {
            f3 = f2;
        }

        if (f3 < -f2) {
            f3 = -f2;
        }

        float f4 = f + f3;
        if (f4 < 0.0F) {
            f4 += 360.0F;
            if (textValue != null) {
                return f4;
            }
        }

        if (f4 > 360.0F) {
            f4 -= 360.0F;
        }

        return f4;
    }

    public double getX() {
        return this.posX;
    }

    public EntityMoveHelper(EntityLiving bjnValue) {
        this.entity = bjnValue;
        this.posX = bjnValue.posX;
        this.posY = bjnValue.posY;
        this.posZ = bjnValue.posZ;
    }
}
