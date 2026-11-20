package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNetherWart extends BlockBush {
    public static PropertyInteger AGE_PROP;

    static {
        net.minecraft.block.BlockNetherWart.AGE_PROP = PropertyInteger.create("age", 0, 3);
    }

    protected BlockNetherWart() {
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockNetherWart.AGE_PROP, 0));
        this.setTickRandomly(true);
        float f = 0.5F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.setCreativeTab((CreativeTabs) null);
    }

    // region String decoding support

    // endregion

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockNetherWart.AGE_PROP, i);
    }

    public boolean canBlockStay(World world, BlockPos blockPos, BlockState blockState) {
        return this.canPlaceBlockOn(world.getBlockState(blockPos.offsetDown()).getBlock());
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return null;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockNetherWart.AGE_PROP});
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int value = (Integer) blockState.getValue(net.minecraft.block.BlockNetherWart.AGE_PROP);
        if (value < 3 && random.nextInt(10) == 0) {
            blockState = blockState.withProperty(net.minecraft.block.BlockNetherWart.AGE_PROP, value + 1);
            world.setBlockState(blockPos, blockState, 2);
        }

        super.updateTick(world, blockPos, blockState, random);
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!world.isRemote) {
            int k = 1;
            if ((Integer) blockState.getValue(net.minecraft.block.BlockNetherWart.AGE_PROP) >= 3) {
                k = 2 + world.rand.nextInt(3);
                if (i > 0) {
                    k += world.rand.nextInt(i + 1);
                }
            }

            int l = 0;

            while (l < k) {
                spawnAsEntity(world, blockPos, new ItemStack(Items.nether_wart));
                l++;
                if (j != 0) {
                    break;
                }
            }
        }
    }

    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.soul_sand;
    }

    // region String decoding support

    // endregion

    public Item getItem(World world, BlockPos blockPos) {
        return Items.nether_wart;
    }

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockNetherWart.AGE_PROP);
    }
}
