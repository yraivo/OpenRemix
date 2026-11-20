package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class InventoryMerchant implements IInventory {
    private static String f;
    private IMerchant theMerchant;
    private ItemStack[] theInventory = new ItemStack[3];
    private int currentRecipeIndex;
    private MerchantRecipe currentRecipe;
    private PlayerEntity thePlayer;

    public int getSizeInventory() {
        return this.theInventory.length;
    }

    public void setInventorySlotContents(int i, ItemStack itemStack) {
        this.theInventory[i] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        if (this.inventoryResetNeededOnSlotChange(i)) {
            this.resetRecipeAndSlots();
        }
    }

    public void markDirty() {
        this.resetRecipeAndSlots();
    }

    public void resetRecipeAndSlots() {
        int j = Container.aW();
        this.currentRecipe = null;
        ItemStack itemStack = this.theInventory[0];
        int i = j;
        ItemStack itemStackValue = this.theInventory[1];
        if (itemStack == null) {
            itemStack = itemStackValue;
            itemStackValue = null;
        }

        label37:
        {
            if (itemStack == null) {
                this.setInventorySlotContents(2, (ItemStack) null);
                if (i != 0) {
                    break label37;
                }
            }

            MerchantRecipeList recipes = this.theMerchant.getRecipes(this.thePlayer);
            label33:
            if (recipes != null) {
                MerchantRecipe btXValue =
                        recipes.canRecipeBeUsed(
                                itemStack, itemStackValue, this.currentRecipeIndex);
                if (btXValue != null && !btXValue.isRecipeDisabled()) {
                    this.currentRecipe = btXValue;
                    this.setInventorySlotContents(2, btXValue.getItemToSell().copy());
                    if (i != 0) {
                        break label33;
                    }
                }

                if (itemStackValue != null) {
                    btXValue =
                            recipes.canRecipeBeUsed(
                                    itemStackValue, itemStack, this.currentRecipeIndex);
                    if (btXValue != null && !btXValue.isRecipeDisabled()) {
                        this.currentRecipe = btXValue;
                        this.setInventorySlotContents(2, btXValue.getItemToSell().copy());
                        if (i != 0) {
                            break label33;
                        }
                    }

                    this.setInventorySlotContents(2, (ItemStack) null);
                    if (i != 0) {
                        break label33;
                    }
                }

                this.setInventorySlotContents(2, (ItemStack) null);
            }
        }

        this.theMerchant.verifySellingItem(this.getStackInSlot(2));
    }

    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

    public boolean hasCustomName() {
        return false;
    }

    static {
        f = "mob.villager";
    }

    public int getFieldCount() {
        return 0;
    }

    public ItemStack getStackInSlotOnClosing(int i) {
        if (this.theInventory[i] != null) {
            ItemStack itemStack = this.theInventory[i];
            this.theInventory[i] = null;
            return itemStack;
        } else {
            return null;
        }
    }

    public ItemStack decrStackSize(int i, int j) {
        if (this.theInventory[i] != null) {
            if (i == 2) {
                ItemStack itemStack = this.theInventory[i];
                this.theInventory[i] = null;
                return itemStack;
            }

            if (this.theInventory[i].stackSize <= j) {
                ItemStack itemStackValue = this.theInventory[i];
                this.theInventory[i] = null;
                if (this.inventoryResetNeededOnSlotChange(i)) {
                    this.resetRecipeAndSlots();
                }

                return itemStackValue;
            } else {
                ItemStack itemStackValue2 = this.theInventory[i].splitStack(j);
                if (this.theInventory[i].stackSize == 0) {
                    this.theInventory[i] = null;
                }

                if (this.inventoryResetNeededOnSlotChange(i)) {
                    this.resetRecipeAndSlots();
                }

                return itemStackValue2;
            }
        } else {
            return null;
        }
    }

    public ItemStack getStackInSlot(int i) {
        return this.theInventory[i];
    }

    // region String decoding support

    // endregion

    public String getName() {
        return f;
    }

    public boolean isUseableByPlayer(PlayerEntity playerEntity) {
        return this.theMerchant.getCustomer() == playerEntity;
    }

    private boolean inventoryResetNeededOnSlotChange(int i) {
        return i == 0 || i == 1;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public void closeInventory(PlayerEntity playerEntity) {}

    // region String decoding support

    // endregion

    public MerchantRecipe getCurrentRecipe() {
        return this.currentRecipe;
    }

    public void openInventory(PlayerEntity playerEntity) {}

    public ChatComponent getDisplayName() {
        return (ChatComponent)
                (this.hasCustomName()
                        ? new ChatComponentText(this.getName())
                        : new ChatComponentTranslation(this.getName(), new Object[0]));
    }

    public int getField(int i) {
        return 0;
    }

    public void setField(int i, int j) {}

    public void clearInventory() {
        int k = Container.aW();
        int j = 0;
        int i = k;

        while (j < this.theInventory.length) {
            this.theInventory[j] = null;
            j++;
            if (i == 0) {
                break;
            }
        }
    }

    public InventoryMerchant(PlayerEntity btYValue, IMerchant btWValue) {
        this.thePlayer = btYValue;
        this.theMerchant = btWValue;
    }

    public void setCurrentRecipeIndex(int currentRecipeIndexValue) {
        this.currentRecipeIndex = currentRecipeIndexValue;
        this.resetRecipeAndSlots();
    }
}
