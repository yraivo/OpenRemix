package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class BlockStoneSlabNew extends BlockSlab {
    public static PropertyBool SEAMLESS;
    public static PropertyEnum VARIANT;
    private static String[] packedTextFragments;

    public IProperty getVariantProperty() {
        return net.minecraft.block.BlockStoneSlabNew.VARIANT;
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (item != Item.getItemFromBlock(Blocks.double_stone_slab2)) {
            for (BlockStoneSlabNewEnumType blockStoneSlabNewEnumType :
                    BlockStoneSlabNewEnumType.values()) {
                list.add(new ItemStack(item, 1, blockStoneSlabNewEnumType.getMetadata()));
                if (i != 0) {
                    break;
                }
            }
        }
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Item.getItemFromBlock(Blocks.stone_slab2);
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        int j =
                b0
                        | ((BlockStoneSlabNewEnumType)
                                        blockState.getValue(
                                                net.minecraft.block.BlockStoneSlabNew.VARIANT))
                                .getMetadata();
        int i = k;
        if (this.isDouble()) {
            if (!(Boolean) blockState.getValue(net.minecraft.block.BlockStoneSlabNew.SEAMLESS)) {
                return j;
            }

            j |= 8;
            if (i != 0) {
                return j;
            }
        }

        if (blockState.getValue(net.minecraft.block.BlockSlab.HALF_PROP)
                == BlockSlabEnumBlockHalf.TOP) {
            j |= 8;
        }

        return j;
    }

    // region String decoding support

    // endregion

    public Object getVariant(ItemStack itemStack) {
        return BlockStoneSlabNewEnumType.byMetadata(itemStack.getItemDamage() & 7);
    }

    public BlockStoneSlabNew() {
        this(BlockPlanksEnumType.getInvertedBlockFlowToken());
    }

    private BlockStoneSlabNew(int initialFlowToken) {
        super(Material.rock);

        BlockState blockState;
        {
            int j = initialFlowToken;

            int i = j;
            blockState = this.blockState.getBaseState();
            if (this.isDouble()) {
                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockStoneSlabNew.SEAMLESS, false);
                if (!(i == 0)) {

                    blockState =
                            blockState.withProperty(
                                    net.minecraft.block.BlockSlab.HALF_PROP,
                                    BlockSlabEnumBlockHalf.BOTTOM);
                }
            } else {

                blockState =
                        blockState.withProperty(
                                net.minecraft.block.BlockSlab.HALF_PROP,
                                BlockSlabEnumBlockHalf.BOTTOM);
            }
        }

        this.setDefaultState(
                blockState.withProperty(
                        net.minecraft.block.BlockStoneSlabNew.VARIANT,
                        BlockStoneSlabNewEnumType.RED_SANDSTONE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public String getFullSlabName(int i) {
        return super.getUnlocalizedName()
                + packedTextFragments[0]
                + BlockStoneSlabNewEnumType.byMetadata(i).getUnlocalizedName();
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockStoneSlabNew.packedTextFragments = new String[2];
        net.minecraft.block.BlockStoneSlabNew.packedTextFragments[0] = new String(".");
        net.minecraft.block.BlockStoneSlabNew.packedTextFragments[1] =
                new String("3\u0000EcXO[3\u00076\u0004VgUD\\");
        net.minecraft.block.BlockStoneSlabNew.SEAMLESS = PropertyBool.create("seamless");
        net.minecraft.block.BlockStoneSlabNew.VARIANT =
                PropertyEnum.create("variant", BlockStoneSlabNewEnumType.class);
    }

    protected BlockStateContainer createBlockState() {
        return this.isDouble()
                ? new BlockStateContainer(
                        this,
                        new IProperty[] {
                            net.minecraft.block.BlockStoneSlabNew.SEAMLESS,
                            net.minecraft.block.BlockStoneSlabNew.VARIANT
                        })
                : new BlockStateContainer(
                        this,
                        new IProperty[] {
                            net.minecraft.block.BlockSlab.HALF_PROP,
                            net.minecraft.block.BlockStoneSlabNew.VARIANT
                        });
    }

    public BlockState getStateFromMeta(int i) {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        BlockState blockState =
                this.getDefaultState()
                        .withProperty(
                                net.minecraft.block.BlockStoneSlabNew.VARIANT,
                                BlockStoneSlabNewEnumType.byMetadata(i & 7));
        int j = k;
        if (this.isDouble()) {
            blockState =
                    blockState.withProperty(
                            net.minecraft.block.BlockStoneSlabNew.SEAMLESS, (i & 8) != 0);
            if (j == 0) {
                return blockState;
            }
        }

        return blockState.withProperty(
                net.minecraft.block.BlockSlab.HALF_PROP,
                (i & 8) == 0 ? BlockSlabEnumBlockHalf.BOTTOM : BlockSlabEnumBlockHalf.TOP);
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.stone_slab2);
    }

    public int damageDropped(BlockState blockState) {
        return ((BlockStoneSlabNewEnumType)
                        blockState.getValue(net.minecraft.block.BlockStoneSlabNew.VARIANT))
                .getMetadata();
    }
}
