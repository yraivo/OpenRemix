package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentFishingSpeed extends Enchantment {
    private static String[] packedTextFragments;

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentFishingSpeed.packedTextFragments[0] = new String("fishingSpeed");
    }

    // endregion

    public int getMaxEnchantability(int i) {
        return super.getMinEnchantability(i) + 50;
    }

    public int getMaxLevel() {
        return 3;
    }

    protected EnchantmentFishingSpeed(
            int i,
            ResourceLocation resourceLocation,
            int j,
            EnumEnchantmentType enumEnchantmentType) {
        super(i, resourceLocation, j, enumEnchantmentType);
        this.setName(packedTextFragments[0]);
    }

    public int getMinEnchantability(int i) {
        return 15 + (i - 1) * 9;
    }

    // region String decoding support

    // endregion
}
