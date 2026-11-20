package net.minecraft.village;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class MerchantRecipe {

    private ItemStack itemToBuy;
    private int maxTradeUses;
    private ItemStack itemToSell;
    private ItemStack secondItemToBuy;

    private boolean rewardsExp;

    private int toolUses;

    public MerchantRecipe(CompoundTag compoundTag) {
        this.readFromTags(compoundTag);
    }

    public MerchantRecipe(ItemStack itemStack, Item item) {
        this(itemStack, new ItemStack(item));
    }

    public int getToolUses() {
        return this.toolUses;
    }

    public void compensateToolUses() {
        this.toolUses = this.maxTradeUses;
    }

    public CompoundTag writeToTags() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.setTag("buy", this.itemToBuy.writeToNBT(new CompoundTag()));
        compoundTag.setTag("sell", this.itemToSell.writeToNBT(new CompoundTag()));
        if (this.secondItemToBuy != null) {
            compoundTag.setTag("buyB", this.secondItemToBuy.writeToNBT(new CompoundTag()));
        }

        compoundTag.setInteger("uses", this.toolUses);
        compoundTag.setInteger("maxUses", this.maxTradeUses);
        compoundTag.setBoolean("rewardExp", this.rewardsExp);
        return compoundTag;
    }

    public boolean getRewardsExp() {
        return this.rewardsExp;
    }

    public int getMaxTradeUses() {
        return this.maxTradeUses;
    }

    public void incrementToolUses() {
        this.toolUses++;
    }

    public boolean hasSecondItemToBuy() {
        return this.secondItemToBuy != null;
    }

    public ItemStack getItemToBuy() {
        return this.itemToBuy;
    }

    public boolean isRecipeDisabled() {
        return this.toolUses >= this.maxTradeUses;
    }

    public void increaseMaxTradeUses(int i) {
        this.maxTradeUses += i;
    }


    public MerchantRecipe(ItemStack itemStack, ItemStack itemStackValue) {
        this(itemStack, (ItemStack) null, itemStackValue);
    }

    public ItemStack getItemToSell() {
        return this.itemToSell;
    }

    public ItemStack getSecondItemToBuy() {
        return this.secondItemToBuy;
    }

    public MerchantRecipe(
            ItemStack itemStack, ItemStack bxdValue, ItemStack baLValue, int toolUsesValue, int j) {
        this.itemToBuy = itemStack;
        this.secondItemToBuy = bxdValue;
        this.itemToSell = baLValue;
        this.toolUses = toolUsesValue;
        this.maxTradeUses = j;
        this.rewardsExp = true;
    }

    public MerchantRecipe(
            ItemStack itemStack, ItemStack itemStackValue, ItemStack itemStackValue2) {
        this(itemStack, itemStackValue, itemStackValue2, 0, 7);
    }

    public void readFromTags(CompoundTag compoundTag) {
        CompoundTag compoundTagValue = compoundTag.getCompoundTag("buy");
        this.itemToBuy = ItemStack.loadItemStackFromNBT(compoundTagValue);
        boolean flag1 = Village.dI();
        CompoundTag compoundTagValue2 = compoundTag.getCompoundTag("sell");
        boolean flag = flag1;
        this.itemToSell = ItemStack.loadItemStackFromNBT(compoundTagValue2);
        if (compoundTag.hasKey("buyB", 10)) {
            this.secondItemToBuy =
                    ItemStack.loadItemStackFromNBT(compoundTag.getCompoundTag("buyB"));
        }

        if (compoundTag.hasKey("uses", 99)) {
            this.toolUses = compoundTag.getInteger("uses");
        }

        {
            if (compoundTag.hasKey("maxUses", 99)) {
                this.maxTradeUses = compoundTag.getInteger("maxUses");
                if (!(flag)) {

                    this.maxTradeUses = 7;
                }
            } else {

                this.maxTradeUses = 7;
            }
        }

        if (compoundTag.hasKey("rewardExp", 1)) {
            this.rewardsExp = compoundTag.getBoolean("rewardExp");
            if (flag) {
                return;
            }
        }

        this.rewardsExp = true;
    }
}
