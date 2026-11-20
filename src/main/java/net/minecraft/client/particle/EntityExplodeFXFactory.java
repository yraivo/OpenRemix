package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityExplodeFXFactory implements IParticleFactory {
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
        return new EntityExplodeFX(world, d0, d1, d2, d3, d4, d5);
    }
}
