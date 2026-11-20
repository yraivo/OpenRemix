package net.minecraft.enchantment;

import net.minecraft.util.DamageSource;

final class EnchantmentHelperModifierDamage implements EnchantmentHelperIModifier {
    public int damageModifier;
    public DamageSource source;

    public void calculateModifier(Enchantment enchantment, int p171_1) {
        this.damageModifier =
                this.damageModifier + enchantment.calcModifierDamage(p171_1, this.source);
    }

    private EnchantmentHelperModifierDamage() {}

    EnchantmentHelperModifierDamage(Object object) {
        this();
    }
}
