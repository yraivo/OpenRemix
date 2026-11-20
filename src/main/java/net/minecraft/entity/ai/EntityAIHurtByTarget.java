package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.BoundingBox;

public class EntityAIHurtByTarget extends EntityAITarget {
    private boolean entityCallsForHelp;
    private Class[] targetClasses;
    private int revengeTimerOld;

    public boolean shouldExecute() {
        int revengeTimer = this.taskOwner.getRevengeTimer();
        return revengeTimer != this.revengeTimerOld
                && this.isSuitableTarget(this.taskOwner.getAITarget(), false);
    }

    public EntityAIHurtByTarget(
            EntityCreature entityCreature, boolean entityCallsForHelpValue, Class... aclass) {
        super(entityCreature, false);
        this.entityCallsForHelp = entityCallsForHelpValue;
        this.targetClasses = aclass;
        this.setMutexBits(1);
    }

    public void startExecuting() {
        String text = EntityAIBase.cJ();
        this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
        String textValue = text;
        this.revengeTimerOld = this.taskOwner.getRevengeTimer();
        if (this.entityCallsForHelp) {
            double targetDistance = this.getTargetDistance();

            for (java.util.Iterator<?> __recoveryIterator171_1017 =
                            (this.taskOwner.world.getEntitiesWithinAABB(
                                            this.taskOwner.getClass(),
                                            new BoundingBox(
                                                            this.taskOwner.posX,
                                                            this.taskOwner.posY,
                                                            this.taskOwner.posZ,
                                                            this.taskOwner.posX + 1.0,
                                                            this.taskOwner.posY + 1.0,
                                                            this.taskOwner.posZ + 1.0)
                                                    .expand(targetDistance, 10.0, targetDistance)))
                                    .iterator();
                    (__recoveryIterator171_1017).hasNext(); ) {
                EntityCreature entityCreature =
                        (EntityCreature) (__recoveryIterator171_1017).next();
                if (this.taskOwner != entityCreature
                        && entityCreature.getAttackTarget() == null
                        && !entityCreature.isOnSameTeam(this.taskOwner.getAITarget())) {
                    byte b0 = 0;

                    for (Class oclass : this.targetClasses) {
                        if (entityCreature.getClass() == oclass) {
                            b0 = 1;
                            if (textValue != null) {
                                break;
                            }
                        }

                        if (textValue == null) {
                            break;
                        }
                    }

                    if (b0 == 0) {
                        this.setEntityAttackTarget(entityCreature, this.taskOwner.getAITarget());
                    }
                }

                if (textValue == null) {
                    break;
                }
            }
        }

        super.startExecuting();
    }

    protected void setEntityAttackTarget(EntityCreature entityCreature, LivingEntity livingEntity) {
        entityCreature.setAttackTarget(livingEntity);
    }
}
