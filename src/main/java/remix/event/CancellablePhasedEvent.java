package remix.event;

public class CancellablePhasedEvent extends PhasedEvent implements Cancellable {
    private boolean cancelled;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public CancellablePhasedEvent(MotionPhase motionPhase) {
        super(motionPhase);
    }

    public void cancel() {
        this.cancelled = true;
    }

    public void setCancelled(boolean cancelledValue) {
        this.cancelled = cancelledValue;
    }
}
