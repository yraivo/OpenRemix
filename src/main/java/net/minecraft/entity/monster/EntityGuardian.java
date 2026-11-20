package net.minecraft.entity.monster;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFoodFishType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;

public class EntityGuardian extends MonsterEntity {
    private LivingEntity targetedEntity;
    private EntityAIWander wander;
    private float field_175486_bm;
    private float field_175483_bk;

    private float field_175485_bl;

    private int field_175479_bo;

    private boolean field_175480_bp;
    private float field_175484_c;
    private float field_175482_b;

    public boolean func_175472_n() {
        return this.isSyncedFlagSet(2);
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setBoolean("Elder", this.isElder());
    }

    public boolean hasTargetedEntity() {
        return this.dataWatcher.getWatchableObjectInt(17) != 0;
    }

    public float func_175477_p(float f) {
        return (this.field_175479_bo + f) / this.func_175464_ck();
    }

    protected String getHurtSound() {
        return !this.isInWater()
                ? "mob.guardian.land.hit"
                : (this.isElder() ? "mob.guardian.elder.hit" : "mob.guardian.hit");
    }

    public void onDataWatcherUpdate(int i) {
        int k = EntitySpiderGroupData.method_7eec03da329a017ec143();
        super.onDataWatcherUpdate(i);
        int j = k;
        if (i == 16) {
            if (!this.isElder() || !(this.width < 1.0F)) {
                return;
            }

            this.setSize(1.9975F, 1.9975F);
            if (j != 0) {
                return;
            }
        }

        if (i == 17) {
            this.field_175479_bo = 0;
            this.targetedEntity = null;
        }
    }

    static void access$200(EntityGuardian entityGuardian, boolean flag) {
        entityGuardian.func_175476_l(flag);
    }

    public boolean isElder() {
        return this.isSyncedFlagSet(4);
    }

    protected String getLivingSound() {
        return !this.isInWater()
                ? "mob.guardian.land.idle"
                : (this.isElder() ? "mob.guardian.elder.idle" : "mob.guardian.idle");
    }

    public float getEyeHeight() {
        return this.height * 0.5F;
    }

    public float getBlockPathWeight(BlockPos blockPos) {
        return this.world.getBlockState(blockPos).getBlock().getMaterial() == Material.water
                ? 10.0F + this.world.getLightBrightness(blockPos) - 0.5F
                : super.getBlockPathWeight(blockPos);
    }

    public boolean handleLavaMovement() {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this)
                && this.world
                        .getCollidingBoundingBoxes(this, this.getEntityBoundingBox())
                        .isEmpty();
    }

    public LivingEntity getTargetedEntity() {
        if (!this.hasTargetedEntity()) {
            return null;
        }

        if (this.world.isRemote) {
            if (this.targetedEntity != null) {
                return this.targetedEntity;
            } else {
                Entity entity =
                        this.world.getEntityByID(this.dataWatcher.getWatchableObjectInt(17));
                if (entity instanceof LivingEntity) {
                    this.targetedEntity = (LivingEntity) entity;
                    return this.targetedEntity;
                } else {
                    return null;
                }
            }
        } else {
            return this.getAttackTarget();
        }
    }

    protected void addRandomArmor() {
        ItemStack itemStack =
                ((WeightedRandomFishable)
                                WeightedRandom.getRandomItem(
                                        this.rand, EntityFishHook.func_174855_j()))
                        .getItemStack(this.rand);
        this.entityDropItem(itemStack, 1.0F);
    }

    static void access$000(EntityGuardian entityGuardian, int i) {
        entityGuardian.setTargetedEntity(i);
    }

    public boolean getCanSpawnHere() {
        return (this.rand.nextInt(20) == 0 || !this.world.canBlockSeeSky(new BlockPos(this)))
                && super.getCanSpawnHere();
    }

    private void setTargetedEntity(int i) {
        this.dataWatcher.updateObject(17, i);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected String getDeathSound() {
        return !this.isInWater()
                ? "mob.guardian.land.death"
                : (this.isElder() ? "mob.guardian.elder.death" : "mob.guardian.death");
    }

    protected PathNavigate getNewNavigator(World worldValue) {
        return new PathNavigateSwimmer(this, worldValue);
    }

    protected boolean isValidLightLevel() {
        return true;
    }

    public void setElder(boolean flag) {
        this.setSyncedFlag(4, flag);
        if (flag) {
            this.setSize(1.9975F, 1.9975F);
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3F);
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0);
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0);
            this.enablePersistence();
            this.wander.setExecutionChance(400);
        }
    }

    public float func_175471_a(float f) {
        return this.field_175484_c + (this.field_175482_b - this.field_175484_c) * f;
    }

    public int func_175464_ck() {
        return this.isElder() ? 60 : 80;
    }

    public float func_175469_o(float f) {
        return this.field_175486_bm + (this.field_175485_bl - this.field_175486_bm) * f;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(17, 0);
    }

    public void setElder() {
        this.setElder(true);
        this.field_175486_bm = this.field_175485_bl = 1.0F;
    }

    public void moveEntityWithHeading(float f, float f1) {
        int i = EntitySpiderGroupData.method_7eec03da329a017ec143();
        if (this.isServerWorld()) {
            if (this.isInWater()) {
                this.moveFlying(f, f1, 0.1F);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.9F;
                this.motionY *= 0.9F;
                this.motionZ *= 0.9F;
                if (this.func_175472_n() || this.getAttackTarget() != null) {
                    return;
                }

                this.motionY -= 0.005;
                if (i != 0) {
                    return;
                }
            }

            super.moveEntityWithHeading(f, f1);
            if (i != 0) {
                return;
            }
        }

        super.moveEntityWithHeading(f, f1);
    }

    public int getVerticalFaceSpeed() {
        return 180;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (!this.func_175472_n()
                && !damageSource.isMagicDamage()
                && damageSource.getSourceOfDamage() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) damageSource.getSourceOfDamage();
            if (!damageSource.isExplosion()) {
                livingEntity.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
                livingEntity.playSound("damage.thorns", 0.5F, 1.0F);
            }
        }

        this.wander.makeUpdate();
        return super.attackEntityFrom(damageSource, f);
    }

    public void onLivingUpdate() {
        int i = EntitySpiderGroupData.method_7eec03da329a017ec143();
        if (this.world.isRemote) {
            label105:
            {
                this.field_175484_c = this.field_175482_b;
                if (!this.isInWater()) {
                    this.field_175483_bk = 2.0F;
                    if (this.motionY > 0.0 && this.field_175480_bp && !this.isSlient()) {
                        this.world.playSound(
                                this.posX,
                                this.posY,
                                this.posZ,
                                "mob.guardian.flop",
                                1.0F,
                                1.0F,
                                false);
                    }

                    this.field_175480_bp =
                            remix.compat.compiler.JvmBooleanSlots.fromInt(
                                    (this.motionY < 0.0
                                                    && this.world.isBlockNormalCube(
                                                            new BlockPos(this).offsetDown(), false)
                                            ? 1
                                            : 0));
                    if (i != 0) {
                        break label105;
                    }
                }

                if (this.func_175472_n()) {
                    if (this.field_175483_bk < 0.5F) {
                        this.field_175483_bk = 4.0F;
                        if (i != 0) {
                            break label105;
                        }
                    }

                    this.field_175483_bk =
                            this.field_175483_bk + (0.5F - this.field_175483_bk) * 0.1F;
                    if (i != 0) {
                        break label105;
                    }
                }

                this.field_175483_bk =
                        this.field_175483_bk + (0.125F - this.field_175483_bk) * 0.2F;
            }

            label107:
            {
                this.field_175482_b = this.field_175482_b + this.field_175483_bk;
                this.field_175486_bm = this.field_175485_bl;
                if (!this.isInWater()) {
                    this.field_175485_bl = this.rand.nextFloat();
                    if (i != 0) {
                        break label107;
                    }
                }

                if (this.func_175472_n()) {
                    this.field_175485_bl =
                            this.field_175485_bl + (0.0F - this.field_175485_bl) * 0.25F;
                    if (i != 0) {
                        break label107;
                    }
                }

                this.field_175485_bl = this.field_175485_bl + (1.0F - this.field_175485_bl) * 0.06F;
            }

            if (this.func_175472_n() && this.isInWater()) {
                Vec3 vec3 = this.getLook(0.0F);
                int j = 0;

                while (j < 2) {
                    this.world.spawnParticle(
                            EnumParticleTypes.WATER_BUBBLE,
                            this.posX + (this.rand.nextDouble() - 0.5) * this.width - vec3.x * 1.5,
                            this.posY + this.rand.nextDouble() * this.height - vec3.y * 1.5,
                            this.posZ + (this.rand.nextDouble() - 0.5) * this.width - vec3.z * 1.5,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                    j++;
                    if (i == 0) {
                        break;
                    }
                }
            }

            if (this.hasTargetedEntity()) {
                if (this.field_175479_bo < this.func_175464_ck()) {
                    this.field_175479_bo++;
                }

                LivingEntity livingEntity = this.getTargetedEntity();
                if (livingEntity != null) {
                    this.getLookHelper().setLookPositionWithEntity(livingEntity, 90.0F, 90.0F);
                    this.getLookHelper().onUpdateLook();
                    double d5 = this.func_175477_p(0.0F);
                    double deltaPosX = livingEntity.posX - this.posX;
                    double d1 =
                            livingEntity.posY
                                    + livingEntity.height * 0.5F
                                    - (this.posY + this.getEyeHeight());
                    double deltaPosZ = livingEntity.posZ - this.posZ;
                    double d3 = Math.sqrt(deltaPosX * deltaPosX + d1 * d1 + deltaPosZ * deltaPosZ);
                    deltaPosX /= d3;
                    d1 /= d3;
                    deltaPosZ /= d3;
                    double d4 = this.rand.nextDouble();

                    while (d4 < d3) {
                        d4 += 1.8 - d5 + this.rand.nextDouble() * (1.7 - d5);
                        this.world.spawnParticle(
                                EnumParticleTypes.WATER_BUBBLE,
                                this.posX + deltaPosX * d4,
                                this.posY + d1 * d4 + this.getEyeHeight(),
                                this.posZ + deltaPosZ * d4,
                                0.0,
                                0.0,
                                0.0,
                                new int[0]);
                        if (i == 0) {
                            break;
                        }
                    }
                }
            }
        }

        label54:
        {
            if (this.inWater) {
                this.setAir(300);
                if (i != 0) {
                    break label54;
                }
            }

            if (this.onGround) {
                this.motionY += 0.5;
                this.motionX = this.motionX + (this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F;
                this.motionZ = this.motionZ + (this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F;
                this.rotationYaw = this.rand.nextFloat() * 360.0F;
                this.onGround = false;
                this.isAirBorne = true;
            }
        }

        if (this.hasTargetedEntity()) {
            this.rotationYaw = this.rotationYawHead;
        }

        super.onLivingUpdate();
    }

    protected void updateAITasks() {
        int j = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        super.updateAITasks();
        int i = j;
        if (this.isElder()) {

            if ((this.ticksExisted + this.getEntityId()) % 1200 == 0) {
                Potion DIG_SLOWDOWN = Potion.DIG_SLOWDOWN;

                for (java.util.Iterator<?> __recoveryIterator171_17625 =
                                (this.world.getPlayers(
                                                EntityPlayerMP.class,
                                                new UpdateAITasksPredicate(this)))
                                        .iterator();
                        (__recoveryIterator171_17625).hasNext(); ) {
                    EntityPlayerMP entityPlayerMP =
                            (EntityPlayerMP) (__recoveryIterator171_17625).next();
                    if (!entityPlayerMP.isPotionActive(DIG_SLOWDOWN)
                            || entityPlayerMP.getActivePotionEffect(DIG_SLOWDOWN).getAmplifier() < 2
                            || entityPlayerMP.getActivePotionEffect(DIG_SLOWDOWN).getDuration()
                                    < 1200) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(
                                new S2BPacketChangeGameState(10, 0.0F));
                        entityPlayerMP.addPotionEffect(new PotionEffect(DIG_SLOWDOWN.id, 6000, 2));
                    }

                    if (i != 0) {
                        break;
                    }
                }
            }

            if (!this.hasHome()) {
                this.setHomePosAndDistance(new BlockPos(this), 16);
            }
        }
    }

    protected void dropFewItems(boolean flag, int i) {
        int l = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        int k = this.rand.nextInt(3) + this.rand.nextInt(i + 1);
        int j = l;
        if (k > 0) {
            this.entityDropItem(new ItemStack(Items.prismarine_shard, k, 0), 1.0F);
        }

        {
            if (this.rand.nextInt(3 + i) > 1) {
                this.entityDropItem(
                        new ItemStack(Items.fish, 1, ItemFishFoodFishType.COD.getMetadata()), 1.0F);
                if (!(j == 0)) {

                    if (this.rand.nextInt(3 + i) > 1) {
                        this.entityDropItem(new ItemStack(Items.prismarine_crystals, 1, 0), 1.0F);
                    }
                }
            } else {

                if (this.rand.nextInt(3 + i) > 1) {
                    this.entityDropItem(new ItemStack(Items.prismarine_crystals, 1, 0), 1.0F);
                }
            }
        }

        if (flag && this.isElder()) {
            this.entityDropItem(new ItemStack(Blocks.sponge, 1, 1), 1.0F);
        }
    }

    private void func_175476_l(boolean flag) {
        this.setSyncedFlag(2, flag);
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.setElder(compoundTag.getBoolean("Elder"));
    }

    public EntityGuardian(World worldValue) {
        super(worldValue);
        this.experienceValue = 10;
        this.setSize(0.85F, 0.85F);
        this.tasks.addTask(4, new EntityGuardianAIGuardianAttack(this));
        EntityAIMoveTowardsRestriction entityAIMoveTowardsRestriction;
        this.tasks.addTask(
                5, entityAIMoveTowardsRestriction = new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(7, this.wander = new EntityAIWander(this, 1.0, 80));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0F, 0.01F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.wander.setMutexBits(3);
        entityAIMoveTowardsRestriction.setMutexBits(3);
        this.targetTasks.addTask(
                1,
                new EntityAINearestAttackableTarget(
                        this,
                        LivingEntity.class,
                        10,
                        true,
                        false,
                        new EntityGuardianGuardianTargetSelector(this)));
        this.moveHelper = new EntityGuardianGuardianMoveHelper(this);
        this.field_175484_c = this.field_175482_b = this.rand.nextFloat();
    }


    private void setSyncedFlag(int i, boolean flag) {
        int l = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        int k = this.dataWatcher.getWatchableObjectInt(16);
        int j = l;
        if (flag) {
            this.dataWatcher.updateObject(16, k | i);
            if (j == 0) {
                return;
            }
        }

        this.dataWatcher.updateObject(16, k & ~i);
    }

    static EntityAIWander access$100(EntityGuardian entityGuardian) {
        return entityGuardian.wander;
    }

    private boolean isSyncedFlagSet(int i) {
        return (this.dataWatcher.getWatchableObjectInt(16) & i) != 0;
    }

    public int getTalkInterval() {
        return 160;
    }

    // Helper used only by this class.
    public static class UpdateAITasksPredicate implements com.google.common.base.Predicate {
        net.minecraft.entity.monster.EntityGuardian bsS;

        public boolean method_0531b006009bad9845c5(
                net.minecraft.entity.player.EntityPlayerMP entityPlayerMP) {
            return this.bsS.getDistanceSqToEntity(entityPlayerMP) < 2500.0
                    && entityPlayerMP.theItemInWorldManager.survivalOrAdventure();
        }

        public UpdateAITasksPredicate(net.minecraft.entity.monster.EntityGuardian bsSValue) {
            this.bsS = bsSValue;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_0531b006009bad9845c5(
                    (net.minecraft.entity.player.EntityPlayerMP) object);
        }
    }
}
