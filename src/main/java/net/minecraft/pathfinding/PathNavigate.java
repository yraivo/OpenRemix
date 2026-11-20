package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

public abstract class PathNavigate {
    private static String[] packedTextFragments;
    protected double speed;
    private Vec3 lastPosCheck = new Vec3(0.0, 0.0, 0.0);

    private int ticksAtLastPos;
    private static String vI;
    protected World worldObj;
    private int totalTicks;
    protected PathEntity currentPath;
    private PathFinder pathFinder;

    private IAttributeInstance pathSearchRange;
    protected EntityLiving theEntity;
    private float heightRequirement = 1.0F;

    protected abstract boolean canNavigate();

    public PathEntity getPathToPos(BlockPos blockPos) {
        if (!this.canNavigate()) {
            return null;
        }

        float fx = this.getPathSearchRange();
        this.worldObj.theProfiler.startSection("pathfind");
        BlockPos blockPosValue = new BlockPos(this.theEntity);
        int i = (int) (fx + 8.0F);
        ChunkCache chunkCache =
                new ChunkCache(
                        this.worldObj,
                        blockPosValue.add(-i, -i, -i),
                        blockPosValue.add(i, i, i),
                        0);
        PathEntity pathEntity =
                this.pathFinder.createEntityPathTo(chunkCache, this.theEntity, blockPos, fx);
        this.worldObj.theProfiler.endSection();
        return pathEntity;
    }

    protected void cQ() {
        Vec3 vec3 = this.getEntityPosition();
        int currentPathLength = this.currentPath.getCurrentPathLength();
        String text = lB();
        int j = this.currentPath.getCurrentPathIndex();
        String textValue = text;

        while (j < this.currentPath.getCurrentPathLength()) {
            if (this.currentPath.getPathPointFromIndex(j).yCoord != (int) vec3.y) {
                currentPathLength = j;
                if (textValue != null) {
                    break;
                }

                Entity.setLegacyGlobalFlowMarker(new String[1]);
            }

            j++;
            if (textValue == null) {
                break;
            }
        }

        float fx = this.theEntity.width * this.theEntity.width * this.heightRequirement;
        int k = this.currentPath.getCurrentPathIndex();

        while (k < currentPathLength) {
            Vec3 vec3Value = this.currentPath.getVectorFromIndex(this.theEntity, k);
            if (vec3.squareDistanceTo(vec3Value) < fx) {
                this.currentPath.setCurrentPathIndex(k + 1);
            }

            k++;
            if (textValue == null) {
                break;
            }
        }

        k = MathHelper.ceil(this.theEntity.width);
        int j1 = (int) this.theEntity.height + 1;
        int l = k;
        int i1 = currentPathLength - 1;

        while (i1 >= this.currentPath.getCurrentPathIndex()) {
            if (this.isDirectPathBetweenPoints(
                    vec3, this.currentPath.getVectorFromIndex(this.theEntity, i1), k, j1, l)) {
                this.currentPath.setCurrentPathIndex(i1);
                if (textValue != null) {
                    break;
                }
            }

            i1--;
            if (textValue == null) {
                break;
            }
        }

        this.checkForStuck(vec3);
    }

    protected void removeSunnyPath() {}

    public PathEntity getPathToEntityLiving(Entity entity) {
        if (!this.canNavigate()) {
            return null;
        }

        float fx = this.getPathSearchRange();
        this.worldObj.theProfiler.startSection("pathfind");
        BlockPos blockPos = new BlockPos(this.theEntity).offsetUp();
        int i = (int) (fx + 16.0F);
        ChunkCache chunkCache =
                new ChunkCache(this.worldObj, blockPos.add(-i, -i, -i), blockPos.add(i, i, i), 0);
        PathEntity pathEntity =
                this.pathFinder.createEntityPathTo(chunkCache, this.theEntity, entity, fx);
        this.worldObj.theProfiler.endSection();
        return pathEntity;
    }

    public boolean tryMoveToEntityLiving(Entity entity, double d0) {
        PathEntity pathToEntityLiving = this.getPathToEntityLiving(entity);
        return pathToEntityLiving != null ? this.setPath(pathToEntityLiving, d0) : false;
    }

    protected abstract boolean isDirectPathBetweenPoints(
            Vec3 vec3, Vec3 vec3Value, int i, int j, int k);

    public static void b(String text) {
        vI = text;
    }

    public void clearPathEntity() {
        this.currentPath = null;
    }

    public boolean setPath(PathEntity bxHValue, double speedValue) {
        if (bxHValue == null) {
            this.currentPath = null;
            return false;
        }

        if (!bxHValue.isSamePath(this.currentPath)) {
            this.currentPath = bxHValue;
        }

        this.removeSunnyPath();
        if (this.currentPath.getCurrentPathLength() == 0) {
            return false;
        }

        this.speed = speedValue;
        Vec3 bxGValue = this.getEntityPosition();
        this.ticksAtLastPos = this.totalTicks;
        this.lastPosCheck = bxGValue;
        return true;
    }

    public final PathEntity getPathToXYZ(double d0, double d1, double d2) {
        return this.getPathToPos(
                new BlockPos(MathHelper.floor(d0), (int) d1, MathHelper.floor(d2)));
    }

    public PathEntity getPath() {
        return this.currentPath;
    }

    protected void checkForStuck(Vec3 bxGValue) {
        if (this.totalTicks - this.ticksAtLastPos > 100) {
            if (bxGValue.squareDistanceTo(this.lastPosCheck) < 2.25) {
                this.clearPathEntity();
            }

            this.ticksAtLastPos = this.totalTicks;
            this.lastPosCheck = bxGValue;
        }
    }

    protected boolean isInLiquid() {
        return this.theEntity.isInWater() || this.theEntity.isInLava();
    }

    public void setSpeed(double speedValue) {
        this.speed = speedValue;
    }

    static {
        net.minecraft.pathfinding.PathNavigate.packedTextFragments = new String[2];
        net.minecraft.pathfinding.PathNavigate.packedTextFragments[1] = new String("dkXTqb");
        String s2 = packedTextFragments[1];
        b(s2);
    }

    public boolean noPath() {
        return this.currentPath == null || this.currentPath.isFinished();
    }

    public static String lB() {
        return vI;
    }

    public float getPathSearchRange() {
        return (float) this.pathSearchRange.getAttributeValue();
    }

    // region String decoding support

    // endregion

    public PathNavigate(EntityLiving bjnValue, World awfValue) {
        this.theEntity = bjnValue;
        this.worldObj = awfValue;
        this.pathSearchRange = bjnValue.getEntityAttribute(SharedMonsterAttributes.followRange);
        this.pathFinder = this.getPathFinder();
    }

    public boolean tryMoveToXYZ(double d0, double d1, double d2, double d3) {
        PathEntity pathToXYZ =
                this.getPathToXYZ(MathHelper.floor(d0), (int) d1, MathHelper.floor(d2));
        return this.setPath(pathToXYZ, d3);
    }

    public void setHeightRequirement(float heightRequirementValue) {
        this.heightRequirement = heightRequirementValue;
    }

    protected abstract Vec3 getEntityPosition();

    protected abstract PathFinder getPathFinder();

    // region String decoding support

    // endregion

    public void onUpdateNavigation() {
        String text = lB();
        this.totalTicks++;
        String textValue = text;
        if (!this.noPath()) {
            label29:
            {
                if (this.canNavigate()) {
                    this.cQ();
                    if (textValue != null) {
                        break label29;
                    }
                }

                if (this.currentPath != null
                        && this.currentPath.getCurrentPathIndex()
                                < this.currentPath.getCurrentPathLength()) {
                    Vec3 vec3 = this.getEntityPosition();
                    Vec3 vec3Value =
                            this.currentPath.getVectorFromIndex(
                                    this.theEntity, this.currentPath.getCurrentPathIndex());
                    if (vec3.y > vec3Value.y
                            && !this.theEntity.onGround
                            && MathHelper.floor(vec3.x) == MathHelper.floor(vec3Value.x)
                            && MathHelper.floor(vec3.z) == MathHelper.floor(vec3Value.z)) {
                        this.currentPath.setCurrentPathIndex(
                                this.currentPath.getCurrentPathIndex() + 1);
                    }
                }
            }

            if (!this.noPath()) {
                Vec3 vec3Value2 = this.currentPath.getPosition(this.theEntity);
                if (vec3Value2 != null) {
                    this.theEntity
                            .getMoveHelper()
                            .setMoveTo(vec3Value2.x, vec3Value2.y, vec3Value2.z, this.speed);
                }
            }
        }
    }

    // region String decoding support

    // endregion
}
