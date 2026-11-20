package net.minecraft.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import optifine.BlockPosM;
import optifine.OptiFineConfig;
import optifine.Reflector;

import java.util.UUID;

public abstract class EntityLiving extends LivingEntity {
    protected float[] equipmentDropChances;
    private boolean persistenceRequired;
    protected EntityJumpHelper jumpHelper;
    private static String[] packedTextFragments;
    private EntityLookHelper lookHelper;
    private boolean canPickUpLoot;
    private ItemStack[] equipment = new ItemStack[5];
    public BiomeGenBase spawnBiome;
    private LivingEntity attackTarget;
    protected PathNavigate navigator;
    private CompoundTag leashNBTTag;
    protected int experienceValue;
    private EntitySenses senses;
    private static boolean aJj;
    public int randomMobsId;
    private Entity leashedToEntity;

    protected EntityMoveHelper moveHelper;
    protected EntityAITasks targetTasks;
    public int livingSoundTime;
    protected EntityAITasks tasks;
    private EntityBodyHelper bodyHelper;
    private boolean isLeashed;
    public BlockPos spawnPosition;

    protected String getLivingSound() {
        return null;
    }

    public float getRenderSizeModifier() {
        return 1.0F;
    }

    public boolean canAttackClass(Class oclass) {
        return oclass != EntityGhast.class;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap()
                .registerAttribute(SharedMonsterAttributes.followRange)
                .setBaseValue(16.0);
    }

    private boolean canSkipUpdate() {
        if (this.isChild()) {
            return false;
        }

        if (this.hurtTime > 0) {
            return false;
        }

        if (this.ticksExisted < 20) {
            return false;
        }

        World entityWorld = this.getEntityWorld();
        if (entityWorld == null) {
            return false;
        }

        if (entityWorld.playerEntities.size() != 1) {
            return false;
        }

        Entity entity = (Entity) entityWorld.playerEntities.get(0);
        double d0 = Math.max(Math.abs(this.posX - entity.posX) - 16.0, 0.0);
        double d1 = Math.max(Math.abs(this.posZ - entity.posZ) - 16.0, 0.0);
        double d2 = d0 * d0 + d1 * d1;
        return !this.isInRangeToRenderDist(d2);
    }

    public LivingEntity getAttackTarget() {
        return this.attackTarget;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(15, (byte) 0);
    }

    protected void updateAITasks() {}

    public void eatGrassBonus() {}

    public static void setLegacyClickGuiPanelLimit(boolean aJjValue) {
        aJj = aJjValue;
    }

    public EntityLiving(World worldValue) {
        super(worldValue);
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        this.equipmentDropChances = new float[5];
        this.randomMobsId = 0;
        this.spawnBiome = null;
        String[] astring = astring1;
        this.spawnPosition = null;
        this.tasks =
                new EntityAITasks(
                        worldValue != null && worldValue.theProfiler != null
                                ? worldValue.theProfiler
                                : null);
        this.targetTasks =
                new EntityAITasks(
                        worldValue != null && worldValue.theProfiler != null
                                ? worldValue.theProfiler
                                : null);
        this.lookHelper = new EntityLookHelper(this);
        this.moveHelper = new EntityMoveHelper(this);
        this.jumpHelper = new EntityJumpHelper(this);
        this.bodyHelper = new EntityBodyHelper(this);
        this.navigator = this.getNewNavigator(worldValue);
        this.senses = new EntitySenses(this);
        int i = 0;

        while (i < this.equipmentDropChances.length) {
            this.equipmentDropChances[i] = 0.085F;
            i++;
            if (astring != null) {
                break;
            }
        }

        UUID uuid = this.getUniqueId();
        long j = uuid.getLeastSignificantBits();

        this.randomMobsId = (int) (j & 2147483647L);
    }

    public void onEntityUpdate() {
        super.onEntityUpdate();
        this.world.theProfiler.startSection("mobBaseTick");
        if (this.isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
            this.livingSoundTime = -this.getTalkInterval();
            this.playLivingSound();
        }

        this.world.theProfiler.endSection();
    }

    public void setCurrentItemOrArmor(int i, ItemStack itemStack) {
        this.equipment[i] = itemStack;
    }

    public void spawnExplosionParticle() {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.world.isRemote) {
            int i = 0;

            while (i < 20) {
                double d0 = this.rand.nextGaussian() * 0.02;
                double d1 = this.rand.nextGaussian() * 0.02;
                double d2 = this.rand.nextGaussian() * 0.02;
                double d3 = 10.0;
                this.world.spawnParticle(
                        EnumParticleTypes.EXPLOSION_NORMAL,
                        this.posX
                                + this.rand.nextFloat() * this.width * 2.0F
                                - this.width
                                - d0 * d3,
                        this.posY + this.rand.nextFloat() * this.height - d1 * d3,
                        this.posZ
                                + this.rand.nextFloat() * this.width * 2.0F
                                - this.width
                                - d2 * d3,
                        d0,
                        d1,
                        d2,
                        new int[0]);
                i++;
                if (astring != null) {
                    break;
                }
            }

            if (astring == null) {
                return;
            }
        }

        this.world.setEntityState(this, (byte) 20);
    }

    public final boolean interactFirst(PlayerEntity playerEntity) {
        if (this.getLeashed() && this.getLeashedToEntity() == playerEntity) {
            this.clearLeashed(true, !playerEntity.capabilities.isCreativeMode);
            return true;
        }

        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        if (itemStack != null && itemStack.getItem() == Items.lead && this.allowLeashing()) {
            if (!(this instanceof EntityTameable) || !((EntityTameable) this).isTamed()) {
                this.setLeashedToEntity(playerEntity, true);
                itemStack.stackSize--;
                return true;
            }

            if (((EntityTameable) this).isOwner(playerEntity)) {
                this.setLeashedToEntity(playerEntity, true);
                itemStack.stackSize--;
                return true;
            }
        }

        return this.interact(playerEntity) ? true : super.interactFirst(playerEntity);
    }

    public boolean replaceItemInInventory(int i, ItemStack itemStack) {
        int j;
        {
            String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
            if (i == 99) {
                j = 0;
                if (!(astring == null)) {

                    j = i - 100 + 1;
                    if (j < 0 || j >= this.equipment.length) {
                        return false;
                    }
                }
            } else {

                j = i - 100 + 1;
                if (j < 0 || j >= this.equipment.length) {
                    return false;
                }
            }
        }

        if (itemStack == null
                || getArmorPosition(itemStack) == j
                || j == 4 && itemStack.getItem() instanceof ItemBlock) {
            this.setCurrentItemOrArmor(j, itemStack);
            return true;
        } else {
            return false;
        }
    }

    public boolean isServerWorld() {
        return super.isServerWorld() && !this.isAIDisabled();
    }

    // region String decoding support

    // endregion

    protected void dropFewItems(boolean flag, int i) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        Item item = this.getDropItem();
        String[] astring = astring1;
        if (item != null) {
            int j = this.rand.nextInt(3);
            if (i > 0) {
                j += this.rand.nextInt(i + 1);
            }

            int k = 0;

            while (k < j) {
                this.dropItem(item, 1);
                k++;
                if (astring != null) {
                    break;
                }
            }
        }
    }

    public void handleHealthUpdate(byte b0) {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (b0 == 20) {
            this.spawnExplosionParticle();
            if (astring == null) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    public boolean isEntityInsideOpaqueBlock() {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.noClip) {
            return false;
        }

        BlockPosM blockPosM = new BlockPosM(0, 0, 0);
        int i = 0;

        while (i < 8) {
            double d0 = this.posX + ((i >> 0) % 2 - 0.5F) * this.width * 0.8F;
            double d1 = this.posY + ((i >> 1) % 2 - 0.5F) * 0.1F;
            double d2 = this.posZ + ((i >> 2) % 2 - 0.5F) * this.width * 0.8F;
            blockPosM.setXyz(d0, d1 + this.getEyeHeight(), d2);
            if (this.world.getBlockState(blockPosM).getBlock().isVisuallyOpaque()) {
                return true;
            }

            i++;
            if (astring != null) {
                break;
            }
        }

        return false;
    }

    public static int getArmorPosition(ItemStack itemStack) {
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)
                || itemStack.getItem() == Items.skull) {
            return 4;
        }

        if (itemStack.getItem() instanceof ItemArmor) {
            switch (((ItemArmor) itemStack.getItem()).armorType) {
                case 0:
                    return 4;
                case 1:
                    return 3;
                case 2:
                    return 2;
                case 3:
                    return 1;
            }
        }

        return 0;
    }

    protected int getExperiencePoints(PlayerEntity playerEntity) {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.experienceValue <= 0) {
            return this.experienceValue;
        }

        int experienceValueValue = this.experienceValue;
        ItemStack[] itemStackArray = this.getInventory();
        int j = 0;

        while (j < itemStackArray.length) {
            if (itemStackArray[j] != null && this.equipmentDropChances[j] <= 1.0F) {
                experienceValueValue += 1 + this.rand.nextInt(3);
            }

            j++;
            if (astring != null) {
                break;
            }
        }

        return experienceValueValue;
    }

    public int getVerticalFaceSpeed() {
        return 40;
    }

    protected void updateEquipmentIfNeeded(EntityItem entityItem) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        ItemStack itemStack = entityItem.getEntityItem();
        String[] astring = astring1;
        int armorPosition = getArmorPosition(itemStack);
        if (armorPosition > -1) {
            byte b0;
            ItemStack itemStackValue;
            b0 = 1;
            itemStackValue = this.getEquipmentInSlot(armorPosition);
            label85:
            if (itemStackValue != null) {
                if (armorPosition == 0) {
                    if (itemStack.getItem() instanceof SwordItem
                            && !(itemStackValue.getItem() instanceof SwordItem)) {
                        b0 = 1;
                        if (astring == null) {
                            break label85;
                        }
                    }

                    if (itemStack.getItem() instanceof SwordItem
                            && itemStackValue.getItem() instanceof SwordItem) {
                        {
                            SwordItem item = (SwordItem) itemStack.getItem();
                            SwordItem itemValue = (SwordItem) itemStackValue.getItem();
                            if (item.getDamageVsEntity() == itemValue.getDamageVsEntity()) {
                                b0 =
                                        (byte)
                                                (itemStack.getItemDamage()
                                                                        <= itemStackValue
                                                                                .getItemDamage()
                                                                && (!itemStack.hasTagCompound()
                                                                        || itemStackValue
                                                                                .hasTagCompound())
                                                        ? 0
                                                        : 1);
                                if (!(astring == null)) {

                                    b0 =
                                            (byte)
                                                    (item.getDamageVsEntity()
                                                                    > itemValue.getDamageVsEntity()
                                                            ? 1
                                                            : 0);
                                }
                            } else {

                                b0 =
                                        (byte)
                                                (item.getDamageVsEntity()
                                                                > itemValue.getDamageVsEntity()
                                                        ? 1
                                                        : 0);
                            }
                        }

                        if (astring == null) {
                            break label85;
                        }
                    }

                    if (itemStack.getItem() instanceof ItemBow
                            && itemStackValue.getItem() instanceof ItemBow) {
                        b0 =
                                (byte)
                                        (itemStack.hasTagCompound()
                                                        && !itemStackValue.hasTagCompound()
                                                ? 1
                                                : 0);
                        if (astring == null) {
                            break label85;
                        }
                    }

                    b0 = 0;
                    if (astring == null) {
                        break label85;
                    }
                }

                if (itemStack.getItem() instanceof ItemArmor
                        && !(itemStackValue.getItem() instanceof ItemArmor)) {
                    b0 = 1;
                    if (astring == null) {
                        break label85;
                    }
                }

                if (itemStack.getItem() instanceof ItemArmor
                        && itemStackValue.getItem() instanceof ItemArmor) {
                    {
                        ItemArmor itemValue2 = (ItemArmor) itemStack.getItem();
                        ItemArmor itemValue3 = (ItemArmor) itemStackValue.getItem();
                        if (itemValue2.damageReduceAmount == itemValue3.damageReduceAmount) {
                            b0 =
                                    (byte)
                                            (itemStack.getItemDamage()
                                                                    <= itemStackValue
                                                                            .getItemDamage()
                                                            && (!itemStack.hasTagCompound()
                                                                    || itemStackValue
                                                                            .hasTagCompound())
                                                    ? 0
                                                    : 1);
                            if (!(astring == null)) {

                                b0 =
                                        (byte)
                                                (itemValue2.damageReduceAmount
                                                                > itemValue3.damageReduceAmount
                                                        ? 1
                                                        : 0);
                            }
                        } else {

                            b0 =
                                    (byte)
                                            (itemValue2.damageReduceAmount
                                                            > itemValue3.damageReduceAmount
                                                    ? 1
                                                    : 0);
                        }
                    }

                    if (astring == null) {
                        break label85;
                    }
                }

                b0 = 0;
            }

            if (b0 != 0 && this.method_14d87da4146070fd903f(itemStack)) {
                if (itemStackValue != null
                        && this.rand.nextFloat() - 0.1F
                                < this.equipmentDropChances[armorPosition]) {
                    this.entityDropItem(itemStackValue, 0.0F);
                }

                if (itemStack.getItem() == Items.diamond && entityItem.getThrower() != null) {
                    PlayerEntity playerEntity =
                            this.world.getPlayerEntityByName(entityItem.getThrower());
                    if (playerEntity != null) {
                        playerEntity.triggerAchievement(AchievementList.diamondsToYou);
                    }
                }

                this.setCurrentItemOrArmor(armorPosition, itemStack);
                this.equipmentDropChances[armorPosition] = 2.0F;
                this.persistenceRequired = true;
                this.onItemPickup(entityItem, 1);
                entityItem.setDead();
            }
        }
    }

    public EntityJumpHelper getJumpHelper() {
        return this.jumpHelper;
    }

    public int getMaxFallHeight() {
        if (this.getAttackTarget() == null) {
            return 3;
        }

        int i = (int) (this.getHealth() - this.getMaxHealth() * 0.33F);
        i -= (3 - this.world.getDifficulty().getDifficultyId()) * 4;
        if (i < 0) {
            i = 0;
        }

        return i + 3;
    }

    protected final void updateEntityActionState() {
        this.NJ++;
        this.world.theProfiler.startSection("checkDespawn");
        this.despawnEntity();
        this.world.theProfiler.endSection();
        this.world.theProfiler.startSection("sensing");
        this.senses.clearSensingCache();
        this.world.theProfiler.endSection();
        this.world.theProfiler.startSection("targetSelector");
        this.targetTasks.onUpdateTasks();
        this.world.theProfiler.endSection();
        this.world.theProfiler.startSection("goalSelector");
        this.tasks.onUpdateTasks();
        this.world.theProfiler.endSection();
        this.world.theProfiler.startSection("navigation");
        this.navigator.onUpdateNavigation();
        this.world.theProfiler.endSection();
        this.world.theProfiler.startSection("mob tick");
        this.updateAITasks();
        this.world.theProfiler.endSection();
        this.world.theProfiler.startSection("controls");
        this.world.theProfiler.startSection("move");
        this.moveHelper.onUpdateMoveHelper();
        this.world.theProfiler.endStartSection("look");
        this.lookHelper.onUpdateLook();
        this.world.theProfiler.endStartSection("jump");
        this.jumpHelper.doJump();
        this.world.theProfiler.endSection();
        this.world.theProfiler.endSection();
    }

    // region String decoding support

    // endregion

    public EntityLookHelper getLookHelper() {
        return this.lookHelper;
    }

    public void setEquipmentDropChance(int i, float f) {
        this.equipmentDropChances[i] = f;
    }

    public void faceEntity(Entity entity, float f, float f1) {
        double d0;
        double d1;
        double d2;
        {
            String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
            d0 = entity.posX - this.posX;
            String[] astring = astring1;
            d1 = entity.posZ - this.posZ;
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                d2 =
                        livingEntity.posY
                                + livingEntity.getEyeHeight()
                                - (this.posY + this.getEyeHeight());
                if (!(astring == null)) {

                    d2 =
                            (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0
                                    - (this.posY + this.getEyeHeight());
                }
            } else {

                d2 =
                        (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0
                                - (this.posY + this.getEyeHeight());
            }
        }

        double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1);
        float f2 = (float) (Math.atan2(d1, d0) * 180.0 / Math.PI) - 90.0F;
        float f3 = (float) (-(Math.atan2(d2, d3) * 180.0 / Math.PI));
        this.rotationPitch = this.updateRotation(this.rotationPitch, f3, f1);
        this.rotationYaw = this.updateRotation(this.rotationYaw, f2, f);
    }

    private float updateRotation(float f, float f1, float f2) {
        float f3 = MathHelper.wrapAngleTo180(f1 - f);
        if (f3 > f2) {
            f3 = f2;
        }

        if (f3 < -f2) {
            f3 = -f2;
        }

        return f + f3;
    }

    public boolean allowLeashing() {
        return !this.getLeashed() && !(this instanceof IMob);
    }

    protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        float clampedAdditionalDifficulty = difficultyInstance.getClampedAdditionalDifficulty();
        String[] astring = astring1;
        if (this.getHeldItem() != null
                && this.rand.nextFloat() < 0.25F * clampedAdditionalDifficulty) {
            EnchantmentHelper.addRandomEnchantment(
                    this.rand,
                    this.getHeldItem(),
                    (int) (5.0F + clampedAdditionalDifficulty * this.rand.nextInt(18)));
        }

        int i = 0;

        while (i < 4) {
            ItemStack itemStack = this.getCurrentArmor(i);
            if (itemStack != null && this.rand.nextFloat() < 0.5F * clampedAdditionalDifficulty) {
                EnchantmentHelper.addRandomEnchantment(
                        this.rand,
                        itemStack,
                        (int) (5.0F + clampedAdditionalDifficulty * this.rand.nextInt(18)));
            }

            i++;
            if (astring != null) {
                break;
            }
        }
    }

    protected void setNoAI(boolean flag) {
        this.dataWatcher.updateObject(15, (byte) (flag ? 1 : 0));
    }

    private boolean isAIDisabled() {
        return this.dataWatcher.getWatchableObjectByte(15) != 0;
    }

    public static Item getArmorItemForSlot(int i, int j) {
        switch (i) {
            case 4:
                if (j == 0) {
                    return Items.leather_helmet;
                } else if (j == 1) {
                    return Items.golden_helmet;
                } else if (j == 2) {
                    return Items.chainmail_helmet;
                } else if (j == 3) {
                    return Items.iron_helmet;
                } else if (j == 4) {
                    return Items.diamond_helmet;
                }
            case 3:
                if (j == 0) {
                    return Items.leather_chestplate;
                } else if (j == 1) {
                    return Items.golden_chestplate;
                } else if (j == 2) {
                    return Items.chainmail_chestplate;
                } else if (j == 3) {
                    return Items.iron_chestplate;
                } else if (j == 4) {
                    return Items.diamond_chestplate;
                }
            case 2:
                if (j == 0) {
                    return Items.leather_leggings;
                } else if (j == 1) {
                    return Items.golden_leggings;
                } else if (j == 2) {
                    return Items.chainmail_leggings;
                } else if (j == 3) {
                    return Items.iron_leggings;
                } else if (j == 4) {
                    return Items.diamond_leggings;
                }
            case 1:
                if (j == 0) {
                    return Items.leather_boots;
                } else if (j == 1) {
                    return Items.golden_boots;
                } else if (j == 2) {
                    return Items.chainmail_boots;
                } else if (j == 3) {
                    return Items.iron_boots;
                } else if (j == 4) {
                    return Items.diamond_boots;
                }
            default:
                return null;
        }
    }

    private void nv() {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        label46:
        if (this.isLeashed && this.leashNBTTag != null) {
            if (this.leashNBTTag.hasKey("UUIDMost", 4) && this.leashNBTTag.hasKey("UUIDLeast", 4)) {
                UUID uuid =
                        new UUID(
                                this.leashNBTTag.getLong("UUIDMost"),
                                this.leashNBTTag.getLong("UUIDLeast"));

                for (java.util.Iterator<?> __recoveryIterator171_27969 =
                                (this.world.getEntitiesWithinAABB(
                                                LivingEntity.class,
                                                this.getEntityBoundingBox()
                                                        .expand(10.0, 10.0, 10.0)))
                                        .iterator();
                        (__recoveryIterator171_27969).hasNext(); ) {
                    LivingEntity bfdValue = (LivingEntity) (__recoveryIterator171_27969).next();
                    if (bfdValue.getUniqueId().equals(uuid)) {
                        this.leashedToEntity = bfdValue;
                        if (astring == null) {
                            break;
                        }
                    }

                    if (astring != null) {
                        break;
                    }
                }

                if (astring == null) {
                    break label46;
                }
            }

            if (this.leashNBTTag.hasKey(packedTextFragments[3], 99)
                    && this.leashNBTTag.hasKey(packedTextFragments[4], 99)
                    && this.leashNBTTag.hasKey(packedTextFragments[5], 99)) {
                BlockPos blockPos =
                        new BlockPos(
                                this.leashNBTTag.getInteger(packedTextFragments[6]),
                                this.leashNBTTag.getInteger(packedTextFragments[7]),
                                this.leashNBTTag.getInteger(packedTextFragments[8]));
                EntityLeashKnot bfdValue2 =
                        EntityLeashKnot.getKnotForPosition(this.world, blockPos);
                if (bfdValue2 == null) {
                    bfdValue2 = EntityLeashKnot.createKnot(this.world, blockPos);
                }

                this.leashedToEntity = bfdValue2;
                if (astring == null) {
                    break label46;
                }
            }

            this.clearLeashed(false, true);
        }

        this.leashNBTTag = null;
    }

    protected float func_110146_f(float f1, float f) {
        this.bodyHelper.updateRenderAngles();
        return f;
    }

    public ItemStack getEquipmentInSlot(int i) {
        return this.equipment[i];
    }

    public boolean getCanSpawnHere() {
        return true;
    }

    public void onLivingUpdate() {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        super.onLivingUpdate();
        String[] astring = astring1;
        this.world.theProfiler.startSection("looting");
        if (!this.world.isRemote
                && this.canPickUpLoot()
                && !this.dead
                && this.world.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
            for (java.util.Iterator<?> __recoveryIterator171_30395 =
                            (this.world.getEntitiesWithinAABB(
                                            EntityItem.class,
                                            this.getEntityBoundingBox().expand(1.0, 0.0, 1.0)))
                                    .iterator();
                    (__recoveryIterator171_30395).hasNext(); ) {
                EntityItem entityItem = (EntityItem) (__recoveryIterator171_30395).next();
                if (!entityItem.isDead
                        && entityItem.getEntityItem() != null
                        && !entityItem.cannotPickup()) {
                    this.updateEquipmentIfNeeded(entityItem);
                }

                if (astring != null) {
                    break;
                }
            }
        }

        this.world.theProfiler.endSection();
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        super.writeEntityToNBT(compoundTag);
        compoundTag.setBoolean("CanPickUpLoot", this.canPickUpLoot());
        String[] astring = astring1;
        compoundTag.setBoolean("PersistenceRequired", this.persistenceRequired);
        NBTTagList nbtTagList = new NBTTagList();
        int i = 0;

        while (i < this.equipment.length) {
            CompoundTag compoundTagValue = new CompoundTag();
            if (this.equipment[i] != null) {
                this.equipment[i].writeToNBT(compoundTagValue);
            }

            nbtTagList.appendTag(compoundTagValue);
            i++;
            if (astring != null) {
                break;
            }
        }

        compoundTag.setTag("Equipment", nbtTagList);
        NBTTagList nbtTagListValue = new NBTTagList();
        int j = 0;

        while (j < this.equipmentDropChances.length) {
            nbtTagListValue.appendTag(new NBTTagFloat(this.equipmentDropChances[j]));
            j++;
            if (astring != null) {
                break;
            }
        }

        compoundTag.setTag("DropChances", nbtTagListValue);
        compoundTag.setBoolean("Leashed", this.isLeashed);
        if (this.leashedToEntity != null) {
            CompoundTag compoundTagValue2;
            label28:
            {
                compoundTagValue2 = new CompoundTag();
                if (this.leashedToEntity instanceof LivingEntity) {
                    compoundTagValue2.setLong(
                            "UUIDMost",
                            this.leashedToEntity.getUniqueId().getMostSignificantBits());
                    compoundTagValue2.setLong(
                            "UUIDLeast",
                            this.leashedToEntity.getUniqueId().getLeastSignificantBits());
                    if (astring == null) {
                        break label28;
                    }
                }

                if (this.leashedToEntity instanceof EntityHanging) {
                    BlockPos blockPos = ((EntityHanging) this.leashedToEntity).getHangingPosition();
                    compoundTagValue2.setInteger(packedTextFragments[0], blockPos.getX());
                    compoundTagValue2.setInteger(packedTextFragments[1], blockPos.getY());
                    compoundTagValue2.setInteger(packedTextFragments[2], blockPos.getZ());
                }
            }

            compoundTag.setTag("Leash", compoundTagValue2);
        }

        if (this.isAIDisabled()) {
            compoundTag.setBoolean("NoAI", this.isAIDisabled());
        }
    }

    public Entity getLeashedToEntity() {
        return this.leashedToEntity;
    }

    public boolean canPickUpLoot() {
        return this.canPickUpLoot;
    }

    public PathNavigate getNavigator() {
        return this.navigator;
    }

    public EntitySenses getEntitySenses() {
        return this.senses;
    }

    public EntityMoveHelper getMoveHelper() {
        return this.moveHelper;
    }

    public boolean canBeSteered() {
        return false;
    }

    public void playLivingSound() {
        String text = this.getLivingSound();
        if (text != null) {
            this.playSound(text, this.getSoundVolume(), this.Jr());
        }
    }

    protected boolean canDespawn() {
        return true;
    }

    // region String decoding support

    // endregion

    protected void mM() {
        if (this.leashNBTTag != null) {
            this.nv();
        }

        if (this.isLeashed) {
            if (!this.isEntityAlive()) {
                this.clearLeashed(true, true);
            }

            if (this.leashedToEntity == null || this.leashedToEntity.isDead) {
                this.clearLeashed(true, true);
            }
        }
    }

    // region String decoding support

    // endregion

    protected void dropEquipment(boolean flag, int i) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        int j = 0;
        String[] astring = astring1;

        while (j < this.getInventory().length) {
            ItemStack itemStack = this.getEquipmentInSlot(j);
            int k = this.equipmentDropChances[j] > 1.0F ? 1 : 0;
            if (itemStack != null
                    && (flag || k != 0)
                    && this.rand.nextFloat() - i * 0.01F < this.equipmentDropChances[j]) {
                if (k == 0 && itemStack.isItemStackDamageable()) {
                    int l = Math.max(itemStack.getMaxDamage() - 25, 1);
                    int i1 = itemStack.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(l) + 1);
                    if (i1 > l) {
                        i1 = l;
                    }

                    if (i1 < 1) {
                        i1 = 1;
                    }

                    itemStack.setItemDamage(i1);
                }

                this.entityDropItem(itemStack, 0.0F);
            }

            j++;
            if (astring != null) {
                break;
            }
        }
    }

    public void clearLeashed(boolean flag, boolean flag1) {
        if (this.isLeashed) {
            this.isLeashed = false;
            this.leashedToEntity = null;
            if (!this.world.isRemote && flag1) {
                this.dropItem(Items.lead, 1);
            }

            if (!this.world.isRemote && flag && this.world instanceof WorldServer) {
                ((WorldServer) this.world)
                        .getEntityTracker()
                        .sendToAllTrackingEntity(
                                this, new S1BPacketEntityAttach(1, this, (Entity) null));
            }
        }
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        super.readEntityFromNBT(compoundTag);
        String[] astring = astring1;
        if (compoundTag.hasKey("CanPickUpLoot", 1)) {
            this.setCanPickUpLoot(compoundTag.getBoolean("CanPickUpLoot"));
        }

        this.persistenceRequired = compoundTag.getBoolean("PersistenceRequired");
        if (compoundTag.hasKey("Equipment", 9)) {
            NBTTagList nbtTagList = compoundTag.getTagList("Equipment", 10);
            int i = 0;

            while (i < this.equipment.length) {
                this.equipment[i] = ItemStack.loadItemStackFromNBT(nbtTagList.getCompoundTagAt(i));
                i++;
                if (astring != null) {
                    break;
                }
            }
        }

        if (compoundTag.hasKey("DropChances", 9)) {
            NBTTagList nbtTagListValue = compoundTag.getTagList("DropChances", 5);
            int j = 0;

            while (j < nbtTagListValue.tagCount()) {
                this.equipmentDropChances[j] = nbtTagListValue.getFloat(j);
                j++;
                if (astring != null) {
                    break;
                }
            }
        }

        this.isLeashed = compoundTag.getBoolean("Leashed");
        if (this.isLeashed && compoundTag.hasKey("Leash", 10)) {
            this.leashNBTTag = compoundTag.getCompoundTag("Leash");
        }

        this.setNoAI(compoundTag.getBoolean("NoAI"));
    }

    protected boolean interact(PlayerEntity playerEntity) {
        return false;
    }

    protected void despawnEntity() {
        Object object = null;
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        Object fieldValue = Reflector.getFieldValue(Reflector.Jg);
        String[] astring = astring1;
        Object fieldValueValue = Reflector.getFieldValue(Reflector.GS);
        if (this.persistenceRequired) {
            this.NJ = 0;
            if (astring == null) {
                return;
            }
        }

        if ((this.NJ & 31) == 31
                && (object = Reflector.call(Reflector.Il, new Object[] {this})) != fieldValue) {
            if (object == fieldValueValue) {
                this.NJ = 0;
                if (astring == null) {
                    return;
                }
            }

            this.setDead();
            if (astring == null) {
                return;
            }
        }

        PlayerEntity playerEntity = this.world.getClosestPlayerToEntity(this, -1.0);
        if (playerEntity != null) {
            double deltaPosX = playerEntity.posX - this.posX;
            double deltaPosY = playerEntity.posY - this.posY;
            double deltaPosZ = playerEntity.posZ - this.posZ;
            double d3 = deltaPosX * deltaPosX + deltaPosY * deltaPosY + deltaPosZ * deltaPosZ;
            if (this.canDespawn() && d3 > 16384.0) {
                this.setDead();
            }

            if (this.NJ > 600 && this.rand.nextInt(800) == 0 && d3 > 1024.0 && this.canDespawn()) {
                this.setDead();
                if (astring == null) {
                    return;
                }
            }

            if (d3 < 1024.0) {
                this.NJ = 0;
            }
        }
    }

    public boolean getLeashed() {
        return this.isLeashed;
    }

    public ItemStack getHeldItem() {
        return this.equipment[0];
    }

    public void onUpdate() {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (OptiFineConfig.isSmoothWorld() && this.canSkipUpdate()) {
            this.onUpdateMinimal();
            if (astring == null) {
                return;
            }
        }

        super.onUpdate();
        if (!this.world.isRemote) {
            this.mM();
        }
    }

    public static boolean canVisitAllClickGuiPanels() {
        boolean flag = isLegacyClickGuiPanelLimitEnabled();
        return !flag;
    }

    public boolean isNoDespawnRequired() {
        return this.persistenceRequired;
    }

    public void setLeashedToEntity(Entity bfdValue, boolean flag) {
        this.isLeashed = true;
        this.leashedToEntity = bfdValue;
        if (!this.world.isRemote && flag && this.world instanceof WorldServer) {
            ((WorldServer) this.world)
                    .getEntityTracker()
                    .sendToAllTrackingEntity(
                            this, new S1BPacketEntityAttach(1, this, this.leashedToEntity));
        }
    }

    public static boolean isLegacyClickGuiPanelLimitEnabled() {
        return aJj;
    }

    public void setAttackTarget(LivingEntity beZValue) {
        this.attackTarget = beZValue;
        Reflector.callVoid(Reflector.IB, new Object[] {this, beZValue});
    }

    protected PathNavigate getNewNavigator(World worldValue) {
        return new PathNavigateGround(this, worldValue);
    }

    private void onUpdateMinimal() {
        this.NJ++;
        if (this instanceof MonsterEntity) {
            float brightness = this.getBrightness(1.0F);
            if (brightness > 0.5F) {
                this.NJ += 2;
            }
        }

        this.despawnEntity();
    }

    static {
        net.minecraft.entity.EntityLiving.packedTextFragments = new String[11];
        net.minecraft.entity.EntityLiving.packedTextFragments[0] = new String("X");
        net.minecraft.entity.EntityLiving.packedTextFragments[1] = new String("Y");
        net.minecraft.entity.EntityLiving.packedTextFragments[2] = new String("Z");
        net.minecraft.entity.EntityLiving.packedTextFragments[3] = new String("X");
        net.minecraft.entity.EntityLiving.packedTextFragments[4] = new String("Y");
        net.minecraft.entity.EntityLiving.packedTextFragments[5] = new String("Z");
        net.minecraft.entity.EntityLiving.packedTextFragments[6] = new String("X");
        net.minecraft.entity.EntityLiving.packedTextFragments[7] = new String("Y");
        net.minecraft.entity.EntityLiving.packedTextFragments[8] = new String("Z");
        setLegacyClickGuiPanelLimit(false);
    }

    public ItemStack getCurrentArmor(int i) {
        return this.equipment[i + 1];
    }

    public int L() {
        return 4;
    }

    public IEntityLivingData onInitialSpawn(
            DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
        this.getEntityAttribute(SharedMonsterAttributes.followRange)
                .applyModifier(
                        new AttributeModifier(
                                "Random spawn bonus", this.rand.nextGaussian() * 0.05, 1));
        return iEntityLivingData;
    }

    protected Item getDropItem() {
        return null;
    }

    public void setAIMoveSpeed(float f) {
        super.setAIMoveSpeed(f);
        this.setMoveForward(f);
    }

    public void enablePersistence() {
        this.persistenceRequired = true;
    }

    public int getTalkInterval() {
        return 80;
    }

    public void setMoveForward(float aIBValue) {
        this.moveForward = aIBValue;
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.rand.nextFloat() < 0.15F * difficultyInstance.getClampedAdditionalDifficulty()) {
            int i = this.rand.nextInt(2);
            float f = this.world.getDifficulty() == EnumDifficulty.HARD ? 0.1F : 0.25F;
            if (this.rand.nextFloat() < 0.095F) {
                i++;
            }

            if (this.rand.nextFloat() < 0.095F) {
                i++;
            }

            if (this.rand.nextFloat() < 0.095F) {
                i++;
            }

            int j = 3;

            while (j >= 0) {
                ItemStack itemStack = this.getCurrentArmor(j);
                if (j < 3 && this.rand.nextFloat() < f && astring == null) {
                    break;
                }

                if (itemStack == null) {
                    Item item = getArmorItemForSlot(j + 1, i);
                    if (item != null) {
                        this.setCurrentItemOrArmor(j + 1, new ItemStack(item));
                    }
                }

                j--;
                if (astring != null) {
                    break;
                }
            }
        }
    }

    public ItemStack[] getInventory() {
        return this.equipment;
    }

    public void setCanPickUpLoot(boolean aJSValue) {
        this.canPickUpLoot = aJSValue;
    }

    public boolean handleLavaMovement() {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this)
                && this.world.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()
                && !this.world.isAnyLiquid(this.getEntityBoundingBox());
    }

    protected boolean method_14d87da4146070fd903f(ItemStack itemStack) {
        return true;
    }
}
