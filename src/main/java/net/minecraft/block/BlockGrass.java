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
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

import java.util.Random;

public class BlockGrass extends Block implements IGrowable {
    public static PropertyBool SNOWY;

    public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
        return BiomeColorHelper.getGrassColorAtPos(iBlockAccess, blockPos);
    }

    // region String decoding support

    // endregion

    public boolean canUseBonemeal(
            World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    public int getBlockColor() {
        return ColorizerGrass.getGrassColor(0.5, 1.0);
    }

    public int getMetaFromState(BlockState blockState) {
        return 0;
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        Block block = iBlockAccess.getBlockState(blockPos.offsetUp()).getBlock();
        return blockState.withProperty(
                net.minecraft.block.BlockGrass.SNOWY, block == Blocks.snow || block == Blocks.snow_layer);
    }

    static {
        net.minecraft.block.BlockGrass.SNOWY = PropertyBool.create("snowy");
    }

    public int getRenderColor(BlockState blockState) {
        return this.getBlockColor();
    }

    // region String decoding support

    // endregion

    protected BlockGrass() {
        super(Material.grass);
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockGrass.SNOWY, false));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Blocks.dirt.getItemDropped(
                Blocks.dirt
                        .getDefaultState()
                        .withProperty(BlockDirt.VARIANT, BlockDirtDirtType.DIRT),
                random,
                i);
    }

    public boolean isStillGrowing(
            World world, BlockPos blockPos, BlockState blockState, boolean flag) {
        return true;
    }

    public void grow(World world, Random random, BlockPos blockPos, BlockState blockState) {
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockPos blockPosValue = blockPos.offsetUp();
        int j = 0;
        int i = l;

        while (j < 128) {
            BlockPos blockPosValue2 = blockPosValue;
            int k = 0;

            while (true) {
                if (k < j / 16) {
                    blockPosValue2 =
                            blockPosValue2.add(
                                    random.nextInt(3) - 1,
                                    (random.nextInt(3) - 1) * random.nextInt(3) / 2,
                                    random.nextInt(3) - 1);
                    if (world.getBlockState(blockPosValue2.offsetDown()).getBlock() != Blocks.grass
                            || world.getBlockState(blockPosValue2).getBlock().isNormalCube()) {
                        break;
                    }

                    k++;
                    if (i == 0) {
                        continue;
                    }
                }

                if (world.getBlockState(blockPosValue2).getBlock().blockMaterial == Material.AIR) {
                    if (random.nextInt(8) == 0) {
                        BlockFlowerEnumFlowerType blockFlowerEnumFlowerType =
                                world.getBiomeGenForCoords(blockPosValue2)
                                        .pickRandomFlower(random, blockPosValue2);
                        BlockFlower blockFlower = blockFlowerEnumFlowerType.getBlockType().getBlock();
                        BlockState blockStateValue =
                                blockFlower
                                        .getDefaultState()
                                        .withProperty(
                                                blockFlower.getTypeProperty(),
                                                blockFlowerEnumFlowerType);
                        if (blockFlower.canBlockStay(world, blockPosValue2, blockStateValue)) {
                            world.setBlockState(blockPosValue2, blockStateValue, 3);
                        }

                        if (i == 0) {
                            break;
                        }
                    }

                    BlockState blockStateValue2 =
                            Blocks.tallgrass
                                    .getDefaultState()
                                    .withProperty(
                                            BlockTallGrass.TYPE, BlockTallGrassEnumType.GRASS);
                    if (Blocks.tallgrass.canBlockStay(world, blockPosValue2, blockStateValue2)) {
                        world.setBlockState(blockPosValue2, blockStateValue2, 3);
                    }
                }
                break;
            }

            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (!world.isRemote) {
            if (world.getLightFromNeighbors(blockPos.offsetUp()) < 4
                    && world.getBlockState(blockPos.offsetUp()).getBlock().getLightOpacity() > 2) {
                world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
                if (i != 0) {
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
                    Block block = world.getBlockState(blockPosValue.offsetUp()).getBlock();
                    BlockState blockStateValue = world.getBlockState(blockPosValue);
                    if (blockStateValue.getBlock() == Blocks.dirt
                            && blockStateValue.getValue(BlockDirt.VARIANT) == BlockDirtDirtType.DIRT
                            && world.getLightFromNeighbors(blockPosValue.offsetUp()) >= 4
                            && block.getLightOpacity() <= 2) {
                        world.setBlockState(blockPosValue, Blocks.grass.getDefaultState());
                    }

                    j++;
                    if (i == 0) {
                        break;
                    }
                }
            }
        }
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockGrass.SNOWY});
    }
}
