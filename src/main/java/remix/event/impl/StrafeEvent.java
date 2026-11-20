package remix.event.impl;

import remix.event.Event;

public class StrafeEvent implements Event {
    private float strafe;
    private float forward;
    private float yaw;

    public void setForward(float f) {
        this.forward = f;
    }

    public void setStrafe(float f) {
        this.strafe = f;
    }

    public float getForward() {
        return this.forward;
    }

    public StrafeEvent(float yaw, float forward, float strafe) {
        this.yaw = yaw;
        this.forward = forward;
        this.strafe = strafe;
    }

    public void setYaw(float f) {
        this.yaw = f;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getStrafe() {
        return this.strafe;
    }
}
