package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

public class EntityAIFleeSun extends EntityAIBase {
    private double shelterZ;
    private double shelterY;
    private World theWorld;
    private double movementSpeed;
    private EntityCreature theCreature;
    private double shelterX;

    private Vec3 findPossibleShelter() {
        String text = EntityAIBase.cJ();
        Random random = this.theCreature.getRNG();
        String textValue = text;
        BlockPos blockPos =
                new BlockPos(
                        this.theCreature.posX,
                        this.theCreature.getEntityBoundingBox().minY,
                        this.theCreature.posZ);
        int i = 0;

        while (i < 10) {
            BlockPos blockPosValue =
                    blockPos.add(
                            random.nextInt(20) - 10,
                            random.nextInt(6) - 3,
                            random.nextInt(20) - 10);
            if (!this.theWorld.isAgainstSky(blockPosValue)
                    && this.theCreature.getBlockPathWeight(blockPosValue) < 0.0F) {
                return new Vec3(blockPosValue.getX(), blockPosValue.getY(), blockPosValue.getZ());
            }

            i++;
            if (textValue == null) {
                break;
            }
        }

        return null;
    }

    public boolean continueExecuting() {
        return !this.theCreature.getNavigator().noPath();
    }

    public EntityAIFleeSun(EntityCreature bhjValue, double movementSpeedValue) {
        this.theCreature = bhjValue;
        this.movementSpeed = movementSpeedValue;
        this.theWorld = bhjValue.world;
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        if (!this.theWorld.isDaytime()) {
            return false;
        }

        if (!this.theCreature.isBurning()) {
            return false;
        }

        if (!this.theWorld.isAgainstSky(
                new BlockPos(
                        this.theCreature.posX,
                        this.theCreature.getEntityBoundingBox().minY,
                        this.theCreature.posZ))) {
            return false;
        }

        Vec3 vec3 = this.findPossibleShelter();
        if (vec3 == null) {
            return false;
        }

        this.shelterX = vec3.x;
        this.shelterY = vec3.y;
        this.shelterZ = vec3.z;
        return true;
    }

    public void startExecuting() {
        this.theCreature
                .getNavigator()
                .tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
    }
}
