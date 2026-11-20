package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.world.World;

public class BlockHay extends BlockRotatedPillar {
    protected ItemStack createStackedBlock(BlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), 1, 0);
    }

    public BlockHay() {
        super(Material.grass);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockRotatedPillar.AXIS, FacingAxis.Y));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public BlockState getStateFromMeta(int i) {
        FacingAxis facingAxis = FacingAxis.Y;
        int l = BlockPlanksEnumType.getInvertedBlockFlowToken();
        int k = i & 12;
        int j = l;
        if (k == 4) {
            facingAxis = FacingAxis.X;
            if (j == 0) {
                return this.getDefaultState()
                        .withProperty(net.minecraft.block.BlockRotatedPillar.AXIS, facingAxis);
            }
        }

        if (k == 8) {
            facingAxis = FacingAxis.Z;
        }

        return this.getDefaultState().withProperty(net.minecraft.block.BlockRotatedPillar.AXIS, facingAxis);
    }

    public int getMetaFromState(BlockState blockState) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        byte b0 = 0;
        int i = j;
        FacingAxis facingAxis = (FacingAxis) blockState.getValue(net.minecraft.block.BlockRotatedPillar.AXIS);
        if (facingAxis == FacingAxis.X) {
            b0 |= 4;
            if (i == 0) {
                return b0;
            }
        }

        if (facingAxis == FacingAxis.Z) {
            b0 |= 8;
        }

        return b0;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockRotatedPillar.AXIS});
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
        return super.onBlockPlaced(world, blockPos, facing, f, f1, f2, i, livingEntity)
                .withProperty(net.minecraft.block.BlockRotatedPillar.AXIS, facing.getAxis());
    }
}
