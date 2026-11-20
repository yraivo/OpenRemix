package net.minecraft.village;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;

public class VillageDoorInfo {
    private boolean isDetachedFromVillageFlag;
    private BlockPos insideBlock;
    private BlockPos doorBlockPos;
    private int lastActivityTimestamp;
    private int doorOpeningRestrictionCounter;
    private Facing insideDirection;

    public int getDistanceToDoorBlockSq(BlockPos blockPos) {
        return (int) blockPos.distanceSq(this.getDoorBlockPos());
    }

    private static Facing getFaceDirection(int i, int j) {
        return i < 0 ? Facing.WEST : (i > 0 ? Facing.EAST : (j < 0 ? Facing.NORTH : Facing.SOUTH));
    }

    public BlockPos getDoorBlockPos() {
        return this.doorBlockPos;
    }

    public int getDistanceSquared(int i, int j, int k) {
        return (int) this.doorBlockPos.distanceSq(i, j, k);
    }

    public void resetDoorOpeningRestrictionCounter() {
        this.doorOpeningRestrictionCounter = 0;
    }

    public int getDoorOpeningRestrictionCounter() {
        return this.doorOpeningRestrictionCounter;
    }

    public boolean func_179850_c(BlockPos blockPos) {
        int deltaX = blockPos.getX() - this.doorBlockPos.getX();
        int j = blockPos.getZ() - this.doorBlockPos.getY();
        return deltaX * this.insideDirection.getFrontOffsetX()
                        + j * this.insideDirection.getFrontOffsetZ()
                >= 0;
    }

    public int getInsideOffsetX() {
        return this.insideDirection.getFrontOffsetX() * 2;
    }

    public void incrementDoorOpeningRestrictionCounter() {
        this.doorOpeningRestrictionCounter++;
    }

    public VillageDoorInfo(BlockPos blockPos, int i, int j, int k) {
        this(blockPos, getFaceDirection(i, j), k);
    }

    public int getDistanceToInsideBlockSq(BlockPos blockPos) {
        return (int) this.insideBlock.distanceSq(blockPos);
    }

    public void setLastActivityTimestamp(int lastActivityTimestampValue) {
        this.lastActivityTimestamp = lastActivityTimestampValue;
    }

    public boolean getIsDetachedFromVillageFlag() {
        return this.isDetachedFromVillageFlag;
    }

    public void setIsDetachedFromVillageFlag(boolean isDetachedFromVillageFlagValue) {
        this.isDetachedFromVillageFlag = isDetachedFromVillageFlagValue;
    }

    public int getInsidePosY() {
        return this.lastActivityTimestamp;
    }

    public BlockPos getInsideBlockPos() {
        return this.insideBlock;
    }

    public int getInsideOffsetZ() {
        return this.insideDirection.getFrontOffsetZ() * 2;
    }

    public VillageDoorInfo(BlockPos blockPos, Facing aAAValue, int lastActivityTimestampValue) {
        this.doorBlockPos = blockPos;
        this.insideDirection = aAAValue;
        this.insideBlock = blockPos.offset(aAAValue, 2);
        this.lastActivityTimestamp = lastActivityTimestampValue;
    }
}
