package remix.event;

public abstract class PhasedEvent implements Event {
    protected MotionPhase phase;

    public PhasedEvent(MotionPhase phaseValue) {
        CancellableEvent.method_da2ac5c94686ee11e9e6();

        this.phase = phaseValue;
    }

    public void setPhase(MotionPhase phaseValue) {
        this.phase = phaseValue;
    }

    public MotionPhase getPhase() {
        return this.phase;
    }
}
