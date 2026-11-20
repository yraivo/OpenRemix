package net.minecraft.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.util.ResourceLocation;

public class PotionHealthBoost extends Potion {
    public PotionHealthBoost(
            int i, ResourceLocation resourceLocation, boolean flag, int j) {
        super(i, resourceLocation, flag, j);
    }

    public void removeAttributesModifiersFromEntity(
            LivingEntity livingEntity, BaseAttributeMap baseAttributeMap, int i) {
        super.removeAttributesModifiersFromEntity(livingEntity, baseAttributeMap, i);
        if (livingEntity.getHealth() > livingEntity.getMaxHealth()) {
            livingEntity.setHealth(livingEntity.getMaxHealth());
        }
    }
}
