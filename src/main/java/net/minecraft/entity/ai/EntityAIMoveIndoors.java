package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.village.VillageDoorInfo;

public class EntityAIMoveIndoors extends EntityAIBase {
    private EntityCreature entityObj;
    private int insidePosX = -1;
    private VillageDoorInfo doorInfo;
    private int insidePosZ = -1;

    public boolean shouldExecute() {
        net.minecraft.util.BlockPos blockPos = new net.minecraft.util.BlockPos(this.entityObj);
        if ((!this.entityObj.world.isDaytime()
                        || this.entityObj.world.isRaining()
                                && !this.entityObj.world.getBiomeGenForCoords(blockPos).canRain())
                && !this.entityObj.world.provider.getHasNoSky()) {
            if (this.entityObj.getRNG().nextInt(50) != 0) {
                return false;
            }

            if (this.insidePosX != -1
                    && this.entityObj.getDistanceSq(
                                    this.insidePosX, this.entityObj.posY, this.insidePosZ)
                            < 4.0) {
                return false;
            }

            net.minecraft.village.Village village =
                    this.entityObj.world.getVillageCollection().getNearestVillage(blockPos, 14);
            if (village == null) {
                return false;
            }

            this.doorInfo = village.getDoorInfo(blockPos);
            return this.doorInfo != null;
        } else {
            return false;
        }
    }

    public void startExecuting() {
        this.insidePosX = -1;
        String text = EntityAIBase.cJ();
        BlockPos blockPos = this.doorInfo.getInsideBlockPos();
        String textValue = text;
        int x = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();
        if (this.entityObj.getDistanceSq(blockPos) > 256.0) {
            Vec3 vec3 =
                    RandomPositionGenerator.findRandomTargetBlockTowards(
                            this.entityObj, 14, 3, new Vec3(x + 0.5, j, k + 0.5));
            if (vec3 != null) {
                this.entityObj.getNavigator().tryMoveToXYZ(vec3.x, vec3.y, vec3.z, 1.0);
            }

            if (textValue != null) {
                return;
            }
        }

        this.entityObj.getNavigator().tryMoveToXYZ(x + 0.5, j, k + 0.5, 1.0);
    }

    public boolean continueExecuting() {
        return !this.entityObj.getNavigator().noPath();
    }

    public EntityAIMoveIndoors(EntityCreature bhjValue) {
        this.entityObj = bhjValue;
        this.setMutexBits(1);
    }

    public void resetTask() {
        this.insidePosX = this.doorInfo.getInsideBlockPos().getX();
        this.insidePosZ = this.doorInfo.getInsideBlockPos().getZ();
        this.doorInfo = null;
    }
}
