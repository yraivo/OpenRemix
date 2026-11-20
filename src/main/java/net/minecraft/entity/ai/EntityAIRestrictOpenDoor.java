package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIRestrictOpenDoor extends EntityAIBase {
    private VillageDoorInfo frontDoor;
    private EntityCreature entityObj;

    private static String e;

    public EntityAIRestrictOpenDoor(EntityCreature bhjValue) {
        this.entityObj = bhjValue;
        if (!(bhjValue.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException(e);
        }
    }

    public void updateTask() {
        this.frontDoor.incrementDoorOpeningRestrictionCounter();
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    // region String decoding support

    // endregion

    public boolean shouldExecute() {
        if (this.entityObj.world.isDaytime()) {
            return false;
        }

        BlockPos blockPos = new BlockPos(this.entityObj);
        Village village =
                this.entityObj.world.getVillageCollection().getNearestVillage(blockPos, 16);
        if (village == null) {
            return false;
        }

        this.frontDoor = village.getNearestDoor(blockPos);
        return this.frontDoor == null
                ? false
                : this.frontDoor.getDistanceToInsideBlockSq(blockPos) < 2.25;
    }

    public void resetTask() {
        ((PathNavigateGround) this.entityObj.getNavigator()).setBreakDoors(true);
        ((PathNavigateGround) this.entityObj.getNavigator()).setEnterDoors(true);
        this.frontDoor = null;
    }

    static {
        e = "Unsupported mob type for RestrictOpenDoorGoal";
    }

    // region String decoding support

    // endregion

    public void startExecuting() {
        ((PathNavigateGround) this.entityObj.getNavigator()).setBreakDoors(false);
        ((PathNavigateGround) this.entityObj.getNavigator()).setEnterDoors(false);
    }

    public boolean continueExecuting() {
        return this.entityObj.world.isDaytime()
                ? false
                : !this.frontDoor.getIsDetachedFromVillageFlag()
                        && this.frontDoor.func_179850_c(new BlockPos(this.entityObj));
    }
}
