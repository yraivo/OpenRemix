package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityTameable;

import com.google.common.base.Predicate;

public class EntityAITargetNonTamed extends EntityAINearestAttackableTarget {
    private EntityTameable theTameable;

    public EntityAITargetNonTamed(
            EntityTameable bkvValue, Class type, boolean p171_2, Predicate p171_3) {
        super(bkvValue, type, 10, p171_2, false, p171_3);
        this.theTameable = bkvValue;
    }

    public boolean shouldExecute() {
        return !this.theTameable.isTamed() && super.shouldExecute();
    }
}
