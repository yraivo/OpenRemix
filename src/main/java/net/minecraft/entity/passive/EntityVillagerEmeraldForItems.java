package net.minecraft.entity.passive;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

class EntityVillagerEmeraldForItems implements EntityVillagerITradeList {
    public EntityVillagerPriceInfo price;
    public Item sellItem;

    public EntityVillagerEmeraldForItems(Item asoValue, EntityVillagerPriceInfo bkSValue) {
        this.sellItem = asoValue;
        this.price = bkSValue;
    }

    public void modifyMerchantRecipeList(MerchantRecipeList merchantRecipeList, Random random) {
        int i = 1;
        if (this.price != null) {
            i = this.price.getPrice(random);
        }

        merchantRecipeList.add(new MerchantRecipe(new ItemStack(this.sellItem, i, 0), Items.emerald));
    }
}
