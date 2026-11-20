package net.minecraft.enchantment;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentUntouching extends Enchantment {
    private static String[] packedTextFragments;

    public boolean canApplyTogether(Enchantment enchantment) {
        return super.canApplyTogether(enchantment)
                && enchantment.effectId != net.minecraft.enchantment.Enchantment.fortune.effectId;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentUntouching.packedTextFragments[0] = new String("untouching");
    }

    // endregion

    public boolean canApply(ItemStack itemStack) {
        return itemStack.getItem() == Items.shears ? true : super.canApply(itemStack);
    }

    protected EnchantmentUntouching(int i, ResourceLocation resourceLocation, int j) {
        super(i, resourceLocation, j, EnumEnchantmentType.DIGGER);
        this.setName(packedTextFragments[0]);
    }

    public int getMaxEnchantability(int i) {
        return super.getMinEnchantability(i) + 50;
    }

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public int getMinEnchantability(int i) {
        return 15;
    }

    public int getMaxLevel() {
        return 1;
    }
}
