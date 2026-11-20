package net.minecraft.item;

import net.minecraft.block.BlockPlanksEnumType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction6 implements Function {
    public String apply(ItemStack itemStack) {
        return BlockPlanksEnumType.byMetadata(itemStack.getItemDamage() + 4).getUnlocalizedName();
    }

    public Object apply(Object p171_0) {
        return this.apply((net.minecraft.item.ItemStack) p171_0);
    }
}
