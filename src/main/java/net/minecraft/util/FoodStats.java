package net.minecraft.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.EnumDifficulty;
import remix.compat.legacy.auth.LicenseSessionBuilder;

public class FoodStats {

    private float foodSaturationLevel;
    private int prevFoodLevel;

    private float foodExhaustionLevel;
    private int foodLevel = 20;
    private int foodTimer;

    public int getPrevFoodLevel() {
        return this.prevFoodLevel;
    }

    public FoodStats() {
        this.foodSaturationLevel = 5.0F;
        this.prevFoodLevel = 20;
    }

    public boolean needFood() {
        return this.foodLevel < 20;
    }

    public void addStats(ItemFood itemFood, ItemStack itemStack) {
        this.addStats(itemFood.getHealAmount(itemStack), itemFood.getSaturationModifier(itemStack));
    }

    public void addStats(int i, float f) {
        this.foodLevel = Math.min(i + this.foodLevel, 20);
        this.foodSaturationLevel =
                Math.min(this.foodSaturationLevel + i * f * 2.0F, this.foodLevel);
    }

    public void addExhaustion(float f) {
        this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + f, 40.0F);
    }

    public int getFoodLevel() {
        return this.foodLevel;
    }

    public void readNBT(CompoundTag compoundTag) {
        if (compoundTag.hasKey("foodLevel", 99)) {
            this.foodLevel = compoundTag.getInteger("foodLevel");
            this.foodTimer = compoundTag.getInteger("foodTickTimer");
            this.foodSaturationLevel = compoundTag.getFloat("foodSaturationLevel");
            this.foodExhaustionLevel = compoundTag.getFloat("foodExhaustionLevel");
        }
    }

    public void setFoodLevel(int foodLevelValue) {
        this.foodLevel = foodLevelValue;
    }

    public float getSaturationLevel() {
        return this.foodSaturationLevel;
    }

    public void writeNBT(CompoundTag compoundTag) {
        compoundTag.setInteger("foodLevel", this.foodLevel);
        compoundTag.setInteger("foodTickTimer", this.foodTimer);
        compoundTag.setFloat("foodSaturationLevel", this.foodSaturationLevel);
        compoundTag.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
    }


    public void setFoodSaturationLevel(float foodSaturationLevelValue) {
        this.foodSaturationLevel = foodSaturationLevelValue;
    }

    public void onUpdate(PlayerEntity playerEntity) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        EnumDifficulty difficulty = playerEntity.world.getDifficulty();
        String textValue = text;
        this.prevFoodLevel = this.foodLevel;
        if (LicenseSessionBuilder.e) {
            this.foodLevel = 0;
            this.foodExhaustionLevel = 4.0F;
        }

        if (this.foodExhaustionLevel > 4.0F) {
            this.foodExhaustionLevel -= 4.0F;
            if (this.foodSaturationLevel > 0.0F) {
                this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
                if (!(textValue != null)) {

                    if (difficulty != EnumDifficulty.PEACEFUL) {
                        this.foodLevel = Math.max(this.foodLevel - 1, 0);
                    }
                }
            } else {

                if (difficulty != EnumDifficulty.PEACEFUL) {
                    this.foodLevel = Math.max(this.foodLevel - 1, 0);
                }
            }
        }

        if (playerEntity.world.getGameRules().getGameRuleBooleanValue("naturalRegeneration")
                && this.foodLevel >= 18
                && playerEntity.shouldHeal()) {
            this.foodTimer++;
            if (this.foodTimer < 80) {
                return;
            }

            playerEntity.heal(1.0F);
            this.addExhaustion(3.0F);
            this.foodTimer = 0;
            if (textValue != null) {
                return;
            }
        }

        if (this.foodLevel <= 0) {
            this.foodTimer++;
            if (this.foodTimer < 80) {
                return;
            }

            if (playerEntity.getHealth() > 10.0F
                    || difficulty == EnumDifficulty.HARD
                    || playerEntity.getHealth() > 1.0F && difficulty == EnumDifficulty.NORMAL) {
                playerEntity.attackEntityFrom(DamageSource.starve, 1.0F);
            }

            this.foodTimer = 0;
            if (textValue != null) {
                return;
            }
        }

        this.foodTimer = 0;
    }
}
