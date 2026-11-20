package remix.event.impl;

import remix.event.MotionPhase;
import remix.event.PhasedRenderEvent;

public class Render3DEvent extends PhasedRenderEvent {
    private float partialTicks;

    public Render3DEvent(MotionPhase motionPhase, float partialTicksValue) {
        super(motionPhase);
        this.partialTicks = partialTicksValue;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}
