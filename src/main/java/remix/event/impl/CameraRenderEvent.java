package remix.event.impl;

import remix.event.MotionPhase;
import remix.event.PhasedEvent;

public class CameraRenderEvent extends PhasedEvent {
    private float hX;
    private float hW;

    public CameraRenderEvent() {
        super(MotionPhase.POST);
    }

    public CameraRenderEvent(float f, float f1) {
        super(MotionPhase.PRE);
    }
}
