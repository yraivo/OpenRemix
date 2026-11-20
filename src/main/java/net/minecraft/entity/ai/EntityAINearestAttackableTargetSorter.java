package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;

import java.util.Comparator;

public class EntityAINearestAttackableTargetSorter implements Comparator {
    private Entity theEntity;

    public EntityAINearestAttackableTargetSorter(Entity entity) {
        this.theEntity = entity;
    }

    public int compare(Entity entity, Entity entityValue) {
        double distanceSqToEntity = this.theEntity.getDistanceSqToEntity(entity);
        double distanceSqToEntityValue = this.theEntity.getDistanceSqToEntity(entityValue);
        return distanceSqToEntity < distanceSqToEntityValue
                ? -1
                : (distanceSqToEntity > distanceSqToEntityValue ? 1 : 0);
    }

    @Override
    public int compare(Object object, Object object1) {
        return this.compare((Entity) object, (Entity) object1);
    }
}
