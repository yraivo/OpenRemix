package net.minecraft.item;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.world.World;

public class ItemGlassBottle extends Item {
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

            if (world.getBlockState(blockPos).getBlock().getMaterial() == Material.water) {
                itemStack.stackSize--;
                playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                if (itemStack.stackSize <= 0) {
                    return new ItemStack(Items.potionitem);
                }

                if (!playerEntity.inventory.addItemStackToInventory(
                        new ItemStack(Items.potionitem))) {
                    playerEntity.dropPlayerItemWithRandomChoice(
                            new ItemStack(Items.potionitem, 1, 0), false);
                }
            }
        }

        return itemStack;
    }

    public ItemGlassBottle() {
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }
}
