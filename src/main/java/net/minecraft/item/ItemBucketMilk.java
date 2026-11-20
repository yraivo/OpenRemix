package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemBucketMilk extends Item {
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        if (!playerEntity.capabilities.isCreativeMode) {
            itemStack.stackSize--;
        }

        if (!world.isRemote) {
            playerEntity.nD();
        }

        playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack.stackSize <= 0 ? new ItemStack(Items.bucket) : itemStack;
    }

    public int getMaxItemUseDuration(ItemStack itemStack) {
        return 32;
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        playerEntity.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        return itemStack;
    }

    public ItemBucketMilk() {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    public EnumAction getItemUseAction(ItemStack itemStack) {
        return EnumAction.DRINK;
    }
}
