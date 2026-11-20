package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemCarrotOnAStick extends Item {
    public ItemCarrotOnAStick() {
        this.setCreativeTab(CreativeTabs.tabTransport);
        this.setMaxStackSize(1);
        this.setMaxDamage(25);
    }

    public boolean isFull3D() {
        return true;
    }

    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        if (playerEntity.isRiding() && playerEntity.ridingEntity instanceof EntityPig) {
            EntityPig ridingEntity = (EntityPig) playerEntity.ridingEntity;
            if (ridingEntity.getAIControlledByPlayer().isControlledByPlayer()
                    && itemStack.getMaxDamage() - itemStack.getItemDamage() >= 7) {
                ridingEntity.getAIControlledByPlayer().boostSpeed();
                itemStack.damageItem(7, playerEntity);
                if (itemStack.stackSize == 0) {
                    ItemStack itemStackValue = new ItemStack(Items.fishing_rod);
                    itemStackValue.setTag(itemStack.getTag());
                    return itemStackValue;
                }
            }
        }

        playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }
}
