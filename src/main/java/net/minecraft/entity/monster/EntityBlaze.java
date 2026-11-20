package net.minecraft.entity.monster;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityBlaze extends MonsterEntity {

    private int heightOffsetUpdateTime;

    private float heightOffset = 0.5F;

    protected String getLivingSound() {
        return "mob.blaze.breathe";
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23F);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0);
    }

    protected void dropFewItems(boolean flag, int i) {
        int j = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        if (flag) {
            int k = this.rand.nextInt(2 + i);
            int l = 0;

            while (l < k) {
                this.dropItem(Items.blaze_rod, 1);
                l++;
                if (j != 0) {
                    break;
                }
            }
        }
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte) 0));
    }

    protected String getDeathSound() {
        return "mob.blaze.death";
    }

    protected Item getDropItem() {
        return Items.blaze_rod;
    }

    public void fall(float f, float f1) {}

    public boolean isBurning() {
        return this.func_70845_n();
    }

    public boolean func_70845_n() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public int getBrightnessForRender(float f) {
        return 15728880;
    }

    public void onLivingUpdate() {
        int i = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }

        if (this.world.isRemote) {
            if (this.rand.nextInt(24) == 0 && !this.isSlient()) {
                this.world.playSound(
                        this.posX + 0.5,
                        this.posY + 0.5,
                        this.posZ + 0.5,
                        "fire.fire",
                        1.0F + this.rand.nextFloat(),
                        this.rand.nextFloat() * 0.7F + 0.3F,
                        false);
            }

            int j = 0;

            while (j < 2) {
                this.world.spawnParticle(
                        EnumParticleTypes.SMOKE_LARGE,
                        this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                        this.posY + this.rand.nextDouble() * this.height,
                        this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                        0.0,
                        0.0,
                        0.0,
                        new int[0]);
                j++;
                if (i != 0) {
                    break;
                }
            }
        }

        super.onLivingUpdate();
    }

    protected boolean isValidLightLevel() {
        return true;
    }

    public EntityBlaze(World worldValue) {
        super(worldValue);
        this.isImmuneToFire = true;
        this.experienceValue = 10;
        this.tasks.addTask(4, new EntityBlazeAIFireballAttack(this));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(
                2, new EntityAINearestAttackableTarget(this, PlayerEntity.class, true));
    }

    protected String getHurtSound() {
        return "mob.blaze.hit";
    }

    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0F);
        }

        this.heightOffsetUpdateTime--;
        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5F + (float) this.rand.nextGaussian() * 3.0F;
        }

        LivingEntity livingEntity = this.getAttackTarget();
        if (livingEntity != null
                && livingEntity.posY + livingEntity.getEyeHeight()
                        > this.posY + this.getEyeHeight() + this.heightOffset) {
            this.motionY = this.motionY + (0.3F - this.motionY) * 0.3F;
            this.isAirBorne = true;
        }

        super.updateAITasks();
    }


    public void setOnFire(boolean flag) {
        byte b0;
        {
            int j = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
            b0 = this.dataWatcher.getWatchableObjectByte(16);
            int i = j;
            if (flag) {
                b0 = (byte) (b0 | 1);
                if (!(i == 0)) {

                    b0 = (byte) (b0 & -2);
                }
            } else {

                b0 = (byte) (b0 & -2);
            }
        }

        this.dataWatcher.updateObject(16, b0);
    }

    public float getBrightness(float f) {
        return 1.0F;
    }
}
