package net.minecraft.block.properties;

import net.minecraft.entity.Entity;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.HashSet;

public class PropertyInteger extends PropertyHelper {

    private ImmutableSet allowedValues;
    private static String[] packedTextFragments;

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }

        if (!super.equals(object)) {
            return false;
        }

        PropertyInteger propertyInteger = (PropertyInteger) object;
        return this.allowedValues.equals(propertyInteger.allowedValues);
    }

    public String getName(Comparable comparable) {
        return this.getName0((Integer) comparable);
    }

    public int hashCode() {
        boolean flag1 = PropertyHelper.isValid();
        int i = super.hashCode();
        i = 31 * i + this.allowedValues.hashCode();
        boolean flag = flag1;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            PropertyHelper.setValid(!flag);
        }

        return i;
    }

    static {
        net.minecraft.block.properties.PropertyInteger.packedTextFragments = new String[3];
        net.minecraft.block.properties.PropertyInteger.packedTextFragments[0] = new String(")");
    }

    public String getName0(Integer integer) {
        return integer.toString();
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public static PropertyInteger create(String text, int i, int j) {
        return new PropertyInteger(text, i, j);
    }

    public Collection getAllowedValues() {
        return this.allowedValues;
    }

    protected PropertyInteger(String text, int i, int j) {
        super(text, Integer.class);
        if (i < 0) {
            throw new IllegalArgumentException("Min value of " + text + " must be 0 or greater");
        }

        if (j <= i) {
            throw new IllegalArgumentException(
                    "Max value of "
                            + text
                            + " must be greater than min ("
                            + i
                            + packedTextFragments[0]);
        }

        HashSet hashset = Sets.newHashSet();

        for (int k = i; k <= j; k++) {
            hashset.add(k);
        }

        this.allowedValues = ImmutableSet.copyOf(hashset);
    }
}
