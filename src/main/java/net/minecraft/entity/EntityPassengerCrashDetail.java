package net.minecraft.entity;

import java.util.concurrent.Callable;

class EntityPassengerCrashDetail implements Callable {
    Entity field_180119_a;

    @Override
    public Object call() {
        return this.func_180118_a();
    }

    EntityPassengerCrashDetail(Entity entity) {
        this.field_180119_a = entity;
    }

    public String func_180118_a() {
        return this.field_180119_a.riddenByEntity.toString();
    }
}
