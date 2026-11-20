package remix.event;

import net.minecraft.entity.Entity;

public class EntityEvent extends CancellableEvent {
    private Entity entity;

    public EntityEvent(Entity entityValue) {
        this.entity = entityValue;
    }

    public Entity getEntity() {
        return this.entity;
    }
}
