package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class PathNavigateClimber extends PathNavigateGround {
    private BlockPos targetPosition;

    public PathNavigateClimber(EntityLiving entityLiving, World world) {
        super(entityLiving, world);
    }

    public void onUpdateNavigation() {
        String text = PathNavigate.lB();
        if (!this.noPath()) {
            super.onUpdateNavigation();
            if (text != null) {
                return;
            }
        }

        if (this.targetPosition != null) {
            double d0 = this.theEntity.width * this.theEntity.width;
            if (this.theEntity.getDistanceSqToCenter(this.targetPosition) >= d0
                    && (this.theEntity.posY <= this.targetPosition.getY()
                            || this.theEntity.getDistanceSqToCenter(
                                            new BlockPos(
                                                    this.targetPosition.getX(),
                                                    MathHelper.floor(this.theEntity.posY),
                                                    this.targetPosition.getZ()))
                                    >= d0)) {
                this.theEntity
                        .getMoveHelper()
                        .setMoveTo(
                                this.targetPosition.getX(),
                                this.targetPosition.getY(),
                                this.targetPosition.getZ(),
                                this.speed);
                if (text != null) {
                    return;
                }
            }

            this.targetPosition = null;
        }
    }

    public PathEntity getPathToEntityLiving(Entity entity) {
        this.targetPosition = new net.minecraft.util.BlockPos(entity);
        return super.getPathToEntityLiving(entity);
    }

    public PathEntity getPathToPos(BlockPos blockPos) {
        this.targetPosition = blockPos;
        return super.getPathToPos(blockPos);
    }

    public boolean tryMoveToEntityLiving(Entity entity, double speedValue) {
        net.minecraft.pathfinding.PathEntity pathToEntityLiving = this.getPathToEntityLiving(entity);
        if (pathToEntityLiving != null) {
            return this.setPath(pathToEntityLiving, speedValue);
        }

        this.targetPosition = new net.minecraft.util.BlockPos(entity);
        this.speed = speedValue;
        return true;
    }
}
