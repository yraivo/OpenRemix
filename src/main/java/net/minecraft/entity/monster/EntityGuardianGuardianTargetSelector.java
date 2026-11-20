package net.minecraft.entity.monster;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;

import com.google.common.base.Predicate;

class EntityGuardianGuardianTargetSelector implements Predicate {
    private EntityGuardian parentEntity;
    EntityGuardian bsT;

    public boolean func_179915_a(LivingEntity livingEntity) {
        return (livingEntity instanceof PlayerEntity || livingEntity instanceof SquidEntity)
                && livingEntity.getDistanceSqToEntity(this.parentEntity) > 9.0;
    }

    EntityGuardianGuardianTargetSelector(EntityGuardian bsTValue) {
        this.bsT = bsTValue;
        this.parentEntity = this.bsT;
    }

    public boolean apply(Object object) {
        return this.func_179915_a((LivingEntity) object);
    }
}
