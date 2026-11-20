package net.minecraft.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemToolMaterial;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.stats.AchievementList;

public class SlotCrafting extends Slot {
    private int amountCrafted;
    private InventoryCrafting craftMatrix;
    private PlayerEntity thePlayer;

    protected void onCrafting(ItemStack itemStack, int i) {
        this.amountCrafted += i;
        this.onCrafting(itemStack);
    }

    public ItemStack decrStackSize(int i) {
        if (this.getHasStack()) {
            this.amountCrafted = this.amountCrafted + Math.min(i, this.getStack().stackSize);
        }

        return super.decrStackSize(i);
    }

    public void onPickupFromSlot(PlayerEntity playerEntity, ItemStack itemStack) {
        int k = Container.aW();
        this.onCrafting(itemStack);
        int i = k;
        ItemStack[] itemStackArray =
                CraftingManager.getInstance()
                        .method_0c7013863aed82d876b8(this.craftMatrix, playerEntity.world);
        int j = 0;

        while (j < itemStackArray.length) {
            ItemStack itemStackValue = this.craftMatrix.getStackInSlot(j);
            ItemStack itemStackValue2 = itemStackArray[j];
            if (itemStackValue != null) {
                this.craftMatrix.decrStackSize(j, 1);
            }

            if (itemStackValue2 != null) {
                if (this.craftMatrix.getStackInSlot(j) == null) {
                    this.craftMatrix.setInventorySlotContents(j, itemStackValue2);
                    if (!(i != 0)) {

                        if (!this.thePlayer.inventory.addItemStackToInventory(itemStackValue2)) {
                            this.thePlayer.dropPlayerItemWithRandomChoice(itemStackValue2, false);
                        }
                    }
                } else {

                    if (!this.thePlayer.inventory.addItemStackToInventory(itemStackValue2)) {
                        this.thePlayer.dropPlayerItemWithRandomChoice(itemStackValue2, false);
                    }
                }
            }

            j++;
            if (i == 0) {
                break;
            }
        }
    }

    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }

    public SlotCrafting(
            PlayerEntity btmValue,
            InventoryCrafting bujValue,
            IInventory iInventory,
            int i,
            int j,
            int k) {
        super(iInventory, i, j, k);
        this.thePlayer = btmValue;
        this.craftMatrix = bujValue;
    }

    protected void onCrafting(ItemStack itemStack) {
        if (this.amountCrafted > 0) {
            itemStack.onCrafting(this.thePlayer.world, this.thePlayer, this.amountCrafted);
        }

        this.amountCrafted = 0;
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.crafting_table)) {
            this.thePlayer.triggerAchievement(AchievementList.buildWorkBench);
        }

        if (itemStack.getItem() instanceof ItemPickaxe) {
            this.thePlayer.triggerAchievement(AchievementList.buildPickaxe);
        }

        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.furnace)) {
            this.thePlayer.triggerAchievement(AchievementList.buildFurnace);
        }

        if (itemStack.getItem() instanceof ItemHoe) {
            this.thePlayer.triggerAchievement(AchievementList.buildHoe);
        }

        if (itemStack.getItem() == Items.bread) {
            this.thePlayer.triggerAchievement(AchievementList.makeBread);
        }

        if (itemStack.getItem() == Items.cake) {
            this.thePlayer.triggerAchievement(AchievementList.bakeCake);
        }

        if (itemStack.getItem() instanceof ItemPickaxe
                && ((ItemPickaxe) itemStack.getItem()).getToolMaterial() != ItemToolMaterial.WOOD) {
            this.thePlayer.triggerAchievement(AchievementList.buildBetterPickaxe);
        }

        if (itemStack.getItem() instanceof SwordItem) {
            this.thePlayer.triggerAchievement(AchievementList.buildSword);
        }

        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table)) {
            this.thePlayer.triggerAchievement(AchievementList.enchantments);
        }

        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.bookshelf)) {
            this.thePlayer.triggerAchievement(AchievementList.bookcase);
        }

        if (itemStack.getItem() == Items.golden_apple && itemStack.getItemDamage() == 1) {
            this.thePlayer.triggerAchievement(AchievementList.overpowered);
        }
    }
}
