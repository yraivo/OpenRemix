package net.minecraft.enchantment;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class EnchantmentDurability extends Enchantment {
    private static String[] packedTextFragments;

    protected EnchantmentDurability(int i, ResourceLocation resourceLocation, int j) {
        super(i, resourceLocation, j, EnumEnchantmentType.BREAKABLE);
        this.setName(packedTextFragments[0]);
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public static boolean negateDamage(ItemStack itemStack, int i, Random random) {
        return itemStack.getItem() instanceof ItemArmor && random.nextFloat() < 0.6F
                ? false
                : random.nextInt(i + 1) > 0;
    }

    public boolean canApply(ItemStack itemStack) {
        return itemStack.isItemStackDamageable() ? true : super.canApply(itemStack);
    }

    public int getMinEnchantability(int i) {
        return 5 + (i - 1) * 8;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.enchantment.EnchantmentDurability.packedTextFragments[0] = new String("durability");
    }

    // endregion

    // region String decoding support

    // endregion

    public int getMaxLevel() {
        return 3;
    }

    public int getMaxEnchantability(int i) {
        return super.getMinEnchantability(i) + 50;
    }
}
