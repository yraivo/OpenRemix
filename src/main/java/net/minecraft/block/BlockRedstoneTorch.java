package net.minecraft.block;

import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockRedstoneTorch extends BlockTorch {
    private static Map toggles;
    private static String ED;

    private boolean isOn;

    public int tickRate(World world) {
        return 2;
    }

    // region String decoding support

    // endregion

    static {
        ED = "random.fizz";
        net.minecraft.block.BlockRedstoneTorch.toggles = Maps.newHashMap();
    }

    public int isProvidingStrongPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return facing == Facing.DOWN
                ? this.isProvidingWeakPower(iBlockAccess, blockPos, blockState, facing)
                : 0;
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (this.isOn) {
            for (Facing facing : Facing.values()) {
                world.notifyNeighborsOfStateChange(blockPos.offset(facing), this);
                if (i == 0) {
                    break;
                }
            }
        }
    }

    private boolean shouldBeOff(World world, BlockPos blockPos, BlockState blockState) {
        Facing facing = ((Facing) blockState.getValue(net.minecraft.block.BlockTorch.FACING_PROP)).getOpposite();
        return world.isSidePowered(blockPos.offset(facing), facing);
    }

    private boolean isBurnedOut(World world, BlockPos blockPos, boolean flag) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!net.minecraft.block.BlockRedstoneTorch.toggles.containsKey(world)) {
            net.minecraft.block.BlockRedstoneTorch.toggles.put(world, Lists.newArrayList());
        }

        List list = (List) net.minecraft.block.BlockRedstoneTorch.toggles.get(world);
        if (flag) {
            list.add(new BlockRedstoneTorchToggle(blockPos, world.getTotalWorldTime()));
        }

        int j = 0;
        int k = 0;

        while (k < list.size()) {
            BlockRedstoneTorchToggle blockRedstoneTorchToggle =
                    (BlockRedstoneTorchToggle) list.get(k);
            if (blockRedstoneTorchToggle.pos.equals(blockPos)) {
                if (++j >= 8) {
                    return true;
                }
            }

            k++;
            if (i != 0) {
                break;
            }
        }

        return false;
    }

    protected BlockRedstoneTorch(boolean ahvValue) {
        this.isOn = ahvValue;
        this.setTickRandomly(true);
        this.setCreativeTab((CreativeTabs) null);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        if (!this.onNeighborChangeInternal(world, blockPos, blockState)
                && this.isOn == this.shouldBeOff(world, blockPos, blockState)) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }

    public int isProvidingWeakPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return this.isOn && blockState.getValue(net.minecraft.block.BlockTorch.FACING_PROP) != facing ? 15 : 0;
    }

    public boolean canProvidePower() {
        return true;
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (this.isOn) {
            double d0 = blockPos.getX() + 0.5F + (random.nextFloat() - 0.5F) * 0.2;
            double d1 = blockPos.getY() + 0.7F + (random.nextFloat() - 0.5F) * 0.2;
            double d2 = blockPos.getZ() + 0.5F + (random.nextFloat() - 0.5F) * 0.2;
            Facing facing = (Facing) blockState.getValue(net.minecraft.block.BlockTorch.FACING_PROP);
            if (facing.getAxis().isHorizontal()) {
                Facing facingValue = facing.getOpposite();

                d0 += 0.27F * facingValue.getFrontOffsetX();
                d1 += 0.22F;
                d2 += 0.27F * facingValue.getFrontOffsetZ();
            }

            world.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
        }
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (this.isOn) {
            for (Facing facing : Facing.values()) {
                world.notifyNeighborsOfStateChange(blockPos.offset(facing), this);
                if (i == 0) {
                    break;
                }
            }
        }
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        boolean flag = this.shouldBeOff(world, blockPos, blockState);
        int i = l;
        List list = (List) net.minecraft.block.BlockRedstoneTorch.toggles.get(world);

        while (list != null && !list.isEmpty()) {
            long k = world.getTotalWorldTime() - ((BlockRedstoneTorchToggle) list.get(0)).time;

            if (k <= 60L) {
                break;
            }

            list.remove(0);
            if (i != 0) {
                break;
            }
        }

        if (this.isOn) {
            if (!flag) {
                return;
            }

            world.setBlockState(
                    blockPos,
                    Blocks.unlit_redstone_torch
                            .getDefaultState()
                            .withProperty(
                                    net.minecraft.block.BlockTorch.FACING_PROP,
                                    blockState.getValue(net.minecraft.block.BlockTorch.FACING_PROP)),
                    3);
            if (!this.isBurnedOut(world, blockPos, true)) {
                return;
            }

            world.playSoundEffect(
                    blockPos.getX() + 0.5F,
                    blockPos.getY() + 0.5F,
                    blockPos.getZ() + 0.5F,
                    ED,
                    0.5F,
                    2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
            int j = 0;

            while (j < 5) {
                double d0 = blockPos.getX() + random.nextDouble() * 0.6 + 0.2;
                double d1 = blockPos.getY() + random.nextDouble() * 0.6 + 0.2;
                double d2 = blockPos.getZ() + random.nextDouble() * 0.6 + 0.2;
                world.spawnParticle(
                        EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
                j++;
                if (i != 0) {
                    break;
                }
            }

            world.scheduleUpdate(blockPos, world.getBlockState(blockPos).getBlock(), 160);
            if (i == 0) {
                return;
            }
        }

        if (!flag && !this.isBurnedOut(world, blockPos, false)) {
            world.setBlockState(
                    blockPos,
                    Blocks.redstone_torch
                            .getDefaultState()
                            .withProperty(
                                    net.minecraft.block.BlockTorch.FACING_PROP,
                                    blockState.getValue(net.minecraft.block.BlockTorch.FACING_PROP)),
                    3);
        }
    }

    public boolean isAssociatedBlock(Block block) {
        return block == Blocks.unlit_redstone_torch || block == Blocks.redstone_torch;
    }

    public void randomTick(World world, BlockPos blockPos, BlockState blockState, Random random) {}

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Item.getItemFromBlock(Blocks.redstone_torch);
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.redstone_torch);
    }

    // region String decoding support

    // endregion
}
