package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityEnderEye extends Entity {
    private double baS;
    private double baT;
    private boolean shatterOrDrop;
    private int despawnTimer;
    private double targetY;

    public float getBrightness(float f) {
        return 1.0F;
    }

    public int getBrightnessForRender(float f) {
        return 15728880;
    }

    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        this.posX = this.posX + this.motionX;
        this.posY = this.posY + this.motionY;
        this.posZ = this.posZ + this.motionZ;
        float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
        boolean flag = flag1;
        this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180.0 / Math.PI);

        while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
            if (!flag) {
                break;
            }
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
            if (!flag) {
                break;
            }
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
            if (!flag) {
                break;
            }
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
            if (!flag) {
                break;
            }
        }

        this.rotationPitch =
                this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        if (!this.world.isRemote) {
            double d0 = this.baS - this.posX;
            double d1 = this.baT - this.posZ;
            float f1 = (float) Math.sqrt(d0 * d0 + d1 * d1);
            float f2 = (float) Math.atan2(d1, d0);
            double d2 = f + (f1 - f) * 0.0025;
            if (f1 < 1.0F) {
                d2 *= 0.8;
                this.motionY *= 0.8;
            }

            this.motionX = Math.cos(f2) * d2;
            this.motionZ = Math.sin(f2) * d2;
            if (this.posY < this.targetY) {
                this.motionY = this.motionY + (1.0 - this.motionY) * 0.015F;
                if (!(flag)) {

                    this.motionY = this.motionY + (-1.0 - this.motionY) * 0.015F;
                }
            } else {

                this.motionY = this.motionY + (-1.0 - this.motionY) * 0.015F;
            }
        }

        label51:
        {
            float f3 = 0.25F;
            if (this.isInWater()) {
                int i = 0;

                while (i < 4) {
                    this.world.spawnParticle(
                            EnumParticleTypes.WATER_BUBBLE,
                            this.posX - this.motionX * f3,
                            this.posY - this.motionY * f3,
                            this.posZ - this.motionZ * f3,
                            this.motionX,
                            this.motionY,
                            this.motionZ,
                            new int[0]);
                    i++;
                    if (!flag) {
                        break;
                    }
                }

                if (flag) {
                    break label51;
                }
            }

            this.world.spawnParticle(
                    EnumParticleTypes.PORTAL,
                    this.posX - this.motionX * f3 + this.rand.nextDouble() * 0.6 - 0.3,
                    this.posY - this.motionY * f3 - 0.5,
                    this.posZ - this.motionZ * f3 + this.rand.nextDouble() * 0.6 - 0.3,
                    this.motionX,
                    this.motionY,
                    this.motionZ,
                    new int[0]);
        }

        if (!this.world.isRemote) {
            this.setPosition(this.posX, this.posY, this.posZ);
            this.despawnTimer++;
            if (this.despawnTimer > 80 && !this.world.isRemote) {
                this.setDead();
                if (this.shatterOrDrop) {
                    this.world.spawnEntityInWorld(
                            new EntityItem(
                                    this.world,
                                    this.posX,
                                    this.posY,
                                    this.posZ,
                                    new ItemStack(Items.ender_eye)));
                    if (flag) {
                        return;
                    }
                }

                this.world.playAuxSFX(2003, new BlockPos(this), 0);
            }
        }
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {}

    public void setVelocity(double motionXValue, double motionYValue, double motionZValue) {
        this.motionX = motionXValue;
        this.motionY = motionYValue;
        this.motionZ = motionZValue;
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(motionXValue * motionXValue + motionZValue * motionZValue);
            this.prevRotationYaw =
                    this.rotationYaw =
                            (float) (Math.atan2(motionXValue, motionZValue) * 180.0 / Math.PI);
            this.prevRotationPitch =
                    this.rotationPitch = (float) (Math.atan2(motionYValue, f) * 180.0 / Math.PI);
        }
    }

    public void moveTowards(BlockPos blockPos) {
        {
            double baSValue = blockPos.getX();
            int baVValue = blockPos.getY();
            boolean flag1 = EntityMinecartEnumMinecartType.d();
            double baTValue = blockPos.getZ();
            double d2 = baSValue - this.posX;
            double d3 = baTValue - this.posZ;
            float f = MathHelper.sqrt(d2 * d2 + d3 * d3);
            boolean flag = flag1;
            if (f > 12.0F) {
                this.baS = this.posX + d2 / f * 12.0;
                this.baT = this.posZ + d3 / f * 12.0;
                this.targetY = this.posY + 8.0;
                if (!(!flag)) {

                    this.baS = baSValue;
                    this.targetY = baVValue;
                    this.baT = baTValue;
                }
            } else {

                this.baS = baSValue;
                this.targetY = baVValue;
                this.baT = baTValue;
            }
        }

        this.despawnTimer = 0;
        this.shatterOrDrop =
                remix.compat.compiler.JvmBooleanSlots.fromInt((this.rand.nextInt(5) > 0 ? 1 : 0));
    }

    protected void entityInit() {}

    public EntityEnderEye(World worldValue, double d0, double d1, double d2) {
        super(worldValue);
        this.despawnTimer = 0;
        this.setSize(0.25F, 0.25F);
        this.setPosition(d0, d1, d2);
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {}

    public boolean isInRangeToRenderDist(double d0) {
        double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        d1 *= 64.0;
        return d0 < d1 * d1;
    }

    public EntityEnderEye(World worldValue) {
        super(worldValue);
        this.setSize(0.25F, 0.25F);
    }

    public boolean canAttackWithItem() {
        return false;
    }
}
