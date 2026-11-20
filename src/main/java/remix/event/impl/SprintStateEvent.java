package remix.event.impl;

import remix.event.Event;

public class SprintStateEvent implements Event {
    private boolean sprinting;

    public void setSprinting(boolean sprintingValue) {
        this.sprinting = sprintingValue;
    }

    public boolean isSprinting() {
        return this.sprinting;
    }

    public SprintStateEvent(boolean sprintingValue) {
        this.sprinting = sprintingValue;
    }
}
