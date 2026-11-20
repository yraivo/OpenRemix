package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentLootBonus extends Enchantment {
    private static String[] packedTextFragments;

    public int getMinEnchantability(int i) {
        return 15 + (i - 1) * 9;
    }

    protected EnchantmentLootBonus(
            int i,
            ResourceLocation resourceLocation,
            int j,
            EnumEnchantmentType enumEnchantmentType) {
        super(i, resourceLocation, j, enumEnchantmentType);
        if (enumEnchantmentType == EnumEnchantmentType.DIGGER) {
            this.setName(packedTextFragments[0]);
        } else if (enumEnchantmentType == EnumEnchantmentType.FISHING_ROD) {
            this.setName(packedTextFragments[1]);
        } else {
            this.setName(packedTextFragments[2]);
        }
    }

    public int getMaxEnchantability(int i) {
        return super.getMinEnchantability(i) + 50;
    }

    public int getMaxLevel() {
        return 3;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentLootBonus.packedTextFragments[0] = new String("lootBonusDigger");
        net.minecraft.enchantment.EnchantmentLootBonus.packedTextFragments[1] = new String("lootBonusFishing");
        net.minecraft.enchantment.EnchantmentLootBonus.packedTextFragments[2] = new String("lootBonus");
    }

    // endregion

    public boolean canApplyTogether(Enchantment enchantment) {
        return super.canApplyTogether(enchantment)
                && enchantment.effectId != net.minecraft.enchantment.Enchantment.silkTouch.effectId;
    }

    static {
        packedTextFragments = new String[3];
        unpackLiteralFragments();
    }

    // region String decoding support

    // endregion
}
