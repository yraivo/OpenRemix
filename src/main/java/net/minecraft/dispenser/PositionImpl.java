package net.minecraft.dispenser;

public class PositionImpl implements IPosition {
    protected double y;
    protected double z;
    protected double x;

    public double getZ() {
        return this.z;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public PositionImpl(double d0, double d1, double d2) {
        this.x = d0;
        this.y = d1;
        this.z = d2;
    }
}
