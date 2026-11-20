package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockTallGrass extends BlockBush implements IGrowable {
    public static PropertyEnum TYPE;

    public BlockEnumOffsetType getOffsetType() {
        return BlockEnumOffsetType.XYZ;
    }

    public boolean canBlockStay(World world, BlockPos blockPos, BlockState blockState) {
        return this.canPlaceBlockOn(world.getBlockState(blockPos.offsetDown()).getBlock());
    }

    public void grow(World world, Random random, BlockPos blockPos, BlockState blockState) {
        BlockDoublePlantEnumPlantType blockDoublePlantEnumPlantType =
                BlockDoublePlantEnumPlantType.GRASS;
        if (blockState.getValue(net.minecraft.block.BlockTallGrass.TYPE) == BlockTallGrassEnumType.FERN) {
            blockDoublePlantEnumPlantType = BlockDoublePlantEnumPlantType.FERN;
        }

        if (Blocks.double_plant.canPlaceBlockAt(world, blockPos)) {
            Blocks.double_plant.placeAt(world, blockPos, blockDoublePlantEnumPlantType, 2);
        }
    }

    public int getBlockColor() {
        return ColorizerGrass.getGrassColor(0.5, 1.0);
    }

    // region String decoding support

    // endregion

    public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
        return iBlockAccess.getBiomeGenForCoords(blockPos).getGrassColorAtPos(blockPos);
    }

    public boolean isReplaceable(World world, BlockPos blockPos) {
        return true;
    }

    public boolean canUseBonemeal(
            World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    public int getMetaFromState(BlockState blockState) {
        return ((BlockTallGrassEnumType) blockState.getValue(net.minecraft.block.BlockTallGrass.TYPE)).getMeta();
    }

    public int getDamageValue(World world, BlockPos blockPos) {
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock().getMetaFromState(blockState);
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        int j = 1;
        int i = k;

        while (j < 3) {
            list.add(new ItemStack(item, 1, j));
            j++;
            if (i == 0) {
                break;
            }
        }
    }

    protected BlockTallGrass() {
        super(Material.vine);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockTallGrass.TYPE, BlockTallGrassEnumType.DEAD_BUSH));
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return random.nextInt(8) == 0 ? Items.wheat_seeds : null;
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockTallGrass.TYPE = PropertyEnum.create("type", BlockTallGrassEnumType.class);
    }

    public void harvestBlock(
            World world,
            PlayerEntity playerEntity,
            BlockPos blockPos,
            BlockState blockState,
            TileEntity tileEntity) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!world.isRemote
                && playerEntity.getCurrentEquippedItem() != null
                && playerEntity.getCurrentEquippedItem().getItem() == Items.shears) {
            playerEntity.triggerAchievement(
                    StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            spawnAsEntity(
                    world,
                    blockPos,
                    new ItemStack(
                            Blocks.tallgrass,
                            1,
                            ((BlockTallGrassEnumType)
                                            blockState.getValue(net.minecraft.block.BlockTallGrass.TYPE))
                                    .getMeta()));
            if (i == 0) {
                return;
            }
        }

        super.harvestBlock(world, playerEntity, blockPos, blockState, tileEntity);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockTallGrass.TYPE, BlockTallGrassEnumType.byMetadata(i));
    }

    public int quantityDroppedWithBonus(int i, Random random) {
        return 1 + random.nextInt(i * 2 + 1);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockTallGrass.TYPE});
    }

    public boolean isStillGrowing(
            World world, BlockPos blockPos, BlockState blockState, boolean flag) {
        return blockState.getValue(net.minecraft.block.BlockTallGrass.TYPE) != BlockTallGrassEnumType.DEAD_BUSH;
    }

    public int getRenderColor(BlockState blockState) {
        if (blockState.getBlock() != this) {
            return super.getRenderColor(blockState);
        }

        BlockTallGrassEnumType value =
                (BlockTallGrassEnumType) blockState.getValue(net.minecraft.block.BlockTallGrass.TYPE);
        return value == BlockTallGrassEnumType.DEAD_BUSH
                ? 16777215
                : ColorizerGrass.getGrassColor(0.5, 1.0);
    }
}
