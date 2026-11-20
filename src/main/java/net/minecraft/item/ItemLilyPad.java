package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.world.World;

public class ItemLilyPad extends ItemColored {
    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        RayTraceResult rayTraceResult = this.getMovingObjectPositionFromPlayer(world, playerEntity, true);
        if (rayTraceResult == null) {
            return itemStack;
        }

        if (rayTraceResult.typeOfHit == RayHitType.BLOCK) {
            BlockPos blockPos = rayTraceResult.getBlockPos();
            if (!world.isBlockModifiable(playerEntity, blockPos)) {
                return itemStack;
            }

            if (!playerEntity.canPlayerEdit(
                    blockPos.offset(rayTraceResult.sideHit), rayTraceResult.sideHit, itemStack)) {
                return itemStack;
            }

            BlockPos blockPosValue = blockPos.offsetUp();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock().getMaterial() == Material.water
                    && (Integer) blockState.getValue(BlockLiquid.LEVEL) == 0
                    && world.isAirBlock(blockPosValue)) {
                world.setBlockState(blockPosValue, Blocks.waterlily.getDefaultState());
                if (!playerEntity.capabilities.isCreativeMode) {
                    itemStack.stackSize--;
                }

                playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            }
        }

        return itemStack;
    }

    public int getColorFromItemStack(ItemStack itemStack, int i) {
        return Blocks.waterlily.getRenderColor(
                Blocks.waterlily.getStateFromMeta(itemStack.getItemDamage()));
    }

    public ItemLilyPad(Block block) {
        super(block, false);
    }
}
