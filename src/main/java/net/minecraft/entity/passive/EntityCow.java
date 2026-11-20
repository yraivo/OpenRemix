package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
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
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityCow extends AnimalEntity {

    protected String getHurtSound() {
        return "mob.cow.hurt";
    }

    protected String getLivingSound() {
        return "mob.cow.say";
    }

    public EntityCow(World worldValue) {
        super(worldValue);
        this.setSize(0.9F, 1.3F);
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.0));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25, Items.wheat, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    protected Item getDropItem() {
        return Items.leather;
    }

    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        return this.method_339102e9ac32aaea0589(entityAgeable);
    }

    public float getEyeHeight() {
        return this.height;
    }

    protected void playStepSound(BlockPos blockPos, Block block) {
        this.playSound("mob.cow.step", 0.15F, 1.0F);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2F);
    }

    public EntityCow method_339102e9ac32aaea0589(EntityAgeable entityAgeable) {
        return new EntityCow(this.world);
    }

    protected String getDeathSound() {
        return "mob.cow.hurt";
    }

    public boolean interact(PlayerEntity playerEntity) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        String textValue = text;
        if (itemStack != null
                && itemStack.getItem() == Items.bucket
                && !playerEntity.capabilities.isCreativeMode) {
            if (itemStack.stackSize-- == 1) {
                playerEntity.inventory.setInventorySlotContents(
                        playerEntity.inventory.currentItem, new ItemStack(Items.milk_bucket));
                if (textValue == null) {
                    return true;
                }
            }

            if (!playerEntity.inventory.addItemStackToInventory(new ItemStack(Items.milk_bucket))) {
                playerEntity.dropPlayerItemWithRandomChoice(
                        new ItemStack(Items.milk_bucket, 1, 0), false);
            }

            return true;
        } else {
            return super.interact(playerEntity);
        }
    }

    protected float getSoundVolume() {
        return 0.4F;
    }


    protected void dropFewItems(boolean flag, int i) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + i);
        int k = 0;
        String textValue = text;

        while (k < j) {
            this.dropItem(Items.leather, 1);
            k++;
            if (textValue != null) {
                break;
            }
        }

        j = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + i);
        k = 0;

        while (k < j) {
            {
                if (this.isBurning()) {
                    this.dropItem(Items.cooked_beef, 1);
                    if (!(textValue == null)) {

                        this.dropItem(Items.beef, 1);
                    }
                } else {

                    this.dropItem(Items.beef, 1);
                }
            }

            k++;
            if (textValue != null) {
                break;
            }
        }
    }
}
