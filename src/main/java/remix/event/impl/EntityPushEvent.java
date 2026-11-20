package remix.event.impl;

import remix.event.EntityEvent;

import net.minecraft.entity.Entity;

public class EntityPushEvent extends EntityEvent {
    public EntityPushEvent(Entity entity) {
        super(entity);
    }
}
