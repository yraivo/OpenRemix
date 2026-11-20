package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.List;
import java.util.Random;

public class BlockSapling extends BlockBush implements IGrowable {

    public static PropertyEnum TYPE_PROP;
    public static PropertyInteger STAGE_PROP;

    public void grow(World world, Random random, BlockPos blockPos, BlockState blockState) {
        this.grow(world, blockPos, blockState, random);
    }

    public boolean isTypeAt(
            World world, BlockPos blockPos, BlockPlanksEnumType blockPlanksEnumType) {
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock() == this
                && blockState.getValue(net.minecraft.block.BlockSapling.TYPE_PROP)
                        == blockPlanksEnumType;
    }

    public boolean isStillGrowing(
            World world, BlockPos blockPos, BlockState blockState, boolean flag) {
        return true;
    }

    public void generateTree(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i;
        Object object;
        int j;
        int k;
        byte b0;
        label153:
        {
            label148:
            {
                label154:
                {
                    i = BlockPlanksEnumType.getInvertedBlockFlowToken();
                    object =
                            random.nextInt(10) == 0
                                    ? new WorldGenBigTree(true)
                                    : new WorldGenTrees(true);
                    j = 0;
                    k = 0;
                    b0 = 0;
                    switch (((BlockPlanksEnumType)
                            blockState.getValue(net.minecraft.block.BlockSapling.TYPE_PROP))) {
                        case SPRUCE:
                            j = 0;

                            label139:
                            while (j >= -1) {
                                k = 0;

                                while (k >= -1) {
                                    if (this.isTypeAt(
                                                    world,
                                                    blockPos.add(j, 0, k),
                                                    BlockPlanksEnumType.SPRUCE)
                                            && this.isTypeAt(
                                                    world,
                                                    blockPos.add(j + 1, 0, k),
                                                    BlockPlanksEnumType.SPRUCE)
                                            && this.isTypeAt(
                                                    world,
                                                    blockPos.add(j, 0, k + 1),
                                                    BlockPlanksEnumType.SPRUCE)
                                            && this.isTypeAt(
                                                    world,
                                                    blockPos.add(j + 1, 0, k + 1),
                                                    BlockPlanksEnumType.SPRUCE)) {
                                        object =
                                                new WorldGenMegaPineTree(
                                                        false, random.nextBoolean());
                                        b0 = 1;
                                        if (i == 0) {
                                            break label139;
                                        }
                                    }

                                    k--;
                                    if (i != 0) {
                                        break;
                                    }
                                }

                                j--;
                                if (i != 0) {
                                    break;
                                }
                            }

                            if (b0 != 0) {
                                break label153;
                            }

                            k = 0;
                            j = 0;
                            object = new WorldGenTaiga2(true);
                            if (i == 0) {
                                break label153;
                            }
                        case BIRCH:
                            object = new WorldGenForest(true, false);
                            if (i == 0) {
                                break label153;
                            }
                        case JUNGLE:
                            break;
                        case ACACIA:
                            break label154;
                        case DARK_OAK:
                            break label148;
                        case OAK:
                        default:
                            break label153;
                    }

                    j = 0;

                    label117:
                    while (j >= -1) {
                        k = 0;

                        while (k >= -1) {
                            if (this.isTypeAt(
                                            world,
                                            blockPos.add(j, 0, k),
                                            BlockPlanksEnumType.JUNGLE)
                                    && this.isTypeAt(
                                            world,
                                            blockPos.add(j + 1, 0, k),
                                            BlockPlanksEnumType.JUNGLE)
                                    && this.isTypeAt(
                                            world,
                                            blockPos.add(j, 0, k + 1),
                                            BlockPlanksEnumType.JUNGLE)
                                    && this.isTypeAt(
                                            world,
                                            blockPos.add(j + 1, 0, k + 1),
                                            BlockPlanksEnumType.JUNGLE)) {
                                object =
                                        new WorldGenMegaJungle(
                                                true,
                                                10,
                                                20,
                                                BlockPlanksEnumType.JUNGLE.getMetadata(),
                                                BlockPlanksEnumType.JUNGLE.getMetadata());
                                b0 = 1;
                                if (i == 0) {
                                    break label117;
                                }
                            }

                            k--;
                            if (i != 0) {
                                break;
                            }
                        }

                        j--;
                        if (i != 0) {
                            break;
                        }
                    }

                    if (b0 != 0) {
                        break label153;
                    }

                    k = 0;
                    j = 0;
                    object =
                            new WorldGenTrees(
                                    true,
                                    4 + random.nextInt(7),
                                    BlockPlanksEnumType.JUNGLE.getMetadata(),
                                    BlockPlanksEnumType.JUNGLE.getMetadata(),
                                    false);
                    if (i == 0) {
                        break label153;
                    }
                }

                object = new WorldGenSavannaTree(true);
                if (i == 0) {
                    break label153;
                }
            }

            j = 0;

            label87:
            while (j >= -1) {
                k = 0;

                while (k >= -1) {
                    if (this.isTypeAt(world, blockPos.add(j, 0, k), BlockPlanksEnumType.DARK_OAK)
                            && this.isTypeAt(
                                    world, blockPos.add(j + 1, 0, k), BlockPlanksEnumType.DARK_OAK)
                            && this.isTypeAt(
                                    world, blockPos.add(j, 0, k + 1), BlockPlanksEnumType.DARK_OAK)
                            && this.isTypeAt(
                                    world,
                                    blockPos.add(j + 1, 0, k + 1),
                                    BlockPlanksEnumType.DARK_OAK)) {
                        object = new WorldGenCanopyTree(true);
                        b0 = 1;
                        if (i == 0) {
                            break label87;
                        }
                    }

                    k--;
                    if (i != 0) {
                        break;
                    }
                }

                j--;
                if (i != 0) {
                    break;
                }
            }

            if (b0 == 0) {
                return;
            }
        }

        {
            BlockState blockStateValue = Blocks.air.getDefaultState();
            if (b0 != 0) {
                world.setBlockState(blockPos.add(j, 0, k), blockStateValue, 4);
                world.setBlockState(blockPos.add(j + 1, 0, k), blockStateValue, 4);
                world.setBlockState(blockPos.add(j, 0, k + 1), blockStateValue, 4);
                world.setBlockState(blockPos.add(j + 1, 0, k + 1), blockStateValue, 4);
                if (!(i == 0)) {

                    world.setBlockState(blockPos, blockStateValue, 4);
                }
            } else {

                world.setBlockState(blockPos, blockStateValue, 4);
            }
        }

        if (!((WorldGenerator) object).generate(world, random, blockPos.add(j, 0, k))) {
            if (b0 != 0) {
                world.setBlockState(blockPos.add(j, 0, k), blockState, 4);
                world.setBlockState(blockPos.add(j + 1, 0, k), blockState, 4);
                world.setBlockState(blockPos.add(j, 0, k + 1), blockState, 4);
                world.setBlockState(blockPos.add(j + 1, 0, k + 1), blockState, 4);
                if (i == 0) {
                    return;
                }
            }

            world.setBlockState(blockPos, blockState, 4);
        }
    }

    public void grow(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if ((Integer) blockState.getValue(net.minecraft.block.BlockSapling.STAGE_PROP) == 0) {
            world.setBlockState(
                    blockPos,
                    blockState.cycleProperty(net.minecraft.block.BlockSapling.STAGE_PROP),
                    4);
            if (i != 0) {
                return;
            }
        }

        this.generateTree(world, blockPos, blockState, random);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockSapling.TYPE_PROP,
                        BlockPlanksEnumType.byMetadata(i & 7))
                .withProperty(net.minecraft.block.BlockSapling.STAGE_PROP, (i & 8) >> 3);
    }

    // region String decoding support

    // endregion

    protected BlockSapling() {
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockSapling.TYPE_PROP, BlockPlanksEnumType.OAK)
                        .withProperty(net.minecraft.block.BlockSapling.STAGE_PROP, 0));
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public boolean canUseBonemeal(
            World world, Random random, BlockPos blockPos, BlockState blockState) {
        return world.rand.nextFloat() < 0.45;
    }

    static {
        net.minecraft.block.BlockSapling.TYPE_PROP =
                PropertyEnum.create("type", BlockPlanksEnumType.class);
        net.minecraft.block.BlockSapling.STAGE_PROP = PropertyInteger.create("stage", 0, 1);
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        if (!world.isRemote) {
            super.updateTick(world, blockPos, blockState, random);
            if (world.getLightFromNeighbors(blockPos.offsetUp()) >= 9 && random.nextInt(7) == 0) {
                this.grow(world, blockPos, blockState, random);
            }
        }
    }

    public int damageDropped(BlockState blockState) {
        return ((BlockPlanksEnumType)
                        blockState.getValue(net.minecraft.block.BlockSapling.TYPE_PROP))
                .getMetadata();
    }

    // region String decoding support

    // endregion

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((BlockPlanksEnumType)
                                        blockState.getValue(
                                                net.minecraft.block.BlockSapling.TYPE_PROP))
                                .getMetadata();
        return i | (Integer) blockState.getValue(net.minecraft.block.BlockSapling.STAGE_PROP) << 3;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockSapling.TYPE_PROP,
                    net.minecraft.block.BlockSapling.STAGE_PROP
                });
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        BlockPlanksEnumType[] blockPlanksEnumTypeArray = BlockPlanksEnumType.values();
        int l = BlockPlanksEnumType.getLegacyBlockFlowToken();
        int j = blockPlanksEnumTypeArray.length;
        int k = 0;
        int i = l;

        while (k < j) {
            BlockPlanksEnumType blockPlanksEnumType = blockPlanksEnumTypeArray[k];
            list.add(new ItemStack(item, 1, blockPlanksEnumType.getMetadata()));
            k++;
            if (i == 0) {
                break;
            }
        }
    }
}
