package net.minecraft.item;

import net.minecraft.block.BlockPrismarineEnumType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction implements Function {
    public String apply(ItemStack itemStack) {
        return BlockPrismarineEnumType.byMetadata(itemStack.getItemDamage()).getUnlocalizedName();
    }

    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }
}
