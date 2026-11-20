package net.minecraft.init;

import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;

public final class BootstrapItemPotionDispenserBehavior implements IBehaviorDispenseItem {
    private final BehaviorDefaultDispenseItem boS = new BehaviorDefaultDispenseItem();

    public ItemStack dispense(IBlockSource iBlockSource, ItemStack itemStack) {
        return net.minecraft.item.ItemPotion.isSplash(itemStack.getItemDamage())
                ? new net.minecraft.init.BootstrapEntityPotionDispenserBehavior(this, itemStack)
                        .dispense(iBlockSource, itemStack)
                : this.boS.dispense(iBlockSource, itemStack);
    }
}
