package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockOldLeaf extends BlockLeaves {

    public static PropertyEnum VARIANT_PROP;

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((BlockPlanksEnumType)
                                        blockState.getValue(
                                                net.minecraft.block.BlockOldLeaf.VARIANT_PROP))
                                .getMetadata();
        if (!(Boolean) blockState.getValue(net.minecraft.block.BlockLeaves.DECAYABLE)) {
            i |= 4;
        }

        if ((Boolean) blockState.getValue(net.minecraft.block.BlockLeaves.CHECK_DECAY)) {
            i |= 8;
        }

        return i;
    }

    public int damageDropped(BlockState blockState) {
        return ((BlockPlanksEnumType)
                        blockState.getValue(net.minecraft.block.BlockOldLeaf.VARIANT_PROP))
                .getMetadata();
    }

    protected ItemStack createStackedBlock(BlockState blockState) {
        return new ItemStack(
                Item.getItemFromBlock(this),
                1,
                ((BlockPlanksEnumType)
                                blockState.getValue(net.minecraft.block.BlockOldLeaf.VARIANT_PROP))
                        .getMetadata());
    }

    // region String decoding support

    // endregion

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockOldLeaf.VARIANT_PROP, this.getWoodType(i))
                .withProperty(net.minecraft.block.BlockLeaves.DECAYABLE, (i & 4) == 0)
                .withProperty(net.minecraft.block.BlockLeaves.CHECK_DECAY, (i & 8) > 0);
    }

    protected int getSaplingDropChance(BlockState blockState) {
        return blockState.getValue(net.minecraft.block.BlockOldLeaf.VARIANT_PROP)
                        == BlockPlanksEnumType.JUNGLE
                ? 40
                : super.getSaplingDropChance(blockState);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.block.BlockOldLeaf.VARIANT_PROP =
                PropertyEnum.create(
                        "variant", BlockPlanksEnumType.class, new BlockPlanksEnumTypePredicate());
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
                            Item.getItemFromBlock(this),
                            1,
                            ((BlockPlanksEnumType)
                                            blockState.getValue(
                                                    net.minecraft.block.BlockOldLeaf.VARIANT_PROP))
                                    .getMetadata()));
            if (i == 0) {
                return;
            }
        }

        super.harvestBlock(world, playerEntity, blockPos, blockState, tileEntity);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this,
                new IProperty[] {
                    net.minecraft.block.BlockOldLeaf.VARIANT_PROP,
                    net.minecraft.block.BlockLeaves.CHECK_DECAY,
                    net.minecraft.block.BlockLeaves.DECAYABLE
                });
    }

    public BlockOldLeaf() {
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockOldLeaf.VARIANT_PROP,
                                BlockPlanksEnumType.OAK)
                        .withProperty(net.minecraft.block.BlockLeaves.CHECK_DECAY, true)
                        .withProperty(net.minecraft.block.BlockLeaves.DECAYABLE, true));
    }

    public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
        BlockState blockState = iBlockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            BlockPlanksEnumType value =
                    (BlockPlanksEnumType)
                            blockState.getValue(net.minecraft.block.BlockOldLeaf.VARIANT_PROP);
            if (value == BlockPlanksEnumType.SPRUCE) {
                return ColorizerFoliage.getFoliageColorPine();
            }

            if (value == BlockPlanksEnumType.BIRCH) {
                return ColorizerFoliage.getFoliageColorBirch();
            }
        }

        return super.colorMultiplier(iBlockAccess, blockPos, i);
    }

    public BlockPlanksEnumType getWoodType(int i) {
        return BlockPlanksEnumType.byMetadata((i & 3) % 4);
    }

    protected void dropApple(World world, BlockPos blockPos, BlockState blockState, int i) {
        if (blockState.getValue(net.minecraft.block.BlockOldLeaf.VARIANT_PROP)
                        == BlockPlanksEnumType.OAK
                && world.rand.nextInt(i) == 0) {
            spawnAsEntity(world, blockPos, new ItemStack(Items.apple, 1, 0));
        }
    }

    public int getRenderColor(BlockState blockState) {
        if (blockState.getBlock() != this) {
            return super.getRenderColor(blockState);
        }

        BlockPlanksEnumType value =
                (BlockPlanksEnumType)
                        blockState.getValue(net.minecraft.block.BlockOldLeaf.VARIANT_PROP);
        return value == BlockPlanksEnumType.SPRUCE
                ? ColorizerFoliage.getFoliageColorPine()
                : (value == BlockPlanksEnumType.BIRCH
                        ? ColorizerFoliage.getFoliageColorBirch()
                        : super.getRenderColor(blockState));
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, BlockPlanksEnumType.OAK.getMetadata()));
        list.add(new ItemStack(item, 1, BlockPlanksEnumType.SPRUCE.getMetadata()));
        list.add(new ItemStack(item, 1, BlockPlanksEnumType.BIRCH.getMetadata()));
        list.add(new ItemStack(item, 1, BlockPlanksEnumType.JUNGLE.getMetadata()));
    }

    // Helper used only by this class.
    public static final class BlockPlanksEnumTypePredicate
            implements com.google.common.base.Predicate {
        public boolean method_6242e4af980f87ad42df(
                net.minecraft.block.BlockPlanksEnumType blockPlanksEnumType) {
            return blockPlanksEnumType.getMetadata() < 4;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_6242e4af980f87ad42df(
                    (net.minecraft.block.BlockPlanksEnumType) object);
        }
    }
}
