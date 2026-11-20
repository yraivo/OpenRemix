package net.minecraft.entity.passive;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityHorse extends AnimalEntity implements IInvBasic {
    private int eatingHaystackCounter;
    private String texturePrefix;
    private float bfI;
    private boolean bfJ;
    private static String[] HORSE_ARMOR_TEXTURES_ABBR;
    private float bfL;
    private float prevRearingAmount;
    protected boolean bfN;
    private static String[] horseMarkingTextures;
    private static IAttribute horseJumpStrength;
    private static Predicate horseBreedingSelector;
    private float bfQ;
    private static String[] horseTextures;
    private int openMouthCounter;
    private boolean field_175508_bO;
    private float rearingAmount;
    private String[] horseTexturesArray = new String[3];
    public int bdU;
    private AnimalChest horseChest;
    private static int[] armorValues;
    private float bfX;
    private static String[] horseArmorTextures;

    private boolean bdR;
    private static String[] HORSE_MARKING_TEXTURES_ABBR;
    private int jumpRearingCounter;
    protected int temper;

    protected float jumpPower;
    private static String[] HORSE_TEXTURES_ABBR;
    private static String[] packedTextFragments;
    private int gallopTime;
    public int field_110278_bp;

    private int getHorseArmorIndex(ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }

        Item item = itemStack.getItem();
        return item == Items.iron_horse_armor
                ? 1
                : (item == Items.golden_horse_armor
                        ? 2
                        : (item == Items.diamond_horse_armor ? 3 : 0));
    }

    public void setRearing(boolean p171_0) {
        if (p171_0) {
            this.setEatingHaystack(false);
        }

        this.setHorseWatchableBoolean(64, p171_0);
    }

    protected EntityHorse getClosestHorse(Entity entity, double d0) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        double MAX_VALUE = Double.MAX_VALUE;
        String textValue = text;
        Entity entityValue = null;

        for (java.util.Iterator<?> __recoveryIterator171_3432 =
                        (this.world.getEntitiesInAABBexcluding(
                                        entity,
                                        entity.getEntityBoundingBox().addCoord(d0, d0, d0),
                                        net.minecraft.entity.passive.EntityHorse
                                                .horseBreedingSelector))
                                .iterator();
                (__recoveryIterator171_3432).hasNext(); ) {
            Entity entityValue2 = (Entity) (__recoveryIterator171_3432).next();
            double distanceSq = entityValue2.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (distanceSq < MAX_VALUE) {
                entityValue = entityValue2;
                MAX_VALUE = distanceSq;
            }

            if (textValue != null) {
                break;
            }
        }

        return (EntityHorse) entityValue;
    }

    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        if (!this.world.isRemote) {
            this.dropChestItems();
        }
    }

    public boolean jc() {
        return this.bdR;
    }

    protected void playStepSound(BlockPos blockPos, Block block) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        BlockSoundType ajf = block.stepSound;
        String textValue = text;
        if (this.world.getBlockState(blockPos.offsetUp()).getBlock() == Blocks.snow_layer) {
            ajf = Blocks.snow_layer.stepSound;
        }

        if (!block.getMaterial().isLiquid()) {
            int horseType = this.getHorseType();
            if (this.riddenByEntity != null && horseType != 1 && horseType != 2) {
                this.gallopTime++;
                if (this.gallopTime > 5 && this.gallopTime % 3 == 0) {
                    this.playSound("mob.horse.gallop", ajf.getVolume() * 0.15F, ajf.getFrequency());
                    if (horseType != 0 || this.rand.nextInt(10) != 0) {
                        return;
                    }

                    this.playSound("mob.horse.breathe", ajf.getVolume() * 0.6F, ajf.getFrequency());
                    if (textValue == null) {
                        return;
                    }
                }

                if (this.gallopTime > 5) {
                    return;
                }

                this.playSound("mob.horse.wood", ajf.getVolume() * 0.15F, ajf.getFrequency());
                if (textValue == null) {
                    return;
                }
            }

            if (ajf == Block.soundTypeWood) {
                this.playSound("mob.horse.wood", ajf.getVolume() * 0.15F, ajf.getFrequency());
                if (textValue == null) {
                    return;
                }
            }

            this.playSound("mob.horse.soft", ajf.getVolume() * 0.15F, ajf.getFrequency());
        }
    }

    public int getMaxTemper() {
        return 100;
    }

    public boolean canWearArmor() {
        return this.getHorseType() == 0;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap()
                .registerAttribute(net.minecraft.entity.passive.EntityHorse.horseJumpStrength);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(53.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.225F);
    }

    protected String getAngrySoundName() {
        this.openHorseMouth();
        this.makeHorseRear();
        int horseType = this.getHorseType();
        return horseType != 3 && horseType != 4
                ? (horseType != 1 && horseType != 2 ? "mob.horse.angry" : "mob.horse.donkey.angry")
                : null;
    }

    public boolean isChested() {
        return this.getHorseWatchableBoolean(8);
    }

    public void onLivingUpdate() {
        if (this.rand.nextInt(200) == 0) {
            this.func_110210_cH();
        }

        super.onLivingUpdate();
        if (!this.world.isRemote) {
            if (this.rand.nextInt(900) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }

            if (!this.isEatingHaystack()
                    && this.riddenByEntity == null
                    && this.rand.nextInt(300) == 0
                    && this.world
                                    .getBlockState(
                                            new BlockPos(
                                                    MathHelper.floor(this.posX),
                                                    MathHelper.floor(this.posY) - 1,
                                                    MathHelper.floor(this.posZ)))
                                    .getBlock()
                            == Blocks.grass) {
                this.setEatingHaystack(true);
            }

            if (this.isEatingHaystack() && ++this.eatingHaystackCounter > 50) {
                this.eatingHaystackCounter = 0;
                this.setEatingHaystack(false);
            }

            if (this.isBreeding() && !this.isAdultHorse() && !this.isEatingHaystack()) {
                EntityHorse closestHorse = this.getClosestHorse(this, 16.0);
                if (closestHorse != null && this.getDistanceSqToEntity(closestHorse) > 4.0) {
                    this.navigator.getPathToEntityLiving(closestHorse);
                }
            }
        }
    }

    public boolean attackEntityFrom(DamageSource damageSource, float p171_1) {
        net.minecraft.entity.Entity entity = damageSource.getEntity();
        return this.riddenByEntity != null && this.riddenByEntity.equals(entity)
                ? false
                : super.attackEntityFrom(damageSource, p171_1);
    }

    public boolean canBePushed() {
        return this.riddenByEntity == null;
    }

    public int getHorseType() {
        return this.dataWatcher.getWatchableObjectByte(19);
    }

    public void fall(float f, float f1) {
        if (f > 1.0F) {
            this.playSound("mob.horse.land", 0.4F, 1.0F);
        }

        int i = MathHelper.ceil((f * 0.5F - 3.0F) * f1);
        if (i > 0) {
            this.attackEntityFrom(DamageSource.fall, i);
            if (this.riddenByEntity != null) {
                this.riddenByEntity.attackEntityFrom(DamageSource.fall, i);
            }

            Block block =
                    this.world
                            .getBlockState(
                                    new BlockPos(
                                            this.posX,
                                            this.posY - 0.2 - this.prevRotationYaw,
                                            this.posZ))
                            .getBlock();
            if (block.getMaterial() != Material.AIR && !this.isSlient()) {
                BlockSoundType ajf = block.stepSound;
                this.world.playSoundAtEntity(
                        this,
                        ajf.getStepSound(),
                        ajf.getVolume() * 0.5F,
                        ajf.getFrequency() * 0.75F);
            }
        }
    }

    public boolean setTamedBy(PlayerEntity playerEntity) {
        this.setOwnerId(playerEntity.getUniqueId().toString());
        this.setHorseTamed(true);
        return true;
    }

    public void setBreeding(boolean flag) {
        this.setHorseWatchableBoolean(16, flag);
    }

    public boolean func_110239_cn() {
        return this.getHorseType() == 0 || this.getHorseArmorIndexSynced() > 0;
    }

    public float getEyeHeight() {
        return this.height;
    }

    public boolean isOnLadder() {
        return false;
    }

    protected String getDeathSound() {
        this.openHorseMouth();
        int horseType = this.getHorseType();
        return horseType == 3
                ? "mob.horse.zombie.death"
                : (horseType == 4
                        ? "mob.horse.skeleton.death"
                        : (horseType != 1 && horseType != 2
                                ? "mob.horse.death"
                                : "mob.horse.donkey.death"));
    }

    private int getChestSize() {
        int horseType = this.getHorseType();
        return !this.isChested() || horseType != 1 && horseType != 2 ? 2 : 17;
    }

    // region String decoding support

    // endregion

    public boolean isRearing() {
        return this.getHorseWatchableBoolean(64);
    }

    public void makeHorseRearWithSound() {
        this.makeHorseRear();
        java.lang.String text = this.getAngrySoundName();
        if (text != null) {
            this.playSound(text, this.getSoundVolume(), this.Jr());
        }
    }

    public boolean getCanSpawnHere() {
        this.prepareChunkForSpawn();
        return super.getCanSpawnHere();
    }

    public boolean prepareChunkForSpawn() {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posZ);
        this.world.getBiomeGenForCoords(new BlockPos(i, 0, j));
        return true;
    }

    public float method_747d3e7081a808d0b7a1(float f) {
        return this.bfQ + (this.bfX - this.bfQ) * f;
    }

    public void dropChests() {
        if (!this.world.isRemote && this.isChested()) {
            this.dropItem(Item.getItemFromBlock(Blocks.chest), 1);
            this.setChested(false);
        }
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
        this.dataWatcher.addObject(19, (byte) 0);
        this.dataWatcher.addObject(20, 0);
        this.dataWatcher.addObject(
                21,
                java.lang.String.valueOf(
                        net.minecraft.entity.passive.EntityHorse.packedTextFragments[0]));
        this.dataWatcher.addObject(22, 0);
    }

    static {
        net.minecraft.entity.passive.EntityHorse.packedTextFragments = new String[8];
        net.minecraft.entity.passive.EntityHorse.packedTextFragments[0] = new String("");
        net.minecraft.entity.passive.EntityHorse.packedTextFragments[1] = new String("");
        net.minecraft.entity.passive.EntityHorse.packedTextFragments[2] = new String("_");
        net.minecraft.entity.passive.EntityHorse.packedTextFragments[3] = new String("_");
        net.minecraft.entity.passive.EntityHorse.packedTextFragments[4] = new String("");
        net.minecraft.entity.passive.EntityHorse.packedTextFragments[6] = new String("");
        net.minecraft.entity.passive.EntityHorse.packedTextFragments[7] = new String("");
        net.minecraft.entity.passive.EntityHorse.horseBreedingSelector =
                new HorseBreedingSelectorPredicate();
        net.minecraft.entity.passive.EntityHorse.horseJumpStrength =
                new RangedAttribute((IAttribute) null, "horse.jumpStrength", 0.7, 0.0, 2.0)
                        .setDescription("Jump Strength")
                        .setShouldWatch(true);
        net.minecraft.entity.passive.EntityHorse.horseArmorTextures =
                new String[] {
                    null,
                    "textures/entity/horse/armor/horse_armor_iron.png",
                    "textures/entity/horse/armor/horse_armor_gold.png",
                    "textures/entity/horse/armor/horse_armor_diamond.png"
                };
        net.minecraft.entity.passive.EntityHorse.HORSE_ARMOR_TEXTURES_ABBR =
                new String[] {packedTextFragments[6], "meo", "goo", "dio"};
        net.minecraft.entity.passive.EntityHorse.armorValues = new int[] {0, 5, 7, 11};
        net.minecraft.entity.passive.EntityHorse.horseTextures =
                new String[] {
                    "textures/entity/horse/horse_white.png",
                    "textures/entity/horse/horse_creamy.png",
                    "textures/entity/horse/horse_chestnut.png",
                    "textures/entity/horse/horse_brown.png",
                    "textures/entity/horse/horse_black.png",
                    "textures/entity/horse/horse_gray.png",
                    "textures/entity/horse/horse_darkbrown.png"
                };
        net.minecraft.entity.passive.EntityHorse.HORSE_TEXTURES_ABBR =
                new String[] {"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
        net.minecraft.entity.passive.EntityHorse.horseMarkingTextures =
                new String[] {
                    null,
                    "textures/entity/horse/horse_markings_white.png",
                    "textures/entity/horse/horse_markings_whitefield.png",
                    "textures/entity/horse/horse_markings_whitedots.png",
                    "textures/entity/horse/horse_markings_blackdots.png"
                };
        net.minecraft.entity.passive.EntityHorse.HORSE_MARKING_TEXTURES_ABBR =
                new String[] {packedTextFragments[7], "wo_", "wmo", "wdo", "bdo"};
    }

    private void func_110266_cB() {
        this.openHorseMouth();
        if (!this.isSlient()) {
            this.world.playSoundAtEntity(
                    this,
                    "eating",
                    1.0F,
                    1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
        }
    }

    public int getTalkInterval() {
        return 400;
    }

    public void setHorseTamed(boolean p171_0) {
        this.setHorseWatchableBoolean(2, p171_0);
    }

    public boolean func_175507_cI() {
        return this.field_175508_bO;
    }

    public void setTemper(int temperValue) {
        this.temper = temperValue;
    }

    protected boolean isMovementBlocked() {
        return this.riddenByEntity != null && this.isHorseSaddled()
                ? true
                : this.isEatingHaystack() || this.isRearing();
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        String text;
        String textValue;
        {
            super.readEntityFromNBT(compoundTag);
            this.setEatingHaystack(compoundTag.getBoolean("EatingHaystack"));
            this.setBreeding(compoundTag.getBoolean("Bred"));
            String textValue2 = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
            this.setChested(compoundTag.getBoolean("ChestedHorse"));
            this.method_596df71968f7c26b36a0(compoundTag.getBoolean("HasReproduced"));
            this.setHorseType(compoundTag.getInteger("Type"));
            text = textValue2;
            this.setHorseVariant(compoundTag.getInteger("Variant"));
            this.setTemper(compoundTag.getInteger("Temper"));
            this.setHorseTamed(compoundTag.getBoolean("Tame"));
            textValue = packedTextFragments[4];
            if (compoundTag.hasKey("OwnerUUID", 8)) {
                textValue = compoundTag.getString("OwnerUUID");
                if (!(text == null)) {

                    String textValue3 = compoundTag.getString("Owner");
                    textValue = PreYggdrasilConverter.getStringUUIDFromName(textValue3);
                }
            } else {

                String textValue3 = compoundTag.getString("Owner");
                textValue = PreYggdrasilConverter.getStringUUIDFromName(textValue3);
            }
        }

        if (textValue.length() > 0) {
            this.setOwnerId(textValue);
        }

        IAttributeInstance attributeInstanceByName =
                this.getAttributeMap().getAttributeInstanceByName("Speed");
        if (attributeInstanceByName != null) {
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
                    .setBaseValue(attributeInstanceByName.getBaseValue() * 0.25);
        }

        if (this.isChested()) {
            NBTTagList nbtTagList = compoundTag.getTagList("Items", 10);
            this.initHorseChest();
            int i = 0;

            while (i < nbtTagList.tagCount()) {
                CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(i);
                int j = compoundTagValue.getByte("Slot") & 255;
                if (j >= 2 && j < this.horseChest.getSizeInventory()) {
                    this.horseChest.setInventorySlotContents(
                            j, ItemStack.loadItemStackFromNBT(compoundTagValue));
                }

                i++;
                if (text != null) {
                    break;
                }
            }
        }

        if (compoundTag.hasKey("ArmorItem", 10)) {
            ItemStack itemStack =
                    ItemStack.loadItemStackFromNBT(compoundTag.getCompoundTag("ArmorItem"));
            if (itemStack != null && isArmorItem(itemStack.getItem())) {
                this.horseChest.setInventorySlotContents(1, itemStack);
            }
        }

        label43:
        {
            if (compoundTag.hasKey("SaddleItem", 10)) {
                ItemStack itemStackValue =
                        ItemStack.loadItemStackFromNBT(compoundTag.getCompoundTag("SaddleItem"));
                if (itemStackValue == null || itemStackValue.getItem() != Items.saddle) {
                    break label43;
                }

                this.horseChest.setInventorySlotContents(0, itemStackValue);
                if (text == null) {
                    break label43;
                }
            }

            if (compoundTag.getBoolean("Saddle")) {
                this.horseChest.setInventorySlotContents(0, new ItemStack(Items.saddle));
            }
        }

        this.updateHorseSlots();
    }

    private float getModifiedMaxHealth() {
        return 15.0F + this.rand.nextInt(8) + this.rand.nextInt(9);
    }

    private boolean getHorseWatchableBoolean(int p171_0) {
        return (this.dataWatcher.getWatchableObjectInt(16) & p171_0) != 0;
    }

    public IEntityLivingData onInitialSpawn(
            DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
        String text;
        Object object;
        int i;
        int j;
        label51:
        {
            object = super.onInitialSpawn(difficultyInstance, iEntityLivingData);
            String textValue = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();

            text = textValue;
            i = 0;
            if (object instanceof EntityHorseGroupData) {
                j = ((EntityHorseGroupData) object).horseType;
                i = ((EntityHorseGroupData) object).horseVariant & 0xFF | this.rand.nextInt(5) << 8;
                if (text == null) {
                    break label51;
                }
            }

            label43:
            {
                if (this.rand.nextInt(10) == 0) {
                    j = 1;
                    if (text == null) {
                        break label43;
                    }
                }

                int k = this.rand.nextInt(7);
                int l = this.rand.nextInt(5);
                j = 0;
                i = k | l << 8;
            }

            object = new EntityHorseGroupData(j, i);
        }

        this.setHorseType(j);
        this.setHorseVariant(i);
        if (this.rand.nextInt(5) == 0) {
            this.setGrowingAge(-24000);
        }

        label37:
        {
            if (j != 4 && j != 3) {
                this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
                        .setBaseValue(this.getModifiedMaxHealth());
                if (j == 0) {
                    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
                            .setBaseValue(this.getModifiedMovementSpeed());
                    if (text == null) {
                        break label37;
                    }
                }

                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.175F);
                if (text == null) {
                    break label37;
                }
            }

            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0);
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2F);
        }

        {
            if (j != 2 && j != 1) {
                this.getEntityAttribute(net.minecraft.entity.passive.EntityHorse.horseJumpStrength)
                        .setBaseValue(this.getModifiedJumpStrength());
                if (!(text == null)) {

                    this.getEntityAttribute(
                                    net.minecraft.entity.passive.EntityHorse.horseJumpStrength)
                            .setBaseValue(0.5);
                }
            } else {

                this.getEntityAttribute(net.minecraft.entity.passive.EntityHorse.horseJumpStrength)
                        .setBaseValue(0.5);
            }
        }

        this.setHealth(this.getMaxHealth());
        return (IEntityLivingData) object;
    }

    public int getTotalArmorValue() {
        return net.minecraft.entity.passive.EntityHorse.armorValues[
                this.getHorseArmorIndexSynced()];
    }

    protected void method_127959ec2d9639b60adb(boolean flag) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        EnumParticleTypes enumParticleTypes =
                flag ? EnumParticleTypes.HEART : EnumParticleTypes.SMOKE_NORMAL;
        int i = 0;

        while (i < 7) {
            double d0 = this.rand.nextGaussian() * 0.02;
            double d1 = this.rand.nextGaussian() * 0.02;
            double d2 = this.rand.nextGaussian() * 0.02;
            this.world.spawnParticle(
                    enumParticleTypes,
                    this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                    this.posY + 0.5 + this.rand.nextFloat() * this.height,
                    this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                    d0,
                    d1,
                    d2,
                    new int[0]);
            i++;
            if (text != null) {
                break;
            }
        }
    }

    public void setOwnerId(String text) {
        this.dataWatcher.updateObject(21, text);
    }

    public String getHorseTexture() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }

        return this.texturePrefix;
    }

    public void setHorseArmorStack(ItemStack itemStack) {
        this.dataWatcher.updateObject(22, this.getHorseArmorIndex(itemStack));
        this.resetTexturePrefix();
    }

    public void setScaleForAge(boolean flag) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (flag) {
            this.setScale(this.gh());
            if (text == null) {
                return;
            }
        }

        this.setScale(1.0F);
    }

    public static boolean isArmorItem(Item item) {
        return item == Items.iron_horse_armor
                || item == Items.golden_horse_armor
                || item == Items.diamond_horse_armor;
    }

    public EntityHorse(World worldValue) {
        super(worldValue);
        this.field_175508_bO = false;
        this.setSize(1.4F, 1.6F);
        this.isImmuneToFire = false;
        this.setChested(false);
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.2));
        this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.0));
        this.tasks.addTask(6, new EntityAIWander(this, 0.7));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.initHorseChest();
    }

    protected float getSoundVolume() {
        return 0.8F;
    }

    private double getModifiedJumpStrength() {
        return 0.4F
                + this.rand.nextDouble() * 0.2
                + this.rand.nextDouble() * 0.2
                + this.rand.nextDouble() * 0.2;
    }

    public void onUpdate() {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        super.onUpdate();
        String textValue = text;
        if (this.world.isRemote && this.dataWatcher.hasObjectChanged()) {
            this.dataWatcher.func_111144_e();
            this.resetTexturePrefix();
        }

        if (this.openMouthCounter > 0 && ++this.openMouthCounter > 30) {
            this.openMouthCounter = 0;
            this.setHorseWatchableBoolean(128, false);
        }

        if (!this.world.isRemote && this.jumpRearingCounter > 0 && ++this.jumpRearingCounter > 20) {
            this.jumpRearingCounter = 0;
            this.setRearing(false);
        }

        if (this.field_110278_bp > 0 && ++this.field_110278_bp > 8) {
            this.field_110278_bp = 0;
        }

        if (this.bdU > 0) {
            this.bdU++;
            if (this.bdU > 300) {
                this.bdU = 0;
            }
        }

        label67:
        {
            this.bfL = this.bfI;
            if (this.isEatingHaystack()) {
                this.bfI = this.bfI + ((1.0F - this.bfI) * 0.4F + 0.05F);
                if (!(this.bfI > 1.0F)) {
                    break label67;
                }

                this.bfI = 1.0F;
                if (textValue == null) {
                    break label67;
                }
            }

            this.bfI = this.bfI + ((0.0F - this.bfI) * 0.4F - 0.05F);
            if (this.bfI < 0.0F) {
                this.bfI = 0.0F;
            }
        }

        label60:
        {
            this.prevRearingAmount = this.rearingAmount;
            if (this.isRearing()) {
                this.bfL = this.bfI = 0.0F;
                this.rearingAmount =
                        this.rearingAmount + ((1.0F - this.rearingAmount) * 0.4F + 0.05F);
                if (!(this.rearingAmount > 1.0F)) {
                    break label60;
                }

                this.rearingAmount = 1.0F;
                if (textValue == null) {
                    break label60;
                }
            }

            this.bfJ = false;
            this.rearingAmount =
                    this.rearingAmount
                            + ((0.8F * this.rearingAmount * this.rearingAmount * this.rearingAmount
                                                    - this.rearingAmount)
                                            * 0.6F
                                    - 0.05F);
            if (this.rearingAmount < 0.0F) {
                this.rearingAmount = 0.0F;
            }
        }

        this.bfQ = this.bfX;
        if (this.getHorseWatchableBoolean(128)) {
            this.bfX = this.bfX + ((1.0F - this.bfX) * 0.7F + 0.05F);
            if (!(this.bfX > 1.0F)) {
                return;
            }

            this.bfX = 1.0F;
            if (textValue == null) {
                return;
            }
        }

        this.bfX = this.bfX + ((0.0F - this.bfX) * 0.7F - 0.05F);
        if (this.bfX < 0.0F) {
            this.bfX = 0.0F;
        }
    }

    public void moveEntityWithHeading(float f, float f1) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (this.riddenByEntity != null
                && this.riddenByEntity instanceof LivingEntity
                && this.isHorseSaddled()) {
            this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
            this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
            f = ((LivingEntity) this.riddenByEntity).moveStrafing * 0.5F;
            f1 = ((LivingEntity) this.riddenByEntity).moveForward;
            if (f1 <= 0.0F) {
                f1 *= 0.25F;
                this.gallopTime = 0;
            }

            if (this.onGround && this.jumpPower == 0.0F && this.isRearing() && !this.bfJ) {
                f = 0.0F;
                f1 = 0.0F;
            }

            if (this.jumpPower > 0.0F && !this.dS() && this.onGround) {
                this.motionY = this.getHorseJumpStrength() * this.jumpPower;
                if (this.isPotionActive(Potion.JUMP)) {
                    this.motionY =
                            this.motionY
                                    + (this.getActivePotionEffect(Potion.JUMP).getAmplifier() + 1)
                                            * 0.1F;
                }

                this.method_6b5a2ec04cd74a4042ef(true);
                this.isAirBorne = true;
                if (f1 > 0.0F) {
                    float f2 = MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F);
                    float f3 = MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F);
                    this.motionX = this.motionX + -0.4F * f2 * this.jumpPower;
                    this.motionZ = this.motionZ + 0.4F * f3 * this.jumpPower;
                    this.playSound("mob.horse.jump", 0.4F, 1.0F);
                }

                this.jumpPower = 0.0F;
            }

            this.stepHeight = 1.0F;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
            if (!this.world.isRemote) {
                this.setAIMoveSpeed(
                        (float)
                                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
                                        .getAttributeValue());
                super.moveEntityWithHeading(f, f1);
            }

            if (this.onGround) {
                this.jumpPower = 0.0F;
                this.method_6b5a2ec04cd74a4042ef(false);
            }

            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d1 = this.posX - this.prevPosX;
            double d0 = this.posZ - this.prevPosZ;
            float f4 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
            if (f4 > 1.0F) {
                f4 = 1.0F;
            }

            this.limbSwingAmount = this.limbSwingAmount + (f4 - this.limbSwingAmount) * 0.4F;
            this.limbSwing = this.limbSwing + this.limbSwingAmount;
            if (text == null) {
                return;
            }
        }

        this.stepHeight = 0.5F;
        this.jumpMovementFactor = 0.02F;
        super.moveEntityWithHeading(f, f1);
    }

    private double getModifiedMovementSpeed() {
        return (0.45F
                        + this.rand.nextDouble() * 0.3
                        + this.rand.nextDouble() * 0.3
                        + this.rand.nextDouble() * 0.3)
                * 0.25;
    }

    public float gh() {
        int growingAge = this.getGrowingAge();
        return growingAge >= 0 ? 1.0F : 0.5F + (-24000 - growingAge) / -24000.0F * 0.5F;
    }

    public boolean isAdultHorse() {
        return !this.isChild();
    }

    public int getHorseArmorIndexSynced() {
        return this.dataWatcher.getWatchableObjectInt(22);
    }

    private void updateHorseSlots() {
        if (!this.world.isRemote) {
            this.setHorseSaddled(this.horseChest.getStackInSlot(0) != null);
            if (this.canWearArmor()) {
                this.setHorseArmorStack(this.horseChest.getStackInSlot(1));
            }
        }
    }

    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        String text;
        EntityHorse entityHorse;
        EntityHorse entityHorseValue;
        int k;
        {
            entityHorse = (EntityHorse) entityAgeable;
            entityHorseValue = new EntityHorse(this.world);
            String textValue = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
            int horseType = this.getHorseType();
            text = textValue;
            int j = entityHorse.getHorseType();
            k = 0;
            if (horseType == j) {
                k = horseType;
                if (!(text == null)) {

                    if (horseType == 0 && j == 1 || horseType == 1 && j == 0) {
                        k = 2;
                    }
                }
            } else {

                if (horseType == 0 && j == 1 || horseType == 1 && j == 0) {
                    k = 2;
                }
            }
        }

        if (k == 0) {
            int i1;
            label60:
            {
                int l = this.rand.nextInt(9);
                if (l < 4) {
                    i1 = this.jvm171_f_977d11088cb5e0() & 0xFF;
                    if (text == null) {
                        break label60;
                    }
                }

                if (l < 8) {
                    i1 = entityHorse.jvm171_f_977d11088cb5e0() & 0xFF;
                    if (text == null) {
                        break label60;
                    }
                }

                i1 = this.rand.nextInt(7);
            }

            label61:
            {
                int j1 = this.rand.nextInt(5);
                if (j1 < 2) {
                    i1 |= this.jvm171_f_977d11088cb5e0() & 0xFF00;
                    if (text == null) {
                        break label61;
                    }
                }

                if (j1 < 4) {
                    i1 |= entityHorse.jvm171_f_977d11088cb5e0() & 0xFF00;
                    if (text == null) {
                        break label61;
                    }
                }

                i1 |= this.rand.nextInt(5) << 8 & 0xFF00;
            }

            entityHorseValue.setHorseVariant(i1);
        }

        entityHorseValue.setHorseType(k);
        double d1 =
                this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getBaseValue()
                        + entityAgeable
                                .getEntityAttribute(SharedMonsterAttributes.maxHealth)
                                .getBaseValue()
                        + this.getModifiedMaxHealth();
        entityHorseValue
                .getEntityAttribute(SharedMonsterAttributes.maxHealth)
                .setBaseValue(d1 / 3.0);
        double d2 =
                this.getEntityAttribute(net.minecraft.entity.passive.EntityHorse.horseJumpStrength)
                                .getBaseValue()
                        + entityAgeable
                                .getEntityAttribute(
                                        net.minecraft.entity.passive.EntityHorse.horseJumpStrength)
                                .getBaseValue()
                        + this.getModifiedJumpStrength();
        entityHorseValue
                .getEntityAttribute(net.minecraft.entity.passive.EntityHorse.horseJumpStrength)
                .setBaseValue(d2 / 3.0);
        double d0 =
                this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getBaseValue()
                        + entityAgeable
                                .getEntityAttribute(SharedMonsterAttributes.movementSpeed)
                                .getBaseValue()
                        + this.getModifiedMovementSpeed();
        entityHorseValue
                .getEntityAttribute(SharedMonsterAttributes.movementSpeed)
                .setBaseValue(d0 / 3.0);
        return entityHorseValue;
    }

    public String getOwnerId() {
        return this.dataWatcher.getWatchableObjectString(21);
    }

    private void initHorseChest() {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        AnimalChest bfVValue = this.horseChest;
        String textValue = text;
        this.horseChest = new AnimalChest("HorseChest", this.getChestSize());
        this.horseChest.setCustomName(this.getName());
        if (bfVValue != null) {
            bfVValue.func_110132_b(this);
            int i = Math.min(bfVValue.getSizeInventory(), this.horseChest.getSizeInventory());
            int j = 0;

            while (j < i) {
                ItemStack itemStack = bfVValue.getStackInSlot(j);
                if (itemStack != null) {
                    this.horseChest.setInventorySlotContents(j, itemStack.copy());
                }

                j++;
                if (textValue != null) {
                    break;
                }
            }
        }

        this.horseChest.func_110134_a(this);
        this.updateHorseSlots();
    }

    public int L() {
        return 6;
    }

    private void makeHorseRear() {
        if (!this.world.isRemote) {
            this.jumpRearingCounter = 1;
            this.setRearing(true);
        }
    }

    public int jvm171_f_977d11088cb5e0() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }

    public boolean isSterile() {
        return this.isUndead() || this.getHorseType() == 2;
    }

    private void resetTexturePrefix() {
        this.texturePrefix = null;
    }

    public boolean func_110253_bW() {
        return this.isAdultHorse();
    }

    public float getRearingAmount(float f) {
        return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * f;
    }

    // region String decoding support

    // endregion

    public void method_596df71968f7c26b36a0(boolean bdRValue) {
        this.bdR = bdRValue;
    }

    public boolean interact(PlayerEntity playerEntity) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        String textValue = text;
        if (itemStack != null && itemStack.getItem() == Items.spawn_egg) {
            return super.interact(playerEntity);
        }

        if (!this.isTame() && this.isUndead()) {
            return false;
        }

        if (this.isTame() && this.isAdultHorse() && playerEntity.isSneaking()) {
            this.openGUI(playerEntity);
            return true;
        }

        if (this.func_110253_bW() && this.riddenByEntity != null) {
            return super.interact(playerEntity);
        }

        if (itemStack != null) {
            byte b0 = 0;
            if (this.canWearArmor()) {
                byte b1;
                label197:
                {
                    b1 = -1;
                    if (itemStack.getItem() == Items.iron_horse_armor) {
                        b1 = 1;
                        if (textValue == null) {
                            break label197;
                        }
                    }

                    if (itemStack.getItem() == Items.golden_horse_armor) {
                        b1 = 2;
                        if (textValue == null) {
                            break label197;
                        }
                    }

                    if (itemStack.getItem() == Items.diamond_horse_armor) {
                        b1 = 3;
                    }
                }

                if (b1 >= 0) {
                    if (!this.isTame()) {
                        this.makeHorseRearWithSound();
                        return true;
                    }

                    this.openGUI(playerEntity);
                    return true;
                }
            }

            if (b0 == 0 && !this.isUndead()) {
                short short1;
                byte b2;
                float f;
                label199:
                {
                    f = 0.0F;
                    short1 = 0;
                    b2 = 0;
                    if (itemStack.getItem() == Items.wheat) {
                        f = 2.0F;
                        short1 = 20;
                        b2 = 3;
                        if (textValue == null) {
                            break label199;
                        }
                    }

                    if (itemStack.getItem() == Items.sugar) {
                        f = 1.0F;
                        short1 = 30;
                        b2 = 3;
                        if (textValue == null) {
                            break label199;
                        }
                    }

                    if (Block.getBlockFromItem(itemStack.getItem()) == Blocks.hay_block) {
                        f = 20.0F;
                        short1 = 180;
                        if (textValue == null) {
                            break label199;
                        }
                    }

                    if (itemStack.getItem() == Items.apple) {
                        f = 3.0F;
                        short1 = 60;
                        b2 = 3;
                        if (textValue == null) {
                            break label199;
                        }
                    }

                    if (itemStack.getItem() == Items.golden_carrot) {
                        f = 4.0F;
                        short1 = 60;
                        b2 = 5;
                        if (!this.isTame() || this.getGrowingAge() != 0) {
                            break label199;
                        }

                        b0 = 1;
                        this.setInLove(playerEntity);
                        if (textValue == null) {
                            break label199;
                        }
                    }

                    if (itemStack.getItem() == Items.golden_apple) {
                        f = 10.0F;
                        short1 = 240;
                        b2 = 10;
                        if (this.isTame() && this.getGrowingAge() == 0) {
                            b0 = 1;
                            this.setInLove(playerEntity);
                        }
                    }
                }

                if (this.getHealth() < this.getMaxHealth() && f > 0.0F) {
                    this.heal(f);
                    b0 = 1;
                }

                if (!this.isAdultHorse() && short1 > 0) {
                    this.addGrowth(short1);
                    b0 = 1;
                }

                if (b2 > 0 && (b0 != 0 || !this.isTame()) && b2 < this.getMaxTemper()) {
                    b0 = 1;
                    this.increaseTemper(b2);
                }

                if (b0 != 0) {
                    this.func_110266_cB();
                }
            }

            if (!this.isTame() && b0 == 0) {
                if (itemStack != null && itemStack.interactWithEntity(playerEntity, this)) {
                    return true;
                }

                this.makeHorseRearWithSound();
                return true;
            }

            if (b0 == 0
                    && this.canCarryChest()
                    && !this.isChested()
                    && itemStack.getItem() == Item.getItemFromBlock(Blocks.chest)) {
                this.setChested(true);
                this.playSound(
                        "mob.chickenplop",
                        1.0F,
                        (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                b0 = 1;
                this.initHorseChest();
            }

            if (b0 == 0
                    && this.func_110253_bW()
                    && !this.isHorseSaddled()
                    && itemStack.getItem() == Items.saddle) {
                this.openGUI(playerEntity);
                return true;
            }

            if (b0 != 0) {
                if (!playerEntity.capabilities.isCreativeMode && --itemStack.stackSize == 0) {
                    playerEntity.inventory.setInventorySlotContents(
                            playerEntity.inventory.currentItem, (ItemStack) null);
                }

                return true;
            }
        }

        if (!this.func_110253_bW() || this.riddenByEntity != null) {
            return super.interact(playerEntity);
        }

        if (itemStack != null && itemStack.interactWithEntity(playerEntity, this)) {
            return true;
        }

        this.mountTo(playerEntity);
        return true;
    }

    public float method_ad4859278b6f26197223(float f) {
        return this.bfL + (this.bfI - this.bfL) * f;
    }

    public boolean isHorseSaddled() {
        return this.getHorseWatchableBoolean(4);
    }

    public boolean canCarryChest() {
        int horseType = this.getHorseType();
        return horseType == 2 || horseType == 1;
    }

    public void method_6b5a2ec04cd74a4042ef(boolean bfNValue) {
        this.bfN = bfNValue;
    }

    private void mountTo(PlayerEntity playerEntity) {
        playerEntity.rotationYaw = this.rotationYaw;
        playerEntity.rotationPitch = this.rotationPitch;
        this.setEatingHaystack(false);
        this.setRearing(false);
        if (!this.world.isRemote) {
            playerEntity.mountEntity(this);
        }
    }

    public boolean isTame() {
        return this.getHorseWatchableBoolean(2);
    }

    public boolean isBreeding() {
        return this.getHorseWatchableBoolean(16);
    }

    public void setHorseVariant(int p171_0) {
        this.dataWatcher.updateObject(20, p171_0);
        this.resetTexturePrefix();
    }

    public void setChested(boolean p171_0) {
        this.setHorseWatchableBoolean(8, p171_0);
    }

    public void handleHealthUpdate(byte b0) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (b0 == 7) {
            this.method_127959ec2d9639b60adb(true);
            if (text == null) {
                return;
            }
        }

        if (b0 == 6) {
            this.method_127959ec2d9639b60adb(false);
            if (text == null) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    public boolean replaceItemInInventory(int i, ItemStack itemStack) {
        if (i == 499 && this.canCarryChest()) {
            if (itemStack == null && this.isChested()) {
                this.setChested(false);
                this.initHorseChest();
                return true;
            }

            if (itemStack != null
                    && itemStack.getItem() == Item.getItemFromBlock(Blocks.chest)
                    && !this.isChested()) {
                this.setChested(true);
                this.initHorseChest();
                return true;
            }
        }

        int j = i - 400;
        if (j >= 0 && j < 2 && j < this.horseChest.getSizeInventory()) {
            if (j == 0 && itemStack != null && itemStack.getItem() != Items.saddle) {
                return false;
            } else if (j != 1
                    || (itemStack == null || isArmorItem(itemStack.getItem()))
                            && this.canWearArmor()) {
                this.horseChest.setInventorySlotContents(j, itemStack);
                this.updateHorseSlots();
                return true;
            } else {
                return false;
            }
        } else {
            int k = i - 500 + 2;
            if (k >= 2 && k < this.horseChest.getSizeInventory()) {
                this.horseChest.setInventorySlotContents(k, itemStack);
                return true;
            } else {
                return false;
            }
        }
    }

    protected Item getDropItem() {
        int i = this.rand.nextInt(4) == 0 ? 1 : 0;
        int j = this.getHorseType();
        return j == 4
                ? Items.bone
                : (j == 3 ? (i != 0 ? null : Items.rotten_flesh) : Items.leather);
    }

    protected void func_142017_o(float p171_0) {
        if (p171_0 > 6.0F && this.isEatingHaystack()) {
            this.setEatingHaystack(false);
        }
    }

    public boolean dS() {
        return this.bfN;
    }

    public boolean isEatingHaystack() {
        return this.getHorseWatchableBoolean(32);
    }

    public String getName() {
        if (this.jvm171_d_45ffeb83d14f3c()) {
            return this.getCustomNameTag();
        }

        int horseType = this.getHorseType();
        switch (horseType) {
            case 0:
            default:
                return StatCollector.translateToLocal("entity.horse.name");
            case 1:
                return StatCollector.translateToLocal("entity.donkey.name");
            case 2:
                return StatCollector.translateToLocal("entity.mule.name");
            case 3:
                return StatCollector.translateToLocal("entity.zombiehorse.name");
            case 4:
                return StatCollector.translateToLocal("entity.skeletonhorse.name");
        }
    }

    public int increaseTemper(int i) {
        int j = MathHelper.clamp(this.getTemper() + i, 0, this.getMaxTemper());
        this.setTemper(j);
        return j;
    }

    protected String getHurtSound() {
        this.openHorseMouth();
        if (this.rand.nextInt(3) == 0) {
            this.makeHorseRear();
        }

        int horseType = this.getHorseType();
        return horseType == 3
                ? "mob.horse.zombie.hit"
                : (horseType == 4
                        ? "mob.horse.skeleton.hit"
                        : (horseType != 1 && horseType != 2
                                ? "mob.horse.hit"
                                : "mob.horse.donkey.hit"));
    }

    public String[] getVariantTexturePaths() {
        if (this.texturePrefix == null) {
            this.setHorseTexturePaths();
        }

        return this.horseTexturesArray;
    }

    public void openGUI(PlayerEntity playerEntity) {
        if (!this.world.isRemote
                && (this.riddenByEntity == null || this.riddenByEntity == playerEntity)
                && this.isTame()) {
            this.horseChest.setCustomName(this.getName());
            playerEntity.displayGUIHorse(this, this.horseChest);
        }
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setBoolean("EatingHaystack", this.isEatingHaystack());
        compoundTag.setBoolean("ChestedHorse", this.isChested());
        compoundTag.setBoolean("HasReproduced", this.jc());
        compoundTag.setBoolean("Bred", this.isBreeding());
        compoundTag.setInteger("Type", this.getHorseType());
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        compoundTag.setInteger("Variant", this.jvm171_f_977d11088cb5e0());
        compoundTag.setInteger("Temper", this.getTemper());
        String textValue = text;
        compoundTag.setBoolean("Tame", this.isTame());
        compoundTag.setString("OwnerUUID", this.getOwnerId());
        if (this.isChested()) {
            NBTTagList nbtTagList = new NBTTagList();
            int i = 2;

            while (i < this.horseChest.getSizeInventory()) {
                ItemStack itemStack = this.horseChest.getStackInSlot(i);
                if (itemStack != null) {
                    CompoundTag compoundTagValue = new CompoundTag();
                    compoundTagValue.setByte("Slot", (byte) i);
                    itemStack.writeToNBT(compoundTagValue);
                    nbtTagList.appendTag(compoundTagValue);
                }

                i++;
                if (textValue != null) {
                    break;
                }
            }

            compoundTag.setTag("Items", nbtTagList);
        }

        if (this.horseChest.getStackInSlot(1) != null) {
            compoundTag.setTag(
                    "ArmorItem", this.horseChest.getStackInSlot(1).writeToNBT(new CompoundTag()));
        }

        if (this.horseChest.getStackInSlot(0) != null) {
            compoundTag.setTag(
                    "SaddleItem", this.horseChest.getStackInSlot(0).writeToNBT(new CompoundTag()));
        }
    }

    public void onInventoryChanged(InventoryBasic inventoryBasic) {
        int horseArmorIndexSynced = this.getHorseArmorIndexSynced();
        boolean horseSaddled = this.isHorseSaddled();
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        this.updateHorseSlots();
        String textValue = text;
        if (this.ticksExisted > 20) {
            {
                if (horseArmorIndexSynced == 0
                        && horseArmorIndexSynced != this.getHorseArmorIndexSynced()) {
                    this.playSound("mob.horse.armor", 0.5F, 1.0F);
                    if (!(textValue == null)) {

                        if (horseArmorIndexSynced != this.getHorseArmorIndexSynced()) {
                            this.playSound("mob.horse.armor", 0.5F, 1.0F);
                        }
                    }
                } else {

                    if (horseArmorIndexSynced != this.getHorseArmorIndexSynced()) {
                        this.playSound("mob.horse.armor", 0.5F, 1.0F);
                    }
                }
            }

            if (!horseSaddled && this.isHorseSaddled()) {
                this.playSound("mob.horse.leather", 0.5F, 1.0F);
            }
        }
    }

    public int getTemper() {
        return this.temper;
    }

    public boolean isBreedingItem(ItemStack itemStack) {
        return false;
    }

    public void setHorseType(int p171_0) {
        this.dataWatcher.updateObject(19, (byte) p171_0);
        this.resetTexturePrefix();
    }

    public void updateRiderPosition() {
        super.updateRiderPosition();
        if (this.prevRearingAmount > 0.0F) {
            float f = MathHelper.sin(this.renderYawOffset * (float) Math.PI / 180.0F);
            float f1 = MathHelper.cos(this.renderYawOffset * (float) Math.PI / 180.0F);
            float f2 = 0.7F * this.prevRearingAmount;
            float f3 = 0.15F * this.prevRearingAmount;
            this.riddenByEntity.setPosition(
                    this.posX + f2 * f,
                    this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + f3,
                    this.posZ - f2 * f1);
            if (this.riddenByEntity instanceof LivingEntity) {
                ((LivingEntity) this.riddenByEntity).renderYawOffset = this.renderYawOffset;
            }
        }
    }

    private boolean canMate() {
        return this.riddenByEntity == null
                && this.ridingEntity == null
                && this.isTame()
                && this.isAdultHorse()
                && !this.isSterile()
                && this.getHealth() >= this.getMaxHealth()
                && this.isInLove();
    }

    public void setEatingHaystack(boolean flag) {
        this.setEating(flag);
    }

    public void setHorseSaddled(boolean p171_0) {
        this.setHorseWatchableBoolean(4, p171_0);
    }

    protected String getLivingSound() {
        this.openHorseMouth();
        if (this.rand.nextInt(10) == 0 && !this.isMovementBlocked()) {
            this.makeHorseRear();
        }

        int horseType = this.getHorseType();
        return horseType == 3
                ? "mob.horse.zombie.idle"
                : (horseType == 4
                        ? "mob.horse.skeleton.idle"
                        : (horseType != 1 && horseType != 2
                                ? "mob.horse.idle"
                                : "mob.horse.donkey.idle"));
    }

    private void dropItemsInChest(Entity entity, AnimalChest animalChest) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (animalChest != null && !this.world.isRemote) {
            int i = 0;

            while (i < animalChest.getSizeInventory()) {
                ItemStack itemStack = animalChest.getStackInSlot(i);
                if (itemStack != null) {
                    this.entityDropItem(itemStack, 0.0F);
                }

                i++;
                if (text != null) {
                    break;
                }
            }
        }
    }

    public boolean canMateWith(AnimalEntity animalEntity) {
        if (animalEntity == this) {
            return false;
        } else if (animalEntity.getClass() != this.getClass()) {
            return false;
        } else {
            net.minecraft.entity.passive.EntityHorse entityHorse =
                    (net.minecraft.entity.passive.EntityHorse) animalEntity;
            if (this.canMate() && entityHorse.canMate()) {
                int horseType = this.getHorseType();
                int j = entityHorse.getHorseType();
                return horseType == j || horseType == 0 && j == 1 || horseType == 1 && j == 0;
            } else {
                return false;
            }
        }
    }

    private void openHorseMouth() {
        if (!this.world.isRemote) {
            this.openMouthCounter = 1;
            this.setHorseWatchableBoolean(128, true);
        }
    }

    public double getHorseJumpStrength() {
        return this.getEntityAttribute(net.minecraft.entity.passive.EntityHorse.horseJumpStrength)
                .getAttributeValue();
    }

    public boolean isUndead() {
        int horseType = this.getHorseType();
        return horseType == 3 || horseType == 4;
    }

    private void func_110210_cH() {
        this.field_110278_bp = 1;
    }

    public void dropChestItems() {
        this.dropItemsInChest(this, this.horseChest);
        this.dropChests();
    }

    public void setEating(boolean p171_0) {
        this.setHorseWatchableBoolean(32, p171_0);
    }

    private void setHorseTexturePaths() {
        String text;
        {
            String textValue = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
            this.texturePrefix = "horse/";
            this.horseTexturesArray[0] = null;
            text = textValue;
            this.horseTexturesArray[1] = null;
            this.horseTexturesArray[2] = null;
            int horseType = this.getHorseType();
            int j = this.jvm171_f_977d11088cb5e0();
            if (horseType == 0) {
                int k = j & 0xFF;
                int l = (j & 0xFF00) >> 8;
                if (k >= net.minecraft.entity.passive.EntityHorse.horseTextures.length) {
                    this.field_175508_bO = false;
                    return;
                }

                this.horseTexturesArray[0] =
                        net.minecraft.entity.passive.EntityHorse.horseTextures[k];
                this.texturePrefix =
                        this.texturePrefix
                                + net.minecraft.entity.passive.EntityHorse.HORSE_TEXTURES_ABBR[k];
                if (l >= net.minecraft.entity.passive.EntityHorse.horseMarkingTextures.length) {
                    this.field_175508_bO = false;
                    return;
                }

                this.horseTexturesArray[1] =
                        net.minecraft.entity.passive.EntityHorse.horseMarkingTextures[l];
                this.texturePrefix =
                        this.texturePrefix
                                + net.minecraft.entity.passive.EntityHorse
                                        .HORSE_MARKING_TEXTURES_ABBR[l];
                if (!(text == null)) {

                    this.horseTexturesArray[0] = packedTextFragments[1];
                    this.texturePrefix =
                            this.texturePrefix
                                    + packedTextFragments[2]
                                    + horseType
                                    + packedTextFragments[3];
                }
            } else {

                this.horseTexturesArray[0] = packedTextFragments[1];
                this.texturePrefix =
                        this.texturePrefix
                                + packedTextFragments[2]
                                + horseType
                                + packedTextFragments[3];
            }
        }

        int horseArmorIndexSynced = this.getHorseArmorIndexSynced();
        if (horseArmorIndexSynced
                >= net.minecraft.entity.passive.EntityHorse.horseArmorTextures.length) {
            this.field_175508_bO = false;
            if (text == null) {
                return;
            }
        }

        this.horseTexturesArray[2] =
                net.minecraft.entity.passive.EntityHorse.horseArmorTextures[horseArmorIndexSynced];
        this.texturePrefix =
                this.texturePrefix
                        + net.minecraft.entity.passive.EntityHorse.HORSE_ARMOR_TEXTURES_ABBR[
                                horseArmorIndexSynced];
        this.field_175508_bO = true;
    }

    public boolean allowLeashing() {
        return !this.isUndead() && super.allowLeashing();
    }

    private void setHorseWatchableBoolean(int i, boolean flag) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        int j = this.dataWatcher.getWatchableObjectInt(16);
        String textValue = text;
        if (flag) {
            this.dataWatcher.updateObject(16, j | i);
            if (textValue == null) {
                return;
            }
        }

        this.dataWatcher.updateObject(16, j & ~i);
    }

    public void setJumpPower(int i) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (this.isHorseSaddled()) {
            {
                if (i < 0) {
                    i = 0;
                    if (!(text == null)) {

                        this.bfJ = true;
                        this.makeHorseRear();
                    }
                } else {

                    this.bfJ = true;
                    this.makeHorseRear();
                }
            }

            if (i >= 90) {
                this.jumpPower = 1.0F;
                if (text == null) {
                    return;
                }
            }

            this.jumpPower = 0.4F + 0.4F * i / 90.0F;
        }
    }

    // Helper used only by this class.
    public static final class HorseBreedingSelectorPredicate
            implements com.google.common.base.Predicate {
        public boolean apply(java.lang.Object p171_0) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) p171_0);
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity instanceof net.minecraft.entity.passive.EntityHorse
                    && ((net.minecraft.entity.passive.EntityHorse) entity).isBreeding();
        }
    }
}
