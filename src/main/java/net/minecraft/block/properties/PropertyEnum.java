package net.minecraft.block.properties;

import net.minecraft.util.StringSerializable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

public class PropertyEnum extends PropertyHelper {
    private static String[] packedTextFragments;
    private ImmutableSet allowedValues;
    private static String h;
    private final Map nameToValue = Maps.newHashMap();

    // region String decoding support

    // endregion

    public static PropertyEnum create(String text, Class oclass) {
        return create(text, oclass, Predicates.alwaysTrue());
    }

    public String getName(Enum oenum) {
        return ((StringSerializable) oenum).getName();
    }

    public static PropertyEnum create(String text, Class oclass, Collection collection) {
        return new PropertyEnum(text, oclass, collection);
    }

    public Collection getAllowedValues() {
        return this.allowedValues;
    }

    // region String decoding support

    // endregion

    public static PropertyEnum create(String text, Class oclass, Predicate predicate) {
        return create(
                text,
                oclass,
                Collections2.filter(Lists.newArrayList(oclass.getEnumConstants()), predicate));
    }

    // region String decoding support

    // endregion

    public String getName(Comparable comparable) {
        return this.getName((Enum) comparable);
    }

    static {
        net.minecraft.block.properties.PropertyEnum.packedTextFragments = new String[2];
        net.minecraft.block.properties.PropertyEnum.packedTextFragments[0] = new String("'");
        net.minecraft.block.properties.PropertyEnum.packedTextFragments[1] =
                new String("Ok`,\u0007Lvg>z9\u0002I\u007fq>d9\u0018Y:vvix\u001d]wg>b9\u0003Y:%");
        h = "Multiple values have the same name '";
    }

    protected PropertyEnum(String text, Class oclass, Collection collection) {
        super(text, oclass);
        this.allowedValues = ImmutableSet.copyOf(collection);

        for (java.util.Iterator<?> __recoveryIterator171_6468 = (collection).iterator();
                (__recoveryIterator171_6468).hasNext(); ) {
            Enum oenum = (Enum) (__recoveryIterator171_6468).next();
            String name = ((StringSerializable) oenum).getName();

            try {
                if (this.nameToValue.containsKey(name)) {
                    throw new IllegalArgumentException(h + name + packedTextFragments[0]);
                }
            } catch (IllegalArgumentException illegalargumentexception) {
                throw method_9a70918140b0a7642dba(illegalargumentexception);
            }

            this.nameToValue.put(name, oenum);
        }
    }

    public static PropertyEnum create(String text, Class oclass, Enum... aenum) {
        return create(text, oclass, Lists.newArrayList(aenum));
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }
}
