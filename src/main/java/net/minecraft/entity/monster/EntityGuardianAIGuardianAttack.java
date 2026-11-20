package net.minecraft.entity.monster;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;

class EntityGuardianAIGuardianAttack extends EntityAIBase {
    EntityGuardian bjR;
    private int tickCounter;
    private EntityGuardian theEntity;

    public boolean continueExecuting() {
        return super.continueExecuting()
                && (this.theEntity.isElder()
                        || this.theEntity.getDistanceSqToEntity(this.theEntity.getAttackTarget())
                                > 9.0);
    }

    public EntityGuardianAIGuardianAttack(EntityGuardian bjRValue) {
        this.bjR = bjRValue;
        this.theEntity = this.bjR;
        this.setMutexBits(3);
    }

    public void startExecuting() {
        this.tickCounter = -10;
        this.theEntity.getNavigator().clearPathEntity();
        this.theEntity
                .getLookHelper()
                .setLookPositionWithEntity(this.theEntity.getAttackTarget(), 90.0F, 90.0F);
        this.theEntity.isAirBorne = true;
    }

    public void resetTask() {
        EntityGuardian.access$000(this.theEntity, 0);
        this.theEntity.setAttackTarget((LivingEntity) null);
        EntityGuardian.access$100(this.theEntity).makeUpdate();
    }

    public void updateTask() {
        int j = EntitySpiderGroupData.method_7eec03da329a017ec143();
        LivingEntity livingEntity = this.theEntity.getAttackTarget();
        int i = j;
        this.theEntity.getNavigator().clearPathEntity();
        this.theEntity.getLookHelper().setLookPositionWithEntity(livingEntity, 90.0F, 90.0F);
        if (!this.theEntity.canEntityBeSeen(livingEntity)) {
            this.theEntity.setAttackTarget((LivingEntity) null);
            if (i != 0) {
                return;
            }
        }

        label38:
        {
            this.tickCounter++;
            if (this.tickCounter == 0) {
                EntityGuardian.access$000(
                        this.theEntity, this.theEntity.getAttackTarget().getEntityId());
                this.theEntity.world.setEntityState(this.theEntity, (byte) 21);
                if (i != 0) {
                    break label38;
                }
            }

            if (this.tickCounter >= this.theEntity.func_175464_ck()) {
                float f = 1.0F;
                if (this.theEntity.world.getDifficulty() == EnumDifficulty.HARD) {
                    f += 2.0F;
                }

                if (this.theEntity.isElder()) {
                    f += 2.0F;
                }

                livingEntity.attackEntityFrom(
                        DamageSource.causeIndirectMagicDamage(this.theEntity, this.theEntity), f);
                livingEntity.attackEntityFrom(
                        DamageSource.causeMobDamage(this.theEntity),
                        (float)
                                this.theEntity
                                        .getEntityAttribute(SharedMonsterAttributes.attackDamage)
                                        .getAttributeValue());
                this.theEntity.setAttackTarget((LivingEntity) null);
                if (i != 0) {
                    break label38;
                }
            }

            if (this.tickCounter >= 60 && this.tickCounter % 20 == 0) {}
        }

        super.updateTask();
    }

    public boolean shouldExecute() {
        LivingEntity livingEntity = this.theEntity.getAttackTarget();
        return livingEntity != null && livingEntity.isEntityAlive();
    }
}
