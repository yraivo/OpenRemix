package net.minecraft.entity.boss;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityDragon extends EntityLiving implements IEntityMultiPart, IMob, IBossDisplayData {
    public double targetX;
    public double targetZ;

    private Entity target;
    public EntityDragonPart dragonPartHead;
    public int ringBufferIndex;
    public boolean bbK;
    public float bct;
    public boolean bcu;
    public double targetY;

    public EntityDragonPart dragonPartBody;

    public int deathTicks;
    public EntityDragonPart dragonPartTail1;
    public float bbG;
    public EntityDragonPart dragonPartTail2;
    public EntityDragonPart dragonPartWing1;
    public double[][] ringBuffer;
    public EntityDragonPart dragonPartTail3;
    public EntityDragonPart dragonPartWing2;
    public EntityEnderCrystal healingEnderCrystal;
    public EntityDragonPart[] dragonPartArray;

    public Entity[] getParts() {
        return this.dragonPartArray;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0);
    }

    protected String getLivingSound() {
        return "mob.enderdragon.growl";
    }

    protected void despawnEntity() {}

    protected void entityInit() {
        super.entityInit();
    }


    protected float getSoundVolume() {
        return 5.0F;
    }

    public double[] getMovementOffsets(int i, float f) {
        if (this.getHealth() <= 0.0F) {
            f = 0.0F;
        }

        f = 1.0F - f;
        int j = this.ringBufferIndex - i * 1 & 63;
        int k = this.ringBufferIndex - i * 1 - 1 & 63;
        double[] adouble = new double[3];
        double d0 = this.ringBuffer[j][0];
        double d1 = MathHelper.wrapAngleTo180(this.ringBuffer[k][0] - d0);
        adouble[0] = d0 + d1 * f;
        d0 = this.ringBuffer[j][1];
        d1 = this.ringBuffer[k][1] - d0;
        adouble[1] = d0 + d1 * f;
        adouble[2] = this.ringBuffer[j][2] + (this.ringBuffer[k][2] - this.ringBuffer[j][2]) * f;
        return adouble;
    }

    private boolean destroyBlocksInAABB(BoundingBox boundingBoxValue) {
        int i = MathHelper.floor(boundingBoxValue.minX);
        int j = MathHelper.floor(boundingBoxValue.minY);
        int k = MathHelper.floor(boundingBoxValue.minZ);
        int l = MathHelper.floor(boundingBoxValue.maxX);
        int i1 = MathHelper.floor(boundingBoxValue.maxY);
        String text = BossStatus.method_c60693f457aa70be7160();
        int j1 = MathHelper.floor(boundingBoxValue.maxZ);
        String textValue = text;
        byte b0 = 0;
        byte b1 = 0;
        int k1 = i;

        label65:
        while (k1 <= l) {
            int l1 = j;

            while (true) {
                if (l1 <= i1) {
                    int i2 = k;

                    while (i2 <= j1) {
                        Block block = this.world.getBlockState(new BlockPos(k1, l1, i2)).getBlock();
                        if (block.getMaterial() != Material.AIR) {
                            if (block != Blocks.barrier
                                    && block != Blocks.obsidian
                                    && block != Blocks.end_stone
                                    && block != Blocks.bedrock
                                    && block != Blocks.command_block
                                    && this.world
                                            .getGameRules()
                                            .getGameRuleBooleanValue("mobGriefing")) {
                                b1 =
                                        (byte)
                                                (!this.world.setBlockToAir(new BlockPos(k1, l1, i2))
                                                                && b1 == 0
                                                        ? 0
                                                        : 1);
                                if (!(textValue == null)) {

                                    b0 = 1;
                                }
                            } else {

                                b0 = 1;
                            }
                        }

                        i2++;
                        if (textValue != null) {
                            break;
                        }
                    }

                    l1++;
                    if (textValue == null) {
                        continue;
                    }
                }

                k1++;
                if (textValue != null) {
                    break label65;
                }
                break;
            }
        }

        if (b1 != 0) {
            double d1 =
                    boundingBoxValue.minX
                            + (boundingBoxValue.maxX - boundingBoxValue.minX)
                                    * this.rand.nextFloat();
            double d2 =
                    boundingBoxValue.minY
                            + (boundingBoxValue.maxY - boundingBoxValue.minY)
                                    * this.rand.nextFloat();
            double d0 =
                    boundingBoxValue.minZ
                            + (boundingBoxValue.maxZ - boundingBoxValue.minZ)
                                    * this.rand.nextFloat();
            this.world.spawnParticle(
                    EnumParticleTypes.EXPLOSION_LARGE, d1, d2, d0, 0.0, 0.0, 0.0, new int[0]);
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }

    public void onKillCommand() {
        this.setDead();
    }

    public void onLivingUpdate() {
        String text = BossStatus.method_c60693f457aa70be7160();
        if (this.world.isRemote) {
            float f = MathHelper.cos(this.bct * (float) Math.PI * 2.0F);
            float f1 = MathHelper.cos(this.bbG * (float) Math.PI * 2.0F);
            if (f1 <= -0.3F && f >= -0.3F && !this.isSlient()) {
                this.world.playSound(
                        this.posX,
                        this.posY,
                        this.posZ,
                        "mob.enderdragon.wings",
                        5.0F,
                        0.8F + this.rand.nextFloat() * 0.3F,
                        false);
            }
        }

        this.bbG = this.bct;
        if (this.getHealth() <= 0.0F) {
            float f12 = (this.rand.nextFloat() - 0.5F) * 8.0F;
            float f14 = (this.rand.nextFloat() - 0.5F) * 4.0F;
            float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
            this.world.spawnParticle(
                    EnumParticleTypes.EXPLOSION_LARGE,
                    this.posX + f12,
                    this.posY + 2.0 + f14,
                    this.posZ + f2,
                    0.0,
                    0.0,
                    0.0,
                    new int[0]);
            if (text == null) {
                return;
            }
        }

        {
            this.updateDragonEnderCrystal();
            float f13 =
                    0.2F
                            / (MathHelper.sqrt(
                                                    this.motionX * this.motionX
                                                            + this.motionZ * this.motionZ)
                                            * 10.0F
                                    + 1.0F);
            f13 *= (float) Math.pow(2.0, this.motionY);
            if (this.bbK) {
                this.bct += f13 * 0.5F;
                if (!(text == null)) {

                    this.bct += f13;
                }
            } else {

                this.bct += f13;
            }
        }

        this.rotationYaw = MathHelper.wrapAngleTo180(this.rotationYaw);
        if (this.ringBufferIndex < 0) {
            int i = 0;

            while (i < this.ringBuffer.length) {
                this.ringBuffer[i][0] = this.rotationYaw;
                this.ringBuffer[i][1] = this.posY;
                i++;
                if (text != null) {
                    break;
                }
            }
        }

        if (++this.ringBufferIndex == this.ringBuffer.length) {
            this.ringBufferIndex = 0;
        }

        label146:
        {
            this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
            this.ringBuffer[this.ringBufferIndex][1] = this.posY;
            if (this.world.isRemote) {
                if (this.newPosRotationIncrements <= 0) {
                    break label146;
                }

                double d2 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
                double d8 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
                double d0 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
                double d1 = MathHelper.wrapAngleTo180(this.newRotationYaw - this.rotationYaw);
                this.rotationYaw = (float) (this.rotationYaw + d1 / this.newPosRotationIncrements);
                this.rotationPitch =
                        (float)
                                (this.rotationPitch
                                        + (this.newRotationPitch - this.rotationPitch)
                                                / this.newPosRotationIncrements);
                this.newPosRotationIncrements--;
                this.setPosition(d2, d8, d0);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                if (text == null) {
                    break label146;
                }
            }

            double d9;
            double d10;
            double d11;
            double d12;
            {
                d12 = this.targetX - this.posX;
                d9 = this.targetY - this.posY;
                d10 = this.targetZ - this.posZ;
                d11 = d12 * d12 + d9 * d9 + d10 * d10;
                if (this.target != null) {
                    this.targetX = this.target.posX;
                    this.targetZ = this.target.posZ;
                    double d4 = this.targetX - this.posX;
                    double d5 = this.targetZ - this.posZ;
                    double d6 = Math.sqrt(d4 * d4 + d5 * d5);
                    double d3 = 0.4F + d6 / 80.0 - 1.0;
                    if (d3 > 10.0) {
                        d3 = 10.0;
                    }

                    this.targetY = this.target.getEntityBoundingBox().minY + d3;
                    if (!(text == null)) {

                        this.targetX = this.targetX + this.rand.nextGaussian() * 2.0;
                        this.targetZ = this.targetZ + this.rand.nextGaussian() * 2.0;
                    }
                } else {

                    this.targetX = this.targetX + this.rand.nextGaussian() * 2.0;
                    this.targetZ = this.targetZ + this.rand.nextGaussian() * 2.0;
                }
            }

            if (this.bcu
                    || d11 < 100.0
                    || d11 > 22500.0
                    || this.collidedHorizontally
                    || this.collidedVertically) {
                this.cj();
            }

            d9 /= MathHelper.sqrt(d12 * d12 + d10 * d10);
            float f3 = 0.6F;
            d9 = MathHelper.clamp(d9, -f3, f3);
            this.motionY += d9 * 0.1F;
            this.rotationYaw = MathHelper.wrapAngleTo180(this.rotationYaw);
            double d14 = 180.0 - Math.atan2(d12, d10) * 180.0 / Math.PI;
            double d15 = MathHelper.wrapAngleTo180(d14 - this.rotationYaw);
            if (d15 > 50.0) {
                d15 = 50.0;
            }

            if (d15 < -50.0) {
                d15 = -50.0;
            }

            Vec3 vec3 =
                    new Vec3(
                                    this.targetX - this.posX,
                                    this.targetY - this.posY,
                                    this.targetZ - this.posZ)
                            .normalize();
            double d13 = -MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F);
            Vec3 vec3Value =
                    new Vec3(
                                    MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F),
                                    this.motionY,
                                    d13)
                            .normalize();
            float f6 = ((float) vec3Value.dotProduct(vec3) + 0.5F) / 1.5F;
            if (f6 < 0.0F) {
                f6 = 0.0F;
            }

            this.randomYawVelocity *= 0.8F;
            float f7 =
                    MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ)
                                    * 1.0F
                            + 1.0F;
            double d7 =
                    Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0
                            + 1.0;
            if (d7 > 40.0) {
                d7 = 40.0;
            }

            {
                this.randomYawVelocity = (float) (this.randomYawVelocity + d15 * (0.7F / d7 / f7));
                this.rotationYaw = this.rotationYaw + this.randomYawVelocity * 0.1F;
                float f9 = (float) (2.0 / (d7 + 1.0));
                float f10 = 0.06F;
                this.moveFlying(0.0F, -1.0F, f10 * (f6 * f9 + (1.0F - f9)));
                if (this.bbK) {
                    this.moveEntity(this.motionX * 0.8F, this.motionY * 0.8F, this.motionZ * 0.8F);
                    if (!(text == null)) {

                        this.moveEntity(this.motionX, this.motionY, this.motionZ);
                    }
                } else {

                    this.moveEntity(this.motionX, this.motionY, this.motionZ);
                }
            }

            Vec3 vec3Value2 = new Vec3(this.motionX, this.motionY, this.motionZ).normalize();
            float f11 = ((float) vec3Value2.dotProduct(vec3Value) + 1.0F) / 2.0F;
            f11 = 0.8F + 0.15F * f11;
            this.motionX *= f11;
            this.motionZ *= f11;
            this.motionY *= 0.91F;
        }

        this.renderYawOffset = this.rotationYaw;
        this.dragonPartHead.width = this.dragonPartHead.height = 3.0F;
        this.dragonPartTail1.width = this.dragonPartTail1.height = 2.0F;
        this.dragonPartTail2.width = this.dragonPartTail2.height = 2.0F;
        this.dragonPartTail3.width = this.dragonPartTail3.height = 2.0F;
        this.dragonPartBody.height = 3.0F;
        this.dragonPartBody.width = 5.0F;
        this.dragonPartWing1.height = 2.0F;
        this.dragonPartWing1.width = 4.0F;
        this.dragonPartWing2.height = 3.0F;
        this.dragonPartWing2.width = 4.0F;
        float f15 =
                (float) (this.getMovementOffsets(5, 1.0F)[1] - this.getMovementOffsets(10, 1.0F)[1])
                        * 10.0F
                        / 180.0F
                        * (float) Math.PI;
        float f16 = MathHelper.cos(f15);
        float f18 = -MathHelper.sin(f15);
        float f4 = this.rotationYaw * (float) Math.PI / 180.0F;
        float f19 = MathHelper.sin(f4);
        float f5 = MathHelper.cos(f4);
        this.dragonPartBody.onUpdate();
        this.dragonPartBody.setLocationAndAngles(
                this.posX + f19 * 0.5F, this.posY, this.posZ - f5 * 0.5F, 0.0F, 0.0F);
        this.dragonPartWing1.onUpdate();
        this.dragonPartWing1.setLocationAndAngles(
                this.posX + f5 * 4.5F, this.posY + 2.0, this.posZ + f19 * 4.5F, 0.0F, 0.0F);
        this.dragonPartWing2.onUpdate();
        this.dragonPartWing2.setLocationAndAngles(
                this.posX - f5 * 4.5F, this.posY + 2.0, this.posZ - f19 * 4.5F, 0.0F, 0.0F);
        if (!this.world.isRemote && this.hurtTime == 0) {
            this.collideWithEntities(
                    this.world.getEntitiesWithinAABBExcludingEntity(
                            this,
                            this.dragonPartWing1
                                    .getEntityBoundingBox()
                                    .expand(4.0, 2.0, 4.0)
                                    .offset(0.0, -2.0, 0.0)));
            this.collideWithEntities(
                    this.world.getEntitiesWithinAABBExcludingEntity(
                            this,
                            this.dragonPartWing2
                                    .getEntityBoundingBox()
                                    .expand(4.0, 2.0, 4.0)
                                    .offset(0.0, -2.0, 0.0)));
            this.attackEntitiesInList(
                    this.world.getEntitiesWithinAABBExcludingEntity(
                            this,
                            this.dragonPartHead.getEntityBoundingBox().expand(1.0, 1.0, 1.0)));
        }

        double[] adouble1 = this.getMovementOffsets(5, 1.0F);
        double[] adouble = this.getMovementOffsets(0, 1.0F);
        float f17 =
                MathHelper.sin(
                        this.rotationYaw * (float) Math.PI / 180.0F
                                - this.randomYawVelocity * 0.01F);
        float f20 =
                MathHelper.cos(
                        this.rotationYaw * (float) Math.PI / 180.0F
                                - this.randomYawVelocity * 0.01F);
        this.dragonPartHead.onUpdate();
        this.dragonPartHead.setLocationAndAngles(
                this.posX + f17 * 5.5F * f16,
                this.posY + (adouble[1] - adouble1[1]) * 1.0 + f18 * 5.5F,
                this.posZ - f20 * 5.5F * f16,
                0.0F,
                0.0F);
        int j = 0;

        while (j < 3) {
            EntityDragonPart entityDragonPart = null;
            if (j == 0) {
                entityDragonPart = this.dragonPartTail1;
            }

            if (j == 1) {
                entityDragonPart = this.dragonPartTail2;
            }

            if (j == 2) {
                entityDragonPart = this.dragonPartTail3;
            }

            double[] adouble2 = this.getMovementOffsets(12 + j * 2, 1.0F);
            float f21 =
                    this.rotationYaw * (float) Math.PI / 180.0F
                            + this.simplifyAngle(adouble2[0] - adouble1[0])
                                    * (float) Math.PI
                                    / 180.0F
                                    * 1.0F;
            float f8 = MathHelper.sin(f21);
            float f22 = MathHelper.cos(f21);
            float f23 = 1.5F;
            float f24 = (j + 1) * 2.0F;
            entityDragonPart.onUpdate();
            entityDragonPart.setLocationAndAngles(
                    this.posX - (f19 * f23 + f8 * f24) * f16,
                    this.posY + (adouble2[1] - adouble1[1]) * 1.0 - (f24 + f23) * f18 + 1.5,
                    this.posZ + (f5 * f23 + f22 * f24) * f16,
                    0.0F,
                    0.0F);
            j++;
            if (text != null) {
                break;
            }
        }

        if (!this.world.isRemote) {
            this.bbK =
                    this.destroyBlocksInAABB(this.dragonPartHead.getEntityBoundingBox())
                            | this.destroyBlocksInAABB(this.dragonPartBody.getEntityBoundingBox());
        }
    }

    public EntityDragon(World worldValue) {
        super(worldValue);
        String text = BossStatus.method_c60693f457aa70be7160();
        this.ringBuffer = new double[64][3];
        this.ringBufferIndex = -1;
        this.dragonPartArray =
                new EntityDragonPart[] {
                    this.dragonPartHead = new EntityDragonPart(this, "head", 6.0F, 6.0F),
                    this.dragonPartBody = new EntityDragonPart(this, "body", 8.0F, 8.0F),
                    this.dragonPartTail1 = new EntityDragonPart(this, "tail", 4.0F, 4.0F),
                    this.dragonPartTail2 = new EntityDragonPart(this, "tail", 4.0F, 4.0F),
                    this.dragonPartTail3 = new EntityDragonPart(this, "tail", 4.0F, 4.0F),
                    this.dragonPartWing1 = new EntityDragonPart(this, "wing", 4.0F, 4.0F),
                    this.dragonPartWing2 = new EntityDragonPart(this, "wing", 4.0F, 4.0F)
                };
        String textValue = text;
        this.setHealth(this.getMaxHealth());
        this.setSize(16.0F, 8.0F);
        this.noClip = true;
        this.isImmuneToFire = true;
        this.targetY = 100.0;
        this.ignoreFrustumCheck = true;
        if (textValue != null) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }
    }

    private void generatePortal(BlockPos blockPos) {
        String text = BossStatus.method_c60693f457aa70be7160();

        String textValue = text;

        int i = -1;

        label57:
        while (i <= 32) {
            int j = -4;

            while (true) {
                if (j <= 4) {
                    int k = -4;

                    while (k <= 4) {
                        double d2 = j * j + k * k;
                        label47:
                        if (d2 <= 12.25) {
                            BlockPos blockPosValue = blockPos.add(j, i, k);
                            if (i < 0) {
                                if (!(d2 <= 6.25)) {
                                    break label47;
                                }

                                this.world.setBlockState(
                                        blockPosValue, Blocks.bedrock.getDefaultState());
                                if (textValue == null) {
                                    break label47;
                                }
                            }

                            if (i > 0) {
                                this.world.setBlockState(
                                        blockPosValue, Blocks.air.getDefaultState());
                                if (textValue == null) {
                                    break label47;
                                }
                            }

                            if (d2 > 6.25) {
                                this.world.setBlockState(
                                        blockPosValue, Blocks.bedrock.getDefaultState());
                                if (textValue == null) {
                                    break label47;
                                }
                            }

                            this.world.setBlockState(
                                    blockPosValue, Blocks.end_portal.getDefaultState());
                        }

                        k++;
                        if (textValue != null) {
                            break;
                        }
                    }

                    j++;
                    if (textValue == null) {
                        continue;
                    }
                }

                i++;
                if (textValue != null) {
                    break label57;
                }
                break;
            }
        }

        this.world.setBlockState(blockPos, Blocks.bedrock.getDefaultState());
        this.world.setBlockState(blockPos.offsetUp(), Blocks.bedrock.getDefaultState());
        BlockPos blockPosValue2 = blockPos.offsetUp(2);
        this.world.setBlockState(blockPosValue2, Blocks.bedrock.getDefaultState());
        this.world.setBlockState(
                blockPosValue2.offsetWest(),
                Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, Facing.EAST));
        this.world.setBlockState(
                blockPosValue2.offsetEast(),
                Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, Facing.WEST));
        this.world.setBlockState(
                blockPosValue2.offsetNorth(),
                Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, Facing.SOUTH));
        this.world.setBlockState(
                blockPosValue2.offsetSouth(),
                Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, Facing.NORTH));
        this.world.setBlockState(blockPos.offsetUp(3), Blocks.bedrock.getDefaultState());
        this.world.setBlockState(blockPos.offsetUp(4), Blocks.dragon_egg.getDefaultState());
    }

    private void updateDragonEnderCrystal() {
        String text;
        text = BossStatus.method_c60693f457aa70be7160();
        if (this.healingEnderCrystal != null) {
            if (this.healingEnderCrystal.isDead) {
                if (!this.world.isRemote) {
                    this.attackEntityFromPart(
                            this.dragonPartHead,
                            DamageSource.setExplosionSource((Explosion) null),
                            10.0F);
                }

                this.healingEnderCrystal = null;
                if (!(text == null)) {

                    if (this.ticksExisted % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                        this.setHealth(this.getHealth() + 1.0F);
                    }
                }
            } else {

                if (this.ticksExisted % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                    this.setHealth(this.getHealth() + 1.0F);
                }
            }
        }

        if (this.rand.nextInt(10) == 0) {
            float f = 32.0F;
            List entitiesWithinAABB =
                    this.world.getEntitiesWithinAABB(
                            EntityEnderCrystal.class, this.getEntityBoundingBox().expand(f, f, f));
            EntityEnderCrystal bcEValue = null;
            double MAX_VALUE = Double.MAX_VALUE;

            for (java.util.Iterator<?> __recoveryIterator171_28299 =
                            (entitiesWithinAABB).iterator();
                    (__recoveryIterator171_28299).hasNext(); ) {
                EntityEnderCrystal entityEnderCrystal =
                        (EntityEnderCrystal) (__recoveryIterator171_28299).next();
                double distanceSqToEntity = entityEnderCrystal.getDistanceSqToEntity(this);
                if (distanceSqToEntity < MAX_VALUE) {
                    MAX_VALUE = distanceSqToEntity;
                    bcEValue = entityEnderCrystal;
                }

                if (text != null) {
                    break;
                }
            }

            this.healingEnderCrystal = bcEValue;
        }
    }

    protected String getHurtSound() {
        return "mob.enderdragon.hit";
    }

    private void attackEntitiesInList(List list) {
        String text = BossStatus.method_c60693f457aa70be7160();
        int i = 0;
        String textValue = text;

        while (i < list.size()) {
            Entity entity = (Entity) list.get(i);
            if (entity instanceof LivingEntity) {
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);
                this.applyEnchantments(this, entity);
            }

            i++;
            if (textValue != null) {
                break;
            }
        }
    }

    public boolean attackEntityFromPart(
            EntityDragonPart entityDragonPart, DamageSource damageSource, float f) {
        if (entityDragonPart != this.dragonPartHead) {
            f = f / 4.0F + 1.0F;
        }

        float f1 = this.rotationYaw * (float) Math.PI / 180.0F;
        float f2 = MathHelper.sin(f1);
        float f3 = MathHelper.cos(f1);
        this.targetX = this.posX + f2 * 5.0F + (this.rand.nextFloat() - 0.5F) * 2.0F;
        this.targetY = this.posY + this.rand.nextFloat() * 3.0F + 1.0;
        this.targetZ = this.posZ - f3 * 5.0F + (this.rand.nextFloat() - 0.5F) * 2.0F;
        this.target = null;
        if (damageSource.getEntity() instanceof PlayerEntity || damageSource.isExplosion()) {
            this.attackDragonFrom(damageSource, f);
        }

        return true;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (damageSource instanceof EntityDamageSource
                && ((EntityDamageSource) damageSource).getIsThornsDamage()) {
            this.attackDragonFrom(damageSource, f);
        }

        return false;
    }

    private float simplifyAngle(double d0) {
        return (float) MathHelper.wrapAngleTo180(d0);
    }

    private void collideWithEntities(List list) {
        String text = BossStatus.method_c60693f457aa70be7160();
        double d0 =
                (this.dragonPartBody.getEntityBoundingBox().minX
                                + this.dragonPartBody.getEntityBoundingBox().maxX)
                        / 2.0;
        String textValue = text;
        double d1 =
                (this.dragonPartBody.getEntityBoundingBox().minZ
                                + this.dragonPartBody.getEntityBoundingBox().maxZ)
                        / 2.0;

        for (java.util.Iterator<?> __recoveryIterator171_32323 = (list).iterator();
                (__recoveryIterator171_32323).hasNext(); ) {
            Entity entity = (Entity) (__recoveryIterator171_32323).next();
            if (entity instanceof LivingEntity) {
                double d2 = entity.posX - d0;
                double d3 = entity.posZ - d1;
                double d4 = d2 * d2 + d3 * d3;
                entity.addVelocity(d2 / d4 * 4.0, 0.2F, d3 / d4 * 4.0);
            }

            if (textValue != null) {
                break;
            }
        }
    }

    protected boolean attackDragonFrom(DamageSource damageSource, float f) {
        return super.attackEntityFrom(damageSource, f);
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    protected void cQ() {
        String text = BossStatus.method_c60693f457aa70be7160();
        this.deathTicks++;
        String textValue = text;
        if (this.deathTicks >= 180 && this.deathTicks <= 200) {
            float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
            float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
            float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
            this.world.spawnParticle(
                    EnumParticleTypes.EXPLOSION_HUGE,
                    this.posX + f,
                    this.posY + 2.0 + f1,
                    this.posZ + f2,
                    0.0,
                    0.0,
                    0.0,
                    new int[0]);
        }

        if (!this.world.isRemote) {
            if (this.deathTicks > 150
                    && this.deathTicks % 5 == 0
                    && this.world.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
                int i = 1000;

                while (i > 0) {
                    int k = EntityXPOrb.method_c640c0ab3979b883b99e(i);
                    i -= k;
                    this.world.spawnEntityInWorld(
                            new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, k));
                    if (textValue != null) {
                        break;
                    }
                }
            }

            if (this.deathTicks == 1) {
                this.world.playBroadcastSound(1018, new BlockPos(this), 0);
            }
        }

        this.moveEntity(0.0, 0.1F, 0.0);
        this.renderYawOffset = this.rotationYaw += 20.0F;
        if (this.deathTicks == 200 && !this.world.isRemote) {
            int j = 2000;

            while (j > 0) {
                int l = EntityXPOrb.method_c640c0ab3979b883b99e(j);
                j -= l;
                this.world.spawnEntityInWorld(
                        new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, l));
                if (textValue != null) {
                    break;
                }
            }

            this.generatePortal(new BlockPos(this.posX, 64.0, this.posZ));
            this.setDead();
        }
    }

    private void cj() {
        this.bcu = false;
        String text = BossStatus.method_c60693f457aa70be7160();
        ArrayList arrayList = Lists.newArrayList(this.world.playerEntities);
        Iterator iterator = arrayList.iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            if (((PlayerEntity) iterator.next()).isSpectator()) {
                iterator.remove();
                if (textValue != null) {
                    break;
                }
            }
        }

        if (this.rand.nextInt(2) == 0 && !arrayList.isEmpty()) {
            this.target = (Entity) arrayList.get(this.rand.nextInt(arrayList.size()));
            if (textValue == null) {
                return;
            }
        }

        int i;
        do {
            this.targetX = 0.0;
            this.targetY = 70.0F + this.rand.nextFloat() * 50.0F;
            this.targetZ = 0.0;
            this.targetX = this.targetX + (this.rand.nextFloat() * 120.0F - 60.0F);
            this.targetZ = this.targetZ + (this.rand.nextFloat() * 120.0F - 60.0F);
            double d0 = this.posX - this.targetX;
            double d1 = this.posY - this.targetY;
            double d2 = this.posZ - this.targetZ;
            i = d0 * d0 + d1 * d1 + d2 * d2 > 100.0 ? 1 : 0;
        } while (i == 0);

        this.target = null;
    }

    public World getWorld() {
        return this.world;
    }
}
