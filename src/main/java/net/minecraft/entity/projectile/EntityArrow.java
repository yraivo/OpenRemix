package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
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

public class EntityArrow extends Entity implements IProjectile {
    private boolean inGround;
    private Block inTile;
    private int xTile = -1;
    private int zTile;
    private int inData;
    private int ticksInAir;
    private int ticksInGround;

    public int canBePickedUp;
    public int arrowShake;

    private int yTile = -1;
    private int knockbackStrength;
    private double damage;
    public Entity shootingEntity;
    private static String[] packedTextFragments;

    public EntityArrow(
            World worldValue, LivingEntity bizValue, LivingEntity livingEntity, float f, float f1) {
        super(worldValue);
        this.zTile = -1;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = bizValue;
        if (bizValue instanceof PlayerEntity) {
            this.canBePickedUp = 1;
        }

        this.posY = bizValue.posY + bizValue.getEyeHeight() - 0.1F;
        double deltaPosX = livingEntity.posX - bizValue.posX;
        double d1 =
                livingEntity.getEntityBoundingBox().minY + livingEntity.height / 3.0F - this.posY;
        double deltaPosZ = livingEntity.posZ - bizValue.posZ;
        double d3 = MathHelper.sqrt(deltaPosX * deltaPosX + deltaPosZ * deltaPosZ);
        if (d3 >= 1.0E-7) {
            float f2 = (float) (Math.atan2(deltaPosZ, deltaPosX) * 180.0 / Math.PI) - 90.0F;
            float f3 = (float) (-(Math.atan2(d1, d3) * 180.0 / Math.PI));
            double d4 = deltaPosX / d3;
            double d5 = deltaPosZ / d3;
            this.setLocationAndAngles(bizValue.posX + d4, this.posY, bizValue.posZ + d5, f2, f3);
            float f4 = (float) (d3 * 0.2F);
            this.setThrowableHeading(deltaPosX, d1 + f4, deltaPosZ, f, f1);
        }
    }

    public void setThrowableHeading(
            double motionXValue, double motionYValue, double motionZValue, float f, float f1) {
        TileEntity.lz();
        float f2 =
                MathHelper.sqrt(
                        motionXValue * motionXValue
                                + motionYValue * motionYValue
                                + motionZValue * motionZValue);
        motionXValue /= f2;
        motionYValue /= f2;
        motionZValue /= f2;
        motionXValue +=
                this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.0075F * f1;
        motionYValue +=
                this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.0075F * f1;
        motionZValue +=
                this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.0075F * f1;
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
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            TileEntity.b(new int[4]);
        }
    }

    public EntityArrow(World worldValue, LivingEntity bizValue, float f) {
        super(worldValue);
        this.zTile = -1;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.shootingEntity = bizValue;
        if (bizValue instanceof PlayerEntity) {
            this.canBePickedUp = 1;
        }

        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(
                bizValue.posX,
                bizValue.posY + bizValue.getEyeHeight(),
                bizValue.posZ,
                bizValue.rotationYaw,
                bizValue.rotationPitch);
        this.posX = this.posX - MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.posY -= 0.1F;
        this.posZ = this.posZ - MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX =
                -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
                        * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionZ =
                MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
                        * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, f * 1.5F, 1.0F);
    }

    public void onCollideWithPlayer(PlayerEntity playerEntity) {
        if (!this.world.isRemote && this.inGround && this.arrowShake <= 0) {
            int i =
                    this.canBePickedUp != 1
                                    && (this.canBePickedUp != 2
                                            || !playerEntity.capabilities.isCreativeMode)
                            ? 0
                            : 1;
            if (this.canBePickedUp == 1
                    && !playerEntity.inventory.addItemStackToInventory(
                            new ItemStack(Items.arrow, 1))) {
                i = 0;
            }

            if (i != 0) {
                this.playSound(
                        "random.pop",
                        0.2F,
                        ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                playerEntity.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    public void setIsCritical(boolean flag) {
        TileEntity.lz();

        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (flag) {
            this.dataWatcher.updateObject(16, (byte) (b0 | 1));

            return;
        }

        this.dataWatcher.updateObject(16, (byte) (b0 & -2));
    }

    public void setPositionAndRotation2(
            double d0, double d1, double d2, float f, float f1, int i, boolean flag) {
        this.setPosition(d0, d1, d2);
        this.setRotation(f, f1);
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
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(
                    this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }

    public EntityArrow(World worldValue, double d0, double d1, double d2) {
        super(worldValue);
        this.zTile = -1;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5F, 0.5F);
        this.setPosition(d0, d1, d2);
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        int[] aint;
        {
            this.xTile = compoundTag.getShort("xTile");
            int[] aint1 = TileEntity.lz();
            this.yTile = compoundTag.getShort("yTile");
            aint = aint1;
            this.zTile = compoundTag.getShort("zTile");
            this.ticksInGround = compoundTag.getShort("life");
            if (compoundTag.hasKey("inTile", 8)) {
                this.inTile = Block.getBlockFromName(compoundTag.getString("inTile"));

            } else {

                this.inTile = Block.getBlockById(compoundTag.getByte("inTile") & 255);
            }
        }

        this.inData = compoundTag.getByte("inData") & 255;
        this.arrowShake = compoundTag.getByte("shake") & 255;
        this.inGround =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (compoundTag.getByte("inGround") == 1 ? 1 : 0));
        if (compoundTag.hasKey("damage", 99)) {
            this.damage = compoundTag.getDouble("damage");
        }

        if (compoundTag.hasKey("pickup", 99)) {
            this.canBePickedUp = compoundTag.getByte("pickup");

            return;
        }

        if (compoundTag.hasKey("player", 99)) {
            this.canBePickedUp = compoundTag.getBoolean("player") ? 1 : 0;
        }
    }

    public void onUpdate() {
        TileEntity.lz();

        super.onUpdate();

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw =
                    this.rotationYaw =
                            (float) (Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
            this.prevRotationPitch =
                    this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180.0 / Math.PI);
        }

        BlockPos blockPos = new BlockPos(this.xTile, this.yTile, this.zTile);
        BlockState blockState = this.world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block.getMaterial() != Material.AIR) {
            block.setBlockBoundsBasedOnState(this.world, blockPos);
            BoundingBox boundingBoxValue =
                    block.getCollisionBoundingBox(this.world, blockPos, blockState);
            if (boundingBoxValue != null
                    && boundingBoxValue.isVecInside(new Vec3(this.posX, this.posY, this.posZ))) {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0) {
            this.arrowShake--;
        }

        if (this.inGround) {
            {
                int j = block.getMetaFromState(blockState);
                if (block == this.inTile && j == this.inData) {
                    this.ticksInGround++;
                    if (!(this.ticksInGround < 1200)) {

                        this.setDead();
                    }
                } else {

                    this.inGround = false;
                    this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
                    this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
                    this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
                    this.ticksInGround = 0;
                    this.ticksInAir = 0;
                }
            }

            return;
        }

        this.ticksInAir++;
        Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
        Vec3 vec3Value =
                new Vec3(
                        this.posX + this.motionX,
                        this.posY + this.motionY,
                        this.posZ + this.motionZ);
        RayTraceResult rayTraceResult =
                this.world.rayTraceBlocks(vec3, vec3Value, false, true, false);
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
                    && (entityValue != this.shootingEntity || this.ticksInAir >= 5)) {
                float f1 = 0.3F;
                BoundingBox boundingBoxValue2 =
                        entityValue.getEntityBoundingBox().expand(f1, f1, f1);
                RayTraceResult rayTraceResultValue =
                        boundingBoxValue2.calculateIntercept(vec3, vec3Value);
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

        if (rayTraceResult != null
                && rayTraceResult.entityHit != null
                && rayTraceResult.entityHit instanceof PlayerEntity) {
            PlayerEntity entityHit = (PlayerEntity) rayTraceResult.entityHit;
            if (entityHit.capabilities.disableDamage
                    || this.shootingEntity instanceof PlayerEntity
                            && !((PlayerEntity) this.shootingEntity).canAttackPlayer(entityHit)) {
                rayTraceResult = null;
            }
        }

        if (rayTraceResult != null) {
            if (rayTraceResult.entityHit != null) {
                float f3 =
                        MathHelper.sqrt(
                                this.motionX * this.motionX
                                        + this.motionY * this.motionY
                                        + this.motionZ * this.motionZ);
                int k = MathHelper.ceil(f3 * this.damage);
                if (this.getIsCritical()) {
                    k += this.rand.nextInt(k / 2 + 2);
                }

                DamageSource damageSource;
                {
                    if (this.shootingEntity == null) {
                        damageSource = DamageSource.causeArrowDamage(this, this);

                    } else {

                        damageSource = DamageSource.causeArrowDamage(this, this.shootingEntity);
                    }
                }

                if (this.isBurning() && !(rayTraceResult.entityHit instanceof EntityEnderman)) {
                    rayTraceResult.entityHit.setFire(5);
                }

                {
                    if (rayTraceResult.entityHit.attackEntityFrom(damageSource, k)) {
                        if (rayTraceResult.entityHit instanceof LivingEntity) {
                            LivingEntity entityHitValue = (LivingEntity) rayTraceResult.entityHit;
                            if (!this.world.isRemote) {
                                entityHitValue.setArrowCountInEntity(
                                        entityHitValue.getArrowCountInEntity() + 1);
                            }

                            if (this.knockbackStrength > 0) {
                                float f7 =
                                        MathHelper.sqrt(
                                                this.motionX * this.motionX
                                                        + this.motionZ * this.motionZ);
                                if (f7 > 0.0F) {
                                    rayTraceResult.entityHit.addVelocity(
                                            this.motionX * this.knockbackStrength * 0.6F / f7,
                                            0.1,
                                            this.motionZ * this.knockbackStrength * 0.6F / f7);
                                }
                            }

                            if (this.shootingEntity instanceof LivingEntity) {
                                EnchantmentHelper.applyThornEnchantments(
                                        entityHitValue, this.shootingEntity);
                                EnchantmentHelper.applyArthropodEnchantments(
                                        (LivingEntity) this.shootingEntity, entityHitValue);
                            }

                            if (this.shootingEntity != null
                                    && rayTraceResult.entityHit != this.shootingEntity
                                    && rayTraceResult.entityHit instanceof PlayerEntity
                                    && this.shootingEntity instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) this.shootingEntity)
                                        .playerNetServerHandler.sendPacket(
                                                new S2BPacketChangeGameState(6, 0.0F));
                            }
                        }

                        this.playSound(
                                "random.bowhit",
                                1.0F,
                                1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                        if (!(rayTraceResult.entityHit instanceof EntityEnderman)) {

                            this.setDead();
                        }
                    } else {

                        this.motionX *= -0.1F;
                        this.motionY *= -0.1F;
                        this.motionZ *= -0.1F;
                        this.rotationYaw += 180.0F;
                        this.prevRotationYaw += 180.0F;
                        this.ticksInAir = 0;
                    }
                }

            } else {

                BlockPos blockPosValue = rayTraceResult.getBlockPos();
                this.xTile = blockPosValue.getX();
                this.yTile = blockPosValue.getY();
                this.zTile = blockPosValue.getZ();
                blockState = this.world.getBlockState(blockPosValue);
                this.inTile = blockState.getBlock();
                this.inData = this.inTile.getMetaFromState(blockState);
                this.motionX = (float) (rayTraceResult.hitVec.x - this.posX);
                this.motionY = (float) (rayTraceResult.hitVec.y - this.posY);
                this.motionZ = (float) (rayTraceResult.hitVec.z - this.posZ);
                float f5 =
                        MathHelper.sqrt(
                                this.motionX * this.motionX
                                        + this.motionY * this.motionY
                                        + this.motionZ * this.motionZ);
                this.posX = this.posX - this.motionX / f5 * 0.05F;
                this.posY = this.posY - this.motionY / f5 * 0.05F;
                this.posZ = this.posZ - this.motionZ / f5 * 0.05F;
                this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                this.inGround = true;
                this.arrowShake = 7;
                this.setIsCritical(false);
                if (this.inTile.getMaterial() != Material.AIR) {
                    this.inTile.onEntityCollidedWithBlock(
                            this.world, blockPosValue, blockState, this);
                }
            }
        }

        if (this.getIsCritical()) {
            i = 0;

            while (i < 4) {
                this.world.spawnParticle(
                        EnumParticleTypes.CRIT,
                        this.posX + this.motionX * i / 4.0,
                        this.posY + this.motionY * i / 4.0,
                        this.posZ + this.motionZ * i / 4.0,
                        -this.motionX,
                        -this.motionY + 0.2,
                        -this.motionZ,
                        new int[0]);
                i++;
            }
        }

        this.posX = this.posX + this.motionX;
        this.posY = this.posY + this.motionY;
        this.posZ = this.posZ + this.motionZ;
        float f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
        this.rotationPitch = (float) (Math.atan2(this.motionY, f4) * 180.0 / Math.PI);

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
        float f6 = 0.99F;
        float f2 = 0.05F;
        if (this.isInWater()) {
            int l = 0;

            while (l < 4) {
                float f8 = 0.25F;
                this.world.spawnParticle(
                        EnumParticleTypes.WATER_BUBBLE,
                        this.posX - this.motionX * f8,
                        this.posY - this.motionY * f8,
                        this.posZ - this.motionZ * f8,
                        this.motionX,
                        this.motionY,
                        this.motionZ,
                        new int[0]);
                l++;
            }

            f6 = 0.6F;
        }

        if (this.isWet()) {
            this.extinguish();
        }

        this.motionX *= f6;
        this.motionY *= f6;
        this.motionZ *= f6;
        this.motionY -= f2;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.doBlockCollisions();
    }

    public double getDamage() {
        return this.damage;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public EntityArrow(World worldValue) {
        super(worldValue);
        this.zTile = -1;
        this.damage = 2.0;
        this.renderDistanceWeight = 10.0;
        this.setSize(0.5F, 0.5F);
    }

    protected void entityInit() {
        this.dataWatcher.addObject(16, (byte) 0);
    }

    public void setDamage(double bbfValue) {
        this.damage = bbfValue;
    }

    public boolean getIsCritical() {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        return (b0 & 1) != 0;
    }

    public void setKnockbackStrength(int birValue) {
        this.knockbackStrength = birValue;
    }

    static {
        net.minecraft.entity.projectile.EntityArrow.packedTextFragments = new String[3];
        net.minecraft.entity.projectile.EntityArrow.packedTextFragments[0] = new String("");
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        compoundTag.setShort("xTile", (short) this.xTile);
        compoundTag.setShort("yTile", (short) this.yTile);
        compoundTag.setShort("zTile", (short) this.zTile);
        compoundTag.setShort("life", (short) this.ticksInGround);
        ResourceLocation nameForObject =
                (ResourceLocation) Block.blockRegistry.getNameForObject(this.inTile);
        compoundTag.setString(
                "inTile",
                nameForObject == null ? packedTextFragments[0] : nameForObject.toString());
        compoundTag.setByte("inData", (byte) this.inData);
        compoundTag.setByte("shake", (byte) this.arrowShake);
        compoundTag.setByte("inGround", (byte) (this.inGround ? 1 : 0));
        compoundTag.setByte("pickup", (byte) this.canBePickedUp);
        compoundTag.setDouble("damage", this.damage);
    }

    public boolean canAttackWithItem() {
        return false;
    }
}
