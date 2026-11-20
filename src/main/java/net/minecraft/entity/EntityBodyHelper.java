package net.minecraft.entity;

import net.minecraft.util.MathHelper;

public class EntityBodyHelper {
    private LivingEntity theLiving;
    private int rotationTickCounter;
    private float prevRenderYawHead;

    public EntityBodyHelper(LivingEntity aZkValue) {
        this.theLiving = aZkValue;
    }

    public void updateRenderAngles() {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        double d0 = this.theLiving.posX - this.theLiving.prevPosX;
        String[] astring = astring1;
        double d1 = this.theLiving.posZ - this.theLiving.prevPosZ;
        if (d0 * d0 + d1 * d1 > 2.5000003E-7F) {
            this.theLiving.renderYawOffset = this.theLiving.rotationYaw;
            this.theLiving.rotationYawHead =
                    this.computeAngleWithBound(
                            this.theLiving.renderYawOffset, this.theLiving.rotationYawHead, 75.0F);
            this.prevRenderYawHead = this.theLiving.rotationYawHead;
            this.rotationTickCounter = 0;
            if (astring == null) {
                return;
            }
        }

        float f;
        {
            f = 75.0F;
            if (Math.abs(this.theLiving.rotationYawHead - this.prevRenderYawHead) > 15.0F) {
                this.rotationTickCounter = 0;
                this.prevRenderYawHead = this.theLiving.rotationYawHead;
                if (!(astring == null)) {

                    this.rotationTickCounter++;

                    if (this.rotationTickCounter > 10) {
                        f = Math.max(1.0F - (this.rotationTickCounter - 10) / 10.0F, 0.0F) * 75.0F;
                    }
                }
            } else {

                this.rotationTickCounter++;

                if (this.rotationTickCounter > 10) {
                    f = Math.max(1.0F - (this.rotationTickCounter - 10) / 10.0F, 0.0F) * 75.0F;
                }
            }
        }

        this.theLiving.renderYawOffset =
                this.computeAngleWithBound(
                        this.theLiving.rotationYawHead, this.theLiving.renderYawOffset, f);
    }

    private float computeAngleWithBound(float f, float f1, float f2) {
        float f3 = MathHelper.wrapAngleTo180(f - f1);
        if (f3 < -f2) {
            f3 = -f2;
        }

        if (f3 >= f2) {
            f3 = f2;
        }

        return f - f3;
    }
}
