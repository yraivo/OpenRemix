package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class AnimalEntity extends EntityAgeable implements IAnimals {

    private PlayerEntity playerInLove;
    private static String[] bft;

    protected Block spawnableBlock = Blocks.grass;
    private int inLove;

    protected int getExperiencePoints(PlayerEntity playerEntity) {
        return 1 + this.world.rand.nextInt(3);
    }

    public static String[] Ky() {
        return bft;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        this.inLove = 0;
        return super.attackEntityFrom(damageSource, f);
    }

    protected void consumeItemFromStack(PlayerEntity playerEntity, ItemStack itemStack) {
        if (!playerEntity.capabilities.isCreativeMode) {
            itemStack.stackSize--;
            if (itemStack.stackSize <= 0) {
                playerEntity.inventory.setInventorySlotContents(
                        playerEntity.inventory.currentItem, (ItemStack) null);
            }
        }
    }

    public boolean canMateWith(AnimalEntity animalEntity) {
        return animalEntity == this
                ? false
                : (animalEntity.getClass() != this.getClass()
                        ? false
                        : this.isInLove() && animalEntity.isInLove());
    }

    public PlayerEntity getPlayerInLove() {
        return this.playerInLove;
    }

    public void resetInLove() {
        this.inLove = 0;
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.inLove = compoundTag.getInteger("InLove");
    }

    // region String decoding support

    // endregion

    public static void method_ca7597a13eb9afc8473a(String[] astring) {
        bft = astring;
    }

    protected boolean canDespawn() {
        return false;
    }

    public int getTalkInterval() {
        return 120;
    }

    public boolean interact(PlayerEntity playerEntity) {
        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        if (itemStack != null) {
            if (this.isBreedingItem(itemStack) && this.getGrowingAge() == 0 && this.inLove <= 0) {
                this.consumeItemFromStack(playerEntity, itemStack);
                this.setInLove(playerEntity);
                return true;
            }

            if (this.isChild() && this.isBreedingItem(itemStack)) {
                this.consumeItemFromStack(playerEntity, itemStack);
                this.func_175501_a((int) (-this.getGrowingAge() / 20 * 0.1F), true);
                return true;
            }
        }

        return super.interact(playerEntity);
    }

    public boolean getCanSpawnHere() {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockPos = new BlockPos(i, j, k);
        return this.world.getBlockState(blockPos.offsetDown()).getBlock() == this.spawnableBlock
                && this.world.getLight(blockPos) > 8
                && super.getCanSpawnHere();
    }

    public float getBlockPathWeight(BlockPos blockPos) {
        return this.world.getBlockState(blockPos.offsetDown()).getBlock() == Blocks.grass
                ? 10.0F
                : this.world.getLightBrightness(blockPos) - 0.5F;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setInteger("InLove", this.inLove);
    }

    // region String decoding support

    // endregion

    protected void updateAITasks() {
        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }

        super.updateAITasks();
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getGrowingAge() != 0) {
            this.inLove = 0;
        }

        if (this.inLove > 0) {
            this.inLove--;
            if (this.inLove % 10 == 0) {
                double d0 = this.rand.nextGaussian() * 0.02;
                double d1 = this.rand.nextGaussian() * 0.02;
                double d2 = this.rand.nextGaussian() * 0.02;
                this.world.spawnParticle(
                        EnumParticleTypes.HEART,
                        this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                        this.posY + 0.5 + this.rand.nextFloat() * this.height,
                        this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                        d0,
                        d1,
                        d2,
                        new int[0]);
            }
        }
    }

    public boolean isInLove() {
        return this.inLove > 0;
    }

    public boolean isBreedingItem(ItemStack itemStack) {
        return itemStack == null ? false : itemStack.getItem() == Items.wheat;
    }

    public void handleHealthUpdate(byte b0) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (b0 == 18) {
            int i = 0;

            while (i < 7) {
                double d0 = this.rand.nextGaussian() * 0.02;
                double d1 = this.rand.nextGaussian() * 0.02;
                double d2 = this.rand.nextGaussian() * 0.02;
                this.world.spawnParticle(
                        EnumParticleTypes.HEART,
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

            if (text == null) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    public void setInLove(PlayerEntity bfsValue) {
        this.inLove = 600;
        this.playerInLove = bfsValue;
        this.world.setEntityState(this, (byte) 18);
    }

    public AnimalEntity(World worldValue) {
        super(worldValue);
    }

    // region Static initialization and string tables
    static {
        String[] astring1 = new String[4];
        method_ca7597a13eb9afc8473a(astring1);
    }
    // endregion
}
