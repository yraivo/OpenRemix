package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;

import com.google.common.base.Predicate;

public final class IMobMobSelectorPredicate implements Predicate {
    public boolean apply(Object object) {
        return this.method_aae6a50880e58b2e8640((Entity) object);
    }

    public boolean method_aae6a50880e58b2e8640(Entity entity) {
        return entity instanceof IMob;
    }
}
