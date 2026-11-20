package net.minecraft.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntitySuspendFX extends EntityFX {
    protected EntitySuspendFX(
            World worldValue,
            double p171_1,
            double p171_2,
            double p171_3,
            double p171_4,
            double p171_5,
            double p171_6) {
        super(worldValue, p171_1, p171_2 - 0.125, p171_3, p171_4, p171_5, p171_6);
        this.particleRed = 0.4F;
        this.particleGreen = 0.4F;
        this.particleBlue = 0.7F;
        this.setParticleTextureIndex(0);
        this.setSize(0.01F, 0.01F);
        this.particleScale = this.particleScale * (this.rand.nextFloat() * 0.6F + 0.2F);
        this.motionX = p171_4 * 0.0;
        this.motionY = p171_5 * 0.0;
        this.motionZ = p171_6 * 0.0;
        this.particleMaxAge = (int) (16.0 / (java.lang.Math.random() * 0.8 + 0.2));
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.world.getBlockState(new BlockPos(this)).getBlock().getMaterial()
                != Material.water) {
            this.setDead();
        }

        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }
    }
}
