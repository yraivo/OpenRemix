package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;

public class RecipeBookCloning implements IRecipe {

    private static String a;

    static {
        a = "generation";
    }

    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        boolean flag1 = ShapedRecipes.method_d87039b07bd6961907f2();
        int i = 0;
        boolean flag = flag1;
        ItemStack itemStack = null;
        int j = 0;

        while (j < inventoryCrafting.getSizeInventory()) {
            ItemStack itemStackValue = inventoryCrafting.getStackInSlot(j);
            if (itemStackValue != null) {
                if (itemStackValue.getItem() == Items.written_book) {
                    if (itemStack != null) {
                        return false;
                    }

                    itemStack = itemStackValue;
                    if (!(!flag)) {

                        if (itemStackValue.getItem() != Items.writable_book) {
                            return false;
                        }

                        i++;
                    }
                } else {

                    if (itemStackValue.getItem() != Items.writable_book) {
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

    // region String decoding support

    // endregion

    public ItemStack getRecipeOutput() {
        return null;
    }

    // region String decoding support

    // endregion

    public int getRecipeSize() {
        return 9;
    }

    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        boolean flag1 = ShapedRecipes.method_d87039b07bd6961907f2();
        int i = 0;
        boolean flag = flag1;
        ItemStack itemStack = null;
        int j = 0;

        while (j < inventoryCrafting.getSizeInventory()) {
            ItemStack itemStackValue = inventoryCrafting.getStackInSlot(j);
            if (itemStackValue != null) {
                if (itemStackValue.getItem() == Items.written_book) {
                    if (itemStack != null) {
                        return null;
                    }

                    itemStack = itemStackValue;
                    if (!(!flag)) {

                        if (itemStackValue.getItem() != Items.writable_book) {
                            return null;
                        }

                        i++;
                    }
                } else {

                    if (itemStackValue.getItem() != Items.writable_book) {
                        return null;
                    }

                    i++;
                }
            }

            j++;
            if (flag) {
                break;
            }
        }

        if (itemStack != null && i >= 1 && ItemEditableBook.getGeneration(itemStack) < 2) {
            ItemStack itemStackValue2 = new ItemStack(Items.written_book, i);
            itemStackValue2.setTag((CompoundTag) itemStack.getTag().copy());
            itemStackValue2.getTag().setInteger(a, ItemEditableBook.getGeneration(itemStack) + 1);
            if (itemStack.hasDisplayName()) {
                itemStackValue2.setStackDisplayName(itemStack.getDisplayName());
            }

            return itemStackValue2;
        } else {
            return null;
        }
    }

    public ItemStack[] getRemainingItems(InventoryCrafting inventoryCrafting) {
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        ItemStack[] itemStackArray = new ItemStack[inventoryCrafting.getSizeInventory()];
        boolean flag = flag1;
        int i = 0;

        while (i < itemStackArray.length) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if (itemStack != null && itemStack.getItem() instanceof ItemEditableBook) {
                itemStackArray[i] = itemStack;
                if (flag) {
                    break;
                }
            }

            i++;
            if (!flag) {
                break;
            }
        }

        return itemStackArray;
    }
}
