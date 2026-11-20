package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class EntityDiggingFXFactory implements IParticleFactory {
    public EntityFX getEntityFX(
            int i,
            World world,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int... aint) {
        return new EntityDiggingFX(world, d0, d1, d2, d3, d4, d5, Block.getStateById(aint[0]))
                .func_174845_l();
    }
}
