package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentArrowKnockback extends Enchantment {
    private static String[] packedTextFragments;

    public int getMaxLevel() {
        return 2;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentArrowKnockback.packedTextFragments[0] = new String("arrowKnockback");
    }

    // endregion

    public int getMaxEnchantability(int i) {
        return this.getMinEnchantability(i) + 25;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public int getMinEnchantability(int i) {
        return 12 + (i - 1) * 20;
    }

    public EnchantmentArrowKnockback(int i, ResourceLocation resourceLocation, int j) {
        super(i, resourceLocation, j, EnumEnchantmentType.BOW);
        this.setName(packedTextFragments[0]);
    }

    // region String decoding support

    // endregion
}
