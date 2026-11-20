package remix.event.impl;

import remix.event.Event;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;

public class BlockCollisionEvent implements Event {
    private BoundingBox bounds;
    private Block block;
    private BlockPos position;

    public void setBounds(BoundingBox boundsValue) {
        this.bounds = boundsValue;
    }

    public BoundingBox getBounds() {
        return this.bounds;
    }

    public Block getBlock() {
        return this.block;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public BlockCollisionEvent(Block blockValue, BlockPos positionValue, BoundingBox boundsValue) {
        this.block = blockValue;
        this.position = positionValue;
        this.bounds = boundsValue;
    }
}
