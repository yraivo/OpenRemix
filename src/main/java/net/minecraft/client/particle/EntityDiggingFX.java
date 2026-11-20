package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityDiggingFX extends EntityFX {
    private BlockState sourceBlockState;
    private static String[] packedTextFragments;

    public int getFXLayer() {
        return 1;
    }

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public void renderParticle(
            WorldRenderer worldRenderer,
            Entity entity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5) {
        EffectRenderer.getParticleFlowMarker();
        float f6 = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
        float f7 = f6 + 0.015609375F;
        float f8 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
        float f9 = f8 + 0.015609375F;
        float f10 = 0.1F * this.particleScale;
        if (this.particleIcon != null) {
            f6 = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0F * 16.0F);
            f7 =
                    this.particleIcon.getInterpolatedU(
                            (this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
            f8 = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0F * 16.0F);
            f9 =
                    this.particleIcon.getInterpolatedV(
                            (this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
        }

        float f11 =
                (float)
                        (this.prevPosX
                                + (this.posX - this.prevPosX) * f
                                - net.minecraft.client.particle.EntityFX.interpPosX);
        float f12 =
                (float)
                        (this.prevPosY
                                + (this.posY - this.prevPosY) * f
                                - net.minecraft.client.particle.EntityFX.interpPosY);
        float f13 =
                (float)
                        (this.prevPosZ
                                + (this.posZ - this.prevPosZ) * f
                                - net.minecraft.client.particle.EntityFX.interpPosZ);
        worldRenderer.setOpaqueVertexColorFromFloats(this.particleRed, this.particleGreen, this.particleBlue);
        worldRenderer.addVertexWithUV(
                f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, f6, f9);
        worldRenderer.addVertexWithUV(
                f11 - f1 * f10 + f4 * f10, f12 + f2 * f10, f13 - f3 * f10 + f5 * f10, f6, f8);
        worldRenderer.addVertexWithUV(
                f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, f7, f8);
        worldRenderer.addVertexWithUV(
                f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10, f7, f9);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            EffectRenderer.setParticleFlowMarker(packedTextFragments[0]);
        }
    }

    public EntityDiggingFX applyBlockTint(BlockPos blockPos) {
        if (this.sourceBlockState.getBlock() == Blocks.grass) {
            return this;
        }

        int i = this.sourceBlockState.getBlock().colorMultiplier(this.world, blockPos);
        this.particleRed *= (i >> 16 & 0xFF) / 255.0F;
        this.particleGreen *= (i >> 8 & 0xFF) / 255.0F;
        this.particleBlue *= (i & 0xFF) / 255.0F;
        return this;
    }

    protected EntityDiggingFX(
            World worldValue,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            BlockState aLAValue) {
        super(worldValue, d0, d1, d2, d3, d4, d5);
        this.sourceBlockState = aLAValue;
        this.setParticleIcon(
                MinecraftClient.getMinecraft()
                        .getBlockRendererDispatcher()
                        .getBlockModelShapes()
                        .getTexture(aLAValue));
        this.particleGravity = aLAValue.getBlock().blockParticleGravity;
        this.particleRed = this.particleGreen = this.particleBlue = 0.6F;
        this.particleScale /= 2.0F;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.particle.EntityDiggingFX.packedTextFragments[0] = new String("aqWTN");
    }

    // endregion

    public EntityDiggingFX func_174845_l() {
        Block block = this.sourceBlockState.getBlock();
        if (block == Blocks.grass) {
            return this;
        }

        int renderColor = block.getRenderColor(this.sourceBlockState);
        this.particleRed *= (renderColor >> 16 & 0xFF) / 255.0F;
        this.particleGreen *= (renderColor >> 8 & 0xFF) / 255.0F;
        this.particleBlue *= (renderColor & 0xFF) / 255.0F;
        return this;
    }
}
