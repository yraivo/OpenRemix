package net.minecraft.entity.boss;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;

public class EntityWither extends MonsterEntity implements IRangedAttackMob, IBossDisplayData {
    private float[] bgO;
    private int blockBreakCounter;
    private int[] bgP;
    private static Predicate attackEntitySelector;
    private float[] bgR;
    private float[] bgS;
    private float[] bgT;
    private static String[] packedTextFragments;

    private int[] Nm;

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    public void addPotionEffect(PotionEffect potionEffect) {}

    protected String getLivingSound() {
        return "mob.wither.idle";
    }

    public void attackEntityWithRangedAttack(LivingEntity livingEntity, float f) {
        this.launchWitherSkullToEntity(0, livingEntity);
    }

    public int getTotalArmorValue() {
        return 4;
    }

    public float au(int p171_0) {
        return this.bgS[p171_0];
    }

    public int getBrightnessForRender(float f) {
        return 15728880;
    }

    public void setInvulTime(int p171_0) {
        this.dataWatcher.updateObject(20, p171_0);
    }

    protected void updateAITasks() {
        String text = BossStatus.method_c60693f457aa70be7160();
        if (this.getInvulTime() > 0) {
            int i = this.getInvulTime() - 1;
            if (i <= 0) {
                this.world.newExplosion(
                        this,
                        this.posX,
                        this.posY + this.getEyeHeight(),
                        this.posZ,
                        7.0F,
                        false,
                        this.world.getGameRules().getGameRuleBooleanValue("mobGriefing"));
                this.world.playBroadcastSound(1013, new BlockPos(this), 0);
            }

            this.setInvulTime(i);
            if (this.ticksExisted % 10 != 0) {
                return;
            }

            this.heal(10.0F);
            if (text == null) {
                return;
            }
        }

        super.updateAITasks();
        int k1 = 1;

        while (k1 < 3) {
            label139:
            if (this.ticksExisted >= this.bgP[k1 - 1]) {
                this.bgP[k1 - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);
                if (this.world.getDifficulty() == EnumDifficulty.NORMAL
                        || this.world.getDifficulty() == EnumDifficulty.HARD) {
                    int k = k1 - 1;
                    int l = this.Nm[k1 - 1];
                    this.Nm[k] = this.Nm[k1 - 1] + 1;
                    if (l > 15) {
                        float f = 10.0F;
                        float f1 = 5.0F;
                        double d0 = MathHelper.nextDouble(this.rand, this.posX - f, this.posX + f);
                        double d1 =
                                MathHelper.nextDouble(this.rand, this.posY - f1, this.posY + f1);
                        double d2 = MathHelper.nextDouble(this.rand, this.posZ - f, this.posZ + f);
                        this.launchWitherSkullToCoords(k1 + 1, d0, d1, d2, true);
                        this.Nm[k1 - 1] = 0;
                    }
                }

                int j = this.getWatchedTargetId(k1);
                if (j > 0) {
                    {
                        Entity entity = this.world.getEntityByID(j);
                        if (entity != null
                                && entity.isEntityAlive()
                                && this.getDistanceSqToEntity(entity) <= 900.0
                                && this.canEntityBeSeen(entity)) {
                            this.launchWitherSkullToEntity(k1 + 1, (LivingEntity) entity);
                            this.bgP[k1 - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
                            this.Nm[k1 - 1] = 0;
                            if (!(text == null)) {

                                this.updateWatchedTargetId(k1, 0);
                            }
                        } else {

                            this.updateWatchedTargetId(k1, 0);
                        }
                    }

                    if (text == null) {
                        break label139;
                    }
                }

                List entitiesWithinAABB =
                        this.world.getEntitiesWithinAABB(
                                LivingEntity.class,
                                this.getEntityBoundingBox().expand(20.0, 8.0, 20.0),
                                Predicates.and(
                                        net.minecraft.entity.boss.EntityWither.attackEntitySelector,
                                        IEntitySelector.NOT_SPECTATING));
                int j2 = 0;

                while (j2 < 10 && !entitiesWithinAABB.isEmpty()) {
                    LivingEntity livingEntity =
                            (LivingEntity)
                                    entitiesWithinAABB.get(
                                            this.rand.nextInt(entitiesWithinAABB.size()));
                    if (livingEntity != this
                            && livingEntity.isEntityAlive()
                            && this.canEntityBeSeen(livingEntity)) {
                        if (livingEntity instanceof PlayerEntity) {
                            if (((PlayerEntity) livingEntity).capabilities.disableDamage) {
                                break;
                            }

                            this.updateWatchedTargetId(k1, livingEntity.getEntityId());
                            if (text == null) {
                                break;
                            }
                        }

                        this.updateWatchedTargetId(k1, livingEntity.getEntityId());
                        if (text == null) {
                            break;
                        }
                    }

                    entitiesWithinAABB.remove(livingEntity);
                    j2++;
                    if (text != null) {
                        break;
                    }
                }
            }

            k1++;
            if (text != null) {
                break;
            }
        }

        {
            if (this.getAttackTarget() != null) {
                this.updateWatchedTargetId(0, this.getAttackTarget().getEntityId());
                if (!(text == null)) {

                    this.updateWatchedTargetId(0, 0);
                }
            } else {

                this.updateWatchedTargetId(0, 0);
            }
        }

        if (this.blockBreakCounter > 0) {
            this.blockBreakCounter--;
            if (this.blockBreakCounter == 0
                    && this.world.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                k1 = MathHelper.floor(this.posY);
                int l1 = MathHelper.floor(this.posX);
                int i2 = MathHelper.floor(this.posZ);
                byte b0 = 0;
                int k2 = -1;

                label108:
                while (k2 <= 1) {
                    int l2 = -1;

                    while (true) {
                        if (l2 <= 1) {
                            int i3 = 0;

                            while (i3 <= 3) {
                                int i1 = l1 + k2;
                                int j3 = k1 + i3;
                                int j1 = i2 + l2;
                                Block block =
                                        this.world
                                                .getBlockState(new BlockPos(i1, j3, j1))
                                                .getBlock();
                                if (block.getMaterial() != Material.AIR
                                        && block != Blocks.bedrock
                                        && block != Blocks.end_portal
                                        && block != Blocks.end_portal_frame
                                        && block != Blocks.command_block
                                        && block != Blocks.barrier) {
                                    b0 =
                                            (byte)
                                                    (!this.world.destroyBlock(
                                                                            new BlockPos(
                                                                                    i1, j3, j1),
                                                                            true)
                                                                    && b0 == 0
                                                            ? 0
                                                            : 1);
                                }

                                i3++;
                                if (text != null) {
                                    break;
                                }
                            }

                            l2++;
                            if (text == null) {
                                continue;
                            }
                        }

                        k2++;
                        if (text != null) {
                            break label108;
                        }
                        break;
                    }
                }

                if (b0 != 0) {
                    this.world.playAuxSFXAtEntity((PlayerEntity) null, 1012, new BlockPos(this), 0);
                }
            }
        }

        if (this.ticksExisted % 20 == 0) {
            this.heal(1.0F);
        }
    }

    protected void dropFewItems(boolean flag, int i) {
        String text = BossStatus.method_c60693f457aa70be7160();
        EntityItem entityItem = this.dropItem(Items.nether_star, 1);
        String textValue = text;
        if (entityItem != null) {
            entityItem.setNoDespawn();
        }

        if (!this.world.isRemote) {
            for (java.util.Iterator<?> __recoveryIterator171_10066 =
                            (this.world.getEntitiesWithinAABB(
                                            PlayerEntity.class,
                                            this.getEntityBoundingBox().expand(50.0, 100.0, 50.0)))
                                    .iterator();
                    (__recoveryIterator171_10066).hasNext(); ) {
                PlayerEntity playerEntity = (PlayerEntity) (__recoveryIterator171_10066).next();
                playerEntity.triggerAchievement(AchievementList.killWither);
                if (textValue != null) {
                    break;
                }
            }
        }
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6F);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
    }

    public boolean isArmored() {
        return this.getHealth() <= this.getMaxHealth() / 2.0F;
    }

    public void mountEntity(Entity entity) {
        this.ridingEntity = null;
    }

    private double method_66ac375ce9491572732d(int p171_0) {
        return p171_0 <= 0 ? this.posY + 3.0 : this.posY + 2.2;
    }

    public EntityWither(World worldValue) {
        super(worldValue);
        BossStatus.method_c60693f457aa70be7160();
        this.bgO = new float[2];
        this.bgS = new float[2];
        this.bgR = new float[2];
        this.bgT = new float[2];
        this.bgP = new int[2];
        this.Nm = new int[2];
        this.setHealth(this.getMaxHealth());
        this.setSize(0.9F, 3.5F);
        this.isImmuneToFire = true;
        ((PathNavigateGround) this.getNavigator()).setCanSwim(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 40, 20.0F));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(
                2,
                new EntityAINearestAttackableTarget(
                        this,
                        EntityLiving.class,
                        0,
                        false,
                        false,
                        net.minecraft.entity.boss.EntityWither.attackEntitySelector));
        this.experienceValue = 50;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            BossStatus.b(packedTextFragments[0]);
        }
    }

    public void func_82206_m() {
        this.setInvulTime(220);
        this.setHealth(this.getMaxHealth() / 3.0F);
    }

    public int getInvulTime() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }

    public void setInWeb() {}

    public float aL(int p171_0) {
        return this.bgO[p171_0];
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.setInvulTime(compoundTag.getInteger("Invul"));
    }

    private float func_82204_b(float f, float f1, float f2) {
        float f3 = MathHelper.wrapAngleTo180(f1 - f);
        if (f3 > f2) {
            f3 = f2;
        }

        if (f3 < -f2) {
            f3 = -f2;
        }

        return f + f3;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        String text = BossStatus.method_c60693f457aa70be7160();
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        if (damageSource == DamageSource.drown
                || damageSource.getEntity() instanceof EntityWither) {
            return false;
        }

        if (this.getInvulTime() > 0 && damageSource != DamageSource.outOfWorld) {
            return false;
        }

        if (this.isArmored()) {
            Entity entity = damageSource.getSourceOfDamage();
            if (entity instanceof EntityArrow) {
                return false;
            }
        }

        Entity entityValue = damageSource.getEntity();
        if (entityValue != null
                && !(entityValue instanceof PlayerEntity)
                && entityValue instanceof LivingEntity
                && ((LivingEntity) entityValue).getCreatureAttribute()
                        == this.getCreatureAttribute()) {
            return false;
        }

        if (this.blockBreakCounter <= 0) {
            this.blockBreakCounter = 20;
        }

        int i = 0;

        while (i < this.Nm.length) {
            this.Nm[i] = this.Nm[i] + 3;
            i++;
            if (text != null) {
                break;
            }
        }

        return super.attackEntityFrom(damageSource, f);
    }

    private double func_82213_w(int i) {
        if (i <= 0) {
            return this.posZ;
        }

        float f = (this.renderYawOffset + 180 * (i - 1)) / 180.0F * (float) Math.PI;
        float f1 = MathHelper.sin(f);
        return this.posZ + f1 * 1.3;
    }

    public int getWatchedTargetId(int p171_0) {
        return this.dataWatcher.getWatchableObjectInt(17 + p171_0);
    }

    protected void despawnEntity() {
        this.NJ = 0;
    }

    public void onLivingUpdate() {
        String text = BossStatus.method_c60693f457aa70be7160();
        this.motionY *= 0.6F;
        String textValue = text;
        if (!this.world.isRemote && this.getWatchedTargetId(0) > 0) {
            Entity entity = this.world.getEntityByID(this.getWatchedTargetId(0));
            if (entity != null) {
                if (this.posY < entity.posY || !this.isArmored() && this.posY < entity.posY + 5.0) {
                    if (this.motionY < 0.0) {
                        this.motionY = 0.0;
                    }

                    this.motionY = this.motionY + (0.5 - this.motionY) * 0.6F;
                }

                double deltaPosX = entity.posX - this.posX;
                double deltaPosZ = entity.posZ - this.posZ;
                double d1 = deltaPosX * deltaPosX + deltaPosZ * deltaPosZ;
                if (d1 > 9.0) {
                    double d2 = MathHelper.sqrt(d1);
                    this.motionX = this.motionX + (deltaPosX / d2 * 0.5 - this.motionX) * 0.6F;
                    this.motionZ = this.motionZ + (deltaPosZ / d2 * 0.5 - this.motionZ) * 0.6F;
                }
            }
        }

        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05F) {
            this.rotationYaw =
                    (float) Math.atan2(this.motionZ, this.motionX) * (180.0F / (float) Math.PI)
                            - 90.0F;
        }

        super.onLivingUpdate();
        int i = 0;

        while (i < 2) {
            this.bgT[i] = this.bgS[i];
            this.bgR[i] = this.bgO[i];
            i++;
            if (textValue != null) {
                break;
            }
        }

        i = 0;

        while (i < 2) {
            int j = this.getWatchedTargetId(i + 1);
            Entity entityValue = null;
            if (j > 0) {
                entityValue = this.world.getEntityByID(j);
            }

            {
                if (entityValue != null) {
                    double d8 = this.func_82214_u(i + 1);
                    double d9 = this.method_66ac375ce9491572732d(i + 1);
                    double d10 = this.func_82213_w(i + 1);
                    double d4 = entityValue.posX - d8;
                    double d5 = entityValue.posY + entityValue.getEyeHeight() - d9;
                    double d6 = entityValue.posZ - d10;
                    double d7 = MathHelper.sqrt(d4 * d4 + d6 * d6);
                    float f = (float) (Math.atan2(d6, d4) * 180.0 / Math.PI) - 90.0F;
                    float f1 = (float) (-(Math.atan2(d5, d7) * 180.0 / Math.PI));
                    this.bgO[i] = this.func_82204_b(this.bgO[i], f1, 40.0F);
                    this.bgS[i] = this.func_82204_b(this.bgS[i], f, 10.0F);
                    if (!(textValue == null)) {

                        this.bgS[i] = this.func_82204_b(this.bgS[i], this.renderYawOffset, 10.0F);
                    }
                } else {

                    this.bgS[i] = this.func_82204_b(this.bgS[i], this.renderYawOffset, 10.0F);
                }
            }

            i++;
            if (textValue != null) {
                break;
            }
        }

        boolean armored = this.isArmored();
        int k = 0;

        while (k < 3) {
            double d11 = this.func_82214_u(k);
            double d12 = this.method_66ac375ce9491572732d(k);
            double d13 = this.func_82213_w(k);
            this.world.spawnParticle(
                    EnumParticleTypes.SMOKE_NORMAL,
                    d11 + this.rand.nextGaussian() * 0.3F,
                    d12 + this.rand.nextGaussian() * 0.3F,
                    d13 + this.rand.nextGaussian() * 0.3F,
                    0.0,
                    0.0,
                    0.0,
                    new int[0]);
            if (armored && this.world.rand.nextInt(4) == 0) {
                this.world.spawnParticle(
                        EnumParticleTypes.SPELL_MOB,
                        d11 + this.rand.nextGaussian() * 0.3F,
                        d12 + this.rand.nextGaussian() * 0.3F,
                        d13 + this.rand.nextGaussian() * 0.3F,
                        0.7F,
                        0.7F,
                        0.5,
                        new int[0]);
            }

            k++;
            if (textValue != null) {
                break;
            }
        }

        if (this.getInvulTime() > 0) {
            k = 0;

            while (k < 3) {
                this.world.spawnParticle(
                        EnumParticleTypes.SPELL_MOB,
                        this.posX + this.rand.nextGaussian() * 1.0,
                        this.posY + this.rand.nextFloat() * 3.3F,
                        this.posZ + this.rand.nextGaussian() * 1.0,
                        0.7F,
                        0.7F,
                        0.9F,
                        new int[0]);
                k++;
                if (textValue != null) {
                    break;
                }
            }
        }
    }

    protected String getDeathSound() {
        return "mob.wither.death";
    }

    private void launchWitherSkullToEntity(int p171_0, LivingEntity livingEntity) {
        this.launchWitherSkullToCoords(
                p171_0,
                livingEntity.posX,
                livingEntity.posY + livingEntity.getEyeHeight() * 0.5,
                livingEntity.posZ,
                p171_0 == 0 && this.rand.nextFloat() < 0.001F);
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setInteger("Invul", this.getInvulTime());
    }

    private double func_82214_u(int i) {
        if (i <= 0) {
            return this.posX;
        }

        float f = (this.renderYawOffset + 180 * (i - 1)) / 180.0F * (float) Math.PI;
        float f1 = MathHelper.cos(f);
        return this.posX + f1 * 1.3;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(0));
        this.dataWatcher.addObject(19, new Integer(0));
        this.dataWatcher.addObject(20, new Integer(0));
    }

    // region String decoding support

    // endregion

    private void launchWitherSkullToCoords(
            int p171_0, double p171_1, double p171_2, double p171_3, boolean p171_4) {
        this.world.playAuxSFXAtEntity(
                (net.minecraft.entity.player.PlayerEntity) null,
                1014,
                new net.minecraft.util.BlockPos(this),
                0);
        double posXValue = this.func_82214_u(p171_0);
        double posYValue = this.method_66ac375ce9491572732d(p171_0);
        double posZValue = this.func_82213_w(p171_0);
        double d6 = p171_1 - posXValue;
        double d7 = p171_2 - posYValue;
        double d8 = p171_3 - posZValue;
        net.minecraft.entity.projectile.EntityWitherSkull entityWitherSkull =
                new net.minecraft.entity.projectile.EntityWitherSkull(this.world, this, d6, d7, d8);
        if (p171_4) {
            entityWitherSkull.setInvulnerable(true);
        }

        entityWitherSkull.posY = posYValue;
        entityWitherSkull.posX = posXValue;
        entityWitherSkull.posZ = posZValue;
        this.world.spawnEntityInWorld(entityWitherSkull);
    }

    public void updateWatchedTargetId(int p171_0, int p171_1) {
        this.dataWatcher.updateObject(17 + p171_0, p171_1);
    }

    protected String getHurtSound() {
        return "mob.wither.hurt";
    }

    static {
        net.minecraft.entity.boss.EntityWither.packedTextFragments = new String[3];
        net.minecraft.entity.boss.EntityWither.packedTextFragments[0] = new String("pJdTAc");
        net.minecraft.entity.boss.EntityWither.attackEntitySelector =
                new AttackEntitySelectorPredicate();
    }

    public void fall(float f, float f1) {}

    // Helper used only by this class.
    public static final class AttackEntitySelectorPredicate
            implements com.google.common.base.Predicate {
        public boolean apply(java.lang.Object p171_0) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) p171_0);
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity instanceof net.minecraft.entity.LivingEntity
                    && ((net.minecraft.entity.LivingEntity) entity).getCreatureAttribute()
                            != net.minecraft.entity.CreatureAttribute.UNDEAD;
        }
    }
}
