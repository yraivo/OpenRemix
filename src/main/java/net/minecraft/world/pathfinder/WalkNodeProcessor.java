package net.minecraft.world.pathfinder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class WalkNodeProcessor extends NodeProcessor {
    private boolean avoidsWater;
    private boolean shouldAvoidWater;
    private boolean canBreakDoors;
    private boolean canSwim;
    private boolean canEnterDoors;

    public void setEnterDoors(boolean canEnterDoorsValue) {
        this.canEnterDoors = canEnterDoorsValue;
    }

    private int getVerticalOffset(Entity entity, int i, int j, int k) {
        return func_176170_a(
                this.blockaccess,
                entity,
                i,
                j,
                k,
                this.entitySizeX,
                this.entitySizeY,
                this.entitySizeZ,
                this.avoidsWater,
                this.canBreakDoors,
                this.canEnterDoors);
    }

    public int findPathOptions(
            PathPoint[] pathPointArray,
            Entity entity,
            PathPoint pathPoint,
            PathPoint pathPointValue,
            float f) {
        NodeProcessor.method_650532ba484532b3efb0();

        int i = 0;
        byte b0 = 0;

        if (this.getVerticalOffset(entity, pathPoint.xCoord, pathPoint.yCoord + 1, pathPoint.zCoord)
                == 1) {
            b0 = 1;
        }

        PathPoint pathPointValue2 =
                this.getSafePoint(
                        entity, pathPoint.xCoord, pathPoint.yCoord, pathPoint.zCoord + 1, b0);
        PathPoint pathPointValue3 =
                this.getSafePoint(
                        entity, pathPoint.xCoord - 1, pathPoint.yCoord, pathPoint.zCoord, b0);
        PathPoint pathPointValue4 =
                this.getSafePoint(
                        entity, pathPoint.xCoord + 1, pathPoint.yCoord, pathPoint.zCoord, b0);
        PathPoint pathPointValue5 =
                this.getSafePoint(
                        entity, pathPoint.xCoord, pathPoint.yCoord, pathPoint.zCoord - 1, b0);
        if (pathPointValue2 != null
                && !pathPointValue2.visited
                && pathPointValue2.distanceTo(pathPointValue) < f) {
            pathPointArray[i++] = pathPointValue2;
        }

        if (pathPointValue3 != null
                && !pathPointValue3.visited
                && pathPointValue3.distanceTo(pathPointValue) < f) {
            pathPointArray[i++] = pathPointValue3;
        }

        if (pathPointValue4 != null
                && !pathPointValue4.visited
                && pathPointValue4.distanceTo(pathPointValue) < f) {
            pathPointArray[i++] = pathPointValue4;
        }

        if (pathPointValue5 != null
                && !pathPointValue5.visited
                && pathPointValue5.distanceTo(pathPointValue) < f) {
            pathPointArray[i++] = pathPointValue5;
        }

        return i;
    }

    private PathPoint getSafePoint(Entity entity, int i, int j, int k, int l) {
        int[] aint1 = NodeProcessor.method_650532ba484532b3efb0();
        PathPoint pathPoint = null;
        int[] aint = aint1;
        int verticalOffset = this.getVerticalOffset(entity, i, j, k);
        if (verticalOffset == 2) {
            return this.openPoint(i, j, k);
        }

        if (verticalOffset == 1) {
            pathPoint = this.openPoint(i, j, k);
        }

        if (pathPoint == null
                && l > 0
                && verticalOffset != -3
                && verticalOffset != -4
                && this.getVerticalOffset(entity, i, j + l, k) == 1) {
            pathPoint = this.openPoint(i, j + l, k);
            j += l;
        }

        if (pathPoint != null) {
            int j1 = 0;
            int k1 = 0;

            while (true) {
                if (j > 0) {
                    k1 = this.getVerticalOffset(entity, i, j - 1, k);
                    if (this.avoidsWater && k1 == -1) {
                        return null;
                    }

                    if (k1 == 1 || aint == null) {
                        if (j1++ >= entity.getMaxFallHeight()) {
                            return null;
                        }

                        if (--j <= 0) {
                            return null;
                        }

                        pathPoint = this.openPoint(i, j, k);

                        continue;
                    }
                }

                if (k1 == -2) {
                    return null;
                }
                break;
            }
        }

        return pathPoint;
    }

    public PathPoint getPathPointToCoords(Entity entity, double d0, double d1, double d2) {
        return this.openPoint(
                MathHelper.floor(d0 - entity.width / 2.0F),
                MathHelper.floor(d1),
                MathHelper.floor(d2 - entity.width / 2.0F));
    }

    public boolean getAvoidsWater() {
        return this.avoidsWater;
    }

    public void setAvoidsWater(boolean avoidsWaterValue) {
        this.avoidsWater = avoidsWaterValue;
    }

    public PathPoint getPathPointTo(Entity entity) {
        NodeProcessor.method_650532ba484532b3efb0();

        if (this.canSwim && entity.isInWater()) {
            int minY = (int) entity.getEntityBoundingBox().minY;
            Block block =
                    this.blockaccess
                            .getBlockState(
                                    new BlockPos(
                                            MathHelper.floor(entity.posX),
                                            minY,
                                            MathHelper.floor(entity.posZ)))
                            .getBlock();

            while (block == Blocks.flowing_water || block == Blocks.water) {
                block =
                        this.blockaccess
                                .getBlockState(
                                        new BlockPos(
                                                MathHelper.floor(entity.posX),
                                                ++minY,
                                                MathHelper.floor(entity.posZ)))
                                .getBlock();
            }

            this.avoidsWater = false;

            return this.openPoint(
                    MathHelper.floor(entity.getEntityBoundingBox().minX),
                    minY,
                    MathHelper.floor(entity.getEntityBoundingBox().minZ));
        }

        int j = MathHelper.floor(entity.getEntityBoundingBox().minY + 0.5);
        return this.openPoint(
                MathHelper.floor(entity.getEntityBoundingBox().minX),
                j,
                MathHelper.floor(entity.getEntityBoundingBox().minZ));
    }

    public static int func_176170_a(
            IBlockAccess iBlockAccess,
            Entity entity,
            int i,
            int j,
            int k,
            int l,
            int i1,
            int j1,
            boolean flag,
            boolean flag1,
            boolean flag2) {
        NodeProcessor.method_650532ba484532b3efb0();

        byte b0 = 0;

        BlockPos blockPos = new BlockPos(entity);

        for (int k1 = i; k1 < i + l; k1++) {
            for (int l1 = j; l1 < j + i1; l1++) {
                int i2 = k;

                while (i2 < k + j1) {
                    BlockPos blockPosValue = new BlockPos(k1, l1, i2);
                    Block block = iBlockAccess.getBlockState(blockPosValue).getBlock();
                    if (block.getMaterial() != Material.AIR) {
                        {
                            if (block != Blocks.trapdoor && block != Blocks.iron_trapdoor) {
                                if (block != Blocks.flowing_water && block != Blocks.water) {
                                    if (!flag2
                                            && block instanceof BlockDoor
                                            && block.getMaterial() == Material.wood) {
                                        return 0;
                                    }
                                    
                                } else {

                                    if (flag) {
                                        return -1;
                                    }

                                    b0 = 1;


                                }
                            } else {

                                b0 = 1;
                            }
                        }

                        if (entity.world.getBlockState(blockPosValue).getBlock()
                                instanceof BlockRailBase) {
                            if (!(entity.world.getBlockState(blockPos).getBlock()
                                            instanceof BlockRailBase)
                                    && !(entity.world
                                                    .getBlockState(blockPos.offsetDown())
                                                    .getBlock()
                                            instanceof BlockRailBase)) {
                                return -3;
                            }
                        } else if (!block.isPassable(iBlockAccess, blockPosValue)
                                && (!flag1
                                        || !(block instanceof BlockDoor)
                                        || block.getMaterial() != Material.wood)) {
                            if (block instanceof BlockFence
                                    || block instanceof BlockFenceGate
                                    || block instanceof BlockWall) {
                                return -3;
                            }

                            if (block == Blocks.trapdoor || block == Blocks.iron_trapdoor) {
                                return -4;
                            }

                            Material material = block.getMaterial();
                            if (material != Material.lava) {
                                return 0;
                            }

                            if (!entity.isInLava()) {
                                return -2;
                            }
                        }
                    }

                    i2++;
                }
                break;
            }

            return b0 != 0 ? 2 : 1;
        }

        return b0 != 0 ? 2 : 1;
    }

    public void initProcessor(IBlockAccess iBlockAccess, Entity entity) {
        super.initProcessor(iBlockAccess, entity);
        this.shouldAvoidWater = this.avoidsWater;
    }

    public void setCanSwim(boolean canSwimValue) {
        this.canSwim = canSwimValue;
    }

    public void postProcess() {
        super.postProcess();
        this.avoidsWater = this.shouldAvoidWater;
    }

    public boolean getCanSwim() {
        return this.canSwim;
    }

    public boolean getEnterDoors() {
        return this.canEnterDoors;
    }

    public void setBreakDoors(boolean canBreakDoorsValue) {
        this.canBreakDoors = canBreakDoorsValue;
    }
}
