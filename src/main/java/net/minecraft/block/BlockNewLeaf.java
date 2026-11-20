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
import net.minecraft.world.World;

import java.util.List;

public class BlockNewLeaf extends BlockLeaves {
    public static PropertyEnum VARIANT;

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
                                                            net.minecraft.block.BlockNewLeaf
                                                                    .VARIANT))
                                            .getMetadata()
                                    - 4));
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
                    net.minecraft.block.BlockNewLeaf.VARIANT,
                    net.minecraft.block.BlockLeaves.CHECK_DECAY,
                    net.minecraft.block.BlockLeaves.DECAYABLE
                });
    }

    public BlockPlanksEnumType getWoodType(int i) {
        return BlockPlanksEnumType.byMetadata((i & 3) + 4);
    }

    protected void dropApple(World world, BlockPos blockPos, BlockState blockState, int i) {
        if (blockState.getValue(net.minecraft.block.BlockNewLeaf.VARIANT)
                        == BlockPlanksEnumType.DARK_OAK
                && world.rand.nextInt(i) == 0) {
            spawnAsEntity(world, blockPos, new ItemStack(Items.apple, 1, 0));
        }
    }

    // region String decoding support

    // endregion

    public int getDamageValue(World world, BlockPos blockPos) {
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock().getMetaFromState(blockState) & 3;
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i =
                b0
                        | ((BlockPlanksEnumType)
                                                blockState.getValue(
                                                        net.minecraft.block.BlockNewLeaf.VARIANT))
                                        .getMetadata()
                                - 4;
        if (!(Boolean) blockState.getValue(net.minecraft.block.BlockLeaves.DECAYABLE)) {
            i |= 4;
        }

        if ((Boolean) blockState.getValue(net.minecraft.block.BlockLeaves.CHECK_DECAY)) {
            i |= 8;
        }

        return i;
    }

    // region String decoding support

    // endregion

    public BlockNewLeaf() {
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockNewLeaf.VARIANT,
                                BlockPlanksEnumType.ACACIA)
                        .withProperty(net.minecraft.block.BlockLeaves.CHECK_DECAY, true)
                        .withProperty(net.minecraft.block.BlockLeaves.DECAYABLE, true));
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockNewLeaf.VARIANT, this.getWoodType(i))
                .withProperty(net.minecraft.block.BlockLeaves.DECAYABLE, (i & 4) == 0)
                .withProperty(net.minecraft.block.BlockLeaves.CHECK_DECAY, (i & 8) > 0);
    }

    public int damageDropped(BlockState blockState) {
        return ((BlockPlanksEnumType) blockState.getValue(net.minecraft.block.BlockNewLeaf.VARIANT))
                .getMetadata();
    }

    protected ItemStack createStackedBlock(BlockState blockState) {
        return new ItemStack(
                Item.getItemFromBlock(this),
                1,
                ((BlockPlanksEnumType)
                                        blockState.getValue(
                                                net.minecraft.block.BlockNewLeaf.VARIANT))
                                .getMetadata()
                        - 4);
    }

    static {
        net.minecraft.block.BlockNewLeaf.VARIANT =
                PropertyEnum.create(
                        "variant", BlockPlanksEnumType.class, new BlockPlanksEnumTypePredicate());
    }

    // Helper used only by this class.
    public static final class BlockPlanksEnumTypePredicate
            implements com.google.common.base.Predicate {
        public boolean apply(java.lang.Object object) {
            return this.method_6242e4af980f87ad42df(
                    (net.minecraft.block.BlockPlanksEnumType) object);
        }

        public boolean method_6242e4af980f87ad42df(
                net.minecraft.block.BlockPlanksEnumType blockPlanksEnumType) {
            return blockPlanksEnumType.getMetadata() >= 4;
        }
    }
}
