package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentKnockback extends Enchantment {
    private static String[] packedTextFragments;

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public int getMaxEnchantability(int i) {
        return super.getMinEnchantability(i) + 50;
    }

    protected EnchantmentKnockback(int i, ResourceLocation resourceLocation, int j) {
        super(i, resourceLocation, j, EnumEnchantmentType.WEAPON);
        this.setName(packedTextFragments[0]);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentKnockback.packedTextFragments[0] = new String("knockback");
    }

    // endregion

    // region String decoding support

    // endregion

    public int getMaxLevel() {
        return 2;
    }

    public int getMinEnchantability(int i) {
        return 5 + 20 * (i - 1);
    }
}
