package net.minecraft.entity.passive;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

class EntityVillagerItemAndEmeraldToItem implements EntityVillagerITradeList {
    public ItemStack uK;
    public ItemStack baL;
    public EntityVillagerPriceInfo bkT;
    public EntityVillagerPriceInfo bkS;

    public EntityVillagerItemAndEmeraldToItem(
            Item item,
            EntityVillagerPriceInfo bkSValue,
            Item itemValue,
            EntityVillagerPriceInfo bkTValue) {
        this.uK = new ItemStack(item);
        this.bkS = bkSValue;
        this.baL = new ItemStack(itemValue);
        this.bkT = bkTValue;
    }

    public void modifyMerchantRecipeList(MerchantRecipeList merchantRecipeList, Random random) {
        int i = 1;
        if (this.bkS != null) {
            i = this.bkS.getPrice(random);
        }

        int j = 1;
        if (this.bkT != null) {
            j = this.bkT.getPrice(random);
        }

        merchantRecipeList.add(
                new MerchantRecipe(
                        new ItemStack(this.uK.getItem(), i, this.uK.getItemDamage()),
                        new ItemStack(Items.emerald),
                        new ItemStack(this.baL.getItem(), j, this.baL.getItemDamage())));
    }
}
