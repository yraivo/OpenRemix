package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public abstract class EntityFireball extends Entity {
    public double accelerationY;
    public double accelerationX;
    public LivingEntity shootingEntity;
    private static String[] packedTextFragments;
    public double accelerationZ;
    private boolean inGround;

    private Block inTile;
    private int xTile = -1;
    private int zTile;
    private int ticksAlive;
    private int yTile = -1;
    private int ticksInAir;

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        this.setBeenAttacked();
        if (damageSource.getEntity() != null) {
            Vec3 vec3 = damageSource.getEntity().getLookVec();
            if (vec3 != null) {
                this.motionX = vec3.x;
                this.motionY = vec3.y;
                this.motionZ = vec3.z;
                this.accelerationX = this.motionX * 0.1;
                this.accelerationY = this.motionY * 0.1;
                this.accelerationZ = this.motionZ * 0.1;
            }

            if (damageSource.getEntity() instanceof LivingEntity) {
                this.shootingEntity = (LivingEntity) damageSource.getEntity();
            }

            return true;
        } else {
            return false;
        }
    }

    protected abstract void onImpact(RayTraceResult rayTraceResult);

    public EntityFireball(World worldValue) {
        super(worldValue);
        this.zTile = -1;
        this.setSize(1.0F, 1.0F);
    }

    protected void entityInit() {}

    public void onUpdate() {
        TileEntity.lz();

        if (!this.world.isRemote
                && (this.shootingEntity != null && this.shootingEntity.isDead
                        || !this.world.isBlockLoaded(new BlockPos(this)))) {
            this.setDead();

            return;
        }

        {
            super.onUpdate();
            this.setFire(1);
            if (this.inGround) {
                if (this.world
                                .getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile))
                                .getBlock()
                        == this.inTile) {
                    this.ticksAlive++;
                    if (this.ticksAlive == 600) {
                        this.setDead();
                    }

                    return;
                }

                this.inGround = false;
                this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
                this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
                this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
                this.ticksAlive = 0;
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

        Entity entity = null;
        List entitiesWithinAABBExcludingEntity =
                this.world.getEntitiesWithinAABBExcludingEntity(
                        this,
                        this.getEntityBoundingBox()
                                .addCoord(this.motionX, this.motionY, this.motionZ)
                                .expand(1.0, 1.0, 1.0));
        double d0 = 0.0;
        int i = 0;

        while (i < entitiesWithinAABBExcludingEntity.size()) {
            Entity entityValue = (Entity) entitiesWithinAABBExcludingEntity.get(i);
            if (entityValue.canBeCollidedWith()
                    && (!entityValue.isEntityEqual(this.shootingEntity) || this.ticksInAir >= 25)) {
                float f = 0.3F;
                BoundingBox boundingBoxValue = entityValue.getEntityBoundingBox().expand(f, f, f);
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

            i++;
        }

        if (entity != null) {
            rayTraceResult = new RayTraceResult(entity);
        }

        if (rayTraceResult != null) {
            this.onImpact(rayTraceResult);
        }

        this.posX = this.posX + this.motionX;
        this.posY = this.posY + this.motionY;
        this.posZ = this.posZ + this.motionZ;
        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw =
                (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) + 90.0F;
        this.rotationPitch = (float) (Math.atan2(f1, this.motionY) * 180.0 / Math.PI) - 90.0F;

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
        float motionFactor = this.getMotionFactor();
        if (this.isInWater()) {
            int j = 0;

            while (j < 4) {
                float f3 = 0.25F;
                this.world.spawnParticle(
                        EnumParticleTypes.WATER_BUBBLE,
                        this.posX - this.motionX * f3,
                        this.posY - this.motionY * f3,
                        this.posZ - this.motionZ * f3,
                        this.motionX,
                        this.motionY,
                        this.motionZ,
                        new int[0]);
                j++;
            }

            motionFactor = 0.8F;
        }

        this.motionX = this.motionX + this.accelerationX;
        this.motionY = this.motionY + this.accelerationY;
        this.motionZ = this.motionZ + this.accelerationZ;
        this.motionX *= motionFactor;
        this.motionY *= motionFactor;
        this.motionZ *= motionFactor;
        this.world.spawnParticle(
                EnumParticleTypes.SMOKE_NORMAL,
                this.posX,
                this.posY + 0.5,
                this.posZ,
                0.0,
                0.0,
                0.0,
                new int[0]);
        this.setPosition(this.posX, this.posY, this.posZ);
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
        compoundTag.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        compoundTag.setTag(
                "direction",
                this.newDoubleNBTList(new double[] {this.motionX, this.motionY, this.motionZ}));
    }

    static {
        net.minecraft.entity.projectile.EntityFireball.packedTextFragments = new String[3];
        net.minecraft.entity.projectile.EntityFireball.packedTextFragments[0] = new String("");
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        int[] aint;
        {
            int[] aint1 = TileEntity.lz();
            this.xTile = compoundTag.getShort("xTile");
            aint = aint1;
            this.yTile = compoundTag.getShort("yTile");
            this.zTile = compoundTag.getShort("zTile");
            if (compoundTag.hasKey("inTile", 8)) {
                this.inTile = Block.getBlockFromName(compoundTag.getString("inTile"));

            } else {

                this.inTile = Block.getBlockById(compoundTag.getByte("inTile") & 255);
            }
        }

        this.inGround =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (compoundTag.getByte("inGround") == 1 ? 1 : 0));
        if (compoundTag.hasKey("direction", 9)) {
            NBTTagList nbtTagList = compoundTag.getTagList("direction", 6);
            this.motionX = nbtTagList.getDoubleAt(0);
            this.motionY = nbtTagList.getDoubleAt(1);
            this.motionZ = nbtTagList.getDoubleAt(2);

            return;
        }

        this.setDead();
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    public EntityFireball(
            World worldValue, double d0, double d1, double d2, double d3, double d4, double d5) {
        super(worldValue);
        this.zTile = -1;
        this.setSize(1.0F, 1.0F);
        this.setLocationAndAngles(d0, d1, d2, this.rotationYaw, this.rotationPitch);
        this.setPosition(d0, d1, d2);
        double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
        this.accelerationX = d3 / d6 * 0.1;
        this.accelerationY = d4 / d6 * 0.1;
        this.accelerationZ = d5 / d6 * 0.1;
    }

    protected float getMotionFactor() {
        return 0.95F;
    }

    public EntityFireball(
            World worldValue, LivingEntity biBValue, double d0, double d1, double d2) {
        super(worldValue);
        this.zTile = -1;
        this.shootingEntity = biBValue;
        this.setSize(1.0F, 1.0F);
        this.setLocationAndAngles(
                biBValue.posX,
                biBValue.posY,
                biBValue.posZ,
                biBValue.rotationYaw,
                biBValue.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = this.motionY = this.motionZ = 0.0;
        d0 += this.rand.nextGaussian() * 0.4;
        d1 += this.rand.nextGaussian() * 0.4;
        d2 += this.rand.nextGaussian() * 0.4;
        double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        this.accelerationX = d0 / d3 * 0.1;
        this.accelerationY = d1 / d3 * 0.1;
        this.accelerationZ = d2 / d3 * 0.1;
    }

    public int getBrightnessForRender(float f) {
        return 15728880;
    }

    public float getCollisionBorderSize() {
        return 1.0F;
    }

    public boolean isInRangeToRenderDist(double d0) {
        double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        d1 *= 64.0;
        return d0 < d1 * d1;
    }

    public float getBrightness(float f) {
        return 1.0F;
    }
}
