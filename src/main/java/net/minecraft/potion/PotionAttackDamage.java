package net.minecraft.potion;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.ResourceLocation;

public class PotionAttackDamage extends Potion {
    public double getAttributeModifierAmount(int i, AttributeModifier attributeModifier) {
        return this.id == Potion.WEAKNESS.id ? -0.5F * (i + 1) : 1.3 * (i + 1);
    }

    protected PotionAttackDamage(
            int i, ResourceLocation resourceLocation, boolean flag, int j) {
        super(i, resourceLocation, flag, j);
    }
}
