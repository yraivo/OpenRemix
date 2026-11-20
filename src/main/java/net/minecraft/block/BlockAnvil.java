package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockAnvil extends BlockFalling {
    public static PropertyDirection FACING;

    public static PropertyInteger DAMAGE;

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float f,
            float f1,
            float f2) {
        if (!world.isRemote) {
            playerEntity.displayGui(new BlockAnvilAnvil(world, blockPos));
        }

        return true;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockAnvil.FACING, Facing.getHorizontal(i & 3))
                .withProperty(net.minecraft.block.BlockAnvil.DAMAGE, (i & 15) >> 2);
    }

    // region String decoding support

    // endregion

    public void onEndFalling(World world, BlockPos blockPos) {
        world.playAuxSFX(1022, blockPos, 0);
    }

    public boolean isFullCube() {
        return false;
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
        Facing facingValue = livingEntity.getHorizontalFacing().rotateY();
        return super.onBlockPlaced(world, blockPos, facing, f, f1, f2, i, livingEntity)
                .withProperty(net.minecraft.block.BlockAnvil.FACING, facingValue)
                .withProperty(net.minecraft.block.BlockAnvil.DAMAGE, i >> 2);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        Facing facing =
                (Facing) iBlockAccess.getBlockState(blockPos).getValue(net.minecraft.block.BlockAnvil.FACING);
        int i = j;
        if (facing.getAxis() == FacingAxis.X) {
            this.setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
            if (i != 0) {
                return;
            }
        }

        this.setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
    }

    protected void onStartFalling(EntityFallingBlock entityFallingBlock) {
        entityFallingBlock.setHurtEntities(true);
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 2));
    }

    // region String decoding support

    // endregion

    public BlockState getStateForEntityRender(BlockState blockState) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockAnvil.FACING, Facing.SOUTH);
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        int i = b0 | ((Facing) blockState.getValue(net.minecraft.block.BlockAnvil.FACING)).getHorizontalIndex();
        return i | (Integer) blockState.getValue(net.minecraft.block.BlockAnvil.DAMAGE) << 2;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this, new IProperty[] {net.minecraft.block.BlockAnvil.FACING, net.minecraft.block.BlockAnvil.DAMAGE});
    }

    public int damageDropped(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockAnvil.DAMAGE);
    }

    protected BlockAnvil() {
        super(Material.anvil);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockAnvil.FACING, Facing.NORTH)
                        .withProperty(net.minecraft.block.BlockAnvil.DAMAGE, 0));
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return true;
    }

    static {
        net.minecraft.block.BlockAnvil.FACING = PropertyDirection.create("facing", FacingPlane.HORIZONTAL);
        net.minecraft.block.BlockAnvil.DAMAGE = PropertyInteger.create("damage", 0, 2);
    }
}
