package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.world.World;

class RecipesBannersRecipeAddPattern implements IRecipe {

    public ItemStack[] getRemainingItems(InventoryCrafting inventoryCrafting) {
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        ItemStack[] itemStackArray = new ItemStack[inventoryCrafting.getSizeInventory()];
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

    private RecipesBannersRecipeAddPattern() {}

    public int getRecipeSize() {
        return 10;
    }

    RecipesBannersRecipeAddPattern(Object object) {
        this();
    }

    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        ItemStack itemStack = null;
        boolean flag = flag1;

        for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
            ItemStack itemStackValue = inventoryCrafting.getStackInSlot(i);
            if (itemStackValue != null && itemStackValue.getItem() == Items.banner) {
                itemStack = itemStackValue.copy();
                itemStack.stackSize = 1;
                break;
            }
        }

        BannerPattern bannerPattern = this.method_8bdbf24ef684299d3e24(inventoryCrafting);
        if (bannerPattern != null) {
            int k = 0;
            int j = 0;

            while (j < inventoryCrafting.getSizeInventory()) {
                ItemStack itemStackValue2 = inventoryCrafting.getStackInSlot(j);
                if (itemStackValue2 != null && itemStackValue2.getItem() == Items.dye) {
                    k = itemStackValue2.getItemDamage();
                    if (flag) {
                        break;
                    }
                }

                j++;
                if (!flag) {
                    break;
                }
            }

            NBTTagList nbtTagList;
            {
                CompoundTag compoundTag =
                        itemStack.getSubCompound("BlockEntityTag", true);

                if (compoundTag.hasKey("Patterns", 9)) {
                    nbtTagList = compoundTag.getTagList("Patterns", 10);
                    if (!(flag)) {

                        nbtTagList = new NBTTagList();
                        compoundTag.setTag("Patterns", nbtTagList);
                    }
                } else {

                    nbtTagList = new NBTTagList();
                    compoundTag.setTag("Patterns", nbtTagList);
                }
            }

            CompoundTag compoundTagValue = new CompoundTag();
            compoundTagValue.setString("Pattern", bannerPattern.getPatternID());
            compoundTagValue.setInteger("Color", k);
            nbtTagList.appendTag(compoundTagValue);
        }

        return itemStack;
    }


    private BannerPattern method_8bdbf24ef684299d3e24(InventoryCrafting inventoryCrafting) {
        BannerPattern[] bannerPatternArray = BannerPattern.values();
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        int length = bannerPatternArray.length;
        boolean flag = flag1;
        int j = 0;

        while (j < length) {
            BannerPattern bannerPattern = bannerPatternArray[j];
            if (bannerPattern.hasValidCrafting()) {
                byte b0;
                label121:
                {
                    b0 = 1;
                    if (bannerPattern.hasCraftingStack()) {
                        byte b1 = 0;
                        byte b2 = 0;
                        int k = 0;

                        while (k < inventoryCrafting.getSizeInventory() && b0 != 0) {
                            ItemStack itemStack = inventoryCrafting.getStackInSlot(k);
                            label104:
                            if (itemStack != null && itemStack.getItem() != Items.banner) {
                                if (itemStack.getItem() == Items.dye) {
                                    if (b2 != 0) {
                                        b0 = 0;
                                        if (flag) {
                                            break;
                                        }
                                    }

                                    b2 = 1;
                                    if (flag) {
                                        break label104;
                                    }
                                }

                                if (b1 != 0
                                        || !itemStack.isItemEqual(
                                                bannerPattern.getCraftingStack())) {
                                    b0 = 0;
                                    if (flag) {
                                        break;
                                    }
                                }

                                b1 = 1;
                            }

                            k++;
                            if (!flag) {
                                break;
                            }
                        }

                        if (b1 == 0) {
                            b0 = 0;
                        }

                        if (flag) {
                            break label121;
                        }
                    }

                    if (inventoryCrafting.getSizeInventory()
                            != bannerPattern.getCraftingLayers().length
                                    * bannerPattern.getCraftingLayers()[0].length()) {
                        b0 = 0;
                        if (flag) {
                            break label121;
                        }
                    }

                    int i1 = -1;
                    int j1 = 0;

                    while (j1 < inventoryCrafting.getSizeInventory() && b0 != 0) {
                        {
                            int l = j1 / 3;
                            int k1 = j1 % 3;
                            ItemStack itemStackValue = inventoryCrafting.getStackInSlot(j1);
                            if (itemStackValue != null
                                    && itemStackValue.getItem() != Items.banner) {
                                if (itemStackValue.getItem() != Items.dye) {
                                    b0 = 0;
                                    if (flag) {
                                        break;
                                    }
                                }

                                if (i1 != -1 && i1 != itemStackValue.getItemDamage()) {
                                    b0 = 0;
                                    if (flag) {
                                        break;
                                    }
                                }

                                if (bannerPattern.getCraftingLayers()[l].charAt(k1) == ' ') {
                                    b0 = 0;
                                    if (flag) {
                                        break;
                                    }
                                }

                                i1 = itemStackValue.getItemDamage();
                                if (!(flag)) {

                                    if (bannerPattern.getCraftingLayers()[l].charAt(k1) != ' ') {
                                        b0 = 0;
                                        if (flag) {
                                            break;
                                        }
                                    }
                                }
                            } else {

                                if (bannerPattern.getCraftingLayers()[l].charAt(k1) != ' ') {
                                    b0 = 0;
                                    if (flag) {
                                        break;
                                    }
                                }
                            }
                        }

                        j1++;
                        if (!flag) {
                            break;
                        }
                    }
                }

                if (b0 != 0) {
                    return bannerPattern;
                }
            }

            j++;
            if (!flag) {
                break;
            }
        }

        return null;
    }

    public ItemStack getRecipeOutput() {
        return null;
    }

    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        byte b0 = 0;
        boolean flag = flag1;
        int i = 0;

        while (i < inventoryCrafting.getSizeInventory()) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(i);
            if (itemStack != null && itemStack.getItem() == Items.banner) {
                if (b0 != 0) {
                    return false;
                }

                if (TileEntityBanner.getPatterns(itemStack) >= 6) {
                    return false;
                }

                b0 = 1;
            }

            i++;
            if (!flag) {
                break;
            }
        }

        return b0 == 0 ? false : this.method_8bdbf24ef684299d3e24(inventoryCrafting) != null;
    }
}
