package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAIWatchClosest2 extends EntityAIWatchClosest {
    public EntityAIWatchClosest2(EntityLiving entityLiving, Class oclass, float f, float f1) {
        super(entityLiving, oclass, f, f1);
        this.setMutexBits(3);
    }
}
