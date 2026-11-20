package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityReddustFXFactory implements IParticleFactory {
    public EntityFX getEntityFX(
            int i,
            World world,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int... j) {
        return new EntityReddustFX(world, d0, d1, d2, (float) d3, (float) d4, (float) d5);
    }
}
