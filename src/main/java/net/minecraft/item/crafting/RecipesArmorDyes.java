package net.minecraft.item.crafting;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmorArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class RecipesArmorDyes implements IRecipe {
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        ItemStack itemStack = null;
        int[] aint = new int[3];
        int i = 0;
        boolean flag = flag1;
        int j = 0;
        ItemArmor itemArmor = null;
        int k = 0;

        while (k < inventoryCrafting.getSizeInventory()) {
            ItemStack itemStackValue = inventoryCrafting.getStackInSlot(k);
            label40:
            if (itemStackValue != null) {
                if (itemStackValue.getItem() instanceof ItemArmor) {
                    itemArmor = (ItemArmor) itemStackValue.getItem();
                    if (itemArmor.getArmorMaterial() != ItemArmorArmorMaterial.LEATHER
                            || itemStack != null) {
                        return null;
                    }

                    itemStack = itemStackValue.copy();
                    itemStack.stackSize = 1;
                    if (!itemArmor.hasColor(itemStackValue)) {
                        break label40;
                    }

                    int l = itemArmor.getColor(itemStack);
                    float f = (l >> 16 & 0xFF) / 255.0F;
                    float f1 = (l >> 8 & 0xFF) / 255.0F;
                    float f2 = (l & 0xFF) / 255.0F;
                    i = (int) (i + Math.max(f, Math.max(f1, f2)) * 255.0F);
                    aint[0] = (int) (aint[0] + f * 255.0F);
                    aint[1] = (int) (aint[1] + f1 * 255.0F);
                    aint[2] = (int) (aint[2] + f2 * 255.0F);
                    j++;
                    if (flag) {
                        break label40;
                    }
                }

                if (itemStackValue.getItem() != Items.dye) {
                    return null;
                }

                float[] afloat =
                        EntitySheep.getDyeRgb(
                                EnumDyeColor.byDyeDamage(itemStackValue.getItemDamage()));
                int j1 = (int) (afloat[0] * 255.0F);
                int k1 = (int) (afloat[1] * 255.0F);
                int i1 = (int) (afloat[2] * 255.0F);
                i += Math.max(j1, Math.max(k1, i1));
                aint[0] += j1;
                aint[1] += k1;
                aint[2] += i1;
                j++;
            }

            k++;
            if (!flag) {
                break;
            }
        }

        if (itemArmor == null) {
            return null;
        }

        k = aint[0] / j;
        int j2 = aint[1] / j;
        int l1 = aint[2] / j;
        float f3 = (float) i / j;
        float f4 = Math.max(k, Math.max(j2, l1));
        k = (int) (k * f3 / f4);
        j2 = (int) (j2 * f3 / f4);
        l1 = (int) (l1 * f3 / f4);
        int i2 = (k << 8) + j2;
        i2 = (i2 << 8) + l1;
        itemArmor.setColor(itemStack, i2);
        return itemStack;
    }

    public ItemStack[] getRemainingItems(InventoryCrafting inventoryCrafting) {
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        ItemStack[] itemStackArray = new ItemStack[inventoryCrafting.getSizeInventory()];
        boolean flag = flag1;
        int i = 0;

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

    public int getRecipeSize() {
        return 10;
    }

    public ItemStack getRecipeOutput() {
        return null;
    }

    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        boolean flag1 = ShapedRecipes.method_d87039b07bd6961907f2();
        ItemStack itemStack = null;
        boolean flag = flag1;
        ArrayList arrayList = Lists.newArrayList();
        int i = 0;

        while (i < inventoryCrafting.getSizeInventory()) {
            ItemStack itemStackValue = inventoryCrafting.getStackInSlot(i);
            if (itemStackValue != null) {
                if (itemStackValue.getItem() instanceof ItemArmor) {
                    ItemArmor item = (ItemArmor) itemStackValue.getItem();
                    if (item.getArmorMaterial() != ItemArmorArmorMaterial.LEATHER
                            || itemStack != null) {
                        return false;
                    }

                    itemStack = itemStackValue;
                    if (!(!flag)) {

                        if (itemStackValue.getItem() != Items.dye) {
                            return false;
                        }

                        arrayList.add(itemStackValue);
                    }
                } else {

                    if (itemStackValue.getItem() != Items.dye) {
                        return false;
                    }

                    arrayList.add(itemStackValue);
                }
            }

            i++;
            if (flag) {
                break;
            }
        }

        return itemStack != null && !arrayList.isEmpty();
    }
}
