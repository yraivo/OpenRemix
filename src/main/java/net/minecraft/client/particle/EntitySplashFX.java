package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntitySplashFX extends EntityRainFX {
    protected EntitySplashFX(
            World worldValue,
            double p171_1,
            double p171_2,
            double p171_3,
            double motionXValue,
            double p171_5,
            double motionZValue) {
        super(worldValue, p171_1, p171_2, p171_3);
        this.particleGravity = 0.04F;
        this.nextTextureIndexX();
        if (p171_5 == 0.0 && (motionXValue != 0.0 || motionZValue != 0.0)) {
            this.motionX = motionXValue;
            this.motionY = p171_5 + 0.1;
            this.motionZ = motionZValue;
        }
    }
}
