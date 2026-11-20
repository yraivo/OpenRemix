package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIAttackOnCollide extends EntityAIBase {
    protected EntityCreature attacker;
    boolean longMemory;
    private double sX;
    private double sV;
    double speedTowardsTarget;
    int attackTick;
    private double sL;
    World worldObj;
    private int delayCounter;
    PathEntity entityPathEntity;
    Class classTarget;

    public boolean shouldExecute() {
        LivingEntity livingEntity = this.attacker.getAttackTarget();
        if (livingEntity == null) {
            return false;
        }

        if (!livingEntity.isEntityAlive()) {
            return false;
        }

        if (this.classTarget != null
                && !this.classTarget.isAssignableFrom(livingEntity.getClass())) {
            return false;
        }

        this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(livingEntity);
        return this.entityPathEntity != null;
    }

    public EntityAIAttackOnCollide(
            EntityCreature entityCreature, Class bhVValue, double p171_2, boolean p171_3) {
        this(entityCreature, p171_2, p171_3);
        this.classTarget = bhVValue;
    }

    public EntityAIAttackOnCollide(
            EntityCreature bhSValue, double speedTowardsTargetValue, boolean longMemoryValue) {
        this.attacker = bhSValue;
        this.worldObj = bhSValue.world;
        this.speedTowardsTarget = speedTowardsTargetValue;
        this.longMemory = longMemoryValue;
        this.setMutexBits(3);
    }

    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
        this.delayCounter = 0;
    }

    public boolean continueExecuting() {
        LivingEntity livingEntity = this.attacker.getAttackTarget();
        return livingEntity == null
                ? false
                : (!livingEntity.isEntityAlive()
                        ? false
                        : (!this.longMemory
                                ? !this.attacker.getNavigator().noPath()
                                : this.attacker.isWithinHomeDistanceFromPosition(
                                        new BlockPos(livingEntity))));
    }

    protected double method_d2bb536412a0d5078fca(LivingEntity livingEntity) {
        return this.attacker.width * 2.0F * this.attacker.width * 2.0F + livingEntity.width;
    }

    public void resetTask() {
        this.attacker.getNavigator().clearPathEntity();
    }

    public void updateTask() {
        String text = EntityAIBase.cJ();
        LivingEntity livingEntity = this.attacker.getAttackTarget();
        String textValue = text;
        this.attacker.getLookHelper().setLookPositionWithEntity(livingEntity, 30.0F, 30.0F);
        double distanceSq =
                this.attacker.getDistanceSq(
                        livingEntity.posX,
                        livingEntity.getEntityBoundingBox().minY,
                        livingEntity.posZ);
        double d1 = this.method_d2bb536412a0d5078fca(livingEntity);
        this.delayCounter--;
        if ((this.longMemory || this.attacker.getEntitySenses().canSee(livingEntity))
                && this.delayCounter <= 0
                && (this.sV == 0.0 && this.sL == 0.0 && this.sX == 0.0
                        || livingEntity.getDistanceSq(this.sV, this.sL, this.sX) >= 1.0
                        || this.attacker.getRNG().nextFloat() < 0.05F)) {
            {
                this.sV = livingEntity.posX;
                this.sL = livingEntity.getEntityBoundingBox().minY;
                this.sX = livingEntity.posZ;
                this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
                if (distanceSq > 1024.0) {
                    this.delayCounter += 10;
                    if (!(textValue != null)) {

                        if (distanceSq > 256.0) {
                            this.delayCounter += 5;
                        }
                    }
                } else {

                    if (distanceSq > 256.0) {
                        this.delayCounter += 5;
                    }
                }
            }

            if (!this.attacker
                    .getNavigator()
                    .tryMoveToEntityLiving(livingEntity, this.speedTowardsTarget)) {
                this.delayCounter += 15;
            }
        }

        this.attackTick = Math.max(this.attackTick - 1, 0);
        if (distanceSq <= d1 && this.attackTick <= 0) {
            this.attackTick = 20;
            if (this.attacker.getHeldItem() != null) {
                this.attacker.swingItem();
            }

            this.attacker.attackEntityAsMob(livingEntity);
        }
    }
}
