package net.minecraft.entity.monster;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;

import java.util.Random;

class EntityGhastAIRandomFly extends EntityAIBase {
    private EntityGhast parentEntity;
    EntityGhast bjQ;

    @Override
    public void startExecuting() {
        Random random = this.parentEntity.getRNG();
        double d0 = this.parentEntity.posX + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
        double d1 = this.parentEntity.posY + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
        double d2 = this.parentEntity.posZ + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
        this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0);
    }

    @Override
    public boolean continueExecuting() {
        return false;
    }

    @Override
    public boolean shouldExecute() {
        EntityMoveHelper moveHelper = this.parentEntity.getMoveHelper();
        if (!moveHelper.isUpdating()) {
            return true;
        }

        double d0 = moveHelper.getX() - this.parentEntity.posX;
        double d1 = moveHelper.getY() - this.parentEntity.posY;
        double d2 = moveHelper.getZ() - this.parentEntity.posZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        return d3 < 1.0 || d3 > 3600.0;
    }

    public EntityGhastAIRandomFly(EntityGhast bjQValue) {
        this.bjQ = bjQValue;
        this.parentEntity = this.bjQ;
        this.setMutexBits(1);
    }
}
