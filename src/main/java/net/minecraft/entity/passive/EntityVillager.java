package net.minecraft.entity.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPlay;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerInteract;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityVillager extends EntityAgeable implements IMerchant, INpc {
    Village villageObj;
    private boolean isMating;
    private int wealth;
    private boolean ND;

    private MerchantRecipeList buyingList;
    private static EntityVillagerITradeList[][][][] DEFAULT_TRADE_LIST_MAP;
    private boolean isLookingForHome;
    private int timeUntilReset;
    private String lastBuyingPlayer;
    private PlayerEntity buyingPlayer;
    private int careerLevel;
    private boolean isWillingToMate;

    private InventoryBasic villagerInventory;
    private int careerId;

    private int randomTickDivider;
    private boolean isPlaying;
    private boolean Nx;

    protected void updateEquipmentIfNeeded(EntityItem entityItem) {
        ItemStack itemStack = entityItem.getEntityItem();
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        Item item = itemStack.getItem();
        String textValue = text;
        if (this.canVillagerPickupItem(item)) {
            ItemStack itemStackValue =
                    this.villagerInventory.method_e963069bd8de390f86b7(itemStack);
            if (itemStackValue == null) {
                entityItem.setDead();
                if (textValue == null) {
                    return;
                }
            }

            itemStack.stackSize = itemStackValue.stackSize;
        }
    }

    protected String getDeathSound() {
        return "mob.villager.death";
    }

    public void setIsWillingToMate(boolean bggValue) {
        this.isWillingToMate = bggValue;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        compoundTag.setInteger("Profession", this.jvm171_f_bd9fdf88541948());
        String textValue = text;
        compoundTag.setInteger("Riches", this.wealth);
        compoundTag.setInteger("Career", this.careerId);
        compoundTag.setInteger("CareerLevel", this.careerLevel);
        compoundTag.setBoolean("Willing", this.isWillingToMate);
        if (this.buyingList != null) {
            compoundTag.setTag("Offers", this.buyingList.getRecipiesAsTags());
        }

        NBTTagList nbtTagList = new NBTTagList();
        int i = 0;

        while (i < this.villagerInventory.getSizeInventory()) {
            ItemStack itemStack = this.villagerInventory.getStackInSlot(i);
            if (itemStack != null) {
                nbtTagList.appendTag(itemStack.writeToNBT(new CompoundTag()));
            }

            i++;
            if (textValue != null) {
                break;
            }
        }

        compoundTag.setTag("Inventory", nbtTagList);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }

    public boolean allowLeashing() {
        return false;
    }

    public void setCustomer(PlayerEntity bgCValue) {
        this.buyingPlayer = bgCValue;
    }

    public boolean iW() {
        int i = this.jvm171_f_bd9fdf88541948() == 0 ? 1 : 0;
        return i != 0 ? !this.hasEnoughItems(5) : !this.hasEnoughItems(1);
    }

    public InventoryBasic getVillagerInventory() {
        return this.villagerInventory;
    }

    public void onStruckByLightning(EntityLightningBolt entityLightningBolt) {
        if (!this.world.isRemote) {
            EntityWitch entityWitch = new EntityWitch(this.world);
            entityWitch.setLocationAndAngles(
                    this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entityWitch.onInitialSpawn(
                    this.world.getDifficultyForLocation(new BlockPos(entityWitch)),
                    (IEntityLivingData) null);
            this.world.spawnEntityInWorld(entityWitch);
            this.setDead();
        }
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
    }

    public EntityVillager func_180488_b(EntityAgeable entityAgeable) {
        EntityVillager entityVillager = new EntityVillager(this.world);
        entityVillager.onInitialSpawn(
                this.world.getDifficultyForLocation(new BlockPos(entityVillager)),
                (IEntityLivingData) null);
        return entityVillager;
    }

    public void setProfession(int i) {
        this.dataWatcher.updateObject(16, i);
    }

    public void handleHealthUpdate(byte b0) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (b0 == 12) {
            this.spawnParticles(EnumParticleTypes.HEART);
            if (text == null) {
                return;
            }
        }

        if (b0 == 13) {
            this.spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
            if (text == null) {
                return;
            }
        }

        if (b0 == 14) {
            this.spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
            if (text == null) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    public boolean isFarmItemInInventory() {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        int i = 0;
        String textValue = text;

        while (i < this.villagerInventory.getSizeInventory()) {
            ItemStack itemStack = this.villagerInventory.getStackInSlot(i);
            if (itemStack != null
                    && (itemStack.getItem() == Items.wheat_seeds
                            || itemStack.getItem() == Items.potato
                            || itemStack.getItem() == Items.carrot)) {
                return true;
            }

            i++;
            if (textValue != null) {
                break;
            }
        }

        return false;
    }

    public void setPlaying(boolean bcuValue) {
        this.isPlaying = bcuValue;
    }

    protected String getLivingSound() {
        return this.isTrading() ? "mob.villager.haggle" : "mob.villager.idle";
    }

    protected String getHurtSound() {
        return "mob.villager.hit";
    }

    public void setRevengeTarget(LivingEntity livingEntity) {
        super.setRevengeTarget(livingEntity);
        if (this.villageObj != null && livingEntity != null) {
            this.villageObj.addOrRenewAgressor(livingEntity);
            if (livingEntity instanceof PlayerEntity) {
                byte b0 = -1;
                if (this.isChild()) {
                    b0 = -3;
                }

                this.villageObj.setReputationForPlayer(livingEntity.getName(), b0);
                if (this.isEntityAlive()) {
                    this.world.setEntityState(this, (byte) 13);
                }
            }
        }
    }

    public void setMating(boolean bbXValue) {
        this.isMating = bbXValue;
    }

    private boolean canVillagerPickupItem(Item item) {
        return item == Items.bread
                || item == Items.potato
                || item == Items.carrot
                || item == Items.wheat
                || item == Items.wheat_seeds;
    }

    public boolean isMating() {
        return this.isMating;
    }

    private void setAdditionalAItasks() {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (!this.ND) {
            this.ND = true;
            if (this.isChild()) {
                this.tasks.addTask(8, new EntityAIPlay(this, 0.32));
                if (text == null) {
                    return;
                }
            }

            if (this.jvm171_f_bd9fdf88541948() == 0) {
                this.tasks.addTask(6, new EntityAIHarvestFarmland(this, 0.6));
            }
        }
    }

    public EntityVillager(World worldValue, int i) {
        this(worldValue, i, EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62());
    }

    private EntityVillager(World worldValue, int i, String initialFlowMarker) {
        super(worldValue);

        String text = initialFlowMarker;

        this.villagerInventory = new InventoryBasic("Items", false, 8);
        this.setProfession(i);
        this.setSize(0.6F, 1.8F);
        String textValue = text;
        ((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(
                1, new EntityAIAvoidEntity(this, new EntityPredicate(this), 8.0F, 0.6, 0.6));
        this.tasks.addTask(1, new EntityAITradePlayer(this));
        this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6));
        this.tasks.addTask(6, new EntityAIVillagerMate(this));
        this.tasks.addTask(7, new EntityAIFollowGolem(this));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, PlayerEntity.class, 3.0F, 1.0F));
        this.tasks.addTask(9, new EntityAIVillagerInteract(this));
        this.tasks.addTask(9, new EntityAIWander(this, 0.6));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.setCanPickUpLoot(true);
        if (textValue != null) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }
    }

    public IEntityLivingData onInitialSpawn(
            DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
        iEntityLivingData = super.onInitialSpawn(difficultyInstance, iEntityLivingData);
        this.setProfession(this.world.rand.nextInt(5));
        this.setAdditionalAItasks();
        return iEntityLivingData;
    }

    public void setLookingForHome() {
        this.isLookingForHome = true;
    }

    protected boolean canDespawn() {
        return false;
    }

    private void spawnParticles(EnumParticleTypes enumParticleTypes) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        int i = 0;
        String textValue = text;

        while (i < 5) {
            double d0 = this.rand.nextGaussian() * 0.02;
            double d1 = this.rand.nextGaussian() * 0.02;
            double d2 = this.rand.nextGaussian() * 0.02;
            this.world.spawnParticle(
                    enumParticleTypes,
                    this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                    this.posY + 1.0 + this.rand.nextFloat() * this.height,
                    this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                    d0,
                    d1,
                    d2,
                    new int[0]);
            i++;
            if (textValue != null) {
                break;
            }
        }
    }

    protected void onGrowingAdult() {
        if (this.jvm171_f_bd9fdf88541948() == 0) {
            this.tasks.addTask(8, new EntityAIHarvestFarmland(this, 0.6));
        }

        super.onGrowingAdult();
    }

    public boolean replaceItemInInventory(int i, ItemStack itemStack) {
        if (super.replaceItemInInventory(i, itemStack)) {
            return true;
        } else {
            int j = i - 300;
            if (j >= 0 && j < this.villagerInventory.getSizeInventory()) {
                this.villagerInventory.setInventorySlotContents(j, itemStack);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean interact(PlayerEntity playerEntity) {
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        int i = itemStack != null && itemStack.getItem() == Items.spawn_egg ? 1 : 0;
        if (i == 0 && this.isEntityAlive() && !this.isTrading() && !this.isChild()) {
            if (!this.world.isRemote && (this.buyingList == null || this.buyingList.size() > 0)) {
                this.setCustomer(playerEntity);
                playerEntity.displayVillagerTradeGui(this);
            }

            playerEntity.triggerAchievement(StatList.timesTalkedToVillagerStat);
            return true;
        } else {
            return super.interact(playerEntity);
        }
    }

    private boolean hasEnoughItems(int i) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        int j = this.jvm171_f_bd9fdf88541948() == 0 ? 1 : 0;

        for (int k = 0; k < this.villagerInventory.getSizeInventory(); k++) {
            ItemStack itemStack = this.villagerInventory.getStackInSlot(k);
            if (itemStack != null) {
                if (itemStack.getItem() == Items.bread && itemStack.stackSize >= 3 * i
                        || itemStack.getItem() == Items.potato && itemStack.stackSize >= 12 * i
                        || itemStack.getItem() == Items.carrot && itemStack.stackSize >= 12 * i) {
                    return true;
                }

                if (j != 0 && itemStack.getItem() == Items.wheat && itemStack.stackSize >= 9 * i) {
                    return true;
                }
            }
            break;
        }

        return false;
    }

    private void populateBuyingList() {
        String text;
        EntityVillagerITradeList[][][] entityVillagerITradeListArrayArrayArray;
        {
            String textValue = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
            entityVillagerITradeListArrayArrayArray =
                    net.minecraft.entity.passive.EntityVillager.DEFAULT_TRADE_LIST_MAP[
                            this.jvm171_f_bd9fdf88541948()];
            text = textValue;
            if (this.careerId != 0 && this.careerLevel != 0) {
                this.careerLevel++;
                if (!(text == null)) {

                    this.careerId =
                            this.rand.nextInt(entityVillagerITradeListArrayArrayArray.length) + 1;
                    this.careerLevel = 1;
                }
            } else {

                this.careerId =
                        this.rand.nextInt(entityVillagerITradeListArrayArrayArray.length) + 1;
                this.careerLevel = 1;
            }
        }

        if (this.buyingList == null) {
            this.buyingList = new MerchantRecipeList();
        }

        int i = this.careerId - 1;
        int j = this.careerLevel - 1;
        EntityVillagerITradeList[][] entityVillagerITradeListArrayArray =
                entityVillagerITradeListArrayArrayArray[i];
        if (j < entityVillagerITradeListArrayArray.length) {
            EntityVillagerITradeList[] entityVillagerITradeListArray =
                    entityVillagerITradeListArrayArray[j];
            EntityVillagerITradeList[] entityVillagerITradeListArrayValue =
                    entityVillagerITradeListArray;
            int k = entityVillagerITradeListArray.length;
            int l = 0;

            while (l < k) {
                EntityVillagerITradeList entityVillagerITradeList =
                        entityVillagerITradeListArrayValue[l];
                entityVillagerITradeList.modifyMerchantRecipeList(this.buyingList, this.rand);
                l++;
                if (text != null) {
                    break;
                }
            }
        }
    }

    public EntityVillager(World worldValue) {
        this(worldValue, 0);
    }

    public ChatComponent getDisplayName() {
        String text = this.getCustomNameTag();
        if (text != null && text.length() > 0) {
            return new ChatComponentText(text);
        }

        if (this.buyingList == null) {
            this.populateBuyingList();
        }

        String textValue = null;
        switch (this.jvm171_f_bd9fdf88541948()) {
            case 0:
                if (this.careerId == 1) {
                    textValue = "farmer";
                } else if (this.careerId == 2) {
                    textValue = "fisherman";
                } else if (this.careerId == 3) {
                    textValue = "shepherd";
                } else if (this.careerId == 4) {
                    textValue = "fletcher";
                }
                break;
            case 1:
                textValue = "librarian";
                break;
            case 2:
                textValue = "cleric";
                break;
            case 3:
                if (this.careerId == 1) {
                    textValue = "armor";
                } else if (this.careerId == 2) {
                    textValue = "weapon";
                } else if (this.careerId == 3) {
                    textValue = "tool";
                }
                break;
            case 4:
                if (this.careerId == 1) {
                    textValue = "butcher";
                } else if (this.careerId == 2) {
                    textValue = "leather";
                }
        }

        if (textValue != null) {
            ChatComponentTranslation chatComponentTranslation =
                    new ChatComponentTranslation("entity.Villager." + textValue, new Object[0]);
            chatComponentTranslation.getChatStyle().setChatHoverEvent(this.getHoverEvent());
            chatComponentTranslation.getChatStyle().setInsertion(this.getUniqueId().toString());
            return chatComponentTranslation;
        } else {
            return super.getDisplayName();
        }
    }

    public PlayerEntity getCustomer() {
        return this.buyingPlayer;
    }

    public void onDeath(DamageSource damageSource) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        label23:
        if (this.villageObj != null) {
            Entity entity = damageSource.getEntity();
            if (entity != null) {
                if (entity instanceof PlayerEntity) {
                    this.villageObj.setReputationForPlayer(entity.getName(), -2);
                    if (text == null) {
                        break label23;
                    }
                }

                if (!(entity instanceof IMob)) {
                    break label23;
                }

                this.villageObj.endMatingSeason();
                if (text == null) {
                    break label23;
                }
            }

            PlayerEntity playerEntity = this.world.getClosestPlayerToEntity(this, 16.0);
            if (playerEntity != null) {
                this.villageObj.endMatingSeason();
            }
        }

        super.onDeath(damageSource);
    }

    public void verifySellingItem(ItemStack itemStack) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (!this.world.isRemote && this.livingSoundTime > -this.getTalkInterval() + 20) {
            this.livingSoundTime = -this.getTalkInterval();
            if (itemStack != null) {
                this.playSound("mob.villager.yes", this.getSoundVolume(), this.Jr());
                if (text == null) {
                    return;
                }
            }

            this.playSound("mob.villager.no", this.getSoundVolume(), this.Jr());
        }
    }

    public void setRecipes(MerchantRecipeList merchantRecipeList) {}

    public MerchantRecipeList getRecipes(PlayerEntity playerEntity) {
        if (this.buyingList == null) {
            this.populateBuyingList();
        }

        return this.buyingList;
    }

    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        return this.func_180488_b(entityAgeable);
    }

    public float getEyeHeight() {
        float f = 1.62F;
        if (this.isChild()) {
            f = (float) (f - 0.81);
        }

        return f;
    }

    public boolean isTrading() {
        return this.buyingPlayer != null;
    }

    public int jvm171_f_bd9fdf88541948() {
        return Math.max(this.dataWatcher.getWatchableObjectInt(16) % 5, 0);
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        this.setProfession(compoundTag.getInteger("Profession"));
        this.wealth = compoundTag.getInteger("Riches");
        this.careerId = compoundTag.getInteger("Career");
        this.careerLevel = compoundTag.getInteger("CareerLevel");
        String textValue = text;
        this.isWillingToMate = compoundTag.getBoolean("Willing");
        if (compoundTag.hasKey("Offers", 10)) {
            CompoundTag compoundTagValue = compoundTag.getCompoundTag("Offers");
            this.buyingList = new MerchantRecipeList(compoundTagValue);
        }

        NBTTagList nbtTagList = compoundTag.getTagList("Inventory", 10);
        int i = 0;

        while (i < nbtTagList.tagCount()) {
            ItemStack itemStack = ItemStack.loadItemStackFromNBT(nbtTagList.getCompoundTagAt(i));
            if (itemStack != null) {
                this.villagerInventory.method_e963069bd8de390f86b7(itemStack);
            }

            i++;
            if (textValue != null) {
                break;
            }
        }

        this.setCanPickUpLoot(true);
        this.setAdditionalAItasks();
    }

    public void useRecipe(MerchantRecipe merchantRecipe) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        merchantRecipe.incrementToolUses();
        this.livingSoundTime = -this.getTalkInterval();
        String textValue = text;
        this.playSound("mob.villager.yes", this.getSoundVolume(), this.Jr());
        int i = 3 + this.rand.nextInt(4);
        if (merchantRecipe.getToolUses() == 1 || this.rand.nextInt(5) == 0) {
            {
                this.timeUntilReset = 40;
                this.Nx = true;
                this.isWillingToMate = true;
                if (this.buyingPlayer != null) {
                    this.lastBuyingPlayer = this.buyingPlayer.getName();
                    if (!(textValue == null)) {

                        this.lastBuyingPlayer = null;
                    }
                } else {

                    this.lastBuyingPlayer = null;
                }
            }

            i += 5;
        }

        if (merchantRecipe.getItemToBuy().getItem() == Items.emerald) {
            this.wealth = this.wealth + merchantRecipe.getItemToBuy().stackSize;
        }

        if (merchantRecipe.getRewardsExp()) {
            this.world.spawnEntityInWorld(
                    new EntityXPOrb(this.world, this.posX, this.posY + 0.5, this.posZ, i));
        }
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    static {
        net.minecraft.entity.passive.EntityVillager.DEFAULT_TRADE_LIST_MAP =
                new EntityVillagerITradeList[][][][] {
                    {
                        {
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.wheat, new EntityVillagerPriceInfo(18, 22)),
                                new EntityVillagerEmeraldForItems(
                                        Items.potato, new EntityVillagerPriceInfo(15, 19)),
                                new EntityVillagerEmeraldForItems(
                                        Items.carrot, new EntityVillagerPriceInfo(15, 19)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.bread, new EntityVillagerPriceInfo(-4, -2))
                            },
                            {
                                new EntityVillagerEmeraldForItems(
                                        Item.getItemFromBlock(Blocks.pumpkin),
                                        new EntityVillagerPriceInfo(8, 13)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.pumpkin_pie, new EntityVillagerPriceInfo(-3, -2))
                            },
                            {
                                new EntityVillagerEmeraldForItems(
                                        Item.getItemFromBlock(Blocks.melon_block),
                                        new EntityVillagerPriceInfo(7, 12)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.apple, new EntityVillagerPriceInfo(-5, -7))
                            },
                            {
                                new EntityVillagerListItemForEmeralds(
                                        Items.cookie, new EntityVillagerPriceInfo(-6, -10)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.cake, new EntityVillagerPriceInfo(1, 1))
                            }
                        },
                        {
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.string, new EntityVillagerPriceInfo(15, 20)),
                                new EntityVillagerEmeraldForItems(
                                        Items.coal, new EntityVillagerPriceInfo(16, 24)),
                                new EntityVillagerItemAndEmeraldToItem(
                                        Items.fish,
                                        new EntityVillagerPriceInfo(6, 6),
                                        Items.cooked_fish,
                                        new EntityVillagerPriceInfo(6, 6))
                            },
                            {
                                new EntityVillagerListEnchantedItemForEmeralds(
                                        Items.fishing_rod, new EntityVillagerPriceInfo(7, 8))
                            }
                        },
                        {
                            {
                                new EntityVillagerEmeraldForItems(
                                        Item.getItemFromBlock(Blocks.wool),
                                        new EntityVillagerPriceInfo(16, 22)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.shears, new EntityVillagerPriceInfo(3, 4))
                            },
                            {
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 1),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 2),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 3),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 5),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 6),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 7),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 8),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 9),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 10),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 12),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 13),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 14),
                                        new EntityVillagerPriceInfo(1, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 15),
                                        new EntityVillagerPriceInfo(1, 2))
                            }
                        },
                        {
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.string, new EntityVillagerPriceInfo(15, 20)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.arrow, new EntityVillagerPriceInfo(-12, -8))
                            },
                            {
                                new EntityVillagerListItemForEmeralds(
                                        Items.bow, new EntityVillagerPriceInfo(2, 3)),
                                new EntityVillagerItemAndEmeraldToItem(
                                        Item.getItemFromBlock(Blocks.gravel),
                                        new EntityVillagerPriceInfo(10, 10),
                                        Items.flint,
                                        new EntityVillagerPriceInfo(6, 10))
                            }
                        }
                    },
                    {
                        {
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.paper, new EntityVillagerPriceInfo(24, 36)),
                                new EntityVillagerListEnchantedBookForEmeralds()
                            },
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.book, new EntityVillagerPriceInfo(8, 10)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.compass, new EntityVillagerPriceInfo(10, 12)),
                                new EntityVillagerListItemForEmeralds(
                                        Item.getItemFromBlock(Blocks.bookshelf),
                                        new EntityVillagerPriceInfo(3, 4))
                            },
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.written_book, new EntityVillagerPriceInfo(2, 2)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.clock, new EntityVillagerPriceInfo(10, 12)),
                                new EntityVillagerListItemForEmeralds(
                                        Item.getItemFromBlock(Blocks.glass),
                                        new EntityVillagerPriceInfo(-5, -3))
                            },
                            {new EntityVillagerListEnchantedBookForEmeralds()},
                            {new EntityVillagerListEnchantedBookForEmeralds()},
                            {
                                new EntityVillagerListItemForEmeralds(
                                        Items.name_tag, new EntityVillagerPriceInfo(20, 22))
                            }
                        }
                    },
                    {
                        {
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.rotten_flesh, new EntityVillagerPriceInfo(36, 40)),
                                new EntityVillagerEmeraldForItems(
                                        Items.gold_ingot, new EntityVillagerPriceInfo(8, 10))
                            },
                            {
                                new EntityVillagerListItemForEmeralds(
                                        Items.redstone, new EntityVillagerPriceInfo(-4, -1)),
                                new EntityVillagerListItemForEmeralds(
                                        new ItemStack(
                                                Items.dye,
                                                1,
                                                EnumDyeColor.BLUE.getDyeColorDamage()),
                                        new EntityVillagerPriceInfo(-2, -1))
                            },
                            {
                                new EntityVillagerListItemForEmeralds(
                                        Items.ender_eye, new EntityVillagerPriceInfo(7, 11)),
                                new EntityVillagerListItemForEmeralds(
                                        Item.getItemFromBlock(Blocks.glowstone),
                                        new EntityVillagerPriceInfo(-3, -1))
                            },
                            {
                                new EntityVillagerListItemForEmeralds(
                                        Items.experience_bottle, new EntityVillagerPriceInfo(3, 11))
                            }
                        }
                    },
                    {
                        {
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.coal, new EntityVillagerPriceInfo(16, 24)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.iron_helmet, new EntityVillagerPriceInfo(4, 6))
                            },
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.iron_ingot, new EntityVillagerPriceInfo(7, 9)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.iron_chestplate, new EntityVillagerPriceInfo(10, 14))
                            },
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.diamond, new EntityVillagerPriceInfo(3, 4)),
                                new EntityVillagerListEnchantedItemForEmeralds(
                                        Items.diamond_chestplate,
                                        new EntityVillagerPriceInfo(16, 19))
                            },
                            {
                                new EntityVillagerListItemForEmeralds(
                                        Items.chainmail_boots, new EntityVillagerPriceInfo(5, 7)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.chainmail_leggings,
                                        new EntityVillagerPriceInfo(9, 11)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.chainmail_helmet, new EntityVillagerPriceInfo(5, 7)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.chainmail_chestplate,
                                        new EntityVillagerPriceInfo(11, 15))
                            }
                        },
                        {
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.coal, new EntityVillagerPriceInfo(16, 24)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.iron_axe, new EntityVillagerPriceInfo(6, 8))
                            },
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.iron_ingot, new EntityVillagerPriceInfo(7, 9)),
                                new EntityVillagerListEnchantedItemForEmeralds(
                                        Items.iron_sword, new EntityVillagerPriceInfo(9, 10))
                            },
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.diamond, new EntityVillagerPriceInfo(3, 4)),
                                new EntityVillagerListEnchantedItemForEmeralds(
                                        Items.diamond_sword, new EntityVillagerPriceInfo(12, 15)),
                                new EntityVillagerListEnchantedItemForEmeralds(
                                        Items.diamond_axe, new EntityVillagerPriceInfo(9, 12))
                            }
                        },
                        {
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.coal, new EntityVillagerPriceInfo(16, 24)),
                                new EntityVillagerListEnchantedItemForEmeralds(
                                        Items.iron_shovel, new EntityVillagerPriceInfo(5, 7))
                            },
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.iron_ingot, new EntityVillagerPriceInfo(7, 9)),
                                new EntityVillagerListEnchantedItemForEmeralds(
                                        Items.iron_pickaxe, new EntityVillagerPriceInfo(9, 11))
                            },
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.diamond, new EntityVillagerPriceInfo(3, 4)),
                                new EntityVillagerListEnchantedItemForEmeralds(
                                        Items.diamond_pickaxe, new EntityVillagerPriceInfo(12, 15))
                            }
                        }
                    },
                    {
                        {
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.porkchop, new EntityVillagerPriceInfo(14, 18)),
                                new EntityVillagerEmeraldForItems(
                                        Items.chicken, new EntityVillagerPriceInfo(14, 18))
                            },
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.coal, new EntityVillagerPriceInfo(16, 24)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.cooked_porkchop, new EntityVillagerPriceInfo(-7, -5)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.cooked_chicken, new EntityVillagerPriceInfo(-8, -6))
                            }
                        },
                        {
                            {
                                new EntityVillagerEmeraldForItems(
                                        Items.leather, new EntityVillagerPriceInfo(9, 12)),
                                new EntityVillagerListItemForEmeralds(
                                        Items.leather_leggings, new EntityVillagerPriceInfo(2, 4))
                            },
                            {
                                new EntityVillagerListEnchantedItemForEmeralds(
                                        Items.leather_chestplate,
                                        new EntityVillagerPriceInfo(7, 12))
                            },
                            {
                                new EntityVillagerListItemForEmeralds(
                                        Items.saddle, new EntityVillagerPriceInfo(8, 10))
                            }
                        }
                    }
                };
    }

    public boolean canAbondonItems() {
        return this.hasEnoughItems(2);
    }

    public boolean getIsWillingToMate(boolean flag) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (!this.isWillingToMate && flag && this.CP()) {
            byte b0 = 0;
            int i = 0;

            while (i < this.villagerInventory.getSizeInventory()) {
                ItemStack itemStack = this.villagerInventory.getStackInSlot(i);
                if (itemStack != null) {
                    if (itemStack.getItem() == Items.bread && itemStack.stackSize >= 3) {
                        b0 = 1;
                        this.villagerInventory.decrStackSize(i, 3);
                        if (!(text == null)) {

                            if ((itemStack.getItem() == Items.potato
                                            || itemStack.getItem() == Items.carrot)
                                    && itemStack.stackSize >= 12) {
                                b0 = 1;
                                this.villagerInventory.decrStackSize(i, 12);
                            }
                        }
                    } else {

                        if ((itemStack.getItem() == Items.potato
                                        || itemStack.getItem() == Items.carrot)
                                && itemStack.stackSize >= 12) {
                            b0 = 1;
                            this.villagerInventory.decrStackSize(i, 12);
                        }
                    }
                }

                if (b0 != 0) {
                    this.world.setEntityState(this, (byte) 18);
                    this.isWillingToMate = true;
                    if (text == null) {
                        break;
                    }
                }

                i++;
                if (text != null) {
                    break;
                }
            }
        }

        return this.isWillingToMate;
    }

    public boolean CP() {
        return this.hasEnoughItems(1);
    }

    protected void updateAITasks() {
        String text;
        text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (--this.randomTickDivider <= 0) {
            BlockPos blockPos = new BlockPos(this);
            this.world.getVillageCollection().addToVillagerPositionList(blockPos);
            this.randomTickDivider = 70 + this.rand.nextInt(50);
            this.villageObj = this.world.getVillageCollection().getNearestVillage(blockPos, 32);
            if (this.villageObj == null) {
                this.detachHome();
                if (!(text == null)) {

                    BlockPos blockPosValue = this.villageObj.getCenter();
                    this.setHomePosAndDistance(
                            blockPosValue, (int) (this.villageObj.getVillageRadius() * 1.0F));
                    if (this.isLookingForHome) {
                        this.isLookingForHome = false;
                        this.villageObj.setDefaultPlayerReputation(5);
                    }
                }
            } else {

                BlockPos blockPosValue = this.villageObj.getCenter();
                this.setHomePosAndDistance(
                        blockPosValue, (int) (this.villageObj.getVillageRadius() * 1.0F));
                if (this.isLookingForHome) {
                    this.isLookingForHome = false;
                    this.villageObj.setDefaultPlayerReputation(5);
                }
            }
        }

        if (!this.isTrading() && this.timeUntilReset > 0) {
            this.timeUntilReset--;
            if (this.timeUntilReset <= 0) {
                if (this.Nx) {
                    for (java.util.Iterator<?> __recoveryIterator171_79838 =
                                    (this.buyingList).iterator();
                            (__recoveryIterator171_79838).hasNext(); ) {
                        MerchantRecipe merchantRecipe =
                                (MerchantRecipe) (__recoveryIterator171_79838).next();
                        if (merchantRecipe.isRecipeDisabled()) {
                            merchantRecipe.increaseMaxTradeUses(
                                    this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                        }

                        if (text != null) {
                            break;
                        }
                    }

                    this.populateBuyingList();
                    this.Nx = false;
                    if (this.villageObj != null && this.lastBuyingPlayer != null) {
                        this.world.setEntityState(this, (byte) 14);
                        this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
                    }
                }

                this.addPotionEffect(new PotionEffect(Potion.REGENERATION.id, 200, 0));
            }
        }

        super.updateAITasks();
    }

    // Helper used only by this class.
    public static class EntityPredicate implements com.google.common.base.Predicate {
        net.minecraft.entity.passive.EntityVillager bkR;

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity instanceof net.minecraft.entity.monster.EntityZombie;
        }

        public EntityPredicate(net.minecraft.entity.passive.EntityVillager bkRValue) {
            this.bkR = bkRValue;
        }
    }
}
