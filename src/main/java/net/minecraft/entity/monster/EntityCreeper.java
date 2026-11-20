package net.minecraft.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAICreeperSwell;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCreeper extends MonsterEntity {

    private int lastActiveTime;
    private int fuseTime = 30;
    private int field_175494_bm;

    private int explosionRadius = 3;
    private int timeSinceIgnited;

    public boolean getPowered() {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    public void onDeath(DamageSource damageSource) {
        int i1 = EntitySpiderGroupData.method_7eec03da329a017ec143();
        super.onDeath(damageSource);
        int i = i1;
        if (damageSource.getEntity() instanceof EntitySkeleton) {
            int j = Item.getIdFromItem(Items.record_13);
            int k = Item.getIdFromItem(Items.record_wait);
            int l = j + this.rand.nextInt(k - j + 1);
            this.dropItem(Item.getItemById(l), 1);
            if (i != 0) {
                return;
            }
        }

        if (damageSource.getEntity() instanceof EntityCreeper
                && damageSource.getEntity() != this
                && ((EntityCreeper) damageSource.getEntity()).getPowered()
                && ((EntityCreeper) damageSource.getEntity()).isAIEnabled()) {
            ((EntityCreeper) damageSource.getEntity()).func_175493_co();
            this.entityDropItem(new ItemStack(Items.skull, 1, 4), 0.0F);
        }
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    private void explode() {
        if (!this.world.isRemote) {
            boolean gameRuleBooleanValue =
                    this.world.getGameRules().getGameRuleBooleanValue("mobGriefing");
            float f = this.getPowered() ? 2.0F : 1.0F;
            this.world.createExplosion(
                    this,
                    this.posX,
                    this.posY,
                    this.posZ,
                    this.explosionRadius * f,
                    gameRuleBooleanValue);
            this.setDead();
        }
    }

    public boolean hasIgnited() {
        return this.dataWatcher.getWatchableObjectByte(18) != 0;
    }

    public void onStruckByLightning(EntityLightningBolt entityLightningBolt) {
        super.onStruckByLightning(entityLightningBolt);
        this.dataWatcher.updateObject(17, (byte) 1);
    }

    public EntityCreeper(World worldValue) {
        super(worldValue);
        this.field_175494_bm = 0;
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAICreeperSwell(this));
        this.tasks.addTask(2, this.field_175455_a);
        this.tasks.addTask(
                3,
                new EntityAIAvoidEntity(
                        this, new CreeperOcelotAvoidancePredicate(this), 6.0F, 1.0, 1.2));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(
                1, new EntityAINearestAttackableTarget(this, PlayerEntity.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }

    protected String getDeathSound() {
        return "mob.creeper.death";
    }

    public void func_175493_co() {
        this.field_175494_bm++;
    }

    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
            if (this.hasIgnited()) {
                this.setCreeperState(1);
            }

            int creeperState = this.getCreeperState();
            if (creeperState > 0 && this.timeSinceIgnited == 0) {
                this.playSound("creeper.primed", 1.0F, 0.5F);
            }

            this.timeSinceIgnited += creeperState;
            if (this.timeSinceIgnited < 0) {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                this.explode();
            }
        }

        super.onUpdate();
    }

    public void setCreeperState(int i) {
        this.dataWatcher.updateObject(16, (byte) i);
    }

    public int getMaxFallHeight() {
        return this.getAttackTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.dataWatcher.updateObject(17, (byte) (compoundTag.getBoolean("powered") ? 1 : 0));
        if (compoundTag.hasKey("Fuse", 99)) {
            this.fuseTime = compoundTag.getShort("Fuse");
        }

        if (compoundTag.hasKey("ExplosionRadius", 99)) {
            this.explosionRadius = compoundTag.getByte("ExplosionRadius");
        }

        if (compoundTag.getBoolean("ignited")) {
            this.ignite();
        }
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) -1);
        this.dataWatcher.addObject(17, (byte) 0);
        this.dataWatcher.addObject(18, (byte) 0);
    }

    public boolean isAIEnabled() {
        return this.field_175494_bm < 1
                && this.world.getGameRules().getGameRuleBooleanValue("doMobLoot");
    }

    public void fall(float f, float f1) {
        super.fall(f, f1);
        this.timeSinceIgnited = (int) (this.timeSinceIgnited + f * 1.5F);
        if (this.timeSinceIgnited > this.fuseTime - 5) {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }


    public float getCreeperFlashIntensity(float f) {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * f)
                / (this.fuseTime - 2);
    }

    public boolean attackEntityAsMob(Entity entity) {
        return true;
    }

    public int getCreeperState() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    protected String getHurtSound() {
        return "mob.creeper.say";
    }

    protected boolean interact(PlayerEntity playerEntity) {
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        if (itemStack != null && itemStack.getItem() == Items.flint_and_steel) {
            this.world.playSoundEffect(
                    this.posX + 0.5,
                    this.posY + 0.5,
                    this.posZ + 0.5,
                    "fire.ignite",
                    1.0F,
                    this.rand.nextFloat() * 0.4F + 0.8F);
            playerEntity.swingItem();
            if (!this.world.isRemote) {
                this.ignite();
                itemStack.damageItem(1, playerEntity);
                return true;
            }
        }

        return super.interact(playerEntity);
    }

    public void ignite() {
        this.dataWatcher.updateObject(18, (byte) 1);
    }

    protected Item getDropItem() {
        return Items.gunpowder;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        if (this.dataWatcher.getWatchableObjectByte(17) == 1) {
            compoundTag.setBoolean("powered", true);
        }

        compoundTag.setShort("Fuse", (short) this.fuseTime);
        compoundTag.setByte("ExplosionRadius", (byte) this.explosionRadius);
        compoundTag.setBoolean("ignited", this.hasIgnited());
    }

    // Helper used only by this class.
    static class CreeperOcelotAvoidancePredicate implements com.google.common.base.Predicate {
        net.minecraft.entity.monster.EntityCreeper field_179959_a;

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity instanceof net.minecraft.entity.passive.EntityOcelot;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }

        // Recovered explicit constructors from original mapped instruction streams.
        CreeperOcelotAvoidancePredicate(net.minecraft.entity.monster.EntityCreeper bsQValue) {
            super();
            this.field_179959_a = bsQValue;
        }
    }
}
