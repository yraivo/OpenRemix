package net.minecraft.entity;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class EntityCreature extends EntityLiving {

    private static int MA;
    public static AttributeModifier FLEEING_SPEED_MODIFIER;
    private boolean isMovementAITaskSet;
    public static UUID FLEEING_SPEED_MODIFIER_UUID;
    private EntityAIBase aiBase;
    private float maximumHomeDistance;
    private BlockPos homePosition = BlockPos.ORIGIN;

    public BlockPos getHomePosition() {
        return this.homePosition;
    }

    protected void mM() {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        super.mM();
        String[] astring = astring1;
        if (this.getLeashed()
                && this.getLeashedToEntity() != null
                && this.getLeashedToEntity().world == this.world) {
            Entity entity = this.getLeashedToEntity();
            this.setHomePosAndDistance(
                    new BlockPos((int) entity.posX, (int) entity.posY, (int) entity.posZ), 5);
            float distanceToEntity = this.getDistanceToEntity(entity);
            if (this instanceof EntityTameable && ((EntityTameable) this).isSitting()) {
                if (distanceToEntity > 10.0F) {
                    this.clearLeashed(true, true);
                }

                return;
            }

            if (!this.isMovementAITaskSet) {
                this.tasks.addTask(2, this.aiBase);
                if (this.getNavigator() instanceof PathNavigateGround) {
                    ((PathNavigateGround) this.getNavigator()).setAvoidsWater(false);
                }

                this.isMovementAITaskSet = true;
            }

            this.func_142017_o(distanceToEntity);
            if (distanceToEntity > 4.0F) {
                this.getNavigator().tryMoveToEntityLiving(entity, 1.0);
            }

            if (distanceToEntity > 6.0F) {
                double d0 = (entity.posX - this.posX) / distanceToEntity;
                double d1 = (entity.posY - this.posY) / distanceToEntity;
                double d2 = (entity.posZ - this.posZ) / distanceToEntity;
                this.motionX = this.motionX + d0 * Math.abs(d0) * 0.4;
                this.motionY = this.motionY + d1 * Math.abs(d1) * 0.4;
                this.motionZ = this.motionZ + d2 * Math.abs(d2) * 0.4;
            }

            if (distanceToEntity > 10.0F) {
                this.clearLeashed(true, true);
            }

            if (astring == null) {
                return;
            }
        }

        if (!this.getLeashed() && this.isMovementAITaskSet) {
            this.isMovementAITaskSet = false;
            this.tasks.removeTask(this.aiBase);
            if (this.getNavigator() instanceof PathNavigateGround) {
                ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
            }

            this.detachHome();
        }
    }

    // region String decoding support

    // endregion

    public void detachHome() {
        this.maximumHomeDistance = -1.0F;
    }

    protected void func_142017_o(float f) {}

    public boolean hasHome() {
        return this.maximumHomeDistance != -1.0F;
    }

    public float getBlockPathWeight(BlockPos blockPos) {
        return 0.0F;
    }

    public static int getClickGuiChildTraversalToken() {
        int i = getLegacyClickGuiChildLimitState();
        return i == 0 ? 33 : 0;
    }

    public EntityCreature(World worldValue) {
        super(worldValue);
        this.maximumHomeDistance = -1.0F;
        this.aiBase = new EntityAIMoveTowardsRestriction(this, 1.0);
    }

    public static void setLegacyClickGuiChildLimitState(int i) {
        MA = i;
    }

    public boolean isWithinHomeDistanceFromPosition(BlockPos blockPos) {
        return this.maximumHomeDistance == -1.0F
                ? true
                : this.homePosition.distanceSq(blockPos)
                        < this.maximumHomeDistance * this.maximumHomeDistance;
    }

    public static int getLegacyClickGuiChildLimitState() {
        return MA;
    }

    public float getMaximumHomeDistance() {
        return this.maximumHomeDistance;
    }

    public boolean isWithinHomeDistanceCurrentPosition() {
        return this.isWithinHomeDistanceFromPosition(new BlockPos(this));
    }

    static {
        setLegacyClickGuiChildLimitState(0);
        net.minecraft.entity.EntityCreature.FLEEING_SPEED_MODIFIER_UUID =
                UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
        net.minecraft.entity.EntityCreature.FLEEING_SPEED_MODIFIER =
                new AttributeModifier(
                                net.minecraft.entity.EntityCreature.FLEEING_SPEED_MODIFIER_UUID,
                                "Fleeing speed bonus",
                                2.0,
                                2)
                        .setSaved(false);
    }

    // region String decoding support

    // endregion

    public boolean hasPath() {
        return !this.navigator.noPath();
    }

    public void setHomePosAndDistance(BlockPos bfnValue, int bcPValue) {
        this.homePosition = bfnValue;
        this.maximumHomeDistance = bcPValue;
    }

    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere()
                && this.getBlockPathWeight(
                                new BlockPos(
                                        this.posX, this.getEntityBoundingBox().minY, this.posZ))
                        >= 0.0F;
    }
}
