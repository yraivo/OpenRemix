package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RecipesMapCloning implements IRecipe {
    public ItemStack[] getRemainingItems(InventoryCrafting inventoryCrafting) {
        ItemStack[] itemStackArray = new ItemStack[inventoryCrafting.getSizeInventory()];
        boolean flag1 = ShapedRecipes.method_d87039b07bd6961907f2();
        int i = 0;
        boolean flag = flag1;

        while (i < itemStackArray.length) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if (itemStack != null && itemStack.getItem().hasContainerItem()) {
                itemStackArray[i] = new ItemStack(itemStack.getItem().getContainerItem());
            }

            i++;
            if (flag) {
                break;
            }
        }

        return itemStackArray;
    }

    public ItemStack getRecipeOutput() {
        return null;
    }

    public int getRecipeSize() {
        return 9;
    }

    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        int i = 0;
        boolean flag = flag1;
        ItemStack itemStack = null;
        int j = 0;

        while (j < inventoryCrafting.getSizeInventory()) {
            ItemStack itemStackValue = inventoryCrafting.getStackInSlot(j);
            if (itemStackValue != null) {
                if (itemStackValue.getItem() == Items.filled_map) {
                    if (itemStack != null) {
                        return null;
                    }

                    itemStack = itemStackValue;
                    if (!(flag)) {

                        if (itemStackValue.getItem() != Items.map) {
                            return null;
                        }

                        i++;
                    }
                } else {

                    if (itemStackValue.getItem() != Items.map) {
                        return null;
                    }

                    i++;
                }
            }

            j++;
            if (!flag) {
                break;
            }
        }

        if (itemStack != null && i >= 1) {
            ItemStack itemStackValue2 =
                    new ItemStack(Items.filled_map, i + 1, itemStack.getItemDamage());
            if (itemStack.hasDisplayName()) {
                itemStackValue2.setStackDisplayName(itemStack.getDisplayName());
            }

            return itemStackValue2;
        } else {
            return null;
        }
    }

    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        int i = 0;
        ItemStack itemStack = null;
        boolean flag1 = ShapedRecipes.method_d87039b07bd6961907f2();
        int j = 0;
        boolean flag = flag1;

        while (j < inventoryCrafting.getSizeInventory()) {
            ItemStack itemStackValue = inventoryCrafting.getStackInSlot(j);
            if (itemStackValue != null) {
                if (itemStackValue.getItem() == Items.filled_map) {
                    if (itemStack != null) {
                        return false;
                    }

                    itemStack = itemStackValue;
                    if (!(!flag)) {

                        if (itemStackValue.getItem() != Items.map) {
                            return false;
                        }

                        i++;
                    }
                } else {

                    if (itemStackValue.getItem() != Items.map) {
                        return false;
                    }

                    i++;
                }
            }

            j++;
            if (flag) {
                break;
            }
        }

        return itemStack != null && i > 0;
    }
}
