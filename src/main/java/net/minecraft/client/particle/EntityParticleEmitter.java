package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityParticleEmitter extends EntityFX {
    private int age;
    private Entity attachedEntity;
    private int lifetime;
    private EnumParticleTypes particleTypes;

    public EntityParticleEmitter(World worldValue, Entity aLOValue, EnumParticleTypes aLPValue) {
        super(
                worldValue,
                aLOValue.posX,
                aLOValue.getEntityBoundingBox().minY + aLOValue.height / 2.0F,
                aLOValue.posZ,
                aLOValue.motionX,
                aLOValue.motionY,
                aLOValue.motionZ);
        this.attachedEntity = aLOValue;
        this.lifetime = 3;
        this.particleTypes = aLPValue;
        this.onUpdate();
    }

    public void onUpdate() {
        String text = EffectRenderer.getParticleFlowMarker();
        int i = 0;
        String textValue = text;

        while (i < 16) {
            double d0 = this.rand.nextFloat() * 2.0F - 1.0F;
            double d1 = this.rand.nextFloat() * 2.0F - 1.0F;
            double d2 = this.rand.nextFloat() * 2.0F - 1.0F;
            if (d0 * d0 + d1 * d1 + d2 * d2 <= 1.0) {
                double d3 = this.attachedEntity.posX + d0 * this.attachedEntity.width / 4.0;
                double d4 =
                        this.attachedEntity.getEntityBoundingBox().minY
                                + this.attachedEntity.height / 2.0F
                                + d1 * this.attachedEntity.height / 4.0;
                double d5 = this.attachedEntity.posZ + d2 * this.attachedEntity.width / 4.0;
                this.world.spawnParticle(
                        this.particleTypes, false, d3, d4, d5, d0, d1 + 0.2, d2, new int[0]);
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        this.age++;
        if (this.age >= this.lifetime) {
            this.setDead();
        }
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

    public int getFXLayer() {
        return 3;
    }
}
