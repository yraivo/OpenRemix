package net.minecraft.item;

public class ItemBook extends Item {
    public boolean isItemTool(ItemStack itemStack) {
        return itemStack.stackSize == 1;
    }

    public int getItemEnchantability() {
        return 1;
    }
}
