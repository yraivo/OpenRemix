package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class EntitySilverfish extends MonsterEntity {

    private EntitySilverfishAISummonSilverfish summonSilverfish;

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void playStepSound(BlockPos blockPos, Block block) {
        this.playSound("mob.silverfish.step", 0.15F, 1.0F);
    }

    protected String getHurtSound() {
        return "mob.silverfish.hit";
    }

    protected String getDeathSound() {
        return "mob.silverfish.kill";
    }

    public float getEyeHeight() {
        return 0.1F;
    }

    public float getBlockPathWeight(BlockPos blockPos) {
        return this.world.getBlockState(blockPos.offsetDown()).getBlock() == Blocks.stone
                ? 10.0F
                : super.getBlockPathWeight(blockPos);
    }

    protected String getLivingSound() {
        return "mob.silverfish.say";
    }


    protected Item getDropItem() {
        return null;
    }

    public EntitySilverfish(World worldValue) {
        super(worldValue);
        this.setSize(0.4F, 0.3F);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, this.summonSilverfish = new EntitySilverfishAISummonSilverfish(this));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, PlayerEntity.class, 1.0, false));
        this.tasks.addTask(5, new EntitySilverfishAIHideInStone(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(
                2, new EntityAINearestAttackableTarget(this, PlayerEntity.class, true));
    }

    protected boolean isValidLightLevel() {
        return true;
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
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

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        if (damageSource instanceof EntityDamageSource || damageSource == DamageSource.magic) {
            this.summonSilverfish.func_179462_f();
        }

        return super.attackEntityFrom(damageSource, f);
    }
}
