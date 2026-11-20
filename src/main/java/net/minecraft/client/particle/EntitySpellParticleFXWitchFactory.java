package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntitySpellParticleFXWitchFactory implements IParticleFactory {
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
        EntitySpellParticleFX entitySpellParticleFX =
                new EntitySpellParticleFX(world, d0, d1, d2, d3, d4, d5);
        entitySpellParticleFX.setBaseSpellTextureIndex(144);
        float f = world.rand.nextFloat() * 0.5F + 0.35F;
        entitySpellParticleFX.setRBGColorF(1.0F * f, 0.0F * f, 1.0F * f);
        return entitySpellParticleFX;
    }
}
