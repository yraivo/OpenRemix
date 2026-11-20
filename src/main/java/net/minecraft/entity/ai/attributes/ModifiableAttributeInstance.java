package net.minecraft.entity.ai.attributes;

import net.minecraft.entity.Entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ModifiableAttributeInstance implements IAttributeInstance {
    private BaseAttributeMap attributeMap;
    private double baseValue;
    private Map mapByName;
    private double cachedValue;
    private Map mapByOperation;
    private boolean needsUpdate;

    private IAttribute genericAttribute;
    private static String te;
    private Map mapByUUID;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public boolean hasModifier(AttributeModifier attributeModifier) {
        return this.mapByUUID.get(attributeModifier.getID()) != null;
    }

    public void removeModifier(AttributeModifier attributeModifier) {
        AttributeModifier.hF();

        int i = 0;

        while (i < 3) {
            Set set = (Set) this.mapByOperation.get(i);
            set.remove(attributeModifier);
            i++;
        }

        Set set1 = (Set) this.mapByName.get(attributeModifier.getName());
        if (set1 != null) {
            set1.remove(attributeModifier);
            if (set1.isEmpty()) {
                this.mapByName.remove(attributeModifier.getName());
            }
        }

        this.mapByUUID.remove(attributeModifier.getID());
        this.flagForUpdate();
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public void setBaseValue(double baseValueValue) {
        if (baseValueValue != this.getBaseValue()) {
            this.baseValue = baseValueValue;
            this.flagForUpdate();
        }
    }

    public void removeAllModifiers() {
        AttributeModifier.hF();

        Collection collection = this.func_111122_c();

        if (collection != null) {
            for (java.util.Iterator<?> __recoveryIterator171_4113 =
                            (Lists.newArrayList(collection)).iterator();
                    (__recoveryIterator171_4113).hasNext(); ) {
                AttributeModifier attributeModifier =
                        (AttributeModifier) (__recoveryIterator171_4113).next();
                this.removeModifier(attributeModifier);
            }
        }
    }

    public Collection getModifiersByOperation(int i) {
        return (Collection) this.mapByOperation.get(i);
    }

    private double computeValue() {
        AttributeModifier.hF();

        double baseValueValue = this.getBaseValue();

        for (java.util.Iterator<?> __recoveryIterator171_4689 = (this.aS(0)).iterator();
                (__recoveryIterator171_4689).hasNext(); ) {
            AttributeModifier attributeModifier =
                    (AttributeModifier) (__recoveryIterator171_4689).next();
            baseValueValue += attributeModifier.getAmount();
        }

        double d1 = baseValueValue;

        for (java.util.Iterator<?> __recoveryIterator171_4921 = (this.aS(1)).iterator();
                (__recoveryIterator171_4921).hasNext(); ) {
            AttributeModifier attributeModifierValue =
                    (AttributeModifier) (__recoveryIterator171_4921).next();
            d1 += baseValueValue * attributeModifierValue.getAmount();
        }

        for (java.util.Iterator<?> __recoveryIterator171_5135 = (this.aS(2)).iterator();
                (__recoveryIterator171_5135).hasNext(); ) {
            AttributeModifier attributeModifierValue2 =
                    (AttributeModifier) (__recoveryIterator171_5135).next();
            d1 *= 1.0 + attributeModifierValue2.getAmount();
        }

        return this.genericAttribute.clampValue(d1);
    }

    public void applyModifier(AttributeModifier attributeModifier) {
        AttributeModifier.hF();

        if (this.getModifier(attributeModifier.getID()) != null) {
            throw new IllegalArgumentException(te);
        }

        Set set = (Set) this.mapByName.get(attributeModifier.getName());
        if (set == null) {
            set = Sets.newHashSet();
            this.mapByName.put(attributeModifier.getName(), set);
        }

        ((Set) this.mapByOperation.get(attributeModifier.getOperation())).add(attributeModifier);
        set.add(attributeModifier);
        this.mapByUUID.put(attributeModifier.getID(), attributeModifier);
        this.flagForUpdate();
    }

    static {
        te = "Modifier is already applied on this attribute!";
    }

    public double getAttributeValue() {
        if (this.needsUpdate) {
            this.cachedValue = this.computeValue();
            this.needsUpdate = false;
        }

        return this.cachedValue;
    }

    protected void flagForUpdate() {
        this.needsUpdate = true;
        this.attributeMap.func_180794_a(this);
    }

    public double getBaseValue() {
        return this.baseValue;
    }

    public AttributeModifier getModifier(UUID uuid) {
        return (AttributeModifier) this.mapByUUID.get(uuid);
    }

    public Collection func_111122_c() {
        AttributeModifier.hF();

        HashSet hashset = Sets.newHashSet();

        int i = 0;

        while (i < 3) {
            hashset.addAll(this.getModifiersByOperation(i));
            i++;
        }

        return hashset;
    }

    public ModifiableAttributeInstance(BaseAttributeMap bsNValue, IAttribute bhIValue) {
        AttributeModifier.hF();

        this.mapByOperation = Maps.newHashMap();

        this.mapByName = Maps.newHashMap();
        this.mapByUUID = Maps.newHashMap();
        this.needsUpdate = true;
        this.attributeMap = bsNValue;
        this.genericAttribute = bhIValue;
        this.baseValue = bhIValue.getDefaultValue();
        int i = 0;

        while (i < 3) {
            this.mapByOperation.put(i, Sets.newHashSet());
            i++;
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            AttributeModifier.b(new String[3]);
        }
    }

    public IAttribute getAttribute() {
        return this.genericAttribute;
    }

    private Collection aS(int i) {
        AttributeModifier.hF();

        HashSet hashset = Sets.newHashSet(this.getModifiersByOperation(i));

        IAttribute iAttribute = this.genericAttribute.func_180372_d();

        while (iAttribute != null) {
            IAttributeInstance attributeInstance =
                    this.attributeMap.getAttributeInstance(iAttribute);
            if (attributeInstance != null) {
                hashset.addAll(attributeInstance.getModifiersByOperation(i));
            }

            iAttribute = iAttribute.func_180372_d();
        }

        return hashset;
    }
}
