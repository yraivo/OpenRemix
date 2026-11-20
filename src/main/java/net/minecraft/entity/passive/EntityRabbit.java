package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityRabbit extends AnimalEntity {
    private EntityRabbitAIAvoidEntity aiAvoidWolves;
    private int field_175540_bm = 0;

    private boolean field_175537_bp;
    private int carrotTicks;
    private int currentMoveTypeDuration;

    private boolean field_175536_bo;
    private PlayerEntity field_175543_bt;
    private int field_175535_bn = 0;
    private EntityRabbitEnumMoveType moveType;

    public void setMoveType(EntityRabbitEnumMoveType bgkValue) {
        this.moveType = bgkValue;
    }

    private boolean isCarrotEaten() {
        return this.carrotTicks == 0;
    }

    private boolean isRabbitBreedingItem(Item item) {
        return item == Items.carrot
                || item == Items.golden_carrot
                || item == Item.getItemFromBlock(Blocks.yellow_flower);
    }

    public void onLivingUpdate() {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        super.onLivingUpdate();
        String textValue = text;
        if (this.field_175540_bm != this.field_175535_bn) {
            if (this.field_175540_bm == 0 && !this.world.isRemote) {
                this.world.setEntityState(this, (byte) 1);
            }

            this.field_175540_bm++;
            if (textValue == null) {
                return;
            }
        }

        if (this.field_175535_bn != 0) {
            this.field_175540_bm = 0;
            this.field_175535_bn = 0;
        }
    }

    protected void addRandomArmor() {
        this.entityDropItem(new ItemStack(Items.rabbit_foot, 1), 0.0F);
    }

    private void calculateRotationYaw(double d0, double d1) {
        this.rotationYaw =
                (float) (Math.atan2(d1 - this.posZ, d0 - this.posX) * 180.0 / Math.PI) - 90.0F;
    }

    public void spawnRunningParticles() {}

    protected float getJumpUpwardsMotion() {
        return this.moveHelper.isUpdating() && this.moveHelper.getY() > this.posY + 0.5
                ? 0.5F
                : this.moveType.aN();
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.setRabbitType(compoundTag.getInteger("RabbitType"));
        this.carrotTicks = compoundTag.getInteger("MoreCarrotTicks");
    }

    private void func_175518_cr() {
        ((EntityRabbitRabbitJumpHelper) this.jumpHelper).func_180066_a(true);
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        return this.isEntityInvulnerable(damageSource)
                ? false
                : super.attackEntityFrom(damageSource, f);
    }

    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        return this.func_175526_b(entityAgeable);
    }

    public void setMovementSpeed(double d0) {
        this.getNavigator().setSpeed(d0);
        this.moveHelper.setMoveTo(
                this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ(), d0);
    }

    protected int getMoveTypeDuration() {
        return this.moveType.getDuration();
    }

    protected void createEatingParticles() {
        this.world.spawnParticle(
                EnumParticleTypes.BLOCK_DUST,
                this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                this.posY + 0.5 + this.rand.nextFloat() * this.height,
                this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                0.0,
                0.0,
                0.0,
                new int[] {Block.getStateId(Blocks.carrots.getStateFromMeta(7))});
        this.carrotTicks = 100;
    }

    public void doMovementAction(EntityRabbitEnumMoveType entityRabbitEnumMoveType) {
        this.setJumping(true, entityRabbitEnumMoveType);
        this.field_175535_bn = entityRabbitEnumMoveType.func_180073_d();
        this.field_175540_bm = 0;
    }


    public boolean func_175523_cj() {
        return this.field_175536_bo;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setInteger("RabbitType", this.getRabbitType());
        compoundTag.setInteger("MoreCarrotTicks", this.carrotTicks);
    }

    public boolean isBreedingItem(ItemStack itemStack) {
        return itemStack != null && this.isRabbitBreedingItem(itemStack.getItem());
    }

    public float method_ad4859278b6f26197223(float f) {
        return this.field_175535_bn == 0 ? 0.0F : (this.field_175540_bm + f) / this.field_175535_bn;
    }

    public EntityRabbit func_175526_b(EntityAgeable entityAgeable) {
        EntityRabbit entityRabbit = new EntityRabbit(this.world);
        if (entityAgeable instanceof EntityRabbit) {
            entityRabbit.setRabbitType(
                    this.rand.nextBoolean()
                            ? this.getRabbitType()
                            : ((EntityRabbit) entityAgeable).getRabbitType());
        }

        return entityRabbit;
    }

    protected String getJumpingSound() {
        return "mob.rabbit.hop";
    }

    public EntityRabbit(World worldValue) {
        super(worldValue);
        this.field_175536_bo = false;
        this.field_175537_bp = false;
        this.currentMoveTypeDuration = 0;
        this.moveType = EntityRabbitEnumMoveType.HOP;
        this.carrotTicks = 0;
        this.field_175543_bt = null;
        this.setSize(0.6F, 0.7F);
        this.jumpHelper = new EntityRabbitRabbitJumpHelper(this, this);
        this.moveHelper = new EntityRabbitRabbitMoveHelper(this);
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.navigator.setHeightRequirement(2.5F);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityRabbitAIPanic(this, 1.33));
        this.tasks.addTask(2, new EntityAITempt(this, 1.0, Items.carrot, false));
        this.tasks.addTask(3, new EntityAIMate(this, 0.8));
        this.tasks.addTask(5, new EntityRabbitAIRaidFarm(this));
        this.tasks.addTask(5, new EntityAIWander(this, 0.6));
        this.tasks.addTask(11, new EntityAIWatchClosest(this, PlayerEntity.class, 10.0F));
        this.aiAvoidWolves =
                new EntityRabbitAIAvoidEntity(
                        this, new RabbitWolfAvoidancePredicate(this), 16.0F, 1.33, 1.33);
        this.tasks.addTask(4, this.aiAvoidWolves);
        this.setMovementSpeed(0.0);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(18, (byte) 0);
    }

    public int getTotalArmorValue() {
        return this.getRabbitType() == 99 ? 8 : super.getTotalArmorValue();
    }

    private void updateMoveTypeDuration() {
        this.currentMoveTypeDuration = this.getMoveTypeDuration();
    }

    public void updateAITasks() {
        String text;
        {
            text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
            if (this.moveHelper.getSpeed() > 0.8) {
                this.setMoveType(EntityRabbitEnumMoveType.SPRINT);
                if (!(text == null)) {

                    if (this.moveType != EntityRabbitEnumMoveType.ATTACK) {
                        this.setMoveType(EntityRabbitEnumMoveType.HOP);
                    }
                }
            } else {

                if (this.moveType != EntityRabbitEnumMoveType.ATTACK) {
                    this.setMoveType(EntityRabbitEnumMoveType.HOP);
                }
            }
        }

        if (this.currentMoveTypeDuration > 0) {
            this.currentMoveTypeDuration--;
        }

        if (this.carrotTicks > 0) {
            this.carrotTicks = this.carrotTicks - this.rand.nextInt(3);
            if (this.carrotTicks < 0) {
                this.carrotTicks = 0;
            }
        }

        label53:
        if (this.onGround) {
            if (!this.field_175537_bp) {
                this.setJumping(false, EntityRabbitEnumMoveType.NONE);
                this.func_175517_cu();
            }

            if (this.getRabbitType() == 99 && this.currentMoveTypeDuration == 0) {
                LivingEntity livingEntity = this.getAttackTarget();
                if (livingEntity != null && this.getDistanceSqToEntity(livingEntity) < 16.0) {
                    this.calculateRotationYaw(livingEntity.posX, livingEntity.posZ);
                    this.moveHelper.setMoveTo(
                            livingEntity.posX,
                            livingEntity.posY,
                            livingEntity.posZ,
                            this.moveHelper.getSpeed());
                    this.doMovementAction(EntityRabbitEnumMoveType.ATTACK);
                    this.field_175537_bp = true;
                }
            }

            EntityRabbitRabbitJumpHelper beUValue = (EntityRabbitRabbitJumpHelper) this.jumpHelper;
            if (!beUValue.aY()) {
                if (!this.moveHelper.isUpdating() || this.currentMoveTypeDuration != 0) {
                    break label53;
                }

                PathEntity path = this.navigator.getPath();
                Vec3 vec3 =
                        new Vec3(
                                this.moveHelper.getX(),
                                this.moveHelper.getY(),
                                this.moveHelper.getZ());
                if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
                    vec3 = path.getPosition(this);
                }

                this.calculateRotationYaw(vec3.x, vec3.z);
                this.doMovementAction(this.moveType);
                if (text == null) {
                    break label53;
                }
            }

            if (!beUValue.func_180065_d()) {
                this.func_175518_cr();
            }
        }

        this.field_175537_bp = this.onGround;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3F);
    }

    public void setRabbitType(int i) {
        if (i == 99) {
            this.tasks.removeTask(this.aiAvoidWolves);
            this.tasks.addTask(4, new EntityRabbitAIEvilAttack(this));
            this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
            this.targetTasks.addTask(
                    2, new EntityAINearestAttackableTarget(this, PlayerEntity.class, true));
            this.targetTasks.addTask(
                    2, new EntityAINearestAttackableTarget(this, EntityWolf.class, true));
            if (!this.jvm171_d_45ffeb83d14f3c()) {
                this.setCustomNameTag(StatCollector.translateToLocal("entity.KillerBunny.name"));
            }
        }

        this.dataWatcher.updateObject(18, (byte) i);
    }

    protected String getLivingSound() {
        return "mob.rabbit.idle";
    }

    private void func_175520_cs() {
        ((EntityRabbitRabbitJumpHelper) this.jumpHelper).func_180066_a(false);
    }

    protected String getHurtSound() {
        return "mob.rabbit.hurt";
    }

    private void func_175517_cu() {
        this.updateMoveTypeDuration();
        this.func_175520_cs();
    }

    public IEntityLivingData onInitialSpawn(
            DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
        Object object;
        int i;
        byte b0;
        {
            object = super.onInitialSpawn(difficultyInstance, iEntityLivingData);
            String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
            i = this.rand.nextInt(6);
            b0 = 0;
            String textValue = text;
            if (object instanceof EntityRabbitRabbitTypeData) {
                i = ((EntityRabbitRabbitTypeData) object).typeData;
                b0 = 1;
                if (!(textValue == null)) {

                    object = new EntityRabbitRabbitTypeData(i);
                }
            } else {

                object = new EntityRabbitRabbitTypeData(i);
            }
        }

        this.setRabbitType(i);
        if (b0 != 0) {
            this.setGrowingAge(-24000);
        }

        return (IEntityLivingData) object;
    }

    protected void dropFewItems(boolean flag, int i) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        int j = this.rand.nextInt(2) + this.rand.nextInt(1 + i);
        int k = 0;
        String textValue = text;

        while (k < j) {
            this.dropItem(Items.rabbit_hide, 1);
            k++;
            if (textValue != null) {
                break;
            }
        }

        j = this.rand.nextInt(2);
        k = 0;

        while (k < j) {
            {
                if (this.isBurning()) {
                    this.dropItem(Items.cooked_rabbit, 1);
                    if (!(textValue == null)) {

                        this.dropItem(Items.rabbit, 1);
                    }
                } else {

                    this.dropItem(Items.rabbit, 1);
                }
            }

            k++;
            if (textValue != null) {
                break;
            }
        }
    }

    static boolean access$000(EntityRabbit entityRabbit) {
        return entityRabbit.isCarrotEaten();
    }

    public void setJumping(boolean flag, EntityRabbitEnumMoveType entityRabbitEnumMoveType) {
        label14:
        {
            String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
            super.setJumping(flag);
            String textValue = text;
            if (!flag) {
                if (this.moveType != EntityRabbitEnumMoveType.ATTACK) {
                    break label14;
                }

                this.moveType = EntityRabbitEnumMoveType.HOP;
                if (textValue == null) {
                    break label14;
                }
            }

            this.setMovementSpeed(1.5 * entityRabbitEnumMoveType.getSpeed());
            this.playSound(
                    this.getJumpingSound(),
                    this.getSoundVolume(),
                    ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }

        this.field_175536_bo = flag;
    }

    public boolean attackEntityAsMob(Entity entity) {
        if (this.getRabbitType() == 99) {
            this.playSound(
                    "mob.attack",
                    1.0F,
                    (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 8.0F);
        } else {
            return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
        }
    }

    public void handleHealthUpdate(byte b0) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (b0 == 1) {
            this.createRunningParticles();
            this.field_175535_bn = 10;
            this.field_175540_bm = 0;
            if (text == null) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    protected String getDeathSound() {
        return "mob.rabbit.death";
    }

    public int getRabbitType() {
        return this.dataWatcher.getWatchableObjectByte(18);
    }

    // Helper used only by this class.
    static class RabbitWolfAvoidancePredicate implements com.google.common.base.Predicate {
        net.minecraft.entity.passive.EntityRabbit field_180087_a;

        RabbitWolfAvoidancePredicate(net.minecraft.entity.passive.EntityRabbit bkQValue) {
            this.field_180087_a = bkQValue;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity instanceof net.minecraft.entity.passive.EntityWolf;
        }
    }
}
