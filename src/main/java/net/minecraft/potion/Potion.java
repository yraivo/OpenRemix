package net.minecraft.potion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class Potion {
    private String name;
    private boolean isBadEffect;
    public static Potion bzL;
    private static String[] packedTextFragments;
    public static Potion DIG_SPEED;
    public static Potion HARM;
    public static Potion bzP;
    public int id;
    public static Potion WITHER;
    public static Potion bzR;
    public static Potion FIRE_RESISTANCE;
    public static Potion[] potionTypes;
    public static Potion BLINDNESS;
    public static Potion NIGHT_VISION;
    public static Potion MOVE_SPEED;
    public static Potion bzX;
    private int statusIconIndex;
    public static Potion SATURATION;
    public static Potion HUNGER;

    public static Potion POISON;
    public static Potion HEAL;
    public static Potion MOVE_SLOWDOWN;
    public static Potion bAd;
    private double effectiveness;
    public static Potion HEALTH_BOOST;
    public static Potion CONFUSION;
    public static Potion DIG_SLOWDOWN;
    private boolean usable;
    private int liquidColor;
    public static Potion JUMP;
    public static Potion INVISIBILITY;
    public static Potion ABSORPTION;
    public static Potion bAl;
    public static Potion WATER_BREATHING;
    public static Potion DAMAGE_BOOST;
    public static Potion bAo;

    private static Map field_180150_I;
    public static Potion WEAKNESS;
    public static Potion bAq;
    public static Potion RESISTANCE;
    public static Potion REGENERATION;
    private final Map attributeModifierMap = Maps.newHashMap();
    public static Potion bAu;

    public void performEffect(LivingEntity livingEntity, int i) {
        int[] aint = PotionEffect.getLegacyPotionFlowMarker();
        if (this.id == REGENERATION.id) {
            if (!(livingEntity.getHealth() < livingEntity.getMaxHealth())) {
                return;
            }

            livingEntity.heal(1.0F);
            if (aint == null) {
                return;
            }
        }

        if (this.id == POISON.id) {
            if (!(livingEntity.getHealth() > 1.0F)) {
                return;
            }

            livingEntity.attackEntityFrom(DamageSource.magic, 1.0F);
            if (aint == null) {
                return;
            }
        }

        if (this.id == WITHER.id) {
            livingEntity.attackEntityFrom(DamageSource.wither, 1.0F);
            if (aint == null) {
                return;
            }
        }

        if (this.id == HUNGER.id && livingEntity instanceof PlayerEntity) {
            ((PlayerEntity) livingEntity).addExhaustion(0.025F * (i + 1));
            if (aint == null) {
                return;
            }
        }

        if (this.id == SATURATION.id && livingEntity instanceof PlayerEntity) {
            if (livingEntity.world.isRemote) {
                return;
            }

            ((PlayerEntity) livingEntity).getFoodStats().addStats(i + 1, 1.0F);
            if (aint == null) {
                return;
            }
        }

        if ((this.id != HEAL.id || livingEntity.isEntityUndead())
                && (this.id != HARM.id || !livingEntity.isEntityUndead())) {
            if ((this.id != HARM.id || livingEntity.isEntityUndead())
                    && (this.id != HEAL.id || !livingEntity.isEntityUndead())) {
                return;
            }

            livingEntity.attackEntityFrom(DamageSource.magic, 6 << i);
            if (aint == null) {
                return;
            }
        }

        livingEntity.heal(Math.max(4 << i, 0));
    }

    public boolean hasStatusIcon() {
        return this.statusIconIndex >= 0;
    }

    public int getId() {
        return this.id;
    }

    public boolean isInstant() {
        return false;
    }

    public Map getAttributeModifierMap() {
        return this.attributeModifierMap;
    }

    public static String getDurationString(PotionEffect potionEffect) {
        if (potionEffect.getIsPotionDurationMax()) {
            return "**:**";
        }

        int duration = potionEffect.getDuration();
        return StringUtils.ticksToElapsedTime(duration);
    }

    public Potion registerPotionAttributeModifier(
            IAttribute iAttribute, String text, double d0, int i) {
        AttributeModifier attributeModifier =
                new AttributeModifier(UUID.fromString(text), this.getName(), d0, i);
        this.attributeModifierMap.put(iAttribute, attributeModifier);
        return this;
    }

    public double getAttributeModifierAmount(int i, AttributeModifier attributeModifier) {
        return attributeModifier.getAmount() * (i + 1);
    }

    public boolean isUsable() {
        return this.usable;
    }

    public int getStatusIconIndex() {
        return this.statusIconIndex;
    }

    protected Potion(int i, ResourceLocation resourceLocation, boolean flag, int j) {
        this.name = packedTextFragments[0];
        this.statusIconIndex = -1;
        this.id = i;
        net.minecraft.potion.Potion.potionTypes[i] = this;
        net.minecraft.potion.Potion.field_180150_I.put(resourceLocation, this);
        this.isBadEffect = flag;
        if (flag) {
            this.effectiveness = 0.5;
        } else {
            this.effectiveness = 1.0;
        }

        this.liquidColor = j;
    }

    public void applyAttributesModifiersToEntity(
            LivingEntity livingEntity, BaseAttributeMap baseAttributeMap, int i) {
        int[] aint1 = PotionEffect.getLegacyPotionFlowMarker();
        Iterator iterator = this.attributeModifierMap.entrySet().iterator();
        int[] aint = aint1;

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            IAttributeInstance attributeInstance =
                    baseAttributeMap.getAttributeInstance((IAttribute) entry.getKey());
            if (attributeInstance != null) {
                AttributeModifier value = (AttributeModifier) entry.getValue();
                attributeInstance.removeModifier(value);
                attributeInstance.applyModifier(
                        new AttributeModifier(
                                value.getID(),
                                this.getName() + packedTextFragments[1] + i,
                                this.getAttributeModifierAmount(i, value),
                                value.getOperation()));
            }

            if (aint != null) {
                break;
            }
        }
    }

    public boolean isBadEffect() {
        return this.isBadEffect;
    }

    protected Potion setIconIndex(int i, int j) {
        this.statusIconIndex = i + j * 8;
        return this;
    }

    public double getEffectiveness() {
        return this.effectiveness;
    }

    public boolean isReady(int i, int j) {
        if (this.id == REGENERATION.id) {
            int i1 = 50 >> j;
            return i1 > 0 ? i % i1 == 0 : true;
        } else if (this.id == POISON.id) {
            int l = 25 >> j;
            return l > 0 ? i % l == 0 : true;
        } else if (this.id == WITHER.id) {
            int k = 40 >> j;
            return k > 0 ? i % k == 0 : true;
        } else {
            return this.id == HUNGER.id;
        }
    }

    protected Potion setEffectiveness(double bAeValue) {
        this.effectiveness = bAeValue;
        return this;
    }

    public static Potion getPotionFromResourceLocation(String text) {
        return (Potion) net.minecraft.potion.Potion.field_180150_I.get(new ResourceLocation(text));
    }

    public int getLiquidColor() {
        return this.liquidColor;
    }

    public String getName() {
        return this.name;
    }

    // region Static initialization and string tables
    static {
        net.minecraft.potion.Potion.packedTextFragments = new String[3];
        net.minecraft.potion.Potion.packedTextFragments[0] = new String("");
        net.minecraft.potion.Potion.packedTextFragments[1] = new String(" ");
        net.minecraft.potion.Potion.potionTypes = new Potion[32];
        net.minecraft.potion.Potion.field_180150_I = Maps.newHashMap();
        bAo = null;
        MOVE_SPEED =
                new Potion(1, new ResourceLocation("speed"), false, 8171462)
                        .setPotionName("potion.moveSpeed")
                        .setIconIndex(0, 0)
                        .registerPotionAttributeModifier(
                                SharedMonsterAttributes.movementSpeed,
                                "91AEAA56-376B-4498-935B-2F7F68070635",
                                0.2F,
                                2);
        MOVE_SLOWDOWN =
                new Potion(2, new ResourceLocation("slowness"), true, 5926017)
                        .setPotionName("potion.moveSlowdown")
                        .setIconIndex(1, 0)
                        .registerPotionAttributeModifier(
                                SharedMonsterAttributes.movementSpeed,
                                "7107DE5E-7CE8-4030-940E-514C1F160890",
                                -0.15F,
                                2);
        DIG_SPEED =
                new Potion(3, new ResourceLocation("haste"), false, 14270531)
                        .setPotionName("potion.digSpeed")
                        .setIconIndex(2, 0)
                        .setEffectiveness(1.5);
        DIG_SLOWDOWN =
                new Potion(4, new ResourceLocation("mining_fatigue"), true, 4866583)
                        .setPotionName("potion.digSlowDown")
                        .setIconIndex(3, 0);
        DAMAGE_BOOST =
                new PotionAttackDamage(5, new ResourceLocation("strength"), false, 9643043)
                        .setPotionName("potion.damageBoost")
                        .setIconIndex(4, 0)
                        .registerPotionAttributeModifier(
                                SharedMonsterAttributes.attackDamage,
                                "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9",
                                2.5,
                                2);
        HEAL =
                new PotionHealth(
                                6, new ResourceLocation("instant_health"), false, 16262179)
                        .setPotionName("potion.heal");
        HARM =
                new PotionHealth(
                                7, new ResourceLocation("instant_damage"), true, 4393481)
                        .setPotionName("potion.harm");
        JUMP =
                new Potion(8, new ResourceLocation("jump_boost"), false, 2293580)
                        .setPotionName("potion.jump")
                        .setIconIndex(2, 1);
        CONFUSION =
                new Potion(9, new ResourceLocation("nausea"), true, 5578058)
                        .setPotionName("potion.confusion")
                        .setIconIndex(3, 1)
                        .setEffectiveness(0.25);
        REGENERATION =
                new Potion(10, new ResourceLocation("regeneration"), false, 13458603)
                        .setPotionName("potion.regeneration")
                        .setIconIndex(7, 0)
                        .setEffectiveness(0.25);
        RESISTANCE =
                new Potion(11, new ResourceLocation("resistance"), false, 10044730)
                        .setPotionName("potion.resistance")
                        .setIconIndex(6, 1);
        FIRE_RESISTANCE =
                new Potion(12, new ResourceLocation("fire_resistance"), false, 14981690)
                        .setPotionName("potion.fireResistance")
                        .setIconIndex(7, 1);
        WATER_BREATHING =
                new Potion(13, new ResourceLocation("water_breathing"), false, 3035801)
                        .setPotionName("potion.waterBreathing")
                        .setIconIndex(0, 2);
        INVISIBILITY =
                new Potion(14, new ResourceLocation("invisibility"), false, 8356754)
                        .setPotionName("potion.invisibility")
                        .setIconIndex(0, 1);
        BLINDNESS =
                new Potion(15, new ResourceLocation("blindness"), true, 2039587)
                        .setPotionName("potion.blindness")
                        .setIconIndex(5, 1)
                        .setEffectiveness(0.25);
        NIGHT_VISION =
                new Potion(16, new ResourceLocation("night_vision"), false, 2039713)
                        .setPotionName("potion.nightVision")
                        .setIconIndex(4, 1);
        HUNGER =
                new Potion(17, new ResourceLocation("hunger"), true, 5797459)
                        .setPotionName("potion.hunger")
                        .setIconIndex(1, 1);
        WEAKNESS =
                new PotionAttackDamage(18, new ResourceLocation("weakness"), true, 4738376)
                        .setPotionName("potion.weakness")
                        .setIconIndex(5, 0)
                        .registerPotionAttributeModifier(
                                SharedMonsterAttributes.attackDamage,
                                "22653B89-116E-49DC-9B6B-9971489B5BE5",
                                2.0,
                                0);
        POISON =
                new Potion(19, new ResourceLocation("poison"), true, 5149489)
                        .setPotionName("potion.poison")
                        .setIconIndex(6, 0)
                        .setEffectiveness(0.25);
        WITHER =
                new Potion(20, new ResourceLocation("wither"), true, 3484199)
                        .setPotionName("potion.wither")
                        .setIconIndex(1, 2)
                        .setEffectiveness(0.25);
        HEALTH_BOOST =
                new PotionHealthBoost(
                                21, new ResourceLocation("health_boost"), false, 16284963)
                        .setPotionName("potion.healthBoost")
                        .setIconIndex(2, 2)
                        .registerPotionAttributeModifier(
                                SharedMonsterAttributes.maxHealth,
                                "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC",
                                4.0,
                                0);
        ABSORPTION =
                new PotionAbsoption(22, new ResourceLocation("absorption"), false, 2445989)
                        .setPotionName("potion.absorption")
                        .setIconIndex(2, 2);
        SATURATION =
                new PotionHealth(
                                23, new ResourceLocation("saturation"), false, 16262179)
                        .setPotionName("potion.saturation");
        bAl = null;
        bzR = null;
        bzL = null;
        bAq = null;
        bzP = null;
        bzX = null;
        bAu = null;
        bAd = null;
    }

    // endregion

    public void affectEntity(
            Entity entity, Entity entityValue, LivingEntity livingEntity, int i, double d0) {
        int[] aint = PotionEffect.getLegacyPotionFlowMarker();
        if ((this.id != HEAL.id || livingEntity.isEntityUndead())
                && (this.id != HARM.id || !livingEntity.isEntityUndead())) {
            if ((this.id != HARM.id || livingEntity.isEntityUndead())
                    && (this.id != HEAL.id || !livingEntity.isEntityUndead())) {
                return;
            }

            int j = (int) (d0 * (6 << i) + 0.5);
            if (entity == null) {
                livingEntity.attackEntityFrom(DamageSource.magic, j);
                if (aint == null) {
                    return;
                }
            }

            livingEntity.attackEntityFrom(
                    DamageSource.causeIndirectMagicDamage(entity, entityValue), j);
            if (aint == null) {
                return;
            }
        }

        int k = (int) (d0 * (4 << i) + 0.5);
        livingEntity.heal(k);
    }

    public void removeAttributesModifiersFromEntity(
            LivingEntity livingEntity, BaseAttributeMap baseAttributeMap, int i) {
        int[] aint1 = PotionEffect.getLegacyPotionFlowMarker();
        Iterator iterator = this.attributeModifierMap.entrySet().iterator();
        int[] aint = aint1;

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            IAttributeInstance attributeInstance =
                    baseAttributeMap.getAttributeInstance((IAttribute) entry.getKey());
            if (attributeInstance != null) {
                attributeInstance.removeModifier((AttributeModifier) entry.getValue());
            }

            if (aint != null) {
                break;
            }
        }
    }

    public Potion setPotionName(String text) {
        this.name = text;
        return this;
    }

    public static String[] y() {
        String[] astring = new String[net.minecraft.potion.Potion.field_180150_I.size()];
        int[] aint1 = PotionEffect.getLegacyPotionFlowMarker();
        int i = 0;
        Iterator iterator = net.minecraft.potion.Potion.field_180150_I.keySet().iterator();
        int[] aint = aint1;

        while (iterator.hasNext()) {
            ResourceLocation resourceLocation = (ResourceLocation) iterator.next();
            astring[i++] = resourceLocation.toString();
            if (aint != null) {
                break;
            }
        }

        return astring;
    }
}
