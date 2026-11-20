package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlabEnumBlockHalf;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemSlab extends ItemBlock {
    private BlockSlab doubleSlab;
    private BlockSlab singleSlab;

    public int getMetadata(int i) {
        return i;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return this.singleSlab.getFullSlabName(itemStack.getItemDamage());
    }

    public boolean canPlaceBlockOnSide(
            World world,
            BlockPos blockPos,
            Facing facing,
            PlayerEntity playerEntity,
            ItemStack itemStack) {
        BlockPos blockPosValue = blockPos;
        IProperty iProperty = this.singleSlab.getVariantProperty();
        Object variant = this.singleSlab.getVariant(itemStack);
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == this.singleSlab) {
            int i = blockState.getValue(BlockSlab.HALF_PROP) == BlockSlabEnumBlockHalf.TOP ? 1 : 0;
            if ((facing == Facing.UP && i == 0 || facing == Facing.DOWN && i != 0)
                    && variant == blockState.getValue(iProperty)) {
                return true;
            }
        }

        blockPos = blockPos.offset(facing);
        BlockState blockStateValue = world.getBlockState(blockPos);
        return blockStateValue.getBlock() == this.singleSlab
                        && variant == blockStateValue.getValue(iProperty)
                ? true
                : super.canPlaceBlockOnSide(
                        world, blockPosValue, facing, playerEntity, itemStack);
    }

    public ItemSlab(Block block, BlockSlab btGValue, BlockSlab btFValue) {
        super(block);
        this.singleSlab = btGValue;
        this.doubleSlab = btFValue;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    private boolean tryPlace(
            ItemStack itemStack, World world, BlockPos blockPos, Object object) {
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == this.singleSlab) {
            Comparable comparable = blockState.getValue(this.singleSlab.getVariantProperty());
            if (comparable == object) {
                BlockState blockStateValue =
                        this.doubleSlab
                                .getDefaultState()
                                .withProperty(this.singleSlab.getVariantProperty(), comparable);
                if (world.checkNoEntityCollision(
                                this.doubleSlab.getCollisionBoundingBox(world, blockPos, blockStateValue))
                        && world.setBlockState(blockPos, blockStateValue, 3)) {
                    world.playSoundEffect(
                            blockPos.getX() + 0.5F,
                            blockPos.getY() + 0.5F,
                            blockPos.getZ() + 0.5F,
                            this.doubleSlab.stepSound.getPlaceSound(),
                            (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F,
                            this.doubleSlab.stepSound.getFrequency() * 0.8F);
                    itemStack.stackSize--;
                }

                return true;
            }
        }

        return false;
    }

    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        if (itemStack.stackSize == 0) {
            return false;
        }

        if (!playerEntity.canPlayerEdit(blockPos.offset(facing), facing, itemStack)) {
            return false;
        }

        Object variant = this.singleSlab.getVariant(itemStack);
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == this.singleSlab) {
            IProperty iProperty = this.singleSlab.getVariantProperty();
            Comparable comparable = blockState.getValue(iProperty);
            BlockSlabEnumBlockHalf value =
                    (BlockSlabEnumBlockHalf) blockState.getValue(BlockSlab.HALF_PROP);
            if ((facing == Facing.UP && value == BlockSlabEnumBlockHalf.BOTTOM
                            || facing == Facing.DOWN && value == BlockSlabEnumBlockHalf.TOP)
                    && comparable == variant) {
                BlockState blockStateValue =
                        this.doubleSlab.getDefaultState().withProperty(iProperty, comparable);
                if (world.checkNoEntityCollision(
                                this.doubleSlab.getCollisionBoundingBox(world, blockPos, blockStateValue))
                        && world.setBlockState(blockPos, blockStateValue, 3)) {
                    world.playSoundEffect(
                            blockPos.getX() + 0.5F,
                            blockPos.getY() + 0.5F,
                            blockPos.getZ() + 0.5F,
                            this.doubleSlab.stepSound.getPlaceSound(),
                            (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F,
                            this.doubleSlab.stepSound.getFrequency() * 0.8F);
                    itemStack.stackSize--;
                }

                return true;
            }
        }

        return this.tryPlace(itemStack, world, blockPos.offset(facing), variant)
                ? true
                : super.onItemUse(itemStack, playerEntity, world, blockPos, facing, f, f1, f2);
    }
}
