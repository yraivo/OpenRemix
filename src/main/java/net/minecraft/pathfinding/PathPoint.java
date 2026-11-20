package net.minecraft.pathfinding;

import net.minecraft.util.MathHelper;

public class PathPoint {
    float distanceToNext;
    private int hash;

    float tn;
    int index = -1;
    public int zCoord;
    public int yCoord;
    PathPoint previous;

    public int xCoord;
    public boolean visited;

    float ia;

    @Override
    public int hashCode() {
        return this.hash;
    }

    public PathPoint(int xCoordValue, int j, int k) {
        this.xCoord = xCoordValue;
        this.yCoord = j;
        this.zCoord = k;
        this.hash = makeHash(xCoordValue, j, k);
    }

    public boolean isAssigned() {
        return this.index >= 0;
    }

    @Override
    public String toString() {
        return this.xCoord + ", " + this.yCoord + ", " + this.zCoord;
    }


    public float distanceToSquared(PathPoint pathPoint) {
        float deltaXCoord = pathPoint.xCoord - this.xCoord;
        float deltaYCoord = pathPoint.yCoord - this.yCoord;
        float deltaZCoord = pathPoint.zCoord - this.zCoord;
        return deltaXCoord * deltaXCoord + deltaYCoord * deltaYCoord + deltaZCoord * deltaZCoord;
    }

    public static int makeHash(int i, int j, int k) {
        return j & 0xFF
                | (i & 32767) << 8
                | (k & 32767) << 24
                | (i < 0 ? Integer.MIN_VALUE : 0)
                | (k < 0 ? 32768 : 0);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PathPoint)) {
            return false;
        }

        PathPoint pathPoint = (PathPoint) object;
        return this.hash == pathPoint.hash
                && this.xCoord == pathPoint.xCoord
                && this.yCoord == pathPoint.yCoord
                && this.zCoord == pathPoint.zCoord;
    }

    public float distanceTo(PathPoint pathPoint) {
        float deltaXCoord = pathPoint.xCoord - this.xCoord;
        float deltaYCoord = pathPoint.yCoord - this.yCoord;
        float deltaZCoord = pathPoint.zCoord - this.zCoord;
        return MathHelper.sqrt(
                deltaXCoord * deltaXCoord + deltaYCoord * deltaYCoord + deltaZCoord * deltaZCoord);
    }
}
