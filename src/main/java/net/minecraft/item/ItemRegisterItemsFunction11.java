package net.minecraft.item;

import net.minecraft.block.BlockStoneEnumType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction11 implements Function {
    public String apply(ItemStack itemStack) {
        return BlockStoneEnumType.getStateFromMeta(itemStack.getItemDamage()).getUnlocalizedName();
    }

    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }
}
