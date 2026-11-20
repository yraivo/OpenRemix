package net.minecraft.client.particle;

import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityBreakingFXFactory implements IParticleFactory {
    public EntityFX getEntityFX(
            int j,
            World world,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int... aint) {
        int i = aint.length > 1 ? aint[1] : 0;
        return new EntityBreakingFX(world, d0, d1, d2, d3, d4, d5, Item.getItemById(aint[0]), i);
    }
}
