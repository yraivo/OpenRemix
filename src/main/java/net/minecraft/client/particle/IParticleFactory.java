package net.minecraft.client.particle;

import net.minecraft.world.World;

public interface IParticleFactory {
    EntityFX getEntityFX(
            int i,
            World world,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int... j);
}
