package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerEnumFlowerType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIDefendVillage;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class EntityIronGolem extends EntityGolem {

    private int homeCheckTimer;
    private int attackTimer;
    Village villageObj;

    private int holdRoseTick;

    public EntityIronGolem(World worldValue) {
        this(worldValue, EntitySpiderGroupData.method_beea3e16fe3ec0995eb9());
    }

    private EntityIronGolem(World worldValue, int initialFlowToken) {
        super(worldValue);

        int j = initialFlowToken;

        int i = j;
        this.setSize(1.4F, 2.9F);
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9, 32.0F));
        this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6, true));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(5, new EntityAILookAtVillager(this));
        this.tasks.addTask(6, new EntityAIWander(this, 0.6));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIDefendVillage(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(
                3,
                new EntityIronGolemAINearestAttackableTargetNonCreeper(
                        this, EntityLiving.class, 10, false, true, IMob.VISIBLE_MOB_SELECTOR));
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            EntitySpiderGroupData.b(++i);
        }
    }

    protected void playStepSound(BlockPos blockPos, Block block) {
        this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
    }

    protected String getHurtSound() {
        return "mob.irongolem.hit";
    }

    protected void updateAITasks() {
        int i = EntitySpiderGroupData.method_7eec03da329a017ec143();
        if (--this.homeCheckTimer <= 0) {
            this.homeCheckTimer = 70 + this.rand.nextInt(50);
            this.villageObj =
                    this.world.getVillageCollection().getNearestVillage(new BlockPos(this), 32);
            if (this.villageObj == null) {
                this.detachHome();
                if (!(i != 0)) {

                    BlockPos blockPos = this.villageObj.getCenter();
                    this.setHomePosAndDistance(
                            blockPos, (int) (this.villageObj.getVillageRadius() * 0.6F));
                }
            } else {

                BlockPos blockPos = this.villageObj.getCenter();
                this.setHomePosAndDistance(
                        blockPos, (int) (this.villageObj.getVillageRadius() * 0.6F));
            }
        }

        super.updateAITasks();
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.setPlayerCreated(compoundTag.getBoolean("PlayerCreated"));
    }

    public void handleHealthUpdate(byte b0) {
        int i = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        if (b0 == 4) {
            this.attackTimer = 10;
            this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
            if (i == 0) {
                return;
            }
        }

        if (b0 == 11) {
            this.holdRoseTick = 400;
            if (i == 0) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    public Village getVillage() {
        return this.villageObj;
    }

    public boolean canAttackClass(Class oclass) {
        return this.isPlayerCreated() && PlayerEntity.class.isAssignableFrom(oclass)
                ? false
                : super.canAttackClass(oclass);
    }

    protected String getDeathSound() {
        return "mob.irongolem.death";
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) 0);
    }

    protected int decreaseAirSupply(int i) {
        return i;
    }

    public void setPlayerCreated(boolean flag) {
        int j = EntitySpiderGroupData.method_7eec03da329a017ec143();
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        int i = j;
        if (flag) {
            this.dataWatcher.updateObject(16, (byte) (b0 | 1));
            if (i != 0) {
                return;
            }
        }

        this.dataWatcher.updateObject(16, (byte) (b0 & -2));
    }


    public boolean attackEntityAsMob(Entity entity) {
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte) 4);
        boolean flag =
                entity.attackEntityFrom(
                        DamageSource.causeMobDamage(this), 7 + this.rand.nextInt(15));
        if (flag) {
            entity.motionY += 0.4F;
            this.applyEnchantments(this, entity);
        }

        this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        return flag;
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setBoolean("PlayerCreated", this.isPlayerCreated());
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.attackTimer > 0) {
            this.attackTimer--;
        }

        if (this.holdRoseTick > 0) {
            this.holdRoseTick--;
        }

        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.5000003E-7F
                && this.rand.nextInt(5) == 0) {
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY - 0.2F);
            int k = MathHelper.floor(this.posZ);
            BlockState blockState = this.world.getBlockState(new BlockPos(i, j, k));
            Block block = blockState.getBlock();
            if (block.getMaterial() != Material.AIR) {
                this.world.spawnParticle(
                        EnumParticleTypes.BLOCK_CRACK,
                        this.posX + (this.rand.nextFloat() - 0.5) * this.width,
                        this.getEntityBoundingBox().minY + 0.1,
                        this.posZ + (this.rand.nextFloat() - 0.5) * this.width,
                        4.0 * (this.rand.nextFloat() - 0.5),
                        0.5,
                        (this.rand.nextFloat() - 0.5) * 4.0,
                        new int[] {Block.getStateId(blockState)});
            }
        }
    }

    public void onDeath(DamageSource damageSource) {
        if (!this.isPlayerCreated() && this.attackingPlayer != null && this.villageObj != null) {
            this.villageObj.setReputationForPlayer(this.attackingPlayer.getName(), -5);
        }

        super.onDeath(damageSource);
    }

    public void setHoldingRose(boolean flag) {
        this.holdRoseTick = flag ? 400 : 0;
        this.world.setEntityState(this, (byte) 11);
    }

    protected void collideWithEntity(Entity entity) {
        if (entity instanceof IMob && this.getRNG().nextInt(20) == 0) {
            this.setAttackTarget((LivingEntity) entity);
        }

        super.collideWithEntity(entity);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    public int getHoldRoseTick() {
        return this.holdRoseTick;
    }

    protected void dropFewItems(boolean flag, int j1) {
        int i1 = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        int j = this.rand.nextInt(3);
        int k = 0;
        int i = i1;

        while (k < j) {
            this.dropItemWithOffset(
                    Item.getItemFromBlock(Blocks.red_flower),
                    1,
                    BlockFlowerEnumFlowerType.POPPY.getMeta());
            k++;
            if (i != 0) {
                break;
            }
        }

        k = 3 + this.rand.nextInt(3);
        int l = 0;

        while (l < k) {
            this.dropItem(Items.iron_ingot, 1);
            l++;
            if (i != 0) {
                break;
            }
        }
    }

    public boolean isPlayerCreated() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }
}
