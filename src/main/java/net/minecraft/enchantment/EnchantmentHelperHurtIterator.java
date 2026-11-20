package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

final class EnchantmentHelperHurtIterator implements EnchantmentHelperIModifier {
    public Entity attacker;
    public LivingEntity user;

    EnchantmentHelperHurtIterator(Object object) {
        this();
    }

    public void calculateModifier(Enchantment enchantment, int p171_1) {
        enchantment.onUserHurt(this.user, this.attacker, p171_1);
    }

    private EnchantmentHelperHurtIterator() {}
}
