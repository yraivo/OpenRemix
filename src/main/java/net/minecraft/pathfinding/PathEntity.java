package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class PathEntity {
    private int pathLength;
    private PathPoint[] points;
    private int currentPathIndex;

    public void setCurrentPathLength(int pathLengthValue) {
        this.pathLength = pathLengthValue;
    }

    public PathPoint getPathPointFromIndex(int i) {
        return this.points[i];
    }

    public Vec3 getPosition(Entity entity) {
        return this.getVectorFromIndex(entity, this.currentPathIndex);
    }

    public boolean isSamePath(PathEntity pathEntity) {
        String text = PathNavigate.lB();
        if (pathEntity == null) {
            return false;
        }

        if (pathEntity.points.length != this.points.length) {
            return false;
        }

        for (int i = 0; i < this.points.length; i++) {
            if (this.points[i].xCoord != pathEntity.points[i].xCoord
                    || this.points[i].yCoord != pathEntity.points[i].yCoord
                    || this.points[i].zCoord != pathEntity.points[i].zCoord) {
                return false;
            }
            break;
        }

        return true;
    }

    public int getCurrentPathLength() {
        return this.pathLength;
    }

    public boolean isFinished() {
        return this.currentPathIndex >= this.pathLength;
    }

    public void setCurrentPathIndex(int currentPathIndexValue) {
        this.currentPathIndex = currentPathIndexValue;
    }

    public PathEntity(PathPoint[] bxjValue) {
        this.points = bxjValue;
        this.pathLength = bxjValue.length;
    }

    public boolean isDestinationSame(Vec3 vec3) {
        PathPoint finalPathPoint = this.getFinalPathPoint();
        return finalPathPoint == null
                ? false
                : finalPathPoint.xCoord == (int) vec3.x && finalPathPoint.zCoord == (int) vec3.z;
    }

    public void incrementPathIndex() {
        this.currentPathIndex++;
    }

    public int getCurrentPathIndex() {
        return this.currentPathIndex;
    }

    public Vec3 getVectorFromIndex(Entity entity, int i) {
        double d0 = this.points[i].xCoord + (int) (entity.width + 1.0F) * 0.5;
        double yCoord = this.points[i].yCoord;
        double d2 = this.points[i].zCoord + (int) (entity.width + 1.0F) * 0.5;
        return new Vec3(d0, yCoord, d2);
    }

    public PathPoint getFinalPathPoint() {
        return this.pathLength > 0 ? this.points[this.pathLength - 1] : null;
    }
}
