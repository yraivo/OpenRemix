package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockMycelium extends Block {

    public static PropertyBool SNOWY_PROP;

    static {
        net.minecraft.block.BlockMycelium.SNOWY_PROP = PropertyBool.create("snowy");
    }

    protected BlockMycelium() {
        super(Material.grass);
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockMycelium.SNOWY_PROP, false));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public int getMetaFromState(BlockState blockState) {
        return 0;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockMycelium.SNOWY_PROP});
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Blocks.dirt.getItemDropped(
                Blocks.dirt
                        .getDefaultState()
                        .withProperty(BlockDirt.VARIANT, BlockDirtDirtType.DIRT),
                random,
                i);
    }

    // region String decoding support

    // endregion

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {
        super.randomDisplayTick(world, blockPos, blockState, random);
        if (random.nextInt(10) == 0) {
            world.spawnParticle(
                    EnumParticleTypes.TOWN_AURA,
                    blockPos.getX() + random.nextFloat(),
                    blockPos.getY() + 1.1F,
                    blockPos.getZ() + random.nextFloat(),
                    0.0,
                    0.0,
                    0.0,
                    new int[0]);
        }
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        Block block = iBlockAccess.getBlockState(blockPos.offsetUp()).getBlock();
        return blockState.withProperty(
                net.minecraft.block.BlockMycelium.SNOWY_PROP, block == Blocks.snow || block == Blocks.snow_layer);
    }

    // region String decoding support

    // endregion

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!world.isRemote) {
            if (world.getLightFromNeighbors(blockPos.offsetUp()) < 4
                    && world.getBlockState(blockPos.offsetUp()).getBlock().getLightOpacity() > 2) {
                world.setBlockState(
                        blockPos,
                        Blocks.dirt
                                .getDefaultState()
                                .withProperty(BlockDirt.VARIANT, BlockDirtDirtType.DIRT));
                if (i == 0) {
                    return;
                }
            }

            if (world.getLightFromNeighbors(blockPos.offsetUp()) >= 9) {
                int j = 0;

                while (j < 4) {
                    BlockPos blockPosValue =
                            blockPos.add(
                                    random.nextInt(3) - 1,
                                    random.nextInt(5) - 3,
                                    random.nextInt(3) - 1);
                    BlockState blockStateValue = world.getBlockState(blockPosValue);
                    Block block = world.getBlockState(blockPosValue.offsetUp()).getBlock();
                    if (blockStateValue.getBlock() == Blocks.dirt
                            && blockStateValue.getValue(BlockDirt.VARIANT) == BlockDirtDirtType.DIRT
                            && world.getLightFromNeighbors(blockPosValue.offsetUp()) >= 4
                            && block.getLightOpacity() <= 2) {
                        world.setBlockState(blockPosValue, this.getDefaultState());
                    }

                    j++;
                    if (i != 0) {
                        break;
                    }
                }
            }
        }
    }
}
