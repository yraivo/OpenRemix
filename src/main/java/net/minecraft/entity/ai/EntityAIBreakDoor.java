package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.EnumDifficulty;

public class EntityAIBreakDoor extends EntityAIDoorInteract {

    private int previousBreakProgress = -1;
    private static String mC;
    private int breakingTime;

    // region String decoding support

    // endregion

    public boolean continueExecuting() {
        double d0 = this.theEntity.getDistanceSq(this.doorPosition);
        if (this.breakingTime <= 240) {
            BlockDoor bigValue = this.doorBlock;
            if (!BlockDoor.isOpen(this.theEntity.world, this.doorPosition) && d0 < 4.0) {
                return true;
            }
        }

        return false;
    }

    // region String decoding support

    // endregion

    public boolean shouldExecute() {
        if (!super.shouldExecute()) {
            return false;
        }

        if (!this.theEntity.world.getGameRules().getGameRuleBooleanValue(mC)) {
            return false;
        }

        BlockDoor bigValue = this.doorBlock;
        return !BlockDoor.isOpen(this.theEntity.world, this.doorPosition);
    }

    public void updateTask() {
        super.updateTask();
        if (this.theEntity.getRNG().nextInt(20) == 0) {
            this.theEntity.world.playAuxSFX(1010, this.doorPosition, 0);
        }

        this.breakingTime++;
        int previousBreakProgressValue = (int) (this.breakingTime / 240.0F * 10.0F);
        if (previousBreakProgressValue != this.previousBreakProgress) {
            this.theEntity.world.sendBlockBreakProgress(
                    this.theEntity.getEntityId(), this.doorPosition, previousBreakProgressValue);
            this.previousBreakProgress = previousBreakProgressValue;
        }

        if (this.breakingTime == 240
                && this.theEntity.world.getDifficulty() == EnumDifficulty.HARD) {
            this.theEntity.world.setBlockToAir(this.doorPosition);
            this.theEntity.world.playAuxSFX(1012, this.doorPosition, 0);
            this.theEntity.world.playAuxSFX(
                    2001, this.doorPosition, Block.getIdFromBlock(this.doorBlock));
        }
    }

    static {
        mC = "mobGriefing";
    }

    public void startExecuting() {
        super.startExecuting();
        this.breakingTime = 0;
    }

    public EntityAIBreakDoor(EntityLiving entityLiving) {
        super(entityLiving);
    }

    public void resetTask() {
        super.resetTask();
        this.theEntity.world.sendBlockBreakProgress(
                this.theEntity.getEntityId(), this.doorPosition, -1);
    }
}
