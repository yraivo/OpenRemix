package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPosMutableBlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFarmland extends Block {
    public static PropertyInteger MOISTURE;

    public boolean isFullCube() {
        return false;
    }

    static {
        net.minecraft.block.BlockFarmland.MOISTURE =
                PropertyInteger.create("moisture", 0, 7);
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.dirt);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockFarmland.MOISTURE);
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return new BoundingBox(
                blockPos.getX(),
                blockPos.getY(),
                blockPos.getZ(),
                blockPos.getX() + 1,
                blockPos.getY() + 1,
                blockPos.getZ() + 1);
    }

    public void onFallenUpon(World world, BlockPos blockPos, Entity entity, float f) {
        if (entity instanceof LivingEntity) {
            if (!world.isRemote && world.rand.nextFloat() < f - 0.5F) {
                if (!(entity instanceof PlayerEntity)
                        && !world.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    return;
                }

                world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
            }

            super.onFallenUpon(world, blockPos, entity, f);
        }
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockFarmland.MOISTURE, i & 7);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        super.onNeighborBlockChange(world, blockPos, blockState, block);
        if (world.getBlockState(blockPos.offsetUp()).getBlock().getMaterial().isSolid()) {
            world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
        }
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Blocks.dirt.getItemDropped(
                Blocks.dirt
                        .getDefaultState()
                        .withProperty(BlockDirt.VARIANT, BlockDirtDirtType.DIRT),
                random,
                i);
    }

    private boolean hasCrops(World world, BlockPos blockPos) {
        Block block = world.getBlockState(blockPos.offsetUp()).getBlock();
        return block instanceof BlockCrops || block instanceof BlockStem;
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int j = (Integer) blockState.getValue(net.minecraft.block.BlockFarmland.MOISTURE);
        int i = k;
        if (!this.hasWater(world, blockPos) && !world.canLightningStrike(blockPos.offsetUp())) {
            if (j > 0) {
                world.setBlockState(
                        blockPos, blockState.withProperty(net.minecraft.block.BlockFarmland.MOISTURE, j - 1), 2);
                if (i == 0) {
                    return;
                }
            }

            if (this.hasCrops(world, blockPos)) {
                return;
            }

            world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
            if (i == 0) {
                return;
            }
        }

        if (j < 7) {
            world.setBlockState(
                    blockPos, blockState.withProperty(net.minecraft.block.BlockFarmland.MOISTURE, 7), 2);
        }
    }

    protected BlockFarmland() {
        super(Material.ground);
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockFarmland.MOISTURE, 0));
        this.setTickRandomly(true);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
        this.setLightOpacity(255);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, net.minecraft.block.BlockFarmland.MOISTURE);
    }

    private boolean hasWater(World world, BlockPos blockPos) {
        for (java.util.Iterator<?> __recoveryIterator171_30980 =
                        (BlockPos.getAllInBoxMutable(
                                        blockPos.add(-4, 0, -4), blockPos.add(4, 1, 4)))
                                .iterator();
                (__recoveryIterator171_30980).hasNext(); ) {
            BlockPosMutableBlockPos blockPosMutableBlockPos =
                    (BlockPosMutableBlockPos) (__recoveryIterator171_30980).next();
            if (world.getBlockState(blockPosMutableBlockPos).getBlock().getMaterial()
                    == Material.water) {
                return true;
            }
        }

        return false;
    }
}
