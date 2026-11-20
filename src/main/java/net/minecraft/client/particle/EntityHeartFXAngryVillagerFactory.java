package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityHeartFXAngryVillagerFactory implements IParticleFactory {
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
        EntityHeartFX entityHeartFX = new EntityHeartFX(world, d0, d1 + 0.5, d2, d3, d4, d5);
        entityHeartFX.setParticleTextureIndex(81);
        entityHeartFX.setRBGColorF(1.0F, 1.0F, 1.0F);
        return entityHeartFX;
    }
}
