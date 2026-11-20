package net.minecraft.tileentity;

public class TileEntityBeaconBeamSegment {
    private float[] colors;
    private int height;

    public float[] getColors() {
        return this.colors;
    }

    public TileEntityBeaconBeamSegment(float[] afloat) {
        this.colors = afloat;
        this.height = 1;
    }

    public int getHeight() {
        return this.height;
    }

    protected void incrementHeight() {
        this.height++;
    }
}
