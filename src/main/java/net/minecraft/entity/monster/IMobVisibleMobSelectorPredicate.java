package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;

import com.google.common.base.Predicate;

public final class IMobVisibleMobSelectorPredicate implements Predicate {
    public boolean method_aae6a50880e58b2e8640(Entity entity) {
        return entity instanceof net.minecraft.entity.monster.IMob && !entity.isInvisible();
    }

    public boolean apply(Object p171_0) {
        return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) p171_0);
    }
}
