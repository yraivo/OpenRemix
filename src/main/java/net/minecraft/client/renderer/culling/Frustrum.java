package net.minecraft.client.renderer.culling;

import net.minecraft.util.BoundingBox;

public class Frustrum implements ICamera {
    private ClippingHelper clippingHelper;
    private double yPosition;
    private double zPosition;
    private double xPosition;

    public Frustrum() {
        this(ClippingHelperImpl.getInstance());
    }

    public void setPosition(double p171_0, double p171_1, double p171_2) {
        this.xPosition = p171_0;
        this.yPosition = p171_1;
        this.zPosition = p171_2;
    }

    public boolean isBoundingBoxInFrustum(BoundingBox boundingBox) {
        return this.isBoxInFrustum(
                boundingBox.minX,
                boundingBox.minY,
                boundingBox.minZ,
                boundingBox.maxX,
                boundingBox.maxY,
                boundingBox.maxZ);
    }

    public boolean isBoxInFrustum(
            double d0, double d1, double d2, double d3, double d4, double d5) {
        return this.clippingHelper.isBoxInFrustum(
                d0 - this.xPosition, d1 - this.yPosition, d2 - this.zPosition, d3 - this.xPosition, d4 - this.yPosition, d5 - this.zPosition);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Frustrum(ClippingHelper aBKValue) {
        super();
        this.clippingHelper = aBKValue;
    }
}
