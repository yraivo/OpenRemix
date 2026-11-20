package net.minecraft.item;

import net.minecraft.block.BlockFlowerEnumFlowerColor;
import net.minecraft.block.BlockFlowerEnumFlowerType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction2 implements Function {
    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }

    public String apply(ItemStack itemStack) {
        return BlockFlowerEnumFlowerType.getType(
                        BlockFlowerEnumFlowerColor.RED, itemStack.getItemDamage())
                .getUnlocalizedName();
    }
}
