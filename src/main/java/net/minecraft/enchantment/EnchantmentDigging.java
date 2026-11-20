package net.minecraft.enchantment;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDigging extends Enchantment {
    private static String[] packedTextFragments;

    public int getMaxEnchantability(int i) {
        return super.getMinEnchantability(i) + 50;
    }

    public int getMinEnchantability(int i) {
        return 1 + 10 * (i - 1);
    }

    public boolean canApply(ItemStack itemStack) {
        return itemStack.getItem() == Items.shears ? true : super.canApply(itemStack);
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentDigging.packedTextFragments[0] = new String("digging");
    }

    // endregion

    protected EnchantmentDigging(int i, ResourceLocation resourceLocation, int j) {
        super(i, resourceLocation, j, EnumEnchantmentType.DIGGER);
        this.setName(packedTextFragments[0]);
    }

    public int getMaxLevel() {
        return 5;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    // region String decoding support

    // endregion
}
