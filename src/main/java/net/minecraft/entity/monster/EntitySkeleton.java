package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;

import java.util.Calendar;

public class EntitySkeleton extends MonsterEntity implements IRangedAttackMob {

    private EntityAIArrowAttack aiArrowAttack;

    private EntityAIAttackOnCollide aiAttackOnCollide;

    public boolean attackEntityAsMob(Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (this.getSkeletonType() == 1 && entity instanceof LivingEntity) {
                ((LivingEntity) entity).addPotionEffect(new PotionEffect(Potion.WITHER.id, 200));
            }

            return true;
        } else {
            return false;
        }
    }

    protected Item getDropItem() {
        return Items.arrow;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(13, new Byte((byte) 0));
    }

    protected void dropFewItems(boolean flag, int i) {
        int j;
        label39:
        {
            j = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
            if (this.getSkeletonType() == 1) {
                int k = this.rand.nextInt(3 + i) - 1;
                int l = 0;

                do {
                    if (l >= k) {
                        break label39;
                    }

                    this.dropItem(Items.coal, 1);
                    l++;
                } while (j == 0);
            }

            int i1 = this.rand.nextInt(3 + i);
            int k1 = 0;

            while (k1 < i1) {
                this.dropItem(Items.arrow, 1);
                k1++;
                if (j != 0) {
                    break;
                }
            }
        }

        int j1 = this.rand.nextInt(3 + i);
        int l1 = 0;

        while (l1 < j1) {
            this.dropItem(Items.bone, 1);
            l1++;
            if (j != 0) {
                break;
            }
        }
    }


    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setByte("SkeletonType", (byte) this.getSkeletonType());
    }

    protected String getHurtSound() {
        return "mob.skeleton.hurt";
    }

    protected String getDeathSound() {
        return "mob.skeleton.death";
    }

    public void setSkeletonType(int i) {
        int k = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        this.dataWatcher.updateObject(13, (byte) i);
        int j = k;
        this.isImmuneToFire = remix.compat.compiler.JvmBooleanSlots.fromInt((i == 1 ? 1 : 0));
        if (i == 1) {
            this.setSize(0.72F, 2.535F);
            if (j == 0) {
                return;
            }
        }

        this.setSize(0.6F, 1.95F);
    }

    public void onLivingUpdate() {
        if (this.world.isDaytime() && !this.world.isRemote) {
            float brightness = this.getBrightness(1.0F);
            BlockPos blockPos = new BlockPos(this.posX, Math.round(this.posY), this.posZ);
            if (brightness > 0.5F
                    && this.rand.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F
                    && this.world.isAgainstSky(blockPos)) {
                byte b0 = 1;
                ItemStack itemStack = this.getEquipmentInSlot(4);
                if (itemStack != null) {
                    if (itemStack.isItemStackDamageable()) {
                        itemStack.setItemDamage(itemStack.getMetadata() + this.rand.nextInt(2));
                        if (itemStack.getMetadata() >= itemStack.getMaxDamage()) {
                            this.renderBrokenItemStack(itemStack);
                            this.setCurrentItemOrArmor(4, (ItemStack) null);
                        }
                    }

                    b0 = 0;
                }

                if (b0 != 0) {
                    this.setFire(8);
                }
            }
        }

        if (this.world.isRemote && this.getSkeletonType() == 1) {
            this.setSize(0.72F, 2.535F);
        }

        super.onLivingUpdate();
    }

    protected void addRandomArmor() {
        if (this.getSkeletonType() == 1) {
            this.entityDropItem(new ItemStack(Items.skull, 1, 1), 0.0F);
        }
    }

    public float getEyeHeight() {
        return this.getSkeletonType() == 1 ? super.getEyeHeight() : 1.74F;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    public int getSkeletonType() {
        return this.dataWatcher.getWatchableObjectByte(13);
    }

    public IEntityLivingData onInitialSpawn(
            DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
        {
            int j = EntitySpiderGroupData.method_7eec03da329a017ec143();
            iEntityLivingData = super.onInitialSpawn(difficultyInstance, iEntityLivingData);
            int i = j;
            if (this.world.provider instanceof WorldProviderHell && this.getRNG().nextInt(5) > 0) {
                this.tasks.addTask(4, this.aiAttackOnCollide);
                this.setSkeletonType(1);
                this.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
                this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
                if (!(i != 0)) {

                    this.tasks.addTask(4, this.aiArrowAttack);
                    this.setEquipmentBasedOnDifficulty(difficultyInstance);
                    this.setEnchantmentBasedOnDifficulty(difficultyInstance);
                }
            } else {

                this.tasks.addTask(4, this.aiArrowAttack);
                this.setEquipmentBasedOnDifficulty(difficultyInstance);
                this.setEnchantmentBasedOnDifficulty(difficultyInstance);
            }
        }

        this.setCanPickUpLoot(
                this.rand.nextFloat()
                        < 0.55F * difficultyInstance.getClampedAdditionalDifficulty());
        if (this.getEquipmentInSlot(4) == null) {
            Calendar calendar = this.world.getCurrentDate();
            if (calendar.get(2) + 1 == 10
                    && calendar.get(5) == 31
                    && this.rand.nextFloat() < 0.25F) {
                this.setCurrentItemOrArmor(
                        4,
                        new ItemStack(
                                this.rand.nextFloat() < 0.1F
                                        ? Blocks.lit_pumpkin
                                        : Blocks.pumpkin));
                this.equipmentDropChances[4] = 0.0F;
            }
        }

        return iEntityLivingData;
    }

    public void updateRidden() {
        super.updateRidden();
        if (this.ridingEntity instanceof EntityCreature) {
            EntityCreature ridingEntityValue = (EntityCreature) this.ridingEntity;
            this.renderYawOffset = ridingEntityValue.renderYawOffset;
        }
    }

    public double getYOffset() {
        return super.getYOffset() - 0.5;
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        if (compoundTag.hasKey("SkeletonType", 99)) {
            byte b0 = compoundTag.getByte("SkeletonType");
            this.setSkeletonType(b0);
        }

        this.setCombatTask();
    }

    public EntitySkeleton(World worldValue) {
        this(worldValue, EntitySpiderGroupData.method_7eec03da329a017ec143());
    }

    private EntitySkeleton(World worldValue, int initialFlowToken) {
        super(worldValue);

        int j = initialFlowToken;

        this.aiArrowAttack = new EntityAIArrowAttack(this, 1.0, 20, 60, 15.0F);
        this.aiAttackOnCollide = new EntityAIAttackOnCollide(this, PlayerEntity.class, 1.2, false);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(2, this.field_175455_a);
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0));
        this.tasks.addTask(
                3, new EntityAIAvoidEntity(this, new EntityPredicate(this), 6.0F, 1.0, 1.2));
        this.tasks.addTask(4, new EntityAIWander(this, 1.0));
        int i = j;
        this.tasks.addTask(6, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(
                2, new EntityAINearestAttackableTarget(this, PlayerEntity.class, true));
        this.targetTasks.addTask(
                3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
        if (worldValue != null && !worldValue.isRemote) {
            this.setCombatTask();
        }

        if (i == 0) {
            Entity.setLegacyGlobalFlowMarker(new String[1]);
        }
    }

    protected String getLivingSound() {
        return "mob.skeleton.say";
    }

    public void setCombatTask() {
        this.tasks.removeTask(this.aiAttackOnCollide);
        int j = EntitySpiderGroupData.method_7eec03da329a017ec143();
        this.tasks.removeTask(this.aiArrowAttack);
        ItemStack itemStack = this.getHeldItem();
        int i = j;
        if (itemStack != null && itemStack.getItem() == Items.bow) {
            this.tasks.addTask(4, this.aiArrowAttack);
            if (i != 0) {
                return;
            }
        }

        this.tasks.addTask(4, this.aiAttackOnCollide);
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    public void attackEntityWithRangedAttack(LivingEntity livingEntity, float f) {
        EntityArrow entityArrow =
                new EntityArrow(
                        this.world,
                        this,
                        livingEntity,
                        1.6F,
                        14 - this.world.getDifficulty().getDifficultyId() * 4);
        int enchantmentLevel =
                EnchantmentHelper.getEnchantmentLevel(
                        Enchantment.power.effectId, this.getHeldItem());
        int j =
                EnchantmentHelper.getEnchantmentLevel(
                        Enchantment.punch.effectId, this.getHeldItem());
        entityArrow.setDamage(
                f * 2.0F
                        + this.rand.nextGaussian() * 0.25
                        + this.world.getDifficulty().getDifficultyId() * 0.11F);
        if (enchantmentLevel > 0) {
            entityArrow.setDamage(entityArrow.getDamage() + enchantmentLevel * 0.5 + 0.5);
        }

        if (j > 0) {
            entityArrow.setKnockbackStrength(j);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem())
                        > 0
                || this.getSkeletonType() == 1) {
            entityArrow.setFire(100);
        }

        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntityInWorld(entityArrow);
    }

    public void setCurrentItemOrArmor(int i, ItemStack itemStack) {
        super.setCurrentItemOrArmor(i, itemStack);
        if (!this.world.isRemote && i == 0) {
            this.setCombatTask();
        }
    }

    public void onDeath(DamageSource damageSource) {
        int j = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        super.onDeath(damageSource);
        int i = j;
        if (damageSource.getSourceOfDamage() instanceof EntityArrow
                && damageSource.getEntity() instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) damageSource.getEntity();
            double deltaPosX = playerEntity.posX - this.posX;
            double deltaPosZ = playerEntity.posZ - this.posZ;
            if (deltaPosX * deltaPosX + deltaPosZ * deltaPosZ >= 2500.0) {
                playerEntity.triggerAchievement(AchievementList.snipeSkeleton);
            }

            if (i == 0) {
                return;
            }
        }

        if (damageSource.getEntity() instanceof EntityCreeper
                && ((EntityCreeper) damageSource.getEntity()).getPowered()
                && ((EntityCreeper) damageSource.getEntity()).isAIEnabled()) {
            ((EntityCreeper) damageSource.getEntity()).func_175493_co();
            this.entityDropItem(
                    new ItemStack(Items.skull, 1, this.getSkeletonType() == 1 ? 1 : 0), 0.0F);
        }
    }

    protected void playStepSound(BlockPos blockPos, Block block) {
        this.playSound("mob.skeleton.step", 0.15F, 1.0F);
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        super.setEquipmentBasedOnDifficulty(difficultyInstance);
        this.setCurrentItemOrArmor(0, new ItemStack(Items.bow));
    }

    // Helper used only by this class.
    public static class EntityPredicate implements com.google.common.base.Predicate {
        net.minecraft.entity.monster.EntitySkeleton bsX;

        public EntityPredicate(net.minecraft.entity.monster.EntitySkeleton bsXValue) {
            this.bsX = bsXValue;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity instanceof net.minecraft.entity.passive.EntityWolf;
        }
    }
}
