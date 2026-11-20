package net.minecraft.world.gen.structure;

import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;

import java.util.Random;

public abstract class StructureComponentBlockSelector {
    protected BlockState blockstate = Blocks.air.getDefaultState();

    public BlockState getBlockState() {
        return this.blockstate;
    }

    public StructureComponentBlockSelector() {}

    public abstract void selectBlocks(Random random, int i, int j, int k, boolean flag);
}
