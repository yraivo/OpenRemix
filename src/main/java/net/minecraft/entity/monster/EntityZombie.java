package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class EntityZombie extends MonsterEntity {
    private boolean isBreakDoorsTaskSet;
    protected static IAttribute reinforcementChance;
    private float zombieHeight;
    private static UUID babySpeedBoostUUID;
    private float zombieWidth;

    private static AttributeModifier babySpeedBoostModifier;

    private final EntityAIBreakDoor breakDoor = new EntityAIBreakDoor(this);
    private int conversionTime;

    public boolean interact(PlayerEntity playerEntity) {
        ItemStack currentEquippedItem = playerEntity.getCurrentEquippedItem();
        if (currentEquippedItem != null
                && currentEquippedItem.getItem() == Items.golden_apple
                && currentEquippedItem.getItemDamage() == 0
                && this.isVillager()
                && this.isPotionActive(Potion.WEAKNESS)) {
            if (!playerEntity.capabilities.isCreativeMode) {
                currentEquippedItem.stackSize--;
            }

            if (currentEquippedItem.stackSize <= 0) {
                playerEntity.inventory.setInventorySlotContents(
                        playerEntity.inventory.currentItem, (ItemStack) null);
            }

            if (!this.world.isRemote) {
                this.startConversion(this.rand.nextInt(2401) + 3600);
            }

            return true;
        } else {
            return false;
        }
    }

    public void handleHealthUpdate(byte b0) {
        int i = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        if (b0 == 16) {
            if (this.isSlient()) {
                return;
            }

            this.world.playSound(
                    this.posX + 0.5,
                    this.posY + 0.5,
                    this.posZ + 0.5,
                    "mob.zombie.remedy",
                    1.0F + this.rand.nextFloat(),
                    this.rand.nextFloat() * 0.7F + 0.3F,
                    false);
            if (i == 0) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    protected void playStepSound(BlockPos blockPos, Block block) {
        this.playSound("mob.zombie.step", 0.15F, 1.0F);
    }

    public double getYOffset() {
        return super.getYOffset() - 0.5;
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        int k = EntitySpiderGroupData.method_7eec03da329a017ec143();
        super.setEquipmentBasedOnDifficulty(difficultyInstance);
        int i = k;
        if (this.rand.nextFloat()
                < (this.world.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F)) {
            int j = this.rand.nextInt(3);
            if (j == 0) {
                this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_sword));
                if (i != 0) {
                    return;
                }
            }

            this.setCurrentItemOrArmor(0, new ItemStack(Items.iron_shovel));
        }
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(12, (byte) 0);
        this.getDataWatcher().addObject(13, (byte) 0);
        this.getDataWatcher().addObject(14, (byte) 0);
    }

    public void setChildSize(boolean flag) {
        this.multiplySize(flag ? 0.5F : 1.0F);
    }

    public float getEyeHeight() {
        float f = 1.74F;
        if (this.isChild()) {
            f = (float) (f - 0.81);
        }

        return f;
    }

    public EntityZombie(World worldValue) {
        super(worldValue);
        this.isBreakDoorsTaskSet = false;
        this.zombieWidth = -1.0F;
        ((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, PlayerEntity.class, 1.0, false));
        this.tasks.addTask(2, this.field_175455_a);
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
        this.setSize(0.6F, 1.95F);
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        if (this.isChild()) {
            compoundTag.setBoolean("IsBaby", true);
        }

        if (this.isVillager()) {
            compoundTag.setBoolean("IsVillager", true);
        }

        compoundTag.setInteger("ConversionTime", this.isConverting() ? this.conversionTime : -1);
        compoundTag.setBoolean("CanBreakDoors", this.isBreakDoorsTaskSet());
    }

    protected void convertToVillager() {
        EntityVillager entityVillager = new EntityVillager(this.world);
        entityVillager.copyLocationAndAnglesFrom(this);
        entityVillager.onInitialSpawn(
                this.world.getDifficultyForLocation(new BlockPos(entityVillager)),
                (IEntityLivingData) null);
        entityVillager.setLookingForHome();
        if (this.isChild()) {
            entityVillager.setGrowingAge(-24000);
        }

        this.world.removeEntity(this);
        this.world.spawnEntityInWorld(entityVillager);
        entityVillager.addPotionEffect(new PotionEffect(Potion.CONFUSION.id, 200, 0));
        this.world.playAuxSFXAtEntity(
                (PlayerEntity) null,
                1017,
                new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ),
                0);
    }

    public int getTotalArmorValue() {
        int i = super.getTotalArmorValue() + 2;
        if (i > 20) {
            i = 20;
        }

        return i;
    }

    protected Item getDropItem() {
        return Items.rotten_flesh;
    }

    protected int getConversionTimeBoost() {
        int k1 = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        int j = 1;
        int i = k1;
        if (this.rand.nextFloat() < 0.01F) {
            int k = 0;
            int l = (int) this.posX - 4;

            while (l < (int) this.posX + 4 && k < 14) {
                int i1 = (int) this.posY - 4;

                while (i1 < (int) this.posY + 4 && k < 14) {
                    int j1 = (int) this.posZ - 4;

                    while (j1 < (int) this.posZ + 4 && k < 14) {
                        Block block = this.world.getBlockState(new BlockPos(l, i1, j1)).getBlock();
                        if (block == Blocks.iron_bars || block == Blocks.bed) {
                            if (this.rand.nextFloat() < 0.3F) {
                                j++;
                            }

                            k++;
                        }

                        j1++;
                        if (i != 0) {
                            break;
                        }
                    }

                    i1++;
                    if (i != 0) {
                        break;
                    }
                }

                l++;
                if (i != 0) {
                    break;
                }
            }
        }

        return j;
    }

    public void onLivingUpdate() {
        if (this.world.isDaytime() && !this.world.isRemote && !this.isChild()) {
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

        if (this.isRiding()
                && this.getAttackTarget() != null
                && this.ridingEntity instanceof EntityChicken) {
            ((EntityLiving) this.ridingEntity)
                    .getNavigator()
                    .setPath(this.getNavigator().getPath(), 1.5);
        }

        super.onLivingUpdate();
    }

    public void setVillager(boolean flag) {
        this.getDataWatcher().updateObject(13, (byte) (flag ? 1 : 0));
    }

    public boolean isVillager() {
        return this.getDataWatcher().getWatchableObjectByte(13) == 1;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        int i = EntitySpiderGroupData.method_7eec03da329a017ec143();
        if (super.attackEntityFrom(damageSource, f)) {
            LivingEntity livingEntity = this.getAttackTarget();
            if (livingEntity == null && damageSource.getEntity() instanceof LivingEntity) {
                livingEntity = (LivingEntity) damageSource.getEntity();
            }

            if (livingEntity != null
                    && this.world.getDifficulty() == EnumDifficulty.HARD
                    && this.rand.nextFloat()
                            < this.getEntityAttribute(net.minecraft.entity.monster.EntityZombie.reinforcementChance)
                                    .getAttributeValue()) {
                int j = MathHelper.floor(this.posX);
                int k = MathHelper.floor(this.posY);
                int l = MathHelper.floor(this.posZ);
                EntityZombie entityZombie = new EntityZombie(this.world);
                int i1 = 0;

                while (i1 < 50) {
                    int j1 =
                            j
                                    + MathHelper.nextIntInclusive(this.rand, 7, 40)
                                            * MathHelper.nextIntInclusive(this.rand, -1, 1);
                    int k1 =
                            k
                                    + MathHelper.nextIntInclusive(this.rand, 7, 40)
                                            * MathHelper.nextIntInclusive(this.rand, -1, 1);
                    int l1 =
                            l
                                    + MathHelper.nextIntInclusive(this.rand, 7, 40)
                                            * MathHelper.nextIntInclusive(this.rand, -1, 1);
                    if (World.doesBlockHaveSolidTopSurface(this.world, new BlockPos(j1, k1 - 1, l1))
                            && this.world.getLightFromNeighbors(new BlockPos(j1, k1, l1)) < 10) {
                        entityZombie.setPosition(j1, k1, l1);
                        if (!this.world.isAnyPlayerWithinRangeAt(j1, k1, l1, 7.0)
                                && this.world.checkNoEntityCollision(
                                        entityZombie.getEntityBoundingBox(), entityZombie)
                                && this.world
                                        .getCollidingBoundingBoxes(
                                                entityZombie, entityZombie.getEntityBoundingBox())
                                        .isEmpty()
                                && !this.world.isAnyLiquid(entityZombie.getEntityBoundingBox())) {
                            this.world.spawnEntityInWorld(entityZombie);
                            entityZombie.setAttackTarget(livingEntity);
                            entityZombie.onInitialSpawn(
                                    this.world.getDifficultyForLocation(new BlockPos(entityZombie)),
                                    (IEntityLivingData) null);
                            this.getEntityAttribute(net.minecraft.entity.monster.EntityZombie.reinforcementChance)
                                    .applyModifier(
                                            new AttributeModifier(
                                                    "Zombie reinforcement caller charge",
                                                    -0.05F,
                                                    0));
                            entityZombie
                                    .getEntityAttribute(net.minecraft.entity.monster.EntityZombie.reinforcementChance)
                                    .applyModifier(
                                            new AttributeModifier(
                                                    "Zombie reinforcement callee charge",
                                                    -0.05F,
                                                    0));
                            if (i != 0) {
                                break;
                            }
                        }
                    }

                    i1++;
                    if (i == 0) {
                        break;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public IEntityLivingData onInitialSpawn(
            DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
        int j = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        Object object = super.onInitialSpawn(difficultyInstance, iEntityLivingData);
        int i = j;
        float clampedAdditionalDifficulty = difficultyInstance.getClampedAdditionalDifficulty();
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * clampedAdditionalDifficulty);
        if (object == null) {
            object =
                    new EntityZombieGroupData(
                            this,
                            this.world.rand.nextFloat() < 0.05F,
                            this.world.rand.nextFloat() < 0.05F,
                            null);
        }

        if (object instanceof EntityZombieGroupData) {
            EntityZombieGroupData entityZombieGroupData =
                    (EntityZombieGroupData) object;
            if (entityZombieGroupData.b) {
                this.setVillager(true);
            }

            label69:
            if (entityZombieGroupData.il) {
                this.setChild(true);
                if (this.world.rand.nextFloat() < 0.05) {
                    List entitiesWithinAABB =
                            this.world.getEntitiesWithinAABB(
                                    EntityChicken.class,
                                    this.getEntityBoundingBox().expand(5.0, 3.0, 5.0),
                                    IEntitySelector.IS_STANDALONE);
                    if (!entitiesWithinAABB.isEmpty()) {
                        EntityChicken entityChicken = (EntityChicken) entitiesWithinAABB.get(0);
                        entityChicken.setChickenJockey(true);
                        this.mountEntity(entityChicken);
                    }

                    if (i == 0) {
                        break label69;
                    }
                }

                if (this.world.rand.nextFloat() < 0.05) {
                    EntityChicken entityChickenValue = new EntityChicken(this.world);
                    entityChickenValue.setLocationAndAngles(
                            this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                    entityChickenValue.onInitialSpawn(difficultyInstance, (IEntityLivingData) null);
                    entityChickenValue.setChickenJockey(true);
                    this.world.spawnEntityInWorld(entityChickenValue);
                    this.mountEntity(entityChickenValue);
                }
            }
        }

        this.setBreakDoorsAItask(this.rand.nextFloat() < clampedAdditionalDifficulty * 0.1F);
        this.setEquipmentBasedOnDifficulty(difficultyInstance);
        this.setEnchantmentBasedOnDifficulty(difficultyInstance);
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

        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
                .applyModifier(
                        new AttributeModifier(
                                "Random spawn bonus", this.rand.nextDouble() * 0.05F, 0));
        double d0 = this.rand.nextDouble() * 1.5 * clampedAdditionalDifficulty;
        if (d0 > 1.0) {
            this.getEntityAttribute(SharedMonsterAttributes.followRange)
                    .applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
        }

        if (this.rand.nextFloat() < clampedAdditionalDifficulty * 0.05F) {
            this.getEntityAttribute(net.minecraft.entity.monster.EntityZombie.reinforcementChance)
                    .applyModifier(
                            new AttributeModifier(
                                    "Leader zombie bonus", this.rand.nextDouble() * 0.25 + 0.5, 0));
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
                    .applyModifier(
                            new AttributeModifier(
                                    "Leader zombie bonus", this.rand.nextDouble() * 3.0 + 1.0, 2));
            this.setBreakDoorsAItask(true);
        }

        return (IEntityLivingData) object;
    }

    static {
        net.minecraft.entity.monster.EntityZombie.reinforcementChance =
                new RangedAttribute((IAttribute) null, "zombie.spawnReinforcements", 0.0, 0.0, 1.0)
                        .setDescription("Spawn Reinforcements Chance");
        net.minecraft.entity.monster.EntityZombie.babySpeedBoostUUID =
                UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
        net.minecraft.entity.monster.EntityZombie.babySpeedBoostModifier =
                new AttributeModifier(
                        net.minecraft.entity.monster.EntityZombie.babySpeedBoostUUID, "Baby speed boost", 0.5, 1);
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    public boolean isConverting() {
        return this.getDataWatcher().getWatchableObjectByte(14) == 1;
    }

    protected void applyEntityAI() {
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0, true));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0, true));
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0, false));
        this.targetTasks.addTask(
                1, new EntityAIHurtByTarget(this, true, new Class[] {EntityPigZombie.class}));
        this.targetTasks.addTask(
                2, new EntityAINearestAttackableTarget(this, PlayerEntity.class, true));
        this.targetTasks.addTask(
                2, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
        this.targetTasks.addTask(
                2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
    }

    public void onKillEntity(LivingEntity livingEntity) {
        super.onKillEntity(livingEntity);
        if ((this.world.getDifficulty() == EnumDifficulty.NORMAL
                        || this.world.getDifficulty() == EnumDifficulty.HARD)
                && livingEntity instanceof EntityVillager) {
            if (this.world.getDifficulty() != EnumDifficulty.HARD && this.rand.nextBoolean()) {
                return;
            }

            EntityZombie entityZombie = new EntityZombie(this.world);
            entityZombie.copyLocationAndAnglesFrom(livingEntity);
            this.world.removeEntity(livingEntity);
            entityZombie.onInitialSpawn(
                    this.world.getDifficultyForLocation(new BlockPos(entityZombie)),
                    (IEntityLivingData) null);
            entityZombie.setVillager(true);
            if (livingEntity.isChild()) {
                entityZombie.setChild(true);
            }

            this.world.spawnEntityInWorld(entityZombie);
            this.world.playAuxSFXAtEntity(
                    (PlayerEntity) null,
                    1016,
                    new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ),
                    0);
        }
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        if (compoundTag.getBoolean("IsBaby")) {
            this.setChild(true);
        }

        if (compoundTag.getBoolean("IsVillager")) {
            this.setVillager(true);
        }

        if (compoundTag.hasKey("ConversionTime", 99)
                && compoundTag.getInteger("ConversionTime") > -1) {
            this.startConversion(compoundTag.getInteger("ConversionTime"));
        }

        this.setBreakDoorsAItask(compoundTag.getBoolean("CanBreakDoors"));
    }

    protected final void setSize(float bcbValue, float bbZValue) {
        int i = this.zombieWidth > 0.0F && this.zombieHeight > 0.0F ? 1 : 0;
        this.zombieWidth = bcbValue;
        this.zombieHeight = bbZValue;
        if (i == 0) {
            this.multiplySize(1.0F);
        }
    }

    public boolean isChild() {
        return this.getDataWatcher().getWatchableObjectByte(12) == 1;
    }

    public void onUpdate() {
        if (!this.world.isRemote && this.isConverting()) {
            int conversionTimeBoost = this.getConversionTimeBoost();
            this.conversionTime -= conversionTimeBoost;
            if (this.conversionTime <= 0) {
                this.convertToVillager();
            }
        }

        super.onUpdate();
    }

    protected String getHurtSound() {
        return "mob.zombie.hurt";
    }

    public void setChild(boolean flag) {
        this.getDataWatcher().updateObject(12, (byte) (flag ? 1 : 0));
        if (this.world != null && !this.world.isRemote) {
            IAttributeInstance entityAttribute =
                    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            entityAttribute.removeModifier(net.minecraft.entity.monster.EntityZombie.babySpeedBoostModifier);
            if (flag) {
                entityAttribute.applyModifier(net.minecraft.entity.monster.EntityZombie.babySpeedBoostModifier);
            }
        }

        this.setChildSize(flag);
    }

    protected int getExperiencePoints(PlayerEntity playerEntity) {
        if (this.isChild()) {
            this.experienceValue = (int) (this.experienceValue * 2.5F);
        }

        return super.getExperiencePoints(playerEntity);
    }

    protected String getDeathSound() {
        return "mob.zombie.death";
    }

    public boolean attackEntityAsMob(Entity entity) {
        boolean flag = super.attackEntityAsMob(entity);
        if (flag) {
            int difficultyId = this.world.getDifficulty().getDifficultyId();
            if (this.getHeldItem() == null
                    && this.isBurning()
                    && this.rand.nextFloat() < difficultyId * 0.3F) {
                entity.setFire(2 * difficultyId);
            }
        }

        return flag;
    }

    public void setBreakDoorsAItask(boolean bbXValue) {
        int i = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        if (this.isBreakDoorsTaskSet != bbXValue) {
            this.isBreakDoorsTaskSet = bbXValue;
            if (bbXValue) {
                this.tasks.addTask(1, this.breakDoor);
                if (i == 0) {
                    return;
                }
            }

            this.tasks.removeTask(this.breakDoor);
        }
    }

    public boolean isBreakDoorsTaskSet() {
        return this.isBreakDoorsTaskSet;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23F);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
        this.getAttributeMap()
                .registerAttribute(net.minecraft.entity.monster.EntityZombie.reinforcementChance)
                .setBaseValue(this.rand.nextDouble() * 0.1F);
    }

    protected void startConversion(int aIDValue) {
        this.conversionTime = aIDValue;
        this.getDataWatcher().updateObject(14, (byte) 1);
        this.removePotionEffect(Potion.WEAKNESS.id);
        this.addPotionEffect(
                new PotionEffect(
                        Potion.DAMAGE_BOOST.id,
                        aIDValue,
                        Math.min(this.world.getDifficulty().getDifficultyId() - 1, 0)));
        this.world.setEntityState(this, (byte) 16);
    }

    protected String getLivingSound() {
        return "mob.zombie.say";
    }

    protected boolean canDespawn() {
        return !this.isConverting();
    }

    protected boolean method_14d87da4146070fd903f(ItemStack itemStack) {
        return itemStack.getItem() == Items.egg && this.isChild() && this.isRiding()
                ? false
                : super.method_14d87da4146070fd903f(itemStack);
    }

    protected final void multiplySize(float f) {
        super.setSize(this.zombieWidth * f, this.zombieHeight * f);
    }

    protected void addRandomArmor() {
        int i = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        switch (this.rand.nextInt(3)) {
            case 0:
                this.dropItem(Items.iron_ingot, 1);
                if (i == 0) {
                    break;
                }
            case 1:
                this.dropItem(Items.carrot, 1);
                if (i == 0) {
                    break;
                }
            case 2:
                this.dropItem(Items.potato, 1);
        }
    }

    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        if (damageSource.getEntity() instanceof EntityCreeper
                && !(this instanceof EntityPigZombie)
                && ((EntityCreeper) damageSource.getEntity()).getPowered()
                && ((EntityCreeper) damageSource.getEntity()).isAIEnabled()) {
            ((EntityCreeper) damageSource.getEntity()).func_175493_co();
            this.entityDropItem(new ItemStack(Items.skull, 1, 2), 0.0F);
        }
    }
}
