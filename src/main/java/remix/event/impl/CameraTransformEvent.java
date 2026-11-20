package remix.event.impl;

import remix.event.MotionPhase;
import remix.event.PhasedRenderEvent;

public class CameraTransformEvent extends PhasedRenderEvent {
    public CameraTransformEvent() {
        super(MotionPhase.PRE);
    }
}
