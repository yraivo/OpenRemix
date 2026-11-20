package net.minecraft.entity.projectile;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemFishFoodFishType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.Arrays;
import java.util.List;

public class EntityFishHook extends Entity {
    public Entity caughtEntity;
    private int xTile = -1;
    private static List FISH;
    private static String[] packedTextFragments;

    private int ticksInGround;
    private int ticksCaughtDelay;
    public PlayerEntity angler;
    private double fishPitch;
    private int zTile;
    private double fishZ;
    private static List JUNK;
    private int fishPosRotationIncrements;
    private int ticksInAir;
    private float fishApproachAngle;
    private boolean inGround;
    private double fishYaw;
    private double fishY;
    private static List TREASURE;
    private int ticksCatchableDelay;
    private double fishX;

    private double clientMotionZ;
    private int ticksCatchable;
    private int yTile = -1;
    private Block inTile;
    private double clientMotionY;
    public int shake;
    private double clientMotionX;

    public void setVelocity(double motionXValue, double motionYValue, double motionZValue) {
        this.clientMotionX = this.motionX = motionXValue;
        this.clientMotionY = this.motionY = motionYValue;
        this.clientMotionZ = this.motionZ = motionZValue;
    }

    public int method_7eec03da329a017ec143() {
        TileEntity.lz();

        if (this.world.isRemote) {
            return 0;
        }

        byte b0;
        {
            b0 = 0;
            if (this.caughtEntity != null) {
                double deltaPosX = this.angler.posX - this.posX;
                double deltaPosY = this.angler.posY - this.posY;
                double deltaPosZ = this.angler.posZ - this.posZ;
                double d6 =
                        MathHelper.sqrt(
                                deltaPosX * deltaPosX
                                        + deltaPosY * deltaPosY
                                        + deltaPosZ * deltaPosZ);
                double d8 = 0.1;
                this.caughtEntity.motionX += deltaPosX * d8;
                this.caughtEntity.motionY =
                        this.caughtEntity.motionY + (deltaPosY * d8 + MathHelper.sqrt(d6) * 0.08);
                this.caughtEntity.motionZ += deltaPosZ * d8;
                b0 = 3;

            } else {

                if (this.ticksCatchable > 0) {
                    EntityItem entityItem =
                            new EntityItem(
                                    this.world,
                                    this.posX,
                                    this.posY,
                                    this.posZ,
                                    this.getFishingResult());
                    double deltaPosXValue = this.angler.posX - this.posX;
                    double deltaPosYValue = this.angler.posY - this.posY;
                    double deltaPosZValue = this.angler.posZ - this.posZ;
                    double d7 =
                            MathHelper.sqrt(
                                    deltaPosXValue * deltaPosXValue
                                            + deltaPosYValue * deltaPosYValue
                                            + deltaPosZValue * deltaPosZValue);
                    double d9 = 0.1;
                    entityItem.motionX = deltaPosXValue * d9;
                    entityItem.motionY = deltaPosYValue * d9 + MathHelper.sqrt(d7) * 0.08;
                    entityItem.motionZ = deltaPosZValue * d9;
                    this.world.spawnEntityInWorld(entityItem);
                    this.angler.world.spawnEntityInWorld(
                            new EntityXPOrb(
                                    this.angler.world,
                                    this.angler.posX,
                                    this.angler.posY + 0.5,
                                    this.angler.posZ + 0.5,
                                    this.rand.nextInt(6) + 1));
                    b0 = 1;
                }
            }
        }

        if (this.inGround) {
            b0 = 2;
        }

        this.setDead();
        this.angler.fishEntity = null;
        return b0;
    }

    public void handleHookCasting(
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
        net.minecraft.entity.projectile.EntityFishHook.packedTextFragments = new String[3];
        net.minecraft.entity.projectile.EntityFishHook.packedTextFragments[0] = new String("");
        net.minecraft.entity.projectile.EntityFishHook.JUNK =
                Arrays.asList(
                        new WeightedRandomFishable(new ItemStack(Items.leather_boots), 10)
                                .setMaxDamagePercent(0.9F),
                        new WeightedRandomFishable(new ItemStack(Items.leather), 10),
                        new WeightedRandomFishable(new ItemStack(Items.bone), 10),
                        new WeightedRandomFishable(new ItemStack(Items.potionitem), 10),
                        new WeightedRandomFishable(new ItemStack(Items.string), 5),
                        new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 2)
                                .setMaxDamagePercent(0.9F),
                        new WeightedRandomFishable(new ItemStack(Items.bowl), 10),
                        new WeightedRandomFishable(new ItemStack(Items.stick), 5),
                        new WeightedRandomFishable(
                                new ItemStack(
                                        Items.dye, 10, EnumDyeColor.BLACK.getDyeColorDamage()),
                                1),
                        new WeightedRandomFishable(new ItemStack(Blocks.tripwire_hook), 10),
                        new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 10));
        net.minecraft.entity.projectile.EntityFishHook.TREASURE =
                Arrays.asList(
                        new WeightedRandomFishable(new ItemStack(Blocks.waterlily), 1),
                        new WeightedRandomFishable(new ItemStack(Items.name_tag), 1),
                        new WeightedRandomFishable(new ItemStack(Items.saddle), 1),
                        new WeightedRandomFishable(new ItemStack(Items.bow), 1)
                                .setMaxDamagePercent(0.25F)
                                .setEnchantable(),
                        new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 1)
                                .setMaxDamagePercent(0.25F)
                                .setEnchantable(),
                        new WeightedRandomFishable(new ItemStack(Items.book), 1).setEnchantable());
        net.minecraft.entity.projectile.EntityFishHook.FISH =
                Arrays.asList(
                        new WeightedRandomFishable(
                                new ItemStack(
                                        Items.fish, 1, ItemFishFoodFishType.COD.getMetadata()),
                                60),
                        new WeightedRandomFishable(
                                new ItemStack(
                                        Items.fish, 1, ItemFishFoodFishType.SALMON.getMetadata()),
                                25),
                        new WeightedRandomFishable(
                                new ItemStack(
                                        Items.fish,
                                        1,
                                        ItemFishFoodFishType.CLOWNFISH.getMetadata()),
                                2),
                        new WeightedRandomFishable(
                                new ItemStack(
                                        Items.fish,
                                        1,
                                        ItemFishFoodFishType.PUFFERFISH.getMetadata()),
                                13));
    }

    public static List func_174855_j() {
        return net.minecraft.entity.projectile.EntityFishHook.FISH;
    }

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

        this.shake = compoundTag.getByte("shake") & 255;
        this.inGround =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (compoundTag.getByte("inGround") == 1 ? 1 : 0));
    }

    public EntityFishHook(World worldValue) {
        super(worldValue);
        this.zTile = -1;
        this.setSize(0.25F, 0.25F);
        this.ignoreFrustumCheck = true;
    }

    public boolean isInRangeToRenderDist(double d0) {
        double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        d1 *= 64.0;
        return d0 < d1 * d1;
    }

    protected void entityInit() {}

    public EntityFishHook(World worldValue, PlayerEntity biJValue) {
        super(worldValue);
        this.zTile = -1;
        this.ignoreFrustumCheck = true;
        this.angler = biJValue;
        this.angler.fishEntity = this;
        this.setSize(0.25F, 0.25F);
        this.setLocationAndAngles(
                biJValue.posX,
                biJValue.posY + biJValue.getEyeHeight(),
                biJValue.posZ,
                biJValue.rotationYaw,
                biJValue.rotationPitch);
        this.posX = this.posX - MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        TileEntity.lz();

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
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI) * f;
        this.handleHookCasting(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
    }

    public EntityFishHook(
            World worldValue, double d0, double d1, double d2, PlayerEntity biJValue) {
        this(worldValue);
        this.setPosition(d0, d1, d2);
        this.ignoreFrustumCheck = true;
        this.angler = biJValue;
        biJValue.fishEntity = this;
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
        compoundTag.setByte("shake", (byte) this.shake);
        compoundTag.setByte("inGround", (byte) (this.inGround ? 1 : 0));
    }

    public void setPositionAndRotation2(
            double fishXValue,
            double fishYValue,
            double biLValue,
            float biNValue,
            float biKValue,
            int aLgValue,
            boolean flag) {
        this.fishX = fishXValue;
        this.fishY = fishYValue;
        this.fishZ = biLValue;
        this.fishYaw = biNValue;
        this.fishPitch = biKValue;
        this.fishPosRotationIncrements = aLgValue;
        this.motionX = this.clientMotionX;
        this.motionY = this.clientMotionY;
        this.motionZ = this.clientMotionZ;
    }

    public PlayerEntity getAngler() {
        return this.angler;
    }

    public void onUpdate() {
        TileEntity.lz();

        super.onUpdate();

        if (this.fishPosRotationIncrements > 0) {
            double d0 = this.posX + (this.fishX - this.posX) / this.fishPosRotationIncrements;
            double d1 = this.posY + (this.fishY - this.posY) / this.fishPosRotationIncrements;
            double d2 = this.posZ + (this.fishZ - this.posZ) / this.fishPosRotationIncrements;
            double d4 = MathHelper.wrapAngleTo180(this.fishYaw - this.rotationYaw);
            this.rotationYaw = (float) (this.rotationYaw + d4 / this.fishPosRotationIncrements);
            this.rotationPitch =
                    (float)
                            (this.rotationPitch
                                    + (this.fishPitch - this.rotationPitch)
                                            / this.fishPosRotationIncrements);
            this.fishPosRotationIncrements--;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);

            return;
        }

        if (!this.world.isRemote) {
            ItemStack currentEquippedItem = this.angler.getCurrentEquippedItem();
            if (this.angler.isDead
                    || !this.angler.isEntityAlive()
                    || currentEquippedItem == null
                    || currentEquippedItem.getItem() != Items.fishing_rod
                    || this.getDistanceSqToEntity(this.angler) > 1024.0) {
                this.setDead();
                this.angler.fishEntity = null;
                return;
            }

            if (this.caughtEntity != null) {
                if (!this.caughtEntity.isDead) {
                    this.posX = this.caughtEntity.posX;
                    double heightValue = this.caughtEntity.height;
                    this.posY = this.caughtEntity.getEntityBoundingBox().minY + heightValue * 0.8;
                    this.posZ = this.caughtEntity.posZ;
                    return;
                }

                this.caughtEntity = null;
            }
        }

        if (this.shake > 0) {
            this.shake--;
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

        Entity entity = null;
        List entitiesWithinAABBExcludingEntity =
                this.world.getEntitiesWithinAABBExcludingEntity(
                        this,
                        this.getEntityBoundingBox()
                                .addCoord(this.motionX, this.motionY, this.motionZ)
                                .expand(1.0, 1.0, 1.0));
        double d3 = 0.0;
        int i = 0;

        while (i < entitiesWithinAABBExcludingEntity.size()) {
            Entity entityValue = (Entity) entitiesWithinAABBExcludingEntity.get(i);
            if (entityValue.canBeCollidedWith()
                    && (entityValue != this.angler || this.ticksInAir >= 5)) {
                float f = 0.3F;
                BoundingBox boundingBoxValue = entityValue.getEntityBoundingBox().expand(f, f, f);
                RayTraceResult rayTraceResultValue =
                        boundingBoxValue.calculateIntercept(vec3, vec3Value);
                if (rayTraceResultValue != null) {
                    double d5 = vec3.distanceTo(rayTraceResultValue.hitVec);
                    if (d5 < d3 || d3 == 0.0) {
                        entity = entityValue;
                        d3 = d5;
                    }
                }
            }

            i++;
        }

        if (entity != null) {
            rayTraceResult = new RayTraceResult(entity);
        }

        if (rayTraceResult != null) {
            if (rayTraceResult.entityHit != null) {
                if (!(!rayTraceResult.entityHit.attackEntityFrom(
                        DamageSource.causeThrownDamage(this, this.angler), 0.0F))) {

                    this.caughtEntity = rayTraceResult.entityHit;
                }
            } else {

                this.inGround = true;
            }
        }

        if (!this.inGround) {
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            float f5 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
            this.rotationPitch = (float) (Math.atan2(this.motionY, f5) * 180.0 / Math.PI);

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
            this.rotationYaw =
                    this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float f6 = 0.92F;
            if (this.onGround || this.collidedHorizontally) {
                f6 = 0.5F;
            }

            byte b0 = 5;
            double d13 = 0.0;
            int j = 0;

            while (j < b0) {
                BoundingBox boundingBoxValue2 = this.getEntityBoundingBox();
                double d7 = boundingBoxValue2.maxY - boundingBoxValue2.minY;
                double d8 = boundingBoxValue2.minY + d7 * j / b0;
                double d6 = boundingBoxValue2.minY + d7 * (j + 1) / b0;
                BoundingBox boundingBoxValue3 =
                        new BoundingBox(
                                boundingBoxValue2.minX,
                                d8,
                                boundingBoxValue2.minZ,
                                boundingBoxValue2.maxX,
                                d6,
                                boundingBoxValue2.maxZ);
                if (this.world.isAABBInMaterial(boundingBoxValue3, Material.water)) {
                    d13 += 1.0 / b0;
                }

                j++;
            }

            if (!this.world.isRemote && d13 > 0.0) {
                WorldServer worldValue = (WorldServer) this.world;
                int k = 1;
                BlockPos blockPos = new BlockPos(this).offsetUp();
                if (this.rand.nextFloat() < 0.25F && this.world.canLightningStrike(blockPos)) {
                    k = 2;
                }

                if (this.rand.nextFloat() < 0.5F && !this.world.isAgainstSky(blockPos)) {
                    k--;
                }

                {
                    if (this.ticksCatchable > 0) {
                        this.ticksCatchable--;
                        if (!(this.ticksCatchable > 0)) {

                            this.ticksCaughtDelay = 0;
                            this.ticksCatchableDelay = 0;
                        }
                    } else {

                        if (this.ticksCatchableDelay > 0) {
                            this.ticksCatchableDelay -= k;
                            if (this.ticksCatchableDelay <= 0) {
                                this.motionY -= 0.2F;
                                this.playSound(
                                        "random.splash",
                                        0.25F,
                                        1.0F
                                                + (this.rand.nextFloat() - this.rand.nextFloat())
                                                        * 0.4F);
                                float f1 = MathHelper.floor(this.getEntityBoundingBox().minY);
                                worldValue.spawnParticle(
                                        EnumParticleTypes.WATER_BUBBLE,
                                        this.posX,
                                        f1 + 1.0F,
                                        this.posZ,
                                        (int) (1.0F + this.width * 20.0F),
                                        this.width,
                                        0.0,
                                        this.width,
                                        0.2F,
                                        new int[0]);
                                worldValue.spawnParticle(
                                        EnumParticleTypes.WATER_WAKE,
                                        this.posX,
                                        f1 + 1.0F,
                                        this.posZ,
                                        (int) (1.0F + this.width * 20.0F),
                                        this.width,
                                        0.0,
                                        this.width,
                                        0.2F,
                                        new int[0]);
                                this.ticksCatchable =
                                        MathHelper.nextIntInclusive(this.rand, 10, 30);

                            } else {

                                this.fishApproachAngle =
                                        (float)
                                                (this.fishApproachAngle
                                                        + this.rand.nextGaussian() * 4.0);
                                float f7 = this.fishApproachAngle * (float) (Math.PI / 180.0);
                                float f2 = MathHelper.sin(f7);
                                float f9 = MathHelper.cos(f7);
                                double d14 = this.posX + f2 * this.ticksCatchableDelay * 0.1F;
                                double d10 =
                                        MathHelper.floor(this.getEntityBoundingBox().minY) + 1.0F;
                                double d9 = this.posZ + f9 * this.ticksCatchableDelay * 0.1F;
                                if (this.rand.nextFloat() < 0.15F) {
                                    worldValue.spawnParticle(
                                            EnumParticleTypes.WATER_BUBBLE,
                                            d14,
                                            d10 - 0.1F,
                                            d9,
                                            1,
                                            f2,
                                            0.1,
                                            f9,
                                            0.0,
                                            new int[0]);
                                }

                                float f3 = f2 * 0.04F;
                                float f4 = f9 * 0.04F;
                                worldValue.spawnParticle(
                                        EnumParticleTypes.WATER_WAKE,
                                        d14,
                                        d10,
                                        d9,
                                        0,
                                        f4,
                                        0.01,
                                        -f3,
                                        1.0,
                                        new int[0]);
                                worldValue.spawnParticle(
                                        EnumParticleTypes.WATER_WAKE,
                                        d14,
                                        d10,
                                        d9,
                                        0,
                                        -f4,
                                        0.01,
                                        f3,
                                        1.0,
                                        new int[0]);
                            }
                        } else {

                            if (this.ticksCaughtDelay > 0) {
                                float f8;
                                {
                                    this.ticksCaughtDelay -= k;
                                    f8 = 0.15F;
                                    if (this.ticksCaughtDelay < 20) {
                                        f8 = (float) (f8 + (20 - this.ticksCaughtDelay) * 0.05);

                                    } else {

                                        if (this.ticksCaughtDelay < 40) {
                                            f8 = (float) (f8 + (40 - this.ticksCaughtDelay) * 0.02);

                                        } else {

                                            if (this.ticksCaughtDelay < 60) {
                                                f8 =
                                                        (float)
                                                                (f8
                                                                        + (60
                                                                                        - this
                                                                                                .ticksCaughtDelay)
                                                                                * 0.01);
                                            }
                                        }
                                    }
                                }

                                if (this.rand.nextFloat() < f8) {
                                    float f11 =
                                            MathHelper.nextFloat(this.rand, 0.0F, 360.0F)
                                                    * (float) (Math.PI / 180.0);
                                    float f10 = MathHelper.nextFloat(this.rand, 25.0F, 60.0F);
                                    double d15 = this.posX + MathHelper.sin(f11) * f10 * 0.1F;
                                    double d17 =
                                            MathHelper.floor(this.getEntityBoundingBox().minY)
                                                    + 1.0F;
                                    double d16 = this.posZ + MathHelper.cos(f11) * f10 * 0.1F;
                                    worldValue.spawnParticle(
                                            EnumParticleTypes.WATER_SPLASH,
                                            d15,
                                            d17,
                                            d16,
                                            2 + this.rand.nextInt(2),
                                            0.1F,
                                            0.0,
                                            0.1F,
                                            0.0,
                                            new int[0]);
                                }

                                if (!(this.ticksCaughtDelay > 0)) {

                                    this.fishApproachAngle =
                                            MathHelper.nextFloat(this.rand, 0.0F, 360.0F);
                                    this.ticksCatchableDelay =
                                            MathHelper.nextIntInclusive(this.rand, 20, 80);
                                }
                            } else {

                                this.ticksCaughtDelay =
                                        MathHelper.nextIntInclusive(this.rand, 100, 900);
                                this.ticksCaughtDelay =
                                        this.ticksCaughtDelay
                                                - EnchantmentHelper.getLureModifier(this.angler)
                                                        * 20
                                                        * 5;
                            }
                        }
                    }
                }

                if (this.ticksCatchable > 0) {
                    this.motionY =
                            this.motionY
                                    - this.rand.nextFloat()
                                            * this.rand.nextFloat()
                                            * this.rand.nextFloat()
                                            * 0.2;
                }
            }

            double d12 = d13 * 2.0 - 1.0;
            this.motionY += 0.04F * d12;
            if (d13 > 0.0) {
                f6 = (float) (f6 * 0.9);
                this.motionY *= 0.8;
            }

            this.motionX *= f6;
            this.motionY *= f6;
            this.motionZ *= f6;
            this.setPosition(this.posX, this.posY, this.posZ);
        }
    }

    public void setDead() {
        super.setDead();
        if (this.angler != null) {
            this.angler.fishEntity = null;
        }
    }

    private ItemStack getFishingResult() {
        float f = this.world.rand.nextFloat();
        int i = EnchantmentHelper.getLuckOfSeaModifier(this.angler);
        int j = EnchantmentHelper.getLureModifier(this.angler);
        float f1 = 0.1F - i * 0.025F - j * 0.01F;
        float f2 = 0.05F + i * 0.01F - j * 0.01F;
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        f2 = MathHelper.clamp(f2, 0.0F, 1.0F);
        if (f < f1) {
            this.angler.triggerAchievement(StatList.junkFishedStat);
            return ((WeightedRandomFishable)
                            WeightedRandom.getRandomItem(
                                    this.rand, net.minecraft.entity.projectile.EntityFishHook.JUNK))
                    .getItemStack(this.rand);
        } else {
            f -= f1;
            if (f < f2) {
                this.angler.triggerAchievement(StatList.treasureFishedStat);
                return ((WeightedRandomFishable)
                                WeightedRandom.getRandomItem(
                                        this.rand,
                                        net.minecraft.entity.projectile.EntityFishHook.TREASURE))
                        .getItemStack(this.rand);
            } else {
                float f3 = f - f2;
                this.angler.triggerAchievement(StatList.fishCaughtStat);
                return ((WeightedRandomFishable)
                                WeightedRandom.getRandomItem(
                                        this.rand,
                                        net.minecraft.entity.projectile.EntityFishHook.FISH))
                        .getItemStack(this.rand);
            }
        }
    }
}
