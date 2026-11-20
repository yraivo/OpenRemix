package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.pathfinder.NodeProcessor;

public class PathFinder {
    private NodeProcessor nodeProcessor;
    private PathPoint[] pathOptions;
    private Path path = new Path();

    private PathEntity addToPath(
            Entity entity, PathPoint pathPoint, PathPoint pathPointValue, float f) {
        pathPoint.ia = 0.0F;
        pathPoint.distanceToNext = pathPoint.distanceToSquared(pathPointValue);
        String text = PathNavigate.lB();
        pathPoint.tn = pathPoint.distanceToNext;
        this.path.clearPath();
        this.path.addPoint(pathPoint);
        String textValue = text;
        PathPoint pathPointValue2 = pathPoint;

        while (!this.path.isPathEmpty()) {
            PathPoint bzJ = this.path.dequeue();
            if (bzJ.equals(pathPointValue)) {
                return this.createEntityPath(pathPoint, pathPointValue);
            }

            if (bzJ.distanceToSquared(pathPointValue)
                    < pathPointValue2.distanceToSquared(pathPointValue)) {
                pathPointValue2 = bzJ;
            }

            bzJ.visited = true;
            int pathOptionsValue =
                    this.nodeProcessor.findPathOptions(
                            this.pathOptions, entity, bzJ, pathPointValue, f);
            int j = 0;

            while (true) {
                if (j < pathOptionsValue) {
                    PathPoint pathPointValue3 = this.pathOptions[j];
                    float f1 = bzJ.ia + bzJ.distanceToSquared(pathPointValue3);
                    if (f1 < f * 2.0F
                            && (!pathPointValue3.isAssigned() || f1 < pathPointValue3.ia)) {
                        pathPointValue3.previous = bzJ;
                        pathPointValue3.ia = f1;
                        pathPointValue3.distanceToNext = pathPointValue3.distanceToSquared(pathPointValue);
                        if (pathPointValue3.isAssigned()) {
                            this.path.changeDistance(
                                    pathPointValue3, pathPointValue3.ia + pathPointValue3.distanceToNext);
                            if (!(textValue != null)) {

                                pathPointValue3.tn = pathPointValue3.ia + pathPointValue3.distanceToNext;
                                this.path.addPoint(pathPointValue3);
                            }
                        } else {

                            pathPointValue3.tn = pathPointValue3.ia + pathPointValue3.distanceToNext;
                            this.path.addPoint(pathPointValue3);
                        }
                    }

                    j++;
                    if (textValue != null) {
                        continue;
                    }
                }

                if (textValue == null) {
                    return pathPointValue2 == pathPoint
                            ? null
                            : this.createEntityPath(pathPoint, pathPointValue2);
                }
                break;
            }
        }

        return pathPointValue2 == pathPoint
                ? null
                : this.createEntityPath(pathPoint, pathPointValue2);
    }

    public PathEntity createEntityPathTo(
            IBlockAccess iBlockAccess, Entity entity, BlockPos blockPos, float f) {
        return this.createEntityPathTo(
                iBlockAccess,
                entity,
                blockPos.getX() + 0.5F,
                blockPos.getY() + 0.5F,
                blockPos.getZ() + 0.5F,
                f);
    }

    private PathEntity createEntityPathTo(
            IBlockAccess iBlockAccess, Entity entity, double d0, double d1, double d2, float f) {
        this.path.clearPath();
        this.nodeProcessor.initProcessor(iBlockAccess, entity);
        PathPoint pathPoint = this.nodeProcessor.getPathPointTo(entity);
        PathPoint pathPointValue = this.nodeProcessor.getPathPointToCoords(entity, d0, d1, d2);
        PathEntity pathEntity = this.addToPath(entity, pathPoint, pathPointValue, f);
        this.nodeProcessor.postProcess();
        return pathEntity;
    }

    private PathEntity createEntityPath(PathPoint pathPoint, PathPoint pathPointValue) {
        String text = PathNavigate.lB();
        int i = 1;
        String textValue = text;
        PathPoint pathPointValue2 = pathPointValue;

        while (pathPointValue2.previous != null) {
            i++;
            pathPointValue2 = pathPointValue2.previous;
            if (textValue == null) {
                break;
            }
        }

        PathPoint[] pathPointArray = new PathPoint[i];
        pathPointValue2 = pathPointValue;
        pathPointArray[--i] = pathPointValue;

        while (pathPointValue2.previous != null) {
            pathPointValue2 = pathPointValue2.previous;
            pathPointArray[--i] = pathPointValue2;
            if (textValue == null) {
                break;
            }
        }

        return new PathEntity(pathPointArray);
    }

    public PathEntity createEntityPathTo(
            IBlockAccess iBlockAccess, Entity entity, Entity entityValue, float p171_3) {
        return this.createEntityPathTo(
                iBlockAccess,
                entity,
                entityValue.posX,
                entityValue.getEntityBoundingBox().minY,
                entityValue.posZ,
                p171_3);
    }

    public PathFinder(NodeProcessor bxlValue) {
        this.pathOptions = new PathPoint[32];
        this.nodeProcessor = bxlValue;
    }
}
