package net.minecraft.entity.passive;

import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

interface EntityVillagerITradeList {
    void modifyMerchantRecipeList(MerchantRecipeList merchantRecipeList, Random random);
}
