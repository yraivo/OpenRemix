package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BlockSponge extends Block {
    public static PropertyBool WET_PROP;

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockSponge.WET_PROP)) {
            Facing facing = Facing.random(random);
            if (facing != Facing.UP
                    && !World.doesBlockHaveSolidTopSurface(world, blockPos.offset(facing))) {
                double d0;
                double d1;
                double d2;
                label36:
                {
                    d0 = blockPos.getX();
                    d1 = blockPos.getY();
                    d2 = blockPos.getZ();
                    if (facing == Facing.DOWN) {
                        d1 -= 0.05;
                        d0 += random.nextDouble();
                        d2 += random.nextDouble();
                        if (i == 0) {
                            break label36;
                        }
                    }

                    d1 += random.nextDouble() * 0.8;
                    if (facing.getAxis() == FacingAxis.X) {
                        d2 += random.nextDouble();
                        if (facing == Facing.EAST) {
                            d0++;
                            if (i == 0) {
                                break label36;
                            }
                        }

                        d0 += 0.05;
                        if (i == 0) {
                            break label36;
                        }
                    }

                    d0 += random.nextDouble();
                    if (facing == Facing.SOUTH) {
                        d2++;
                        if (i == 0) {
                            break label36;
                        }
                    }

                    d2 += 0.05;
                }

                world.spawnParticle(
                        EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }

    // region String decoding support

    // endregion

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockSponge.WET_PROP});
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        this.setWet(world, blockPos, blockState);
        super.onNeighborBlockChange(world, blockPos, blockState, block);
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        this.setWet(world, blockPos, blockState);
    }

    public int damageDropped(BlockState blockState) {
        return ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockSponge.WET_PROP)) ? 1 : 0;
    }

    static {
        net.minecraft.block.BlockSponge.WET_PROP = PropertyBool.create("wet");
    }

    protected void setWet(World world, BlockPos blockPos, BlockState blockState) {
        if (!(Boolean) blockState.getValue(net.minecraft.block.BlockSponge.WET_PROP)
                && this.absorbWater(world, blockPos)) {
            world.setBlockState(
                    blockPos, blockState.withProperty(net.minecraft.block.BlockSponge.WET_PROP, true), 2);
            world.playAuxSFX(2001, blockPos, Block.getIdFromBlock(Blocks.water));
        }
    }

    protected BlockSponge() {
        super(Material.sponge);
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockSponge.WET_PROP, false));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    private boolean absorbWater(World world, BlockPos blockPos) {
        LinkedList linkedlist = Lists.newLinkedList();
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        ArrayList arrayList = Lists.newArrayList();
        linkedlist.add(new Tuple(blockPos, 0));
        int j = 0;
        int i = l;

        while (!linkedlist.isEmpty()) {
            Tuple tuple =
                    (Tuple) linkedlist.poll();
            BlockPos blockPosValue = (BlockPos) tuple.getFirst();
            int k = (Integer) tuple.getSecond();

            for (Facing facing : Facing.values()) {
                BlockPos blockPosValue2 = blockPosValue.offset(facing);
                if (world.getBlockState(blockPosValue2).getBlock().getMaterial()
                        == Material.water) {
                    world.setBlockState(blockPosValue2, Blocks.air.getDefaultState(), 2);
                    arrayList.add(blockPosValue2);
                    j++;
                    if (k < 6) {
                        linkedlist.add(new Tuple(blockPosValue2, k + 1));
                    }
                }

                if (i != 0) {
                    break;
                }
            }

            if (j > 64 && i == 0 || i != 0) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_10399 = (arrayList).iterator();
                (__recoveryIterator171_10399).hasNext(); ) {
            BlockPos blockPosValue3 = (BlockPos) (__recoveryIterator171_10399).next();
            world.notifyNeighborsOfStateChange(blockPosValue3, Blocks.air);
            if (i != 0) {
                break;
            }
        }

        return j > 0;
    }

    // region String decoding support

    // endregion

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockSponge.WET_PROP, (i & 1) == 1);
    }

    public int getMetaFromState(BlockState blockState) {
        return ((java.lang.Boolean) blockState.getValue(net.minecraft.block.BlockSponge.WET_PROP)) ? 1 : 0;
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }
}
