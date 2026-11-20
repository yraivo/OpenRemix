package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class BlockHugeMushroom extends Block {
    public static PropertyEnum VARIANT;
    private Block smallBlock;

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2,
            int i,
            LivingEntity livingEntity) {
        return this.getDefaultState();
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Item.getItemFromBlock(this.smallBlock);
    }

    // region String decoding support

    // endregion

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockHugeMushroom.VARIANT});
    }

    static {
        net.minecraft.block.BlockHugeMushroom.VARIANT =
                PropertyEnum.create("variant", BlockHugeMushroomEnumType.class);
    }

    public int quantityDropped(Random random) {
        return Math.max(0, random.nextInt(10) - 7);
    }

    // region String decoding support

    // endregion

    public BlockHugeMushroom(Material material, Block akkValue) {
        super(material);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(
                                net.minecraft.block.BlockHugeMushroom.VARIANT,
                                BlockHugeMushroomEnumType.ALL_OUTSIDE));
        this.smallBlock = akkValue;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(
                        net.minecraft.block.BlockHugeMushroom.VARIANT, BlockHugeMushroomEnumType.byMetadata(i));
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Item.getItemFromBlock(this.smallBlock);
    }

    public int getMetaFromState(BlockState blockState) {
        return ((BlockHugeMushroomEnumType) blockState.getValue(net.minecraft.block.BlockHugeMushroom.VARIANT))
                .getMetadata();
    }
}
