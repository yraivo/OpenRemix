package net.minecraft.client.particle;

import net.minecraft.init.Items;
import net.minecraft.world.World;

public class EntityBreakingFXSnowballFactory implements IParticleFactory {
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
        return new EntityBreakingFX(world, d0, d1, d2, Items.snowball);
    }
}
