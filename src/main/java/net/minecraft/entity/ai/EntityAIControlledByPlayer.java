package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

public class EntityAIControlledByPlayer extends EntityAIBase {
    private boolean speedBoosted;
    private EntityLiving thisEntity;
    private int maxSpeedBoostTime;
    private int speedBoostTime;
    private float currentSpeed;
    private float maxSpeed;

    public void updateTask() {
        String text = EntityAIBase.cJ();
        PlayerEntity riddenByEntity = (PlayerEntity) this.thisEntity.riddenByEntity;
        EntityCreature bhtValue = (EntityCreature) this.thisEntity;
        String textValue = text;
        float fx =
                MathHelper.wrapAngleTo180(riddenByEntity.rotationYaw - this.thisEntity.rotationYaw)
                        * 0.5F;
        if (fx > 5.0F) {
            fx = 5.0F;
        }

        if (fx < -5.0F) {
            fx = -5.0F;
        }

        this.thisEntity.rotationYaw = MathHelper.wrapAngleTo180(this.thisEntity.rotationYaw + fx);
        if (this.currentSpeed < this.maxSpeed) {
            this.currentSpeed = this.currentSpeed + (this.maxSpeed - this.currentSpeed) * 0.01F;
        }

        if (this.currentSpeed > this.maxSpeed) {
            this.currentSpeed = this.maxSpeed;
        }

        int i = MathHelper.floor(this.thisEntity.posX);
        int j = MathHelper.floor(this.thisEntity.posY);
        int k = MathHelper.floor(this.thisEntity.posZ);
        float currentSpeedValue = this.currentSpeed;
        if (this.speedBoosted) {
            if (this.speedBoostTime++ > this.maxSpeedBoostTime) {
                this.speedBoosted = false;
            }

            currentSpeedValue +=
                    currentSpeedValue
                            * 1.15F
                            * MathHelper.sin(
                                    (float) this.speedBoostTime
                                            / this.maxSpeedBoostTime
                                            * (float) Math.PI);
        }

        float f2 = 0.91F;
        if (this.thisEntity.onGround) {
            f2 =
                    this.thisEntity
                                    .world
                                    .getBlockState(
                                            new BlockPos(
                                                    MathHelper.floor(i),
                                                    MathHelper.floor(j) - 1,
                                                    MathHelper.floor(k)))
                                    .getBlock()
                                    .slipperiness
                            * 0.91F;
        }

        float f9;
        float f10;
        label104:
        {
            float f3 = 0.16277136F / (f2 * f2 * f2);
            float f4 = MathHelper.sin(bhtValue.rotationYaw * (float) Math.PI / 180.0F);
            float f5 = MathHelper.cos(bhtValue.rotationYaw * (float) Math.PI / 180.0F);
            float f6 = bhtValue.getAIMoveSpeed() * f3;
            float f7 = Math.max(currentSpeedValue, 1.0F);
            f7 = f6 / f7;
            float f8 = currentSpeedValue * f7;
            f9 = -(f8 * f4);
            f10 = f8 * f5;
            if (MathHelper.abs(f9) > MathHelper.abs(f10)) {
                if (f9 < 0.0F) {
                    f9 -= this.thisEntity.width / 2.0F;
                }

                if (f9 > 0.0F) {
                    f9 += this.thisEntity.width / 2.0F;
                }

                f10 = 0.0F;
                if (textValue != null) {
                    break label104;
                }

                Entity.setLegacyGlobalFlowMarker(new String[4]);
            }

            f9 = 0.0F;
            if (f10 < 0.0F) {
                f10 -= this.thisEntity.width / 2.0F;
            }

            if (f10 > 0.0F) {
                f10 += this.thisEntity.width / 2.0F;
            }
        }

        int l = MathHelper.floor(this.thisEntity.posX + f9);
        int i1 = MathHelper.floor(this.thisEntity.posZ + f10);
        int j1 = MathHelper.floor(this.thisEntity.width + 1.0F);
        int k1 = MathHelper.floor(this.thisEntity.height + riddenByEntity.height + 1.0F);
        int l1 = MathHelper.floor(this.thisEntity.width + 1.0F);
        if (i != l || k != i1) {
            Block block = this.thisEntity.world.getBlockState(new BlockPos(i, j, k)).getBlock();
            int i2 =
                    this.isStairOrSlab(block)
                                    || block.getMaterial() == Material.AIR
                                            && this.isStairOrSlab(
                                                    this.thisEntity
                                                            .world
                                                            .getBlockState(
                                                                    new BlockPos(i, j - 1, k))
                                                            .getBlock())
                            ? 0
                            : 1;
            if (i2 != 0
                    && 0
                            == WalkNodeProcessor.func_176170_a(
                                    this.thisEntity.world,
                                    this.thisEntity,
                                    l,
                                    j,
                                    i1,
                                    j1,
                                    k1,
                                    l1,
                                    false,
                                    false,
                                    true)
                    && 1
                            == WalkNodeProcessor.func_176170_a(
                                    this.thisEntity.world,
                                    this.thisEntity,
                                    i,
                                    j + 1,
                                    k,
                                    j1,
                                    k1,
                                    l1,
                                    false,
                                    false,
                                    true)
                    && 1
                            == WalkNodeProcessor.func_176170_a(
                                    this.thisEntity.world,
                                    this.thisEntity,
                                    l,
                                    j + 1,
                                    i1,
                                    j1,
                                    k1,
                                    l1,
                                    false,
                                    false,
                                    true)) {
                bhtValue.getJumpHelper().setJumping();
            }
        }

        if (!riddenByEntity.capabilities.isCreativeMode
                && this.currentSpeed >= this.maxSpeed * 0.5F
                && this.thisEntity.getRNG().nextFloat() < 0.006F
                && !this.speedBoosted) {
            ItemStack heldItem = riddenByEntity.getHeldItem();
            if (heldItem != null && heldItem.getItem() == Items.carrot_on_a_stick) {
                heldItem.damageItem(1, riddenByEntity);
                if (heldItem.stackSize == 0) {
                    ItemStack itemStack = new ItemStack(Items.fishing_rod);
                    itemStack.setTag(heldItem.getTag());
                    riddenByEntity.inventory.mainInventory[riddenByEntity.inventory.currentItem] =
                            itemStack;
                }
            }
        }

        this.thisEntity.moveEntityWithHeading(0.0F, currentSpeedValue);
    }

    public boolean shouldExecute() {
        return this.thisEntity.isEntityAlive()
                && this.thisEntity.riddenByEntity != null
                && this.thisEntity.riddenByEntity instanceof PlayerEntity
                && (this.speedBoosted || this.thisEntity.canBeSteered());
    }

    public EntityAIControlledByPlayer(EntityLiving bhtValue, float maxSpeedValue) {
        this.thisEntity = bhtValue;
        this.maxSpeed = maxSpeedValue;
        this.setMutexBits(7);
    }

    public boolean isControlledByPlayer() {
        return !this.isSpeedBoosted() && this.currentSpeed > this.maxSpeed * 0.3F;
    }

    public void boostSpeed() {
        this.speedBoosted = true;
        this.speedBoostTime = 0;
        this.maxSpeedBoostTime = this.thisEntity.getRNG().nextInt(841) + 140;
    }

    public void resetTask() {
        this.speedBoosted = false;
        this.currentSpeed = 0.0F;
    }

    private boolean isStairOrSlab(Block block) {
        return block instanceof BlockStairs || block instanceof BlockSlab;
    }

    public boolean isSpeedBoosted() {
        return this.speedBoosted;
    }

    public void startExecuting() {
        this.currentSpeed = 0.0F;
    }
}
