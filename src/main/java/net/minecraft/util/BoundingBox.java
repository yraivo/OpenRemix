package net.minecraft.util;

import net.minecraft.entity.Entity;

public class BoundingBox {

    private static String[] packedTextFragments;
    public double maxX;
    public double minZ;
    public double minX;
    public double maxY;
    public double minY;
    public double maxZ;

    private static String[] g;

    // region Static initialization and string tables
    static {
        net.minecraft.util.BoundingBox.packedTextFragments = new String[3];
        net.minecraft.util.BoundingBox.packedTextFragments[0] = new String("]");
        String[] astring1 = new String[2];
        b(astring1);
    }

    // endregion

    public BoundingBox(
            double p171_0,
            double p171_1,
            double p171_2,
            double p171_3,
            double p171_4,
            double p171_5) {
        this.minX = java.lang.Math.min(p171_0, p171_3);
        this.minY = java.lang.Math.min(p171_1, p171_4);
        this.minZ = java.lang.Math.min(p171_2, p171_5);
        this.maxX = java.lang.Math.max(p171_0, p171_3);
        this.maxY = java.lang.Math.max(p171_1, p171_4);
        this.maxZ = java.lang.Math.max(p171_2, p171_5);
    }

    public boolean isVecInside(Vec3 vec3) {
        return !(vec3.x > this.minX) || !(vec3.x < this.maxX)
                ? false
                : (vec3.y > this.minY && vec3.y < this.maxY
                        ? vec3.z > this.minZ && vec3.z < this.maxZ
                        : false);
    }

    private boolean method_031d4ea405e96be8cf43(Vec3 vec3) {
        return vec3 == null
                ? false
                : vec3.x >= this.minX
                        && vec3.x <= this.maxX
                        && vec3.y >= this.minY
                        && vec3.y <= this.maxY;
    }

    public double calculateXOffset(BoundingBox boundingBox, double d0) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (boundingBox.maxY > this.minY
                && boundingBox.minY < this.maxY
                && boundingBox.maxZ > this.minZ
                && boundingBox.minZ < this.maxZ) {
            if (d0 > 0.0 && boundingBox.maxX <= this.minX) {
                double d1 = this.minX - boundingBox.maxX;
                if (!(d1 < d0)) {
                    return d0;
                }

                d0 = d1;
                if (text != null) {
                    return d0;
                }
            }

            if (d0 < 0.0 && boundingBox.minX >= this.maxX) {
                double d2 = this.maxX - boundingBox.minX;
                if (d2 > d0) {
                    d0 = d2;
                }
            }

            return d0;
        } else {
            return d0;
        }
    }

    // region String decoding support

    // endregion

    @Override
    public String toString() {
        return "box["
                + this.minX
                + ", "
                + this.minY
                + ", "
                + this.minZ
                + " -> "
                + this.maxX
                + ", "
                + this.maxY
                + ", "
                + this.maxZ
                + packedTextFragments[0];
    }

    public BoundingBox expand(double p171_0, double p171_1, double p171_2) {
        double d3 = this.minX - p171_0;
        double d4 = this.minY - p171_1;
        double d5 = this.minZ - p171_2;
        double d6 = this.maxX + p171_0;
        double d7 = this.maxY + p171_1;
        double d8 = this.maxZ + p171_2;
        return new net.minecraft.util.BoundingBox(d3, d4, d5, d6, d7, d8);
    }

    public static void b(String[] astring) {
        g = astring;
    }

    public boolean intersectsWith(BoundingBox boundingBox) {
        return !(boundingBox.maxX > this.minX) || !(boundingBox.minX < this.maxX)
                ? false
                : (boundingBox.maxY > this.minY && boundingBox.minY < this.maxY
                        ? boundingBox.maxZ > this.minZ && boundingBox.minZ < this.maxZ
                        : false);
    }

    public BoundingBox addCoord(double d0, double d1, double d2) {
        String text;
        double d3;
        double d4;
        double d5;
        double d6;
        double d7;
        double d8;
        {
            String textValue = Vec3i.getLegacyCoordinateFlowMarker();
            d3 = this.minX;
            d4 = this.minY;
            d5 = this.minZ;
            d6 = this.maxX;
            d7 = this.maxY;
            text = textValue;
            d8 = this.maxZ;
            if (d0 < 0.0) {
                d3 += d0;
                if (!(text != null)) {

                    if (d0 > 0.0) {
                        d6 += d0;
                    }
                }
            } else {

                if (d0 > 0.0) {
                    d6 += d0;
                }
            }
        }

        {
            if (d1 < 0.0) {
                d4 += d1;
                if (!(text != null)) {

                    if (d1 > 0.0) {
                        d7 += d1;
                    }
                }
            } else {

                if (d1 > 0.0) {
                    d7 += d1;
                }
            }
        }

        if (d2 < 0.0) {
            d5 += d2;
            if (text != null) {
                return new BoundingBox(d3, d4, d5, d6, d7, d8);
            }
        }

        if (d2 > 0.0) {
            d8 += d2;
        }

        return new BoundingBox(d3, d4, d5, d6, d7, d8);
    }

    public BoundingBox(BlockPos blockPos, BlockPos blockPosValue) {
        this.minX = blockPos.getX();
        this.minY = blockPos.getY();
        this.minZ = blockPos.getZ();
        this.maxX = blockPosValue.getX();
        this.maxY = blockPosValue.getY();
        this.maxZ = blockPosValue.getZ();
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public double calculateYOffset(BoundingBox boundingBox, double d0) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (boundingBox.maxX > this.minX
                && boundingBox.minX < this.maxX
                && boundingBox.maxZ > this.minZ
                && boundingBox.minZ < this.maxZ) {
            if (d0 > 0.0 && boundingBox.maxY <= this.minY) {
                double d1 = this.minY - boundingBox.maxY;
                if (!(d1 < d0)) {
                    return d0;
                }

                d0 = d1;
                if (text != null) {
                    return d0;
                }
            }

            if (d0 < 0.0 && boundingBox.minY >= this.maxY) {
                double d2 = this.maxY - boundingBox.minY;
                if (d2 > d0) {
                    d0 = d2;
                }
            }

            return d0;
        } else {
            return d0;
        }
    }

    public BoundingBox offset(double p171_0, double p171_1, double p171_2) {
        return new net.minecraft.util.BoundingBox(
                this.minX + p171_0,
                this.minY + p171_1,
                this.minZ + p171_2,
                this.maxX + p171_0,
                this.maxY + p171_1,
                this.maxZ + p171_2);
    }

    public double getAverageEdgeLength() {
        double d0 = this.maxX - this.minX;
        double d1 = this.maxY - this.minY;
        double d2 = this.maxZ - this.minZ;
        return (d0 + d1 + d2) / 3.0;
    }

    public static String[] b() {
        return g;
    }

    private boolean method_aa21924409ecc94a2791(Vec3 vec3) {
        return vec3 == null
                ? false
                : vec3.y >= this.minY
                        && vec3.y <= this.maxY
                        && vec3.z >= this.minZ
                        && vec3.z <= this.maxZ;
    }

    public RayTraceResult calculateIntercept(Vec3 vec3, Vec3 vec3Value) {
        Vec3 intermediateWithXValue = vec3.getIntermediateWithXValue(vec3Value, this.minX);
        Vec3 intermediateWithXValueValue = vec3.getIntermediateWithXValue(vec3Value, this.maxX);
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        Vec3 intermediateWithYValue = vec3.getIntermediateWithYValue(vec3Value, this.minY);
        Vec3 intermediateWithYValueValue = vec3.getIntermediateWithYValue(vec3Value, this.maxY);
        Vec3 intermediateWithZValue = vec3.getIntermediateWithZValue(vec3Value, this.minZ);
        String textValue = text;
        Vec3 intermediateWithZValueValue = vec3.getIntermediateWithZValue(vec3Value, this.maxZ);
        if (!this.method_aa21924409ecc94a2791(intermediateWithXValue)) {
            intermediateWithXValue = null;
        }

        if (!this.method_aa21924409ecc94a2791(intermediateWithXValueValue)) {
            intermediateWithXValueValue = null;
        }

        if (!this.method_701ebd1280b576644a1e(intermediateWithYValue)) {
            intermediateWithYValue = null;
        }

        if (!this.method_701ebd1280b576644a1e(intermediateWithYValueValue)) {
            intermediateWithYValueValue = null;
        }

        if (!this.method_031d4ea405e96be8cf43(intermediateWithZValue)) {
            intermediateWithZValue = null;
        }

        if (!this.method_031d4ea405e96be8cf43(intermediateWithZValueValue)) {
            intermediateWithZValueValue = null;
        }

        Vec3 vec3Value2 = null;
        if (intermediateWithXValue != null) {
            vec3Value2 = intermediateWithXValue;
        }

        if (intermediateWithXValueValue != null
                && (vec3Value2 == null
                        || vec3.squareDistanceTo(intermediateWithXValueValue)
                                < vec3.squareDistanceTo(vec3Value2))) {
            vec3Value2 = intermediateWithXValueValue;
        }

        if (intermediateWithYValue != null
                && (vec3Value2 == null
                        || vec3.squareDistanceTo(intermediateWithYValue)
                                < vec3.squareDistanceTo(vec3Value2))) {
            vec3Value2 = intermediateWithYValue;
        }

        if (intermediateWithYValueValue != null
                && (vec3Value2 == null
                        || vec3.squareDistanceTo(intermediateWithYValueValue)
                                < vec3.squareDistanceTo(vec3Value2))) {
            vec3Value2 = intermediateWithYValueValue;
        }

        if (intermediateWithZValue != null
                && (vec3Value2 == null
                        || vec3.squareDistanceTo(intermediateWithZValue)
                                < vec3.squareDistanceTo(vec3Value2))) {
            vec3Value2 = intermediateWithZValue;
        }

        if (intermediateWithZValueValue != null
                && (vec3Value2 == null
                        || vec3.squareDistanceTo(intermediateWithZValueValue)
                                < vec3.squareDistanceTo(vec3Value2))) {
            vec3Value2 = intermediateWithZValueValue;
        }

        if (vec3Value2 == null) {
            return null;
        }

        Facing facing = null;
        if (vec3Value2 == intermediateWithXValue) {
            facing = Facing.WEST;
        } else if (vec3Value2 == intermediateWithXValueValue) {
            facing = Facing.EAST;
        } else if (vec3Value2 == intermediateWithYValue) {
            facing = Facing.DOWN;
        } else if (vec3Value2 == intermediateWithYValueValue) {
            facing = Facing.UP;
        } else if (vec3Value2 == intermediateWithZValue) {
            facing = Facing.NORTH;
        } else {
            facing = Facing.SOUTH;
        }

        RayTraceResult rayTraceResult = new RayTraceResult(vec3Value2, facing);
        if (textValue == null) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }

        return rayTraceResult;
    }

    public static BoundingBox fromBounds(
            double p171_0,
            double p171_1,
            double p171_2,
            double p171_3,
            double p171_4,
            double p171_5) {
        double d6 = java.lang.Math.min(p171_0, p171_3);
        double d7 = java.lang.Math.min(p171_1, p171_4);
        double d8 = java.lang.Math.min(p171_2, p171_5);
        double d9 = java.lang.Math.max(p171_0, p171_3);
        double d10 = java.lang.Math.max(p171_1, p171_4);
        double d11 = java.lang.Math.max(p171_2, p171_5);
        return new net.minecraft.util.BoundingBox(d6, d7, d8, d9, d10, d11);
    }

    public BoundingBox offsetBounds(
            double p171_0,
            double p171_1,
            double p171_2,
            double p171_3,
            double p171_4,
            double p171_5) {
        return new net.minecraft.util.BoundingBox(
                this.minX + p171_0,
                this.minY + p171_1,
                this.minZ + p171_2,
                this.maxX + p171_3,
                this.maxY + p171_4,
                this.maxZ + p171_5);
    }

    public double calculateZOffset(BoundingBox boundingBox, double d0) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        if (boundingBox.maxX > this.minX
                && boundingBox.minX < this.maxX
                && boundingBox.maxY > this.minY
                && boundingBox.minY < this.maxY) {
            if (d0 > 0.0 && boundingBox.maxZ <= this.minZ) {
                double d1 = this.minZ - boundingBox.maxZ;
                if (!(d1 < d0)) {
                    return d0;
                }

                d0 = d1;
                if (text != null) {
                    return d0;
                }
            }

            if (d0 < 0.0 && boundingBox.minZ >= this.maxZ) {
                double d2 = this.maxZ - boundingBox.minZ;
                if (d2 > d0) {
                    d0 = d2;
                }
            }

            return d0;
        } else {
            return d0;
        }
    }

    public BoundingBox contract(double p171_0, double p171_1, double p171_2) {
        double d3 = this.minX + p171_0;
        double d4 = this.minY + p171_1;
        double d5 = this.minZ + p171_2;
        double d6 = this.maxX - p171_0;
        double d7 = this.maxY - p171_1;
        double d8 = this.maxZ - p171_2;
        return new net.minecraft.util.BoundingBox(d3, d4, d5, d6, d7, d8);
    }

    public BoundingBox union(BoundingBox boundingBox) {
        double d0 = java.lang.Math.min(this.minX, boundingBox.minX);
        double d1 = java.lang.Math.min(this.minY, boundingBox.minY);
        double d2 = java.lang.Math.min(this.minZ, boundingBox.minZ);
        double d3 = java.lang.Math.max(this.maxX, boundingBox.maxX);
        double d4 = java.lang.Math.max(this.maxY, boundingBox.maxY);
        double d5 = java.lang.Math.max(this.maxZ, boundingBox.maxZ);
        return new net.minecraft.util.BoundingBox(d0, d1, d2, d3, d4, d5);
    }

    private boolean method_701ebd1280b576644a1e(Vec3 vec3) {
        return vec3 == null
                ? false
                : vec3.x >= this.minX
                        && vec3.x <= this.maxX
                        && vec3.z >= this.minZ
                        && vec3.z <= this.maxZ;
    }

    // region String decoding support

    // endregion
}
