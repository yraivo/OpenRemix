package net.minecraft.client.particle;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDropParticleFX extends EntityFX {
    private Material materialType;
    private int bobTimer;

    public float getBrightness(float f) {
        return this.materialType == Material.water ? super.getBrightness(f) : 1.0F;
    }

    public void onUpdate() {
        String text;
        {
            String textValue = EffectRenderer.getParticleFlowMarker();
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            text = textValue;
            this.prevPosZ = this.posZ;
            if (this.materialType == Material.water) {
                this.particleRed = 0.2F;
                this.particleGreen = 0.3F;
                this.particleBlue = 1.0F;
                if (!(text != null)) {

                    this.particleRed = 1.0F;
                    this.particleGreen = 16.0F / (40 - this.bobTimer + 16);
                    this.particleBlue = 4.0F / (40 - this.bobTimer + 8);
                }
            } else {

                this.particleRed = 1.0F;
                this.particleGreen = 16.0F / (40 - this.bobTimer + 16);
                this.particleBlue = 4.0F / (40 - this.bobTimer + 8);
            }
        }

        {
            this.motionY = this.motionY - this.particleGravity;
            if (this.bobTimer-- > 0) {
                this.motionX *= 0.02;
                this.motionY *= 0.02;
                this.motionZ *= 0.02;
                this.setParticleTextureIndex(113);
                if (!(text != null)) {

                    this.setParticleTextureIndex(112);
                }
            } else {

                this.setParticleTextureIndex(112);
            }
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.98F;
        this.motionY *= 0.98F;
        this.motionZ *= 0.98F;
        if (this.particleMaxAge-- <= 0) {
            this.setDead();
        }

        if (this.onGround) {
            {
                if (this.materialType == Material.water) {
                    this.setDead();
                    this.world.spawnParticle(
                            EnumParticleTypes.WATER_SPLASH,
                            this.posX,
                            this.posY,
                            this.posZ,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                    if (!(text != null)) {

                        this.setParticleTextureIndex(114);
                    }
                } else {

                    this.setParticleTextureIndex(114);
                }
            }

            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
        }

        BlockPos blockPos = new BlockPos(this);
        BlockState blockState = this.world.getBlockState(blockPos);
        Material material = blockState.getBlock().getMaterial();
        if (material.isLiquid() || material.isSolid()) {
            double d0 = 0.0;
            if (blockState.getBlock() instanceof BlockLiquid) {
                d0 =
                        BlockLiquid.getLiquidHeightPercent(
                                (Integer) blockState.getValue(BlockLiquid.LEVEL));
            }

            double d1 = MathHelper.floor(this.posY) + 1 - d0;
            if (this.posY < d1) {
                this.setDead();
            }
        }
    }

    public int getBrightnessForRender(float f) {
        return this.materialType == Material.water ? super.getBrightnessForRender(f) : 257;
    }

    protected EntityDropParticleFX(
            World worldValue, double d0, double d1, double d2, Material material) {
        this(worldValue, d0, d1, d2, material, EffectRenderer.getParticleFlowMarker());
    }

    private EntityDropParticleFX(
            World worldValue,
            double d0,
            double d1,
            double d2,
            Material aLCValue,
            String initialFlowMarker) {
        super(worldValue, d0, d1, d2, 0.0, 0.0, 0.0);

        {
            String text = initialFlowMarker;

            String textValue = text;
            this.motionX = this.motionY = this.motionZ = 0.0;
            if (aLCValue == Material.water) {
                this.particleRed = 0.0F;
                this.particleGreen = 0.0F;
                this.particleBlue = 1.0F;
                if (!(textValue != null)) {

                    this.particleRed = 1.0F;
                    this.particleGreen = 0.0F;
                    this.particleBlue = 0.0F;
                }
            } else {

                this.particleRed = 1.0F;
                this.particleGreen = 0.0F;
                this.particleBlue = 0.0F;
            }
        }

        this.setParticleTextureIndex(113);
        this.setSize(0.01F, 0.01F);
        this.particleGravity = 0.06F;
        this.materialType = aLCValue;
        this.bobTimer = 40;
        this.particleMaxAge = (int) (64.0 / (Math.random() * 0.8 + 0.2));
        this.motionX = this.motionY = this.motionZ = 0.0;
    }
}
