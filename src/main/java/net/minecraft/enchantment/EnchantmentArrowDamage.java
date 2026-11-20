package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentArrowDamage extends Enchantment {
    private static String[] packedTextFragments;

    public EnchantmentArrowDamage(int i, ResourceLocation resourceLocation, int j) {
        super(i, resourceLocation, j, EnumEnchantmentType.BOW);
        this.setName(packedTextFragments[0]);
    }

    public int getMaxEnchantability(int i) {
        return this.getMinEnchantability(i) + 15;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentArrowDamage.packedTextFragments[0] = new String("arrowDamage");
    }

    // endregion

    public int getMinEnchantability(int i) {
        return 1 + (i - 1) * 10;
    }

    public int getMaxLevel() {
        return 5;
    }

    // region String decoding support

    // endregion
}
