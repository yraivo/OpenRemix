package net.minecraft.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WeightedRandomChestContent extends WeightedRandomItem {
    private int theMinimumChanceToGenerateItem;
    private int theMaximumChanceToGenerateItem;
    private ItemStack theItemId;

    public WeightedRandomChestContent(Item item, int i, int j, int k, int l) {
        super(l);
        this.theItemId = new ItemStack(item, 1, i);
        this.theMinimumChanceToGenerateItem = j;
        this.theMaximumChanceToGenerateItem = k;
    }

    public WeightedRandomChestContent(
            ItemStack bxdValue, int theMinimumChanceToGenerateItemValue, int j, int k) {
        super(k);
        this.theItemId = bxdValue;
        this.theMinimumChanceToGenerateItem = theMinimumChanceToGenerateItemValue;
        this.theMaximumChanceToGenerateItem = j;
    }

    public static void generateChestContents(
            Random random, List list, IInventory iInventory, int i) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int j = 0;
        String textValue = text;

        while (j < i) {
            label33:
            {
                WeightedRandomChestContent randomItem =
                        (WeightedRandomChestContent) WeightedRandom.getRandomItem(random, list);
                int k =
                        randomItem.theMinimumChanceToGenerateItem
                                + random.nextInt(
                                        randomItem.theMaximumChanceToGenerateItem
                                                - randomItem.theMinimumChanceToGenerateItem
                                                + 1);
                if (randomItem.theItemId.getMaxStackSize() >= k) {
                    ItemStack itemStack = randomItem.theItemId.copy();
                    itemStack.stackSize = k;
                    iInventory.setInventorySlotContents(
                            random.nextInt(iInventory.getSizeInventory()), itemStack);
                    if (textValue != null) {
                        break label33;
                    }
                }

                int l = 0;

                while (l < k) {
                    ItemStack itemStackValue = randomItem.theItemId.copy();
                    itemStackValue.stackSize = 1;
                    iInventory.setInventorySlotContents(
                            random.nextInt(iInventory.getSizeInventory()), itemStackValue);
                    l++;
                    if (textValue == null) {
                        break;
                    }
                }
            }

            j++;
            if (textValue == null) {
                break;
            }
        }
    }

    public static List func_177629_a(
            List list, WeightedRandomChestContent... weightedRandomChestContentArray) {
        ArrayList arrayList = Lists.newArrayList(list);
        Collections.addAll(arrayList, weightedRandomChestContentArray);
        return arrayList;
    }

    public static void generateDispenserContents(
            Random random, List list, TileEntityDispenser tileEntityDispenser, int i) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int j = 0;
        String textValue = text;

        while (j < i) {
            label33:
            {
                WeightedRandomChestContent randomItem =
                        (WeightedRandomChestContent) WeightedRandom.getRandomItem(random, list);
                int k =
                        randomItem.theMinimumChanceToGenerateItem
                                + random.nextInt(
                                        randomItem.theMaximumChanceToGenerateItem
                                                - randomItem.theMinimumChanceToGenerateItem
                                                + 1);
                if (randomItem.theItemId.getMaxStackSize() >= k) {
                    ItemStack itemStack = randomItem.theItemId.copy();
                    itemStack.stackSize = k;
                    tileEntityDispenser.setInventorySlotContents(
                            random.nextInt(tileEntityDispenser.getSizeInventory()), itemStack);
                    if (textValue != null) {
                        break label33;
                    }
                }

                int l = 0;

                while (l < k) {
                    ItemStack itemStackValue = randomItem.theItemId.copy();
                    itemStackValue.stackSize = 1;
                    tileEntityDispenser.setInventorySlotContents(
                            random.nextInt(tileEntityDispenser.getSizeInventory()), itemStackValue);
                    l++;
                    if (textValue == null) {
                        break;
                    }
                }
            }

            j++;
            if (textValue == null) {
                break;
            }
        }
    }
}
