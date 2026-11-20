package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.pathfinder.SwimNodeProcessor;

public class PathNavigateSwimmer extends PathNavigate {
    private static String[] packedTextFragments;

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.pathfinding.PathNavigateSwimmer.packedTextFragments[0] = new String("tQzx2b");
    }

    // endregion

    protected PathFinder getPathFinder() {
        return new PathFinder(new SwimNodeProcessor());
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    protected boolean isDirectPathBetweenPoints(Vec3 vec3, Vec3 vec3Value, int i, int j, int k) {
        RayTraceResult rayTraceResult =
                this.worldObj.rayTraceBlocks(
                        vec3,
                        new Vec3(
                                vec3Value.x,
                                vec3Value.y + this.theEntity.height * 0.5,
                                vec3Value.z),
                        false,
                        true,
                        false);
        return rayTraceResult == null || rayTraceResult.typeOfHit == RayHitType.MISS;
    }

    protected boolean canNavigate() {
        return this.isInLiquid();
    }

    protected Vec3 getEntityPosition() {
        return new Vec3(
                this.theEntity.posX,
                this.theEntity.posY + this.theEntity.height * 0.5,
                this.theEntity.posZ);
    }

    protected void removeSunnyPath() {
        super.removeSunnyPath();
    }

    // region String decoding support

    // endregion

    public PathNavigateSwimmer(EntityLiving entityLiving, World world) {
        super(entityLiving, world);
    }

    protected void cQ() {
        String text = PathNavigate.lB();
        Vec3 vec3 = this.getEntityPosition();
        float f = this.theEntity.width * this.theEntity.width;
        String textValue = text;
        byte b0 = 6;
        if (vec3.squareDistanceTo(
                        this.currentPath.getVectorFromIndex(
                                this.theEntity, this.currentPath.getCurrentPathIndex()))
                < f) {
            this.currentPath.incrementPathIndex();
        }

        int i =
                Math.min(
                        this.currentPath.getCurrentPathIndex() + b0,
                        this.currentPath.getCurrentPathLength() - 1);

        while (i > this.currentPath.getCurrentPathIndex()) {
            Vec3 vec3Value = this.currentPath.getVectorFromIndex(this.theEntity, i);
            if (vec3Value.squareDistanceTo(vec3) <= 36.0
                    && this.isDirectPathBetweenPoints(vec3, vec3Value, 0, 0, 0)) {
                this.currentPath.setCurrentPathIndex(i);
                if (textValue != null) {
                    break;
                }
            }

            i--;
            if (textValue == null) {
                break;
            }
        }

        this.checkForStuck(vec3);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            PathNavigate.b(packedTextFragments[0]);
        }
    }
}
