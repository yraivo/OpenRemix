package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentArrowFire extends Enchantment {
    private static String[] packedTextFragments;

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentArrowFire.packedTextFragments[0] = new String("arrowFire");
    }

    // endregion

    public int getMaxEnchantability(int i) {
        return 50;
    }

    public EnchantmentArrowFire(int i, ResourceLocation resourceLocation, int j) {
        super(i, resourceLocation, j, EnumEnchantmentType.BOW);
        this.setName(packedTextFragments[0]);
    }

    public int getMinEnchantability(int i) {
        return 20;
    }

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public int getMaxLevel() {
        return 1;
    }
}
