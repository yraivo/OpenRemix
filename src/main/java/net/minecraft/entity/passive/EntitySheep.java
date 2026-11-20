package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Random;

public class EntitySheep extends AnimalEntity {

    private int sheepTimer;

    private static Map DYE_TO_RGB;
    private EntityAIEatGrass entityAIEatGrass;

    private final InventoryCrafting inventoryCrafting =
            new InventoryCrafting(new SheepDyeMixingContainer(this), 2, 1);

    public IEntityLivingData onInitialSpawn(
            DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
        iEntityLivingData = super.onInitialSpawn(difficultyInstance, iEntityLivingData);
        this.setFleeceColor(getRandomSheepColor(this.world.rand));
        return iEntityLivingData;
    }

    public EntitySheep func_180491_b(EntityAgeable entityAgeable) {
        EntitySheep entitySheep = (EntitySheep) entityAgeable;
        EntitySheep entitySheepValue = new EntitySheep(this.world);
        entitySheepValue.setFleeceColor(this.getDyeColorMixFromParents(this, entitySheep));
        return entitySheepValue;
    }

    static {
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB = Maps.newEnumMap(EnumDyeColor.class);
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.WHITE, new float[] {1.0F, 1.0F, 1.0F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.ORANGE, new float[] {0.85F, 0.5F, 0.2F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.MAGENTA, new float[] {0.7F, 0.3F, 0.85F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.LIGHT_BLUE, new float[] {0.4F, 0.6F, 0.85F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.YELLOW, new float[] {0.9F, 0.9F, 0.2F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.LIME, new float[] {0.5F, 0.8F, 0.1F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.PINK, new float[] {0.95F, 0.5F, 0.65F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.GRAY, new float[] {0.3F, 0.3F, 0.3F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.SILVER, new float[] {0.6F, 0.6F, 0.6F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.CYAN, new float[] {0.3F, 0.5F, 0.6F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.PURPLE, new float[] {0.5F, 0.25F, 0.7F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.BLUE, new float[] {0.2F, 0.3F, 0.7F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.BROWN, new float[] {0.4F, 0.3F, 0.2F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.GREEN, new float[] {0.4F, 0.5F, 0.2F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.RED, new float[] {0.6F, 0.2F, 0.2F});
        net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.put(EnumDyeColor.BLACK, new float[] {0.1F, 0.1F, 0.1F});
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte) 0));
    }

    public boolean interact(PlayerEntity playerEntity) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        String textValue = text;
        if (itemStack != null
                && itemStack.getItem() == Items.shears
                && !this.getSheared()
                && !this.isChild()) {
            if (!this.world.isRemote) {
                this.setSheared(true);
                int i = 1 + this.rand.nextInt(3);
                int j = 0;

                while (j < i) {
                    EntityItem entityItem =
                            this.entityDropItem(
                                    new ItemStack(
                                            Item.getItemFromBlock(Blocks.wool),
                                            1,
                                            this.getFleeceColor().getMetadata()),
                                    1.0F);
                    entityItem.motionY = entityItem.motionY + this.rand.nextFloat() * 0.05F;
                    entityItem.motionX =
                            entityItem.motionX
                                    + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
                    entityItem.motionZ =
                            entityItem.motionZ
                                    + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
                    j++;
                    if (textValue != null) {
                        break;
                    }
                }
            }

            itemStack.damageItem(1, playerEntity);
            this.playSound("mob.sheep.shear", 1.0F, 1.0F);
        }

        return super.interact(playerEntity);
    }

    public EnumDyeColor getFleeceColor() {
        return EnumDyeColor.byMetadata(this.dataWatcher.getWatchableObjectByte(16) & 15);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23F);
    }

    public static float[] getDyeRgb(EnumDyeColor enumDyeColor) {
        return (float[]) net.minecraft.entity.passive.EntitySheep.DYE_TO_RGB.get(enumDyeColor);
    }

    public float getHeadRotationPointY(float f) {
        return this.sheepTimer <= 0
                ? 0.0F
                : (this.sheepTimer >= 4 && this.sheepTimer <= 36
                        ? 1.0F
                        : (this.sheepTimer < 4
                                ? (this.sheepTimer - f) / 4.0F
                                : -(this.sheepTimer - 40 - f) / 4.0F));
    }

    public boolean getSheared() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 16) != 0;
    }

    protected String getLivingSound() {
        return "mob.sheep.say";
    }

    public void eatGrassBonus() {
        this.setSheared(false);
        if (this.isChild()) {
            this.addGrowth(60);
        }
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.setSheared(compoundTag.getBoolean("Sheared"));
        this.setFleeceColor(EnumDyeColor.byMetadata(compoundTag.getByte("Color")));
    }

    protected String getHurtSound() {
        return "mob.sheep.say";
    }

    protected void playStepSound(BlockPos blockPos, Block block) {
        this.playSound("mob.sheep.step", 0.15F, 1.0F);
    }

    protected String getDeathSound() {
        return "mob.sheep.say";
    }

    public float getHeadRotationAngleX(float f) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            float f1 = (this.sheepTimer - 4 - f) / 32.0F;
            return (float) (Math.PI / 5) + 0.2199115F * MathHelper.sin(f1 * 28.7F);
        } else {
            return this.sheepTimer > 0
                    ? (float) (Math.PI / 5)
                    : this.rotationPitch / (180.0F / (float) Math.PI);
        }
    }

    protected void updateAITasks() {
        this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }

    public void setSheared(boolean flag) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        String textValue = text;
        if (flag) {
            this.dataWatcher.updateObject(16, (byte) (b0 | 16));
            if (textValue == null) {
                return;
            }
        }

        this.dataWatcher.updateObject(16, (byte) (b0 & -17));
    }

    public void handleHealthUpdate(byte b0) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (b0 == 10) {
            this.sheepTimer = 40;
            if (text == null) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setBoolean("Sheared", this.getSheared());
        compoundTag.setByte("Color", (byte) this.getFleeceColor().getMetadata());
    }

    public void setFleeceColor(EnumDyeColor enumDyeColor) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        this.dataWatcher.updateObject(16, (byte) (b0 & 240 | enumDyeColor.getMetadata() & 15));
    }

    private EnumDyeColor getDyeColorMixFromParents(
            AnimalEntity animalEntity, AnimalEntity animalEntityValue) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        int dyeColorDamage = ((EntitySheep) animalEntity).getFleeceColor().getDyeColorDamage();
        String textValue = text;
        int j = ((EntitySheep) animalEntityValue).getFleeceColor().getDyeColorDamage();
        this.inventoryCrafting.getStackInSlot(0).setItemDamage(dyeColorDamage);
        this.inventoryCrafting.getStackInSlot(1).setItemDamage(j);
        ItemStack itemStack =
                CraftingManager.getInstance()
                        .findMatchingRecipe(
                                this.inventoryCrafting, ((EntitySheep) animalEntity).world);
        if (itemStack != null && itemStack.getItem() == Items.dye) {
            int k = itemStack.getItemDamage();
            if (textValue == null) {
                return EnumDyeColor.byDyeDamage(k);
            }
        }

        int l = this.world.rand.nextBoolean() ? dyeColorDamage : j;
        return EnumDyeColor.byDyeDamage(l);
    }

    public EntitySheep(World worldValue) {
        super(worldValue);
        this.entityAIEatGrass = new EntityAIEatGrass(this);
        this.setSize(0.9F, 1.3F);
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.1, Items.wheat, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(5, this.entityAIEatGrass);
        this.tasks.addTask(6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.inventoryCrafting.setInventorySlotContents(0, new ItemStack(Items.dye, 1, 0));
        this.inventoryCrafting.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 0));
    }

    public void onLivingUpdate() {
        if (this.world.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.onLivingUpdate();
    }

    protected void dropFewItems(boolean flag, int i) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (!this.getSheared()) {
            this.entityDropItem(
                    new ItemStack(
                            Item.getItemFromBlock(Blocks.wool),
                            1,
                            this.getFleeceColor().getMetadata()),
                    0.0F);
        }

        int j = this.rand.nextInt(2) + 1 + this.rand.nextInt(1 + i);
        int k = 0;

        while (k < j) {
            {
                if (this.isBurning()) {
                    this.dropItem(Items.cooked_mutton, 1);
                    if (!(text == null)) {

                        this.dropItem(Items.mutton, 1);
                    }
                } else {

                    this.dropItem(Items.mutton, 1);
                }
            }

            k++;
            if (text != null) {
                break;
            }
        }
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.wool);
    }

    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        return this.func_180491_b(entityAgeable);
    }

    public static EnumDyeColor getRandomSheepColor(Random random) {
        int i = random.nextInt(100);
        return i < 5
                ? EnumDyeColor.BLACK
                : (i < 10
                        ? EnumDyeColor.GRAY
                        : (i < 15
                                ? EnumDyeColor.SILVER
                                : (i < 18
                                        ? EnumDyeColor.BROWN
                                        : (random.nextInt(500) == 0
                                                ? EnumDyeColor.PINK
                                                : EnumDyeColor.WHITE))));
    }

    public float getEyeHeight() {
        return 0.95F * this.height;
    }
}
