package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMobSpawner extends BlockContainer {
    public Item getItem(World world, BlockPos blockPos) {
        return null;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    protected BlockMobSpawner() {
        super(Material.rock);
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, f, i);
        int j = 15 + world.rand.nextInt(15) + world.rand.nextInt(15);
        this.dropXpOnBlockBreak(world, blockPos, j);
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return null;
    }

    public int getRenderType() {
        return 3;
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityMobSpawner();
    }

    public int quantityDropped(Random random) {
        return 0;
    }
}
