package remix.event;

public class PhasedRenderEvent extends PhasedEvent {
    public PhasedRenderEvent(MotionPhase motionPhase) {
        super(motionPhase);
    }
}
