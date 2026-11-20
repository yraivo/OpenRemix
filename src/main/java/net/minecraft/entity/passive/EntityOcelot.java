package net.minecraft.entity.passive;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAIOcelotSit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityOcelot extends EntityTameable {
    private EntityAITempt aiTempt;

    private EntityAIAvoidEntity avoidEntity;

    private static String[] packedTextFragments;

    public boolean interact(PlayerEntity playerEntity) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        String textValue = text;
        if (this.isTamed()) {
            if (!this.isOwner(playerEntity)
                    || this.world.isRemote
                    || this.isBreedingItem(itemStack)) {
                return super.interact(playerEntity);
            }

            this.aiSit.setSitting(!this.isSitting());
            if (textValue == null) {
                return super.interact(playerEntity);
            }
        }

        if (this.aiTempt.isRunning()
                && itemStack != null
                && itemStack.getItem() == Items.fish
                && playerEntity.getDistanceSqToEntity(this) < 9.0) {
            if (!playerEntity.capabilities.isCreativeMode) {
                itemStack.stackSize--;
            }

            if (itemStack.stackSize <= 0) {
                playerEntity.inventory.setInventorySlotContents(
                        playerEntity.inventory.currentItem, (ItemStack) null);
            }

            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0) {
                    this.setTamed(true);
                    this.setTameSkin(1 + this.world.rand.nextInt(3));
                    this.setOwnerId(playerEntity.getUniqueId().toString());
                    this.playTameEffect(true);
                    this.aiSit.setSitting(true);
                    this.world.setEntityState(this, (byte) 7);
                    if (textValue == null) {
                        return true;
                    }
                }

                this.playTameEffect(false);
                this.world.setEntityState(this, (byte) 6);
            }

            return true;
        } else {
            return super.interact(playerEntity);
        }
    }

    protected void dropFewItems(boolean flag, int i) {}

    protected String getHurtSound() {
        return "mob.cat.hitt";
    }

    protected String getLivingSound() {
        return this.isTamed()
                ? (this.isInLove()
                        ? "mob.cat.purr"
                        : (this.rand.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow"))
                : packedTextFragments[0];
    }

    public boolean canMateWith(AnimalEntity animalEntity) {
        if (animalEntity == this) {
            return false;
        }

        if (!this.isTamed()) {
            return false;
        }

        if (!(animalEntity instanceof EntityOcelot)) {
            return false;
        }

        EntityOcelot entityOcelot = (EntityOcelot) animalEntity;
        return !entityOcelot.isTamed() ? false : this.isInLove() && entityOcelot.isInLove();
    }

    public boolean getCanSpawnHere() {
        return this.world.rand.nextInt(3) != 0;
    }

    public void fall(float f, float f1) {}

    public IEntityLivingData onInitialSpawn(
            DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        iEntityLivingData = super.onInitialSpawn(difficultyInstance, iEntityLivingData);
        String textValue = text;
        if (this.world.rand.nextInt(7) == 0) {
            int i = 0;

            while (i < 2) {
                EntityOcelot entityOcelot = new EntityOcelot(this.world);
                entityOcelot.setLocationAndAngles(
                        this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                entityOcelot.setGrowingAge(-24000);
                this.world.spawnEntityInWorld(entityOcelot);
                i++;
                if (textValue != null) {
                    break;
                }
            }
        }

        return iEntityLivingData;
    }

    protected void setupTamedAI() {
        if (this.avoidEntity == null) {
            this.avoidEntity =
                    new EntityAIAvoidEntity(
                            this, new OcelotPlayerAvoidancePredicate(this), 16.0F, 0.8, 1.33);
        }

        this.tasks.removeTask(this.avoidEntity);
        if (!this.isTamed()) {
            this.tasks.addTask(4, this.avoidEntity);
        }
    }

    protected boolean canDespawn() {
        return !this.isTamed() && this.ticksExisted > 2400;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        this.aiSit.setSitting(false);
        return super.attackEntityFrom(damageSource, f);
    }

    public String getName() {
        return this.jvm171_d_45ffeb83d14f3c()
                ? this.getCustomNameTag()
                : (this.isTamed()
                        ? StatCollector.translateToLocal("entity.Cat.name")
                        : super.getName());
    }

    public boolean attackEntityAsMob(Entity entity) {
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public void updateAITasks() {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (this.getMoveHelper().isUpdating()) {
            label26:
            {
                double speed = this.getMoveHelper().getSpeed();
                if (speed == 0.6) {
                    this.setSneaking(true);
                    this.setSprinting(false);
                    if (text == null) {
                        break label26;
                    }
                }

                if (speed == 1.33) {
                    this.setSneaking(false);
                    this.setSprinting(true);
                    if (text == null) {
                        break label26;
                    }
                }

                this.setSneaking(false);
                this.setSprinting(false);
            }

            if (text == null) {
                return;
            }
        }

        this.setSneaking(false);
        this.setSprinting(false);
    }

    protected String getDeathSound() {
        return "mob.cat.hitt";
    }

    public EntityOcelot(World worldValue) {
        super(worldValue);
        this.setSize(0.6F, 0.7F);
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, this.aiTempt = new EntityAITempt(this, 0.6, Items.fish, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0, 10.0F, 5.0F));
        this.tasks.addTask(6, new EntityAIOcelotSit(this, 0.8));
        this.tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3F));
        this.tasks.addTask(8, new EntityAIOcelotAttack(this));
        this.tasks.addTask(9, new EntityAIMate(this, 0.8));
        this.tasks.addTask(10, new EntityAIWander(this, 0.8));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, PlayerEntity.class, 10.0F));
        this.targetTasks.addTask(
                1, new EntityAITargetNonTamed(this, EntityChicken.class, false, (Predicate) null));
    }

    public EntityOcelot func_180493_b(EntityAgeable entityAgeable) {
        EntityOcelot entityOcelot = new EntityOcelot(this.world);
        if (this.isTamed()) {
            entityOcelot.setOwnerId(this.getOwnerId());
            entityOcelot.setTamed(true);
            entityOcelot.setTameSkin(this.getTameSkin());
        }

        return entityOcelot;
    }

    public boolean handleLavaMovement() {
        if (this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this)
                && this.world.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()
                && !this.world.isAnyLiquid(this.getEntityBoundingBox())) {
            BlockPos blockPos =
                    new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
            if (blockPos.getY() < 63) {
                return false;
            }

            Block block = this.world.getBlockState(blockPos.offsetDown()).getBlock();
            if (block == Blocks.grass || block.getMaterial() == Material.leaves) {
                return true;
            }
        }

        return false;
    }

    public void setTamed(boolean flag) {
        super.setTamed(flag);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, (byte) 0);
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setInteger("CatType", this.getTameSkin());
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3F);
    }

    protected Item getDropItem() {
        return Items.leather;
    }

    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        return this.func_180493_b(entityAgeable);
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.setTameSkin(compoundTag.getInteger("CatType"));
    }

    static {
        net.minecraft.entity.passive.EntityOcelot.packedTextFragments = new String[3];
        net.minecraft.entity.passive.EntityOcelot.packedTextFragments[0] = new String("");
    }

    public void setTameSkin(int i) {
        this.dataWatcher.updateObject(18, (byte) i);
    }

    public boolean isBreedingItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() == Items.fish;
    }

    public int getTameSkin() {
        return this.dataWatcher.getWatchableObjectByte(18);
    }

    // Helper used only by this class.
    static class OcelotPlayerAvoidancePredicate implements com.google.common.base.Predicate {
        net.minecraft.entity.passive.EntityOcelot field_179875_a;

        OcelotPlayerAvoidancePredicate(net.minecraft.entity.passive.EntityOcelot bkPValue) {
            this.field_179875_a = bkPValue;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity instanceof net.minecraft.entity.player.PlayerEntity;
        }
    }
}
