package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

class ContainerBrewingStandPotion extends Slot {
    private PlayerEntity player;

    public static boolean canHoldPotion(ItemStack itemStack) {
        return itemStack != null
                && (itemStack.getItem() == Items.potionitem
                        || itemStack.getItem() == Items.glass_bottle);
    }

    public boolean isItemValid(ItemStack itemStack) {
        return canHoldPotion(itemStack);
    }

    public ContainerBrewingStandPotion(
            PlayerEntity btkValue, IInventory iInventory, int p171_2, int p171_3, int p171_4) {
        super(iInventory, p171_2, p171_3, p171_4);
        this.player = btkValue;
    }

    public int getSlotStackLimit() {
        return 1;
    }

    public void onPickupFromSlot(PlayerEntity playerEntity, ItemStack itemStack) {
        if (itemStack.getItem() == Items.potionitem && itemStack.getItemDamage() > 0) {
            this.player.triggerAchievement(AchievementList.potion);
        }

        super.onPickupFromSlot(playerEntity, itemStack);
    }
}
