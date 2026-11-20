package net.minecraft.item;

import net.minecraft.block.BlockStoneBrickEnumType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction3 implements Function {
    public String apply(ItemStack itemStack) {
        return BlockStoneBrickEnumType.getStateFromMeta(itemStack.getItemDamage()).getVariantName();
    }

    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }
}
