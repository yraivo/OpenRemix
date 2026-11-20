package net.minecraft.enchantment;

import net.minecraft.entity.CreatureAttribute;

final class EnchantmentHelperModifierLiving implements EnchantmentHelperIModifier {
    public CreatureAttribute entityLiving;
    public float livingModifier;

    private EnchantmentHelperModifierLiving() {}

    EnchantmentHelperModifierLiving(Object object) {
        this();
    }

    public void calculateModifier(Enchantment enchantment, int i) {
        this.livingModifier =
                this.livingModifier + enchantment.calcDamageByCreature(i, this.entityLiving);
    }
}
