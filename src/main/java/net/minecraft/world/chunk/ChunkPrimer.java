package net.minecraft.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;

public class ChunkPrimer {

    private BlockState defaultState;

    private final short[] data = new short[65536];

    private static IndexOutOfBoundsException method_e58a88fb4d7636fa7240(
            IndexOutOfBoundsException indexoutofboundsexception) {
        return indexoutofboundsexception;
    }

    public void setBlockState(int i, int j, int k, BlockState blockState) {
        int l = i << 12 | k << 8 | j;
        this.setBlockState(l, blockState);
    }


    public ChunkPrimer() {
        this.defaultState = Blocks.air.getDefaultState();
    }

    public BlockState getBlockState(int i, int j, int k) {
        int l = i << 12 | k << 8 | j;
        return this.getBlockState(l);
    }

    public BlockState getBlockState(int i) {
        if (i >= 0 && i < this.data.length) {
            BlockState blockState = (BlockState) Block.BLOCK_STATE_IDS.getByValue(this.data[i]);
            return blockState != null ? blockState : this.defaultState;
        } else {
            throw new IndexOutOfBoundsException("The coordinate is out of range");
        }
    }

    public void setBlockState(int i, BlockState blockState) {
        int[] aint = Chunk.Wj();
        if (i >= 0 && i < this.data.length) {
            this.data[i] = (short) Block.BLOCK_STATE_IDS.get(blockState);
            if (aint == null) {
                return;
            }
        }

        throw new IndexOutOfBoundsException("The coordinate is out of range");
    }
}
