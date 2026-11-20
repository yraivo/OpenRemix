package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAITempt extends EntityAIBase {
    private double yaw;
    private double targetZ;
    private PlayerEntity temptingPlayer;
    private double speed;
    private double targetY;
    private Item temptItem;
    private double pitch;
    private double targetX;
    private int delayTemptCounter;

    private static String kK;
    private boolean scaredByPlayerMovement;
    private EntityCreature temptedEntity;
    private boolean avoidWater;
    private boolean isRunning;

    public void startExecuting() {
        this.targetX = this.temptingPlayer.posX;
        this.targetY = this.temptingPlayer.posY;
        this.targetZ = this.temptingPlayer.posZ;
        this.isRunning = true;
        this.avoidWater = ((PathNavigateGround) this.temptedEntity.getNavigator()).getAvoidsWater();
        ((PathNavigateGround) this.temptedEntity.getNavigator()).setAvoidsWater(false);
    }

    static {
        kK = "Unsupported mob type for TemptGoal";
    }

    public EntityAITempt(
            EntityCreature bhjValue,
            double speedValue,
            Item bjAValue,
            boolean scaredByPlayerMovementValue) {
        this.temptedEntity = bhjValue;
        this.speed = speedValue;
        this.temptItem = bjAValue;
        this.scaredByPlayerMovement = scaredByPlayerMovementValue;
        this.setMutexBits(3);
        if (!(bhjValue.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException(kK);
        }
    }

    public boolean continueExecuting() {
        if (this.scaredByPlayerMovement) {
            if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 36.0) {
                if (this.temptingPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ)
                        > 0.010000000000000002) {
                    return false;
                }

                if (Math.abs(this.temptingPlayer.rotationPitch - this.pitch) > 5.0
                        || Math.abs(this.temptingPlayer.rotationYaw - this.yaw) > 5.0) {
                    return false;
                }
            } else {
                this.targetX = this.temptingPlayer.posX;
                this.targetY = this.temptingPlayer.posY;
                this.targetZ = this.temptingPlayer.posZ;
            }

            this.pitch = this.temptingPlayer.rotationPitch;
            this.yaw = this.temptingPlayer.rotationYaw;
        }

        return this.shouldExecute();
    }

    // region String decoding support

    // endregion

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public void updateTask() {
        String text = EntityAIBase.cJ();
        this.temptedEntity
                .getLookHelper()
                .setLookPositionWithEntity(
                        this.temptingPlayer, 30.0F, this.temptedEntity.getVerticalFaceSpeed());
        String textValue = text;
        if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 6.25) {
            this.temptedEntity.getNavigator().clearPathEntity();
            if (textValue != null) {
                return;
            }
        }

        this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.speed);
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void resetTask() {
        this.temptingPlayer = null;
        this.temptedEntity.getNavigator().clearPathEntity();
        this.delayTemptCounter = 100;
        this.isRunning = false;
        ((PathNavigateGround) this.temptedEntity.getNavigator()).setAvoidsWater(this.avoidWater);
    }

    public boolean shouldExecute() {
        if (this.delayTemptCounter > 0) {
            this.delayTemptCounter--;
            return false;
        }

        this.temptingPlayer =
                this.temptedEntity.world.getClosestPlayerToEntity(this.temptedEntity, 10.0);
        if (this.temptingPlayer == null) {
            return false;
        }

        ItemStack currentEquippedItem = this.temptingPlayer.getCurrentEquippedItem();
        return currentEquippedItem == null
                ? false
                : currentEquippedItem.getItem() == this.temptItem;
    }

    // region String decoding support

    // endregion
}
