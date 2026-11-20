package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentArrowInfinite extends Enchantment {
    private static String[] packedTextFragments;

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public int getMaxLevel() {
        return 1;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentArrowInfinite.packedTextFragments[0] = new String("arrowInfinite");
    }

    // endregion

    public int getMinEnchantability(int i) {
        return 20;
    }

    public int getMaxEnchantability(int i) {
        return 50;
    }

    // region String decoding support

    // endregion

    public EnchantmentArrowInfinite(int i, ResourceLocation resourceLocation, int j) {
        super(i, resourceLocation, j, EnumEnchantmentType.BOW);
        this.setName(packedTextFragments[0]);
    }
}
