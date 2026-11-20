package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class SlimeEntity extends EntityLiving implements IMob {
    public float squishAmount;
    private boolean wasOnGround;

    public float prevSquishFactor;
    public float squishFactor;

    public IEntityLivingData onInitialSpawn(
            DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
        int i = this.rand.nextInt(3);
        if (i < 2
                && this.rand.nextFloat()
                        < 0.5F * difficultyInstance.getClampedAdditionalDifficulty()) {
            i++;
        }

        int j = 1 << i;
        this.setSlimeSize(j);
        return super.onInitialSpawn(difficultyInstance, iEntityLivingData);
    }

    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.SLIME;
    }

    protected void setSlimeSize(int experienceValueValue) {
        this.dataWatcher.updateObject(16, (byte) experienceValueValue);
        this.setSize(0.51000005F * experienceValueValue, 0.51000005F * experienceValueValue);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
                .setBaseValue(experienceValueValue * experienceValueValue);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
                .setBaseValue(0.2F + 0.1F * experienceValueValue);
        this.setHealth(this.getMaxHealth());
        this.experienceValue = experienceValueValue;
    }

    protected boolean canDamagePlayer() {
        return this.getSlimeSize() > 1;
    }

    protected float getSoundVolume() {
        return 0.4F * this.getSlimeSize();
    }

    public void onDataWatcherUpdate(int i) {
        if (i == 16) {
            int j = this.getSlimeSize();
            this.setSize(0.51000005F * j, 0.51000005F * j);
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;
            if (this.isInWater() && this.rand.nextInt(20) == 0) {
                this.resetHeight();
            }
        }

        super.onDataWatcherUpdate(i);
    }

    public boolean getCanSpawnHere() {
        Chunk chunkFromBlockCoords =
                this.world.getChunkFromBlockCoords(
                        new BlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ)));
        if (this.world.getWorldInfo().getTerrainType() == WorldType.FLAT
                && this.rand.nextInt(4) != 1) {
            return false;
        }

        if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            BiomeGenBase biomeGenForCoords =
                    this.world.getBiomeGenForCoords(
                            new BlockPos(
                                    MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ)));
            if (biomeGenForCoords == BiomeGenBase.swampland
                    && this.posY > 50.0
                    && this.posY < 70.0
                    && this.rand.nextFloat() < 0.5F
                    && this.rand.nextFloat() < this.world.getCurrentMoonPhaseFactor()
                    && this.world.getLightFromNeighbors(new BlockPos(this))
                            <= this.rand.nextInt(8)) {
                return super.getCanSpawnHere();
            }

            if (this.rand.nextInt(10) == 0) {

                if (chunkFromBlockCoords.getRandomWithSeed(987234911L).nextInt(10) == 0
                        && this.posY < 40.0) {
                    return super.getCanSpawnHere();
                }
            }
        }

        return false;
    }

    public SlimeEntity(World worldValue) {
        super(worldValue);
        this.moveHelper = new EntitySlimeSlimeMoveHelper(this);
        this.tasks.addTask(1, new EntitySlimeAISlimeFloat(this));
        this.tasks.addTask(2, new EntitySlimeAISlimeAttack(this));
        this.tasks.addTask(3, new EntitySlimeAISlimeFaceRandom(this));
        this.tasks.addTask(5, new EntitySlimeAISlimeHop(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
        this.targetTasks.addTask(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
    }

    protected boolean makesSoundOnJump() {
        return this.getSlimeSize() > 0;
    }

    protected void alterSquishAmount() {
        this.squishAmount *= 0.6F;
    }

    public float getEyeHeight() {
        return 0.625F * this.height;
    }

    protected String getJumpSound() {
        return "mob.slime." + (this.getSlimeSize() > 1 ? "big" : "small");
    }

    public int getVerticalFaceSpeed() {
        return 0;
    }

    protected int getAttackStrength() {
        return this.getSlimeSize();
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        int integer = compoundTag.getInteger("Size");
        if (integer < 0) {
            integer = 0;
        }

        this.setSlimeSize(integer + 1);
        this.wasOnGround = compoundTag.getBoolean("wasOnGround");
    }

    protected Item getDropItem() {
        return this.getSlimeSize() == 1 ? Items.slime_ball : null;
    }

    public void setDead() {
        int i1 = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        int j = this.getSlimeSize();
        int i = i1;
        if (!this.world.isRemote && j > 1 && this.getHealth() <= 0.0F) {
            int k = 2 + this.rand.nextInt(3);
            int l = 0;

            while (l < k) {
                float f = (l % 2 - 0.5F) * j / 4.0F;
                float f1 = (l / 2 - 0.5F) * j / 4.0F;
                SlimeEntity slimeEntity = this.createInstance();
                if (this.jvm171_d_45ffeb83d14f3c()) {
                    slimeEntity.setCustomNameTag(this.getCustomNameTag());
                }

                if (this.isNoDespawnRequired()) {
                    slimeEntity.enablePersistence();
                }

                slimeEntity.setSlimeSize(j / 2);
                slimeEntity.setLocationAndAngles(
                        this.posX + f,
                        this.posY + 0.5,
                        this.posZ + f1,
                        this.rand.nextFloat() * 360.0F,
                        0.0F);
                this.world.spawnEntityInWorld(slimeEntity);
                l++;
                if (i != 0) {
                    break;
                }
            }
        }

        super.setDead();
    }

    protected boolean makesSoundOnLand() {
        return this.getSlimeSize() > 2;
    }

    public void onCollideWithPlayer(PlayerEntity playerEntity) {
        if (this.canDamagePlayer()) {
            this.func_175451_e(playerEntity);
        }
    }

    public void applyEntityCollision(Entity entity) {
        super.applyEntityCollision(entity);
        if (entity instanceof EntityIronGolem && this.canDamagePlayer()) {
            this.func_175451_e((LivingEntity) entity);
        }
    }

    protected int getJumpDelay() {
        return this.rand.nextInt(20) + 10;
    }

    protected SlimeEntity createInstance() {
        return new SlimeEntity(this.world);
    }

    protected void func_175451_e(LivingEntity livingEntity) {
        int slimeSize = this.getSlimeSize();
        if (this.canEntityBeSeen(livingEntity)
                && this.getDistanceSqToEntity(livingEntity) < 0.6 * slimeSize * 0.6 * slimeSize
                && livingEntity.attackEntityFrom(
                        DamageSource.causeMobDamage(this), this.getAttackStrength())) {
            this.playSound(
                    "mob.attack",
                    1.0F,
                    (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.applyEnchantments(this, livingEntity);
        }
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) 1);
    }

    public int getSlimeSize() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    protected void jump() {
        this.motionY = 0.42F;
        this.isAirBorne = true;
    }

    public void onUpdate() {
        int i = EntitySpiderGroupData.method_7eec03da329a017ec143();
        if (!this.world.isRemote
                && this.world.getDifficulty() == EnumDifficulty.PEACEFUL
                && this.getSlimeSize() > 0) {
            this.isDead = true;
        }

        {
            this.squishFactor = this.squishFactor + (this.squishAmount - this.squishFactor) * 0.5F;
            this.prevSquishFactor = this.squishFactor;
            super.onUpdate();
            if (this.onGround && !this.wasOnGround) {
                int j = this.getSlimeSize();
                int k = 0;

                while (k < j * 8) {
                    float f = this.rand.nextFloat() * (float) Math.PI * 2.0F;
                    float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
                    float f2 = MathHelper.sin(f) * j * 0.5F * f1;
                    float f3 = MathHelper.cos(f) * j * 0.5F * f1;
                    World worldValue = this.world;
                    EnumParticleTypes enumParticleTypes = this.getParticleType();
                    double d0 = this.posX + f2;
                    double d1 = this.posZ + f3;
                    worldValue.spawnParticle(
                            enumParticleTypes,
                            d0,
                            this.getEntityBoundingBox().minY,
                            d1,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                    k++;
                    if (i == 0) {
                        break;
                    }
                }

                if (this.makesSoundOnLand()) {
                    this.playSound(
                            this.getJumpSound(),
                            this.getSoundVolume(),
                            ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
                }

                this.squishAmount = -0.5F;
                if (!(i != 0)) {

                    if (!this.onGround && this.wasOnGround) {
                        this.squishAmount = 1.0F;
                    }
                }
            } else {

                if (!this.onGround && this.wasOnGround) {
                    this.squishAmount = 1.0F;
                }
            }
        }

        this.wasOnGround = this.onGround;
        this.alterSquishAmount();
    }

    protected String getDeathSound() {
        return "mob.slime." + (this.getSlimeSize() > 1 ? "big" : "small");
    }


    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setInteger("Size", this.getSlimeSize() - 1);
        compoundTag.setBoolean("wasOnGround", this.wasOnGround);
    }

    protected String getHurtSound() {
        return "mob.slime." + (this.getSlimeSize() > 1 ? "big" : "small");
    }
}
