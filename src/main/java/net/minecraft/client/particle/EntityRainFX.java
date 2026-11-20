package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityRainFX extends EntityFX {
    protected EntityRainFX(World worldValue, double p171_1, double p171_2, double p171_3) {
        super(worldValue, p171_1, p171_2, p171_3, 0.0, 0.0, 0.0);
        this.motionX *= 0.3F;
        this.motionY = java.lang.Math.random() * 0.2F + 0.1F;
        this.motionZ *= 0.3F;
        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;
        this.setParticleTextureIndex(19 + this.rand.nextInt(4));
        this.setSize(0.01F, 0.01F);
        this.particleGravity = 0.06F;
        this.particleMaxAge = (int) (8.0 / (java.lang.Math.random() * 0.8 + 0.2));
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        String text = EffectRenderer.getParticleFlowMarker();
        this.prevPosZ = this.posZ;
        String textValue = text;
        this.motionY = this.motionY - this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.98F;
        this.motionY *= 0.98F;
        this.motionZ *= 0.98F;
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }

        if (this.onGround) {
            if (Math.random() < 0.5) {
                this.setDead();
            }

            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
        }

        BlockPos blockPos = new BlockPos(this);
        BlockState blockState = this.world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        block.setBlockBoundsBasedOnState(this.world, blockPos);
        Material material = blockState.getBlock().getMaterial();
        if (material.isLiquid() || material.isSolid()) {
            double d1;
            {
                d1 = 0.0;
                if (blockState.getBlock() instanceof BlockLiquid) {
                    d1 = 1.0F - BlockLiquid.getLiquidHeightPercent((Integer) blockState.getValue(BlockLiquid.LEVEL));
                    if (!(textValue != null)) {

                        d1 = block.getBlockBoundsMaxY();
                    }
                } else {

                    d1 = block.getBlockBoundsMaxY();
                }
            }

            double d0 = MathHelper.floor(this.posY) + d1;
            if (this.posY < d0) {
                this.setDead();
            }
        }
    }
}
