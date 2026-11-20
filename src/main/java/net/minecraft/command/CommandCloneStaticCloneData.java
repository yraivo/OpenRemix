package net.minecraft.command;

import net.minecraft.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;

class CommandCloneStaticCloneData {
    public BlockState blockState;
    public CompoundTag aYW;
    public BlockPos vh;

    public CommandCloneStaticCloneData(
            BlockPos blockPos, BlockState blockState, CompoundTag aYWValue) {
        this.vh = blockPos;
        this.blockState = blockState;
        this.aYW = aYWValue;
    }
}
