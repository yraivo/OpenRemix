package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.PlayerEntity;

public class EntityAILookAtTradePlayer extends EntityAIWatchClosest {
    private EntityVillager theMerchant;

    public boolean shouldExecute() {
        if (this.theMerchant.isTrading()) {
            this.closestEntity = this.theMerchant.getCustomer();
            return true;
        } else {
            return false;
        }
    }

    public EntityAILookAtTradePlayer(EntityVillager bjGValue) {
        super(bjGValue, PlayerEntity.class, 8.0F);
        this.theMerchant = bjGValue;
    }
}
