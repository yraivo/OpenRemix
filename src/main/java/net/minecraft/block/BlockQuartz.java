package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;

public class BlockQuartz extends Block {
    public static PropertyEnum VARIANT_PROP;

    public int getMetaFromState(BlockState blockState) {
        return ((BlockQuartzEnumType)
                        blockState.getValue(net.minecraft.block.BlockQuartz.VARIANT_PROP))
                .getMetaFromState();
    }

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2,
            int i,
            LivingEntity livingEntity) {
        if (i == BlockQuartzEnumType.LINES_Y.getMetaFromState()) {
            switch (facing.getAxis()) {
                case Z:
                    return this.getDefaultState()
                            .withProperty(
                                    net.minecraft.block.BlockQuartz.VARIANT_PROP,
                                    BlockQuartzEnumType.LINES_Z);
                case X:
                    return this.getDefaultState()
                            .withProperty(
                                    net.minecraft.block.BlockQuartz.VARIANT_PROP,
                                    BlockQuartzEnumType.LINES_X);
                case Y:
                default:
                    return this.getDefaultState()
                            .withProperty(
                                    net.minecraft.block.BlockQuartz.VARIANT_PROP,
                                    BlockQuartzEnumType.LINES_Y);
            }
        } else {
            return i == BlockQuartzEnumType.CHISELED.getMetaFromState()
                    ? this.getDefaultState()
                            .withProperty(
                                    net.minecraft.block.BlockQuartz.VARIANT_PROP,
                                    BlockQuartzEnumType.CHISELED)
                    : this.getDefaultState()
                            .withProperty(
                                    net.minecraft.block.BlockQuartz.VARIANT_PROP,
                                    BlockQuartzEnumType.DEFAULT);
        }
    }

    public MapColor getMapColor(BlockState blockState) {
        return MapColor.quartzColor;
    }

    // region String decoding support

    // endregion

    public int damageDropped(BlockState blockState) {
        BlockQuartzEnumType value =
                (BlockQuartzEnumType)
                        blockState.getValue(net.minecraft.block.BlockQuartz.VARIANT_PROP);
        return value != BlockQuartzEnumType.LINES_X && value != BlockQuartzEnumType.LINES_Z
                ? value.getMetaFromState()
                : BlockQuartzEnumType.LINES_Y.getMetaFromState();
    }

    static {
        net.minecraft.block.BlockQuartz.VARIANT_PROP =
                PropertyEnum.create("variant", BlockQuartzEnumType.class);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockQuartz.VARIANT_PROP,
                        BlockQuartzEnumType.byMetadata(i));
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, net.minecraft.block.BlockQuartz.VARIANT_PROP);
    }

    // region String decoding support

    // endregion

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, BlockQuartzEnumType.DEFAULT.getMetaFromState()));
        list.add(new ItemStack(item, 1, BlockQuartzEnumType.CHISELED.getMetaFromState()));
        list.add(new ItemStack(item, 1, BlockQuartzEnumType.LINES_Y.getMetaFromState()));
    }

    protected ItemStack createStackedBlock(BlockState blockState) {
        BlockQuartzEnumType value =
                (BlockQuartzEnumType)
                        blockState.getValue(net.minecraft.block.BlockQuartz.VARIANT_PROP);
        return value != BlockQuartzEnumType.LINES_X && value != BlockQuartzEnumType.LINES_Z
                ? super.createStackedBlock(blockState)
                : new ItemStack(
                        Item.getItemFromBlock(this),
                        1,
                        BlockQuartzEnumType.LINES_Y.getMetaFromState());
    }

    public BlockQuartz() {
        super(Material.rock);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockQuartz.VARIANT_PROP,
                                BlockQuartzEnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
