package remix.event.impl;

import remix.event.Cancellable;
import remix.event.MotionPhase;
import remix.event.PhasedEvent;

public class StepEvent extends PhasedEvent implements Cancellable {
    private boolean cancelled;
    private float height;

    public void setCancelled(boolean cancelledValue) {
        this.cancelled = cancelledValue;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public float getHeight() {
        return this.height;
    }

    public StepEvent(MotionPhase motionPhase, float heightValue) {
        super(motionPhase);
        this.height = heightValue;
    }

    public void setHeight(float heightValue) {
        this.height = heightValue;
    }
}
