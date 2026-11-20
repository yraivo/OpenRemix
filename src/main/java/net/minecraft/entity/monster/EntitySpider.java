package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySpider extends MonsterEntity {

    protected String getDeathSound() {
        return "mob.spider.death";
    }

    public boolean isBesideClimbableBlock() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public float getEyeHeight() {
        return 0.65F;
    }

    public void setInWeb() {}

    protected Item getDropItem() {
        return Items.string;
    }

    public EntitySpider(World worldValue) {
        super(worldValue);
        this.setSize(1.4F, 0.9F);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.field_175455_a);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntitySpiderAISpiderAttack(this, PlayerEntity.class));
        this.tasks.addTask(4, new EntitySpiderAISpiderAttack(this, EntityIronGolem.class));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntitySpiderAISpiderTarget(this, PlayerEntity.class));
        this.targetTasks.addTask(3, new EntitySpiderAISpiderTarget(this, EntityIronGolem.class));
    }


    protected PathNavigate getNewNavigator(World worldValue) {
        return new PathNavigateClimber(this, worldValue);
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    protected void playStepSound(BlockPos blockPos, Block block) {
        this.playSound("mob.spider.step", 0.15F, 1.0F);
    }

    public void setBesideClimbableBlock(boolean flag) {
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

    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    public IEntityLivingData onInitialSpawn(
            DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
        Object object = super.onInitialSpawn(difficultyInstance, iEntityLivingData);
        if (this.world.rand.nextInt(100) == 0) {
            EntitySkeleton entitySkeleton = new EntitySkeleton(this.world);
            entitySkeleton.setLocationAndAngles(
                    this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            entitySkeleton.onInitialSpawn(difficultyInstance, (IEntityLivingData) null);
            this.world.spawnEntityInWorld(entitySkeleton);
            entitySkeleton.mountEntity(this);
        }

        if (object == null) {
            object = new EntitySpiderGroupData();
            if (this.world.getDifficulty() == EnumDifficulty.HARD
                    && this.world.rand.nextFloat()
                            < 0.1F * difficultyInstance.getClampedAdditionalDifficulty()) {
                ((EntitySpiderGroupData) object).method_5cca81c7e4d1b0803e89(this.world.rand);
            }
        }

        if (object instanceof EntitySpiderGroupData) {
            int i = ((EntitySpiderGroupData) object).potionEffectId;
            if (i > 0 && Potion.potionTypes[i] != null) {
                this.addPotionEffect(new PotionEffect(i, Integer.MAX_VALUE));
            }
        }

        return (IEntityLivingData) object;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3F);
    }

    protected void dropFewItems(boolean flag, int i) {
        super.dropFewItems(flag, i);
        if (flag && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + i) > 0)) {
            this.dropItem(Items.spider_eye, 1);
        }
    }

    protected String getLivingSound() {
        return "mob.spider.say";
    }

    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    protected String getHurtSound() {
        return "mob.spider.say";
    }

    public boolean isPotionApplicable(PotionEffect potionEffect) {
        return potionEffect.getPotionID() == Potion.POISON.id
                ? false
                : super.isPotionApplicable(potionEffect);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte) 0));
    }
}
