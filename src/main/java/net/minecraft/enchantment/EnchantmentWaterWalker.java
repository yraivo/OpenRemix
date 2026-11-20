package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentWaterWalker extends Enchantment {
    private static String[] packedTextFragments;

    public EnchantmentWaterWalker(int i, ResourceLocation resourceLocation, int j) {
        super(i, resourceLocation, j, EnumEnchantmentType.ARMOR_FEET);
        this.setName(packedTextFragments[0]);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentWaterWalker.packedTextFragments[0] = new String("waterWalker");
    }

    // endregion

    // region String decoding support

    // endregion

    public int getMinEnchantability(int i) {
        return i * 10;
    }

    public int getMaxEnchantability(int i) {
        return this.getMinEnchantability(i) + 15;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public int getMaxLevel() {
        return 3;
    }
}
