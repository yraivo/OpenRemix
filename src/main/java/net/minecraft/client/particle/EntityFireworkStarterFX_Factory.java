package net.minecraft.client.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

public class EntityFireworkStarterFX_Factory implements IParticleFactory {
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
        EntityFireworkSparkFX entityFireworkSparkFX =
                new EntityFireworkSparkFX(
                        world,
                        d0,
                        d1,
                        d2,
                        d3,
                        d4,
                        d5,
                        MinecraftClient.getMinecraft().effectRenderer);
        entityFireworkSparkFX.setAlphaF(0.99F);
        return entityFireworkSparkFX;
    }
}
