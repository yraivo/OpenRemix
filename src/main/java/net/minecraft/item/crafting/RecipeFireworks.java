package net.minecraft.item.crafting;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class RecipeFireworks implements IRecipe {
    private ItemStack uK;

    public ItemStack getRecipeOutput() {
        return this.uK;
    }

    public int getRecipeSize() {
        return 10;
    }


    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        this.uK = null;
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        boolean flag = flag1;
        int i1 = 0;
        int j1 = 0;
        int k1 = 0;

        while (k1 < inventoryCrafting.getSizeInventory()) {
            ItemStack itemStack = inventoryCrafting.getStackInSlot(k1);
            label239:
            if (itemStack != null) {
                if (itemStack.getItem() == Items.gunpowder) {
                    j++;
                    if (flag) {
                        break label239;
                    }

                    Entity.setLegacyGlobalFlowMarker(new String[5]);
                }

                if (itemStack.getItem() == Items.firework_charge) {
                    l++;
                    if (flag) {
                        break label239;
                    }
                }

                if (itemStack.getItem() == Items.dye) {
                    k++;
                    if (flag) {
                        break label239;
                    }
                }

                if (itemStack.getItem() == Items.paper) {
                    i++;
                    if (flag) {
                        break label239;
                    }
                }

                if (itemStack.getItem() == Items.glowstone_dust) {
                    i1++;
                    if (flag) {
                        break label239;
                    }
                }

                if (itemStack.getItem() == Items.diamond) {
                    i1++;
                    if (flag) {
                        break label239;
                    }
                }

                if (itemStack.getItem() == Items.fire_charge) {
                    j1++;
                    if (flag) {
                        break label239;
                    }
                }

                if (itemStack.getItem() == Items.feather) {
                    j1++;
                    if (flag) {
                        break label239;
                    }
                }

                if (itemStack.getItem() == Items.gold_nugget) {
                    j1++;
                    if (flag) {
                        break label239;
                    }
                }

                if (itemStack.getItem() != Items.skull) {
                    return false;
                }

                j1++;
            }

            k1++;
            if (!flag) {
                break;
            }
        }

        i1 += k + j1;
        if (j > 3 || i > 1) {
            return false;
        }

        if (j >= 1 && i == 1 && i1 == 0) {
            this.uK = new ItemStack(Items.fireworks);
            if (l > 0) {
                CompoundTag compoundTag = new CompoundTag();
                CompoundTag compoundTagValue = new CompoundTag();
                NBTTagList nbtTagList = new NBTTagList();
                int i2 = 0;

                while (i2 < inventoryCrafting.getSizeInventory()) {
                    ItemStack itemStackValue = inventoryCrafting.getStackInSlot(i2);
                    if (itemStackValue != null
                            && itemStackValue.getItem() == Items.firework_charge
                            && itemStackValue.hasTagCompound()
                            && itemStackValue.getTag().hasKey("Explosion", 10)) {
                        nbtTagList.appendTag(itemStackValue.getTag().getCompoundTag("Explosion"));
                    }

                    i2++;
                    if (!flag) {
                        break;
                    }
                }

                compoundTagValue.setTag("Explosions", nbtTagList);
                compoundTagValue.setByte("Flight", (byte) j);
                compoundTag.setTag("Fireworks", compoundTagValue);
                this.uK.setTag(compoundTag);
            }

            return true;
        } else if (j == 1 && i == 0 && l == 0 && k > 0 && j1 <= 1) {
            this.uK = new ItemStack(Items.firework_charge);
            CompoundTag compoundTagValue2 = new CompoundTag();
            CompoundTag compoundTagValue3 = new CompoundTag();
            byte b0 = 0;
            ArrayList arrayList = Lists.newArrayList();
            int k2 = 0;

            while (k2 < inventoryCrafting.getSizeInventory()) {
                ItemStack itemStackValue2 = inventoryCrafting.getStackInSlot(k2);
                label157:
                if (itemStackValue2 != null) {
                    if (itemStackValue2.getItem() == Items.dye) {
                        arrayList.add(ItemDye.dyeColors[itemStackValue2.getItemDamage() & 15]);
                        if (flag) {
                            break label157;
                        }
                    }

                    if (itemStackValue2.getItem() == Items.glowstone_dust) {
                        compoundTagValue3.setBoolean("Flicker", true);
                        if (flag) {
                            break label157;
                        }
                    }

                    if (itemStackValue2.getItem() == Items.diamond) {
                        compoundTagValue3.setBoolean("Trail", true);
                        if (flag) {
                            break label157;
                        }
                    }

                    if (itemStackValue2.getItem() == Items.fire_charge) {
                        b0 = 1;
                        if (flag) {
                            break label157;
                        }
                    }

                    if (itemStackValue2.getItem() == Items.feather) {
                        b0 = 4;
                        if (flag) {
                            break label157;
                        }
                    }

                    if (itemStackValue2.getItem() == Items.gold_nugget) {
                        b0 = 2;
                        if (flag) {
                            break label157;
                        }
                    }

                    if (itemStackValue2.getItem() == Items.skull) {
                        b0 = 3;
                    }
                }

                k2++;
                if (!flag) {
                    break;
                }
            }

            int[] aint1 = new int[arrayList.size()];
            int l2 = 0;

            while (l2 < aint1.length) {
                aint1[l2] = (Integer) arrayList.get(l2);
                l2++;
                if (!flag) {
                    break;
                }
            }

            compoundTagValue3.setIntArray("Colors", aint1);
            compoundTagValue3.setByte("Type", b0);
            compoundTagValue2.setTag("Explosion", compoundTagValue3);
            this.uK.setTag(compoundTagValue2);
            return true;
        } else if (j == 0 && i == 0 && l == 1 && k > 0 && k == i1) {
            ArrayList arrayListValue = Lists.newArrayList();
            int l1 = 0;

            while (l1 < inventoryCrafting.getSizeInventory()) {
                ItemStack itemStackValue3 = inventoryCrafting.getStackInSlot(l1);
                if (itemStackValue3 != null) {
                    if (itemStackValue3.getItem() == Items.dye) {
                        arrayListValue.add(ItemDye.dyeColors[itemStackValue3.getItemDamage() & 15]);
                        if (!(flag)) {

                            if (itemStackValue3.getItem() == Items.firework_charge) {
                                this.uK = itemStackValue3.copy();
                                this.uK.stackSize = 1;
                            }
                        }
                    } else {

                        if (itemStackValue3.getItem() == Items.firework_charge) {
                            this.uK = itemStackValue3.copy();
                            this.uK.stackSize = 1;
                        }
                    }
                }

                l1++;
                if (!flag) {
                    break;
                }
            }

            int[] aint = new int[arrayListValue.size()];
            int j2 = 0;

            while (j2 < aint.length) {
                aint[j2] = (Integer) arrayListValue.get(j2);
                j2++;
                if (!flag) {
                    break;
                }
            }

            if (this.uK != null && this.uK.hasTagCompound()) {
                CompoundTag compoundTagValue4 = this.uK.getTag().getCompoundTag("Explosion");
                if (compoundTagValue4 == null) {
                    return false;
                }

                compoundTagValue4.setIntArray("FadeColors", aint);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

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

    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        return this.uK.copy();
    }
}
