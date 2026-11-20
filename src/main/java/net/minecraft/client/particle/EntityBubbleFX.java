package net.minecraft.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityBubbleFX extends EntityFX {
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY += 0.002;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.85F;
        this.motionY *= 0.85F;
        this.motionZ *= 0.85F;
        if (this.world.getBlockState(new BlockPos(this)).getBlock().getMaterial()
                != Material.water) {
            this.setDead();
        }

        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }

    protected EntityBubbleFX(
            World worldValue,
            double p171_1,
            double p171_2,
            double p171_3,
            double p171_4,
            double p171_5,
            double p171_6) {
        super(worldValue, p171_1, p171_2, p171_3, p171_4, p171_5, p171_6);
        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;
        this.setParticleTextureIndex(32);
        this.setSize(0.02F, 0.02F);
        this.particleScale = this.particleScale * (this.rand.nextFloat() * 0.6F + 0.2F);
        this.motionX = p171_4 * 0.2F + (java.lang.Math.random() * 2.0 - 1.0) * 0.02F;
        this.motionY = p171_5 * 0.2F + (java.lang.Math.random() * 2.0 - 1.0) * 0.02F;
        this.motionZ = p171_6 * 0.2F + (java.lang.Math.random() * 2.0 - 1.0) * 0.02F;
        this.particleMaxAge = (int) (8.0 / (java.lang.Math.random() * 0.8 + 0.2));
    }
}
