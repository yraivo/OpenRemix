package net.minecraft.item;

import net.minecraft.block.BlockPlanksEnumType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction5 implements Function {
    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }

    public String apply(ItemStack itemStack) {
        return BlockPlanksEnumType.byMetadata(itemStack.getItemDamage()).getUnlocalizedName();
    }
}
