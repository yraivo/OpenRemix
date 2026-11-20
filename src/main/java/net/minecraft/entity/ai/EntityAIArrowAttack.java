package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.MathHelper;

public class EntityAIArrowAttack extends EntityAIBase {
    private IRangedAttackMob rangedAttackEntityHost;
    private int rangedAttackTime = -1;
    private LivingEntity attackTarget;
    private static String kG;
    private double entityMoveSpeed;
    private float maxAttackDistance;
    private float tE;
    private int field_75318_f;

    private int mu;
    private int maxRangedAttackTime;
    private EntityLiving entityHost;

    public EntityAIArrowAttack(IRangedAttackMob iRangedAttackMob, double d0, int i, float f) {
        this(iRangedAttackMob, d0, i, i, f);
    }

    public EntityAIArrowAttack(
            IRangedAttackMob bhPValue, double entityMoveSpeedValue, int i, int j, float f) {
        if (!(bhPValue instanceof LivingEntity)) {
            throw new IllegalArgumentException(kG);
        }

        this.rangedAttackEntityHost = bhPValue;
        this.entityHost = (EntityLiving) bhPValue;
        this.entityMoveSpeed = entityMoveSpeedValue;
        this.mu = i;
        this.maxRangedAttackTime = j;
        this.tE = f;
        this.maxAttackDistance = f * f;
        this.setMutexBits(3);
    }

    public void updateTask() {
        String text;
        double d0;
        boolean flag;
        {
            String textValue = EntityAIBase.cJ();
            d0 =
                    this.entityHost.getDistanceSq(
                            this.attackTarget.posX,
                            this.attackTarget.getEntityBoundingBox().minY,
                            this.attackTarget.posZ);
            text = textValue;
            flag = this.entityHost.getEntitySenses().canSee(this.attackTarget);
            if (flag) {
                this.field_75318_f++;
                if (!(text != null)) {

                    this.field_75318_f = 0;
                }
            } else {

                this.field_75318_f = 0;
            }
        }

        {
            if (d0 <= this.maxAttackDistance && this.field_75318_f >= 20) {
                this.entityHost.getNavigator().clearPathEntity();
                if (!(text != null)) {

                    this.entityHost
                            .getNavigator()
                            .tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
                }
            } else {

                this.entityHost
                        .getNavigator()
                        .tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
            }
        }

        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
        if (--this.rangedAttackTime == 0) {
            if (d0 > this.maxAttackDistance || !flag) {
                return;
            }

            float fx = MathHelper.sqrt(d0) / this.tE;
            float f1 = MathHelper.clamp(fx, 0.1F, 1.0F);
            this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, f1);
            this.rangedAttackTime = MathHelper.floor(fx * (this.maxRangedAttackTime - this.mu) + this.mu);
            if (text != null) {
                return;
            }
        }

        if (this.rangedAttackTime < 0) {
            float f2 = MathHelper.sqrt(d0) / this.tE;
            this.rangedAttackTime = MathHelper.floor(f2 * (this.maxRangedAttackTime - this.mu) + this.mu);
        }
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public void resetTask() {
        this.attackTarget = null;
        this.field_75318_f = 0;
        this.rangedAttackTime = -1;
    }

    public boolean shouldExecute() {
        LivingEntity bhQValue = this.entityHost.getAttackTarget();
        if (bhQValue == null) {
            return false;
        }

        this.attackTarget = bhQValue;
        return true;
    }

    // region String decoding support

    // endregion

    public boolean continueExecuting() {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
    }

    static {
        kG = "ArrowAttackGoal requires Mob implements RangedAttackMob";
    }

    // region String decoding support

    // endregion
}
