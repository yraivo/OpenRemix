package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class EntityAIMoveToBlock extends EntityAIBase {
    private int searchLength;
    protected int runDelay;
    protected BlockPos destinationBlock = BlockPos.ORIGIN;
    private boolean isAboveDestination;
    private EntityCreature theEntity;
    private int field_179490_f;
    private double movementSpeed;
    private int timeoutCounter;

    public void updateTask() {
        String text = EntityAIBase.cJ();
        if (this.theEntity.getDistanceSqToCenter(this.destinationBlock.offsetUp()) > 1.0) {
            this.isAboveDestination = false;
            this.timeoutCounter++;
            if (this.timeoutCounter % 40 != 0) {
                return;
            }

            this.theEntity
                    .getNavigator()
                    .tryMoveToXYZ(
                            this.destinationBlock.getX() + 0.5,
                            this.destinationBlock.getY() + 1,
                            this.destinationBlock.getZ() + 0.5,
                            this.movementSpeed);
            if (text != null) {
                return;
            }
        }

        this.isAboveDestination = true;
        this.timeoutCounter--;
    }

    public void startExecuting() {
        this.theEntity
                .getNavigator()
                .tryMoveToXYZ(
                        this.destinationBlock.getX() + 0.5,
                        this.destinationBlock.getY() + 1,
                        this.destinationBlock.getZ() + 0.5,
                        this.movementSpeed);
        this.timeoutCounter = 0;
        this.field_179490_f =
                this.theEntity.getRNG().nextInt(this.theEntity.getRNG().nextInt(1200) + 1200)
                        + 1200;
    }

    public boolean shouldExecute() {
        if (this.runDelay > 0) {
            this.runDelay--;
            return false;
        } else {
            this.runDelay = 200 + this.theEntity.getRNG().nextInt(200);
            return this.searchForDestination();
        }
    }

    public boolean continueExecuting() {
        return this.timeoutCounter >= -this.field_179490_f
                && this.timeoutCounter <= 1200
                && this.shouldMoveTo(this.theEntity.world, this.destinationBlock);
    }

    private boolean searchForDestination() {
        int searchLengthValue = this.searchLength;

        BlockPos blockPos = new BlockPos(this.theEntity);
        String text = EntityAIBase.cJ();
        byte b1 = 0;
        String textValue = text;

        while (b1 <= 1) {
            int j = 0;

            label68:
            while (j < searchLengthValue) {
                byte b2 = 0;

                while (true) {
                    if (b2 <= j) {
                        byte b3 = (byte) (b2 < j && b2 > -j ? j : 0);

                        while (b3 <= j) {
                            BlockPos bkhValue = blockPos.add(b2, b1 - 1, b3);
                            if (this.theEntity.isWithinHomeDistanceFromPosition(bkhValue)
                                    && this.shouldMoveTo(this.theEntity.world, bkhValue)) {
                                this.destinationBlock = bkhValue;
                                return true;
                            }

                            b3 = (byte) (b3 > 0 ? -b3 : 1 - b3);
                            if (textValue == null) {
                                break;
                            }
                        }

                        b2 = (byte) (b2 > 0 ? -b2 : 1 - b2);
                        if (textValue != null) {
                            continue;
                        }
                    }

                    j++;
                    if (textValue == null) {
                        break label68;
                    }
                    break;
                }
            }

            b1 = (byte) (b1 > 0 ? -b1 : 1 - b1);
            if (textValue == null) {
                break;
            }
        }

        return false;
    }

    public EntityAIMoveToBlock(
            EntityCreature bhjValue, double movementSpeedValue, int searchLengthValue) {
        this.theEntity = bhjValue;
        this.movementSpeed = movementSpeedValue;
        this.searchLength = searchLengthValue;
        this.setMutexBits(5);
    }

    public void resetTask() {}

    protected boolean getIsAboveDestination() {
        return this.isAboveDestination;
    }

    protected abstract boolean shouldMoveTo(World world, BlockPos blockPos);
}
