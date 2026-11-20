package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class ShapelessRecipes implements IRecipe {
    private List recipeItems;
    private ItemStack recipeOutput;

    public ShapelessRecipes(ItemStack itemStack, List list) {
        this.recipeOutput = itemStack;
        this.recipeItems = list;
    }

    public int getRecipeSize() {
        return this.recipeItems.size();
    }

    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        return this.recipeOutput.copy();
    }

    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        ArrayList arrayList = Lists.newArrayList(this.recipeItems);
        boolean flag = flag1;
        int i = 0;

        while (i < inventoryCrafting.getHeight()) {
            int j = 0;

            while (true) {
                if (j < inventoryCrafting.getWidth()) {
                    ItemStack itemStack = inventoryCrafting.getStackInRowAndColumn(j, i);
                    if (itemStack != null) {
                        byte b0 = 0;

                        for (java.util.Iterator<?> __recoveryIterator171_1786 =
                                        (arrayList).iterator();
                                (__recoveryIterator171_1786).hasNext(); ) {
                            ItemStack itemStackValue =
                                    (ItemStack) (__recoveryIterator171_1786).next();
                            if (itemStack.getItem() == itemStackValue.getItem()
                                    && (itemStackValue.getItemDamage() == 32767
                                            || itemStack.getItemDamage()
                                                    == itemStackValue.getItemDamage())) {
                                b0 = 1;
                                arrayList.remove(itemStackValue);
                                if (flag) {
                                    break;
                                }
                            }

                            if (!flag) {
                                break;
                            }
                        }

                        if (b0 == 0) {
                            return false;
                        }
                    }

                    j++;
                    if (flag) {
                        continue;
                    }
                }

                i++;
                if (!flag) {
                    return arrayList.isEmpty();
                }
                break;
            }
        }

        return arrayList.isEmpty();
    }

    public ItemStack[] getRemainingItems(InventoryCrafting inventoryCrafting) {
        ItemStack[] itemStackArray = new ItemStack[inventoryCrafting.getSizeInventory()];
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        int i = 0;
        boolean flag = flag1;

        while (i < itemStackArray.length) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if (itemStack != null && itemStack.getItem().hasContainerItem()) {
                itemStackArray[i] = new ItemStack(itemStack.getItem().getContainerItem());
            }

            i++;
            if (!flag) {
                break;
            }
        }

        return itemStackArray;
    }
}
