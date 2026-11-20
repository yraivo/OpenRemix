package net.minecraft.entity.passive;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

class EntityVillagerListEnchantedBookForEmeralds implements EntityVillagerITradeList {
    public void modifyMerchantRecipeList(MerchantRecipeList merchantRecipeList, Random random) {
        Enchantment enchantment =
                Enchantment.enchantmentsBookList[
                        random.nextInt(Enchantment.enchantmentsBookList.length)];
        int i =
                MathHelper.nextIntInclusive(
                        random, enchantment.getMinLevel(), enchantment.getMaxLevel());
        ItemStack itemStack =
                Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i));
        int j = 2 + random.nextInt(5 + i * 10) + 3 * i;
        if (j > 64) {
            j = 64;
        }

        merchantRecipeList.add(
                new MerchantRecipe(
                        new ItemStack(Items.book), new ItemStack(Items.emerald, j), itemStack));
    }
}
