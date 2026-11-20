package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

import com.google.common.collect.Lists;

import java.util.List;

public class EntityAIMoveThroughVillage extends EntityAIBase {
    private VillageDoorInfo doorInfo;
    private double movementSpeed;
    private List doorList = Lists.newArrayList();
    private PathEntity entityPathNavigate;

    private boolean isNocturnal;
    private static String te;
    private EntityCreature theEntity;

    // region String decoding support

    // endregion

    public boolean continueExecuting() {
        if (this.theEntity.getNavigator().noPath()) {
            return false;
        }

        float f = this.theEntity.width + 4.0F;
        return this.theEntity.getDistanceSq(this.doorInfo.getDoorBlockPos()) > f * f;
    }

    public boolean shouldExecute() {
        this.resizeDoorList();
        if (this.isNocturnal && this.theEntity.world.isDaytime()) {
            return false;
        }

        Village village =
                this.theEntity
                        .world
                        .getVillageCollection()
                        .getNearestVillage(new BlockPos(this.theEntity), 0);
        if (village == null) {
            return false;
        }

        this.doorInfo = this.findNearestDoor(village);
        if (this.doorInfo == null) {
            return false;
        }

        PathNavigateGround navigator = (PathNavigateGround) this.theEntity.getNavigator();
        boolean enterDoors = navigator.getEnterDoors();
        navigator.setBreakDoors(false);
        this.entityPathNavigate = navigator.getPathToPos(this.doorInfo.getDoorBlockPos());
        navigator.setBreakDoors(enterDoors);
        if (this.entityPathNavigate != null) {
            return true;
        }

        Vec3 vec3 =
                RandomPositionGenerator.findRandomTargetBlockTowards(
                        this.theEntity,
                        10,
                        7,
                        new Vec3(
                                this.doorInfo.getDoorBlockPos().getX(),
                                this.doorInfo.getDoorBlockPos().getY(),
                                this.doorInfo.getDoorBlockPos().getZ()));
        if (vec3 == null) {
            return false;
        }

        navigator.setBreakDoors(false);
        this.entityPathNavigate =
                this.theEntity.getNavigator().getPathToXYZ(vec3.x, vec3.y, vec3.z);
        navigator.setBreakDoors(enterDoors);
        return this.entityPathNavigate != null;
    }

    public void resetTask() {
        if (this.theEntity.getNavigator().noPath()
                || this.theEntity.getDistanceSq(this.doorInfo.getDoorBlockPos())
                        < 16.0) {
            this.doorList.add(this.doorInfo);
        }
    }

    public void startExecuting() {
        this.theEntity.getNavigator().setPath(this.entityPathNavigate, this.movementSpeed);
    }

    static {
        te = "Unsupported mob for MoveThroughVillageGoal";
    }

    private VillageDoorInfo findNearestDoor(Village village) {
        String text = EntityAIBase.cJ();
        VillageDoorInfo villageDoorInfo = null;
        int MAX_VALUE = Integer.MAX_VALUE;
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_5873 =
                        (village.getVillageDoorInfoList()).iterator();
                (__recoveryIterator171_5873).hasNext(); ) {
            VillageDoorInfo villageDoorInfoValue =
                    (VillageDoorInfo) (__recoveryIterator171_5873).next();
            int j =
                    villageDoorInfoValue.getDistanceSquared(
                            MathHelper.floor(this.theEntity.posX),
                            MathHelper.floor(this.theEntity.posY),
                            MathHelper.floor(this.theEntity.posZ));
            if (j < MAX_VALUE && !this.doesDoorListContain(villageDoorInfoValue)) {
                villageDoorInfo = villageDoorInfoValue;
                MAX_VALUE = j;
            }

            if (textValue == null) {
                break;
            }
        }

        return villageDoorInfo;
    }

    private boolean doesDoorListContain(VillageDoorInfo villageDoorInfo) {
        for (java.util.Iterator<?> __recoveryIterator171_6758 = (this.doorList).iterator();
                (__recoveryIterator171_6758).hasNext(); ) {
            VillageDoorInfo villageDoorInfoValue =
                    (VillageDoorInfo) (__recoveryIterator171_6758).next();
            if (villageDoorInfo.getDoorBlockPos().equals(villageDoorInfoValue.getDoorBlockPos())) {
                return true;
            }
        }

        return false;
    }

    public EntityAIMoveThroughVillage(
            EntityCreature bhjValue, double movementSpeedValue, boolean isNocturnalValue) {
        this.theEntity = bhjValue;
        this.movementSpeed = movementSpeedValue;
        this.isNocturnal = isNocturnalValue;
        this.setMutexBits(1);
        if (!(bhjValue.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException(te);
        }
    }

    // region String decoding support

    // endregion

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    private void resizeDoorList() {
        if (this.doorList.size() > 15) {
            this.doorList.remove(0);
        }
    }
}
