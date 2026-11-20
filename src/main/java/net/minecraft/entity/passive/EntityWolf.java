package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWolf extends EntityTameable {
    private boolean isShaking;
    private float timeWolfIsShaking;

    private float headRotationCourseOld;
    private float prevTimeWolfIsShaking;
    private float headRotationCourse;

    private boolean isWet;
    private static String[] packedTextFragments;

    protected void applyEntityAttributes() {
        {
            String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
            super.applyEntityAttributes();
            String textValue = text;
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3F);
            if (this.isTamed()) {
                this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
                if (!(textValue == null)) {

                    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
                }
            } else {

                this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
            }
        }

        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, new Float(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte) 0));
        this.dataWatcher.addObject(20, new Byte((byte) EnumDyeColor.RED.getMetadata()));
    }

    public void setCollarColor(EnumDyeColor enumDyeColor) {
        this.dataWatcher.updateObject(20, (byte) (enumDyeColor.getDyeColorDamage() & 15));
    }

    public boolean interact(PlayerEntity playerEntity) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        String textValue = text;
        if (this.isTamed()) {
            label80:
            if (itemStack != null) {
                if (itemStack.getItem() instanceof ItemFood) {
                    ItemFood item = (ItemFood) itemStack.getItem();
                    if (item.isWolfsFavoriteMeat()
                            && this.dataWatcher.getWatchableObjectFloat(18) < 20.0F) {
                        if (!playerEntity.capabilities.isCreativeMode) {
                            itemStack.stackSize--;
                        }

                        this.heal(item.getHealAmount(itemStack));
                        if (itemStack.stackSize <= 0) {
                            playerEntity.inventory.setInventorySlotContents(
                                    playerEntity.inventory.currentItem, (ItemStack) null);
                        }

                        return true;
                    }

                    if (textValue == null) {
                        break label80;
                    }
                }

                if (itemStack.getItem() == Items.dye) {
                    EnumDyeColor enumDyeColor = EnumDyeColor.byDyeDamage(itemStack.getItemDamage());
                    if (enumDyeColor != this.getCollarColor()) {
                        this.setCollarColor(enumDyeColor);
                        if (!playerEntity.capabilities.isCreativeMode
                                && --itemStack.stackSize <= 0) {
                            playerEntity.inventory.setInventorySlotContents(
                                    playerEntity.inventory.currentItem, (ItemStack) null);
                        }

                        return true;
                    }
                }
            }

            if (!this.isOwner(playerEntity)
                    || this.world.isRemote
                    || this.isBreedingItem(itemStack)) {
                return super.interact(playerEntity);
            }

            this.aiSit.setSitting(!this.isSitting());
            this.isJumping = false;
            this.navigator.clearPathEntity();
            this.setAttackTarget((LivingEntity) null);
            if (textValue == null) {
                return super.interact(playerEntity);
            }
        }

        if (itemStack != null && itemStack.getItem() == Items.bone && !this.isAngry()) {
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
                    this.navigator.clearPathEntity();
                    this.setAttackTarget((LivingEntity) null);
                    this.aiSit.setSitting(true);
                    this.setHealth(20.0F);
                    this.setOwnerId(playerEntity.getUniqueId().toString());
                    this.playTameEffect(true);
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

    protected boolean canDespawn() {
        return !this.isTamed() && this.ticksExisted > 2400;
    }

    public boolean isAngry() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    protected void updateAITasks() {
        this.dataWatcher.updateObject(18, this.getHealth());
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setBoolean("Angry", this.isAngry());
        compoundTag.setByte("CollarColor", (byte) this.getCollarColor().getDyeColorDamage());
    }

    public float getTailRotation() {
        return this.isAngry()
                ? 1.5393804F
                : (this.isTamed()
                        ? (0.55F - (20.0F - this.dataWatcher.getWatchableObjectFloat(18)) * 0.02F)
                                * (float) java.lang.Math.PI
                        : (float) (java.lang.Math.PI / 5));
    }

    public void setBegging(boolean flag) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (flag) {
            this.dataWatcher.updateObject(19, (byte) 1);
            if (text == null) {
                return;
            }
        }

        this.dataWatcher.updateObject(19, (byte) 0);
    }

    static {
        net.minecraft.entity.passive.EntityWolf.packedTextFragments = new String[3];
        net.minecraft.entity.passive.EntityWolf.packedTextFragments[0] = new String("WhTYtc");
    }

    protected Item getDropItem() {
        return Item.getItemById(-1);
    }

    public boolean shouldAttackEntity(LivingEntity livingEntity, LivingEntity livingEntityValue) {
        if (!(livingEntity instanceof EntityCreeper) && !(livingEntity instanceof EntityGhast)) {
            if (livingEntity instanceof EntityWolf) {
                EntityWolf entityWolf = (EntityWolf) livingEntity;
                if (entityWolf.isTamed() && entityWolf.func_180492_cm() == livingEntityValue) {
                    return false;
                }
            }

            return livingEntity instanceof PlayerEntity
                            && livingEntityValue instanceof PlayerEntity
                            && !((PlayerEntity) livingEntityValue)
                                    .canAttackPlayer((PlayerEntity) livingEntity)
                    ? false
                    : !(livingEntity instanceof EntityHorse)
                            || !((EntityHorse) livingEntity).isTame();
        } else {
            return false;
        }
    }

    public EntityWolf method_990cdb53cf9ac12be90c(EntityAgeable entityAgeable) {
        net.minecraft.entity.passive.EntityWolf entityWolf =
                new net.minecraft.entity.passive.EntityWolf(this.world);
        java.lang.String text = this.getOwnerId();
        if (text != null && text.trim().length() > 0) {
            entityWolf.setOwnerId(text);
            entityWolf.setTamed(true);
        }

        return entityWolf;
    }

    public void setAttackTarget(LivingEntity livingEntity) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        super.setAttackTarget(livingEntity);
        String textValue = text;
        if (livingEntity == null) {
            this.setAngry(false);
            if (textValue == null) {
                return;
            }
        }

        if (!this.isTamed()) {
            this.setAngry(true);
        }
    }

    public float getShakeAngle(float f, float f1) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        float f2 =
                (this.prevTimeWolfIsShaking
                                + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * f
                                + f1)
                        / 1.8F;
        String textValue = text;
        if (f2 < 0.0F) {
            f2 = 0.0F;
            if (textValue == null) {
                return MathHelper.sin(f2 * (float) Math.PI)
                        * MathHelper.sin(f2 * (float) Math.PI * 11.0F)
                        * 0.15F
                        * (float) Math.PI;
            }
        }

        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        return MathHelper.sin(f2 * (float) Math.PI)
                * MathHelper.sin(f2 * (float) Math.PI * 11.0F)
                * 0.15F
                * (float) Math.PI;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.world.isRemote
                && this.isWet
                && !this.isShaking
                && !this.hasPath()
                && this.onGround) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            this.world.setEntityState(this, (byte) 8);
        }

        if (!this.world.isRemote && this.getAttackTarget() == null && this.isAngry()) {
            this.setAngry(false);
        }
    }

    protected String getHurtSound() {
        return "mob.wolf.hurt";
    }

    public EntityWolf(World worldValue) {
        super(worldValue);
        EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        this.setSize(0.6F, 0.8F);
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0, true));
        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0, 10.0F, 2.0F));
        this.tasks.addTask(6, new EntityAIMate(this, 1.0));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIBeg(this, 8.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(
                4,
                new EntityAITargetNonTamed(
                        this, AnimalEntity.class, false, new EntityPredicate(this)));
        this.targetTasks.addTask(
                5, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, false));
        this.setTamed(false);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            EntityRabbitRabbitJumpHelper.b(packedTextFragments[0]);
        }
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    public boolean isWolfWet() {
        return this.isWet;
    }

    protected String getLivingSound() {
        return this.isAngry()
                ? "mob.wolf.growl"
                : (this.rand.nextInt(3) == 0
                        ? (this.isTamed() && this.dataWatcher.getWatchableObjectFloat(18) < 10.0F
                                ? "mob.wolf.whine"
                                : "mob.wolf.panting")
                        : "mob.wolf.bark");
    }

    protected void playStepSound(BlockPos blockPos, Block block) {
        this.playSound("mob.wolf.step", 0.15F, 1.0F);
    }

    public boolean canMateWith(AnimalEntity animalEntity) {
        if (animalEntity == this) {
            return false;
        }

        if (!this.isTamed()) {
            return false;
        }

        if (!(animalEntity instanceof net.minecraft.entity.passive.EntityWolf)) {
            return false;
        }

        net.minecraft.entity.passive.EntityWolf entityWolf =
                (net.minecraft.entity.passive.EntityWolf) animalEntity;
        return !entityWolf.isTamed()
                ? false
                : (entityWolf.isSitting() ? false : this.isInLove() && entityWolf.isInLove());
    }

    public void handleHealthUpdate(byte b0) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (b0 == 8) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            if (text == null) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.setAngry(compoundTag.getBoolean("Angry"));
        if (compoundTag.hasKey("CollarColor", 99)) {
            this.setCollarColor(EnumDyeColor.byDyeDamage(compoundTag.getByte("CollarColor")));
        }
    }

    public boolean isBegging() {
        return this.dataWatcher.getWatchableObjectByte(19) == 1;
    }

    public void onUpdate() {
        String text;
        {
            String textValue = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
            super.onUpdate();
            text = textValue;
            this.headRotationCourseOld = this.headRotationCourse;
            if (this.isBegging()) {
                this.headRotationCourse =
                        this.headRotationCourse + (1.0F - this.headRotationCourse) * 0.4F;
                if (!(text == null)) {

                    this.headRotationCourse =
                            this.headRotationCourse + (0.0F - this.headRotationCourse) * 0.4F;
                }
            } else {

                this.headRotationCourse =
                        this.headRotationCourse + (0.0F - this.headRotationCourse) * 0.4F;
            }
        }

        if (this.isWet()) {
            this.isWet = true;
            this.isShaking = false;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            if (text == null) {
                return;
            }
        }

        if ((this.isWet || this.isShaking) && this.isShaking) {
            if (this.timeWolfIsShaking == 0.0F) {
                this.playSound(
                        "mob.wolf.shake",
                        this.getSoundVolume(),
                        (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }

            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05F;
            if (this.prevTimeWolfIsShaking >= 2.0F) {
                this.isWet = false;
                this.isShaking = false;
                this.prevTimeWolfIsShaking = 0.0F;
                this.timeWolfIsShaking = 0.0F;
            }

            if (this.timeWolfIsShaking > 0.4F) {
                float minY = (float) this.getEntityBoundingBox().minY;
                int i =
                        (int)
                                (MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float) Math.PI)
                                        * 7.0F);
                int j = 0;

                while (j < i) {
                    float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
                    this.world.spawnParticle(
                            EnumParticleTypes.WATER_SPLASH,
                            this.posX + f1,
                            minY + 0.8F,
                            this.posZ + f2,
                            this.motionX,
                            this.motionY,
                            this.motionZ,
                            new int[0]);
                    j++;
                    if (text != null) {
                        break;
                    }
                }
            }
        }
    }

    public float getInterestedAngle(float p171_0) {
        return (this.headRotationCourseOld
                        + (this.headRotationCourse - this.headRotationCourseOld) * p171_0)
                * 0.15F
                * (float) java.lang.Math.PI;
    }

    public boolean allowLeashing() {
        return !this.isAngry() && super.allowLeashing();
    }

    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        return this.method_990cdb53cf9ac12be90c(entityAgeable);
    }

    public boolean attackEntityAsMob(Entity entity) {
        boolean flag =
                entity.attackEntityFrom(
                        DamageSource.causeMobDamage(this),
                        (int)
                                this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
                                        .getAttributeValue());
        if (flag) {
            this.applyEnchantments(this, entity);
        }

        return flag;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float p171_1) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        net.minecraft.entity.Entity entity = damageSource.getEntity();
        this.aiSit.setSitting(false);
        if (entity != null
                && !(entity instanceof net.minecraft.entity.player.PlayerEntity)
                && !(entity instanceof net.minecraft.entity.projectile.EntityArrow)) {
            p171_1 = (p171_1 + 1.0F) / 2.0F;
        }

        return super.attackEntityFrom(damageSource, p171_1);
    }

    public int L() {
        return 8;
    }

    public void setTamed(boolean flag) {
        {
            String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
            super.setTamed(flag);
            String textValue = text;
            if (flag) {
                this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
                if (!(textValue == null)) {

                    this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
                }
            } else {

                this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
            }
        }

        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
    }

    public int getVerticalFaceSpeed() {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    public void setAngry(boolean flag) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        String textValue = text;
        if (flag) {
            this.dataWatcher.updateObject(16, (byte) (b0 | 2));
            if (textValue == null) {
                return;
            }
        }

        this.dataWatcher.updateObject(16, (byte) (b0 & -3));
    }

    public float getShadingWhileWet(float p171_0) {
        return 0.75F
                + (this.prevTimeWolfIsShaking
                                + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p171_0)
                        / 2.0F
                        * 0.25F;
    }

    public boolean isBreedingItem(ItemStack itemStack) {
        return itemStack == null
                ? false
                : (!(itemStack.getItem() instanceof ItemFood)
                        ? false
                        : ((ItemFood) itemStack.getItem()).isWolfsFavoriteMeat());
    }

    protected String getDeathSound() {
        return "mob.wolf.death";
    }

    public EnumDyeColor getCollarColor() {
        return EnumDyeColor.byDyeDamage(this.dataWatcher.getWatchableObjectByte(20) & 15);
    }

    public float getEyeHeight() {
        return this.height * 0.8F;
    }

    // Helper used only by this class.
    public static class EntityPredicate implements com.google.common.base.Predicate {
        net.minecraft.entity.passive.EntityWolf bkU;

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }

        public EntityPredicate(net.minecraft.entity.passive.EntityWolf bkUValue) {
            this.bkU = bkUValue;
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity instanceof net.minecraft.entity.passive.EntitySheep
                    || entity instanceof net.minecraft.entity.passive.EntityRabbit;
        }
    }
}
