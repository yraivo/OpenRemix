package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BlockOldLog extends BlockLog {

    public static PropertyEnum VARIANT_PROP;

    static {
        net.minecraft.block.BlockOldLog.VARIANT_PROP =
                PropertyEnum.create(
                        "variant", BlockPlanksEnumType.class, new BlockPlanksEnumTypePredicate());
    }

    public BlockOldLog() {
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockOldLog.VARIANT_PROP,
                                BlockPlanksEnumType.OAK)
                        .withProperty(net.minecraft.block.BlockLog.AXIS_PROP, BlockLogEnumAxis.Y));
    }

    // region String decoding support

    // endregion

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, BlockPlanksEnumType.OAK.getMetadata()));
        list.add(new ItemStack(item, 1, BlockPlanksEnumType.SPRUCE.getMetadata()));
        list.add(new ItemStack(item, 1, BlockPlanksEnumType.BIRCH.getMetadata()));
        list.add(new ItemStack(item, 1, BlockPlanksEnumType.JUNGLE.getMetadata()));
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockOldLog.VARIANT_PROP,
                    net.minecraft.block.BlockLog.AXIS_PROP
                });
    }

    public BlockState getStateFromMeta(int i) {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockState blockState =
                this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockOldLog.VARIANT_PROP,
                                BlockPlanksEnumType.byMetadata((i & 3) % 4));
        int j = k;
        switch (i & 12) {
            case 0:
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockLog.AXIS_PROP, BlockLogEnumAxis.Y);
                if (j == 0) {
                    break;
                }
            case 4:
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockLog.AXIS_PROP, BlockLogEnumAxis.X);
                if (j == 0) {
                    break;
                }
            case 8:
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockLog.AXIS_PROP, BlockLogEnumAxis.Z);
                if (j == 0) {
                    break;
                }
            default:
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockLog.AXIS_PROP, BlockLogEnumAxis.NONE);
        }

        return blockState;
    }

    protected ItemStack createStackedBlock(BlockState blockState) {
        return new ItemStack(
                Item.getItemFromBlock(this),
                1,
                ((BlockPlanksEnumType)
                                blockState.getValue(net.minecraft.block.BlockOldLog.VARIANT_PROP))
                        .getMetadata());
    }

    public int damageDropped(BlockState blockState) {
        return ((BlockPlanksEnumType)
                        blockState.getValue(net.minecraft.block.BlockOldLog.VARIANT_PROP))
                .getMetadata();
    }

    public int getMetaFromState(BlockState blockState) {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        byte b0 = 0;
        int j =
                b0
                        | ((BlockPlanksEnumType)
                                        blockState.getValue(
                                                net.minecraft.block.BlockOldLog.VARIANT_PROP))
                                .getMetadata();
        int i = k;
        switch (((BlockLogEnumAxis) blockState.getValue(net.minecraft.block.BlockLog.AXIS_PROP))) {
            case X:
                j |= 4;
                if (i == 0) {
                    break;
                }
            case Z:
                j |= 8;
                if (i == 0) {
                    break;
                }
            case NONE:
                j |= 12;
        }

        return j;
    }

    // region String decoding support

    // endregion

    // Helper used only by this class.
    public static final class BlockPlanksEnumTypePredicate
            implements com.google.common.base.Predicate {
        public boolean apply(java.lang.Object object) {
            return this.method_6242e4af980f87ad42df(
                    (net.minecraft.block.BlockPlanksEnumType) object);
        }

        public boolean method_6242e4af980f87ad42df(
                net.minecraft.block.BlockPlanksEnumType blockPlanksEnumType) {
            return blockPlanksEnumType.getMetadata() < 4;
        }
    }
}
