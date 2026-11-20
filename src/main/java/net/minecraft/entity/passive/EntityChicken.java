package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityChicken extends AnimalEntity {

    public float bfw;
    public float bfx;
    public int timeUntilNextEgg;
    public float wingRotation;
    public float destPos;
    public boolean chickenJockey;
    public float wingRotDelta = 1.0F;

    public boolean isChickenJockey() {
        return this.chickenJockey;
    }

    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        return this.method_c1c98245cc40e3db8c31(entityAgeable);
    }

    protected void playStepSound(BlockPos blockPos, Block block) {
        this.playSound("mob.chicken.step", 0.15F, 1.0F);
    }

    protected int getExperiencePoints(PlayerEntity playerEntity) {
        return this.isChickenJockey() ? 10 : super.getExperiencePoints(playerEntity);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }

    public void fall(float f, float f1) {}

    protected boolean canDespawn() {
        return this.isChickenJockey() && this.riddenByEntity == null;
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.chickenJockey = compoundTag.getBoolean("IsChickenJockey");
        if (compoundTag.hasKey("EggLayTime")) {
            this.timeUntilNextEgg = compoundTag.getInteger("EggLayTime");
        }
    }

    public EntityChicken method_c1c98245cc40e3db8c31(EntityAgeable entityAgeable) {
        return new EntityChicken(this.world);
    }


    protected Item getDropItem() {
        return Items.feather;
    }

    protected void dropFewItems(boolean flag, int i) {
        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        int k = 0;
        String textValue = text;

        while (k < j) {
            this.dropItem(Items.feather, 1);
            k++;
            if (textValue != null) {
                break;
            }
        }

        if (this.isBurning()) {
            this.dropItem(Items.cooked_chicken, 1);
            if (textValue == null) {
                return;
            }
        }

        this.dropItem(Items.chicken, 1);
    }

    public void updateRiderPosition() {
        super.updateRiderPosition();
        float f = MathHelper.sin(this.renderYawOffset * (float) Math.PI / 180.0F);
        float f1 = MathHelper.cos(this.renderYawOffset * (float) Math.PI / 180.0F);
        float f2 = 0.1F;
        float f3 = 0.0F;
        this.riddenByEntity.setPosition(
                this.posX + f2 * f,
                this.posY + this.height * 0.5F + this.riddenByEntity.getYOffset() + f3,
                this.posZ - f2 * f1);
        if (this.riddenByEntity instanceof LivingEntity) {
            ((LivingEntity) this.riddenByEntity).renderYawOffset = this.renderYawOffset;
        }
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setBoolean("IsChickenJockey", this.chickenJockey);
        compoundTag.setInteger("EggLayTime", this.timeUntilNextEgg);
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.bfx = this.wingRotation;
        this.bfw = this.destPos;
        this.destPos = (float) (this.destPos + (this.onGround ? -1 : 4) * 0.3);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
        if (!this.onGround && this.wingRotDelta < 1.0F) {
            this.wingRotDelta = 1.0F;
        }

        this.wingRotDelta = (float) (this.wingRotDelta * 0.9);
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }

        this.wingRotation = this.wingRotation + this.wingRotDelta * 2.0F;
        if (!this.world.isRemote
                && !this.isChild()
                && !this.isChickenJockey()
                && --this.timeUntilNextEgg <= 0) {
            this.playSound(
                    "mob.chicken.plop",
                    1.0F,
                    (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(Items.egg, 1);
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        }
    }

    protected String getDeathSound() {
        return "mob.chicken.hurt";
    }

    protected String getLivingSound() {
        return "mob.chicken.say";
    }

    public float getEyeHeight() {
        return this.height;
    }

    public boolean isBreedingItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getItem() == Items.wheat_seeds;
    }

    public void setChickenJockey(boolean bfBValue) {
        this.chickenJockey = bfBValue;
    }

    public EntityChicken(World worldValue) {
        super(worldValue);
        this.setSize(0.4F, 0.7F);
        this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0, Items.wheat_seeds, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    protected String getHurtSound() {
        return "mob.chicken.hurt";
    }
}
