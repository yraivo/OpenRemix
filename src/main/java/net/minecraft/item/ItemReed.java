package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemReed extends Item {
    private Block block;

    public ItemReed(Block blWValue) {
        this.block = blWValue;
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
        {
            String text = ItemStack.getLegacyItemStackFlowMarker();
            BlockState blockState = world.getBlockState(blockPos);
            String textValue = text;
            Block block = blockState.getBlock();
            if (block == Blocks.snow_layer
                    && (Integer) blockState.getValue(BlockSnow.LAYERS_PROP) < 1) {
                facing = Facing.UP;
                if (!(textValue == null)) {

                    if (!block.isReplaceable(world, blockPos)) {
                        blockPos = blockPos.offset(facing);
                    }
                }
            } else {

                if (!block.isReplaceable(world, blockPos)) {
                    blockPos = blockPos.offset(facing);
                }
            }
        }

        if (!playerEntity.canPlayerEdit(blockPos, facing, itemStack)) {
            return false;
        }

        if (itemStack.stackSize == 0) {
            return false;
        }

        if (world.canBlockBePlaced(
                this.block, blockPos, false, facing, (Entity) null, itemStack)) {
            BlockState blockStateValue =
                    this.block.onBlockPlaced(world, blockPos, facing, f, f1, f2, 0, playerEntity);
            if (world.setBlockState(blockPos, blockStateValue, 3)) {
                blockStateValue = world.getBlockState(blockPos);
                if (blockStateValue.getBlock() == this.block) {
                    ItemBlock.setTileEntityNBT(world, blockPos, itemStack);
                    blockStateValue
                            .getBlock()
                            .onBlockPlacedBy(
                                    world, blockPos, blockStateValue, playerEntity, itemStack);
                }

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

        return false;
    }
}
