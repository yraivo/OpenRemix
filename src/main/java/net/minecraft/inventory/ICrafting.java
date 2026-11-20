package net.minecraft.inventory;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface ICrafting {
    void updateCraftingInventory(Container container, List list);

    void sendSlotContents(Container container, int i, ItemStack itemStack);

    void sendProgressBarUpdate(Container container, int i, int j);

    void func_175173_a(Container container, IInventory iInventory);
}
