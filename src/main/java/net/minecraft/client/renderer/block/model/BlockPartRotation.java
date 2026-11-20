package net.minecraft.client.renderer.block.model;

import net.minecraft.util.FacingAxis;
import remix.compat.vecmath.Vector3f;

public class BlockPartRotation {
    public Vector3f origin;
    public FacingAxis axis;
    public boolean rescale;
    public float angle;

    public BlockPartRotation(
            Vector3f azgValue, FacingAxis aAKValue, float f, boolean flag) {
        this.origin = azgValue;
        this.axis = aAKValue;
        this.angle = f;
        this.rescale = flag;
    }
}
