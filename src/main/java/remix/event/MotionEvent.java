package remix.event;

import remix.event.impl.PacketEvent;

public class MotionEvent extends PhasedEvent implements Cancellable {
    private boolean forcePositionAndRotationPacket;
    public boolean onGround;
    public double y;
    private boolean cancelled;
    private float previousYaw;
    public float pitch;
    public double z;
    private float previousPitch;
    public double x;
    public float yaw;

    public float getPreviousPitch() {
        return this.previousPitch;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setY(double d0) {
        this.y = d0;
    }

    public void setForcePositionAndRotationPacket(boolean flag) {
        this.forcePositionAndRotationPacket = flag;
    }

    @Override
    public void cancel() {
        this.cancelled = true;
    }

    public void setZ(double d0) {
        this.z = d0;
    }

    public void setX(double d0) {
        this.x = d0;
    }

    public float getYaw() {
        return this.yaw;
    }

    public MotionEvent(MotionPhase motionPhase) {
        super(motionPhase);
    }

    public void setYaw(float yawValue) {
        this.yaw = yawValue;
    }

    public void setPitch(float pitchValue) {
        this.pitch = pitchValue;
    }

    public boolean hasRotationChanged() {
        double d0 = this.yaw - this.previousYaw;
        double d1 = this.pitch - this.previousPitch;
        return Math.abs(d0) != 0.0 || Math.abs(d1) != 0.0;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    public double getY() {
        return this.y;
    }

    public double getX() {
        return this.x;
    }

    public double getZ() {
        return this.z;
    }

    public MotionEvent(
            float f, float f1, float f2, float f3, double d0, double d1, double d2, boolean flag) {
        this(f, f1, f2, f3, d0, d1, d2, flag, PacketEvent.method_d87039b07bd6961907f2());
    }

    private MotionEvent(
            float yawValue,
            float pitchValue,
            float previousYawValue,
            float previousPitchValue,
            double d0,
            double d1,
            double d2,
            boolean onGroundValue,
            boolean initialFlowFlag) {
        super(MotionPhase.PRE);

        this.yaw = yawValue;
        this.pitch = pitchValue;
        this.previousYaw = previousYawValue;
        this.previousPitch = previousPitchValue;
        this.y = d1;
        this.x = d0;
        this.z = d2;
        this.onGround = onGroundValue;
    }

    public float getPreviousYaw() {
        return this.previousYaw;
    }

    public boolean shouldForcePositionAndRotationPacket() {
        return this.forcePositionAndRotationPacket;
    }

    @Override
    public void setCancelled(boolean cancelledValue) {
        this.cancelled = cancelledValue;
    }
}
