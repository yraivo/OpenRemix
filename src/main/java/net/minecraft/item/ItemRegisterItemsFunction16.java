package net.minecraft.item;

import net.minecraft.block.BlockSandStoneEnumType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction16 implements Function {
    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }

    public String apply(ItemStack itemStack) {
        return BlockSandStoneEnumType.byMetadata(itemStack.getItemDamage()).getUnlocalizedName();
    }
}
