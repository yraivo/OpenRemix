package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRedstoneOre extends Block {
    private boolean isOn;

    private void spawnRedstoneParticles(World world, BlockPos blockPos) {
        Random random = world.rand;
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        double d0 = 0.0625;
        int j = 0;
        int i = k;

        while (j < 6) {
            double d1 = blockPos.getX() + random.nextFloat();
            double d2 = blockPos.getY() + random.nextFloat();
            double d3 = blockPos.getZ() + random.nextFloat();
            if (j == 0 && !world.getBlockState(blockPos.offsetUp()).getBlock().isOpaqueCube()) {
                d2 = blockPos.getY() + d0 + 1.0;
            }

            if (j == 1 && !world.getBlockState(blockPos.offsetDown()).getBlock().isOpaqueCube()) {
                d2 = blockPos.getY() - d0;
            }

            if (j == 2 && !world.getBlockState(blockPos.offsetSouth()).getBlock().isOpaqueCube()) {
                d3 = blockPos.getZ() + d0 + 1.0;
            }

            if (j == 3 && !world.getBlockState(blockPos.offsetNorth()).getBlock().isOpaqueCube()) {
                d3 = blockPos.getZ() - d0;
            }

            if (j == 4 && !world.getBlockState(blockPos.offsetEast()).getBlock().isOpaqueCube()) {
                d1 = blockPos.getX() + d0 + 1.0;
            }

            if (j == 5 && !world.getBlockState(blockPos.offsetWest()).getBlock().isOpaqueCube()) {
                d1 = blockPos.getX() - d0;
            }

            if (d1 < blockPos.getX()
                    || d1 > blockPos.getX() + 1
                    || d2 < 0.0
                    || d2 > blockPos.getY() + 1
                    || d3 < blockPos.getZ()
                    || d3 > blockPos.getZ() + 1) {
                world.spawnParticle(
                        EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0, 0.0, 0.0, new int[0]);
            }

            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public void onEntityCollidedWithBlock(World world, BlockPos blockPos, Entity entity) {
        this.setOn(world, blockPos);
        super.onEntityCollidedWithBlock(world, blockPos, entity);
    }

    public int quantityDroppedWithBonus(int i, Random random) {
        return this.quantityDropped(random) + random.nextInt(i + 1);
    }

    public BlockRedstoneOre(boolean isOnValue) {
        super(Material.rock);
        if (isOnValue) {
            this.setTickRandomly(true);
        }

        this.isOn = isOnValue;
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (this.isOn) {
            this.spawnRedstoneParticles(world, blockPos);
        }
    }

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float f,
            float f1,
            float f2) {
        this.setOn(world, blockPos);
        return super.onBlockActivated(world, blockPos, blockState, playerEntity, facing, f, f1, f2);
    }

    protected ItemStack createStackedBlock(BlockState blockState) {
        return new ItemStack(Blocks.redstone_ore);
    }

    private void setOn(World world, BlockPos blockPos) {
        this.spawnRedstoneParticles(world, blockPos);
        if (this == Blocks.redstone_ore) {
            world.setBlockState(blockPos, Blocks.lit_redstone_ore.getDefaultState());
        }
    }

    public int tickRate(World world) {
        return 30;
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, f, i);
        if (this.getItemDropped(blockState, world.rand, i) != Item.getItemFromBlock(this)) {
            int j = 1 + world.rand.nextInt(5);
            this.dropXpOnBlockBreak(world, blockPos, j);
        }
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.redstone;
    }

    public void onBlockClicked(World world, BlockPos blockPos, PlayerEntity playerEntity) {
        this.setOn(world, blockPos);
        super.onBlockClicked(world, blockPos, playerEntity);
    }

    public int quantityDropped(Random random) {
        return 4 + random.nextInt(2);
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (this == Blocks.lit_redstone_ore) {
            world.setBlockState(blockPos, Blocks.redstone_ore.getDefaultState());
        }
    }
}
