package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class BlockFlower extends BlockBush {
    private static String ET;

    protected PropertyEnum type;

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(
                        this.getTypeProperty(),
                        BlockFlowerEnumFlowerType.getType(this.getBlockType(), i));
    }

    public IProperty getTypeProperty() {
        if (this.type == null) {
            this.type =
                    PropertyEnum.create(
                            ET,
                            BlockFlowerEnumFlowerType.class,
                            new GetTypePropertyPredicate(this));
        }

        return this.type;
    }

    public abstract BlockFlowerEnumFlowerColor getBlockType();

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockFlowerEnumFlowerType[] blockFlowerEnumFlowerTypeArray =
                BlockFlowerEnumFlowerType.getTypes(this.getBlockType());
        int i = j;

        for (BlockFlowerEnumFlowerType blockFlowerEnumFlowerType : blockFlowerEnumFlowerTypeArray) {
            list.add(new ItemStack(item, 1, blockFlowerEnumFlowerType.getMeta()));
            if (i != 0) {
                break;
            }
        }
    }

    public int getMetaFromState(BlockState blockState) {
        return ((BlockFlowerEnumFlowerType) blockState.getValue(this.getTypeProperty())).getMeta();
    }

    protected BlockFlower() {
        super(Material.plants);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                this.getTypeProperty(),
                                this.getBlockType() == BlockFlowerEnumFlowerColor.RED
                                        ? BlockFlowerEnumFlowerType.POPPY
                                        : BlockFlowerEnumFlowerType.DANDELION));
    }

    static {
        ET = "type";
    }

    // region String decoding support

    // endregion

    public BlockEnumOffsetType getOffsetType() {
        return BlockEnumOffsetType.XZ;
    }

    // region String decoding support

    // endregion

    public int damageDropped(BlockState blockState) {
        return ((BlockFlowerEnumFlowerType) blockState.getValue(this.getTypeProperty())).getMeta();
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {this.getTypeProperty()});
    }

    // Helper used only by this class.
    public static class GetTypePropertyPredicate implements com.google.common.base.Predicate {
        net.minecraft.block.BlockFlower aTX;

        public GetTypePropertyPredicate(net.minecraft.block.BlockFlower aTXValue) {
            this.aTX = aTXValue;
        }

        public boolean method_d11a6eeda37adcb59377(
                net.minecraft.block.BlockFlowerEnumFlowerType blockFlowerEnumFlowerType) {
            return blockFlowerEnumFlowerType.getBlockType() == this.aTX.getBlockType();
        }

        public boolean apply(java.lang.Object object) {
            return this.method_d11a6eeda37adcb59377(
                    (net.minecraft.block.BlockFlowerEnumFlowerType) object);
        }
    }
}
