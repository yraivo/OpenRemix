package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IRecipe {
    int getRecipeSize();

    ItemStack[] getRemainingItems(InventoryCrafting inventoryCrafting);

    boolean matches(InventoryCrafting inventoryCrafting, World world);

    ItemStack getRecipeOutput();

    ItemStack getCraftingResult(InventoryCrafting inventoryCrafting);
}
