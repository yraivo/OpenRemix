package net.minecraft.command;

public class CommandBaseCoordinateArg {
    private double uj;
    private double um;
    private boolean relative;

    public double fh() {
        return this.uj;
    }

    protected CommandBaseCoordinateArg(double d0, double d1, boolean flag) {
        this.uj = d0;
        this.um = d1;
        this.relative = flag;
    }

    public boolean isRelative() {
        return this.relative;
    }

    public double cl() {
        return this.um;
    }
}
