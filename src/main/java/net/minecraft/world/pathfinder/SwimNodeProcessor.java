package net.minecraft.world.pathfinder;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class SwimNodeProcessor extends NodeProcessor {
    public void initProcessor(IBlockAccess iBlockAccess, Entity entity) {
        super.initProcessor(iBlockAccess, entity);
    }

    public void postProcess() {
        super.postProcess();
    }

    public int findPathOptions(
            PathPoint[] pathPointArray,
            Entity entity,
            PathPoint pathPoint,
            PathPoint pathPointValue,
            float f) {
        NodeProcessor.method_650532ba484532b3efb0();

        int i = 0;
        Facing[] facingArray = Facing.values();

        for (Facing facing : facingArray) {
            PathPoint pathPointValue2 =
                    this.getSafePoint(
                            entity,
                            pathPoint.xCoord + facing.getFrontOffsetX(),
                            pathPoint.yCoord + facing.getFrontOffsetY(),
                            pathPoint.zCoord + facing.getFrontOffsetZ());
            if (pathPointValue2 != null
                    && !pathPointValue2.visited
                    && pathPointValue2.distanceTo(pathPointValue) < f) {
                pathPointArray[i++] = pathPointValue2;
            }
        }

        return i;
    }

    public PathPoint getPathPointToCoords(Entity entity, double d0, double d1, double d2) {
        return this.openPoint(
                MathHelper.floor(d0 - entity.width / 2.0F),
                MathHelper.floor(d1 + 0.5),
                MathHelper.floor(d2 - entity.width / 2.0F));
    }

    private int method_f368e5828a245b010c9e(Entity entity, int i, int j, int k) {
        NodeProcessor.method_650532ba484532b3efb0();

        int l = i;

        while (l < i + this.entitySizeX) {
            int i1 = j;

            while (true) {
                if (i1 < j + this.entitySizeY) {
                    int j1 = k;

                    while (j1 < k + this.entitySizeZ) {
                        BlockPos blockPos = new BlockPos(l, i1, j1);
                        Block block = this.blockaccess.getBlockState(blockPos).getBlock();
                        if (block.getMaterial() != Material.water) {
                            return 0;
                        }

                        j1++;
                    }

                    i1++;

                    continue;
                }

                l++;

                break;
            }
        }

        return -1;
    }

    public PathPoint getPathPointTo(Entity entity) {
        return this.openPoint(
                MathHelper.floor(entity.getEntityBoundingBox().minX),
                MathHelper.floor(entity.getEntityBoundingBox().minY + 0.5),
                MathHelper.floor(entity.getEntityBoundingBox().minZ));
    }

    private PathPoint getSafePoint(Entity entity, int i, int j, int k) {
        int l = this.method_f368e5828a245b010c9e(entity, i, j, k);
        return l == -1 ? this.openPoint(i, j, k) : null;
    }
}
