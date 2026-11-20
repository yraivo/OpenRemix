package net.minecraft.item;

import net.minecraft.block.BlockPlanksEnumType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction10 implements Function {
    public String apply(ItemStack itemStack) {
        return BlockPlanksEnumType.byMetadata(itemStack.getItemDamage()).getUnlocalizedName();
    }

    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }
}
