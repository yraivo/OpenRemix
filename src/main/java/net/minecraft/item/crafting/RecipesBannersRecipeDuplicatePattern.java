package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.world.World;

class RecipesBannersRecipeDuplicatePattern implements IRecipe {
    private RecipesBannersRecipeDuplicatePattern() {}

    public int getRecipeSize() {
        return 2;
    }

    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        int i = 0;
        boolean flag = flag1;

        while (i < inventoryCrafting.getSizeInventory()) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if (itemStack != null && TileEntityBanner.getPatterns(itemStack) > 0) {
                ItemStack itemStackValue = itemStack.copy();
                itemStackValue.stackSize = 1;
                return itemStackValue;
            }

            i++;
            if (!flag) {
                break;
            }
        }

        return null;
    }

    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        ItemStack itemStack = null;
        boolean flag1 = ShapedRecipes.method_d87039b07bd6961907f2();
        ItemStack itemStackValue = null;
        boolean flag = flag1;
        int i = 0;

        while (i < inventoryCrafting.getSizeInventory()) {
            ItemStack itemStackValue2 = inventoryCrafting.getStackInSlot(i);
            label75:
            if (itemStackValue2 != null) {
                if (itemStackValue2.getItem() != Items.banner) {
                    return false;
                }

                if (itemStack != null && itemStackValue != null) {
                    return false;
                }

                int j = TileEntityBanner.getBaseColor(itemStackValue2);
                int k = TileEntityBanner.getPatterns(itemStackValue2) > 0 ? 1 : 0;
                if (itemStack != null) {
                    if (k != 0) {
                        return false;
                    }

                    if (j != TileEntityBanner.getBaseColor(itemStack)) {
                        return false;
                    }

                    itemStackValue = itemStackValue2;
                    if (!flag) {
                        break label75;
                    }
                }

                if (itemStackValue != null) {
                    if (k == 0) {
                        return false;
                    }

                    if (j != TileEntityBanner.getBaseColor(itemStackValue)) {
                        return false;
                    }

                    itemStack = itemStackValue2;
                    if (!flag) {
                        break label75;
                    }
                }

                if (k != 0) {
                    itemStack = itemStackValue2;
                    if (!flag) {
                        break label75;
                    }
                }

                itemStackValue = itemStackValue2;
            }

            i++;
            if (flag) {
                break;
            }
        }

        return itemStack != null && itemStackValue != null;
    }

    public ItemStack getRecipeOutput() {
        return null;
    }

    RecipesBannersRecipeDuplicatePattern(Object object) {
        this();
    }

    public ItemStack[] getRemainingItems(InventoryCrafting inventoryCrafting) {
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        ItemStack[] itemStackArray = new ItemStack[inventoryCrafting.getSizeInventory()];
        boolean flag = flag1;
        int i = 0;

        while (i < itemStackArray.length) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if (itemStack != null) {
                if (itemStack.getItem().hasContainerItem()) {
                    itemStackArray[i] = new ItemStack(itemStack.getItem().getContainerItem());
                    if (!(flag)) {

                        if (itemStack.hasTagCompound()
                                && TileEntityBanner.getPatterns(itemStack) > 0) {
                            itemStackArray[i] = itemStack.copy();
                            itemStackArray[i].stackSize = 1;
                        }
                    }
                } else {

                    if (itemStack.hasTagCompound()
                            && TileEntityBanner.getPatterns(itemStack) > 0) {
                        itemStackArray[i] = itemStack.copy();
                        itemStackArray[i].stackSize = 1;
                    }
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
