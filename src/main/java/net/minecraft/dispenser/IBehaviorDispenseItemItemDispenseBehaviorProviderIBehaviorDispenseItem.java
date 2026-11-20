package net.minecraft.dispenser;

import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;

public final class IBehaviorDispenseItemItemDispenseBehaviorProviderIBehaviorDispenseItem implements IBehaviorDispenseItem {
    public ItemStack dispense(IBlockSource iBlockSource, ItemStack itemStack) {
        return itemStack;
    }
}
