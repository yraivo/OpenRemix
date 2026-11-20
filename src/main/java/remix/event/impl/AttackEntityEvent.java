package remix.event.impl;

import remix.event.CancellablePhasedEvent;
import remix.event.MotionPhase;

import net.minecraft.entity.Entity;

public class AttackEntityEvent extends CancellablePhasedEvent {
    private Entity entity;

    public Entity getEntity() {
        return this.entity;
    }

    public AttackEntityEvent(MotionPhase motionPhase, Entity entityValue) {
        super(motionPhase);
        this.entity = entityValue;
    }
}
