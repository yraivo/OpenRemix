package remix.event.impl;

import net.minecraft.entity.Entity;

import remix.event.Event;

public class ModelRotationEvent implements Event {
    public float previousBodyYaw;
    public float previousHeadYaw;
    public float bodyYaw;
    public float pitch;
    public boolean sneaking;
    public float previousPitch;
    public Entity entity;
    public float headYaw;
    public boolean usingItem;

    public ModelRotationEvent(Entity entityValue, boolean sneakingValue, boolean usingItemValue) {
        this.sneaking = sneakingValue;
        this.usingItem = usingItemValue;
        this.entity = entityValue;
    }

    public void setPreviousPitch(float previousPitchValue) {
        this.previousPitch = previousPitchValue;
    }

    public float getHeadYaw() {
        return this.headYaw;
    }

    public float getPreviousBodyYaw() {
        return this.previousBodyYaw;
    }

    public boolean isSneaking() {
        return this.sneaking;
    }

    public void setUsingItem(boolean usingItemValue) {
        this.usingItem = usingItemValue;
    }

    public void setBodyYaw(float bodyYawValue) {
        this.bodyYaw = bodyYawValue;
    }

    public void setPreviousHeadYaw(float previousHeadYawValue) {
        this.previousHeadYaw = previousHeadYawValue;
    }

    public float getPreviousHeadYaw() {
        return this.previousHeadYaw;
    }

    public void setPreviousBodyYaw(float previousBodyYawValue) {
        this.previousBodyYaw = previousBodyYawValue;
    }

    public float getBodyYaw() {
        return this.bodyYaw;
    }

    public ModelRotationEvent(
            Entity entityValue, float f, float f1, float f2, float f3, float f4, float f5) {
        super();
        boolean initialFlowFlag = PacketEvent.d();

        boolean flag1 = initialFlowFlag;

        this.previousBodyYaw = f;
        boolean flag = flag1;
        this.bodyYaw = f1;
        this.previousHeadYaw = f2;
        this.headYaw = f3;
        this.pitch = f4;
        this.previousPitch = f5;
        this.entity = entityValue;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            PacketEvent.method_a8745b073fa8adfa1857(!flag);
        }
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setSneaking(boolean sneakingValue) {
        this.sneaking = sneakingValue;
    }

    public void setHeadYaw(float headYawValue) {
        this.headYaw = headYawValue;
    }

    public float getPreviousPitch() {
        return this.previousPitch;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public boolean isUsingItem() {
        return this.usingItem;
    }

    public void setPitch(float pitchValue) {
        this.pitch = pitchValue;
    }
}
