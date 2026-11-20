package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class EnchantmentThorns extends Enchantment {
    private static String za;
    private static String[] packedTextFragments;

    static {
        net.minecraft.enchantment.EnchantmentThorns.packedTextFragments = new String[2];
        net.minecraft.enchantment.EnchantmentThorns.packedTextFragments[0] = new String("thorns");
        net.minecraft.enchantment.EnchantmentThorns.packedTextFragments[1] =
                new String("\u001b\u0004r\u001d\"%I\u000b\rp\u000e+3");
        za = "damage.thorns";
    }

    public int getMaxEnchantability(int i) {
        return super.getMinEnchantability(i) + 50;
    }

    public static int method_17247e8ac00c07f277f5(int i, Random random) {
        return i > 10 ? i - 10 : 1 + random.nextInt(4);
    }

    public void onUserHurt(LivingEntity livingEntity, Entity entity, int i) {
        EnchantmentHelper.b();

        Random random = livingEntity.getRNG();

        ItemStack itemStack = EnchantmentHelper.getEnchantedItem(Enchantment.thorns, livingEntity);
        if (method_3e0c3c21393d3f360b43(i, random)) {
            entity.attackEntityFrom(
                    DamageSource.causeThornsDamage(livingEntity),
                    method_17247e8ac00c07f277f5(i, random));
            entity.playSound(za, 0.5F, 1.0F);
            if (itemStack == null) {
                return;
            }

            itemStack.damageItem(3, livingEntity);

            return;
        }

        if (itemStack != null) {
            itemStack.damageItem(1, livingEntity);
        }
    }

    public int getMaxLevel() {
        return 3;
    }

    public static boolean method_3e0c3c21393d3f360b43(int i, Random random) {
        return i <= 0 ? false : random.nextFloat() < 0.15F * i;
    }

    public boolean canApply(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemArmor ? true : super.canApply(itemStack);
    }

    // region String decoding support

    // endregion

    public EnchantmentThorns(int i, ResourceLocation resourceLocation, int j) {
        super(i, resourceLocation, j, EnumEnchantmentType.ARMOR_TORSO);
        this.setName(packedTextFragments[0]);
    }

    public int getMinEnchantability(int i) {
        return 10 + 20 * (i - 1);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
