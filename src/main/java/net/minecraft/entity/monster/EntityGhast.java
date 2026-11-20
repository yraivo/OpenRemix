package net.minecraft.entity.monster;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityGhast extends EntityFlying implements IMob {

    private int explosionStrength = 1;

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0);
    }

    public boolean isAttacking() {
        return this.dataWatcher.getWatchableObjectByte(16) != 0;
    }

    public boolean getCanSpawnHere() {
        return this.rand.nextInt(20) == 0
                && super.getCanSpawnHere()
                && this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setInteger("ExplosionPower", this.explosionStrength);
    }

    protected String getDeathSound() {
        return "mob.ghast.death";
    }

    public float getEyeHeight() {
        return 2.6F;
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        if (compoundTag.hasKey("ExplosionPower", 99)) {
            this.explosionStrength = compoundTag.getInteger("ExplosionPower");
        }
    }

    protected String getHurtSound() {
        return "mob.ghast.scream";
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        } else if ("fireball".equals(damageSource.getDamageType())
                && damageSource.getEntity() instanceof PlayerEntity) {
            super.attackEntityFrom(damageSource, 1000.0F);
            ((PlayerEntity) damageSource.getEntity()).triggerAchievement(AchievementList.ghast);
            return true;
        } else {
            return super.attackEntityFrom(damageSource, f);
        }
    }

    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
    }

    public int getFireballStrength() {
        return this.explosionStrength;
    }


    protected Item getDropItem() {
        return Items.gunpowder;
    }

    protected String getLivingSound() {
        return "mob.ghast.moan";
    }

    public void setAttacking(boolean flag) {
        this.dataWatcher.updateObject(16, (byte) (flag ? 1 : 0));
    }

    protected float getSoundVolume() {
        return 10.0F;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) 0);
    }

    public EntityGhast(World worldValue) {
        super(worldValue);
        this.setSize(4.0F, 4.0F);
        this.isImmuneToFire = true;
        this.experienceValue = 5;
        this.moveHelper = new EntityGhastGhastMoveHelper(this);
        this.tasks.addTask(5, new EntityGhastAIRandomFly(this));
        this.tasks.addTask(7, new EntityGhastAILookAround(this));
        this.tasks.addTask(7, new EntityGhastAIFireballAttack(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }

    public int L() {
        return 1;
    }

    protected void dropFewItems(boolean flag, int i) {
        int i1 = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        int k = this.rand.nextInt(2) + this.rand.nextInt(1 + i);
        int l = 0;
        int j = i1;

        while (l < k) {
            this.dropItem(Items.ghast_tear, 1);
            l++;
            if (j != 0) {
                break;
            }
        }

        k = this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        l = 0;

        while (l < k) {
            this.dropItem(Items.gunpowder, 1);
            l++;
            if (j != 0) {
                break;
            }
        }
    }
}
