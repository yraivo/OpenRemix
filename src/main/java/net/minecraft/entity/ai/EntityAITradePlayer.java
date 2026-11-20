package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Container;

public class EntityAITradePlayer extends EntityAIBase {
    private EntityVillager villager;

    @Override
    public void resetTask() {
        this.villager.setCustomer((PlayerEntity) null);
    }

    @Override
    public void startExecuting() {
        this.villager.getNavigator().clearPathEntity();
    }

    @Override
    public boolean shouldExecute() {
        if (!this.villager.isEntityAlive()) {
            return false;
        }

        if (this.villager.isInWater()) {
            return false;
        }

        if (!this.villager.onGround) {
            return false;
        }

        if (this.villager.velocityChanged) {
            return false;
        }

        PlayerEntity playerEntity = this.villager.getCustomer();
        return playerEntity == null
                ? false
                : (this.villager.getDistanceSqToEntity(playerEntity) > 16.0
                        ? false
                        : playerEntity.openContainer instanceof Container);
    }

    public EntityAITradePlayer(EntityVillager bhkValue) {
        this.villager = bhkValue;
        this.setMutexBits(5);
    }
}
