package net.minecraft.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public interface IMerchant {
    void setCustomer(PlayerEntity playerEntity);

    void useRecipe(MerchantRecipe merchantRecipe);

    void setRecipes(MerchantRecipeList merchantRecipeList);

    PlayerEntity getCustomer();

    MerchantRecipeList getRecipes(PlayerEntity playerEntity);

    ChatComponent getDisplayName();

    void verifySellingItem(ItemStack itemStack);
}
