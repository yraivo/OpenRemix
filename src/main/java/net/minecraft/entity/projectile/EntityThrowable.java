package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public abstract class EntityThrowable extends Entity implements IProjectile {
    private int ticksInAir;
    private static String[] packedTextFragments;

    private String throwerName;
    private Block inTile;
    private LivingEntity thrower;
    private int yTile;
    protected boolean inGround;

    private int ticksInGround;
    private int xTile = -1;
    public int throwableShake;
    private int zTile;

    public void setThrowableHeading(
            double motionXValue, double motionYValue, double motionZValue, float f, float f1) {
        float f2 =
                MathHelper.sqrt(
                        motionXValue * motionXValue
                                + motionYValue * motionYValue
                                + motionZValue * motionZValue);
        motionXValue /= f2;
        motionYValue /= f2;
        motionZValue /= f2;
        motionXValue += this.rand.nextGaussian() * 0.0075F * f1;
        motionYValue += this.rand.nextGaussian() * 0.0075F * f1;
        motionZValue += this.rand.nextGaussian() * 0.0075F * f1;
        motionXValue *= f;
        motionYValue *= f;
        motionZValue *= f;
        this.motionX = motionXValue;
        this.motionY = motionYValue;
        this.motionZ = motionZValue;
        float f3 = MathHelper.sqrt(motionXValue * motionXValue + motionZValue * motionZValue);
        this.prevRotationYaw =
                this.rotationYaw =
                        (float) (Math.atan2(motionXValue, motionZValue) * 180.0 / Math.PI);
        this.prevRotationPitch =
                this.rotationPitch = (float) (Math.atan2(motionYValue, f3) * 180.0 / Math.PI);
        this.ticksInGround = 0;
    }

    static {
        net.minecraft.entity.projectile.EntityThrowable.packedTextFragments = new String[4];
        net.minecraft.entity.projectile.EntityThrowable.packedTextFragments[0] = new String("");
        net.minecraft.entity.projectile.EntityThrowable.packedTextFragments[1] = new String("");
    }

    public EntityThrowable(World worldValue) {
        super(worldValue);
        this.yTile = -1;
        this.zTile = -1;
        this.setSize(0.25F, 0.25F);
    }

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

    protected abstract void onImpact(RayTraceResult rayTraceResult);

    protected float getGravityVelocity() {
        return 0.03F;
    }

    protected float getVelocity() {
        return 1.5F;
    }

    public EntityThrowable(World worldValue, double d0, double d1, double d2) {
        super(worldValue);
        this.yTile = -1;
        this.zTile = -1;
        this.ticksInGround = 0;
        this.setSize(0.25F, 0.25F);
        this.setPosition(d0, d1, d2);
    }

    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        TileEntity.lz();

        this.lastTickPosZ = this.posZ;
        super.onUpdate();

        if (this.throwableShake > 0) {
            this.throwableShake--;
        }

        {
            if (this.inGround) {
                if (this.world
                                .getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile))
                                .getBlock()
                        == this.inTile) {
                    this.ticksInGround++;
                    if (this.ticksInGround == 1200) {
                        this.setDead();
                    }

                    return;
                }

                this.inGround = false;
                this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
                this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
                this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;

            } else {

                this.ticksInAir++;
            }
        }

        Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
        Vec3 vec3Value =
                new Vec3(
                        this.posX + this.motionX,
                        this.posY + this.motionY,
                        this.posZ + this.motionZ);
        RayTraceResult rayTraceResult = this.world.rayTraceBlocks(vec3, vec3Value);
        vec3 = new Vec3(this.posX, this.posY, this.posZ);
        vec3Value =
                new Vec3(
                        this.posX + this.motionX,
                        this.posY + this.motionY,
                        this.posZ + this.motionZ);
        if (rayTraceResult != null) {
            vec3Value =
                    new Vec3(
                            rayTraceResult.hitVec.x,
                            rayTraceResult.hitVec.y,
                            rayTraceResult.hitVec.z);
        }

        if (!this.world.isRemote) {
            Entity entity = null;
            List entitiesWithinAABBExcludingEntity =
                    this.world.getEntitiesWithinAABBExcludingEntity(
                            this,
                            this.getEntityBoundingBox()
                                    .addCoord(this.motionX, this.motionY, this.motionZ)
                                    .expand(1.0, 1.0, 1.0));
            double d0 = 0.0;
            LivingEntity livingEntity = this.getThrower();
            int j = 0;

            while (j < entitiesWithinAABBExcludingEntity.size()) {
                Entity entityValue = (Entity) entitiesWithinAABBExcludingEntity.get(j);
                if (entityValue.canBeCollidedWith()
                        && (entityValue != livingEntity || this.ticksInAir >= 5)) {
                    float f = 0.3F;
                    BoundingBox boundingBoxValue =
                            entityValue.getEntityBoundingBox().expand(f, f, f);
                    RayTraceResult rayTraceResultValue =
                            boundingBoxValue.calculateIntercept(vec3, vec3Value);
                    if (rayTraceResultValue != null) {
                        double d1 = vec3.distanceTo(rayTraceResultValue.hitVec);
                        if (d1 < d0 || d0 == 0.0) {
                            entity = entityValue;
                            d0 = d1;
                        }
                    }
                }

                j++;
            }

            if (entity != null) {
                rayTraceResult = new RayTraceResult(entity);
            }
        }

        if (rayTraceResult != null) {
            if (rayTraceResult.typeOfHit == RayHitType.BLOCK
                    && this.world.getBlockState(rayTraceResult.getBlockPos()).getBlock()
                            == Blocks.portal) {
                this.setInPortal();

            } else {

                this.onImpact(rayTraceResult);
            }
        }

        this.posX = this.posX + this.motionX;
        this.posY = this.posY + this.motionY;
        this.posZ = this.posZ + this.motionZ;
        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
        this.rotationPitch = (float) (Math.atan2(this.motionY, f1) * 180.0 / Math.PI);

        while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch =
                this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float f2 = 0.99F;
        float f3 = this.getGravityVelocity();
        if (this.isInWater()) {
            int i = 0;

            while (i < 4) {
                float f4 = 0.25F;
                this.world.spawnParticle(
                        EnumParticleTypes.WATER_BUBBLE,
                        this.posX - this.motionX * f4,
                        this.posY - this.motionY * f4,
                        this.posZ - this.motionZ * f4,
                        this.motionX,
                        this.motionY,
                        this.motionZ,
                        new int[0]);
                i++;
            }

            f2 = 0.8F;
        }

        this.motionX *= f2;
        this.motionY *= f2;
        this.motionZ *= f2;
        this.motionY -= f3;
        this.setPosition(this.posX, this.posY, this.posZ);
    }

    protected float getInaccuracy() {
        return 0.0F;
    }

    public boolean isInRangeToRenderDist(double d0) {
        double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        d1 *= 64.0;
        return d0 < d1 * d1;
    }

    public LivingEntity getThrower() {
        if (this.thrower == null && this.throwerName != null && this.throwerName.length() > 0) {
            this.thrower = this.world.getPlayerEntityByName(this.throwerName);
        }

        return this.thrower;
    }

    protected void entityInit() {}

    public void readEntityFromNBT(CompoundTag compoundTag) {
        {
            TileEntity.lz();

            this.xTile = compoundTag.getShort("xTile");
            this.yTile = compoundTag.getShort("yTile");

            this.zTile = compoundTag.getShort("zTile");
            if (compoundTag.hasKey("inTile", 8)) {
                this.inTile = Block.getBlockFromName(compoundTag.getString("inTile"));

            } else {

                this.inTile = Block.getBlockById(compoundTag.getByte("inTile") & 255);
            }
        }

        this.throwableShake = compoundTag.getByte("shake") & 255;
        this.inGround =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (compoundTag.getByte("inGround") == 1 ? 1 : 0));
        this.throwerName = compoundTag.getString("ownerName");
        if (this.throwerName != null && this.throwerName.length() == 0) {
            this.throwerName = null;
        }
    }

    public EntityThrowable(World worldValue, LivingEntity biVValue) {
        super(worldValue);
        this.yTile = -1;
        this.zTile = -1;
        this.thrower = biVValue;
        this.setSize(0.25F, 0.25F);
        this.setLocationAndAngles(
                biVValue.posX,
                biVValue.posY + biVValue.getEyeHeight(),
                biVValue.posZ,
                biVValue.rotationYaw,
                biVValue.rotationPitch);
        this.posX = this.posX - MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.posY -= 0.1F;
        this.posZ = this.posZ - MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        float f = 0.4F;
        this.motionX =
                -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
                        * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI)
                        * f;
        this.motionZ =
                MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
                        * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI)
                        * f;
        this.motionY =
                -MathHelper.sin(
                                (this.rotationPitch + this.getInaccuracy())
                                        / 180.0F
                                        * (float) Math.PI)
                        * f;
        this.setThrowableHeading(
                this.motionX, this.motionY, this.motionZ, this.getVelocity(), 1.0F);
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        compoundTag.setShort("xTile", (short) this.xTile);
        compoundTag.setShort("yTile", (short) this.yTile);
        compoundTag.setShort("zTile", (short) this.zTile);
        ResourceLocation nameForObject =
                (ResourceLocation) Block.blockRegistry.getNameForObject(this.inTile);
        compoundTag.setString(
                "inTile",
                nameForObject == null ? packedTextFragments[0] : nameForObject.toString());
        compoundTag.setByte("shake", (byte) this.throwableShake);
        compoundTag.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        if ((this.throwerName == null || this.throwerName.length() == 0)
                && this.thrower instanceof PlayerEntity) {
            this.throwerName = this.thrower.getName();
        }

        compoundTag.setString(
                "ownerName", this.throwerName == null ? packedTextFragments[1] : this.throwerName);
    }
}
