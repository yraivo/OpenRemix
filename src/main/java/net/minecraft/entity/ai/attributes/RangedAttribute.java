package net.minecraft.entity.ai.attributes;

import net.minecraft.util.MathHelper;

public class RangedAttribute extends BaseAttribute {

    private double maximumValue;

    private String description;
    private double minimumValue;

    public RangedAttribute(IAttribute iAttribute, String text, double d0, double d1, double d2) {
        super(iAttribute, text, d0);
        this.minimumValue = d1;
        this.maximumValue = d2;
        if (d1 > d2) {
            throw new IllegalArgumentException(
                    "Minimum value cannot be bigger than maximum value!");
        }

        try {
            if (d0 < d1) {
                throw new IllegalArgumentException(
                        "Default value cannot be lower than minimum value!");
            }
        } catch (IllegalArgumentException illegalargumentexception1) {
            throw method_01d66b2932c4ad785a31(illegalargumentexception1);
        }

        try {
            if (d0 > d2) {
                throw new IllegalArgumentException(
                        "Default value cannot be bigger than maximum value!");
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_01d66b2932c4ad785a31(illegalargumentexception);
        }
    }

    private static IllegalArgumentException method_01d66b2932c4ad785a31(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public double clampValue(double d0) {
        return MathHelper.clamp(d0, this.minimumValue, this.maximumValue);
    }


    public String getDescription() {
        return this.description;
    }

    public RangedAttribute setDescription(String text) {
        this.description = text;
        return this;
    }
}
