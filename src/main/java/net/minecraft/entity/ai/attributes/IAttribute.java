package net.minecraft.entity.ai.attributes;

public interface IAttribute {
    boolean getShouldWatch();

    String getAttributeUnlocalizedName();

    double clampValue(double d0);

    double getDefaultValue();

    IAttribute func_180372_d();
}
