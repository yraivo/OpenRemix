package net.minecraft.item;

import net.minecraft.block.BlockDoublePlantEnumPlantType;
import net.minecraft.item.ItemStack;

import com.google.common.base.Function;

public final class ItemRegisterItemsFunction12 implements Function {
    public String apply(ItemStack itemStack) {
        return BlockDoublePlantEnumPlantType.byMetadata(itemStack.getItemDamage())
                .getUnlocalizedName();
    }

    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }
}
