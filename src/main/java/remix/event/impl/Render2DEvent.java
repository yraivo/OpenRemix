package remix.event.impl;

import remix.event.Event;
import remix.event.MotionPhase;

import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent implements Event {
    private ScaledResolution resolution;
    protected MotionPhase phase;

    public void setResolution(ScaledResolution resolutionValue) {
        this.resolution = resolutionValue;
    }

    public MotionPhase getPhase() {
        return this.phase;
    }

    public int getHeight() {
        return this.resolution.getHeight();
    }

    public void setPhase(MotionPhase phaseValue) {
        this.phase = phaseValue;
    }

    public Render2DEvent(ScaledResolution resolutionValue, boolean flag) {
        this.phase = MotionPhase.POST;
        this.resolution = resolutionValue;
    }

    public ScaledResolution getResolution() {
        return this.resolution;
    }

    public int getWidth() {
        return this.resolution.getWidth();
    }

    public Render2DEvent(ScaledResolution resolutionValue) {
        this.phase = MotionPhase.PRE;
        this.resolution = resolutionValue;
    }
}
