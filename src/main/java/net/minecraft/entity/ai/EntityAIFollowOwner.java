package net.minecraft.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollowOwner extends EntityAIBase {
    private LivingEntity theOwner;
    private double followSpeed;
    World theWorld;
    private PathNavigate petPathfinder;
    private static String mC;
    float minDist;
    private EntityTameable thePet;

    private int sR;
    private boolean mv;
    float maxDist;

    static {
        mC = "Unsupported mob type for FollowOwnerGoal";
    }

    public void updateTask() {
        String text = EntityAIBase.cJ();
        this.thePet
                .getLookHelper()
                .setLookPositionWithEntity(
                        this.theOwner, 10.0F, this.thePet.getVerticalFaceSpeed());
        String textValue = text;
        if (!this.thePet.isSitting() && --this.sR <= 0) {
            this.sR = 10;
            if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.followSpeed)
                    && !this.thePet.getLeashed()
                    && this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0) {
                int i = MathHelper.floor(this.theOwner.posX) - 2;
                int j = MathHelper.floor(this.theOwner.posZ) - 2;
                int k = MathHelper.floor(this.theOwner.getEntityBoundingBox().minY);
                int l = 0;

                while (l <= 4) {
                    int i1 = 0;

                    while (i1 <= 4) {
                        if ((l < 1 || i1 < 1 || l > 3 || i1 > 3)
                                && World.doesBlockHaveSolidTopSurface(
                                        this.theWorld, new BlockPos(i + l, k - 1, j + i1))
                                && !this.theWorld
                                        .getBlockState(new BlockPos(i + l, k, j + i1))
                                        .getBlock()
                                        .isFullCube()
                                && !this.theWorld
                                        .getBlockState(new BlockPos(i + l, k + 1, j + i1))
                                        .getBlock()
                                        .isFullCube()) {
                            this.thePet.setLocationAndAngles(
                                    i + l + 0.5F,
                                    k,
                                    j + i1 + 0.5F,
                                    this.thePet.rotationYaw,
                                    this.thePet.rotationPitch);
                            this.petPathfinder.clearPathEntity();
                            return;
                        }

                        i1++;
                        if (textValue == null) {
                            break;
                        }
                    }

                    l++;
                    if (textValue == null) {
                        break;
                    }
                }
            }
        }
    }

    // region String decoding support

    // endregion

    public boolean shouldExecute() {
        LivingEntity bhmValue = this.thePet.func_180492_cm();
        if (bhmValue == null) {
            return false;
        }

        if (this.thePet.isSitting()) {
            return false;
        }

        if (this.thePet.getDistanceSqToEntity(bhmValue) < this.minDist * this.minDist) {
            return false;
        }

        this.theOwner = bhmValue;
        return true;
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }

    public void startExecuting() {
        this.sR = 0;
        this.mv = ((PathNavigateGround) this.thePet.getNavigator()).getAvoidsWater();
        ((PathNavigateGround) this.thePet.getNavigator()).setAvoidsWater(false);
    }

    // region String decoding support

    // endregion

    public void resetTask() {
        this.theOwner = null;
        this.petPathfinder.clearPathEntity();
        ((PathNavigateGround) this.thePet.getNavigator()).setAvoidsWater(true);
    }

    public boolean continueExecuting() {
        return !this.petPathfinder.noPath()
                && this.thePet.getDistanceSqToEntity(this.theOwner) > this.maxDist * this.maxDist
                && !this.thePet.isSitting();
    }

    public EntityAIFollowOwner(
            EntityTameable bhoValue,
            double followSpeedValue,
            float minDistValue,
            float maxDistValue) {
        this.thePet = bhoValue;
        this.theWorld = bhoValue.world;
        this.followSpeed = followSpeedValue;
        this.petPathfinder = bhoValue.getNavigator();
        this.minDist = minDistValue;
        this.maxDist = maxDistValue;
        this.setMutexBits(3);
        if (!(bhoValue.getNavigator() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException(mC);
        }
    }
}
