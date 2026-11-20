package net.minecraft.item;

import net.minecraft.block.BlockSilverfishEnumType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction15 implements Function {
    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }

    public String apply(ItemStack itemStack) {
        return BlockSilverfishEnumType.byMetadata(itemStack.getItemDamage()).getUnlocalizedName();
    }
}
