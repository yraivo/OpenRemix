package net.minecraft.entity.passive;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

class EntityVillagerListEnchantedItemForEmeralds implements EntityVillagerITradeList {
    public EntityVillagerPriceInfo bkS;
    public ItemStack uK;

    public void modifyMerchantRecipeList(MerchantRecipeList merchantRecipeList, Random random) {
        int i = 1;
        if (this.bkS != null) {
            i = this.bkS.getPrice(random);
        }

        ItemStack itemStack = new ItemStack(Items.emerald, i, 0);
        ItemStack itemStackValue = new ItemStack(this.uK.getItem(), 1, this.uK.getItemDamage());
        itemStackValue =
                EnchantmentHelper.addRandomEnchantment(
                        random, itemStackValue, 5 + random.nextInt(15));
        merchantRecipeList.add(new MerchantRecipe(itemStack, itemStackValue));
    }

    public EntityVillagerListEnchantedItemForEmeralds(Item item, EntityVillagerPriceInfo bkSValue) {
        this.uK = new ItemStack(item);
        this.bkS = bkSValue;
    }
}
