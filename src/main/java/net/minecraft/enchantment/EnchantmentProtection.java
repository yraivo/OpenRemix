package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class EnchantmentProtection extends Enchantment {

    private static int[] baseEnchantability;
    private static String[] protectionName;
    private static int[] thresholdEnchantability;

    private static int[] levelEnchantability;
    public int protectionType;

    public int calcModifierDamage(int i, DamageSource damageSource) {
        if (damageSource.canHarmInCreative()) {
            return 0;
        }

        float f = (6 + i * i) / 3.0F;
        return this.protectionType == 0
                ? MathHelper.floor(f * 0.75F)
                : (this.protectionType == 1 && damageSource.isFireDamage()
                        ? MathHelper.floor(f * 1.25F)
                        : (this.protectionType == 2 && damageSource == DamageSource.fall
                                ? MathHelper.floor(f * 2.5F)
                                : (this.protectionType == 3 && damageSource.isExplosion()
                                        ? MathHelper.floor(f * 1.5F)
                                        : (this.protectionType == 4 && damageSource.isProjectile()
                                                ? MathHelper.floor(f * 1.5F)
                                                : 0))));
    }

    public boolean canApplyTogether(Enchantment enchantment) {
        if (!(enchantment instanceof EnchantmentProtection)) {
            return super.canApplyTogether(enchantment);
        }

        EnchantmentProtection enchantmentProtection = (EnchantmentProtection) enchantment;
        return enchantmentProtection.protectionType == this.protectionType
                ? false
                : this.protectionType == 2 || enchantmentProtection.protectionType == 2;
    }

    public static double method_f0e3644d9b9839060196(Entity entity, double d0) {
        int maxEnchantmentLevel =
                EnchantmentHelper.getMaxEnchantmentLevel(
                        Enchantment.blastProtection.effectId, entity.getInventory());
        if (maxEnchantmentLevel > 0) {
            d0 -= MathHelper.floor(d0 * (maxEnchantmentLevel * 0.15F));
        }

        return d0;
    }

    public int getMaxEnchantability(int i) {
        return this.getMinEnchantability(i)
                + net.minecraft.enchantment.EnchantmentProtection.thresholdEnchantability[this.protectionType];
    }

    static {
        net.minecraft.enchantment.EnchantmentProtection.protectionName =
                new String[] {"all", "fire", "fall", "explosion", "projectile"};
        net.minecraft.enchantment.EnchantmentProtection.baseEnchantability = new int[] {1, 10, 5, 5, 3};
        net.minecraft.enchantment.EnchantmentProtection.levelEnchantability = new int[] {11, 8, 6, 8, 6};
        net.minecraft.enchantment.EnchantmentProtection.thresholdEnchantability = new int[] {20, 12, 10, 12, 15};
    }

    public int getMaxLevel() {
        return 4;
    }

    public int getMinEnchantability(int i) {
        return net.minecraft.enchantment.EnchantmentProtection.baseEnchantability[this.protectionType]
                + (i - 1) * net.minecraft.enchantment.EnchantmentProtection.levelEnchantability[this.protectionType];
    }

    public EnchantmentProtection(int i, ResourceLocation resourceLocation, int j, int k) {
        super(i, resourceLocation, j, EnumEnchantmentType.ARMOR);
        this.protectionType = k;
        if (k == 2) {
            this.type = EnumEnchantmentType.ARMOR_FEET;
        }
    }

    public static int getFireTimeForEntity(Entity entity, int i) {
        int j =
                EnchantmentHelper.getMaxEnchantmentLevel(
                        Enchantment.fireProtection.effectId, entity.getInventory());
        if (j > 0) {
            i -= MathHelper.floor((float) i * j * 0.15F);
        }

        return i;
    }

    public String getName() {
        return "enchantment.protect."
                + net.minecraft.enchantment.EnchantmentProtection.protectionName[this.protectionType];
    }
}
