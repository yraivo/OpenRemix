package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

public class NextTickListEntry implements Comparable {
    private long tickEntryID;
    public BlockPos position;
    public int priority;

    public long scheduledTime;
    private Block block;
    private static long nextTickEntryID;

    public NextTickListEntry setScheduledTime(long scheduledTimeValue) {
        this.scheduledTime = scheduledTimeValue;
        return this;
    }

    public Block getBlock() {
        return this.block;
    }

    public void setPriority(int priorityValue) {
        this.priority = priorityValue;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NextTickListEntry)) {
            return false;
        }

        NextTickListEntry nextTickListEntry = (NextTickListEntry) object;
        return this.position.equals(nextTickListEntry.position)
                && Block.isEqualTo(this.block, nextTickListEntry.block);
    }

    public NextTickListEntry(BlockPos blockPos, Block block) {
        long aMRValue = net.minecraft.world.NextTickListEntry.nextTickEntryID;

        net.minecraft.world.NextTickListEntry.nextTickEntryID += 1L;
        this.tickEntryID = aMRValue;
        this.position = blockPos;
        this.block = block;
    }

    public int compareTo(NextTickListEntry nextTickListEntry) {
        return this.scheduledTime < nextTickListEntry.scheduledTime
                ? -1
                : (this.scheduledTime > nextTickListEntry.scheduledTime
                        ? 1
                        : (this.priority != nextTickListEntry.priority
                                ? this.priority - nextTickListEntry.priority
                                : (this.tickEntryID < nextTickListEntry.tickEntryID
                                        ? -1
                                        : (this.tickEntryID > nextTickListEntry.tickEntryID
                                                ? 1
                                                : 0))));
    }

    @Override
    public int hashCode() {
        return this.position.hashCode();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((NextTickListEntry) object);
    }

    @Override
    public String toString() {
        return Block.getIdFromBlock(this.block)
                + ": "
                + this.position
                + ", "
                + this.scheduledTime
                + ", "
                + this.priority
                + ", "
                + this.tickEntryID;
    }


}
