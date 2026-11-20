package net.minecraft.entity;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

public class SharedMonsterAttributes {
    private static Logger logger;
    public static IAttribute followRange;

    public static IAttribute maxHealth;
    private static String[] packedTextFragments;
    public static IAttribute movementSpeed;

    public static IAttribute attackDamage;
    private static String[] legacyEntityFlowMarker;
    public static IAttribute knockbackResistance;

    public static void setAttributeModifiers(
            BaseAttributeMap baseAttributeMap, NBTTagList nbtTagList) {
        String[] astring1 = getLegacyEntityFlowMarker();
        int i = 0;
        String[] astring = astring1;

        while (i < nbtTagList.tagCount()) {
            {
                CompoundTag compoundTag = nbtTagList.getCompoundTagAt(i);
                IAttributeInstance attributeInstanceByName =
                        baseAttributeMap.getAttributeInstanceByName(compoundTag.getString("Name"));
                if (attributeInstanceByName != null) {
                    applyModifiersToAttributeInstance(attributeInstanceByName, compoundTag);
                    if (!(astring == null)) {

                        net.minecraft.entity.SharedMonsterAttributes.logger.warn(
                                "Ignoring unknown attribute '"
                                        + compoundTag.getString("Name")
                                        + packedTextFragments[0]);
                    }
                } else {

                    net.minecraft.entity.SharedMonsterAttributes.logger.warn(
                            "Ignoring unknown attribute '"
                                    + compoundTag.getString("Name")
                                    + packedTextFragments[0]);
                }
            }

            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public static AttributeModifier readAttributeModifierFromNBT(CompoundTag compoundTag) {
        UUID uuid = new UUID(compoundTag.getLong("UUIDMost"), compoundTag.getLong("UUIDLeast"));

        try {
            return new AttributeModifier(
                    uuid,
                    compoundTag.getString("Name"),
                    compoundTag.getDouble("Amount"),
                    compoundTag.getInteger("Operation"));
        } catch (Exception exception) {
            net.minecraft.entity.SharedMonsterAttributes.logger.warn(
                    "Unable to create attribute: " + exception.getMessage());
            return null;
        }
    }

    public static CompoundTag writeAttributeModifierToNBT(AttributeModifier attributeModifier) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.setString("Name", attributeModifier.getName());
        compoundTag.setDouble("Amount", attributeModifier.getAmount());
        compoundTag.setInteger("Operation", attributeModifier.getOperation());
        compoundTag.setLong("UUIDMost", attributeModifier.getID().getMostSignificantBits());
        compoundTag.setLong("UUIDLeast", attributeModifier.getID().getLeastSignificantBits());
        return compoundTag;
    }

    // region String decoding support

    // endregion

    public static String[] getLegacyEntityFlowMarker() {
        return net.minecraft.entity.SharedMonsterAttributes.legacyEntityFlowMarker;
    }

    private static CompoundTag writeAttributeInstanceToNBT(IAttributeInstance iAttributeInstance) {
        String[] astring1 = getLegacyEntityFlowMarker();
        CompoundTag compoundTag = new CompoundTag();
        String[] astring = astring1;
        IAttribute attribute = iAttributeInstance.getAttribute();
        compoundTag.setString("Name", attribute.getAttributeUnlocalizedName());
        compoundTag.setDouble("Base", iAttributeInstance.getBaseValue());
        Collection collection = iAttributeInstance.func_111122_c();
        if (collection != null && !collection.isEmpty()) {
            NBTTagList nbtTagList = new NBTTagList();

            for (java.util.Iterator<?> __recoveryIterator171_5870 = (collection).iterator();
                    (__recoveryIterator171_5870).hasNext(); ) {
                AttributeModifier attributeModifier =
                        (AttributeModifier) (__recoveryIterator171_5870).next();
                if (attributeModifier.isSaved()) {
                    nbtTagList.appendTag(writeAttributeModifierToNBT(attributeModifier));
                }

                if (astring != null) {
                    break;
                }
            }

            compoundTag.setTag("Modifiers", nbtTagList);
        }

        return compoundTag;
    }

    // region String decoding support

    // endregion

    public static void setLegacyEntityFlowMarker(String[] astring) {
        net.minecraft.entity.SharedMonsterAttributes.legacyEntityFlowMarker = astring;
    }

    private static void applyModifiersToAttributeInstance(
            IAttributeInstance iAttributeInstance, CompoundTag compoundTag) {
        String[] astring1 = getLegacyEntityFlowMarker();
        iAttributeInstance.setBaseValue(compoundTag.getDouble("Base"));
        String[] astring = astring1;
        if (compoundTag.hasKey("Modifiers", 9)) {
            NBTTagList nbtTagList = compoundTag.getTagList("Modifiers", 10);
            int i = 0;

            while (i < nbtTagList.tagCount()) {
                AttributeModifier attributeModifier =
                        readAttributeModifierFromNBT(nbtTagList.getCompoundTagAt(i));
                if (attributeModifier != null) {
                    AttributeModifier modifier =
                            iAttributeInstance.getModifier(attributeModifier.getID());
                    if (modifier != null) {
                        iAttributeInstance.removeModifier(modifier);
                    }

                    iAttributeInstance.applyModifier(attributeModifier);
                }

                i++;
                if (astring != null) {
                    break;
                }
            }
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        net.minecraft.entity.SharedMonsterAttributes.packedTextFragments = new String[3];
        net.minecraft.entity.SharedMonsterAttributes.packedTextFragments[0] = new String("'");
        setLegacyEntityFlowMarker(null);
        net.minecraft.entity.SharedMonsterAttributes.logger = LogManager.getLogger();
        net.minecraft.entity.SharedMonsterAttributes.maxHealth =
                new RangedAttribute(
                                (IAttribute) null, "generic.maxHealth", 20.0, 0.0, Double.MAX_VALUE)
                        .setDescription("Max Health")
                        .setShouldWatch(true);
        net.minecraft.entity.SharedMonsterAttributes.followRange =
                new RangedAttribute((IAttribute) null, "generic.followRange", 32.0, 0.0, 2048.0)
                        .setDescription("Follow Range");
        net.minecraft.entity.SharedMonsterAttributes.knockbackResistance =
                new RangedAttribute((IAttribute) null, "generic.knockbackResistance", 0.0, 0.0, 1.0)
                        .setDescription("Knockback Resistance");
        net.minecraft.entity.SharedMonsterAttributes.movementSpeed =
                new RangedAttribute(
                                (IAttribute) null,
                                "generic.movementSpeed",
                                0.7F,
                                0.0,
                                Double.MAX_VALUE)
                        .setDescription("Movement Speed")
                        .setShouldWatch(true);
        net.minecraft.entity.SharedMonsterAttributes.attackDamage =
                new RangedAttribute(
                        (IAttribute) null, "generic.attackDamage", 2.0, 0.0, Double.MAX_VALUE);
    }

    public static NBTTagList writeBaseAttributeMapToNBT(BaseAttributeMap baseAttributeMap) {
        String[] astring1 = getLegacyEntityFlowMarker();
        NBTTagList nbtTagList = new NBTTagList();
        Iterator iterator = baseAttributeMap.getAllAttributes().iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            IAttributeInstance iAttributeInstance = (IAttributeInstance) iterator.next();
            nbtTagList.appendTag(writeAttributeInstanceToNBT(iAttributeInstance));
            if (astring != null) {
                break;
            }
        }

        return nbtTagList;
    }
}
