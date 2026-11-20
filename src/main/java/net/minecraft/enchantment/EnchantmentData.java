package net.minecraft.enchantment;

import net.minecraft.util.WeightedRandomItem;

public class EnchantmentData extends WeightedRandomItem {
    public int enchantmentLevel;
    public Enchantment enchantmentobj;

    public EnchantmentData(Enchantment bGPValue, int enchantmentLevelValue) {
        super(bGPValue.getWeight());
        this.enchantmentobj = bGPValue;
        this.enchantmentLevel = enchantmentLevelValue;
    }
}
