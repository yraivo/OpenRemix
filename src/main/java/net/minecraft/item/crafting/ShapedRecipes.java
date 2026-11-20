package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;

public class ShapedRecipes implements IRecipe {
    private boolean copyIngredientNBT;
    private ItemStack recipeOutput;
    private static boolean f;
    private int recipeWidth;
    private ItemStack[] recipeItems;
    private int recipeHeight;

    public ShapedRecipes(int i, int j, ItemStack[] btMValue, ItemStack itemStack) {
        this.recipeWidth = i;
        this.recipeHeight = j;
        this.recipeItems = btMValue;
        this.recipeOutput = itemStack;
    }

    public static boolean method_b417d7500ec59d69e373() {
        return f;
    }

    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
    }

    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        boolean flag1 = method_b417d7500ec59d69e373();
        int i = 0;
        boolean flag = flag1;

        while (i <= 3 - this.recipeWidth) {
            for (int j = 0; j <= 3 - this.recipeHeight; j++) {
                if (this.checkMatch(inventoryCrafting, i, j, true)) {
                    return true;
                }

                if (this.checkMatch(inventoryCrafting, i, j, false)) {
                    return true;
                }
                break;
            }

            i++;
            if (!flag) {
                return false;
            }
        }

        return false;
    }

    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        boolean flag1 = method_b417d7500ec59d69e373();
        ItemStack itemStack = this.getRecipeOutput().copy();
        boolean flag = flag1;
        if (this.copyIngredientNBT) {
            int i = 0;

            while (i < inventoryCrafting.getSizeInventory()) {
                ItemStack itemStackValue = inventoryCrafting.getStackInSlot(i);
                if (itemStackValue != null && itemStackValue.hasTagCompound()) {
                    itemStack.setTag((CompoundTag) itemStackValue.getTag().copy());
                }

                i++;
                if (!flag) {
                    break;
                }
            }
        }

        return itemStack;
    }

    public ItemStack[] getRemainingItems(InventoryCrafting inventoryCrafting) {
        ItemStack[] itemStackArray = new ItemStack[inventoryCrafting.getSizeInventory()];
        boolean flag1 = method_b417d7500ec59d69e373();
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

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = method_b417d7500ec59d69e373();
        return !flag;
    }

    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        f = flag;
    }

    static {
        if (!method_b417d7500ec59d69e373()) {
            method_a8745b073fa8adfa1857(true);
        }
    }

    public ShapedRecipes OV() {
        this.copyIngredientNBT = true;
        return this;
    }

    private boolean checkMatch(
            InventoryCrafting inventoryCrafting, int i, int j, boolean flag) {
        boolean flag2 = method_d87039b07bd6961907f2();
        int k = 0;
        boolean flag1 = flag2;

        while (k < 3) {
            for (int l = 0; l < 3; l++) {
                int i1 = k - i;
                int j1 = l - j;
                ItemStack itemStack = null;
                if (i1 >= 0 && j1 >= 0 && i1 < this.recipeWidth && j1 < this.recipeHeight) {
                    if (flag) {
                        itemStack = this.recipeItems[this.recipeWidth - i1 - 1 + j1 * this.recipeWidth];
                    } else {
                        itemStack = this.recipeItems[i1 + j1 * this.recipeWidth];
                    }
                }

                ItemStack itemStackValue = inventoryCrafting.getStackInRowAndColumn(k, l);
                if (itemStackValue != null || itemStack != null) {
                    if (itemStackValue == null && itemStack != null
                            || itemStackValue != null && itemStack == null) {
                        return false;
                    }

                    if (itemStack.getItem() != itemStackValue.getItem()) {
                        return false;
                    }

                    if (itemStack.getItemDamage() != 32767
                            && itemStack.getItemDamage() != itemStackValue.getItemDamage()) {
                        return false;
                    }
                }
                break;
            }

            k++;
            if (flag1) {
                return true;
            }
        }

        return true;
    }
}
