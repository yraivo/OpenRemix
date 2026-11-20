package net.minecraft.util;

import net.minecraft.entity.LivingEntity;

public class CombatEntry {
    private DamageSource damageSrc;
    private String field_94566_e;
    private float fallDistance;
    private float damage;
    private float health;
    private int field_94567_b;

    public String func_94562_g() {
        return this.field_94566_e;
    }

    public float aP() {
        return this.damageSrc == DamageSource.outOfWorld ? Float.MAX_VALUE : this.fallDistance;
    }

    public ChatComponent getDamageSrcDisplayName() {
        return this.getDamageSrc().getEntity() == null
                ? null
                : this.getDamageSrc().getEntity().getDisplayName();
    }

    public float getDamage() {
        return this.damage;
    }

    public DamageSource getDamageSrc() {
        return this.damageSrc;
    }

    public boolean isLivingDamageSrc() {
        return this.damageSrc.getEntity() instanceof LivingEntity;
    }

    public CombatEntry(
            DamageSource bERValue,
            int i,
            float healthValue,
            float damageValue,
            String text,
            float fallDistanceValue) {
        this.damageSrc = bERValue;
        this.field_94567_b = i;
        this.damage = damageValue;
        this.health = healthValue;
        this.field_94566_e = text;
        this.fallDistance = fallDistanceValue;
    }
}
