package net.minecraft.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class WeightedRandomFishable extends WeightedRandomItem {
    private float maxDamagePercent;
    private ItemStack returnStack;
    private boolean enchantable;

    public WeightedRandomFishable setEnchantable() {
        this.enchantable = true;
        return this;
    }

    public ItemStack getItemStack(Random random) {
        ItemStack itemStack = this.returnStack.copy();
        if (this.maxDamagePercent > 0.0F) {
            int i = (int) (this.maxDamagePercent * this.returnStack.getMaxDamage());
            int j = itemStack.getMaxDamage() - random.nextInt(random.nextInt(i) + 1);
            if (j > i) {
                j = i;
            }

            if (j < 1) {
                j = 1;
            }

            itemStack.setItemDamage(j);
        }

        if (this.enchantable) {
            EnchantmentHelper.addRandomEnchantment(random, itemStack, 30);
        }

        return itemStack;
    }

    public WeightedRandomFishable setMaxDamagePercent(float maxDamagePercentValue) {
        this.maxDamagePercent = maxDamagePercentValue;
        return this;
    }

    public WeightedRandomFishable(ItemStack bxdValue, int i) {
        super(i);
        this.returnStack = bxdValue;
    }
}
