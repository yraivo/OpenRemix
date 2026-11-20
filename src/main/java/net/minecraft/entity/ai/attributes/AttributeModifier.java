package net.minecraft.entity.ai.attributes;

import net.minecraft.util.MathHelper;

import io.netty.util.internal.ThreadLocalRandom;

import org.apache.commons.lang3.Validate;

import java.util.UUID;

public class AttributeModifier {
    private boolean isSaved = true;
    private String name;

    private UUID id;
    private int operation;

    private static String[] f;
    private double amount;

    public UUID getID() {
        return this.id;
    }

    // region String decoding support

    // endregion

    public static void b(String[] astring) {
        f = astring;
    }

    @Override
    public int hashCode() {
        return this.id != null ? this.id.hashCode() : 0;
    }

    // region String decoding support

    // endregion

    public double getAmount() {
        return this.amount;
    }

    public int getOperation() {
        return this.operation;
    }

    @Override
    public String toString() {
        return "AttributeModifier{amount="
                + this.amount
                + ", operation="
                + this.operation
                + ", name='"
                + this.name
                + '\''
                + ", id="
                + this.id
                + ", serialize="
                + this.isSaved
                + '}';
    }

    public AttributeModifier(UUID uuid, String text, double amountValue, int operationValue) {
        this.id = uuid;
        this.name = text;
        this.amount = amountValue;
        this.operation = operationValue;
        Validate.notEmpty(text, "Modifier name cannot be empty", new Object[0]);

        long j = 0L;

        Validate.inclusiveBetween(j, 2L, operationValue, "Invalid operation");
    }

    public String getName() {
        return this.name;
    }

    public boolean isSaved() {
        return this.isSaved;
    }

    public AttributeModifier(String text, double d0, int i) {
        this(MathHelper.randomUuid(ThreadLocalRandom.current()), text, d0, i);
    }

    public AttributeModifier setSaved(boolean isSavedValue) {
        this.isSaved = isSavedValue;
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object != null && this.getClass() == object.getClass()) {
            AttributeModifier attributeModifier = (AttributeModifier) object;
            if (this.id != null) {
                if (!this.id.equals(attributeModifier.id)) {
                    return false;
                }
            } else if (attributeModifier.id != null) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    // region String decoding support

    // endregion

    public static String[] hF() {
        return f;
    }

    static {
        String[] astring1 = new String[5];
        b(astring1);
    }
}
