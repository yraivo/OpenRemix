package net.minecraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntityBoat extends Entity {
    private double beh;
    private double velocityY;
    private double bbg;
    private int boatPosRotationIncrements;
    private double speedMultiplier;
    private double bbm;
    private boolean isBoatEmpty = true;
    private double bbk;
    private double velocityX;
    private double velocityZ;
    private double bbo;

    public EntityBoat(
            World worldValue, double prevPosXValue, double prevPosYValue, double prevPosZValue) {
        this(worldValue);
        this.setPosition(prevPosXValue, prevPosYValue, prevPosZValue);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = prevPosXValue;
        this.prevPosY = prevPosYValue;
        this.prevPosZ = prevPosZValue;
    }

    public EntityBoat(World worldValue) {
        super(worldValue);
        this.speedMultiplier = 0.07;
        this.preventEntitySpawning = true;
        this.setSize(1.5F, 0.6F);
    }

    public void performHurtAnimation() {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0F);
    }

    public void setDamageTaken(float p171_0) {
        this.dataWatcher.updateObject(19, p171_0);
    }

    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            double d0 = java.lang.Math.cos(this.rotationYaw * java.lang.Math.PI / 180.0) * 0.4;
            double d1 = java.lang.Math.sin(this.rotationYaw * java.lang.Math.PI / 180.0) * 0.4;
            this.riddenByEntity.setPosition(
                    this.posX + d0,
                    this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(),
                    this.posZ + d1);
        }
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void updateFallState(double d0, boolean flag, Block block, BlockPos blockPos) {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        if (flag) {
            if (!(this.fallDistance > 3.0F)) {
                return;
            }

            this.fall(this.fallDistance, 1.0F);
            if (!this.world.isRemote && !this.isDead) {
                this.setDead();
                int i = 0;

                while (i < 3) {
                    this.dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
                    i++;
                    if (!flag1) {
                        break;
                    }
                }

                i = 0;

                while (i < 2) {
                    this.dropItemWithOffset(Items.stick, 1, 0.0F);
                    i++;
                    if (!flag1) {
                        break;
                    }
                }
            }

            this.fallDistance = 0.0F;
            if (flag1) {
                return;
            }
        }

        if (this.world.getBlockState(new BlockPos(this).offsetDown()).getBlock().getMaterial()
                        != Material.water
                && d0 < 0.0) {
            this.fallDistance = (float) (this.fallDistance - d0);
        }
    }

    protected void readEntityFromNBT(CompoundTag compoundTag) {}

    public float getDamageTaken() {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        if (!this.world.isRemote && !this.isDead) {
            if (this.riddenByEntity != null
                    && this.riddenByEntity == damageSource.getEntity()
                    && damageSource instanceof EntityDamageSourceIndirect) {
                return false;
            }

            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + f * 10.0F);
            this.setBeenAttacked();
            int i =
                    damageSource.getEntity() instanceof PlayerEntity
                                    && ((PlayerEntity) damageSource.getEntity())
                                            .capabilities
                                            .isCreativeMode
                            ? 1
                            : 0;
            if (i != 0 || this.getDamageTaken() > 40.0F) {
                if (this.riddenByEntity != null) {
                    this.riddenByEntity.mountEntity(this);
                }

                if (i == 0) {
                    this.dropItemWithOffset(Items.boat, 1, 0.0F);
                }

                this.setDead();
            }

            return true;
        } else {
            return true;
        }
    }

    public void onUpdate() {
        boolean flag1 = EntityMinecartEnumMinecartType.d();
        super.onUpdate();
        boolean flag = flag1;
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }

        if (this.getDamageTaken() > 0.0F) {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        byte b0 = 5;
        double d0 = 0.0;
        int i = 0;

        while (i < b0) {
            double d1 =
                    this.getEntityBoundingBox().minY
                            + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY)
                                    * (i + 0)
                                    / b0
                            - 0.125;
            double d3 =
                    this.getEntityBoundingBox().minY
                            + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY)
                                    * (i + 1)
                                    / b0
                            - 0.125;
            BoundingBox boundingBoxValue =
                    new BoundingBox(
                            this.getEntityBoundingBox().minX,
                            d1,
                            this.getEntityBoundingBox().minZ,
                            this.getEntityBoundingBox().maxX,
                            d3,
                            this.getEntityBoundingBox().maxZ);
            if (this.world.isAABBInMaterial(boundingBoxValue, Material.water)) {
                d0 += 1.0 / b0;
            }

            i++;
            if (flag) {
                break;
            }
        }

        double d9 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (d9 > 0.2975) {
            double d2 = Math.cos(this.rotationYaw * Math.PI / 180.0);
            double d4 = Math.sin(this.rotationYaw * Math.PI / 180.0);
            int j = 0;

            while (j < 1.0 + d9 * 60.0) {
                label197:
                {
                    double d5 = this.rand.nextFloat() * 2.0F - 1.0F;
                    double d6 = (this.rand.nextInt(2) * 2 - 1) * 0.7;
                    if (this.rand.nextBoolean()) {
                        double d7 = this.posX - d2 * d5 * 0.8 + d4 * d6;
                        double d8 = this.posZ - d4 * d5 * 0.8 - d2 * d6;
                        this.world.spawnParticle(
                                EnumParticleTypes.WATER_SPLASH,
                                d7,
                                this.posY - 0.125,
                                d8,
                                this.motionX,
                                this.motionY,
                                this.motionZ,
                                new int[0]);
                        if (!flag) {
                            break label197;
                        }
                    }

                    double d23 = this.posX + d2 + d4 * d5 * 0.7;
                    double d25 = this.posZ + d4 - d2 * d5 * 0.7;
                    this.world.spawnParticle(
                            EnumParticleTypes.WATER_SPLASH,
                            d23,
                            this.posY - 0.125,
                            d25,
                            this.motionX,
                            this.motionY,
                            this.motionZ,
                            new int[0]);
                }

                j++;
                if (flag) {
                    break;
                }
            }
        }

        if (this.world.isRemote && this.isBoatEmpty) {
            if (this.boatPosRotationIncrements > 0) {
                double d10 = this.posX + (this.beh - this.posX) / this.boatPosRotationIncrements;
                double d14 = this.posY + (this.bbk - this.posY) / this.boatPosRotationIncrements;
                double d18 = this.posZ + (this.bbg - this.posZ) / this.boatPosRotationIncrements;
                double d21 = MathHelper.wrapAngleTo180(this.bbm - this.rotationYaw);
                this.rotationYaw =
                        (float) (this.rotationYaw + d21 / this.boatPosRotationIncrements);
                this.rotationPitch =
                        (float)
                                (this.rotationPitch
                                        + (this.bbo - this.rotationPitch)
                                                / this.boatPosRotationIncrements);
                this.boatPosRotationIncrements--;
                this.setPosition(d10, d14, d18);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                if (!flag) {
                    return;
                }
            }

            double d11 = this.posX + this.motionX;
            double d15 = this.posY + this.motionY;
            double d19 = this.posZ + this.motionZ;
            this.setPosition(d11, d15, d19);
            if (this.onGround) {
                this.motionX *= 0.5;
                this.motionY *= 0.5;
                this.motionZ *= 0.5;
            }

            this.motionX *= 0.99F;
            this.motionY *= 0.95F;
            this.motionZ *= 0.99F;
            if (!flag) {
                return;
            }
        }

        {
            if (d0 < 1.0) {
                double d12 = d0 * 2.0 - 1.0;
                this.motionY += 0.04F * d12;
                if (!(!flag)) {

                    if (this.motionY < 0.0) {
                        this.motionY /= 2.0;
                    }

                    this.motionY += 0.007F;
                }
            } else {

                if (this.motionY < 0.0) {
                    this.motionY /= 2.0;
                }

                this.motionY += 0.007F;
            }
        }

        if (this.riddenByEntity instanceof LivingEntity) {
            LivingEntity riddenByEntityValue = (LivingEntity) this.riddenByEntity;
            float f = this.riddenByEntity.rotationYaw + -riddenByEntityValue.moveStrafing * 90.0F;
            this.motionX =
                    this.motionX
                            + -Math.sin(f * (float) Math.PI / 180.0F)
                                    * this.speedMultiplier
                                    * riddenByEntityValue.moveForward
                                    * 0.05F;
            this.motionZ =
                    this.motionZ
                            + Math.cos(f * (float) Math.PI / 180.0F)
                                    * this.speedMultiplier
                                    * riddenByEntityValue.moveForward
                                    * 0.05F;
        }

        double d13 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        if (d13 > 0.35) {
            double d16 = 0.35 / d13;
            this.motionX *= d16;
            this.motionZ *= d16;
            d13 = 0.35;
        }

        label170:
        {
            if (d13 > d9 && this.speedMultiplier < 0.35) {
                this.speedMultiplier = this.speedMultiplier + (0.35 - this.speedMultiplier) / 35.0;
                if (!(this.speedMultiplier > 0.35)) {
                    break label170;
                }

                this.speedMultiplier = 0.35;
                if (!flag) {
                    break label170;
                }
            }

            this.speedMultiplier = this.speedMultiplier - (this.speedMultiplier - 0.07) / 35.0;
            if (this.speedMultiplier < 0.07) {
                this.speedMultiplier = 0.07;
            }
        }

        int i1 = 0;

        while (i1 < 4) {
            int j1 = MathHelper.floor(this.posX + (i1 % 2 - 0.5) * 0.8);
            int l = MathHelper.floor(this.posZ + (i1 / 2 - 0.5) * 0.8);
            int k1 = 0;

            while (k1 < 2) {
                {
                    int k = MathHelper.floor(this.posY) + k1;
                    BlockPos blockPos = new BlockPos(j1, k, l);
                    Block block = this.world.getBlockState(blockPos).getBlock();
                    if (block == Blocks.snow_layer) {
                        this.world.setBlockToAir(blockPos);
                        this.collidedHorizontally = false;
                        if (!(!flag)) {

                            if (block == Blocks.waterlily) {
                                this.world.destroyBlock(blockPos, true);
                                this.collidedHorizontally = false;
                            }
                        }
                    } else {

                        if (block == Blocks.waterlily) {
                            this.world.destroyBlock(blockPos, true);
                            this.collidedHorizontally = false;
                        }
                    }
                }

                k1++;
                if (flag) {
                    break;
                }
            }

            i1++;
            if (flag) {
                break;
            }
        }

        if (this.onGround) {
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        }

        label141:
        {
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            if (this.collidedHorizontally && d9 > 0.2) {
                if (this.world.isRemote || this.isDead) {
                    break label141;
                }

                this.setDead();
                i1 = 0;

                while (i1 < 3) {
                    this.dropItemWithOffset(Item.getItemFromBlock(Blocks.planks), 1, 0.0F);
                    i1++;
                    if (flag) {
                        break;
                    }
                }

                i1 = 0;

                do {
                    if (i1 >= 2) {
                        break label141;
                    }

                    this.dropItemWithOffset(Items.stick, 1, 0.0F);
                    i1++;
                } while (!flag);
            }

            this.motionX *= 0.99F;
            this.motionY *= 0.95F;
            this.motionZ *= 0.99F;
        }

        this.rotationPitch = 0.0F;
        double rotationYawValue = this.rotationYaw;
        double d20 = this.prevPosX - this.posX;
        double d22 = this.prevPosZ - this.posZ;
        if (d20 * d20 + d22 * d22 > 0.001) {
            rotationYawValue = (float) (Math.atan2(d22, d20) * 180.0 / Math.PI);
        }

        double d24 = MathHelper.wrapAngleTo180(rotationYawValue - this.rotationYaw);
        if (d24 > 20.0) {
            d24 = 20.0;
        }

        if (d24 < -20.0) {
            d24 = -20.0;
        }

        this.rotationYaw = (float) (this.rotationYaw + d24);
        this.setRotation(this.rotationYaw, this.rotationPitch);
        if (!this.world.isRemote) {
            List entitiesWithinAABBExcludingEntity =
                    this.world.getEntitiesWithinAABBExcludingEntity(
                            this, this.getEntityBoundingBox().expand(0.2F, 0.0, 0.2F));
            if (entitiesWithinAABBExcludingEntity != null
                    && !entitiesWithinAABBExcludingEntity.isEmpty()) {
                int l1 = 0;

                while (l1 < entitiesWithinAABBExcludingEntity.size()) {
                    Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(l1);
                    if (entity != this.riddenByEntity
                            && entity.canBePushed()
                            && entity instanceof EntityBoat) {
                        entity.applyEntityCollision(this);
                    }

                    l1++;
                    if (flag) {
                        break;
                    }
                }
            }

            if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
                this.riddenByEntity = null;
            }
        }
    }

    public void setForwardDirection(int p171_0) {
        this.dataWatcher.updateObject(18, p171_0);
    }

    public int getForwardDirection() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    public void setPositionAndRotation2(
            double behValue,
            double bbkValue,
            double bbgValue,
            float bbmValue,
            float bboValue,
            int i,
            boolean flag) {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        if (flag && this.riddenByEntity != null) {
            this.prevPosX = this.posX = behValue;
            this.prevPosY = this.posY = bbkValue;
            this.prevPosZ = this.posZ = bbgValue;
            this.rotationYaw = bbmValue;
            this.rotationPitch = bboValue;
            this.boatPosRotationIncrements = 0;
            this.setPosition(behValue, bbkValue, bbgValue);
            this.motionX = this.velocityX = 0.0;
            this.motionY = this.velocityY = 0.0;
            this.motionZ = this.velocityZ = 0.0;
            if (flag1) {
                return;
            }
        }

        label28:
        {
            if (this.isBoatEmpty) {
                this.boatPosRotationIncrements = i + 5;
                if (flag1) {
                    break label28;
                }
            }

            double d3 = behValue - this.posX;
            double d4 = bbkValue - this.posY;
            double d5 = bbgValue - this.posZ;
            double d6 = d3 * d3 + d4 * d4 + d5 * d5;
            if (d6 <= 1.0) {
                return;
            }

            this.boatPosRotationIncrements = 3;
        }

        this.beh = behValue;
        this.bbk = bbkValue;
        this.bbg = bbgValue;
        this.bbm = bbmValue;
        this.bbo = bboValue;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    protected void writeEntityToNBT(CompoundTag compoundTag) {}

    public void setVelocity(double motionXValue, double motionYValue, double motionZValue) {
        this.velocityX = this.motionX = motionXValue;
        this.velocityY = this.motionY = motionYValue;
        this.velocityZ = this.motionZ = motionZValue;
    }

    public boolean canBePushed() {
        return true;
    }

    public double getMountedYOffset() {
        return this.height * 0.0 - 0.3F;
    }

    public BoundingBox getCollisionBox(Entity entity) {
        return entity.getEntityBoundingBox();
    }

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    protected void entityInit() {
        this.dataWatcher.addObject(17, new java.lang.Integer(0));
        this.dataWatcher.addObject(18, new java.lang.Integer(1));
        this.dataWatcher.addObject(19, new java.lang.Float(0.0F));
    }

    public void setIsBoatEmpty(boolean bbhValue) {
        this.isBoatEmpty = bbhValue;
    }

    public void setTimeSinceHit(int p171_0) {
        this.dataWatcher.updateObject(17, p171_0);
    }

    public int getTimeSinceHit() {
        return this.dataWatcher.getWatchableObjectInt(17);
    }

    public BoundingBox getCollisionBoundingBox() {
        return this.getEntityBoundingBox();
    }

    public boolean interactFirst(PlayerEntity playerEntity) {
        if (this.riddenByEntity != null
                && this.riddenByEntity instanceof net.minecraft.entity.player.PlayerEntity
                && this.riddenByEntity != playerEntity) {
            return true;
        }

        if (!this.world.isRemote) {
            playerEntity.mountEntity(this);
        }

        return true;
    }
}
