package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.village.MerchantRecipe;

public class SlotMerchantResult extends Slot {
    private IMerchant theMerchant;
    private PlayerEntity thePlayer;
    private int sR;
    private InventoryMerchant theMerchantInventory;

    public SlotMerchantResult(
            PlayerEntity btmValue,
            IMerchant btlValue,
            InventoryMerchant btnValue,
            int i,
            int j,
            int k) {
        super(btnValue, i, j, k);
        this.thePlayer = btmValue;
        this.theMerchant = btlValue;
        this.theMerchantInventory = btnValue;
    }

    protected void onCrafting(ItemStack itemStack) {
        itemStack.onCrafting(this.thePlayer.world, this.thePlayer, this.sR);
        this.sR = 0;
    }

    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }

    private boolean doTrade(
            MerchantRecipe merchantRecipe, ItemStack itemStack, ItemStack itemStackValue) {
        ItemStack itemStackValue2 = merchantRecipe.getItemToBuy();
        ItemStack itemStackValue3 = merchantRecipe.getSecondItemToBuy();
        if (itemStack != null && itemStack.getItem() == itemStackValue2.getItem()) {
            if (itemStackValue3 != null
                    && itemStackValue != null
                    && itemStackValue3.getItem() == itemStackValue.getItem()) {
                itemStack.stackSize = itemStack.stackSize - itemStackValue2.stackSize;
                itemStackValue.stackSize = itemStackValue.stackSize - itemStackValue3.stackSize;
                return true;
            }

            if (itemStackValue3 == null && itemStackValue == null) {
                itemStack.stackSize = itemStack.stackSize - itemStackValue2.stackSize;
                return true;
            }
        }

        return false;
    }

    public void onPickupFromSlot(PlayerEntity playerEntity, ItemStack itemStack) {
        this.onCrafting(itemStack);
        MerchantRecipe currentRecipe = this.theMerchantInventory.getCurrentRecipe();
        if (currentRecipe != null) {
            ItemStack itemStackValue = this.theMerchantInventory.getStackInSlot(0);
            ItemStack itemStackValue2 = this.theMerchantInventory.getStackInSlot(1);
            if (this.doTrade(currentRecipe, itemStackValue, itemStackValue2)
                    || this.doTrade(currentRecipe, itemStackValue2, itemStackValue)) {
                this.theMerchant.useRecipe(currentRecipe);
                playerEntity.triggerAchievement(StatList.timesTradedWithVillagerStat);
                if (itemStackValue != null && itemStackValue.stackSize <= 0) {
                    itemStackValue = null;
                }

                if (itemStackValue2 != null && itemStackValue2.stackSize <= 0) {
                    itemStackValue2 = null;
                }

                this.theMerchantInventory.setInventorySlotContents(0, itemStackValue);
                this.theMerchantInventory.setInventorySlotContents(1, itemStackValue2);
            }
        }
    }

    protected void onCrafting(ItemStack itemStack, int i) {
        this.sR += i;
        this.onCrafting(itemStack);
    }

    public ItemStack decrStackSize(int i) {
        if (this.getHasStack()) {
            this.sR = this.sR + Math.min(i, this.getStack().stackSize);
        }

        return super.decrStackSize(i);
    }
}
