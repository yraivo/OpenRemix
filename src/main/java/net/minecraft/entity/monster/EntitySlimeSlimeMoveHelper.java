package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;

class EntitySlimeSlimeMoveHelper extends EntityMoveHelper {
    private SlimeEntity slime;
    SlimeEntity bjw;
    private boolean field_179923_j;
    private float field_179922_g;
    private int field_179924_h;

    public EntitySlimeSlimeMoveHelper(SlimeEntity bjwValue) {
        super(bjwValue);
        this.bjw = bjwValue;
        this.slime = this.bjw;
    }

    public void func_179920_a(float f, boolean flag) {
        this.field_179922_g = f;
        this.field_179923_j = flag;
    }

    public void setSpeed(double speedValue) {
        this.speed = speedValue;
        this.update = true;
    }

    public void onUpdateMoveHelper() {
        int j = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        this.entity.rotationYaw =
                this.limitAngle(
                        this.entity.rotationYaw, this.field_179922_g, 30.0F);
        this.entity.rotationYawHead = this.entity.rotationYaw;
        this.entity.renderYawOffset = this.entity.rotationYaw;
        int i = j;
        if (!this.update) {
            this.entity.setMoveForward(0.0F);
            if (i == 0) {
                return;
            }
        }

        this.update = false;
        if (this.entity.onGround) {
            this.entity.setAIMoveSpeed(
                    (float)
                            (this.speed
                                    * this.entity
                                            .getEntityAttribute(
                                                    SharedMonsterAttributes.movementSpeed)
                                            .getAttributeValue()));
            if (this.field_179924_h-- <= 0) {
                this.field_179924_h = this.slime.getJumpDelay();
                if (this.field_179923_j) {
                    this.field_179924_h /= 3;
                }

                this.slime.getJumpHelper().setJumping();
                if (!this.slime.makesSoundOnJump()) {
                    return;
                }

                this.slime.playSound(
                        this.slime.getJumpSound(),
                        this.slime.getSoundVolume(),
                        ((this.slime.getRNG().nextFloat() - this.slime.getRNG().nextFloat()) * 0.2F
                                        + 1.0F)
                                * 0.8F);
                if (i == 0) {
                    return;
                }
            }

            this.slime.moveStrafing = this.slime.moveForward = 0.0F;
            this.entity.setAIMoveSpeed(0.0F);
            if (i == 0) {
                return;
            }
        }

        this.entity.setAIMoveSpeed(
                (float)
                        (this.speed
                                * this.entity
                                        .getEntityAttribute(SharedMonsterAttributes.movementSpeed)
                                        .getAttributeValue()));
    }
}
