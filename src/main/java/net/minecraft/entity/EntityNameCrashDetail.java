package net.minecraft.entity;

import java.util.concurrent.Callable;

class EntityNameCrashDetail implements Callable {
    Entity field_96564_a;

    EntityNameCrashDetail(Entity entity) {
        this.field_96564_a = entity;
    }

    public String getCrashDetail() {
        return this.field_96564_a.getName();
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }
}
