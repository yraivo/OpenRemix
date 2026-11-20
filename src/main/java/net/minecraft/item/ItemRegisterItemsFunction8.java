package net.minecraft.item;

import net.minecraft.block.BlockRedSandstoneEnumType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction8 implements Function {
    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }

    public String apply(ItemStack itemStack) {
        return BlockRedSandstoneEnumType.byMetadata(itemStack.getItemDamage()).getUnlocalizedName();
    }
}
