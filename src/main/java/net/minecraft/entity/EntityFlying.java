package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityFlying extends EntityLiving {
    public void moveEntityWithHeading(float f, float f1) {
        label37:
        {
            String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
            if (this.isInWater()) {
                this.moveFlying(f, f1, 0.02F);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.8F;
                this.motionY *= 0.8F;
                this.motionZ *= 0.8F;
                if (astring == null) {
                    break label37;
                }
            }

            if (this.isInLava()) {
                this.moveFlying(f, f1, 0.02F);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
                if (astring == null) {
                    break label37;
                }
            }

            float f2 = 0.91F;
            if (this.onGround) {
                f2 =
                        this.world
                                        .getBlockState(
                                                new BlockPos(
                                                        MathHelper.floor(this.posX),
                                                        MathHelper.floor(
                                                                        this.getEntityBoundingBox()
                                                                                .minY)
                                                                - 1,
                                                        MathHelper.floor(this.posZ)))
                                        .getBlock()
                                        .slipperiness
                                * 0.91F;
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);
            this.moveFlying(f, f1, this.onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;
            if (this.onGround) {
                f2 =
                        this.world
                                        .getBlockState(
                                                new BlockPos(
                                                        MathHelper.floor(this.posX),
                                                        MathHelper.floor(
                                                                        this.getEntityBoundingBox()
                                                                                .minY)
                                                                - 1,
                                                        MathHelper.floor(this.posZ)))
                                        .getBlock()
                                        .slipperiness
                                * 0.91F;
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= f2;
            this.motionY *= f2;
            this.motionZ *= f2;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d1 = this.posX - this.prevPosX;
        double d0 = this.posZ - this.prevPosZ;
        float f4 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
        if (f4 > 1.0F) {
            f4 = 1.0F;
        }

        this.limbSwingAmount = this.limbSwingAmount + (f4 - this.limbSwingAmount) * 0.4F;
        this.limbSwing = this.limbSwing + this.limbSwingAmount;
    }

    public boolean isOnLadder() {
        return false;
    }

    protected void updateFallState(double d0, boolean flag, Block block, BlockPos blockPos) {}

    public EntityFlying(World worldValue) {
        super(worldValue);
    }

    public void fall(float f, float f1) {}
}
