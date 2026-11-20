package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityCrit2FXMagicFactory implements IParticleFactory {
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
        EntityCrit2FX entityCrit2FX = new EntityCrit2FX(world, d0, d1, d2, d3, d4, d5);
        entityCrit2FX.setRBGColorF(
                entityCrit2FX.getRedColorF() * 0.3F,
                entityCrit2FX.getGreenColorF() * 0.8F,
                entityCrit2FX.getBlueColorF());
        entityCrit2FX.nextTextureIndexX();
        return entityCrit2FX;
    }
}
