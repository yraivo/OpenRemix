package remix.event.impl;

import remix.event.CancellableEvent;

public class CameraRotationEvent extends CancellableEvent {
    public float pitch;
    public float yaw;

    public CameraRotationEvent(float yawValue, float pitchValue) {
        this.yaw = yawValue;
        this.pitch = pitchValue;
    }
}
