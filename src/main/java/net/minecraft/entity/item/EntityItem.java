package net.minecraft.entity.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class EntityItem extends Entity {
    private String thrower;
    public float hoverStart;

    private int delayBeforeCanPickup;
    private int health = 5;

    private int age;
    private static Logger logger;
    private String owner;

    public void setNoDespawn() {
        this.age = -6000;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        compoundTag.setShort("Health", (byte) this.health);
        compoundTag.setShort("Age", (short) this.age);
        compoundTag.setShort("PickupDelay", (short) this.delayBeforeCanPickup);
        if (this.getThrower() != null) {
            compoundTag.setString("Thrower", this.thrower);
        }

        if (this.jvm171_d_9c4d0aa0a2e8b5() != null) {
            compoundTag.setString("Owner", this.owner);
        }

        if (this.getEntityItem() != null) {
            compoundTag.setTag("Item", this.getEntityItem().writeToNBT(new CompoundTag()));
        }
    }

    public int getAge() {
        return this.age;
    }

    public void setThrower(String bbaValue) {
        this.thrower = bbaValue;
    }

    public boolean canAttackWithItem() {
        return false;
    }

    protected void dealFireDamage(int i) {
        this.attackEntityFrom(DamageSource.inFire, i);
    }

    public void setEntityItemStack(ItemStack itemStack) {
        this.getDataWatcher().updateObject(10, itemStack);
        this.getDataWatcher().setObjectWatched(10);
    }

    public String getName() {
        return this.jvm171_d_45ffeb83d14f3c()
                ? this.getCustomNameTag()
                : StatCollector.translateToLocal(
                        "item." + this.getEntityItem().getUnlocalizedName());
    }

    public String getThrower() {
        return this.thrower;
    }

    private void searchForOtherItemsNearby() {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        Iterator iterator =
                this.world
                        .getEntitiesWithinAABB(
                                EntityItem.class, this.getEntityBoundingBox().expand(0.5, 0.0, 0.5))
                        .iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            EntityItem entityItem = (EntityItem) iterator.next();
            this.combineItems(entityItem);
            if (!flag) {
                break;
            }
        }
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        this.health = compoundTag.getShort("Health") & 255;
        this.age = compoundTag.getShort("Age");
        if (compoundTag.hasKey("PickupDelay")) {
            this.delayBeforeCanPickup = compoundTag.getShort("PickupDelay");
        }

        if (compoundTag.hasKey("Owner")) {
            this.owner = compoundTag.getString("Owner");
        }

        if (compoundTag.hasKey("Thrower")) {
            this.thrower = compoundTag.getString("Thrower");
        }

        CompoundTag compoundTagValue = compoundTag.getCompoundTag("Item");
        this.setEntityItemStack(ItemStack.loadItemStackFromNBT(compoundTagValue));
        if (this.getEntityItem() == null) {
            this.setDead();
        }
    }

    public String jvm171_d_9c4d0aa0a2e8b5() {
        return this.owner;
    }

    private boolean combineItems(EntityItem entityItem) {
        if (entityItem == this) {
            return false;
        }

        if (entityItem.isEntityAlive() && this.isEntityAlive()) {
            ItemStack itemStack = this.getEntityItem();
            ItemStack itemStackValue = entityItem.getEntityItem();
            if (this.delayBeforeCanPickup == 32767 || entityItem.delayBeforeCanPickup == 32767) {
                return false;
            }

            if (this.age != -32768 && entityItem.age != -32768) {
                if (itemStackValue.getItem() != itemStack.getItem()) {
                    return false;
                }

                if (itemStackValue.hasTagCompound() ^ itemStack.hasTagCompound()) {
                    return false;
                }

                if (itemStackValue.hasTagCompound()
                        && !itemStackValue.getTag().equals(itemStack.getTag())) {
                    return false;
                }

                if (itemStackValue.getItem() == null) {
                    return false;
                }

                if (itemStackValue.getItem().getHasSubtypes()
                        && itemStackValue.getItemDamage() != itemStack.getItemDamage()) {
                    return false;
                }

                if (itemStackValue.stackSize < itemStack.stackSize) {
                    return entityItem.combineItems(this);
                }

                if (itemStackValue.stackSize + itemStack.stackSize
                        > itemStackValue.getMaxStackSize()) {
                    return false;
                }

                itemStackValue.stackSize = itemStackValue.stackSize + itemStack.stackSize;
                entityItem.delayBeforeCanPickup =
                        Math.max(entityItem.delayBeforeCanPickup, this.delayBeforeCanPickup);
                entityItem.age = Math.min(entityItem.age, this.age);
                entityItem.setEntityItemStack(itemStackValue);
                this.setDead();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void onCollideWithPlayer(PlayerEntity playerEntity) {
        if (!this.world.isRemote) {
            ItemStack itemStack = this.getEntityItem();
            int stackSize = itemStack.stackSize;
            if (this.delayBeforeCanPickup == 0
                    && (this.owner == null
                            || 6000 - this.age <= 200
                            || this.owner.equals(playerEntity.getName()))
                    && playerEntity.inventory.addItemStackToInventory(itemStack)) {
                if (itemStack.getItem() == Item.getItemFromBlock(Blocks.log)) {
                    playerEntity.triggerAchievement(AchievementList.mineWood);
                }

                if (itemStack.getItem() == Item.getItemFromBlock(Blocks.log2)) {
                    playerEntity.triggerAchievement(AchievementList.mineWood);
                }

                if (itemStack.getItem() == Items.leather) {
                    playerEntity.triggerAchievement(AchievementList.killCow);
                }

                if (itemStack.getItem() == Items.diamond) {
                    playerEntity.triggerAchievement(AchievementList.diamonds);
                }

                if (itemStack.getItem() == Items.blaze_rod) {
                    playerEntity.triggerAchievement(AchievementList.blazeRod);
                }

                if (itemStack.getItem() == Items.diamond && this.getThrower() != null) {
                    PlayerEntity playerEntityValue =
                            this.world.getPlayerEntityByName(this.getThrower());
                    if (playerEntityValue != null && playerEntityValue != playerEntity) {
                        playerEntityValue.triggerAchievement(AchievementList.diamondsToYou);
                    }
                }

                if (!this.isSlient()) {
                    this.world.playSoundAtEntity(
                            playerEntity,
                            "random.pop",
                            0.2F,
                            ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                }

                playerEntity.onItemPickup(this, stackSize);
                if (itemStack.stackSize <= 0) {
                    this.setDead();
                }
            }
        }
    }

    public EntityItem(World worldValue, double d0, double d1, double d2) {
        super(worldValue);
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0);
        this.setSize(0.25F, 0.25F);
        this.setPosition(d0, d1, d2);
        this.rotationYaw = (float) (Math.random() * 360.0);
        this.motionX = (float) (Math.random() * 0.2F - 0.1F);
        this.motionY = 0.2F;
        this.motionZ = (float) (Math.random() * 0.2F - 0.1F);
    }

    public void setPickupDelay(int bbcValue) {
        this.delayBeforeCanPickup = bbcValue;
    }

    public EntityItem(World worldValue) {
        super(worldValue);
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0);
        this.setSize(0.25F, 0.25F);
        this.setEntityItemStack(new ItemStack(Blocks.air, 0));
    }

    public void setOwner(String bbeValue) {
        this.owner = bbeValue;
    }

    static {
        net.minecraft.entity.item.EntityItem.logger = LogManager.getLogger();
    }

    public boolean cannotPickup() {
        return this.delayBeforeCanPickup > 0;
    }

    public ItemStack getEntityItem() {
        ItemStack itemStack = this.getDataWatcher().getWatchableObjectItemStack(10);
        return itemStack == null ? new ItemStack(Blocks.stone) : itemStack;
    }

    protected void entityInit() {
        this.getDataWatcher().addObjectByDataType(10, 5);
    }

    public void setDefaultPickupDelay() {
        this.delayBeforeCanPickup = 10;
    }

    public void setAgeToCreativeDespawnTime() {
        this.age = 4800;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        if (this.getEntityItem() != null
                && this.getEntityItem().getItem() == Items.nether_star
                && damageSource.isExplosion()) {
            return false;
        }

        this.setBeenAttacked();
        this.health = (int) (this.health - f);
        if (this.health <= 0) {
            this.setDead();
        }

        return false;
    }

    public EntityItem(World worldValue, double d0, double d1, double d2, ItemStack itemStack) {
        this(worldValue, d0, d1, d2);
        this.setEntityItemStack(itemStack);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public void setInfinitePickupDelay() {
        this.delayBeforeCanPickup = 32767;
    }

    public void onUpdate() {
        boolean flag = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        if (this.getEntityItem() == null) {
            this.setDead();
            if (flag) {
                return;
            }
        }

        super.onUpdate();
        if (this.delayBeforeCanPickup > 0 && this.delayBeforeCanPickup != 32767) {
            this.delayBeforeCanPickup--;
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.04F;
        this.noClip =
                this.pushOutOfBlocks(
                        this.posX,
                        (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0,
                        this.posZ);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        int i =
                (int) this.prevPosX == (int) this.posX
                                && (int) this.prevPosY == (int) this.posY
                                && (int) this.prevPosZ == (int) this.posZ
                        ? 0
                        : 1;
        if (i != 0 || this.ticksExisted % 25 == 0) {
            if (this.world.getBlockState(new BlockPos(this)).getBlock().getMaterial()
                    == Material.lava) {
                this.motionY = 0.2F;
                this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
                this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
                this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
            }

            if (!this.world.isRemote) {
                this.searchForOtherItemsNearby();
            }
        }

        float f = 0.98F;
        if (this.onGround) {
            f =
                    this.world
                                    .getBlockState(
                                            new BlockPos(
                                                    MathHelper.floor(this.posX),
                                                    MathHelper.floor(
                                                                    this.getEntityBoundingBox()
                                                                            .minY)
                                                            - 1,
                                                    MathHelper.floor(this.posZ)))
                                    .getBlock()
                                    .slipperiness
                            * 0.98F;
        }

        this.motionX *= f;
        this.motionY *= 0.98F;
        this.motionZ *= f;
        if (this.onGround) {
            this.motionY *= -0.5;
        }

        if (this.age != -32768) {
            this.age++;
        }

        this.handleWaterMovement();
        if (!this.world.isRemote && this.age >= 6000) {
            this.setDead();
        }
    }

    public void setNoPickupDelay() {
        this.delayBeforeCanPickup = 0;
    }

    public void travelToDimension(int i) {
        super.travelToDimension(i);
        if (!this.world.isRemote) {
            this.searchForOtherItemsNearby();
        }
    }

    public void func_174870_v() {
        this.setInfinitePickupDelay();
        this.age = 5999;
    }

    public boolean handleWaterMovement() {
        boolean flag = EntityMinecartEnumMinecartType.d();
        if (this.world.handleMaterialAcceleration(
                this.getEntityBoundingBox(), Material.water, this)) {
            if (!this.inWater && !this.firstUpdate) {
                this.resetHeight();
            }

            this.inWater = true;
            if (!flag) {
                return this.inWater;
            }
        }

        this.inWater = false;
        return this.inWater;
    }
}
