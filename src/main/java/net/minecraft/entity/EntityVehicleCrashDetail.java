package net.minecraft.entity;

import java.util.concurrent.Callable;

class EntityVehicleCrashDetail implements Callable {
    Entity field_180117_a;

    public String func_180116_a() {
        return this.field_180117_a.ridingEntity.toString();
    }

    @Override
    public Object call() {
        return this.func_180116_a();
    }

    EntityVehicleCrashDetail(Entity entity) {
        this.field_180117_a = entity;
    }
}
