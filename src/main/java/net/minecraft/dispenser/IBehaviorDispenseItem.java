package net.minecraft.dispenser;

import net.minecraft.item.ItemStack;
import net.minecraft.dispenser.IBehaviorDispenseItemItemDispenseBehaviorProviderIBehaviorDispenseItem;

public interface IBehaviorDispenseItem {
    IBehaviorDispenseItem itemDispenseBehaviorProvider = new IBehaviorDispenseItemItemDispenseBehaviorProviderIBehaviorDispenseItem();

    ItemStack dispense(IBlockSource iBlockSource, ItemStack itemStack);
}
