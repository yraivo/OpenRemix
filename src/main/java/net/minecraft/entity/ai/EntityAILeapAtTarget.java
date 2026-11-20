package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.MathHelper;

public class EntityAILeapAtTarget extends EntityAIBase {
    private static String[] packedTextFragments;
    EntityLiving leaper;
    float leapMotionY;
    LivingEntity leapTarget;

    // region String decoding support

    // endregion

    public EntityAILeapAtTarget(EntityLiving bhtValue, float leapMotionYValue) {
        this.leaper = bhtValue;
        this.leapMotionY = leapMotionYValue;
        this.setMutexBits(5);
    }

    public void startExecuting() {
        double deltaPosX = this.leapTarget.posX - this.leaper.posX;
        double deltaPosZ = this.leapTarget.posZ - this.leaper.posZ;
        float f = MathHelper.sqrt(deltaPosX * deltaPosX + deltaPosZ * deltaPosZ);
        EntityAIBase.cJ();
        this.leaper.motionX =
                this.leaper.motionX + (deltaPosX / f * 0.5 * 0.8F + this.leaper.motionX * 0.2F);
        this.leaper.motionZ =
                this.leaper.motionZ + (deltaPosZ / f * 0.5 * 0.8F + this.leaper.motionZ * 0.2F);
        this.leaper.motionY = this.leapMotionY;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            EntityAIBase.b(packedTextFragments[0]);
        }
    }

    public boolean continueExecuting() {
        return !this.leaper.onGround;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public boolean shouldExecute() {
        this.leapTarget = this.leaper.getAttackTarget();
        if (this.leapTarget == null) {
            return false;
        }

        double distanceSqToEntity = this.leaper.getDistanceSqToEntity(this.leapTarget);
        return !(distanceSqToEntity >= 4.0) || !(distanceSqToEntity <= 16.0)
                ? false
                : (!this.leaper.onGround ? false : this.leaper.getRNG().nextInt(5) == 0);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.entity.ai.EntityAILeapAtTarget.packedTextFragments[0] = new String("qMJD9");
    }
    // endregion
}
