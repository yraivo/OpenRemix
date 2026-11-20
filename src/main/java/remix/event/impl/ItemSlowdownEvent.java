package remix.event.impl;

import remix.event.CancellableEvent;

public class ItemSlowdownEvent extends CancellableEvent {
    private double multiplier;

    public void setMultiplier(double multiplierValue) {
        this.multiplier = multiplierValue;
    }

    public ItemSlowdownEvent(double multiplierValue) {
        this.multiplier = multiplierValue;
    }

    public double getMultiplier() {
        return this.multiplier;
    }
}
