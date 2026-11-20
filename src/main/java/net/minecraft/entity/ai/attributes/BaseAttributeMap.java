package net.minecraft.entity.ai.attributes;

import net.minecraft.server.management.LowerStringMap;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public abstract class BaseAttributeMap {

    private static String f;
    protected Multimap field_180377_c;
    protected final Map attributes = Maps.newHashMap();
    protected final Map attributesByName = new LowerStringMap();

    public IAttributeInstance registerAttribute(IAttribute iAttribute) {
        AttributeModifier.hF();

        try {
            if (this.attributesByName.containsKey(iAttribute.getAttributeUnlocalizedName())) {
                throw new IllegalArgumentException(f);
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }

        IAttributeInstance iAttributeInstance = this.func_180376_c(iAttribute);
        this.attributesByName.put(iAttribute.getAttributeUnlocalizedName(), iAttributeInstance);
        this.attributes.put(iAttribute, iAttributeInstance);
        IAttribute iAttributeValue = iAttribute.func_180372_d();

        while (iAttributeValue != null) {
            this.field_180377_c.put(iAttributeValue, iAttribute);
            iAttributeValue = iAttributeValue.func_180372_d();
        }

        return iAttributeInstance;
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public void applyAttributeModifiers(Multimap multimap) {
        AttributeModifier.hF();

        Iterator iterator = multimap.entries().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            IAttributeInstance attributeInstanceByName =
                    this.getAttributeInstanceByName((String) entry.getKey());

            try {
                if (attributeInstanceByName != null) {
                    attributeInstanceByName.removeModifier((AttributeModifier) entry.getValue());
                    attributeInstanceByName.applyModifier((AttributeModifier) entry.getValue());
                }
            } catch (IllegalArgumentException illegalargumentexception) {
                throw method_9a70918140b0a7642dba(illegalargumentexception);
            }
        }
    }

    // region String decoding support

    // endregion

    public BaseAttributeMap() {
        this.field_180377_c = HashMultimap.create();
    }

    public IAttributeInstance getAttributeInstance(IAttribute iAttribute) {
        return (IAttributeInstance) this.attributes.get(iAttribute);
    }

    public void func_180794_a(IAttributeInstance iAttributeInstance) {}

    static {
        f = "Attribute is already registered!";
    }

    // region String decoding support

    // endregion

    public IAttributeInstance getAttributeInstanceByName(String text) {
        return (IAttributeInstance) this.attributesByName.get(text);
    }

    public Collection getAllAttributes() {
        return this.attributesByName.values();
    }

    public void method_09d9b3c802fb50258978(Multimap multimap) {
        AttributeModifier.hF();

        Iterator iterator = multimap.entries().iterator();

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            IAttributeInstance attributeInstanceByName =
                    this.getAttributeInstanceByName((String) entry.getKey());

            try {
                if (attributeInstanceByName != null) {
                    attributeInstanceByName.removeModifier((AttributeModifier) entry.getValue());
                }
            } catch (IllegalArgumentException illegalargumentexception) {
                throw method_9a70918140b0a7642dba(illegalargumentexception);
            }
        }
    }

    protected abstract IAttributeInstance func_180376_c(IAttribute iAttribute);
}
