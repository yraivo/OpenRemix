package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class ItemNameTag extends Item {
    public boolean itemInteractionForEntity(
            ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity) {
        if (!itemStack.hasDisplayName()) {
            return false;
        } else if (livingEntity instanceof EntityLiving) {
            EntityLiving entityLiving = (EntityLiving) livingEntity;
            entityLiving.setCustomNameTag(itemStack.getDisplayName());
            entityLiving.enablePersistence();
            itemStack.stackSize--;
            return true;
        } else {
            return super.itemInteractionForEntity(itemStack, playerEntity, livingEntity);
        }
    }

    public ItemNameTag() {
        this.setCreativeTab(CreativeTabs.tabTools);
    }
}
