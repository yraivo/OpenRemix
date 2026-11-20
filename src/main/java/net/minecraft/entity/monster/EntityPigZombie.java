package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityPigZombie extends EntityZombie {

    private static AttributeModifier ATTACK_SPEED_BOOST_MODIFIER;

    private static UUID ATTACK_SPEED_BOOST_MODIFIER_UUID;
    private int randomSoundDelay;
    private UUID angerTargetUUID;
    private int angerLevel;
    private static String[] packedTextFragments;

    public boolean interact(PlayerEntity playerEntity) {
        return false;
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.angerLevel = compoundTag.getShort("Anger");
        String text = compoundTag.getString("HurtBy");
        if (text.length() > 0) {
            this.angerTargetUUID = UUID.fromString(text);
            PlayerEntity beqValue = this.world.getPlayerEntityByUUID(this.angerTargetUUID);
            this.setRevengeTarget(beqValue);
            if (beqValue != null) {
                this.attackingPlayer = beqValue;
                this.recentlyHit = this.getRevengeTimer();
            }
        }
    }

    public EntityPigZombie(World worldValue) {
        super(worldValue);
        this.isImmuneToFire = true;
    }

    public boolean getCanSpawnHere() {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    static void access$000(EntityPigZombie entityPigZombie, Entity entity) {
        entityPigZombie.becomeAngryAt(entity);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(net.minecraft.entity.monster.EntityZombie.reinforcementChance).setBaseValue(0.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23F);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0);
    }

    public boolean isAngry() {
        return this.angerLevel > 0;
    }

    private void becomeAngryAt(Entity entity) {
        this.angerLevel = 400 + this.rand.nextInt(400);
        this.randomSoundDelay = this.rand.nextInt(40);
        if (entity instanceof LivingEntity) {
            this.setRevengeTarget((LivingEntity) entity);
        }
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        Entity entity = damageSource.getEntity();
        if (entity instanceof PlayerEntity) {
            this.becomeAngryAt(entity);
        }

        return super.attackEntityFrom(damageSource, f);
    }

    protected String getLivingSound() {
        return "mob.zombiepig.zpig";
    }

    public IEntityLivingData onInitialSpawn(
            DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
        super.onInitialSpawn(difficultyInstance, iEntityLivingData);
        this.setVillager(false);
        return iEntityLivingData;
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        this.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
    }

    static {
        net.minecraft.entity.monster.EntityPigZombie.packedTextFragments = new String[3];
        net.minecraft.entity.monster.EntityPigZombie.packedTextFragments[0] = new String("");
        net.minecraft.entity.monster.EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER_UUID =
                UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
        net.minecraft.entity.monster.EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER =
                new AttributeModifier(
                                net.minecraft.entity.monster.EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER_UUID,
                                "Attacking speed boost",
                                0.05,
                                0)
                        .setSaved(false);
    }

    public void setRevengeTarget(LivingEntity livingEntity) {
        super.setRevengeTarget(livingEntity);
        if (livingEntity != null) {
            this.angerTargetUUID = livingEntity.getUniqueId();
        }
    }

    public boolean handleLavaMovement() {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this)
                && this.world.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()
                && !this.world.isAnyLiquid(this.getEntityBoundingBox());
    }

    public void onUpdate() {
        super.onUpdate();
    }

    protected String getDeathSound() {
        return "mob.zombiepig.zpigdeath";
    }

    protected void dropFewItems(boolean flag, int i) {
        int i1 = EntitySpiderGroupData.method_7eec03da329a017ec143();
        int k = this.rand.nextInt(2 + i);
        int j = i1;
        int l = 0;

        while (l < k) {
            this.dropItem(Items.rotten_flesh, 1);
            l++;
            if (j == 0) {
                break;
            }
        }

        k = this.rand.nextInt(2 + i);
        l = 0;

        while (l < k) {
            this.dropItem(Items.gold_nugget, 1);
            l++;
            if (j == 0) {
                break;
            }
        }
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityPigZombieAIHurtByAggressor(this));
        this.targetTasks.addTask(2, new EntityPigZombieAITargetAggressor(this));
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        int j = EntitySpiderGroupData.method_7eec03da329a017ec143();
        super.writeEntityToNBT(compoundTag);
        compoundTag.setShort("Anger", (short) this.angerLevel);
        int i = j;
        if (this.angerTargetUUID != null) {
            compoundTag.setString("HurtBy", this.angerTargetUUID.toString());
            if (i != 0) {
                return;
            }
        }

        compoundTag.setString("HurtBy", packedTextFragments[0]);
    }

    protected void updateAITasks() {
        {
            int j = EntitySpiderGroupData.method_7eec03da329a017ec143();
            IAttributeInstance entityAttribute =
                    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            int i = j;
            if (this.isAngry()) {
                if (!this.isChild()
                        && !entityAttribute.hasModifier(
                                net.minecraft.entity.monster.EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER)) {
                    entityAttribute.applyModifier(
                            net.minecraft.entity.monster.EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER);
                }

                this.angerLevel--;
                if (!(i != 0)) {

                    if (entityAttribute.hasModifier(net.minecraft.entity.monster.EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER)) {
                        entityAttribute.removeModifier(net.minecraft.entity.monster.EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER);
                    }
                }
            } else {

                if (entityAttribute.hasModifier(net.minecraft.entity.monster.EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER)) {
                    entityAttribute.removeModifier(net.minecraft.entity.monster.EntityPigZombie.ATTACK_SPEED_BOOST_MODIFIER);
                }
            }
        }

        if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
            this.playSound(
                    "mob.zombiepig.zpigangry",
                    this.getSoundVolume() * 2.0F,
                    ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
        }

        if (this.angerLevel > 0 && this.angerTargetUUID != null && this.getAITarget() == null) {
            PlayerEntity beqValue = this.world.getPlayerEntityByUUID(this.angerTargetUUID);
            this.setRevengeTarget(beqValue);
            this.attackingPlayer = beqValue;
            this.recentlyHit = this.getRevengeTimer();
        }

        super.updateAITasks();
    }

    protected String getHurtSound() {
        return "mob.zombiepig.zpighurt";
    }

    protected void addRandomArmor() {
        this.dropItem(Items.gold_ingot, 1);
    }
}
