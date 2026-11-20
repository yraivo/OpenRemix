package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

import java.util.List;

public class EntityAIAvoidEntity extends EntityAIBase {
    private PathNavigate entityPathNavigate;
    public final Predicate canBeSeenSelector = new VisibleEntityAvoidancePredicate(this);
    private double farSpeed;
    private float avoidDistance;
    private Predicate avoidTargetSelector;
    private double nearSpeed;
    private PathEntity entityPathEntity;
    protected EntityCreature theEntity;
    protected Entity closestLivingEntity;

    @Override
    public boolean shouldExecute() {
        List entitiesInAABBexcluding =
                this.theEntity.world.getEntitiesInAABBexcluding(
                        this.theEntity,
                        this.theEntity
                                .getEntityBoundingBox()
                                .expand(this.avoidDistance, 3.0, this.avoidDistance),
                        Predicates.and(
                                new Predicate[] {
                                    IEntitySelector.NOT_SPECTATING,
                                    this.canBeSeenSelector,
                                    this.avoidTargetSelector
                                }));
        if (entitiesInAABBexcluding.isEmpty()) {
            return false;
        }

        this.closestLivingEntity = (Entity) entitiesInAABBexcluding.get(0);
        Vec3 vec3 =
                RandomPositionGenerator.findRandomTargetBlockAwayFrom(
                        this.theEntity,
                        16,
                        7,
                        new Vec3(
                                this.closestLivingEntity.posX,
                                this.closestLivingEntity.posY,
                                this.closestLivingEntity.posZ));
        if (vec3 == null) {
            return false;
        }

        if (this.closestLivingEntity.getDistanceSq(vec3.x, vec3.y, vec3.z)
                < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity)) {
            return false;
        }

        this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(vec3.x, vec3.y, vec3.z);
        return this.entityPathEntity == null
                ? false
                : this.entityPathEntity.isDestinationSame(vec3);
    }

    @Override
    public boolean continueExecuting() {
        return !this.entityPathNavigate.noPath();
    }

    @Override
    public void updateTask() {
        String text = EntityAIBase.cJ();
        if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0) {
            this.theEntity.getNavigator().setSpeed(this.nearSpeed);
            if (text != null) {
                return;
            }
        }

        this.theEntity.getNavigator().setSpeed(this.farSpeed);
    }

    @Override
    public void startExecuting() {
        this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
    }

    @Override
    public void resetTask() {
        this.closestLivingEntity = null;
    }

    public EntityAIAvoidEntity(
            EntityCreature bhSValue,
            Predicate predicate,
            float avoidDistanceValue,
            double farSpeedValue,
            double nearSpeedValue) {
        this.theEntity = bhSValue;
        this.avoidTargetSelector = predicate;
        this.avoidDistance = avoidDistanceValue;
        this.farSpeed = farSpeedValue;
        this.nearSpeed = nearSpeedValue;
        this.entityPathNavigate = bhSValue.getNavigator();
        this.setMutexBits(1);
    }

    // Helper used only by this class.
    static class VisibleEntityAvoidancePredicate implements com.google.common.base.Predicate {
        net.minecraft.entity.ai.EntityAIAvoidEntity field_98219_c;

        public boolean func_180419_a(net.minecraft.entity.Entity entity) {
            return entity.isEntityAlive()
                    && this.field_98219_c.theEntity.getEntitySenses().canSee(entity);
        }

        VisibleEntityAvoidancePredicate(net.minecraft.entity.ai.EntityAIAvoidEntity bhNValue) {
            this.field_98219_c = bhNValue;
        }

        public boolean apply(java.lang.Object object) {
            return this.func_180419_a((net.minecraft.entity.Entity) object);
        }
    }
}
