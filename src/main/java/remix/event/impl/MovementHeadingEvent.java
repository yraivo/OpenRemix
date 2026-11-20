package remix.event.impl;

import remix.event.Cancellable;
import remix.event.MotionPhase;
import remix.event.PhasedEvent;

public class MovementHeadingEvent extends PhasedEvent implements Cancellable {
    private float strafe;
    private boolean cancelled;
    private float forward;

    public MovementHeadingEvent(float forward, float strafe) {
        super(MotionPhase.PRE);
        this.forward = forward;
        this.strafe = strafe;
    }

    public void setForward(float f) {
        this.forward = f;
    }

    public MovementHeadingEvent() {
        super(MotionPhase.POST);
    }

    public void setCancelled(boolean flag) {
        this.cancelled = flag;
    }

    public float getForward() {
        return this.forward;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setStrafe(float f) {
        this.strafe = f;
    }

    public float getStrafe() {
        return this.strafe;
    }

    public void cancel() {
        this.cancelled = true;
    }
}
