package remix.event.impl;

import remix.event.EntityEvent;

import net.minecraft.entity.Entity;

public class PushOutOfBlocksEvent extends EntityEvent {
    public PushOutOfBlocksEvent(Entity entity) {
        super(entity);
    }
}
