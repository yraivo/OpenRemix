package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.MathHelper;

class EntityGuardianGuardianMoveHelper extends EntityMoveHelper {
    private EntityGuardian entityGuardian;
    EntityGuardian bju;

    public void onUpdateMoveHelper() {
        int i = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        if (this.update && !this.entityGuardian.getNavigator().noPath()) {
            double deltaPosX = this.posX - this.entityGuardian.posX;
            double deltaPosY = this.posY - this.entityGuardian.posY;
            double deltaPosZ = this.posZ - this.entityGuardian.posZ;
            double d3 = deltaPosX * deltaPosX + deltaPosY * deltaPosY + deltaPosZ * deltaPosZ;
            d3 = MathHelper.sqrt(d3);
            deltaPosY /= d3;
            float f = (float) (Math.atan2(deltaPosZ, deltaPosX) * 180.0 / Math.PI) - 90.0F;
            this.entityGuardian.rotationYaw = this.limitAngle(this.entityGuardian.rotationYaw, f, 30.0F);
            this.entityGuardian.renderYawOffset = this.entityGuardian.rotationYaw;
            float f1 =
                    (float)
                            (this.speed
                                    * this.entityGuardian
                                            .getEntityAttribute(
                                                    SharedMonsterAttributes.movementSpeed)
                                            .getAttributeValue());
            this.entityGuardian.setAIMoveSpeed(
                    this.entityGuardian.getAIMoveSpeed() + (f1 - this.entityGuardian.getAIMoveSpeed()) * 0.125F);
            double d4 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.5) * 0.05;
            double d5 = Math.cos(this.entityGuardian.rotationYaw * (float) Math.PI / 180.0F);
            double d6 = Math.sin(this.entityGuardian.rotationYaw * (float) Math.PI / 180.0F);
            this.entityGuardian.motionX += d4 * d5;
            this.entityGuardian.motionZ += d4 * d6;
            d4 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.75) * 0.05;
            this.entityGuardian.motionY += d4 * (d6 + d5) * 0.25;
            this.entityGuardian.motionY = this.entityGuardian.motionY + this.entityGuardian.getAIMoveSpeed() * deltaPosY * 0.1;
            EntityLookHelper lookHelper = this.entityGuardian.getLookHelper();
            double d7 = this.entityGuardian.posX + deltaPosX / d3 * 2.0;
            double d8 = this.entityGuardian.getEyeHeight() + this.entityGuardian.posY + deltaPosY / d3 * 1.0;
            double d9 = this.entityGuardian.posZ + deltaPosZ / d3 * 2.0;
            double lookPosX = lookHelper.getLookPosX();
            double lookPosY = lookHelper.getLookPosY();
            double lookPosZ = lookHelper.getLookPosZ();
            if (!lookHelper.getIsLooking()) {
                lookPosX = d7;
                lookPosY = d8;
                lookPosZ = d9;
            }

            this.entityGuardian
                    .getLookHelper()
                    .setLookPosition(
                            lookPosX + (d7 - lookPosX) * 0.125,
                            lookPosY + (d8 - lookPosY) * 0.125,
                            lookPosZ + (d9 - lookPosZ) * 0.125,
                            10.0F,
                            40.0F);
            EntityGuardian.access$200(this.entityGuardian, true);
            if (i == 0) {
                return;
            }
        }

        this.entityGuardian.setAIMoveSpeed(0.0F);
        EntityGuardian.access$200(this.entityGuardian, false);
    }

    public EntityGuardianGuardianMoveHelper(EntityGuardian bjuValue) {
        super(bjuValue);
        this.bju = bjuValue;
        this.entityGuardian = this.bju;
    }
}
