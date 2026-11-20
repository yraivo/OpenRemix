package net.minecraft.item;

import net.minecraft.block.BlockSandEnumType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction17 implements Function {
    public String apply(ItemStack itemStack) {
        return BlockSandEnumType.byMetadata(itemStack.getItemDamage()).getUnlocalizedName();
    }

    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }
}
