package net.minecraft.entity.player;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEventAction;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettingsGameType;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.event.impl.WaterPushEvent;
import remix.module.movement.SprintModule;
import remix.module.render.FreeCamModule;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public abstract class PlayerEntity extends LivingEntity {
    private int xpSeed;
    protected boolean sleeping;
    public int experienceTotal;
    public double previousChasingPosY;
    protected int flyToggleTimer;
    public PlayerCapabilities capabilities;
    private boolean hasReducedDebug;
    public float sleepRenderOffsetX;
    public BlockPos playerLocation;
    public int xpCooldown;
    private static String[] packedTextFragments;
    public Container inventoryContainer;
    public float speedOnGround;
    private int lastXPSound;
    public float sleepRenderOffsetZ;
    public Container openContainer;
    private int itemInUseCount;
    public float prevCameraYaw;
    public float cameraYaw;
    private ItemStack itemInUse;
    protected FoodStats foodStats;
    private BlockPos spawnChunk;
    private boolean spawnForced;
    public double previousChasingPosX;
    public float experience;
    public double previousChasingPosZ;
    public float speedInAir;
    private BlockPos startMinecartRidingCoordinate;
    public float sleepRenderOffsetY;
    private InventoryEnderChest theInventoryEnderChest;

    public double chasingPosY;
    public EntityFishHook fishEntity;
    private int sleepTimer;
    private GameProfile gameProfile;
    public double chasingPosX;

    public InventoryPlayer inventory = new InventoryPlayer(this);
    private static String[] legacyModuleToggleMarker;
    public double chasingPosZ;
    public int experienceLevel;

    public void addStat(StatBase statBase, int i) {}

    protected int getExperiencePoints(PlayerEntity playerEntity) {
        if (this.world.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            return 0;
        }

        int i = this.experienceLevel * 7;
        return i > 100 ? 100 : i;
    }

    protected void updateEntityActionState() {
        super.updateEntityActionState();
        this.nx();
        this.rotationYawHead = this.rotationYaw;
    }

    public void sendPlayerAbilities() {}

    public static UUID getUUID(GameProfile p171_0) {
        java.util.UUID uuid = p171_0.getId();
        if (uuid == null) {
            uuid = net.minecraft.entity.player.PlayerEntity.getOfflineUUID(p171_0.getName());
        }

        return uuid;
    }

    public void clonePlayer(PlayerEntity previous, boolean keepEverything) {
        boolean repeatInventoryCopy = InventoryPlayer.getLegacyInventoryFlowToken() == 0;
        if (keepEverything) {
            this.inventory.copyInventory(previous.inventory);
            this.setHealth(previous.getHealth());
            this.foodStats = previous.foodStats;
            this.copyExperienceAndScore(previous);
            this.teleportDirection = previous.teleportDirection;
        }
        if ((!keepEverything || repeatInventoryCopy)
                && this.world.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.copyInventory(previous.inventory);
            this.copyExperienceAndScore(previous);
        }

        this.theInventoryEnderChest = previous.theInventoryEnderChest;
        this.getDataWatcher()
                .updateObject(10, previous.getDataWatcher().getWatchableObjectByte(10));
    }

    private void copyExperienceAndScore(PlayerEntity previous) {
        this.experienceLevel = previous.experienceLevel;
        this.experienceTotal = previous.experienceTotal;
        this.experience = previous.experience;
        this.setScore(previous.getScore());
    }

    public void jump() {
        int j = InventoryPlayer.getLegacyInventoryFlowMarker();
        super.jump();
        this.triggerAchievement(StatList.jumpStat);
        int i = j;
        if (this.isSprinting()) {
            this.addExhaustion(0.8F);
            if (i == 0) {
                return;
            }
        }

        this.addExhaustion(0.2F);
    }

    private void collideWithPlayer(Entity entity) {
        entity.onCollideWithPlayer(this);
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        if (this.capabilities.disableDamage && !damageSource.canHarmInCreative()) {
            return false;
        }

        this.NJ = 0;
        if (this.getHealth() <= 0.0F) {
            return false;
        }

        if (this.isPlayerSleeping() && !this.world.isRemote) {
            this.wakeUpPlayer(true, true, false);
        }

        if (damageSource.isDifficultyScaled()) {
            if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
                f = 0.0F;
            }

            if (this.world.getDifficulty() == EnumDifficulty.EASY) {
                f = f / 2.0F + 1.0F;
            }

            if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                f = f * 3.0F / 2.0F;
            }
        }

        if (f == 0.0F) {
            return false;
        }

        Entity entity = damageSource.getEntity();
        if (entity instanceof EntityArrow && ((EntityArrow) entity).shootingEntity != null) {
            entity = ((EntityArrow) entity).shootingEntity;
        }

        return super.attackEntityFrom(damageSource, f);
    }

    public float getArmorVisibility() {
        int j = 0;
        int k = InventoryPlayer.getLegacyInventoryFlowMarker();
        ItemStack[] bkX = this.inventory.armorInventory;
        int i = k;

        for (ItemStack itemStack : bkX) {
            if (itemStack != null) {
                j++;
            }

            if (i != 0) {
                break;
            }
        }

        return (float) j / this.inventory.armorInventory.length;
    }

    public float getToolDigEfficiency(Block block) {
        int k = InventoryPlayer.getLegacyInventoryFlowMarker();
        float strVsBlock = this.inventory.getStrVsBlock(block);
        int i = k;
        if (strVsBlock > 1.0F) {
            int j = EnchantmentHelper.getEfficiencyModifier(this);
            ItemStack itemStack = this.inventory.getCurrentItem();
            if (j > 0 && itemStack != null) {
                strVsBlock += j * j + 1;
            }
        }

        if (this.isPotionActive(Potion.DIG_SPEED)) {
            strVsBlock *=
                    1.0F + (this.getActivePotionEffect(Potion.DIG_SPEED).getAmplifier() + 1) * 0.2F;
        }

        if (this.isPotionActive(Potion.DIG_SLOWDOWN)) {
            float f1 = 1.0F;
            switch (this.getActivePotionEffect(Potion.DIG_SLOWDOWN).getAmplifier()) {
                case 0:
                    f1 = 0.3F;
                    if (i == 0) {
                        break;
                    }
                case 1:
                    f1 = 0.09F;
                    if (i == 0) {
                        break;
                    }
                case 2:
                    f1 = 0.0027F;
                    if (i == 0) {
                        break;
                    }
                case 3:
                default:
                    f1 = 8.1E-4F;
            }

            strVsBlock *= f1;
        }

        if (this.isInsideOfMaterial(Material.water)
                && !EnchantmentHelper.getAquaAffinityModifier(this)) {
            strVsBlock /= 5.0F;
        }

        if (!this.onGround) {
            strVsBlock /= 5.0F;
        }

        return strVsBlock;
    }

    public boolean isPlayerSleeping() {
        return this.sleeping;
    }

    public boolean isBlocking() {
        return this.isUsingItem()
                && this.itemInUse.getItem().getItemUseAction(this.itemInUse) == EnumAction.BLOCK;
    }

    public void setScore(int p171_0) {
        this.dataWatcher.updateObject(18, p171_0);
    }

    public void addToPlayerScore(Entity killed, int points) {
        boolean legacyFirstObjectiveOnly = InventoryPlayer.getLegacyInventoryFlowMarker() != 0;
        this.addScore(points);
        Scoreboard scoreboard = this.getWorldScoreboard();
        Collection objectives =
                scoreboard.getObjectivesFromCriteria(IScoreObjectiveCriteria.totalKillCount);
        boolean playerKill = killed instanceof PlayerEntity;
        if (playerKill) {
            this.triggerAchievement(StatList.playerKillsStat);
            objectives.addAll(
                    scoreboard.getObjectivesFromCriteria(IScoreObjectiveCriteria.playerKillCount));
            objectives.addAll(this.updateTeamKillObjectives(killed));
        }
        if (!playerKill || legacyFirstObjectiveOnly) {
            this.triggerAchievement(StatList.mobKillsStat);
        }

        for (Object entry : objectives) {
            ScoreObjective objective = (ScoreObjective) entry;
            this.getWorldScoreboard()
                    .getValueFromObjective(this.getName(), objective)
                    .incrementScore();
            if (legacyFirstObjectiveOnly) {
                break;
            }
        }
    }

    public ItemStack getHeldItem() {
        return this.inventory.getCurrentItem();
    }

    public boolean isInvisibleToPlayer(PlayerEntity playerEntity) {
        if (!this.isInvisible()) {
            return false;
        }

        if (playerEntity.isSpectator()) {
            return false;
        }

        Team team = this.getTeam();
        return team == null
                || playerEntity == null
                || playerEntity.getTeam() != team
                || !team.getSeeFriendlyInvisiblesEnabled();
    }

    public int getItemInUseDuration() {
        return this.isUsingItem()
                ? this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount
                : 0;
    }

    public void handleHealthUpdate(byte b0) {
        int i = InventoryPlayer.getLegacyInventoryFlowMarker();
        if (b0 == 9) {
            this.onItemUseFinish();
            if (i == 0) {
                return;
            }
        }

        if (b0 == 23) {
            this.hasReducedDebug = false;
            if (i == 0) {
                return;
            }
        }

        if (b0 == 22) {
            this.hasReducedDebug = true;
            if (i == 0) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    public void setAbsorptionAmount(float p171_0) {
        if (p171_0 < 0.0F) {
            p171_0 = 0.0F;
        }

        this.getDataWatcher().updateObject(17, p171_0);
    }

    protected String getSwimSound() {
        return "game.player.swim";
    }

    public boolean canOpen(LockCode lockCode) {
        if (lockCode.isEmpty()) {
            return true;
        }

        net.minecraft.item.ItemStack currentEquippedItem = this.getCurrentEquippedItem();
        return currentEquippedItem != null
                && currentEquippedItem.hasDisplayName()
                && currentEquippedItem.getDisplayName().equals(lockCode.getLock());
    }

    public static void setLegacyModuleToggleMarker(String[] astring) {
        legacyModuleToggleMarker = astring;
    }

    protected void damageEntity(DamageSource damageSource, float p171_1) {
        if (!this.isEntityInvulnerable(damageSource)) {
            if (!damageSource.isUnblockable() && this.isBlocking() && p171_1 > 0.0F) {
                p171_1 = (1.0F + p171_1) * 0.5F;
            }

            p171_1 = this.applyArmorCalculations(damageSource, p171_1);
            p171_1 = this.applyPotionDamageCalculations(damageSource, p171_1);
            float f1 = p171_1;
            p171_1 = java.lang.Math.max(p171_1 - this.getAbsorptionAmount(), 0.0F);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (f1 - p171_1));
            if (p171_1 != 0.0F) {
                this.addExhaustion(damageSource.getHungerDamage());
                float health = this.getHealth();
                this.setHealth(this.getHealth() - p171_1);
                this.getCombatTracker().trackDamage(damageSource, health, p171_1);
                if (p171_1 < 3.4028235E37F) {
                    this.addStat(
                            net.minecraft.stats.StatList.damageTakenStat,
                            java.lang.Math.round(p171_1 * 10.0F));
                }
            }
        }
    }

    public void displayGUIBook(ItemStack itemStack) {}

    public ItemStack getCurrentArmor(int i) {
        return this.inventory.armorItemInSlot(i);
    }

    public int getMaxInPortalTime() {
        return this.capabilities.disableDamage ? 0 : 80;
    }

    public void resetStat(StatBase statBase) {}

    public void displayGui(IInteractionObject iInteractionObject) {}

    private boolean isInBed() {
        return this.world.getBlockState(this.playerLocation).getBlock() == Blocks.bed;
    }

    public void preparePlayerToSpawn() {
        this.setSize(0.6F, 1.8F);
        super.preparePlayerToSpawn();
        this.setHealth(this.getMaxHealth());
        this.deathTime = 0;
    }

    protected void onItemUseFinish() {
        if (this.itemInUse != null) {
            this.updateItemUse(this.itemInUse, 16);
            int stackSize = this.itemInUse.stackSize;
            ItemStack itemStack = this.itemInUse.onItemUseFinish(this.world, this);
            if (itemStack != this.itemInUse
                    || itemStack != null && itemStack.stackSize != stackSize) {
                this.inventory.mainInventory[this.inventory.currentItem] = itemStack;
                if (itemStack.stackSize == 0) {
                    this.inventory.mainInventory[this.inventory.currentItem] = null;
                }
            }

            this.clearItemInUse();
        }
    }

    public float getBedOrientationInDegrees() {
        if (this.playerLocation != null) {
            Facing facing =
                    (Facing)
                            this.world
                                    .getBlockState(this.playerLocation)
                                    .getValue(BlockDirectional.AGE);
            switch (facing) {
                case SOUTH:
                    return 90.0F;
                case NORTH:
                    return 270.0F;
                case WEST:
                    return 0.0F;
                case EAST:
                    return 180.0F;
            }
        }

        return 0.0F;
    }

    public EntityPlayerEnumStatus trySleep(BlockPos bedPosition) {
        int inventoryFlowToken = InventoryPlayer.getLegacyInventoryFlowMarker();
        if (!this.world.isRemote) {
            if (this.isPlayerSleeping() || !this.isEntityAlive()) {
                return EntityPlayerEnumStatus.OTHER_PROBLEM;
            }

            if (!this.world.provider.isSurfaceWorld()) {
                return EntityPlayerEnumStatus.NOT_POSSIBLE_HERE;
            }

            if (this.world.isDaytime()) {
                return EntityPlayerEnumStatus.NOT_POSSIBLE_NOW;
            }

            if (Math.abs(this.posX - bedPosition.getX()) > 3.0
                    || Math.abs(this.posY - bedPosition.getY()) > 2.0
                    || Math.abs(this.posZ - bedPosition.getZ()) > 3.0) {
                return EntityPlayerEnumStatus.TOO_FAR_AWAY;
            }

            double d0 = 8.0;
            double d1 = 5.0;
            List entitiesWithinAABB =
                    this.world.getEntitiesWithinAABB(
                            MonsterEntity.class,
                            new BoundingBox(
                                    bedPosition.getX() - d0,
                                    bedPosition.getY() - d1,
                                    bedPosition.getZ() - d0,
                                    bedPosition.getX() + d0,
                                    bedPosition.getY() + d1,
                                    bedPosition.getZ() + d0));
            if (!entitiesWithinAABB.isEmpty()) {
                return EntityPlayerEnumStatus.NOT_SAFE;
            }
        }

        if (this.isRiding()) {
            this.mountEntity(null);
        }

        this.setSize(0.2F, 0.2F);
        boolean bedLoaded = this.world.isBlockLoaded(bedPosition);
        if (bedLoaded) {
            Facing facing =
                    (Facing) this.world.getBlockState(bedPosition).getValue(BlockDirectional.AGE);
            float offsetX = 0.5F;
            float offsetZ = 0.5F;
            switch (facing) {
                case SOUTH:
                    offsetZ = 0.9F;
                    if (inventoryFlowToken == 0) {
                        break;
                    }
                case NORTH:
                    offsetZ = 0.1F;
                    if (inventoryFlowToken == 0) {
                        break;
                    }
                case WEST:
                    offsetX = 0.1F;
                    if (inventoryFlowToken == 0) {
                        break;
                    }
                case EAST:
                    offsetX = 0.9F;
            }

            this.setSleepRenderOffsets(facing);
            this.setPosition(
                    bedPosition.getX() + offsetX,
                    bedPosition.getY() + 0.6875F,
                    bedPosition.getZ() + offsetZ);
        }
        if (!bedLoaded || inventoryFlowToken != 0) {
            this.setPosition(
                    bedPosition.getX() + 0.5F,
                    bedPosition.getY() + 0.6875F,
                    bedPosition.getZ() + 0.5F);
        }

        this.sleeping = true;
        this.sleepTimer = 0;
        this.playerLocation = bedPosition;
        this.motionX = this.motionZ = this.motionY = 0.0;
        if (!this.world.isRemote) {
            this.world.updateAllPlayersSleepingFlag();
        }

        return EntityPlayerEnumStatus.OK;
    }

    public static BlockPos getBedSpawnLocation(World worldValue, BlockPos blockPos, boolean flag) {
        if (worldValue.getBlockState(blockPos).getBlock() != Blocks.bed) {
            if (!flag) {
                return null;
            }

            Material material = worldValue.getBlockState(blockPos).getBlock().getMaterial();
            Material materialValue =
                    worldValue.getBlockState(blockPos.offsetUp()).getBlock().getMaterial();
            int i = !material.isSolid() && !material.isLiquid() ? 1 : 0;
            int j = !materialValue.isSolid() && !materialValue.isLiquid() ? 1 : 0;
            return i != 0 && j != 0 ? blockPos : null;
        } else {
            return BlockBed.getSafeExitLocation(worldValue, blockPos, 0);
        }
    }

    public void onKillEntity(LivingEntity livingEntity) {
        if (livingEntity instanceof net.minecraft.entity.monster.IMob) {
            this.triggerAchievement(net.minecraft.stats.AchievementList.killEnemy);
        }

        net.minecraft.entity.EntityListEntityEggInfo entityListEntityEggInfo =
                (net.minecraft.entity.EntityListEntityEggInfo)
                        net.minecraft.entity.EntityList.entityEggs.get(
                                net.minecraft.entity.EntityList.getEntityID(livingEntity));
        if (entityListEntityEggInfo != null) {
            this.triggerAchievement(entityListEntityEggInfo.field_151512_d);
        }
    }

    protected String getDeathSound() {
        return "game.player.die";
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
        compoundTag.setInteger("SelectedItemSlot", this.inventory.currentItem);
        compoundTag.setBoolean("Sleeping", this.sleeping);
        compoundTag.setShort("SleepTimer", (short) this.sleepTimer);
        compoundTag.setFloat("XpP", this.experience);
        compoundTag.setInteger("XpLevel", this.experienceLevel);
        compoundTag.setInteger("XpTotal", this.experienceTotal);
        compoundTag.setInteger("XpSeed", this.xpSeed);
        compoundTag.setInteger("Score", this.getScore());
        if (this.spawnChunk != null) {
            compoundTag.setInteger("SpawnX", this.spawnChunk.getX());
            compoundTag.setInteger("SpawnY", this.spawnChunk.getY());
            compoundTag.setInteger("SpawnZ", this.spawnChunk.getZ());
            compoundTag.setBoolean("SpawnForced", this.spawnForced);
        }

        this.foodStats.writeNBT(compoundTag);
        this.capabilities.writeCapabilitiesToNBT(compoundTag);
        compoundTag.setTag("EnderItems", this.theInventoryEnderChest.saveInventoryToNBT());
        ItemStack itemStack = this.inventory.getCurrentItem();
        if (itemStack != null && itemStack.getItem() != null) {
            compoundTag.setTag("SelectedItem", itemStack.writeToNBT(new CompoundTag()));
        }
    }

    // region Static initialization and string tables
    static {
        net.minecraft.entity.player.PlayerEntity.packedTextFragments = new String[2];
        net.minecraft.entity.player.PlayerEntity.packedTextFragments[0] = new String(" ");
        setLegacyModuleToggleMarker(null);
    }

    // endregion

    public boolean sendCommandFeedback() {
        return MinecraftServer.getServer()
                .worldServers[0]
                .getGameRules()
                .getGameRuleBooleanValue("sendCommandFeedback");
    }

    public boolean canHarvestBlock(Block block) {
        return this.inventory.canHeldItemHarvest(block);
    }

    protected String getFallSoundString(int i) {
        return i > 4 ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
    }

    public float getAbsorptionAmount() {
        return this.getDataWatcher().getWatchableObjectFloat(17);
    }

    public float getAIMoveSpeed() {
        return (float)
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
    }

    public float getEyeHeight() {
        float f = 1.62F;
        if (this.isPlayerSleeping()) {
            f = 0.2F;
        }

        if (this.isSneaking()) {
            f -= 0.08F;
        }

        return f;
    }

    public void onLivingUpdate() {
        int i = InventoryPlayer.getLegacyInventoryFlowMarker();
        if (this.flyToggleTimer > 0) {
            this.flyToggleTimer--;
        }

        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL
                && this.world.getGameRules().getGameRuleBooleanValue("naturalRegeneration")) {
            if (this.getHealth() < this.getMaxHealth() && this.ticksExisted % 20 == 0) {
                this.heal(1.0F);
            }

            if (this.foodStats.needFood() && this.ticksExisted % 10 == 0) {
                this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + 1);
            }
        }

        this.inventory.decrementAnimations();
        this.prevCameraYaw = this.cameraYaw;
        super.onLivingUpdate();
        IAttributeInstance entityAttribute =
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (!this.world.isRemote) {
            entityAttribute.setBaseValue(this.capabilities.getWalkSpeed());
        }

        this.jumpMovementFactor = this.speedInAir;
        if (this.isSprinting()) {
            this.jumpMovementFactor = (float) (this.jumpMovementFactor + this.speedInAir * 0.3);
        }

        this.setAIMoveSpeed((float) entityAttribute.getAttributeValue());
        float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float f1 = (float) (Math.atan(-this.motionY * 0.2F) * 15.0);
        if (f > 0.1F) {
            f = 0.1F;
        }

        if (!this.onGround || this.getHealth() <= 0.0F) {
            f = 0.0F;
        }

        if (this.onGround || this.getHealth() <= 0.0F) {
            f1 = 0.0F;
        }

        this.cameraYaw = this.cameraYaw + (f - this.cameraYaw) * 0.4F;
        this.cameraPitch = this.cameraPitch + (f1 - this.cameraPitch) * 0.8F;
        if (this.getHealth() > 0.0F && !this.isSpectator()) {
            BoundingBox boundingBoxValue = null;
            if (this.ridingEntity != null && !this.ridingEntity.isDead) {
                boundingBoxValue =
                        this.getEntityBoundingBox()
                                .union(this.ridingEntity.getEntityBoundingBox())
                                .expand(1.0, 0.0, 1.0);
            } else {
                boundingBoxValue = this.getEntityBoundingBox().expand(1.0, 0.5, 1.0);
            }

            List entitiesWithinAABBExcludingEntity =
                    this.world.getEntitiesWithinAABBExcludingEntity(this, boundingBoxValue);
            int j = 0;

            while (j < entitiesWithinAABBExcludingEntity.size()) {
                Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(j);
                if (!entity.isDead) {
                    this.collideWithPlayer(entity);
                }

                j++;
                if (i != 0) {
                    break;
                }
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            InventoryPlayer.setLegacyInventoryFlowMarker(++i);
        }
    }

    public void addExperience(int i) {
        int l = InventoryPlayer.getLegacyInventoryFlowMarker();
        this.addScore(i);
        int k = Integer.MAX_VALUE - this.experienceTotal;
        int j = l;
        if (i > k) {
            i = k;
        }

        this.experience = this.experience + (float) i / this.xpBarCap();
        this.experienceTotal += i;

        while (this.experience >= 1.0F) {
            this.experience = (this.experience - 1.0F) * this.xpBarCap();
            this.addExperienceLevel(1);
            this.experience = this.experience / this.xpBarCap();
            if (j != 0) {
                break;
            }
        }
    }

    public int xpBarCap() {
        return this.experienceLevel >= 30
                ? 112 + (this.experienceLevel - 30) * 9
                : (this.experienceLevel >= 15
                        ? 37 + (this.experienceLevel - 15) * 5
                        : 7 + this.experienceLevel * 2);
    }

    public void playSound(String text, float p171_1, float p171_2) {
        this.world.playSoundToNearExcept(this, text, p171_1, p171_2);
    }

    protected void damageArmor(float f) {
        this.inventory.damageArmor(f);
    }

    public boolean canEat(boolean flag) {
        return (flag || this.foodStats.needFood()) && !this.capabilities.disableDamage;
    }

    public InventoryEnderChest getInventoryEnderChest() {
        return this.theInventoryEnderChest;
    }

    protected boolean canTriggerWalking() {
        return !this.capabilities.isFlying;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap()
                .registerAttribute(SharedMonsterAttributes.attackDamage)
                .setBaseValue(1.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.1F);
    }

    public boolean hasReducedDebug() {
        return this.hasReducedDebug;
    }

    public int getScore() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    public void respawnPlayer() {}

    public void addChatComponentMessage(ChatComponent chatComponent) {}

    public void displayVillagerTradeGui(IMerchant iMerchant) {}

    public GameProfile getGameProfile() {
        return this.gameProfile;
    }

    public Scoreboard getWorldScoreboard() {
        return this.world.getScoreboard();
    }

    public boolean isLocalUser() {
        return false;
    }

    protected void closeScreen() {
        this.openContainer = this.inventoryContainer;
    }

    public BlockPos getBedLocation() {
        return this.spawnChunk;
    }

    public void wakeUpPlayer(boolean flag, boolean flag1, boolean flag2) {
        this.setSize(0.6F, 1.8F);
        BlockState blockState = this.world.getBlockState(this.playerLocation);
        if (this.playerLocation != null && blockState.getBlock() == Blocks.bed) {
            this.world.setBlockState(
                    this.playerLocation, blockState.withProperty(BlockBed.OCCUPIED_PROP, false), 4);
            BlockPos blockPos = BlockBed.getSafeExitLocation(this.world, this.playerLocation, 0);
            if (blockPos == null) {
                blockPos = this.playerLocation.offsetUp();
            }

            this.setPosition(
                    blockPos.getX() + 0.5F, blockPos.getY() + 0.1F, blockPos.getZ() + 0.5F);
        }

        this.sleeping = false;
        if (!this.world.isRemote && flag1) {
            this.world.updateAllPlayersSleepingFlag();
        }

        this.sleepTimer = flag ? 0 : 100;
        if (flag2) {
            this.setSpawnPoint(this.playerLocation, false);
        }
    }

    public int getPortalCooldown() {
        return 10;
    }

    public void fall(float f, float f1) {
        if (!this.capabilities.allowFlying) {
            if (f >= 2.0F) {
                this.addStat(StatList.distanceFallenStat, (int) Math.round(f * 100.0));
            }

            super.fall(f, f1);
        }
    }

    public void moveEntityWithHeading(float strafe, float forward) {
        boolean repeatFlyingMove = InventoryPlayer.getLegacyInventoryFlowToken() == 0;
        double oldX = this.posX;
        double oldY = this.posY;
        double oldZ = this.posZ;
        boolean flying = this.capabilities.isFlying && this.ridingEntity == null;
        if (flying) {
            double verticalSpeed = this.motionY;
            float oldMovementFactor = this.jumpMovementFactor;
            this.jumpMovementFactor =
                    this.capabilities.getFlySpeed() * (this.isSprinting() ? 2 : 1);
            super.moveEntityWithHeading(strafe, forward);
            this.motionY = verticalSpeed * 0.6;
            this.jumpMovementFactor = oldMovementFactor;
        }
        if (!flying || repeatFlyingMove) {
            super.moveEntityWithHeading(strafe, forward);
        }
        this.addMovementStat(this.posX - oldX, this.posY - oldY, this.posZ - oldZ);
    }

    public void triggerAchievement(StatBase statBase) {
        this.addStat(statBase, 1);
    }

    public void setSpawnPoint(BlockPos bdyValue, boolean aJFValue) {
        int i = InventoryPlayer.getLegacyInventoryFlowToken();
        if (bdyValue != null) {
            this.spawnChunk = bdyValue;
            this.spawnForced = aJFValue;
            if (i != 0) {
                return;
            }
        }

        this.spawnChunk = null;
        this.spawnForced = false;
    }

    protected void updateItemUse(ItemStack itemStack, int particleCount) {
        int inventoryFlowToken = InventoryPlayer.getLegacyInventoryFlowToken();
        if (itemStack.getItemUseAction() == EnumAction.DRINK) {
            this.playSound("random.drink", 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (itemStack.getItemUseAction() == EnumAction.EAT) {
            int particle = 0;

            while (particle < particleCount) {
                Vec3 velocity =
                        new Vec3(
                                (this.rand.nextFloat() - 0.5) * 0.1,
                                Math.random() * 0.1 + 0.1,
                                0.0);
                velocity = velocity.rotatePitch(-this.rotationPitch * (float) Math.PI / 180.0F);
                velocity = velocity.rotateYaw(-this.rotationYaw * (float) Math.PI / 180.0F);
                double verticalOffset = -this.rand.nextFloat() * 0.6 - 0.3;
                Vec3 particlePosition =
                        new Vec3((this.rand.nextFloat() - 0.5) * 0.3, verticalOffset, 0.6);
                particlePosition =
                        particlePosition.rotatePitch(
                                -this.rotationPitch * (float) Math.PI / 180.0F);
                particlePosition =
                        particlePosition.rotateYaw(-this.rotationYaw * (float) Math.PI / 180.0F);
                particlePosition =
                        particlePosition.addVector(
                                this.posX, this.posY + this.getEyeHeight(), this.posZ);
                boolean hasSubtypes = itemStack.getHasSubtypes();
                if (hasSubtypes) {
                    this.world.spawnParticle(
                            EnumParticleTypes.ITEM_CRACK,
                            particlePosition.x,
                            particlePosition.y,
                            particlePosition.z,
                            velocity.x,
                            velocity.y + 0.05,
                            velocity.z,
                            new int[] {
                                Item.getIdFromItem(itemStack.getItem()), itemStack.getItemDamage()
                            });
                }

                if (!hasSubtypes || inventoryFlowToken == 0) {
                    this.world.spawnParticle(
                            EnumParticleTypes.ITEM_CRACK,
                            particlePosition.x,
                            particlePosition.y,
                            particlePosition.z,
                            velocity.x,
                            velocity.y + 0.05,
                            velocity.z,
                            new int[] {Item.getIdFromItem(itemStack.getItem())});
                }
                particle++;
                if (inventoryFlowToken == 0) {
                    break;
                }
            }

            this.playSound(
                    "random.eat",
                    0.5F + 0.5F * this.rand.nextInt(2),
                    (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
        }
    }

    private Collection updateTeamKillObjectives(Entity entity) {
        int l = InventoryPlayer.getLegacyInventoryFlowToken();
        ScorePlayerTeam playersTeam = this.getWorldScoreboard().getPlayersTeam(this.getName());
        int i = l;
        if (playersTeam != null) {
            int j = playersTeam.getChatFormat().getColorIndex();
            if (j >= 0 && j < IScoreObjectiveCriteria.killedByTeam.length) {
                for (java.util.Iterator<?> __recoveryIterator171_66313 =
                                (this.getWorldScoreboard()
                                                .getObjectivesFromCriteria(
                                                        IScoreObjectiveCriteria.killedByTeam[j]))
                                        .iterator();
                        (__recoveryIterator171_66313).hasNext(); ) {
                    ScoreObjective scoreObjective =
                            (ScoreObjective) (__recoveryIterator171_66313).next();
                    Score valueFromObjective =
                            this.getWorldScoreboard()
                                    .getValueFromObjective(entity.getName(), scoreObjective);
                    valueFromObjective.incrementScore();
                    if (i == 0) {
                        break;
                    }
                }
            }
        }

        ScorePlayerTeam playersTeamValue =
                this.getWorldScoreboard().getPlayersTeam(entity.getName());
        if (playersTeamValue != null) {
            int k = playersTeamValue.getChatFormat().getColorIndex();
            if (k >= 0 && k < IScoreObjectiveCriteria.teamKill.length) {
                return this.getWorldScoreboard()
                        .getObjectivesFromCriteria(IScoreObjectiveCriteria.teamKill[k]);
            }
        }

        return Lists.newArrayList();
    }

    public void destroyCurrentEquippedItem() {
        this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
    }

    public void setItemInUse(ItemStack bdwValue, int bdtValue) {
        if (bdwValue != this.itemInUse) {
            this.itemInUse = bdwValue;
            this.itemInUseCount = bdtValue;
            if (!this.world.isRemote) {
                this.setEating(true);
            }
        }
    }

    protected String getSplashSound() {
        return "game.player.swim.splash";
    }

    public double getYOffset() {
        return -0.35;
    }

    public void addExhaustion(float f) {
        if (!this.capabilities.disableDamage && !this.world.isRemote) {
            this.foodStats.addExhaustion(f);
        }
    }

    private void addMountedMovementStat(double d0, double d1, double d2) {
        int i = InventoryPlayer.getLegacyInventoryFlowToken();
        if (this.ridingEntity != null) {
            int j = Math.round(MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
            if (j > 0) {
                if (this.ridingEntity instanceof EntityMinecart) {
                    this.addStat(StatList.distanceByMinecartStat, j);
                    if (this.startMinecartRidingCoordinate == null) {
                        this.startMinecartRidingCoordinate = new BlockPos(this);
                        if (i != 0) {
                            return;
                        }
                    }

                    if (!(this.startMinecartRidingCoordinate.distanceSq(
                                    MathHelper.floor(this.posX),
                                    MathHelper.floor(this.posY),
                                    MathHelper.floor(this.posZ))
                            >= 1000000.0)) {
                        return;
                    }

                    this.triggerAchievement(AchievementList.onARail);
                    if (i != 0) {
                        return;
                    }
                }

                if (this.ridingEntity instanceof EntityBoat) {
                    this.addStat(StatList.distanceByBoatStat, j);
                    if (i != 0) {
                        return;
                    }
                }

                if (this.ridingEntity instanceof EntityPig) {
                    this.addStat(StatList.distanceByPigStat, j);
                    if (i != 0) {
                        return;
                    }
                }

                if (this.ridingEntity instanceof EntityHorse) {
                    this.addStat(StatList.distanceByHorseStat, j);
                }
            }
        }
    }

    public boolean replaceItemInInventory(int i, ItemStack itemStack) {
        if (i >= 0 && i < this.inventory.mainInventory.length) {
            this.inventory.setInventorySlotContents(i, itemStack);
            return true;
        }

        int j = i - 100;
        if (j >= 0 && j < this.inventory.armorInventory.length) {
            int l = j + 1;
            if (itemStack != null && itemStack.getItem() != null) {
                if (itemStack.getItem() instanceof ItemArmor) {
                    if (EntityLiving.getArmorPosition(itemStack) != l) {
                        return false;
                    }
                } else if (l != 4
                        || itemStack.getItem() != Items.skull
                                && !(itemStack.getItem() instanceof ItemBlock)) {
                    return false;
                }
            }

            this.inventory.setInventorySlotContents(
                    j + this.inventory.mainInventory.length, itemStack);
            return true;
        } else {
            int k = i - 200;
            if (k >= 0 && k < this.theInventoryEnderChest.getSizeInventory()) {
                this.theInventoryEnderChest.setInventorySlotContents(k, itemStack);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean isPushedByWater() {
        WaterPushEvent waterPushEvent = new WaterPushEvent(this);
        RemixClient.eventBus.post(waterPushEvent);
        return !this.capabilities.isFlying && !waterPushEvent.isCancelled();
    }

    public EntityItem dropItem(ItemStack itemStack, boolean randomScatter, boolean recordThrower) {
        int inventoryFlowToken = InventoryPlayer.getLegacyInventoryFlowToken();
        if (itemStack == null) {
            return null;
        }

        if (itemStack.stackSize == 0) {
            return null;
        }

        double dropY = this.posY - 0.3F + this.getEyeHeight();
        EntityItem entityItem = new EntityItem(this.world, this.posX, dropY, this.posZ, itemStack);
        entityItem.setPickupDelay(40);
        if (recordThrower) {
            entityItem.setThrower(this.getName());
        }

        if (randomScatter) {
            float scatterSpeed = this.rand.nextFloat() * 0.5F;
            float scatterAngle = this.rand.nextFloat() * (float) Math.PI * 2.0F;
            entityItem.motionX = -MathHelper.sin(scatterAngle) * scatterSpeed;
            entityItem.motionZ = MathHelper.cos(scatterAngle) * scatterSpeed;
            entityItem.motionY = 0.2F;
        }
        if (!randomScatter || inventoryFlowToken == 0) {
            float forwardSpeed = 0.3F;
            entityItem.motionX =
                    -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI)
                            * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI)
                            * forwardSpeed;
            entityItem.motionZ =
                    MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI)
                            * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI)
                            * forwardSpeed;
            entityItem.motionY =
                    -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI) * forwardSpeed
                            + 0.1F;
            float spreadAngle = this.rand.nextFloat() * (float) Math.PI * 2.0F;
            forwardSpeed = 0.02F * this.rand.nextFloat();
            entityItem.motionX = entityItem.motionX + Math.cos(spreadAngle) * forwardSpeed;
            entityItem.motionY =
                    entityItem.motionY + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
            entityItem.motionZ = entityItem.motionZ + Math.sin(spreadAngle) * forwardSpeed;
        }

        this.joinEntityItemWithWorld(entityItem);
        if (recordThrower) {
            this.triggerAchievement(StatList.dropStat);
        }

        return entityItem;
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.entityUniqueID = getUUID(this.gameProfile);
        NBTTagList nbtTagList = compoundTag.getTagList("Inventory", 10);
        this.inventory.readFromNBT(nbtTagList);
        this.inventory.currentItem = compoundTag.getInteger("SelectedItemSlot");
        this.sleeping = compoundTag.getBoolean("Sleeping");
        this.sleepTimer = compoundTag.getShort("SleepTimer");
        this.experience = compoundTag.getFloat("XpP");
        this.experienceLevel = compoundTag.getInteger("XpLevel");
        this.experienceTotal = compoundTag.getInteger("XpTotal");
        this.xpSeed = compoundTag.getInteger("XpSeed");
        if (this.xpSeed == 0) {
            this.xpSeed = this.rand.nextInt();
        }

        this.setScore(compoundTag.getInteger("Score"));
        if (this.sleeping) {
            this.playerLocation = new BlockPos(this);
            this.wakeUpPlayer(true, true, false);
        }

        if (compoundTag.hasKey("SpawnX", 99)
                && compoundTag.hasKey("SpawnY", 99)
                && compoundTag.hasKey("SpawnZ", 99)) {
            this.spawnChunk =
                    new BlockPos(
                            compoundTag.getInteger("SpawnX"),
                            compoundTag.getInteger("SpawnY"),
                            compoundTag.getInteger("SpawnZ"));
            this.spawnForced = compoundTag.getBoolean("SpawnForced");
        }

        this.foodStats.readNBT(compoundTag);
        this.capabilities.readCapabilitiesFromNBT(compoundTag);
        if (compoundTag.hasKey("EnderItems", 9)) {
            NBTTagList nbtTagListValue = compoundTag.getTagList("EnderItems", 10);
            this.theInventoryEnderChest.loadInventoryFromNBT(nbtTagListValue);
        }
    }

    public boolean interactWith(Entity entity) {
        int i = InventoryPlayer.getLegacyInventoryFlowToken();
        if (this.isSpectator()) {
            if (entity instanceof IInventory) {
                this.displayGUIChest((IInventory) entity);
            }

            return false;
        } else {
            ItemStack currentEquippedItem = this.getCurrentEquippedItem();
            ItemStack itemStack = currentEquippedItem != null ? currentEquippedItem.copy() : null;
            if (!entity.interactFirst(this)) {
                if (currentEquippedItem != null && entity instanceof LivingEntity) {
                    if (this.capabilities.isCreativeMode) {
                        currentEquippedItem = itemStack;
                    }

                    if (currentEquippedItem.interactWithEntity(this, (LivingEntity) entity)) {
                        if (currentEquippedItem.stackSize <= 0
                                && !this.capabilities.isCreativeMode) {
                            this.destroyCurrentEquippedItem();
                        }

                        return true;
                    }
                }

                return false;
            } else {
                if (currentEquippedItem != null
                        && currentEquippedItem == this.getCurrentEquippedItem()) {
                    if (currentEquippedItem.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                        this.destroyCurrentEquippedItem();
                        if (i != 0) {
                            return true;
                        }
                    }

                    if (currentEquippedItem.stackSize < itemStack.stackSize
                            && this.capabilities.isCreativeMode) {
                        currentEquippedItem.stackSize = itemStack.stackSize;
                    }
                }

                return true;
            }
        }
    }

    public void onCriticalHit(Entity entity) {}

    public int getItemInUseCount() {
        return this.itemInUseCount;
    }

    public void setItemInUseCount(int bdtValue) {
        this.itemInUseCount = bdtValue;
    }

    public void openEditCommandBlock(CommandBlockLogic commandBlockLogic) {}

    public String getName() {
        return this.gameProfile.getName();
    }

    public void setInWeb() {
        if (!this.capabilities.isFlying) {
            super.setInWeb();
        }
    }

    public void setReducedDebug(boolean bdmValue) {
        this.hasReducedDebug = bdmValue;
    }

    protected void resetHeight() {
        if (!this.isSpectator()) {
            super.resetHeight();
        }
    }

    public void displayGUIChest(IInventory iInventory) {}

    public int getSleepTimer() {
        return this.sleepTimer;
    }

    public ItemStack getCurrentEquippedItem() {
        return this.inventory.getCurrentItem();
    }

    public ItemStack[] getInventory() {
        return this.inventory.armorInventory;
    }

    public Team getTeam() {
        return this.getWorldScoreboard().getPlayersTeam(this.getName());
    }

    private void setSleepRenderOffsets(Facing facing) {
        this.sleepRenderOffsetX = 0.0F;
        int j = InventoryPlayer.getLegacyInventoryFlowToken();
        this.sleepRenderOffsetZ = 0.0F;
        int i = j;
        switch (facing) {
            case SOUTH:
                this.sleepRenderOffsetZ = -1.8F;
                if (i != 0) {
                    break;
                }
            case NORTH:
                this.sleepRenderOffsetZ = 1.8F;
                if (i != 0) {
                    break;
                }
            case WEST:
                this.sleepRenderOffsetX = 1.8F;
                if (i != 0) {
                    break;
                }
            case EAST:
                this.sleepRenderOffsetX = -1.8F;
        }
    }

    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        boolean legacyZeroDeathMotion = InventoryPlayer.getLegacyInventoryFlowMarker() != 0;
        this.setSize(0.2F, 0.2F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionY = 0.1F;
        if (this.getName().equals("Notch")) {
            this.dropItem(new ItemStack(Items.apple, 1), true, false);
        }

        if (!this.world.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.dropAllItems();
        }

        if (damageSource != null) {
            float deathYaw = (this.attackedAtYaw + this.rotationYaw) * (float) Math.PI / 180.0F;
            this.motionX = -MathHelper.cos(deathYaw) * 0.1F;
            this.motionZ = -MathHelper.sin(deathYaw) * 0.1F;
        }
        if (damageSource == null || legacyZeroDeathMotion) {
            this.motionX = this.motionZ = 0.0;
        }

        this.triggerAchievement(StatList.deathsStat);
        this.resetStat(StatList.timeSinceDeathStat);
    }

    public static String[] getLegacyModuleToggleMarker() {
        return legacyModuleToggleMarker;
    }

    public boolean canAttackPlayer(PlayerEntity playerEntity) {
        Team team = this.getTeam();
        Team teamValue = playerEntity.getTeam();
        return team == null || !team.isSameTeam(teamValue) || team.getAllowFriendlyFire();
    }

    public PlayerEntity(World worldValue, GameProfile gameprofile) {
        super(worldValue);
        this.theInventoryEnderChest = new InventoryEnderChest();
        this.foodStats = new FoodStats();
        this.capabilities = new PlayerCapabilities();
        this.speedOnGround = 0.1F;
        this.speedInAir = 0.02F;
        this.hasReducedDebug = false;
        this.entityUniqueID = getUUID(gameprofile);
        this.gameProfile = gameprofile;
        this.inventoryContainer = new ContainerPlayer(this.inventory, !worldValue.isRemote, this);
        this.openContainer = this.inventoryContainer;
        BlockPos blockPos = worldValue.getSpawnPoint();
        this.setLocationAndAngles(
                blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, 0.0F, 0.0F);
        this.bek = 180.0F;
        this.fireResistance = 20;
    }

    public void attackTargetEntityWithCurrentItem(Entity target) {
        boolean legacyAttackFlow = InventoryPlayer.getLegacyInventoryFlowMarker() != 0;
        if (!target.canAttackWithItem() || target.hitByEntity(this)) {
            return;
        }

        float damage =
                (float)
                        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
                                .getAttributeValue();
        float enchantmentDamage = this.getAttackEnchantmentDamage(target, legacyAttackFlow);
        int knockback = EnchantmentHelper.getKnockbackModifier(this) + (this.isSprinting() ? 1 : 0);
        if (!(damage > 0.0F || enchantmentDamage > 0.0F)) {
            return;
        }

        boolean critical =
                this.fallDistance > 0.0F
                        && !this.onGround
                        && !this.isOnLadder()
                        && !this.isInWater()
                        && !this.isPotionActive(Potion.BLINDNESS)
                        && this.ridingEntity == null
                        && target instanceof LivingEntity;
        if (critical && damage > 0.0F) {
            damage *= 1.5F;
        }
        damage += enchantmentDamage;

        int fireAspect = EnchantmentHelper.getFireAspectModifier(this);
        boolean appliedTemporaryFire =
                target instanceof LivingEntity && fireAspect > 0 && !target.isBurning();
        if (appliedTemporaryFire) {
            target.setFire(1);
        }
        double previousMotionX = target.motionX;
        double previousMotionY = target.motionY;
        double previousMotionZ = target.motionZ;
        if (target.attackEntityFrom(DamageSource.causePlayerDamage(this), damage)) {
            if (knockback > 0) {
                this.applyAttackKnockback(target, knockback);
            }
            if (target instanceof EntityPlayerMP && target.velocityChanged) {
                ((EntityPlayerMP) target)
                        .playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(target));
                target.velocityChanged = false;
                target.motionX = previousMotionX;
                target.motionY = previousMotionY;
                target.motionZ = previousMotionZ;
            }

            if (critical) {
                this.onCriticalHit(target);
            }
            if (enchantmentDamage > 0.0F) {
                this.onEnchantmentCritical(target);
            }
            if (damage >= 18.0F) {
                this.triggerAchievement(AchievementList.overkill);
            }

            this.setLastAttacker(target);
            if (target instanceof LivingEntity) {
                EnchantmentHelper.applyThornEnchantments((LivingEntity) target, this);
            }
            EnchantmentHelper.applyArthropodEnchantments(this, target);
            this.applyHeldItemAttackWear(target);
            if (target instanceof LivingEntity) {
                this.addStat(StatList.damageDealtStat, Math.round(damage * 10.0F));
                if (fireAspect > 0) {
                    target.setFire(fireAspect * 4);
                }
            }
            this.addExhaustion(0.3F);
            if (!legacyAttackFlow) {
                return;
            }
        }
        if (appliedTemporaryFire) {
            target.extinguish();
        }
    }

    private float getAttackEnchantmentDamage(Entity target, boolean legacyAttackFlow) {
        if (target instanceof LivingEntity) {
            float bonus =
                    EnchantmentHelper.getEnchantmentDamageBonus(
                            this.getHeldItem(), ((LivingEntity) target).getCreatureAttribute());
            if (!legacyAttackFlow) {
                return bonus;
            }
        }
        return EnchantmentHelper.getEnchantmentDamageBonus(
                this.getHeldItem(), CreatureAttribute.UNDEFINED);
    }

    private void applyAttackKnockback(Entity target, int knockback) {
        float yaw = this.rotationYaw * (float) Math.PI / 180.0F;
        target.addVelocity(
                -MathHelper.sin(yaw) * knockback * 0.5F,
                0.1,
                MathHelper.cos(yaw) * knockback * 0.5F);
        if (this instanceof LocalPlayer) {
            RemixClient.INSTANCE.getClientServices();
            SprintModule sprint = ClientServices.moduleManager.sprint;
            if (!sprint.isEnabled() || !sprint.keepSprintSetting.asBoolean()) {
                this.motionX *= 0.6;
                this.motionZ *= 0.6;
                this.setSprinting(false);
            }
        }
    }

    private void applyHeldItemAttackWear(Entity target) {
        ItemStack heldStack = this.getCurrentEquippedItem();
        Object damageRecipient = target;
        if (target instanceof EntityDragonPart) {
            IEntityMultiPart parent = ((EntityDragonPart) target).entityDragonObj;
            if (parent instanceof LivingEntity) {
                damageRecipient = parent;
            }
        }
        if (heldStack != null && damageRecipient instanceof LivingEntity) {
            heldStack.hitEntity((LivingEntity) damageRecipient, this);
            if (heldStack.stackSize <= 0) {
                this.destroyCurrentEquippedItem();
            }
        }
    }

    public abstract boolean isSpectator();

    public int getTotalArmorValue() {
        return this.inventory.getTotalArmorValue();
    }

    public void clearItemInUse() {
        this.itemInUse = null;
        this.itemInUseCount = 0;
        if (!this.world.isRemote) {
            this.setEating(false);
        }
    }

    public EntityItem dropPlayerItemWithRandomChoice(ItemStack itemStack, boolean p171_1) {
        return this.dropItem(itemStack, false, false);
    }

    public boolean isEntityInsideOpaqueBlock() {
        return !this.sleeping && super.isEntityInsideOpaqueBlock();
    }

    public void onEnchantmentCritical(Entity entity) {}

    protected String getHurtSound() {
        return "game.player.hurt";
    }

    public void updateRidden() {
        int i = InventoryPlayer.getLegacyInventoryFlowMarker();
        if (!this.world.isRemote && this.isSneaking()) {
            this.mountEntity(null);
            this.setSneaking(false);
            if (i == 0) {
                return;
            }
        }

        double posXValue = this.posX;
        double posYValue = this.posY;
        double posZValue = this.posZ;
        float rotationYawValue = this.rotationYaw;
        float rotationPitchValue = this.rotationPitch;
        super.updateRidden();
        this.prevCameraYaw = this.cameraYaw;
        this.cameraYaw = 0.0F;
        this.addMountedMovementStat(
                this.posX - posXValue, this.posY - posYValue, this.posZ - posZValue);
        if (this.ridingEntity instanceof EntityPig) {
            this.rotationPitch = rotationPitchValue;
            this.rotationYaw = rotationYawValue;
            this.renderYawOffset = ((EntityPig) this.ridingEntity).renderYawOffset;
        }
    }

    public boolean getAlwaysRenderNameTagForRender() {
        return true;
    }

    public ItemStack getItemInUse() {
        return this.itemInUse;
    }

    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0.0F || this.isPlayerSleeping();
    }

    public void setDead() {
        super.setDead();
        this.inventoryContainer.onContainerClosed(this);
        if (this.openContainer != null) {
            this.openContainer.onContainerClosed(this);
        }
    }

    public EntityItem dropOneItem(boolean flag) {
        return this.dropItem(
                this.inventory.decrStackSize(
                        this.inventory.currentItem,
                        flag && this.inventory.getCurrentItem() != null
                                ? this.inventory.getCurrentItem().stackSize
                                : 1),
                false,
                true);
    }

    public void addScore(int p171_0) {
        int j = this.getScore();
        this.dataWatcher.updateObject(18, j + p171_0);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte) 0);
        this.dataWatcher.addObject(17, 0.0F);
        this.dataWatcher.addObject(18, 0);
        this.dataWatcher.addObject(10, (byte) 0);
    }

    public void openEditSign(TileEntitySign tileEntitySign) {}

    public boolean isWearing(EnumPlayerModelParts enumPlayerModelParts) {
        return (this.getDataWatcher().getWatchableObjectByte(10)
                        & enumPlayerModelParts.getPartMask())
                == enumPlayerModelParts.getPartMask();
    }

    public void setCurrentItemOrArmor(int i, ItemStack itemStack) {
        this.inventory.armorInventory[i] = itemStack;
    }

    public boolean isAllowEdit() {
        return this.capabilities.allowEdit;
    }

    public ChatComponent getDisplayName() {
        ChatComponentText chatComponentText =
                new ChatComponentText(
                        ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getName()));
        chatComponentText
                .getChatStyle()
                .setChatClickEvent(
                        new ClickEvent(
                                ClickEventAction.SUGGEST_COMMAND,
                                "/msg " + this.getName() + packedTextFragments[0]));
        chatComponentText.getChatStyle().setChatHoverEvent(this.getHoverEvent());
        chatComponentText.getChatStyle().setInsertion(this.getName());
        return chatComponentText;
    }

    public boolean shouldHeal() {
        return this.getHealth() > 0.0F && this.getHealth() < this.getMaxHealth();
    }

    public void addMovementStat(double d0, double d1, double d2) {
        int i = InventoryPlayer.getLegacyInventoryFlowToken();
        if (this.ridingEntity == null) {
            if (this.isInsideOfMaterial(Material.water)) {
                int j = Math.round(MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
                if (j <= 0) {
                    return;
                }

                this.addStat(StatList.distanceDoveStat, j);
                this.addExhaustion(0.015F * j * 0.01F);
                if (i != 0) {
                    return;
                }
            }

            if (this.isInWater()) {
                int k = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
                if (k <= 0) {
                    return;
                }

                this.addStat(StatList.distanceSwumStat, k);
                this.addExhaustion(0.015F * k * 0.01F);
                if (i != 0) {
                    return;
                }
            }

            if (this.isOnLadder()) {
                if (!(d1 > 0.0)) {
                    return;
                }

                this.addStat(StatList.distanceClimbedStat, (int) Math.round(d1 * 100.0));
                if (i != 0) {
                    return;
                }
            }

            if (this.onGround) {
                int l = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
                if (l <= 0) {
                    return;
                }

                this.addStat(StatList.distanceWalkedStat, l);
                if (this.isSprinting()) {
                    this.addStat(StatList.distanceSprintedStat, l);
                    this.addExhaustion(0.099999994F * l * 0.01F);
                    if (i != 0) {
                        return;
                    }
                }

                if (this.isSneaking()) {
                    this.addStat(StatList.distanceCrouchedStat, l);
                }

                this.addExhaustion(0.01F * l * 0.01F);
                if (i != 0) {
                    return;
                }
            }

            int i1 = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
            if (i1 > 25) {
                this.addStat(StatList.distanceFlownStat, i1);
            }
        }
    }

    public boolean isUsingItem() {
        return this.itemInUse != null;
    }

    protected boolean isPlayer() {
        return true;
    }

    public void stopUsingItem() {
        if (this.itemInUse != null) {
            this.itemInUse.onPlayerStoppedUsing(this.world, this, this.itemInUseCount);
        }

        this.clearItemInUse();
    }

    public boolean isPlayerFullyAsleep() {
        return this.sleeping && this.sleepTimer >= 100;
    }

    public ItemStack getEquipmentInSlot(int i) {
        return i == 0 ? this.inventory.getCurrentItem() : this.inventory.armorInventory[i - 1];
    }

    public boolean canPlayerEdit(BlockPos blockPos, Facing facing, ItemStack itemStack) {
        if (this.capabilities.allowEdit) {
            return true;
        }

        if (itemStack == null) {
            return false;
        }

        BlockPos blockPosValue = blockPos.offset(facing.getOpposite());
        Block block = this.world.getBlockState(blockPosValue).getBlock();
        return itemStack.canPlaceOn(block) || itemStack.canEditBlocks();
    }

    public void addExperienceLevel(int i) {
        this.experienceLevel += i;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experience = 0.0F;
            this.experienceTotal = 0;
        }

        if (i > 0
                && this.experienceLevel % 5 == 0
                && this.lastXPSound < this.ticksExisted - 100.0F) {
            float f = this.experienceLevel > 30 ? 1.0F : this.experienceLevel / 30.0F;
            this.world.playSoundAtEntity(this, "random.levelup", f * 0.75F, 1.0F);
            this.lastXPSound = this.ticksExisted;
        }
    }

    public void displayGUIHorse(EntityHorse entityHorse, IInventory iInventory) {}

    public void setGameType(WorldSettingsGameType worldSettingsGameType) {}

    public void removeExperienceLevel(int p171_0) {
        this.experienceLevel -= p171_0;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experience = 0.0F;
            this.experienceTotal = 0;
        }

        this.xpSeed = this.rand.nextInt();
    }

    protected void joinEntityItemWithWorld(EntityItem entityItem) {
        this.world.spawnEntityInWorld(entityItem);
    }

    public boolean isSpawnForced() {
        return this.spawnForced;
    }

    public FoodStats getFoodStats() {
        return this.foodStats;
    }

    public static UUID getOfflineUUID(String text) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + text).getBytes(Charsets.UTF_8));
    }

    public void onUpdate() {
        boolean exclusivePlayerFlow = InventoryPlayer.getLegacyInventoryFlowToken() != 0;
        RemixClient.INSTANCE.getClientServices();
        FreeCamModule freeCam = ClientServices.moduleManager.freeCam;
        this.noClip = this.isSpectator() || freeCam.isEnabled();
        if (this.isSpectator()) {
            this.onGround = false;
        }

        this.tickHeldItemUse(exclusivePlayerFlow);
        if (this.xpCooldown > 0) {
            this.xpCooldown--;
        }
        this.tickSleeping(exclusivePlayerFlow);

        super.onUpdate();
        if (!this.world.isRemote
                && this.openContainer != null
                && !this.openContainer.canInteractWith(this)) {
            this.closeScreen();
            this.openContainer = this.inventoryContainer;
        }
        if (this.isBurning() && this.capabilities.disableDamage) {
            this.extinguish();
        }

        this.updateChasingPosition();
        if (this.ridingEntity == null) {
            this.startMinecartRidingCoordinate = null;
        }
        if (!this.world.isRemote) {
            this.foodStats.onUpdate(this);
            this.triggerAchievement(StatList.minutesPlayedStat);
            if (this.isEntityAlive()) {
                this.triggerAchievement(StatList.timeSinceDeathStat);
            }
        }

        double clampedX = MathHelper.clamp(this.posX, -2.9999999E7, 2.9999999E7);
        double clampedZ = MathHelper.clamp(this.posZ, -2.9999999E7, 2.9999999E7);
        if (clampedX != this.posX || clampedZ != this.posZ) {
            this.setPosition(clampedX, this.posY, clampedZ);
        }
    }

    private void tickHeldItemUse(boolean exclusivePlayerFlow) {
        if (this.itemInUse == null) {
            return;
        }
        ItemStack heldItem = this.inventory.getCurrentItem();
        if (heldItem == this.itemInUse) {
            if (this.itemInUseCount <= 25 && this.itemInUseCount % 4 == 0) {
                this.updateItemUse(heldItem, 5);
            }
            if (--this.itemInUseCount != 0 || this.world.isRemote) {
                return;
            }

            this.onItemUseFinish();
            if (exclusivePlayerFlow) {
                return;
            }
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }
        this.clearItemInUse();
    }

    private void tickSleeping(boolean exclusivePlayerFlow) {
        if (this.isPlayerSleeping()) {
            this.sleepTimer = Math.min(this.sleepTimer + 1, 100);
            if (this.world.isRemote) {
                return;
            }
            if (!this.isInBed()) {
                this.wakeUpPlayer(true, true, false);
                if (exclusivePlayerFlow) {
                    return;
                }
            }
            if (!this.world.isDaytime()) {
                return;
            }
            this.wakeUpPlayer(false, true, true);
            if (exclusivePlayerFlow) {
                return;
            }
        }

        if (this.sleepTimer > 0 && ++this.sleepTimer >= 110) {
            this.sleepTimer = 0;
        }
    }

    private void updateChasingPosition() {
        this.previousChasingPosX = this.chasingPosX;
        this.previousChasingPosY = this.chasingPosY;
        this.previousChasingPosZ = this.chasingPosZ;
        double deltaX = this.posX - this.chasingPosX;
        double deltaY = this.posY - this.chasingPosY;
        double deltaZ = this.posZ - this.chasingPosZ;
        if (Math.abs(deltaX) > 10.0) {
            this.previousChasingPosX = this.chasingPosX = this.posX;
        }
        if (Math.abs(deltaZ) > 10.0) {
            this.previousChasingPosZ = this.chasingPosZ = this.posZ;
        }
        if (Math.abs(deltaY) > 10.0) {
            this.previousChasingPosY = this.chasingPosY = this.posY;
        }
        this.chasingPosX += deltaX * 0.25;
        this.chasingPosZ += deltaZ * 0.25;
        this.chasingPosY += deltaY * 0.25;
    }

    public int getXPSeed() {
        return this.xpSeed;
    }
}
