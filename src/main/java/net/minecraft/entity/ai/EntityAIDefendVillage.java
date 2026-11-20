package net.minecraft.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityIronGolem;

public class EntityAIDefendVillage extends EntityAITarget {
    EntityIronGolem irongolem;
    LivingEntity villageAgressorTarget;

    public boolean shouldExecute() {
        net.minecraft.village.Village village = this.irongolem.getVillage();
        if (village == null) {
            return false;
        }

        this.villageAgressorTarget = village.findNearestVillageAggressor(this.irongolem);
        if (!this.isSuitableTarget(this.villageAgressorTarget, false)) {
            if (this.taskOwner.getRNG().nextInt(20) == 0) {
                this.villageAgressorTarget = village.getNearestTargetPlayer(this.irongolem);
                return this.isSuitableTarget(this.villageAgressorTarget, false);
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public void startExecuting() {
        this.irongolem.setAttackTarget(this.villageAgressorTarget);
        super.startExecuting();
    }

    public EntityAIDefendVillage(EntityIronGolem bkpValue) {
        super(bkpValue, false, true);
        this.irongolem = bkpValue;
        this.setMutexBits(1);
    }
}
