package net.minecraft.world.pathfinder;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public abstract class NodeProcessor {
    protected int entitySizeZ;
    protected IntHashMap pointMap = new IntHashMap();
    private static int[] f;
    protected IBlockAccess blockaccess;
    protected int entitySizeX;
    protected int entitySizeY;

    public abstract PathPoint getPathPointTo(Entity entity);

    public abstract int findPathOptions(
            PathPoint[] pathPointArray,
            Entity entity,
            PathPoint pathPoint,
            PathPoint pathPointValue,
            float f);

    public void initProcessor(IBlockAccess iBlockAccess, Entity entity) {
        method_650532ba484532b3efb0();
        this.blockaccess = iBlockAccess;
        this.pointMap.clearMap();
        this.entitySizeX = MathHelper.floor(entity.width + 1.0F);
        this.entitySizeY = MathHelper.floor(entity.height + 1.0F);
        this.entitySizeZ = MathHelper.floor(entity.width + 1.0F);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(new int[5]);
        }
    }

    public static int[] method_650532ba484532b3efb0() {
        return f;
    }

    public static void b(int[] aint) {
        f = aint;
    }

    static {
        if (method_650532ba484532b3efb0() == null) {
            b(new int[1]);
        }
    }

    public void postProcess() {}

    protected PathPoint openPoint(int i, int j, int k) {
        int l = PathPoint.makeHash(i, j, k);
        PathPoint pathPoint = (PathPoint) this.pointMap.lookup(l);
        if (pathPoint == null) {
            pathPoint = new PathPoint(i, j, k);
            this.pointMap.addKey(l, pathPoint);
        }

        return pathPoint;
    }

    public abstract PathPoint getPathPointToCoords(Entity entity, double d0, double d1, double d2);
}
