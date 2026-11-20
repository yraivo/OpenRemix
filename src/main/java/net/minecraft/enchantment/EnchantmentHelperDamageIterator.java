package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

final class EnchantmentHelperDamageIterator implements EnchantmentHelperIModifier {
    public LivingEntity user;
    public Entity target;

    public void calculateModifier(Enchantment enchantment, int p171_1) {
        enchantment.onEntityDamaged(this.user, this.target, p171_1);
    }

    EnchantmentHelperDamageIterator(Object object) {
        this();
    }

    private EnchantmentHelperDamageIterator() {}
}
