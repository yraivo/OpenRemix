package net.minecraft.entity.ai.attributes;

import net.minecraft.server.management.LowerStringMap;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServersideAttributeMap extends BaseAttributeMap {
    private final Set attributeInstanceSet = Sets.newHashSet();
    protected final Map descriptionToAttributeInstanceMap = new LowerStringMap();

    public ModifiableAttributeInstance func_180795_e(IAttribute iAttribute) {
        return (ModifiableAttributeInstance) super.getAttributeInstance(iAttribute);
    }

    public IAttributeInstance getAttributeInstanceByName(String text) {
        return this.func_180796_b(text);
    }

    public IAttributeInstance getAttributeInstance(IAttribute iAttribute) {
        return this.func_180795_e(iAttribute);
    }

    public void func_180794_a(IAttributeInstance iAttributeInstance) {
        AttributeModifier.hF();

        if (iAttributeInstance.getAttribute().getShouldWatch()) {
            this.attributeInstanceSet.add(iAttributeInstance);
        }

        for (java.util.Iterator<?> __recoveryIterator171_1346 =
                        (this.field_180377_c.get(iAttributeInstance.getAttribute())).iterator();
                (__recoveryIterator171_1346).hasNext(); ) {
            IAttribute iAttribute = (IAttribute) (__recoveryIterator171_1346).next();
            ModifiableAttributeInstance modifiableAttributeInstance =
                    this.func_180795_e(iAttribute);
            if (modifiableAttributeInstance != null) {
                modifiableAttributeInstance.flagForUpdate();
            }
        }
    }

    public ModifiableAttributeInstance func_180796_b(String text) {
        IAttributeInstance attributeInstanceByName = super.getAttributeInstanceByName(text);
        if (attributeInstanceByName == null) {
            attributeInstanceByName =
                    (IAttributeInstance) this.descriptionToAttributeInstanceMap.get(text);
        }

        return (ModifiableAttributeInstance) attributeInstanceByName;
    }

    public Set getAttributeInstanceSet() {
        return this.attributeInstanceSet;
    }

    protected IAttributeInstance func_180376_c(IAttribute iAttribute) {
        return new ModifiableAttributeInstance(this, iAttribute);
    }

    public Collection getWatchedAttributes() {
        AttributeModifier.hF();

        HashSet hashset = Sets.newHashSet();

        for (java.util.Iterator<?> __recoveryIterator171_2701 =
                        (this.getAllAttributes()).iterator();
                (__recoveryIterator171_2701).hasNext(); ) {
            IAttributeInstance iAttributeInstance =
                    (IAttributeInstance) (__recoveryIterator171_2701).next();
            if (iAttributeInstance.getAttribute().getShouldWatch()) {
                hashset.add(iAttributeInstance);
            }
        }

        return hashset;
    }

    public IAttributeInstance registerAttribute(IAttribute iAttribute) {
        net.minecraft.entity.ai.attributes.IAttributeInstance iAttributeInstance = super.registerAttribute(iAttribute);
        if (iAttribute instanceof net.minecraft.entity.ai.attributes.RangedAttribute
                && ((net.minecraft.entity.ai.attributes.RangedAttribute) iAttribute).getDescription() != null) {
            this.descriptionToAttributeInstanceMap.put(
                    ((net.minecraft.entity.ai.attributes.RangedAttribute) iAttribute).getDescription(), iAttributeInstance);
        }

        return iAttributeInstance;
    }
}
