package net.minecraft.item;

import net.minecraft.block.BlockFlowerEnumFlowerColor;
import net.minecraft.block.BlockFlowerEnumFlowerType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction9 implements Function {
    public String apply(ItemStack itemStack) {
        return BlockFlowerEnumFlowerType.getType(
                        BlockFlowerEnumFlowerColor.YELLOW, itemStack.getItemDamage())
                .getUnlocalizedName();
    }

    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }
}
