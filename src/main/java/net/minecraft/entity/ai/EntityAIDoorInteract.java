package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;

public abstract class EntityAIDoorInteract extends EntityAIBase {
    boolean hasStoppedDoorInteraction;
    protected BlockDoor doorBlock;
    float entityPositionZ;

    protected BlockPos doorPosition = BlockPos.ORIGIN;
    float entityPositionX;
    private static String vI;
    protected EntityLiving theEntity;

    public boolean shouldExecute() {
        String text = EntityAIBase.cJ();
        if (!this.theEntity.collidedHorizontally) {
            return false;
        }

        PathNavigateGround navigator = (PathNavigateGround) this.theEntity.getNavigator();
        PathEntity path = navigator.getPath();
        if (path != null && !path.isFinished() && navigator.getEnterDoors()) {
            int i = 0;

            while (i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength())) {
                PathPoint pathPointFromIndex = path.getPathPointFromIndex(i);
                this.doorPosition =
                        new BlockPos(
                                pathPointFromIndex.xCoord,
                                pathPointFromIndex.yCoord + 1,
                                pathPointFromIndex.zCoord);
                if (this.theEntity.getDistanceSq(
                                this.doorPosition.getX(),
                                this.theEntity.posY,
                                this.doorPosition.getZ())
                        <= 2.25) {
                    this.doorBlock = this.getBlockDoor(this.doorPosition);
                    if (this.doorBlock != null) {
                        return true;
                    }
                }

                i++;
                if (text == null) {
                    break;
                }
            }

            this.doorPosition = new BlockPos(this.theEntity).offsetUp();
            this.doorBlock = this.getBlockDoor(this.doorPosition);
            return this.doorBlock != null;
        } else {
            return false;
        }
    }

    private BlockDoor getBlockDoor(BlockPos blockPos) {
        Block block = this.theEntity.world.getBlockState(blockPos).getBlock();
        return block instanceof BlockDoor && block.getMaterial() == Material.wood
                ? (BlockDoor) block
                : null;
    }

    public void startExecuting() {
        this.hasStoppedDoorInteraction = false;
        this.entityPositionX = (float) (this.doorPosition.getX() + 0.5F - this.theEntity.posX);
        this.entityPositionZ = (float) (this.doorPosition.getZ() + 0.5F - this.theEntity.posZ);
    }

    public void updateTask() {
        float fx = (float) (this.doorPosition.getX() + 0.5F - this.theEntity.posX);
        float f1 = (float) (this.doorPosition.getZ() + 0.5F - this.theEntity.posZ);
        float f2 = this.entityPositionX * fx + this.entityPositionZ * f1;
        if (f2 < 0.0F) {
            this.hasStoppedDoorInteraction = true;
        }
    }

    static {
        vI = "Unsupported mob type for DoorInteractGoal";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public EntityAIDoorInteract(EntityLiving bhtValue) {
        this.theEntity = bhtValue;
        if (!(bhtValue.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException(vI);
        }
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public boolean continueExecuting() {
        return !this.hasStoppedDoorInteraction;
    }
}
