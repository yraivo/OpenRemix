package net.minecraft.client.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;

public class EntityFootStepFXFactory implements IParticleFactory {
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
        return new EntityFootStepFX(
                MinecraftClient.getMinecraft().getTextureManager(), world, d0, d1, d2);
    }
}
