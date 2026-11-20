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
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCrops extends BlockBush implements IGrowable {
    public static PropertyInteger AGE;

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {
        int j1 = BlockPlanksEnumType.getLegacyBlockFlowToken();
        super.dropBlockAsItemWithChance(world, blockPos, blockState, f, 0);
        int j = j1;
        if (!world.isRemote) {
            int k = (Integer) blockState.getValue(net.minecraft.block.BlockCrops.AGE);
            if (k >= 7) {
                int l = 3 + i;
                int i1 = 0;

                while (i1 < l) {
                    if (world.rand.nextInt(15) <= k) {
                        spawnAsEntity(world, blockPos, new ItemStack(this.getSeed(), 1, 0));
                    }

                    i1++;
                    if (j == 0) {
                        break;
                    }
                }
            }
        }
    }

    // region String decoding support

    // endregion

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockCrops.AGE, i);
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        super.updateTick(world, blockPos, blockState, random);
        if (world.getLightFromNeighbors(blockPos.offsetUp()) >= 9) {
            int value = (Integer) blockState.getValue(net.minecraft.block.BlockCrops.AGE);
            if (value < 7) {
                float growthChance = getGrowthChance(this, world, blockPos);
                if (random.nextInt((int) (25.0F / growthChance) + 1) == 0) {
                    world.setBlockState(
                            blockPos, blockState.withProperty(net.minecraft.block.BlockCrops.AGE, value + 1), 2);
                }
            }
        }
    }

    public void growCrops(World world, BlockPos blockPos, BlockState blockState) {
        int i =
                (Integer) blockState.getValue(net.minecraft.block.BlockCrops.AGE)
                        + MathHelper.nextIntInclusive(world.rand, 2, 5);
        if (i > 7) {
            i = 7;
        }

        world.setBlockState(blockPos, blockState.withProperty(net.minecraft.block.BlockCrops.AGE, i), 2);
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return ((java.lang.Integer) blockState.getValue(net.minecraft.block.BlockCrops.AGE)) == 7
                ? this.getCrop()
                : this.getSeed();
    }

    static {
        net.minecraft.block.BlockCrops.AGE = PropertyInteger.create("age", 0, 7);
    }

    public boolean canBlockStay(World world, BlockPos blockPos, BlockState blockState) {
        return (world.getLight(blockPos) >= 8 || world.isAgainstSky(blockPos))
                && this.canPlaceBlockOn(world.getBlockState(blockPos.offsetDown()).getBlock());
    }

    public boolean isStillGrowing(
            World world, BlockPos blockPos, BlockState blockState, boolean flag) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockCrops.AGE) < 7;
    }

    public boolean canUseBonemeal(
            World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    protected Item getSeed() {
        return Items.wheat_seeds;
    }

    protected static float getGrowthChance(Block block, World world, BlockPos blockPos) {
        int k1 = BlockPlanksEnumType.getInvertedBlockFlowToken();
        float f = 1.0F;
        BlockPos blockPosValue = blockPos.offsetDown();
        int j = -1;
        int i = k1;

        label78:
        while (j <= 1) {
            int k = -1;

            while (true) {
                if (k <= 1) {
                    float f1 = 0.0F;
                    BlockState blockState = world.getBlockState(blockPosValue.add(j, 0, k));
                    if (blockState.getBlock() == Blocks.farmland) {
                        f1 = 1.0F;
                        if ((Integer) blockState.getValue(BlockFarmland.MOISTURE) > 0) {
                            f1 = 3.0F;
                        }
                    }

                    if (j != 0 || k != 0) {
                        f1 /= 4.0F;
                    }

                    f += f1;
                    k++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label78;
                }
                break;
            }
        }

        BlockPos blockPosValue2 = blockPos.offsetNorth();
        BlockPos blockPosValue3 = blockPos.offsetSouth();
        BlockPos blockPosValue4 = blockPos.offsetWest();
        BlockPos blockPosValue5 = blockPos.offsetEast();
        int l =
                block != world.getBlockState(blockPosValue4).getBlock()
                                && block != world.getBlockState(blockPosValue5).getBlock()
                        ? 0
                        : 1;
        int i1 =
                block != world.getBlockState(blockPosValue2).getBlock()
                                && block != world.getBlockState(blockPosValue3).getBlock()
                        ? 0
                        : 1;
        if (l != 0 && i1 != 0) {
            f /= 2.0F;
            if (i == 0) {
                return f;
            }
        }

        int j1 =
                block != world.getBlockState(blockPosValue4.offsetNorth()).getBlock()
                                && block
                                        != world.getBlockState(blockPosValue5.offsetNorth())
                                                .getBlock()
                                && block
                                        != world.getBlockState(blockPosValue5.offsetSouth())
                                                .getBlock()
                                && block
                                        != world.getBlockState(blockPosValue4.offsetSouth())
                                                .getBlock()
                        ? 0
                        : 1;
        if (j1 != 0) {
            f /= 2.0F;
        }

        return f;
    }

    public void grow(World world, Random random, BlockPos blockPos, BlockState blockState) {
        this.growCrops(world, blockPos, blockState);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockCrops.AGE});
    }

    // region String decoding support

    // endregion

    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.farmland;
    }

    public Item getItem(World world, BlockPos blockPos) {
        return this.getSeed();
    }

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockCrops.AGE);
    }

    protected BlockCrops() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(net.minecraft.block.BlockCrops.AGE, 0));
        this.setTickRandomly(true);
        float f = 0.5F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.setCreativeTab((CreativeTabs) null);
        this.setHardness(0.0F);
        this.setStepSound(net.minecraft.block.Block.soundTypeGrass);
        this.disableStats();
    }

    protected Item getCrop() {
        return Items.wheat;
    }
}
