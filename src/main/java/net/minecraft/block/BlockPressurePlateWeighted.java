package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate {

    public static PropertyInteger POWER;
    private int field_150068_a;

    // region String decoding support

    // endregion

    protected BlockPressurePlateWeighted(String text, Material material, int i) {
        super(material);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockPressurePlateWeighted.POWER, 0));
        this.field_150068_a = i;
    }

    protected int computeRedstoneStrength(World world, BlockPos blockPos) {
        int i =
                Math.min(
                        world.getEntitiesWithinAABB(Entity.class, this.getSensitiveAABB(blockPos))
                                .size(),
                        this.field_150068_a);
        if (i > 0) {
            float f = (float) Math.min(this.field_150068_a, i) / this.field_150068_a;
            return MathHelper.ceil(f * 15.0F);
        } else {
            return 0;
        }
    }

    protected int getRedstoneStrength(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockPressurePlateWeighted.POWER);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this, new IProperty[] {net.minecraft.block.BlockPressurePlateWeighted.POWER});
    }

    public int tickRate(World world) {
        return 10;
    }

    protected BlockState setRedstoneStrength(BlockState blockState, int i) {
        return blockState.withProperty(net.minecraft.block.BlockPressurePlateWeighted.POWER, i);
    }

    static {
        net.minecraft.block.BlockPressurePlateWeighted.POWER =
                PropertyInteger.create("power", 0, 15);
    }

    // region String decoding support

    // endregion

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockPressurePlateWeighted.POWER);
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockPressurePlateWeighted.POWER, i);
    }
}
