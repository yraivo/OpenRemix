package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentOxygen extends Enchantment {
    private static String[] packedTextFragments;

    public int getMinEnchantability(int i) {
        return 10 * i;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentOxygen.packedTextFragments[0] = new String("oxygen");
    }

    // endregion

    // region String decoding support

    // endregion

    public int getMaxLevel() {
        return 3;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public int getMaxEnchantability(int i) {
        return this.getMinEnchantability(i) + 30;
    }

    public EnchantmentOxygen(int i, ResourceLocation resourceLocation, int j) {
        super(i, resourceLocation, j, EnumEnchantmentType.ARMOR_HEAD);
        this.setName(packedTextFragments[0]);
    }
}
