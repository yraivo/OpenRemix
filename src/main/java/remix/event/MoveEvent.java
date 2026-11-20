package remix.event;

import remix.event.MotionPhase;
import remix.event.PhasedEvent;

public class MoveEvent extends PhasedEvent {
    private double z;
    private double y;
    private double x;

    public double getZ() {
        return this.z;
    }

    public void setZ(double d0) {
        this.z = d0;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double d0) {
        this.y = d0;
    }

    public MoveEvent(double d0, double d1, double d2) {
        super(MotionPhase.PRE);
        this.x = d0;
        this.y = d1;
        this.z = d2;
    }

    public double getX() {
        return this.x;
    }

    public MoveEvent() {
        super(MotionPhase.POST);
    }

    public void setX(double d0) {
        this.x = d0;
    }
}
