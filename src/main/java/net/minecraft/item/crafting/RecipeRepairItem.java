package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class RecipeRepairItem implements IRecipe {
    public ItemStack[] getRemainingItems(InventoryCrafting inventoryCrafting) {
        boolean flag1 = ShapedRecipes.method_d87039b07bd6961907f2();
        ItemStack[] itemStackArray = new ItemStack[inventoryCrafting.getSizeInventory()];
        boolean flag = flag1;
        int i = 0;

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

    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        boolean flag1 = ShapedRecipes.method_d87039b07bd6961907f2();
        ArrayList arrayList = Lists.newArrayList();
        int i = 0;
        boolean flag = flag1;

        while (i < inventoryCrafting.getSizeInventory()) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if (itemStack != null) {
                arrayList.add(itemStack);
                if (arrayList.size() > 1) {
                    ItemStack itemStackValue = (ItemStack) arrayList.get(0);
                    if (itemStack.getItem() != itemStackValue.getItem()
                            || itemStackValue.stackSize != 1
                            || itemStack.stackSize != 1
                            || !itemStackValue.getItem().isDamageable()) {
                        return false;
                    }
                }
            }

            i++;
            if (flag) {
                break;
            }
        }

        return arrayList.size() == 2;
    }

    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        ArrayList arrayList = Lists.newArrayList();
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        int i = 0;
        boolean flag = flag1;

        while (i < inventoryCrafting.getSizeInventory()) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if (itemStack != null) {
                arrayList.add(itemStack);
                if (arrayList.size() > 1) {
                    ItemStack itemStackValue = (ItemStack) arrayList.get(0);
                    if (itemStack.getItem() != itemStackValue.getItem()
                            || itemStackValue.stackSize != 1
                            || itemStack.stackSize != 1
                            || !itemStackValue.getItem().isDamageable()) {
                        return null;
                    }
                }
            }

            i++;
            if (!flag) {
                break;
            }
        }

        if (arrayList.size() == 2) {
            ItemStack itemStackValue2 = (ItemStack) arrayList.get(0);
            ItemStack itemStackValue3 = (ItemStack) arrayList.get(1);
            if (itemStackValue2.getItem() == itemStackValue3.getItem()
                    && itemStackValue2.stackSize == 1
                    && itemStackValue3.stackSize == 1
                    && itemStackValue2.getItem().isDamageable()) {
                Item item = itemStackValue2.getItem();
                int j = item.getMaxDamage() - itemStackValue2.getMetadata();
                int k = item.getMaxDamage() - itemStackValue3.getMetadata();
                int l = j + k + item.getMaxDamage() * 5 / 100;
                int i1 = item.getMaxDamage() - l;
                if (i1 < 0) {
                    i1 = 0;
                }

                return new ItemStack(itemStackValue2.getItem(), 1, i1);
            }
        }

        return null;
    }

    public ItemStack getRecipeOutput() {
        return null;
    }

    public int getRecipeSize() {
        return 4;
    }
}
