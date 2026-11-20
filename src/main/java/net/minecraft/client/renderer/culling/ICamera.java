package net.minecraft.client.renderer.culling;

import net.minecraft.util.BoundingBox;

public interface ICamera {
    boolean isBoundingBoxInFrustum(BoundingBox boundingBox);

    void setPosition(double d0, double d1, double d2);
}
