package net.minecraft.entity.ai.attributes;

import java.util.Collection;
import java.util.UUID;

public interface IAttributeInstance {
    double getAttributeValue();

    AttributeModifier getModifier(UUID uuid);

    IAttribute getAttribute();

    void removeAllModifiers();

    void setBaseValue(double d0);

    double getBaseValue();

    Collection getModifiersByOperation(int i);

    Collection func_111122_c();

    void applyModifier(AttributeModifier attributeModifier);

    boolean hasModifier(AttributeModifier attributeModifier);

    void removeModifier(AttributeModifier attributeModifier);
}
