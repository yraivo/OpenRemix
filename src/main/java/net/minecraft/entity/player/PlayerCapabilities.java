package net.minecraft.entity.player;

import net.minecraft.nbt.CompoundTag;

public class PlayerCapabilities {

    public boolean isCreativeMode;
    public boolean allowFlying;
    private float walkSpeed;
    public boolean disableDamage;
    public boolean allowEdit = true;
    private float flySpeed = 0.05F;

    public boolean isFlying;

    public void setFlySpeed(float flySpeedValue) {
        this.flySpeed = flySpeedValue;
    }

    public void readCapabilitiesFromNBT(CompoundTag compoundTag) {
        if (compoundTag.hasKey("abilities", 10)) {
            CompoundTag compoundTagValue = compoundTag.getCompoundTag("abilities");
            this.disableDamage = compoundTagValue.getBoolean("invulnerable");
            this.isFlying = compoundTagValue.getBoolean("flying");
            this.allowFlying = compoundTagValue.getBoolean("mayfly");
            this.isCreativeMode = compoundTagValue.getBoolean("instabuild");
            if (compoundTagValue.hasKey("flySpeed", 99)) {
                this.flySpeed = compoundTagValue.getFloat("flySpeed");
                this.walkSpeed = compoundTagValue.getFloat("walkSpeed");
            }

            if (compoundTagValue.hasKey("mayBuild", 1)) {
                this.allowEdit = compoundTagValue.getBoolean("mayBuild");
            }
        }
    }


    public float getFlySpeed() {
        return this.flySpeed;
    }

    public void writeCapabilitiesToNBT(CompoundTag compoundTag) {
        CompoundTag compoundTagValue = new CompoundTag();
        compoundTagValue.setBoolean("invulnerable", this.disableDamage);
        compoundTagValue.setBoolean("flying", this.isFlying);
        compoundTagValue.setBoolean("mayfly", this.allowFlying);
        compoundTagValue.setBoolean("instabuild", this.isCreativeMode);
        compoundTagValue.setBoolean("mayBuild", this.allowEdit);
        compoundTagValue.setFloat("flySpeed", this.flySpeed);
        compoundTagValue.setFloat("walkSpeed", this.walkSpeed);
        compoundTag.setTag("abilities", compoundTagValue);
    }

    public PlayerCapabilities() {
        this.walkSpeed = 0.1F;
    }

    public void setPlayerWalkSpeed(float walkSpeedValue) {
        this.walkSpeed = walkSpeedValue;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }
}
