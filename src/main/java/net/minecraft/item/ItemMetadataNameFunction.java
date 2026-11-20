package net.minecraft.item;

import com.google.common.base.Function;

class ItemMetadataNameFunction implements Function {
    String[] a;

    public String apply(ItemStack itemStack) {
        int itemDamage = itemStack.getItemDamage();
        if (itemDamage < 0 || itemDamage >= this.a.length) {
            itemDamage = 0;
        }

        return this.a[itemDamage];
    }

    public Object apply(Object object) {
        return this.apply((ItemStack) object);
    }

    ItemMetadataNameFunction(String[] astring) {
        this.a = astring;
    }
}
