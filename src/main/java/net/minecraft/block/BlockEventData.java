package net.minecraft.block;

import net.minecraft.util.BlockPos;

public class BlockEventData {

    private BlockPos position;
    private int eventID;
    private int eventParameter;
    private Block blockType;

    private static String[] packedTextFragments;

    public int getEventID() {
        return this.eventID;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BlockEventData)) {
            return false;
        }

        BlockEventData blockEventData = (BlockEventData) object;
        return this.position.equals(blockEventData.position)
                && this.eventID == blockEventData.eventID
                && this.eventParameter == blockEventData.eventParameter
                && this.blockType == blockEventData.blockType;
    }

    public int getEventParameter() {
        return this.eventParameter;
    }

    @Override
    public String toString() {
        return "TE("
                + this.position
                + "),"
                + this.eventID
                + packedTextFragments[0]
                + this.eventParameter
                + packedTextFragments[1]
                + this.blockType;
    }

    public Block getBlock() {
        return this.blockType;
    }

    public BlockEventData(BlockPos blockPos, Block block, int eventIDValue, int j) {
        this.position = blockPos;
        this.eventID = eventIDValue;
        this.eventParameter = j;
        this.blockType = block;
    }

    static {
        net.minecraft.block.BlockEventData.packedTextFragments = new String[3];
        net.minecraft.block.BlockEventData.packedTextFragments[0] = new String(",");
        net.minecraft.block.BlockEventData.packedTextFragments[1] = new String(",");
    }

    public BlockPos getPosition() {
        return this.position;
    }
}
