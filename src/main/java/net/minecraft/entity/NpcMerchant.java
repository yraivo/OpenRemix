package net.minecraft.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class NpcMerchant implements IMerchant {
    private PlayerEntity customer;
    private InventoryMerchant theMerchantInventory;
    private static String e;
    private MerchantRecipeList recipeList;

    private ChatComponent agL;

    public ChatComponent getDisplayName() {
        return (ChatComponent)
                (this.agL != null ? this.agL : new ChatComponentTranslation(e, new Object[0]));
    }

    public NpcMerchant(PlayerEntity bhDValue, ChatComponent agLValue) {
        this.customer = bhDValue;
        this.agL = agLValue;
        this.theMerchantInventory = new InventoryMerchant(bhDValue, this);
    }

    public void useRecipe(MerchantRecipe merchantRecipe) {
        merchantRecipe.incrementToolUses();
    }

    public MerchantRecipeList getRecipes(PlayerEntity playerEntity) {
        return this.recipeList;
    }

    static {
        e = "entity.Villager.name";
    }

    public void setCustomer(PlayerEntity playerEntity) {}

    // region String decoding support

    // endregion

    public void setRecipes(MerchantRecipeList bhFValue) {
        this.recipeList = bhFValue;
    }

    // region String decoding support

    // endregion

    public PlayerEntity getCustomer() {
        return this.customer;
    }

    public void verifySellingItem(ItemStack itemStack) {}
}
