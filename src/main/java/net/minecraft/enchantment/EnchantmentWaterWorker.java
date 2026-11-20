package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentWaterWorker extends Enchantment {
    private static String[] packedTextFragments;

    public int getMaxLevel() {
        return 1;
    }

    public int getMinEnchantability(int i) {
        return 1;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentWaterWorker.packedTextFragments[0] = new String("waterWorker");
    }

    // endregion

    // region String decoding support

    // endregion

    public EnchantmentWaterWorker(int i, ResourceLocation resourceLocation, int j) {
        super(i, resourceLocation, j, EnumEnchantmentType.ARMOR_HEAD);
        this.setName(packedTextFragments[0]);
    }

    public int getMaxEnchantability(int i) {
        return this.getMinEnchantability(i) + 40;
    }
}
