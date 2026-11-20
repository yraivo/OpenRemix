package net.minecraft.item;

import net.minecraft.block.BlockWallEnumType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction14 implements Function {
    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }

    public String apply(ItemStack itemStack) {
        return BlockWallEnumType.byMetadata(itemStack.getItemDamage()).getUnlocalizedName();
    }
}
