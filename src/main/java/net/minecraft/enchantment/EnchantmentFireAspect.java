package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentFireAspect extends Enchantment {
    private static String[] packedTextFragments;

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentFireAspect.packedTextFragments[0] = new String("fire");
    }

    // endregion

    public int getMinEnchantability(int i) {
        return 10 + 20 * (i - 1);
    }

    public int getMaxEnchantability(int i) {
        return super.getMinEnchantability(i) + 50;
    }

    public int getMaxLevel() {
        return 2;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    protected EnchantmentFireAspect(int i, ResourceLocation resourceLocation, int j) {
        super(i, resourceLocation, j, EnumEnchantmentType.WEAPON);
        this.setName(packedTextFragments[0]);
    }

    // region String decoding support

    // endregion
}
