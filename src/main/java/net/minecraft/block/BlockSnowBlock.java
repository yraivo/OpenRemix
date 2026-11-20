package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSnowBlock extends Block {
    protected BlockSnowBlock() {
        super(Material.craftedSnow);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (world.getLightFor(EnumSkyBlock.BLOCK, blockPos) > 11) {
            this.dropBlockAsItem(world, blockPos, world.getBlockState(blockPos), 0);
            world.setBlockToAir(blockPos);
        }
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.snowball;
    }

    public int quantityDropped(Random random) {
        return 4;
    }
}
