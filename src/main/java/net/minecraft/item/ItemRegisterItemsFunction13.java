package net.minecraft.item;

import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction13 implements Function {


    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }

    public String apply(ItemStack itemStack) {
        return (itemStack.getItemDamage() & 1) == 1 ? "wet" : "dry";
    }
}
