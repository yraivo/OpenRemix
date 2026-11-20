package net.minecraft.entity.item;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Rotations;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.List;

public class EntityArmorStand extends LivingEntity {
    private static Rotations DEFAULT_RIGHTLEG_ROTATION;
    private Rotations bodyRotation;

    private static Rotations DEFAULT_HEAD_ROTATION;
    private static Rotations DEFAULT_BODY_ROTATION;
    private boolean canInteract;
    private static Rotations DEFAULT_LEFTLEG_ROTATION;
    private long punchCooldown;

    private static Rotations DEFAULT_RIGHTARM_ROTATION;

    private static Rotations DEFAULT_LEFTARM_ROTATION;
    private Rotations rightLegRotation;
    private int disabledSlots;
    private Rotations rightArmRotation;
    private Rotations leftArmRotation;
    private Rotations headRotation;
    private Rotations leftLegRotation;
    private final ItemStack[] contents = new ItemStack[5];

    public Rotations getHeadRotation() {
        return this.headRotation;
    }

    public boolean isImmuneToExplosions() {
        return this.isInvisible();
    }

    public boolean hasNoBasePlate() {
        return (this.dataWatcher.getWatchableObjectByte(10) & 8) != 0;
    }

    public Rotations getLeftArmRotation() {
        return this.leftArmRotation;
    }

    public boolean isChild() {
        return this.isSmall();
    }

    private void dropBlock() {
        Block.spawnAsEntity(this.world, new BlockPos(this), new ItemStack(Items.armor_stand));
        this.dropContents();
    }

    private void method_19fd2cb92de7572f3a6f(boolean flag) {
        byte b0;
        {
            boolean flag2 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
            b0 = this.dataWatcher.getWatchableObjectByte(10);
            boolean flag1 = flag2;
            if (flag) {
                b0 = (byte) (b0 | 8);
                if (!(flag1)) {

                    b0 = (byte) (b0 & -9);
                }
            } else {

                b0 = (byte) (b0 & -9);
            }
        }

        this.dataWatcher.updateObject(10, b0);
    }

    public void setCurrentItemOrArmor(int p171_0, ItemStack itemStack) {
        this.contents[p171_0] = itemStack;
    }

    protected void updatePotionMetadata() {
        this.setInvisible(this.canInteract);
    }

    private CompoundTag readPoseFromNBT() {
        CompoundTag compoundTag = new CompoundTag();
        if (!net.minecraft.entity.item.EntityArmorStand.DEFAULT_HEAD_ROTATION.equals(this.headRotation)) {
            compoundTag.setTag("Head", this.headRotation.writeToNBT());
        }

        if (!net.minecraft.entity.item.EntityArmorStand.DEFAULT_BODY_ROTATION.equals(this.bodyRotation)) {
            compoundTag.setTag("Body", this.bodyRotation.writeToNBT());
        }

        if (!net.minecraft.entity.item.EntityArmorStand.DEFAULT_LEFTARM_ROTATION.equals(this.leftArmRotation)) {
            compoundTag.setTag("LeftArm", this.leftArmRotation.writeToNBT());
        }

        if (!net.minecraft.entity.item.EntityArmorStand.DEFAULT_RIGHTARM_ROTATION.equals(this.rightArmRotation)) {
            compoundTag.setTag("RightArm", this.rightArmRotation.writeToNBT());
        }

        if (!net.minecraft.entity.item.EntityArmorStand.DEFAULT_LEFTLEG_ROTATION.equals(this.leftLegRotation)) {
            compoundTag.setTag("LeftLeg", this.leftLegRotation.writeToNBT());
        }

        if (!net.minecraft.entity.item.EntityArmorStand.DEFAULT_RIGHTLEG_ROTATION.equals(this.rightLegRotation)) {
            compoundTag.setTag("RightLeg", this.rightLegRotation.writeToNBT());
        }

        return compoundTag;
    }

    public boolean interactAt(PlayerEntity playerEntity, Vec3 vec3) {
        boolean flag = EntityMinecartEnumMinecartType.d();
        if (!this.world.isRemote && !playerEntity.isSpectator()) {
            byte b0;
            ItemStack itemStack;
            int i;
            b0 = 0;
            itemStack = playerEntity.getCurrentEquippedItem();
            i = itemStack != null ? 1 : 0;
            label144:
            if (i != 0 && itemStack.getItem() instanceof ItemArmor) {
                ItemArmor item = (ItemArmor) itemStack.getItem();
                if (item.armorType == 3) {
                    b0 = 1;
                    if (!flag) {
                        break label144;
                    }
                }

                if (item.armorType == 2) {
                    b0 = 2;
                    if (!flag) {
                        break label144;
                    }
                }

                if (item.armorType == 1) {
                    b0 = 3;
                    if (!flag) {
                        break label144;
                    }
                }

                if (item.armorType == 0) {
                    b0 = 4;
                }
            }

            if (i != 0
                    && (itemStack.getItem() == Items.skull
                            || itemStack.getItem() == Item.getItemFromBlock(Blocks.pumpkin))) {
                b0 = 4;
            }

            byte b1;
            label152:
            {
                b1 = 0;
                boolean small = this.isSmall();
                double d3 = small ? vec3.y * 2.0 : vec3.y;
                if (d3 >= 0.1 && d3 < 0.1 + (small ? 0.8 : 0.45) && this.contents[1] != null) {
                    b1 = 1;
                    if (!flag) {
                        break label152;
                    }
                }

                if (d3 >= 0.9 + (small ? 0.3 : 0.0)
                        && d3 < 0.9 + (small ? 1.0 : 0.7)
                        && this.contents[3] != null) {
                    b1 = 3;
                    if (!flag) {
                        break label152;
                    }
                }

                if (d3 >= 0.4 && d3 < 0.4 + (small ? 1.0 : 0.8) && this.contents[2] != null) {
                    b1 = 2;
                    if (!flag) {
                        break label152;
                    }
                }

                if (d3 >= 1.6 && this.contents[4] != null) {
                    b1 = 4;
                }
            }

            int j = this.contents[b1] != null ? 1 : 0;
            if ((this.disabledSlots & 1 << b1) != 0 || (this.disabledSlots & 1 << b0) != 0) {
                b1 = b0;
                if ((this.disabledSlots & 1 << b0) != 0) {
                    if ((this.disabledSlots & 1) != 0) {
                        return true;
                    }

                    b1 = 0;
                }
            }

            if (i != 0 && b0 == 0 && !this.getShowArms()) {
                return true;
            }

            if (i != 0) {
                this.method_e9d2cc1e3595d38b3ac0(playerEntity, b0);
                if (!flag) {
                    return true;
                }
            }

            if (j != 0) {
                this.method_e9d2cc1e3595d38b3ac0(playerEntity, b1);
            }

            return true;
        } else {
            return true;
        }
    }

    protected void collideWithEntity(Entity entity) {}

    public void setLeftArmRotation(Rotations bdfValue) {
        this.leftArmRotation = bdfValue;
        this.dataWatcher.updateObject(13, bdfValue);
    }

    public boolean replaceItemInInventory(int i, ItemStack itemStack) {
        int j;
        {
            boolean flag = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
            if (i == 99) {
                j = 0;
                if (!(flag)) {

                    j = i - 100 + 1;
                    if (j < 0 || j >= this.contents.length) {
                        return false;
                    }
                }
            } else {

                j = i - 100 + 1;
                if (j < 0 || j >= this.contents.length) {
                    return false;
                }
            }
        }

        if (itemStack == null
                || EntityLiving.getArmorPosition(itemStack) == j
                || j == 4 && itemStack.getItem() instanceof ItemBlock) {
            this.setCurrentItemOrArmor(j, itemStack);
            return true;
        } else {
            return false;
        }
    }

    public float getEyeHeight() {
        return this.isChild() ? this.height * 0.5F : this.height * 0.9F;
    }

    public void onUpdate() {
        super.onUpdate();
        Rotations watchableObjectRotations = this.dataWatcher.getWatchableObjectRotations(11);
        if (!this.headRotation.equals(watchableObjectRotations)) {
            this.setHeadRotation(watchableObjectRotations);
        }

        Rotations watchableObjectRotationsValue = this.dataWatcher.getWatchableObjectRotations(12);
        if (!this.bodyRotation.equals(watchableObjectRotationsValue)) {
            this.setBodyRotation(watchableObjectRotationsValue);
        }

        Rotations watchableObjectRotationsValue2 = this.dataWatcher.getWatchableObjectRotations(13);
        if (!this.leftArmRotation.equals(watchableObjectRotationsValue2)) {
            this.setLeftArmRotation(watchableObjectRotationsValue2);
        }

        Rotations watchableObjectRotationsValue3 = this.dataWatcher.getWatchableObjectRotations(14);
        if (!this.rightArmRotation.equals(watchableObjectRotationsValue3)) {
            this.setRightArmRotation(watchableObjectRotationsValue3);
        }

        Rotations watchableObjectRotationsValue4 = this.dataWatcher.getWatchableObjectRotations(15);
        if (!this.leftLegRotation.equals(watchableObjectRotationsValue4)) {
            this.setLeftLegRotation(watchableObjectRotationsValue4);
        }

        Rotations watchableObjectRotationsValue5 = this.dataWatcher.getWatchableObjectRotations(16);
        if (!this.rightLegRotation.equals(watchableObjectRotationsValue5)) {
            this.setRightLegRotation(watchableObjectRotationsValue5);
        }
    }

    public void onKillCommand() {
        this.setDead();
    }

    public void setInvisible(boolean bcXValue) {
        this.canInteract = bcXValue;
        super.setInvisible(bcXValue);
    }

    private void method_ffaa23ce3617e7a7bd47(boolean flag) {
        byte b0;
        {
            boolean flag2 = EntityMinecartEnumMinecartType.d();
            b0 = this.dataWatcher.getWatchableObjectByte(10);
            boolean flag1 = flag2;
            if (flag) {
                b0 = (byte) (b0 | 1);
                if (!(!flag1)) {

                    b0 = (byte) (b0 & -2);
                }
            } else {

                b0 = (byte) (b0 & -2);
            }
        }

        this.dataWatcher.updateObject(10, b0);
    }

    public Rotations getBodyRotation() {
        return this.bodyRotation;
    }

    protected void nr() {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        List entitiesWithinAABBExcludingEntity =
                this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
        boolean flag = flag1;
        if (entitiesWithinAABBExcludingEntity != null
                && !entitiesWithinAABBExcludingEntity.isEmpty()) {
            int i = 0;

            while (i < entitiesWithinAABBExcludingEntity.size()) {
                Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(i);
                if (entity instanceof EntityMinecart
                        && ((EntityMinecart) entity).getMinecartType()
                                == EntityMinecartEnumMinecartType.RIDEABLE
                        && this.getDistanceSqToEntity(entity) <= 0.2) {
                    entity.applyEntityCollision(this);
                }

                i++;
                if (!flag) {
                    break;
                }
            }
        }
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        super.readEntityFromNBT(compoundTag);
        boolean flag = flag1;
        if (compoundTag.hasKey("Equipment", 9)) {
            NBTTagList nbtTagList = compoundTag.getTagList("Equipment", 10);
            int i = 0;

            while (i < this.contents.length) {
                this.contents[i] = ItemStack.loadItemStackFromNBT(nbtTagList.getCompoundTagAt(i));
                i++;
                if (!flag) {
                    break;
                }
            }
        }

        this.setInvisible(compoundTag.getBoolean("Invisible"));
        this.method_ffaa23ce3617e7a7bd47(compoundTag.getBoolean("Small"));
        this.method_de671d25b3ee1d5e26f3(compoundTag.getBoolean("ShowArms"));
        this.disabledSlots = compoundTag.getInteger("DisabledSlots");
        this.method_59cdaf2443ad553fadda(compoundTag.getBoolean("NoGravity"));
        this.method_19fd2cb92de7572f3a6f(compoundTag.getBoolean("NoBasePlate"));
        this.noClip = this.hasNoGravity();
        CompoundTag compoundTagValue = compoundTag.getCompoundTag("Pose");
        this.writePoseToNBT(compoundTagValue);
    }

    private void method_de671d25b3ee1d5e26f3(boolean flag) {
        byte b0;
        {
            boolean flag2 = EntityMinecartEnumMinecartType.d();
            b0 = this.dataWatcher.getWatchableObjectByte(10);
            boolean flag1 = flag2;
            if (flag) {
                b0 = (byte) (b0 | 4);
                if (!(!flag1)) {

                    b0 = (byte) (b0 & -5);
                }
            } else {

                b0 = (byte) (b0 & -5);
            }
        }

        this.dataWatcher.updateObject(10, b0);
    }

    public EntityArmorStand(World worldValue, double p171_1, double p171_2, double p171_3) {
        this(worldValue);
        this.setPosition(p171_1, p171_2, p171_3);
    }

    private void method_59cdaf2443ad553fadda(boolean flag) {
        byte b0;
        {
            boolean flag2 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
            b0 = this.dataWatcher.getWatchableObjectByte(10);
            boolean flag1 = flag2;
            if (flag) {
                b0 = (byte) (b0 | 2);
                if (!(flag1)) {

                    b0 = (byte) (b0 & -3);
                }
            } else {

                b0 = (byte) (b0 & -3);
            }
        }

        this.dataWatcher.updateObject(10, b0);
    }

    public void setRightArmRotation(Rotations bdeValue) {
        this.rightArmRotation = bdeValue;
        this.dataWatcher.updateObject(14, bdeValue);
    }

    public Rotations getLeftLegRotation() {
        return this.leftLegRotation;
    }

    private void damageArmorStand(float f) {
        boolean flag1 = EntityMinecartEnumMinecartType.d();
        float health = this.getHealth();
        boolean flag = flag1;
        health -= f;
        if (health <= 0.5F) {
            this.dropContents();
            this.setDead();
            if (!flag) {
                return;
            }
        }

        this.setHealth(health);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(10, (byte) 0);
        this.dataWatcher.addObject(11, net.minecraft.entity.item.EntityArmorStand.DEFAULT_HEAD_ROTATION);
        this.dataWatcher.addObject(12, net.minecraft.entity.item.EntityArmorStand.DEFAULT_BODY_ROTATION);
        this.dataWatcher.addObject(13, net.minecraft.entity.item.EntityArmorStand.DEFAULT_LEFTARM_ROTATION);
        this.dataWatcher.addObject(14, net.minecraft.entity.item.EntityArmorStand.DEFAULT_RIGHTARM_ROTATION);
        this.dataWatcher.addObject(15, net.minecraft.entity.item.EntityArmorStand.DEFAULT_LEFTLEG_ROTATION);
        this.dataWatcher.addObject(16, net.minecraft.entity.item.EntityArmorStand.DEFAULT_RIGHTLEG_ROTATION);
    }

    public void setHeadRotation(Rotations bdgValue) {
        this.headRotation = bdgValue;
        this.dataWatcher.updateObject(11, bdgValue);
    }

    public boolean canBePushed() {
        return false;
    }

    static {
        net.minecraft.entity.item.EntityArmorStand.DEFAULT_HEAD_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
        net.minecraft.entity.item.EntityArmorStand.DEFAULT_BODY_ROTATION = new Rotations(0.0F, 0.0F, 0.0F);
        net.minecraft.entity.item.EntityArmorStand.DEFAULT_LEFTARM_ROTATION = new Rotations(-10.0F, 0.0F, -10.0F);
        net.minecraft.entity.item.EntityArmorStand.DEFAULT_RIGHTARM_ROTATION = new Rotations(-15.0F, 0.0F, 10.0F);
        net.minecraft.entity.item.EntityArmorStand.DEFAULT_LEFTLEG_ROTATION = new Rotations(-1.0F, 0.0F, -1.0F);
        net.minecraft.entity.item.EntityArmorStand.DEFAULT_RIGHTLEG_ROTATION = new Rotations(1.0F, 0.0F, 1.0F);
    }

    public boolean hasNoGravity() {
        return (this.dataWatcher.getWatchableObjectByte(10) & 2) != 0;
    }

    private void playParticles() {
        if (this.world instanceof WorldServer) {
            ((WorldServer) this.world)
                    .spawnParticle(
                            EnumParticleTypes.BLOCK_DUST,
                            this.posX,
                            this.posY + this.height / 1.5,
                            this.posZ,
                            10,
                            this.width / 4.0F,
                            this.height / 4.0F,
                            this.width / 4.0F,
                            0.05,
                            new int[] {Block.getStateId(Blocks.planks.getDefaultState())});
        }
    }

    public Rotations getRightArmRotation() {
        return this.rightArmRotation;
    }

    private void method_e9d2cc1e3595d38b3ac0(PlayerEntity playerEntity, int i) {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        ItemStack itemStack = this.contents[i];
        boolean flag = flag1;
        if ((itemStack == null || (this.disabledSlots & 1 << i + 8) == 0)
                && (itemStack != null || (this.disabledSlots & 1 << i + 16) == 0)) {
            int j = playerEntity.inventory.currentItem;
            ItemStack itemStackValue = playerEntity.inventory.getStackInSlot(j);
            if (playerEntity.capabilities.isCreativeMode
                    && (itemStack == null
                            || itemStack.getItem() == Item.getItemFromBlock(Blocks.air))
                    && itemStackValue != null) {
                ItemStack itemStackValue2 = itemStackValue.copy();
                itemStackValue2.stackSize = 1;
                this.setCurrentItemOrArmor(i, itemStackValue2);
                if (flag) {
                    return;
                }
            }

            if (itemStackValue != null && itemStackValue.stackSize > 1) {
                if (itemStack != null) {
                    return;
                }

                ItemStack itemStackValue3 = itemStackValue.copy();
                itemStackValue3.stackSize = 1;
                this.setCurrentItemOrArmor(i, itemStackValue3);
                itemStackValue.stackSize--;
                if (flag) {
                    return;
                }
            }

            this.setCurrentItemOrArmor(i, itemStackValue);
            playerEntity.inventory.setInventorySlotContents(j, itemStack);
        }
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        boolean flag1 = EntityMinecartEnumMinecartType.d();
        super.writeEntityToNBT(compoundTag);
        boolean flag = flag1;
        NBTTagList nbtTagList = new NBTTagList();
        int i = 0;

        while (i < this.contents.length) {
            CompoundTag compoundTagValue = new CompoundTag();
            if (this.contents[i] != null) {
                this.contents[i].writeToNBT(compoundTagValue);
            }

            nbtTagList.appendTag(compoundTagValue);
            i++;
            if (flag) {
                break;
            }
        }

        compoundTag.setTag("Equipment", nbtTagList);
        if (this.getAlwaysRenderNameTag()
                && (this.getCustomNameTag() == null || this.getCustomNameTag().length() == 0)) {
            compoundTag.setBoolean("CustomNameVisible", this.getAlwaysRenderNameTag());
        }

        compoundTag.setBoolean("Invisible", this.isInvisible());
        compoundTag.setBoolean("Small", this.isSmall());
        compoundTag.setBoolean("ShowArms", this.getShowArms());
        compoundTag.setInteger("DisabledSlots", this.disabledSlots);
        compoundTag.setBoolean("NoGravity", this.hasNoGravity());
        compoundTag.setBoolean("NoBasePlate", this.hasNoBasePlate());
        compoundTag.setTag("Pose", this.readPoseFromNBT());
    }

    protected float func_110146_f(float p171_0, float p171_1) {
        this.prevRenderYawOffset = this.prevRotationYaw;
        this.renderYawOffset = this.rotationYaw;
        return 0.0F;
    }

    private void writePoseToNBT(CompoundTag compoundTag) {
        boolean flag;
        {
            boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
            NBTTagList nbtTagList = compoundTag.getTagList("Head", 5);
            flag = flag1;
            if (nbtTagList.tagCount() > 0) {
                this.setHeadRotation(new Rotations(nbtTagList));
                if (!(flag)) {

                    this.setHeadRotation(net.minecraft.entity.item.EntityArmorStand.DEFAULT_HEAD_ROTATION);
                }
            } else {

                this.setHeadRotation(net.minecraft.entity.item.EntityArmorStand.DEFAULT_HEAD_ROTATION);
            }
        }

        {
            NBTTagList nbtTagListValue = compoundTag.getTagList("Body", 5);
            if (nbtTagListValue.tagCount() > 0) {
                this.setBodyRotation(new Rotations(nbtTagListValue));
                if (!(flag)) {

                    this.setBodyRotation(net.minecraft.entity.item.EntityArmorStand.DEFAULT_BODY_ROTATION);
                }
            } else {

                this.setBodyRotation(net.minecraft.entity.item.EntityArmorStand.DEFAULT_BODY_ROTATION);
            }
        }

        {
            NBTTagList nbtTagListValue2 = compoundTag.getTagList("LeftArm", 5);
            if (nbtTagListValue2.tagCount() > 0) {
                this.setLeftArmRotation(new Rotations(nbtTagListValue2));
                if (!(flag)) {

                    this.setLeftArmRotation(net.minecraft.entity.item.EntityArmorStand.DEFAULT_LEFTARM_ROTATION);
                }
            } else {

                this.setLeftArmRotation(net.minecraft.entity.item.EntityArmorStand.DEFAULT_LEFTARM_ROTATION);
            }
        }

        {
            NBTTagList nbtTagListValue3 = compoundTag.getTagList("RightArm", 5);
            if (nbtTagListValue3.tagCount() > 0) {
                this.setRightArmRotation(new Rotations(nbtTagListValue3));
                if (!(flag)) {

                    this.setRightArmRotation(net.minecraft.entity.item.EntityArmorStand.DEFAULT_RIGHTARM_ROTATION);
                }
            } else {

                this.setRightArmRotation(net.minecraft.entity.item.EntityArmorStand.DEFAULT_RIGHTARM_ROTATION);
            }
        }

        {
            NBTTagList nbtTagListValue4 = compoundTag.getTagList("LeftLeg", 5);
            if (nbtTagListValue4.tagCount() > 0) {
                this.setLeftLegRotation(new Rotations(nbtTagListValue4));
                if (!(flag)) {

                    this.setLeftLegRotation(net.minecraft.entity.item.EntityArmorStand.DEFAULT_LEFTLEG_ROTATION);
                }
            } else {

                this.setLeftLegRotation(net.minecraft.entity.item.EntityArmorStand.DEFAULT_LEFTLEG_ROTATION);
            }
        }

        NBTTagList nbtTagListValue5 = compoundTag.getTagList("RightLeg", 5);
        if (nbtTagListValue5.tagCount() > 0) {
            this.setRightLegRotation(new Rotations(nbtTagListValue5));
            if (flag) {
                return;
            }
        }

        this.setRightLegRotation(net.minecraft.entity.item.EntityArmorStand.DEFAULT_RIGHTLEG_ROTATION);
    }

    public EntityArmorStand(World worldValue) {
        super(worldValue);
        this.headRotation = net.minecraft.entity.item.EntityArmorStand.DEFAULT_HEAD_ROTATION;
        this.bodyRotation = net.minecraft.entity.item.EntityArmorStand.DEFAULT_BODY_ROTATION;
        this.leftArmRotation = net.minecraft.entity.item.EntityArmorStand.DEFAULT_LEFTARM_ROTATION;
        this.rightArmRotation = net.minecraft.entity.item.EntityArmorStand.DEFAULT_RIGHTARM_ROTATION;
        this.leftLegRotation = net.minecraft.entity.item.EntityArmorStand.DEFAULT_LEFTLEG_ROTATION;
        this.rightLegRotation = net.minecraft.entity.item.EntityArmorStand.DEFAULT_RIGHTLEG_ROTATION;
        this.setSilent(true);
        this.noClip = this.hasNoGravity();
        this.setSize(0.5F, 1.975F);
    }

    public Rotations getRightLegRotation() {
        return this.rightLegRotation;
    }

    public void setBodyRotation(Rotations bcUValue) {
        this.bodyRotation = bcUValue;
        this.dataWatcher.updateObject(12, bcUValue);
    }

    public ItemStack[] getInventory() {
        return this.contents;
    }

    public void setRightLegRotation(Rotations bddValue) {
        this.rightLegRotation = bddValue;
        this.dataWatcher.updateObject(16, bddValue);
    }

    public ItemStack getHeldItem() {
        return this.contents[0];
    }

    public boolean isServerWorld() {
        return super.isServerWorld() && !this.hasNoGravity();
    }

    public boolean isSmall() {
        return (this.dataWatcher.getWatchableObjectByte(10) & 1) != 0;
    }

    private void dropContents() {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        int i = 0;
        boolean flag = flag1;

        while (i < this.contents.length) {
            if (this.contents[i] != null && this.contents[i].stackSize > 0) {
                if (this.contents[i] != null) {
                    Block.spawnAsEntity(
                            this.world, new BlockPos(this).offsetUp(), this.contents[i]);
                }

                this.contents[i] = null;
            }

            i++;
            if (!flag) {
                break;
            }
        }
    }

    public ItemStack getCurrentArmor(int p171_0) {
        return this.contents[p171_0 + 1];
    }

    public void moveEntityWithHeading(float p171_0, float p171_1) {
        if (!this.hasNoGravity()) {
            super.moveEntityWithHeading(p171_0, p171_1);
        }
    }

    public ItemStack getEquipmentInSlot(int p171_0) {
        return this.contents[p171_0];
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        boolean flag = EntityMinecartEnumMinecartType.d();
        if (this.world.isRemote || this.canInteract) {
            return false;
        }

        if (DamageSource.outOfWorld.equals(damageSource)) {
            this.setDead();
            return false;
        }

        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        if (damageSource.isExplosion()) {
            this.dropContents();
            this.setDead();
            return false;
        }

        if (DamageSource.inFire.equals(damageSource)) {
            if (!this.isBurning()) {
                this.setFire(5);
                if (!flag) {
                    return false;
                }
            }

            this.damageArmorStand(0.15F);
            return false;
        } else {
            if (DamageSource.onFire.equals(damageSource) && this.getHealth() > 0.5F) {
                this.damageArmorStand(4.0F);
                return false;
            }

            boolean flag1 = "arrow".equals(damageSource.getDamageType());
            boolean flag2 = "player".equals(damageSource.getDamageType());
            if (!flag2 && !flag1) {
                return false;
            }

            if (damageSource.getSourceOfDamage() instanceof EntityArrow) {
                damageSource.getSourceOfDamage().setDead();
            }

            if (damageSource.getEntity() instanceof PlayerEntity
                    && !((PlayerEntity) damageSource.getEntity()).capabilities.allowEdit) {
                return false;
            }

            if (damageSource.isCreativePlayer()) {
                this.playParticles();
                this.setDead();
                return false;
            }

            long bcZValue = this.world.getTotalWorldTime();
            long j = bcZValue - this.punchCooldown;

            if (j > 5L && !flag1) {
                this.punchCooldown = bcZValue;
                if (!flag) {
                    return false;
                }
            }

            this.dropBlock();
            this.playParticles();
            this.setDead();
            return false;
        }
    }

    public boolean getShowArms() {
        return (this.dataWatcher.getWatchableObjectByte(10) & 4) != 0;
    }

    public void setLeftLegRotation(Rotations bdhValue) {
        this.leftLegRotation = bdhValue;
        this.dataWatcher.updateObject(15, bdhValue);
    }
}
