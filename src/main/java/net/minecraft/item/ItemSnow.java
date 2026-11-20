package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemSnow extends ItemBlock {
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

        if (!playerEntity.canPlayerEdit(blockPos, facing, itemStack)) {
            return false;
        }

        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block != this.block && facing != Facing.UP) {
            blockPos = blockPos.offset(facing);
            blockState = world.getBlockState(blockPos);
            block = blockState.getBlock();
        }

        if (block == this.block) {
            int value = (Integer) blockState.getValue(BlockSnow.LAYERS_PROP);
            if (value <= 7) {
                BlockState blockStateValue =
                        blockState.withProperty(BlockSnow.LAYERS_PROP, value + 1);
                if (world.checkNoEntityCollision(
                                this.block.getCollisionBoundingBox(
                                        world, blockPos, blockStateValue))
                        && world.setBlockState(blockPos, blockStateValue, 2)) {
                    world.playSoundEffect(
                            blockPos.getX() + 0.5F,
                            blockPos.getY() + 0.5F,
                            blockPos.getZ() + 0.5F,
                            this.block.stepSound.getPlaceSound(),
                            (this.block.stepSound.getVolume() + 1.0F) / 2.0F,
                            this.block.stepSound.getFrequency() * 0.8F);
                    itemStack.stackSize--;
                    return true;
                }
            }
        }

        return super.onItemUse(itemStack, playerEntity, world, blockPos, facing, f, f1, f2);
    }

    public int getMetadata(int i) {
        return i;
    }

    public ItemSnow(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
}
