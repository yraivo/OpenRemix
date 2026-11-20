package net.minecraft.util;

import net.minecraft.entity.Entity;

public class RayTraceResult {
    private BlockPos blockPos;
    public Facing sideHit;

    public Entity entityHit;
    public RayHitType typeOfHit;

    public Vec3 hitVec;

    public RayTraceResult(
            RayHitType typeOfHitValue, Vec3 vec3, Facing sideHitValue, BlockPos blockPosValue) {
        this.typeOfHit = typeOfHitValue;
        this.blockPos = blockPosValue;
        this.sideHit = sideHitValue;
        this.hitVec = new Vec3(vec3.x, vec3.y, vec3.z);
    }

    public RayTraceResult(Entity entityHitValue, Vec3 hitVecValue) {
        this.typeOfHit = RayHitType.ENTITY;
        this.entityHit = entityHitValue;
        this.hitVec = hitVecValue;
    }


    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public RayTraceResult(Vec3 vec3, Facing facing, BlockPos blockPosValue) {
        this(RayHitType.BLOCK, vec3, facing, blockPosValue);
    }

    public RayTraceResult(Vec3 vec3, Facing facing) {
        this(RayHitType.BLOCK, vec3, facing, BlockPos.ORIGIN);
    }

    public RayTraceResult(Entity entity) {
        this(entity, new Vec3(entity.posX, entity.posY, entity.posZ));
    }

    @Override
    public String toString() {
        return "HitResult{type="
                + this.typeOfHit
                + ", blockpos="
                + this.blockPos
                + ", f="
                + this.sideHit
                + ", pos="
                + this.hitVec
                + ", entity="
                + this.entityHit
                + '}';
    }
}
