package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityEndermite extends MonsterEntity {
    private int lifetime = 0;

    private boolean playerSpawned = false;

    protected boolean isValidLightLevel() {
        return true;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.lifetime = compoundTag.getInteger("Lifetime");
        this.playerSpawned = compoundTag.getBoolean("PlayerSpawned");
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setInteger("Lifetime", this.lifetime);
        compoundTag.setBoolean("PlayerSpawned", this.playerSpawned);
    }

    protected String getDeathSound() {
        return "mob.silverfish.kill";
    }

    protected Item getDropItem() {
        return null;
    }

    public void onLivingUpdate() {
        int k = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        super.onLivingUpdate();
        int i = k;
        if (this.world.isRemote) {
            int j = 0;

            while (j < 2) {
                this.world.spawnParticle(
                        EnumParticleTypes.PORTAL,
                        this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                        this.posY + this.rand.nextDouble() * this.height,
                        this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                        (this.rand.nextDouble() - 0.5) * 2.0,
                        -this.rand.nextDouble(),
                        (this.rand.nextDouble() - 0.5) * 2.0,
                        new int[0]);
                j++;
                if (i != 0) {
                    break;
                }
            }

            if (i == 0) {
                return;
            }
        }

        if (!this.isNoDespawnRequired()) {
            this.lifetime++;
        }

        if (this.lifetime >= 2400) {
            this.setDead();
        }
    }

    protected void playStepSound(BlockPos blockPos, Block block) {
        this.playSound("mob.silverfish.step", 0.15F, 1.0F);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
    }

    public void setSpawnedByPlayer(boolean bhaValue) {
        this.playerSpawned = bhaValue;
    }

    public boolean isSpawnedByPlayer() {
        return this.playerSpawned;
    }

    public float getEyeHeight() {
        return 0.1F;
    }

    protected String getHurtSound() {
        return "mob.silverfish.hit";
    }


    public void onUpdate() {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }

    public boolean getCanSpawnHere() {
        if (super.getCanSpawnHere()) {
            PlayerEntity playerEntity = this.world.getClosestPlayerToEntity(this, 5.0);
            return playerEntity == null;
        } else {
            return false;
        }
    }

    protected String getLivingSound() {
        return "mob.silverfish.say";
    }

    public EntityEndermite(World worldValue) {
        super(worldValue);
        this.experienceValue = 3;
        this.setSize(0.4F, 0.3F);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, PlayerEntity.class, 1.0, false));
        this.tasks.addTask(3, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(
                2, new EntityAINearestAttackableTarget(this, PlayerEntity.class, true));
    }
}
