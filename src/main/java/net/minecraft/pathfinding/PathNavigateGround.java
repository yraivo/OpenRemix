package net.minecraft.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

import java.util.Iterator;

public class PathNavigateGround extends PathNavigate {
    private boolean shouldAvoidSun;
    protected WalkNodeProcessor nodeProcessor;

    protected PathFinder getPathFinder() {
        this.nodeProcessor = new WalkNodeProcessor();
        this.nodeProcessor.setEnterDoors(true);
        return new PathFinder(this.nodeProcessor);
    }

    public void setAvoidSun(boolean shouldAvoidSunValue) {
        this.shouldAvoidSun = shouldAvoidSunValue;
    }

    public void setEnterDoors(boolean p171_0) {
        this.nodeProcessor.setEnterDoors(p171_0);
    }

    protected void removeSunnyPath() {
        String text = PathNavigate.lB();
        super.removeSunnyPath();
        String textValue = text;
        if (this.shouldAvoidSun) {
            if (this.worldObj.isAgainstSky(
                    new BlockPos(
                            MathHelper.floor(this.theEntity.posX),
                            (int) (this.theEntity.getEntityBoundingBox().minY + 0.5),
                            MathHelper.floor(this.theEntity.posZ)))) {
                return;
            }

            int i = 0;

            while (i < this.currentPath.getCurrentPathLength()) {
                PathPoint pathPointFromIndex = this.currentPath.getPathPointFromIndex(i);
                if (this.worldObj.isAgainstSky(
                        new BlockPos(
                                pathPointFromIndex.xCoord,
                                pathPointFromIndex.yCoord,
                                pathPointFromIndex.zCoord))) {
                    this.currentPath.setCurrentPathLength(i - 1);
                    return;
                }

                i++;
                if (textValue == null) {
                    break;
                }
            }
        }
    }

    private boolean method_65a74e4388a498324da6(
            int i, int j, int k, int l, int i1, int j1, Vec3 vec3, double d0, double d1) {
        String text = PathNavigate.lB();
        Iterator iterator =
                BlockPos.getAllInBox(
                                new BlockPos(i, j, k),
                                new BlockPos(i + l - 1, j + i1 - 1, k + j1 - 1))
                        .iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            BlockPos blockPos = (BlockPos) iterator.next();
            double d2 = blockPos.getX() + 0.5 - vec3.x;
            double d3 = blockPos.getZ() + 0.5 - vec3.z;
            if (d2 * d0 + d3 * d1 >= 0.0) {
                Block block = this.worldObj.getBlockState(blockPos).getBlock();
                if (!block.isPassable(this.worldObj, blockPos)) {
                    return false;
                }
            }

            if (textValue == null) {
                break;
            }
        }

        return true;
    }

    public PathNavigateGround(EntityLiving entityLiving, World world) {
        super(entityLiving, world);
    }

    private int getPathablePosY() {
        if (this.theEntity.isInWater() && this.getCanSwim()) {
            int minY = (int) this.theEntity.getEntityBoundingBox().minY;
            Block block =
                    this.worldObj
                            .getBlockState(
                                    new BlockPos(
                                            MathHelper.floor(this.theEntity.posX),
                                            minY,
                                            MathHelper.floor(this.theEntity.posZ)))
                            .getBlock();
            int j = 0;

            while (block == Blocks.flowing_water || block == Blocks.water) {
                block =
                        this.worldObj
                                .getBlockState(
                                        new BlockPos(
                                                MathHelper.floor(this.theEntity.posX),
                                                ++minY,
                                                MathHelper.floor(this.theEntity.posZ)))
                                .getBlock();
                if (++j > 16) {
                    return (int) this.theEntity.getEntityBoundingBox().minY;
                }
            }

            return minY;
        } else {
            return (int) (this.theEntity.getEntityBoundingBox().minY + 0.5);
        }
    }

    public boolean getCanSwim() {
        return this.nodeProcessor.getCanSwim();
    }

    private boolean method_fa588fbe5690afc75822(
            int i, int j, int k, int l, int i1, int j1, Vec3 vec3, double d0, double d1) {
        int k1 = i - l / 2;
        String text = PathNavigate.lB();
        int l1 = k - j1 / 2;
        String textValue = text;
        if (!this.method_65a74e4388a498324da6(k1, j, l1, l, i1, j1, vec3, d0, d1)) {
            return false;
        }

        int i2 = k1;

        while (i2 < k1 + l) {
            int j2 = l1;

            while (true) {
                if (j2 < l1 + j1) {
                    double d2 = i2 + 0.5 - vec3.x;
                    double d3 = j2 + 0.5 - vec3.z;
                    if (d2 * d0 + d3 * d1 >= 0.0) {
                        Block block =
                                this.worldObj.getBlockState(new BlockPos(i2, j - 1, j2)).getBlock();
                        Material material = block.getMaterial();
                        if (material == Material.AIR) {
                            return false;
                        }

                        if (material == Material.water && !this.theEntity.isInWater()) {
                            return false;
                        }

                        if (material == Material.lava) {
                            return false;
                        }
                    }

                    j2++;
                    if (textValue != null) {
                        continue;
                    }
                }

                i2++;
                if (textValue == null) {
                    return true;
                }
                break;
            }
        }

        return true;
    }

    public void setCanSwim(boolean p171_0) {
        this.nodeProcessor.setCanSwim(p171_0);
    }

    public void setAvoidsWater(boolean p171_0) {
        this.nodeProcessor.setAvoidsWater(p171_0);
    }

    public boolean getEnterDoors() {
        return this.nodeProcessor.getEnterDoors();
    }

    protected Vec3 getEntityPosition() {
        return new Vec3(this.theEntity.posX, this.getPathablePosY(), this.theEntity.posZ);
    }

    protected boolean isDirectPathBetweenPoints(Vec3 vec3, Vec3 vec3Value, int i, int j, int k) {
        int l = MathHelper.floor(vec3.x);
        int i1 = MathHelper.floor(vec3.z);
        String text = PathNavigate.lB();
        double d0 = vec3Value.x - vec3.x;
        String textValue = text;
        double d1 = vec3Value.z - vec3.z;
        double d2 = d0 * d0 + d1 * d1;
        if (d2 < 1.0E-8) {
            return false;
        }

        double d3 = 1.0 / Math.sqrt(d2);
        d0 *= d3;
        d1 *= d3;
        i += 2;
        k += 2;
        if (!this.method_fa588fbe5690afc75822(l, (int) vec3.y, i1, i, j, k, vec3, d0, d1)) {
            return false;
        }

        i -= 2;
        k -= 2;
        double d4 = 1.0 / Math.abs(d0);
        double d5 = 1.0 / Math.abs(d1);
        double d6 = l * 1 - vec3.x;
        double d7 = i1 * 1 - vec3.z;
        if (d0 >= 0.0) {
            d6++;
        }

        if (d1 >= 0.0) {
            d7++;
        }

        d6 /= d0;
        d7 /= d1;
        int j1 = d0 < 0.0 ? -1 : 1;
        int k1 = d1 < 0.0 ? -1 : 1;
        int l1 = MathHelper.floor(vec3Value.x);
        int i2 = MathHelper.floor(vec3Value.z);
        int j2 = l1 - l;
        int k2 = i2 - i1;

        while (j2 * j1 > 0 || k2 * k1 > 0) {
            {
                if (d6 < d7) {
                    d6 += d4;
                    l += j1;
                    j2 = l1 - l;
                    if (!(textValue != null)) {

                        d7 += d5;
                        i1 += k1;
                        k2 = i2 - i1;
                    }
                } else {

                    d7 += d5;
                    i1 += k1;
                    k2 = i2 - i1;
                }
            }

            if (!this.method_fa588fbe5690afc75822(l, (int) vec3.y, i1, i, j, k, vec3, d0, d1)) {
                return false;
            }
        }

        return true;
    }

    protected boolean canNavigate() {
        return this.theEntity.onGround
                || this.getCanSwim() && this.isInLiquid()
                || this.theEntity.isRiding()
                        && this.theEntity instanceof EntityZombie
                        && this.theEntity.ridingEntity instanceof EntityChicken;
    }

    public void setBreakDoors(boolean p171_0) {
        this.nodeProcessor.setBreakDoors(p171_0);
    }

    public boolean getAvoidsWater() {
        return this.nodeProcessor.getAvoidsWater();
    }
}
