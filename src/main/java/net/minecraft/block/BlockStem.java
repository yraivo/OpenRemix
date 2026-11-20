package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockStem extends BlockBush implements IGrowable {
    public static PropertyDirection FACING_PROP;
    public static PropertyInteger AGE_PROP;

    private Block cropBlock;

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        super.updateTick(world, blockPos, blockState, random);
        int i = k;
        if (world.getLightFromNeighbors(blockPos.offsetUp()) >= 9) {
            float growthChance = BlockCrops.getGrowthChance(this, world, blockPos);
            if (random.nextInt((int) (25.0F / growthChance) + 1) == 0) {
                int j = (Integer) blockState.getValue(net.minecraft.block.BlockStem.AGE_PROP);
                if (j < 7) {
                    blockState =
                            blockState.withProperty(net.minecraft.block.BlockStem.AGE_PROP, j + 1);
                    world.setBlockState(blockPos, blockState, 2);
                    if (i != 0) {
                        return;
                    }
                }

                for (java.util.Iterator<?> __recoveryIterator171_1828 =
                                (FacingPlane.HORIZONTAL).iterator();
                        (__recoveryIterator171_1828).hasNext(); ) {
                    Facing facing = (Facing) (__recoveryIterator171_1828).next();
                    if (world.getBlockState(blockPos.offset(facing)).getBlock() == this.cropBlock) {
                        return;
                    }

                    if (i == 0) {
                        break;
                    }
                }

                blockPos = blockPos.offset(FacingPlane.HORIZONTAL.random(random));
                Block block = world.getBlockState(blockPos.offsetDown()).getBlock();
                if (world.getBlockState(blockPos).getBlock().blockMaterial == Material.AIR
                        && (block == Blocks.farmland
                                || block == Blocks.dirt
                                || block == Blocks.grass)) {
                    world.setBlockState(blockPos, this.cropBlock.getDefaultState());
                }
            }
        }
    }

    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.farmland;
    }

    public Item getItem(World world, BlockPos blockPos) {
        Item item = this.getSeedItem();
        return item != null ? item : null;
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {
        int i1 = BlockPlanksEnumType.getInvertedBlockFlowToken();
        super.dropBlockAsItemWithChance(world, blockPos, blockState, f, i);
        int j = i1;
        if (!world.isRemote) {
            Item item = this.getSeedItem();
            if (item != null) {
                int k = (Integer) blockState.getValue(net.minecraft.block.BlockStem.AGE_PROP);
                int l = 0;

                while (l < 3) {
                    if (world.rand.nextInt(15) <= k) {
                        spawnAsEntity(world, blockPos, new ItemStack(item));
                    }

                    l++;
                    if (j != 0) {
                        break;
                    }
                }
            }
        }
    }

    public BlockState getStateFromMeta(int p171_0) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockStem.AGE_PROP, p171_0);
    }

    static {
        net.minecraft.block.BlockStem.AGE_PROP = PropertyInteger.create("age", 0, 7);
        net.minecraft.block.BlockStem.FACING_PROP =
                PropertyDirection.create("facing", new FacingPredicate());
    }

    protected BlockStem(Block ahtValue) {
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockStem.AGE_PROP, 0)
                        .withProperty(net.minecraft.block.BlockStem.FACING_PROP, Facing.UP));
        this.cropBlock = ahtValue;
        this.setTickRandomly(true);
        float f = 0.125F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.setCreativeTab((CreativeTabs) null);
    }

    public boolean isStillGrowing(
            World world, BlockPos blockPos, BlockState blockState, boolean p171_3) {
        return (java.lang.Integer) blockState.getValue(net.minecraft.block.BlockStem.AGE_PROP) != 7;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        this.maxY =
                ((java.lang.Integer)
                                                iBlockAccess
                                                        .getBlockState(blockPos)
                                                        .getValue(
                                                                net.minecraft.block.BlockStem
                                                                        .AGE_PROP)
                                        * 2
                                + 2)
                        / 16.0F;
        float f = 0.125F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, (float) this.maxY, 0.5F + f);
    }

    public int getMetaFromState(BlockState blockState) {
        return (java.lang.Integer) blockState.getValue(net.minecraft.block.BlockStem.AGE_PROP);
    }

    public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int p171_2) {
        return this.getRenderColor(iBlockAccess.getBlockState(blockPos));
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        blockState = blockState.withProperty(net.minecraft.block.BlockStem.FACING_PROP, Facing.UP);
        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_10804 =
                        (FacingPlane.HORIZONTAL).iterator();
                (__recoveryIterator171_10804).hasNext(); ) {
            Facing facing = (Facing) (__recoveryIterator171_10804).next();
            if (iBlockAccess.getBlockState(blockPos.offset(facing)).getBlock() == this.cropBlock) {
                blockState =
                        blockState.withProperty(net.minecraft.block.BlockStem.FACING_PROP, facing);
                if (i == 0) {
                    break;
                }
            }

            if (i != 0) {
                break;
            }
        }

        return blockState;
    }

    public void setBlockBoundsForItemRender() {
        float f = 0.125F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
    }

    protected BlockStateContainer createBlockState() {
        return new net.minecraft.block.state.BlockStateContainer(
                this,
                new net.minecraft.block.properties.IProperty[] {
                    net.minecraft.block.BlockStem.AGE_PROP,
                    net.minecraft.block.BlockStem.FACING_PROP
                });
    }

    public void grow(World world, Random random, BlockPos blockPos, BlockState blockState) {
        this.growStem(world, blockPos, blockState);
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return null;
    }

    public void growStem(World world, BlockPos blockPos, BlockState blockState) {
        int i =
                (Integer) blockState.getValue(net.minecraft.block.BlockStem.AGE_PROP)
                        + MathHelper.nextIntInclusive(world.rand, 2, 5);
        world.setBlockState(
                blockPos,
                blockState.withProperty(net.minecraft.block.BlockStem.AGE_PROP, Math.min(7, i)),
                2);
    }

    public int getRenderColor(BlockState blockState) {
        if (blockState.getBlock() != this) {
            return super.getRenderColor(blockState);
        }

        int value = (java.lang.Integer) blockState.getValue(net.minecraft.block.BlockStem.AGE_PROP);
        int j = value * 32;
        int k = 255 - value * 8;
        int l = value * 4;
        return j << 16 | k << 8 | l;
    }

    protected Item getSeedItem() {
        return this.cropBlock == Blocks.pumpkin
                ? Items.pumpkin_seeds
                : (this.cropBlock == Blocks.melon_block ? Items.melon_seeds : null);
    }

    public boolean canUseBonemeal(
            World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    // Helper used only by this class.
    public static final class FacingPredicate implements com.google.common.base.Predicate {
        public boolean apply(java.lang.Object object) {
            return this.apply((net.minecraft.util.Facing) object);
        }

        public boolean apply(net.minecraft.util.Facing facing) {
            return facing != net.minecraft.util.Facing.DOWN;
        }
    }
}
