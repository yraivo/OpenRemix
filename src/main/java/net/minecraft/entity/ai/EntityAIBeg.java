package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityAIBeg extends EntityAIBase {
    private PlayerEntity thePlayer;
    private EntityWolf theWolf;
    private int timeoutCounter;
    private float minPlayerDistance;
    private World worldObject;

    public boolean continueExecuting() {
        return !this.thePlayer.isEntityAlive()
                ? false
                : (this.theWolf.getDistanceSqToEntity(this.thePlayer)
                                > this.minPlayerDistance * this.minPlayerDistance
                        ? false
                        : this.timeoutCounter > 0
                                && this.hasPlayerGotBoneInHand(this.thePlayer));
    }

    public void resetTask() {
        this.theWolf.setBegging(false);
        this.thePlayer = null;
    }

    public void startExecuting() {
        this.theWolf.setBegging(true);
        this.timeoutCounter = 40 + this.theWolf.getRNG().nextInt(40);
    }

    public boolean shouldExecute() {
        this.thePlayer =
                this.worldObject.getClosestPlayerToEntity(this.theWolf, this.minPlayerDistance);
        return this.thePlayer == null ? false : this.hasPlayerGotBoneInHand(this.thePlayer);
    }

    public void updateTask() {
        this.theWolf
                .getLookHelper()
                .setLookPosition(
                        this.thePlayer.posX,
                        this.thePlayer.posY + this.thePlayer.getEyeHeight(),
                        this.thePlayer.posZ,
                        10.0F,
                        this.theWolf.getVerticalFaceSpeed());
        this.timeoutCounter--;
    }

    public EntityAIBeg(EntityWolf bieValue, float minPlayerDistanceValue) {
        this.theWolf = bieValue;
        this.worldObject = bieValue.world;
        this.minPlayerDistance = minPlayerDistanceValue;
        this.setMutexBits(2);
    }

    private boolean hasPlayerGotBoneInHand(PlayerEntity playerEntity) {
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        return itemStack == null
                ? false
                : (!this.theWolf.isTamed() && itemStack.getItem() == Items.bone
                        ? true
                        : this.theWolf.isBreedingItem(itemStack));
    }
}
