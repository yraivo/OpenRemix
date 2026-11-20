package net.minecraft.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.util.ResourceLocation;

public class PotionAbsoption extends Potion {
    public void applyAttributesModifiersToEntity(
            LivingEntity livingEntity, BaseAttributeMap baseAttributeMap, int i) {
        livingEntity.setAbsorptionAmount(livingEntity.getAbsorptionAmount() + 4 * (i + 1));
        super.applyAttributesModifiersToEntity(livingEntity, baseAttributeMap, i);
    }

    protected PotionAbsoption(int i, ResourceLocation resourceLocation, boolean flag, int j) {
        super(i, resourceLocation, flag, j);
    }

    public void removeAttributesModifiersFromEntity(
            LivingEntity livingEntity, BaseAttributeMap baseAttributeMap, int i) {
        livingEntity.setAbsorptionAmount(livingEntity.getAbsorptionAmount() - 4 * (i + 1));
        super.removeAttributesModifiersFromEntity(livingEntity, baseAttributeMap, i);
    }
}
