package remix.util.inventory;

import net.minecraft.item.ItemStack;

public final class ItemSlot {
    public int slotIndex;
    public ItemStack itemStack;

    public ItemSlot(ItemStack itemStackValue, int slotIndexValue) {
        this.itemStack = itemStackValue;
        this.slotIndex = slotIndexValue;
    }

    ItemSlot() {}
}
