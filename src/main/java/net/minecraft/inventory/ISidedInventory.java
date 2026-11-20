package net.minecraft.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;

public interface ISidedInventory extends IInventory {
    boolean canExtractItem(int i, ItemStack itemStack, Facing facing);

    int[] getSlotsForFace(Facing facing);

    boolean canInsertItem(int i, ItemStack itemStack, Facing facing);
}
