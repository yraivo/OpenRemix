package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityHugeExplodeFX extends EntityFX {
    private int maximumTime = 8;
    private int timeSinceStart;

    public void onUpdate() {
        String text = EffectRenderer.getParticleFlowMarker();
        int i = 0;
        String textValue = text;

        while (i < 6) {
            double d0 = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            double d1 = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            double d2 = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0;
            this.world.spawnParticle(
                    EnumParticleTypes.EXPLOSION_LARGE,
                    d0,
                    d1,
                    d2,
                    (float) this.timeSinceStart / this.maximumTime,
                    0.0,
                    0.0,
                    new int[0]);
            i++;
            if (textValue == null) {
                break;
            }
        }

        this.timeSinceStart++;
        if (this.timeSinceStart == this.maximumTime) {
            this.setDead();
        }
    }

    public int getFXLayer() {
        return 1;
    }

    public void renderParticle(
            WorldRenderer worldRenderer,
            Entity entity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5) {}

    protected EntityHugeExplodeFX(
            World worldValue, double d0, double d1, double d2, double d3, double d4, double d5) {
        super(worldValue, d0, d1, d2, 0.0, 0.0, 0.0);
    }
}
