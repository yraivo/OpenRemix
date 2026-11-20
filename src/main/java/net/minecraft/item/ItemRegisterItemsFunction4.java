package net.minecraft.item;

import net.minecraft.block.BlockDirtDirtType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction4 implements Function {
    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }

    public String apply(ItemStack itemStack) {
        return BlockDirtDirtType.byMetadata(itemStack.getItemDamage()).getUnlocalizedName();
    }
}
